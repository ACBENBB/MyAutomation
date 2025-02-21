package io.securitize.tests.apiTests.cicd.ISR.ISR_TransactionService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_TransactionService extends BaseApiTest {

    @Test()
    public void HealthController_checkTest180() {
        testRequest(Method.GET, "https://isr-transaction-service.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ISR/isr-transaction-service", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void TokenTransactionsController_getByIdTest603() {
        testRequest(Method.GET, "https://isr-transaction-service.internal.{environment}.securitize.io/v1/token-transactions/{id}", "TokenTransactionsController_getById", LoginAs.NONE, "ISR/isr-transaction-service", "{\"description\":\"Get Token Transaction By Id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TokenTransactionDto\"}}}}");
    }


    @Test()
    public void ReportingController_getAllTransactionsForInvestorOnCsvTest630() {
        testRequest(Method.GET, "https://isr-transaction-service.internal.{environment}.securitize.io/v1/reporting/investors/{investorId}/transactions/csv", "ReportingController_getAllTransactionsForInvestorOnCsv", LoginAs.NONE, "ISR/isr-transaction-service", "{\"description\":\"PresignedUrl for Investor Transactions CSV\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TransactionsDownloadResponseDto\"}}}}");
    }


    @Test()
    public void TokenTransactionsController_getTokenTransactionsTest612() {
        testRequest(Method.GET, "https://isr-transaction-service.internal.{environment}.securitize.io/v1/token-transactions", "TokenTransactionsController_getTokenTransactions", LoginAs.NONE, "ISR/isr-transaction-service", "{\"description\":\"Get Token Transactions By Filters\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTokenTransactionsResponseDto\"}}}}");
    }

}