package io.securitize.tests.apiTests.cicd.FT.FT_FundraiseOpportunitySvc;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class fundraise_opportunity_svc extends BaseApiTest {

    @Test()
    public void OpportunitiesController_getOpportunityByIdTest756() {
        testRequest(Method.GET, "https://fr-opportunity-svc.internal.{environment}.securitize.io/v1/opportunities/{id}", "OpportunitiesController_getOpportunityById", LoginAs.OPERATOR, "FT/fundraise-opportunity-svc", "{\"description\":\"Get opportunity by id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OpportunityResponse\"}}}}");
    }


    @Test()
    public void LanguageController_getLanguagesTest813() {
        testRequest(Method.GET, "https://fr-opportunity-svc.internal.{environment}.securitize.io/v1/languages", "LanguageController_getLanguages", LoginAs.OPERATOR, "FT/fundraise-opportunity-svc", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/LanguageResponse\"}}}}}");
    }


    @Test()
    public void HealthController_checkTest168() {
        testRequest(Method.GET, "https://fr-opportunity-svc.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.OPERATOR, "FT/fundraise-opportunity-svc", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void OpportunitiesController_getOpportunitiesTest271() {
        testRequest(Method.GET, "https://fr-opportunity-svc.internal.{environment}.securitize.io/v1/opportunities", "OpportunitiesController_getOpportunities", LoginAs.OPERATOR, "FT/fundraise-opportunity-svc", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void OpportunitiesFieldsController_getOpportunityFieldsTest219() {
        testRequest(Method.GET, "https://fr-opportunity-svc.internal.{environment}.securitize.io/v1/fields", "OpportunitiesFieldsController_getOpportunityFields", LoginAs.OPERATOR, "FT/fundraise-opportunity-svc", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void OpportunitiesFieldsController_getOpportunityFieldByIdTest157() {
        testRequest(Method.GET, "https://fr-opportunity-svc.internal.{environment}.securitize.io/v1/fields/{id}", "OpportunitiesFieldsController_getOpportunityFieldById", LoginAs.OPERATOR, "FT/fundraise-opportunity-svc", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Field\"}}}}");
    }


    @Test()
    public void OpportunitiesDocumentsController_GetOpportunityDocumentTest177() {
        testRequest(Method.GET, "https://fr-opportunity-svc.internal.{environment}.securitize.io/v1/documents/{id}", "OpportunitiesDocumentsController_GetOpportunityDocument", LoginAs.OPERATOR, "FT/fundraise-opportunity-svc", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OpportunitiesDocumentsGetResponse\"}}}}");
    }




}

