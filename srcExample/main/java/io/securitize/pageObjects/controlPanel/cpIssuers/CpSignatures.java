package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.Users;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.tests.transferAgent.testData.DistributionData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;

public class CpSignatures extends AbstractPage<CpSignatures> {

    private static final ExtendedBy signAllTransactionsButton = new ExtendedBy("sign all pending transactions button", By.xpath("//button[contains(text(),'Sign all pending transaction')]"));
    private static final ExtendedBy filterByStatusSelector = new ExtendedBy("filter by status selector", By.xpath("//select[@class='custom-select']"));
    private static final ExtendedBy signAllPendingTransactionsBtn = new ExtendedBy("sign 1 transaction button", By.xpath("//button[contains(@class, 'primary')]"));
    private static final String signaturesTableRowFindByDescriptionTemplateXpath = "//td//span[contains(text(),'%s')]//ancestor::tr";
    private static final String signaturesTableRowCheckboxByDescriptionTemplateXpath = "//td//span[contains(text(),'%s')]//ancestor::tr//input//ancestor::td";
    private static final ExtendedBy signaturesTableHeadersRow = new ExtendedBy("Signatures table header row", By.xpath("//th//ancestor::tr"));
    private static final ExtendedBy settingsButtonOnFirstRow = new ExtendedBy("Settings Button on First Row", By.xpath("//button[contains(@class, 'btn') and contains(@class, 'dropdown-toggle') and contains(@class, 'btn-default') and contains(@class, 'btn-xs') and contains(@class, 'icon-btn') and contains(@class, 'md-btn-flat') and contains(@class, 'hide-arrow')]"));
    private static final ExtendedBy viewButton = new ExtendedBy("View button", By.xpath("//table/tbody/tr[1]/td[9]/div/ul/li/a"));
    private static final ExtendedBy transactionHash = new ExtendedBy("Transaction Hash", By.xpath("//*[contains(text(), '0x')]"));
    private static final ExtendedBy signaturesList = new ExtendedBy("Signatures Elements Lists", By.xpath("//tr[@role='row']"));
    private static final ExtendedBy tableIsLoadingMsg = new ExtendedBy("Table Is Loading Msg", By.xpath("//*[contains(text(), 'Loading')]"));
    private static final ExtendedBy noRecordsFoundMsg = new ExtendedBy("No Records Found Txt", By.xpath("//*[contains(text(), 'No records were found')]"));
    private static final ExtendedBy pendingTransactionsList = new ExtendedBy("Pending Transactions List", By.xpath("//*[contains(text(), 'pending')]"));
    private static final ExtendedBy pendingTransactions = new ExtendedBy("Pending Transactions", By.xpath("//*[contains(text(), 'pending')]"));
    private static final ExtendedBy sign1TransactionButton = new ExtendedBy("sign 1 transaction button", By.xpath("//button[contains(@class, 'btn-primary')]"));
    private static final ExtendedBy alertSignatureMessage = new ExtendedBy("Alert or error message after signing", By.xpath("//div[@role='alert']"));
    private static final ExtendedBy cogWheelFirstRow = new ExtendedBy("cog Wheel of First Row", By.xpath("//td//span[contains(text(),'AUT')]//ancestor::tr//div//button//i"));
    private static final ExtendedBy cogWheelSignButton = new ExtendedBy("cog Wheel menu - Sign button ", By.xpath("//a[@class='dropdown-item' and contains(text(),'Sign')]"));

    public CpSignatures(Browser browser) {
        super(browser);
    }

    public CpSignatures checkRowByUserFirstName(String firstName) {
        // wait for confirmation message to show up
        String xPathString = String.format(signaturesTableRowCheckboxByDescriptionTemplateXpath, firstName);
        ExtendedBy rowCheckbox = new ExtendedBy("signature row with description containing first name of " + firstName, By.xpath(xPathString));
        browser.findElement(rowCheckbox).click();
        return this;
    }

    public CpConfirmSignature clickSignAllTransactionsButton() {
        browser.click(signAllPendingTransactionsBtn);
        return new CpConfirmSignature(browser);
    }

    public void clickSignAllTransactionsBtn() {
        browser.click(signAllTransactionsButton);
    }

