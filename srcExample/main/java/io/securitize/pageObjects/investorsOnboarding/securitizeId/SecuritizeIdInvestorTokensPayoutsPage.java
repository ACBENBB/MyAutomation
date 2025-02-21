package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;

public class SecuritizeIdInvestorTokensPayoutsPage extends AbstractPage {

    private static final ExtendedBy registerWalletButton = new ExtendedBy("Register wallet button", By.xpath("//button/span[text()='Register wallet']"));
    private static final ExtendedBy wallet_accountStatusLegend = new ExtendedBy("Wallet/Account status legend", By.xpath("//*[contains(text(),'Status Legend')]"));

    private static final ExtendedBy walletTableRow = new ExtendedBy("Wallets table row", By.xpath("//li[@class='wallets-list-row']"));
    private static final ExtendedBy walletTableRowWalletNameColumn = new ExtendedBy("Wallets table row - wallet name column", By.xpath("//i[contains(@class, 'wallets-icon')]/../span[2]"));
    private static final ExtendedBy walletTableRowWalletStatusColumn = new ExtendedBy("Wallets table row - wallet status column", By.xpath("//div[contains(@class, 'payouts-status')]"));
    private static final ExtendedBy walletTableRowWalletTokensColumn = new ExtendedBy("Wallets table row - wallet tokens column", By.xpath("//div[contains(@class, 'wallets-tokens')]"));
    private static final ExtendedBy walletTableRowWalletAddressColumn = new ExtendedBy("Wallets table row - wallet address column", By.xpath("//span[contains(@class, 'wallets-label') and contains(text(), 'Address')]/.."));

    private static final ExtendedBy registerAccountButton = new ExtendedBy("Register account button", By.xpath("//button/span[contains(text(),'Register Account')]"));
    private static final ExtendedBy registerAccountMetaMaskOption = new ExtendedBy("Register account with MetaMask option", By.xpath("//li/a/img[@alt='MetaMask']"));
    private static final ExtendedBy registerAccountMetaMaskButton = new ExtendedBy("Register account with MetaMask button", By.xpath("//button[contains(text(), 'Register with Metamask')]"));
    private static final ExtendedBy dismissButton = new ExtendedBy("Dismiss button", By.xpath("//button[contains(text(), 'Dismiss')]"));

    private static final ExtendedBy dashboardButton = new ExtendedBy("dashboard button", By.xpath("//a[@href='/']"));


    public SecuritizeIdInvestorTokensPayoutsPage(Browser browser) {
        super(browser, wallet_accountStatusLegend);
    }

    public SecuritizeIdInvestorTokensPayoutsPage(Browser browser, boolean isLegacyFlow) {
        super(browser, registerAccountButton);
    }

    public String getWalletStatus(String walletName) {
        return getWalletDetailFromTable(walletName, walletTableRowWalletStatusColumn.getBy(), "status");
    }

    public String getWalletAddress(String walletName) {
        String tempValue = getWalletDetailFromTable(walletName, walletTableRowWalletAddressColumn.getBy(), "address");
        // the address column structure differs from other columns, so we need to parse the result we get
        if (tempValue != null) {
            return tempValue.split("\n")[1].trim();
        } else {
            return "Error occur trying to read wallet address!";
        }
    }

    public int getWalletTokens(String walletName) {
        String result = getWalletDetailFromTable(walletName, walletTableRowWalletTokensColumn.getBy(), "tokens");
        return result == null ? -1 : Integer.parseInt(result);
    }

    private String getWalletDetailFromTable(String walletName, By columnBy, String columnName) {
        List<WebElement> walletsRows = browser.findElements(walletTableRow);
        for (WebElement currentRow: walletsRows) {
            String currentWalletName = currentRow.findElement(walletTableRowWalletNameColumn.getBy()).getText();
            info("Found wallet with name: " + currentWalletName);
            if (currentWalletName.equals(walletName)) {
                String currentWalletInfo = currentRow.findElement(columnBy).getText();
                info("Wallet " + currentWalletName + " " + columnName + ": " + currentWalletInfo);
                return currentWalletInfo.toLowerCase();
            }
        }

        return null;
    }


    public void waitForTokensCountToBeNotZero(String walletName, int timeoutSeconds, int checkIntervalMilliseconds) {

        Function<String, Boolean> internalWaitForTokensCount = t -> {
            try {
                info("Reading number of tokens...");
                browser.refreshPage();
                int tokensCount = getWalletTokens(walletName);
                info("Number of tokens: " + tokensCount);
                return (tokensCount > 0);
            } catch (Exception e) {
                return false;
            }
        };
        String description = "TokensCountToBeNotZero";
        Browser.waitForExpressionToEqual(internalWaitForTokensCount, null, true, description, timeoutSeconds, checkIntervalMilliseconds);
    }

    public SecuritizeIdInvestorTokensPayoutsPage clickRegisterAccountButton() {
        browser.click(registerAccountButton, false);
        return this;
    }

    public SecuritizeIdInvestorTokensPayoutsPage clickRegisterAccountWithMetaMaskOption() {
        browser.click(registerAccountMetaMaskOption, false);
        return this;
    }

    public void clickRegisterWithMetaMaskButton() {
        browser.click(registerAccountMetaMaskButton, false);
    }

    public void clickDismiss() {
        browser.click(dismissButton, false);
    }

    public SecuritizeIdInvestorHomePage switchToDashboardScreen() {
        browser.click(dashboardButton);
        return new SecuritizeIdInvestorHomePage(browser);
    }
}
