package io.securitize.tests.apiTests.cicd.TA.TA_DistributionGw;

import io.restassured.http.*;
import io.securitize.infra.api.apicodegen.*;
import org.testng.annotations.*;

public class TA_DistributionGw extends BaseApiTest {

    @Test()
    public void AppController_getHelloTest916() {
        testRequest(Method.GET, "http://cp.{environment}.securitize.io/distributions/api", "AppController_getHello", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

//    @Test()
//    public void DistributionController_confirmDistributionTest414() {
//        testRequest(Method.POST, "https://cp.rc.securitize.io//distributions/api/distributions/{distributionId}/confirm", "DistributionController_confirmDistribution", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
//    }

//    @Test()
//    public void DistributionController_confirmUploadCsvTest415() {
//        testRequest(Method.POST, "https://cp.rc.securitize.io//distributions/api/distributions/{distributionId}/upload/confirm", "DistributionController_confirmUploadCsv", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
//    }

//    @Test()
//    public void DistributionController_createDistributionTest713() {
//        testRequest(Method.POST, "https://cp.rc.securitize.io//distributions/api/distributions", "DistributionController_createDistribution", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
//    }

    @Test()
    public void DistributionController_getCsvPresignedPostTest91() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/distributions/{distributionId}/upload", "DistributionController_getCsvPresignedPost", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void DistributionController_getDistributionTest595() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/distributions/{distributionId}", "DistributionController_getDistribution", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void DistributionController_getDistributionDownloadUrlTest649() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/distributions/{distributionId}/download", "DistributionController_getDistributionDownloadUrl", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void DistributionController_getDistributionInvestorsTest27() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/distributions/{distributionId}/investors", "DistributionController_getDistributionInvestors", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void DistributionController_getDistributionPresignedUrlTest33() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/distributions/{distributionId}/download", "DistributionController_getDistributionPresignedUrl", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void DistributionController_getDistributionsTest62() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/distributions", "DistributionController_getDistributions", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void DistributionController_getInvestorsTest344() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/distributions/{distributionId}/investors", "DistributionController_getInvestors", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void HealthController_checkTest595() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api", "HealthController_check", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }

    @Test()
    public void IssuerController_getIssuerInfoTest119() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/issuers/{id}/info", "IssuerController_getIssuerInfo", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void TaxFormsController_getLatestInvestorTaxFormDownloadLinkTest480() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/tax-forms/{investorId}/latest", "TaxFormsController_getLatestInvestorTaxFormDownloadLink", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

    @Test()
    public void TaxFormsController_getTaxFormSignedUrlTest842() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/distributions/api/tax-forms/{taxFormId}/download", "TaxFormsController_getTaxFormSignedUrl", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
    }

//    @Test()
//    public void TaxFormsController_UpdateTaxFormTest963() {
//        testRequest(Method.PUT, "https://cp.rc.securitize.io//distributions/api/tax-forms/{taxFormId}", "TaxFormsController_UpdateTaxForm", LoginAs.OPERATOR, "TA/TA_distribution_gw", "{\"description\":\"\"}");
//    }

}
