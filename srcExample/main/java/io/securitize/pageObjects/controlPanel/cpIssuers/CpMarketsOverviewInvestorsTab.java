package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.debug;
import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class CpMarketsOverviewInvestorsTab extends AbstractPage {

    private static final ExtendedBy searchBar = new ExtendedBy("Search bar", By.xpath("//input[@class='MuiInput-input MuiInputBase-input MuiInputBase-inputAdornedStart MuiInputBase-inputAdornedEnd css-mnn31']"));
    private static final ExtendedBy marketsOverviewInvestorsTable = new ExtendedBy("Investors table", By.xpath("//div[@class='MuiDataGrid-main css-opb0c2']"));
    private static final ExtendedBy marketsOverviewInvestorsTableColumnHeaders = new ExtendedBy("Investors table columns headers", By.xpath("//div[@class='MuiDataGrid-columnHeader']"));
    private static final ExtendedBy marketsOverviewInvestorRows = new ExtendedBy("Investors table investor rows", By.xpath("//div[@class='MuiDataGrid-row']"));
    private static final ExtendedBy marketsOverviewUniqueRow = new ExtendedBy("Investors table unique row", By.xpath("//div[@class='MuiDataGrid-row MuiDataGrid-row--lastVisible']"));
    private static final ExtendedBy uniqueEntryFoundInList = new ExtendedBy("Wait to find a unique value in list", By.xpath("//p[contains(text(),'1–1 of 1')]"));

    public CpMarketsOverviewInvestorsTab(Browser browser) {
        super(browser, searchBar);
    }

    public void searchUniqueInvestorByTextBox(String investorName){
        browser.click(searchBar).clear();
        browser.click(searchBar).sendKeys(investorName);
        browser.waitForElementTextNotEmpty(searchBar);
        browser.waitForElementVisibility(uniqueEntryFoundInList);
    }

    public String getInvestorDetailByUniqueRow(String tableColumn) {
        for (int i = 0; i < 3; i++) {
            try {
                int columnIndex = getColumnIndex(tableColumn);

                WebElement investorRow = browser.findElement(marketsOverviewUniqueRow);
                List<WebElement> investorDetailCells = investorRow.findElements(By.xpath("//div[@role='cell']"));

                return browser.getElementText(investorDetailCells.get(columnIndex + 1), "unique investor column " + tableColumn);
            } catch (StaleElementReferenceException e) {
                // only in cases of stale element give another try - otherwise fail
                debug("Stale element exception occur. Will try again..." + e);
                browser.waitForPageStable();
            }
        }

        errorAndStop("Too many stale element event occur.. Terminating...", false);
        return null;
    }

    private int getColumnIndex(String name) {
        List<WebElement> headers = browser.findElements(marketsOverviewInvestorsTableColumnHeaders);
        for (int i = 0; i < headers.size() ; i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }

        throw new RuntimeException("Unable to find column with header name of " + name);
    }
}
