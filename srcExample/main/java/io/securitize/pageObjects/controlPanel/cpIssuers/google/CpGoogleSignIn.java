package io.securitize.pageObjects.controlPanel.cpIssuers.google;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.utils.Authentication;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpGoogleSignIn extends AbstractPage<CpGoogleSignIn> {

    private static final ExtendedBy emailField = new ExtendedBy("email field", By.id("identifierId"));
    private static final ExtendedBy passwordField = new ExtendedBy("password field", By.xpath("//input[@name='password' or @name='Passwd']"), true);
    private static final ExtendedBy mfaField = new ExtendedBy("2 factor authentication field", By.name("totpPin"));
    private static final ExtendedBy continueButton = new ExtendedBy("continue button - right before sign-in", By.xpath("//button/span[contains(text(), 'Continue')]"));
    private static final ExtendedBy nextButton = new ExtendedBy("next button", By.xpath("//div[@id='identifierNext' or @id='passwordNext']"));
    private static final ExtendedBy mfaNextButton = new ExtendedBy("2 factor authentication - next button", By.xpath("//button/span[contains(text(), 'Next')]"));

    public CpGoogleSignIn(Browser browser) {
        super(browser, emailField);
    }

    public void typeEmail(String email) {
        browser.typeTextElement(emailField, email);
    }

    public void typePassword(String password) {
        browser.typeTextElement(passwordField, password);
    }

    public void type2FaSecret(String value) {
        browser.typeTextElement(mfaField, value);
    }

    public void clickNext() {
        browser.click(nextButton, false);
    }

    public void click2FaNext() {
        browser.clickAndWaitForElementToVanish(mfaNextButton, ExecuteBy.WEBDRIVER, false);
    }

    public void clickContinue() {
        browser.waitForPageStable();
        // The condition will avoid test failing if this confirmation will be removed in the future
        if (browser.isElementVisible(continueButton)) {
            browser.click(continueButton);
        }
    }

    public void performLogin(String email, String password, String mfaSecret) {
        int numberOfOpenWindows = browser.getNumberOfOpenWindows();
        typeEmail(email);
        clickNext();
        typePassword(password);
        clickNext();
        type2FaSecret(Authentication.getTOTPCode(mfaSecret));
        click2FaNext();
        // if the 'give permissions to Securitize to access Google account' tab appears - handle it
        giveGooglePermissionsToSecuritize();
        browser.switchToFirstWindow();
    }

    private void giveGooglePermissionsToSecuritize() {
        var elements = browser.findElementsQuick(continueButton, 5);
        if (!elements.isEmpty()) {
            browser.click(continueButton);
        }
    }
}
