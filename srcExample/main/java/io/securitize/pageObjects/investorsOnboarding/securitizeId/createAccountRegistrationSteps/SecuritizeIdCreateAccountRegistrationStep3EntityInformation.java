package io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdCreateAccountRegistrationStep3EntityInformation extends AbstractPage {
    public static final ExtendedBy securitizeFullLogo = new ExtendedBy("Securitize Id - Create account registration step 3 entity information- Securitize full logo", By.xpath("//img[@alt ='Securitize iD']"));
    public static final ExtendedBy nextButton = new ExtendedBy("Securitize Id -Create account registration step 3 entity information- Next button", By.id("registration-next"));
    public static final ExtendedBy entityNameTextBox = new ExtendedBy("Securitize Id -Create account registration step 3 entity information- Insert entity's name", By.id("registration-entity-name"));
    public static final ExtendedBy entityRegisteredCountrySelector = new ExtendedBy("Securitize Id -Create account registration step 3 entity information- Select entity's registered country", By.className("Select-control"));
    public static final ExtendedBy stateOfResidenceSelector = new ExtendedBy("Securitize Id -Create account registration step 3 entity information- State of residence selector", By.xpath("//label[text()='State']/..//div[contains(@class, 'Select-placeholder') or contains(@id, 'react-select')]"));
    private static final String countryOfResidenceXpathTemplate = "//div[@role = 'option' and (text()='%s')]";
    private static final String stateOfResidenceXpathTemplate = "//div[@role = 'option' and (text()='%s')]";


    public SecuritizeIdCreateAccountRegistrationStep3EntityInformation(Browser browser) {
        super(browser, securitizeFullLogo);
    }

    public SecuritizeIdCreateAccountRegistrationStep3EntityInformation setCountryAndState(String country, String state) {
        setCountryOfResidence(country);
        if(country.equalsIgnoreCase("United States of America")){
            setStateOfResidence(state);
        }
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep3EntityInformation setCountryOfResidence(String country) {
        browser.click(entityRegisteredCountrySelector, false);

        String locatorXpath = String.format(countryOfResidenceXpathTemplate, country);
        ExtendedBy locator = new ExtendedBy("Country of residence: " + country, By.xpath(locatorXpath));

        browser.click(locator);
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep3EntityInformation setStateOfResidence(String state) {
        if (state == null) {
            return this;
        }

        browser.click(stateOfResidenceSelector, false);

        String locatorXpath = String.format(stateOfResidenceXpathTemplate, state);
        ExtendedBy locator = new ExtendedBy("State of residence: " + state, By.xpath(locatorXpath));

        browser.click(locator);
        return this;
    }


    public SecuritizeIdCreateAccountRegistrationStep3EntityInformation insertEntityName(String value) {
        browser.typeTextElement(entityNameTextBox, value);
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep4 clickNextButton() {
        browser.click(nextButton, false);
        return new SecuritizeIdCreateAccountRegistrationStep4(browser);
    }



}