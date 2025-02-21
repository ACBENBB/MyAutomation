package io.securitize.tests.apiTests.cicd.ISR.ISR_ControlPanelAuditService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_ControlPanelAuditService extends BaseApiTest {

    @Test()
    public void AuditLogsController_getIssuersTest934() {
        testRequest(Method.GET, "https://control-panel-audit-service.internal.{environment}.securitize.io/api/v2/audit-logs/issuers", "AuditLogsController_getIssuers", LoginAs.NONE, "ISR/control-panel-audit-service", "{\"description\":\"\"}");
    }


    @Test()
    public void HealthCheckController_checkDatabaseConnectionTest310() {
        testRequest(Method.GET, "https://control-panel-audit-service.internal.{environment}.securitize.io/api/v2/health-check", "HealthCheckController_checkDatabaseConnection", LoginAs.NONE, "ISR/control-panel-audit-service", "{\"description\":\"\"}");
    }


    @Test()
    public void AuditLogsController_getOperatorsTest359() {
        testRequest(Method.GET, "https://control-panel-audit-service.internal.{environment}.securitize.io/api/v2/audit-logs/operators", "AuditLogsController_getOperators", LoginAs.NONE, "ISR/control-panel-audit-service", "{\"description\":\"\"}");
    }


    @Test()
    public void AuditLogsIssuerController_getAuditLogsByIssuerIdTest650() {
        testRequest(Method.GET, "https://control-panel-audit-service.internal.{environment}.securitize.io/api/v2/issuers/{issuerId}/audit-logs", "AuditLogsIssuerController_getAuditLogsByIssuerId", LoginAs.NONE, "ISR/control-panel-audit-service", "{\"description\":\"\"}");
    }


    @Test()
    public void AuditLogsIssuerController_getOperatorsByIssuerIdTest710() {
        testRequest(Method.GET, "https://control-panel-audit-service.internal.{environment}.securitize.io/api/v2/issuers/{issuerId}/audit-logs/operators", "AuditLogsIssuerController_getOperatorsByIssuerId", LoginAs.NONE, "ISR/control-panel-audit-service", "{\"description\":\"\"}");
    }


    @Test()
    public void AuditLogsController_getTokensTest801() {
        testRequest(Method.GET, "https://control-panel-audit-service.internal.{environment}.securitize.io/api/v2/audit-logs/tokens", "AuditLogsController_getTokens", LoginAs.NONE, "ISR/control-panel-audit-service", "{\"description\":\"\"}");
    }


    @Test()
    public void AuditLogsController_getAuditLogsTest627() {
        testRequest(Method.GET, "https://control-panel-audit-service.internal.{environment}.securitize.io/api/v2/audit-logs", "AuditLogsController_getAuditLogs", LoginAs.NONE, "ISR/control-panel-audit-service", "{\"description\":\"\"}");
    }


    @Test()
    public void AuditLogsIssuerController_getTokensByIssuerIdTest604() {
        testRequest(Method.GET, "https://control-panel-audit-service.internal.{environment}.securitize.io/api/v2/issuers/{issuerId}/audit-logs/tokens", "AuditLogsIssuerController_getTokensByIssuerId", LoginAs.NONE, "ISR/control-panel-audit-service", "{\"description\":\"\"}");
    }

}