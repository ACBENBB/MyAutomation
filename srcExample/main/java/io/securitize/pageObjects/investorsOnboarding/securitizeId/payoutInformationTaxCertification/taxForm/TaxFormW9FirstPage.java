package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.*;

public class TaxFormW9FirstPage extends AbstractPage {

    private static final ExtendedBy individualNameInput = new ExtendedBy("Individual Name Input Box", By.id("formInvestorName"));
    private static final ExtendedBy businessNameInput = new ExtendedBy("Entity Name Input Box", By.id("businessName"));

    private static final ExtendedBy streetAddressInput = new ExtendedBy("Street Address Input Box", By.id("address1"));
    private static final ExtendedBy additionalInformationInput = new ExtendedBy("Additional Information Input Box", By.id("address2"));
    private static final ExtendedBy cityInput = new ExtendedBy("City Input Box", By.id("city"));
    private static final ExtendedBy postalCodeInput = new ExtendedBy("Postal Code Input Box", By.id("postalCode"));
    private static final ExtendedBy stateRegionDropdown = new ExtendedBy("State Region Dropdown", By.name("region"));

    private static final ExtendedBy federalTaxClasificationDropdown = new ExtendedBy("Individual Federal Tax Classification Dropdown Option", By.xpath("//select[@name='formClassification']"));
    private static final ExtendedBy ssnTaxIdentifierType = new ExtendedBy("SSN Tax Identifier Type", By.xpath("//select[@name='taxPayerIdType']"));
    private static final ExtendedBy taxpayerIdNumberInput = new ExtendedBy("Taxpayer ID Number", By.id("taxPayerIdNumber"));

    private static final ExtendedBy w9ContinueBtn = new ExtendedBy("W9 Continue Button", By.xpath("//button[@name='w9-submit']"));

    public TaxFormW9FirstPage(Browser browser) {
        super(browser);
    }


    public String getIndividualNameInputAutoComplete() {
        return browser.findElement(individualNameInput).getAttribute("value");
    }

    public void setEntityName(String entityName) {
        browser.findElement(individualNameInput).sendKeys(entityName);
    }

    public String getEntityBusinessName() {
        return browser.findElement(businessNameInput).getAttribute("value");
    }

    public void setEntityFederalTaxClassification(String option) {
        browser.selectElementByVisibleText(federalTaxClasificationDropdown, "Sole Proprietor");
    }

    public void setEntityTaxIdentifierType(String option) {
        browser.selectElementByVisibleText(ssnTaxIdentifierType, "Social Security Number (SSN)");
    }

    public String getStreetNameAutoComplete() {
        return browser.findElement(streetAddressInput).getAttribute("value").trim();
    }

    public String getCityAutoComplete() {
        return browser.findElement(cityInput).getAttribute("value");
    }

    public String getPostalCodeAutoComplete() {
        return browser.findElement(postalCodeInput).getAttribute("value");
    }

    public String getStateRegionAutoComplete() {
        Select select = new Select(browser.findElement(stateRegionDropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    public String getFederalTaxClassification() {
        Select select = new Select(browser.findElement(federalTaxClasificationDropdown));
        WebElement option = select.getFirstSelectedOption();
        return option.getText().trim();
    }

    public String getTaxIdentifierTypeAutoComplete() {
        Select select = new Select(browser.findElement(ssnTaxIdentifierType));
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }

    public void setTaxPayerIdNumber(String taxPayerId) {
        browser.sendKeysElement(taxpayerIdNumberInput, taxPayerId, taxPayerId);
    }

    public String geTaxPayerIdNumber() {
        WebElement element = browser.findElement(taxpayerIdNumberInput);
        return browser.getElementAttribute(taxpayerIdNumberInput, "value");
    }

    public void clickContinueBtn() {
        browser.click(w9ContinueBtn);
    }

    public void validateTaxIdFormat(String taxIdActual, String taxIdExpected) {
        String lastFourTaxIdNumbers = taxIdExpected.substring(taxIdExpected.length() - 4);
        String finalTaxIdStringFormat = "***-**-" + lastFourTaxIdNumbers;
        Assert.assertEquals(taxIdActual, finalTaxIdStringFormat);
    }

}