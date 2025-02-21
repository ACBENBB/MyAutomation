package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TaxFormW8BENSeccondPage extends AbstractPage {

    private static final ExtendedBy certificationAgreementCheckBox = new ExtendedBy("Certification Agreement Checkbox", By.xpath("//label[@for='agree']"));
    private static final ExtendedBy signatureInputText = new ExtendedBy("Signature Text", By.xpath("//label[text()='Name']//span"));
    private static final ExtendedBy signatureInputBox = new ExtendedBy("Signature Input Box", By.xpath("//input[@name='nameConfirmation']"));
    private static final ExtendedBy dateInputBoxAutoComplete = new ExtendedBy("Date Input Box Autocomplete Text", By.xpath("//input[@id='formDate']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Tax Form Submit Button", By.xpath("//button[@name='w8ben-submit']"));

    private static final ExtendedBy certifyBeneficialOwner = new ExtendedBy("Certify Beneficial Owner Checkbox", By.xpath("//label[@for='checkClaimOfTax']"));




    public TaxFormW8BENSeccondPage(Browser browser) {
        super(browser);
    }

    public void checkCertificationAgreement() {
        browser.click(certificationAgreementCheckBox);
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

    public void clickCertifyBeneficialOwnerCheckBox() {
        browser.click(certifyBeneficialOwner);
    }

}