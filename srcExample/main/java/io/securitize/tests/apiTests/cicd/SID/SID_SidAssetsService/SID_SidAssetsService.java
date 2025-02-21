package io.securitize.tests.apiTests.cicd.SID.SID_SidAssetsService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class SID_SidAssetsService extends BaseApiTest {

    @Test()
    public void HealthController_checkHealthTest568() {
        testRequest(Method.GET, "https://sid-assets-service.internal.{environment}.securitize.io/health", "HealthController_checkHealth", LoginAs.NONE, "SID/sid-assets-service", "{\"description\":\"Health Check\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/HealthDto\"}}}}");
    }


//    @Test()
//    public void AssetsController_deleteInvestmentRequestTest833() {
//        testRequest(Method.DELETE, "https://sid-assets-service.internal.{environment}.securitize.io/api/v1/assets/{investorId}/investment-requests/{id}", "AssetsController_deleteInvestmentRequest", LoginAs.NONE, "SID/sid-assets-service", "{\"description\":\"Cancel Investor Investment Request\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DeleteInvestmentRequestResponseDto\"}}}}");
//    }


    @Test()
    public void TokenWalletsController_getInvestmentRequestsTest745() {
        testRequest(Method.GET, "https://sid-assets-service.internal.{environment}.securitize.io/api/v1/token-wallets/{investorId}", "TokenWalletsController_getInvestmentRequests", LoginAs.NONE, "SID/sid-assets-service", "{\"description\":\"Get Token Wallets By InvestorId\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetTokenWalletByInvestorId\"}}}}}");
    }


    @Test()
    public void IssuersController_getIssuerTest390() {
        testRequest(Method.GET, "https://sid-assets-service.internal.{environment}.securitize.io/api/v1/issuers/{issuerId}", "IssuersController_getIssuer", LoginAs.NONE, "SID/sid-assets-service", "{\"description\":\"Get Issuer By Issuer ID\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetIssuerByIssuerId\"}}}}");
    }


    @Test()
    public void AssetsController_getInvestmentRequestsTest804() {
        testRequest(Method.GET, "https://sid-assets-service.internal.{environment}.securitize.io/api/v1/assets/{investorId}/investment-requests", "AssetsController_getInvestmentRequests", LoginAs.NONE, "SID/sid-assets-service", "{\"description\":\"Investor Investment Requests\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetPendingIssuanceResponseDto\"}}}}}");
    }


    @Test()
    public void AssetsController_getAllPendingIssuanceAssetsTest157() {
        testRequest(Method.GET, "https://sid-assets-service.internal.{environment}.securitize.io/api/v1/assets/{investorId}/pending-issuance", "AssetsController_getAllPendingIssuanceAssets", LoginAs.NONE, "SID/sid-assets-service", "{\"description\":\"Investor Assets for Issuance Pending\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetPendingIssuanceResponseDto\"}}}}}");
    }


    @Test()
    public void AssetsController_getAllIssuedAssetsTest898() {
        testRequest(Method.GET, "https://sid-assets-service.internal.{environment}.securitize.io/api/v1/assets/{investorId}/issued", "AssetsController_getAllIssuedAssets", LoginAs.NONE, "SID/sid-assets-service", "{\"description\":\"Investor Issued Assets\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetIssuedAssetsResponseDto\"}}}}}");
    }


    @Test()
    public void InvestorsController_getInvestorIssuersTest771() {
        testRequest(Method.GET, "https://sid-assets-service.internal.{environment}.securitize.io/api/v1/investors/{investorId}/issuers", "InvestorsController_getInvestorIssuers", LoginAs.NONE, "SID/sid-assets-service", "{\"description\":\"Get Issuers By Investor ID\"}");
    }

}

