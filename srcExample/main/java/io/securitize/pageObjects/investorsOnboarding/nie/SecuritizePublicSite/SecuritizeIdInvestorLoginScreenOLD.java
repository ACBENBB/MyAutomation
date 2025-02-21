package io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import io.securitize.pageObjects.investorsOnboarding.nie.NieDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdLoginScreenLoggedIn;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdTwoFactorPopUp;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorLoginScreenOLD extends SecuritizeIdInvestorAbstractPage<SecuritizeIdInvestorLoginScreenOLD> {

    private static final ExtendedBy emailTextBox = new ExtendedBy("Securitize Id - Email text box", By.id("login-email"));
    private static final ExtendedBy passwordTextBox = new ExtendedBy("Securitize Id - Password field", By.id("login-password"), true);
    private static final ExtendedBy loginButton = new ExtendedBy("Securitize Id - Login button", By.id("login-submit"));
    private static final ExtendedBy registrationButton = new ExtendedBy("Securitize Id - Registration button", By.id("link-registration"));

    public SecuritizeIdInvestorLoginScreenOLD(Browser browser) {
        super(browser, loginButton);
    }


    private void setEmail(String email, Boolean clearEmail){
        if (clearEmail) {
            // regular clear doesn't seem to work here so we need advanced clearing logic
            browser.clearTextElement(emailTextBox);
        }
        browser.typeTextElement(emailTextBox, email);
    }

    public void setPassword(String password){
        browser.typeTextElement(passwordTextBox, password);
    }

    public void clickLoginButton(){
        browser.click(loginButton, false);
    }

    private void clickLoginButton(String twoFaPrivateKey){
        browser.click(loginButton, false);
        SecuritizeIdTwoFactorPopUp securitizeIDTwoFactorPopUp = new SecuritizeIdTwoFactorPopUp(browser);
        securitizeIDTwoFactorPopUp
                .generate2FACode(twoFaPrivateKey)
                .setTwoFaCodeInUI();
        new SecuritizeIdLoginScreenLoggedIn(browser);
    }

    @SuppressWarnings("unused")
    public SecuritizeIdInvestorLoginScreenOLD disableReCaptcha() {
        browser.disableReCaptcha();
        return this;
    }

    //In case the email is already remembered, you only need the password to login
    public SecuritizeIdInvestorLoginScreenOLD performLoginWithCredentials(String password){
        setPassword(password);
        clickLoginButton();
        return this;
    }

    //In case it is a brand new login, you need email and password to perform the login
    public SecuritizeIdInvestorLoginScreenOLD performLoginWithCredentials(String email, String password, Browser browser, Boolean clearEmail) {
        return performLoginWithCredentials(email, password, browser, clearEmail, true);
    }

    public SecuritizeIdInvestorLoginScreenOLD performLoginWithCredentials(String email, String password, Browser browser, Boolean clearEmail, Boolean isSkip2Fa) {
        setEmail(email, clearEmail);
        setPassword(password);
        clickLoginButton();
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(browser);
        if (isSkip2Fa) {
            securitizeIDDashboard.clickSkipTwoFactor();
        }
        return this;
    }

    //In case it is a brand new login and has 2FA
    public NieDashboard performLoginWithCredentials(String email, String password, String twoFaPrivateKey, Boolean clearEmail) {
        setEmail(email, clearEmail);
        setPassword(password);
        clickLoginButton(twoFaPrivateKey);
        SecuritizeIdLoginScreenLoggedIn securitizeIdLoginScreenLoggedIn = new SecuritizeIdLoginScreenLoggedIn(browser);
        return securitizeIdLoginScreenLoggedIn.loginWithRememberedUserRedirectToNiePlatformHomePage(email);
    }
}