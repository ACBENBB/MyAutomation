package io.securitize.tests.apiTests.cicd.FT.FT_FundraisePmGw;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class fundraise_pm_gw extends BaseApiTest {

    @Test()
    public void PmTranslationController_getTranslationTest48() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/translations/{language}/{system}", "PmTranslationController_getTranslation", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void SettingsController_getIssuerConfigurationTest255() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/settings/issuers/{issuerId}", "SettingsController_getIssuerConfiguration", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get issuer settings\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetIssuerSettingsDto\"}}}}");
    }


    @Test()
    public void ConnectTranslationController_getTranslationTest167() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/connect/translations/{language}/{system}", "ConnectTranslationController_getTranslation", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void OpportunitiesController_getOpportunityTest226() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/opportunities/{opportunityId}", "OpportunitiesController_getOpportunity", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get Opportunity\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetOpportunityDto\"}}}}");
    }


    @Test()
    public void SettingsController_getIssuerConfigurationTest27() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/connect/settings/issuers/{issuerId}", "SettingsController_getIssuerConfiguration", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get issuer settings\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetIssuerSettingsDto\"}}}}");
    }


    @Test()
    public void OpportunitiesController_getOpportunitiesTest741() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/connect/opportunities", "ConnectOpportunitiesController_getOpportunities", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get Opportunities\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetOpportunitiesDto\"}}}}}");
    }


    @Test()
    public void SettingsController_getAccreditationSettingsTest760() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/settings/accreditation", "SettingsController_getAccreditationSettings", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get accreditation settings\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccreditationSettings\"}}}}");
    }


    @Test()
    public void CashAccountController_getPermissionsTest165() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/cash-account", "CashAccountController_getPermissions", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get Cash Account permissions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetCashAccountSummaryResponseDto\"}}}}");
    }


    @Test()
    public void CashAccountController_getPermissionsTest745() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/connect/cash-account", "ConnectCashAccountController_getPermissions", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get Cash Account permissions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetCashAccountSummaryResponseDto\"}}}}");
    }


    @Test()
    public void OpportunitiesController_getQualificationQuestionsTest928() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/opportunities/{opportunityId}/qualification", "OpportunitiesController_getQualificationQuestions", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get qualification questions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetQualificationQuestions\"}}}}");
    }


    @Test()
    public void OpportunitiesController_getTestTest571() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/opportunities/{opportunityId}/test", "OpportunitiesController_getTest", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void HealthController_checkTest653() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/health", "HealthController_check", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void OpportunitiesController_getOpportunitiesTest791() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/opportunities", "OpportunitiesController_getOpportunities", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get Opportunities\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetOpportunitiesDto\"}}}}}");
    }


    @Test()
    public void OpportunitiesController_getOpportunityTest693() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/connect/opportunities/{opportunityId}", "ConnectOpportunitiesController_getOpportunity", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get Opportunity\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetOpportunityDto\"}}}}");
    }


    @Test()
    public void SettingsController_getAccreditationSettingsTest184() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/connect/settings/accreditation", "ConnectSettingsController_getAccreditationSettings", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get accreditation settings\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetAccreditationSettings\"}}}}");
    }


    @Test()
    public void OpportunitiesController_getQualificationQuestionsTest390() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/connect/opportunities/{opportunityId}/qualification", "ConnectOpportunitiesController_getQualificationQuestions", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"Get qualification questions\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetQualificationQuestions\"}}}}");
    }


    @Test()
    public void OpportunitiesController_getSocialDataTest655() {
        testRequest(Method.GET, "https://pm-gw.{environment}.securitize.io/v1/opportunities/{opportunityId}/social", "OpportunitiesController_getSocialData", LoginAs.SECURITIZE_ID, "FT/fundraise-pm-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OpportunitySocialDto\"}}}}");
    }
}

