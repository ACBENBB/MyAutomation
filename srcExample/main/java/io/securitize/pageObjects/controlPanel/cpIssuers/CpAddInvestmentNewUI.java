package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpAddInvestmentNewUI extends AbstractPage<CpAddInvestment> {

    private static final ExtendedBy currencyTypeSelector = new ExtendedBy("pledge currency type selector", By.xpath("//div[@id= 'mui-component-select-currency']"));
    private static final ExtendedBy amountField = new ExtendedBy("amount field", By.xpath("//input[@name='pledgedAmount']"));
    private static final ExtendedBy subscriptionAgreementSelector = new ExtendedBy("subscription agreement selector", By.xpath("//div[@id='mui-component-select-subscriptionAgreementStatus']"));
    private static final ExtendedBy subscriptionAgreementOption = new ExtendedBy("subscription agreement options", By.xpath("//li[@data-value='confirmed']"));
    private static final ExtendedBy saveButton = new ExtendedBy("ok button", By.xpath("//button[text()='Save']"));
    private static final String currencyOption = "//li[contains(text(),'%s')]";

    CpAddInvestmentNewUI(Browser browser) {
        super(browser, currencyTypeSelector);
    }

    public CpAddInvestmentNewUI selectCurrency(String currencyName) {
        browser.click(currencyTypeSelector);
        String xPathString = String.format(currencyOption, currencyName);
        ExtendedBy currencyOption = new ExtendedBy("Chosen pledge currency: " +currencyName , By.xpath(xPathString));
        browser.click(currencyOption);
        return this;
    }

    public CpAddInvestmentNewUI addPledgeAmount(String value) {
        browser.typeTextElementCtrlA(amountField, value);
        return this;
    }

    public CpAddInvestmentNewUI selectSA() {
        browser.click(subscriptionAgreementSelector);
        browser.click(subscriptionAgreementOption);
        return this;
    }

    public void clickSaveButton() {
        browser.clickAndWaitForElementToVanish(saveButton);
    }
}