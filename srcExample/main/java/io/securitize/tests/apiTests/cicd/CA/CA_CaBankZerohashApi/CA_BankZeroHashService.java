package io.securitize.tests.apiTests.cicd.CA.CA_CaBankZerohashApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class CA_BankZeroHashService extends BaseApiTest {

    @Test()
    public void HealthController_checkTest875() {
        testRequest(Method.GET, "https://ca-bank-zerohash-api.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "CA/ca-bank-zerohash-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void AccountsController_getParticipantsByProviderIdTest47() {
        testRequest(Method.GET, "https://ca-bank-zerohash-api.internal.{environment}.securitize.io/v1/accounts/{providerId}", "AccountsController_getParticipantsByProviderId", LoginAs.NONE, "CA/ca-bank-zerohash-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/CreateAccountResponseDto\"}}}}");
    }


    @Test()
    public void TransferMethodsController_getCryptoDepositInstructionsTest317() {
        testRequest(Method.GET, "https://ca-bank-zerohash-api.internal.{environment}.securitize.io/v1/accounts/{providerId}/deposit-instructions/crypto", "TransferMethodsController_getCryptoDepositInstructions", LoginAs.NONE, "CA/ca-bank-zerohash-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/CryptoDepositInstructions\"}}}}");
    }


    @Test()
    public void AccountsController_getBalanceTest815() {
        testRequest(Method.GET, "https://ca-bank-zerohash-api.internal.{environment}.securitize.io/v1/accounts/{providerId}/balance", "AccountsController_getBalance", LoginAs.NONE, "CA/ca-bank-zerohash-api", "{\"description\":\"Return balance for an account\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetBalanceResponseDto\"}}}}");
    }
}

