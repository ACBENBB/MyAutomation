package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.testng.*;

public class TaxFormW9SeccondPage extends AbstractPage {

    private static final ExtendedBy federalTaxClassificationText = new ExtendedBy("Federal Classification Text Value", By.xpath("//strong[text()='Federal Tax Classification']//parent::span//parent::div//following-sibling::div//span"));
    private static final ExtendedBy nameText = new ExtendedBy("Name Text Value", By.xpath("//strong[text()='Name']//parent::span//parent::div//following-sibling::div//span"));
    private static final ExtendedBy countryText = new ExtendedBy("Country Text Value", By.xpath("//strong[text()='Country']//parent::span//parent::div//following-sibling::div//span"));
    private static final ExtendedBy addressText = new ExtendedBy("Address Text Value", By.xpath("//strong[text()='Address']//parent::span//parent::div//following-sibling::div//span"));
    private static final ExtendedBy stateRegionText = new ExtendedBy("State/Region Text Value", By.xpath("//strong[text()='State / Region']//parent::span//parent::div//following-sibling::div//span"));
    private static final ExtendedBy cityText = new ExtendedBy("City Text Value", By.xpath("//strong[text()='City']//parent::span//parent::div//following-sibling::div//span"));
    private static final ExtendedBy zipText = new ExtendedBy("Zip Text Value", By.xpath("//strong[text()='Zip']//parent::span//parent::div//following-sibling::div//span"));
    private static final ExtendedBy ssnText = new ExtendedBy("SSN Text Value", By.xpath("//strong[text()='SSN']//parent::div//parent::div//following-sibling::div//span"));

    private static final ExtendedBy certificationCheckBox = new ExtendedBy("Certification CheckBox", By.xpath("//input[@id='agree']"));
    private static final ExtendedBy certificationText = new ExtendedBy("Certification Text", By.xpath("//label[@for='agree']"));

    private static final ExtendedBy signatureAgreementText = new ExtendedBy("Signature Agreement Text", By.xpath("//strong[text()='Name']//parent::span//parent::div//following-sibling::div//span"));
    private static final ExtendedBy signatureInputText = new ExtendedBy("Signature Text", By.xpath("//label[text()='Name']//span"));
    private static final ExtendedBy signatureInputBox = new ExtendedBy("Signature Input Box", By.xpath("//input[@name='nameConfirmation']"));

    private static final ExtendedBy dateInputBoxAutoComplete = new ExtendedBy("Tax Form W9 Date Text Box", By.xpath("//input[@id='formDate']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Tax Form W9 Submit Button", By.xpath("//button[@name='w9-submit']"));

    public TaxFormW9SeccondPage(Browser browser) {
        super(browser);
    }

    public String getNameText() {
        return browser.findElement(nameText).getText();
    }

    public String getFederalTaxClassificationText() {
        return browser.findElement(federalTaxClassificationText).getText();
    }

    public String getCountryText() {
        return browser.findElement(countryText).getText();
    }

    public String getAddressText() {
        return browser.findElement(addressText).getText().trim();
    }

    public String getStateRegionText() {
        return browser.findElement(stateRegionText).getText();
    }

    public String getCityText() {
        return browser.findElement(cityText).getText();
    }

    public String getZipText() {
        return browser.findElement(zipText).getText();
    }

    public String getSsnText() {
        return browser.findElement(ssnText).getText();
    }

    public void clickCertificationCheckbox() {
        browser.click(certificationText);
    }

    public String getCertificationText() {
        return browser.findElement(certificationText).getText();
    }

    public String getSignatureAgreementText() {
        return browser.findElement(signatureAgreementText).getText();
    }

    public String getSignatureInputText() {
        String signatureRaw = browser.findElement(signatureInputText).getText();
        return signatureRaw.substring(1, signatureRaw.length() -1);
    }

    public void setSignatureInputBox(String signature) {
        browser.findElement(signatureInputBox).sendKeys(signature);
    }

    public String getDateInputBoxText() {
        return browser.getElementAttribute(dateInputBoxAutoComplete, "value");
    }

    public void clickSubmit() {
        browser.click(submitButton);
    }

    public boolean isSubmitBtnEnabled() {
        return browser.findElement(submitButton).isEnabled();
    }

    public void validateTaxIdFormat(String taxIdActual, String taxIdExpected) {
        String lastFourTaxIdNumbers = taxIdExpected.substring(taxIdExpected.length() - 4);
        String finalTaxIdStringFormat = "***-**-" + lastFourTaxIdNumbers;
        Assert.assertEquals(taxIdActual, finalTaxIdStringFormat);
    }

}