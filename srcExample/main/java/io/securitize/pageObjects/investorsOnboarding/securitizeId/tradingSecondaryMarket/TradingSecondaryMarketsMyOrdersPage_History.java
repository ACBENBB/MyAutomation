package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsMyOrdersPage_History extends AbstractPage {
    private static final ExtendedBy historyTab = new ExtendedBy("History tab", By.xpath("//a[contains(text(),'History')]"));
    private static final ExtendedBy historyTokenName = new ExtendedBy("Token Name", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[2]"));
    private static final ExtendedBy historyDateValue = new ExtendedBy("History order creation date", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[3]"));
    private static final ExtendedBy historySideOrder = new ExtendedBy("History order Side type", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[4]"));
    private static final ExtendedBy historyPriceValue = new ExtendedBy("History order price", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[6]"));
    private static final ExtendedBy historyQuantityValue = new ExtendedBy("History order quantity", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[7]"));
    private static final ExtendedBy historyStatusValue = new ExtendedBy("History order Status", By.xpath("//div[@class='table-responsive']//tbody/tr[1]/td[12]"));
    private static final ExtendedBy firstHistoryOrder = new ExtendedBy("First Active order", By.xpath("//div[@class='table-responsive']//i"));
    private static final ExtendedBy firstHistoryPriceValue = new ExtendedBy("First Active order", By.xpath("//div[@class='table-responsive']//tbody/tr[2]/td[6]"));
    private static final ExtendedBy firstFilledHistoryOrder = new ExtendedBy("First Active order", By.xpath("//div[@class='table-responsive']//tbody/tr[2]/td[8]"));
    private static final ExtendedBy firstHistoryStatusValue = new ExtendedBy("History order Status", By.xpath("//div[@class='table-responsive']//tbody/tr[2]/td[12]"));
    private static final ExtendedBy ordersRows = new ExtendedBy("History order Status", By.xpath("//tr[@data-test-id]"));

    public TradingSecondaryMarketsMyOrdersPage_History(Browser browser) {super(browser);
    }

    public TradingSecondaryMarketsMyOrdersPage_History clickHistoryTab() {
        browser.click(historyTab, false);
        return this;
    }

    public String getTokenName() {
        return browser.getElementText(historyTokenName);
    }

    public String getDateValue() {
        return browser.getElementText(historyDateValue);
    }

    public String getSideValue() {
        return browser.getElementText(historySideOrder);
    }

    public String getPriceValue() {
        return browser.getElementText(historyPriceValue);
    }

    public String getQuantityValue() {
        return browser.getElementText(historyQuantityValue);
    }

    public String getStatusValue(){ return browser.getElementText(historyStatusValue); }

    public void expandFirstOrder() {
        browser.click(firstHistoryOrder);
    }

    public String getFirstOrderPriceValue() {
        return browser.getElementText(firstHistoryPriceValue);
    }

    public String getFirstOrderFilledValue() {
        return browser.getElementText(firstFilledHistoryOrder);
    }

    public String getFirstStatusValue() {
        return browser.getElementText(firstHistoryStatusValue);
    }

    public String getOrderIdByIndex(int index) {
        return browser.findElements(ordersRows).get(index).getAttribute("data-test-id");
    }

}

