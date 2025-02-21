package io.securitize.tests.apiTests.cicd.ATS.ATS_AtsConfigApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ATS_AtsConfigApi extends BaseApiTest {

    @Test()
    public void ConfigController_getMarketTest809() {
        testRequest(Method.GET, "https://ats-config-api.internal.{environment}.securitize.io/v1/market", "ConfigController_getMarket", LoginAs.NONE, "ATS/ats-config-api", "{\"description\":\"Get market availability by region\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/MarketAvailableByRegionDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest308() {
        testRequest(Method.GET, "https://ats-config-api.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ATS/ats-config-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }

    @Test()
    public void ConfigController_getInfoTest432() {
        testRequest(Method.GET, "https://ats-config-api.internal.{environment}.securitize.io/v1/info", "ConfigController_getInfo", LoginAs.NONE, "ATS/ats-config-api", "{\"description\":\"Get all info with markets by region\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetConfigWithRegionsDto\"}}}}");
    }


    @Test()
    public void ConfigController_getHealthTest565() {
        testRequest(Method.GET, "https://ats-config-api.internal.{environment}.securitize.io/v1/health", "ConfigController_getHealth", LoginAs.NONE, "ATS/ats-config-api", "{\"description\":\"Get health\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/HealthDto\"}}}}");
    }


    @Test()
    public void PrometheusController_indexTest926() {
        testRequest(Method.GET, "https://ats-config-api.internal.{environment}.securitize.io/metrics", "PrometheusController_index", LoginAs.NONE, "ATS/ats-config-api", "{\"description\":\"\"}");
    }




}