    public CpSignatures filterByStatus(String status) {
        browser.selectElementByVisibleText(filterByStatusSelector, status);
        // give the page enough time to load the new table and stabilize
        browser.waitForPageStable(Duration.ofSeconds(2));
        return this;
    }

    public boolean isTableContainingRowsByDescriptionContent(String content, int rowAmount) {
        String xPathString = String.format(signaturesTableRowFindByDescriptionTemplateXpath, content);
        ExtendedBy rows = new ExtendedBy("signature table rows with description containing " + content, By.xpath(xPathString));
        int rowsFound = browser.findElements(rows).size();
        info(String.format("Found %s matching rows with content of %s", rowsFound, content));
        return rowsFound > rowAmount;
    }


    public CpSignatures waitForPendingTableToContainRowsByDescriptionContent(String content, int rowAmount) {
        Function<String, Boolean> internalWaitForTableToContain = t -> {
            try {
                info(String.format("Checking if pending table contains %s rows with content of %s", rowAmount, content));
                boolean result = isTableContainingRowsByDescriptionContent(content, rowAmount);
                info("Result in pending page: " + result);

                if (!result) {
                    info("Refreshing page for next attempt");
                    browser.refreshPage(true);
                }

                return result;
            } catch (Exception e) {
                info("An error occur trying to check if pending table contains content: " + e);
                return false;
            }
        };

        String description = String.format("Checking if pending table contains %s rows with content of %s", rowAmount, content);
        Browser.waitForExpressionToEqual(internalWaitForTableToContain, null, true, description, 120, 5000);
        return this;
    }

    public CpSignatures waitForTableToContainRowsByDescriptionOnAllStatuses (String content,int rowAmount) {
        Function<String, Boolean> internalWaitForTableToContain = t -> {
            try {
                info(String.format("Checking if table contains %s rows with content of %s", rowAmount, content));
                browser.refreshPage(true);
                filterByStatus("All");
                browser.waitForPageStable(Duration.ofSeconds(4));
                String xPathString = String.format(signaturesTableRowFindByDescriptionTemplateXpath, content);
                ExtendedBy rows = new ExtendedBy("signature table rows with description containing " + content, By.xpath(xPathString));
                int rowsFound = browser.findElements(rows).size();
                info(String.format("Found %s matching rows with content of %s", rowsFound, content));
                return rowsFound == rowAmount;
            } catch (Exception e) {
                info("An error occur trying to check if table contains content: " + e);
                return false;
            }
        };
        String description = String.format("Checking if table contains %s rows with content of %s", rowAmount, content);
        Browser.waitForExpressionToEqual(internalWaitForTableToContain, null, true, description, 120, 5000);
        return this;
    }


    public CpSignatures waitForTableToContainRowsByDescriptionContent(String content, int rowAmount) {
        Function<String, Boolean> internalWaitForTableToContain = t -> {
            try {
                info(String.format("Checking if table contains %s rows with content of %s", rowAmount, content));
                filterByStatus("Sent");
                boolean result = isTableContainingRowsByDescriptionContent(content, rowAmount);
                info("Result in sent page: " + result);

                // try the success filter
                if (!result) {
                    // find the header row in the table so that we can wait for it to vanish - to know
                    // the table is being refreshed
                    WebElement headersRow = browser.findElement(signaturesTableHeadersRow);
                    filterByStatus("Success");
                    browser.waitForElementToVanish(headersRow, signaturesTableHeadersRow.getDescription());
                    result = isTableContainingRowsByDescriptionContent(content, rowAmount);
                    info("Result in success page: " + result);
                }

                if (!result) {
                    info("Refreshing page for next attempt");
                    WebElement headersRow = browser.findElement(signaturesTableHeadersRow);
                    filterByStatus("Sent"); // return to original page before refreshing
                    browser.waitForElementToVanish(headersRow, signaturesTableHeadersRow.getDescription());
                    browser.refreshPage(true);
                }

                return result;
            } catch (Exception e) {
                info("An error occur trying to check if table contains content: " + e);
                return false;
            }
        };

        String description = String.format("Checking if table contains %s rows with content of %s", rowAmount, content);
        Browser.waitForExpressionToEqual(internalWaitForTableToContain, null, true, description, 120, 5000);
        return this;
    }

