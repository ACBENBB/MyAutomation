package io.securitize.tests.apiTests.cicd.ST.ST_Web3Proxy;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Web3Proxy extends BaseApiTest {

    @Test()
    public void HealthController_checkTest723() {
        testRequest(Method.GET, "https://web3.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ST/web3-proxy", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void HealthController_checkProviderTest51() {
        testRequest(Method.GET, "https://web3.internal.{environment}.securitize.io/health/{providerName}", "HealthController_checkProvider", LoginAs.NONE, "ST/web3-proxy", "{\"description\":\"\"}");
    }


    @Test()
    public void ProviderController_getProviderTest351() {
        testRequest(Method.GET, "https://web3.internal.{environment}.securitize.io/providers/{name}", "ProviderController_getProvider", LoginAs.NONE, "ST/web3-proxy", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/ProviderResponseDto\"}}}}");
    }


    @Test()
    public void ContractRegistrationController_getAllProviderContractsTest397() {
        testRequest(Method.GET, "https://web3.internal.{environment}.securitize.io/contracts", "ContractRegistrationController_getAllProviderContracts", LoginAs.NONE, "ST/web3-proxy", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/ContractResponseDTO\"}}}}}");
    }


    @Test()
    public void ProviderController_getProvidersTest762() {
        testRequest(Method.GET, "https://web3.internal.{environment}.securitize.io/providers", "ProviderController_getProviders", LoginAs.NONE, "ST/web3-proxy", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/ProviderResponseDto\"}}}}}");
    }




}

