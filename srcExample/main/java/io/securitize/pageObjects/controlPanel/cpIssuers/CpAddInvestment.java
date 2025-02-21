package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpAddInvestment extends AbstractPage<CpAddInvestment> {

    private static final ExtendedBy currencyTypeSelector = new ExtendedBy("Currency type selector", By.xpath("//label[contains(text(), 'Currency')]/../select"));
    private static final ExtendedBy subscriptionAgreementSelector = new ExtendedBy("Subscription agreement selector", By.xpath("//label[contains(text(), 'Subscription Agreement')]/../select"));
    private static final ExtendedBy dateField = new ExtendedBy("date field", By.xpath("//div[contains(@class, 'date-block')]/div[contains(@class, 'input-group')]/input"));
    private static final ExtendedBy amountField = new ExtendedBy("amount field", By.xpath("//input[@type='number']"));

    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));

    CpAddInvestment(Browser browser) {
        super(browser, currencyTypeSelector);
    }

    public CpAddInvestment selectCurrency(String value) {
        browser.selectElementByVisibleText(currencyTypeSelector, value);
        return this;
    }

    public CpAddInvestment typeDate(String value) {
        browser.typeTextElement(dateField, value);
        return this;
    }


    public CpAddInvestment typeAmount(int value) {
        browser.typeTextElement(amountField, value + "");
        return this;
    }

    public CpAddInvestment selectSubscriptionAgreement(String status) {
        browser.selectElementByVisibleText(subscriptionAgreementSelector, status);
        return this;
    }

    public void clickOk() {
        browser.clickAndWaitForElementToVanish(okButton);
    }
}