package io.securitize.scripts;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.dashboard.TestStatus;
import io.securitize.infra.database.MySqlDatabase;
import io.securitize.infra.googleapi.ApiEndpointsStatusSheet;
import io.securitize.infra.googleapi.GoogleSheets;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.scripts.checkapichanges.MicroserviceMapping;
import io.securitize.tests.abstractClass.AbstractTest;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.jetbrains.annotations.NotNull;
import org.testng.SkipException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.*;

public class ApiCheckChangesStatusSpreadsheetUpdate extends AbstractTest {

    private static final String EXTRACT_SWAGGER_FROM_JS_REGEX = "swaggerDoc[\"|']: (.*?);";
    private static final boolean ONLY_GET_ENDPOINTS = true;

    private static final String SQL_PATH = "sql/getLastStatusOfEveryTest.sql";
    private static List<TestStatus> testStatusesCache = null;
    static Map<String, List<String>> errors = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException, SQLException {
        LocalTime startTime = LocalTime.now();

        // read remote Google sheet mapping all microservices by domains
        var dataFromGoogleSheets = GoogleSheets.readDataFromSheets(Users.getProperty(UsersProperty.ValidateApiSwaggerMappingsSpreadsheetId), MicroserviceMapping.class);

        // prepare object to hold all data to be sent to google sheets
        //  domain   service name   endpoint state
        Map<String, Map<String, Map<String, String>>> data = buildData(dataFromGoogleSheets);

        // write data to GoogleSheets
        ApiEndpointsStatusSheet.updateTestedApiEndpoints(data, errors);

        // Record end time
        LocalTime endTime = LocalTime.now();

        // Calculate duration
        Duration duration = Duration.between(startTime, endTime);

        // Format the duration into the desired format
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        // Print the formatted duration
        debug(String.format("Finished in: %02d:%02d:%02d%n", hours, minutes, seconds));
    }

    @NotNull
    private static Map<String, Map<String, Map<String, String>>> buildData(Map<String, List<MicroserviceMapping>> dataFromGoogleSheets) throws SQLException, IOException {
        Map<String, Map<String, Map<String, String>>> data = new HashMap<>();
        for (Map.Entry<String, List<MicroserviceMapping>> currentEntry : dataFromGoogleSheets.entrySet()) {
            String currentDomain = currentEntry.getKey();
            List<MicroserviceMapping> microserviceMappings = currentEntry.getValue();

            debug(String.format("Working on domain: %s (%s microservices)%n", currentDomain, microserviceMappings.size()));
            Map<String, Map<String, String>> serviceData = new HashMap<>();
            for (MicroserviceMapping currentMicroService : microserviceMappings) {
                analyzeMicroService(currentDomain, serviceData, currentMicroService);
            }
            data.put(currentDomain, serviceData);
        }
        return data;
    }

    private static void analyzeMicroService(String currentDomain, Map<String, Map<String, String>> serviceData, MicroserviceMapping currentMicroService) throws SQLException, IOException {
        String microServiceName = currentMicroService.getMicroServiceName();
        debug("\tWorking on microservice: " + microServiceName);
        String currentContent = getMicroserviceRemoteSwaggerContent(currentDomain, currentMicroService);
        if (currentContent == null) {
            return;
        } else if (currentContent.toLowerCase().contains("<html")) {
            String message = "Remote content is HTML and not JSON.";
            warning(message, false);
            addError(currentDomain, "An error occur trying to parse openApi file of: " + currentMicroService.getSwaggerUrl() + ". " + message);
        }

        Map<String, String> operationIdsAndStatus = new HashMap<>();
        OpenAPIParser openAPIParser = new OpenAPIParser();
        SwaggerParseResult openApiContent;
        Paths paths;
        try {
            openApiContent = openAPIParser.readContents(currentContent, null, null);
            paths = openApiContent.getOpenAPI().getPaths();
        } catch (Exception e) {
            warning("An error occur trying to parse openApi file of: " + currentMicroService.getSwaggerUrl());
            addError(currentDomain, "An error occur trying to parse openApi file of: " + currentMicroService.getSwaggerUrl());
            return;
        }
        debug("\tFound " + paths.size() + " paths");
        for (String currentPath : paths.keySet()) {
            analyzePath(currentDomain,operationIdsAndStatus, paths, currentPath);
        }
        serviceData.put(microServiceName, operationIdsAndStatus);
    }

    private static void analyzePath(String currentDomain, Map<String, String> operationIdsAndStatus, Paths paths, String currentPath) throws SQLException, IOException {
        debug("\t\tPath: " + currentPath + ":");
        var operations = paths.get(currentPath).readOperationsMap();
        for (Map.Entry<PathItem.HttpMethod, Operation> currentEntry : operations.entrySet()) {
            PathItem.HttpMethod currentOperation = currentEntry.getKey();
            Operation currentOperationObject = currentEntry.getValue();
            if (ONLY_GET_ENDPOINTS && currentOperation != PathItem.HttpMethod.GET) {
                continue;
            }
            String currentOperationID = currentOperationObject.getOperationId();
            if (currentOperationID == null) {
                String message = String.format("Missing operationId for %s on path %s", currentOperation, currentPath);
                warning(message, false);
                addError(currentDomain, message);
                return;
            }
            String lastExecutionTestStatus = getLastOperationIdRunResult(currentOperationID);
            debug(String.format("\t\t\t* %s: %s - last run result: %s%n", currentOperation, currentOperationID, lastExecutionTestStatus));
            operationIdsAndStatus.put(currentOperationID, lastExecutionTestStatus);
        }
    }

