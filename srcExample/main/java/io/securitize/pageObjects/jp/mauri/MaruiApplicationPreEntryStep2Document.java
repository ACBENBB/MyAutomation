package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiApplicationPreEntryStep2Document extends AbstractJpPage {

    private final String step2Url;

    public MaruiApplicationPreEntryStep2Document(Browser browser) {
        super(browser, step02Header);
        this.step2Url = getMaruiOpportunitiesUrl() + "/pre-entry/documents";
    }

    // Step 2 書類ダウンロード
    private static final ExtendedBy step02Header = new ExtendedBy("Step 02 Head Line", By.xpath("//h1[text()[contains(., '書類ダウンロード')]]"));
    private static final ExtendedBy productDescriptionDocumentLink = new ExtendedBy("Product Description Document Link", By.xpath("//*[contains(@class,'DocumentItem') and contains(., '発行登録目論見書')]/div"));
    private static final ExtendedBy preEntryImportantMattersDocumentLink = new ExtendedBy("PreEntry Important matters Document Link", By.xpath("//*[contains(@class,'DocumentItem') and contains(., '重要説明事項（抽選申込み時）')]/div"));
    private static final ExtendedBy confirmCheckbox = new ExtendedBy("Confirm Checkbox", By.xpath("//*[contains(@class,'form-check-input')]"));
    private static final ExtendedBy nextStepButton = new ExtendedBy("Next Step Button", By.xpath("//button/*[text()[contains(.,'次のステップに進む')]]"));
    private static final ExtendedBy backStepButton = new ExtendedBy("Back Step Button",By.xpath("//button/*[text()[contains(.,'戻る')]]"));

    public void waitUntilProductDescriptionDocumentBecomesClickable() {
        browser.waitForElementClickable(productDescriptionDocumentLink);
    }

    public void waitUntilProductDescriptionDocumentBecomesClickable(boolean retry) {
        retryFunctionWithRefreshPageAndNavigateToUrl(
            ()-> {waitUntilProductDescriptionDocumentBecomesClickable(); return null;}, this.step2Url, retry);
    }

    public void waitUntilPreEntryImportantMattersDocumentBecomesClickable() {
        browser.waitForElementClickable(preEntryImportantMattersDocumentLink);
    }

    public void waitUntilPreEntryImportantMattersDocumentBecomesClickable(boolean retry) {
        retryFunctionWithRefreshPageAndNavigateToUrl(
            ()-> {waitUntilPreEntryImportantMattersDocumentBecomesClickable(); return null;}, this.step2Url, retry);
    }

    public void waitUntilAllDocumentsBecomeVisible(boolean retry) {
        waitUntilProductDescriptionDocumentBecomesClickable(retry);
        waitUntilPreEntryImportantMattersDocumentBecomesClickable(retry);
    }

    public MaruiApplicationPreEntryStep2Document clickProductDescriptionDocumentLink() {
        browser.click(productDescriptionDocumentLink);
        browser.waitForNumbersOfTabsToBe(2);
        browser.waitForPageStable();
        browser.closeLastTabAndSwitchToPreviousOne();
        browser.waitForNumbersOfTabsToBe(1);
        return this;
    }

    public MaruiApplicationPreEntryStep2Document clickProductDescriptionDocumentLink(boolean retry) {
        return retryFunctionWithRefreshPageAndNavigateToUrl(this::clickProductDescriptionDocumentLink, this.step2Url, retry);
    }

    public MaruiApplicationPreEntryStep2Document clickPreEntryImportantMattersDocumentLink() {
        browser.click(preEntryImportantMattersDocumentLink);
        browser.waitForNumbersOfTabsToBe(2);
        browser.waitForPageStable();
        browser.closeLastTabAndSwitchToPreviousOne();
        browser.waitForNumbersOfTabsToBe(1);
        return this;
    }

    public MaruiApplicationPreEntryStep2Document clickPreEntryImportantMattersDocumentLink(boolean retry) {
        return retryFunctionWithRefreshPageAndNavigateToUrl(this::clickPreEntryImportantMattersDocumentLink, this.step2Url, retry);
    }

    public MaruiApplicationPreEntryStep2Document clickConfirmCheckbox() {
        browser.waitForElementClickable(confirmCheckbox);
        browser.click(confirmCheckbox);
        return this;
    }

    public MaruiApplicationPreEntryStep3InputAmount clickNextStepButton() {
        browser.click(nextStepButton);
        if (browser.isElementVisibleQuick(step02Header, 15)) {
            browser.waitForElementToVanish(step02Header);
        }
        return new MaruiApplicationPreEntryStep3InputAmount(browser);
    }

    public MaruiApplicationPreEntryStep3InputAmount clickNextStepButton(boolean retry) {
        return RetryOnExceptions.retryFunction(this::clickNextStepButton,
                ()-> {
                    browser.refreshPage();
                    browser.navigateTo(this.step2Url);
                    if (!browser.findElement(confirmCheckbox).isSelected()) {
                        browser.waitForElementClickable(confirmCheckbox);
                        browser.click(confirmCheckbox);
                    }
                    return null;
                },
                retry
        );
    }

    public void clickDocumentLinksAndConfirmCheckboxAndNextStepButton() {
        browser.waitForElementClickable(productDescriptionDocumentLink);
        browser.waitForElementClickable(preEntryImportantMattersDocumentLink);
        clickProductDescriptionDocumentLink();
        clickPreEntryImportantMattersDocumentLink();
        clickConfirmCheckbox();
        browser.click(nextStepButton);
        if (browser.isElementVisibleQuick(step02Header, 5)) {
            browser.waitForElementToVanish(step02Header);
        }
    }

    public MaruiApplicationPreEntryStep3InputAmount completeAllSteps() {
        clickDocumentLinksAndConfirmCheckboxAndNextStepButton();
        return new MaruiApplicationPreEntryStep3InputAmount(browser);
    }

    public MaruiApplicationPreEntryStep3InputAmount completeAllSteps(boolean retry) {
        retryFunctionWithRefreshPageAndNavigateToUrl(()-> {clickDocumentLinksAndConfirmCheckboxAndNextStepButton(); return null;}, this.step2Url, retry);
        return retryFunctionWithRefreshPage(()-> new MaruiApplicationPreEntryStep3InputAmount(browser), retry);
    }

}
