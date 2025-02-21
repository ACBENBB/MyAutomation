package io.securitize.tests.apiTests.cicd.ST.ST_Hsm_PermissionService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Hsm_PermissionService extends BaseApiTest {

    @Test()
    public void OperatorsController_getOperatorKeysTest841() {
        testRequest(Method.GET, "https://hsm-permissions-service.internal.{environment}.securitize.io/api/v1/operators/{operatorId}", "OperatorsController_getOperatorKeys", LoginAs.NONE, "ST/hsm-permission-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OperatorKeysDto\"}}}}");
    }




}

