package io.securitize.infra.api;

import io.restassured.builder.*;
import io.restassured.http.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.*;
import io.restassured.specification.*;
import io.securitize.infra.config.*;
import org.json.*;

import static io.restassured.RestAssured.given;

public class TransferAgentAPI {

    public static String associateSidInvestorToIssuerOnboardingDB(String issuerId, String securitizeIdProfileId) {

        LoginAPI loginAPI = new LoginAPI();
        Response loginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String headerSetCookie[] = loginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];
        System.out.println("bearerToken -- > " + bearerToken);
        String env = MainConfig.getProperty(MainConfigProperty.environment);
        RequestSpecification syncInvestorToIssuerRequest = new RequestSpecBuilder()
                .setBaseUri("https://cp."+env+".securitize.io")
                .addHeader("accessToken", bearerToken)
                .addCookies(loginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String body = new JSONObject()
                .put("securitizeIdProfileId", securitizeIdProfileId).toString();

        String syncSidInvestorToIssuerResponse = null;
        syncSidInvestorToIssuerResponse = given().log().all()
                    .spec(syncInvestorToIssuerRequest)
                    .pathParam("issuerId", issuerId)
                    .with().body(body)
                    .when().post("https://cp."+env+".securitize.io/api/v2/issuers/{issuerId}/register-sec-id-investors")
                    .then().assertThat().statusCode(200).extract().response().asString();

        return syncSidInvestorToIssuerResponse;
    }

    public String getIssuedSecurities(String issuerId, String tokenId) {

        LoginAPI loginAPI = new LoginAPI();
        Response loginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String headerSetCookie[] = loginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];
        System.out.println("bearerToken -- > " + bearerToken);

        String env = MainConfig.getProperty(MainConfigProperty.environment);

        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri("https://cp."+env+".securitize.io")
                .addHeader("accessToken", bearerToken)
                .addCookies(loginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String response2 = null;
        response2 = given().log().all()
                .spec(request)
                .when().get("https://cp."+env+".securitize.io"+"/isr/api/v1/issuers/" + issuerId + "/tokens/" + tokenId + "/control-book-records/info")
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response2);

        JsonPath jsonPath = new JsonPath(response2);
        return jsonPath.getString("issuedSecurities");
    }

}
