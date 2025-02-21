package io.securitize.tests.apiTests.cicd.CA.CA_CaConfigApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class CA_ConfigService extends BaseApiTest {

    @Test()
    public void HealthController_checkTest916() {
        testRequest(Method.GET, "https://ca-config-api.internal.{environment}.securitize.io/api/health", "HealthController_check", LoginAs.NONE, "CA/ca-config-api", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void ConfigController_getInfoTest963() {
        testRequest(Method.GET, "https://ca-config-api.internal.{environment}.securitize.io/v1/info", "ConfigController_getInfo", LoginAs.NONE, "CA/ca-config-api", "{\"description\":\"Get all info\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetConfigDto\"}}}}");
    }
}

