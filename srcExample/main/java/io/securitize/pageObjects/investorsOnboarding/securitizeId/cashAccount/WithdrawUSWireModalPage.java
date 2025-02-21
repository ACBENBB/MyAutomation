package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawUSWireModalPage extends AbstractPage {

    private static final ExtendedBy withdrawModalBody = new ExtendedBy("Transfer Types Section Title", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy accountHolderField = new ExtendedBy("Account Holder Field", By.cssSelector("[name='accountHolder']"));
    private static final ExtendedBy feeValue = new ExtendedBy("Amount Input Field", By.cssSelector("[data-test-id='ca-transfermodalbeneficiarybankform-fee-value']"));
    private static final ExtendedBy amountField = new ExtendedBy("Amount Input Field", By.cssSelector("[name='amount']"));
    private static final ExtendedBy recipientName = new ExtendedBy("Recipient Name Input Field", By.cssSelector("[name='accountName']"));
    private static final ExtendedBy accountNumber = new ExtendedBy("Account Number Input Field", By.cssSelector("[name='accountNumber']"));
    private static final ExtendedBy routingNumber = new ExtendedBy("Routing Number Input Field", By.cssSelector("[name='routingNumber']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Continue Button", By.cssSelector("[data-test-id='beneficiary-bank-submit-btn']"));


    public WithdrawUSWireModalPage(Browser browser) {
        super(browser, withdrawModalBody);
    }

    public WithdrawConfirmationModalPage clickSubmitBtn() {
        browser.click(submitButton);

        return new WithdrawConfirmationModalPage(browser);
    }

    public String getFee() {
        return browser.findElement(feeValue).getText().replaceAll("[^$0-9,.]", "");
    }

    public void setAmount(String amount) {
        browser.findElement(amountField).sendKeys(amount);
    }

    public String getAccountHolder() {
        return browser.findElement(accountHolderField).getAttribute("value");
    }
    public void setRecipientName(String name) {
        browser.findElement(recipientName).sendKeys(name);
    }

    public void setAccountNumber(String number) {
        browser.findElement(accountNumber).sendKeys(number);
    }

    public void setRoutingNumber(String number) {
        browser.findElement(routingNumber).sendKeys(number);
    }



}
