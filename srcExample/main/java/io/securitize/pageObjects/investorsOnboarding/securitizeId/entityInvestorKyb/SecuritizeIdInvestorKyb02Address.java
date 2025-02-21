package io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc03IndividualAddress;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorKyb02Address extends SecuritizeIdInvestorKyc03IndividualAddress {

    private static final ExtendedBy streetNameField = new ExtendedBy("Street name field", By.id("wizard-address-street"));
    private static final ExtendedBy streetNumberField = new ExtendedBy("Street number field", By.id("wizard-address-house-number"));
    private static final ExtendedBy streetAdditionalInfoField = new ExtendedBy("Street additional info field", By.id("wizard-address-entrance"));
    private static final ExtendedBy cityField = new ExtendedBy("City field", By.id("wizard-address-city"));
    private static final ExtendedBy zipCodeField = new ExtendedBy("Zip code field", By.id("wizard-address-zip"));
    private static final ExtendedBy stateSelector = new ExtendedBy("state Selector", By.xpath("//input[@name='state']/../div"));
    private static final String stateValuePlaceHolder = "//div[@role = 'option' and (text()='%s')]";

    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 05 - continue button", By.id("wizard-continue"));

    SecuritizeIdInvestorKyb02Address(Browser browser) {
        super(browser);
    }

    public SecuritizeIdInvestorKyb03EntityDocuments clickContinueButton() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorKyb03EntityDocuments(browser);
    }

    public SecuritizeIdInvestorKyb02Address typeStreetName(String name) {
        browser.typeTextElement(streetNameField, name);
        return this;
    }


    public SecuritizeIdInvestorKyb02Address typeStreetNumber(String number) {
        browser.typeTextElement(streetNumberField, number);
        return this;
    }


    public SecuritizeIdInvestorKyb02Address typeStreetAdditionalInfo(String info) {
        browser.typeTextElement(streetAdditionalInfoField, info);
        return this;
    }


    public SecuritizeIdInvestorKyb02Address typeCity(String city) {
        browser.typeTextElement(cityField, city);
        return this;
    }


    public SecuritizeIdInvestorKyb02Address typeZipCode(String zipCode) {
        browser.typeTextElement(zipCodeField, zipCode);
        return this;
    }

    public SecuritizeIdInvestorKyb02Address selectState(String stateName) {
        browser.click(stateSelector, false); // open the list of states
        String fullXpathSelector = String.format(stateValuePlaceHolder, stateName);
        ExtendedBy stateValueSelector = new ExtendedBy(stateName + " state Selector", By.xpath(fullXpathSelector));
        browser.click(stateValueSelector);
        return this;
    }

    public SecuritizeIdInvestorKyb03EntityDocuments clickContinueEntity() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorKyb03EntityDocuments(browser);
    }
}
