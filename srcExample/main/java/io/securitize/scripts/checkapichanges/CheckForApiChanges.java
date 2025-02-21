package io.securitize.scripts.checkapichanges;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.googleapi.GoogleSheets;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.tests.abstractClass.AbstractTest;
import io.swagger.v3.oas.models.PathItem;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.openapitools.openapidiff.core.output.HtmlRender;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.*;

public class CheckForApiChanges extends AbstractTest {

    private static final String EXTRACT_SWAGGER_FROM_JS_REGEX = "swaggerDoc[\"|']: (.*?);";
    private static final boolean CHECK_ONLY_GET_ENDPOINTS = true;
    private static final String SWAGGER_DIFF_CSS = "https://jenkins.rc.securitize.io/userContent/swaggerDiff.css";

    @DataProvider(name = "swaggerFilesScenariosProvider", parallel = true)
    public Object[][] createTestScenarios() throws IOException {
        var dataFromGoogleSheets = GoogleSheets.readDataFromSheets(Users.getProperty(UsersProperty.ValidateApiSwaggerMappingsSpreadsheetId), MicroserviceMapping.class);

        List<Object[]> result = new ArrayList<>();
        for (String currentDomainName : dataFromGoogleSheets.keySet()) {
            var currentDomainTests = dataFromGoogleSheets.get(currentDomainName);
            for (MicroserviceMapping currentMapping : currentDomainTests) {
                Object[] current = new Object[] {
                        currentMapping.getMicroServiceName(),
                        currentMapping.getMicroServiceName() + ".json",
                        currentMapping.getSwaggerUrl(),
                        currentDomainName};
                result.add(current);
            }
        }
        return result.toArray(new Object[result.size()][]);
    }


    @Test(dataProvider = "swaggerFilesScenariosProvider")
    public void checkForApiChanges(String scenarioName, String baselineFilePath, String remoteSwaggerUrl, String domainName) throws IOException {
        MultiReporter.setTestCategory(domainName);

        if (remoteSwaggerUrl == null) {
            String message = String.format("Skipping scenario: %s%nbaseline: %s%n because remoteUrl is null", scenarioName, baselineFilePath);
            info(message);
            throw new SkipException(message);
        }
        String updatedRemoteUrl = getUpdatedRemoteUrl(remoteSwaggerUrl);
        info(String.format("Running scenario: %s%nbaseline: %s%nremoteUrl: %s", scenarioName, baselineFilePath, updatedRemoteUrl));

        // read baseline content
        String baselineContent = getBaselineContent(baselineFilePath);

        // read remote swagger content
        String currentContent = "";
        try {
            currentContent = readRemoteContent(updatedRemoteUrl);
        } catch (FileNotFoundException e) {
            updatedRemoteUrl = updatedRemoteUrl.replace(".json", "/swagger-ui-init.js");
            currentContent = readRemoteContent(updatedRemoteUrl);
        } catch (Exception e) {
            errorAndStop("An error occur trying to read remote url of: " + updatedRemoteUrl + ". Details: " + e, false);
        }

        // run the comparison
        ChangedOpenApi diff = OpenApiCompare.fromContents(baselineContent, currentContent);

        // filter comparison results for only GET actions
        if (CHECK_ONLY_GET_ENDPOINTS) {
            diff = filterForGetOnly(diff);
        }

        // output results
        try {
            String resultsAsHTML = new HtmlRender(scenarioName,
                    SWAGGER_DIFF_CSS).render(diff);
            reportRawHTML(resultsAsHTML);
        } catch (Exception e) {
            String plainResults = new ConsoleRender().render(diff);
            info(plainResults);
        }

        var diffResult = diff.isChanged();
        if (diffResult.getWeight() < 2) {
            info("API change status: " + diffResult);
        } else {
            errorAndStop("API has changed!", false);
        }
    }

    private String getBaselineContent(String baselineFilePath) throws IOException {
        // read baseline file from local
        String baselineFileFullPath = ResourcesUtils.searchResourcePathByFileName("/api/specifications", baselineFilePath);
        if (baselineFileFullPath == null) {
            String message = "Can't find baseline file: " + baselineFilePath;
            errorAndStop(message, false);
        }
        assert baselineFileFullPath != null;
        return Files.readString(Paths.get(baselineFileFullPath));
    }

    private String getUpdatedRemoteUrl(String remoteSwaggerUrl) {
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
        }
        return String.format(remoteSwaggerUrl, MainConfig.getProperty(MainConfigProperty.environment));
    }

    private ChangedOpenApi filterForGetOnly(ChangedOpenApi diff) {
        diff = diff.setNewEndpoints(diff.getNewEndpoints().stream().filter(x -> x.getMethod() == PathItem.HttpMethod.GET).collect(Collectors.toList()));
        diff = diff.setMissingEndpoints(diff.getMissingEndpoints().stream().filter(x -> x.getMethod() == PathItem.HttpMethod.GET).collect(Collectors.toList()));
        diff = diff.setChangedSchemas(diff.getChangedSchemas().stream().filter(x -> x.getContext().getMethod() == PathItem.HttpMethod.GET).collect(Collectors.toList()));
        return diff.setChangedOperations(diff.getChangedOperations().stream().filter(x -> x.getHttpMethod() == PathItem.HttpMethod.GET).collect(Collectors.toList()));
    }

    private String readRemoteContent(String urlAsString) throws IOException {
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
}
