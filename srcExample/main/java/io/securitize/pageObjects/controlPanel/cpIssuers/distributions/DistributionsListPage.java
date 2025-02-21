package io.securitize.pageObjects.controlPanel.cpIssuers.distributions;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;

public class DistributionsListPage extends AbstractPage {

    private static final ExtendedBy generateDistributionBtn = new ExtendedBy("Generate distribution button", By.xpath("//button[@id='ta-distributions-button-open']"));
    private static final ExtendedBy distributionsRows = new ExtendedBy("All Distributions Grid Rows", By.xpath("//div[@data-id]"));
    private static final ExtendedBy distributionColumns = new ExtendedBy("Distributions Grid Columns", By.xpath("//div[@class='MuiDataGrid-cellContent']"));
    private static final ExtendedBy rowsPerPageInput = new ExtendedBy("Rows per page value", By.xpath("//div[@aria-haspopup='listbox']/following-sibling::input"));
    private static final ExtendedBy rowsPerPageClick = new ExtendedBy("Rows per page click", By.xpath("//*[@data-testid='ArrowDropDownIcon']"));
    private static final ExtendedBy rowsPerPageDropdown = new ExtendedBy("Rows per Dropdown", By.xpath("//ul[@role='listbox']/li"));
    private static final ExtendedBy nextPageButton = new ExtendedBy("Next Page button", By.xpath("//button[@title='Go to next page']"));
    private static final String investorRow = "//*[contains(@class, 'MuiDataGrid-cellContent') and contains(text(), '%s')]";
    private static final ExtendedBy distributionStatus = new ExtendedBy("Distribution Status Chip Text Span", By.xpath("//div[@id='ta-chip-distribution-details-status']//span"));
    private static final ExtendedBy refreshPageBtn = new ExtendedBy("Distribution List Refresh Page Btn", By.xpath("//button[text()='Refresh']"));
    private static final ExtendedBy paymentMethodChipList = new ExtendedBy("Distribution List Payment Method Chip", By.xpath("//*[text()='Reinvest']"));
    private static final ExtendedBy tokensToBeIssued = new ExtendedBy("Tokens To Be Issued", By.xpath("//span[contains(text(), 'Tokens to be issued:')]"));
    private static final ExtendedBy loadingCSVRefresh = new ExtendedBy("refresh btn", By.xpath("//button[text()='Refresh']"));

    public DistributionsListPage(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    public GenerateDistributionModalPage clickGenerateDistributionBtn() {
        browser.click(generateDistributionBtn);
        return new GenerateDistributionModalPage(browser);
    }

    public DistributionDetailsPage clickDistributionByRow(String rowIndex) {
        int row = Integer.parseInt(rowIndex);
        List<WebElement> distributionsRow = browser.findElements(distributionsRows);
        if(!distributionsRow.isEmpty()) {
            browser.waitForPageStable();
            distributionsRow.get(row).click();
            browser.waitForPageStable();
            return new DistributionDetailsPage(browser);
        }
        try {
            throw new RuntimeException("DISTRIBUTION LIST - > There is no distribution created.");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        browser.waitForPageStable();
        return null;
    }

    public int getDistributionsRowCount() {
        return browser.findElements(distributionsRows).size();
    }

    public String getDistributionColumnDataByRow(String columnHeader, String row) {
        browser.waitForPageStable();
        final String distributionRow = "(//div[@data-id]["+row+"]";
        final String distributionRowContent = "//div[@class='MuiDataGrid-cellContent'])["+setColumnHeaderXpath(columnHeader)+"]";
        if (columnHeader.equalsIgnoreCase("status")) {
            String statusChipXpath = "//*[contains(@class, 'MuiChip-label')])";
            WebElement rowDataColumn = browser.waitForElementVisibility(new ExtendedBy("distribution row + statusChipXpath", By.xpath(distributionRow+statusChipXpath)));
            return rowDataColumn.getText();
        } else {
            WebElement rowDataColumn = browser.waitForElementVisibility(new ExtendedBy("distribution row + distributionRowContent", By.xpath(distributionRow+distributionRowContent)));
            return rowDataColumn.getText();
        }
    }

    public String setColumnHeaderXpath(String columnHeader) {
        String columnHeaderIndex = null;
        if(columnHeader.equalsIgnoreCase("type")) {
            columnHeaderIndex = "1";
        } else if(columnHeader.equalsIgnoreCase("distributionName")) {
            columnHeaderIndex = "2";
        } else if(columnHeader.equalsIgnoreCase("date")) {
            columnHeaderIndex = "3";
        } else if(columnHeader.equalsIgnoreCase("total amount")) {
            columnHeaderIndex = "4";
        } else if(columnHeader.equalsIgnoreCase("status")) {
            columnHeaderIndex = "5";
        }
        return columnHeaderIndex;
    }

    public int getRowsPerPageValue() {
        return Integer.parseInt(browser.findElement(rowsPerPageInput).getAttribute("value"));
    }

    public void waitForDistributionListToLoad() {
        browser.waitForElementVisibility(distributionsRows);
    }

    public boolean isInvestorDisplayed(String email) {
        boolean result = false;
        try {
            browser.findElement(new ExtendedBy("Investor is present", By.xpath(String.format(investorRow, email))));
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                browser.findElement(nextPageButton).click();
                browser.findElement(new ExtendedBy("Investor is present", By.xpath(String.format(investorRow, email))));
                result = true;
            } catch (Exception e1) {
                e1.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    public boolean isReinvestPayoutMethodUpdatedInDistributionListUi() {
        return browser.findElements(paymentMethodChipList).size() > 0;
    }

    public ExtendedBy getDistributionsRows() {
        return distributionsRows;
    }

    public String getDistributionStatus() {
        return browser.getElementText(distributionStatus);
    }

    public boolean isloadingCSVRefreshDisplayed() {
//        browser.waitForPageStable();
        return browser.isElementVisible(loadingCSVRefresh);
    }

    public double getTokensToBeIssued() {
        return parseTotalNumberOfTokensToBeIssued(browser.findElement(tokensToBeIssued).getText());
    }

    public static double parseTotalNumberOfTokensToBeIssued(String input) {
        String[] parts = input.split(":");
        if (parts.length >= 2) {
            String numberString = parts[1].trim();
            try {
                return Double.parseDouble(numberString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format: " + numberString);
            }
        } else {
            throw new IllegalArgumentException("Invalid input format");
        }
    }

}