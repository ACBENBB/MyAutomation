package io.securitize.tests.apiTests.cicd.ISR.ISR_ControlPanelMainApi;

import io.restassured.http.Method;
import io.securitize.infra.api.apicodegen.BaseApiTest;
import io.securitize.infra.api.apicodegen.LoginAs;
import org.testng.annotations.Test;

public class ISR_MainApi extends BaseApiTest {

    @Test()
    public void getBlockchainTransactionByExternalTxIdTest315() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/blockchain-transactions-by-external-id/{externalTxId}", "getBlockchainTransactionByExternalTxId", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"processed\":{\"type\":\"boolean\"},\"txId\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"},\"transaction\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"signed\",\"sent\",\"success\",\"failure\"]}}}");
    }


    @Test()
    public void getTokenDocumentsTest929() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/documents", "getTokenDocuments", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"tokenId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"title\":{\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"pdf\",\"doc\",\"ppt\",\"url\",\"other\"]},\"category\":{\"type\":\"string\",\"enum\":[\"basic\",\"investor-only\"]},\"url\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getDashboardOnboardingInformationTest77() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/dashboard/onboarding", "getDashboardOnboardingInformation", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"authorizedData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date\",\"type\":\"string\"},\"value\":{\"type\":\"number\"}}}},\"registeredData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date\",\"type\":\"string\"},\"value\":{\"type\":\"number\"}}}},\"totalRegistered\":{\"type\":\"number\"},\"countryDistribution\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"quantity\":{\"type\":\"number\"},\"countryCode\":{\"type\":\"string\"}}}},\"totalFunded\":{\"type\":\"number\"},\"totalPledged\":{\"type\":\"number\"}}}");
    }


    @Test()
    public void getBlockchainTransactionsTest524() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v3/issuers/{issuerId}/tokens/{tokenId}/blockchain-transactions", "getBlockchainTransactions", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date-time\",\"type\":\"number\"},\"tokenWalletId\":{\"type\":\"string\"},\"tokenAmount\":{\"type\":\"number\"},\"tokenDRSId\":{\"type\":\"number\"},\"investorId\":{\"type\":\"number\"},\"description\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"InvestorLock\",\"LockTokens\",\"BurnTokens\",\"BurnTBETokens\",\"UpdateRole\",\"IssueTokens\",\"TransferTBE\",\"ReallocateTokens\",\"InternalTBETransfer\",\"IssueTokensToTBE\",\"ReleaseLock\",\"HoldTrading\",\"RemoveWallet\",\"RegisterWallet\",\"ReleaseTrading\",\"RemoveInvestor\",\"UpdateComplianceRules\",\"UpdateCountryComplianceStatus\",\"PierTBETransfer\"]},\"externalTxId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"signed\",\"sent\",\"success\",\"failure\"]}}}");
    }


    @Test()
    public void getBankAccountByIdTest163() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/bank-accounts/{bankAccountId}", "getBankAccountById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"instructions\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}}");
    }


    @Test()
    public void resumeBlockchainEventsTest73() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system/resume-blockchain-events", "resumeBlockchainEvents", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"status\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getLogsTest571() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/logs", "getLogs", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"amount\":{\"format\":\"double\",\"type\":\"number\"},\"investor\":{\"type\":\"object\",\"properties\":{\"firstName\":{\"default\":\"\",\"type\":\"string\"},\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"id\":{\"type\":\"number\"}}},\"registrationSource\":{\"type\":[\"string\",\"null\"]},\"currency\":{\"type\":\"object\",\"properties\":{\"identifier\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"new-investor-registered\",\"new-investor-from-rfe\",\"funds-received\",\"subscription-signed\",\"new-signature-required\",\"wallet-registered\",\"investor-requiring-manual-review\"]}}}");
    }


    @Test()
    public void getLinksTest508() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/links", "getLinks", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"linkedUserId\":{\"type\":\"number\"},\"linkType\":{\"type\":\"string\",\"enum\":[\"fbo\",\"main-investor\"]},\"info\":{\"type\":\"object\",\"properties\":{\"custodianId\":{\"type\":\"number\"}}}}}}}}");
    }


    @Test()
    public void getConfigurationVariableByIdTest270() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configuration-variables/{configurationId}", "getConfigurationVariableById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"access\":{\"type\":\"string\",\"enum\":[\"public\",\"private\"]},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"section\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"value\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getJurisdictionsPerTokenConfigurationsListPerCountryTest304() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/configurations/jurisdictions-per-token/countries", "getJurisdictionsPerTokenConfigurationsListPerCountry", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"reverseSolicitation\":{\"default\":false,\"type\":\"boolean\"},\"disclaimerType\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\",\"none\"]},\"solicitation\":{\"default\":true,\"type\":\"boolean\"},\"additionalAccreditation\":{\"type\":\"boolean\"},\"stateAndRegionName\":{\"type\":[\"string\",\"null\"]},\"allowSkipAccreditation\":{\"type\":\"boolean\"},\"qualificationName\":{\"type\":[\"string\",\"null\"]},\"qualificationRequired\":{\"type\":\"boolean\"},\"stateAndRegionCode\":{\"type\":[\"string\",\"null\"]},\"qualificationType\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\"]},\"accreditationText\":{\"type\":\"string\"},\"disclaimerName\":{\"type\":[\"string\",\"null\"]},\"countryCode\":{\"format\":\"countryCode\",\"type\":\"string\"},\"areaType\":{\"type\":\"string\",\"enum\":[\"country\",\"state\",\"region\"]},\"isTokenSpecific\":{\"type\":\"boolean\"},\"countryName\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getAlertsTest651() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/alerts", "getAlerts", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"count\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"pending-manual-review-for-kyc\",\"transactions-pending-sign\",\"pending-approval-to-register-a-wallet\"]}}}}");
    }


    @Test()
    public void getTextsTest428() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/texts", "getTexts", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"issuerId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"system\",\"content\"]},\"value\":{\"type\":[\"string\",\"null\"]},\"updatedAt\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getTokenIssuancesTest216() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/token-issuances/", "getTokenIssuances", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issueAmount\":{\"type\":\"string\"},\"executionDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"investmentRoundId\":{\"type\":\"number\"},\"description\":{\"type\":\"string\"},\"canDelete\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"source\":{\"type\":\"string\",\"enum\":[\"automated\",\"manual\"]},\"issuanceTime\":{\"format\":\"date-time\",\"type\":\"string\"},\"creationDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"processing\",\"success\",\"failure\"]},\"target\":{\"type\":\"string\",\"enum\":[\"wallet\",\"treasury\"]}}}}");
    }


    @Test()
    public void getHolderAffiliateRecordByIdTest426() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/holder-affiliate-records/{holderAffiliateRecordId}", "getHolderAffiliateRecordById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"tokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"comment\":{\"type\":\"string\"},\"documentId\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"},\"userId\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getHealthCheckTest600() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/health", "getHealthCheck", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getLegalSignerByIdTest572() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/legal-signers/{legalSignerId}", "getLegalSignerById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"required\":[\"signerType\",\"email\",\"documents\"],\"properties\":{\"lastName\":{\"type\":\"string\"},\"zipCode\":{\"type\":\"string\"},\"birthdate\":{\"type\":\"string\"},\"city\":{\"type\":\"string\"},\"streetNumber\":{\"type\":\"string\"},\"documents\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"fileName\":{\"type\":\"string\"},\"side\":{\"type\":\"string\"},\"docType\":{\"type\":\"string\",\"enum\":[\"passport\",\"driver-license\",\"national-identity-card\",\"residence-permit\",\"visa\",\"passport-card\",\"work-permit\",\"state-id\",\"uk-biometric-residence-permit\",\"tax-id\",\"voter-id\",\"certificate-of-naturalisation\",\"home-office-letter\",\"immigration-status-document\",\"birth-certificate\",\"selfie\",\"other\",\"document-or-certificate-of-trust\",\"list-of-trustees\",\"certificate-of-formation\",\"partnership-agreement\",\"lp-list-and-authorised-signers-list\",\"articles-of-organization\",\"articles-of-corporation\",\"operating-agreement\",\"members-and-authorised-signers-list\",\"by-laws\",\"shareholders-list-and-authorised-signers-list\"]},\"docCategory\":{\"type\":\"string\",\"enum\":[\"signer-identification\",\"signer-entity\"]},\"fileKey\":{\"type\":\"string\"}}}},\"entityType\":{\"type\":[\"string\",\"null\"],\"enum\":[\"revocable-trust\",\"irrevocable-trust\",\"limited-general-partnership\",\"llc\",\"corporation\",\"other\",null]},\"businessName\":{\"type\":[\"string\",\"null\"]},\"ultimateBeneficialOwner\":{\"type\":\"boolean\"},\"legalName\":{\"type\":[\"string\",\"null\"]},\"firstName\":{\"type\":\"string\"},\"countryCode\":{\"format\":\"countryCode\",\"type\":\"string\"},\"street\":{\"type\":\"string\"},\"taxId\":{\"type\":[\"string\",\"null\"]},\"signerType\":{\"type\":\"string\",\"enum\":[\"individual\",\"entity\"]},\"taxCountry\":{\"type\":[\"string\",\"null\"]},\"middleName\":{\"type\":\"string\"},\"state\":{\"type\":[\"string\",\"null\"]},\"email\":{\"format\":\"email\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getInvestorsTest658() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/markets/overview/investors", "getInvestors", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\"}");
    }


    @Test()
    public void getSystemQuestionsTest258() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/questions", "getSystemQuestions", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"question\":{\"type\":\"string\"},\"answer\":{\"type\":\"string\"},\"section\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"},\"position\":{\"type\":\"number\"}}}}}}");
    }


    @Test()
    public void getDefaultCountrySettingsTest613() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/default-country-settings", "getDefaultCountrySettings", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"agreementOnEmail\":{\"type\":\"boolean\"},\"countryCode\":{\"type\":\"string\"},\"agreement1\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}},\"agreement2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}},\"id\":{\"type\":\"integer\"},\"type\":{\"type\":\"string\",\"enum\":[\"normal\",\"blocked\",\"reverse\"]}}}}}}");
    }


    @Test()
    public void getLegalSignersTest642() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/legal-signers", "getLegalSigners", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"legalName\":{\"type\":\"string\"},\"address\":{\"type\":\"object\",\"properties\":{\"zip\":{\"type\":\"string\"},\"city\":{\"type\":\"string\"},\"street\":{\"type\":\"string\"},\"countryCode\":{\"type\":\"string\"},\"houseNumber\":{\"type\":\"string\"},\"state\":{\"type\":\"string\"}}},\"signerId\":{\"type\":\"string\"},\"documents\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"side\":{\"type\":\"string\",\"enum\":[\"front\",\"back\"]},\"fileName\":{\"type\":\"string\"},\"thumbnail\":{\"type\":\"string\"},\"verificationStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"verified\",\"not-verified\"]},\"docType\":{\"type\":\"string\",\"enum\":[\"passport\",\"driving-licence\",\"national-identity-card\",\"residence-permit\",\"visa\",\"passport-card\",\"work-permit\",\"state-id\",\"uk-biometric-residence-permit\",\"tax-id\",\"voter-id\",\"certificate-of-naturalisation\",\"home-office-letter\",\"immigration-status-document\",\"birth-certificate\",\"other\"]},\"docCategory\":{\"type\":\"string\",\"enum\":[\"signer-entity\",\"signer-identification\",\"selfie\",\"address-proof\",\"identification\",\"entity\",\"other\"]},\"documentId\":{\"type\":\"string\"},\"uri\":{\"type\":\"string\"},\"fileType\":{\"type\":\"string\"},\"createDate\":{\"type\":\"string\"}}}},\"entityType\":{\"type\":\"string\"},\"taxId\":{\"type\":\"string\"},\"investorId\":{\"type\":\"string\"},\"businessName\":{\"type\":\"string\"},\"signerType\":{\"type\":\"string\",\"enum\":[\"entity\",\"individual\"]},\"individualName\":{\"type\":\"object\",\"properties\":{\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"firsName\":{\"default\":\"\",\"type\":\"string\"},\"middleName\":{\"type\":\"string\"}}},\"birthDate\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getEmailTemplatesTest443() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/email-templates", "getEmailTemplates", LoginAs.OPERATOR, "ISR/main-api", "{\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"isDefault\":{\"type\":\"boolean\"},\"allowedVariables\":{\"type\":[\"string\",\"null\"]},\"subject\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"from\":{\"format\":\"email\",\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"content\":{\"format\":\"html\",\"type\":\"string\"},\"enabled\":{\"type\":\"boolean\"},\"variableDescription\":{\"type\":[\"string\",\"null\"]}}}}}}");
    }


    @Test()
    public void getOpportunitiesOverviewDetailsTest828() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/markets/overview/opportunities", "getOpportunitiesOverviewDetails", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\"}");
    }


    @Test()
    public void getDocumentsByUserIdTest719() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/documents", "getDocumentsByUserId", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"documentFace\":{\"type\":[\"string\",\"null\"]},\"tokenId\":{\"type\":[\"string\",\"null\"]},\"documentNumber\":{\"type\":[\"string\",\"null\"]},\"imageUrl\":{\"format\":\"url\",\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"string\"},\"showToInvestor\":{\"type\":\"boolean\"},\"type\":{\"type\":\"string\"},\"documentTitle\":{\"type\":\"string\"},\"contentType\":{\"type\":\"string\",\"example\":\"image/png\"},\"thumbnailUrl\":{\"format\":\"url\",\"type\":[\"string\",\"null\"]}}}}");
    }


    @Test()
    public void getJurisdictionsConfigurationsListPerCountryTest118() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configurations/jurisdictions/countries", "getJurisdictionsConfigurationsListPerCountry", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"reverseSolicitation\":{\"default\":false,\"type\":\"boolean\"},\"disclaimerType\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\",\"none\"]},\"solicitation\":{\"default\":true,\"type\":\"boolean\"},\"additionalAccreditation\":{\"type\":\"boolean\"},\"stateAndRegionName\":{\"type\":[\"string\",\"null\"]},\"qualificationName\":{\"type\":[\"string\",\"null\"]},\"qualificationRequired\":{\"type\":\"boolean\"},\"stateAndRegionCode\":{\"type\":[\"string\",\"null\"]},\"qualificationType\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\"]},\"accreditationText\":{\"type\":\"string\"},\"disclaimerName\":{\"type\":[\"string\",\"null\"]},\"countryCode\":{\"format\":\"countryCode\",\"type\":\"string\"},\"areaType\":{\"type\":\"string\",\"enum\":[\"country\",\"state\",\"region\"]},\"countryName\":{\"type\":\"string\"},\"accreditationFirst\":{\"type\":\"boolean\"}}}}}}");
    }


    @Test()
    public void getSystemEmailTemplateByIdTest144() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/email-templates/{systemEmailTemplateId}", "getSystemEmailTemplateById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"allowedVariables\":{\"type\":\"string\"},\"subject\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"from\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"content\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"variableDescription\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getWalletConnectProjectIdTest597() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system/wallet-connect", "getWalletConnectProjectId", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"projectId\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getDefaultAgreementsTest498() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/agreements", "getDefaultAgreements", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"yesText\":{\"type\":[\"string\",\"null\"]},\"noText\":{\"type\":[\"string\",\"null\"]},\"agreementId\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"text\":{\"type\":[\"string\",\"null\"]},\"title\":{\"type\":[\"string\",\"null\"]},\"type\":{\"type\":\"string\",\"enum\":[\"yes\",\"yes_no\"]},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getIssuerDocumentsTest360() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/documents", "getIssuerDocuments", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"title\":{\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"pdf\",\"doc\",\"ppt\",\"url\",\"other\"]},\"category\":{\"type\":\"string\",\"enum\":[\"basic\",\"investor-only\"]},\"url\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getTokensTest945() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens", "getTokens", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"required\":[\"totalItems\",\"data\"],\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"name\",\"iconUrl\",\"isDeployed\"],\"properties\":{\"totalAmountPledged\":{\"type\":\"number\"},\"decimals\":{\"type\":\"number\"},\"isDeployed\":{\"type\":\"boolean\"},\"deploymentId\":{\"type\":[\"string\",\"null\"]},\"name\":{\"type\":\"string\"},\"totalAmountFunded\":{\"type\":\"number\"},\"id\":{\"format\":\"uuid\",\"type\":\"string\"},\"iconUrl\":{\"type\":[\"string\",\"null\"]},\"isDripActive\":{\"type\":\"boolean\"},\"tokenIconUrl\":{\"type\":[\"string\",\"null\"]}}}}}}");
    }


    @Test()
    public void getSnapshotByIdTest257() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/snapshots/{snapshotId}", "getSnapshotById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"type\":\"string\"},\"distributeCreated\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"distributed\",\"none\"]}}}");
    }


    @Test()
    public void getInvestorsTest587() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors", "getInvestors", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"kycStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"processing\",\"updates-required\",\"verified\",\"manual-review\",\"rejected\",\"expired\"]},\"countryCode\":{\"format\":\"countryCode\",\"type\":\"string\"},\"investorName\":{\"description\":\"In case the user is indevidual, it will be full name, in case user is entity it will be the company's name\",\"type\":\"string\",\"example\":\"Apple\"},\"securitizeIdProfileId\":{\"type\":\"string\"},\"investorType\":{\"type\":\"string\",\"enum\":[\"individual\",\"entity\"]}}}}}}");
    }


    @Test()
    public void getWalletManagersTest292() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/wallet-managers", "getWalletManagers", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"custodian\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getSecuritizeIdInvestorByIdTest474() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}", "getSecuritizeIdInvestorById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"phonePrefix\":{\"type\":\"string\"},\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"gender\":{\"type\":\"string\",\"enum\":[\"male\",\"female\"]},\"city\":{\"type\":\"string\"},\"postalCode\":{\"type\":\"string\"},\"addressAdditionalInfo\":{\"type\":\"string\"},\"language\":{\"type\":\"string\"},\"taxId1\":{\"type\":\"string\"},\"taxId2\":{\"type\":\"string\"},\"taxId3\":{\"type\":\"string\"},\"cityOfBirth\":{\"type\":\"string\"},\"streetName\":{\"type\":\"string\"},\"taxCountryCode2\":{\"type\":\"string\"},\"countryOfBirth\":{\"type\":\"string\"},\"taxCountryCode3\":{\"type\":\"string\"},\"countryCode\":{\"type\":\"string\"},\"kycComment\":{\"type\":\"string\"},\"taxCountryCode1\":{\"type\":\"string\"},\"entityDba\":{\"type\":\"string\"},\"state\":{\"type\":\"string\"},\"email\":{\"type\":\"string\"},\"birthDay\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuersData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"tokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"issuerName\":{\"type\":\"string\"},\"userId\":{\"type\":\"integer\"}}}},\"identityDocumentNumber\":{\"type\":\"string\"},\"verificationStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"processing\",\"update-required\",\"verified\",\"manual-review\",\"rejected\",\"expired\"]},\"streetNumber\":{\"type\":\"string\"},\"entityType\":{\"type\":\"string\"},\"investorId\":{\"type\":\"string\"},\"fullName\":{\"type\":\"string\"},\"creationDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"firstName\":{\"default\":\"\",\"type\":\"string\"},\"phoneNumber\":{\"type\":\"string\"},\"tfaEnabled\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"middleName\":{\"type\":[\"string\",\"null\"]},\"comment\":{\"type\":\"string\"},\"investorType\":{\"type\":\"string\",\"enum\":[\"entity\",\"individual\"]}}}");
    }


    @Test()
    public void getTokenIssuancesByInvestmentRoundIdTest678() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/token-issuances", "getTokenIssuancesByInvestmentRoundId", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\"}");
    }


    @Test()
    public void getSystemTokenWalletsTest378() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system/token-wallets", "getSystemTokenWallets", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"tokenId\",\"address\"],\"properties\":{\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"walletId\":{\"type\":\"number\"},\"address\":{\"type\":\"string\"},\"isCustodian\":{\"type\":\"boolean\"},\"tokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"isMain\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"investorId\":{\"type\":\"number\"},\"tokensHeld\":{\"type\":\"string\"},\"securitizeIdProfileId\":{\"type\":\"string\"},\"status\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getPrivacyControlOperatorsTest305() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/privacy-control", "getPrivacyControlOperators", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"externalId\":{\"type\":\"number\"},\"operatorId\":{\"type\":\"number\"},\"email\":{\"format\":\"email\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getHasTokensTest809() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{investorId}/has-tokens", "getHasTokens", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"required\":[\"hasTokens\"],\"properties\":{\"hasTokens\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getAgreementByNameTest494() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/agreements/{agreementName}", "getAgreementByName", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"yesText\":{\"type\":[\"string\",\"null\"]},\"noText\":{\"type\":[\"string\",\"null\"]},\"subtitle\":{\"type\":[\"string\",\"null\"]},\"documentUrl\":{\"type\":[\"string\",\"null\"]},\"agreementId\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"yes\",\"yes_no\"]},\"checkOptions\":{\"type\":[\"string\",\"null\"]}}}");
    }


    @Test()
    public void getBankAccountsTest352() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/bank-accounts", "getBankAccounts", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"instructions\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}}}}}");
    }


    @Test()
    public void getSecuritizeIdAlertsTest71() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/alerts", "getSecuritizeIdAlerts", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"count\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"securitize-id-manual-review\"]}}}}");
    }


    @Test()
    public void getHistoricalTokenValuesTest406() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/historical-token-values", "getHistoricalTokenValues", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"tokenValueDate\",\"tokenValue\",\"addedBy\",\"modificationDate\"],\"properties\":{\"modificationDate\":{\"type\":\"string\"},\"addedBy\":{\"type\":\"string\"},\"tokenValueDate\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"tokenValue\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getTokenStateJurisdictionalConfigTest874() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/configurations/jurisdictions-per-token/countries/{countryCode}/states/{stateCode}", "getTokenStateJurisdictionalConfig", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"qualification\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\"]},\"content\":{\"type\":\"string\"}}},\"reverseSolicitation\":{\"default\":false,\"type\":\"boolean\"},\"accreditationText\":{\"type\":\"string\"},\"solicitation\":{\"default\":true,\"type\":\"boolean\"},\"additionalAccreditation\":{\"type\":\"boolean\"},\"allowSkipAccreditation\":{\"type\":\"boolean\"},\"stateCode\":{\"type\":\"string\"},\"countryName\":{\"type\":\"string\"},\"disclaimer\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\",\"none\"]},\"content\":{\"type\":[\"string\",\"null\"]}}},\"qualificationRequired\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getCurrenciesTest510() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/currencies", "getCurrencies", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"identifier\":{\"type\":\"string\"},\"decimals\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"crypto\",\"fiat\"]}}}}}}");
    }


    @Test()
    public void getInvestmentRoundJurisdictionalExceptionsTest604() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}/jurisdictional-exceptions", "getInvestmentRoundJurisdictionalExceptions", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"countryCode\",\"exceptionName\",\"exceptionValue\",\"stateAndRegionCode\"],\"properties\":{\"countryCode\":{\"format\":\"countrCode\",\"type\":\"string\"},\"exceptionValue\":{\"type\":[\"string\",\"number\",\"boolean\"]},\"exceptionName\":{\"type\":\"string\",\"enum\":[\"min-investment-crypto\",\"min-investment-fiat\",\"docusign-id\",\"allow-skip-accreditation\",\"custodian-docusign-id\"]},\"stateAndRegionCode\":{\"type\":[\"string\",\"null\"]}}}}");
    }


    @Test()
    public void getTokenTransactionByIdTest425() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/token-transactions/{tokenTransactionId}", "getTokenTransactionById", LoginAs.OPERATOR, "ISR/main-api", "{\"description\":\"Success\"}");
    }


    @Test()
    public void getFundraiseTokenConfigurationTest102() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/configurations/fundraise", "getFundraiseTokenConfiguration", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"required\":[\"wireTransferInstructions\",\"xpubs\",\"supportedCurrencies\"],\"properties\":{\"xpubs\":{\"type\":\"object\",\"properties\":{\"ethXpub\":{\"type\":[\"string\",\"null\"]},\"btcXpub\":{\"type\":[\"string\",\"null\"]}}},\"supportedCurrencies\":{\"type\":\"array\",\"items\":{\"format\":\"currencyCode\",\"type\":\"string\"}},\"wireTransferInstructions\":{\"type\":\"string\"},\"investmentRegulationType\":{\"type\":\"string\"},\"cashAccountIdentifier\":{\"type\":[\"string\",\"null\"]},\"investmentType\":{\"type\":\"string\"},\"depositOptions\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getBlockchainTransactionByIdTest641() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/blockchain-transactions/{blockchainTransactionId}", "getBlockchainTransactionById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"processed\":{\"type\":\"boolean\"},\"txId\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"},\"transaction\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"signed\",\"sent\",\"success\",\"failure\"]}}}");
    }


    @Test()
    public void getLegalSignerByIdTest201() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/legal-signers/{legalSignerId}", "getLegalSignerById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"email\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getHistoricalTokenValueByIdTest531() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/historical-token-values/{id}", "getHistoricalTokenValueById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"modificationDate\":{\"type\":\"string\"},\"addedBy\":{\"type\":\"string\"},\"tokenValueDate\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"tokenValue\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getSystemEmailTemplateByIdTest722() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system-email-templates/{systemEmailTemplateId}", "getSystemEmailTemplateById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"allowedVariables\":{\"type\":\"string\"},\"subject\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"from\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"content\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"variableDescription\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getDefaultConfigurationByIdTest706() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/default-configurations/{configurationId}", "getDefaultConfigurationById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"access\":{\"type\":\"string\",\"enum\":[\"public\",\"private\"]},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"section\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"},\"value\":{\"type\":[\"string\",\"null\"]},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getInvestorKYCStatusByIdTest405() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/kyc", "getInvestorKYCStatusById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"accreditedDeclarationDate\":{\"format\":\"date-time\",\"type\":[\"string\",\"null\"]},\"kycStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"processing\",\"updates-required\",\"manual-review\",\"verified\",\"rejected\",\"expired\"]},\"kycStatusExpiryDate\":{\"type\":[\"string\",\"null\"]},\"kycProvider\":{\"type\":[\"string\",\"null\"],\"enum\":[\"internal\",\"securitize-id\",\"brokerdealer\",null]},\"onfidoApplicantId\":{\"type\":[\"string\",\"null\"]},\"kycStatusChangingDate\":{\"type\":\"string\"},\"accreditedStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"pending\",\"confirmed\",\"rejected\",\"no\",\"no-accepted\",\"expired\",\"updates-required\",\"not-accredited\",\"processing\"]},\"hasReport\":{\"type\":\"boolean\"},\"accreditedExpiryDate\":{\"format\":\"date-time\",\"type\":[\"string\",\"null\"]},\"failureReason\":{\"type\":[\"string\",\"null\"],\"enum\":[\"no-documents\",\"could-not-create-applicant\",\"could-not-upload-user-document\",\"could-not-create-standard-check\",\"bad-request\",\"authorization-error\",\"user-authorization-error\",\"bad-referrer\",\"expired-token\",\"account-disabled\",\"trial-limits-reached\",\"resource-not-found\",\"gone\",\"validation-error\",\"missing-billing-info\",\"missing-documents\",\"invalid-reports-names\",\"missing-id-numbers\",\"invalid-check-type-for-variant\",\"standard-check-photo-facial-sim-without-document\",\"failed-check-requirements\",\"incomplete-checks\",\"rate-limit\",\"internal-server-error\",\"not-described-reason\",null]},\"registrationSource\":{\"type\":[\"string\",\"null\"]},\"kycPerformedAt\":{\"format\":\"date-time\",\"type\":[\"string\",\"null\"]},\"kycComments\":{\"type\":[\"string\",\"null\"]}}}");
    }


    @Test()
    public void getTokenDocumentByIdTest148() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/documents/{tokenDocumentId}", "getTokenDocumentById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"title\":{\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"pdf\",\"doc\",\"ppt\",\"url\",\"other\"]},\"category\":{\"type\":\"string\",\"enum\":[\"basic\",\"investor-only\"]},\"url\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getPstInvestorByIdTest356() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/pst/investors/{pstInvestorId}", "getPstInvestorById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"string\"}");
    }


    @Test()
    public void getLegalSignersTest802() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/legal-signers", "getLegalSigners", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"taxId\":{\"type\":[\"string\",\"null\"]},\"name\":{\"type\":\"string\"},\"signerType\":{\"type\":\"string\",\"enum\":[\"individual\",\"entity\"]},\"id\":{\"type\":\"number\"}}}}}}");
    }


    @Test()
    public void getLegalSignerDocumentsTest477() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/legal-signers/{legalSignerId}/documents", "getLegalSignerDocuments", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"side\":{\"type\":\"string\",\"enum\":[\"front\",\"back\"]},\"fileName\":{\"type\":\"string\"},\"thumbnail\":{\"type\":\"string\"},\"verificationStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"verified\",\"not-verified\"]},\"docType\":{\"type\":\"string\",\"enum\":[\"passport\",\"driving-licence\",\"national-identity-card\",\"residence-permit\",\"visa\",\"passport-card\",\"work-permit\",\"state-id\",\"uk-biometric-residence-permit\",\"tax-id\",\"voter-id\",\"certificate-of-naturalisation\",\"home-office-letter\",\"immigration-status-document\",\"birth-certificate\",\"other\"]},\"docCategory\":{\"type\":\"string\",\"enum\":[\"signer-entity\",\"signer-identification\",\"selfie\",\"address-proof\",\"identification\",\"entity\",\"other\"]},\"documentId\":{\"type\":\"string\"},\"uri\":{\"type\":\"string\"},\"fileType\":{\"type\":\"string\"},\"createDate\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getIssuerRatesTest404() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/rates", "getIssuerRates", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"BTC\":{\"type\":\"number\"},\"EUR\":{\"type\":\"number\"},\"BCH\":{\"type\":\"number\"},\"ETH\":{\"type\":\"number\"}}}");
    }


    @Test()
    public void getTokenWalletByAddressTest467() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/token-wallet/{address}", "getTokenWalletByAddress", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"isRedemptionWallet\":{\"type\":\"boolean\"},\"tokensAmount\":{\"type\":\"number\"},\"user\":{\"type\":[\"object\",\"null\"],\"properties\":{\"firstName\":{\"default\":\"\",\"type\":\"string\"},\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"fullName\":{\"type\":\"string\",\"example\":\"Itai Raz\"},\"middleName\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"}}}}}");
    }


    @Test()
    public void getAffiliateHoldersTest818() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/affiliate-holders", "getAffiliateHolders", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"fullName\":{\"type\":\"string\"},\"lastChange\":{\"format\":\"date-time\",\"type\":\"string\"},\"comment\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}}}}}");
    }


    @Test()
    public void getPartnersTest254() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/partners", "getPartners", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"sourceType\":{\"type\":\"string\",\"enum\":[\"registrationSource\",\"brokerDealerGroup\"]},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getUserDocumentByIdTest633() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/documents/{userDocumentId}", "getUserDocumentById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"tokenId\":{\"type\":[\"string\",\"null\"]},\"documentNumber\":{\"type\":\"string\"},\"imageUrl\":{\"format\":\"url\",\"type\":\"string\"},\"showToInvestor\":{\"type\":\"boolean\"},\"type\":{\"type\":\"string\",\"enum\":[\"passport\",\"driver-license\",\"national-identity-card\",\"residence-permit\",\"visa\",\"passport-card\",\"work-permit\",\"state-id\",\"uk-biometric-residence-permit\",\"tax-id\",\"voter-id\",\"certificate-of-naturalisation\",\"home-office-letter\",\"immigration-status-document\",\"birth-certificate\",\"other\",\"selfie\",\"certificate-of-formation\",\"articles-of-organization\",\"operating-agreement\",\"address-proof\",\"shareholders-list-and-authorised-signers-list\",\"by-laws\",\"cash-card\",\"health-insurance-card\",\"my-number-card\",\"driver-history-certificate\",\"special-permanent-resident-certificate\",\"resident-record-card\"]},\"documentTitle\":{\"type\":\"string\"},\"contentType\":{\"type\":\"string\",\"example\":\"image/png\"}}}");
    }


    @Test()
    public void getControlBookTest747() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v3/issuers/{issuerId}/tokens/{tokenId}/control-book-records/info", "getControlBook", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"authorizedSecurities\":{\"type\":\"string\"},\"totalCbSecurities\":{\"type\":[\"string\",\"null\"]},\"totalIssuedToDRSTokens\":{\"type\":\"string\"},\"totalIssuedTokenBonus\":{\"type\":\"string\"},\"totalIssuedToBlockchain\":{\"type\":\"string\"},\"totalIssuedTokensToRedemption\":{\"type\":\"string\"},\"issuedSecurities\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getJurisdictionsByCountryCodeTest361() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configurations/jurisdictions/countries/{countryCode}", "getJurisdictionsByCountryCode", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"qualification\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\"]},\"content\":{\"type\":\"string\"}}},\"reverseSolicitation\":{\"default\":false,\"type\":\"boolean\"},\"accreditationText\":{\"type\":\"string\"},\"solicitation\":{\"default\":true,\"type\":\"boolean\"},\"additionalAccreditation\":{\"type\":\"boolean\"},\"countryName\":{\"type\":\"string\"},\"disclaimer\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\",\"none\"]},\"content\":{\"type\":[\"string\",\"null\"]}}},\"qualificationRequired\":{\"type\":\"boolean\"},\"accreditationFirst\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getInvestmentRoundByIdTest453() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}", "getInvestmentRoundById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"required\":[\"jurisdictionalExceptionsData\",\"tokenCalculatorData\"],\"properties\":{\"minInvestmentCrypto\":{\"type\":[\"number\",\"null\"]},\"investmentSubscriptionAgreement\":{\"type\":[\"string\",\"null\"]},\"subscriptionAgreementDocusignId\":{\"type\":[\"string\",\"null\"]},\"tokenCalculatorData\":{\"type\":[\"object\",\"null\"],\"properties\":{\"tiers\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"bonusPercentage\":{\"maximum\":100,\"type\":\"number\",\"minimum\":0},\"minInvestmentAmount\":{\"type\":\"number\",\"exclusiveMinimum\":true,\"minimum\":0}}}},\"tokenValue\":{\"type\":\"number\",\"minimum\":0}}},\"subscriptionAgreementCustodianDocusignId\":{\"type\":[\"string\",\"null\"]},\"investmentTermsAndConditions\":{\"type\":[\"string\",\"null\"]},\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuanceDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"jurisdictionalExceptionsData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"countryCode\",\"exceptionName\",\"exceptionValue\",\"stateAndRegionCode\"],\"properties\":{\"countryCode\":{\"format\":\"countrCode\",\"type\":\"string\"},\"exceptionValue\":{\"type\":[\"string\",\"number\",\"boolean\"]},\"stateAndRegionCode\":{\"type\":[\"string\",\"null\"]},\"exceptionName\":{\"type\":\"string\",\"enum\":[\"min-investment-crypto\",\"min-investment-fiat\",\"docusign-id\",\"custodian-docusign-id\"]}}}},\"name\":{\"type\":\"string\"},\"startsAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"json\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"isTokenCalculatorVisible\":{\"type\":\"boolean\"},\"endsAt\":{\"format\":\"date-time\",\"type\":[\"string\",\"null\"]},\"minInvestmentFiat\":{\"type\":[\"number\",\"null\"]},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"planned\",\"active\",\"done\"]}}}");
    }


    @Test()
    public void prepareSignBlockchainTransactionTest504() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/blockchain-transactions/{blockchainTransactionId}/sign", "prepareSignBlockchainTransaction", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"required\":[\"transactionData\"],\"properties\":{\"transactionData\":{\"type\":\"string\"},\"additionalData\":{\"type\":\"object\",\"properties\":{\"networkId\":{\"type\":\"number\"},\"networkType\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void getHolderAffiliateRecordsTest880() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/holder-affiliate-records", "getHolderAffiliateRecords", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"tokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"comment\":{\"type\":\"string\"},\"documentId\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"},\"userId\":{\"type\":\"integer\"},\"status\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getBrokerDealerGroupsTest921() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/broker-dealer-groups", "getBrokerDealerGroups", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"},\"issuers\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"format\":\"uuid\",\"type\":\"string\"}}}},\"enabled\":{\"type\":\"boolean\"},\"updatedAt\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getStatesAndRegionsTest551() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/info/states-and-regions", "getStatesAndRegions", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"name\",\"code\",\"countryCode\",\"type\"],\"properties\":{\"code\":{\"type\":\"string\"},\"countryCode\":{\"format\":\"enum\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"},\"type\":{\"type\":\"string\",\"enum\":[\"state\",\"region\"]}}}}");
    }


    @Test()
    public void getDocumentsByInvestorIdTest166() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/documents", "getDocumentsByInvestorId", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"side\":{\"type\":\"string\",\"enum\":[\"front\",\"back\"]},\"fileName\":{\"type\":\"string\"},\"thumbnail\":{\"format\":\"url\",\"type\":\"string\"},\"verificationStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"verified\",\"not-verified\"]},\"docType\":{\"type\":\"string\",\"enum\":[\"passport\",\"driving-licence\",\"national-identity-card\",\"residence-permit\",\"visa\",\"passport-card\",\"work-permit\",\"state-id\",\"uk-biometric-residence-permit\",\"tax-id\",\"voter-id\",\"certificate-of-naturalisation\",\"home-office-letter\",\"immigration-status-document\",\"birth-certificate\",\"selfie\",\"other\"]},\"docCategory\":{\"type\":\"string\",\"enum\":[\"signer-entity\",\"signer-identification\",\"selfie\",\"address-proof\",\"identification\",\"entity\",\"other\"]},\"documentId\":{\"type\":\"string\"},\"fileType\":{\"type\":\"string\",\"example\":\"image/png\"},\"createDate\":{\"format\":\"date-time\",\"type\":\"string\"}}}}");
    }


    @Test()
    public void getLegalSignerDocumentByIdTest29() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/legal-signers/{legalSignerId}/documents/{documentId}", "getLegalSignerDocumentById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"url\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getSystemIssuersTest765() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system/issuers", "getSystemIssuers", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"issuerId\",\"name\"],\"properties\":{\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"mainCurrency\":{\"type\":\"string\"},\"isDeleted\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getCustodiansTest244() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/custodians", "getCustodians", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"address\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"logo\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"friendlyName\":{\"type\":\"string\"},\"email\":{\"format\":\"email\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getIssuerDocumentByIdTest763() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/documents/{issuerDocumentId}", "getIssuerDocumentById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"title\":{\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"pdf\",\"doc\",\"ppt\",\"url\",\"other\"]},\"category\":{\"type\":\"string\",\"enum\":[\"basic\",\"investor-only\"]},\"url\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getDashboardFundraiseInformationTest186() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/dashboard/fundraise", "getDashboardFundraiseInformation", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"amountRaised\":{\"type\":\"number\"},\"fundData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date\",\"type\":\"string\"},\"value\":{\"type\":\"number\"}}}},\"pledgeData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date\",\"type\":\"string\"},\"value\":{\"type\":\"number\"}}}},\"amountPledged\":{\"type\":\"number\"},\"countryDistribution\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"quantity\":{\"type\":\"number\"},\"countryCode\":{\"type\":\"string\"}}}},\"status\":{\"type\":\"string\",\"enum\":[\"active\",\"inactive\"]}}}");
    }


    @Test()
    public void getMessagesTest812() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/messages", "getMessages", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"description\":\"the total number of items found\",\"type\":\"number\"},\"data\":{\"description\":\"sent-emails paged data\",\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"sentDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"subject\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"category\":{\"type\":\"string\"},\"content\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getBlockchainTransactionsTest882() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/blockchain-transactions", "getBlockchainTransactions", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date-time\",\"type\":\"number\"},\"tokenDRSId\":{\"type\":\"number\"},\"description\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"signed\",\"sent\",\"success\",\"failure\"]}}}");
    }


    @Test()
    public void getInvestorTokenAmountByIssuerIdAndTokenIdTest119() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/tokens/{tokenId}", "getInvestorTokenAmountByIssuerIdAndTokenId", LoginAs.OPERATOR, "ISR/main-api", "{\"properties\":{\"tokensTreasury\":{\"type\":[\"number\",\"null\"]},\"iconUrl\":{\"type\":[\"number\",\"null\"]},\"tokensHeld\":{\"type\":[\"number\",\"null\"]}}}");
    }


    @Test()
    public void getControlBookTest149() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/control-book-records/info", "getControlBook", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"authorizedSecurities\":{\"type\":\"string\"},\"totalCbSecurities\":{\"type\":[\"number\",\"null\"]},\"totalIssuedToDRSTokens\":{\"type\":\"number\"},\"totalIssuedTokenBonus\":{\"type\":\"number\"},\"totalIssuedToBlockchain\":{\"type\":\"number\"},\"issuedSecurities\":{\"type\":\"number\"}}}");
    }


    @Test()
    public void getDefaultCountrySettingsByIdTest397() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/default-country-settings/{defaultCountrySettingsId}", "getDefaultCountrySettingsById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"agreementOnEmail\":{\"type\":\"boolean\"},\"accreditationText\":{\"type\":\"string\"},\"countryCode\":{\"type\":\"string\"},\"agreement1\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"}}},\"agreement2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"}}},\"additionalAccreditation\":{\"type\":\"boolean\"},\"id\":{\"type\":\"integer\"},\"type\":{\"type\":\"string\",\"enum\":[\"normal\",\"blocked\",\"reverse\"]},\"qualificationRequired\":{\"type\":\"boolean\"},\"accreditationFirst\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getSystemEntitiesTest642() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system/entities", "getSystemEntities", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"investor\":{\"type\":\"object\"},\"missingEntities\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"issuer\":{\"type\":\"object\"},\"token\":{\"type\":\"object\"}}}");
    }


    @Test()
    public void getInvestorTokensTest990() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/tokens", "getInvestorTokens", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"investorId\":{\"type\":\"number\"},\"tokens\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"tokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"tokensTreasury\":{\"type\":\"number\"},\"tbeOmnibusWalletAddress\":{\"type\":\"string\"},\"totalTokens\":{\"type\":\"number\"},\"tokensHeld\":{\"type\":\"number\"},\"isAffiliate\":{\"type\":\"boolean\"},\"bonusTokens\":{\"type\":\"number\"}}}}}}}");
    }


    @Test()
    public void getRateTest876() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/rate", "getRate", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"BTC\":{\"type\":\"number\"},\"EUR\":{\"type\":\"number\"},\"BCH\":{\"type\":\"number\"},\"ETH\":{\"type\":\"number\"}}}");
    }


    @Test()
    public void getEmailTemplateByNameTest382() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/email-templates/{emailTemplatesName}", "getEmailTemplateByName", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"isDefault\":{\"type\":\"boolean\"},\"allowedVariables\":{\"type\":[\"string\",\"null\"]},\"subject\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"from\":{\"format\":\"email\",\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"content\":{\"format\":\"html\",\"type\":\"string\"},\"enabled\":{\"type\":\"boolean\"},\"variableDescription\":{\"type\":[\"string\",\"null\"]}}}");
    }


    @Test()
    public void getInvestorDocumentByIdTest265() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/documents/{documentId}", "getInvestorDocumentById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"verificationStatus\":{\"type\":\"string\",\"enum\":[\"pending\",\"verified\",\"not-verified\"]},\"url\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void monthlyTransactionsLogTest410() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/token-transactions/monthly", "monthlyTransactionsLog", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"string\"}");
    }


    @Test()
    public void getSystemEmailTemplatesTest118() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system-email-templates", "getSystemEmailTemplates", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"allowedVariables\":{\"type\":\"string\"},\"subject\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"from\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"content\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"variableDescription\":{\"type\":[\"string\",\"null\"]}}}}}}");
    }


    @Test()
    public void getLoginDataTest6() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/login-as", "getLoginData", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"required\":[\"token\",\"expiresAt\"],\"properties\":{\"expiresAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"token\":{\"format\":\"uuid\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getSystemTextByIdTest425() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/texts/{systemTextId}", "getSystemTextById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"isHtml\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"system\",\"content\"]},\"value\":{\"type\":\"string\"},\"updatedAt\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getIssuersTest811() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers", "getIssuers", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"name\",\"defaultTokenId\"],\"properties\":{\"raisingStatus\":{\"type\":\"boolean\"},\"tokensIssued\":{\"type\":\"boolean\"},\"dashboardUrl\":{\"type\":\"string\"},\"importOrigin\":{\"default\":\"manual\",\"type\":\"string\",\"enum\":[\"manual\",\"pst\"]},\"logoFullName\":{\"type\":[\"string\",\"null\"]},\"amountFunded\":{\"type\":\"number\"},\"custodians\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"name\",\"friendlyName\",\"email\"],\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"friendlyName\":{\"type\":\"string\"},\"email\":{\"format\":\"email\",\"type\":\"string\"}}}},\"isDemo\":{\"type\":\"boolean\"},\"logoSquare\":{\"type\":[\"string\",\"null\"]},\"tokenHoldersCount\":{\"type\":\"number\"},\"logoDark\":{\"type\":[\"string\",\"null\"]},\"registeredInvestors\":{\"type\":\"number\"},\"defaultTokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"logo\":{\"type\":[\"string\",\"null\"]},\"mainCurrencyIdentifier\":{\"type\":\"string\"},\"tokens\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"name\",\"iconUrl\",\"defaultValue\",\"showTbeWallet\",\"tbeOmnibusWalletAddress\"],\"properties\":{\"showTbeWallet\":{\"type\":\"boolean\"},\"defaultValue\":{\"type\":[\"number\",\"null\"]},\"name\":{\"type\":\"string\"},\"tbeOmnibusWalletAddress\":{\"type\":[\"string\",\"null\"]},\"id\":{\"format\":\"uuid\",\"type\":\"string\"},\"iconUrl\":{\"type\":[\"string\",\"null\"]}}}},\"id\":{\"format\":\"uuid\",\"type\":\"string\"},\"pendingActions\":{\"type\":\"number\"},\"amountPledged\":{\"type\":\"number\"},\"landingPageUrl\":{\"type\":[\"string\",\"null\"]}}}}}}");
    }


    @Test()
    public void getAllIssuersClientConfigsTest7() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/configurations/client-configurations", "getAllIssuersClientConfigs", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"section\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"value\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getInfoTest46() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/info", "getInfo", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"mainCurrency\":{\"type\":\"string\"},\"allowAddOwo\":{\"type\":\"boolean\"},\"brokerDealerGroupPermissions\":{\"type\":\"object\",\"required\":[\"canAccessToIssuerConfiguration\",\"canAccessToIssuerSignatures\",\"canIssueTokens\"],\"properties\":{\"canAccessToIssuerConfiguration\":{\"type\":\"boolean\"},\"canIssueTokens\":{\"type\":\"boolean\"},\"canAccessToIssuerSignatures\":{\"type\":\"boolean\"}}},\"tokens\":{\"minItems\":1,\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"name\",\"representDebt\"],\"properties\":{\"showTbeWallet\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"tbeOmnibusWalletAddress\":{\"type\":[\"string\",\"null\"]},\"id\":{\"format\":\"uuid\",\"type\":\"string\"},\"iconUrl\":{\"type\":[\"string\",\"null\"]},\"representDebt\":{\"type\":\"boolean\"}}}},\"countries\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"isDemo\":{\"type\":\"boolean\"},\"rounds\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"tokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"issuanceDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"isEvergreenRound\":{\"type\":\"boolean\"},\"status\":{\"type\":\"string\",\"enum\":[\"planned\",\"active\",\"done\"]}}}},\"currencies\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"identifier\",\"rate\",\"name\"],\"properties\":{\"identifier\":{\"type\":\"string\"},\"rate\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}}}}}");
    }


    @Test()
    public void getExternalIdsTest358() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/convert/external-ids", "getExternalIds", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"investorId\":{\"type\":\"number\"},\"externalId\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getSecuritizeIdInvestorAssetsTest125() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/assets", "getSecuritizeIdInvestorAssets", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"usersData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"dashboardUrl\":{\"type\":\"string\"},\"showTbeWallet\":{\"type\":[\"number\",\"null\"]},\"tokenId\":{\"type\":\"string\"},\"issuerLogo\":{\"type\":\"string\"},\"issuerName\":{\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"investorId\":{\"type\":\"number\"},\"tbeOmnibusWalletAddress\":{\"type\":[\"string\",\"null\"]},\"totalTokens\":{\"type\":\"number\"},\"debtValue\":{\"type\":\"number\"},\"issuerId\":{\"type\":\"string\"},\"tokenLogoUrl\":{\"type\":\"string\"},\"children\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"dashboardUrl\":{\"type\":\"string\"},\"showTbeWallet\":{\"type\":[\"number\",\"null\"]},\"tokenId\":{\"type\":\"string\"},\"issuerLogo\":{\"type\":\"string\"},\"issuerName\":{\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"investorId\":{\"type\":\"number\"},\"tbeOmnibusWalletAddress\":{\"type\":[\"string\",\"null\"]},\"totalTokens\":{\"type\":\"number\"},\"debtValue\":{\"type\":\"number\"},\"issuerId\":{\"type\":\"string\"},\"tokenLogoUrl\":{\"type\":\"string\"},\"deploymentId\":{\"type\":[\"string\",\"null\"]},\"tokensTreasury\":{\"type\":\"number\"},\"saftForTokenId\":{\"type\":[\"string\",\"null\"]},\"linkType\":{\"type\":\"string\",\"enum\":[\"fbo\",\"main-investor\"]},\"tokensHeld\":{\"type\":\"number\"},\"saftTokenId\":{\"type\":[\"string\",\"null\"]}}}},\"deploymentId\":{\"type\":[\"string\",\"null\"]},\"tokensTreasury\":{\"type\":\"number\"},\"saftForTokenId\":{\"type\":[\"string\",\"null\"]},\"linkType\":{\"type\":\"string\",\"enum\":[\"fbo\",\"main-investor\"]},\"tokensHeld\":{\"type\":\"number\"},\"saftTokenId\":{\"type\":[\"string\",\"null\"]}}}}}}");
    }


    @Test()
    public void getAffiliateByIdTest924() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/affiliates/{affiliateId}", "getAffiliateById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"issuerId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"updatedAt\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getIssuerLabelsTest692() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/labels", "getIssuerLabels", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getTokenTransactionsTest990() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/token-transactions", "getTokenTransactions", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"sellerTransactionFee\":{\"type\":[\"string\",\"null\"]},\"notes\":{\"type\":[\"string\",\"null\"]},\"modifier\":{\"type\":\"number\"},\"txId\":{\"type\":[\"integer\",\"null\"]},\"netProceedAmount\":{\"type\":[\"string\",\"null\"]},\"grossProceedAmount\":{\"type\":[\"string\",\"null\"]},\"manuallyModified\":{\"type\":\"boolean\"},\"type\":{\"type\":\"string\",\"enum\":[\"issuance\",\"reallocation\",\"transfer-ok\",\"transfer-rejected\",\"reclaim\",\"destroy\",\"freeze\",\"unfreeze\"]},\"ethTxId\":{\"type\":[\"string\",\"null\"]},\"buyerTransactionComission\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"processType\":{\"type\":\"string\",\"enum\":[\"empty\",\"routine\",\"non-routine\",\"amendment\"]},\"amountToDisplay\":{\"type\":\"string\"},\"initialTimestamp\":{\"format\":\"date-time\",\"type\":\"string\"},\"manualCostBasisAmount\":{\"type\":[\"string\",\"null\"]},\"amount\":{\"type\":\"number\"},\"manualPurchaseAmount\":{\"type\":[\"string\",\"null\"]},\"sellerTransactionCommission\":{\"type\":[\"string\",\"null\"]},\"closedTimestamp\":{\"format\":\"date-time\",\"type\":\"string\"},\"receiver\":{\"type\":[\"object\",\"null\"],\"properties\":{\"investorWalletAddress\":{\"type\":[\"string\",\"null\"]},\"investorCountry\":{\"type\":\"string\"},\"walletType\":{\"type\":\"string\",\"enum\":[\"onchain\",\"drs\",\"bonus\"]},\"investorId\":{\"type\":\"integer\"},\"walletBalanceAfterTransaction\":{\"type\":[\"number\",\"null\"]},\"investorName\":{\"type\":\"string\"},\"investorBalanceAfterTransaction\":{\"type\":[\"number\",\"null\"]},\"investorUSState\":{\"type\":[\"string\",\"null\"]}}},\"costBasisAmount\":{\"type\":[\"string\",\"null\"]},\"costBasisUpdatedAt\":{\"format\":\"date-time\",\"type\":[\"string\",\"null\"]},\"manualNetProceedAmount\":{\"type\":[\"string\",\"null\"]},\"purchaseAmount\":{\"type\":[\"string\",\"null\"]},\"buyerTransactionFee\":{\"type\":[\"string\",\"null\"]},\"pierMatchId\":{\"format\":\"uuid\",\"type\":[\"string\",\"null\"]},\"costBasisUpdatedById\":{\"type\":[\"number\",\"null\"]},\"txIdLink\":{\"type\":[\"string\",\"null\"]},\"sender\":{\"type\":[\"object\",\"null\"],\"properties\":{\"investorWalletAddress\":{\"type\":[\"string\",\"null\"]},\"investorCountry\":{\"type\":\"string\"},\"walletType\":{\"type\":\"string\",\"enum\":[\"onchain\",\"drs\",\"bonus\"]},\"investorId\":{\"type\":\"number\"},\"walletBalanceAfterTransaction\":{\"type\":[\"number\",\"null\"]},\"investorName\":{\"type\":\"string\"},\"investorBalanceAfterTransaction\":{\"type\":[\"number\",\"null\"]},\"investorUSState\":{\"type\":[\"string\",\"null\"]}}},\"costBasisUpdatedBy\":{\"type\":[\"string\",\"null\"],\"enum\":[\"operator\",\"investor\",\"api\",null]},\"receiverType\":{\"type\":\"string\",\"enum\":[\"investor\",\"issuer\",\"unknown\"]},\"senderType\":{\"type\":\"string\",\"enum\":[\"investor\",\"issuer\",\"unknown\"]},\"manualGrossProceedAmount\":{\"type\":[\"string\",\"null\"]},\"manuallyCreated\":{\"type\":\"boolean\"}}}}}}");
    }


    @Test()
    public void getSystemTokensTest935() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system/tokens", "getSystemTokens", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"tokenId\",\"name\"],\"properties\":{\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"defaultTokenValue\":{\"type\":\"number\"},\"showTbeWallet\":{\"type\":\"boolean\"},\"tokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"decimals\":{\"type\":\"number\"},\"deploymentId\":{\"type\":\"string\"},\"isInvestorPays\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"fundraiseEnabled\":{\"type\":\"boolean\"},\"tokenIcon\":{\"type\":\"string\"},\"isDripActive\":{\"type\":\"boolean\"},\"isEvergreen\":{\"type\":\"boolean\"}}}}");
    }


    @Test()
    public void getSystemEmailTemplatesTest596() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/email-templates", "getSystemEmailTemplates", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"allowedVariables\":{\"type\":\"string\"},\"subject\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"from\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"content\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"variableDescription\":{\"type\":[\"string\",\"null\"]}}}}}}");
    }


    @Test()
    public void getTokenInvestorByExternalIdTest301() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/investors-external/{userExternalId}/token-investor", "getTokenInvestorByExternalId", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"tokensAmount\":{\"type\":\"number\"},\"user\":{\"type\":\"object\",\"properties\":{\"firstName\":{\"default\":\"\",\"type\":\"string\"},\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"fullName\":{\"type\":\"string\",\"example\":\"Itai Raz\"},\"middleName\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"}}}}}");
    }


    @Test()
    public void getJurisdictionsPerTokenByCountryCodeTest126() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/configurations/jurisdictions-per-token/countries/{countryCode}", "getJurisdictionsPerTokenByCountryCode", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"qualification\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\"]},\"content\":{\"type\":\"string\"}}},\"reverseSolicitation\":{\"default\":false,\"type\":\"boolean\"},\"accreditationText\":{\"type\":\"string\"},\"solicitation\":{\"default\":true,\"type\":\"boolean\"},\"additionalAccreditation\":{\"type\":\"boolean\"},\"allowSkipAccreditation\":{\"type\":\"boolean\"},\"countryName\":{\"type\":\"string\"},\"disclaimer\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\",\"none\"]},\"content\":{\"type\":[\"string\",\"null\"]}}},\"qualificationRequired\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getSystemTextsTest139() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/texts", "getSystemTexts", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"isHtml\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"system\",\"content\"]},\"value\":{\"type\":[\"string\",\"null\"]},\"updatedAt\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getPrerenderedAddressByIdTest84() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/prerendered-addresses/{prerenderedAddressId}", "getPrerenderedAddressById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"addressSignature\":{\"type\":[\"string\",\"null\"]},\"address\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"currencyId\":{\"type\":\"number\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"derivativeId\":{\"type\":\"number\"}}}");
    }


    @Test()
    public void getMsfCsvTest795() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/msf", "getMsfCsv", LoginAs.OPERATOR, "ISR/main-api", "{\"description\":\"Success\"}");
    }


    @Test()
    public void getGeneralConfigurationsTest888() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configuration/general", "getGeneralConfigurations", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"general\":{\"type\":\"object\",\"properties\":{\"beneficialOwnerInformation\":{\"type\":\"object\",\"properties\":{\"surname\":{\"type\":[\"string\",\"null\"]},\"name\":{\"type\":[\"string\",\"null\"]},\"dateOfBirth\":{\"type\":[\"string\",\"null\"]}}},\"images\":{\"type\":\"object\",\"properties\":{\"logoDark\":{\"type\":[\"string\",\"null\"]},\"logoFullName\":{\"type\":[\"string\",\"null\"]},\"favicon\":{\"type\":\"string\"},\"logo\":{\"type\":[\"string\",\"null\"]},\"logoSquare\":{\"type\":[\"string\",\"null\"]}}},\"issuerName\":{\"type\":\"string\"},\"issuerLeiCode\":{\"minLength\":20,\"pattern\":\"^[A-Za-z0-9]*$\",\"type\":[\"string\",\"null\"],\"maxLength\":20},\"colorScheme\":{\"type\":\"object\",\"properties\":{\"actionColor\":{\"type\":\"string\"},\"primaryColor\":{\"type\":\"string\"},\"secondaryColor\":{\"type\":\"string\"}}}}},\"investorsArea\":{\"type\":\"object\",\"properties\":{\"socialLinks\":{\"properties\":{\"whatsapp\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"icon\":{\"type\":\"string\"},\"alt\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"url\":{\"type\":\"string\"}}},\"twitter\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"icon\":{\"type\":\"string\"},\"alt\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"url\":{\"type\":\"string\"}}},\"supportEmail\":{\"format\":\"email\",\"type\":\"string\"},\"facebook\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"icon\":{\"type\":\"string\"},\"alt\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"url\":{\"type\":\"string\"}}},\"telegram\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"icon\":{\"type\":\"string\"},\"alt\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"url\":{\"type\":\"string\"}}},\"medium\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"icon\":{\"type\":\"string\"},\"alt\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"url\":{\"type\":\"string\"}}}}},\"about\":{\"type\":\"object\",\"properties\":{\"background\":{\"type\":[\"string\",\"null\"]},\"Text\":{\"type\":\"string\"}}},\"wizard\":{\"type\":\"object\",\"properties\":{\"background\":{\"type\":[\"string\",\"null\"]}}},\"announcements\":{\"type\":\"object\",\"properties\":{\"background\":{\"type\":[\"string\",\"null\"]},\"subtitle\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"body\":{\"type\":\"string\"}}}}},\"landingPage\":{\"type\":\"object\",\"properties\":{\"external\":{\"type\":\"object\",\"properties\":{\"externalURL\":{\"type\":\"string\"},\"platformURL\":{\"type\":\"string\"}}},\"type\":{\"type\":\"string\",\"enum\":[\"basic\",\"external\"]},\"basic\":{\"type\":\"object\",\"properties\":{\"LoginButtonText\":{\"type\":\"string\"},\"pageMode\":{\"type\":\"string\",\"enum\":[\"coming-soon\",\"login-only\",\"login-n-registration\"]},\"comingSoon\":{\"type\":\"string\"},\"backgroundImage\":{\"type\":\"string\"},\"restrictLogin\":{\"type\":\"string\",\"enum\":[\"disabled\",\"allow-holders\",\"require-label\"]},\"limitedAccess\":{\"type\":\"object\",\"properties\":{\"password\":{\"type\":\"string\"},\"isLimitedAccess\":{\"type\":\"boolean\"},\"username\":{\"type\":\"string\"}}},\"page\":{\"type\":\"boolean\"},\"text\":{\"type\":\"string\"},\"headline\":{\"type\":\"string\"},\"registrationButtonText\":{\"type\":\"string\"}}},\"landingPageUrl\":{\"type\":\"string\"}}},\"questions\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"questionId\":{\"type\":\"number\"},\"question\":{\"type\":\"string\"},\"answer\":{\"type\":\"string\"},\"position\":{\"type\":\"number\"}}}},\"email\":{\"type\":\"object\",\"properties\":{\"investorCommunicationsEmail\":{\"format\":\"email\",\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"custom\",\"default\"]}}}}}");
    }


    @Test()
    public void getSecuritizeIdInvestorPendingAssetsTest706() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/pending-assets", "getSecuritizeIdInvestorPendingAssets", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"dashboardUrl\":{\"type\":\"string\"},\"tokenId\":{\"type\":\"string\"},\"tokenLogoUrl\":{\"type\":\"string\"},\"issuerLogo\":{\"type\":\"string\"},\"issuerName\":{\"type\":\"string\"},\"deploymentId\":{\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"investorId\":{\"type\":\"number\"},\"tokensAssigned\":{\"type\":\"number\"}}}}");
    }


    @Test()
    public void getSystemTextByIdTest38() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system-texts/{systemTextId}", "getSystemTextById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"isHtml\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"system\",\"content\"]},\"value\":{\"type\":\"string\"},\"updatedAt\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getInvestmentRoundsTest956() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/", "getInvestmentRounds", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"minInvestmentCrypto\":{\"type\":[\"number\",\"null\"]},\"investmentSubscriptionAgreement\":{\"type\":[\"string\",\"null\"]},\"subscriptionAgreementDocusignId\":{\"type\":[\"string\",\"null\"]},\"subscriptionAgreementCustodianDocusignId\":{\"type\":[\"string\",\"null\"]},\"investmentTermsAndConditions\":{\"type\":[\"string\",\"null\"]},\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuanceDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"startsAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"json\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"endsAt\":{\"format\":\"date-time\",\"type\":[\"string\",\"null\"]},\"minInvestmentFiat\":{\"type\":[\"number\",\"null\"]},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"planned\",\"active\",\"done\"]}}}}}}");
    }


    @Test()
    public void getStateJurisdictionalConfigTest814() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configurations/jurisdictions/countries/{countryCode}/states/{stateCode}", "getStateJurisdictionalConfig", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"qualification\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\"]},\"content\":{\"type\":\"string\"}}},\"reverseSolicitation\":{\"default\":false,\"type\":\"boolean\"},\"accreditationText\":{\"type\":\"string\"},\"solicitation\":{\"default\":true,\"type\":\"boolean\"},\"additionalAccreditation\":{\"type\":\"boolean\"},\"stateCode\":{\"type\":\"string\"},\"countryName\":{\"type\":\"string\"},\"disclaimer\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\",\"none\"]},\"content\":{\"type\":[\"string\",\"null\"]}}},\"qualificationRequired\":{\"type\":\"boolean\"},\"accreditationFirst\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getPrerenderedAddressesTest305() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/prerendered-addresses", "getPrerenderedAddresses", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"addressSignature\":{\"type\":[\"string\",\"null\"]},\"address\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"currencyId\":{\"type\":\"number\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"derivativeId\":{\"type\":\"number\"}}}}}}");
    }


    @Test()
    public void getS3PresignedUrlTest602() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/upload", "getS3PresignedUrl", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"fileKey\":{\"type\":\"string\"},\"fields\":{\"type\":\"object\"},\"url\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getIssuerByIdTest317() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}", "getIssuerById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"dashboardUrl\":{\"type\":\"string\"},\"defaultTokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getControlBookRecordsTest64() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/control-book-records", "getControlBookRecords", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"authorizedSecurities\":{\"type\":\"string\"},\"tokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"documentationId\":{\"type\":\"string\"},\"description\":{\"type\":\"string\"},\"authoriser\":{\"type\":\"string\"},\"operator\":{\"type\":[\"object\",\"null\"],\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"}}},\"authorizedSecuritiesForDisplay\":{\"type\":\"string\"},\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"previousAuthorizedSecuritiesForDisplay\":{\"type\":\"string\"},\"previousCbSecurities\":{\"type\":[\"number\",\"null\"]},\"previousAuthorizedSecurities\":{\"type\":[\"string\",\"null\"]},\"previousCbSecuritiesForDisplay\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"cbSecuritiesForDisplay\":{\"type\":\"string\"},\"cbSecurities\":{\"type\":[\"number\",\"null\"]}}}}}}");
    }


    @Test()
    public void getimportInvestorsProgressTest686() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/import-investor", "getimportInvestorsProgress", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\"}");
    }


    @Test()
    public void checkLabelExistsTest773() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/labels/{labelText}", "checkLabelExists", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"string\"}");
    }


    @Test()
    public void getTokenWalletsTest84() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/token-wallets", "getTokenWallets", LoginAs.OPERATOR, "ISR/main-api", "{\"additionalProperties\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"items\":{\"type\":\"object\",\"properties\":{\"owner\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"}}},\"address\":{\"format\":\"blockchainWalletAddress\",\"type\":\"string\"},\"isCustodian\":{\"type\":\"boolean\"},\"omnibusTokens\":{\"type\":\"number\"},\"walletType\":{\"type\":\"string\",\"enum\":[\"normal\",\"custodian\",\"omnibus\"]},\"isMain\":{\"type\":\"boolean\"},\"tokensLocked\":{\"type\":\"number\"},\"link\":{\"format\":\"url\",\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"creationDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"tokensPending\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"tokensHeld\":{\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"syncing\",\"ready\",\"rejected\",\"cancelled\"]}}}}}},\"type\":\"object\"}");
    }


    @Test()
    public void getTokenRegionJurisdictionalConfigTest660() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/configurations/jurisdictions-per-token/countries/{countryCode}/regions/{regionCode}", "getTokenRegionJurisdictionalConfig", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"qualification\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\"]},\"content\":{\"type\":\"string\"}}},\"regionCode\":{\"type\":\"string\"},\"reverseSolicitation\":{\"default\":false,\"type\":\"boolean\"},\"accreditationText\":{\"type\":\"string\"},\"solicitation\":{\"default\":true,\"type\":\"boolean\"},\"additionalAccreditation\":{\"type\":\"boolean\"},\"allowSkipAccreditation\":{\"type\":\"boolean\"},\"countryName\":{\"type\":\"string\"},\"disclaimer\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\",\"none\"]},\"content\":{\"type\":[\"string\",\"null\"]}}},\"qualificationRequired\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getIssuerQuestionsTest737() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/questions", "getIssuerQuestions", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"question\":{\"type\":\"string\"},\"answer\":{\"type\":\"string\"},\"section\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"position\":{\"format\":\"int32\",\"type\":\"number\"},\"isVisible\":{\"type\":\"boolean\"}}}}}}");
    }


    @Test()
    public void getDashboardGeneralInformationTest503() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/dashboard", "getDashboardGeneralInformation", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"round\":{\"type\":\"object\",\"properties\":{\"amountRaised\":{\"type\":\"number\"},\"amountPledged\":{\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"active\",\"inactive\"]}}},\"holders\":{\"type\":\"object\",\"properties\":{\"totalEUHolders\":{\"type\":\"number\"},\"totalUSHolders\":{\"type\":\"number\"},\"totalWorldHolders\":{\"type\":\"number\"},\"totalHolders\":{\"type\":\"number\"}}},\"onboarding\":{\"type\":\"object\",\"properties\":{\"totalRegistered\":{\"type\":\"number\"},\"totalFunded\":{\"type\":\"number\"},\"totalPledged\":{\"type\":\"number\"}}}}}");
    }


    @Test()
    public void getConfigurationVariablesTest92() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configuration-variables", "getConfigurationVariables", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"access\":{\"type\":\"string\",\"enum\":[\"public\",\"private\"]},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"section\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"},\"value\":{\"type\":[\"string\",\"null\"]},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getClientConfigsTest816() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configurations/client-configs", "getClientConfigs", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"section\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"value\":{\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getCurrencyByIdTest210() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/currencies/{currencyId}", "getCurrencyById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"object\",\"properties\":{\"identifier\":{\"type\":\"string\"},\"decimals\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"crypto\",\"fiat\"]}}}}}");
    }


    @Test()
    public void getPredefinedLabelsTest715() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/predefined-labels", "getPredefinedLabels", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"string\"}}");
    }


    @Test()
    public void getBrokerDealerGroupByIdTest327() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/broker-dealer-groups/{brokerDealerGroupId}", "getBrokerDealerGroupById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuersData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"canAccessToIssuerConfiguration\",\"canAccessToIssuerSignatures\",\"canIssueTokens\",\"userCanBeEditedByIssuer\"],\"properties\":{\"canAccessToIssuerConfiguration\":{\"type\":\"boolean\"},\"canIssueTokens\":{\"type\":\"boolean\"},\"userCanBeEditedByIssuer\":{\"type\":\"boolean\"},\"canAccessToIssuerSignatures\":{\"type\":\"boolean\"},\"id\":{\"format\":\"uuid\",\"type\":\"string\"}}}},\"name\":{\"type\":\"string\"},\"authorizations\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"enum\":[\"transfer-agent\",\"allow-export\"]}},\"id\":{\"type\":\"integer\"},\"enabled\":{\"type\":\"boolean\"},\"email\":{\"format\":\"email\",\"type\":\"string\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getAffiliatesTest719() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/affiliates", "getAffiliates", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"issuerId\":{\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"updatedAt\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getLegalSignerDocumentByIdTest417() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/legal-signers/{legalSignerId}/legal-signer-documents/{legalSignerDocumentId}", "getLegalSignerDocumentById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"fileName\":{\"type\":\"string\"},\"side\":{\"type\":\"string\"},\"docType\":{\"type\":\"string\",\"enum\":[\"passport\",\"driver-license\",\"national-identity-card\",\"residence-permit\",\"visa\",\"passport-card\",\"work-permit\",\"state-id\",\"uk-biometric-residence-permit\",\"tax-id\",\"voter-id\",\"certificate-of-naturalisation\",\"home-office-letter\",\"immigration-status-document\",\"birth-certificate\",\"other\",\"document-or-certificate-of-trust\",\"list-of-trustees\",\"certificate-of-formation\",\"partnership-agreement\",\"lp-list-and-authorised-signers-list\",\"articles-of-organization\",\"operating-agreement\",\"members-and-authorised-signers-list\",\"by-laws\",\"shareholders-list-and-authorised-signers-list\"]},\"imageUrl\":{\"type\":\"string\"},\"docCategory\":{\"type\":\"string\",\"enum\":[\"signer-identification\",\"signer-entity\"]},\"fileKey\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getDefaultConfigurationsTest107() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/default-configurations", "getDefaultConfigurations", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"access\":{\"type\":\"string\",\"enum\":[\"public\",\"private\"]},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"section\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"number\"},\"value\":{\"type\":[\"string\",\"null\"]},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getAllIssuersClientConfigFiltersTest303() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/configurations/client-configurations-filters", "getAllIssuersClientConfigFilters", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"names\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"}}}},\"issuers\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"domain\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getHealthCheckStatusTest923() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/health-check", "getHealthCheckStatus", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"blockchainEvents\":{\"type\":\"object\",\"required\":[\"status\"],\"properties\":{\"message\":{\"type\":[\"string\",\"null\"]},\"status\":{\"type\":\"string\",\"enum\":[\"ok\",\"error\"]}}},\"abstractionLayer\":{\"type\":\"object\",\"required\":[\"status\"],\"properties\":{\"message\":{\"type\":[\"string\",\"null\"]},\"status\":{\"type\":\"string\",\"enum\":[\"ok\",\"error\"]}}}}}");
    }


    @Test()
    public void getFundraiseContentTest982() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configurations/fundraise-content", "getFundraiseContent", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"string\"}");
    }


    @Test()
    public void getDashboardHoldersInformationTest382() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/dashboard/holders", "getDashboardHoldersInformation", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalEUHolders\":{\"type\":\"number\"},\"totalUSHolders\":{\"type\":\"number\"},\"totalWorldHolders\":{\"type\":\"number\"},\"totalHolders\":{\"type\":\"number\"},\"topThreeMaximumTokenStakes\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"holderName\":{\"type\":\"string\"},\"quantity\":{\"type\":\"number\"},\"holder\":{\"type\":\"object\",\"properties\":{\"fullName\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}}}}},\"countryDistribution\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"quantity\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"}}}},\"individualVsInstitutionalData\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"quantity\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getTokenByIdTest950() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}", "getTokenById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"showTbeWallet\":{\"type\":\"boolean\"},\"tokenSymbol\":{\"type\":\"string\"},\"documents\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"category\":{\"type\":\"string\"},\"url\":{\"type\":\"string\"}}}},\"isInvestorPays\":{\"type\":\"boolean\"},\"tokenName\":{\"type\":\"string\"},\"saft\":{\"type\":\"object\",\"properties\":{\"availableSaft\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"}}}},\"isParent\":{\"type\":\"boolean\"},\"availableForSaft\":{\"type\":\"boolean\"},\"saftInfo\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"string\"},\"type\":{\"type\":\"string\"}}},\"isChild\":{\"type\":\"boolean\"}}},\"defaultTokenValue\":{\"type\":[\"number\",\"null\"]},\"isTokenRepresentsDebt\":{\"type\":\"boolean\"},\"supportedCurrencies\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"tokenVideoUrl\":{\"type\":\"string\"},\"decimals\":{\"maximum\":18,\"type\":\"integer\",\"minimum\":0},\"isDeployed\":{\"type\":\"boolean\"},\"deploymentId\":{\"type\":[\"string\",\"null\"]},\"tokenDescription\":{\"type\":\"string\"},\"enableTokenLifecycleManagement\":{\"type\":\"boolean\"},\"enableFundraise\":{\"type\":\"boolean\"},\"isDripActive\":{\"type\":\"boolean\"},\"tokenIconUrl\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getInvestorIdsTest804() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/convert/investor-ids", "getInvestorIds", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"investorId\":{\"type\":\"number\"},\"externalId\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getJurisdictionsConfigurationsTest287() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configurations/jurisdictions", "getJurisdictionsConfigurations", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"restrictedJurisdictionsState\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"restrictedJurisdictions\":{\"type\":\"array\",\"items\":{\"format\":\"countryCode\",\"type\":\"string\"}},\"disclaimersAndDefinitions\":{\"type\":\"object\",\"properties\":{\"standardQualificationDefinition\":{\"type\":\"string\"},\"generalDisclaimer\":{\"type\":\"string\"},\"standardCountryDisclaimer\":{\"type\":\"string\"}}}}}");
    }


    @Test()
    public void getDefaultAgreementByIdTest191() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/agreements/{agreementId}", "getDefaultAgreementById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"yesText\":{\"type\":\"string\"},\"noText\":{\"type\":[\"string\",\"null\"]},\"agreementId\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"yes\",\"yes_no\"]},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getInvestorTokenGeneralInformationTest263() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/token-general-information/", "getInvestorTokenGeneralInformation", LoginAs.OPERATOR, "ISR/main-api", "{\"properties\":{\"lockedTBETokens\":{\"type\":\"number\"},\"lockedTokens\":{\"type\":\"number\"},\"totalTokensAssigned\":{\"type\":\"number\"},\"hasActiveIssuance\":{\"type\":\"boolean\"},\"roundBonusTokensForDisplay\":{\"type\":\"string\"},\"roundTokensAssigned\":{\"type\":\"number\"},\"roundBonusTokensAssignedForDisplay\":{\"type\":\"string\"},\"roundBonusTokensAssigned\":{\"type\":\"number\"},\"isAffiliate\":{\"type\":\"boolean\"},\"totalLockedTokens\":{\"type\":\"number\"},\"lockedTokensForDisplay\":{\"type\":\"string\"},\"totalLockedTokensForDisplay\":{\"type\":\"string\"},\"totalBonusTokensForDisplay\":{\"type\":\"string\"},\"totalBonusTokens\":{\"type\":\"number\"},\"totalTokensHeld\":{\"type\":\"number\"},\"lockedTBETokensForDisplay\":{\"type\":\"string\"},\"roundBonusTokens\":{\"type\":\"number\"},\"blockchainId\":{\"type\":\"string\"},\"totalTokensAssignedForDisplay\":{\"type\":\"string\"},\"totalTokensHeldForDisplay\":{\"type\":\"string\"},\"roundTokensAssignedForDisplay\":{\"type\":\"string\"}}}");
    }


    @Test()
    public void getRegionJurisdictionalConfigTest464() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/configurations/jurisdictions/countries/{countryCode}/regions/{regionCode}", "getRegionJurisdictionalConfig", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"qualification\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\"]},\"content\":{\"type\":\"string\"}}},\"regionCode\":{\"type\":\"string\"},\"reverseSolicitation\":{\"default\":false,\"type\":\"boolean\"},\"accreditationText\":{\"type\":\"string\"},\"solicitation\":{\"default\":true,\"type\":\"boolean\"},\"additionalAccreditation\":{\"type\":\"boolean\"},\"countryName\":{\"type\":\"string\"},\"disclaimer\":{\"type\":\"object\",\"properties\":{\"issuerAgreementId\":{\"type\":\"integer\"},\"type\":{\"default\":\"standard\",\"type\":\"string\",\"enum\":[\"standard\",\"specific\",\"none\"]},\"content\":{\"type\":[\"string\",\"null\"]}}},\"qualificationRequired\":{\"type\":\"boolean\"},\"accreditationFirst\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getInvestorInvestmentTest10() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/investment", "getInvestorInvestment", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"investorData\":{\"type\":[\"string\",\"null\"]},\"subscriptionAgreementStatus\":{\"type\":[\"string\",\"null\"],\"enum\":[\"none\",\"requested\",\"sent\",\"signed\",\"confirmed\",\"rejected\",\"pre-signed\",null]},\"investmentRequest\":{\"type\":[\"object\",\"null\"],\"properties\":{\"pledged\":{\"type\":[\"number\",\"null\"]},\"bonusTokensAssigned\":{\"type\":[\"number\",\"null\"]},\"id\":{\"type\":[\"number\",\"null\"]},\"tokensAssigned\":{\"type\":[\"number\",\"null\"]},\"totalFunded\":{\"type\":[\"number\",\"null\"]},\"bonusTokens\":{\"type\":[\"number\",\"null\"]},\"status\":{\"type\":\"string\",\"enum\":[\"in-progress\",\"pending\",\"confirmed\",\"cancelled\",\"refunded\",\"new-round\",null,\"\"]}}},\"pledged\":{\"type\":[\"object\",\"null\"],\"properties\":{\"amount\":{\"type\":\"number\"},\"currencyId\":{\"type\":\"integer\"}}},\"subscriptionAgreementSignedAt\":{\"format\":\"date-time\",\"type\":[\"string\",\"null\"]},\"depositWallets\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"address\":{\"type\":\"string\"},\"investorData\":{\"type\":[\"string\",\"null\"]},\"tokenName\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"},\"type\":{\"type\":\"string\"}}}},\"transactions\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"usdWorth\":{\"type\":\"number\"},\"amount\":{\"type\":\"number\"},\"data\":{\"type\":\"string\"},\"externalTransactionConfirmation\":{\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"roundName\":{\"type\":[\"string\",\"null\"]},\"id\":{\"type\":\"integer\"},\"source\":{\"type\":\"string\",\"enum\":[\"automated\",\"manual\"]},\"transactionTime\":{\"format\":\"date-time\",\"type\":\"string\"},\"currencyId\":{\"type\":\"integer\"},\"direction\":{\"type\":\"string\",\"enum\":[\"deposit\",\"withdraw\",\"liquidation\",\"refund\"]}}}},\"totalFunded\":{\"type\":[\"object\",\"null\"],\"properties\":{\"amount\":{\"type\":\"number\"},\"currencyId\":{\"type\":\"integer\"}}},\"bonusTokens\":{\"type\":[\"number\",\"null\"]},\"status\":{\"type\":[\"string\",\"null\"],\"enum\":[\"in-progress\",\"pending\",\"confirmed\",\"cancelled\",\"refunded\",\"new-round\",null,\"\"]}}}");
    }


    @Test()
    public void getPermissionsPerIssuerTest256() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/info/permissions", "getPermissionsPerIssuer", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"canViewEditInvestorInfo\":{\"type\":\"boolean\"},\"isBDRestrictedAccess\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getTokenStatusTest141() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/token-status", "getTokenStatus", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"tokenBlockchainName\":{\"type\":\"string\"},\"status\":{\"description\":\"token-status\",\"type\":\"string\",\"enum\":[\"on-hold\",\"operational\"]}}}");
    }


    @Test()
    public void getAlertsCountTest312() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/alerts/count", "getAlertsCount", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"count\":{\"type\":\"integer\"}}}");
    }


    @Test()
    public void getSecuritizeIdAlertsCountTest368() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/alerts/count", "getSecuritizeIdAlertsCount", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"count\":{\"type\":\"integer\"}}}");
    }


    @Test()
    public void getWalletRegistrarOwnerTest267() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/wallet-registrar-owner", "getWalletRegistrarOwner", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"privateKey\":{\"type\":\"string\"},\"address\":{\"format\":\"blockchainWalletAddress\",\"type\":\"string\"}}}");
    }


    @Test()
    public void getSystemTextsTest873() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/system-texts", "getSystemTexts", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"createdAt\":{\"type\":\"string\"},\"isHtml\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"active\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"system\",\"content\"]},\"value\":{\"type\":[\"string\",\"null\"]},\"updatedAt\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getSnapshotRecordsTest729() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/snapshots/{snapshotId}/records", "getSnapshotRecords", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"payoutWallet\":{\"type\":\"object\",\"properties\":{\"address\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}},\"investor\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"email\":{\"format\":\"email\",\"type\":\"string\"}}},\"wallet\":{\"type\":\"object\",\"properties\":{\"address\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"}}},\"tokens\":{\"type\":\"number\"},\"type\":{\"type\":\"string\",\"enum\":[\"wallet\",\"treasury\",\"bonus\"]}}}}}}");
    }


    @Test()
    public void getIssuersNavListTest161() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/nav-list", "getIssuersNavList", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"required\":[\"id\",\"name\"],\"properties\":{\"logoDark\":{\"type\":\"string\"},\"logoFullName\":{\"type\":\"string\"},\"defaultTokenId\":{\"format\":\"uuid\",\"type\":\"string\"},\"name\":{\"type\":\"string\"},\"logo\":{\"type\":\"string\"},\"id\":{\"format\":\"uuid\",\"type\":\"string\"},\"logoSquare\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getInvestorsTest542() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors", "getInvestors", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalSelectableItems\":{\"type\":\"number\"},\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"lastName\":{\"default\":\"\",\"type\":\"string\"},\"kycStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"processing\",\"updates-required\",\"manual-review\",\"verified\",\"rejected\",\"expired\"]},\"birthdate\":{\"format\":\"date\",\"type\":[\"string\",\"null\"]},\"subscriptionAgreementStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"requested\",\"sent\",\"signed\",\"confirmed\",\"rejected\",\"pre-signed\"]},\"accreditedStatus\":{\"type\":\"string\",\"enum\":[\"none\",\"pending\",\"confirmed\",\"rejected\",\"no\",\"no-accepted\",\"processing\",\"not-accredited\"]},\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"entityName\":{\"type\":\"string\"},\"countryCode\":{\"format\":\"countryCode\",\"type\":\"string\"},\"investorStatus\":{\"description\":\"if not 'ready' this investor is in process\",\"type\":\"string\",\"enum\":[\"ready\",\"no-kyc\",\"no-accredited\",\"no-subscription-agreement\",\"no-funded\"]},\"percentage\":{\"type\":\"number\"},\"isLocked\":{\"type\":\"boolean\"},\"tokens\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"email\":{\"format\":\"email\",\"type\":\"string\"},\"bonusTokens\":{\"type\":\"number\"},\"amountFunded\":{\"type\":\"number\"},\"fullName\":{\"type\":\"string\",\"example\":\"Itai Raz\"},\"tokensAssigned\":{\"type\":\"number\"},\"labels\":{\"type\":\"array\",\"items\":{\"type\":\"string\"}},\"firstName\":{\"default\":\"\",\"type\":\"string\"},\"walletRegistrationStatus\":{\"type\":\"string\"},\"roundIds\":{\"type\":\"array\",\"items\":{\"type\":\"number\"}},\"name\":{\"description\":\"In case the user is indevidual, it will be full name, in case user is entity it will be the company's name\",\"type\":\"string\",\"example\":\"Apple\"},\"registrationSource\":{\"type\":\"string\"},\"stateCode\":{\"format\":\"stateCode\",\"type\":\"string\"},\"walletAddress\":{\"format\":\"blockchainWalletAddress\",\"type\":\"string\"},\"amountPledged\":{\"type\":\"number\"},\"tokenInTreasury\":{\"type\":\"number\"},\"investorType\":{\"type\":\"string\",\"enum\":[\"individual\",\"entity\",\"custodian-wallet-manager\",\"fbo-individual\",\"fbo-entity\"]},\"pledgeDate\":{\"type\":\"string\"}}}}}}");
    }


    @Test()
    public void getTbeBalancesTest823() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/tbe-balances", "getTbeBalances", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"inProgressInternalTBETransferTokensAmount\":{\"type\":\"string\"},\"issuerId\":{\"type\":\"string\"},\"openSellOrderTBETokensAmount\":{\"type\":\"string\"},\"totalTBETokensHeld\":{\"type\":\"string\"},\"totalTBETokensWithdrawableAmount\":{\"type\":\"string\"},\"tokenId\":{\"type\":\"string\"},\"inProgressBurnTBETokensAmount\":{\"type\":\"string\"},\"isAffiliate\":{\"type\":\"boolean\"},\"securitizeIdProfileId\":{\"type\":\"string\"},\"totalTBETokensInLockUpPeriod\":{\"type\":\"string\"},\"inProgressTBETokensWithdrawalsAmount\":{\"type\":\"string\"},\"lockedTBETokensAmount\":{\"type\":\"string\"}}}}");
    }


    @Test()
    public void getSecuritizeIdInvestorAssignedAssetsTest763() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/assigned-assets", "getSecuritizeIdInvestorAssignedAssets", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"type\":\"string\"},\"dashboardUrl\":{\"type\":\"string\"},\"tokenId\":{\"type\":\"string\"},\"tokenLogoUrl\":{\"type\":\"string\"},\"issuerLogo\":{\"type\":\"string\"},\"issuerName\":{\"type\":\"string\"},\"deploymentId\":{\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"investorId\":{\"type\":\"number\"},\"tokensAssigned\":{\"type\":\"number\"}}}}");
    }


    @Test()
    public void getSystemQuestionByIdTest551() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/questions/{questionId}", "getSystemQuestionById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"question\":{\"type\":\"string\"},\"answer\":{\"type\":\"string\"},\"section\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"position\":{\"type\":\"number\"}}}");
    }


    @Test()
    public void getInvestorDetailsByIdTest886() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/details", "getInvestorDetailsById", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"phonePrefix\":{\"type\":[\"string\",\"null\"]},\"lastName\":{\"default\":\"\",\"type\":[\"string\",\"null\"]},\"zipCode\":{\"type\":\"string\"},\"birthdate\":{\"type\":\"string\"},\"gender\":{\"type\":\"string\",\"enum\":[\"male\",\"female\"]},\"city\":{\"type\":\"string\"},\"taxId2\":{\"type\":[\"string\",\"null\"]},\"taxId3\":{\"type\":[\"string\",\"null\"]},\"createdAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"cityOfBirth\":{\"type\":[\"string\",\"null\"]},\"isEditable\":{\"type\":\"boolean\"},\"taxCountryCode2\":{\"type\":[\"string\",\"null\"]},\"jsonOpen\":{\"type\":[\"string\",\"null\"]},\"taxCountryCode3\":{\"type\":[\"string\",\"null\"]},\"countryOfBirth\":{\"type\":[\"string\",\"null\"]},\"entityName\":{\"type\":\"string\"},\"countryCode\":{\"type\":\"string\"},\"state\":{\"type\":[\"string\",\"null\"]},\"incorporationDate\":{\"format\":\"date-time\",\"type\":[\"string\",\"null\"]},\"custodianId\":{\"type\":[\"number\",\"null\"]},\"email\":{\"format\":\"email\",\"type\":\"string\"},\"securitizeIdProfileId\":{\"type\":[\"string\",\"null\"]},\"is2FaEnabled\":{\"type\":\"boolean\"},\"updatedAt\":{\"format\":\"date-time\",\"type\":\"string\"},\"identityDocumentNumber\":{\"type\":[\"string\",\"null\"]},\"address2\":{\"type\":[\"string\",\"null\"]},\"address1\":{\"type\":\"string\"},\"entityType\":{\"type\":[\"string\",\"null\"],\"enum\":[\"revocable-trust\",\"irrevocable-trust\",\"limited-general-partnership\",\"llc\",\"corporation\",\"other\",null]},\"entityLeiCode\":{\"minLength\":20,\"pattern\":\"^[A-Za-z0-9]*$\",\"type\":[\"string\",\"null\"],\"maxLength\":20},\"externalId\":{\"type\":\"string\"},\"fboEmail\":{\"format\":\"email\",\"type\":[\"string\",\"null\"]},\"jsonPrivate\":{\"type\":[\"string\",\"null\"]},\"brokerDealerGroupId\":{\"type\":[\"number\",\"null\"]},\"firstName\":{\"default\":\"\",\"type\":[\"string\",\"null\"]},\"taxCountryCode\":{\"type\":[\"string\",\"null\"]},\"custodianName\":{\"type\":[\"string\",\"null\"]},\"phoneNumber\":{\"type\":\"string\"},\"operatorComments\":{\"type\":[\"string\",\"null\"]},\"taxId\":{\"type\":[\"string\",\"null\"]},\"name\":{\"type\":\"string\"},\"registrationSource\":{\"type\":[\"string\",\"null\"]},\"taxCountry\":{\"type\":[\"string\",\"null\"]},\"middleName\":{\"type\":[\"string\",\"null\"]},\"isSecuritizeMarkets\":{\"type\":\"boolean\"},\"blockchainId\":{\"type\":[\"string\",\"null\"]},\"investorType\":{\"type\":\"string\",\"enum\":[\"individual\",\"entity\"]}}}");
    }


    @Test()
    public void getLinksBySecuritizeIdTest619() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/securitize-id/investors/{investorId}/links", "getLinksBySecuritizeId", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issuerId\":{\"format\":\"uuid\",\"type\":\"string\"},\"links\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"linkedUserId\":{\"format\":\"uuis\",\"type\":\"number\"},\"linkType\":{\"type\":\"string\",\"enum\":[\"fbo\",\"main-investor\"]},\"info\":{\"type\":\"object\",\"properties\":{\"custodianId\":{\"type\":\"number\"}}}}}},\"userId\":{\"type\":\"number\"}}}}");
    }


    @Test()
    public void getLabelsTest403() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/labels", "getLabels", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"number\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"label\":{\"type\":\"string\"},\"userId\":{\"type\":\"number\"}}}}}}");
    }


    @Test()
    public void getAgreementsTest941() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/agreements", "getAgreements", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"yesText\":{\"type\":[\"string\",\"null\"]},\"noText\":{\"type\":[\"string\",\"null\"]},\"subtitle\":{\"type\":[\"string\",\"null\"]},\"documentUrl\":{\"type\":[\"string\",\"null\"]},\"agreementId\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"text\":{\"type\":\"string\"},\"title\":{\"type\":\"string\"},\"type\":{\"type\":\"string\",\"enum\":[\"yes\",\"yes_no\"]},\"checkOptions\":{\"type\":[\"string\",\"null\"]}}}}}}");
    }


    @Test()
    public void getTokensAndWalletsTest474() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/investors/{userId}/tokens", "getTokensAndWallets", LoginAs.OPERATOR, "ISR/main-api", "{\"properties\":{\"isOwnedTokens\":{\"type\":\"boolean\"},\"roundTokensAssigned\":{\"type\":[\"number\",\"null\"]},\"wallets\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"owner\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"}}},\"address\":{\"format\":\"blockchainWalletAddress\",\"type\":\"string\"},\"isCustodian\":{\"type\":\"boolean\"},\"omnibusTokens\":{\"type\":\"number\"},\"walletType\":{\"type\":\"string\",\"enum\":[\"normal\",\"custodian\",\"omnibus\"]},\"isMain\":{\"type\":\"boolean\"},\"tokensLocked\":{\"type\":\"number\"},\"link\":{\"format\":\"url\",\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"creationDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"tokensPending\":{\"type\":\"number\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"tokensHeld\":{\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"syncing\",\"syncing-investor-pays\",\"ready\",\"rejected\",\"cancelled\"]}}}},\"tokensAssigned\":{\"type\":[\"number\",\"null\"]},\"hasInvestmentRequests\":{\"type\":\"boolean\"},\"isSpecificToken\":{\"type\":\"boolean\"},\"roundBonusTokensAssigned\":{\"type\":[\"number\",\"null\"]},\"isAffiliate\":{\"type\":\"boolean\"},\"issuances\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"issueAmount\":{\"type\":\"number\"},\"executionDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"tokenName\":{\"type\":\"string\"},\"investmentRoundId\":{\"type\":\"number\"},\"description\":{\"type\":\"string\"},\"canDelete\":{\"type\":\"boolean\"},\"id\":{\"type\":\"number\"},\"source\":{\"type\":\"string\",\"enum\":[\"automated\",\"manual\"]},\"issuanceTime\":{\"format\":\"date-time\",\"type\":\"string\"},\"creationDate\":{\"format\":\"date-time\",\"type\":\"string\"},\"status\":{\"type\":\"string\",\"enum\":[\"pending\",\"processing\",\"success\",\"failure\"]},\"target\":{\"type\":\"string\",\"enum\":[\"wallet\",\"treasury\"]}}}},\"isAutoIssuanceEnabled\":{\"type\":\"boolean\"},\"round\":{\"type\":[\"object\",\"null\"],\"properties\":{\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\"}}},\"blockchain\":{\"type\":\"string\"},\"tokensTreasury\":{\"type\":[\"number\",\"null\"]},\"isDripActive\":{\"type\":\"boolean\"},\"roundBonusTokens\":{\"type\":[\"number\",\"null\"]},\"blockchainId\":{\"type\":[\"string\",\"null\"]},\"tokensHeld\":{\"type\":[\"number\",\"null\"]},\"availableTokensTreasury\":{\"type\":\"number\"},\"bonusTokens\":{\"type\":[\"number\",\"null\"]}}}");
    }


    @Test()
    public void getRoundInfoTest168() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/investment-rounds/{investmentRoundId}/info", "getRoundInfo", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"amountFunded\":{\"type\":\"number\"},\"amountPledged\":{\"type\":\"number\"},\"open\":{\"type\":\"boolean\"}}}");
    }


    @Test()
    public void getSnapshotsTest644() {
        testRequest(Method.GET, "https://cp.{environment}.securitize.io/api/v2/issuers/{issuerId}/tokens/{tokenId}/snapshots", "getSnapshots", LoginAs.OPERATOR, "ISR/main-api", "{\"type\":\"object\",\"properties\":{\"totalItems\":{\"type\":\"integer\"},\"data\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"date\":{\"format\":\"date-time\",\"type\":\"string\"},\"issuerId\":{\"type\":\"string\"},\"distributeCreated\":{\"type\":\"boolean\"},\"name\":{\"type\":\"string\"},\"id\":{\"type\":\"number\"},\"status\":{\"type\":\"string\",\"enum\":[\"distributed\",\"none\"]}}}}}}");
    }

}