package io.securitize.tests.blockchain;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.blockchain.testData.AUT429_POJO;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT429_ST_SecDid extends AbstractTest {

    @Test(description = "AUT429_ST_SecDid - Generate Transaction")
    public void AUT429_ST_SecDidTest() {
        String env = MainConfig.getProperty(MainConfigProperty.environment);
        String baseUri = "https://defi-transactions-service.internal." + env + ".securitize.io";

        AUT429_POJO aut429_pojo = new AUT429_POJO();
        RequestSpecification curlPostSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addHeader("accept", "application/json")
                .setContentType(ContentType.JSON)
                .build();

        String body = new JSONObject()
                .put("securitizeId", aut429_pojo.getSecuritizeId()).toString();

        String response = BaseApiTest.getDefaultSpec()
                .spec(curlPostSpec)
                .log().all()
                .pathParam("partnersId", aut429_pojo.getPartnersId())
                .pathParam("wallet", aut429_pojo.getWallet())
                .body(body)
                .when()
                .post("api/v1/partners/{partnersId}/wallets/{wallet}/whitelist")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response().asString();

        JSONObject responseObject = new JSONObject(response);
        startTestLevel("Results");
        info("Response object:" + responseObject.toString(4));
        endTestLevel(false);
    }
}