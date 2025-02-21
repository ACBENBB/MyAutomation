package io.securitize.tests.fundraise.abstractclass;

import io.securitize.infra.api.*;
import io.securitize.pageObjects.investorsOnboarding.investWizard.InvestWizard_QualificationPage;
import io.securitize.pageObjects.investorsOnboarding.investWizard.InvestWizard_SignAgreement;
import io.securitize.pageObjects.investorsOnboarding.nie.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorRegistration01InvestorType;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdLoginScreenLoggedIn;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc01IndividualIdentityVerification;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.fundraise.pojo.FT_TestData;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;
import static io.securitize.tests.fundraise.abstractclass.ConstantsStrings.CHANGE_OF_STATUS_TITLE;
import static io.securitize.tests.fundraise.abstractclass.ConstantsStrings.CONTACT_US_BTN;
import static io.securitize.tests.fundraise.abstractclass.ConstantsStrings.KYCstatus.PROCESSING;

public class AbstractFT extends AbstractUiTest {

    public InvestorDetails investorDetails;

    public enum FT_TestScenario {
        FT_INVEST_WIRETRANSFER("Wire Transfer"),
        FT_INVEST_CASHACCOUNT("Cash Account"),
        FT_INVEST_WEB3("Web3"),
        FT_ACCREDITATION_FIRST("Accreditation First"),
        FT_INVEST_DOCUSIGN("Docusign"),
        FT_INVEST_VERIFYDOC("Verify Documents"),
        FT_NIE_KYC_STATUS("KYC Status PopUp");

        private final String displayName;

