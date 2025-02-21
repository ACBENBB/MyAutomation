package io.securitize.tests.fundraise;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.sumsub.SumsubUniqueImage;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.pageObjects.investorsOnboarding.nie.NieDashboard;
import io.securitize.pageObjects.investorsOnboarding.nie.NieDashboardDocuments;
import io.securitize.pageObjects.investorsOnboarding.nie.NieWelcomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdLoginScreenLoggedIn;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;


public class AUT229_FT_ControlPanel_InvestorDocuments extends AbstractSecIdNieSharedFlow {

    private static final String ISSUER_NAME = "Nie";

    @Test(description = "AUT229 - Add/Edit/Delete Investor documents")
    public void AUT229_FT_ControlPanel_InvestorDocuments() throws Exception {
        String documentName = "This is the document title by Automation";
        String currentDate = DateTimeUtils.currentDate("d  MMM, yyyy");
        // Format of document should be: dayWithSuffix Mon, Year, like: 2nd Nov, 2021
        String documentDate = currentDate.replace("  ", DateTimeUtils.getDaySuffix(DateTimeUtils.currentDate("d")) + " ");

        startTestLevel("Create investor");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        endTestLevel();

        startTestLevel("Login to SiD and then to issuer page");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false)
                .waitForSIDToLogin()
                .clickSkipTwoFactor()
                .clickHomeLink()
                .performLogout();

        getBrowser().navigateTo(MainConfig.getInvestPageUrl(ISSUER_NAME));
        NieWelcomePage welcomePage = new NieWelcomePage(getBrowser());
        welcomePage
                .clickLogInWithoutAccountSelector()
                .performLoginWithCredentials(investorDetails.getPassword());
        SecuritizeIdLoginScreenLoggedIn securitizeIdLoginScreenLoggedIn = new SecuritizeIdLoginScreenLoggedIn(browser.get());
        securitizeIdLoginScreenLoggedIn.clickAllowButton();
        endTestLevel();

        startTestLevel("Open control panel -> Login");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl));
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        endTestLevel();

        startTestLevel("Open issuer and specific investor details");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        String issuerFullName = Users.getIssuerDetails(ISSUER_NAME, IssuerDetails.issuerFullName);
        CpSelectIssuer selectIssuer = sideMenu.clickIssuers();
        selectIssuer.clickViewIssuerByName(issuerFullName);
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        CpInvestorDetailsPage investorDetailsPage = onBoarding.clickShowInvestorDetailsByName(investorDetails.getFirstName());
        endTestLevel();

        startTestLevel("Add Document");
        int imageIndex = SumsubUniqueImage.getDriverLicenseFrontIndex();
        String documentImage = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-front" + imageIndex + ".jpg");
        CpAddDocument addDocument = investorDetailsPage
                .clickAddDocument()
                .typeDocumentTitle(documentName)
                .pickShowToInvestor()
                .uploadImage(documentImage);
        String categoryPicked = addDocument.selectRandomCategory();
        addDocument
                .clickOk();
        endTestLevel();

        startTestLevel("Validate Document from the UI");
        getBrowser().openNewTabAndSwitchToIt(MainConfig.getInvestPageUrl(ISSUER_NAME));
        NieDashboard nieDashboardSecond = new NieDashboard(getBrowser());
        NieDashboardDocuments nieDashboardDocuments = nieDashboardSecond.switchToDocumentsTab();

        Assert.assertEquals(documentName, nieDashboardDocuments.getDocumentName("1"));
        Assert.assertEquals(documentDate, nieDashboardDocuments.getDocumentDate("1"));
        endTestLevel();

        startTestLevel("Validate Document Download");
        nieDashboardDocuments.clickDownload("1");
        getBrowser().switchToLatestWindow();
        nieDashboardDocuments.waitForDocumentImageToBeDisplayed();
        endTestLevel();
    }
}