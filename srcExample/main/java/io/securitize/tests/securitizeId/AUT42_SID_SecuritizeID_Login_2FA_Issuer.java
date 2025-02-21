package io.securitize.tests.securitizeId;

import io.securitize.infra.config.*;
import io.securitize.pageObjects.investorsOnboarding.nie.NieWelcomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import io.securitize.tests.InvestorDetails;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT42_SID_SecuritizeID_Login_2FA_Issuer extends AbstractSecIdInvestorRegistrationFlow {

    @Test(description = "AUT42 - Securitize iD Login Test", groups = {"coreSID"})
    public void AUT42_SID_SecuritizeID_Login_2FA_Issuer_Test() {

        String issuerName = "Nie";
        String state = "Alaska";
        InvestorDetails investorDetails = performSecuritizeIdCreateAccountAndLogin("randomUSInvestor", false, state);

        startTestLevel("Set up two factor authentication");
        SecuritizeIdSettings securitizeIDSettings = new SecuritizeIdSettings(getBrowser());
        securitizeIDSettings
                .clickEnableTwoFactorPopUp()
                .setTwoFaPassword(investorDetails.getPassword())
                .clickTwoFaPasswordNext();
        SecuritizeIdTwoFactorPopUp securitizeIDTwoFactorPopUp = new SecuritizeIdTwoFactorPopUp(getBrowser());
        securitizeIDTwoFactorPopUp
                .obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        endTestLevel();


        startTestLevel("Login to issuer page");
        getBrowser().navigateTo(MainConfig.getInvestPageUrl(issuerName));
        NieWelcomePage welcomePage = new NieWelcomePage(getBrowser());
        welcomePage
                .clickLogIn()
                .loginWithRememberedUserRedirectToNiePlatformHomePage(investorDetails.getEmail())
                .performLogoutIncludingClearCookies();
        endTestLevel();


        startTestLevel("Relogin to issuer page - using fresh login");
        NieWelcomePage welcomePageSecond = new NieWelcomePage(getBrowser());
        welcomePageSecond
                .clickLogInWithoutAccountSelector()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), securitizeIDTwoFactorPopUp.getTwoFaPrivateKey(), true)
                .performLogoutIncludingClearCookies();
        endTestLevel();


        startTestLevel("Login to Securitize ID using email and password (inc. 2FA)");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentialsOnlySID(investorDetails.getEmail(), investorDetails.getPassword(), securitizeIDTwoFactorPopUp.getTwoFaPrivateKey(), true);
        endTestLevel();


        startTestLevel("Disable 2FA Password");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard
                .goToSettingPage()
                .clickSettingsLoginInformationCard();
        securitizeIDSettings
                .toggle2Fa()
                .setTwoFaPassword(investorDetails.getPassword())
                .clickTwoFaPasswordNext();
        SecuritizeIdTwoFactorPopUp securitizeIDTwoFactorPopUpSecond = new SecuritizeIdTwoFactorPopUp(getBrowser());
        securitizeIDTwoFactorPopUpSecond
                .generate2FACode(securitizeIDTwoFactorPopUp.getTwoFaPrivateKey())
                .setTwoFaCodeInUI();
        securitizeIdDashboard
                .clickHomeLink()
                .performLogoutIncludingClearCookies();
        endTestLevel();


        startTestLevel("Relogin to Securitize ID - using fresh login");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreenSecond = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreenSecond
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), true)
                .waitForSIDToLogin();
        endTestLevel();
    }
}