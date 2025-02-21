package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawInternationalWireModalPage extends AbstractPage {

    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy feeValue = new ExtendedBy("Amount Input Field", By.cssSelector("[data-test-id='ca-transfermodalbeneficiarybankform-fee-value']"));
    private static final ExtendedBy amountField = new ExtendedBy("Amount Input Field", By.cssSelector("[name='amount']"));
    private static final ExtendedBy bankNameField = new ExtendedBy("Bank Name Input Field", By.cssSelector("[name='bankName']"));
    private static final ExtendedBy recipientName = new ExtendedBy("Recipient Name Input Field", By.cssSelector("[name='accountName']"));
    private static final ExtendedBy accountHolderField = new ExtendedBy("Account Holder Field", By.cssSelector("[name='accountHolder']"));
    private static final ExtendedBy accountNumber = new ExtendedBy("Account Number Input Field", By.cssSelector("[name='accountNumber']"));
    private static final ExtendedBy bicSwiftField = new ExtendedBy("BIC / Swift Code Input Field", By.cssSelector("[name='swiftCode']"));
    private static final ExtendedBy nextButton = new ExtendedBy("Next Button", By.cssSelector("[data-test-id='beneficiary-bank-submit-btn']"));


    public WithdrawInternationalWireModalPage(Browser browser) {
        super(browser, modalBody);
    }

    public WithdrawConfirmationModalPage clickNextButton() {
        browser.click(nextButton);

        return new WithdrawConfirmationModalPage(browser);
    }

    public String getFee() {
        return browser.findElement(feeValue).getText().replaceAll("[^$0-9,.]", "");
    }

    public void setAmount(String amount) {
        browser.findElement(amountField).sendKeys(amount);
    }

    public void setBankName(String amount) {
        browser.findElement(bankNameField).sendKeys(amount);
    }

    public void setRecipientName(String name) {
        browser.findElement(recipientName).sendKeys(name);
    }

    public void setAccountNumber(String number) {
        browser.findElement(accountNumber).sendKeys(number);
    }

    public void setBicSwift(String number) {
        browser.findElement(bicSwiftField).sendKeys(number);
    }

    public String getAccountHolder() {
        return browser.findElement(accountHolderField).getAttribute("value");
    }


}
