package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsAccessMarketPopUp extends AbstractPage {

    private static final ExtendedBy dontShowAgainCheckbox = new ExtendedBy("Don't show message again", By.id("market-dont-show"));
    private static final ExtendedBy goToMarketsButton = new ExtendedBy("Go to Securitize Markets", By.id("market-accept"));
    private static final ExtendedBy agreeMarket = new ExtendedBy("Consent Markets checkbox", By.name("isAgreeMarket"));
    private static final ExtendedBy agreeTnC = new ExtendedBy("Agree Terms and Conditions checkbox", By.name("isAgreeTermsAndConditions"));
    private static final ExtendedBy submitTnC = new ExtendedBy("Go to markets button", By.id("btn-submit"));
    private static final ExtendedBy youAreBeingRedirectedToMarkets = new ExtendedBy("You are being redirected to Securitize Markets - constractor", By.xpath("//*[contains(text(), 'You are being redirected to Securitize Markets')]"));


    public TradingSecondaryMarketsAccessMarketPopUp(Browser browser) {
        super(browser, youAreBeingRedirectedToMarkets);
    }

    public boolean isGoToSecuritizeMarketsBtnVisible() {
        return browser.isElementVisible(goToMarketsButton);
    }

    public TradingSecondaryMarketsAccessMarketPopUp clickDontShowAgain() {
        browser.click(dontShowAgainCheckbox);
        return this;
    }

    public TradingSecondaryMarketsAccessMarketPopUp clickGoToMarket() {
        browser.waitForPageStable();
        browser.click(goToMarketsButton, false);
        return this;
    }

    public TradingSecondaryMarketsAccessMarketPopUp clickAgreeMarket() {
        browser.click(agreeMarket);
        return this;
    }

    public TradingSecondaryMarketsAccessMarketPopUp clickAgreeTnC() {
        browser.click(agreeTnC);
        return this;
    }

    public TradingSecondaryMarketsAccessMarketPopUp clickSubmitTnC() {
        browser.waitForElementClickable(submitTnC, 5);
        browser.click(submitTnC, false);
        return this;
    }

    //Pop up not shown if you go directly to the Market URL
    public void accessMarket() {
        clickDontShowAgain();
        clickGoToMarket();
        // *********  Disabling the consent screen when registering to ATS - remove once this change is permanent.
        /*clickAgreeMarket();
        clickAgreeTnC();
        clickSubmitTnC();*/
    }
 }