package io.securitize.tests.apiTests.cicd.ATS.ATS_AtsReportingApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ATS_AtsReportingApi extends BaseApiTest {

    @Test()
    public void TradeHistoryController_getAllTest236() {
        testRequest(Method.GET, "https://ats-reporting-api.internal.{environment}.securitize.io/v2/trade-history", "TradeHistoryController_getAll", LoginAs.NONE, "ATS/ats-reporting-api", "{\"description\":\"Get trade history\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetTradeHistoryResponseDto\"}}}}}");
    }


    @Test()
    public void CAISReportsController_getPreviewTest802() {
        testRequest(Method.GET, "https://ats-reporting-api.internal.{environment}.securitize.io/v1/cais-reports/preview", "CAISReportsController_getPreview", LoginAs.NONE, "ATS/ats-reporting-api", "{\"description\":\"Preview CAIS Report\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/CAISReport\"}}}}");
    }


    @Test()
    public void PrometheusController_indexTest372() {
        testRequest(Method.GET, "https://ats-reporting-api.internal.{environment}.securitize.io/metrics", "PrometheusController_index", LoginAs.NONE, "ATS/ats-reporting-api", "{\"description\":\"\"}");
    }


    @Test()
    public void TradeHistoryController_getAllDeprecatedTest293() {
        testRequest(Method.GET, "https://ats-reporting-api.internal.{environment}.securitize.io/v1/trade-history", "TradeHistoryController_getAllDeprecated", LoginAs.NONE, "ATS/ats-reporting-api", "{\"description\":\"Get trade history DEPRECATED\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/TradeHistory\"}}}}}");
    }


    @Test()
    public void TradeHistoryController_getSummaryTest349() {
        testRequest(Method.GET, "https://ats-reporting-api.internal.{environment}.securitize.io/v2/trade-history/summary", "TradeHistoryController_getSummary", LoginAs.NONE, "ATS/ats-reporting-api", "{\"description\":\"Get trade history summary v2. Can filter and return by deploymentId\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/TradeSummary\"}}}}}");
    }




}

