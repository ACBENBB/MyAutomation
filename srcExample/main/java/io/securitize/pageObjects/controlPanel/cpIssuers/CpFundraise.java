package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.debug;
import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class CpFundraise extends AbstractPage {

    private static final ExtendedBy addInvestorButton = new ExtendedBy("add investor button", By.xpath("//button//span[contains(text(),'Add Investor')]"));
    private static final ExtendedBy importInvestorButton = new ExtendedBy("import investor button", By.xpath("//button//span[contains(text(),'Import investors')]"));
    private static final String showInvestorEyeButtonInTableByNameTemplate = "//table//td//a[contains(text(), '%s')]/ancestor::tr//i[contains(@class, '-eye')]";
    private static final ExtendedBy exportButton = new ExtendedBy("export button", By.xpath("//button[contains(text(),'Export List')]"));
    private static final ExtendedBy fundraiseTable = new ExtendedBy("Investors table", By.xpath("//table[contains(@class, 'table b-table card-table table-striped table-hover')]//td"));
    private static final ExtendedBy fundraiseTableColumnHeaders = new ExtendedBy("Investors table columns headers", By.xpath("//table//th"));
    private static final ExtendedBy fundraiseInvestorRows = new ExtendedBy("Investors table investor rows", By.xpath("//table//tbody//tr"));
    private static final ExtendedBy searchBox = new ExtendedBy("Search for investor", By.xpath("//*[contains(@class, 'd-inline-block w-auto float-sm-right form-control form-control-sm')]"));
    private static final ExtendedBy uniqueEntryFoundInList = new ExtendedBy("Wait to find a unique value in list", By.xpath("//span[text()=' Showing 1 - 1 of 1 entries ']"));
    private static final ExtendedBy allEyeButtonsInTable = new ExtendedBy("", By.xpath("//table//td//../..//i[contains(@class, '-eye')]"));
    private static final ExtendedBy selectAllCellsTickBox = new ExtendedBy("Tick Box that ticks all the tick boxes", By.xpath("//th[@aria-label='Checkbox']/div/label"));
    private static final ExtendedBy runIssueTokensButton = new ExtendedBy("Run Bulk Issuance button", By.xpath("//button/span[contains(text(),'Run')]"));
    private static final ExtendedBy confirmRunButton = new ExtendedBy("confirm run button", By.xpath("//button[text()='Run']"));
    private static final ExtendedBy okButton = new ExtendedBy("ok run button", By.xpath("//button[text()='OK']"));
    private static final ExtendedBy noRecordFoundMessage = new ExtendedBy("filter button", By.xpath("//div[contains(text(),' No records were found')]"));
    private static final ExtendedBy entryFoundInList = new ExtendedBy("Wait to find values in list", By.xpath("//span[text()=' Showing 1 - 1 of 1 entries ']"));

    public CpFundraise(Browser browser) {
        super(browser, addInvestorButton);
    }

    public void searchUniqueInvestorByTextBox(String investorName, String nonValidSearch) {
            browser.typeTextElementCtrlA(searchBox, nonValidSearch);
            browser.waitForElementVisibility(noRecordFoundMessage);
            browser.typeTextElementCtrlA(searchBox,investorName);
            browser.waitForElementVisibility(entryFoundInList);
    }

    public void searchInvestorByTextBox(String investorName) {
        browser.typeTextElementCtrlA(searchBox, investorName);
        browser.waitForElementTextNotEmpty(fundraiseTable);
    }

    public CpInvestorDetailsPage clickOnFirstInvestor() {
        List<WebElement> eyeButtons = browser.findElements(allEyeButtonsInTable);
        if (eyeButtons.isEmpty()) {
            browser.waitForPageStable(Duration.ofSeconds(10));
            eyeButtons = browser.findElements(allEyeButtonsInTable);
            if (eyeButtons.isEmpty()) {
                throw new RuntimeException("No eye buttons found on the page.");
            }
        }
        WebElement locator = eyeButtons.get(0);
        browser.click(locator, "Clicks on the first eye button");
        browser.waitForElementToVanish(locator, "eye button");
        return new CpInvestorDetailsPage(browser);
    }

    public void clickAllTickBoxes() {
        WebElement element = browser.findElement(selectAllCellsTickBox);
        browser.clickWithJs(element);
    }

    public void clickRunIssuanceButton() {
        browser.click(runIssueTokensButton);
    }

    public void clickConfirmRunButton() {
        browser.click(confirmRunButton);
    }

    public void clickOkButton() {
        browser.click(okButton);
    }

    public int countInvestorsByName(String investorName) {
        browser.waitForPageStable(Duration.ofSeconds(30));
        List<WebElement> investorRows = browser.findElements(fundraiseInvestorRows);
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
                browser.waitForPageStable(Duration.ofSeconds(30));
            }
        }
        return nameTotalCount;
    }

    public String getInvestorDetailByIndex(int index, String tableColumn) {
        for (int i = 0; i < 3; i++) {
            try {
                List<WebElement> investorRows = browser.findElements(fundraiseTable);
                int columnIndex = getColumnIndex(tableColumn);

                WebElement investorRowByIndex = investorRows.get(index);
                List<WebElement> investorDetailCells = investorRowByIndex.findElements(By.xpath("//td"));

                return browser.getElementText(investorDetailCells.get(columnIndex), "investor at index number" + index + " column " + tableColumn);
            } catch (StaleElementReferenceException e) {
                // only in cases of stale element give another try - otherwise fail
                debug("Stale element exception occur. Will try again..." + e);
                browser.waitForPageStable(Duration.ofSeconds(30));
            }
        }

        errorAndStop("Too many stale element event occur.. Terminating...", false);
        return null;
    }

    private int getColumnIndex(String name) {
        List<WebElement> headers = browser.findElements(fundraiseTableColumnHeaders);
        for (int i = 0; i < headers.size(); i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }

        throw new RuntimeException("Unable to find column with header name of " + name);
    }
}