    private static String getMicroserviceRemoteSwaggerContent(String domainName, MicroserviceMapping microserviceMapping) {
        String remoteSwaggerUrl = microserviceMapping.getSwaggerUrl();
        if ((remoteSwaggerUrl == null) || !(remoteSwaggerUrl.toLowerCase().startsWith("http"))) {
            addError(domainName, String.format("Invalid swagger url for service %s: %s ", microserviceMapping.getMicroServiceName(), remoteSwaggerUrl));
            debug("Skipping " + remoteSwaggerUrl);
            return null;
        }

        String updatedRemoteUrl = getUpdatedRemoteUrl(remoteSwaggerUrl);
        // read remote swagger content
        String currentContent = null;
        try {
            currentContent = readRemoteContent(updatedRemoteUrl);
        } catch (FileNotFoundException e) {
            updatedRemoteUrl = updatedRemoteUrl.replace(".json", "/swagger-ui-init.js");
            try {
                currentContent = readRemoteContent(updatedRemoteUrl);
            } catch (Exception ex) {
                warning("An error occur trying to read remote url of: " + updatedRemoteUrl + ". Details: " + ex, false);
                addError(domainName, "An error occur trying to read remote url of: " + updatedRemoteUrl);
            }
        } catch (Exception e) {
            warning("An error occur trying to read remote url of: " + updatedRemoteUrl + ". Details: " + e, false);
            addError(domainName, "An error occur trying to read remote url of: " + updatedRemoteUrl);
        }
        return currentContent;
    }


    private static String getUpdatedRemoteUrl(String remoteSwaggerUrl) {
        if (remoteSwaggerUrl.isBlank() || (!remoteSwaggerUrl.startsWith("http"))) {
            String message = "Invalid remote url: " + remoteSwaggerUrl;
            info(message);
            throw new SkipException(message);
        }

        remoteSwaggerUrl = remoteSwaggerUrl
                .replace(".dev.", ".%s.")
                .replace(".sandbox.", ".%s.")
                .replace(".rc.", ".%s.");

        // remove un-needed characters
        remoteSwaggerUrl = remoteSwaggerUrl.replace("#", "");
        while (remoteSwaggerUrl.endsWith("/")) {
            remoteSwaggerUrl = remoteSwaggerUrl.substring(0, remoteSwaggerUrl.length() - 1);
        }

        if (remoteSwaggerUrl.endsWith("/api-docs")) {
            remoteSwaggerUrl = remoteSwaggerUrl.replace("/api-docs", "/swagger.json");
        } else if (remoteSwaggerUrl.endsWith("/api/swagger")) {
            remoteSwaggerUrl += ".json";
        } else if (remoteSwaggerUrl.endsWith("/doc/swagger")) {
            remoteSwaggerUrl += "-json";
        } else if (remoteSwaggerUrl.endsWith("/docs")) {
            remoteSwaggerUrl += "/swagger-ui-init.js";
        } else if (remoteSwaggerUrl.endsWith("/swagger")) {
            remoteSwaggerUrl += ".json";
        } else if (remoteSwaggerUrl.endsWith("/documentation")) {
            remoteSwaggerUrl = remoteSwaggerUrl.replace("documentation", "swagger.json");
        } else if (remoteSwaggerUrl.endsWith("/docs/index.html")) {
            remoteSwaggerUrl = remoteSwaggerUrl.replace("/docs/index.html", "/docs/json");
        } else if (remoteSwaggerUrl.endsWith("/docs/static/index.html")) {
            remoteSwaggerUrl = remoteSwaggerUrl.replace("/docs/static/index.html", "/docs/json");
        }

        if (MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production")) {
            return remoteSwaggerUrl.replace(".%s", "");
        } else {
            return String.format(remoteSwaggerUrl, MainConfig.getProperty(MainConfigProperty.environment));
        }
    }


    private static String readRemoteContent(String urlAsString) throws IOException {
        URL url = new URL(urlAsString);
        String result;
        try (InputStreamReader stream = new InputStreamReader(url.openStream())) {
            try (BufferedReader reader = new BufferedReader(stream)) {
                result = reader.lines().collect(Collectors.joining());
            }
        }

        if (urlAsString.endsWith(".js")) {
            result = RegexWrapper.getFirstGroup(result, EXTRACT_SWAGGER_FROM_JS_REGEX);
        }

        return result;
    }

    private static String getLastOperationIdRunResult(String testName) throws SQLException, IOException {
        if (testStatusesCache == null) {
            String query = ResourcesUtils.getResourceContentAsString(SQL_PATH);
            String environment = MainConfig.getProperty(MainConfigProperty.environment);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("environment", environment);
            testStatusesCache = MySqlDatabase.query(query, "TEST_STATUS_MAPPING", parameters);
            if (testStatusesCache.isEmpty()) {
                errorAndStop("Can't load list of historic runs from the database.", false);
            }
        }

        var lastTestResult = testStatusesCache.stream()
                .filter(x -> x.getTestName()
                        .toLowerCase()
                        .contains(testName.toLowerCase()))
                .findFirst();
        return lastTestResult.map(TestStatus::getStatus).orElse("NOT IMPLEMENTED");
    }

    private static void addError(String domain, String error) {
        errors.computeIfAbsent(domain, k -> new ArrayList<>());
        errors.get(domain).add(error);
    }
}