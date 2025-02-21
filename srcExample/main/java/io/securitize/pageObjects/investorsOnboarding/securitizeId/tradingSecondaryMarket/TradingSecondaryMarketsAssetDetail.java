package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TradingSecondaryMarketsAssetDetail extends AbstractPage {

    private static final ExtendedBy assetDetailBuyButton = new ExtendedBy("Buy button", By.xpath("//button[@data-test-id='asset-info-card-buy-button']"));
    private static final ExtendedBy assetDetailSellButton = new ExtendedBy("Sell button", By.xpath("//button[@data-test-id='asset-info-card-sell-button']"));
    private static final ExtendedBy chartTitle = new ExtendedBy("Chart Title", By.xpath("//*[@class='summaryInfo']/div[@class='title']"));
    private static final ExtendedBy tradesTab = new ExtendedBy("Trades tab", By.xpath("//a[contains(text(), 'trades')]"));
    private static final ExtendedBy infoRows = new ExtendedBy("Info Rows", By.xpath("//*[@class='TradesHistory']//*[@class='info-row']"));

    private static final ExtendedBy myOrdersTab = new ExtendedBy("My orders tab", By.xpath("//a[contains(text(), 'My Orders')]"));
    private static final ExtendedBy assetInfoTab = new ExtendedBy("My orders tab", By.xpath("//a[contains(text(), 'Asset Info')]"));
    private static final ExtendedBy tradingSuitability = new ExtendedBy("Trading suitability card", By.id("suitability-card"));
    private static final ExtendedBy tradingAccreditation = new ExtendedBy("Trading Accreditation card", By.id("accreditation-card"));
    private static final ExtendedBy companyName22x = new ExtendedBy("Securitize ID - production - trading - 22x", By.xpath("//span[contains(@class, 'companyName') and contains(text(), '22x')]"));
    private static final ExtendedBy securitizeIdAvatar = new ExtendedBy("Securitize ID - Trading - Avatar", By.xpath("//*[contains(@class, 'sec-id-user-menu_avatar fill2')]"));
    private static final ExtendedBy securitizeIdLogoutButton = new ExtendedBy("Securitize ID - Trading  - Logout button", By.xpath("//i[contains(@class , 'icon-log-out')]"));
    private static final ExtendedBy marketClosedModal = new ExtendedBy("Secondary market closed modal", By.xpath("//div[contains(@class, 'NotificationPopoverBody popover-body')]"));
    private static final ExtendedBy assetBuySellOrderRows = new ExtendedBy("Asset Buy Sell Order Row for Count validation", By.xpath("//button[contains(@data-test-id, 'order-book')]"));
    private static final ExtendedBy maintenanceBanner = new ExtendedBy("Maintenance Banner", By.xpath("//h2[text()='Maintenance']"));
    private static final ExtendedBy verifyYourIdentityCard = new ExtendedBy("Verify your identity card", By.id("complete-details-card"));
    private static final ExtendedBy watchlistButton = new ExtendedBy("Watchlist button", By.xpath("//button[contains(@class,'watchlist')]"));
    private static final ExtendedBy emptyOrderBookContainer = new ExtendedBy("Empty order book container", By.className("BoxMessage"));
    private static final ExtendedBy lastPrice = new ExtendedBy("Last price value", By.xpath("//div[@class='h1Font value']"));
    private static final ExtendedBy openPrice = new ExtendedBy("Open price value", By.xpath("//div[@class='row smallValuesRow']/div[1]/div[2]"));
    private static final ExtendedBy closePrice = new ExtendedBy("Close price value", By.xpath("//div[@class='row smallValuesRow']/div[2]/div[2]"));
    private static final ExtendedBy highPrice = new ExtendedBy("High price value", By.xpath("//div[@class='row smallValuesRow']/div[3]/div[2]"));
    private static final ExtendedBy lowPrice = new ExtendedBy("Low price value", By.xpath("//div[@class='row smallValuesRow']/div[4]/div[2]"));
    private static final ExtendedBy limitedTradingBanner = new ExtendedBy("Low price value", By.xpath("//div[contains(@class, 'LimitedTradingCardComponent')]"));

    public TradingSecondaryMarketsAssetDetail(Browser browser) {
        super(browser, myOrdersTab);
    }

    public void clickTradesTab() {
        browser.click(tradesTab, false);
    }

    public void clickMyOrdersTab() {
        browser.click(myOrdersTab, false);
    }

    public void clickAssetInfoTab() {
        browser.click(assetInfoTab, false);
    }

    public void clickAssetDetailBuyButton() {
        browser.click(assetDetailBuyButton, false);
    }

    public TradingSecondaryMarketsTradeModal clickBuyButton() {
        clickAssetDetailBuyButton();
        return new TradingSecondaryMarketsTradeModal(browser);
    }

    public void clickAssetDetailSellButton() {
        browser.click(assetDetailSellButton, false);
    }

    public void clickSuitabilityCard() {
        browser.click(tradingSuitability, false);
    }

    public AccreditationPage clickAccreditationCard() {
        browser.click(tradingAccreditation, false);
        return new AccreditationPage(browser);
    }

    public String getCompanyName() {
        return browser.getElementText(companyName22x);
    }

    public void securitizeIdTradingLogout() {
        browser.click(securitizeIdAvatar);
        browser.click(securitizeIdLogoutButton);
    }

    public boolean assertMarketClosed() {
        return !browser.findElements(marketClosedModal).isEmpty();
    }

    public int getAssetBuySellOrdersCount() {
        return browser.findElements(assetBuySellOrderRows).size();
    }

    public boolean isBuyButtonDisplayed() {
        return browser.isElementEnabled(assetDetailBuyButton);
    }

    public boolean isSellButtonDisplayed() {
        return browser.isElementEnabled(assetDetailSellButton);
    }

    public String getChartTitle() {
        return browser.getElementText(chartTitle);
    }

    public void clickVerifyYourIdentityCard() {
        browser.click(verifyYourIdentityCard, false);
    }

    public boolean isAssetInWatchlist() {
        browser.waitForElementVisibility(watchlistButton);
        return browser.getElementAttribute(watchlistButton, "class").contains("enabled");
    }

    public void clickWatchlistButton() {
        browser.click(watchlistButton, false);
    }

    public boolean isOrderBookEmpty() {
        return browser.isElementVisible(emptyOrderBookContainer);
    }

    public boolean isButtonDisplayedInTheOrderBook(String button) {
        final ExtendedBy buyButtonInTheOrderBook = new ExtendedBy("Buy button in the order book", By.xpath(String.format("//*[@id='parentTableContainer']//button[text()='%s']", button)));
        return browser.isElementVisible(buyButtonInTheOrderBook);
    }

    public String getLastPrice() {
        return browser.getElementText(lastPrice);
    }

    public String getOpenPrice() {
        return browser.getElementText(openPrice);
    }

    public String getClosePrice() {
        return browser.getElementText(closePrice);
    }

    public String getHighPrice() {
        return browser.getElementText(highPrice);
    }

    public String getLowPrice() {
        return browser.getElementText(lowPrice);
    }

    public boolean isBuyButtonEnabled() {
        browser.waitForElementVisibility(assetDetailBuyButton);
        return browser.isElementEnabled(assetDetailBuyButton);
    }

    public boolean isLimitedTradingBannerVisible() {
        browser.waitForElementVisibility(assetDetailBuyButton);
        return browser.isElementVisible(limitedTradingBanner);
    }

    public List<WebElement> getTrades() {
        return browser.findElements(infoRows);
    }
}