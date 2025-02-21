package io.securitize.tests.apiTests.cicd.TA.TA_DistributionApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class TA_DistributionApi extends BaseApiTest {

    @Test()
    public void DistributionController_getInvestorByIdTest220() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions/{distributionId}/investors/{investorId}", "DistributionController_getInvestorById", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionInvestorTransactionsDto\"}}}}");
    }


    @Test()
    public void DistributionController_getDistributionListTest53() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions", "DistributionController_getDistributionList", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionDataListResponseDto\"}}}}");
    }


    @Test()
    public void DistributionController_getIssuerInfoTest434() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions/issuers/{id}/info", "DistributionController_getIssuerInfo", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/IssuerInfoResponseDto\"}}}}");
    }


    @Test()
    public void DistributionInvestorController_getDistributionByInvestorTest854() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/investors/{investorId}/history", "DistributionInvestorController_getDistributionByInvestor", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionInvestorHistoryResponseDto\"}}}}");
    }


    @Test()
    public void DistributionController_getInvestorsTest869() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions/{id}/investors", "DistributionController_getInvestors", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionInvestorsResponseDto\"}}}}");
    }


    @Test()
    public void DistributionCsvController_getDistributionCsvTest943() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions/{id}/download", "DistributionCsvController_getDistributionCsv", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionDownloadResponseDto\"}}}}");
    }


    @Test()
    public void DistributionCalendarController_getHolidaysTest615() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions/distribution-calendar/holidays", "DistributionCalendarController_getHolidays", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/DistributionCalendarDataResponseDto\"}}}}}");
    }


    @Test()
    public void HealthController_checkTest525() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void DistributionController_getDistributionTest257() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions/{id}", "DistributionController_getDistribution", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionDataResponseDto\"}}}}");
    }


    @Test()
    public void DistributionController_getDistribuionsByInvestorIdTest653() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions/investors/{id}", "DistributionController_getDistribuionsByInvestorId", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionDataListResponseDto\"}}}}");
    }


    @Test()
    public void DistributionCsvController_getUploadPresignedUrlTest740() {
        testRequest(Method.GET, "https://distributions-api.internal.{environment}.securitize.io/api/v2/distributions/{id}/upload", "DistributionCsvController_getUploadPresignedUrl", LoginAs.OPERATOR, "TA/distribution-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/PresignedPostDto\"}}}}");
    }

}