package io.securitize.pageObjects.controlPanel.cpIssuers.distributions;

import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.*;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class DistributionDetailsPage extends AbstractPage {

    private static final ExtendedBy distributionDate = new ExtendedBy("Distribution Date", By.xpath("//span[contains(text(), ' - ')]"));
    private static final ExtendedBy amountPerTokenText = new ExtendedBy("Amount Per Token Text", By.xpath("//span[contains(text(), 'Amount per Token')]"));
    private static final ExtendedBy distributionAmount = new ExtendedBy("Distribution Amount Text", By.xpath("//span[contains(text(), 'Total Amount')]"));
    private static final ExtendedBy distributionFundingNeeds = new ExtendedBy("Distribution Funding Needs", By.xpath("//span[contains(text(), 'CA funding needs:')]"));
    private static final ExtendedBy distributionStatus = new ExtendedBy("Distribution Status Text", By.xpath("//div[@id='ta-chip-distribution-details-status']//span"));
    private static final ExtendedBy confirmDistributionBtn = new ExtendedBy("Confirm Distribution Btn", By.xpath("//button[@id='ta-confirm-distribution-button-open']"));
    private static final ExtendedBy confirmBtn = new ExtendedBy("Confirm Distribution Modal Btn", By.xpath("//button/p[contains(text(), 'Confirm')]"));
    private static final ExtendedBy distributionInvestorsRows = new ExtendedBy("Distribution Investor Row", By.xpath("//div[@role='row']"));
    private static final ExtendedBy taxFormUpdatedSnakbar = new ExtendedBy("TaxForm Updated Snackbar", By.xpath("//*[contains(text(), 'Tax form updated')]"));
    private static final ExtendedBy distributionDetailsDownloadCSVBtn = new ExtendedBy("Distribution Details Download CSV Btn", By.xpath("//button[@type='button' and text()='Download CSV']"));
    private static final ExtendedBy distributionDetailsSearchBox = new ExtendedBy("Distribution Details Search Box", By.xpath("//input[@placeholder='Search…']"));
    private static final ExtendedBy distributionDetailsPageInvestorTokens = new ExtendedBy("Distribution Details Page Investor Tokens", By.xpath("(//div[@role='row']//div[@data-field='tokens'])[2]/div"));
    private static final ExtendedBy distributionDetailsRowsDisplayed = new ExtendedBy("Distribution Details Rows Displayed", By.xpath("//div[contains(@class,'MuiDataGrid-row')]"));
    private static final ExtendedBy taxFormStatusValidBtn = new ExtendedBy("Distribution TaxForm Valid Btn", By.xpath("//button[contains(@class, 'MuiButton-textSuccess')]"));
    private static final ExtendedBy taxFormStatusInvalidBtn = new ExtendedBy("Distribution TaxForm Invalid Btn", By.xpath("//button[contains(@class, 'MuiButton-textError')]"));
    private static final ExtendedBy taxFormStatusValidConfirmValidBtn = new ExtendedBy("Distribution TaxForm Status Confirm Valid Btn", By.xpath("//button[contains(@class, 'MuiButton-containedSuccess')]"));
    private static final ExtendedBy taxFormStatusValidConfirmInvalidBtn = new ExtendedBy("Distribution TaxForm Status Confirm Invalid Btn", By.xpath("//button[contains(@class, 'MuiButton-containedError')]"));
    private static final ExtendedBy taxFormValidModalText = new ExtendedBy("Distribution TaxForm Status Confirm Invalid Text", By.xpath("//button[contains(@class, 'MuiButton-containedError')]"));
    private static final ExtendedBy taxFormInvalidModalText = new ExtendedBy("Distribution TaxForm Status Confirm Invalid Text", By.xpath("//button[contains(@class, 'MuiButton-containedError')]"));
    private static final ExtendedBy singleRowInvalidTaxFormStatusLabel = new ExtendedBy("Distribution Single Row TaxForm Status Lavel", By.xpath("(//*[@data-field='tax-forms']/div)/p"));
    private static final ExtendedBy distributionDetailsPaginationNext = new ExtendedBy("Distribution Details Pagination Next", By.xpath("//button[@title='Go to next page']"));
    private static final ExtendedBy investorsPayoutStatus = new ExtendedBy("", By.xpath("//div[@id='ta-chip-distribution-details-payout-status']/span"));
    //Redemptions Web Elements
    private static final ExtendedBy singleRowInvestorsName = new ExtendedBy("Distribution Single Row Investor Name", By.cssSelector("div[data-field='name']:not([aria-label='Investor Name'])"));
    private static final ExtendedBy singleRowInvestorsEmail = new ExtendedBy("Distribution Single Row Investor Email", By.cssSelector("div[data-field='email']:not([aria-label='Email'])"));
    private static final ExtendedBy singleRowInvestorsExpectedToRedeem = new ExtendedBy("Distribution Single Row Tokens Expected to Redeem", By.cssSelector("div[data-field='redemptionExpectedTokens']:not([aria-label='Expected to Redeem'])"));
    private static final ExtendedBy singleRowInvestorsRedeemed = new ExtendedBy("Distribution Single Row Redeemed Tokens", By.cssSelector("div[data-field='tokens']:not([aria-label='Redeemed'])"));
    private static final ExtendedBy singleRowInvestorsGiveBack = new ExtendedBy("Distribution Single Row Give Back Tokens", By.cssSelector("div[data-field='redemptionGiveBackTokens']:not([aria-label='Give Back'])"));
    private static final ExtendedBy singleRowInvestorsGrossAmount = new ExtendedBy("Distribution Single Row Investor Gross Amount", By.cssSelector("div[data-field='payoutAmount']:not([aria-label='Gross Amount'])"));
    private static final ExtendedBy singleRowInvestorsPaidStatus = new ExtendedBy("Distribution Single Row Investor Paid Status", By.cssSelector("div[data-field='payout-method']:not([aria-label='Payout Status'])"));
    private static final ExtendedBy singleRowGrossAmountValue = new ExtendedBy("Payment Amount", By.xpath("//*[contains(@class, 'MuiDataGrid-row') and contains(@class,'MuiDataGrid-row--lastVisible')]//*[@data-field='payoutAmount']"));
    private static final ExtendedBy singleRowGiveBackAmountValue = new ExtendedBy("Give Back Tokens", By.xpath("//*[contains(@class, 'MuiDataGrid-row') and contains(@class,'MuiDataGrid-row--lastVisible')]//*[@data-field='redemptionGiveBackTokens']"));
    private static final ExtendedBy singleRowRedeemedTokensAmountValue = new ExtendedBy("Redeemed Tokens", By.xpath("//*[contains(@class, 'MuiDataGrid-row') and contains(@class,'MuiDataGrid-row--lastVisible')]//*[@data-field='tokens']"));
    private static final ExtendedBy distributionConfirmationModalBtn = new ExtendedBy("Distribution Confirmation Modal Btn", By.xpath("//*[text()='Confirm']/ancestor::button"));
    private static final ExtendedBy confirmRedemptionButton = new ExtendedBy("Confirm pop up button", By.xpath("//button[text()='Confirm distribution']"));
    private static final ExtendedBy uploadCSVButton = new ExtendedBy("Upload button", By.id("distribution-upload-button"));
    private static final ExtendedBy statusLabel = new ExtendedBy("Status label", By.xpath("//*[contains(@class, 'MuiChip-label') and contains(@class, 'MuiChip-labelMedium')]"));
    private static final ExtendedBy tableDisplayedRows = new ExtendedBy("", By.xpath("//*[contains(@class, 'MuiTablePagination-displayedRows')]"));
    private static final ExtendedBy tokensToBeIssued = new ExtendedBy("Tokens to be issued Text", By.xpath("//span[contains(text(), 'Tokens to be issued')]"));


    public DistributionDetailsPage(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    public String getSingleRowRedeemedTokensAmountValueText() {
        return browser.findElement(singleRowRedeemedTokensAmountValue).getText();
    }

    public String getSingleRowGiveBackAmountValueText() {
        return browser.findElement(singleRowGiveBackAmountValue).getText();
    }

    public String getSingleRowGrossAmountValueText() {
        return browser.findElement(singleRowGrossAmountValue).getText();
    }

    public String getStatusLabelText() {
        return browser.findElement(statusLabel).getText();
    }

    public void uploadCSV(String path) {
        browser.setElementAttribute(browser.findElement(uploadCSVButton), "style", "display:block;");
        browser.findElement(uploadCSVButton).sendKeys(path);
    }

    public void clickConfirmRedemptionButton() {
        browser.click(confirmRedemptionButton);
    }

    public void waitForDistributionConfirmationModalBtnToDisplay() {
        browser.waitForElementVisibility(distributionConfirmationModalBtn);
    }

    public void clickDistributionConfirmationModalBtn() {
        WebElement payoutsTab = browser.findElement(distributionConfirmationModalBtn);
        browser.executeScript("arguments[0].click();", payoutsTab);
    }

    public void clickConfirmDristributionBtn() {
        browser.click(confirmDistributionBtn);
    }

    public void clickConfirmDistributionModalBtn() {
        browser.clickAndWaitForElementToVanish(confirmBtn);
    }

    public void setTaxFormStatusTo(String taxFormStatus) {
        browser.waitForPageStable();
        if (taxFormStatus.equalsIgnoreCase("valid")) {
            WebElement element = browser.findElement(taxFormStatusValidBtn);
            element.click();
            browser.findElement(taxFormValidModalText).isDisplayed();
            browser.clickAndWaitForElementToVanish(taxFormStatusValidConfirmValidBtn);
        } else if (taxFormStatus.equalsIgnoreCase("invalid")) {
            WebElement element = browser.findElement(taxFormStatusInvalidBtn);
            element.click();
            browser.findElement(taxFormInvalidModalText).isDisplayed();
            browser.clickAndWaitForElementToVanish(taxFormStatusValidConfirmInvalidBtn);
        }
        browser.waitForElementToVanish(taxFormUpdatedSnakbar);
    }

    public Double getTotalDistributionAmount() {
        browser.waitForPageStable();
        WebElement element = browser.findElement(distributionAmount);
        String[] strings = element.getText().replace(",", "").split("\\$");
        return Double.parseDouble(strings[1]);
    }

    public double getDistributionFundingNeeds() {
        browser.waitForPageStable();
        WebElement element = browser.findElement(distributionFundingNeeds);
        String[] strings = element.getText().replace(",", "").split("\\$");
        return Double.parseDouble(strings[1]);
    }

    public String[] getDistributionDate() {
        return browser.getElementText(distributionDate).split(" ");
    }

    public String getAmountPerToken() {
        WebElement element = browser.findElement(amountPerTokenText);
        return element.getText().replaceAll("Amount per Token: \\$", "");
    }

    public boolean isDistributionStatusCompleted() {
        browser.waitForPageStable();
        return browser.findElement(distributionStatus).getText().equalsIgnoreCase("completed");
    }

    public String getDijstributionDetailsPageTokensByInvestorMail(String investorEmail) {
        return getDistributionDetailsPageInvestorTokens();
    }

    public String getDistributionDetailsPageInvestorTokens() {
        return browser.findElement(distributionDetailsPageInvestorTokens).getText().replace(",", "");
    }

    public void clickDistributionDetailsDownloadCSVBtn() {
        browser.click(distributionDetailsDownloadCSVBtn);
    }

    public void searchDistributionInvestor(String investorMail) {
        browser.clearTextInput(distributionDetailsSearchBox);
        browser.sendKeysElement(distributionDetailsSearchBox, "Search Investor Mail In Distribution", investorMail);
        waitForSingleSearchResult();
    }

    public double calculateTotalDistributionAmount() {
        boolean arePagesLeft = true;
        double distributionAmount = 0;
        do {
            browser.waitForPageStable();
            int rowsDisplayed = browser.findElements(distributionDetailsRowsDisplayed).size();
            for (int i = 1; i <= rowsDisplayed; i++) {
                String cellData = getDistributionDetailsColumnDataByRow("amount", i);
                Double amount = Double.parseDouble(cellData.replace("$", ""));
                distributionAmount = distributionAmount + amount;
            }
            WebElement paginationNext = browser.findElement(distributionDetailsPaginationNext);
            if (paginationNext.isEnabled()) {
                paginationNext.click();
                browser.waitForPageStable();
            } else {
                arePagesLeft = false;
            }
        } while (arePagesLeft);
        return distributionAmount;
    }

    public String getDistributionDetailsColumnDataByRow(String columnHeader, int row) {
        browser.waitForPageStable();
        final String distributionRow = "(//div[contains(@class, 'MuiDataGrid-row')][" + row + "]";
        final String distributionRowContent = "//div[contains(@class, 'MuiDataGrid-cellContent')])[" + setColumnHeaderXpath(columnHeader) + "]";
        WebElement rowDataColumn = Browser.getDriver().findElement(By.xpath(distributionRow + distributionRowContent));
        return rowDataColumn.getText();
    }

    public String setColumnHeaderXpath(String columnHeader) {
        String columnHeaderIndex = null;
        if (columnHeader.equalsIgnoreCase("investor name")) {
            columnHeaderIndex = "1";
        } else if (columnHeader.equalsIgnoreCase("email")) {
            columnHeaderIndex = "2";
        } else if (columnHeader.equalsIgnoreCase("tokens")) {
            columnHeaderIndex = "3";
        } else if (columnHeader.equalsIgnoreCase("amount")) {
            columnHeaderIndex = "4";
        }
        return columnHeaderIndex;
    }

    public String getDistributionIdFromUrl() {
        browser.waitForPageStable(Duration.ofSeconds(5));
        String url = browser.getCurrentUrl();
        String[] distributionId = url.split("/");
        return distributionId[6];
    }

    public boolean isCompleteDistributionBtnEnabled() {
        return browser.isElementEnabled(confirmDistributionBtn);
    }

    public void areAllInvestorsPaymentsCompleted() {
        List<WebElement> investorsStatus = browser.findElements(investorsPayoutStatus);
        for (WebElement status : investorsStatus) {
            String statusText = status.getText();
            Assert.assertTrue(statusText.equalsIgnoreCase("Completed") || statusText.equalsIgnoreCase("Unable") || statusText.equalsIgnoreCase("In progress"));
        }
    }

    public ExtendedBy getInvestorsNameData() {
        return singleRowInvestorsName;
    }

    public ExtendedBy getInvestorsEmailData() {
        return singleRowInvestorsEmail;
    }

    public ExtendedBy getInvestorsExpectedToRedeemData() {
        return singleRowInvestorsExpectedToRedeem;
    }

    public ExtendedBy getInvestorsRedeemedData() {
        return singleRowInvestorsRedeemed;
    }

    public ExtendedBy getInvestorsGiveBackData() {
        return singleRowInvestorsGiveBack;
    }

    public ExtendedBy getInvestorsGrossAmountData() {
        return singleRowInvestorsGrossAmount;
    }

    public ExtendedBy getInvestorsPaidStatusData() {
        return singleRowInvestorsPaidStatus;
    }

    public int getRowsCountByColumn(ExtendedBy elements) {
        int holdersAmount = 0;
        List<WebElement> investorsEmailsList = browser.findElements(elements);
        for (WebElement emailRow : investorsEmailsList) {
            try {
                String email = emailRow.getText();
                holdersAmount++;
            } catch (Exception e) {

            }
        }
        return holdersAmount;
    }

    public void waitForSingleSearchResult() {
        Function<String, Boolean> internalWaitForSingleSearchResult = t -> {
            if (browser.findElements(distributionInvestorsRows).size() == 2) {
                return true;
            } else {
                return false;
            }
        };
        String description = "Waiting for Single Search Result On Distribution Details Page";
        Browser.waitForExpressionToEqual(internalWaitForSingleSearchResult, null, true, description, 10, 5000);
    }

    public void waitForTaxFormStatus(String taxFormStatus) {
        Function<String, Boolean> internalWaitForTaxFormStatus = t -> {
            WebElement element = browser.findElement(singleRowInvalidTaxFormStatusLabel);
            if (element.getText().equalsIgnoreCase(taxFormStatus)) {
                return true;
            } else {
                browser.refreshPage();
                return false;
            }
        };
        String description = "Waiting for Investor TaxForm Status on Distribution Details Page";
        Browser.waitForExpressionToEqual(internalWaitForTaxFormStatus, null, true, description, 5, 5000);
    }

    public String getDisplayedRowsText() {
        return browser.findElement(tableDisplayedRows).getText();
    }

    public int getDistributionInvestorsCount() {
        String[] displayedRowsText = getDisplayedRowsText().split(" ");
        int numberOfInvestors = 0;
        if (displayedRowsText.length > 1){
            try {
                numberOfInvestors = Integer.parseInt(displayedRowsText[2]);
            } catch (Exception e) {
                errorAndStop("Error parsing numberOfInvestors" + e, true);
            }
        } else {
            errorAndStop("Length of displayedRowsText should be at least 2, but it is: " + displayedRowsText.length, true);
        }
        return numberOfInvestors;
    }

    public void waitForInvestorsDistributionToExist() {

        Function<String, Boolean> waitForInvestorsDistributionToExist = t -> {
            try {
                browser.refreshPage();
                browser.waitForPageStable(Duration.ofSeconds(3));
                int amountOfInvestors = getDistributionInvestorsCount();
                return amountOfInvestors != 0;
            } catch (Exception e) {
                return false;
            }
        };

        String description = "waitForInvestorsDistribution to not equal 0";
        Browser.waitForExpressionToEqual(waitForInvestorsDistributionToExist, null, true, description, 60, 5000);
    }

    public double getTokensToBeIssued() {
        WebElement element = browser.findElement(tokensToBeIssued);
        return RegexWrapper.stringToDouble(element.getText());
    }

    public void waitForTokensToBeIssuedToLoad() {
        Function<String, Boolean> waitForTokensToBeIssuedToLoad = t -> {
            try {
                browser.refreshPage();
                browser.waitForPageStable(Duration.ofSeconds(3));
                double amountOfTokensToBeIssued = getTokensToBeIssued();
                return amountOfTokensToBeIssued != 0;
            } catch (Exception e) {
                return false;
            }
        };

        String description = "Wait For Tokens To Be Issued to not equal 0";
        Browser.waitForExpressionToEqual(waitForTokensToBeIssuedToLoad, null, true, description, 60, 5000);
    }

}