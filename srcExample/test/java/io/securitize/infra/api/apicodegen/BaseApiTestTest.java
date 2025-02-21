package io.securitize.infra.api.apicodegen;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.http.Method;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import org.testng.SkipException;
import org.testng.annotations.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class BaseApiTestTest {
    public static final String TESTS_RESOURCE_PATH = "Tests/";
    public static final String TEST_JSON_SCHEMA = "{\"description\":\"Successful response\",\"content\":{\"application/json\":{\"schema\": {\"type\": \"object\",\"properties\": {\"message\": {\"type\": \"string\",\"example\": \"Hello, World!\"}}}}}}";
    public static final String EXPECTED_BODY = "{\"message\":\"SUCCESS\"}";
    private static WireMockServer wireMockServer;

    private static final BaseApiTest baseApiTest = new BaseApiTest();


    @BeforeClass
    public void setUp() {

        // avoid having our urls to WireMock changed by the CicdApi logic
        MainConfig.setProperty(MainConfigProperty.remoteRunCicdApi, "false");

        WireMockConfiguration wireMockConfiguration = new WireMockConfiguration();
        wireMockConfiguration.dynamicPort();
        wireMockServer = new WireMockServer(wireMockConfiguration);
        wireMockServer.start();
    }

    @AfterClass
    public void tearDown() {
        wireMockServer.stop();
    }

    private static String getCurrentMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[2].getMethodName();
    }


    /* Simple GET test without any parameters */
    @Test
    public void get_NoParameters_Ok() {
        // Arrange
        String urlPath = "/get/NoParameters";
        wireMockServer.stubFor(get(urlPath)
                .willReturn(ok()
                .withBody(EXPECTED_BODY)));

        // Act
        String actualBody = baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA);

        // Assert
        performSimpleBodyAssert(actualBody);
    }

    private void performSimpleBodyAssert(String actualBody) {
        assertThat(actualBody).as("Actual response body").contains(EXPECTED_BODY);
    }


    /* Testing path parameters */
    @Test
    public void get_PathParameterFromOperationIdFile_Ok() {
        // Arrange
        String pathParamName = "pathParam1";
        String urlPath = "/get/PathParam/{" + pathParamName + "}";
        String fullUrlPath = "/get/PathParam/5";
        wireMockServer.stubFor(get(fullUrlPath)
                .willReturn(ok()
                .withBody(EXPECTED_BODY)));

        // Act
        String actualBody = baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA);

        // Assert
        performSimpleBodyAssert(actualBody);
    }


    @Test
    public void get_PathParameterFromGenericOnly_Ok() {
        // Arrange
        String pathParamName = "pathParamFromGeneric";
        String urlPath = "/get/PathParam/{" + pathParamName + "}";
        String fullUrlPath = "/get/PathParam/generic5";
        wireMockServer.stubFor(get(fullUrlPath)
                .willReturn(ok()
                .withBody(EXPECTED_BODY)));

        // Act
        String actualBody = baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA);

        // Assert
        performSimpleBodyAssert(actualBody);
    }


    @Test
    public void get_PathParameterFromFileAndGeneric_Ok() {
        // Arrange
        String pathParamGenericName = "pathParamFromGeneric";
        String pathParamFileName = "pathParamFromFile";
        String urlPath = String.format("/get/PathParam/{%s}/{%s}",pathParamGenericName, pathParamFileName);
        String fullUrlPath = "/get/PathParam/generic5/5";
        wireMockServer.stubFor(get(fullUrlPath)
                .willReturn(ok()
                .withBody(EXPECTED_BODY)));

        // Act
        String actualBody = baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA);

        // Assert
        performSimpleBodyAssert(actualBody);
    }


    @Test
    public void get_MissingPathParameter_SkippedWithMessage() {
        // Arrange
        String pathParamName = "pathParam1";
        String urlPath = "/get/MissingPathParam/{" + pathParamName + "}";
        wireMockServer.stubFor(get(urlPath)
                .willReturn(ok()
                .withBody(EXPECTED_BODY)));

        // Act
        Throwable t = catchThrowable(() -> baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA));

        // Assert
        assertThat(t).as("Thrown exception").isInstanceOf(SkipException.class);
        assertThat(t.getMessage()).as("Exception message").contains("missing", pathParamName);
    }


    /* Testing query parameters */
    @Test
    public void get_QueryParameterFromOperationIdFile_Ok() {
        // Arrange
        String queryParamName = "queryParameterFromOperationIdFile";
        String queryParamValue = "123";
        String urlPath = "/get/QueryParamFromOperationIdFile";
        String urlPathWithQueryParams = String.format("%s?%s=%s", urlPath, queryParamName, queryParamValue);
        wireMockServer.stubFor(get(urlPathWithQueryParams)
                .withQueryParam(queryParamName, equalTo(queryParamValue))
                .willReturn(ok()
                        .withBody(EXPECTED_BODY)));

        // Act
        String actualBody = baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA);

        // Assert
        performSimpleBodyAssert(actualBody);
    }


    /* Testing custom headers */
    @Test
    public void get_HeadersFromOperationIdFile_Ok() {
        // Arrange
        String headerName = "headerFromOperationIdFile";
        String headerValue = "header123";
        String urlPath = "/get/headerFromOperationIdFile";
        wireMockServer.stubFor(get(urlPath)
                .withHeader(headerName, equalTo(headerValue))
                .willReturn(ok()
                        .withBody(EXPECTED_BODY)));

        // Act
        String actualBody = baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA);

        // Assert
        performSimpleBodyAssert(actualBody);
    }


    /* Testing custom responses */
    @Test
    public void get_customExpectedResponseCode_Ok() {
        // Arrange
        String urlPath = "/get/customExpectedResponseCode";
        wireMockServer.stubFor(get(urlPath)
                .willReturn(aResponse().withStatus(418)
                        .withBody(EXPECTED_BODY)));

        // Act
        String actualBody = baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA);

        // Assert
        performSimpleBodyAssert(actualBody);
    }


    @Test
    public void get_customExpectedResponseBody_Ok() {
        // Arrange
        String urlPath = "/get/customExpectedResponseBody";
        String expectedBody = "{\"message\":\"custom body message\"}";
        wireMockServer.stubFor(get(urlPath)
                .willReturn(ok()
                        .withBody(expectedBody)));

        // Act
        String actualBody = baseApiTest.testRequest(Method.GET, wireMockServer.url(urlPath), getCurrentMethodName(), LoginAs.NONE, TESTS_RESOURCE_PATH, TEST_JSON_SCHEMA);

        // Assert
        assertThat(actualBody).as("Actual response body").contains(expectedBody);
    }
}