package io.securitize.infra.api;

import io.restassured.builder.*;
import io.restassured.http.*;
import io.restassured.response.*;
import io.restassured.specification.*;
import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.pojo.*;
import org.joda.time.*;
import org.json.*;

import java.time.LocalTime;
import java.time.format.*;

import static io.restassured.RestAssured.given;

public class SnapshotAPI extends AbstractAPI {

    public String createSnapshot(Response cpLoginResponse) {
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];

        String env = MainConfig.getProperty(MainConfigProperty.environment);
        RequestSpecification createSnapshotRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://cp."+env+".securitize.io")
                .addHeader("authorization", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String body = new JSONObject()
                .put("date", LocalDate.now() + "T21:54:32-03:00") // this shouldn't be hardcoded
                .put("name", "AUT365 Snapshot")
                .toString();

        String issuerId = Users.getProperty(UsersProperty.ta_distribution_issuerId_aut365);
        String tokenId = Users.getProperty(UsersProperty.ta_distribution_tokenId_aut365);

        return given().log().all()
                    .spec(createSnapshotRequestSpec)
                    .pathParam("issuerId", issuerId)
                    .pathParam("tokenId", tokenId)
                    .body(body)
                    .when().post("api/v2/issuers/{issuerId}/tokens/{tokenId}/snapshots")
                    .then().extract().response().asString();
    }

    public String createSnapshot(Response cpLoginResponse, String issuer, String token, String name) {
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];

        String env = MainConfig.getProperty(MainConfigProperty.environment);
        RequestSpecification createSnapshotRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://cp."+env+".securitize.io")
                .addHeader("authorization", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String body = new JSONObject()
                .put("date", LocalDate.now() + "T21:54:32-03:00") // this shouldn't be hardcoded
                .put("name", name+" Snapshot")
                .toString();

        return given().log().all()
                .spec(createSnapshotRequestSpec)
                .pathParam("issuerId", issuer)
                .pathParam("tokenId", token)
                .body(body)
                .when().post("api/v2/issuers/{issuerId}/tokens/{tokenId}/snapshots")
                .then().extract().response().asString();
    }

}