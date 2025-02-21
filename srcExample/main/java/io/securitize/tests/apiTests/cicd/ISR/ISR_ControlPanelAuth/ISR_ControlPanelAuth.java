package io.securitize.tests.apiTests.cicd.ISR.ISR_ControlPanelAuth;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_ControlPanelAuth extends BaseApiTest {

    @Test()
    public void AuthInfoController_findOneTest0() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/auth-info/{externalId}", "AuthInfoController_findOne", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AuthInfoResponseDto\"}}}}");
    }


    @Test()
    public void TwoFactorAuthController_getTest389() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/two-factor-auth", "TwoFactorAuthController_get", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TwoFactorAuthResponseDto\"}}}}");
    }


    @Test()
    public void OrganizationsController_getOorganizationsTest103() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/organizations", "OrganizationsController_getOorganizations", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\"}");
    }


    @Test()
    public void CurrentOperatorController_getOperatorByIdTest452() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/current-operator/{currentOperatorId}/operators/{operatorId}", "CurrentOperatorController_getOperatorById", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void OperatorsController_getOperatorsTest79() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/operators", "OperatorsController_getOperators", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void CurrentOperatorController_getOperatorsTest82() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/current-operator/{currentOperatorId}/operators", "CurrentOperatorController_getOperators", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\"}");
    }


    @Test()
    public void OrganizationsController_getOrganizationByIdTest116() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/organizations/{organizationId}", "OrganizationsController_getOrganizationById", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void OauthController_validateClientConfigTest552() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/oauth/validate-client-config", "OauthController_validateClientConfig", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void HealthController_getHealthCheckTest568() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/health", "HealthController_getHealthCheck", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\"}");
    }


    @Test()
    public void AuthInfoController_findTest291() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/auth-info", "AuthInfoController_find", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/AuthInfoResponseDto\"}}}}}");
    }


    @Test()
    public void CurrentOperatorController_getPublicKeyByIdTest748() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/current-operator/{currentOperatorId}/operators/{operatorId}/key", "CurrentOperatorController_getPublicKeyById", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void OperatorsController_getOperatorByIdTest82() {
        testRequest(Method.GET, "https://control-panel-auth.internal.{environment}.securitize.io/api/v2/operators/{operatorId}", "OperatorsController_getOperatorById", LoginAs.OPERATOR, "ISR/control-panel-auth", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }

}