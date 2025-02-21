package io.securitize.tests.apiTests.cicd.SID.SID_SecIdApiService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.info;

public class SID_SecIdApiService extends BaseApiTest {

    public static String accessToken = "";

    @BeforeClass
    private void setUpSID_SecIdApiService() {
        getSpec(LoginAs.SECURITIZE_ID, "code");
    }


    @Test(priority = -1)
    public void postV1ClientidOauth2Authorize_SetAccessTokenTest() {
        // 1. Prerequisite :Set code  param,
        //    which is initialized as Varargs param 'securitizeIdAuthorizeIssuerCode' equals to "code"
        //    by method setUpSID_SecIdApiService()

        //2. Set access-token: Execute the tested method (with the code param 'securitizeIdAuthorizeIssuerCode'),
        //   get an access-token & initialize the class methods (other tests) with it
        String responseAsString2 = testRequest(Method.POST, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/oauth2/authorize", "postV1ClientidOauth2Authorize", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/authorizeResponse\"}");
        JSONObject response2 = new JSONObject(responseAsString2);
        accessToken = response2.getString("accessToken");
        info("accessToken is: " + accessToken);
    }


    @Test()
    public void
    getV1ClientidInvestorSignersSigneridDocumentsDocumentidViewTest850() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/investor/signers/{signerId}/documents/{documentId}/view", "getV1ClientidInvestorSignersSigneridDocumentsDocumentidView", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/signerDocumentView\"}");
    }


    @Test()
    public void getV1ClientidInvestorDomainWalletsTest48() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/investor/domain/wallets", "getV1ClientidInvestorDomainWallets", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/Model6\"}");
    }


    @Test()
    public void getV1ClientidTest556() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}", "getV1Clientid", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/configResponse\"}");
    }


    @Test()
    public void getV1ClientidInvestorTest168() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/investor", "getV1ClientidInvestor", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/investorResponse\"}");
    }


    @Test()
    public void getV1ClientidInvestorDocumentsTest417() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/investor/documents", "getV1ClientidInvestorDocuments", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/investorDocumentsResponse\"}");
    }


    @Test()
    public void getV1ClientidInvestorInfoTest539() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/investor/info", "getV1ClientidInvestorInfo", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/Model3\"}");
    }


    @Test()
    public void getV1ClientidConfigTest734() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/config", "getV1ClientidConfig", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/configResponse\"}");
    }


    @Test()
    public void getV1ClientidInvestorSignersTest561() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/investor/signers", "getV1ClientidInvestorSigners", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/investorSigners\"}");
    }


    @Test()
    public void getV1ClientidInvestorDocumentsDocumentidViewTest602() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/investor/documents/{documentId}/view", "getV1ClientidInvestorDocumentsDocumentidView", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/investorDocumentView\"}");
    }


    @Test()
    public void getV1ClientidInvestorVerificationTest7() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v1/{clientId}/investor/verification", "getV1ClientidInvestorVerification", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/investorDocument\"}");
    }

    @Test()
    public void getV2ClientidInvestorVerificationTest834() {
        testRequest(Method.GET, "https://sec-id-api.{environment}.securitize.io/v2/{clientId}/investor/verification", "getV2ClientidInvestorVerification", LoginAs.NONE, "SID/sec-id-api-service", "{\"$ref\":\"#/definitions/investorVerificationV2\"}");
    }

}

