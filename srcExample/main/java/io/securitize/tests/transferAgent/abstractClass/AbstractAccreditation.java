package io.securitize.tests.transferAgent.abstractClass;

import io.restassured.response.Response;
import io.securitize.infra.api.AccreditationAPI;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.LoginAPI;
import io.securitize.infra.api.anticaptcha.AntiCaptchaApi;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage2FA;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSideMenu;
import io.securitize.pageObjects.controlPanel.cpMarkets.CpAccreditation;
import io.securitize.pageObjects.controlPanel.cpMarkets.CpAccreditationDetails;
import io.securitize.pageObjects.controlPanel.cpMarkets.CpMarkets;
import io.securitize.pageObjects.controlPanel.cpMarkets.CpMarketsInvestorPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationMethodPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationStatusPage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractDelayedUiTest;
import org.testng.Assert;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractAccreditation extends AbstractDelayedUiTest {

    InvestorDetails investorDetails;
    private final String updatesRequiredMsg = "Updates Required Test Message";
    private final String rejectedMsg = "Rejected Test Message";
    private final String expiredMsg = "Expired Test Message";
    public static final String EXTRACT_LINK_FROM_EMAIL_REGEX = "((https:\\/\\/)[a-zA-Z0-9]+\\.ct.sendgrid.net\\/ls\\/click\\?upn=[a-zA-Z0-9_.-]+)";
    public String investorMail = "";
    public String accreditationMethod = "";

    public enum InvestorType {
        INDIVIDUAL,
        ENTITY,
        INDIVIDUAL_NON_US,
        ENTITY_NON_US
    }

    public enum IndividualAccreditationMethod {
        // INDIVIDUAL
        THIRD_PARTY("Verification Letter"),
        INCOME("Income"),
        INCOME_PROD("Accreditation Production Sanity Flow"),
        NET_WORTH("Net Worth"),
        PROFESSIONAL_LICENSE("Professional License");

        private final String displayName;

        IndividualAccreditationMethod(String displayname) {
            this.displayName = displayname;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public enum EntityAccreditationMethod {
        // ENTITY
        THIRD_PARTY("Verification Letter"),
        ASSETS_AND_INVESTMENTS("Assets And Investments"),
        OWNERS_OF_EQUITY_SECURITIES("Owners Of Equity Securities");

        private final String toString;

        EntityAccreditationMethod(String toString) {
            this.toString = toString;
        }

        @Override
        public String toString() {
            return toString;
        }
    }

    public enum AccreditationStatus {
        PROCESSING("Processing"),
        VERIFIED("Verified"),
        UPDATES_REQUIRED("Updates required"),
        REJECTED("Rejected"),
        EXPIRED("Expired");

        private final String displayName;

        AccreditationStatus(String displayname) {
            this.displayName = displayname;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public void createInvestor(String accreditationMethod) {
        if (accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.INCOME.name()) |
                accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.NET_WORTH.name()) |
                accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.PROFESSIONAL_LICENSE.name())) {
            startTestLevel("Create new Individual Investor");
            investorDetails = InvestorDetails.generateRandomUSInvestor();
        } else if (
                accreditationMethod.equalsIgnoreCase(EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name()) |
                        accreditationMethod.equalsIgnoreCase(EntityAccreditationMethod.OWNERS_OF_EQUITY_SECURITIES.name())) {
            startTestLevel("Create new Entity Investor");
            investorDetails = InvestorDetails.generateRandomEntityInvestor();
        } else if (accreditationMethod.equalsIgnoreCase(InvestorType.INDIVIDUAL_NON_US.toString())) {
            startTestLevel("Create new NON US Individual Investor");
            investorDetails = InvestorDetails.generateRandomNonUSInvestor();
        }
        InvestorsAPI investorsAPI = new InvestorsAPI();
        try {
            investorsAPI.createNewSecuritizeIdInvestor(this.investorDetails);
        } catch (Exception e) {
            errorAndStop("InvestorAPI wasn't able to create the investor", false);
        }
    }

    public void loginToSecId(String investorMail) {
        startTestLevel("Navigate To SecID and login with created ");
        openBrowser();
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen.clickAcceptCookies()
                .performLoginWithCredentials(investorMail, Users.getProperty(UsersProperty.taInvestorGenericPass), false);
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard
                .clickSkipTwoFactor();
        endTestLevel();
    }

    public void loginToSecId(String investorMail, String password) {
        startTestLevel("Navigate To SecID and login with created ");
        openBrowser();
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen.clickAcceptCookies()
                .performLoginWithCredentials(investorMail, password, false);
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard
                .clickSkipTwoFactor();
        endTestLevel();
    }

    public void navigateToAccreditationPage() {
        startTestLevel("Navigate to Qualification Accreditation Page");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.clickAccount().clickAccreditationQualificationCard();
        endTestLevel();
    }

    public void completeAccreditation(String accreditationMethod) {
        AccreditationPage accreditationPage = new AccreditationPage(getBrowser());
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(getBrowser());
        if (accreditationMethod.equalsIgnoreCase(InvestorType.INDIVIDUAL_NON_US.toString())) {
            startTestLevel("Select 'I consider myself a Professional Investor', NON US Investor");
            // TODO there are config differences between RC and SANBBOX in this radio btn selector.
            accreditationPage.clickIConsiderMyselfProfessionalInvestor().clickSubmitAccreditationStatusButton();
            endTestLevel();
        } else {
            startTestLevel("Select Im Accredited Investor Radio Btn for US Investors");
            accreditationPage.clickIamAccreditedInvestor().clickSubmitAccreditationStatusButton();
            endTestLevel();
        }

        if (accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.INCOME.name()) |
                accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.NET_WORTH.name()) |
                accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.PROFESSIONAL_LICENSE.name()) |
                accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.THIRD_PARTY.name())
        ) {
            startTestLevel("Complete Individual Accreditation " + accreditationMethod);
            accreditationStatusPage.completeIndividualInvestorAccreditation(accreditationMethod);
            endTestLevel();
        } else if (
                accreditationMethod.equalsIgnoreCase(EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name()) |
                accreditationMethod.equalsIgnoreCase(EntityAccreditationMethod.OWNERS_OF_EQUITY_SECURITIES.name()))
        {
            startTestLevel("Complete Entity Accreditation " + accreditationMethod);
            accreditationStatusPage.completeEntityInvestorAccreditation(accreditationMethod);
            endTestLevel();
        }
        if (!accreditationMethod.equalsIgnoreCase(InvestorType.INDIVIDUAL_NON_US.toString())) {
            accreditationStatusPage.clickOnUnderstood();
            assertAccreditationStatusOnSecIdBadge();
        }
    }

    public void assertAccreditationStatusOnSecIdBadge() {
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(getBrowser());
        endTestLevel();
        startTestLevel("Assert Accreditation Status on SecID Badge");
        Assert.assertEquals(accreditationStatusPage.getAccreditationStatusBadge(), AccreditationStatus.PROCESSING.toString());
        endTestLevel();
    }

    public void resetInvestorTestData(String investorMail, String accreditationMethod, String lawyer) {
        if (!accreditationMethod.equalsIgnoreCase(InvestorType.INDIVIDUAL_NON_US.toString())) {
            resetExistingCpMarketsAccreditation(investorMail, lawyer, AccreditationStatus.EXPIRED.toString());
        }
        setMarketsAccreditationStatusTo(investorMail, "none");
    }

    // TODO refactor to POJO model
    public String mapAccreditationMethodString(String accreditationMethod) {
        String accMethod = "";
        if(accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.THIRD_PARTY.name())) {
            accMethod = "Verification Letter";
        } else if(accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.INCOME.name())) {
            accMethod = "Income";
        } else if(accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.NET_WORTH.name())) {
            accMethod = "Net Worth";
        } else if(accreditationMethod.equalsIgnoreCase(IndividualAccreditationMethod.PROFESSIONAL_LICENSE.name())) {
            accMethod = "Professional License";
        } else if(accreditationMethod.equalsIgnoreCase(EntityAccreditationMethod.THIRD_PARTY.name())) {
            accMethod = "Accreditation Letter";
        } else if(accreditationMethod.equalsIgnoreCase(EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name())) {
            accMethod = "Assets And Investments";
        } else if(accreditationMethod.equalsIgnoreCase(EntityAccreditationMethod.OWNERS_OF_EQUITY_SECURITIES.name())) {
            accMethod = "Equity Owners";
        }
        return accMethod;
    }

    public void loginAndCompleteAccreditation(String investorMail, String accreditationMethod, String lawyer) {
        this.accreditationMethod = mapAccreditationMethodString(accreditationMethod);
        resetInvestorTestData(investorMail, accreditationMethod, lawyer);
        loginToSecId(investorMail);
        navigateToAccreditationPage();
        completeAccreditation(accreditationMethod);
    }

    public void accreditationProdSanity(String investorMail, String accreditationMethod) {
        setMarketAccreditationStatusViaUI(investorMail, "None");
        // TODO API call need to be refactored for prod
//        setMarketsAccreditationStatusTo(investorMail, "none");
        loginToSecId(investorMail);
        navigateToAccreditationPage();
        accreditationSanityFlow(accreditationMethod);
    }

    public void setMarketAccreditationStatusViaUI(String investorMail, String status) {
        loginToControlPanel();
        navigateToMarketsInvestors();
        searchMarketInvestor(investorMail);
        clickUniqueMarketsInvestorRow();
        clickMarketsInvestorVerificationTab();
        setAccreditationStatusTo(status);
        logoutFromControlPanelSideMenu();
    }

    public void navigateToMarketsInvestors() {
        CpSideMenu cpSideMenu = new CpSideMenu(getBrowser());
        cpSideMenu.clickMarketsInvestors();
    }

    public void searchMarketInvestor(String investorMail) {
        CpMarkets cpMarkets = new CpMarkets(getBrowser());
        cpMarkets.typeSearchBox(investorMail);
        cpMarkets.waitForUniqueSearchedResult();
    }

    public void clickUniqueMarketsInvestorRow() {
        CpMarkets cpMarkets = new CpMarkets(getBrowser());
        cpMarkets.clickUniqueMarketsInvestorRow();
    }

    public void clickMarketsInvestorVerificationTab() {
        CpMarketsInvestorPage cpMarketsInvestorPage = new CpMarketsInvestorPage(getBrowser());
        cpMarketsInvestorPage.clickInVerificationTab();
    }

    public void setAccreditationStatusTo(String accreditationStatus) {
        CpMarketsInvestorPage cpMarketsInvestorPage = new CpMarketsInvestorPage(getBrowser());
        cpMarketsInvestorPage.clickAccreditationStatusEditBtn();
        cpMarketsInvestorPage.setAccreditationStatus(accreditationStatus);
        cpMarketsInvestorPage.clickAccreditationStatusSaveBtn();
    }

    public void accreditationSanityFlow(String accreditationMethod) {
        AccreditationPage accreditationPage = new AccreditationPage(getBrowser());
        accreditationPage.clickIamAccreditedInvestor().clickSubmitAccreditationStatusButton();
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(getBrowser());
        accreditationStatusPage.completeIndividualInvestorAccreditation(accreditationMethod);
    }

    public void loginAndCompleteAccreditation(String investorMail, String password, String accreditationMethod, String lawyer) {
        this.accreditationMethod = mapAccreditationMethodString(accreditationMethod);
        resetInvestorTestData(investorMail, accreditationMethod, lawyer);
        loginToSecId(investorMail, password);
        navigateToAccreditationPage();
        completeAccreditation(accreditationMethod);
    }

    public void loginToControlPanel() {
        startTestLevel("Login in to CP with AUT Operator");
        String url = MainConfig.getProperty(MainConfigProperty.cpUrl);
        openBrowser();
        getBrowser().navigateTo(url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        loginPage.setUsernameAndPassword();

        if (MainConfig.isProductionEnvironment()) {
            startTestLevel("use Anti-Captcha to solve the reCaptcha");
            AntiCaptchaApi antiCaptchaApi = new AntiCaptchaApi();
            antiCaptchaApi.solveRecaptchaWithRetries(getBrowser().getCurrentUrl(), loginPage);
            endTestLevel();
        } else {
            loginPage.clickSignInButton();
        }

        CpLoginPage2FA loginPage2Fa = new CpLoginPage2FA(getBrowser());
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        endTestLevel();
    }

    public void navigateToMarketsAccreditation() {
        startTestLevel("Navigate to Markets - > Accreditation");
        CpSideMenu cpSideMenu = new CpSideMenu(getBrowser());
        cpSideMenu.clickMarketsAccreditation();
        endTestLevel();
    }

    public void setAccreditationTo(String setAccreditationTo) {
        CpAccreditationDetails accreditationDetails = new CpAccreditationDetails(getBrowser());
        startTestLevel("Set and Submit Accreditation Status from CP");
        if (setAccreditationTo.equalsIgnoreCase(AccreditationStatus.VERIFIED.toString())) {
            accreditationDetails.selectAccreditationStatus(setAccreditationTo);
        } else if (setAccreditationTo.equalsIgnoreCase(AccreditationStatus.UPDATES_REQUIRED.toString())) {
            accreditationDetails.selectAccreditationStatus(AccreditationStatus.UPDATES_REQUIRED.toString());
            Assert.assertFalse(accreditationDetails.isSubmitReviewBtnEnabled());
            accreditationDetails.setMessageForInvestor(updatesRequiredMsg);
        } else if (setAccreditationTo.equalsIgnoreCase(AccreditationStatus.REJECTED.toString())) {
            accreditationDetails.selectAccreditationStatus(AccreditationStatus.REJECTED.toString());
            accreditationDetails.setMessageForInvestor(rejectedMsg);
        } else if (setAccreditationTo.equalsIgnoreCase(AccreditationStatus.EXPIRED.toString())) {
            accreditationDetails.selectAccreditationStatus(AccreditationStatus.EXPIRED.toString());
            accreditationDetails.setMessageForInvestor(expiredMsg);
        }
        accreditationDetails.clickAccreditationSubmitReviewBtn();
        endTestLevel();
    }

    public void openAccreditation(String investorMail, String lawyer, String accreditationInitStatus) {
        startTestLevel("Search and Open Investor Accreditation");
        CpAccreditation cpAccreditation = new CpAccreditation(getBrowser());
        cpAccreditation.waitForAccreditationTableIsLoaded();
        cpAccreditation.searchAccreditationBy(investorMail);
        cpAccreditation.assignLawyer(lawyer);
        Assert.assertTrue(accreditationMethod.equalsIgnoreCase(cpAccreditation.getAccreditationMethodText()));
        Assert.assertTrue(cpAccreditation.getAccreditationStatusBadge().equalsIgnoreCase(accreditationInitStatus),
                "Accreditation Status Badge should be " + accreditationInitStatus + " and is not the case.");
        System.out.println("getAccreditationInvestorNameText " + cpAccreditation.getAccreditationInvestorNameText());
        cpAccreditation.clickViewAccreditationBtn();
        endTestLevel();
    }

    public void openAccreditationAndSubmit(String investorMail, String lawyer, String accreditationInitStatus, String setAccreditationTo) {
        openAccreditation(investorMail, lawyer, accreditationInitStatus);
        setAccreditationTo(setAccreditationTo);
        logoutFromControlPanelSideMenu();
    }

    public void logoutFromControlPanelSideMenu() {
        startTestLevel("Logout from CP");
        CpSideMenu cpSideMenu = new CpSideMenu(getBrowser());
        cpSideMenu.logoutFromCp();
        endTestLevel();
    }

    public void logoutFromControlPanelAccreditation() {
        CpAccreditation cpAccreditation = new CpAccreditation(getBrowser());
        cpAccreditation.logoutFromCP();
    }

    public void setCpAccreditationStatusAndSubmit(String investorMail, String lawyer, String accreditationInitStatus, String setAccreditationTo) {
        loginToControlPanel();
        navigateToMarketsAccreditation();
        openAccreditationAndSubmit(investorMail, lawyer, accreditationInitStatus, setAccreditationTo);
    }

    public void verifySecIdAccreditationStatus(String statusOnAccreditationApp) {

        startTestLevel("Verify Sec ID Accreditation Status for US Investors");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.clickAccount().clickAccreditationQualificationCard();
        AccreditationPage accreditationPage = new AccreditationPage(getBrowser());
        if (statusOnAccreditationApp.equalsIgnoreCase(AccreditationStatus.VERIFIED.name())) {
            accreditationPage.refreshPageToUpdateAccreditationStatus();
            Assert.assertTrue(accreditationPage.isAccreditationStatusVerified());
            getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.accreditationUrl));
            AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(getBrowser());
            Assert.assertEquals(accreditationStatusPage.getAccreditationStatusBadge(), statusOnAccreditationApp);
        } else if (statusOnAccreditationApp.equalsIgnoreCase(AccreditationStatus.REJECTED.name())) {
            securitizeIdDashboard.openUserMenu();
            Assert.assertTrue(accreditationPage.isIAmNotAnAccreditedInvestorRadioBtnChecked());
        } else if (statusOnAccreditationApp.equalsIgnoreCase(AccreditationStatus.EXPIRED.name())) {
            securitizeIdDashboard.openUserMenu();
            Assert.assertTrue(accreditationPage.isIAmAnAccreditedInvestorRadioBtnChecked());
        }
        endTestLevel();
    }

    public void verifySecIdNonUsAccreditationStatus() {
        getBrowser().waitForPageStable();
        final String url = "http://id." + MainConfig.getProperty(MainConfigProperty.environment) + ".securitize.io/#/profile/accreditation";
        getBrowser().navigateTo(url);
        AccreditationPage accreditationPage = new AccreditationPage(getBrowser());
        Assert.assertTrue(accreditationPage.getIConsiderMyselfProfessionalInvestor().isSelected());
    }

    public void provideSecIdAccreditationUpdates(String investorMail, String accreditationMethod) {

        startTestLevel("Extract link from email and navigate");
        CpAccreditation cpAccreditation = new CpAccreditation(getBrowser());
        String link = cpAccreditation.extractLinkFromEmail(investorMail);
        Assert.assertNotNull(link, "Unable to extract link from registration email");
        endTestLevel();
        startTestLevel("Navigate to extracted link from email: " + link);
        // getBrowser().navigateTo("about:blank"); // workaround to a bug of navigation not happening in this page
        getBrowser().navigateTo(link);
        endTestLevel();
        startTestLevel("Validate Updates Required Msg and Provide Updates");
        AccreditationMethodPage accreditationMethodPage = new AccreditationMethodPage(getBrowser());
        Assert.assertEquals(accreditationMethodPage.getUpdatesRequiredMsg(), updatesRequiredMsg);
        accreditationMethodPage.provideUpdates(accreditationMethod);
        endTestLevel();

    }

    public void setMarketsAccreditationStatusTo(String investorMail, String accreditationStatus) {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String securitizeId = investorsAPI.getUniqueSecuritizeIdByEmail(investorMail);
        LoginAPI loginAPI = new LoginAPI();
        Response cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        AccreditationAPI accreditationAPI = new AccreditationAPI();
        accreditationAPI.setMarketsAccreditationStatus(cpLoginResponse, securitizeId, accreditationStatus);
    }

    public void resetExistingCpMarketsAccreditation(String investorMail, String lawyer, String accreditationStatus) {
        startTestLevel("Search Market - > Accreditation");
            loginToControlPanel();
            searchMarketAccreditationByMail(investorMail);
            assignLawyerAndOpenAccreditation(lawyer);
            setAccreditationTo(accreditationStatus);
            logoutFromControlPanelSideMenu();
        endTestLevel();
    }

    public void searchMarketAccreditationByMail(String investorMail) {
        startTestLevel("Navigate to Markets - > Accreditation");
        CpSideMenu cpSideMenu = new CpSideMenu(getBrowser());
        cpSideMenu.clickMarketsAccreditation();
        CpAccreditation cpAccreditation = new CpAccreditation(getBrowser());
        cpAccreditation.waitForAccreditationTableIsLoaded();
        cpAccreditation.searchAccreditationBy(investorMail);
    }

    public void assignLawyerAndOpenAccreditation(String lawyer) {
        CpAccreditation cpAccreditation = new CpAccreditation(getBrowser());
        cpAccreditation.assignLawyer(lawyer);
        cpAccreditation.clickViewAccreditationBtn();
    }


}