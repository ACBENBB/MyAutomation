package io.securitize.tests.apiTests.cicd.CA.CA_CaTransferMethodApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class CA_TransferMethodService extends BaseApiTest {

    @Test()
    public void CashAccountTransferMethodsController_getTransferMethodsTest823() {
        testRequest(Method.GET, "https://ca-transfer-method-api.internal.{environment}.securitize.io/v1/cash-accounts/{cashAccountId}/transfer-methods", "CashAccountTransferMethodsController_getTransferMethods", LoginAs.NONE, "CA/ca-transfer-method-api", "{\"description\":\"Get transfer methods for a cash account\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransferMethodsResponseDto\"}}}}");
    }


    @Test()
    public void InvestorTransferMethodsController_getTransferMethodsTest649() {
        testRequest(Method.GET, "https://ca-transfer-method-api.internal.{environment}.securitize.io/v1/investors/{investorId}/transfer-methods", "InvestorTransferMethodsController_getTransferMethods", LoginAs.NONE, "CA/ca-transfer-method-api", "{\"description\":\"Get transfer methods for an investor\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTransferMethodsResponseDto\"}}}}");
    }


    @Test()
    public void CashAccountTransferMethodsController_getInvestorDepositInstructionsTest657() {
        testRequest(Method.GET, "https://ca-transfer-method-api.internal.{environment}.securitize.io/v2/cash-accounts/{cashAccountId}/deposit-instructions", "CashAccountTransferMethodsController_getInvestorDepositInstructions", LoginAs.NONE, "CA/ca-transfer-method-api", "{\"description\":\"Get deposit instructions for a cash account\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/UsdAccountDepositInstructions\"}}}}");
    }

    @Test()
    public void CashAccountTransferMethodsController_getCashAccountDepositInstructionsTest261() {
        testRequest(Method.GET, "https://ca-transfer-method-api.internal.{environment}.securitize.io/v2/cash-accounts/{cashAccountId}/deposit-instructions", "CashAccountTransferMethodsController_getCashAccountDepositInstructions", LoginAs.NONE, "CA/ca-transfer-method-api", "{\"description\":\"Get deposit instructions for a cash account - DEPRECATED\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DeprecatedUsdAccountDepositInstructions\"}}}}");
    }


    @Test()
    public void InvestorTransferMethodsController_getInvestorDepositInstructionsV2Test462() {
        testRequest(Method.GET, "https://ca-transfer-method-api.internal.{environment}.securitize.io/v2/investors/{investorId}/deposit-instructions", "InvestorTransferMethodsController_getInvestorDepositInstructionsV2", LoginAs.NONE, "CA/ca-transfer-method-api", "{\"description\":\"Get deposit instructions for an investor - DEPRECATED\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DeprecatedUsdAccountDepositInstructions\"}}}}");
    }


    @Test()
    public void HealthController_checkTest575() {
        testRequest(Method.GET, "https://ca-transfer-method-api.internal.{environment}.securitize.io/api/health", "HealthController_check", LoginAs.NONE, "CA/ca-transfer-method-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void AccountTransferMethodsController_getUserWalletsTest259() {
        testRequest(Method.GET, "https://ca-transfer-method-api.internal.{environment}.securitize.io/v1/crypto-wallets", "AccountTransferMethodsController_getUserWallets", LoginAs.NONE, "CA/ca-transfer-method-api", "{\"description\":\"Get User Wallets\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Wallets\"}}}}");
    }


    @Test()
    public void InvestorTransferMethodsController_getInvestorDepositInstructionsTest558() {
        testRequest(Method.GET, "https://ca-transfer-method-api.internal.{environment}.securitize.io/v2/investors/{investorId}/deposit-instructions", "InvestorTransferMethodsController_getInvestorDepositInstructions", LoginAs.NONE, "CA/ca-transfer-method-api", "{\"description\":\"Get deposit instructions for an investor\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AccountDepositInstructions\"}}}}");
    }
}

