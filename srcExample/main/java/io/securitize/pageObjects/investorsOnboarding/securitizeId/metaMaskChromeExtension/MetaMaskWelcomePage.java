package io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class MetaMaskWelcomePage extends AbstractPage<MetaMaskWelcomePage> {

    private static final ExtendedBy getStartedButton = new ExtendedBy("Get Started button", By.xpath("//button[contains(@class, 'btn-primary')]"));
    private static final ExtendedBy createAWalletButton = new ExtendedBy("Create A Wallet button", By.xpath("//button[contains(text(), 'Create')]"));
    private static final ExtendedBy NoToDataSharingButton = new ExtendedBy("No Thanks - don't share data button", By.xpath("//button[contains(text(), 'No thanks')]"));

    private static final ExtendedBy newPasswordField = new ExtendedBy("New password field", By.xpath("//input[@data-testid='create-password-new']"), true);
    private static final ExtendedBy confirmPasswordField = new ExtendedBy("Confirm password field", By.xpath("//input[@data-testid='create-password-confirm']"), true);
    private static final ExtendedBy agreeToTermsCheckbox = new ExtendedBy("Set password - Agree to terms checkbox", By.xpath("//input[@data-testid='create-password-terms']"));
    private static final ExtendedBy onboardingIAgreeTermsCheckbox = new ExtendedBy("onboarding - Agree to terms checkbox", By.xpath("//input[@id='onboarding__terms-checkbox']"));
    private static final ExtendedBy setPasswordButton = new ExtendedBy("Set password button", By.xpath("//button[@data-testid='create-password-wallet']"));

    private static final ExtendedBy remindMeLaterButton = new ExtendedBy("Remind me later button", By.xpath("//div[@class='reveal-seed-phrase__reveal-button']"));
    private static final ExtendedBy secureMyWalletButton = new ExtendedBy("Secure my wallet button", By.xpath("//button[@data-testid='secure-wallet-recommended']"));
    private static final ExtendedBy revealSecretWordsButton = new ExtendedBy("Reveal secret recovery phrase button", By.xpath("//button[@data-testid='recovery-phrase-reveal']"));
    private static final ExtendedBy secretWordsFields = new ExtendedBy("Secret words fields", By.xpath("//div[contains(@data-testid, 'recovery-phrase-chip-')]"));
    private static final ExtendedBy missingSecretWordsFields = new ExtendedBy("Missing secret words fields", By.xpath("//input[contains(@data-testid, 'recovery-phrase-input-')]"));
    private static final ExtendedBy nextButton = new ExtendedBy("Next button", By.xpath("//button[text()='Next']"));
    private static final ExtendedBy doneButton = new ExtendedBy("Done button", By.xpath("//button[text()='Done']"));

    private static final String secretWordXpathTemplate = "//div[@data-testid='seed-phrase-sorted']/div[@data-testid='draggable-seed-%s']";
    private static final ExtendedBy confirmButton = new ExtendedBy("Confirm button", By.xpath("//button[@data-testid='recovery-phrase-confirm']"));
    private static final ExtendedBy allDoneButton = new ExtendedBy("All Done button", By.xpath("//button[@data-testid='onboarding-complete-done']"));


    public MetaMaskWelcomePage(Browser browser) {
        super(browser, onboardingIAgreeTermsCheckbox);
    }

    @SuppressWarnings("unused")
    public static MetaMaskWelcomePage openMetaMask(Browser browser) {
        String metamaskUrl = browser.getInstalledExtensionUrl("MetaMask");
        browser.navigateTo(metamaskUrl + "/home.html#initialize/welcome");
        return new MetaMaskWelcomePage(browser);
    }

    public static MetaMaskWelcomePage openMetaMaskInNewTab(Browser browser) {
        // splitting it to two steps of opening a new tab and then navigating to metaMask via WebDriver
        // otherwise the navigation gets blocked by Chrome
        browser.openNewTabAndSwitchToIt();
        return openMetaMask(browser);
    }

    public MetaMaskWelcomePage checkOnBoardingIAgreeTerms() {
        browser.click(onboardingIAgreeTermsCheckbox);
        return this;
    }

    public MetaMaskWelcomePage clickGetStarted() {
        browser.click(getStartedButton);
        return this;
    }

    public MetaMaskWelcomePage clickCreateAWallet() {
        browser.click(createAWalletButton);
        return this;
    }

    public MetaMaskWelcomePage clickNoThanksToDataSharing() {
        browser.click(NoToDataSharingButton);
        return this;
    }

    public MetaMaskWelcomePage setPassword(String password) {
        browser.typeTextElement(newPasswordField, password);
        browser.typeTextElement(confirmPasswordField, password);
        browser.click(agreeToTermsCheckbox);
        browser.click(setPasswordButton);
        return this;
    }

    public MetaMaskWelcomePage clickSecureMyWallet() {
        browser.click(secureMyWalletButton);
        return this;
    }
    public MetaMaskWelcomePage clickRevealSecretWords() {
        browser.click(revealSecretWordsButton);
        return this;
    }

    public List<String> getSecretWords() {
        var secretWordsElements = browser.findElements(secretWordsFields);
        return secretWordsElements.stream()
                .map(x -> browser.getElementText(x, "secret word value"))
                .collect(Collectors.toList());
    }


    public MetaMaskWelcomePage clickNext() {
        browser.click(nextButton);
        return this;
    }
    public void clickDone() {
        browser.click(doneButton);
    }


    public MetaMaskWelcomePage fillSecretPhrase(List<String> secretWords) {
        var missingSecretWordsElements = browser.findElements(missingSecretWordsFields);
        for (WebElement current : missingSecretWordsElements) {
            String dataTestId = browser.executeScript("return arguments[0].getAttribute('data-testid')", current);
            String idAsString = dataTestId.substring(dataTestId.lastIndexOf('-') + 1);
            int id = Integer.parseInt(idAsString);
            current.sendKeys(secretWords.get(id));
        }

        return this;
    }

    public MetaMaskWelcomePage clickConfirm() {
        browser.click(confirmButton);
        return this;
    }

    public MetaMaskWelcomePage clickAllDone() {
        browser.click(allDoneButton);
        return this;
    }

    public MetaMaskHomePage performFullSetup(String password) {

        List<String> secretWords = checkOnBoardingIAgreeTerms()
                        .clickGetStarted()
                        .clickNoThanksToDataSharing()
                        .setPassword(password)
                        .clickSecureMyWallet()
                        .clickRevealSecretWords()
                        .getSecretWords();

        clickNext()
                .fillSecretPhrase(secretWords)
                .clickConfirm()
                .clickAllDone()
                .clickNext()
                .clickDone();

        return new MetaMaskHomePage(browser).closePopup();
    }
}