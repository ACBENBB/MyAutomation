package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpAddInvestor extends AbstractPage {

    private static final ExtendedBy firstNameField = new ExtendedBy("first name field", By.name("firstName"));
    private static final ExtendedBy middleNameField = new ExtendedBy("middle name field", By.name("middleName"));
    private static final ExtendedBy lastNameField = new ExtendedBy("last name field", By.name("lastName"));
    private static final ExtendedBy emailField = new ExtendedBy("email field", By.name("email"));
    private static final ExtendedBy countrySelector = new ExtendedBy("country selector", By.name("countryCode"));
    private static final ExtendedBy stateSelector = new ExtendedBy("state selector", By.name("state"));
    private static final ExtendedBy custodianSelector = new ExtendedBy("custodian selector", By.name("custodianId"));


    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[contains(text(), 'OK')]"));


    CpAddInvestor(Browser browser) {
        super(browser, firstNameField);
    }

    public CpAddInvestor typeFirstName(String firstName) {
        browser.typeTextElement(firstNameField, firstName);
        return this;
    }

    public CpAddInvestor typeMiddleName(String middleName) {
        browser.typeTextElement(middleNameField, middleName);
        return this;
    }

    public CpAddInvestor typeLastName(String lastName) {
        browser.typeTextElement(lastNameField, lastName);
        return this;
    }

    public CpAddInvestor typeEmail(String email) {
        browser.typeTextElement(emailField, email);
        return this;
    }

    public CpAddInvestor selectCountry(String value) {
        browser.selectElementByVisibleText(countrySelector, value);
        return this;
    }

    public CpAddInvestor selectCustodian(String value) {
        browser.selectElementByVisibleText(custodianSelector, value);
        return this;
    }

    public String selectRandomState() {
        return browser.selectRandomValueInElement(stateSelector, false);
    }

    public CpInvestorDetailsPage clickOK() {
        browser.clickAndWaitForElementToVanish(okButton);
        return new CpInvestorDetailsPage(browser);
    }
}
