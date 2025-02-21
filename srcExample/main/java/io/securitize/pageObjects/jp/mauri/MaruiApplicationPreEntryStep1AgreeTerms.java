package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiApplicationPreEntryStep1AgreeTerms extends AbstractJpPage {

    public MaruiApplicationPreEntryStep1AgreeTerms(Browser browser) {
        super(browser, agreeToTermsCheckbox);
    }

    // Step 1 お申込みにあたってのご確認
    private static final ExtendedBy step01Header = new ExtendedBy("Step 01 Head Line", By.xpath("//h1[text()[contains(., 'お申込みにあたってのご確認')]]"));
    private static final ExtendedBy agreeToTermsCheckbox = new ExtendedBy("Agree to Terms Checkbox", By.xpath("//*[contains(@class,'form-check-input')]"));
    private static final ExtendedBy nextStepButton = new ExtendedBy("Next Step Button", By.xpath("//button"));

    public MaruiApplicationPreEntryStep1AgreeTerms clickPurchaseRequestAgreeCheckbox() {
        browser.click(agreeToTermsCheckbox);
        return this;
    }

    public MaruiApplicationPreEntryStep2Document clickNextStepButton() {
        browser.clickWithJs(browser.findElement(nextStepButton));
        browser.waitForElementToVanish(step01Header);
        return new MaruiApplicationPreEntryStep2Document(browser);
    }

    public MaruiApplicationPreEntryStep2Document agreeToTermsAndClickNextButton() {
        if (!browser.findElement(agreeToTermsCheckbox).isSelected()) {
            browser.click(agreeToTermsCheckbox);
        }
        browser.clickWithJs(browser.findElement(nextStepButton));
        if (browser.isElementVisibleQuick(step01Header, 5)) {
            browser.waitForElementToVanish(step01Header);
        }
        return new MaruiApplicationPreEntryStep2Document(browser);
    }

    public MaruiApplicationPreEntryStep2Document agreeToTermsAndClickNextButton(boolean retry) {
        String url = getMaruiOpportunitiesUrl() + "/pre-entry/description";
        return retryFunctionWithRefreshPageAndNavigateToUrl(this::agreeToTermsAndClickNextButton, url, retry);
    }
}