    public CpSignatures waitForSignaturePageToStabilize() {
        browser.waitForPageStable(Duration.ofSeconds(3));
        return this;
    }

    public String getLatestSuccessTransactionHash() {
        browser.waitForPageStable(Duration.ofSeconds(5));
        List<WebElement> rows = browser.findElements(settingsButtonOnFirstRow);
        browser.executeScript("arguments[0].click();", rows.get(0));
        browser.waitForPageStable(Duration.ofSeconds(2));
        browser.click(viewButton);
        browser.waitForPageStable(Duration.ofSeconds(4));
        return browser.findElement(transactionHash).getText().replace("Tx: ", "");
    }

    public CpSignatures waitForTableToContainRowsByDescription(String content, int rowAmount) {
        Function<String, Boolean> internalWaitForTableToContain = t -> {
            try {
                info(String.format("Checking if table contains %s rows with content of %s", rowAmount, content));
                filterByStatus("Sent");
                boolean result = isTableContainingRowsByDescription(content, rowAmount);
                info("Result in sent page: " + result);

                // try the success filter
                if (!result) {
                    // find the header row in the table so that we can wait for it to vanish - to know
                    // the table is being refreshed
                    WebElement headersRow = browser.findElement(signaturesTableHeadersRow);
                    filterByStatus("Success");
                    browser.waitForElementToVanish(headersRow, signaturesTableHeadersRow.getDescription());
                    result = isTableContainingRowsByDescription(content, rowAmount);
                    info("Result in success page: " + result);
                }

                if (!result) {
                    info("Refreshing page for next attempt");
                    WebElement headersRow = browser.findElement(signaturesTableHeadersRow);
                    filterByStatus("Sent"); // return to original page before refreshing
                    browser.waitForElementToVanish(headersRow, signaturesTableHeadersRow.getDescription());
                    browser.refreshPage(true);
                }

                return result;
            } catch (Exception e) {
                info("An error occur trying to check if table contains content: " + e);
                return false;
            }
        };

        String description = String.format("Checking if table contains %s rows with content of %s", rowAmount, content);
        Browser.waitForExpressionToEqual(internalWaitForTableToContain, null, true, description, 120, 5000);
        return this;
    }

    public boolean isTableContainingRowsByDescription(String content, int rowAmount) {
        String xPathString = String.format(signaturesTableRowFindByDescriptionTemplateXpath, content);
        ExtendedBy rows = new ExtendedBy("signature table rows with description containing " + content, By.xpath(xPathString));
        int rowsFound = browser.findElements(rows).size();
        info(String.format("Found %s matching rows with content of %s", rowsFound, content));
        return rowsFound == rowAmount;
    }

    public int getPendingTransactionsCount() {
        browser.waitForPageStable();
        // we take 1 element because 'Sign All Peding Transactions' btn is always present.
        return browser.findElements(pendingTransactionsList).size() - 1;
    }

