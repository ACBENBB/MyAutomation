package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpAddTransaction extends AbstractPage {

    private static final ExtendedBy dateField = new ExtendedBy("date field", By.name("transactionTime"));
    private static final ExtendedBy typeSelector = new ExtendedBy("Type selector", By.name("direction"));
    private static final ExtendedBy amountField = new ExtendedBy("amount field", By.name("amount"));
    private static final ExtendedBy currencyTypeSelector = new ExtendedBy("Currency type selector", By.name("currencyId"));
    private static final ExtendedBy txIdField = new ExtendedBy("TX ID field", By.name("externalTransactionConfirmation"));

    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));

    CpAddTransaction(Browser browser) {
        super(browser, amountField);
    }


    public CpAddTransaction typeDate(String value) {
        browser.typeTextElement(dateField, value);
        return this;
    }

    public CpAddTransaction setType(String type) {
        browser.selectElementByVisibleText(typeSelector, type);
        return this;
    }

    public CpAddTransaction typeAmount(double value) {
        browser.typeTextElement(amountField, value + "");
        return this;
    }

    public CpAddTransaction selectCurrency(String currency) {
        browser.selectElementByVisibleText(currencyTypeSelector, currency);
        return this;
    }

    public CpAddTransaction typeTxId(String value) {
        browser.typeTextElement(txIdField, value);
        return this;
    }

    public void clickOk() {
        browser.clickAndWaitForElementToVanish(okButton);
    }
}