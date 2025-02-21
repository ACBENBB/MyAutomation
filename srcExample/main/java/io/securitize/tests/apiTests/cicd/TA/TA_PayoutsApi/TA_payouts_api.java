package io.securitize.tests.apiTests.cicd.TA.TA_PayoutsApi;

import io.securitize.infra.api.apicodegen.BaseApiTest;

public class TA_payouts_api extends BaseApiTest {
/* DEPRECATED
    @Test()
    public void AdminController_getUploadUrlTest541() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/admin/distribution/upload", "AdminController_getUploadUrl", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/PresignedUrlResponseDto\"}}}}");
    }


//    @Test()
//    public void AdminController_deleteDistributionTest877() {
//        testRequest(Method.DELETE, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/admin/distribution/{distributionId}", "AdminController_deleteDistribution", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\"}");
//    }


    @Test()
    public void DistributionController_getDistributionCsvPresignedUrlTest323() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/distribution/{issuerId}/{tokenId}/download", "DistributionController_getDistributionCsvPresignedUrl", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionDownloadResponseDto\"}}}}");
    }


    @Test()
    public void TaxFormsController_getTaxFormPresignedUrlTest905() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/{taxFormId}/download", "TaxFormsController_getTaxFormPresignedUrl", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DownloadUrlDto\"}}}}");
    }


    @Test()
    public void TaxFormsController_getW8BENEFormTest125() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/w8ben-e", "TaxFormsController_getW8BENEForm", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DownloadUrlDto\"}}}}");
    }


    @Test()
    public void TaxFormsController_getUploadUrlTest565() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/w8ben-e/{investorId}/upload", "TaxFormsController_getUploadUrl", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/PresignedUrlResponseDto\"}}}}");
    }


//    @Test()
//    public void TaxFormsController_addW9TaxFormTest225() {
//        testRequest(Method.POST, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/w9/{investorId}", "TaxFormsController_addW9TaxForm", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/W9TaxFormResponseDto\"}}}}");
//    }
//
//
//    @Test()
//    public void AdminController_addDistributionTest442() {
//        testRequest(Method.POST, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/admin/distribution", "AdminController_addDistribution", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionEntity\"}}}}");
//    }
//
//
//    @Test()
//    public void AdminController_replaceDistributionTest468() {
//        testRequest(Method.PUT, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/admin/distribution", "AdminController_replaceDistribution", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionEntity\"}}}}");
//    }
//
//
//    @Test()
//    public void PaymentPreferencesController_recipientsAccountsUpdatesTest768() {
//        testRequest(Method.POST, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/payment-preferences/webhooks/payment-rails/recipients-account", "PaymentPreferencesController_recipientsAccountsUpdates", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\"}");
//    }
//
//
//    @Test()
//    public void PaymentPreferencesController_activatePaymentPreferenceTest730() {
//        testRequest(Method.PATCH, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/payment-preferences/{investorId}", "PaymentPreferencesController_activatePaymentPreference", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/PaymentPreferenceResponseDto\"}}}}}");
//    }


    @Test()
    public void PaymentPreferencesController_getPaymentPreferencesTest85() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/payment-preferences/{investorId}", "PaymentPreferencesController_getPaymentPreferences", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/PaymentPreferenceResponseDto\"}}}}}");
    }


//    @Test()
//    public void TaxFormsController_deleteTaxFormTest289() {
//        testRequest(Method.DELETE, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/{taxFormId}", "TaxFormsController_deleteTaxForm", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\"}");
//    }
//
//
//    @Test()
//    public void TaxFormsController_addW8benTaxFormTest217() {
//        testRequest(Method.POST, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/w8ben/{investorId}", "TaxFormsController_addW8benTaxForm", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/W8benTaxFormResponseDto\"}}}}");
//    }


    @Test()
    public void AdminController_getDistributionTest945() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/admin/distribution/{issuerId}/{tokenId}", "AdminController_getDistribution", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void TaxFormsController_getDownloadLatestInvestorTaxFormTest942() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/{investorId}/latest", "TaxFormsController_getDownloadLatestInvestorTaxForm", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DownloadUrlDto\"}}}}");
    }


//    @Test()
//    public void PaymentPreferencesController_addCryptoPaymentPreferenceTest317() {
//        testRequest(Method.POST, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/payment-preferences/crypto/{investorId}", "PaymentPreferencesController_addCryptoPaymentPreference", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/CryptoPaymentPreferenceResponseDto\"}}}}");
//    }


    @Test()
    public void DistributionController_getDistributionInvestorsTest808() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/distribution/{issuerId}/{tokenId}/investors", "DistributionController_getDistributionInvestors", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionInvestorsResponseDto\"}}}}");
    }


//    @Test()
//    public void TaxFormsController_addW8beneTaxFormTest25() {
//        testRequest(Method.POST, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/w8ben-e/{investorId}", "TaxFormsController_addW8beneTaxForm", LoginAs.NONE, "TA/payouts-api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TaxFormResponseDto\"}}}}");
//    }


    @Test()
    public void DistributionController_getDistributionDataTest153() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/distribution/{issuerId}/{tokenId}", "DistributionController_getDistributionData", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DistributionDataResponseDto\"}}}}");
    }


    @Test()
    public void TaxFormsController_getInvestorTaxFormsTest901() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/{investorId}", "TaxFormsController_getInvestorTaxForms", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/TaxFormPaginationResponseDto\"}}}}");
    }


    @Test()
    public void TaxFormsController_getDownloadTaxFormLinkTest536() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/tax-forms/{investorId}/{taxFormId}", "TaxFormsController_getDownloadTaxFormLink", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/DownloadUrlDto\"}}}}");
    }


    @Test()
    public void PaymentHistoryController_getPaymentHistoryTest538() {
        testRequest(Method.GET, "http://payouts-api:8080.{internalUrlToRemoteRunCicdApi}/v1/payment-histories/{investorId}", "PaymentHistoryController_getPaymentHistory", LoginAs.NONE, "TA/TA_payouts_api", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/PaymentHistoryPaginationResponseDto\"}}}}");
    }
*/



}

