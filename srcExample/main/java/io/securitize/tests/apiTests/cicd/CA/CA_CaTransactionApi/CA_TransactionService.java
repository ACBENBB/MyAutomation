package io.securitize.tests.apiTests.cicd.CA.CA_CaTransactionApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class CA_TransactionService extends BaseApiTest {

    @Test()
    public void SummariesController_getAssetDailySummaryTest287() {
        testRequest(Method.GET, "https://ca-transaction-api.internal.{environment}.securitize.io/v1/summaries/asset-withdrawals", "SummariesController_getAssetDailySummary", LoginAs.NONE, "CA/ca-transaction-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetWithdrawalSummaryDto\"}}}}");
    }


    @Test()
    public void SettingsController_getSettingsTest240() {
        testRequest(Method.GET, "https://ca-transaction-api.internal.{environment}.securitize.io/v1/settings/{investorId}", "SettingsController_getSettings", LoginAs.NONE, "CA/ca-transaction-api", "{\"description\":\"Get Settings\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Settings\"}}}}");
    }


    @Test()
    public void SummariesController_getCreditCardsDailySummaryTest901() {
        testRequest(Method.GET, "https://ca-transaction-api.internal.{environment}.securitize.io/v1/summaries/credit-card", "SummariesController_getCreditCardsDailySummary", LoginAs.NONE, "CA/ca-transaction-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetCreditCardsDailySummaryDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest576() {
        testRequest(Method.GET, "https://ca-transaction-api.internal.{environment}.securitize.io/api/health", "HealthController_check", LoginAs.NONE, "CA/ca-transaction-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void SummariesController_getInstantSettlementSummaryTest853() {
        testRequest(Method.GET, "https://ca-transaction-api.internal.{environment}.securitize.io/v1/summaries/instant-settlement", "SummariesController_getInstantSettlementSummary", LoginAs.NONE, "CA/ca-transaction-api", "{\"description\":\"Get Instant Settlement Summary\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetInstantSettlementSummaryDto\"}}}}");
    }


    @Test()
    public void TransactionsController_getTransactionsTest821() {
        testRequest(Method.GET, "https://ca-transaction-api.internal.{environment}.securitize.io/v2/transactions", "TransactionsController_getTransactions", LoginAs.NONE, "CA/ca-transaction-api", "{\"description\":\"Get Transactions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransactionsDto\"}}}}");
    }
}

