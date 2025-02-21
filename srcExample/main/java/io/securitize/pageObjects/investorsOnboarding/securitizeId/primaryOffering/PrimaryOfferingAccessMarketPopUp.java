package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class PrimaryOfferingAccessMarketPopUp extends AbstractPage {

    /* IMPORTANT - Dario: This is a particular class, if you created the investor from API, the investor won't be on Market CP, so you'll see the old
    * "Share personal information with Market", but this is not a real scenario anymore. If you create it from the UI, the investor is in Market CP,
    * and then you'll see the pop-up of being redirected. That's why this class is handeling the 2 scenarios */

    private static final ExtendedBy dontShowAgainCheckbox = new ExtendedBy("Don't show message again", By.id("market-dont-show"));
    private static final ExtendedBy goToMarketsButton = new ExtendedBy("Go to Securitize Markets", By.id("market-accept"));
    private static final ExtendedBy agreeMarket = new ExtendedBy("Consent Markets checkbox", By.name("isAgreeMarket"));
    private static final ExtendedBy agreeTnC = new ExtendedBy("Agree Terms and Conditions checkbox", By.name("isAgreeTermsAndConditions"));
    private static final ExtendedBy submitTnC = new ExtendedBy("Go to markets button", By.id("btn-submit"));


    public PrimaryOfferingAccessMarketPopUp(Browser browser) {
        super(browser);
    }

    public PrimaryOfferingAccessMarketPopUp waitForMarketPopUpFromAPI(){
        browser.waitForElementVisibility(submitTnC);
        return this;
    }

    public PrimaryOfferingAccessMarketPopUp waitForMarketPopUpFromUI(){
        browser.waitForElementVisibility(dontShowAgainCheckbox);
        return this;
    }

    public PrimaryOfferingAccessMarketPopUp clickDontShowAgain() {
        browser.click(dontShowAgainCheckbox);
        return this;
    }

    public PrimaryOfferingAccessMarketPopUp clickGoToMarket() {
        browser.click(goToMarketsButton, false);
        return this;
    }

    public PrimaryOfferingAccessMarketPopUp clickAgreeMarket() {
        browser.click(agreeMarket);
        return this;
    }

    public PrimaryOfferingAccessMarketPopUp clickAgreeTnC() {
        browser.click(agreeTnC);
        return this;
    }

    public PrimaryOfferingAccessMarketPopUp clickSubmitTnC() {
        browser.waitForElementClickable(submitTnC, 5);
        browser.click(submitTnC, false);
        return this;
    }

    public void acceptMarketsConsentFromAPI() {
        waitForMarketPopUpFromAPI();
        clickAgreeMarket();
        clickAgreeTnC();
        clickSubmitTnC();
    }

    //Pop up not shown if you go directly to the Market URL
    public void accessMarketWithInvestorCreatedFromAPI() {
        acceptMarketsConsentFromAPI();
        clickDontShowAgain();
        clickGoToMarket();
    }

    public void accessMarketWithInvestorCreatedFromUI() {
        waitForMarketPopUpFromUI();
        clickDontShowAgain();
        clickGoToMarket();
    }
}


