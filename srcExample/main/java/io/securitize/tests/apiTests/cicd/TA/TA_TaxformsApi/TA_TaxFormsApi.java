package io.securitize.tests.apiTests.cicd.TA.TA_TaxformsApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class TA_TaxFormsApi extends BaseApiTest {

    @Test()
    public void TaxFormsController_getAggregatedTaxFormPresignedUrlTest849() {
        testRequest(Method.GET, "https://tax-forms-api.internal.{environment}.securitize.io/api/v1/tax-forms/{investorId}/{taxFormType}/{taxYear}/download", "TaxFormsController_getAggregatedTaxFormPresignedUrl", LoginAs.SECURITIZE_ID, "TA/tax-forms-api", "{\"description\":\"\"}");
    }


    @Test()
    public void HealthController_checkTest823() {
        testRequest(Method.GET, "https://tax-forms-api.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.SECURITIZE_ID, "TA/tax-forms-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void TaxFormsController_getInvestorTaxFormsTest187() {
        testRequest(Method.GET, "https://tax-forms-api.internal.{environment}.securitize.io/api/v1/tax-forms", "TaxFormsController_getInvestorTaxForms", LoginAs.SECURITIZE_ID, "TA/tax-forms-api", "{\"description\":\"\"}");
    }


    @Test()
    public void TaxFormsController_getTaxFormTest69() {
        testRequest(Method.GET, "https://tax-forms-api.internal.{environment}.securitize.io/api/v1/tax-forms/{taxFormId}", "TaxFormsController_getTaxForm", LoginAs.SECURITIZE_ID, "TA/tax-forms-api", "{\"description\":\"\"}");
    }


    @Test()
    public void TaxFormsController_getW8BENEFormTest717() {
        testRequest(Method.GET, "https://tax-forms-api.internal.{environment}.securitize.io/api/v1/tax-forms/w8ben-e", "TaxFormsController_getW8BENEForm", LoginAs.SECURITIZE_ID, "TA/tax-forms-api", "{\"description\":\"\"}");
    }


    @Test()
    public void TaxFormsController_getTaxFormPresignedUrlTest885() {
        testRequest(Method.GET, "https://tax-forms-api.internal.{environment}.securitize.io/api/v1/tax-forms/{taxFormId}/download", "TaxFormsController_getTaxFormPresignedUrl", LoginAs.SECURITIZE_ID, "TA/tax-forms-api", "{\"description\":\"\"}");
    }


    @Test()
    public void TaxFormsController_getW8BeneUploadUrlTest937() {
        testRequest(Method.GET, "https://tax-forms-api.internal.{environment}.securitize.io/api/v1/tax-forms/w8ben-e/upload", "TaxFormsController_getW8BeneUploadUrl", LoginAs.SECURITIZE_ID, "TA/tax-forms-api", "{\"description\":\"\"}");
    }




}

