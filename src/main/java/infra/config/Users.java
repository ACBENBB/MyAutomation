package infra.config;

import groovy.lang.MissingPropertyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Users {
/*    public class Users {
        // not stored inside the resources folder as this sensitive file should be external and not in the source control
        private static final String PROPERTY_FILE_NAME = "users.properties";
        private static final int VAULT_CACHE_DURATION_HOURS = 4;

        private static Properties properties = null;

        public static synchronized void init() {
            if (properties == null || properties.size() == 0) {
                properties = new Properties();
                loadFromAwsParameterStore();

                File localPropertiesFile = new File(PROPERTY_FILE_NAME);
                if (localPropertiesFile.exists()) {
                    try (FileInputStream input = new FileInputStream(localPropertiesFile)) {
                        properties.load(input);
                    } catch (IOException e) {
                        warning("An error occur trying to load local users config: " + e, false);
                    }
                }

                if (properties.size() == 0) {
                    errorAndStop("No users data was loaded - AWS remote or local. Can't resume", false);
                }
            }
        }

        public static void resetParameters() {
            debug("Resetting parameters...");
            properties = null;

            String genericCacheFileName = getCacheFileName("/qa/generic/");
            File genericCacheFile = new File(genericCacheFileName);
            if (genericCacheFile.exists()) {
                genericCacheFile.delete();
            }

            String environmentCacheFileName = getCacheFileName("/qa/" + MainConfig.getProperty(MainConfigProperty.environment) + "/");
            File environmentCacheFile = new File(environmentCacheFileName);
            if (environmentCacheFile.exists()) {
                environmentCacheFile.delete();
            }
            init();
        }

        private static void loadFromAwsParameterStore() {
            try  {
                String pathPrefix = "/qa/generic/";
                loadAwsParametersByPath(pathPrefix);

                pathPrefix = "/qa/" + MainConfig.getProperty(MainConfigProperty.environment) + "/";
                loadAwsParametersByPath(pathPrefix);

            } catch (SsmException | SdkClientException e) {
                warning("An error occur trying to load parameters from AWS parameter store. Details: " + e, false);
            }
        }


        private static void loadAwsParametersByPath(String pathPrefix) {
            // env variable that should only exist if running as part of a Jenkins job - we don't want vault cache
            String jenkinsHome = System.getenv("JENKINS_HOME");
            String bitbucketCI = System.getenv("CI");

            // running on Jenkins or Bitbucket - no cache allowed (reading or creating)
            if (StringUtils.isNotEmpty(jenkinsHome) || StringUtils.isNotEmpty(bitbucketCI)) {
                loadAwsParametersByPathFromVault(pathPrefix, false);
            } else if (isAwsCacheFileExists(pathPrefix)) { // running locally and cache file exists
                loadAwsParametersByPathFromCache(pathPrefix);
            } else { // running locally and cache file is too old or doesn't exist
                loadAwsParametersByPathFromVault(pathPrefix, true);
            }
        }

        private static String getCacheFileName(String pathPrefix) {
            pathPrefix = pathPrefix.replace("/", "-");
            if (pathPrefix.endsWith("-")) {
                pathPrefix = pathPrefix.substring(0, pathPrefix.length() - 1);
            }
            return "vault-cache" + pathPrefix + ".properties";
        }
        private static boolean isAwsCacheFileExists(String pathPrefix) {
            String cacheFileName = getCacheFileName(pathPrefix);
            Path path = Paths.get(cacheFileName);

            // Check if file exists
            if (!Files.exists(path)) {
                return false;
            }

            // Get file's last modified time
            try {
                BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
                FileTime lastModifiedTime = attributes.lastModifiedTime();

                // Calculate the difference between current time and last modified time
                LocalDateTime lastModified = lastModifiedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime now = LocalDateTime.now();
                long hoursDifference = ChronoUnit.HOURS.between(lastModified, now);

                // Check if the file is not older than 1 day (24 hours)
                if (hoursDifference <= VAULT_CACHE_DURATION_HOURS) {
                    return true;
                } else {
                    debug(String.format("Vault file of %s has gone stale.. Age in hours: %s > %s. Refreshing it...", cacheFileName, hoursDifference, VAULT_CACHE_DURATION_HOURS));
                    Files.delete(path);
                    return false;
                }
            } catch (Exception e) {
                debug("Unable to determine if vault cache file exists for " + pathPrefix + ". Details: " + ExceptionUtils.getStackTrace(e));
                return false;
            }
        }

        private static void loadAwsParametersByPathFromCache(String pathPrefix) {
            String cacheFileName = getCacheFileName(pathPrefix);

            try (BufferedReader br = new BufferedReader(new FileReader(cacheFileName))) {
                String line;
                int lineCounter = 0;
                while ((line = br.readLine()) != null) {
                    if (line.trim().startsWith("#")) {
                        continue;
                    }
                    String[] values = line.split("=", 2);
                    properties.put(values[0].trim(), values.length > 1 ? values[1].trim() : "");
                    lineCounter++;
                }
                info(String.format("Loaded %s lines from cache of %s (%s)", lineCounter, pathPrefix, cacheFileName));
            } catch (IOException e) {
                errorAndStop(String.format("An error occur trying to load cache of %s. Details:%n%s", pathPrefix, ExceptionUtils.getStackTrace(e)),false);
            }
        }

        private static void loadAwsParametersByPathFromVault(String pathPrefix, boolean isCacheAllowed) {
            SsmClient ssmClient = Ssm.getSsmClient();

            GetParametersByPathRequest parameterByPathRequest = GetParametersByPathRequest.builder()
                    .path(pathPrefix)
                    .withDecryption(true)
                    .build();

            info("Pulling all parameters from " + pathPrefix);
            int counter = 0;
            StopWatch sw = StopWatch.createStarted();

            GetParametersByPathResponse response;
            Map<String, String> valuesForCache = new HashMap<>();
            do {
                response = ssmClient.getParametersByPath(parameterByPathRequest);
                counter += response.parameters().size();
                for (Parameter current : response.parameters()) {
                    String key = current.name().replace(pathPrefix, "");
                    String value = current.value();
                    properties.put(key, value);
                    valuesForCache.put(key, value);
                }
                parameterByPathRequest = GetParametersByPathRequest.builder()
                        .path(pathPrefix)
                        .withDecryption(true)
                        .nextToken(response.nextToken())
                        .build();
            }
            while (StringUtils.isNotBlank(response.nextToken()));
            info(String.format("Loaded %s parameters in %sms", counter, sw.getTime()));

            if (isCacheAllowed) {
                // write cache file
                writeCacheFile(pathPrefix, valuesForCache);
            }
        }

        private static void writeCacheFile(String pathPrefix, Map<String, String> valuesForCache) {
            String cacheFileName = getCacheFileName(pathPrefix);

            try (FileWriter writer = new FileWriter(cacheFileName)) {
                for (Map.Entry<String, String> entry : valuesForCache.entrySet()) {
                    if (!entry.getKey().trim().isEmpty()) {
                        writer.write(entry.getKey() + "=" + entry.getValue().trim() + System.lineSeparator());
                    }
                }
                debug("Cache file generated successfully: " + cacheFileName);
            } catch (IOException e) {
                warning(String.format("An error occur trying to create cache file of %s. Details: %s", cacheFileName, ExceptionUtils.getStackTrace(e)), false);
            }
        }

        public static String getProperty(UsersProperty propertyName) {
            return getProperty(propertyName.name(), false);
        }

        public static int getIntProperty(UsersProperty propertyName) {
            return Integer.parseInt(getProperty(propertyName));
        }

        public static String getIssuerDetails(String issuerName, IssuerDetails propertyName) {
            // first try to obtain issuer details without specifying the environment - as stored in AWS parameter store
            String fullPropertyName = issuerName + "_" + propertyName;
            String value = getProperty(fullPropertyName, true);

            // if not found try specifying the environment as prefix - as used in local file
            if (value == null) {
                String env = MainConfig.getProperty(MainConfigProperty.environment);
                fullPropertyName = env + "_" + issuerName + "_" + propertyName;
                value = getProperty(fullPropertyName, true);
            }

            if (value != null) {
                return value;
            } else {
                throw new MissingPropertyException("Can't find property " + propertyName + " for issuer " + issuerName + ". Searched by " + fullPropertyName + ". Validate value exists in the users properties file");
            }
        }

        public static WalletDetails getIssuerWalletDetails(String issuerName, TestNetwork testNetwork) {
            return new WalletDetails(
                    getIssuerDetails(issuerName.toLowerCase(), IssuerDetails.valueOf(testNetwork.toString().toLowerCase() + "SignerWalletAddress")),
                    getIssuerDetails(issuerName.toLowerCase(), IssuerDetails.valueOf(testNetwork.toString().toLowerCase() + "SignerPrivateKey")));
        }

        public static void updateAutomationCpBearer(String value) {
            String environment = MainConfig.getProperty(MainConfigProperty.environment);
            String parameterFullPath = "/qa/" + environment + "/automationCpBearer";

            // make sure we store the actual bearer value with the "Bearer%20" prefix
            value = value.replace("Bearer%20", "");

            try  {
                SsmClient ssmClient = Ssm.getSsmClient();
                PutParameterRequest parameterRequest = PutParameterRequest.builder()
                        .name(parameterFullPath)
                        .value(value)
                        .description("automatically updated automationCpBearer for API calls on " + environment)
                        .type(ParameterType.SECURE_STRING)
                        .overwrite(true)
                        .build();

                PutParameterResponse parameterResponse = ssmClient.putParameter(parameterRequest);
                info("updateAutomationCpBearer status: " + parameterResponse.tierAsString());

            } catch (SsmException e) {
                errorAndStop("An error occur trying to update 'automationCpBearer' on " + environment + ". Details: " + e, false);
            }
        }


        private static String getProperty(String propertyName, boolean allowNullValue) {
            String valueFromEnv = System.getenv(propertyName);
            if (valueFromEnv != null) {
                return valueFromEnv;
            }

            init();

            String value = properties.getProperty(propertyName);
            if (value == null && !allowNullValue) {
                errorAndStop("could not find value for " + propertyName, false);
            }
            return value;
        }*/
    }
