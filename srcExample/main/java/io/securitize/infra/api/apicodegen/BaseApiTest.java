package io.securitize.infra.api.apicodegen;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.Method;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import io.securitize.infra.api.apicodegen.model.OperationDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.exceptions.InvalidResponseStatusCode;
import io.securitize.infra.utils.JsonUtils;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.apiTests.cicd.SID.SID_SecIdApiService.SID_SecIdApiService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import org.web3j.utils.Files;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.*;
import static io.securitize.infra.utils.Authentication.getTOTPCode;

public class BaseApiTest extends AbstractTest {

    private static final String[] ENVIRONMENTS_NAMES = {"RC", "SANDBOX", "QA", "PRODUCTION"};
    protected static ArrayList<String> testsToSkipList = null;
    protected static Map<String, String> securitizeIdSpecCookies = null;
    protected static String securitizeIdFirstCookie = null;
    protected static String securitizeIdSpecBearer = null;
    protected static String securitizeIdSpecCode = "";
    protected static Map<String, String> cashAccountSecuritizeIdSpecCookies = null;
    protected static String cashAccountSecuritizeIdFirstCookie = null;
    protected static String cashAccountSecuritizeIdSpecBearer = null;
    private static Map<String, String> operatorSpecCookies = null;
    private static String nieFirstCookie = null;
    private static String nieSpecBearer = null;
    private static long countOfHashtags = 0;
    final boolean DEBUG_MODE = false;
    HashMap<String, Object> genericFilePathParams = new HashMap<>();


    @BeforeSuite(alwaysRun = true)
    protected void initAbstractApiTestSuite(ITestContext testContext) {
        Users.init();
    }

    public String testRequest(Method method, String endpoint, String operationId, LoginAs loginAs, String resourcePath, String jsonSchema, String... securitizeIdAuthorizeIssuerCode) {
        return testRequest(method, endpoint, operationId, loginAs, resourcePath, jsonSchema, null, securitizeIdAuthorizeIssuerCode);
    }

    protected String testRequest(Method method, String endpoint, String operationId, LoginAs loginAs, String resourcePath, String jsonSchema, Map<String, Object> parameters, String... securitizeIdAuthorizeIssuerCode) {
        endpoint = getEndpointUrl(endpoint);

        OperationDetails operationDetails = readOperationDetails(resourcePath, operationId, endpoint);

        if (operationDetails.getSkips().isSkipEntireTest()) {
            skip(operationDetails.getSkips().getSkipReason());
            throw new SkipException(operationDetails.getSkips().getSkipReason());
        }

        RequestSpecification currentSpec = getSpec(loginAs, securitizeIdAuthorizeIssuerCode);
        if (DEBUG_MODE) {
            currentSpec = currentSpec.log().all();
        }

        updateCurrentSpec(operationDetails, endpoint, currentSpec, parameters);

        ValidatableResponse validatableResponse = currentSpec
                .when()
                .request(method, endpoint)
                .then();

        if (DEBUG_MODE) {
            validatableResponse = validatableResponse.log().all();
        }

        // validate status code if not marked as skipped
        if (operationDetails.getSkips().isSkipStatusCodeCheck()) {
            System.out.println("*** Skipping status code check as requested ***");
        } else {
            int actualStatusCode = validatableResponse.extract().statusCode();
            int expectedStatusCode = getExpectedStatusCode(operationDetails, method);
            if (actualStatusCode != expectedStatusCode) {
                throw new InvalidResponseStatusCode(expectedStatusCode, actualStatusCode);
            }
        }

        // validate body schema if not marked as skipped
        if (operationDetails.getSkips().isSkipBodySchemaCheck()) {
            System.out.println("*** Skipping body schema check as requested ***");
        } else {
            String returnBody = validatableResponse.extract().asString();
            if (!Objects.equals(returnBody, "")) {
                validatableResponse
                        .body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema(jsonSchema));
            }
        }

        // validate actual body json content if not marked as skipped
        if (operationDetails.getSkips().isSkipBodyContentCheck()) {
            System.out.println("*** Skipping body content check as requested ***");
        } else {
            HashMap<String, Object> expectedBodyValues = operationDetails.getExpectedResponse().getBody();
            updatePlaceHoldersFromParameters(expectedBodyValues, parameters);
            for (String currentResponseBodyKey : expectedBodyValues.keySet()) {
                Object expected = expectedBodyValues.get(currentResponseBodyKey);
                Object actual = JsonUtils.searchJsonRecursivelyForKey(JsonParser.parseString(validatableResponse.extract().asString()), currentResponseBodyKey);
                Assert.assertEquals(actual, expected, "Mismatch comparing value for json key " + currentResponseBodyKey);
            }
        }

