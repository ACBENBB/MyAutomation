package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CpMasterSecurityholderFile extends AbstractPage {

    private static final ExtendedBy searchBox = new ExtendedBy("Search Box", By.xpath("//input[@placeholder='Search...']"));
    private static final ExtendedBy msfTable = new ExtendedBy("MSF Investors table", By.xpath("//div/table[contains(@class, 'table b-table card-table table-striped table-hover')]"));
    private static final ExtendedBy msfInvestorsTableColumnHeaders = new ExtendedBy("Investors table columns headers", By.xpath("//table//th"));
    private static final ExtendedBy tableCell = new ExtendedBy("Table row", By.xpath("//tr//td[@role='cell']"));
    private static final String firstRowCellTemplateXpath = "(//td[@role='cell'])[%s]";
    private static final String eyeButtonString = "(//td[@role='cell'])[%s]//button//i";
    private static final ExtendedBy noRecordFoundMessage = new ExtendedBy("filter button", By.xpath("//div[contains(text(),' No records were found')]"));

    public CpMasterSecurityholderFile(Browser browser) {
        super(browser, msfTable);
    }

    public CpInvestorDetailsPage searchUniqueInvestorByTextBox(String NON_VALID_SEARCH, String searchText, boolean enterInvestor) {
        browser.typeTextElementCtrlA(searchBox, NON_VALID_SEARCH);
        browser.waitForElementVisibility(noRecordFoundMessage);
        browser.typeTextElementCtrlA(searchBox, searchText);
        browser.waitForElementVisibility(tableCell);
        String xPathString = String.format(firstRowCellTemplateXpath, getColumnIndex("Record Holder") + 1);
        ExtendedBy firstRowCell = new ExtendedBy("first row cell of " + searchText, By.xpath(xPathString));
        String xPathString2 = String.format(eyeButtonString, getColumnIndex("Number of Securities") + 2);
        ExtendedBy eyeButton = new ExtendedBy("Eye button", By.xpath(xPathString2));
        if (enterInvestor) {
            browser.click(eyeButton);
        }
      //  searchInvestorWaitAndClick(NON_VALID_SEARCH, searchBox, searchText, firstRowCell, eyeButton, enterInvestor);

        // I must return  CpInvestorDetailsPage - in case I do not need to enter IDP - return null
        if (!enterInvestor) {
            return null;
        }
        return new CpInvestorDetailsPage(browser);
    }

    public void searchInvestorWaitAndClick(String NON_VALID_SEARCH, ExtendedBy searchBox, String searchText, ExtendedBy firstRowCell, ExtendedBy clickElement, boolean enterInvestor) {
        browser.waitForElementVisibility(tableCell);
        browser.typeTextElementCtrlA(searchBox, NON_VALID_SEARCH);
        browser.waitForElementVisibility(noRecordFoundMessage);
        browser.typeTextElementCtrlA(searchBox, searchText);
        /*browser.waitForElementVisibility(tableRow);
        browser.typeTextElementCtrlA(searchBox, searchText);
        browser.waitForPageStable(Duration.ofSeconds(10));*/
        browser.waitForTextInElement(firstRowCell, searchText);
        if (enterInvestor) {
            browser.click(clickElement);
            waitInvestorDetailsPage(120, clickElement);
        }
    }

    public String getInvestorDetailByIndex(int index, String tableColumn) {
        List<WebElement> investorRows = browser.findElements(msfTable);
        int columnIndex = getColumnIndex(tableColumn);

        WebElement investorRowByIndex = investorRows.get(index);
        List<WebElement> investorDetailCells = investorRowByIndex.findElements(By.xpath("//td"));

        return browser.getElementText(investorDetailCells.get(columnIndex), "investor at index number" + index + " column " + tableColumn);
    }

    private int getColumnIndex(String name) {
        List<WebElement> headers = browser.findElements(msfInvestorsTableColumnHeaders);
        for (int i = 0; i < headers.size(); i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }
        throw new RuntimeException("Unable to find column with header name of " + name);
    }
}
