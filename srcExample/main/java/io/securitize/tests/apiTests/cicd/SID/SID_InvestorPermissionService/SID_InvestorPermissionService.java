package io.securitize.tests.apiTests.cicd.SID.SID_InvestorPermissionService;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class SID_InvestorPermissionService extends BaseApiTest {

//    @Test()
//    public void addPermissionTest413() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/api/permissions", "addPermission", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful_operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void getPermissionsTest316() {
        testRequest(Method.GET, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/api/permissions", "getPermissions", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful_operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void authorizeTest694() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/authorize", "authorize", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"expiresIn\":{\"type\":\"integer\"},\"investorId\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"},\"refreshToken\":{\"type\":\"string\"}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void verifySecretTest111() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/verify-secret", "verifySecret", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void getInvestorsIssuersTest575() {
        testRequest(Method.GET, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/api/investors/{investorId}/issuers", "getInvestorsIssuers", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void createIssuerConfigurationTest780() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/", "createIssuerConfiguration", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"issuerIcon\":{\"type\":\"string\"},\"issuerName\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"secret\":{\"type\":\"string\"},\"redirectUrls\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void editPermissionTest661() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/api/permissions/{permissionId}", "editPermission", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful_operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void verifyTest714() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/verify", "verify", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void refreshTest300() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/refresh", "refresh", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"expiresIn\":{\"type\":\"integer\"},\"investorId\":{\"type\":\"string\"},\"accessToken\":{\"type\":\"string\"},\"refreshToken\":{\"type\":\"string\"}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void getAuthStatusTest772() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/auth-status", "getAuthStatus", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void editPermissionTest542() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/permissions/{permissionId}", "editPermission", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful_operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void healthCheckTest84() {
        testRequest(Method.GET, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/health", "healthCheck", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful health-check\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"string\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void verifyAccessByRouteTest694() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/verify-access-by-route", "verifyAccessByRoute", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void updateIssuerConfigurationTest931() {
//        testRequest(Method.PATCH, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}", "updateIssuerConfiguration", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"issuerIcon\":{\"type\":\"string\"},\"termsAndConditionsUrl\":{\"type\":\"string\"},\"issuerDashboardUrl\":{\"type\":\"string\"},\"issuerName\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"secret\":{\"type\":\"string\"},\"redirectUrls\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void getIssuerConfigurationTest889() {
        testRequest(Method.GET, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}", "getIssuerConfiguration", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"issuerIcon\":{\"type\":\"string\"},\"termsAndConditionsUrl\":{\"type\":\"string\"},\"issuerDashboardUrl\":{\"type\":\"string\"},\"issuerName\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"secret\":{\"type\":\"string\"},\"redirectUrls\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void verifySecretTest523() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/verify-config-secret", "verifySecret", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void addPermissionTest925() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/permissions", "addPermission", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful_operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


    @Test()
    public void getPermissionsTest463() {
        testRequest(Method.GET, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/permissions", "getPermissions", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful_operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\"},\"status\":{\"type\":\"integer\"}}}}}}");
    }


//    @Test()
//    public void createInvestorRequestTest881() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/create-request", "createInvestorRequest", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"code\":{\"format\":\"uuid\",\"type\":\"string\"},\"permissions\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"investorId\":{\"type\":\"string\"}}},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }


//    @Test()
//    public void verifyAccessTest177() {
//        testRequest(Method.POST, "http://investor-permission-service:5005.{internalUrlToRemoteRunCicdApi}/v1/oauth2/{issuerId}/verify-access", "verifyAccess", LoginAs.NONE, "SID/investor-permission-service", "{\"description\":\"successful operation\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"boolean\"},\"success\":{\"type\":\"boolean\"},\"error\":{\"nullable\":true,\"type\":\"object\"},\"status\":{\"type\":\"integer\"}}}}}}");
//    }




}

