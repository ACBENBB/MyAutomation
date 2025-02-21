package io.securitize.tests.apiTests.cicd.ISR.ISR_IssuerService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_IssuerService extends BaseApiTest {

    @Test()
    public void FundraiseDocumentsController_getIssuerDocumentsTest581() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/issuer-fundraise-documents", "FundraiseDocumentsController_getIssuerDocuments", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/FundraiseDocument\"}}}}}");
    }


    @Test()
    public void HealthController_checkTest206() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void HistoricalTokenValuesController_getByIdTest296() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/historical-token-values/{id}", "HistoricalTokenValuesController_getById", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"Get Historical Token Value\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/HistoricalTokenValue\"}}}}");
    }


    @Test()
    public void IssuersController_getByIdTest798() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/issuers/{id}", "IssuersController_getById", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"Get Issuer By Id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Issuer\"}}}}");
    }


    @Test()
    public void HistoricalTokenValuesController_getHistoricalTokenValuesTest121() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/historical-token-values", "HistoricalTokenValuesController_getHistoricalTokenValues", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"Get Historical Token Values By Filters\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetHistoricalTokenValuesResponseDto\"}}}}");
    }


    @Test()
    public void LabelsController_findLabelsUniquesTest267() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/issuer-labels", "LabelsController_findLabelsUniques", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"Get unique labels array\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void TokensController_getTokensTest422() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/tokens", "TokensController_getTokens", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"Get Tokens By Filters\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetTokensResponseDto\"}}}}");
    }


    @Test()
    public void IssuersController_getIssuersTest665() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/issuers", "IssuersController_getIssuers", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"Get Issuers By Filters\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Issuer\"}}}}");
    }


    @Test()
    public void IssuersController_getIssuerLabelsTest601() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/issuers/{issuerId}/labels", "IssuersController_getIssuerLabels", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"Get issuer labels\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void TokensController_getByIdTest697() {
        testRequest(Method.GET, "https://isr-issuer-service.internal.{environment}.securitize.io/v1/tokens/{id}", "TokensController_getById", LoginAs.NONE, "ISR/isr-issuer-service", "{\"description\":\"Get Token By Id\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TokenDto\"}}}}");
    }




}

