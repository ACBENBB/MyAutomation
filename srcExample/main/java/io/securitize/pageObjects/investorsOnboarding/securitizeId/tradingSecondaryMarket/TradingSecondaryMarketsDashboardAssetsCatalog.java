package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class TradingSecondaryMarketsDashboardAssetsCatalog extends AbstractPage {

    private static final ExtendedBy withdrawButton = new ExtendedBy("Withdraw button", By.xpath("//button[text()='Withdraw']"));
    private static final ExtendedBy assetCatalogSearchBox = new ExtendedBy("Search box", By.xpath("//input[@id='SearchAsset']"));
    private static final ExtendedBy assetCatalogTab = new ExtendedBy("Asset Catalog Tab", By.xpath("//a[@data-tab='assets']"));
    private static final ExtendedBy myOrdersTab = new ExtendedBy("My Orders Tab", By.xpath("//a[@data-tab='my-orders']"));
    private static final ExtendedBy myAccountTab = new ExtendedBy("My Account Tab", By.xpath("//a[@data-tab='my-account']"));
    private static final ExtendedBy linkAccountButton = new ExtendedBy("Link account button", By.xpath("//button[contains(text(),'Add account')]"));
    private static final ExtendedBy assetNameColumn = new ExtendedBy("Type column", By.xpath("//table//th[text()='Name']"));
    private static final ExtendedBy clickTokenByName = new ExtendedBy("Token Name Xpath", By.xpath("//td[text()='TTk1']/../.."));
    private static final ExtendedBy preMarketSessionCloseBtn = new ExtendedBy("Pre Mkt Session Close Btn", By.xpath("(//*[text()='Pre-Market Session']/parent::div/preceding-sibling::div)//*[2]"));
    private static final ExtendedBy assetRows = new ExtendedBy("Asset Rows", By.xpath("//tr[@class='selectable']"));
    private static final ExtendedBy maintenanceBanner = new ExtendedBy("Maintenance Banner", By.xpath("//h2[text()='Maintenance']"));
    private static final ExtendedBy firstAsset = new ExtendedBy("First Asset Cell", By.className("assetCell"));
    private static final ExtendedBy holdingAssets = new ExtendedBy("Holding asset Icon", By.xpath("//div[contains(@class,'holdingsIndicator dotIcon orange')]/../following-sibling::td[1]"));
    private static final ExtendedBy marketsList = new ExtendedBy("Markets list", By.xpath("//div[@class='RegionHeader']"));
    private static final ExtendedBy firstAssetName = new ExtendedBy("The name of the first asset in the catalog", By.xpath("//*[@class='ticker'] | //*[@class='assetCell']/following::td"));

    private static final String regionStatusTemplate = "//*[@data-test-id='%s-market-status-id']";

    public TradingSecondaryMarketsDashboardAssetsCatalog(Browser browser) {
        super(browser);
    }

    public void searchAsset(String assetName) {
        browser.sendKeysElement(assetCatalogSearchBox, "Search Asset", assetName);
    }

    public TradingSecondaryMarketsAssetDetail clickFirstAsset() {
        browser.click(firstAsset);
        return new TradingSecondaryMarketsAssetDetail(browser);
    }

    public boolean isPreMarketSessionPopUpVisible() {
        return browser.isElementVisibleQuick(preMarketSessionCloseBtn);
    }

    public void closePreMarketSessionPopUp() {
        browser.click(preMarketSessionCloseBtn);
    }

    public void clickMyAccountTab() {
        browser.click(myAccountTab, false);
    }

    public void clickWithdrawButton(){
        browser.click(withdrawButton, false);
    }

    public void clickLinkAccountOption(){
        browser.click(linkAccountButton);
    }

    public void clickTokenByName(String tokenName) {
        String dynamicXpath = String.format("//*[text()='%s'][1]", tokenName);
        final ExtendedBy clickTokenByName = new ExtendedBy("Token Name Xpath", By.xpath(dynamicXpath));
        browser.click(clickTokenByName,false);
    }

    public int getAssetSearchResultsCount() {
        return browser.findElements(assetRows).size();
    }

    public boolean isMaintenanceBannerDisplayed() {
        return browser.isElementVisible(maintenanceBanner);
    }

    public void maintenanceBannerWorkaround() {
        Browser.waitForExpressionToEqual(t -> {
            boolean result = isMaintenanceBannerDisplayed();
            if(!result) {
                browser.refreshPage();
            }
            return result;
        }, null, true, "Waiting for maintenanance banner to vanish", 30, 1000);
    }

    public List<WebElement> getHoldingAssets() {
        return browser.findElements(holdingAssets);
    }

    public void addAssetToWatchlist(String assetName) {
        String dynamicXpath = String.format("//td[text()='%s']/preceding-sibling::td/button", assetName);
        final ExtendedBy clickFavoriteByName = new ExtendedBy("Asset name Xpath", By.xpath(dynamicXpath));
        boolean isSelected = browser.getElementAttribute(clickFavoriteByName, "class").contains("CircularButtonComponent  enabled");
        TradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal = new TradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal(browser);
        if (isSelected) { //To remove from favorites
            browser.click(clickFavoriteByName, false);
            Assert.assertTrue(tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.isToastVisible(), "The remove toast is not visible");
            Assert.assertEquals(tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.getToastMessage(), "Removed asset from the watchlist", "The remove toast text is incorrect");
            tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.clickToast();
        }
        browser.click(clickFavoriteByName, false);
        Assert.assertEquals(tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.getWatchlistTitle(), "Get notified about your favorite assets", "The watchlist modal did not open");
        tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.clickConfirmButton();
        Assert.assertTrue(tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.isToastVisible(), "The add toast is not visible");
        Assert.assertEquals(tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.getToastMessage(), "Asset added to the watchlist", "The add toast text is incorrect");
        tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.clickToast();
    }

    public int getMarketsCount() {
        return browser.findElements(marketsList).size();
    }

    public List <String> getAssetsByMarket(String market) {
        final ExtendedBy assetsInTheMarket
                = new ExtendedBy(String.format("Assets in the %s market", market),
                By.xpath(String.format("//img[@class='region-icon' and @alt='%s']/../../../following-sibling::div[1]//td[3]", market)));

        return browser.findElements(assetsInTheMarket).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getFirstAsset() {
        return browser.getElementText(firstAssetName);
    }

    public String getRegionStatus(String region) {
        String xPathString = String.format(regionStatusTemplate, region);
        ExtendedBy regionMarketStatus = new ExtendedBy("Region '%s' market status", By.xpath(xPathString));
        return browser.getElementText(regionMarketStatus);
    }

}