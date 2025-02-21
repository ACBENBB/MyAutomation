package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdLoginScreenLoggedIn;
import org.openqa.selenium.By;

public class NieWelcomePage extends AbstractPage<NieWelcomePage> {

    private static final ExtendedBy createAccountButton = new ExtendedBy("Create a Securitize ID button", By.id("btn-create-securitize-id"));
    private static final ExtendedBy loginButton = new ExtendedBy("Log-in button", By.id("btn-log-in"));

    public NieWelcomePage(Browser browser) {
        super(browser, createAccountButton);
    }

    public SecuritizeIdCreateAccountRegistrationStep1 ClickCreateAccount() {
        browser.switchToFirstWindow();
        browser.click(createAccountButton, false);
        return new SecuritizeIdCreateAccountRegistrationStep1(browser);
    }

    public SecuritizeIdLoginScreenLoggedIn clickLogIn() {
        browser.click(loginButton, false);
        return new SecuritizeIdLoginScreenLoggedIn(browser);
    }

    /* When you do not see the "Account Selector", you get just redirected to the login(Log In button) in authorize page: #/authorize?issuerId=... */
    public SecuritizeIdInvestorLoginScreen clickLogInWithoutAccountSelector() {
        browser.click(loginButton, false);
        return new SecuritizeIdInvestorLoginScreen(browser);
    }

    /* When you do not see the "Account Selector", you get just redirected to the authorize(Allow button)in authorize page: #/authorize?issuerId=... */
    public SecuritizeIdLoginScreenLoggedIn clickLogInWithoutAccountSelectorDirectAccess() {
        browser.click(loginButton, false);
        return new SecuritizeIdLoginScreenLoggedIn(browser);
    }


}
