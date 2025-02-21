package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsDepositAndWithdraw extends AbstractPage {
    private static final ExtendedBy bankCardLinked = new ExtendedBy("Bank card linked", By.xpath("//div[text()='CITIZENS BANK NA']"));
    private static final ExtendedBy amountInput = new ExtendedBy("Amount input", By.xpath("//div[contains(@class, 'CashInput')]//input"));
    private static final ExtendedBy submitButton = new ExtendedBy("Submit button", By.xpath("//button[contains(@class, 'ButtonComponent submit-button btn btn-primary btn-lg')]"));
    private static final ExtendedBy understoodButton = new ExtendedBy("Understood button", By.xpath("//button[contains(text(),'Understood')]"));

    private static final ExtendedBy transactionType = new ExtendedBy("Transaction type", By.xpath("//div[@class='movementContainer'][1]"));
    private static final ExtendedBy transactionAmount = new ExtendedBy("Transaction amount", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[3]"));
    private static final ExtendedBy transactionStatus = new ExtendedBy("Transaction status", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[5]"));

    public TradingSecondaryMarketsDepositAndWithdraw(Browser browser) {
        super(browser, bankCardLinked);
    }

    public void clickBankCard() {
        browser.click(bankCardLinked, false);
    }

    public void insertDepositAmount() {
        browser.findElement(amountInput).sendKeys("5");
    }

    public void insertWithdrawAmount(){
        browser.findElement(amountInput).sendKeys("2");
    }

    public void clickSubmitButton() {
        browser.click(submitButton, false);
    }

    public void clickUnderstoodButton() {
        browser.click(understoodButton, false);
    }
    public String getTransactionType() { return browser.getElementText(transactionType); }
    public String getTransactionAmount() {
        return browser.getElementText(transactionAmount);
    }
    public String getTransactionStatus() {
        return browser.getElementText(transactionStatus);
    }


    public void makeDeposit(){
        this.clickBankCard();
        this.insertDepositAmount();
        this.clickSubmitButton();
        this.clickUnderstoodButton();
    }

    public void makeWithdraw() {
        this.clickBankCard();
        this.insertWithdrawAmount();
        this.clickSubmitButton();
        this.clickUnderstoodButton();
    }
}
