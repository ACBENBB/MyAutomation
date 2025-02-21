package io.securitize.infra.api.primetrust;


import io.restassured.response.Response;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.reporting.MultiReporter;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PrimeTrustAPI {

    public static void unlinkAccountByEmail (String email) {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String securitizeID = investorsAPI.getUniqueSecuritizeIdByEmail(email);
        String primeTrustID = getAccountOwnerId(securitizeID);
        if (primeTrustID != null) {
            unlinkAccount(primeTrustID);
        }
    }
    private static String getAccountOwnerId(String securitizeID) {
        String url = MainConfig.getProperty(MainConfigProperty.secAcctOwnerUrl);
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("x-api-key", Users.getProperty(UsersProperty.ca_prime_trust_x_api_key));
        Response response = given()
                .log().all()
                .headers(headers)
                .pathParam("securitizeID", securitizeID)
                .get(url)
                .then().log().all()
                .extract().response();

        switch (response.statusCode()) {
            case 200:
                String id = response.path("id");
                MultiReporter.info("Found account " + securitizeID + " with id " + id);
                return id;
            case 404:
                MultiReporter.info("Account " + securitizeID + " is not linked");
                return null;
            default:
                Assert.fail("Invalid status code");
        }

        return null;
    }

    public static String createJWT() {
        String url = Users.getProperty(UsersProperty.ca_prime_trust_create_jwt_api_url);

        String body = new JSONObject().put("expires_at", "2023-12-13").toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", Users.getProperty(UsersProperty.ca_prime_trust_jwt_auth_token));
        headers.put("Content-Type", "application/json");

        Response response = given()
                .log().all()
                .headers(headers)
                .body(body)
                .post(url).then()
                .log().all().assertThat()
                .statusCode(201)
                .extract().response();

        return response.path("token");
    }

    public static void unlinkAccount(String investorId) {
        String url = "https://sandbox.primetrust.com/v2/accounts/{investorId}/sandbox";

        JSONObject attributes = new JSONObject()
                .put("name", "unlinked");
        JSONObject data = new JSONObject()
                .put("type", "accounts")
                .put("attributes", attributes);
        String body = new JSONObject()
                .put("data", data).toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + createJWT());
        headers.put("Content-Type", "application/json");

        given()
                .log().all()
                .headers(headers)
                .body(body)
                .pathParam("investorId", investorId)
                .patch(url)
                .then()
                .log().all().assertThat()
                .statusCode(200)
                .extract().response();

        MultiReporter.info("Successfully unlinked account: " + investorId);
    }

}
