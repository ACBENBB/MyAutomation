package io.securitize.tests.apiTests.cicd.ISR.ISR_InvestmentService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_InvestmentService extends BaseApiTest {

    @Test()
    public void InvestmentRequestController_getInvestmentRequestsByTokenIdTest776() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investment-requests", "InvestmentRequestController_getInvestmentRequestsByTokenId", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\"}");
    }


    @Test()
    public void DepositWalletController_getDepositWalletsTest613() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investors/{investorId}/deposit-wallets", "DepositWalletController_getDepositWallets", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DepositWalletResponseDto\"}}}}");
    }


    @Test()
    public void DepositWalletController_getDepositWalletByIdTest284() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investors/{investorId}/deposit-wallets/{depositWalletId}", "DepositWalletController_getDepositWalletById", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DepositWalletResponseDto\"}}}}");
    }


    @Test()
    public void InvestmentRoundController_getInvestmentRoundByIdTest581() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}", "InvestmentRoundController_getInvestmentRoundById", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/InvestmentRoundResponseDto\"}}}}");
    }


    @Test()
    public void InvestmentRequestController_getInvestmentRequestsForTokensAndWalletsTest142() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/investors/{investorId}/investment-requests", "InvestmentRequestController_getInvestmentRequestsForTokensAndWallets", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetInvestmentRequestsForTokensAndWalletsResponseDto\"}}}}");
    }


    @Test()
    public void DepositWalletController_getDepositWalletsByInvestorIdTest196() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/investors/{investorId}/deposit-wallets", "DepositWalletController_getDepositWalletsByInvestorId", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\"}");
    }


    @Test()
    public void InvestmentRoundController_getInvestmentRoundsTest794() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investment-rounds", "InvestmentRoundController_getInvestmentRounds", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\"}");
    }


    @Test()
    public void SubscriptionAgreementStatusLogsController_getInvestorInvestmentRequestSubscriptionAgreementStatusLogsTest313() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}/investors/{investorId}/investment-requests/{investmentRequestId}/subscription-agreement-status-logs", "SubscriptionAgreementStatusLogsController_getInvestorInvestmentRequestSubscriptionAgreementStatusLogs", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetInvestmentRequestResponseDto\"}}}}");
    }


    @Test()
    public void HealthCheckController_checkDatabaseConnectionTest298() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/health", "HealthCheckController_checkDatabaseConnection", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\"}");
    }


    @Test()
    public void DepositTransactionController_getDepositTransactionsByTokenIdTest507() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/deposit-transactions", "DepositTransactionController_getDepositTransactionsByTokenId", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DepositTransactionResponseDto\"}}}}");
    }


    @Test()
    public void DepositTransactionController_getDepositTransactionsByWalletIdTest790() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}/investors/{investorId}/deposit-wallets/{depositWalletId}/deposit-transactions", "DepositTransactionController_getDepositTransactionsByWalletId", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DepositTransactionResponseDto\"}}}}");
    }


    @Test()
    public void DepositTransactionController_getDepositTransactionsByInvestorIdTest199() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/investors/{investorId}/deposit-transactions", "DepositTransactionController_getDepositTransactionsByInvestorId", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DepositTransactionResponseDto\"}}}}");
    }


    @Test()
    public void InvestmentRequestController_getInvestorInvestmentRequestsPerRoundTest306() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}/investors/{investorId}/investment-requests", "InvestmentRequestController_getInvestorInvestmentRequestsPerRound", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetInvestmentRequestResponseDto\"}}}}}");
    }


    @Test()
    public void InvestmentRequestController_getInvestmentRequestByIdTest53() {
        testRequest(Method.GET, "https://isr-investment-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}/investors/{investorId}/investment-requests/{investmentRequestId}", "InvestmentRequestController_getInvestmentRequestById", LoginAs.NONE, "ISR/isr-investment-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetInvestmentRequestResponseDto\"}}}}");
    }

}
