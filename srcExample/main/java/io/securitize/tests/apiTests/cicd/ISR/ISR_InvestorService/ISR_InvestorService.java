package io.securitize.tests.apiTests.cicd.ISR.ISR_InvestorService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_InvestorService extends BaseApiTest {

    @Test()
    public void InvestorsController_getByIdTest484() {
        testRequest(Method.GET, "https://isr-investor-service.internal.{environment}.securitize.io/v1/investors/{id}", "InvestorsController_getById", LoginAs.NONE, "ISR/isr-investor-service", "{\"description\":\"Get Investor By Id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/InvestorDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest775() {
        testRequest(Method.GET, "https://isr-investor-service.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ISR/isr-investor-service", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void InvestorsController_getInvestorsTest351() {
        testRequest(Method.GET, "https://isr-investor-service.internal.{environment}.securitize.io/v1/investors", "InvestorsController_getInvestors", LoginAs.NONE, "ISR/isr-investor-service", "{\"description\":\"Get Investors By Filters\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetInvestorsResponseDto\"}}}}");
    }

}