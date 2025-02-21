package io.securitize.tests.apiTests.cicd.FT.FT_FundraiseInvestment;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class fundraise_investment extends BaseApiTest {
    @Test()
    public void AchController_getAccountTest475() {
        testRequest(Method.GET, "https://fr-investment-svc.internal.{environment}.securitize.io/v1/ach/accounts/{investorId}", "AchController_getAccount", LoginAs.OPERATOR, "FT/fundraise-investment", "{\"description\":\"\"}");
    }


    @Test()
    public void InvestmentsController_getInvestmentByIdTest445() {
        testRequest(Method.GET, "https://fr-investment-svc.internal.{environment}.securitize.io/v1/investments/{investmentId}", "InvestmentsController_getInvestmentById", LoginAs.OPERATOR, "FT/fundraise-investment", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void HealthController_checkTest117() {
        testRequest(Method.GET, "https://fr-investment-svc.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.OPERATOR, "FT/fundraise-investment", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void InvestmentsV2Controller_getInvestmentByIdTest950() {
        testRequest(Method.GET, "https://fr-investment-svc.internal.{environment}.securitize.io/v2/investments/{investmentId}", "InvestmentsV2Controller_getInvestmentById", LoginAs.OPERATOR, "FT/fundraise-investment", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void InvestmentsV2Controller_getInvestmentsTest313() {
        testRequest(Method.GET, "https://fr-investment-svc.internal.{environment}.securitize.io/v2/investments", "InvestmentsV2Controller_getInvestments", LoginAs.OPERATOR, "FT/fundraise-investment", "{\"description\":\"\"}");
    }


    @Test()
    public void InvestmentsController_getInvestmentsTest598() {
        testRequest(Method.GET, "https://fr-investment-svc.internal.{environment}.securitize.io/v1/investments", "InvestmentsController_getInvestments", LoginAs.OPERATOR, "FT/fundraise-investment", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"object\"}}}}}");
    }


    @Test()
    public void InvestmentsController_getInvestmentCurrenciesTest885() {
        testRequest(Method.GET, "https://fr-investment-svc.internal.{environment}.securitize.io/v1/investments/investment-rounds/{investmentRoundId}/currencies", "InvestmentsController_getInvestmentCurrencies", LoginAs.OPERATOR, "FT/fundraise-investment", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetInvestmentCurrenciesResponseDto\"}}}}}");
    }
}
