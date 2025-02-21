package io.securitize.tests.apiTests.cicd.FT.FT_IssuerDashboardWebApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class FT_IssuerDashboardWebApi extends BaseApiTest {

   /* @Test()
    public void postApiResendqualificationemailTest62() {
        testRequest(Method.POST, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/resend-qualification-email", "postApiResendqualificationemail", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"description\":\"No Content\"}");
    }*/


    @Test()
    public void getApiIssuersettings() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/issuer-settings", "getApiIssuersettings", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiLiquidityproviders() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/liquidity-providers", "getApiLiquidityproviders", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiFaq() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/faq", "getApiFaq", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiOpportunities() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/opportunities", "getApiOpportunities", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiAuthorizationsettingsCountrycode() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/authorization-settings/{countryCode}", "getApiAuthorizationsettingsCountrycode", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    /*@Test()
    public void postApiDocusignTest90() {
        testRequest(Method.POST, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/docusign", "postApiDocusign", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"type\":\"string\"}");
    }*/


    @Test()
    public void getApiDocuments() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/documents", "getApiDocuments", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    /*@Test()
    public void postApiWalletsTest23() {
        testRequest(Method.POST, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/wallets", "postApiWallets", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"$ref\":\"#/definitions/Model31\"}");
    }*/


    @Test()
    public void getApiWallets() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/wallets", "getApiWallets", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiSession() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/session", "getApiSession", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiOpportunitiesOpportunityidDeposits() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/opportunities/{opportunityId}/deposits", "getApiOpportunitiesOpportunityidDeposits", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiInvestortokensTokenid() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/investor-tokens/{tokenId}", "getApiInvestortokensTokenid", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiOpportunitiesOpportunityidWizard() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/opportunities/{opportunityId}/wizard", "getApiOpportunitiesOpportunityidWizard", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiOpportunitiesOpportunityidAgreementdocument() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/opportunities/{opportunityId}/agreement-document", "getApiOpportunitiesOpportunityidAgreementdocument", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    /*@Test()
    public void patchApiFwApiAnyTest862() {
        testRequest(Method.PATCH, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/fw/{api}/{any*}", "patchApiFwApiAny", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"type\":\"string\"}");
    }*/


    /*@Test()
    public void postApiFwApiAnyTest66() {
        testRequest(Method.POST, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/fw/{api}/{any*}", "postApiFwApiAny", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"type\":\"string\"}");
    }*/


    @Test()
    public void getApiFwApiAny() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/fw/{api}/{any*}", "getApiFwApiAny", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    /*@Test()
    public void deleteApiFwApiAnyTest543() {
        testRequest(Method.DELETE, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/fw/{api}/{any*}", "deleteApiFwApiAny", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"type\":\"string\"}");
    }*/


    /*@Test()
    public void putApiFwApiAnyTest436() {
        testRequest(Method.PUT, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/fw/{api}/{any*}", "putApiFwApiAny", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"type\":\"string\"}");
    }*/


    @Test()
    public void getApiI18nLanguage() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/i18n/{language}", "getApiI18nLanguage", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiLogout() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/logout", "getApiLogout", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    /*@Test()
    public void postApiAuthorizeTest928() {
        testRequest(Method.POST, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/authorize", "postApiAuthorize", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"$ref\":\"#/definitions/Model27\"}");
    }*/


    @Test()
    public void getApiOpportunitiesOpportunityidBrokerdealer() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/opportunities/{opportunityId}/broker-dealer", "getApiOpportunitiesOpportunityidBrokerdealer", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    @Test()
    public void getApiPortfolio() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/portfolio", "getApiPortfolio", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    /*@Test()
    public void postApiQualificationquestionsTest217() {
        testRequest(Method.POST, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/qualification-questions", "postApiQualificationquestions", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"$ref\":\"#/definitions/Model55\"}");
    }*/


    @Test()
    public void getApiQualificationquestions() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/qualification-questions", "getApiQualificationquestions", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }


    /*@Test()
    public void patchApiOpportunitiesOpportunityidTest164() {
        testRequest(Method.PATCH, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/opportunities/{opportunityId}", "patchApiOpportunitiesOpportunityid", LoginAs.NEWINVESTOREXPERIENCE, "FT/issuer-dashboard-web-api", "{\"$ref\":\"#/definitions/Model46\"}");
    }*/


    @Test()
    public void getApiOpportunitiesOpportunityid() {
        testRequest(Method.GET, "https://qa-api-test-issuer.invest.{environment}.securitize.io/api/opportunities/{opportunityId}", "getApiOpportunitiesOpportunityid", LoginAs.NEW_INVESTOR_EXPERIENCE, "FT/issuer-dashboard-web-api", "{}");
    }
}

