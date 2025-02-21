package io.securitize.tests.blockchain;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.blockchain.testData.AUT474_POJO;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT474_ST_Web3Swap_HolderTokenTransaction extends AbstractTest {

    @Test(description = "AUT474_ST_Swap")
    public void AUT474_ST_Web3Swap_HolderTokenTransaction_test() {

        String env = MainConfig.getProperty(MainConfigProperty.environment);
        String baseUrl = "https://abstraction-layer.internal." + env + ".securitize.io";
        AUT474_POJO aut416_pojo = new AUT474_POJO();
        RequestSpecification curlPostSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("accept", "application/json")
                .setContentType(ContentType.JSON)
                .build();

        JSONObject holder = new JSONObject()
                .put("id", aut416_pojo.getId())
                .put("country", aut416_pojo.getCountry())
                .put("wallets", Collections.singletonList(aut416_pojo.getWallet()));
        String body = new JSONObject()
                .put("identity", aut416_pojo.getIdentity())
                .put("holder", holder)
                .put("purchaseAmount", aut416_pojo.getPurchaseAmount())
                .put("documentHash", aut416_pojo.getDocumentHash())
                .put("value", aut416_pojo.getValue())
                .put("investorPays", aut416_pojo.getInvestorPays()).toString();

        String response = BaseApiTest.getDefaultSpec()
                .spec(curlPostSpec)
                .log().all()
                .pathParam("deploymentId", aut416_pojo.getDeploymentId())
                .body(body)
                .when()
                .post("/v1/deployments/{deploymentId}/holders/swap")
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