package io.securitize.pageObjects.jp.controlPanel;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

import static io.securitize.infra.reporting.MultiReporter.info;

public class JpCpSelectIssuer extends AbstractJpPage {

    private static final ExtendedBy viewIssuerButton = new ExtendedBy("View issuer button", By.xpath("//button[contains(text(), ' 発行体を見る ')]"));
    private static final String VIEW_ISSUER_BUTTON_BY_ISSUER_TEXT_TEMPLATE = "//*[text()[contains(.,'%s')]]/ancestor::node()[contains(@class, 'issuer-card')]//button[contains(text(), '発行体を見る')]";
    private static final ExtendedBy searchIssuerTextBox = new ExtendedBy("search field in issuers list screen", By.xpath("//*[contains (@class, 'form-control form-control-m')]"));
    private static final ExtendedBy issuerTitle = new ExtendedBy("Issuers Title", By.xpath("//h4[text()=' 発行体リスト ']"));
    private static final ExtendedBy languageCodeValue = new ExtendedBy("Language Code Value", By.xpath("//div[@class='lang-code']"));
    private static final ExtendedBy languageSelect = new ExtendedBy("Language Select", By.xpath("//*[contains(@class,'ion-ios-arrow-down')]"));
    private static final ExtendedBy englishRadioButton = new ExtendedBy("English Radio Button", By.xpath("//*[@class='form-check-input' and @value='en']"));
    private static final ExtendedBy japaneseRadioButton = new ExtendedBy("English Radio Button", By.xpath("//*[@class='form-check-input' and @value='ja']"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Logout Button", By.xpath("//*[@class='nav-item']"));

    public JpCpSelectIssuer(Browser browser) {
        super(browser, searchIssuerTextBox);
    }

    public void logout() {
        browser.clickWithJs(browser.findElement(logoutButton));
    }

    public JpCpSelectIssuer setLanguageEnglish() {
        return setLanguage("EN");
    }

    public JpCpSelectIssuer setLanguageJapanese() {
        return setLanguage("JA");
    }

    public JpCpSelectIssuer setLanguage(String languageCodeToSet) {
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
        return this;
    }

    public JpCpSelectIssuer clickViewIssuerByName(String issuerName) {
        browser.typeTextElement(searchIssuerTextBox, issuerName);
        String fullXpathSelector = String.format(VIEW_ISSUER_BUTTON_BY_ISSUER_TEXT_TEMPLATE, issuerName);
        ExtendedBy valueSelector = new ExtendedBy("View issuer button for " + issuerName, By.xpath(fullXpathSelector));
        browser.waitForPageStable();
        browser.click(valueSelector);
        return this;
    }

    public JpCpSelectIssuer waitForIssuerPageToLoad(){
        browser.waitForElementVisibility(issuerTitle);
        browser.waitForElementVisibility(searchIssuerTextBox);
        return this;
    }
}
