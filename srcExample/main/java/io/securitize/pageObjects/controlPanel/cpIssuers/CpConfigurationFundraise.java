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

public class CpConfigurationFundraise extends AbstractPage {

    private static final ExtendedBy configurationFundraisePageHeader = new ExtendedBy("Configration Fundraise Header", By.xpath("//div//h4[contains(text(), 'Configuration')]"));
    private static final ExtendedBy addRoundButton = new ExtendedBy("add round button", By.xpath("//button//span[contains(text(),'Add Round')]"));
    private static final ExtendedBy editRoundButton = new ExtendedBy("edit round button", By.xpath("//tr[@aria-rowindex='2']//button[@data-original-title='Edit']/i"));
    private static final ExtendedBy deleteRoundButton = new ExtendedBy("delete round button", By.xpath("//tr[@aria-rowindex='2']//button[@data-original-title='Delete']/i"));
    private static final ExtendedBy allDeleteRoundsButtons = new ExtendedBy("all delete rounds button", By.xpath("//table//tbody//tr//button[@data-original-title='Delete']/i"));
    private static final ExtendedBy roundsTable = new ExtendedBy("Investors table", By.xpath("//table[contains(@class, 'table b-table card-table table-striped table-hover')]//td"));
    private static final ExtendedBy roundsTableColumnHeaders = new ExtendedBy("Rounds table columns headers", By.xpath("//table//th"));
    private static final ExtendedBy roundsRows = new ExtendedBy("Rounds rows", By.xpath("//table//tbody//tr"));
    private static final ExtendedBy statusColumn = new ExtendedBy("status column", By.xpath("//th[contains(text(),'Status')]"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));

    public CpConfigurationFundraise(Browser browser) {
        super(browser, configurationFundraisePageHeader);
    }

    public CpAddRound clickAddRound() {
        browser.waitForPageStable();
        browser.click(addRoundButton, false);
        return new CpAddRound(browser);
    }

    public CpAddRound clickEditRound() {
        browser.waitForPageStable();
        browser.click(editRoundButton, false);
        return new CpAddRound(browser);
    }

    public void clickDeleteRound() {
        browser.waitForPageStable();
        browser.click(deleteRoundButton, false);
    }

    public String getRoundDetailByIndex(int index, String tableColumn) {
        browser.waitForPageStable();
        for (int i = 0; i < 3; i++) {
            try {
                List<WebElement> investorRows = browser.findElements(roundsRows);
                int columnIndex = getColumnIndex(tableColumn);

                WebElement investorRowByIndex = investorRows.get(index);
                List<WebElement> investorDetailCells = investorRowByIndex.findElements(By.tagName("td"));

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
        List<WebElement> headers = browser.findElements(roundsTableColumnHeaders);
        for (int i = 0; i < headers.size() ; i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }

        throw new RuntimeException("Unable to find column with header name of " + name);
    }

    public void waitForRoundTableToBeStable() {
        browser.waitForPageStable();
    }

    public int getRowCount() {
        browser.waitForPageStable();
        return browser.findElements(roundsRows).size();
    }

    public void clickOkButton() {
        browser.clickAndWaitForElementToVanish(okButton);
    }

    public void removeAllNonDefaultRounds() {

        browser.waitForPageStable();

        List<WebElement> rows = browser.findElements(roundsRows);

        try {
            if (rows.size() != 1) {
                for (int i = 0; i < rows.size() - 1; i++) {
                    browser.executeScript("arguments[0].click();", browser.findElements(allDeleteRoundsButtons).get(1));
                    browser.waitForPageStable(Duration.ofSeconds(2));
                    browser.click(okButton);
                    browser.waitForPageStable(Duration.ofSeconds(2));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
