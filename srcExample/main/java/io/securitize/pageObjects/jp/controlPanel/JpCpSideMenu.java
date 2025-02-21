package io.securitize.pageObjects.jp.controlPanel;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.securitize.infra.reporting.MultiReporter.info;

public class JpCpSideMenu extends AbstractJpPage {

    private static final ExtendedBy onboardingButton = new ExtendedBy("onboarding button", By.xpath("//a[contains(@href,'investors/onboarding')]"));
    private static final ExtendedBy languageCodeValue = new ExtendedBy("Language Code Value", By.xpath("//div[@class='lang-code']"));
    private static final ExtendedBy languageSelect = new ExtendedBy("Language Select", By.xpath("//*[contains(@class,'ion-ios-arrow-down')]"));
    private static final ExtendedBy englishRadioButton = new ExtendedBy("English Radio Button", By.xpath("//*[@class='form-check-input' and @value='en']"));
    private static final ExtendedBy japaneseRadioButton = new ExtendedBy("English Radio Button", By.xpath("//*[@class='form-check-input' and @value='ja']"));
    private static final ExtendedBy fundraiseButton = new ExtendedBy("fundraise button", By.xpath("//a[contains(@href,'/investors/fundraise')]"));
    private static final ExtendedBy holdersButton = new ExtendedBy("holders button", By.xpath("//a[contains(@href,'/investors/holders')]"));
    private static final ExtendedBy tokenDropdownButton = new ExtendedBy("Token Selector Button", By.xpath("//div[@class='dropdown btn-group b-dropdown token-selector-dropdown']//button[@class='btn dropdown-toggle btn-link dropdown-toggle-no-caret']"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Logout Button", By.xpath("//*[contains(@class,'ion-ios-log-out')]"));

    public JpCpSideMenu(Browser browser) {
        super(browser, fundraiseButton);
        setLanguageJapanese();
    }

    public void logout() {
        browser.click(logoutButton);
    }

    public JpCpOnBoarding clickOnBoarding() {
        browser.waitForElementClickable(onboardingButton);
        browser.clickWithJs(browser.findElement(onboardingButton));
        return new JpCpOnBoarding(browser);
    }

    public JpCpFundraise clickFundraise() {
        browser.waitForElementClickable(fundraiseButton);
        browser.clickWithJs(browser.findElement(fundraiseButton));
        return new JpCpFundraise(browser);
    }

    public JpCpHolders clickHolders() {
        browser.waitForElementClickable(holdersButton);
        browser.clickWithJs(browser.findElement(holdersButton));
        return new JpCpHolders(browser);
    }

    public void openTokenDropdown() {
        WebElement tokenDropdown = browser.findElement(tokenDropdownButton);
        browser.waitForPageStable();
        browser.click(tokenDropdown, "opening token dropdown", ExecuteBy.JAVASCRIPT, false);
    }

    public void clickTokenName(String tokenName) {
        ExtendedBy tokenSelector = new ExtendedBy("Token Selector", By.xpath("//span[contains(text(), '" + tokenName + "')]"));
        browser.waitForElementVisibility(tokenSelector);
        browser.waitForPageStable();
        browser.click(tokenSelector);
    }

    public void setLanguageEnglish() {
        setLanguage("EN");
    }

    public void setLanguageJapanese() {
        setLanguage("JA");
    }

    public void setLanguage(String languageCodeToSet) {
        String languageCodeCurrent = browser.getElementText(languageCodeValue);
        if (languageCodeCurrent.equalsIgnoreCase(languageCodeToSet)) {
            info("Language is already " + languageCodeToSet);
        } else {
            browser.click(languageSelect);
            if (languageCodeToSet.equalsIgnoreCase("EN")) {
                info("Language to set EN");
                browser.clickWithJs(browser.findElement(englishRadioButton));
            } else {
                info("Language to set JA");
                browser.clickWithJs(browser.findElement(japaneseRadioButton));
            }
            browser.click(languageSelect);
        }

    }
}
