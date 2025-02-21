package io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.securitize.infra.reporting.MultiReporter.addScreenshot;

public class SecuritizeIdInvestorKyc04IndividualLegalInformation extends SecuritizeIdInvestorAbstractPage {

    public static final ExtendedBy countryOfBirthSelector = new ExtendedBy("Country of birth selector", By.xpath("//label[@for='wizard-legal-country']/../div"));
    private static final String countryOfBirthValuePlaceHolder = "//div[@role = 'option' and (text()='%s')]";

    public static final ExtendedBy stateOfBirthSelector = new ExtendedBy("State of birth selector", By.xpath("//label[@for='wizard-legal-birth-state']/../div"));
    private static final String stateOfBirthValuePlaceHolder = "//div[@role = 'option' and (text()='%s')]";

    public static final ExtendedBy cityOfBirthField = new ExtendedBy("City of birth field", By.id("wizard-legal-birth-city"));
    private static final ExtendedBy countryOfTaxSelector = new ExtendedBy("Country of tax selector", By.xpath("//label[@for='tax-country-code0']/../div"));
    public static final ExtendedBy taxIdField = new ExtendedBy("Tax id field", By.id("tax-country-id0"));

    public static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 05 - continue button", By.id("legal-info-wizard-continue"));

    public SecuritizeIdInvestorKyc04IndividualLegalInformation(Browser browser) {
        super(browser, cityOfBirthField);
    }

    public SecuritizeIdInvestorKyc04IndividualLegalInformation selectCountryOfBirth(String countryName) {
        browser.click(countryOfBirthSelector, false); // open the list of countries
        String fullXpathSelector = String.format(countryOfBirthValuePlaceHolder, countryName);
        ExtendedBy countryValueSelector = new ExtendedBy(countryName + " country Selector", By.xpath(fullXpathSelector));
        browser.click(countryValueSelector);
        return this;
    }

    public SecuritizeIdInvestorKyc04IndividualLegalInformation selectStateOfBirth(String stateName) {
        if (browser.findElementsQuick(stateOfBirthSelector, 2).size() > 0) {
            browser.click(stateOfBirthSelector, false); // open the list of states
            String fullXpathSelector = String.format(stateOfBirthValuePlaceHolder, stateName);
            ExtendedBy stateValueSelector = new ExtendedBy(stateName + " state Selector", By.xpath(fullXpathSelector));
            browser.click(stateValueSelector);
        } else {
            addScreenshot("No state selector is visible, skipping it...");
        }
        return this;
    }

    @SuppressWarnings("unused")
    public SecuritizeIdInvestorKyc04IndividualLegalInformation selectCountryOfTax(String countryName) {
        if (!browser.getElementAttribute(countryOfTaxSelector, "class").toLowerCase().contains("is-disabled")) {
            browser.click(countryOfTaxSelector, false); // open the list of countries
            String fullXpathSelector = String.format(countryOfBirthValuePlaceHolder, countryName);
            ExtendedBy stateValueSelector = new ExtendedBy(countryName + " country Selector", By.xpath(fullXpathSelector));
            browser.click(stateValueSelector);
        } else {
            MultiReporter.addScreenshot("Country of tax field isn't enabled, skipping it...");
        }
        return this;
    }

    public SecuritizeIdInvestorKyc04IndividualLegalInformation typeCityOfBirth(String city) {
        browser.typeTextElement(cityOfBirthField, city);
        return this;
    }

    public SecuritizeIdInvestorKyc04IndividualLegalInformation typeTaxId(String taxId) {
        browser.typeTextElement(taxIdField, taxId);
        return this;
    }

    public void clickContinue() {
        WebElement continueButtonElement = browser.findElement(continueButton);
        browser.click(continueButton, false);
        // Wait for page to be redirected when we finish the KYC
        browser.waitForElementStalenes(continueButtonElement);
    }
}
