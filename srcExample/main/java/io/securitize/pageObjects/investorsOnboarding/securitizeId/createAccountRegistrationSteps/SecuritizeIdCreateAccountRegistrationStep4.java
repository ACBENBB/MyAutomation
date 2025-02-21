package io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdCreateAccountRegistrationStep4 extends AbstractPage {
    public static final ExtendedBy emailTextBox = new ExtendedBy("Securitize Id - Create account registration step 4- Insert email", By.id("registration-email"));
    public static final ExtendedBy passwordTextBox = new ExtendedBy("Securitize Id - Create account registration step 4 - Insert password", By.id("registration-password"), true);
    public static final ExtendedBy submitButton = new ExtendedBy("Securitize Id - Create account registration step 4- Submit button", By.id("registration-submit"));

    public SecuritizeIdCreateAccountRegistrationStep4(Browser browser) {
        super(browser, emailTextBox);
    }

    public SecuritizeIdCreateAccountRegistrationStep4 insertEmailAddress(String value) {
        browser.typeTextElement(emailTextBox, value);
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep4 insertPassword(String value) {
        browser.typeTextElement(passwordTextBox, value);
        return this;
    }

    public void clickSubmitButton() {
        browser.click(submitButton, false);
    }

}
