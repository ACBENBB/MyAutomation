package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpAddEvergreenInvestment extends AbstractPage {

    private static final ExtendedBy currencyTypeSelector = new ExtendedBy("Currency type selector", By.xpath("//select[@name='currency']"));
    private static final ExtendedBy pledgedAmountField = new ExtendedBy("Pledged amount field", By.xpath("//label[contains(text(), 'Pledged Amount')]/..//input"));
    private static final ExtendedBy signatureDateField = new ExtendedBy("Signature date field", By.xpath("//*[contains(@class ,'cp-border-r-off form-control')]"));
    private static final ExtendedBy subscriptionAgreementSelector = new ExtendedBy("Subscription agreement selector", By.xpath("//label[contains(text(), 'Subscription Agreement')]/../select"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));

    CpAddEvergreenInvestment(Browser browser) {
        super(browser, currencyTypeSelector);
    }

    public CpAddEvergreenInvestment selectCurrency(String value) {
        browser.selectElementByVisibleText(currencyTypeSelector, value);
        return this;
    }

    public CpAddEvergreenInvestment setPledgedAmount(int value) {
        browser.typeTextElement(pledgedAmountField, value + "");
        return this;
    }

    public CpAddEvergreenInvestment setSignatureDate(String todaysDate) {
        browser.typeTextElement(signatureDateField, todaysDate);
        return this;
    }

    public CpAddEvergreenInvestment selectSubscriptionAgreement(String value) {
        browser.selectElementByVisibleText(subscriptionAgreementSelector, value);
        return this;
    }

    public void clickOk() {
        browser.clickAndWaitForElementToVanish(okButton);
    }
}