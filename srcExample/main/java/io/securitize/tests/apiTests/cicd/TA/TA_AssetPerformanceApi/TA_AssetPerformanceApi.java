package io.securitize.tests.apiTests.cicd.TA.TA_AssetPerformanceApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class TA_AssetPerformanceApi extends BaseApiTest {

    @Test()
    public void AssetPerformanceController_getAssetPerformanceTotalValueTest800() {
        testRequest(Method.GET, "https://asset-performance-api.internal.{environment}.securitize.io/api/asset-performance/total-value", "AssetPerformanceController_getAssetPerformanceTotalValue", LoginAs.OPERATOR, "TA/asset-performance-api", "{\"description\":\"\"}");
    }


    @Test()
    public void AssetPerformanceController_getAssetPerformanceConfigTest492() {
        testRequest(Method.GET, "https://asset-performance-api.internal.{environment}.securitize.io/api/asset-performance/config", "AssetPerformanceController_getAssetPerformanceConfig", LoginAs.OPERATOR, "TA/asset-performance-api", "{\"description\":\"\"}");
    }


    @Test()
    public void HealthController_checkTest524() {
        testRequest(Method.GET, "https://asset-performance-api.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.OPERATOR, "TA/asset-performance-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void AssetPerformanceController_getAssetPerformanceTest928() {
        testRequest(Method.GET, "https://asset-performance-api.internal.{environment}.securitize.io/api/asset-performance", "AssetPerformanceController_getAssetPerformance", LoginAs.OPERATOR, "TA/asset-performance-api", "{\"description\":\"\"}");
    }


    @Test()
    public void PortfolioPerformanceController_getPortfolioIncomeTest849() {
        testRequest(Method.GET, "https://asset-performance-api.internal.{environment}.securitize.io/api/portfolio-performance", "PortfolioPerformanceController_getPortfolioIncome", LoginAs.OPERATOR, "TA/asset-performance-api", "{\"description\":\"\"}");
    }




}

