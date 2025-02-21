package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.*;

public class TaxFormW8BENFirstPage extends AbstractPage {

    private static final ExtendedBy nameInput = new ExtendedBy("Name Input Box", By.id("formInvestorName"));
    private static final ExtendedBy countryOfCitizenshipDropdown = new ExtendedBy("Country of Citizenship", By.xpath("//select[@name='countryOfCitizenship']"));

    private static final ExtendedBy federalTaxClassificationText = new ExtendedBy("Federal Classification Text Value", By.xpath("//*[text()='Federal Tax Classification']"));

    private static final ExtendedBy nameText = new ExtendedBy("Name Text Value", By.xpath("//*[text()='Name']"));

    private static final ExtendedBy dobYearDropdown = new ExtendedBy("DOB Year Dropdown", By.xpath("//select[@name='year']"));
    private static final ExtendedBy dobMonthDropdown = new ExtendedBy("DOB Month Dropdown", By.xpath("//select[@name='month']"));
    private static final ExtendedBy dobDayDropdown = new ExtendedBy("DOB Day Dropdown", By.xpath("//select[@name='day']"));

    private static final ExtendedBy streetAddressInput = new ExtendedBy("Street Address Input Box", By.id("address1"));
    private static final ExtendedBy additionalInformationInput = new ExtendedBy("Additional Information Input Box", By.id("address2"));
    private static final ExtendedBy cityInput = new ExtendedBy("City Input Box", By.id("city"));
    private static final ExtendedBy postalCodeInput = new ExtendedBy("Postal Code Input Box", By.id("postalCode"));
    private static final ExtendedBy stateRegionInput = new ExtendedBy("State Region Input Box", By.xpath("//input[@name='region']"));
    private static final ExtendedBy countryDropdown = new ExtendedBy("Country Dropdown", By.xpath("//select[@name='country']"));

    private static final ExtendedBy sameAsPermanentAddressCheckBox = new ExtendedBy("Sames as permanent address checkbox", By.xpath("//input[@name='copyAddressData']"));
    private static final ExtendedBy mailingStreetAddressInput = new ExtendedBy("Mailing Street Address Input Box", By.id("mailingAddress1"));
    private static final ExtendedBy mailingAdditionalInformationInput = new ExtendedBy("Mailing Additional Information Input Box", By.id("mailingAddress2"));
    private static final ExtendedBy mailingCityInput = new ExtendedBy("Mailing City Input Box", By.id("mailingCity"));
    private static final ExtendedBy mailingPostalCodeInput = new ExtendedBy("Mailing Postal Code Input Box", By.id("mailingPostalCode"));
    private static final ExtendedBy mailingStateRegionInput = new ExtendedBy("Mailing State Region Input Box", By.name("mailingRegion"));
    private static final ExtendedBy mailingCountryDropdown = new ExtendedBy("Mailing Country Dropdown", By.xpath("//select[@name='mailingCountry']"));

    private static final ExtendedBy taxPayerIdentificationNumberInput = new ExtendedBy("U.S. taxpayer identification number (SSN or ITIN)", By.xpath("//input[@name='USTaxPayerId']"));
    private static final ExtendedBy foreignTxIdentifyingNumberInput = new ExtendedBy("Foreign Tax Identifying Number", By.xpath("//input[@name='foreignTaxPayerId']"));
    private static final ExtendedBy wontProvideTaxNumberCheckbox = new ExtendedBy("I will not provide a tax id number CHECKBOX", By.xpath("//input[@name='checkTaxPayer']"));
    private static final ExtendedBy wontProvideTaxNumberLabel = new ExtendedBy("I will not provide a tax id number LABEL", By.xpath("//input[@name='checkTaxPayer']//following-sibling::label"));

    private static final ExtendedBy w8BenContinueBtn = new ExtendedBy("W8 Ben Continue Button", By.xpath("//button[@name='w8ben-submit']"));

    public TaxFormW8BENFirstPage(Browser browser) {
        super(browser);
    }

    public String getNameText() {
        return browser.getElementText(nameText);
    }
    public String getFederalTaxClassificationText() {
        return browser.getElementText(federalTaxClassificationText);
    }

    public String getNameInputAutoComplete() {
        return browser.getElementAttribute(nameInput, "value");
    }

    public String getCountryOfCitizenship() {
        Select select = new Select(browser.findElement(countryOfCitizenshipDropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    public String getDOBYearAutoComplete() {
        Select select = new Select(browser.findElement(dobYearDropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    public String getDOBMonthAutoComplete() {
        Select select = new Select(browser.findElement(dobMonthDropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    public String getDOBDayAutoComplete() {
        Select select = new Select(browser.findElement(dobDayDropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    public String getStreetAddressAutoComplete() {
        return browser.getElementAttribute(streetAddressInput, "value").trim();
    }

    public String getCityAutoComplete() {
        return browser.getElementAttribute(cityInput, "value");
    }

    public String getPostalCodeAutoComplete() {
        return browser.getElementAttribute(postalCodeInput, "value");
    }

    public void setStateRegion(String stateRegion) {
        browser.sendKeysElement(stateRegionInput,"State Region", stateRegion);
    }

    public String getAddressCountryAutoComplete() {
        Select select = new Select(browser.findElement(countryDropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    // MAILING ADDRESS CARD
    public boolean isSameAsPermanentAddressCheckboxSelected() {
        return browser.findElement(sameAsPermanentAddressCheckBox).isSelected();
    }

    public String getMailingStreetAddressAutoComplete() {
        return browser.getElementAttribute(mailingStreetAddressInput, "value").trim();
    }

    public String getMailingCityAutoComplete() {
        return browser.getElementAttribute(mailingCityInput, "value");
    }

    public String getMailingPostalCodeAutoComplete() {
        return browser.getElementAttribute(mailingPostalCodeInput, "value");
    }

    public String getMailingStateRegion() {
        return browser.getElementAttribute(mailingStateRegionInput, "value");
    }

    public String getMailingCountryAutoComplete() {
        Select select = new Select(browser.findElement(mailingCountryDropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    public void setTaxPayerIdentificationNumber(String taxId) {
        browser.sendKeysElement(taxPayerIdentificationNumberInput, "Tax ID", taxId);
    }

    public void setForeignTaxIdentifyingNumber(String foreignTaxId) {
        browser.sendKeysElement(foreignTxIdentifyingNumberInput, "Foreign Tax ID", foreignTaxId);
    }

    public void clickIWillNotProvideTaxId() {
        browser.click(wontProvideTaxNumberLabel);
    }

    public void clickContinueBtn() {
        browser.click(w8BenContinueBtn);
    }

    public String getForeignIdentifiyingNumber() {
        return browser.getElementAttribute(foreignTxIdentifyingNumberInput, "value");
    }

    public void validateTaxIdFormat(String taxIdActual, String taxIdExpected) {
        String lastFourTaxIdNumbers = taxIdExpected.substring(taxIdExpected.length() - 4);
        String finalTaxIdStringFormat = "**-***" + lastFourTaxIdNumbers;
        Assert.assertEquals(taxIdActual, finalTaxIdStringFormat);
    }

}