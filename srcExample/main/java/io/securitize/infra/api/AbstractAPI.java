package io.securitize.infra.api;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.config.*;
import io.securitize.infra.utils.Authentication;
import org.json.JSONObject;
import org.testng.Assert;

import java.net.SocketException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.info;

public abstract class AbstractAPI {
    private static Map<String, String> securitizeIdSpecCookies = null;
    private static String securitizeIdSpecBearer = null;

    static {
        RestAssured.baseURI = MainConfig.getProperty(MainConfigProperty.baseAPIUrl);
        RestAssured.port = 443;
    }

    protected static synchronized RequestSpecification getSecuritizeIdSpec() {
        if (securitizeIdSpecCookies == null) {

            RequestSpecification securitizeIdSpec = given();

            String body = new JSONObject()
                    .put("email", Users.getProperty(UsersProperty.apiInvestorEmail))
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
        }

        return given().cookies(securitizeIdSpecCookies).header("Authorization", securitizeIdSpecBearer);
    }

    protected static synchronized Response getSecuritizeIdSpec(String username, String password) {

        RequestSpecification securitizeIdSpec = given();

        String body = new JSONObject()
                .put("email", username)
                .put("password", password).toString();

        return securitizeIdSpec.
                log().all()
                .body(body)
                .contentType("application/json")
                .post(MainConfig.getProperty(MainConfigProperty.baseDsApiUrl) + "login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @SuppressWarnings("SameParameterValue")
    String postLoginAPI(String url, String body, int expectedResponseCode, boolean logResponse) {

        String basicAuthUsername = Users.getProperty(UsersProperty.httpAuthenticationUsername);
        String basicAuthPassword = Users.getProperty(UsersProperty.httpAuthenticationPassword);

        Response response =
                given().auth()
                        .basic(basicAuthUsername, basicAuthPassword)
                        .headers(
                                "accept", "application/json, text/plain, */*",
                                "content-type", "application/json;charset=UTF-8")
                        .with()
                        .body(body)
                        .log().all()
                        .post(url)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);

        // remove prefix of Bearer%20 and return the actual bearer value
        return response.cookie("accessToken").split("Bearer%20")[1];
    }

    Response postLoginAPIb(String url, String body, int expectedResponseCode, boolean logResponse) {

        String basicAuthUsername = Users.getProperty(UsersProperty.httpAuthenticationUsername);
        String basicAuthPassword = Users.getProperty(UsersProperty.httpAuthenticationPassword);

        Response response =
                given().auth()
                        .basic(basicAuthUsername, basicAuthPassword)
                        .headers(
                                "accept", "application/json, text/plain, */*",
                                "content-type", "application/json;charset=UTF-8")
                        .with()
                        .body(body)
                        .log().all()
                        .post(url)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);

        // remove prefix of Bearer%20 and return the actual bearer value
        return response;
    }

    @SuppressWarnings("unused")
    String postAPI(String url, String body) {
        return postAPI(url, body, null,200, false);
    }

    @SuppressWarnings("SameParameterValue")
    String postAPI(String url, String body, int expectedResponseCode, boolean logResponse) {
        return postAPI(url, body, null,expectedResponseCode, logResponse);
    }

    void postMultipartAPI(String investorId, String url, HashMap<String, Object> multipartContent, MultiPartSpecification fileMultipart, int expectedResponseCode, boolean logResponse) {
        String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);

        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Accept", ContentType.JSON);
        headers.put("Content-Type", "multipart/form-data");
        headers.put("Origin", "https://cp." + currentEnvironment + ".securitize.io");
        headers.put("Referer", "https://cp." + currentEnvironment + ".securitize.io/" + investorId);

        RequestSpecification requestSpecification = given().headers(headers);

        for (String currentKey : multipartContent.keySet()) {
            requestSpecification = requestSpecification.multiPart(currentKey, multipartContent.get(currentKey));
        }

        if (fileMultipart != null) {
            requestSpecification = requestSpecification.multiPart(fileMultipart);
        }

        // to handle socket exceptions which randomly and seldom occur
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(SocketException.class)
                .withDelay(Duration.ofSeconds(10))
                .withMaxRetries(3)
                .build();