    public boolean isNorecordsFoundMsgDisplayed() {
        try {
            browser.waitForPageStable();
            return browser.findElement(noRecordsFoundMsg).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void signAllPendingTransactions(DistributionData distributionData) {

        Function<String, Boolean> internalSignAllPendingTransactions = t -> {
            browser.waitForPageStable(Duration.ofSeconds(10));
            int pendingTransactionsCount = getPendingTransactionsCount();
            if (pendingTransactionsCount > 0) {
                clickSignAllTransactionsBtn();
                CpConfirmSignature cpConfirmSignature = new CpConfirmSignature(browser);
                cpConfirmSignature.typePrivateKey(distributionData.walletPk);
                cpConfirmSignature.typeSignerAddress(distributionData.walletAddress);
                cpConfirmSignature.clickSignNow();
                browser.refreshPage();
                return false;
            } else {
                return true;
            }
        };
        String description = "Signing All Pending Transactions";
        Browser.waitForExpressionToEqual(internalSignAllPendingTransactions, null, true, description, 120, 15000);

    }
    
    public void waitForNoRecordsFoundMsgDisappears() {
        Function<String, Boolean> internalWaitForNoRecordsMsgDisappears = t -> {
            if (isNorecordsFoundMsgDisplayed()) {
                browser.refreshPage();
                browser.waitForPageStable();
                return false;
            } else {
                return true;
            }

        };
        String description = "Waiting No Records Founded msg disappears";
        Browser.waitForExpressionToEqual(internalWaitForNoRecordsMsgDisappears, null, true, description, 1500, 5000);
    }

    public void waitForExpectedTransactionRows(int expectedTransactionsRows) {
        Function<String, Boolean> internalWaitForExpectedTxRows = t -> {
            int actualTransactionsRows = browser.findElements(signaturesList).size();
            if (actualTransactionsRows < expectedTransactionsRows) {
                browser.refreshPage();
                browser.waitForPageStable();
                return false;
            } else {
                return true;
            }
        };
        String description = "Waiting for Expected Transaction Row Count be: " + expectedTransactionsRows;
        Browser.waitForExpressionToEqual(internalWaitForExpectedTxRows, null, true, description, 1500, 5000);
    }

    public void waitForTransactionsCount(int totalNumberOfInvestors) {
        int investorsPerSignatureEnvCpConfig = 100;
        int expectedTransactionsRows = calculateSignaturesCount(totalNumberOfInvestors, investorsPerSignatureEnvCpConfig);
        waitForNoRecordsFoundMsgDisappears();
        waitForExpectedTransactionRows(expectedTransactionsRows);
    }

    public void waitForNoRecordsFoundMsgVisible() {
        Function<String, Boolean> internalWaitForNoRecordsMsgDisappears = t -> {
            if (isNorecordsFoundMsgDisplayed()) {
                return true;
            } else {
                browser.refreshPage();
                browser.waitForPageStable();
                return false;
            }
        };
        String description = "Waiting No Records Founded MSG to BE Displayed";
        Browser.waitForExpressionToEqual(internalWaitForNoRecordsMsgDisappears, null, true, description, 600, 5000);

    }



    public int calculateSignaturesCount(int totalNumberOfInvestors, int investorsPerSignatureEnvCpConfig) {
        int signaturesCount = totalNumberOfInvestors / investorsPerSignatureEnvCpConfig;
        info("signaturesCount = " + signaturesCount);
        if (totalNumberOfInvestors % investorsPerSignatureEnvCpConfig != 0) {
            signaturesCount++; // Add an extra firm if there are remaining investors
        }
        return signaturesCount;
    }

    public int checkHowManySignaturesByFirstName(String firstName) {
        int numOfSignatures;
        String xPathString = String.format(signaturesTableRowCheckboxByDescriptionTemplateXpath, firstName);
        ExtendedBy rowCheckbox = new ExtendedBy("signature row with description containing first name of " + firstName, By.xpath(xPathString));
        List<WebElement> checkboxes = browser.findElements(rowCheckbox);
        numOfSignatures = checkboxes.size();
        return numOfSignatures;
    }

    public CpConfirmSignature clickSingleTransactionButton() {
        browser.waitForElementVisibility(cogWheelFirstRow);
        browser.clickWithJs(browser.findElement(cogWheelFirstRow));
        browser.waitForElementVisibility(cogWheelSignButton);
        browser.click(cogWheelSignButton);
        return new CpConfirmSignature(browser);
    }

    public void verifySuccessMessage() {
        browser.verifyAlertMessage(alertSignatureMessage, "Success", true);
    }

    public CpSignatures checkAndMarkRowByUserFirstName(String firstName, int numOfSignatures) {
        String xPathString = String.format(signaturesTableRowCheckboxByDescriptionTemplateXpath, firstName);
        ExtendedBy rowCheckbox = new ExtendedBy("signature row with description containing first name of " + firstName, By.xpath(xPathString));
        List<WebElement> listCheckBox = browser.findElements(rowCheckbox);
        int count = 0;
        for (WebElement checkBox : listCheckBox) {
            if (count < numOfSignatures) {
                checkBox.click();
                count++;
            } }
        return this;
    }
    public void signSignatures(String issuerName, String firstName, int numOfSignatures){
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerWalletAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);
        waitForSignaturePageToStabilize();
        if (numOfSignatures>1){
            checkAndMarkRowByUserFirstName(firstName, numOfSignatures);
            clickSignAllTransactionsButton();
        }
        else{
            clickSingleTransactionButton();
        }
        CpConfirmSignature confirmSignature = new CpConfirmSignature(browser);
        confirmSignature.clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();
    }
}
