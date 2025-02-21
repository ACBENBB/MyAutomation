package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class CpSecuritiesTransactions extends AbstractPage {

    private static final ExtendedBy searchBox = new ExtendedBy("Search Box", By.xpath("//input[@placeholder='Search...']"));
    private static final ExtendedBy securitiesTransactionsTable = new ExtendedBy("Securities Transactions Investors table", By.xpath("//table"));
    private static final ExtendedBy securitiesTransactionsInvestorsTableColumnHeaders = new ExtendedBy("Investors table columns headers", By.xpath("//table//th"));
    private static final ExtendedBy entryFoundInList = new ExtendedBy("Wait to find values in list", By.xpath("//span[text()=' Showing 1 - 1 of 1 entries ']"));
    private static final ExtendedBy fourthRow = new ExtendedBy("Fourth row", By.xpath("//table/tbody/tr[4]"));
    private static final String transactionRow = "//table/tbody/tr[1]/td/a[contains(text(), '%s')]/ancestor::tr/td[contains(text(), 'Redemption')]";
    private static final String transactionRow2 = "//table/tbody/tr[%s]/td/a[contains(text(), '%s')]/ancestor::tr/td[contains(text(), 'Issuance')]";
    private static final ExtendedBy hashTableElements = new ExtendedBy("hashTableElements", By.xpath("//tr[@role='row']//td[@aria-colindex=4]/a"));
    private static final ExtendedBy noRecordFoundMessage = new ExtendedBy("filter button", By.xpath("//div[contains(text(),' No records were found')]"));

    public CpSecuritiesTransactions(Browser browser) {
        super(browser, securitiesTransactionsTable);
    }

    public void searchValueInvestorByTextBox(String transactionId, String nonValidSearch) {
            browser.typeTextElementCtrlA(searchBox, nonValidSearch);
            browser.waitForElementVisibility(noRecordFoundMessage);
            browser.typeTextElementCtrlA(searchBox, transactionId);
            browser.waitForElementVisibility(entryFoundInList);
    }

    public void clearValueFromTextBox() {
        browser.clearTextElement(searchBox);
    }

    public String getInvestorDetailByIndex(int index, String tableColumn) {
        List<WebElement> investorRows = browser.findElements(securitiesTransactionsTable);
        int columnIndex = getColumnIndex(tableColumn);
        WebElement investorRowByIndex = investorRows.get(index);
        List<WebElement> investorDetailCells = investorRowByIndex.findElements(By.xpath("//td"));
        return browser.getElementText(investorDetailCells.get(columnIndex), "investor at index number" + index + " column " + tableColumn);
    }

    public String waitInvestorDetailsPage(int timeoutSeconds, ExtendedBy clickElement){
        browser.clickWithJs(browser.findElement(clickElement));
        Function<String, String> internalWaitInvestorDetailsPage = t -> {
            try {
                String pageTitle = browser.getPageTitle().toLowerCase();
                    if (!pageTitle.contains("details")){
                        browser.waitForPageStable(Duration.ofSeconds(5));
                        pageTitle = browser.getPageTitle().toLowerCase();
                        if (pageTitle.contains("transaction")){
                        browser.waitForElementClickable(clickElement);
                        browser.clickWithJs(browser.findElement(clickElement));
                        pageTitle = browser.getPageTitle().toLowerCase();
                        }
                    }
                return pageTitle;
            } catch (Exception e) {
                return null;
            }
        };
        String description = "Wait for Investor Details Page to upload";
        return Browser.waitForExpressionNotNull(internalWaitInvestorDetailsPage, null, description, timeoutSeconds, 3000);
    }

    public CpInvestorDetailsPage getInvestorDetailByIDP(String tableColumn, int timeoutSeconds) {
        int columnIndex = getColumnIndex(tableColumn);
        int indexInt = columnIndex + 1;
        ExtendedBy receiverCell = new ExtendedBy("Receiver cell", By.xpath("//table//td["+indexInt+"]//a"));
        waitInvestorDetailsPage(timeoutSeconds, receiverCell);
        return new CpInvestorDetailsPage(browser);
    }


    private int getColumnIndex(String name) {
        List<WebElement> headers = browser.findElements(securitiesTransactionsInvestorsTableColumnHeaders);
        for (int i = 0; i < headers.size(); i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }

        throw new RuntimeException("Unable to find column with header name of " + name);
    }

    public boolean isRedemptionTransactionPresentInSecuritiesTransaction(String transactionHash) {
        long t = System.currentTimeMillis();
        //Setting 15 minutes of wait as max time
        long end = t + 900000;
        //Setting 10 minutes of wait as max time
        boolean isPresent = false;
        while (System.currentTimeMillis() < end || isPresent) {
            browser.refreshPage();
            browser.waitForPageStable(Duration.ofSeconds(4));
            browser.scrollToBottomOfElement(fourthRow);
            WebElement button = null;
            try {
                button = browser.findElement(new ExtendedBy("Transaction", By.xpath(String.format(transactionRow, transactionHash))));
                if (button.isDisplayed()) {
                    isPresent = true;
                    break;
                }
            } catch (NoSuchElementException ex) {
                // button is missing, trying again...
            } catch (StaleElementReferenceException ex2) {

            }
        }
        return isPresent;
    }

    public void waitFotTransactionToBePresentInSecurities(String transactionHash) {
        Function<String, Boolean> internalWaitForTransactionHash = t -> {
            browser.waitForPageStable();
            List<WebElement> hashElementsList = browser.findElements(hashTableElements);
            for (WebElement element : hashElementsList) {
                if (element.getText()
                        .contains(transactionHash)) {
                    return true;
                } else {
                    browser.refreshPage();
                    return false;
                }
            }
            return false;
        };
        String description = "Waiting for Tx with Hash = " + transactionHash + "to be present.";
        Browser.waitForExpressionToEqual(internalWaitForTransactionHash, null, true, description, 180, 20000);
    }

}
