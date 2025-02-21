package io.securitize.tests.apiTests.cicd.ST.ST_Defi_AbstractionLayer;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Defi_AbstractionLayer extends BaseApiTest {

    @Test()
    public void HealthController_checkTest753() {
        testRequest(Method.GET, "https://defi-abstraction-layer.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ST/defi-abstraction-layer", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void Web3AtsTransactionsController_getMatchHandlerContractTest132() {
        testRequest(Method.GET, "https://defi-abstraction-layer.internal.{environment}.securitize.io/api/v1/deployments/{deploymentId}/match-handler", "Web3AtsTransactionsController_getMatchHandlerContract", LoginAs.NONE, "ST/defi-abstraction-layer", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/MatchHandlerResponseDto\"}}}}");
    }


    @Test()
    public void Web3AtsTransactionsController_getEventContractTest317() {
        testRequest(Method.GET, "https://defi-abstraction-layer.internal.{environment}.securitize.io/api/v1/deployments/{deploymentId}/events/contract", "Web3AtsTransactionsController_getEventContract", LoginAs.NONE, "ST/defi-abstraction-layer", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/MatchEventResponseDto\"}}}}");
    }




}

