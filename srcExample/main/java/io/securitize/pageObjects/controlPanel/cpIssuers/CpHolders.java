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

public class CpHolders extends AbstractPage {

    private static final ExtendedBy addInvestorButton = new ExtendedBy("add investor button", By.xpath("//button//span[contains(text(),'Add Investor')]"));
    private static final ExtendedBy holdersOfRecordTable = new ExtendedBy("Investors table", By.xpath("//table[contains(@class, 'table b-table card-table table-striped table-hover')]//td"));
    private static final ExtendedBy holdersOfRecordTableColumnHeaders = new ExtendedBy("investors table columns headers", By.xpath("//table//th"));
    private static final ExtendedBy searchInvestorTextBox = new ExtendedBy("Search for investor", By.xpath("//*[contains(@class, 'd-inline-block w-auto float-sm-right form-control form-control-sm')]"));
    private static final String recordHolderColumnCellsPattern = "//table//tr//td[@role='cell'][%s]//a";
    private static final String walletAddressColumnCellsPattern = "//table//tr//td[@role='cell'][%s]";
    private static final ExtendedBy filterButton = new ExtendedBy("filter button", By.xpath("//div[contains(text(),'Filter')]"));
    private static final ExtendedBy minTokenAmountField = new ExtendedBy("filter button", By.xpath("//input[@min]"));
    private static final ExtendedBy noRecordFoundMessage = new ExtendedBy("filter button", By.xpath("//div[contains(text(),' No records were found')]"));

    public CpHolders(Browser browser) {
        super(browser, addInvestorButton);
    }

    public void searchUniqueInvestorByTextBox(String investorName) {
        browser.typeTextElementCtrlA(searchInvestorTextBox, investorName);
        browser.waitForElementTextNotEmpty(searchInvestorTextBox);
        browser.waitForElementVisibility(holdersOfRecordTableColumnHeaders);
    }

    public String getInvestorDetailByIndex(int index, String tableColumn) {
        for (int i = 0; i < 3; i++) {
            try {
                List<WebElement> investorRows = browser.findElements(holdersOfRecordTable);
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
        List<WebElement> headers = browser.findElements(holdersOfRecordTableColumnHeaders);
        for (int i = 0; i < headers.size(); i++) {
            String currentColumnHeader = browser.getElementText(headers.get(i), "Header column index " + i);
            if (name.equalsIgnoreCase(currentColumnHeader)) {
                return i;
            }
        }
        throw new RuntimeException("Unable to find column with header name of " + name);
    }

    public String waitInvestorDetailsPage(int timeoutSeconds, ExtendedBy clickElement) {
        Function<String, String> internalWaitInvestorDetailsPage = t -> {
            browser.waitForPageStable(Duration.ofSeconds(5));
            String pageTitle = browser.getPageTitle().toLowerCase();
            try {
                if (!pageTitle.contains("details")) {
                }
                return pageTitle;
            } catch (Exception e) {
                return null;
            }
        };
        String description = "Wait for Investor Details Page to upload";
        return Browser.waitForExpressionNotNull(internalWaitInvestorDetailsPage, null, description, timeoutSeconds, 1000);
    }

    public void findInvestorWithTokens(String searchText, String autName, String procedureType, String tokensHeldIn, int tokensAmount, String nonValidSearch) {

        // reset table to avoid errors with non-valid search
        searchUniqueInvestorByTextBox(nonValidSearch);
        browser.waitForElementVisibility(noRecordFoundMessage);

        // filter investors by minimum token amount and delete non-valid search
        browser.click(filterButton);
        browser.typeTextElementCtrlA(minTokenAmountField, String.valueOf(tokensAmount));
        if (searchText != null) {
            browser.typeTextElementCtrlA(searchInvestorTextBox, searchText);
        } else {
            browser.clearTextInput(searchInvestorTextBox);
            browser.waitForElementToVanish(noRecordFoundMessage);
        }
        browser.waitForElementVisibility(holdersOfRecordTable);

        // set list of "Record Holder" column
        int recordHoldersColumnIndex = getColumnIndex("Record Holder") + 1;
        String xPathStringRecordHolder = String.format(recordHolderColumnCellsPattern, recordHoldersColumnIndex);
        ExtendedBy recordHolderColumnCells = new ExtendedBy("cell of column record holders", By.xpath(xPathStringRecordHolder));
        List<WebElement> listOfRecordHolders;

        // set list of "Wallet Address" column
        int recordWalletAddressColumnIndex = getColumnIndex("Wallet Address") + 1;
        String xPathStringWalletAddress = String.format(walletAddressColumnCellsPattern, recordWalletAddressColumnIndex);
        ExtendedBy walletAddressColumnCells = new ExtendedBy("cell of column wallet address", By.xpath(xPathStringWalletAddress));
        List<WebElement> listOfWalletAddress = browser.findElements(walletAddressColumnCells);

        // set expected text from wallet address column by required token held in
        String expectedTextInCell;
        if (tokensHeldIn.equals("wallet")) {
            expectedTextInCell = "0x";
        } else if (tokensHeldIn.equals("TBE")) {
            expectedTextInCell = "treasury";
        } else if (tokensHeldIn.equals("both")) {
            expectedTextInCell = "0x";
        } else {
            expectedTextInCell = "";
        }

        // get row with require token held in - TBE/wallet/both
        for (int i = 0; i < listOfWalletAddress.size(); i++) {
            listOfRecordHolders = browser.findElements(recordHolderColumnCells);
            listOfWalletAddress = browser.findElements(walletAddressColumnCells);
            if (listOfWalletAddress.get(i).getText().contains(expectedTextInCell)) {
                listOfRecordHolders.get(i).click();
                waitInvestorDetailsPage(3000, recordHolderColumnCells);
                CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(browser);
                int tokensCanBeProcedure = investorDetailsPage.getTokensToProcedure(tokensHeldIn, procedureType);
                if (procedureType.equals("unlock")) {
                    tokensCanBeProcedure = investorDetailsPage.getTokensToUnlock();
                }
                if (tokensCanBeProcedure < tokensAmount) {
                    browser.navigateBack();
                } else {
                    if (autName.contains("608") || autName.contains("535")) {
                        // change name of investor to AUT name
                        browser.refreshPage();
                        browser.waitForPageStable();
                        investorDetailsPage.clickEdit()
                                .typeFirstName(autName)
                                .typeMiddleName(procedureType)
                                .typeLastName(tokensHeldIn)
                                .clickSaveChanges();
                    }
                    break;
                }
            }
        }


    }


}