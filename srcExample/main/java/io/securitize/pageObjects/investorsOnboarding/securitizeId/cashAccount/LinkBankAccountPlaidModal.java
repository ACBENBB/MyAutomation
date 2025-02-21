package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class LinkBankAccountPlaidModal extends AbstractPage {
    private static final ExtendedBy continueButton = new ExtendedBy("Continue button", By.xpath("//button[@data-test-id='plaid-link-modal-initial-step-continue-button']"));
    private static final ExtendedBy iframePlaid = new ExtendedBy("Plaid iframe", By.xpath("//iframe[@title='Plaid Link']"));
    private static final ExtendedBy generalContinueButton = new ExtendedBy("Genera continue button", By.cssSelector("#aut-button span span"));
    private static final ExtendedBy chaseBankButton = new ExtendedBy("Chase bank button", By.id("aut-ins_56"));
    private static final ExtendedBy userNameField = new ExtendedBy("User Field", By.id("username"));
    private static final ExtendedBy passwordField = new ExtendedBy("Password Field", By.id("password"));
    private static final ExtendedBy signInButton = new ExtendedBy("Sign in button", By.id("submit-credentials"));
    private static final ExtendedBy getCodeButton = new ExtendedBy("Get code button", By.id("submit-device"));
    private static final ExtendedBy submitVerificationButton = new ExtendedBy("Continue button", By.id("submit-code"));
    private static final ExtendedBy checkingAccountCheckbox = new ExtendedBy("Checking checkbox", By.cssSelector("label[for='account_0'] input[type='checkbox']"));
    private static final ExtendedBy accountNameCheckbox = new ExtendedBy("Account holder name checkbox", By.id("name"));
    private static final ExtendedBy accountNumberCheckbox = new ExtendedBy("Account number checkbox", By.id("account-number"));
    private static final ExtendedBy submitAccountsButton = new ExtendedBy("Submit accounts button", By.id("submit-accounts"));
    private static final ExtendedBy termsCheckbox = new ExtendedBy("Terms checkbox", By.id("terms"));
    private static final ExtendedBy connectAccountButton = new ExtendedBy("Connect Account button", By.id("submit-confirmation"));
    private static final ExtendedBy successText = new ExtendedBy("Success text", By.id("a11y-title"));
    private static final ExtendedBy accountYouWantToLink = new ExtendedBy("Link account button", By.cssSelector("[data-test-id*='plaid-link-modal-account-selection-account-card']"));
    private static final ExtendedBy linkAccountButton = new ExtendedBy("Link account button", By.cssSelector("[data-test-id='plaid-link-modal-account-selection-link-button']"));
    private static final ExtendedBy fundMyAccountButton = new ExtendedBy("Fund my account button", By.cssSelector("[data-test-id='plaid-link-modal-success-step-button']"));

    public LinkBankAccountPlaidModal(Browser browser) {
        super(browser, continueButton);
    }

    public void clickContinue1Button() {
        browser.click(continueButton, false);
    }

    public void clickGetStartedButton() {
        browser.switchToFrame(iframePlaid);
        browser.click(generalContinueButton, false);
    }

    public void clickChaseBankButton() {
        browser.click(chaseBankButton, false);
    }

    public void setUserName(String email) {
        browser.typeTextElement(userNameField, email);
    }

    public void setPassword(String password) {
        browser.typeTextElement(passwordField, password);
    }

    public void clickSignIn() {
        browser.click(signInButton, false);
    }

    public void insertBankCredentials() {
        browser.switchToLatestWindow();
        setUserName("user");
        setPassword("user");
        clickSignIn();
    }

    public void clickGetCodeButton() {
        browser.click(getCodeButton, false);
    }

    public void clickSubmitVerificationButton() {
        browser.click(submitVerificationButton, false);
    }

    public void selectCheckingAccount() {
        browser.click(checkingAccountCheckbox, false);
    }

    public void selectNameCheckbox() {
        browser.click(accountNameCheckbox, false);
    }

    public void selectAccountNumberCheckbox() {
        browser.click(accountNumberCheckbox, false);
    }

    public void clickSubmitButton() {
        browser.click(submitAccountsButton, false);
    }

    public void selectTermsCheckbox() {
        browser.click(termsCheckbox, false);
    }

    public void clickConnectAccountButton() {
        browser.click(connectAccountButton, false);
        browser.switchToFirstWindow();
        browser.switchToFrame(iframePlaid);
    }

    public void selectAccountYouWantToLink() {
        //Need to use this click method because browser.click() is not working.
        browser.findElement(accountYouWantToLink).click();
    }

    public void clickLinkAccountButton() {
        browser.click(linkAccountButton);
    }

    public void clickFundMyAccountButton() {
        browser.click(fundMyAccountButton);
    }

    public void clickContinueToLoginButton() {
        browser.click(generalContinueButton, false);
        browser.switchToLatestWindow();
    }

    public void clickContinueButton() {
        browser.waitForElementVisibility(successText);
        browser.click(generalContinueButton);
        browser.switchBackToDefaultContent();
    }

    public void linkBankAccount() {
        clickContinue1Button();
        clickGetStartedButton();
        clickChaseBankButton();
        clickContinueToLoginButton();
        insertBankCredentials();
        clickGetCodeButton();
        clickSubmitVerificationButton();
        selectCheckingAccount();
        selectNameCheckbox();
        selectAccountNumberCheckbox();
        clickSubmitButton();
        selectTermsCheckbox();
        clickConnectAccountButton();
        clickContinueButton();
        selectAccountYouWantToLink();
        clickLinkAccountButton();
        clickFundMyAccountButton();
    }


}
