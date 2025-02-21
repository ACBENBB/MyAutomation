package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.exceptions.WalletNotReadyTimeoutException;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdAddWallet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.infoAndShowMessageInBrowser;

public class NieDashboardPortfolio extends AbstractPage {

    // tabs
    private static final ExtendedBy walletTabButton = new ExtendedBy("Switch to the 'Wallets' tab button", By.xpath("//li[@class='nav-item']//span[contains(text(), 'Wallets')]"));
    private static final ExtendedBy ownedAssetsTabButton = new ExtendedBy("Switch to the 'Owned assets' tab button", By.id("owned-assets-tab"));

    private static final ExtendedBy ownedAssetsCount = new ExtendedBy("Owned assets count", By.xpath("//a[@id='owned-assets-tab']//span[contains(text(),'(')]"));

    private static final ExtendedBy investmentRequestsCount = new ExtendedBy("Investment requests counter", By.xpath("//span[contains(text(), 'investment requests')]//following-sibling::span"));
    private static final ExtendedBy investmentCardHeaders = new ExtendedBy("Investment card headers", By.xpath("//div[@class='portfolio-tab-card-body__logo']/span"));

    private static final String committedInvestmentAmountTemplate = "//div[@class='portfolio-tab-card-body__logo']/span[contains(text(), '%s')]//following::span[contains(@id, 'committed-investment-amount')]";
    private static final String fundedInvestmentAmountTemplate = "//div[@class='portfolio-tab-card-body__logo']/span[contains(text(), '%s')]//following::span[contains(@id, 'funded-investment-amount')]";
    private static final String investmentVerificationStatusTemplate = "//span[contains(text(), '%s')]//ancestor ::div[@class='portfolio-tab-card-body']//preceding-sibling::div//div[contains(@class, 'portfolio-tabs-header__top__badge')]";
    private static final ExtendedBy continueInvestmentButton = new ExtendedBy("Portfolio Investmet request Continue investment button",By.xpath("//button[contains(@id ,'btn-fund-investment')]"));

    // wallets tab
    private static final ExtendedBy addNewWalletButton = new ExtendedBy("Add new wallet button", By.id("btn-add-wallet"));
    private static final ExtendedBy addWalletInSecIdButton = new ExtendedBy("Add new wallet in Securitize ID button", By.id("btn-confirm-add-wallet"));
    private static final ExtendedBy walletDetailsLabel = new ExtendedBy("Wallet details label", By.xpath("//p[contains(@class, 'token-wallets__wallet-name')]"));
    private static final String authorizeWalletByAssetNameTemplate = "//tr/td/div[contains(text(), '%s')]//ancestor::tr/td/button[contains(@id, 'btn-authorize-wallet')]";
    private static final String walletStatusByAssetNameTemplate = "//tr/td/div[contains(text(), '%s')]//ancestor::tr/td/div[contains(@class, 'wallets__status')]";
    private static final String walletBalanceByAssetNameTemplate = "//tr/td/div[contains(text(), '%s')]//ancestor::tr/td/div[contains(@id, 'tokensHeld')]";

    private static final String ownedAssetStatusTemplate = "//div[contains(@class, 'portfolio-tab-card-body')]//span[contains(text(), '%s')]//ancestor::div//div[contains(@id, 'investment-status-label')]";
    private static final String ownedAssetBalanceTemplate = "//div[contains(@class, 'portfolio-tab-card-body')]//span[contains(text(), '%s')]//ancestor::div//span[contains(@id, 'total-token-balance')]";
    private static final String ownedAssetCurrentValueTemplate = "//div[contains(@class, 'portfolio-tab-card-body')]//span[contains(text(), '%s')]//ancestor::div//span[contains(@id, 'current-value')]";
    private static final String ownedAssetViewDetailsButtonTemplate = "//div[@class='portfolio-tab-card-body__logo']/span[contains(text(), '%s')]//following::button[contains(@id, 'btn-view-token')]";


