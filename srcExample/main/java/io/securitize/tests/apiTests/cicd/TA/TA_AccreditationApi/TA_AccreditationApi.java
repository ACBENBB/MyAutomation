package io.securitize.tests.apiTests.cicd.TA.TA_AccreditationApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class TA_AccreditationApi extends BaseApiTest {

    @Test()
    public void AccreditationsController_getAccreditationByIdTest352() {
        testRequest(Method.GET, "https://accreditation-api.internal.{environment}.securitize.io/api/v1/accreditations/{accreditationId}", "AccreditationsController_getAccreditationById", LoginAs.OPERATOR, "TA/accreditation-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AccreditationDto\"}}}}");
    }


    @Test()
    public void HealthController_checkTest806() {
        testRequest(Method.GET, "https://accreditation-api.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.OPERATOR, "TA/accreditation-api", "{\"description\":\"\"}");
    }


    @Test()
    public void AccreditationsController_getAccreditationAttorneyLetterTest28() {
        testRequest(Method.GET, "https://accreditation-api.internal.{environment}.securitize.io/api/v1/accreditations/{accreditationId}/attorney-letter", "AccreditationsController_getAccreditationAttorneyLetter", LoginAs.OPERATOR, "TA/accreditation-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/AttorneyLetterUrlResponseDto\"}}}}");
    }


    @Test()
    public void AttachmentsController_getVerificationLetterTest918() {
        testRequest(Method.GET, "https://accreditation-api.internal.{environment}.securitize.io/api/v1/attachments/verification-letter", "AttachmentsController_getVerificationLetter", LoginAs.OPERATOR, "TA/accreditation-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DownloadUrlDto\"}}}}");
    }


    @Test()
    public void AttachmentsController_getAttachmentPresignedPostTest387() {
        testRequest(Method.GET, "https://accreditation-api.internal.{environment}.securitize.io/api/v1/attachments", "AttachmentsController_getAttachmentPresignedPost", LoginAs.OPERATOR, "TA/accreditation-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/PresignedPostDto\"}}}}");
    }


    @Test()
    public void AccreditationsController_getAccreditationsTest382() {
        testRequest(Method.GET, "https://accreditation-api.internal.{environment}.securitize.io/api/v1/accreditations", "AccreditationsController_getAccreditations", LoginAs.OPERATOR, "TA/accreditation-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void AccreditationsController_getAccreditationZipFileFileTest461() {
        testRequest(Method.GET, "https://accreditation-api.internal.{environment}.securitize.io/api/v1/accreditations/{accreditationId}/zip-file", "AccreditationsController_getAccreditationZipFileFile", LoginAs.OPERATOR, "TA/accreditation-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/ZipFileUrlResponseDto\"}}}}");
    }


    @Test()
    public void AttorneyController_getAttorneysTest550() {
        testRequest(Method.GET, "https://accreditation-api.internal.{environment}.securitize.io/api/v1/attorneys", "AttorneyController_getAttorneys", LoginAs.OPERATOR, "TA/accreditation-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/AttorneyDto\"}}}}}");
    }

}