package io.securitize.pageObjects.jp.controlPanel;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

import static io.securitize.infra.reporting.MultiReporter.info;

public class JpCpPanelAdministration extends AbstractJpPage {

    private static final ExtendedBy addOperatorsButton = new ExtendedBy("Add Operators Button", By.xpath("//button[@class='btn btn-info']"));
    private static final ExtendedBy administratorsTableRows = new ExtendedBy("Administrators Table Rows", By.xpath("//tbody/tr"));
    private static final ExtendedBy filterCollapseIcon = new ExtendedBy("Filter Collapse Icon", By.xpath("//*[@class='collapse-icon']"));
    private static final ExtendedBy filterIssuerSelect = new ExtendedBy("Filter Issuer Select", By.xpath("//*[@name='issuer-id']"));
    private static final ExtendedBy filterAuthorizationsSelect = new ExtendedBy("Filter Authorizations Select", By.xpath("//*[@name='operator-authorization']"));
    private static final ExtendedBy filterAuthorizationLevelSelect = new ExtendedBy("Filter Authorization Level Select", By.xpath("//*[@name='authorization-level']"));
    private static final ExtendedBy searchAdministratorTextBox = new ExtendedBy("search field for administrators", By.xpath("//*[@placeholder='検索...']"));
    // %s below is email address of administrator. email address is used to identify the specific entry
    private static final String TEMPLATE_STATUS_TEXT_OF_ADMIN_EMAIL = "//tr[td[ text() = '%s' ]]/td[7]";
    private static final String TEMPLATE_EDIT_BUTTON_OF_ADMIN_EMAIL = "//tr[td[ text() = '%s' ]]/td[8]/button[1]";
    private static final ExtendedBy editOperatorButtonOnEditOperatorDialog = new ExtendedBy("Edit Operator Button on Edit Operator Dialog", By.xpath("//button[@class='btn btn-primary']"));
    private static final ExtendedBy languageCodeValue = new ExtendedBy("Language Code Value", By.xpath("//div[@class='lang-code']"));
    private static final ExtendedBy languageSelect = new ExtendedBy("Language Select", By.xpath("//*[contains(@class,'ion-ios-arrow-down')]"));
    private static final ExtendedBy englishRadioButton = new ExtendedBy("English Radio Button", By.xpath("//*[@class='form-check-input' and @value='en']"));
    private static final ExtendedBy japaneseRadioButton = new ExtendedBy("English Radio Button", By.xpath("//*[@class='form-check-input' and @value='ja']"));

    public JpCpPanelAdministration(Browser browser) {
        super(browser, addOperatorsButton);
        setLanguageJapanese();
    }

    private void clickFilterIfNotOpen() {
        if (browser.isElementEnabled(filterIssuerSelect)) {
            browser.clickWithJs(browser.findElement(filterCollapseIcon));
        }
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

    public void unsetFilter() {
        clickFilterIfNotOpen();
        browser.selectElementByVisibleText(filterIssuerSelect, "すべて");
        browser.selectElementByVisibleText(filterAuthorizationsSelect, "すべて");
        browser.selectElementByVisibleText(filterAuthorizationLevelSelect, "すべて");
    }

    public void searchAdministratorAndWaitUntilOnlyOneIsFound(String email) {
        unsetFilter();
        browser.typeTextElement(searchAdministratorTextBox, email);
        browser.waitForPageStable();
        browser.waitForElementVisibleCount(administratorsTableRows, 1);
    }

    public String adminStatusText(String email) {
        String statusTextXpath = String.format(TEMPLATE_STATUS_TEXT_OF_ADMIN_EMAIL, email);
        ExtendedBy statusText = new ExtendedBy("Admin Status of " + email, By.xpath(statusTextXpath));
        browser.waitForElementVisibility(statusText);
        return browser.getElementText(statusText);
    }

    public void clickEditButtonOfEmail(String email) {
        String editButtonXpath = String.format(TEMPLATE_EDIT_BUTTON_OF_ADMIN_EMAIL, email);
        ExtendedBy editButton = new ExtendedBy("Click Edit button of " + email, By.xpath(editButtonXpath));
        browser.waitForElementClickable(editButton);
        browser.click(editButton);
        browser.waitForElementVisibility(editOperatorButtonOnEditOperatorDialog,5);
        browser.resetImplicitWaitTimeout();
    }

}
