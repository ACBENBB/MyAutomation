package io.securitize.tests.securitytests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.abstractClass.AbstractTest;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.warning;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public abstract class AbstractEndpointsSecurityTest extends AbstractTest {
    private static final String OUTPUT_FILE_FOR_SLACK = "securityOutput.txt";
    private String failedMessages = "";
    private String passedMessages = "";

    private static final String[] supportMethodsForNegativeTests = { "Get", "Post", "Put", "Patch"};
    private static final String[] supportMethodsForPositiveTests = { "Get" };
    private static final String[] URL_SUFFIX_IGNORE_LIST = { "/health" };
    private static final String SWAGGER_UI_URL = "/sid/swagger/swagger-ui-init.js";
    private static OpenAPI openAPI;
    private static String bearerToken;
    private static String investorId;
    private final String baseUrl;

    public AbstractEndpointsSecurityTest(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @BeforeClass
    public void beforeClass() throws Exception {
        loadSwaggerJson();
        bearerToken = Users.getProperty(UsersProperty.automationCpBearer);
        investorId = Users.getProperty(UsersProperty.apiSidInvestorId);
    }

    @AfterClass
    public void afterClass() {
        String fullMessage = "";
        if (!failedMessages.isBlank()) {
            fullMessage += String.format("Failed tests:%n%s%n", failedMessages);
        }

        if (!passedMessages.isBlank()) {
            fullMessage += String.format("Passed tests:%n%s", passedMessages);
        }

        try {
            Files.write(Paths.get(OUTPUT_FILE_FOR_SLACK), fullMessage.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            warning("An issue occur trying to append to the slack output file. Details: " + e);
        }
    }


    private synchronized void writeResultToFileForSlack(boolean isPassed, String testType, String expectedStatusCode, String methodName, String url, int actualStatusCode) {
        String statusIcon = isPassed ? "✓" : "x";
        String message = String.format("%s %s test - %s %s (expected: %s actual: %s)%n", statusIcon, testType, methodName, url, expectedStatusCode, actualStatusCode);

        if (isPassed) {
            passedMessages += message;
        } else {
            failedMessages += message;
        }
    }

    private void loadSwaggerJson() throws IOException {
        String fullUrl;
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        if (environment.equalsIgnoreCase("production")) {
            fullUrl = String.format(baseUrl, "");
        } else {
            fullUrl = String.format(baseUrl, environment+".");
        }
        RestAssured.baseURI = fullUrl;

        // load swagger document
        String swaggerDocument = loadSwaggerDocument();

        // parse swagger document
        SwaggerParseResult result = new OpenAPIParser().readContents(swaggerDocument, null, null);
        openAPI = result.getOpenAPI();
    }

    private static String loadSwaggerDocument() throws IOException {
        String rawResponse = given()
                .get(SWAGGER_UI_URL)
                .then()
                .statusCode(200)
                .extract().body().asString();

        try {
            int startPosition = rawResponse.indexOf("\"openapi\"");
            int endPosition = rawResponse.indexOf("};", startPosition);
            return "{" + rawResponse.substring(startPosition).substring(0, endPosition + 2);
        } catch (Exception e) {
            throw new IOException("Can't find swagger json inside response:" + rawResponse);
        }
    }


    public Iterator<String[]> createListOfTestScenarios(String[] supportMethods) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String[]> testCases = new ArrayList<>();
        info("found " + openAPI.getPaths().size() + " endpoints to test!");
        for (String currentPathKey : openAPI.getPaths().keySet()) {
            // ignore paths that match the blacklist
            if (Arrays.stream(URL_SUFFIX_IGNORE_LIST).anyMatch(x -> currentPathKey.toLowerCase().endsWith(x.toLowerCase()))) {
                continue;
            }
            PathItem currentPath = openAPI.getPaths().get(currentPathKey);

            // used to find out to which methods we have mappings: GET, POST, PUT...
            // if found, add it to the list of tests we need to perform
            for (String currentSupportedMethod : supportMethods) {
                Method declaredMethod = currentPath.getClass().getDeclaredMethod("get" + currentSupportedMethod);
                if (declaredMethod.invoke(currentPath) != null) {
                    testCases.add(new String[] {
                            currentSupportedMethod.toUpperCase() + " " + currentPathKey,
                            currentSupportedMethod.toUpperCase(),
                            currentPathKey});
                }
            }
        }
        return testCases.iterator();
    }

    @DataProvider(name = "testDataProviderForNegativeScenarios", parallel = true)
    public Iterator<String[]> createNegativeTestScenarios() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return createListOfTestScenarios(supportMethodsForNegativeTests);
    }

    @DataProvider(name = "testDataProviderForPositiveScenarios", parallel = true)
    public Iterator<String[]> createPositiveTestScenarios() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return createListOfTestScenarios(supportMethodsForPositiveTests);
    }

    public void forbiddenWithoutTokensTest(String testName, String method, String url) {
        if (url.contains("{")) {
            url = url.replaceAll("\\{.+}", "1");
        }

        RequestSpecification given = given()
                .log().ifValidationFails();

        if (!method.equalsIgnoreCase("get")) {
            given = given.contentType(ContentType.JSON)
                    .body("{}");
        }

        info(String.format("Test '%s' starting...%n", testName));
        int statusCode = given
                .when()
                .request(method, url)
                .then()
                .log().ifValidationFails()
                .statusCode(anyOf(is(401),is(403)))
                .extract().statusCode();

        writeResultToFileForSlack(
                statusCode == 401 || statusCode == 403,
                "Negative",
                "401 or 403",
                method,
                url,
                statusCode);

        assertThat(statusCode, anyOf(is(401), is(403)));
        info(String.format("Test %s successful! We got %s!%n", testName, statusCode));
    }


    protected void okWithTokensTest(String testName, String method, String url) {
        if (url.contains("{")) {
            url = url.replaceAll("\\{.+}", investorId);
        }

        RequestSpecification given = given()
                .log().ifValidationFails();

        if (!method.equalsIgnoreCase("get")) {
            given = given.contentType(ContentType.JSON)
                    .body("{}");
        }

        info(String.format("Test '%s' starting...%n", testName));
        int statusCode = given
                .auth().oauth2(bearerToken)
                .when()
                .request(method, url)
                .then()
                .log().ifValidationFails()
                .extract().statusCode();

        writeResultToFileForSlack(
                statusCode == 200,
                "Positive",
                "200",
                method,
                url,
                statusCode);

        assertThat(statusCode, is(200));
        info(String.format("Test %s successful! We got %s!%n", testName, statusCode));
    }
}
