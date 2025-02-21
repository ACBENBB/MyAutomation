package io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorRegistration04_3KeyPartiesCheck extends SecuritizeIdInvestorAbstractPage {

    private static final ExtendedBy addAnotherLegalSignerButton = new ExtendedBy("Add another legal signer button", By.id("wizard-add-signer"));
    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 05.2 - continue button", By.id("wizard-continue"));


    public SecuritizeIdInvestorRegistration04_3KeyPartiesCheck(Browser browser) {
        super(browser, addAnotherLegalSignerButton);
    }

    public SecuritizeIdInvestorRegistration05KeyParties clickContinue() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorRegistration05KeyParties(browser);
    }
}

