package io.securitize.tests.ats.abstractClass;

import com.jayway.jsonpath.JsonPath;
import io.securitize.infra.api.*;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.database.MySqlDatabase;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.TransferFundsModalPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket.*;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.ats.pojo.ATS_BuyOrder;
import io.securitize.tests.ats.pojo.ATS_SellOrder;
import io.securitize.tests.ats.properties.AssertionErrors;
import net.minidev.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractATS extends AbstractUiTest {
    protected AtsAssetAPI atsAssetAPI = new AtsAssetAPI();
    protected AtsOrderAPI atsOrderAPI = new AtsOrderAPI();
    protected AtsAccountAPI atsAccountAPI = new AtsAccountAPI();
    protected AtsConfigAPI atsConfigAPI = new AtsConfigAPI();
    protected CashAccountConfigAPI cashAccountConfigAPI = new CashAccountConfigAPI();

    @AfterSuite
    public void closeDatabaseConnection() {
        MySqlDatabase.closeConnection("ats");
    }

    public void loginToSecuritizeId(String email, String password) {
        String secIdUrl = MainConfig.getProperty(MainConfigProperty.secIdUrl);
        getBrowser().navigateTo(secIdUrl);
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(getBrowser());
        if (securitizeLogin.isAcceptCookiesButtonPresent()) {
            securitizeLogin.clickAcceptCookies();
        }
        securitizeLogin.performLoginWithCredentials(email, password, true);
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard.clickSkipTwoFactor();
        securitizeLogin.waitForSIDToLogin();
    }

    public void navigateToSecondaryMarket() {
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.clickTrading();
    }

    public void searchAssetInAssetCatalogPage(String assetName) {
        TradingSecondaryMarketsDashboardAssetsCatalog tradingSecondaryMarketsDashboardAssetsCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        tradingSecondaryMarketsDashboardAssetsCatalog.searchAsset(assetName);
    }

    public void validateSearchResultsCount(int expectedResultsCount) {
        getBrowser().waitForPageStable();
        TradingSecondaryMarketsDashboardAssetsCatalog tradingSecondaryMarketsDashboardAssetsCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        Assert.assertEquals(tradingSecondaryMarketsDashboardAssetsCatalog.getAssetSearchResultsCount(), expectedResultsCount);
    }

    public void selectSecondaryMarketTokenByName(String tokenName) {
        TradingSecondaryMarketsDashboardAssetsCatalog secondaryMarketDashboardAssetCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        secondaryMarketDashboardAssetCatalog.clickTokenByName(tokenName);
    }


    public void validateAssetBuySellOrderCountGreaterThan(int rowsCount) {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        int actualAssetBuySellOrdersCount = tradingSecondaryMarketsAssetDetail.getAssetBuySellOrdersCount();
        assertThat(actualAssetBuySellOrdersCount)
                .withFailMessage("Expected row count to be greater than <%d> but was <%d>.", rowsCount, actualAssetBuySellOrdersCount)
                .isGreaterThan(rowsCount);
    }

    public void closePreMarketSessionPopUpIfVisible() {
        TradingSecondaryMarketsDashboardAssetsCatalog secondaryMarketsDashboardAssetsCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        if (secondaryMarketsDashboardAssetsCatalog.isPreMarketSessionPopUpVisible()) {
            secondaryMarketsDashboardAssetsCatalog.closePreMarketSessionPopUp();
        }
    }

    public void createBuyOrder(Double orderPrice, Double orderQuantity) {
        clickAssetDetailsBuyBtn();
        completeBuyOrder(orderPrice, orderQuantity);
    }

    public void cancelAllActiveOrders() {
        clickMyOrdersTab();
        TradingSecondaryMarketsMyOrdersPage_ActiveOrders tradingSecondaryMarketsMyOrdersPage_activeOrders = new TradingSecondaryMarketsMyOrdersPage_ActiveOrders(getBrowser());
        tradingSecondaryMarketsMyOrdersPage_activeOrders.clickActiveOrdersTab();
        tradingSecondaryMarketsMyOrdersPage_activeOrders.cancelActiveOrders();
    }

    public boolean cancelFirstActiveOrder() {
        clickMyOrdersTab();
        TradingSecondaryMarketsMyOrdersPage_ActiveOrders tradingSecondaryMarketsMyOrdersPageActiveOrders = new TradingSecondaryMarketsMyOrdersPage_ActiveOrders(getBrowser());
        tradingSecondaryMarketsMyOrdersPageActiveOrders.clickActiveOrdersTab();
        return tradingSecondaryMarketsMyOrdersPageActiveOrders.cancelFirstActiveOrder();
    }

    public void assertChartIsVisible(String symbol) {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        Assert.assertEquals(tradingSecondaryMarketsAssetDetail.getChartTitle(), String.format("%s Price", symbol.toUpperCase()), "The chart is not displayed");
    }

    public void assertTradesAreListed() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        Assert.assertFalse(tradingSecondaryMarketsAssetDetail.getTrades().isEmpty(), "The trades are not listed");
    }

    public void assertBuyOrderCreated(ATS_BuyOrder atsBuyOrder) {
        clickMyOrdersTab();
        TradingSecondaryMarketsMyOrdersPage_ActiveOrders secondaryMarketsMyOrdersPageActiveOrders = new TradingSecondaryMarketsMyOrdersPage_ActiveOrders(getBrowser());
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getSideValue(), "Buy", AssertionErrors.ITS_NOT_A_BUY_ORDER);
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getPriceValue(), "$" + getStringFromDouble(atsBuyOrder.getBuyPrice(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getQuantityValue(), getStringFromDouble(atsBuyOrder.getBuyQuantity(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
    }

    public void createSellOrder(Double orderPrice, Double orderQuantity) {
        clickSellButton();
        TradingSecondaryMarketsCreateOrders secondaryMarketsCreateOrders = new TradingSecondaryMarketsCreateOrders(getBrowser());
        secondaryMarketsCreateOrders.createSellOrder(orderPrice, orderQuantity);
    }

    public void assertSellOrderCreated(ATS_SellOrder atsSellOrder) {
        clickMyOrdersTab();
        TradingSecondaryMarketsMyOrdersPage_ActiveOrders secondaryMarketsMyOrdersPageActiveOrders = new TradingSecondaryMarketsMyOrdersPage_ActiveOrders(getBrowser());
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getSideValue(), "Sell", AssertionErrors.ITS_NOT_A_SELL_ORDER);
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getPriceValue(), "$" + getStringFromDouble(atsSellOrder.getSellPrice(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getQuantityValue(), getStringFromDouble(atsSellOrder.getSellQuantity(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
    }

    public void assertSellOrderStatusInHistoryTab(ATS_SellOrder atsSellOrder, String status) {
        TradingSecondaryMarketsAssetDetail secondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        secondaryMarketsAssetDetail.clickMyOrdersTab();
        TradingSecondaryMarketsMyOrdersPage_History secondaryMarketsMyOrdersPageHistory = new TradingSecondaryMarketsMyOrdersPage_History(getBrowser());
        secondaryMarketsMyOrdersPageHistory.clickHistoryTab();
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getSideValue(), "Sell", AssertionErrors.ITS_NOT_A_SELL_ORDER);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getPriceValue(), "$" + getStringFromDouble(atsSellOrder.getSellPrice(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getQuantityValue(), getStringFromDouble(atsSellOrder.getSellQuantity(), Locale.US), AssertionErrors.ORDER_QUANTITY_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getStatusValue(), status, AssertionErrors.ORDER_STATUS_DOES_NOT_MATCH);
    }

    public void assertSellOrderStatusInHistoryTab(ATS_SellOrder atsSellOrder, String status, String previousOrderId, String latestOrderId) {
        TradingSecondaryMarketsAssetDetail secondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        secondaryMarketsAssetDetail.clickMyOrdersTab();
        TradingSecondaryMarketsMyOrdersPage_History secondaryMarketsMyOrdersPageHistory = new TradingSecondaryMarketsMyOrdersPage_History(getBrowser());
        secondaryMarketsMyOrdersPageHistory.clickHistoryTab();
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getSideValue(), "Sell", AssertionErrors.ITS_NOT_A_SELL_ORDER);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getPriceValue(), "$" + getStringFromDouble(atsSellOrder.getSellPrice(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getQuantityValue(), getStringFromDouble(atsSellOrder.getSellQuantity(), Locale.US), AssertionErrors.ORDER_QUANTITY_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getStatusValue(), status, AssertionErrors.ORDER_STATUS_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getOrderIdByIndex(1), previousOrderId, AssertionErrors.ORDER_ID_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getOrderIdByIndex(0), latestOrderId, AssertionErrors.ORDER_ID_DOES_NOT_MATCH);
    }

    public void assertBuyOrderStatusInHistoryTab(ATS_BuyOrder atsBuyOrder, String status) {
        TradingSecondaryMarketsAssetDetail secondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        secondaryMarketsAssetDetail.clickMyOrdersTab();
        TradingSecondaryMarketsMyOrdersPage_History secondaryMarketsMyOrdersPageHistory = new TradingSecondaryMarketsMyOrdersPage_History(getBrowser());
        secondaryMarketsMyOrdersPageHistory.clickHistoryTab();
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getSideValue(), "Buy", AssertionErrors.ITS_NOT_A_BUY_ORDER);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getPriceValue(), "$" + getStringFromDouble(atsBuyOrder.getBuyPrice(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getQuantityValue(), getStringFromDouble(atsBuyOrder.getBuyQuantity(), Locale.US), AssertionErrors.ORDER_QUANTITY_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getStatusValue(), status, AssertionErrors.ORDER_STATUS_DOES_NOT_MATCH);
    }

    public void assertOpenSellOrderCreated(ATS_SellOrder ats_sellOrder, Double filled) {
        TradingSecondaryMarketsMyOrdersPage_ActiveOrders secondaryMarketsMyOrdersPageActiveOrders = new TradingSecondaryMarketsMyOrdersPage_ActiveOrders(getBrowser());
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getSideValue(), "Sell", AssertionErrors.ITS_NOT_A_SELL_ORDER);
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getPriceValue(), "$" + getStringFromDouble(ats_sellOrder.getSellPrice(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getQuantityValue(), getStringFromDouble(ats_sellOrder.getSellQuantity(), Locale.US), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getFilledValue(), getStringFromDouble(filled), AssertionErrors.FILLED_DOES_NOT_MATCH);

        secondaryMarketsMyOrdersPageActiveOrders.expandFirstOrder();
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getFirstOrderPriceValue(), "$" + getStringFromDouble(ats_sellOrder.getSellPrice()), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageActiveOrders.getFirstOrderFilledValue(), getStringFromDouble(filled), AssertionErrors.FILLED_DOES_NOT_MATCH);
    }

    public void assertCancelledSellOrderCreated(ATS_SellOrder ats_sellOrder, Double filled) {
        TradingSecondaryMarketsMyOrdersPage_History secondaryMarketsMyOrdersPageHistory = new TradingSecondaryMarketsMyOrdersPage_History(getBrowser());
        secondaryMarketsMyOrdersPageHistory.clickHistoryTab();
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getSideValue(), "Sell", AssertionErrors.ITS_NOT_A_SELL_ORDER);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getPriceValue(), "$" + getStringFromDouble(ats_sellOrder.getSellPrice()), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getQuantityValue(), getStringFromDouble(ats_sellOrder.getSellQuantity()), AssertionErrors.ORDER_QUANTITY_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getStatusValue(), "Cancelled", AssertionErrors.ORDER_STATUS_DOES_NOT_MATCH);
        secondaryMarketsMyOrdersPageHistory.expandFirstOrder();
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getFirstOrderPriceValue(), "$" + getStringFromDouble(ats_sellOrder.getSellPrice()), AssertionErrors.ORDER_PRICE_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getFirstOrderFilledValue(), getStringFromDouble(filled), AssertionErrors.FILLED_DOES_NOT_MATCH);
        Assert.assertEquals(secondaryMarketsMyOrdersPageHistory.getFirstStatusValue(), "Done", AssertionErrors.ORDER_STATUS_DOES_NOT_MATCH);
    }

    public void clickAssetDetailsBuyBtn() {
        TradingSecondaryMarketsAssetDetail secondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        secondaryMarketsAssetDetail.clickAssetDetailBuyButton();
    }

    public void clickTradesTab() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        tradingSecondaryMarketsAssetDetail.clickTradesTab();
    }

    public void clickMyOrdersTab() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        tradingSecondaryMarketsAssetDetail.clickMyOrdersTab();
    }

    public void clickHistoryTab() {
        TradingSecondaryMarketsMyOrdersPage_History secondaryMarketsMyOrdersPageHistory = new TradingSecondaryMarketsMyOrdersPage_History(getBrowser());
        secondaryMarketsMyOrdersPageHistory.clickHistoryTab();
    }

    public void clickAssetInfoTab() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        tradingSecondaryMarketsAssetDetail.clickAssetInfoTab();
    }

    public void completeBuyOrder(Double orderPrice, Double orderQuantity) {
        TradingSecondaryMarketsCreateOrders secondaryMarketsCreateOrders = new TradingSecondaryMarketsCreateOrders(getBrowser());
        secondaryMarketsCreateOrders.createBuyOrder(orderPrice, orderQuantity);
    }

    public void validateThereIsNoOrderCreated() {
        // TODO
    }

    public boolean isATSAvailable() {
        return false;
    }

    public void validateNoActiveOrders() {
        TradingSecondaryMarketsMyOrdersPage_ActiveOrders tradingSecondaryMarketsMyOrdersPage_activeOrders = new TradingSecondaryMarketsMyOrdersPage_ActiveOrders(getBrowser());
        tradingSecondaryMarketsMyOrdersPage_activeOrders.assertNoActiveOrdersText();
    }

    public void logoutAts() {
        startTestLevel("Logout secondary market");
        closePreMarketSessionPopUpIfVisible();
        TradingSecondaryMarketsMyAccountButton secondaryMarketsMyAccountButton = new TradingSecondaryMarketsMyAccountButton(getBrowser());
        secondaryMarketsMyAccountButton
                .clickUserIcon()
                .clickLogoutLink();
        secondaryMarketsMyAccountButton.waitForLogoutToComplete();
        endTestLevel();
    }

    public void deleteCreatedOrders() {
        // TODO
    }

    public void clickBuyButton() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        tradingSecondaryMarketsAssetDetail.clickAssetDetailBuyButton();
    }

    public void clickSellButton() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        tradingSecondaryMarketsAssetDetail.clickAssetDetailSellButton();
    }

    public String getTradingModalDueDate() {
        TradingSecondaryMarketsTradeModal tradingSecondaryMarketsTradeModal = new TradingSecondaryMarketsTradeModal(getBrowser());
        return tradingSecondaryMarketsTradeModal.getDueDate();
    }

    public void closeOrderModal() {
        TradingSecondaryMarketsTradeModal tradingSecondaryMarketsTradeModal = new TradingSecondaryMarketsTradeModal(getBrowser());
        tradingSecondaryMarketsTradeModal.clickCloseModalBtn();
    }

    public void validateBuyModalLoaded() {
        clickBuyButton();
        String modalDate = getTradingModalDueDate();
        Assert.assertNotNull(modalDate);
        closeOrderModal();
    }

    public void validateSellModalLoaded() {
        clickSellButton();
        String modalDate = getTradingModalDueDate();
        Assert.assertNotNull(modalDate);
        closeOrderModal();
    }

    public String getStringFromDouble(Double value) {
        final DecimalFormat of = new DecimalFormat("0.00");
        return of.format(value);
    }

    public String getStringFromDouble(Double value, Locale locale) {
        final DecimalFormat of = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(locale));
        return of.format(value);
    }

    public String getAwsProperty(String key) {
        return DataManager.getInstance("ats").getAwsProperty(key);
    }

    public boolean isAssetEnabledForTrading(String security) {
        JSONArray array = JsonPath.read(atsAssetAPI.getAssets(), String.format("[?(@.security=='%s')].enabled", security));
        return (Boolean) array.get(0);
    }

    public boolean isBuyButtonEnabled() {
        TradingSecondaryMarketsAssetDetail secondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        return secondaryMarketsAssetDetail.isBuyButtonDisplayed();
    }

    public boolean isSellButtonEnabled() {
        TradingSecondaryMarketsAssetDetail secondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        return secondaryMarketsAssetDetail.isSellButtonDisplayed();
    }

    public void validateHoldingIconsAreDisplayed(List<String> listFromGateway) {
        TradingSecondaryMarketsDashboardAssetsCatalog secondaryMarketDashboardAssetCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        List<String> listFromUI = secondaryMarketDashboardAssetCatalog.getHoldingAssets().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        Assert.assertTrue(listFromUI.size() > 0, "The UI does not display the holding icons");
        Assert.assertEquals(listFromUI, listFromGateway, "The holdings icons from WEB and API are not the same");
    }

    public void clickAddFundsButton() {
        TradingSecondaryMarketsTradeModal tradingSecondaryMarketsTradeModal = new TradingSecondaryMarketsTradeModal(getBrowser());
        tradingSecondaryMarketsTradeModal.clickAddFundsBtn();
    }

    public void validateAddFundsModal(List<String> availableDepositOptions) {
        TransferFundsModalPage transferFundsModalPage = new TransferFundsModalPage(getBrowser());
        Assert.assertTrue(transferFundsModalPage.isPageLoaded(), "The deposit funds modal is not visible");
        Assert.assertEquals(availableDepositOptions, transferFundsModalPage.getPaymentMethods(),
                "The payment methods validation failed");
        transferFundsModalPage.clickCloseBtn();
        closeOrderModal();
    }

    public void addAssetToWatchlist(String assetName) {
        TradingSecondaryMarketsDashboardAssetsCatalog tradingSecondaryMarketsDashboardAssetsCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        tradingSecondaryMarketsDashboardAssetsCatalog.addAssetToWatchlist(assetName);
    }

    public void validateAssetIsInWatchlist() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        boolean isSelected = tradingSecondaryMarketsAssetDetail.isAssetInWatchlist();
        Assert.assertTrue(isSelected, "The asset is not in the watchlist");
        tradingSecondaryMarketsAssetDetail.clickWatchlistButton();
        TradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal = new TradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal(getBrowser());
        Assert.assertTrue(tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.isToastVisible(), "The remove toast is not visible");
        Assert.assertEquals(tradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal.getToastMessage(), "Removed asset from the watchlist", "The remove toast text is incorrect");
    }

    public void validateMarketsCount(int expectedCount) {
        TradingSecondaryMarketsDashboardAssetsCatalog tradingSecondaryMarketsDashboardAssetsCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        Assert.assertEquals(tradingSecondaryMarketsDashboardAssetsCatalog.getMarketsCount(), expectedCount, "The expected markets count is not correct");
    }

    public void validateMarketAssets(List<String> assetsList, String market) {
        TradingSecondaryMarketsDashboardAssetsCatalog tradingSecondaryMarketsDashboardAssetsCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        Assert.assertEqualsNoOrder(tradingSecondaryMarketsDashboardAssetsCatalog.getAssetsByMarket(market), assetsList, "The expected markets count is not correct");
    }

    public void validateAssetInfoTab(String tokenName) {
        TradingSecondaryMarketsAssetInfoTab<AbstractATS> tradingSecondaryMarketsAssetInfoTab = new TradingSecondaryMarketsAssetInfoTab<>(getBrowser());
        Assert.assertTrue(tradingSecondaryMarketsAssetInfoTab.getFirstCardTitle().contains(tokenName), "The card title is incorrect");
    }

    public boolean isOrderBookEmpty() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        return tradingSecondaryMarketsAssetDetail.isOrderBookEmpty();
    }

    public void isButtonDisplayedInTheOrderBook(String button) {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        tradingSecondaryMarketsAssetDetail.isButtonDisplayedInTheOrderBook(button);
    }

    public void verifyAssetDetailsValues() {
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());

        String regex = "\\$\\d+\\.\\d{2}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tradingSecondaryMarketsAssetDetail.getLastPrice());
        Assert.assertTrue(matcher.matches(), "The last price is not displayed");
        matcher = pattern.matcher(tradingSecondaryMarketsAssetDetail.getOpenPrice());
        Assert.assertTrue(matcher.matches(), "The open price is not displayed");
        matcher = pattern.matcher(tradingSecondaryMarketsAssetDetail.getClosePrice());
        Assert.assertTrue(matcher.matches(), "The close price is not displayed");
        matcher = pattern.matcher(tradingSecondaryMarketsAssetDetail.getHighPrice());
        Assert.assertTrue(matcher.matches(), "The high price is not displayed");
        matcher = pattern.matcher(tradingSecondaryMarketsAssetDetail.getLowPrice());
        Assert.assertTrue(matcher.matches(), "The low price is not displayed");

    }

    public String setRegularTrading(String tokenName) {
        String responseAsString = atsAssetAPI.setTradingType(tokenName, "REGULAR");
        String actualTradingType = JsonPath.read(responseAsString, "$.tradingType");
        Assert.assertEquals(actualTradingType, "REGULAR", "There was a problem updating the tradingType back to regular");
        return actualTradingType;
    }

    public String setLimitedTrading(String tokenName, long windowStartTime, long windowEndTime) {
        Map body = new HashMap<>();
        body.put("tradingType", "LIMITED_TRADING");
        body.put("windowStartTime", windowStartTime);
        body.put("windowEndTime", windowEndTime);
        String responseAsString = atsAssetAPI.setTradingType(tokenName, body);
        String actualTradingType = JsonPath.read(responseAsString, "$.tradingType");
        Assert.assertEquals(actualTradingType, "LIMITED_TRADING", "There was a problem updating the tradingType");
        return actualTradingType;
    }

    public String getFirstAssetName(){
        TradingSecondaryMarketsDashboardAssetsCatalog tradingSecondaryMarketsDashboardAssetsCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());

        return tradingSecondaryMarketsDashboardAssetsCatalog.getFirstAsset();
    }

    public boolean isRegionAvailable(String region, AtsGatewayAPI atsGatewayAPI) {
        String config = atsGatewayAPI.getConfig();
        List<Map<String, Object>> regions = JsonPath.read(config, String.format("$.market.regions[?(@.identifier=='%s')]", region));

        if (regions.isEmpty()) {
            return false;
        } else {
            Map<String, Object> usRegion = regions.get(0);
            return (boolean) usRegion.getOrDefault("isAvailable", false);
        }
    }

    public void validateRegionStatus(String region, AtsGatewayAPI atsGatewayAPI) {
        boolean available = isRegionAvailable(region, atsGatewayAPI);
        String expectedStatus = available ? "Open" : "Closed";
        TradingSecondaryMarketsDashboardAssetsCatalog tradingSecondaryMarketsDashboardAssetsCatalog = new TradingSecondaryMarketsDashboardAssetsCatalog(getBrowser());
        String actualStatus = tradingSecondaryMarketsDashboardAssetsCatalog.getRegionStatus(region);
        Assert.assertEquals(actualStatus, expectedStatus, String.format("The market status for region '%s' is incorrect", region));
    }

    public String getLastOrder(AtsGatewayAPI atsGatewayAPI, String security, String status) {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("security", security);
        queryParams.put("pageNumber", "1");
        queryParams.put("pageSize","1");
        queryParams.put("status", status);
        String response = atsGatewayAPI.getOrders(queryParams);
        return JsonPath.read(response, "$.data[0].orderId");
    }

}
