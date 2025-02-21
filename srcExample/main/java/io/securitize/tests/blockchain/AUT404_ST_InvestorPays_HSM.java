package io.securitize.tests.blockchain;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.blockchain.testData.AUT404_POJO;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT404_ST_InvestorPays_HSM extends AbstractTest {

    @Test(description = "AUT404_ST_InvestorPays_HSM - Generate Transaction")
    public void AUT404_ST_InvestorPays_HSM() {

        String env = MainConfig.getProperty(MainConfigProperty.environment);
        String baseUri = "https://abstraction-layer.internal." + env + ".securitize.io";

        RequestSpecification curlPostSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addHeader("accept", "application/json")
                .setContentType(ContentType.JSON)
                .build();

        AUT404_POJO aut404_pojo = new AUT404_POJO();

        String body = new JSONObject()
                .put("identity", aut404_pojo.getIdentity())
                .put("holderId", aut404_pojo.getHolderId())
                .put("wallet", aut404_pojo.getWallet())
                .put("investorPays", aut404_pojo.getInvestorPays()).toString();

        String response = BaseApiTest.getDefaultSpec()
                .spec(curlPostSpec)
                .log().all()
                .body(body)
                .pathParam("deploymentId", aut404_pojo.getDeploymentId())
                .when()
                .post("/v1/deployments/{deploymentId}/wallets")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response().asString();

        startTestLevel("Results");
        JSONObject responseObject = new JSONObject(response);
        String transactionId = responseObject.get("transactionId").toString();
        String transactionData = responseObject.get("transactionData").toString();
        JSONObject additionalData = responseObject.getJSONObject("additionalData");
        String networkId = additionalData.get("networkId").toString();
        String networkType = additionalData.get("networkType").toString();
        String chainId = additionalData.get("chainId").toString();
        String txType = additionalData.get("txType").toString();

        info("transactionId " + transactionId);
        info("transactionData " + transactionData);
        info("networkId " + networkId);
        info("networkType " + networkType);
        info("chainId " + chainId);
        info("txType " + txType);
        endTestLevel(false);
    }
}