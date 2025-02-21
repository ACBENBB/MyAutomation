package io.securitize.pageObjects.jp.abstractClass;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public abstract class AbstractJpDashboardDocuments extends AbstractJpPage {

    protected static final ExtendedBy yourDocumentsTitleLabel = new ExtendedBy("Your documents box title", By.xpath("//h1[text()='ドキュメント']"));
    protected static final ExtendedBy opportunitySelect = new ExtendedBy("Opportunity Select Box ", By.xpath("//*[@class='form-control']"));
    protected static final ExtendedBy opportunityDocumentsTab = new ExtendedBy("Opportunity Documents Tab", By.xpath("//a[@data-tab='opportunity-documents']"));
    protected static final ExtendedBy documentTableHeaders = new ExtendedBy("Document table headers", By.xpath("//table/thead/tr/th"));
    protected static final ExtendedBy noItemsText = new ExtendedBy("No items text", By.xpath("//h2[contains(text(), 'No items')]"));
    protected static final String TEMPLATE_DOCUMENT_DATA_BY_ROW_COLUMN = "//tbody/tr[%d]/td[%d]";
    protected static final String TEMPLATE_DOCUMENT_DOWNLOAD_BY_ROW = "//tbody/tr[%d]/td[%d]/a";

    protected AbstractJpDashboardDocuments(Browser browser) {
        super(browser, yourDocumentsTitleLabel);
    }

    public void selectOpportunity(String value) {
        int timeoutSeconds = 5;
        if (browser.isElementVisibleQuick(noItemsText, timeoutSeconds)) {
            browser.waitForElementToVanish(noItemsText);
        }
        browser.waitForElementVisibility(opportunitySelect);
        browser.selectElementByVisibleText(opportunitySelect, value);
        browser.waitForPageStable();
    }

    public void selectOpportunity(String value, boolean retry) {
        retryFunctionWithRefreshPage(()->{selectOpportunity(value); return null;}, retry);
    }

    // Marui: 社債関連書類, Sony Bank: 商品関連情報
    public void clickOpportunityDocumentsTab() {
        browser.click(opportunityDocumentsTab, false);
    }

    public String getDocumentByTableHeaderAndRowNumber(String tableHeader, int row) {
        browser.waitForElementVisibility(yourDocumentsTitleLabel);
        int timeoutSeconds = 5;
        if (browser.isElementVisibleQuick(noItemsText, timeoutSeconds)) {
            browser.waitForElementToVanish(noItemsText);
        }
        boolean retryOnStaleElementException = true;
        int column = getColumnIndex(tableHeader, retryOnStaleElementException);
        String description = "tableHeader " + tableHeader + " column:" + column + ", row:" + row;
        ExtendedBy element = new ExtendedBy(description, By.xpath(String.format(TEMPLATE_DOCUMENT_DATA_BY_ROW_COLUMN, row, column)));
        return browser.getElementText(element);
    }

    public String getDocumentByTableHeaderAndRowNumber(String tableHeader, int row, boolean retry) {
        return retryFunctionWithRefreshPage(()-> getDocumentByTableHeaderAndRowNumber(tableHeader, row), retry);
    }

    public void clickDownloadByRowNumber(int row) {
        browser.waitForElementVisibility(yourDocumentsTitleLabel);
        boolean retryOnStaleElementException = true;
        int currentNumberOfOpenTabs = Browser.getDriver().getWindowHandles().size();
        int expectedNumberOfOpenTabs = currentNumberOfOpenTabs + 1;
        int column = getColumnIndex("", retryOnStaleElementException); // no table header on "Download"
        ExtendedBy element = new ExtendedBy("Download button", By.xpath(String.format(TEMPLATE_DOCUMENT_DOWNLOAD_BY_ROW, row, column)));
        browser.click(element);
        browser.waitForNumbersOfTabsToBe(expectedNumberOfOpenTabs);
    }

    public void clickDownloadByRowNumber(int row, boolean retry) {
        retryFunctionWithRefreshPage(()->{clickDownloadByRowNumber(row); return null;}, retry);
    }

    private int getColumnIndex(String tableHeader) {
        browser.waitForElementVisibility(yourDocumentsTitleLabel);
        List<WebElement> headers = browser.findElements(documentTableHeaders);
        int columnIndex;
        for (int i = 0; i < headers.size() ; i++) {
            columnIndex = i + 1;
            String currentTableHeader = browser.getElementText(headers.get(i), "Header column index " + columnIndex);
            // all headers except download
            if (tableHeader.equalsIgnoreCase(currentTableHeader)) {
                return columnIndex;
            }
            // download (no table header)
            if (tableHeader.equalsIgnoreCase("") && (currentTableHeader == null)) {
                return columnIndex;
            }
        }
        throw new RuntimeException("Unable to find column with header name of " + tableHeader);
    }

    private int getColumnIndex(String tableHeader, boolean retry) {
        return RetryOnExceptions.retryOnStaleElement(()-> getColumnIndex(tableHeader), retry);
    }
}
