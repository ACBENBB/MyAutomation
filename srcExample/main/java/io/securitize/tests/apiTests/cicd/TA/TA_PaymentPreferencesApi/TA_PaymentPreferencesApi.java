package io.securitize.tests.apiTests.cicd.TA.TA_PaymentPreferencesApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class TA_PaymentPreferencesApi extends BaseApiTest {

    @Test()
    public void TokenPreferencesController_getTokenPreferencesTest627() {
        testRequest(Method.GET, "https://payment-preferences-api.internal.{environment}.securitize.io/api/v1/token-preferences", "TokenPreferencesController_getTokenPreferences", LoginAs.SECURITIZE_ID, "TA/payment-preferences-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/TokenPreferenceResponseDto\"}}}}}");
    }


    @Test()
    public void HealthController_checkTest935() {
        testRequest(Method.GET, "https://payment-preferences-api.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.SECURITIZE_ID, "TA/payment-preferences-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void PaymentPreferencesController_getPaymentPreferencesTest451() {
        testRequest(Method.GET, "https://payment-preferences-api.internal.{environment}.securitize.io/api/v1/payment-preferences", "PaymentPreferencesController_getPaymentPreferences", LoginAs.SECURITIZE_ID, "TA/payment-preferences-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/PaymentPreferenceResponseDto\"}}}}}");
    }


    @Test()
    public void PaymentPreferencesController_getDistributionsPayoutMethodTest695() {
        testRequest(Method.GET, "https://payment-preferences-api.internal.{environment}.securitize.io/api/v1/payment-preferences/distributions", "PaymentPreferencesController_getDistributionsPayoutMethod", LoginAs.SECURITIZE_ID, "TA/payment-preferences-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/PayoutMethodResponseDto\"}}}}");
    }




}

