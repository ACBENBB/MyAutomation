package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpAddTransactionNewUI extends AbstractPage {

    private static String txType;
    private static final ExtendedBy typeSelector = new ExtendedBy("Type selector", By.xpath("//div[@id='mui-component-select-type']"));
    private static final ExtendedBy currencyTypeSelector = new ExtendedBy("currency selector", By.xpath("//div[@id='mui-component-select-currency']"));
    private static final ExtendedBy currencyOption = new ExtendedBy("currency option", By.xpath("//li[@data-value='1']"));
    private static final ExtendedBy amountField = new ExtendedBy("amountField", By.xpath("//input[@placeholder='Amount']"));
    private static final ExtendedBy txIdField = new ExtendedBy("txId field", By.xpath("//input[@placeholder='Tx ID']"));
    private static final ExtendedBy saveButton = new ExtendedBy("ok button", By.xpath("//button[text()='Save']"));
    private static final String typeDepositOption = "//li[@data-value='%s']";

    CpAddTransactionNewUI(Browser browser) {
        super(browser);
    }

    public CpAddTransactionNewUI setType(String value) {
        txType = value;
        String xPathString = String.format(typeDepositOption, txType);
        ExtendedBy typeDepositOption = new ExtendedBy("Chosen transaction: " +txType , By.xpath(xPathString));
        browser.click(typeSelector);
        browser.click(typeDepositOption);
        return this;
    }

    public CpAddTransactionNewUI selectCurrency() {
        browser.click(currencyTypeSelector);
        browser.click(currencyOption);
        return this;
    }

    public CpAddTransactionNewUI typeAmount(double value) {
        browser.typeTextElementCtrlA(amountField, String.valueOf(value));
        return this;
    }

    public CpAddTransactionNewUI typeTxId(String value) {
        browser.typeTextElement(txIdField, value);
        return this;
    }

    public void clickSave() {
        browser.clickAndWaitForElementToVanish(saveButton);
    }
}