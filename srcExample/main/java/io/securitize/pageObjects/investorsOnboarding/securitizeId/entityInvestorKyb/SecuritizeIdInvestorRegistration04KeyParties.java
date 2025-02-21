package io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class SecuritizeIdInvestorRegistration04KeyParties extends SecuritizeIdInvestorAbstractPage {

    /* Individual */
    private static final ExtendedBy firstNameField = new ExtendedBy("First name field", By.id("new-legal-signer-wizard-signer-first-name"));
    private static final ExtendedBy middleNameField = new ExtendedBy("Middle name field", By.id("new-legal-signer-wizard-signer-middle-name"));
    private static final ExtendedBy lastNameField = new ExtendedBy("Last name field", By.id("new-legal-signer-wizard-signer-last-name"));
    private static final ExtendedBy taxIDField = new ExtendedBy("Tax ID field", By.id("new-legal-signer-wizard-signer-tax-id"));
    private static final ExtendedBy countryPayTaxSelector = new ExtendedBy("Entity type selector", By.xpath("((//*[contains (text(), 'e.g. United States of America')]))[1] | //*[text()= 'Select country']"));
    private static final ExtendedBy selectCountryPayTaxUSA = new ExtendedBy("Country of residence - United States", By.xpath("//*[@role = 'option' and (text()='United States of America')]"));
    private static final ExtendedBy legalSignerEmail = new ExtendedBy("Legal signer email", By.id("new-legal-signer-wizard-signer-email"));
    private static final ExtendedBy beneficialOwnerCheckbox = new ExtendedBy("Legal signer email", By.id("new-legal-signer-isbeneficialowner-signer"));
    private static final ExtendedBy birthDateMonthSelector = new ExtendedBy("Birthdate month Selector", By.xpath("//input[@id='new-legal-signer-month']/../../.."));
    private static final ExtendedBy birthDateDaySelector = new ExtendedBy("Birthdate day Selector", By.xpath("//input[@id='new-legal-signer-day']/../../.."));
    private static final ExtendedBy birthDateYearSelector = new ExtendedBy("Birthdate year Selector", By.xpath("//input[@id='new-legal-signer-year']/../../.."));
    private static final String selectorValuePlaceHolder = "//div[@role = 'option' and (text()='%s')]";
    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 05 - conitnue button", By.id("wizard-continue"));
    /* Entity */
    private static final ExtendedBy entityTab = new ExtendedBy("Entity Tab", By.xpath("//a[text()='Entity']"));
    private static final ExtendedBy entityLegalName = new ExtendedBy("Entity legal name", By.id("new-legal-entity-signer-wizard-signer-legal-name"));
    private static final ExtendedBy entityDoingBusinessAs = new ExtendedBy("Entity Doing Business As", By.id("new-legal-entity-signer-wizard-signer-business"));
    private static final ExtendedBy entityTaxID_EIN = new ExtendedBy("Tax ID (EIN)", By.id("new-legal-entity-signer-wizard-signer-entity-id"));
    private static final ExtendedBy entityCountryPayTaxSelector = new ExtendedBy("Entity type selector", By.xpath("//input[contains(@id, 'country-code')]/ancestor::div[contains(@class, 'Select-control')]//div[contains(@class, 'Select-placeholder')]"));
    private static final ExtendedBy entitySelectCountryPayTaxUSA = new ExtendedBy("Country of residence - United States", By.xpath("//div[@role = 'option' and (text()='United States of America')]"));
    private static final ExtendedBy entityTypeCombo = new ExtendedBy("Entity Type Combo", By.id("new-legal-entity-signer-wizard-signer-entity-type"));
    private static final ExtendedBy legalSignerCheckbox = new ExtendedBy("This individual is a legal signer checkbox", By.name("isLegalSigner"));
    private static final ExtendedBy entityTaxID = new ExtendedBy("Entity Tax ID", By.id("wizard-signer-entity-id"));
    private static final ExtendedBy entityLegalSignerEmail = new ExtendedBy("Entity legal signer email", By.id("new-legal-entity-signer-wizard-signer-entity-email"));
    private static final ExtendedBy keyPartiesListButton = new ExtendedBy("Key Parties List button", By.xpath("//button[contains (text(), 'Key Parties List')]"));
    private static final ExtendedBy subtitle = new ExtendedBy("screen subtitle", By.xpath("//div[@class = 'page-title']/p[contains (@class, 'sub-title')]"));


    public SecuritizeIdInvestorRegistration04KeyParties(Browser browser) {
        super(browser, firstNameField, entityTab);
    }

    /* Individual */

    public SecuritizeIdInvestorRegistration04KeyParties typeFirstName(String firstName) {
        browser.typeTextElement(firstNameField, firstName);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties typeMiddleName(String middleName) {
        browser.typeTextElement(middleNameField, middleName);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties typeLastName(String lastName) {
        browser.typeTextElement(lastNameField, lastName);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties typeTaxID(String taxID) {
        browser.typeTextElement(taxIDField, taxID);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties selectCountryPayTaxUSA() {
        browser.click(countryPayTaxSelector);
        browser.click(selectCountryPayTaxUSA);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties setBirthDate(String birthDate) {
        String birthMonth = DateTimeUtils.convertDateFormat(birthDate, "MM/dd/yyyy", "MMM");
        String birthDay = DateTimeUtils.convertDateFormat(birthDate, "MM/dd/yyyy", "d");
        String birthYear = DateTimeUtils.convertDateFormat(birthDate, "MM/dd/yyyy", "yyyy");

        return this.setBirthMonth(birthMonth)
                .setBirthDay(birthDay)
                .setBirthYear(birthYear);
    }

    private SecuritizeIdInvestorRegistration04KeyParties setBirthMonth(String birthMonth) {
        return birthDateInternalSelector(birthDateMonthSelector, birthMonth);
    }

    private SecuritizeIdInvestorRegistration04KeyParties setBirthDay(String birthDay) {
        return birthDateInternalSelector(birthDateDaySelector, birthDay);
    }

    private SecuritizeIdInvestorRegistration04KeyParties setBirthYear(String birthYear) {
        return birthDateInternalSelector(birthDateYearSelector, birthYear);
    }

    private SecuritizeIdInvestorRegistration04KeyParties birthDateInternalSelector(ExtendedBy birthDatePartSelector, String value) {
        browser.click(birthDatePartSelector, false); // open the relevant list
        String fullXpathSelector = String.format(selectorValuePlaceHolder, value);
        ExtendedBy valueSelector = new ExtendedBy(value + " birth part Selector", By.xpath(fullXpathSelector));
        browser.click(valueSelector);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties insertEmail() {
        browser.typeTextElement(legalSignerEmail, "daniel+legalSigner@securitize.io");
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties beneficialOwnerSelect() {
        browser.click(beneficialOwnerCheckbox);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties legalSignerSelect() {
        browser.click(legalSignerCheckbox);
        return this;
    }

    /* Entity */

    public SecuritizeIdInvestorRegistration04KeyParties switchToEntityTab() {
        browser.click(entityTab);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties typeEntityLegalName(String legalName) {
        browser.typeTextElement(entityLegalName, legalName);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties typeEntityDoingBusinessAs(String entityDBA) {
        browser.typeTextElement(entityDoingBusinessAs, entityDBA);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties typeEntityTaxID_EIN(String taxID) {
        browser.typeTextElement(entityTaxID_EIN, taxID);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties selectEntityCountryPayTaxUSA() {
        Optional<WebElement> countrySelector = browser.findFirstVisibleElementQuick(entityCountryPayTaxSelector, 5);
        if (countrySelector.isPresent()) {
            browser.click(countrySelector.get(), entityCountryPayTaxSelector.getDescription());
            browser.click(entitySelectCountryPayTaxUSA);
        } else {
            errorAndStop("Can't locate country selector. Terminating!", true);
        }
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties pickEntityType(String entityType) {
        browser.selectElementByVisibleText(entityTypeCombo, entityType);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties typeEntityTaxID(String taxID) {
        browser.typeTextElement(entityTaxID, taxID);
        return this;
    }

    public SecuritizeIdInvestorRegistration04KeyParties insertEntityEmail(String email) {
        browser.typeTextElement(entityLegalSignerEmail, email);
        return this;
    }


    /* General */

    public SecuritizeIdInvestorKyb04_1KeyPartiesAddress clickContinue() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorKyb04_1KeyPartiesAddress(browser);
    }

    public void clickContinueReturningVoid() {
        browser.click(continueButton, false);
    }

    public void clickKeyPartiesList() {
        browser.click(keyPartiesListButton, false);
    }

    public String getSubtitleText() {
        return browser.getElementText(subtitle);
    }
}
