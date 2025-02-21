package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public class CpInvestorsList extends AbstractPage {

    private static final ExtendedBy investorsTable = new ExtendedBy("Investors table", By.xpath("//div[contains(@class, 'sec-id-investors-table')]"));
    private static final ExtendedBy investorsTableColumnHeaders = new ExtendedBy("Investors table columns headers", By.xpath("//table//th"));
    private static final String findInvestorTableRowByNameContentTemplate = "//table//td[contains(text(), '%s')]/..";
    private static final ExtendedBy investorsTableRowsEmptyAlert = new ExtendedBy("Investors table rows empty alert", By.xpath("//table//td/div[@role='alert']"));
    private static final ExtendedBy searchBox = new ExtendedBy("", By.xpath("//input[@placeholder='Search...']"));
    private static final ExtendedBy investorsResultsCount = new ExtendedBy("CP > SiD > Investors lists search results count", By.xpath("//span[contains(text(), 'Showing 1 - 1 of 1 entries')] | //span[contains(text(), ' Showing 1 - 2 of 2 entries ')] "));
    private static final ExtendedBy viewInvestorDetailsBtn = new ExtendedBy("Investor Details Btn", By.xpath("//i[@class='ion ion-ios-eye']"));
    private static final ExtendedBy issuersFilter = new ExtendedBy("Issuers filter", By.xpath("//div[contains(@class, 'multiselect__tags')]//*[contains(text(), 'Issuers')]"));
    private static final ExtendedBy countryFilter = new ExtendedBy("Country filter", By.xpath("//div[contains(@class, 'multiselect__tags')]//*[contains(text(), 'Country')]"));
    private static final ExtendedBy stateFilter = new ExtendedBy("State filter", By.xpath("//div[contains(@class, 'multiselect__tags')]//*[contains(text(), 'State')]"));
    private static final ExtendedBy investorTypeFilter = new ExtendedBy("Investor type filter", By.xpath("//div[contains(@class, 'multiselect__tags')]//*[contains(text(), 'Investor type')]"));
    private static final ExtendedBy verificationStatusFilter = new ExtendedBy("Verification status filter", By.xpath("//div[contains(@class, 'multiselect__tags')]//*[contains(text(), 'Verification status')]"));
    private static final ExtendedBy verificationErrorFilter = new ExtendedBy("Verification error filter", By.xpath("//div[contains(@class, 'multiselect__tags')]//*[contains(text(), 'Verification error')]"));
    private static final ExtendedBy assigneeFilter = new ExtendedBy("Assignee filter", By.xpath("//div[contains(@class, 'multiselect__tags')]//*[contains(text(), 'Assignee')]"));
    private static final ExtendedBy startDateFilter = new ExtendedBy("Start date filter", By.xpath("//input[@name='fromDate']"));
    private static final ExtendedBy endDateFilter = new ExtendedBy("End date filter", By.xpath("//input[@name='toDate']"));
    private static final ExtendedBy saveFilter = new ExtendedBy("Save filter", By.xpath("//button[text()='Save']"));
    private static final ExtendedBy clearFilter = new ExtendedBy("Clear filter", By.xpath("//button[text()='Clear']"));
    private static final ExtendedBy clearCreationDatesBtn = new ExtendedBy("Clear creation dates button", By.xpath("//span[text()=' Clear ']"));

    private static final String filterValueTemplate = "//span[contains(text(), '%s')]";


    public CpInvestorsList(Browser browser) {
        super(browser, investorsTable);
    }

    public CpInvestorsList searchInvestorByEmail(String investorName) {
        browser.typeTextElement(searchBox, investorName);
        return this;
    }

    public void waitForInvestorsTableToBeEmpty() {
        info("Waiting for table to not contain any rows by empty alert");
        browser.waitForElementVisibility(investorsTableRowsEmptyAlert);
        info("Found alert for empty table, great!");
    }

    public CpInvestorDetailsPage clickViewInvestorDetailsBtn() {
        browser.waitForElementVisibility(investorsResultsCount);
        browser.click(viewInvestorDetailsBtn, false);
        return new CpInvestorDetailsPage(browser);
    }

    public String getInvestorDetailByName(String investorName, String tableColumn) {
        int columnIndex = getColumnIndex(tableColumn);
        int maxAttempts = 2;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                WebElement investorRow = getInvestorRowByNameContent(investorName);
                List<WebElement> investorDetailCells = investorRow.findElements(By.tagName("td"));
                return browser.getElementText(investorDetailCells.get(columnIndex), "investor " + investorName + " column " + tableColumn);
            } catch (StaleElementReferenceException e) {
                if (i < maxAttempts) {
                    debug("A stale element exception has occur.. Trying again in 5 seconds...");
                    browser.sleep(5000);
                } else {
                    throw e;
                }
            }
        }

        errorAndStop("Couldn't get investor details by name. Check the log for possible debug messages", true);
        return null;
    }

    private int getColumnIndex(String name) {
        List<WebElement> headers = browser.findElements(investorsTableColumnHeaders);
        for (int i = 0; i < headers.size(); i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }

        throw new RuntimeException("Unable to find column with header name of " + name);
    }

    private WebElement getInvestorRowByNameContent(String nameContent) {
        String fullXpathLocator = String.format(findInvestorTableRowByNameContentTemplate, nameContent);
        ExtendedBy locator = new ExtendedBy("Investor '" + nameContent + "' row in table", By.xpath(fullXpathLocator));
        return browser.findElement(locator);
    }

    public void filterIssuer(String issuer) {
        filterValue(issuersFilter, issuer);
    }

    public void filterCountry(String country) {
        filterValue(countryFilter, country);
    }

    private void filterState(String state) {
        filterValue(stateFilter, state);
    }

    public void filterInvestorType(String investorType) {
        filterValue(investorTypeFilter, investorType);
    }

    public void filterVerificationStatus(String verificationStatus) {
        filterValue(verificationStatusFilter, verificationStatus);

    }

    public void filterVerificationError(String verificationError) {
        filterValue(verificationErrorFilter, verificationError);
    }

    public void filterAssignee(String assignee) {
        filterValue(assigneeFilter, assignee);
    }

    public void filterStartDate(String startDate) {
        browser.sendKeysElement(startDateFilter, "Start date to filter by", startDate);
    }

    public void filterEndDate(String endDate) {
        browser.sendKeysElement(endDateFilter, "End date to filter by", endDate);
    }

    private void filterValue(ExtendedBy comboBoxLocator, String value) {
        browser.click(comboBoxLocator);
        String fullXpathLocator = String.format(filterValueTemplate, value);
        ExtendedBy locator = new ExtendedBy(value + " filter", By.xpath(fullXpathLocator));
        browser.click(locator);
        Optional<WebElement> element = browser.findFirstVisibleElementQuick(saveFilter, 15);
        if (element.isPresent()) {
            browser.click(element.get(), saveFilter.getDescription());
        } else {
            MultiReporter.errorAndStop("Couldn't find Save button.", true);
        }
    }

    public void clearFilterIssuer() {
        clearFilter(issuersFilter);
    }

    public void clearFilterCountry() {
        clearFilter(countryFilter);
    }

    public void clearFilterInvestorType() {
        clearFilter(investorTypeFilter);
    }

    public void clearFilterVerificationStatus() {
        clearFilter(verificationStatusFilter);
    }

    public void clearFilterVerificationError() {
        clearFilter(verificationErrorFilter);
    }

    public void clearFilterAssignee() {
        clearFilter(assigneeFilter);
    }

    public void clearCreationDates() {
        browser.click(clearCreationDatesBtn);
    }

    public void clearAllFilters() {
        clearFilterIssuer();
        clearFilterCountry();
        clearFilterInvestorType();
        clearFilterVerificationStatus();
        clearFilterVerificationError();
        clearFilterAssignee();
        clearCreationDates();
    }

    private void clearFilter(ExtendedBy comboBoxLocator) {
        browser.click(comboBoxLocator);
        Optional<WebElement> element = browser.findFirstVisibleElementQuick(clearFilter, 15);
        if (element.isPresent()) {
            browser.click(element.get(), clearFilter.getDescription());
        } else {
            MultiReporter.errorAndStop("Couldn't find Clear button.", true);
        }

    }

    public void applyAllFilters(String issuer, String country, String state, String investorType, String verificationStatus, String verificationError, String assignee, String startDate, String endDate) {
        filterIssuer(issuer);
        filterCountry(country);
        if(country.equalsIgnoreCase("United States of America"))
            filterState(state);
        filterInvestorType(investorType);
        filterVerificationStatus(verificationStatus);
        // verification errors can't be set via an API call on production
        if(!verificationError.equalsIgnoreCase("verification_errors_can_not_be_set")){
            filterVerificationError(verificationError);
        }
        filterAssignee(assignee);
        filterStartDate(startDate);
        filterEndDate(endDate);
    }

    public void waitForTableToContainNumberOfRows(int expectedNumberOfRows) {
        waitForTableToContainNumberOfRows(expectedNumberOfRows, 30);
    }

    public void waitForTableToContainNumberOfRows(int expectedNumberOfRows, int timeoutSeconds) {
        info("Waiting for investors table to contain " + expectedNumberOfRows + " rows");
        Function<String, Boolean> internalWaitForTableToContainNumberOfRows = t -> {
            try {
                WebElement table = browser.findElement(investorsTable);
                List<WebElement> rows = table.findElements(By.tagName("tr"));
                return rows.size() - 1 == expectedNumberOfRows; //Subtracting 1 from rows because of the column names also having a tr
            } catch (StaleElementReferenceException e) {
                debug("A StaleElementReferenceException occur trying to waitForTableToContainNumberOfRows. Details: " + e.toString());
                return false;
            } catch (Exception e) {
                warning("An error occur trying to waitForTableToContainNumberOfRows. Details: " + e.toString(), true);
                return false;
            }
        };
        String description = "TableToContainNumberOfRows=" + expectedNumberOfRows;
        Browser.waitForExpressionToEqual(internalWaitForTableToContainNumberOfRows, null, true, description, timeoutSeconds, 1000);
    }

}