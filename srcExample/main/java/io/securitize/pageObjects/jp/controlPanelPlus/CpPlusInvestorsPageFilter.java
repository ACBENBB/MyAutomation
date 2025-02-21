package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

// 投資家一覧 フィルター
public class CpPlusInvestorsPageFilter extends AbstractJpPage {
    private static final ExtendedBy filterToggle = new ExtendedBy("Filter Toggle", By.xpath("//*[text()[contains(.,'フィルタ')]]"));
    private static final ExtendedBy investorInput = new ExtendedBy("Investor Input", By.xpath("//*[@name='q']"));
    private static final ExtendedBy confirmationStatusSelect = new ExtendedBy("Confirmation Status Select", By.xpath("//*[@name='kycConfirmationStatus']"));
    private static final ExtendedBy kycConfirmationSelect = new ExtendedBy("KYC Confirmation Select", By.xpath("//*[@name='kycConfirmationCase']"));
    private static final ExtendedBy bankAccountStatusSelect = new ExtendedBy("Bank Account Status Select", By.xpath("//*[@name='bankConfirmationStatus']"));
    private static final ExtendedBy approvalStatusSelect = new ExtendedBy("Approval Status Select", By.xpath("//*[@name='compatibilityConfirmationStatus']"));
    private static final ExtendedBy clearAll = new ExtendedBy("Clear All", By.xpath("//*[@name='compatibilityConfirmationStatus']"));

    public CpPlusInvestorsPageFilter(Browser browser) {
        super(browser, filterToggle);
    }

    public void clickFilterIfNotOpen() {
        if (browser.isElementEnabled(investorInput)) {
            browser.clickWithJs(browser.findElement(filterToggle));
        }
    }

    /**
     * filter investors using email address. as filter operation can fail (not so often) retry has been added.
     * as a result of filter, only 1 investor is expected to be found.
     */
    public boolean filterInvestorWith(String emailAddress) {
        CpPlusInvestorsPage investorsPage = new CpPlusInvestorsPage(browser);
        RetryOnExceptions.retryFunction(
                ()-> {
                    clickFilterIfNotOpen();
                    enterInvestorInput(emailAddress);
                    browser.waitForPageStable();
                    investorsPage.waitUntilNumberOfInvestorsInTableBecomes(1);
                    return null;
                },
                ()-> {browser.refreshPage(); return null;},
                true
        );
        return investorsPage.numberOfInvestorRowsInTable() == 1;
    }

    public void clickConfirmationStatusSelect(String value) {
        browser.selectElementByVisibleText(confirmationStatusSelect, value);
    }

    public void clickKycConfirmationSelect(String value) {
        browser.selectElementByVisibleText(kycConfirmationSelect, value);
    }

    public void clickBankAccountStatusSelect(String value) {
        browser.selectElementByVisibleText(bankAccountStatusSelect, value);
    }

    public void clickApprovalStatusSelect(String value) {
        browser.selectElementByVisibleText(approvalStatusSelect, value);
    }

    public void clickClearAll() {
        browser.click(clearAll);
    }

    public void enterInvestorInput(String value) {
        browser.typeTextElement(investorInput, value);
    }
}
