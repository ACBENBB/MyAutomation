package io.securitize.tests.apiTests.cicd.SID.SID_InvitationService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class SID_InvitationService extends BaseApiTest {

    @Test()
    public void getInvitesTest270() {
        testRequest(Method.GET, "http://securitize-id-invitation-service:3004.{internalUrlToRemoteRunCicdApi}/invites", "getInvites", LoginAs.NONE, "SID/invitation-service", "{\"$ref\":\"#/definitions/Model7\"}");
    }


    @Test()
    public void getHealthTest913() {
        testRequest(Method.GET, "http://securitize-id-invitation-service:3004.{internalUrlToRemoteRunCicdApi}/health", "getHealth", LoginAs.NONE, "SID/invitation-service", "{\"type\":\"string\"}");
    }


//    @Test()
//    public void postInviteIndividualBulkTest662() {
//        testRequest(Method.POST, "http://securitize-id-invitation-service:3004.{internalUrlToRemoteRunCicdApi}/invite/individual/bulk", "postInviteIndividualBulk", LoginAs.NONE, "SID/invitation-service", "{\"$ref\":\"#/definitions/Model11\"}");
//    }



//    @Test()
//    public void postInvitesInviteidExportTest293() {
//        testRequest(Method.POST, "http://securitize-id-invitation-service:3004.{internalUrlToRemoteRunCicdApi}/invites/{inviteId}/export", "postInvitesInviteidExport", LoginAs.NONE, "SID/invitation-service", "{\"description\":\"No Content\"}");
//    }


//    @Test()
//    public void postInviteEntityBulkTest335() {
//        testRequest(Method.POST, "http://securitize-id-invitation-service:3004.{internalUrlToRemoteRunCicdApi}/invite/entity/bulk", "postInviteEntityBulk", LoginAs.NONE, "SID/invitation-service", "{\"$ref\":\"#/definitions/Model11\"}");
//    }

}
