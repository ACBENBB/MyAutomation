package io.securitize.tests.apiTests.cicd.TA.TA_AccreditationGw;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class TA_AccreditationGw extends BaseApiTest {

    @Test()
    public void HealthController_checkTest643() {
        testRequest(Method.GET, "https://accreditationapi.{environment}.securitize.io/health", "HealthController_check", LoginAs.SECURITIZE_ID, "TA/accreditation-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void FormController_getFormTest273() {
        testRequest(Method.GET, "https://accreditationapi.{environment}.securitize.io/api/v1/form", "FormController_getForm", LoginAs.SECURITIZE_ID, "TA/accreditation-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void AttachmentsController_getVerificationLetterTest355() {
        testRequest(Method.GET, "https://accreditationapi.{environment}.securitize.io/api/v1/attachments/verification-letter", "AttachmentsController_getVerificationLetter", LoginAs.SECURITIZE_ID, "TA/accreditation-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void AttachmentsController_getAttachmentTest78() {
        testRequest(Method.GET, "https://accreditationapi.{environment}.securitize.io/api/v1/attachments", "AttachmentsController_getAttachment", LoginAs.SECURITIZE_ID, "TA/accreditation-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void AccreditationsController_getAccreditationsTest696() {
        testRequest(Method.GET, "https://accreditationapi.{environment}.securitize.io/api/v1/accreditations", "AccreditationsController_getAccreditations", LoginAs.SECURITIZE_ID, "TA/accreditation-gw", "{\"description\":\"\"}");
    }

}