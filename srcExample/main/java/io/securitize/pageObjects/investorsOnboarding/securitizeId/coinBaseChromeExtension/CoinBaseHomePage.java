package io.securitize.pageObjects.investorsOnboarding.securitizeId.coinBaseChromeExtension;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class CoinBaseHomePage extends AbstractPage<CoinBaseHomePage> {

    // welcome page
    private static final ExtendedBy createNewWalletButton = new ExtendedBy("Create new wallet button", By.xpath("//button[@data-testid='btn-create-new-wallet']"));

    // username page
    private static final ExtendedBy usernameField = new ExtendedBy("Username field", By.xpath("//input[@data-testid='username-input']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Submit button", By.xpath("//button[@data-testid='btn-submit-username']"));

    // words page
    private static final ExtendedBy showWordsButton = new ExtendedBy("Show words button", By.xpath("//span[@data-icon-name='visibleInactive']"));
    private static final ExtendedBy wordsList = new ExtendedBy("List of words", By.xpath("//div[@data-testid='blur-text-content']"));
    private static final ExtendedBy acceptWordsCheckbox = new ExtendedBy("Accept words checkbox", By.xpath("//div[@data-testid='backup-checkbox-parent']"));
    private static final ExtendedBy backupContinueButton = new ExtendedBy("Backup continue button", By.xpath("//button[@data-testid='backup-continue-button']"));

    // verify words page
    private static final String wordXpathLocator = "//button[@data-testid='mnemonic-word-%s']";
    private static final ExtendedBy submitWordsButton = new ExtendedBy("Submit words button", By.xpath("//button[@data-testid='btn-verify-continue']"));

    // password page
    private static final ExtendedBy passwordField = new ExtendedBy("new password field", By.xpath("//input[@data-testid='setPassword']"));
    private static final ExtendedBy verifyPasswordField = new ExtendedBy("verify new password field", By.xpath("//input[@data-testid='setPasswordVerify']"));
    private static final ExtendedBy agreeTermsCheckbox = new ExtendedBy("Accept terms checkbox", By.xpath("//div[@data-testid='terms-and-privacy-policy-parent']"));
    private static final ExtendedBy submitPasswordButton = new ExtendedBy("Submit password button", By.xpath("//button[@data-testid='btn-password-continue']"));

    private static final ExtendedBy addFundsButton = new ExtendedBy("Add funds button", By.xpath("//button[@data-testid='add-funds-receive-btn']"));

    public CoinBaseHomePage(Browser browser) {
        super(browser, createNewWalletButton);
    }

    public static CoinBaseHomePage openCoinBaseInNewTab(Browser browser) {
        browser.openNewTabAndSwitchToIt();
        String coinbaseUrl = browser.getInstalledExtensionUrl("Coinbase Wallet extension");
        browser.navigateTo(coinbaseUrl + "/index.html");
        return new CoinBaseHomePage(browser);
    }

    public CoinBaseHomePage clickCreateNewWalletButton() {
        browser.click(createNewWalletButton, false);
        return this;
    }

    public CoinBaseHomePage setUsername(String value) {
        browser.typeTextElement(usernameField, value);
        return this;
    }

    public CoinBaseHomePage clickSubmit() {
        browser.click(submitButton, false);
        return this;
    }

    public CoinBaseHomePage clickShowWords() {
        browser.click(showWordsButton, false);
        return this;
    }

    public String[] getWords() {
        return browser.getElementText(wordsList).split(" ");
    }

    public CoinBaseHomePage clickAcceptWordsCheckbox() {
        browser.click(acceptWordsCheckbox, false);
        return this;
    }

    public CoinBaseHomePage clickContinue() {
        browser.click(backupContinueButton, false);
        return this;
    }

    public CoinBaseHomePage clickOnWord(String value) {
        String fullXpathLocator = String.format(wordXpathLocator, value);
        ExtendedBy locator = new ExtendedBy("word locator - " + value, By.xpath(fullXpathLocator));

        browser.click(locator, false);
        return this;
    }

    public CoinBaseHomePage clickSubmitWords() {
        browser.click(submitWordsButton, false);
        return this;
    }

    public CoinBaseHomePage setPassword(String value) {
        browser.typeTextElement(passwordField, value);
        return this;
    }

    public CoinBaseHomePage setVerifyPassword(String value) {
        browser.typeTextElement(verifyPasswordField, value);
        return this;
    }

    public CoinBaseHomePage acceptTerms() {
        browser.click(agreeTermsCheckbox, false);
        return this;
    }


    public void clickSubmitPasswordButton() {
        browser.click(submitPasswordButton, false);
    }

    public String performFullRegistration(String username, String password) {
        String[] words = clickCreateNewWalletButton()
                .setUsername(username)
                .clickSubmit()
                .clickShowWords()
                .getWords();

        clickAcceptWordsCheckbox()
                .clickContinue()
                .clickOnWord(words[0])
                .clickOnWord(words[words.length - 1])
                .clickSubmitWords()

                .setPassword(password)
                .setVerifyPassword(password)
                .acceptTerms()
                .clickSubmitPasswordButton();

        Credentials credentials = WalletUtils.loadBip39Credentials(password, String.join(" ", words));

        // workaround for coinBase extension bug - without this code extension gets stuck on the
        // "please pin coinBase extension message"
        browser.waitForElementVisibility(addFundsButton, 90);
        browser.refreshPage();
        browser.waitForElementVisibility(addFundsButton, 90);

        return credentials.getAddress();
    }
}