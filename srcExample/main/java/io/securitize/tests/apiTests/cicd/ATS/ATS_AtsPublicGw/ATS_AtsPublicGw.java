package io.securitize.tests.apiTests.cicd.ATS.ATS_AtsPublicGw;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ATS_AtsPublicGw extends BaseApiTest {

    @Test()
    public void HealthController_checkTest584() {
        testRequest(Method.GET, "https://ats-public-gw.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ATS/ats-public-gw", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void AssetsController_getMarketSummaryTest20() {
        testRequest(Method.GET, "https://ats-public-gw.internal.{environment}.securitize.io/v1/assets/market-summary", "AssetsController_getMarketSummary", LoginAs.NONE, "ATS/ats-public-gw", "{\"description\":\"\"}");
    }




}

