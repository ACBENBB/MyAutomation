package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public class CpOnBoarding extends AbstractPage {

    private static final ExtendedBy addInvestorButton = new ExtendedBy("add investor button", By.xpath("//button//span[contains(text(),'Add Investor')]"));
    private static final ExtendedBy importInvestorButton = new ExtendedBy("import investor button", By.xpath("//button//span[contains(text(),'Import investors')]"));
    private static final String showInvestorEyeButtonInTableByNameTemplate = "//table//td//a[contains(text(), '%s')]/ancestor::tr//i[contains(@class, '-eye')]";
    private static final ExtendedBy exportButton = new ExtendedBy("export button", By.xpath("//button[contains(text(),'Export List')]"));
    private static final ExtendedBy onboardingTable = new ExtendedBy("Investors table", By.xpath("//table[contains(@class, 'table b-table card-table table-striped table-hover')]//td"));
    private static final ExtendedBy onboardingTableColumnHeaders = new ExtendedBy("Investors table columns headers", By.xpath("//table//th"));
    private static final ExtendedBy onboardingInvestorRows = new ExtendedBy("Investors table investor rows", By.xpath("//table//tbody//tr"));
    private static final ExtendedBy searchBox = new ExtendedBy("Search for investor", By.xpath("//*[contains(@class, 'd-inline-block w-auto float-sm-right form-control form-control-sm')]"));
    private static final ExtendedBy uniqueEntryFoundInList = new ExtendedBy("Wait to find a unique value in list", By.xpath("//span[text()=' Showing 1 - 1 of 1 entries ']"));
    private static final ExtendedBy allEyeButtonsInTable = new ExtendedBy("", By.xpath("//table//td//../..//i[contains(@class, '-eye')]"));
    private static final ExtendedBy noRecordFoundMessage = new ExtendedBy("filter button", By.xpath("//div[contains(text(),' No records were found')]"));

    public CpOnBoarding(Browser browser) {
        super(browser, addInvestorButton);
    }

    public void clickExportList() {
        browser.click(exportButton);
    }

    public CpAddInvestor clickAddInvestor() {
        browser.click(addInvestorButton);
        return new CpAddInvestor(browser);
    }

    public CpImportInvestors clickImportInvestor() {
        browser.click(importInvestorButton);
        return new CpImportInvestors(browser);
    }

    public CpInvestorDetailsPage clickShowInvestorDetailsByName(String name) {
        String fullXpathLocator = String.format(showInvestorEyeButtonInTableByNameTemplate, name);
        ExtendedBy locator = new ExtendedBy("Eye button to show details of investor '" + name + "' in table", By.xpath(fullXpathLocator));
        browser.clickAndWaitForElementToVanish(locator);
        browser.waitForPageStable();
        return new CpInvestorDetailsPage(browser);
    }

    public CpInvestorDetailsPage clickOnFirstInvestor() {
        browser.waitForElementVisibility(allEyeButtonsInTable);
        List<WebElement> eyeButtons = browser.findElements(allEyeButtonsInTable);
        WebElement locator = eyeButtons.get(0);
        browser.click(locator, "Clicks on the first eye button");
        browser.waitForPageStable(Duration.ofSeconds(10));
        return new CpInvestorDetailsPage(browser);
    }

    public CpInvestorDetailsPage clickOnInvestorByIndex(int index) {
        List<WebElement> eyeButtons = browser.findElements(allEyeButtonsInTable);
        WebElement locator = eyeButtons.get(index);
        browser.click(locator, "Clicks on the eye button number " + index);
        browser.waitForPageStable(Duration.ofSeconds(2));
        browser.refreshPage();
        return new CpInvestorDetailsPage(browser);
    }

    public CpOnBoarding searchInvestorByTextBox(String investorName) {
        browser.waitForPageStable(Duration.ofSeconds(2));
        browser.click(searchBox).clear();
        browser.click(searchBox).sendKeys(investorName);
        browser.waitForElementTextNotEmpty(searchBox);
        browser.waitForPageStable(Duration.ofSeconds(4));
        return this;
    }

    public void searchUniqueInvestorByTextBox(String investorName, String nonValidSearch) {
        browser.typeTextElementCtrlA(searchBox, nonValidSearch);
        browser.waitForElementVisibility(noRecordFoundMessage);
        browser.typeTextElementCtrlA(searchBox, investorName);
        browser.waitForElementVisibility(uniqueEntryFoundInList);
    }

    public void searchUniqueInvestorWithTimeout(String searchTerm, int timeoutSeconds) {
        browser.typeTextElement(searchBox, searchTerm);
        browser.waitForElementVisibility(uniqueEntryFoundInList, timeoutSeconds);
    }

    public String getInvestorDetailByIndex(int index, String tableColumn) {
        for (int i = 0; i < 3; i++) {
            try {
                List<WebElement> investorRows = browser.findElements(onboardingTable);
                int columnIndex = getColumnIndex(tableColumn);

                WebElement investorRowByIndex = investorRows.get(index);
                List<WebElement> investorDetailCells = investorRowByIndex.findElements(By.xpath("//td"));

                return browser.getElementText(investorDetailCells.get(columnIndex), "investor at index number" + index + " column " + tableColumn);
            } catch (StaleElementReferenceException e) {
                // only in cases of stale element give another try - otherwise fail
                debug("Stale element exception occur. Will try again..." + e);
                browser.sleep(5000);
            }
        }

        errorAndStop("Too many stale element event occur.. Terminating...", false);
        return null;
    }

    private int getColumnIndex(String name) {
        List<WebElement> headers = browser.findElements(onboardingTableColumnHeaders);
        for (int i = 0; i < headers.size(); i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }

        throw new RuntimeException("Unable to find column with header name of " + name);
    }

    public CpOnBoarding waitForTableToBeEmpty() {
        info("Waiting for table to not contain any rows");
        Browser.waitForExpressionToEqual(q -> browser.findElementsQuick(onboardingTable, 1).isEmpty(),
                null,
                true,
                "wait for table to be empty",
                30,
                1000);
        return this;
    }

    public CpOnBoarding waitForTableToContainExactlyOneRowWithContent(String content) {
        Browser.waitForExpressionToEqual(q -> {
                    try {
                        var elements = browser.findElementsQuick(onboardingInvestorRows, 1);
                        return elements.size() == 1 && elements.get(0).getText().toLowerCase().contains(content.toLowerCase());
                    } catch (StaleElementReferenceException e) {
                        return false;
                    }
                },
                null,
                true,
                "Waiting for table to contain exactly one row with content of: " + content,
                30,
                1000);
        return this;
    }

    public CpOnBoarding waitForTableToBeNotEmpty() {
        info("Waiting for table to contains at least one row");
        browser.waitForElementVisibility(onboardingTable);
        return this;
    }

    public int countInvestorsByName(String investorName) {
        browser.sleep(5000);
        List<WebElement> investorRows = browser.findElements(onboardingInvestorRows);
        int rowTotalCount = investorRows.size();
        int nameTotalCount = 0;
        String columnName = "Name / Entity";

        for (int i = 1; i <= rowTotalCount; i++) {
            try {
                String actualInvestorName = getInvestorDetailByIndex(i, columnName);

                if (actualInvestorName.contains(investorName)) {
                    nameTotalCount++;

                }
            } catch (StaleElementReferenceException e) {
                // only in cases of stale element give another try - otherwise fail
                debug("Stale element exception occur. Will try again..." + e);
                browser.sleep(5000);
            }
        }
        return nameTotalCount;
    }

    public void waitForTableToCountRowsByCellDetail(int expectedNumberOfRows, int timeoutSeconds, String detailName) {
        info("Waiting for Onboarding table to contain at least " + expectedNumberOfRows + " rows");
        Function<String, Boolean> internalWaitForTableToCountRowsByCellDetail = t -> {
            try {
                if (countInvestorsByName(detailName) < expectedNumberOfRows) {
                    browser.refreshPage();
                    return false;
                }

                return true;
            } catch (StaleElementReferenceException e) {
                debug("A StaleElementReferenceException occur trying to waitForTableToCountRowsByCellDetail. Details: " + e);
                return false;
            } catch (Exception e) {
                warning("An error occur trying to waitForTableToCountRowsByCellDetail. Details: " + e, true);
                return false;
            }
        };
        String description = "TableToContainNumberOfRows=" + expectedNumberOfRows;
        Browser.waitForExpressionToEqual(internalWaitForTableToCountRowsByCellDetail, null, true, description, timeoutSeconds, 1000);
    }

    public CpInvestorDetailsPage findSpecificInvestor(String searchText, String nonValidSearch) {
        browser.typeTextElementCtrlA(searchBox, nonValidSearch);
        browser.waitForElementVisibility(noRecordFoundMessage);
        browser.typeTextElementCtrlA(searchBox, searchText);
        browser.waitForElementVisibility(uniqueEntryFoundInList);
        CpInvestorDetailsPage investorDetailsPage = clickShowInvestorDetailsByName(searchText);
        return investorDetailsPage;
    }

}
