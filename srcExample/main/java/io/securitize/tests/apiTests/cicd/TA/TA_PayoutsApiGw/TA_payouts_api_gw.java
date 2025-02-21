package io.securitize.tests.apiTests.cicd.TA.TA_PayoutsApiGw;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class TA_payouts_api_gw extends BaseApiTest {

    @Test()
    public void HealthController_checkTest100() {
        testRequest(Method.GET, "https://payouts-api-gw.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.SECURITIZE_ID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
    }


    @Test()
    public void InfoController_getInvestorWalletsTest391() {
        testRequest(Method.GET, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/info/wallets", "InfoController_getInvestorWallets", LoginAs.SECURITIZE_ID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
    }


//    @Test()
//    public void TaxFormsController_addW8benTaxFormTest507() {
//        testRequest(Method.POST, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/tax-forms/w8ben", "TaxFormsController_addW8benTaxForm", LoginAs.SECURITIZEID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
//    }
//
//
//    @Test()
//    public void TaxFormsController_addW9TaxFormTest568() {
//        testRequest(Method.POST, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/tax-forms/w9", "TaxFormsController_addW9TaxForm", LoginAs.SECURITIZEID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
//    }


    @Test()
    public void PaymentHistoryController_getPaymentHistoryTest471() {
        testRequest(Method.GET, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/payment-histories", "PaymentHistoryController_getPaymentHistory", LoginAs.SECURITIZE_ID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
    }


//    @Test()
//    public void PaymentPreferencesController_addCryptoPaymentPreferenceTest174() {
//        testRequest(Method.POST, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/payment-preferences/crypto", "PaymentPreferencesController_addCryptoPaymentPreference", LoginAs.SECURITIZEID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
//    }


    @Test()
    public void TaxFormsController_getInvestorTaxFormsTest918() {
        testRequest(Method.GET, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/tax-forms", "TaxFormsController_getInvestorTaxForms", LoginAs.SECURITIZE_ID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
    }


//    @Test()
//    public void PaymentPreferencesController_activePayoutMethodPreferenceTest502() {
//        testRequest(Method.PATCH, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/payment-preferences", "PaymentPreferencesController_activePayoutMethodPreference", LoginAs.SECURITIZEID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
//    }


    @Test()
    public void InfoController_getInvestorInformationTest415() {
        testRequest(Method.GET, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/info/investor", "InfoController_getInvestorInformation", LoginAs.SECURITIZE_ID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
    }


    @Test()
    public void TaxFormsController_getUploadUrlTest255() {
        testRequest(Method.GET, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/tax-forms/w8ben-e/upload", "TaxFormsController_getUploadUrl", LoginAs.SECURITIZE_ID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
    }


//    @Test()
//    public void TaxFormsController_addW8beneTaxFormTest86() {
//        testRequest(Method.POST, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/tax-forms/w8ben-e", "TaxFormsController_addW8beneTaxForm", LoginAs.SECURITIZEID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
//    }


    @Test()
    public void TaxFormsController_getW8BENEFormTemplateTest275() {
        testRequest(Method.GET, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/tax-forms/w8ben-e", "TaxFormsController_getW8BENEFormTemplate", LoginAs.SECURITIZE_ID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
    }


    @Test()
    public void TaxFormsController_getDownloadTaxFormLinkTest891() {
        testRequest(Method.GET, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/tax-forms/{formId}", "TaxFormsController_getDownloadTaxFormLink", LoginAs.SECURITIZE_ID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
    }


//    @Test()
//    public void TaxFormsController_deleteTaxFormTest276() {
//        testRequest(Method.DELETE, "https://payouts-api-gw.internal.{environment}.securitize.io/api/v1/tax-forms/{formId}", "TaxFormsController_deleteTaxForm", LoginAs.SECURITIZEID, "TA/TA_payouts_api_gw", "{\"description\":\"\"}");
//    }




}

