package io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdCreateAccountRegistrationStep2 extends AbstractPage<SecuritizeIdCreateAccountRegistrationStep2> {

    public static final ExtendedBy countryOfResidenceSelector = new ExtendedBy("Securitize Id - Create account registration step 2- Country of residence selector", By.xpath("//label[text()='Country of residence']/..//div[contains(@class, 'Select-placeholder') or contains(@id, 'react-select')]"));
    public static final ExtendedBy stateOfResidenceSelector = new ExtendedBy("Securitize Id - Create account registration step 2 - State of residence selector", By.xpath("//label[text()='State']/..//div[contains(@class, 'Select-placeholder') or contains(@id, 'react-select')]"));
    private static final String countryOfResidenceXpathTemplate = "//div[@role = 'option' and (text()='%s')]";
    private static final String stateOfResidenceXpathTemplate = "//div[@role = 'option' and (text()='%s')]";
    public static final ExtendedBy phoneNumber = new ExtendedBy("Securitize Id - Create account registration step 2- Insert phone number", By.id("phone"));

    public static final ExtendedBy nextButton = new ExtendedBy("Securitize Id - Create account registration step 1- Next button", By.id("registration-next"));


    public SecuritizeIdCreateAccountRegistrationStep2(Browser browser) {
        super(browser, countryOfResidenceSelector);
    }

    public SecuritizeIdCreateAccountRegistrationStep2 setCountryOfResidence(String country) {
        browser.click(countryOfResidenceSelector, false);

        String locatorXpath = String.format(countryOfResidenceXpathTemplate, country);
        ExtendedBy locator = new ExtendedBy("Country of residence: " + country, By.xpath(locatorXpath));

        browser.click(locator);
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep2 setStateOfResidence(String state) {
        if (state == null) {
            return this;
        }

        browser.click(stateOfResidenceSelector, false);

        String locatorXpath = String.format(stateOfResidenceXpathTemplate, state);
        ExtendedBy locator = new ExtendedBy("State of residence: " + state, By.xpath(locatorXpath));

        browser.click(locator);
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep2 setCountryAndState(String country, String state) {
        setCountryOfResidence(country);
        if(country.equalsIgnoreCase("United States of America")){
             setStateOfResidence(state);
        }
            return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep2 insertPhoneNumber(String value) {
        browser.typeTextElement(phoneNumber, value);
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep3 clickNextButton() {
        browser.click(nextButton,false);
        return new SecuritizeIdCreateAccountRegistrationStep3(browser);
    }

}