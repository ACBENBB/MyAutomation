package io.securitize.tests.apiTests.cicd.ST.ST_AttestationService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_AttestationService extends BaseApiTest {

    @Test()
    public void HealthController_checkTest41() {
        testRequest(Method.GET, "https://attestation-service.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.NONE, "ST/attestation-service", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void UsersController_attestationTest867() {
        testRequest(Method.GET, "https://attestation-service.internal.{environment}.securitize.io/api/v1/partners/{partnerId}/users/{securitizeId}/attestation", "UsersController_attestation", LoginAs.NONE, "ST/attestation-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AttestationDto\"}}}}");
    }


    @Test()
    public void UsersController_getPartnerTest434() {
        testRequest(Method.GET, "https://attestation-service.internal.{environment}.securitize.io/api/v1/partners/{partnerId}", "UsersController_getPartner", LoginAs.NONE, "ST/attestation-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/PartnerDto\"}}}}");
    }




}

