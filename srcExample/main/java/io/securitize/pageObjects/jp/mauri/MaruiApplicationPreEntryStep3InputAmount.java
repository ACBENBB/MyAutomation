package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

import java.time.Duration;

public class MaruiApplicationPreEntryStep3InputAmount extends AbstractJpPage {

    public MaruiApplicationPreEntryStep3InputAmount(Browser browser) {
        super(browser, amountInputField);
    }

    // Step 3 抽選申込
    private static final ExtendedBy amountInputField = new ExtendedBy("Amount Input Field", By.xpath("//input[@class='form-control']"));
    private static final ExtendedBy amountYenText = new ExtendedBy("Amount Yen Display Field", By.xpath("//*[@class='row' and contains(., 'お申し込み金額')]/div[2]"));
    private static final ExtendedBy nextStepButton = new ExtendedBy("Next Step Button", By.xpath("//button/*[text()[contains(.,'次のステップに進む')]]"));
    private static final ExtendedBy backStepButton = new ExtendedBy("Back Step Button", By.xpath("//button/*[text()[contains(.,'戻る')]]"));

    public void enterAmountInputField(String amount) {
        if (browser.isElementVisibleQuick(amountInputField, 5)) {
            browser.typeTextElement(amountInputField, amount);
        }
    }

    public MaruiApplicationPreEntryStep4Confirm clickNextStepButton() {
        browser.click(nextStepButton);
        if (browser.isElementVisible(amountInputField)) {
            browser.waitForElementToVanish(amountInputField);
        }
        return new MaruiApplicationPreEntryStep4Confirm(browser);
    }

    public MaruiApplicationPreEntryStep4Confirm enterAmountAndClickNextStepButton(String amount) {
        boolean retryOnStaleElementException = true;
        RetryOnExceptions.retryOnStaleElement(()-> {enterAmountInputField(amount); return null;}, retryOnStaleElementException);
        if (browser.isElementVisibleQuick(nextStepButton, 5)) {
            browser.click(nextStepButton);
        }
        if (browser.isElementVisibleQuick(amountInputField, 5)) {
            browser.waitForElementToVanish(amountInputField);
        }
        return new MaruiApplicationPreEntryStep4Confirm(browser);
    }

    public MaruiApplicationPreEntryStep4Confirm enterAmountAndClickNextStepButton(String amount, boolean retry) {
        browser.waitForPageStable(Duration.ofSeconds(3));
        String url = getMaruiOpportunitiesUrl() + "/pre-entry/form";
        return retryFunctionWithRefreshPageAndNavigateToUrl(()-> enterAmountAndClickNextStepButton(amount), url, retry);
    }

    public void clickBackButton() {
        browser.click(backStepButton);
    }

    public String getAmountYenText() {
        return browser.getElementText(amountYenText);
    }
}
