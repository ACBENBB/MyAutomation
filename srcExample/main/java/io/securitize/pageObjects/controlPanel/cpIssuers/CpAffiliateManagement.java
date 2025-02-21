package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.*;

public class CpAffiliateManagement extends AbstractPage<CpAffiliateManagement> {

    private static final ExtendedBy affiliateManagementPageHeader = new ExtendedBy("Affiliate Management Header", By.xpath("//div//h4[contains(text(), 'Affiliate Management')]"));
    private static final ExtendedBy affiliateTableColumnHeaders = new ExtendedBy("Affiliate table columns headers", By.xpath("//div[@class='MuiDataGrid-columnHeaderTitle css-cc8tf1']"));
    private static final ExtendedBy affiliateRows = new ExtendedBy("Affiliate rows", By.xpath("//div[@role='row']"));
    private static final ExtendedBy lastChangeSortHeader = new ExtendedBy("Sort by Last Change header", By.xpath("//div[contains(text(), 'Last change')]/parent::div/following-sibling::div/button"));
    private static final ExtendedBy deactivateButton = new ExtendedBy("deactivate affiliate button", By.xpath("//button[contains(text(),'Deactivate')]"));
    private static final ExtendedBy deactivateFieldText = new ExtendedBy("deactivate affiliate field text", By.xpath("//div/input[@name='comment']"));
    private static final ExtendedBy submitButton = new ExtendedBy("submit button", By.xpath("//button[@type='submit']"));

    public CpAffiliateManagement(Browser browser) {
        super(browser, affiliateManagementPageHeader);
    }

    public String getAffiliateDetailByIndex(int index, String tableColumn) {
        for (int i = 0; i < 3; i++) {
            try {
                List<WebElement> investorRows = browser.findElements(affiliateRows);
                int columnIndex = getColumnIndex(tableColumn);

                WebElement investorRowByIndex = investorRows.get(index);
                List<WebElement> investorDetailCells = investorRowByIndex.findElements(By.xpath("//div[@role='cell']"));

                return browser.getElementText(investorDetailCells.get(columnIndex), "round at index number" + index + " column " + tableColumn);
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
        List<WebElement> headers = browser.findElements(affiliateTableColumnHeaders);
        for (int i = 0; i < headers.size() ; i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }

        throw new RuntimeException("Unable to find column with header name of " + name);
    }

    public void sortByLastChange() {
        browser.waitForPageStable();
        //browser.click(lastChangeSortHeader, false);
        WebElement element = browser.findElement(lastChangeSortHeader);
        Actions actions = new Actions(Browser.getDriver());
        actions.moveToElement(element).perform();
        browser.click(element, "", ExecuteBy.JAVASCRIPT, false);
        browser.waitForPageStable();
        browser.click(element, "", ExecuteBy.JAVASCRIPT, false);
    }

    public void clickDeactivateAffiliate() {
        browser.click(deactivateButton, false);
    }

    public void setDeactivateComment(String value) {
        browser.waitForPageStable();
        browser.waitForElementVisibility(deactivateFieldText);
        browser.typeTextElement(deactivateFieldText, value);
    }

    public void clickSubmitButton() {
        browser.clickAndWaitForElementToVanish(submitButton);
    }


    public void waitForTableToBeNotEmpty() {
        info("Waiting for 'Affiliate management' table to contains at least one row");
        Browser.waitForExpressionToEqual(q -> browser.findElementsQuick(affiliateRows, 1).size() > 1,
                null,
                true,
                "wait for 'Affiliate management' table to be not empty",
                60,
                3000);
    }
}
