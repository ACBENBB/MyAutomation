package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import io.securitize.pageObjects.investorsOnboarding.nie.NieDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorLoginScreen extends SecuritizeIdInvestorAbstractPage<SecuritizeIdInvestorLoginScreen> {

    private static final ExtendedBy emailTextBox = new ExtendedBy("Securitize Id - Email text box", By.id("login-email"));
    private static final ExtendedBy passwordTextBox = new ExtendedBy("Securitize Id - Password field", By.id("login-password"), true);
    private static final ExtendedBy loginButton = new ExtendedBy("Securitize Id - Login button", By.id("login-submit"));
    public static final ExtendedBy registrationButton = new ExtendedBy("Securitize Id - Registration button", By.id("link-registration"));


    public SecuritizeIdInvestorLoginScreen(Browser browser) {
        super(browser, registrationButton);
    }


    public boolean isAcceptCookiesButtonPresent() {
        return !browser.findElements(acceptCookiesButton).isEmpty();
    }

    public SecuritizeIdCreateAccountRegistrationStep1 clickCreateAccount() {
        browser.click(registrationButton);
        return new SecuritizeIdCreateAccountRegistrationStep1(browser);
    }



    private void setEmail(String email, Boolean clearEmail){
        if (clearEmail) {
            // regular clear doesn't seem to work here so we need advanced clearing logic
            browser.clearTextInput(emailTextBox);
        }
        browser.typeTextElement(emailTextBox, email);
    }

    public void setPassword(String password){
        browser.typeTextElement(passwordTextBox, password);
    }

    public void clickLoginButton(){
        browser.click(loginButton, false);
    }

    private NieDashboard clickLoginButton(String twoFaPrivateKey){
        browser.click(loginButton, false);
        SecuritizeIdTwoFactorPopUp securitizeIDTwoFactorPopUp = new SecuritizeIdTwoFactorPopUp(browser);
        securitizeIDTwoFactorPopUp
                .generate2FACode(twoFaPrivateKey)
                .setTwoFaCodeInUI();
        return new NieDashboard(browser);
    }

    //without NieDashboard object
    private SecuritizeIdInvestorLoginScreen clickLoginButtonOnlySID(String twoFaPrivateKey){
        browser.click(loginButton, false);
        SecuritizeIdTwoFactorPopUp securitizeIDTwoFactorPopUp = new SecuritizeIdTwoFactorPopUp(browser);
        securitizeIDTwoFactorPopUp
                .generate2FACode(twoFaPrivateKey)
                .setTwoFaCodeInUI();
        return this;
    }

    @SuppressWarnings("unused")
    public SecuritizeIdInvestorLoginScreen disableReCaptcha() {
        browser.disableReCaptcha();
        return this;
    }

    //In case the email is already remembered, you only need the password to login
    public SecuritizeIdInvestorLoginScreen performLoginWithCredentials(String password){
        setPassword(password);
        clickLoginButton();
        return this;
    }

    //In case it is a brand new login, you need email and password to perform the login
    public SecuritizeIdInvestorLoginScreen performLoginWithCredentials(String email, String password, Boolean clearEmail) {
        setEmail(email, clearEmail);
        setPassword(password);
        clickLoginButton();
        return this;
    }

    //In case it is a brand new login and has 2FA
    public NieDashboard performLoginWithCredentials(String email, String password, String twoFaPrivateKey, Boolean clearEmail) {
        setEmail(email, clearEmail);
        setPassword(password);
        clickLoginButton(twoFaPrivateKey);
        return new NieDashboard(browser);
    }

    //In case it is a brand new login and has 2FA - without NieDashboard object
    public SecuritizeIdInvestorLoginScreen performLoginWithCredentialsOnlySID(String email, String password, String twoFaPrivateKey, Boolean clearEmail) {
        setEmail(email, clearEmail);
        setPassword(password);
        clickLoginButtonOnlySID(twoFaPrivateKey);
        return this;
    }

    public SecuritizeIdDashboard waitForSIDToLogin(){
        return new SecuritizeIdDashboard(browser);
    }

    public NieDashboard waitForNIELogin(){
        return new NieDashboard(browser);
    }

}