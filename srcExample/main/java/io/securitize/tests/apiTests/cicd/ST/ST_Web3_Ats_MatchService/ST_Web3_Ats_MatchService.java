package io.securitize.tests.apiTests.cicd.ST.ST_Web3_Ats_MatchService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Web3_Ats_MatchService extends BaseApiTest {

    @Test()
    public void HealthController_checkTest618() {
        testRequest(Method.GET, "https://web3-ats-match-service.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ST/web3-ats-match-service", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void BlockchainEventsController_getBlockchainEventsContractAddressTest966() {
        testRequest(Method.GET, "https://web3-ats-match-service.internal.{environment}.securitize.io/api/v1/events/contract", "BlockchainEventsController_getBlockchainEventsContractAddress", LoginAs.NONE, "ST/web3-ats-match-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/BlockchainEventResponseDto\"}}}}");
    }


    @Test()
    public void MatchOrderController_getMatchContractAddressTest108() {
        testRequest(Method.GET, "https://web3-ats-match-service.internal.{environment}.securitize.io/api/v1/match-handler", "MatchOrderController_getMatchContractAddress", LoginAs.NONE, "ST/web3-ats-match-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/MatchHandlerResponseDto\"}}}}");
    }




}

