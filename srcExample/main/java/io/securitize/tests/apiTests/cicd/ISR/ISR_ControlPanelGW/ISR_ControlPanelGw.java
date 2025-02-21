package io.securitize.tests.apiTests.cicd.ISR.ISR_ControlPanelGW;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_ControlPanelGw extends BaseApiTest {

    @Test()
    public void IssuerLabelsControllers_getLabelsTest526() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuer-labels", "IssuerLabelsControllers_getLabels", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"Get unique labels array\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void ImportIssuanceController_getStatusTest838() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/import-issuance/status", "ImportIssuanceController_getStatus", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void InvestorsController_getInvestorTokenGeneralInformationTest283() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{investorId}/token-general-information", "InvestorsController_getInvestorTokenGeneralInformation", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void CustodiansController_getCustodiansPerIssuerIdTest214() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/custodians", "CustodiansController_getCustodiansPerIssuerId", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"Returned custodians per issuer.\"}");
    }


    @Test()
    public void ImportTransactionController_statusTest887() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/import-transaction/status", "ImportTransactionController_status", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void PayoutsController_getHistoryPayoutsTest484() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/investors/{investorId}/payouts", "PayoutsController_getHistoryPayouts", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void ControlBookRecordsController_getControlBookRecordsTest631() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/control-book-records", "ControlBookRecordsController_getControlBookRecords", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void AffiliateHoldersController_getAffiliateHoldersTest644() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/holder-affiliate-records", "AffiliateHoldersController_getAffiliateHolders", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void InvestmentsController_getInvestmentsPerInvestorByTokenIdAndRoundIdTest966() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{investorId}/investment", "InvestmentsController_getInvestmentsPerInvestorByTokenIdAndRoundId", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetActiveInvestmentRequestsResponseDto\"}}}}}");
    }


    @Test()
    public void TokenTransactionsController_getMonthlyTransactionsLogTest983() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/token-transactions/monthly", "TokenTransactionsController_getMonthlyTransactionsLog", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void IssuerFundraiseDocumentController_getFundraiseContentTest406() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/configurations/issuer-fundraise-documents", "IssuerFundraiseDocumentController_getFundraiseContent", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"Issuer Fundraise documents returned for the issuer.\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/IssuerFundraiseDocumentResponseDto\"}}}}}");
    }


    @Test()
    public void InvestorsController_getInvestorVerificationTest238() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{userId}/verification", "InvestorsController_getInvestorVerification", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void DocumentsController_getInvestorDocumentTest240() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{investorId}/documents/{documentId}", "DocumentsController_getInvestorDocument", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void IssuerLabelsSpecificController_getLabelsForIssuerTest323() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/labels", "IssuerLabelsSpecificController_getLabelsForIssuer", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"Get issuer labels\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void DocumentsController_getDocumentsByInvestorIdTest753() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{investorId}/documents", "DocumentsController_getDocumentsByInvestorId", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void RegionsController_getRegionJurisdictionalConfigTest872() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/configurations/jurisdictions/countries/{countryCode}/regions/{regionCode}", "RegionsController_getRegionJurisdictionalConfig", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void HealthController_checkTest34() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/health", "HealthController_check", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"The Health Check is successful\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\",\"properties\":{\"details\":{\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}},\"error\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{}},\"status\":{\"type\":\"string\",\"example\":\"ok\"},\"info\":{\"nullable\":true,\"additionalProperties\":{\"additionalProperties\":{\"type\":\"string\"},\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}},\"type\":\"object\",\"example\":{\"database\":{\"status\":\"up\"}}}}}}}}");
    }


    @Test()
    public void ControlBookRecordsController_getControlBookTest42() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/control-book-records/info", "ControlBookRecordsController_getControlBook", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void TemporaryAssetsController_getS3PresignedUrlTest338() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/upload", "TemporaryAssetsController_getS3PresignedUrl", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void InvestorsController_getInvestorDetailsTest160() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{investorId}/details", "InvestorsController_getInvestorDetails", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void IssuerController_getIssuerByIssuerIdTest350() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}", "IssuerController_getIssuerByIssuerId", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void InvestorsExternalController_getIssuerTokensTest19() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/investors-external/{externalInvestorId}/token-investor", "InvestorsExternalController_getIssuerTokens", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void IssuerController_getIssuerTokensTest201() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens", "IssuerController_getIssuerTokens", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void TokenWalletsController_getTokenWallesForInvestorsTest466() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/token-wallets", "TokenWalletsController_getTokenWallesForInvestors", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void StatesController_getStateJurisdictionalConfigTest273() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/configurations/jurisdictions/countries/{countryCode}/states/{stateCode}", "StatesController_getStateJurisdictionalConfig", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void TokenTransactionsController_getTokenTransactionsTest374() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/token-transactions", "TokenTransactionsController_getTokenTransactions", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void AgreementsSpecificController_getAgreementByNameTest372() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/agreements/{agreementName}", "AgreementsSpecificController_getAgreementByName", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"Returned specifics agreements.\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetAgreementsByNameDto\"}}}}}");
    }


    @Test()
    public void SubscriptionAgreementStatusLogsController_getInvestorInvestmentRequestSubscriptionAgreementStatusLogsTest516() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}/investors/{investorId}/investment-requests/{investmentRequestId}/subscription-agreement-status-logs", "SubscriptionAgreementStatusLogsController_getInvestorInvestmentRequestSubscriptionAgreementStatusLogs", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/GetInvestmentRequestResponseDto\"}}}}");
    }


    @Test()
    public void TokenIssuancesController_getTokenIssuancesTest891() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{investorId}/token-issuances", "TokenIssuancesController_getTokenIssuances", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void MarketsController_getMarketsOverviewCardsTest371() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/markets/overview", "MarketsController_getMarketsOverviewCards", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void IssuerController_getIssuersListTest326() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers", "IssuerController_getIssuersList", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void HolderAffiliateRecordsController_getHolderAffiliateRecordsTest177() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{userId}/holder-affiliate-records", "HolderAffiliateRecordsController_getHolderAffiliateRecords", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void AgreementsController_getAgreementsTest655() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/agreements", "AgreementsController_getAgreements", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"Returned the info of the agreements.\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/GetAgreementByIdDto\"}}}}}");
    }


    @Test()
    public void TransactionHistoryController_getTransactionHistoryTest912() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/investors/{investorId}/transaction-history", "TransactionHistoryController_getTransactionHistory", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void InfoController_getInfoTest287() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/info", "InfoController_getInfo", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"Returned the info of the rounds with the respectively tokens.\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/InfoResponseDto\"}}}}}");
    }


    @Test()
    public void SnapshotsController_getSnapshotRecordsByIdTest750() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/snapshots/{snapshotId}/records", "SnapshotsController_getSnapshotRecordsById", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void TokenPreferencesController_getTokenPreferencesTest735() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{investorId}", "TokenPreferencesController_getTokenPreferences", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void DepositTransactionsController_getDepositTransactionsByTokenIdAndRoundIdTest131() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{investorId}/deposit-transactions", "DepositTransactionsController_getDepositTransactionsByTokenIdAndRoundId", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void CountriesController_getJurisdictionsConfigurationsListPerCountryTest420() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/configurations/jurisdictions/countries", "CountriesController_getJurisdictionsConfigurationsListPerCountry", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void SnapshotsController_getSnapshotByIdTest530() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/snapshots/{snapshotId}", "SnapshotsController_getSnapshotById", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }


    @Test()
    public void TokenStatusController_getIssuerTokensTest619() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/token-status", "TokenStatusController_getIssuerTokens", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void TokenWalletController_getIssuerTokensTest372() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/token-wallet/{walletId}", "TokenWalletController_getIssuerTokens", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void IssuerController_getPermissionsPerIssuerTest618() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/info/permissions", "IssuerController_getPermissionsPerIssuer", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void InvestorsController_getInvestorTokenAmountByIssuerIdAndTokenIdTest427() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors/{userId}/tokens/{tokenId}", "InvestorsController_getInvestorTokenAmountByIssuerIdAndTokenId", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void CountriesController_getJurisdictionsByCountryCodeTest934() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/configurations/jurisdictions/countries/{countryCode}", "CountriesController_getJurisdictionsByCountryCode", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void InvestorsController_getInvestorsTest565() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/investors", "InvestorsController_getInvestors", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void OpportunityController_getMarketsOpportunitiesTest101() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/markets/opportunities", "OpportunityController_getMarketsOpportunities", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void MarketsController_getMarketsOverviewInvestorsTest658() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/markets/investors", "MarketsController_getMarketsOverviewInvestors", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void FundraiseContentController_getFundraiseContentTest61() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/configurations/fundraise-content", "FundraiseContentController_getFundraiseContent", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"Fundraise content returned for the issuer.\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/FundraiseContentResponseDto\"}}}}}");
    }


    @Test()
    public void JurisdictionsController_getJurisdictionsConfigurationsTest23() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/configurations/jurisdictions", "JurisdictionsController_getJurisdictionsConfigurations", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void DepositWalletsController_getDepositWalletsTest128() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/investors/{investorId}/deposit-wallets", "DepositWalletsController_getDepositWallets", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\"}");
    }


    @Test()
    public void TokenSnapshotsController_getSnapshotsTest953() {
        testRequest(Method.GET, "https://isr-control-panel-gw.internal.{environment}.securitize.io/isr/api/v1/issuers/{issuerId}/tokens/{tokenId}/snapshots", "TokenSnapshotsController_getSnapshots", LoginAs.OPERATOR, "ISR/control-panel-gw", "{\"description\":\"\",\"content\":{\"application/json\":{\"schema\":{\"type\":\"object\"}}}}");
    }

}