        return validatableResponse.extract().body().asString();
    }


    private void updateCurrentSpec(OperationDetails operationDetails, String endpoint, RequestSpecification currentSpec, Map<String, Object> parameters) {
        HashMap<String, ?> pathParams = operationDetails.getPathParams();
        // check we got all the path parameters as needed in the url
        int expectedPathParamsCount = StringUtils.countMatches(endpoint, "{");
        if (expectedPathParamsCount > 0 &&
                ((pathParams == null) || (expectedPathParamsCount != pathParams.size()))) {
            String skipReason = "Skipping test as not all path parameters were provided";
            skip(skipReason);
            throw new SkipException(skipReason);
        }
        if (pathParams != null) {
            currentSpec = currentSpec.pathParams(pathParams);
        }

        HashMap<String, Object> queryParams = operationDetails.getQueryParams();
        if (queryParams != null) {
            updatePlaceHoldersFromParameters(queryParams, parameters);
            currentSpec = currentSpec.queryParams(queryParams);
        }

        HashMap<String, Object> headers = operationDetails.getHeaders();
        if (headers != null) {
            updatePlaceHoldersFromParameters(headers, parameters);
            currentSpec = currentSpec.headers(headers);
        }

        HashMap<String, Object> body = operationDetails.getBody();
        if (body != null) {
            if (body.containsKey("code") && body.get("code").equals("")) {
                body.replace("code", "", securitizeIdSpecCode);
            }
            updatePlaceHoldersFromParameters(body, parameters);
            currentSpec = currentSpec
                    .contentType("application/json")
                    .body(body);
        }
    }

    @NotNull
    private String getEndpointUrl(String endpoint) {
        String actualEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        if (endpoint.contains("{environment}")) {
            if (actualEnvironment.equals("production")) {
                endpoint = endpoint.replace("{environment}.", "");
            } else {
                endpoint = endpoint.replace("{environment}", actualEnvironment);
            }
        } else if (MainConfig.getProperty(MainConfigProperty.remoteRunCicdApi).equals("true")) {
            endpoint = setCurrentInternalUrl(endpoint, actualEnvironment);
        } else {
            endpoint = endpoint.replace(".{internalUrlToRemoteRunCicdApi}", "");
        }
        return endpoint;
    }

    protected OperationDetails readOperationDetails(String resourceJsonFileName, String operationId, String endpoint) {
        //handle skips
        populateTestsToSkipList(resourceJsonFileName);
        if (testsToSkipList != null && testsToSkipList.contains(operationId)) {
            String skipReason = "Test found in Skip List. Please review skip.json";
            skip(skipReason);
            throw new SkipException(skipReason);
        }

        populateGenericFileMicroservicePathParams(resourceJsonFileName);

        String operationInputsFilePath = ResourcesUtils.searchResourcePathByFileName("/api/content/" + resourceJsonFileName, operationId + ".json");
        if (operationInputsFilePath == null) {
            List<String> requiredPathParams = getRequiredPathParams(endpoint);
            OperationDetails operationDetails = new OperationDetails();
            operationDetails.setPathParams(getDefaultPathParams(endpoint));
            String loadedPathParamsAsString = operationDetails.getPathParams().keySet().toString();
            info("Loaded Path Parameters from default dictionary " + loadedPathParamsAsString);
            ArrayList<String> missingPathParams = new ArrayList<>(CollectionUtils.subtract(requiredPathParams, operationDetails.getPathParams().keySet()));
            if (missingPathParams.size() > 0) {
                String skipReason = "Even after loading default parameters, some are missing: " + missingPathParams;
                skip(skipReason);
                throw new SkipException(skipReason);
            } else {
                return operationDetails;
            }
        }

        return populateInputsFilePathParams(endpoint, operationInputsFilePath);
    }

    @NotNull
    private OperationDetails populateInputsFilePathParams(String endpoint, String operationInputsFilePath) {
        OperationDetails operationDetails;
        try {
            String jsonInputsFileContent = Files.readString(new File(operationInputsFilePath));
            jsonInputsFileContent = updateJsonWithEnvironmentSpecificValues(jsonInputsFileContent);
            if (jsonInputsFileContent.contains("#")) {
                jsonInputsFileContent = updatePlaceholders(jsonInputsFileContent);
            }
            operationDetails = new Gson().fromJson(jsonInputsFileContent, OperationDetails.class);
        } catch (Exception e) {
            throw new RuntimeException("An error occur trying to read operation details json file " + operationInputsFilePath + ". Details: " + e);
        }

        addDefaultPathParamsIfNeeded(operationDetails, endpoint);
        return operationDetails;
    }

    public static RequestSpecification getDefaultSpec() {
        return given()
                .filter((requestSpec, responseSpec, ctx) -> {
                    Response response = ctx.next(requestSpec, responseSpec);

                    startTestLevel(requestSpec.getMethod() + " " + requestSpec.getURI() + " -> " + response.getStatusCode());

                    startTestLevel("Request");
                    String requestAsString = System.lineSeparator() +
                            "Request method:\t" + requestSpec.getMethod() + System.lineSeparator() +
                            "Request URI:\t" + requestSpec.getURI() + System.lineSeparator() +
                            "Request params: " + requestSpec.getRequestParams() + System.lineSeparator() +
                            "Query params:\t" + requestSpec.getQueryParams() + System.lineSeparator() +
                            "Form params:\t" + requestSpec.getFormParams() + System.lineSeparator() +
                            "Headers:\t\t" + requestSpec.getHeaders() + System.lineSeparator() +
                            "Cookies:\t" + requestSpec.getCookies() + System.lineSeparator() + System.lineSeparator() +
                            "Body:\t" + new Prettifier().getPrettifiedBodyIfPossible(requestSpec);
                    info(requestAsString);
                    endTestLevel(false);

                    startTestLevel("Response");
                    String responseAsString = System.lineSeparator() +
                            response.getStatusLine() + System.lineSeparator() +
                            response.getHeaders() + System.lineSeparator() + System.lineSeparator() +
                            new Prettifier().getPrettifiedBodyIfPossible(response, response.getBody());
                    info(responseAsString);
                    endTestLevel(false);

                    endTestLevel(false);
                    return response;
                });
    }

    private static synchronized RequestSpecification getOperatorSpec() {
        if (operatorSpecCookies == null) {

            RequestSpecification operatorSpec = getDefaultSpec();

            String body = new JSONObject()
                    .put("email", Users.getProperty(UsersProperty.apiOperatorEmail))
                    .put("password", Users.getProperty(UsersProperty.apiOperatorPassword))
                    .put("tfaToken", getTOTPCode(Users.getProperty(UsersProperty.apiOperator2FaSecret))).toString();

            Response response = operatorSpec.
                    log().all()
                    .body(body)
                    .contentType("application/json")
                    .post(MainConfig.getProperty(MainConfigProperty.baseAPIUrl) + "session/login")
                    .then()
                    .log().all()
                    .statusCode(201)
                    .extract().response();

            operatorSpecCookies = response.getCookies();
        }

        return getDefaultSpec().cookies(operatorSpecCookies);
    }

    private static synchronized RequestSpecification getSecuritizeIdSpec(String... securitizeIdAuthorizeIssuerCode) {
        if (securitizeIdSpecCookies == null) {
            RequestSpecification securitizeIdSpec = getDefaultSpec();

            String body = new JSONObject()
                    .put("email", Users.getProperty(UsersProperty.apiSidInvestorEmail))
                    .put("password", Users.getProperty(UsersProperty.apiInvestorPassword)).toString();

            Response response = securitizeIdSpec.
                    log().all()
                    .body(body)
                    .contentType("application/json")
                    .post(MainConfig.getProperty(MainConfigProperty.baseDsApiUrl) + "login")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();

            securitizeIdSpecBearer = new JSONObject(response.getBody().asString()).getString("token");
            securitizeIdSpecCookies = response.getCookies();
            securitizeIdFirstCookie = response.getHeader("set-cookie").split(";")[0];
        }
        RequestSpecification securitizeIdSpecResult = getDefaultSpec().cookies(securitizeIdSpecCookies).header("Authorization", securitizeIdSpecBearer);

        if (securitizeIdAuthorizeIssuerCode.length == 1 && securitizeIdAuthorizeIssuerCode[0].equalsIgnoreCase("code")) {
            String body2 = new JSONObject()
                    .put("issuerId", Users.getProperty(UsersProperty.apiIssuerId))
                    .put("redirectUrl", Users.getProperty(UsersProperty.apiSidRedirectUrl))
                    .put("permissions", Users.getProperty(UsersProperty.apiSidPermissions).split(",")).toString();

            Response response2 = securitizeIdSpecResult.
                    log().all()
                    .body(body2)
                    .contentType("application/json")
                    .post(MainConfig.getProperty(MainConfigProperty.baseDsApiUrl) + "authorize-issuer")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();

            securitizeIdSpecCode = new JSONObject(response2.asString()).getString("code");
            info("securitizeIdSpecCode:" + securitizeIdSpecCode);
        }

        return securitizeIdSpecResult;
    }

    private static synchronized RequestSpecification getATSSpec() {
        return getDefaultSpec().header("X-api-key", Users.getProperty(UsersProperty.ats_apiKey));
    }

    private static synchronized RequestSpecification getNewInvestorExperienceSpec() {
        if (nieSpecBearer == null) {

            RequestSpecification newInvestorExperienceAuthorizeIssuer = getDefaultSpec();

            String issuerId = Users.getProperty(UsersProperty.apiIssuerId);
            String apiIssuerURL = Users.getProperty(UsersProperty.apiIssuerURL);
            if (securitizeIdSpecBearer == null) {
                getSecuritizeIdSpec();
            }

            /* 1) Hitting authorize-issuer with bearer & cookie, getting code from the response */
            newInvestorExperienceAuthorizeIssuer.header("Authorization", securitizeIdSpecBearer);
            newInvestorExperienceAuthorizeIssuer.header("Cookie", securitizeIdFirstCookie);
            String[] permissions = {"info", "details", "verification"};

            String bodyAuthorizeIssuer = new JSONObject()
                    .put("redirectUrl", apiIssuerURL)
                    .put("permissions", permissions)
                    .put("issuerId", issuerId).toString();

            Response authorizeIssuerResponse = newInvestorExperienceAuthorizeIssuer.
                    log().all()
                    .body(bodyAuthorizeIssuer)
                    .contentType("application/json")
                    .post(MainConfig.getProperty(MainConfigProperty.baseDsApiUrl) + "authorize-issuer")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();

            /* 2) Hitting authorize-issuer with bearer & cookie, getting code from the previous response */
            String code = new JSONObject(authorizeIssuerResponse.getBody().asString()).getString("code");

            /* 3) Hitting authorization-settings and getting the 2 disclaimers */
            RequestSpecification newInvestorExperienceAuthorizationSettings = getDefaultSpec();

            Response authorizationSettingsResponse = newInvestorExperienceAuthorizationSettings.
                    log().all()
                    .contentType("application/json")
                    .get(apiIssuerURL + "authorization-settings/US")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();

            String generalDisclaimer = new JSONObject(authorizationSettingsResponse.getBody().asString()).getString("generalDisclaimer");
            String disclaimer = new JSONObject(authorizationSettingsResponse.getBody().asString()).getString("disclaimer");

            /* 4) Hitting authorize with the code and disclaimers to get the Bearer token */
            RequestSpecification newInvestorExperienceAuthorize = getDefaultSpec();

            String bodyAuthorize = new JSONObject()
                    .put("code", code)
                    .put("countryCode", "US")
                    .put("disclaimer", disclaimer)
                    .put("generalDisclaimer", generalDisclaimer)
                    .put("isAgree", true).toString();

            Response authorizationResponse = newInvestorExperienceAuthorize.
                    log().all()
                    .body(bodyAuthorize)
                    .contentType("application/json")
                    .post(apiIssuerURL + "authorize")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();

            nieSpecBearer = new JSONObject(authorizationResponse.getBody().asString()).getString("token");
            nieFirstCookie = authorizationResponse.getHeader("set-cookie").split(";")[0];
        }

        return getDefaultSpec().header("Authorization", nieSpecBearer)
                .header("Cookie", nieFirstCookie);
    }

    private static synchronized RequestSpecification getCashAccountSpec() {
        if (cashAccountSecuritizeIdSpecCookies == null) {
            RequestSpecification cashAccountSecuritizeIdSpec = getDefaultSpec();

            String body = new JSONObject()
                    .put("email", Users.getProperty(UsersProperty.ca_gw_api_mail))
                    .put("password", Users.getProperty(UsersProperty.apiInvestorPassword)).toString();

            Response response = cashAccountSecuritizeIdSpec.
                    log().all()
                    .body(body)
                    .contentType("application/json")
                    .post(MainConfig.getProperty(MainConfigProperty.baseDsApiUrl) + "login")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();

            cashAccountSecuritizeIdSpecBearer = new JSONObject(response.getBody().asString()).getString("token");
            cashAccountSecuritizeIdSpecCookies = response.getCookies();
            cashAccountSecuritizeIdFirstCookie = response.getHeader("set-cookie").split(";")[0];
        }
        return getDefaultSpec().cookies(cashAccountSecuritizeIdSpecCookies).header("Authorization", cashAccountSecuritizeIdSpecBearer);
    }


    protected int getExpectedStatusCode(OperationDetails operationDetails, Method method) {
        if (operationDetails.getExpectedResponse() == null || operationDetails.getExpectedResponse().getStatusCode() == 0) {
            return getStatusCodeByMethod(method);
        }
        return operationDetails.getExpectedResponse().getStatusCode();
    }

    private int getStatusCodeByMethod(Method method) {
        switch (method) {
            case GET:
            case PUT:
            case PATCH:
                return 200;
            case POST:
                return 201;
            case DELETE:
                return 204;
            default:
                return -1;
        }
    }

    private HashMap<String, Object> getDefaultPathParams(String endpoint) {
        String regex = "\\{(.*?)}";
        HashMap<String, Object> newPathParams = new HashMap<>();
        Matcher m = Pattern.compile(regex)
                .matcher(endpoint);
        while (m.find()) {
            String currentParam = m.group().substring(1, m.group().length() - 1);
            if (genericFilePathParams.containsKey(currentParam)) {
                newPathParams.put(currentParam, genericFilePathParams.get(currentParam));
            }
        }
        return newPathParams;
    }

    private List<String> getRequiredPathParams(String endpoint) {
        String regex = "\\{(.*?)}";
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(regex)
                .matcher(endpoint);
        while (m.find()) {
            allMatches.add(m.group().substring(1, m.group().length() - 1));
        }
        return allMatches;
    }

    protected void populateTestsToSkipList(String jsonFileName) {
        String skipTestsFilePath = ResourcesUtils.searchResourcePathByFileName("/api/content/" + jsonFileName, "skip.json");
        if (skipTestsFilePath != null) {
            String jsonFileContent;
            try {
                jsonFileContent = Files.readString(new File(skipTestsFilePath));
                jsonFileContent = updateSkipsJsonWithEnvironmentSpecificValues(jsonFileContent);
                testsToSkipList = new Gson().fromJson(jsonFileContent, new TypeToken<List<String>>() {
                }.getType());
            } catch (IOException e) {
                warning("An error occurred trying to figure out if we should skip test " + jsonFileName + ". Details: " + e, false);
            }
        }
    }

    protected void populateGenericFileMicroservicePathParams(String resourceJsonFileName) {
        if (genericFilePathParams == null || genericFilePathParams.size() == 0) {
            String genericMicroserviceFilePath = ResourcesUtils.searchResourcePathByFileName("/api/content/" + resourceJsonFileName, "generic.json");

            if (genericMicroserviceFilePath != null) {
                String jsonGenericFileContent;
                try {
                    jsonGenericFileContent = Files.readString(new File(genericMicroserviceFilePath));
                    jsonGenericFileContent = updateJsonWithEnvironmentSpecificValues(jsonGenericFileContent);
                    if (jsonGenericFileContent.contains("#")) {
                        jsonGenericFileContent = updatePlaceholders(jsonGenericFileContent);
                    }
                    genericFilePathParams = new Gson().fromJson(jsonGenericFileContent, new TypeToken<HashMap<String, Object>>() {
                    }.getType());
                } catch (Exception e) {
                    errorAndStop("An error occur trying to set generic values: " + resourceJsonFileName + ". Details: " + e, false);
                }
            }
        }
    }

    private void addDefaultPathParamsIfNeeded(OperationDetails operationDetails, String endpoint) {
        HashMap<String, Object> operationDetailsPathParams = operationDetails.getPathParams();
        List<String> requiredPathParams = getRequiredPathParams(endpoint);
        List<String> missingPathParams = requiredPathParams;

        //reflect missingPathParams after retrieving path params from operations file
        if (operationDetailsPathParams != null && missingPathParams.size() > 0) {
            missingPathParams = new ArrayList<>(CollectionUtils.subtract(requiredPathParams, operationDetailsPathParams.keySet()));
        }
        //update path params from generic file
        if (operationDetailsPathParams == null && missingPathParams.size() > 0) {
            operationDetails.setPathParams(getDefaultPathParams(endpoint));
            info("Loading path parameters from default dictionary");
            missingPathParams = new ArrayList<>(CollectionUtils.subtract(requiredPathParams, operationDetails.getPathParams().keySet()));
        }
        if (missingPathParams.size() > 0) {
            info("There are missing path parameters: " + missingPathParams + ", trying to update them with default values");
            // union default parameters with operationDetails values -> operationDetails takes precedence if exists in both
            HashMap<String, Object> tmpMap = getDefaultPathParams(endpoint);
            if (operationDetailsPathParams != null) {
                tmpMap.putAll(operationDetailsPathParams);
            }
            missingPathParams = new ArrayList<>(CollectionUtils.subtract(requiredPathParams, tmpMap.keySet()));
            operationDetails.setPathParams(tmpMap);
        }

        if (missingPathParams.size() > 0) {
            String errorMessage = "Even after loading default parameters, some are missing: " + missingPathParams;
            errorAndStop(errorMessage, false, new IllegalArgumentException(errorMessage));
        } else {
            info("All path parameters values are present");
        }
    }

    private String updatePlaceholders(String originalFileContent) {
        String resultFileContent = originalFileContent;
        countOfHashtags = resultFileContent.chars().filter(ch -> ch == '#').count();
        //General
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ISSUER_ID#")) ? getReplaceFileContent(resultFileContent, "#ISSUER_ID#", UsersProperty.apiIssuerId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#OPERATOR_ID#")) ? getReplaceFileContent(resultFileContent, "#OPERATOR_ID#", UsersProperty.apiOperatorId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#TOKEN_ID#")) ? getReplaceFileContent(resultFileContent, "#TOKEN_ID#", UsersProperty.apiTokenId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#INVESTOR_ID#")) ? getReplaceFileContent(resultFileContent, "#INVESTOR_ID#", UsersProperty.apiInvestorId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#OPPORTUNITY_ID#")) ? getReplaceFileContent(resultFileContent, "#OPPORTUNITY_ID#", UsersProperty.apiOpportunityId) : resultFileContent;
        //SID
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_SYSTEM#")) ? getReplaceFileContent(resultFileContent, "#SID_SYSTEM#", UsersProperty.apiSidSystem) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_LANGUAGE#")) ? getReplaceFileContent(resultFileContent, "#SID_LANGUAGE#", UsersProperty.apiSidLanguage) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_NAME#")) ? getReplaceFileContent(resultFileContent, "#SID_NAME#", UsersProperty.apiSidName) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_KEY#")) ? getReplaceFileContent(resultFileContent, "#SID_KEY#", UsersProperty.apiSidKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_INVESTOR_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_INVESTOR_ID#", UsersProperty.apiSidInvestorId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_LIVENESS_INVESTOR_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_LIVENESS_INVESTOR_ID#", UsersProperty.apiSidLivenessInvestorId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_USER_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_USER_ID#", UsersProperty.apiSidUserId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_INVESTOR_EMAIL#")) ? getReplaceFileContent(resultFileContent, "#SID_INVESTOR_EMAIL#", UsersProperty.apiSidInvestorEmail) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_INVESTOR_PASSWORD#")) ? getReplaceFileContent(resultFileContent, "#SID_INVESTOR_PASSWORD#", UsersProperty.apiSidInvestorPassword) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_ISSUER_SECRET#")) ? getReplaceFileContent(resultFileContent, "#SID_ISSUER_SECRET#", UsersProperty.apiSidIssuerSecret) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_DOCUMENT_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_DOCUMENT_ID#", UsersProperty.apiSidDocumentId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_LEGAL_SIGNER_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_LEGAL_SIGNER_ID#", UsersProperty.apiSidLegalSignerId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_LEGAL_SIGNER_DOCUMENT_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_LEGAL_SIGNER_DOCUMENT_ID#", UsersProperty.apiSidLegalSignerDocumentId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_CONFIDENTIAl_DOCUMENT_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_CONFIDENTIAl_DOCUMENT_ID#", UsersProperty.apiSidConfidentialDocumentId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_WALLET#")) ? getReplaceFileContent(resultFileContent, "#SID_WALLET#", UsersProperty.apiSidWallet) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_WALLETS_RPC_PROVIDER#")) ? getReplaceFileContent(resultFileContent, "#SID_WALLETS_RPC_PROVIDER#", UsersProperty.apiSidWalletsRpcProvider) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_TOKEN_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_TOKEN_ID#", UsersProperty.apiSidTokenId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_TYPE#")) ? getReplaceFileContent(resultFileContent, "#SID_TYPE#", UsersProperty.apiSidType) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_REDIRECT_URL#")) ? getReplaceFileContent(resultFileContent, "#SID_REDIRECT_URL#", UsersProperty.apiSidRedirectUrl) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_PERMISSIONS#")) ? getReplaceFileContent(resultFileContent, "#SID_PERMISSIONS#", UsersProperty.apiSidPermissions) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_TFA_REQUEST_ID#")) ? getReplaceFileContent(resultFileContent, "#SID_TFA_REQUEST_ID#", UsersProperty.apiSidTfaRequestId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_COOKIE#")) ? getReplaceFileContent(resultFileContent, "#SID_COOKIE#", BaseApiTest.securitizeIdFirstCookie) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_AUTHORIZATION#")) ? getReplaceFileContent(resultFileContent, "#SID_AUTHORIZATION#", BaseApiTest.securitizeIdSpecBearer) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_CODE#")) ? getReplaceFileContent(resultFileContent, "#SID_CODE#", BaseApiTest.securitizeIdSpecCode) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#SID_ACCESS_TOKEN#")) ? getReplaceFileContent(resultFileContent, "#SID_ACCESS_TOKEN#", SID_SecIdApiService.accessToken) : resultFileContent;
        //ATS
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_INVESTOR_ID#")) ? getReplaceFileContent(resultFileContent, "#ATS_INVESTOR_ID#", UsersProperty.apiAtsInvestorId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_FUNDS_TRANSFER_ID#")) ? getReplaceFileContent(resultFileContent, "#ATS_FUNDS_TRANSFER_ID#", UsersProperty.apiAtsFundsTransferId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_SECURITY#")) ? getReplaceFileContent(resultFileContent, "#ATS_SECURITY#", UsersProperty.apiAtsSecurity) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_DEPLOYMENT_ID#")) ? getReplaceFileContent(resultFileContent, "#ATS_DEPLOYMENT_ID#", UsersProperty.apiAtsDeploymentId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_ORDER_ID#")) ? getReplaceFileContent(resultFileContent, "#ATS_ORDER_ID#", UsersProperty.apiAtsOrderId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_INTERNAL_CASH_TRANSFER_ID#")) ? getReplaceFileContent(resultFileContent, "#ATS_INTERNAL_CASH_TRANSFER_ID#", UsersProperty.apiAtsInternalCashTransferId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_QUOTE_ID#")) ? getReplaceFileContent(resultFileContent, "#ATS_QUOTE_ID#", UsersProperty.apiAtsQuoteId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_ACCOUNT_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#ATS_ACCOUNT_API_KEY#", UsersProperty.apiAtsAccountApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_ASSET_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#ATS_ASSET_API_KEY#", UsersProperty.apiAtsAssetApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_BANK_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#ATS_BANK_API_KEY#", UsersProperty.apiAtsBankApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_CONFIG_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#ATS_CONFIG_API_KEY#", UsersProperty.apiAtsConfigApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_ORDER_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#ATS_ORDER_API_KEY#", UsersProperty.apiAtsOrderApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_REPORTING_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#ATS_REPORTING_API_KEY#", UsersProperty.apiAtsReportingApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_GW_CONNECT_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#ATS_GW_CONNECT_API_KEY#", UsersProperty.apiAtsGwConnectApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#ATS_PROVIDER_NAME#")) ? getReplaceFileContent(resultFileContent, "#ATS_PROVIDER_NAME#", UsersProperty.apiAtsProviderName) : resultFileContent;
        //CA
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_INVESTOR_ID#")) ? getReplaceFileContent(resultFileContent, "#CA_INVESTOR_ID#", UsersProperty.ca_api_investorId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_CONFIG_X_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#CA_CONFIG_X_API_KEY#", UsersProperty.ca_config_xApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_EXCHANGE_X_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#CA_EXCHANGE_X_API_KEY#", UsersProperty.ca_exchange_xApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_INVESTOR_X_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#CA_INVESTOR_X_API_KEY#", UsersProperty.ca_investor_xApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_TRANSACTION_X_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#CA_TRANSACTION_X_API_KEY#", UsersProperty.ca_transaction_xApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_TRANSFER_METHOD_X_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#CA_TRANSFER_METHOD_X_API_KEY#", UsersProperty.ca_transfer_method_xApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_BANK_ZEROHASH_X_API_KEY#")) ? getReplaceFileContent(resultFileContent, "#CA_BANK_ZEROHASH_X_API_KEY#", UsersProperty.ca_bank_zerohash_xApiKey) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_PROVIDER_ID#")) ? getReplaceFileContent(resultFileContent, "#CA_PROVIDER_ID#", UsersProperty.ca_api_providerId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#CA_CASH_ACCOUNT_ID#")) ? getReplaceFileContent(resultFileContent, "#CA_CASH_ACCOUNT_ID#", UsersProperty.ca_api_cashAccountId) : resultFileContent;
        //TA
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#TA_W9_TAXFORM_ID#")) ? getReplaceFileContent(resultFileContent, "#TA_W9_TAXFORM_ID#", UsersProperty.apiTaW9TaxformId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#TA_ISSUER_ID#")) ? getReplaceFileContent(resultFileContent, "#TA_ISSUER_ID#", UsersProperty.apiTaIssuerId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#TA_TOKEN_ID#")) ? getReplaceFileContent(resultFileContent, "#TA_TOKEN_ID#", UsersProperty.apiTaTokenId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#TA_DISTRIBUTION_ID#")) ? getReplaceFileContent(resultFileContent, "#TA_DISTRIBUTION_ID#", UsersProperty.apiTaDistributionId) : resultFileContent;
        resultFileContent = (countOfHashtags > 0 && resultFileContent.contains("#TA_INVESTOR_ID#")) ? getReplaceFileContent(resultFileContent, "#TA_INVESTOR_ID#", UsersProperty.apiTaInvestorId) : resultFileContent;

        return resultFileContent;
    }

    @NotNull
    private String getReplaceFileContent(String fileContent, String placeholder, UsersProperty property) {
        //handle a property (apiSidPermissions) which is an array by removing the " next to the Square brackets
        if (property == UsersProperty.apiSidPermissions) {
            countOfHashtags -= 2;
            String replacedFileContent = fileContent.replace(placeholder, Users.getProperty(property));
            return replacedFileContent.replace("\"[", "[").replace("]\"", "]");
        }

        //handle property contains " by adding \ (backslash escape character)
        String propertyAsString = Users.getProperty(property);
        String replacedPropertyAsString;
        if (propertyAsString.contains("\"")) {
            replacedPropertyAsString = Users.getProperty(property).replace("\"", "\\\"");
        } else {
            replacedPropertyAsString = Users.getProperty(property);
        }

        String replacedFileContent = fileContent.replace(placeholder, replacedPropertyAsString);
        countOfHashtags -= 2;
        return replacedFileContent;
    }

    @NotNull
    private String getReplaceFileContent(String fileContent, String placeholder, String value) {
        String replacedFileContent = fileContent.replace(placeholder, value);
        countOfHashtags -= 2;
        return replacedFileContent;
    }


    protected static synchronized RequestSpecification getSpec(LoginAs loginAs, String... securitizeIdAuthorizeIssuerCode) {
        if (loginAs == LoginAs.NONE) {
            return getDefaultSpec();
        } else if (loginAs == LoginAs.SECURITIZE_ID) {
            return getSecuritizeIdSpec(securitizeIdAuthorizeIssuerCode);
        } else if (loginAs == LoginAs.ATS) {
            return getATSSpec();
        } else if (loginAs == LoginAs.OPERATOR) {
            return getOperatorSpec();
        } else if (loginAs == LoginAs.NEW_INVESTOR_EXPERIENCE) {
            return getNewInvestorExperienceSpec();
        } else if (loginAs == LoginAs.CASH_ACCOUNT) {
            return getCashAccountSpec();
        } else {
            throw new RuntimeException("Invalid loginAs value: " + loginAs);
        }
    }

    private void updatePlaceHoldersFromParameters(HashMap<String, Object> mapOfValues, Map<String, Object> parameters) {
        if (parameters != null) {
            for (String currentParameterKey : parameters.keySet()) {
                for (String currentKey : mapOfValues.keySet()) {
                    String currentValueAsString = mapOfValues.get(currentKey).toString();
                    if (currentValueAsString.contains("#" + currentParameterKey + "#")) {
                        mapOfValues.put(currentKey, currentValueAsString.replace("#" + currentParameterKey + "#", parameters.get(currentParameterKey).toString()));
                    }
                }
            }
        }

        // validate we have placed a value for each placeholder
        List<String> unfilledPlaceHolders = findUnfilledPlaceholders(mapOfValues);
        if (unfilledPlaceHolders.size() > 0) {
            String skipReason = "Missing values for the following place holders, can't resume: " + StringUtils.join(unfilledPlaceHolders, ", ");
            skip(skipReason);
            throw new SkipException(skipReason);
        }
    }

    private List<String> findUnfilledPlaceholders(Map<String, Object> mapOfValues) {
        return mapOfValues.keySet() // collect just the keys
                .stream()

                // for each key - if its value is in the format of #SOME-VALUE#
                .filter(v -> mapOfValues.get(v).toString().matches("#.*#"))

                // add this key to the list of unfilled placeholders
                .collect(Collectors.toList());
    }

    private String setCurrentInternalUrl(String endpoint, String actualEnvironment) {
        //the first ':' in the endpoint is after http/s and the second ':' is before the port number
        //for example: http://i18n-service:5018.{internalUrlToRemoteRunCicdApi}/...
        int firstColon = endpoint.indexOf(":");
        int secondColon = endpoint.indexOf(":", firstColon + 1);

        if (actualEnvironment.equals("production")) {
            endpoint = endpoint.replace("{internalUrlToRemoteRunCicdApi}", MainConfig.getProperty(MainConfigProperty.internalUrlToRemoteRunCicdApi))
                    .replace("{environment}.", "")
                    .replace(endpoint.substring(secondColon, secondColon + 5), "")
                    .replace("http", "https");
        } else {
            endpoint = endpoint.replace("{internalUrlToRemoteRunCicdApi}", MainConfig.getProperty(MainConfigProperty.internalUrlToRemoteRunCicdApi))
                    .replace("{environment}", actualEnvironment)
                    .replace(endpoint.substring(secondColon, secondColon + 5), "")
                    .replace("http", "https");
        }
        return endpoint;
    }

    private String updateSkipsJsonWithEnvironmentSpecificValues(String originalJsonContent) {
        // ignore comments in JSON file
        originalJsonContent = originalJsonContent.replaceAll("// .*", "");
        originalJsonContent = originalJsonContent.replaceAll("//\".*", "");

        // parse original file
        JSONArray jsonArray = new JSONArray(originalJsonContent);

        // copy test to skip from current environment section (if exists) to the generic list of tests to ignore
        String environmentAsLowerCase = MainConfig.getProperty(MainConfigProperty.environment).toLowerCase();
        String environmentAsUpperCase = MainConfig.getProperty(MainConfigProperty.environment).toUpperCase();
        JSONArray environmentSpecificTestsToSkip = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            Object current = jsonArray.get(i);
            // all tests to skip are String elements. Only environment specific values are stored in a JSONObject
            if (current instanceof JSONObject) {
                JSONObject currentAsJsonObject = (JSONObject) current;
                // support cases where environment is all lower and upper case
                if (currentAsJsonObject.has(environmentAsLowerCase)) {
                    environmentSpecificTestsToSkip = currentAsJsonObject.getJSONArray(environmentAsLowerCase);
                } else if (currentAsJsonObject.has(environmentAsUpperCase)) {
                    environmentSpecificTestsToSkip = currentAsJsonObject.getJSONArray(environmentAsUpperCase);
                }

                // if found specific tests to skip for current environment - add them to the generic skip list
                if (environmentSpecificTestsToSkip != null) {
                    for (int j = 0; j < environmentSpecificTestsToSkip.length(); j++) {
                        jsonArray.put(environmentSpecificTestsToSkip.getString(j));
                    }
                }

                // remove environment specific values from the array to leave only list of tests to skip
                jsonArray.remove(i);
                break;
            }
        }

        return jsonArray.toString(4);
    }

    private String updateJsonWithEnvironmentSpecificValues(String originalJsonContent) {
        // ignore JsonArrays
        if (originalJsonContent.trim().startsWith("[")) {
            return originalJsonContent;
        }

        // ignore comments in JSON file
        originalJsonContent = originalJsonContent.replaceAll("// .*", "");
        originalJsonContent = originalJsonContent.replaceAll("//\".*", "");

        // parse original file
        JSONObject rawJson = new JSONObject(originalJsonContent);

        // copy values from current environment section (if exists) to top level to be used
        String environmentAsLowerCase = MainConfig.getProperty(MainConfigProperty.environment).toLowerCase();
        String environmentAsUpperCase = MainConfig.getProperty(MainConfigProperty.environment).toUpperCase();
        if (rawJson.has(environmentAsLowerCase)) {
            recursiveUpdate(rawJson, rawJson.getJSONObject(environmentAsLowerCase));
        } else if (rawJson.has(environmentAsUpperCase)) {
            recursiveUpdate(rawJson, rawJson.getJSONObject(environmentAsUpperCase));
        }

        // remove sections mentioning environments
        for (String currentEnv : ENVIRONMENTS_NAMES) {
            rawJson.remove(currentEnv.toLowerCase());
            rawJson.remove(currentEnv.toUpperCase());
        }

        return rawJson.toString(4);
    }

    private static void recursiveUpdate(JSONObject genericSection, JSONObject envSection) {
        for (String currentKey : envSection.keySet()) {
            if (envSection.get(currentKey) instanceof JSONObject) {
                if (!genericSection.has(currentKey)) {
                    genericSection.put(currentKey, envSection.get(currentKey));
                } else {
                    recursiveUpdate(genericSection.getJSONObject(currentKey), envSection.getJSONObject(currentKey));
                }
            } else if (envSection.get(currentKey) instanceof JSONArray) {
                throw new NotImplementedException("JSONArray not yet supported!");
            } else {
                genericSection.put(currentKey, envSection.get(currentKey));
            }
        }
    }
}