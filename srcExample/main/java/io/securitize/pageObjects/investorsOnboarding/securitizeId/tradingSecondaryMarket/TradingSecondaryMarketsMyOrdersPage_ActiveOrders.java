package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TradingSecondaryMarketsMyOrdersPage_ActiveOrders extends AbstractPage {
    private static final ExtendedBy activeOrdersTab = new ExtendedBy("Active orders tab", By.xpath("//a[contains(text(),'Active orders')]"));
    private static final ExtendedBy tokenName = new ExtendedBy("Token Name", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[2]"));
    private static final ExtendedBy dateValue = new ExtendedBy("Active order creation date", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[3]"));
    private static final ExtendedBy sideActiveOrder = new ExtendedBy("Active order Side type", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[4]"));
    private static final ExtendedBy priceValue = new ExtendedBy("Active order price", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[6]"));
    private static final ExtendedBy firstOrderPriceValue = new ExtendedBy("Active order price", By.xpath("//div[@class='table-responsive']//tbody/tr[2]/td[6]"));
    private static final ExtendedBy quantityValue = new ExtendedBy("Active order quantity", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[7]"));
    private static final ExtendedBy filledValue = new ExtendedBy("Active order status", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[8]"));
    private static final ExtendedBy firstOrderFilledValue = new ExtendedBy("Active order status", By.xpath("//div[@class='table-responsive']//tbody/tr[2]/td[8]"));
    private static final ExtendedBy expandArrowIcon = new ExtendedBy("Arrow icon to expand active order", By.xpath("(//div[@class='ExpandButton collapsed'])[1]"));
    private static final ExtendedBy expandedFilledValue = new ExtendedBy("Expanded order Filled value", By.xpath("//tr[@class='tradesRow lastTrade']//td[8]"));
    private static final ExtendedBy cancelOrderButton = new ExtendedBy("Cancel order button", By.xpath("(//button[@class='ButtonComponent  btn btn-ghost-danger btn-md'])[1]"));
    private static final ExtendedBy confirmCancelOrderButton = new ExtendedBy("Confirm cancel order button", By.xpath("//*[contains(text(), 'Cancel order')]"));
    private static final ExtendedBy noActiveOrdersText = new ExtendedBy("No Active Orders Text", By.xpath("//div[@class='title']"));
    private static final ExtendedBy activeOrdersRows = new ExtendedBy("Active Orders", By.xpath("//table[contains(@class, 'TableComponent')]//tbody//tr"));
    private static final ExtendedBy activeOrdersCancelBtn = new ExtendedBy("Active Orders First Cancel Button", By.xpath("(//table[contains(@class, 'TableComponent')]//tbody//tr)[1]//*[contains(text(), 'Cancel')]"));
    private static final ExtendedBy firstActiveOrder = new ExtendedBy("First Active order", By.xpath("//div[@class='table-responsive']//i"));
    private static final ExtendedBy NothingToShowHerText = new ExtendedBy("Nothing to show here yet message", By.xpath("//*[contains(text(), 'Nothing to show here yet')]"));


    public TradingSecondaryMarketsMyOrdersPage_ActiveOrders(Browser browser) {
        super(browser);
    }

    public TradingSecondaryMarketsMyOrdersPage_ActiveOrders clickActiveOrdersTab() {
        browser.click(activeOrdersTab, false);
        return this;
    }

    public String getTokenName() {
        return browser.getElementText(tokenName);
    }

    public String getDateValue() {
        return browser.getElementText(dateValue);
    }

    public String getSideValue() { return browser.getElementText(sideActiveOrder); }

    public String getPriceValue() {
        return browser.getElementText(priceValue);
    }

    public String getFirstOrderPriceValue() {
        return browser.getElementText(firstOrderPriceValue);
    }

    public String getQuantityValue() { return browser.getElementText(quantityValue);}

    public String getFilledValue() { return browser.getElementText(filledValue);}
    public String getFirstOrderFilledValue() { return browser.getElementText(firstOrderFilledValue);}

    public TradingSecondaryMarketsMyOrdersPage_ActiveOrders clickExpandOrderArrow() {
        browser.click(expandArrowIcon, false);
        return this;
    }

    public void expandFirstOrder(){
        browser.click(firstActiveOrder);
    }

    public String getExpandedFilledValue() { return browser.getElementText(expandedFilledValue);}

    public TradingSecondaryMarketsMyOrdersPage_ActiveOrders clickCancelOrderButton() {
        browser.click(cancelOrderButton, false);
        return this;
    }

    public TradingSecondaryMarketsMyOrdersPage_ActiveOrders clickConfirmCancelOrderButton() {
        browser.click(confirmCancelOrderButton, false);
        return this;
    }

    private boolean isNoActiveOrdersTextVisible() {
        return browser.isElementVisible(noActiveOrdersText);
    }

    private String getNoActiveOrdersText() {
        return browser.findElement(noActiveOrdersText).getText();
    }

    public void assertNoActiveOrdersText() {
        browser.waitForElementVisibility(NothingToShowHerText);
    }

    public List<WebElement> getActiveOrdersElements() {
        return browser.findElements(activeOrdersRows);
    }

    public void cancelActiveOrders() {
        if(!isNoActiveOrdersTextVisible()) {
            int activeOrdersCount = getActiveOrdersElements().size();
            if(activeOrdersCount > 0) {
                for (int i = 0; i < activeOrdersCount; i++) {
                    browser.findElement(activeOrdersCancelBtn).click();
                    clickConfirmCancelOrderButton();
                    browser.waitForPageStable();
                }
            }
        }
    }

    public boolean cancelFirstActiveOrder() {
        if (!isNoActiveOrdersTextVisible()) {
            int activeOrdersCount = getActiveOrdersElements().size();
            if (activeOrdersCount > 0) {
                browser.findElements(activeOrdersCancelBtn).get(0).click();
                clickConfirmCancelOrderButton();
                browser.waitForPageStable();
                return true;
            }
        }
        return false;
    }
}
