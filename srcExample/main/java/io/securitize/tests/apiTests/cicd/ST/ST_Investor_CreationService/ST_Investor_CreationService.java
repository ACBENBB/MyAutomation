package io.securitize.tests.apiTests.cicd.ST.ST_Investor_CreationService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ST_Investor_CreationService extends BaseApiTest {

    @Test()
    public void OperatorsController_getOperatorKeysTest213() {
        testRequest(Method.GET, "https://investor-creation-service.internal.{environment}.securitize.io/api/v1/operators/{operatorId}", "OperatorsController_getOperatorKeys", LoginAs.NONE, "ST/investor-creation-service", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/OperatorKeysDto\"}}}}");
    }




}

