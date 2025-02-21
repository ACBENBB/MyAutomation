package io.securitize.tests.apiTests.cicd.SID.SID_VerificationManagerService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class SID_VerificationManagerService extends BaseApiTest {

    @Test()
    public void getInvestorLivenessTest865() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/documents/liveness", "getInvestorLiveness", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"video\":{\"type\":\"string\"},\"contentType\":{\"type\":\"string\"}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


    @Test()
    public void handleGetRequiredTfaDocumentsTest826() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/tfa/document-types", "handleGetRequiredTfaDocuments", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"allowedDocumentTypes\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void createResetTfaRequestTest184() {
//        testRequest(Method.POST, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/tfa/requests", "createResetTfaRequest", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"documentType\":{\"type\":\"string\"},\"livenessStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"approved\",\"rejected\"]},\"taxId\":{\"type\":\"string\"},\"investorId\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"mainIdentificationNumber\":{\"type\":\"string\"},\"sumsubActionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"none\",\"verified\",\"rejected\",\"processing\"]}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void handleGetResetTfaRequestsTest580() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/tfa/requests", "handleGetResetTfaRequests", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"documentType\":{\"type\":\"string\"},\"livenessStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"approved\",\"rejected\"]},\"taxId\":{\"type\":\"string\"},\"investorId\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"mainIdentificationNumber\":{\"type\":\"string\"},\"sumsubActionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"none\",\"verified\",\"rejected\",\"processing\"]}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


    @Test()
    public void getExtendedTfaRequestSpecificTest770() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/tfa/requests/{id}/extended", "getExtendedTfaRequestSpecific", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"sumsubApiUrl\":{\"type\":\"string\"},\"documentType\":{\"type\":\"string\"},\"livenessStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"approved\",\"rejected\"]},\"taxId\":{\"type\":\"string\"},\"allowedDocumentTypes\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"investorId\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"mainIdentificationNumber\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"},\"isLivenessComplete\":{\"type\":\"boolean\"},\"sumsubActionId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"none\",\"verified\",\"rejected\",\"processing\"]}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void uploadDocumentToSumSubTest877() {
//        testRequest(Method.POST, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/sumsub-document-upload", "uploadDocumentToSumSub", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void handleEnqueueStepTest929() {
//        testRequest(Method.POST, "https://verification-dsc.{environment}.securitize.io/v1/verification/enqueue/{step}", "handleEnqueueStep", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void getInvestorsVerificationStatusTest98() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}", "getInvestorsVerificationStatus", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"get investor verification status\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"additionalProperties\":false,\"type\":\"object\",\"properties\":{\"updateDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"isManual\":{\"type\":\"boolean\"},\"investorId\":{\"type\":\"string\"},\"operatorFullName\":{\"type\":\"string\"},\"verificationErrors\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"subStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"expired\",\"documents-expired\",\"blocked\"]},\"errors\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"status\":{\"type\":\"string\",\"enum\":[\"none\",\"processing\",\"updates-required\",\"verified\",\"manual-review\",\"rejected\",\"expired\"]},\"createDate\":{\"format\":\"date-time\",\"type\":\"string\"}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void deleteInvestorRowsTest576() {
//        testRequest(Method.DELETE, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}", "deleteInvestorRows", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void changeInvestorStatusManuallyTest419() {
//        testRequest(Method.PUT, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}", "changeInvestorStatusManually", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"updated investor verification status\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void getSumsubProgressTest778() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/sumsub-progress", "getSumsubProgress", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"fileUpload\":{\"type\":\"object\",\"properties\":{\"isComplete\":{\"type\":\"boolean\"}}},\"livenss\":{\"type\":\"object\",\"properties\":{\"isComplete\":{\"type\":\"boolean\"}}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void handleSumsubWebhookTest681() {
//        testRequest(Method.POST, "https://verification-dsc.{environment}.securitize.io/v1/webhooks/sumsub", "handleSumsubWebhook", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void getInvestorsDocumentsTest820() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/documents", "getInvestorsDocuments", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\",\"items\":{\"additionalProperties\":false,\"type\":\"object\",\"properties\":{\"side\":{\"type\":\"string\",\"enum\":[\"front\",\"back\"]},\"fileName\":{\"type\":\"string\"},\"verificationStatus\":{\"type\":\"string\"},\"docType\":{\"type\":\"string\"},\"documentErrors\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"documentId\":{\"type\":\"string\"},\"_id\":{\"type\":\"string\"},\"uri\":{\"type\":\"string\"},\"fileType\":{\"type\":\"string\"},\"verificationId\":{\"type\":\"string\"},\"verificationSubStatus\":{\"type\":\"string\"},\"expirationDate\":{\"nullable\":true,\"format\":\"date-time\",\"type\":\"string\"}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void startVerificationTest426() {
//        testRequest(Method.POST, "https://verification-dsc.{environment}.securitize.io/v1/verification", "startVerification", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void getInvestorsRequestsHistoryTest751() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/requests-history", "getInvestorsRequestsHistory", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"get investor verification status\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\",\"items\":{\"additionalProperties\":true,\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date-time\",\"type\":\"string\"},\"idologyCheckId\":{\"nullable\":true,\"type\":\"string\"},\"provider\":{\"type\":\"string\",\"enum\":[\"manual\",\"sumsub\"]},\"isManual\":{\"type\":\"boolean\"},\"verificationErrors\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"applicantId\":{\"nullable\":true,\"type\":\"string\"},\"subStatus\":{\"type\":\"string\"},\"operator\":{\"nullable\":true,\"type\":\"string\"},\"status\":{\"type\":\"string\"},\"expirationDate\":{\"nullable\":true,\"format\":\"date-time\",\"type\":\"string\"}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void handleSumsubActionWebhookTest377() {
//        testRequest(Method.POST, "https://verification-dsc.{environment}.securitize.io/v1/webhooks/sumsub-action", "handleSumsubActionWebhook", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void handleSumSubSessionTest595() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/sumsub-session/{type}", "handleSumSubSession", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"sumsubApiUrl\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"},\"fileUpload\":{\"type\":\"object\",\"properties\":{\"isComplete\":{\"type\":\"boolean\"}}},\"livenss\":{\"type\":\"object\",\"properties\":{\"isComplete\":{\"type\":\"boolean\"}}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void handleDocumentExpiryMigrationTest679() {
//        testRequest(Method.POST, "https://verification-dsc.{environment}.securitize.io/v1/verification/migrate-document-expiary", "handleDocumentExpiryMigration", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void checkHealthStatusTest892() {
        testRequest(Method.GET, "https://verification-dsc.{environment}.securitize.io/health", "checkHealthStatus", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"get investor verification status\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"additionalProperties\":false,\"type\":\"object\",\"properties\":{\"health\":{\"type\":\"string\"}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void handleSubmitTfaVerificationTest975() {
//        testRequest(Method.POST, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/tfa/requests/{id}/submit", "handleSubmitTfaVerification", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void updateDocumentVerificationStatusTest9() {
//        testRequest(Method.PUT, "https://verification-dsc.{environment}.securitize.io/v1/investors/{investorId}/documents/{documentId}", "updateDocumentVerificationStatus", LoginAs.NONE, "SID/verification-manager-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }

}