        FT_TestScenario(String displayname) {
            this.displayName = displayname;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public FT_TestData createTestDataObject(FT_TestScenario testScenario) {
        return new FT_TestData(testScenario);
    }

    public String createSecuritizeIdInvestor() throws Exception {
        investorDetails = InvestorDetails.generateCashAccountInvestorReady();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        return investorsAPI.createCashAccountReadyInvestor(investorDetails);
    }

    public void setKYCToVerified(String secId, String kycStatus) {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.setKYCToPassed(secId, kycStatus);
    }

    public void associateInvestorToIssuer(String issuerid, String SiD) {
        TransferAgentAPI.associateSidInvestorToIssuerOnboardingDB(issuerid, SiD);
    }

    public String getCpInvestorId(String issuerId, String investorEmail) {
        IssuersAPI issuersAPI = new IssuersAPI();
        return issuersAPI.getInvestorCpIdByEmail(issuerId, investorEmail);
    }

    public void setCurrentAccreditationStatus(String issuerId, String cpInvestorId, String tokenId, String qualificationStatus) {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.setQualificationStatus(issuerId, cpInvestorId, tokenId, qualificationStatus);
    }

    public void setQualificationStatus(String issuerId, String cpInvestorId, String tokenId, String qualificationStatus) {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.setQualificationStatus(issuerId, cpInvestorId, tokenId, qualificationStatus);
    }

    public void setMarketsAccreditationStatus(String secId, String status) {
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketAccreditation(secId, status);
    }

    public void setMarketsUSAccountStatus(String secId, String accreditationStatus, String signature) {
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketUSAccountStatus(secId, accreditationStatus, signature);
    }

    public void setMarketSuitabilityStatus(String secId, String accreditationStatus) {
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketUSSuitability(secId, accreditationStatus);
    }

    public void navigateToIssuerNieWebSite(String nieUrl) {
        getBrowser().navigateTo(nieUrl);
    }

    public void loginToNie(String investorEmail, String password) {
        NieWelcomePage welcomePage = new NieWelcomePage(getBrowser());
        welcomePage
                .clickLogInWithoutAccountSelector()
                .clickAcceptCookies()
                .performLoginWithCredentials(investorEmail, password, true);
        SecuritizeIdLoginScreenLoggedIn securitizeIdLoginScreenLoggedIn = new SecuritizeIdLoginScreenLoggedIn(getBrowser());
        if(investorEmail.contains("AUT539")) {
            securitizeIdLoginScreenLoggedIn.clickAllowButtonWithAccreditationFirst();
        } else {
            securitizeIdLoginScreenLoggedIn.clickAllowButton();
        }
    }

    public void navigateToOpportunitiesTab() {
        NieDashboard nieDashboard = new NieDashboard(getBrowser());
        // TODO rename to clickOpportunitiesTab()
        nieDashboard.switchToOpportunitiesTab();
    }

    public void clickInvestmentByName(String investmentName) {
        NieDashboardOpportunities nieDashboardOpportunities = new NieDashboardOpportunities(getBrowser());
        nieDashboardOpportunities.clickViewInvestmentDetailsByName(investmentName);

    }

    public void setInvestmentCurrencyAndAmount(String currencyId, String investmentAmount) {
        NieInvestmentDetails investmentDetails = new NieInvestmentDetails(getBrowser());
        investmentDetails.setInvestmentCurrencyString(currencyId).setStringInvestmentAmount(investmentAmount);
    }

    public void setInvestmentAmount(String investmentAmount) {
        NieInvestmentDetails investmentDetails = new NieInvestmentDetails(getBrowser());
        investmentDetails.setStringInvestmentAmount(investmentAmount);
    }

    public String createCashAccount(FT_TestData testData) {
        String cashAccountId = null;
        Object cashAccountFunds = 10000;
        if(testData.hasCashAccount) {
            cashAccountId = createCashAccountWithFunds(testData.investorSid, cashAccountFunds);
        }
        return  cashAccountId;
    }

    public String createCashAccountWithFunds(String investorId, Object funds) {
        CashAccountApi service = new CashAccountApi();
        return service.createCashAccountWithFunds(investorId, funds);
    }

    public void clickInvest() {
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        nieInvestmentDetails.clickInvest();
    }

    public void clickAgreementContinue(){
        InvestWizard_SignAgreement investWizardSignAgreement = new InvestWizard_SignAgreement(getBrowser());
        // TODO remove do while when bug is solved.
        do {
            investWizardSignAgreement.clickContinue();
        } while (investWizardSignAgreement.isContinueBtnVisible());
    }

    public void clickOnFundYourInvestmentPageExitProcess() {
        FundYourInvestmentPage fundYourInvestmentPage = new FundYourInvestmentPage(getBrowser());
        fundYourInvestmentPage.clickExitProcess();
    }

    public void assertFundYourInvestmentPageWireTransferDetails() {
        getBrowser().waitForPageStable();
        FundYourInvestmentPage fundYourInvestmentPage = new FundYourInvestmentPage(getBrowser());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(fundYourInvestmentPage.isWireTransferCardTitleVisible(), "Wire Transfer Card Title is NOT Visible");
        softAssert.assertTrue(fundYourInvestmentPage.isWireTransferCardDetailsVisible(), "Wire Transfer Card Details are NOT Visible");
        softAssert.assertAll();
    }

    public void assertFundYourInvestmentPageCashAccountDetails() {
        getBrowser().waitForPageStable();
        FundYourInvestmentPage fundYourInvestmentPage = new FundYourInvestmentPage(getBrowser());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(fundYourInvestmentPage.isCashAccountCardTitleVisible(), "Cash Account Card Title is NOT Visible");
        softAssert.assertTrue(fundYourInvestmentPage.isCashAccountCardDetailsVisible(), "Cash Account Card Details are NOT Visible");
        softAssert.assertAll();
    }

     public void assertInvestmentApprovedMsgWireTransfer() {
        startTestLevel("assertInvestmentApprovedMsgWireTransfer");
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(nieInvestmentDetails.isInvestmentApprovedMsgTitleDisplayed());
        softAssert.assertTrue(nieInvestmentDetails.isInvestmentApprovedMsgDetailsDisplayed());
        softAssert.assertTrue(nieInvestmentDetails.isInvestmentApprovedButtonDisplayed());
        softAssert.assertAll();
        endTestLevel();
    }

    public void updatesSubscriptionAgreementStatusViaApi(FT_TestData testData) throws Exception {
        IssuersAPI issuersAPI = new IssuersAPI();
        testData.roundId  = issuersAPI.getCpOnboardingRoundId(testData.issuerId, testData.tokenId, testData.cpInvestorId);
        issuersAPI.pachInvestment(testData);
        getBrowser().refreshPage();
    }

    public void assertCpInvestmentViaApi(FT_TestScenario testScenario, FT_TestData testData) {
        startTestLevel("assertCpInvestmentViaApi");
        if(testScenario.equals(FT_TestScenario.FT_INVEST_WIRETRANSFER)) {
            IssuersAPI issuersAPI = new IssuersAPI();
            String roundId = issuersAPI.getCpOnboardingRoundId(testData.issuerId, testData.tokenId, testData.cpInvestorId);
            String pledgeAmount = issuersAPI.getInvestmentPledgeAmount(testData.issuerId, testData.tokenId, testData.cpInvestorId, roundId);
            Assert.assertEquals(testData.investmentAmount, pledgeAmount);
        } else if(testScenario.equals(FT_TestScenario.FT_INVEST_CASHACCOUNT)) {
            IssuersAPI issuersAPI = new IssuersAPI();
            String roundId = issuersAPI.getCpOnboardingRoundId(testData.issuerId, testData.tokenId, testData.cpInvestorId);
            String pledgeAmount = issuersAPI.getInvestmentPledgeAmount(testData.issuerId, testData.tokenId, testData.cpInvestorId, roundId);
            Assert.assertEquals(testData.investmentAmount, pledgeAmount);
        } else if(testScenario.equals(FT_TestScenario.FT_INVEST_WEB3)) {
            // TODO IMPLEMENT
        }else if(testScenario.equals(FT_TestScenario.FT_ACCREDITATION_FIRST)) {
            IssuersAPI issuersAPI = new IssuersAPI();
            String roundId = issuersAPI.getCpOnboardingRoundId(testData.issuerId, testData.tokenId, testData.cpInvestorId);
            String pledgeAmount = issuersAPI.getInvestmentPledgeAmount(testData.issuerId, testData.tokenId, testData.cpInvestorId, roundId);
            Assert.assertEquals(testData.investmentAmount, pledgeAmount);
        }else if(testScenario.equals(FT_TestScenario.FT_INVEST_DOCUSIGN)) {
            IssuersAPI issuersAPI = new IssuersAPI();
            String roundId = issuersAPI.getCpOnboardingRoundId(testData.issuerId, testData.tokenId, testData.cpInvestorId);
            String pledgeAmount = issuersAPI.getInvestmentPledgeAmount(testData.issuerId, testData.tokenId, testData.cpInvestorId, roundId);
            Assert.assertEquals(testData.investmentAmount, pledgeAmount);
        }
        endTestLevel();
    }

    public FT_TestData createFtTestInvestor(FT_TestData testdata) throws Exception {
        // CREATE A NEW INVESTOR VIA API
        testdata.investorSid = createSecuritizeIdInvestor();
        // SET KYC VERIFIED .
        setKYCToVerified(testdata.investorSid, testdata.investorKycStatus);
        // ADD INVESTOR TO ISSUER ONBOARDING DB
        associateInvestorToIssuer(testdata.issuerId, testdata.investorSid);
        testdata.cpInvestorId = getCpInvestorId(testdata.issuerId, investorDetails.getEmail());
        // SET ONBOARDING FLAGS
        // - Current Accreditation Status
        setCurrentAccreditationStatus(testdata.issuerId, testdata.cpInvestorId, testdata.tokenId, testdata.onboardingAccreditationStatus);
        // - Qualification Status for <TOKEN>
        setQualificationStatus(testdata.issuerId, testdata.cpInvestorId, testdata.tokenId, testdata.onboardingQualificationStatus);
        // SET MARKET FLAGS
        // - US Suitability
        setMarketSuitabilityStatus(testdata.investorSid, testdata.marketSuitabilityStatus);
        // - EU Suitability
        // - US Broker Dealer Account
        setMarketsUSAccountStatus(testdata.investorSid, testdata.marketUSAccountStatus, "");
        // - EU Broker Dealer Account
        // - Accreditation Status
        setMarketsAccreditationStatus(testdata.investorSid, testdata.marketAccreditationStatus);
        // IF TRUE -> CREATE CASH ACCOUNT VIA API
        testdata.cashAccountId = createCashAccount(testdata);
        return testdata;
    }

    public void clickCashAccountOption() {
        FundYourInvestmentPage fundYourInvestmentPage = new FundYourInvestmentPage(getBrowser());
        fundYourInvestmentPage.clickCashAccountOption();
    }

    public void clickInvestButtonOnFundingPage() {
        FundYourInvestmentPage fundYourInvestmentPage = new FundYourInvestmentPage(getBrowser());
        fundYourInvestmentPage.clickCashAccountConfirmPurchaseButton();
    }

    public void assertCashAccountFinishInvestmentPopUp() {
        NieFinishInvestmentPopUp nieFinishInvestmentPopUp = new NieFinishInvestmentPopUp(getBrowser());
        nieFinishInvestmentPopUp.assertAllElementAreDisplayed();
    }

    public void clickIAmAnAccreditedInvestor() {
        NieInvestorQualifications accreditationFirstPage = new NieInvestorQualifications(getBrowser());
        accreditationFirstPage.clickAmAccreditedInvestorForAccreditationFirst1();
    }

    public void verifyPopUpAndKYCStatus(String kycStatus) {
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        nieInvestmentDetails.clickInvest();
        nieInvestmentDetails.waitForPopUpToBeDisplayed();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(nieInvestmentDetails.isPopUpLogoDisplayed(), "Logo is not displayed");
        softAssert.assertTrue(nieInvestmentDetails.isPopUpTitleCorrect(kycStatus), "The Title is not what was expected");
        softAssert.assertTrue(nieInvestmentDetails.isPopUpDescriptionDisplayed(), "Description is not displayed");
        softAssert.assertTrue(nieInvestmentDetails.isPopUpBtnTextCorrect(kycStatus), "The Button text is not what was expected");
        softAssert.assertAll();
        if (!kycStatus.equals(PROCESSING.getStatus())) nieInvestmentDetails.clickCompleteVerification();
    }
    public void verifyTypeOfInvestorStep() {
        SecuritizeIdInvestorRegistration01InvestorType investorRegistration01InvestorType = new SecuritizeIdInvestorRegistration01InvestorType(getBrowser(), true);
        investorRegistration01InvestorType.closePopUp();
    }
    public void updateKycStatusByAPI(FT_TestData testdata ,String kycStatus) throws InterruptedException {
        setKYCToVerified(testdata.investorSid, kycStatus);
        testdata.investorKycStatus = kycStatus;
    }
    public void verifyKycStatusRejected() {
        // In this case the calculator is not displayed and the button says CONTACT US
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(nieInvestmentDetails.isChangeOfStatusLogoDisplayed(), "Logo is not displayed");
        softAssert.assertEquals(nieInvestmentDetails.getChangeOfStatusTitle(),CHANGE_OF_STATUS_TITLE, "Title should be Change of status");
        softAssert.assertTrue(nieInvestmentDetails.isChangeOfStatusDescriptionDisplayed(), "Description is not displayed");
        softAssert.assertEquals(nieInvestmentDetails.getContactUsBtnText(),CONTACT_US_BTN, " Button text should be Contact Us");
        softAssert.assertAll();
    }
    public void verifyIdentityVerificationStep() {
        SecuritizeIdInvestorKyc01IndividualIdentityVerification identityVerification = new SecuritizeIdInvestorKyc01IndividualIdentityVerification(getBrowser());
        identityVerification.clickExitProcess();
    }
    public void verifyInvestWizardQualification() {
        InvestWizard_QualificationPage qualificationPage = new InvestWizard_QualificationPage(getBrowser());
        qualificationPage.clickExitProcess();
    }

}