package io.securitize.tests.apiTests.cicd.SID.SID_InvestorArchivesService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class SID_InvestorArchivesService extends BaseApiTest {

    @Test()
    public void getHealthTest828() {
        testRequest(Method.GET, "http://investor-archives-service:5020.{internalUrlToRemoteRunCicdApi}/health", "getHealth", LoginAs.NONE, "SID/investor-archives-service", "{\"$ref\":\"#/definitions/Model1\"}");
    }
}