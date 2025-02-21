package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.TransferFundsModalPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsTradeModal extends AbstractPage {

    private static final ExtendedBy priceField = new ExtendedBy("Price field", By.xpath("//input[@name='price']"));
    private static final ExtendedBy amountField = new ExtendedBy("Amount field", By.xpath("//input[@name='amount']"));
    private static final ExtendedBy nextButton = new ExtendedBy("Next button", By.xpath("//button[contains(text(),'Next')]"));
    private static final ExtendedBy cancelButton = new ExtendedBy("Cancel button", By.xpath("//button[contains(text(),'Cancel')]"));
    private static final ExtendedBy switchSell = new ExtendedBy("Switch sell", By.xpath("//label/div[contains(text(),'Sell')]"));
    private static final ExtendedBy expirationDateDropdown = new ExtendedBy("Expiration date dropdown", By.xpath("//div[@class='itemSelectorContainer']//button[@type='button']"));
    private static final ExtendedBy selectExpirationDateValue = new ExtendedBy("Expiration date value", By.linkText("1"));
    private static final ExtendedBy dueDate = new ExtendedBy("Due Date Element", By.xpath("//div[@class='OrderModalForm']//div[@class='date']"));
    private static final ExtendedBy modalCloseBtn = new ExtendedBy("Modal Close Btn", By.xpath("//div[@class='HeaderContainer ']/child::*[3]"));
    private static final ExtendedBy bodyContainer = new ExtendedBy("Body Container", By.className("BodyContainer"));
    private static final ExtendedBy addFundsBtn = new ExtendedBy("Add Funds Btn", By.cssSelector("[data-test-id='order-modal-deposit-available-button']"));

    public TradingSecondaryMarketsTradeModal(Browser browser) { super(browser,nextButton); }

    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return browser.findElement(bodyContainer).isDisplayed();
    }

    public TransferFundsModalPage clickAddFundsBtn(){
        browser.click(addFundsBtn);
        return new TransferFundsModalPage(browser);
    }

    public String getAddFundsButtonText() {
        return browser.getElement(addFundsBtn).getText();
    }

    public TradingSecondaryMarketsTradeModal setOrderPrice(Double orderPrice) {
        browser.typeTextElement(priceField, String.valueOf(orderPrice));
        return this;
    }

    public TradingSecondaryMarketsTradeModal setOrderQuantity(Double orderQuantity) {
        browser.typeTextElement(amountField, String.valueOf(orderQuantity));
        return this;
    }

    public TradingSecondaryMarketsTradeModal clickNextButton() {
        browser.click(nextButton, false);
        return this;
    }

    public TradingSecondaryMarketsTradeModal clickSellOption(){
        browser.click(switchSell,false);
        return this;
    }

    public TradingSecondaryMarketsTradeModal selectExpirationDate(){
        browser.click(expirationDateDropdown);
        browser.click(selectExpirationDateValue);
        return this;
    }

    public String getDueDate() {
        return browser.findElement(dueDate).getText();
    }

    public void clickCloseModalBtn() {
        browser.click(modalCloseBtn);
    }

}
