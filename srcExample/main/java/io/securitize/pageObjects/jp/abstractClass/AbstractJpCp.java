package io.securitize.pageObjects.jp.abstractClass;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.controlPanel.JpCpInvestorsDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractJpCp extends AbstractJpPage {

    protected static final ExtendedBy exportButton = new ExtendedBy("export button", By.xpath("//button[contains(text(),'リストをエクスポート')]"));
    protected static final ExtendedBy eyeIcons = new ExtendedBy("Eye Icons", By.xpath("//*[contains(@class,'eye')]"));
    protected static final ExtendedBy dataLoadingMessage = new ExtendedBy("Reading Data Message", By.xpath("//*[text()[contains(.,'データ読み込み中。しばらくお待ち下さい。')]]"));
    protected static final ExtendedBy recordNotFoundMessage = new ExtendedBy("Record Not Found Message", By.xpath("//*[text()[contains(.,'選択条件に合うレコードは見つかりませんでした。')]]"));
    protected static final ExtendedBy searchInputBox = new ExtendedBy("Search Input Box", By.xpath("//*[@placeholder='検索...']"));

    protected static final ExtendedBy onboardingTableHeaders = new ExtendedBy("Onboarding table headers", By.xpath("//table/thead/tr/th"));
    protected static final String XPATH_TEMPLATE_TABLE_COLUMN_DATA_BY_HEADER_NAME = "//table/tbody/tr/td[count(//table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    protected static final String XPATH_TEMPLATE_CHECKBOX_BY_INVESTOR_NAME = "//table//td//a[contains(text(), '%s')]/ancestor::tr//*[contains(@type, 'checkbox')]";
    protected static final String XPATH_TEMPLATE_EYE_ICON_BY_INVESTOR_NAME = "//table//td//a[contains(text(), '%s')]/ancestor::tr//i[contains(@class, '-eye')]";
    protected static final String XPATH_TEMPLATE_TRASH_ICON_BY_INVESTOR_NAME = "//table//td//a[contains(text(), '%s')]/ancestor::tr//i[contains(@class, '-trash')]";
    protected static final String XPATH_TEMPLATE_TABLE_HEADER = "//tr/th[text()[contains(.,'%s')]]";
    protected static final String XPATH_TEMPLATE_TABLE_DATA_BY_INVESTOR_AND_COLUMN = "//tr[td/a[ text() = ' %s ']]/td[%d]";

    protected static final ExtendedBy oKButtonOnDeleteDialog = new ExtendedBy("OK Button On Delete Dialog", By.xpath("//button[text()[contains(., 'OK')]]"));
    protected static final ExtendedBy cancelButtonOnDeleteDialog = new ExtendedBy("Cancel Button On Delete Dialog", By.xpath("//button[text()[contains(., 'キャンセル')]]"));

    protected AbstractJpCp(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
        waitForReadingDataMessageToVanish();
    }

    public AbstractJpCp waitForReadingDataMessageToVanish(long waitTimeoutInSeconds) {
        try {
            browser.setImplicitWaitTimeout(waitTimeoutInSeconds);
            browser.waitForElementToVanish(dataLoadingMessage);
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            info("reading-data message element not found");
        }
        browser.resetImplicitWaitTimeout();
        return this;
    }
    public AbstractJpCp waitForReadingDataMessageToVanish() {
        return waitForReadingDataMessageToVanish(15);
    }

    public AbstractJpCp waitUntilNumberOfTableRowsBecome(int number) {
        browser.waitForPageStable(Duration.ofSeconds(3));
        browser.waitForElementCount(eyeIcons, number);
        return this;
    }

    public AbstractJpCp enterSearchText(String searchText, int expectedNumberOfInvestors) {
        browser.typeTextElement(searchInputBox, searchText);
        browser.waitForPageStable(Duration.ofSeconds(3));
        if (expectedNumberOfInvestors != 0) {
            try {
                waitUntilNumberOfTableRowsBecome(expectedNumberOfInvestors);
            } catch (org.openqa.selenium.TimeoutException timeoutException) {
                if (browser.isElementVisible(recordNotFoundMessage)) {
                    return null;
                }
            }
        }
        return this;
    }

    public AbstractJpCp enterUniqueSearchText(String searchText) {
        return enterSearchText(searchText, 1);
    }

    public String getInvestorDataByTableHeaderAndRowNumber(String tableHeader, int row) {
        String columnLocator = String.format(XPATH_TEMPLATE_TABLE_COLUMN_DATA_BY_HEADER_NAME, tableHeader);
        ExtendedBy investorsColumn = new ExtendedBy("Investors column", By.xpath(columnLocator));
        WebElement investorCell = browser.findElements(investorsColumn).get(row);
        return RetryOnExceptions.retryOnStaleElement(
                ()-> browser.getElementText(investorCell, "Investor data about header: " + tableHeader + ", row: " + row),
                true);
    }

    public String getTableDataAbout(String tableHeader, String investorName) {
        int column = getColumnIndexForLocator(tableHeader);
        String tableCellLocator = String.format(XPATH_TEMPLATE_TABLE_DATA_BY_INVESTOR_AND_COLUMN, investorName, column);
        String description = tableHeader.replace("\n", " ") + " of " + investorName; // to avoid new line in print
        ExtendedBy extendedBy = new ExtendedBy(description, By.xpath(tableCellLocator));
        return RetryOnExceptions.retryOnStaleElement(()-> browser.getElementText(extendedBy), true);
    }

    public AbstractJpCp clickCheckboxOf(String investorName) {
        String checkboxLocator = String.format(XPATH_TEMPLATE_CHECKBOX_BY_INVESTOR_NAME, investorName);
        ExtendedBy checkbox = new ExtendedBy("checkbox on " + investorName, By.xpath(checkboxLocator));
        browser.clickWithJs(browser.findElement(checkbox));
        return this;
    }

    public boolean isSelectedCheckboxOf(String investorName) {
        String checkboxLocator = String.format(XPATH_TEMPLATE_CHECKBOX_BY_INVESTOR_NAME, investorName);
        ExtendedBy checkbox = new ExtendedBy("checkbox on " + investorName, By.xpath(checkboxLocator));
        return browser.findElement(checkbox).isSelected();
    }

    public JpCpInvestorsDetails clickEyeIconOf(String investorName) {
        String eyeIconLocator = String.format(XPATH_TEMPLATE_EYE_ICON_BY_INVESTOR_NAME, investorName);
        ExtendedBy eyeIcon = new ExtendedBy("eye icon on " + investorName, By.xpath(eyeIconLocator));
        browser.clickWithJs(browser.findElement(eyeIcon));
        browser.waitForPageStable();
        return new JpCpInvestorsDetails(browser);
    }

    public AbstractJpCp clickTrashIconOf(String investorName) {
        String trashIconLocator = String.format(XPATH_TEMPLATE_TRASH_ICON_BY_INVESTOR_NAME, investorName);
        ExtendedBy trashIcon = new ExtendedBy("trash icon on " + investorName, By.xpath(trashIconLocator));
        browser.clickWithJs(browser.findElement(trashIcon));
        return this;
    }

    public AbstractJpCp clickOkButtonOnDeleteDialog() {
        browser.clickAndWaitForElementToVanish(oKButtonOnDeleteDialog);
        return this;
    }

    public AbstractJpCp clickCancelButtonOnDeleteDialog() {
        browser.clickAndWaitForElementToVanish(cancelButtonOnDeleteDialog);
        return this;
    }

    private int getColumnIndexForLocator(String tableHeader) {
        List<WebElement> headers = browser.findElements(onboardingTableHeaders);
        int columnIndex;
        WebElement header;
        for (int i = 0; i < headers.size() ; i++) {
            columnIndex = i + 1;
            header = headers.get(i);
            String currentTableHeader = browser.getElementText(header, "Header column index " + columnIndex);
            if (tableHeader.equalsIgnoreCase(currentTableHeader)) {
                return columnIndex;
            }
        }
        throw new RuntimeException("Unable to find column with header name of " + tableHeader);
    }

    public AbstractJpCp clickExportButton() {
        long waitTimeoutInSeconds = 15;
        browser.waitForElementClickable(exportButton);
        browser.clickWithJs(browser.findElement(exportButton));
        browser.waitForPageStable(Duration.ofSeconds(3));
        waitForReadingDataMessageToVanish(waitTimeoutInSeconds);
        return this;
    }

    public AbstractJpCp clickTableHeader(String headerName) {
        long waitTimeoutInSeconds = 15;
        String tableHeaderLocator = String.format(XPATH_TEMPLATE_TABLE_HEADER, headerName);
        ExtendedBy tableHeader = new ExtendedBy("Table Header " + headerName, By.xpath(tableHeaderLocator));
        browser.click(tableHeader);
        browser.waitForPageStable(Duration.ofSeconds(3));
        waitForReadingDataMessageToVanish(waitTimeoutInSeconds);
        return this;
    }
}
