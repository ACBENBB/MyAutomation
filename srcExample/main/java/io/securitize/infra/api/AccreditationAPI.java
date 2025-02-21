package io.securitize.infra.api;

import io.restassured.builder.*;
import io.restassured.http.*;
import io.restassured.response.*;
import io.restassured.specification.*;
import io.securitize.infra.config.*;
import org.json.*;

import static io.restassured.RestAssured.given;

public class AccreditationAPI extends AbstractAPI {

    public String setMarketsAccreditationStatus(Response cpLoginResponse, String securitizeId, String accreditationStatus) {
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];

        String env = MainConfig.getProperty(MainConfigProperty.environment);
        RequestSpecification marketsAccreditationRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://cp."+env+".securitize.io")
                .addHeader("authorization", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String body = new JSONObject()
                .put("comment", "set by api")
                .put("status", accreditationStatus).toString();

        return given().log().all()
                .spec(marketsAccreditationRequestSpec)
                .pathParam("securitizeId", securitizeId).with().body(body)
                .when().patch("/api/v2/broker-dealer/investors/{securitizeId}/accreditation")
                .then().extract().response().asString();
    }

}
