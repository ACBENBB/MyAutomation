package io.securitize.tests.apiTests.cicd.CA.CA_CaAccountGwApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class CA_AccountGwService extends BaseApiTest {

    @Test()
    public void TransactionsController_getInstantSettlementLimitTest160() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account/transactions/instant-settlements/limit", "TransactionsController_getInstantSettlementLimit", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Instant Settlement Limit\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetInstantSettlementLimitDto\"}}}}");
    }


    @Test()
    public void AccountSummaryController_getUserAccountSummaryV1Test429() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account-summary", "AccountSummaryController_getUserAccountSummaryV1", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Account - DEPRECATED\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountSummaryDtoV1\"}}}}");
    }


    @Test()
    public void ConnectAccountSummaryController_getUserAccountSummaryTest413() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v2/account-summary", "ConnectAccountSummaryController_getUserAccountSummary", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Account Summary\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountSummaryDto\"}}}}");
    }


    @Test()
    public void ConnectAccountController_getLinkedAccountTest798() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account/linked", "ConnectAccountController_getLinkedAccount", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Linked Account\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransferMethodDto\"}}}}");
    }


    @Test()
    public void ConnectTransferMethodsControllerV2_getDepositInstructionsTest240() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v2/account/transfer-methods/deposit-instructions", "ConnectTransferMethodsControllerV2_getDepositInstructions", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get deposit instructions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountDepositInstructionsDto\"}}}}");
    }


    @Test()
    public void TransactionsController_getAccountTransactionsTest437() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account/transactions", "TransactionsController_getAccountTransactions", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Transactions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransactionsDto\"}}}}");
    }


    @Test()
    public void TransferMethodsControllerV2_getDepositInstructionsTest789() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v2/account/transfer-methods/deposit-instructions", "TransferMethodsControllerV2_getDepositInstructions", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get deposit instructions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountDepositInstructionsDto\"}}}}");
    }


    @Test()
    public void TransactionsController_getAssetWithdrawalLimitTest947() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account/transactions/asset-withdrawal/limit", "TransactionsController_getAssetWithdrawalLimit", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Asset Withdrawals Limit\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetWithdrawalsLimitDto\"}}}}");
    }


    @Test()
    public void ConnectTransactionsController_getAssetWithdrawalLimitTest72() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account/transactions/asset-withdrawal/limit", "ConnectTransactionsController_getAssetWithdrawalLimit", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Asset Withdrawal Limit\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetWithdrawalsLimitDto\"}}}}");
    }


    @Test()
    public void TransferMethodsController_getDepositInstructionsV1Test607() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account/transfer-methods/deposit-instructions", "TransferMethodsController_getDepositInstructionsV1", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get deposit instructions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DeprecatedGetUSDDepositInstructions\"}}}}");
    }


    @Test()
    public void ConnectTransferMethodsControllerV2_getTransferMethodsTest22() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v2/account/transfer-methods", "ConnectTransferMethodsControllerV2_getTransferMethods", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Fund Transfer Methods\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransferMethodsResponseDto\"}}}}");
    }


    @Test()
    public void ConnectTransferMethodsController_getFundTransferMethodsTest150() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account/transfer-methods", "ConnectTransferMethodsController_getFundTransferMethods", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Fund Transfer Methods\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountFundTransferMethodsResponseDto\"}}}}");
    }


    @Test()
    public void ConnectTransactionsController_getInstantSettlementLimitTest661() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account/transactions/instant-settlements/limit", "ConnectTransactionsController_getInstantSettlementLimit", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Instant Settlement Limit\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetInstantSettlementLimitDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest679() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/health", "HealthController_check", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void BalanceController_getUserBalanceTest84() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v2/account/balance", "BalanceController_getUserBalance", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get User Balance\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetCashBalanceDto\"}}}}");
    }


    @Test()
    public void ConnectTransactionsController_getAccountTransactionsTest200() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account/transactions", "ConnectTransactionsController_getAccountTransactions", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Transactions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransactionsDto\"}}}}");
    }


    @Test()
    public void WalletsController_getUserWalletsTest986() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account/wallets", "WalletsController_getUserWallets", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Wallets\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetWalletsDto\"}}}}");
    }


    @Test()
    public void ConnectExchangeController_getLastQuoteTest648() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/forex", "ConnectExchangeController_getLastQuote", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetLastQuoteResponseDto\"}}}}");
    }


    @Test()
    public void ConnectAccountSummaryController_getUserAccountSummaryV1Test210() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account-summary", "ConnectAccountSummaryController_getUserAccountSummaryV1", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Account Summary - DEPRECATED\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountSummaryDtoV1\"}}}}");
    }


    @Test()
    public void ExchangeController_getLastQuoteTest48() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/forex", "ExchangeController_getLastQuote", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetLastQuoteResponseDto\"}}}}");
    }


    @Test()
    public void AccountController_getLinkedAccountTest727() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account/linked", "AccountController_getLinkedAccount", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Linked Account\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransferMethodDto\"}}}}");
    }


    @Test()
    public void ConnectConfigController_getConfigTest673() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/config", "ConnectConfigController_getConfig", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Config\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetConfigResponseDto\"}}}}");
    }


    @Test()
    public void ConfigController_getConfigTest971() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/config", "ConfigController_getConfig", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Config\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetConfigResponseDto\"}}}}");
    }


    @Test()
    public void ConnectAccountController_getCreditCardsLimitTest271() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account/credit-cards/limit", "ConnectAccountController_getCreditCardsLimit", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Cards limit\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetCreditCardsLimitDto\"}}}}");
    }


    @Test()
    public void TransferMethodsControllerV2_getFundTransferMethodsTest16() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v2/account/transfer-methods", "TransferMethodsControllerV2_getFundTransferMethods", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Fund Transfer Methods\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransferMethodsResponseDto\"}}}}");
    }


    @Test()
    public void TransferMethodsController_getFundTransferMethodsTest152() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account/transfer-methods", "TransferMethodsController_getFundTransferMethods", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Fund Transfer Methods\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountFundTransferMethodsResponseDto\"}}}}");
    }


    @Test()
    public void AccountController_getCreditCardsLimitTest180() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v1/account/credit-cards/limit", "AccountController_getCreditCardsLimit", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Cards limit\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetCreditCardsLimitDto\"}}}}");
    }


    @Test()
    public void AccountSummaryController_getUserAccountSummaryTest13() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/api/v2/account-summary", "AccountSummaryController_getUserAccountSummary", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Account\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountSummaryDto\"}}}}");
    }


    @Test()
    public void ConnectTransferMethodsController_getDepositInstructionsV1Test137() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account/transfer-methods/deposit-instructions", "ConnectTransferMethodsController_getDepositInstructionsV1", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get deposit instructions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccountWireTransferInstructionsDto\"}}}}");
    }


    @Test()
    public void ConnectWalletsController_getUserWalletsTest150() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v1/account/wallets", "ConnectWalletsController_getUserWallets", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get Wallets\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetWalletsDto\"}}}}");
    }


    @Test()
    public void ConnectBalanceController_getUserBalanceTest997() {
        testRequest(Method.GET, "https://account-gw.{environment}.securitize.io/connect/v2/account/balance", "ConnectBalanceController_getUserBalance", LoginAs.CASH_ACCOUNT, "CA/ca-account-gw-api", "{\"description\":\"Get User Balance\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetCashBalanceDto\"}}}}");
    }
}

