package io.securitize.tests.apiTests.cicd.CA.CA_CaInvestorApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class CA_InvestorService extends BaseApiTest {

    @Test()
    public void HealthController_checkTest424() {
        testRequest(Method.GET, "https://ca-investor-api.internal.{environment}.securitize.io/api/health", "HealthController_check", LoginAs.NONE, "CA/ca-investor-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void AccountsController_getBalanceTest588() {
        testRequest(Method.GET, "https://ca-investor-api.internal.{environment}.securitize.io/v2/accounts/{investorId}/balance", "AccountsController_getBalance", LoginAs.NONE, "CA/ca-investor-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Balance\"}}}}");
    }


    @Test()
    public void CashAccountsController_getCashAccountsTest272() {
        testRequest(Method.GET, "https://ca-investor-api.internal.{environment}.securitize.io/v1/cash-accounts", "CashAccountsController_getCashAccounts", LoginAs.NONE, "CA/ca-investor-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetCashAccountsResponseDto\"}}}}");
    }
}

