package io.securitize.tests.apiTests.cicd.ATS.ATS_AtsAssetApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ATS_AtsAssetApi extends BaseApiTest {

    @Test()
    public void AssetsController_getAssetWalletAddressTest269() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/{deploymentId}/tbe-address", "AssetsController_getAssetWalletAddress", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get asset omnibus tbe address\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AssetOmnibusTBEWalletAddress\"}}}}");
    }


    @Test()
    public void AssetsController_getDisplayableContentTest708() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/{security}/displayable-content", "AssetsController_getDisplayableContent", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get Data for Asset Info Tab\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AssetDisplayableContent\"}}}}");
    }


    @Test()
    public void AssetsController_getAssetRulesTest370() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/{security}/rules", "AssetsController_getAssetRules", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get Asset Rules\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AssetRules\"}}}}");
    }


    @Test()
    public void AssetsController_getAssetStatisticsTest19() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/statistics", "AssetsController_getAssetStatistics", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get all asset statistics\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/Statistics\"}}}}}");
    }


    @Test()
    public void AssetsController_getAssetDetailsTest55() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/asset-details", "AssetsController_getAssetDetails", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get Asset Details\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetDetailsDto\"}}}}");
    }


    @Test()
    public void IssuersController_getIssuersTest302() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/issuers", "IssuersController_getIssuers", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get all issuers\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/Issuer\"}}}}}");
    }


    @Test()
    public void AssetsController_getAssetStatisticsByIdTest729() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/{security}/statistics", "AssetsController_getAssetStatisticsById", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get asset statistics by id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Statistics\"}}}}");
    }


    @Test()
    public void AssetsController_getWeb3InfoTest622() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/{deploymentId}/web3-info", "AssetsController_getWeb3Info", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get asset web3 info\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AssetWeb3Info\"}}}}");
    }


    @Test()
    public void HealthController_checkTest244() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/api/health", "HealthController_check", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void AssetsController_getAssetCategoriesTest776() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/categories", "AssetsController_getAssetCategories", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get all asset categories\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void PrometheusController_indexTest149() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/metrics", "PrometheusController_index", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"\"}");
    }


    @Test()
    public void AssetsController_getAssetsTest50() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets", "AssetsController_getAssets", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get all assets\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetAssetDto\"}}}}}");
    }


    @Test()
    public void AssetsController_getAssetByIdTest250() {
        testRequest(Method.GET, "https://ats-asset-api.internal.{environment}.securitize.io/v1/assets/{security}", "AssetsController_getAssetById", LoginAs.NONE, "ATS/ats-asset-api", "{\"description\":\"Get assets by id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAssetDto\"}}}}");
    }




}

