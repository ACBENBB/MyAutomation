package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

import static io.securitize.infra.reporting.MultiReporter.info;

public class CpPlusCorporateBondLedgerPage extends AbstractJpPage {

    private static final ExtendedBy createButton = new ExtendedBy("Create Button", By.xpath("//button/*[text()[contains(.,'作成')]]"));
    private static final ExtendedBy okButtonOnCreateDialog = new ExtendedBy("OK Button on Create Dialog", By.xpath("//*[@class='modal-content' and contains(.,'社債原簿作成')]//*[text()='OK']"));
    private static final ExtendedBy okButtonOnDeleteDialog = new ExtendedBy("OK Button on Delete Dialog", By.xpath("//*[@class='modal-content' and contains(.,'社債原簿削除')]//*[text()='OK']"));
    private static final ExtendedBy modalTitleCreate = new ExtendedBy("Modal Title Create", By.xpath("//*[contains(@class,'modal-title') and contains(.,'社債原簿作成')]"));
    private static final ExtendedBy modalTitleDelete = new ExtendedBy("Modal Title Delete", By.xpath("//*[contains(@class,'modal-title') and contains(.,'社債原簿削除')]"));

    public CpPlusCorporateBondLedgerPage(Browser browser) {
        super(browser, createButton);
    }

    public CpPlusCorporateBondLedgerPage clickCreateButton() {
        browser.click(createButton);
        return this;
    }

    public CpPlusCorporateBondLedgerPage clickOkButtonOnCreateDialog() {
        browser.clickAndWaitForElementToVanish(okButtonOnCreateDialog);
        browser.waitForElementToVanish(modalTitleCreate);
        return this;
    }

    public String getCreateDate(int row) {
        ExtendedBy createDate = new ExtendedBy("Create Date", By.xpath(String.format(XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER, row, "作成日時")));
        return browser.getElementText(createDate);
    }

    public String getStatus(int row) {
        ExtendedBy status = new ExtendedBy("Status", By.xpath(String.format(XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER, row, "ステータス")));
        return browser.getElementText(status);
    }

    public String getOperator(int row) {
        ExtendedBy operator = new ExtendedBy("Operator", By.xpath(String.format(XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER, row, "オペレーター")));
        return browser.getElementText(operator);
    }

    public void clickDownloadButton(int row) {
        ExtendedBy download = new ExtendedBy("Trash Icon", By.xpath(String.format(XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER, row, "") + "/button[1]"));
        browser.waitForElementClickable(download);
        browser.clickWithJs(browser.findElement(download));
        browser.waitForPageStable();
    }

    public void clickTrashIcon(int row) {
        String xpath = String.format(XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER, row, "") + "/button[2]";
        ExtendedBy trash = new ExtendedBy("Trash Icon", By.xpath(xpath));
        browser.waitForPageStable();
        browser.waitForElementClickable(trash);
        info("about to click on " + xpath);
        browser.clickWithJs(browser.findElement(trash));
        browser.waitForElementToVanish(modalTitleDelete);
    }

    public void clickTrashIcon(int row, boolean retry) {
        retryFunctionWithRefreshPage(() -> {clickTrashIcon(row); return null;}, retry);
    }

    public void clickOkButtonOnDeleteDialog() {
        browser.waitForPageStable();
        browser.clickWithJs(browser.findElement(okButtonOnDeleteDialog));
    }

    public void deleteAt(int row, boolean retry) {
        clickTrashIcon(row, retry);
        clickOkButtonOnDeleteDialog();
        browser.waitForPageStable();
    }

    public void deleteAllCorporateBondLedgers(CpPlusCorporateBondLedgerPage corporateBondLedgerPage, boolean retry) {
        if (corporateBondLedgerPage.isNoDataTextFound()) {
            info("corporate bond ledger does not exist");
        } else {
            int atTheTopRow = 1;
            int totalNumberOfLedgers = corporateBondLedgerPage.numberOfRowsInTable();
            for(int number=1; number<=totalNumberOfLedgers; number++) {
                info("delete corporate bond ledger " + number + "/" + totalNumberOfLedgers);
                corporateBondLedgerPage.deleteAt(atTheTopRow, retry);
            }
        }
    }

    public CpPlusCorporateBondLedgerPage createCorporateLedger() {
        CpPlusCorporateBondLedgerPage corporateBondLedgerPage = clickCreateButton()
                .clickOkButtonOnCreateDialog();
        int atTheTopRow = 1;
        int delayInSeconds = 3;
        int maxRetries = 60;
        if (!getStatus(atTheTopRow).equals("作成完了")) {
            // wait until the status becomes processing first, then wait until the status becomes success.
            waitUntilStatusBecomes(()-> getStatus(atTheTopRow), "作成中", delayInSeconds, maxRetries);
            waitUntilStatusBecomes(()-> getStatus(atTheTopRow), "作成完了", delayInSeconds, maxRetries);
        }
        return corporateBondLedgerPage;
    }
}
