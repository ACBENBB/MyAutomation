package io.securitize.tests.apiTests.cicd.SID.SID_WebApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.json.JSONObject;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.info;

public class SID_WebApi extends BaseApiTest {

    @BeforeClass
    private void setUpSID_WebApi() {
        getSpec(LoginAs.SECURITIZE_ID);
    }


    @Test()
    public void postLogin_SetCookieAndAuthorizationTest() {
        BaseApiTest.securitizeIdSpecCookies = null;
        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/login", "postLogin", LoginAs.SECURITIZE_ID, "SID/web-api", "{\"$ref\":\"#/definitions/Model14\"}", "code");
        String cookie = BaseApiTest.securitizeIdFirstCookie;
        info("cookie is: " + cookie);
        String authorization = BaseApiTest.securitizeIdSpecBearer;
        info("authorization is: " + authorization);

        //optional:Varargs param 'securitizeIdAuthorizeIssuerCode' equals to "code"
        String securitizeIdSpecCode = BaseApiTest.securitizeIdSpecCode;
        info("securitizeIdSpecCode is: " + securitizeIdSpecCode);
    }


    //invalidates current authorization (cookie & token)
    //uses existing authorization set by method 'postLoginTestSetAuthorizationAndCookie'
    @Test(priority = 2)
    public void getSessionTest240() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/session", "getSession", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model14\"}");
    }


    //invalidates current authorization (cookie & token)
    //uses new authorization set inside the method run ('LoginAs.SECURITIZEID' param)
    @Test(priority = 3)
    public void getLogoutTest650() {
        BaseApiTest.securitizeIdSpecCookies = null;
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/logout", "getLogout", LoginAs.SECURITIZE_ID, "SID/web-api", "{\"description\":\"No Content\"}");
    }


    @Test()
    public void postAuthorizeissuerSetCodeTest() {
        String responseAsString = testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/authorize-issuer", "postAuthorizeissuer", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model52\"}");
        JSONObject response = new JSONObject(responseAsString);
        String code = response.getString("code");
        info("code is: " + code);
    }


    @Test()
    public void getWalletsWalletAgreementTest181() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/wallets/{wallet}/agreement", "getWalletsWalletAgreement", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model49\"}");
    }


    @Test()
    public void getProfileWizardstatusTest229() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/profile/wizard-status", "getProfileWizardstatus", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model4\"}");
    }


    @Test()
    public void getIpdataTest694() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/ip-data", "getIpdata", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model9\"}");
    }


    @Test()
    public void getWalletsTest274() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/wallets", "getWallets", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model25\"}");
    }


//    @Test()
//    public void postDocumentsIdentityTest57() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/documents/identity", "postDocumentsIdentity", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model27\"}");
//    }


    @Test()
    public void getDocumentsIdentityTest521() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/documents/identity", "getDocumentsIdentity", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model27\"}");
    }


    @Test()
    public void getProfilePersonalinfoTest758() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/profile/personal-info", "getProfilePersonalinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model39\"}");
    }


//    @Test()
//    public void putProfilePersonalinfoTest874() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/profile/personal-info", "putProfilePersonalinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model98\"}");
//    }

    //    @Test()
//    public void deleteDocumentsBucketFilenameTest579() {
//        testRequest(Method.DELETE, "https://api-dsc.{environment}.securitize.io/documents/bucket/{fileName}", "deleteDocumentsBucketFilename", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }
    @Test()
    public void getWalletsrpcProviderTest844() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/wallets-rpc/{provider}", "getWalletsrpcProvider", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model48\"}");
    }


//    @Test()
//    public void putProfileBrokerdealerTest23() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/profile/broker-dealer", "putProfileBrokerdealer", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model92\"}");
//    }

//    @Test()
//    public void deleteSignerSigneridTest648() {
//        testRequest(Method.DELETE, "https://api-dsc.{environment}.securitize.io/signer/{signerId}", "deleteSignerSignerid", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model17\"}");
//    }


    @Test()
    public void getProfileEntityinfoTest198() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/profile/entity-info", "getProfileEntityinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model36\"}");
    }

