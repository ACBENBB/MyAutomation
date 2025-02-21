package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class CpPlusBankDepositFilePage extends AbstractJpPage {

    private static final ExtendedBy createButton = new ExtendedBy("Create Button", By.xpath("//button/*[text()[contains(.,'作成')]]"));
    private static final ExtendedBy okButtonOnCreateDialog = new ExtendedBy("OK Button on Create Dialog", By.xpath("//*[@class='modal-content' and contains(.,'銀行入金用ファイル作成')]//*[text()='作成']"));
    private static final ExtendedBy okButtonOnDeleteDialog = new ExtendedBy("OK Button on Delete Dialog", By.xpath("//*[@class='modal-content' and contains(.,'銀行入金用ファイル削除')]//*[text()='OK']"));
    private static final ExtendedBy interestPaymentCheckbox = new ExtendedBy("Interest Payment Checkbox", By.xpath("//*[@name='isInterestPayment']"));
    private static final ExtendedBy redemptionPaymentCheckbox = new ExtendedBy("Redemption Payment Checkbox", By.xpath("//*[@name='isRedemptionPayment']"));
    private static final ExtendedBy paymentYearMonthSelect = new ExtendedBy("Payment Year-Month Select", By.xpath("//*[@name='paymentYearMonth']"));
    private static final String TEMPLATE_BANK_DEPOSIT_PAYMENT = "//td[text()[contains(.,'%s')]]";

    public CpPlusBankDepositFilePage(Browser browser) {
        super(browser, createButton);
    }

    public CpPlusBankDepositFilePage clickCreateButton() {
        browser.click(createButton);
        return this;
    }

    public CpPlusBankDepositFilePage clickOkButtonOnCreateDialog() {
        browser.click(okButtonOnCreateDialog);
        browser.waitForElementToVanish(interestPaymentCheckbox);
        return this;
    }

    public boolean isBankDepositFileFound(String paymentYearOrMonth) {
        ExtendedBy bankDepositFileName = new ExtendedBy("Bank Deposit File Name", By.xpath(String.format(TEMPLATE_BANK_DEPOSIT_PAYMENT, paymentYearOrMonth)));
        return browser.isElementVisibleQuick(bankDepositFileName, 15);
    }

    public String getStatus(int row) {
        ExtendedBy status = new ExtendedBy("Status", By.xpath(String.format(XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER, row, "ステータス")));
        if (!browser.isElementVisibleQuick(status, 15)) {
           browser.refreshPage();
        }
        return browser.getElementText(status);
    }

    public void clickDownloadButton(int row) {
        ExtendedBy downloadButton = new ExtendedBy("Trash Icon", By.xpath(String.format(XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER, row, "") + "/button[1]"));
        browser.clickWithJs(browser.findElement(downloadButton));
    }

    public void clickTrashIcon(int row) {
        ExtendedBy trash = new ExtendedBy("Trash Icon", By.xpath(String.format(XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER, row, "") + "/button[2]"));
        browser.clickWithJs(browser.findElement(trash));
    }

    public CpPlusBankDepositFilePage selectPaymentYearMonth(String year, String month) {
        browser.selectElementByVisibleText(paymentYearMonthSelect, year + "年" + month + "月");
        return this;
    }

    public CpPlusBankDepositFilePage clickInterestPaymentCheckbox(boolean isInterestPayment) {
        if (isInterestPayment) {
            browser.waitForElementClickable(interestPaymentCheckbox);
            browser.clickWithJs(browser.findElement(interestPaymentCheckbox));
            browser.waitForPageStable();
        }
        return this;
    }

    public CpPlusBankDepositFilePage clickRedemptionPaymentCheckbox(boolean isRedemptionPayment) {
        if (isRedemptionPayment) {
            browser.waitForElementClickable(redemptionPaymentCheckbox);
            browser.clickWithJs(browser.findElement(redemptionPaymentCheckbox));
            browser.waitForPageStable();
        }
        return this;
    }

    public void clickOkButtonOnDeleteDialog() {
        browser.clickWithJs(browser.findElement(okButtonOnDeleteDialog));
        browser.waitForPageStable();
    }

    public void deleteAt(int row) {
        clickTrashIcon(row);
        clickOkButtonOnDeleteDialog();
    }

    public CpPlusBankDepositFilePage createBankDepositFile(String payYear, String payMonth, boolean isInterestPay, boolean isRedemptionPay) {
        CpPlusBankDepositFilePage bankDepositFilePage = clickCreateButton()
                .selectPaymentYearMonth(payYear, payMonth)
                .clickInterestPaymentCheckbox(isInterestPay)
                .clickRedemptionPaymentCheckbox(isRedemptionPay)
                .clickOkButtonOnCreateDialog();
        int atTheTopRow = 1;
        int delayInSeconds = 1;
        int maxRetries = 10;
        if (!getStatus(atTheTopRow).equals("作成完了")) {
            // wait until the status becomes processing first, then wait until the status becomes success.
            waitUntilStatusBecomes(()-> getStatus(atTheTopRow), "作成中", delayInSeconds, maxRetries);
            waitUntilStatusBecomes(()-> getStatus(atTheTopRow), "作成完了", delayInSeconds, maxRetries);
        }
        return bankDepositFilePage;
    }
}
