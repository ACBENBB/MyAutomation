package io.securitize.pageObjects.controlPanel.cpIssuers.etherScan;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class EtherScanTransactionDetailsPage extends AbstractPage {

    private static final ExtendedBy tokensIFrame = new ExtendedBy("tokens pane iframe", By.id("tokenpageiframe"));
    private static final ExtendedBy tokensTableRows = new ExtendedBy("tokens table rows (inside the iframe)", By.xpath("//table/tbody/tr"));
    private static final ExtendedBy tokensTableColumns = new ExtendedBy("tokens table columns (inside the iframe)", By.xpath("//table/thead/tr/th"));

    public EtherScanTransactionDetailsPage(Browser browser) {
        super(browser, tokensIFrame);
    }

    public int getTransactionsCount() {
        browser.switchToFrame(tokensIFrame);
        List<WebElement> tokenRows = browser.findElements(tokensTableRows);
        browser.switchBackToDefaultContent();
        return tokenRows.size();
    }

    public String getTokensTransactionDataByIndex(String columnName, int index) {
        browser.switchToFrame(tokensIFrame);
        List<WebElement> tokenRows = browser.findElements(tokensTableRows);

        // check the requested index exists
        if (tokenRows.size() < (index + 1)) {
            errorAndStop("An error occur extracting token information from etherscan - requested row index of " + index + " doesn't exist, as table only has " + tokenRows.size() + " rows", true);
        }

        // find requested column position
        List<WebElement> tableColumns = browser.findElements(tokensTableColumns);
        for (int i = 0; i < tableColumns.size(); i++) {
            WebElement currentColumn = tableColumns.get(i);
            String currentColumnHeader = currentColumn.getText();
            if (currentColumnHeader.trim().toLowerCase().equals(columnName.trim().toLowerCase())) {
                // return requested data
                WebElement requestedCell = tokenRows.get(index).findElements(By.tagName("td")).get(i + 1);
                String result = browser.getElementText(requestedCell, "Tokens table, row " + index + " column " + columnName);
                browser.switchBackToDefaultContent();
                return result;
            }
        }

        errorAndStop("An error occur extracting token information from etherscan - can't find requested column of " + columnName, true);
        return null;
    }
}