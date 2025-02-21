package io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountPopUps;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3EntityInformation;
import org.openqa.selenium.By;

public class SecuritizeIdAccountTypePopUp extends AbstractPage {

    private static final ExtendedBy individualAccountTypeField = new ExtendedBy("Securitize Id - Account type popup - Individual account type field", By.xpath("///div[text() = 'Individual']"));
    private static final ExtendedBy entitylAccountTypeField = new ExtendedBy("Securitize Id - Account type popup - Entity account type field", By.xpath("//div[text() = 'Entity']"));

    private static final ExtendedBy nextButton = new ExtendedBy("Securitize Id - Account type popup - Next button", By.xpath("//button[text() = 'Next']"));


    public SecuritizeIdAccountTypePopUp(Browser browser) {
        super(browser);
    }

    public SecuritizeIdAccountTypePopUp clickIndividualAcccoutType() {
        browser.click(individualAccountTypeField, false);
        return this;
    }

    public SecuritizeIdAccountTypePopUp clickEntitylAcccoutType() {
        browser.click(entitylAccountTypeField, false);
        return this;
    }


    public SecuritizeIdAddEntityAccountPopUp clickNext() {
        browser.click(nextButton, false);
        return new SecuritizeIdAddEntityAccountPopUp(browser);
    }

}