    public NieDashboardPortfolio(Browser browser) {
        super(browser, investmentRequestsCount);
    }

    public int getInvestmentsRequests() {
        String rawCount = browser.getElementText(investmentRequestsCount);
        return RegexWrapper.stringToInteger(rawCount);
    }

    public boolean isInvestmentRequestVisibleByHeader(String header) {
        List<WebElement> elements = browser.findElements(investmentCardHeaders);
        for (WebElement currentHeader : elements) {
            if (browser.getElementText(currentHeader, "Current card header").trim().equalsIgnoreCase(header.trim())) {
                return true;
            }
        }
        return false;
    }


    public float getCommittedInvestmentByName(String investmentName) {
        String locator = String.format(committedInvestmentAmountTemplate, investmentName);
        ExtendedBy extendedBy = new ExtendedBy(investmentName + " - Committed investment amount", By.xpath(locator));
        String amount = browser.getElementText(extendedBy);
        return RegexWrapper.stringToFloat(amount);
    }

    public float getFundedInvestmentByName(String investmentName) {
        String locator = String.format(fundedInvestmentAmountTemplate, investmentName);
        ExtendedBy extendedBy = new ExtendedBy(investmentName + " - Funded investment amount", By.xpath(locator));
        String amount = browser.getElementText(extendedBy);
        return RegexWrapper.stringToFloat(amount);
    }

    public String getInvestmentVerificationStatusByName(String investmentName) {
        String locator = String.format(investmentVerificationStatusTemplate, investmentName);
        ExtendedBy extendedBy = new ExtendedBy(investmentName + " - investment verification status", By.xpath(locator));
        return browser.getElementText(extendedBy);
    }

    public void clickContinueInvestmentButton() {
        browser.click(continueInvestmentButton,false);
    }


    public NieDashboardPortfolio switchToWalletsTab() {
        browser.click(walletTabButton);
        return this;
    }

    public NieDashboardPortfolio clickAddNewWallet() {
        browser.click(addNewWalletButton);
        return this;
    }

    public SecuritizeIdAddWallet clickAddWalletInSecId() {
        browser.click(addWalletInSecIdButton, false);
        return new SecuritizeIdAddWallet(browser);
    }

    public String getWalletDetailsLabel() {
        browser.waitForElementVisibility(walletDetailsLabel, 60);
        return browser.getElementText(walletDetailsLabel);
    }

    public void authorizeWalletByAssetName(String assetName) {
        String locator = String.format(authorizeWalletByAssetNameTemplate, assetName);
        ExtendedBy extendedBy = new ExtendedBy(assetName + " - Authorise wallet button", By.xpath(locator));
        browser.click(extendedBy, false);
    }

    private String getWalletStatusByAssetName(String assetName) {
        String locator = String.format(walletStatusByAssetNameTemplate, assetName);
        ExtendedBy extendedBy = new ExtendedBy(assetName + " - Wallet status", By.xpath(locator));
        browser.waitForElementVisibility(extendedBy, 60);
//        browser.scrollToElement(browser.findElement(extendedBy), false);
        return browser.getElementText(extendedBy);
    }


    private int getWalletBalanceByAssetName(String assetName) {
        String locator = String.format(walletBalanceByAssetNameTemplate, assetName);
        ExtendedBy extendedBy = new ExtendedBy(assetName + " - Wallet balance", By.xpath(locator));
        browser.waitForElementVisibility(extendedBy, 60);
        browser.scrollToElement(browser.findElement(extendedBy), false);
        String valueAsString = browser.getElementText(extendedBy);
        return RegexWrapper.stringToInteger(valueAsString);
    }


