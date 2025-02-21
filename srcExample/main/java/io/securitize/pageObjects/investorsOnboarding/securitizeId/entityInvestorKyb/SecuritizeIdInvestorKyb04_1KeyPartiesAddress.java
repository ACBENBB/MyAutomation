package io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorKyb04_1KeyPartiesAddress extends SecuritizeIdInvestorAbstractPage {

    private static final ExtendedBy streetNameField = new ExtendedBy("Street name field", By.id("wizard-address-street"));
    private static final ExtendedBy streetNumberField = new ExtendedBy("Street number field", By.id("wizard-address-house-number"));
    private static final ExtendedBy streetAdditionalInfoField = new ExtendedBy("Street additional info field", By.id("wizard-address-entrance"));
    private static final ExtendedBy cityField = new ExtendedBy("City field", By.id("wizard-address-city"));
    private static final ExtendedBy zipCodeField = new ExtendedBy("Zip code field", By.id("wizard-address-zip"));
    private static final ExtendedBy countryOfResidenceSelector = new ExtendedBy("Country of residence selector", By.xpath("//*[text()= 'e.g. United States of America'] | //*[text() = 'Select country']"));
    private static final ExtendedBy selectCountryOfResidenceUSA = new ExtendedBy("Country of residence - United States", By.xpath("//div[@role = 'option' and (text()='United States of America')]"));
    private static final ExtendedBy stateOfResidenceSelector = new ExtendedBy("State of residence selector", By.xpath("//*[text()='e.g. New York'] | //*[text() = 'Select state']"));
    private static final ExtendedBy selectStateOfResidenceNewYork = new ExtendedBy("State of residence - New York", By.xpath("//div[@role = 'option' and (text()='New York')]"));

    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 05.1 - continue button", By.id("wizard-continue"));

    SecuritizeIdInvestorKyb04_1KeyPartiesAddress(Browser browser) {
        super(browser, streetNameField);
    }

    public SecuritizeIdInvestorKyb04_1KeyPartiesAddress typeStreetName(String name) {
        browser.typeTextElement(streetNameField, name);
        return this;
    }


    public SecuritizeIdInvestorKyb04_1KeyPartiesAddress typeStreetNumber(String number) {
        browser.typeTextElement(streetNumberField, number);
        return this;
    }


    public SecuritizeIdInvestorKyb04_1KeyPartiesAddress typeStreetAdditionalInfo(String info) {
        browser.typeTextElement(streetAdditionalInfoField, info);
        return this;
    }


    public SecuritizeIdInvestorKyb04_1KeyPartiesAddress typeCity(String city) {
        browser.typeTextElement(cityField, city);
        return this;
    }


    public SecuritizeIdInvestorKyb04_1KeyPartiesAddress typeZipCode(String zipCode) {
        browser.typeTextElement(zipCodeField, zipCode);
        return this;
    }


    public SecuritizeIdInvestorKyb04_1KeyPartiesAddress SetCountryOfResidenceUSA_NY(){
        browser.click(countryOfResidenceSelector);
        browser.click(selectCountryOfResidenceUSA);
        browser.click(stateOfResidenceSelector);
        browser.click(selectStateOfResidenceNewYork);
        return this;
    }

    public SecuritizeIdInvestorRegistration04_2KeyPartiesIdentityDocuments clickContinue() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorRegistration04_2KeyPartiesIdentityDocuments(browser);
    }
}