//    @Test()
//    public void putProfileEntityinfoTest995() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/profile/entity-info", "putProfileEntityinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model36\"}");
//    }


    @Test()
    public void getSumsubsessionLivenessTest144() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/sumsub-session/liveness", "getSumsubsessionLiveness", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model44\"}");
    }

//    @Test()
//    public void patchSuitabilityTest147() {
//        testRequest(Method.PATCH, "https://api-dsc.{environment}.securitize.io/suitability", "patchSuitability", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model20\"}");
//    }


    @Test()
    public void getSuitabilityTest343() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/suitability", "getSuitability", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model20\"}");
    }


    @Test()
    public void getProfileAddressinfoTest449() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/profile/address-info", "getProfileAddressinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model34\"}");
    }


//    @Test()
//    public void putProfileAddressinfoTest690() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/profile/address-info", "putProfileAddressinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model34\"}");
//    }


    @Test()
    public void getSignersDocumentsTest709() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/signers/documents", "getSignersDocuments", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
    }


//    @Test()
//    public void deleteDocumentsDocumentidTest602() {
//        testRequest(Method.DELETE, "https://api-dsc.{environment}.securitize.io/documents/{documentId}", "deleteDocumentsDocumentid", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }


    @Test()
    public void getSumsubsessionFileuploadTest143() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/sumsub-session/file-upload", "getSumsubsessionFileupload", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model44\"}");
    }


    @Test()
    public void getProfileTwofactorseedTest431() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/profile/two-factor-seed", "getProfileTwofactorseed", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model40\"}");
    }


//    @Test()
//    public void patchWalletsWalletidTest463() {
//        testRequest(Method.PATCH, "https://api-dsc.{environment}.securitize.io/wallets/{walletId}", "patchWalletsWalletid", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model25\"}");
//    }


//    @Test()
//    public void postUserAuthorizedinvestorsTest836() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/user/authorized-investors", "postUserAuthorizedinvestors", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


    @Test()
    public void getUserAuthorizedinvestorsTest275() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/user/authorized-investors", "getUserAuthorizedinvestors", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model46\"}");
    }

//    @Test()
//    public void deleteUserAuthorizedinvestorsTest205() {
//        testRequest(Method.DELETE, "https://api-dsc.{environment}.securitize.io/user/authorized-investors", "deleteUserAuthorizedinvestors", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void postProfileVerifypasswordTest316() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/profile/verify-password", "postProfileVerifypassword", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void postProfileChangepasswordTest282() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/profile/change-password", "postProfileChangepassword", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


    @Test()
    public void getHealthTest831() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/health", "getHealth", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
    }


//    }
//    @Test()
//    public void postUserAuthorizedinvestorsApproveTest256() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/user/authorized-investors/approve", "postUserAuthorizedinvestorsApprove", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


    @Test()
    public void getSignersTest930() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/signers", "getSigners", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model17\"}");
    }


//    @Test()
//    public void postUserAuthorizedinvestorsApproverequestTest767() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/user/authorized-investors/approve-request", "postUserAuthorizedinvestorsApproverequest", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void patchProfileUpdateTest581() {
//        testRequest(Method.PATCH, "https://api-dsc.{environment}.securitize.io/profile/update", "patchProfileUpdate", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/user\"}");
//    }


    @Test()
    public void getOpportunitiesFiltersTest541() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/opportunities/filters", "getOpportunitiesFilters", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model31\"}");
    }

//    @Test()
//    public void postWalletsAlgorandTest940() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/wallets/algorand", "postWalletsAlgorand", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model25\"}");
//    }


//    @Test()
//    public void postProfileUserInvestorsTest702() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/profile/user/investors", "postProfileUserInvestors", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model14\"}");
//    }


//    @Test()
//    public void deleteNotificationsIdTest544() {
//        testRequest(Method.DELETE, "https://api-dsc.{environment}.securitize.io/notifications/{id}", "deleteNotificationsId", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model11\"}");
//    }


//    @Test()
//    public void postRegisterTest308() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/register", "postRegister", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }


