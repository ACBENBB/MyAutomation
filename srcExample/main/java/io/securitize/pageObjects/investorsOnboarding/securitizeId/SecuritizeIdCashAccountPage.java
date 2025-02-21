package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.*;
import io.securitize.pageObjects.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.MigrationModalPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.TransactionRow;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.TransferFundsModalPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.WithdrawModalPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SecuritizeIdCashAccountPage extends AbstractPage {

    private static final ExtendedBy userMenuButton = new ExtendedBy("User Menu button", By.cssSelector("button.user-menu_avatar"));
    private static final ExtendedBy manageFundsButton = new ExtendedBy("Manage funds button", By.id("btn-cash-account-add-funds"));
    private static final ExtendedBy addFundsButton = new ExtendedBy("Add Funds button", By.cssSelector("[data-test-id='ca-account-addfunds-btn']"));
    private static final ExtendedBy withdrawButton = new ExtendedBy("Withdraw button", By.cssSelector("[data-test-id='ca-account-withdraw-btn']"));
    private static final ExtendedBy usdCard = new ExtendedBy("USD Card", By.xpath(".//div[contains(@class, '__balanceTitleBox__')][contains(., 'USD')]"));
    private static final ExtendedBy eurCard = new ExtendedBy("EUR Card", By.xpath(".//div[contains(@class, '__balanceTitleBox__')][contains(., 'EUR')]"));
    private static final ExtendedBy availableBalance = new ExtendedBy("Available Balance", By.cssSelector("[data-test-id='ca-account-cashbalance-value']"));
    private static final ExtendedBy unlinkButton = new ExtendedBy("Unlink Button", By.cssSelector("[data-test-id^='card-unlink-button-container']"));
    private static final ExtendedBy unlinkCardModalButton = new ExtendedBy("Unlink Modal", By.cssSelector("[data-test-id='ca-unlinkaccountmodal-submit-btn']"));
    private static final ExtendedBy closeAccountModalButton = new ExtendedBy("Close Button", By.cssSelector("[class^=style__RightSectionContainer__]"));
    private static final ExtendedBy transactionHistoryRows = new ExtendedBy("Transaction History", By.cssSelector("[class*=style__HistoryTable_] tbody tr"));
    private static final ExtendedBy transactionHistoryRowCells = new ExtendedBy("Transaction History Row Cells", By.tagName("td"));
    private static final ExtendedBy goBackArrowButton = new ExtendedBy("Go Back Arrow Button", By.cssSelector("[class*='_goBack_']"));
    private static final ExtendedBy migrationModalBody = new ExtendedBy("Migration modal body", By.cssSelector("[class^='MigrationModal__content']"));
    private static final ExtendedBy closeTermsAndConditionnsModal = new ExtendedBy("Migrating terms and conditions close button", By.xpath("//div[@class='modal-content']/div[1]/div[2]/*/*"));
    private static final ExtendedBy unlinkAccountLink = new ExtendedBy("Unlink Bank account link", By.cssSelector("[data-test-id*='linked-bank-account-unlink-container']"));
    private static final ExtendedBy unlinkAccountButton = new ExtendedBy("Unlink account button ", By.cssSelector("[data-test-id='ca-unlinkaccountmodal-submit-btn']"));


    public SecuritizeIdCashAccountPage(Browser browser) {
        super(browser, manageFundsButton);

        //todo to be removed after June 23rd. (When Synapse migration period is over)
        if(this.isMigrationModalPresent()) {
            MigrationModalPage migrationModalPage = new MigrationModalPage(browser);
            migrationModalPage.close();
        }
    }

    public double getCashAccountHistoryLastTransaction(String columnHeader) {
        return Double.parseDouble(getMyAccountHistoryColumnDataByRow(columnHeader, 1));
    }

    public void acceptTermsAndConditionsModalIfPresent() {
        browser.waitForPageStable();
        try {
            browser.click(closeTermsAndConditionnsModal);
        } catch (NoSuchElementException e) {

        } catch  (TimeoutException e) {

        }
    }

    private String getMyAccountHistoryColumnDataByRow(String columnHeader, int row) {
        browser.waitForPageStable();
        final String historyRow = "(//table//tbody//tr[" + row + "]";
        final String historyRowContent = "//td)[" + setColumnHeaderXpath(columnHeader) + "]";
        WebElement rowDataColumn = Browser.getDriver().findElement(By.xpath(historyRow + historyRowContent));
        return rowDataColumn.getText().replaceAll("\\$", "");
    }

    public String setColumnHeaderXpath(String columnHeader) {
        String columnHeaderIndex = null;
        if (columnHeader.equalsIgnoreCase("type")) {
            columnHeaderIndex = "1";
        } else if (columnHeader.equalsIgnoreCase("date")) {
            columnHeaderIndex = "2";
        } else if (columnHeader.equalsIgnoreCase("transaction amount")) {
            columnHeaderIndex = "3";
        } else if (columnHeader.equalsIgnoreCase("fee")) {
            columnHeaderIndex = "4";
        } else if (columnHeader.equalsIgnoreCase("status")) {
            columnHeaderIndex = "5";
        } else if (columnHeader.equalsIgnoreCase("action")) {
            columnHeaderIndex = "6";
        } else if (columnHeader.equalsIgnoreCase("total")) {
            columnHeaderIndex = "7";
        }
        return columnHeaderIndex;
    }

    public void clickManageFundsButton() {
        browser.click(manageFundsButton, false);
        
    }

    public TransferFundsModalPage clickAddFundsButton() {
        browser.click(addFundsButton, false);
        return new TransferFundsModalPage(browser);
    }

    public boolean isAddFundsButtonPresent() {
        return browser.isElementPresentOrVisible(addFundsButton);
    }

    public WithdrawModalPage clickWithdrawButton() {
        browser.findElement(withdrawButton).click();
        return new WithdrawModalPage(browser);
    }

    public String getAddFundsButtonText() {
        return browser.getElementText(addFundsButton);
    }

    public WebElement getUSDCurrencyCard() {
        return browser.getElement(usdCard);
    }

    public String getUSDBalance() {
        return getUSDCurrencyCard().findElement(availableBalance.getBy()).getText();
    }

    public WebElement getEURCurrencyCard() {
        return browser.getElement(eurCard);
    }

    public WebElement getUnlinkButton() {
        return browser.findElement(unlinkButton);
    }

    public WebElement getUnlinkCardModalButton() {
        WebElement button = browser.findElement(unlinkCardModalButton);
        new FluentWait<>(button)
                .withTimeout(Duration.ofSeconds(5))
                .until(b -> !b.getText().isEmpty());
        return button;
    }

    public WebElement getCloseAccountModal() {
        return browser.findElement(closeAccountModalButton);
    }

    public List<WebElement> getTransactionHistoryRows() {
        return browser.findElements(transactionHistoryRows);
    }

    public List<TransactionRow> getTransactionRows() {
        List<TransactionRow> transactionRowList = new ArrayList<>();

        for (WebElement row : getTransactionHistoryRows()) {
            TransactionRow transactionRow = parseTransactionRow(row);
            transactionRowList.add(transactionRow);
        }

        return transactionRowList;
    }

    public TransactionRow getTransactionRow(int index) {
        WebElement row = getTransactionHistoryRows().get(index);
        return parseTransactionRow(row);
    }

    private TransactionRow parseTransactionRow(WebElement row) {
        List<String> rowCells = row.findElements(transactionHistoryRowCells.getBy()).stream()
                .map(WebElement::getText).collect(Collectors.toList());

        return new TransactionRow(rowCells);
    }

    public SecuritizeIdDashboard clickGoBack() {
        browser.click(goBackArrowButton);
        return new SecuritizeIdDashboard(browser);
    }

    public boolean isMigrationModalPresent() {
        return !browser.findElements(migrationModalBody).isEmpty();
    }

    public SecuritizeIdCashAccountPage refreshPage() {
        browser.refreshPage();

        return new SecuritizeIdCashAccountPage(browser);
    }

    public boolean isUnlinkBankLinkPresent() {
        return browser.isElementPresentOrVisible(unlinkAccountLink);
    }

    public void clickUnlinkLink() {
        browser.click(unlinkAccountLink, false);
        browser.switchToLatestWindow();
    }

    public void clickUnlinkAccountButton() {
        browser.click(unlinkAccountButton);
    }
}
