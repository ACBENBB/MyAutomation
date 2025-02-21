package io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountPopUps;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.SecuritizeIdInvestorKyb01EntityInformation;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc01IndividualIdentityVerification;
import org.openqa.selenium.By;

public class SecuritizeIdAddEntityAccountPopUp extends AbstractPage {

    private static final ExtendedBy entityNameInputField = new ExtendedBy("Securitize Id - Add Entity Account PopUp - Entity name input field", By.xpath("//input[@placeholder = 'Entity’s Name']"), true);
    public static final ExtendedBy countryOfResidenceSelector = new ExtendedBy("Securitize Id - Add Entity Account PopUp - Country of residence selector", By.xpath("(//div[contains(@id,'react-select-')])[1]"));
    public static final ExtendedBy stateOfResidenceSelector = new ExtendedBy("Securitize Id - Add Entity Account PopUp - State of residence selector", By.xpath("(//div[contains(@id,'react-select-')])[2]"));
    private static final String countryOfResidenceXpathTemplate = "//div[@role = 'option' and (text()='%s')]";
    private static final String stateOfResidenceXpathTemplate = "//div[@role = 'option' and (text()='%s')]";
    private static final ExtendedBy nextButton = new ExtendedBy("Next button", By.xpath("//button[text() = 'Next']"));


    public SecuritizeIdAddEntityAccountPopUp(Browser browser) {
        super(browser, entityNameInputField);
    }

    public SecuritizeIdAddEntityAccountPopUp setEntityName(String entityName) {
        browser.typeTextElement(entityNameInputField, entityName);
        return this;
    }

    public SecuritizeIdAddEntityAccountPopUp setCountryOfResidence(String country) {
        browser.click(countryOfResidenceSelector, false);

        String locatorXpath = String.format(countryOfResidenceXpathTemplate, country);
        ExtendedBy locator = new ExtendedBy("Country of residence: " + country, By.xpath(locatorXpath));

        browser.click(locator);
        return this;
    }

    public SecuritizeIdAddEntityAccountPopUp setStateOfResidence(String state) {
        if (state == null) {
            return this;
        }

        browser.click(stateOfResidenceSelector, false);

        String locatorXpath = String.format(stateOfResidenceXpathTemplate, state);
        ExtendedBy locator = new ExtendedBy("State of residence: " + state, By.xpath(locatorXpath));

        browser.click(locator);
        return this;
    }

    public SecuritizeIdAddEntityAccountPopUp setCountryAndState(String country, String state) {
        setCountryOfResidence(country);
        if (country.equalsIgnoreCase("United States of America")) {
            setStateOfResidence(state);
        }
        return this;
    }

    public SecuritizeIdInvestorKyb01EntityInformation clickNext() {
        browser.click(nextButton, false);
        return new SecuritizeIdInvestorKyb01EntityInformation(browser);
    }

    public SecuritizeIdInvestorKyb01EntityInformation setEntityInformation(String entityName, String country, String state ) {
        setEntityName(entityName);
        setCountryAndState(country, state);
        clickNext();
        return new SecuritizeIdInvestorKyb01EntityInformation(browser);
    }

}
