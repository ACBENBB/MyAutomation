package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsAccreditationForm extends AbstractPage {
    private static final ExtendedBy agreementSection = new ExtendedBy("Agreements", By.xpath("//div[@class='Agreements form-group']"));
    private static final ExtendedBy agreementCheckbox = new ExtendedBy("Agreement checkbox", By.id("AgreementCheckbox"));
    private static final ExtendedBy submitOrderButton = new ExtendedBy("Submit order button", By.xpath("//button[contains(text(),'Submit order')]"));
    private static final ExtendedBy cancelButton = new ExtendedBy("cancel button", By.xpath("//button[contains(text(),'Cancel')]"));


    public TradingSecondaryMarketsAccreditationForm(Browser browser) { super(browser, agreementSection); }

    public TradingSecondaryMarketsAccreditationForm clickAgreementCheckbox() {
        browser.click(agreementCheckbox, false);
        return this;
    }
    public void clickSubmitOrderButton() {
        browser.click(submitOrderButton, false);
    }
}
