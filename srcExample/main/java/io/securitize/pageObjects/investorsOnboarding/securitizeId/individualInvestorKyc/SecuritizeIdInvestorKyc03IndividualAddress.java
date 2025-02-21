package io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.SecuritizeIdInvestorKyb03EntityDocuments;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorKyc03IndividualAddress extends SecuritizeIdInvestorAbstractPage {

    public static final ExtendedBy streetNameField = new ExtendedBy("Street name field", By.id("wizard-address-street"));
    public static final ExtendedBy streetAdditionalInfoField = new ExtendedBy("Street additional info field", By.id("wizard-address-entrance"));
    public static final ExtendedBy cityField = new ExtendedBy("City field", By.id("wizard-address-city"));
    public static final ExtendedBy zipCodeField = new ExtendedBy("Zip code field", By.id("wizard-address-zip"));
    public static final ExtendedBy phoneNumberField = new ExtendedBy("Phone number field", By.id("phone"));


    public static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 04 - continue button", By.id("wizard-continue"));

    public SecuritizeIdInvestorKyc03IndividualAddress(Browser browser) {
        super(browser, streetNameField);
    }

    public SecuritizeIdInvestorKyc03IndividualAddress typeStreetName(String name) {
        browser.typeTextElement(streetNameField, name);
        return this;
    }

    public String getTypedStreetName() {
        return browser.getElementText(streetNameField);
    }

    public SecuritizeIdInvestorKyc03IndividualAddress typeStreetAdditionalInfo(String info) {
        browser.typeTextElement(streetAdditionalInfoField, info);
        return this;
    }

    public String getTypedStreetAdditionalInfo() {
        return browser.getElementText(streetAdditionalInfoField);
    }

    public SecuritizeIdInvestorKyc03IndividualAddress typeCity(String city) {
        browser.typeTextElement(cityField, city);
        return this;
    }

    public SecuritizeIdInvestorKyc03IndividualAddress typeZipCode(String zipCode) {
        browser.typeTextElement(zipCodeField, zipCode);
        return this;
    }

    public SecuritizeIdInvestorKyc03IndividualAddress typePhoneNumber(String phoneNumber) {
        browser.typeTextElement(phoneNumberField, phoneNumber);
        return this;
    }

    public void clickContinueIndividual() {
        browser.click(continueButton, false);
    }

    public SecuritizeIdInvestorKyb03EntityDocuments clickContinueEntity() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorKyb03EntityDocuments(browser);
    }

}
