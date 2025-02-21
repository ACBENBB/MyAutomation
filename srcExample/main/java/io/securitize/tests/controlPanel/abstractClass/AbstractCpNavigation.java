package io.securitize.tests.controlPanel.abstractClass;

import io.securitize.infra.api.anticaptcha.AntiCaptchaApi;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.Users;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.tests.abstractClass.AbstractUiTest;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public abstract class AbstractCpNavigation extends AbstractUiTest {

    private String firstName;
    private CpInvestorDetailsPage investorDetailsPage;
    private CpInvestorDetailsPageNewUI investorDetailsPageNewUI;
    private String tokenName;

    public void navigateToPage(String pageName) {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.navigateToPageInSideMenu(pageName);
    }

    public void cpNavigateToSecuritiesTransactions() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickSecuritiesTransactions();
    }

    public void cpNavigateToMarketsOverview() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickMarketsOverview();
    }

    public void cpNavigateToConfigurationFundraise() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickConfigurationFundraise();
    }

    public CpSignatures cpNavigateToSignaturesScreen() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        return sideMenu.clickSignatures();
    }

    public void cpNavigateToOnboarding() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickOnBoarding();
    }

    public void cpNavigateToAffiliateManagement() {
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickAffiliateManagement();
    }

    public void cpLoginUsingEmailAndPassword() {
        cpLoginUsingEmailAndPassword(null, null);
    }

    public void cpLoginUsingEmailAndPassword(String email, String password) {

        startTestLevel("Login to control panel using email and password + 2FA ");
        String url = MainConfig.getCpUrl();
        getBrowser().navigateTo(url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());

        if (!MainConfig.isProductionEnvironment()) {
            if (email == null) {
                loginPage.loginCpUsingEmailPassword(false);
            } else {
                loginPage.loginCpUsingEmailPassword(email, password, false);
            }
        } else {
            loginPage.setUsernameAndPassword(email, password);
            startTestLevel("use Anti-Captcha to solve the reCaptcha");
            AntiCaptchaApi antiCaptchaApi = new AntiCaptchaApi();
            antiCaptchaApi.solveRecaptchaWithRetries(getBrowser().getCurrentUrl(), loginPage);
            endTestLevel();
        }
        endTestLevel();


        startTestLevel("Populate Login 2FA with the secret key");
        CpLoginPage2FA loginPage2Fa = new CpLoginPage2FA(getBrowser());
        loginPage2Fa.obtainPrivateKey().generate2FACode().setTwoFaCodeInUI();
        endTestLevel();
    }

    public void cpSelectIssuer(String issuerName, boolean isPropertyNeeded) {
        if (!isPropertyNeeded) {
            startTestLevel("Select Issuer");
            CpSelectIssuer selectIssuer = new CpSelectIssuer(getBrowser());
            selectIssuer.clickViewIssuerByName(issuerName);
            endTestLevel();
        } else {
            cpSelectIssuer(issuerName);
        }
    }

    public void cpSelectIssuer(String issuerName) {

        startTestLevel("Select Issuer");
        String issuerFullName = Users.getIssuerDetails(issuerName, IssuerDetails.issuerFullName);
        CpSelectIssuer selectIssuer = new CpSelectIssuer(getBrowser());
        selectIssuer.clickViewIssuerByName(issuerFullName);
        endTestLevel();
    }


    public void navigateInvestorDirectUrl(String url) {
        if (!getBrowser().getPageTitle()
                .contains("Details".toLowerCase())) {
            if (url != null) {
                getBrowser().navigateTo(url);
                getBrowser().waitForPageStable();
                investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
            }
        }
    }

    public void navigateNewInvestorDirectUrl(String url) {
        if (!getBrowser().getPageTitle()
                .equals("Investor Details".toLowerCase())) {
            getBrowser().navigateTo(url);
            getBrowser().waitForPageStable();
            investorDetailsPageNewUI = new CpInvestorDetailsPageNewUI(getBrowser());
        }
        if (investorDetailsPageNewUI == null) {
            investorDetailsPageNewUI = new CpInvestorDetailsPageNewUI(getBrowser());
        }
    }

    public String getTokenName(String tokenTicker) {
        switch (tokenTicker) {
            case "TTk1":
                tokenName = "TestToken1";
                break;
            case "SBT1":
                tokenName = "SBToken1";
                break;
            default:
                tokenName = "Unknown token ticker, please add manually";
                break;
        }
        return tokenName;
    }
}
