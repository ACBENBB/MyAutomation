package io.securitize.infra.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;
import io.securitize.infra.aws.Ssm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathResponse;
import software.amazon.awssdk.services.ssm.model.Parameter;
import software.amazon.awssdk.services.ssm.model.SsmException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.securitize.infra.reporting.MultiReporter.*;

public class DataManager {
    private static Config mainConfig = ConfigFactory.load()
            .withFallback(ConfigFactory.systemEnvironment())
            .withFallback(ConfigFactory.parseResources("config/MainConfig.properties"));
    private static Properties awsProperties = new Properties();
    private static Config teamConfig;
    private static Properties databaseProperties;
    private static DataManager instance = null;
    private static String currentTeamName;

    private DataManager() {
        //Hides constructor
    }

    public static synchronized DataManager getInstance(String teamName) {
        if (instance == null) {
            instance = new DataManager();
            instance.loadFromAwsParameterStore();
        }
        if (DataManager.teamConfig == null || !DataManager.currentTeamName.equals(teamName)) {
            if (StringUtils.isBlank(teamName)) {
                error("An error occur trying to load properties from the team folder. The team name cannot be blank.");
            } else {
                DataManager.currentTeamName = teamName;
                DataManager.teamConfig = ConfigFactory.empty();
                DataManager.teamConfig = loadPropertiesFromFolder(teamName);
            }
        }
        return instance;
    }

    public static Config loadPropertiesFromFolder(String teamName) {
        File directory = new File("src/main/resources/config/" + teamName);
        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".properties")) {
                Config config = ConfigFactory.parseFile(file);
                teamConfig = teamConfig.withFallback(config);
            }
        }
        return teamConfig.resolve();
    }

    private void loadFromAwsParameterStore() {
        try {
            SsmClient ssmClient = Ssm.getSsmClient();
            String pathPrefix = "/qa/generic/";
            loadAwsParametersByPath(ssmClient, pathPrefix);

            pathPrefix = "/qa/" + mainConfig.getString("environment") + "/";
            loadAwsParametersByPath(ssmClient, pathPrefix);

        } catch (SsmException | SdkClientException e) {
            warning("An error occur trying to load parameters from AWS parameter store. Details: " + e, false);
        }
    }

    private static void loadAwsParametersByPath(SsmClient ssmClient, String pathPrefix) {
        GetParametersByPathRequest parameterByPathRequest = GetParametersByPathRequest.builder()
                .path(pathPrefix)
                .withDecryption(true)
                .build();

        info("Pulling all parameters from " + pathPrefix);
        int counter = 0;
        StopWatch sw = StopWatch.createStarted();

        GetParametersByPathResponse response;
        do {
            response = ssmClient.getParametersByPath(parameterByPathRequest);
            counter += response.parameters().size();
            for (Parameter current : response.parameters()) {
                awsProperties.put(current.name().replace(pathPrefix, ""), current.value());
            }
            parameterByPathRequest = GetParametersByPathRequest.builder()
                    .path(pathPrefix)
                    .withDecryption(true)
                    .nextToken(response.nextToken())
                    .build();
        }
        while (StringUtils.isNotBlank(response.nextToken()));
        info(String.format("Loaded %s parameters in %sms", counter, sw.getTime()));
    }

    private void loadDatabaseProperties() {
        //TODO Implement connecting to the database and get values.
        // Note: in ATS we have two databases: Mongo and MySQL
    }

    public String getMainConfigProperty(String key) {
        return mainConfig.getString(key);
    }

    public String getAwsProperty(String key) {
        return awsProperties.getProperty(key);
    }

    public String getTeamProperty(String key) {
        return teamConfig.getString(key);
    }

    private String getDatabaseProperty(String key) {
        return databaseProperties.getProperty(key);
    }

}