        RequestSpecification finalRequestSpecification = requestSpecification;
        Response response = Failsafe.with(retryPolicy).get(() -> finalRequestSpecification
                .with()
                .log().all()
                .post(url)
                .then()
                .log().all()
                .extract()
                .response());

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);

    }

    String postAPI(String url, String body, HashMap<String, Object> headers, int expectedResponseCode, boolean logResponse) {
        return postAPI(null, url, body, headers, expectedResponseCode, logResponse);
    }

    String postAPI(RequestSpecification spec, String url, String body, HashMap<String, Object> headers, int expectedResponseCode, boolean logResponse) {
        return postAPI(spec, url, body, headers, expectedResponseCode, false, true, logResponse);
    }

    String postAPI(RequestSpecification spec, String url, String body, HashMap<String, Object> headers, int expectedResponseCode, boolean returnBearerTokenFromCookie, boolean addBearerInCookie, boolean logResponse) {
        return postAPI(spec, url, body, headers, expectedResponseCode, returnBearerTokenFromCookie, addBearerInCookie, true, logResponse);
    }
        @SuppressWarnings("SameParameterValue")
    String postAPI(RequestSpecification spec, String url, String body, HashMap<String, Object> headers, int expectedResponseCode, boolean returnBearerTokenFromCookie, boolean addBearerInCookie, boolean logRequest, boolean logResponse) {

        // set default headers if none provided
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + Authentication.getBearerToken());
            headers.put("Content-Type", ContentType.JSON);
            headers.put("Accept", ContentType.JSON);
        }

        RequestSpecification givenObject;
        if (spec != null) {
            givenObject = given().spec(spec);
        } else {
            givenObject = given();
        }

        RequestSpecification requestSpecification = givenObject.headers(headers);

        if (addBearerInCookie) {
            Object cookieValue;
            if (headers.containsKey("Authorization")) {
                cookieValue = headers.get("Authorization");
            } else {
                cookieValue = Authentication.getBearerToken();
            }

            requestSpecification = requestSpecification.cookie("accessToken", cookieValue);
        }

            requestSpecification
                        .with()
                        .body(body);

        if (logRequest) {
            requestSpecification = requestSpecification.log().all();
        }

            ValidatableResponse validatableResponse = requestSpecification
                        .post(url)
                        .then();

        if (logResponse) {
            validatableResponse.log().all();
        }

        Response response = validatableResponse
                        .extract()
                        .response();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s. Body of error response: %s", response.statusCode(), expectedResponseCode, responseAsString);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);


        if ((spec != null) && (response.cookies().size() > 0)) {
            spec.cookies(response.getCookies());
        }

        if (returnBearerTokenFromCookie) {
            return response.cookie("accessToken").split("Bearer%20")[1];
        }

        return responseAsString;
    }


    @SuppressWarnings("SameParameterValue")
    void patchAPI(String url, String body, int expectedResponseCode, boolean logResponse) {

        Response response =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + Authentication.getBearerToken(),
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON)
                        .cookie("accessToken", "Bearer " + Authentication.getBearerToken())
                        .with()
                        .body(body)
                        .log().all()
                        .patch(url)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);
    }

    public String patchAPI(RequestSpecification requestSpecification, String url, Map body){
        return patchAPI(requestSpecification, url, body, 200, true);
    }

    public String postAPI(RequestSpecification requestSpecification, String url, Map body){
        return postAPI(requestSpecification, url, body, 200, true);
    }

    public String postAPI(RequestSpecification requestSpecification, String url, Map body, int statusCode){
        return postAPI(requestSpecification, url, body, statusCode, true);
    }

    @SuppressWarnings("SameParameterValue")
    public String patchAPI(RequestSpecification requestSpecification, String url, Map body, int expectedResponseCode, boolean logResponse) {

        Response response =
                given().spec(requestSpecification)
                        .contentType("application/json")
                        .with()
                        .body(body)
                        .log().all()
                        .patch(url)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);
        return response.body().asString();
    }

    public String postAPI(RequestSpecification requestSpecification, String url, Map body, int expectedResponseCode, boolean logResponse) {

        Response response = given().log().all()
                .spec(requestSpecification)
                .contentType("application/json")
                .body(body)
                .when().post(url)
                .then().log().all()
                .extract().response();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);
        return response.body().asString();
    }

 void putAPI(String url, String body, int expectedResponseCode, boolean logResponse) {

        Response response =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + Authentication.getBearerToken(),
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON)
                        .cookie("accessToken", "Bearer " + Authentication.getBearerToken())
                        .with()
                        .body(body)
                        .log().all()
                        .put(url)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);
    }

    @SuppressWarnings("unused")
    protected String getAPI(String url) {
        return getAPI(url, 200, false);
    }

    @SuppressWarnings("SameParameterValue")
    String getAPI(String url, int expectedResponseCode, boolean logResponse) {
        return getAPI(url, null, null, expectedResponseCode, logResponse);
    }

    String getAPI(String url, Map<String, String> queryParams,  int expectedResponseCode, boolean logResponse) {
        return getAPI(url, queryParams, null, expectedResponseCode, logResponse);
    }

    String getAPI(String url, Map<String, String> queryParams, HashMap<String, Object> headers, int expectedResponseCode, boolean logResponse) {
        return getAPI(null, url, queryParams, headers, expectedResponseCode, logResponse);
    }

    String getAPI(RequestSpecification spec, String url, Map<String, String> queryParams, HashMap<String, Object> headers, int expectedResponseCode,
                  boolean logResponse) {
        return getAPI(spec, url, queryParams, headers, expectedResponseCode, true, logResponse);
    }

    String getAPI(RequestSpecification requestSpecification, String endpoint){
        return getAPI(requestSpecification, endpoint, new HashMap<>(), new HashMap<>(), 200, true);
    }

    String getAPI(RequestSpecification requestSpecification, String endpoint, Map<String, String> queryParams){
        return getAPI(requestSpecification, endpoint, queryParams, new HashMap<>(), 200, true);
    }
    public String getAPI(RequestSpecification spec, String url, Map<String, String> queryParams, HashMap<String, Object> headers, int expectedResponseCode,
                  boolean addAccessToken, boolean logResponse) {

        RequestSpecification givenObject;
        if (spec != null) {
            givenObject = given().spec(spec);
        } else {
            givenObject = given();
        }

        // set default headers if none provided
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + Authentication.getBearerToken());
            headers.put("Content-Type", ContentType.JSON);
            headers.put("Accept", ContentType.JSON);
        }

        info("Perform GET to url " + url + " expecting response code of: " + expectedResponseCode);

        RequestSpecification requestSpecification = givenObject
                .headers(headers);

        if (addAccessToken) {
            givenObject = givenObject.cookie("accessToken", "Bearer " + Authentication.getBearerToken());
        }

        if (queryParams != null) {
            requestSpecification = requestSpecification.queryParams(queryParams);
        }

        ExtractableResponse<Response> response = requestSpecification.when()
                .log().all()
                .get(url)
                .then()
                .extract();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);

        return responseAsString;
    }

    Response postLoginAPIReturnResponse(String url, String body, int expectedResponseCode, boolean logResponse) {

        String basicAuthUsername = Users.getProperty(UsersProperty.httpAuthenticationUsername);
        String basicAuthPassword = Users.getProperty(UsersProperty.httpAuthenticationPassword);

        Response response =
                given().auth()
                        .basic(basicAuthUsername, basicAuthPassword)
                        .headers(
                                "accept", "application/json, text/plain, */*",
                                "content-type", "application/json;charset=UTF-8")
                        .with()
                        .body(body)
                        .log().all()
                        .post(url)
                        .then()
                        .log().all()
                        .extract()
                        .response();

        int actualResponseCode = response.statusCode();
        info("Response code: " + actualResponseCode);

        String responseAsString = response.body().asString();

        if (logResponse) {
            info("Response: " + responseAsString);
        }

        // validate response code after logging response
        String assertFailedMessage = String.format("Error: actual response code of %s doesn't match expected response code of %s", response.statusCode(), expectedResponseCode);
        Assert.assertEquals(response.statusCode(), expectedResponseCode, assertFailedMessage);
        return response;
    }

}
