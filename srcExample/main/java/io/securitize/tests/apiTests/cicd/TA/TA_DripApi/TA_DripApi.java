package io.securitize.tests.apiTests.cicd.TA.TA_DripApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class TA_DripApi extends BaseApiTest {

    @Test()
    public void AssetPlanController_getSnapshotTest198() {
        testRequest(Method.GET, "https://drip-api.internal.{environment}.securitize.io/api/v1/asset-plan/snapshot", "AssetPlanController_getSnapshot", LoginAs.SECURITIZE_ID, "TA/drip-api", "{\"description\":\"\"}");
    }


    @Test()
    public void HealthController_checkTest636() {
        testRequest(Method.GET, "https://drip-api.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.SECURITIZE_ID, "TA/drip-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void OptionalCashController_getRecurrentReinvestmentUrlFormTest887() {
        testRequest(Method.GET, "https://drip-api.internal.{environment}.securitize.io/api/v1/optional-cash/recurrent-reinvestment/info", "OptionalCashController_getRecurrentReinvestmentUrlForm", LoginAs.SECURITIZE_ID, "TA/drip-api", "{\"description\":\"\"}");
    }


    @Test()
    public void AssetPlanController_getAssetPlanTest717() {
        testRequest(Method.GET, "https://drip-api.internal.{environment}.securitize.io/api/v1/asset-plan", "AssetPlanController_getAssetPlan", LoginAs.SECURITIZE_ID, "TA/drip-api", "{\"description\":\"\"}");
    }


    @Test()
    public void AssetPlanController_investorHasPlanTest926() {
        testRequest(Method.GET, "https://drip-api.internal.{environment}.securitize.io/api/v1/asset-plan/investor-has-plan/{securitizeIdProfileId}/token-id/{tokenId}", "AssetPlanController_investorHasPlan", LoginAs.SECURITIZE_ID, "TA/drip-api", "{\"description\":\"\"}");
    }




}

