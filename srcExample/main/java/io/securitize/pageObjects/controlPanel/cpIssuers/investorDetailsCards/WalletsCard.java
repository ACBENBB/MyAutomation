package io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.infoAndShowMessageInBrowser;

public class WalletsCard extends AbstractCard {

    private static final ExtendedBy walletsCard = new ExtendedBy("Wallets card", By.xpath("//div[@class='card mb-5']//h4[contains(text(), 'Wallets')]/ancestor::div[@class='card mb-5']"));
    private static final ExtendedBy walletRows = new ExtendedBy("wallet rows", By.xpath(".//tr"));

    public WalletsCard(Browser browser) {
        super(browser, "Wallets");
    }

    @Override
    public int getEntityCount() {
        int result = browser.findElementsInElement(walletsCard, walletRows).size() - 3;
        info("found " + result + " rows in table");
        return result;
    }

    @Override
    public List<WebElement> getEntityTableRows() {
        return browser.findElementsInElement(walletsCard, walletRows);
    }

    public void waitForWalletToHoldNumberOfTokens(int index, int numberOfTokens, int timeoutSeconds, int checkIntervalSeconds) {

        Function<String, Boolean> internalWaitForWalletToHoldTokens = t -> {
            try {
                browser.refreshPage(true);
                scrollTableIntoView(index);
                info("Checking number of tokens in the wallet status...");
                String actualTokensHeld = getDetailAtIndex(index, "Tokens Held").trim();
                infoAndShowMessageInBrowser("Wallet tokens held: " + actualTokensHeld);
                scrollTableIntoView(index);
                return (actualTokensHeld.equals(numberOfTokens+""));
            } catch (Exception e) {
                return false;
            }
        };

        String description = "waitForWalletToHoldNumberOfTokens=" + numberOfTokens;
        Browser.waitForExpressionToEqual(internalWaitForWalletToHoldTokens, null, true, description, timeoutSeconds, checkIntervalSeconds * 1000);
    }
}
