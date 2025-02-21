package io.securitize.pageObjects.controlPanel;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class CpAuditLog extends AbstractPage {

    private static final ExtendedBy tableContainer = new ExtendedBy("Audit table container", By.xpath("//div[contains(@class, 'audit-log-table-container')]"));
    private static final ExtendedBy searchField = new ExtendedBy("Audit table search field", By.xpath("//input[@type='text' and contains(@placeholder, 'Search')]"));
    private static final ExtendedBy auditLogTable = new ExtendedBy("Audit log table", By.xpath("//table//tbody//td"));
    private static final ExtendedBy auditLogTableRows = new ExtendedBy("Audit log table", By.xpath("//table//tr"));
    private static final ExtendedBy auditLogTableHeaders = new ExtendedBy("Audit log table headers", By.xpath("//table//th"));

    public CpAuditLog(Browser browser) {
        super(browser, tableContainer);
    }

    public void searchByText(String text){
        browser.click(searchField).clear();
        browser.click(searchField).sendKeys(text);
        browser.waitForElementTextNotEmpty(searchField);
    }


    public void waitForTableToBeEmpty() {
        info("Waiting for table to not contain any rows");
        Browser.waitForExpressionToEqual(q -> browser.findElementsQuick(auditLogTable, 1).size() <= 1,
                null,
                true,
                "wait for table to be empty",
                30,
                1000);

    }

    public void waitForTableToBeNotEmpty() {
        info("Waiting for table to contains at least one row");
        Browser.waitForExpressionToEqual(q -> browser.findElementsQuick(auditLogTable, 1).size() > 1,
                null,
                true,
                "wait for table to be not empty",
                30,
                1000);
    }

    public String getTableData(int rowIndex, String columnHeader) {
        List<WebElement> rows = browser.findElements(auditLogTableRows);
        int columnIndex = getColumnIndex(columnHeader);

        String description = "table row #" + rowIndex + " column " + columnHeader;
        WebElement cell = rows.get(rowIndex).findElements(By.tagName("td")).get(columnIndex);
        return browser.getElementText(cell, description);
    }

    private int getColumnIndex(String name) {
        List<WebElement> headers = browser.findElements(auditLogTableHeaders);
        for (int i = 0; i < headers.size() ; i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }

        errorAndStop("Unable to find column with header name of " + name, true);
        return -1;
    }
}