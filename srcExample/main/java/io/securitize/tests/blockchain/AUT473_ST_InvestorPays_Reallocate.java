package io.securitize.tests.blockchain;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.blockchain.testData.AUT473_POJO;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT473_ST_InvestorPays_Reallocate extends AbstractTest {

    @Test(description = "AUT473_ST_InvestorPays_Reallocate")
    public void AUT473_ST_InvestorPays_Reallocate_test() {
        String env = MainConfig.getProperty(MainConfigProperty.environment);
        String baseUri = "https://abstraction-layer.internal." + env + ".securitize.io";
        AUT473_POJO aut415_pojo = new AUT473_POJO();

        RequestSpecification curlPostSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addHeader("accept", "application/json")
                .setContentType(ContentType.JSON)
                .build();

        JSONObject holder = new JSONObject()
                .put("id", aut415_pojo.getId())
                .put("country", aut415_pojo.getCountry())
                .put("wallet", aut415_pojo.getWallet());
        String body = new JSONObject()
                .put("identity", aut415_pojo.getIdentity())
                .put("holder", holder)
                .put("value", aut415_pojo.getValue())
                .put("investorPays", aut415_pojo.getInvestorPays()).toString();
        

        String response = BaseApiTest.getDefaultSpec()
                .spec(curlPostSpec)
                .log().all()
                .pathParam("deploymentId", aut415_pojo.getDeploymentId())
                .body(body)
                .when()
                .post("/v1/deployments/{deploymentId}/omnibus/tokens/reallocation")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().asString();

        startTestLevel("Results");
        JSONObject responseObject = new JSONObject(response);
        info("Response object:" + responseObject.toString(4));
        endTestLevel(false);
    }
}