    public void waitForWalletStatusToBecome(String assetName, String expectedStatus, boolean withBrowserRefresh, int waitTimeSeconds, int waitIntervalSeconds) {
        // introduced withBrowserRefresh to allow to disallow refreshing the browser between wait
        // cycles - this is due to product issue where refreshing before the wallet status becomes
        // syncing causes the sync to never happen
        Function<String, Boolean> internalWaitForStatus = t -> {
            try {
                if (withBrowserRefresh) {
                     browser.refreshPage();
                }
                info("Checking " + assetName + " wallet status...");
                String entityStatus = getWalletStatusByAssetName(assetName);
                if (withBrowserRefresh) {
                    infoAndShowMessageInBrowser(assetName + " wallet status: " + entityStatus);
                }
                return (entityStatus.trim().equalsIgnoreCase(expectedStatus.trim()));
            } catch (Exception e) {
                return false;
            }
        };

        String description = "WalletStatusToBecome=" + expectedStatus;
        RuntimeException exceptionToThrowOnTimeout = null;
        if (expectedStatus.equalsIgnoreCase("ready")) {
            exceptionToThrowOnTimeout = new WalletNotReadyTimeoutException("Wallet was not marked as ready even after " + waitTimeSeconds + " seconds.");
        }
        Browser.waitForExpressionToEqual(internalWaitForStatus, null, true, description, waitTimeSeconds, waitIntervalSeconds * 1000, exceptionToThrowOnTimeout);
    }

    public void waitForWalletTokensBalanceToBecome(String assetName, int expectedBalance, int timeoutSeconds, int intervalCheckSeconds) {

        Function<String, Boolean> internalWaitForStatus = t -> {
            try {
                browser.refreshPage();
                info("Checking " + assetName + " wallet balance...");
                int currentBalance = getWalletBalanceByAssetName(assetName);
                infoAndShowMessageInBrowser(assetName + " wallet balance: " + currentBalance);
                return (currentBalance == expectedBalance);
            } catch (Exception e) {
                return false;
            }
        };
        String description = "WalletTokensBalanceToBecome=" + expectedBalance;
        Browser.waitForExpressionToEqual(internalWaitForStatus, null, true, description, timeoutSeconds, intervalCheckSeconds * 1000);
    }



    public void switchToOwnedAssetsTab() {
        browser.click(ownedAssetsTabButton, false);
    }

    public int getOwnedAssetsHeaderCount() {
        String valueAsString = browser.getElementText(ownedAssetsCount);
        return RegexWrapper.stringToInteger(valueAsString);
    }

    public String getOwnedAssetStatusByName(String investmentName) {
        String locator = String.format(ownedAssetStatusTemplate, investmentName);
        ExtendedBy extendedBy = new ExtendedBy(investmentName + " - Owned assets status", By.xpath(locator));
        return browser.getElementText(extendedBy);
    }

    public int getOwnedAssetBalanceByName(String investmentName) {
        String locator = String.format(ownedAssetBalanceTemplate, investmentName);
        ExtendedBy extendedBy = new ExtendedBy(investmentName + " - Owned assets status", By.xpath(locator));
        String valueAsString = browser.getElementText(extendedBy);
        return RegexWrapper.stringToInteger(valueAsString);
    }

    public int getOwnedAssetCurrentValueByName(String investmentName) {
        String locator = String.format(ownedAssetCurrentValueTemplate, investmentName);
        ExtendedBy extendedBy = new ExtendedBy(investmentName + " - Owned assets current value", By.xpath(locator));
        String valueAsString = browser.getElementText(extendedBy);
        return RegexWrapper.stringToInteger(valueAsString);
    }

    public NieOwnedAssetDetails viewOwnedAssetDetailsByName(String investmentName) {
        String locator = String.format(ownedAssetViewDetailsButtonTemplate, investmentName);
        ExtendedBy extendedBy = new ExtendedBy(investmentName + " - Owned assets details button", By.xpath(locator));
        browser.click(extendedBy, false);

        return new NieOwnedAssetTokenDetails(browser).switchToInvestmentDetailsTab();
    }
}