//    @Test()
//    public void postSetinvitepasswordTest776() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/set-invite-password", "postSetinvitepassword", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model14\"}");
//    }


//    @Test()
//    public void putSignersSigneridTest575() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/signers/{signerId}", "putSignersSignerid", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model105\"}");
//    }


//    @Test()
//    public void putProfileInvestortypeTest226() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/profile/investor-type", "putProfileInvestortype", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void postTokenwalletsAuthorizeTest633() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/token-wallets/authorize", "postTokenwalletsAuthorize", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model76\"}");
//    }


    @Test()
    public void getFeatureflagsTest785() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/feature-flags", "getFeatureflags", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model4\"}");
    }


//    @Test()
//    public void postIsauthorizedTest288() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/is-authorized", "postIsauthorized", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }


//    @Test()
//    public void postTokenwalletsWithdrawTest428() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/token-wallets/withdraw", "postTokenwalletsWithdraw", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void postLoginInvestorInvestoridTest926() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/login/investor/{investorId}", "postLoginInvestorInvestorid", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void postWalletsWalletTest577() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/wallets/{wallet}", "postWalletsWallet", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model25\"}");
//    }


    @Test()
    public void getProfileLegalinfoTest741() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/profile/legal-info", "getProfileLegalinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model38\"}");
    }


//    @Test()
//    public void putProfileLegalinfoTest689() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/profile/legal-info", "putProfileLegalinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model96\"}");
//    }


//    @Test()
//    public void postProfileRunverificationTest39() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/profile/run-verification", "postProfileRunverification", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/user\"}");
//    }


//    @Test()
//    public void postResetpasswordTest510() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/reset-password", "postResetpassword", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model14\"}");
//    }


//    @Test()
//    public void postNotificationsMarkreadTest87() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/notifications/mark-read", "postNotificationsMarkread", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/verificationErrors\"}");
//    }


    @Test()
    public void getSessionListTest928() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/session/list", "getSessionList", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model43\"}");
    }


//    @Test()
//    public void deleteSessionSessionidTest92() {
//        testRequest(Method.DELETE, "https://api-dsc.{environment}.securitize.io/session/{sessionId}", "deleteSessionSessionid", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }


//    @Test()
//    public void postWalletsPolygonTest565() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/wallets/polygon", "postWalletsPolygon", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model25\"}");
//    }


    @Test()
    public void getProfileAdditionalinfoTest358() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/profile/additional-info", "getProfileAdditionalinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model33\"}");
    }


//    @Test()
//    public void putProfileAdditionalinfoTest589() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/profile/additional-info", "putProfileAdditionalinfo", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model33\"}");
//    }


//    @Test()
//    public void postProfileEnabletwofactorTest17() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/profile/enable-two-factor", "postProfileEnabletwofactor", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/user\"}");
//    }


//    @Test()
//    public void postUploadTempTest920() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/upload/temp", "postUploadTemp", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model78\"}");
//    }


//    @Test()
//    public void postSignerTest981() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/signer", "postSigner", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model17\"}");
//    }


//    @Test()
//    public void postRegisterResendemailTest733() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/register/resend-email", "postRegisterResendemail", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }


//    @Test()
//    public void postProfileArchiveTest151() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/profile/archive", "postProfileArchive", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }


    @Test()
    public void getgetProfileArchiveTest34() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/profile/archive", "getProfileArchive", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model35\"}");
    }


//    @Test()
//    public void postInvestorpaysWithdrawTest960() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/investor-pays/withdraw", "postInvestorpaysWithdraw", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model70\"}");
//    }


//    @Test()
//    public void putInvestorpaysWithdrawTest874() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/investor-pays/withdraw", "putInvestorpaysWithdraw", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void postRequestresetpasswordTest425() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/request-reset-password", "postRequestresetpassword", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }


//    @Test()
//    public void postImportinformationTest531() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/import-information", "postImportinformation", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
//    }


    @Test()
    public void getImportinformationTest223() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/import-information", "getImportinformation", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model5\"}");
    }


