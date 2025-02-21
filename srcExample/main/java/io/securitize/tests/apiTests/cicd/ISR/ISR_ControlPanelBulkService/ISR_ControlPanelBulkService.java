package io.securitize.tests.apiTests.cicd.ISR.ISR_ControlPanelBulkService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_ControlPanelBulkService extends BaseApiTest {

    @Test()
    public void IsrImportIssuanceController_importStatusTest899() {
        testRequest(Method.GET, "https://control-panel-bulk-service.internal.{environment}.securitize.io/api/v2/isr-import-issuance/status/{issuerId}", "IsrImportIssuanceController_importStatus", LoginAs.NONE, "ISR/control-panel-bulk-service", "{\"description\":\"\"}");
    }


    @Test()
    public void IsrImportInvestorsController_getimportInvestorsProgressTest690() {
        testRequest(Method.GET, "https://control-panel-bulk-service.internal.{environment}.securitize.io/api/v2/isr-import-investors/{issuerId}", "IsrImportInvestorsController_getimportInvestorsProgress", LoginAs.NONE, "ISR/control-panel-bulk-service", "{\"description\":\"\"}");
    }


    @Test()
    public void HealthCheckController_checkDatabaseConnectionTest745() {
        testRequest(Method.GET, "https://control-panel-bulk-service.internal.{environment}.securitize.io/health", "HealthCheckController_checkDatabaseConnection", LoginAs.NONE, "ISR/control-panel-bulk-service", "{\"description\":\"\"}");
    }


    @Test()
    public void IsrImportTransactionController_importStatusTest94() {
        testRequest(Method.GET, "https://control-panel-bulk-service.internal.{environment}.securitize.io/api/v2/isr-import-transaction/status/{issuerId}", "IsrImportTransactionController_importStatus", LoginAs.NONE, "ISR/control-panel-bulk-service", "{\"description\":\"\"}");
    }

}