//    @Test()
//    public void postProfileDeleteaccountTest562() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/profile/delete-account", "postProfileDeleteaccount", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/user\"}");
//    }


    @Test()
    public void getPublicissuerinfoIssueridTest521() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/public-issuer-info/{issuerId}", "getPublicissuerinfoIssuerid", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model41\"}");
    }


//    @Test()
//    public void postVerifyemailTest143() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/verify-email", "postVerifyemail", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model14\"}");
//    }


//    @Test()
//    public void postProfileDisabletwofactorTest505() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/profile/disable-two-factor", "postProfileDisabletwofactor", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/user\"}");
//    }


    @Test()
    public void getI18nLanguageTest497() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/i18n/{language}", "getI18nLanguage", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model28\"}");
    }


//    @Test()
//    public void postAccreditationTest730() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/accreditation", "postAccreditation", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model1\"}");
//    }


    @Test()
    public void getAccreditationTest878() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/accreditation", "getAccreditation", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model1\"}");
    }


//    @Test()
//    public void putProfilePhonenumberTest732() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/profile/phone-number", "putProfilePhonenumber", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model101\"}");
//    }


//    @Test()
//    public void patchWalletsrpcTest791() {
//        testRequest(Method.PATCH, "https://api-dsc.{environment}.securitize.io/wallets-rpc", "patchWalletsrpc", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void postWalletsrpcTest13() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/wallets-rpc", "postWalletsrpc", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void deleteWalletsrpcTest738() {
//        testRequest(Method.DELETE, "https://api-dsc.{environment}.securitize.io/wallets-rpc", "deleteWalletsrpc", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


//    @Test()
//    public void putWalletsrpcTest441() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/wallets-rpc", "putWalletsrpc", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


    @Test()
    public void getOpportunitiesTest332() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/opportunities", "getOpportunities", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model13\"}");
    }


//    @Test()
//    public void postWalletsAvalancheTest498() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/wallets/avalanche", "postWalletsAvalanche", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model25\"}");
//    }


    @Test()
    public void logetIssuerinfoIssueridTest129() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/issuer-info/{issuerId}", "getIssuerinfoIssuerid", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model29\"}");
    }


//    @Test()
//    public void postDocumentsAddressTest450() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/documents/address", "postDocumentsAddress", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model27\"}");
//    }


    @Test()
    public void getDocumentsAddressTest827() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/documents/address", "getDocumentsAddress", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model27\"}");
    }


//    @Test()
//    public void postVerifyresetpasswordcodeTest820() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/verify-reset-password-code", "postVerifyresetpasswordcode", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model65\"}");
//    }


//    @Test()
//    public void postInvestorpaysAuthorizeTest854() {
//        testRequest(Method.POST, "https://api-dsc.{environment}.securitize.io/investor-pays/authorize", "postInvestorpaysAuthorize", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model70\"}");
//    }


//    @Test()
//    public void putInvestorpaysAuthorizeTest608() {
//        testRequest(Method.PUT, "https://api-dsc.{environment}.securitize.io/investor-pays/authorize", "putInvestorpaysAuthorize", LoginAs.NONE, "SID/web-api", "{\"type\":\"string\"}");
//    }


    @Test()
    public void getInvestmentsCashaccountTest188() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/investments/cash-account", "getInvestmentsCashaccount", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model28\"}");
    }

    @Test()
    public void getTfaflowLivenessTest544() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/tfa-flow/liveness", "getTfaflowLiveness", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model45\"}");
    }

    @Test()
    public void getTfaflowAuthenticatePingTest462() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/tfa-flow/authenticate/ping", "getTfaflowAuthenticatePing", LoginAs.NONE, "SID/web-api", "{\"description\":\"No Content\"}");
    }

    @Test()
    public void getReversesolicitationTest196() {
        testRequest(Method.GET, "https://api-dsc.{environment}.securitize.io/reverse-solicitation", "getReversesolicitation", LoginAs.NONE, "SID/web-api", "{\"$ref\":\"#/definitions/Model12\"}");
    }

}

