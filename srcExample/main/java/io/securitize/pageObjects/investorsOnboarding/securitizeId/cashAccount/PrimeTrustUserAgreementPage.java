package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class PrimeTrustUserAgreementPage extends AbstractPage {

    private static final ExtendedBy confirmButton = new ExtendedBy("Confirm button", By.cssSelector("[data-test-id='ca-createaccountmodal-submit-btn']"));
    private static final ExtendedBy agreementCheckBox = new ExtendedBy("Confirm button", By.id("AgreementCheckbox"));
    private static final ExtendedBy signatureField = new ExtendedBy("Confirm button", By.id("SignatureTitle"));
    private static final ExtendedBy signatureLabelValue = new ExtendedBy("Confirm button", By.cssSelector("[data-test-id='ca-createaccountmodal-signaturelabel-value']"));
    private static final ExtendedBy createAcctSuccessButton = new ExtendedBy("Confirm button", By.cssSelector("[data-test-id='ca-createaccountmodal-success-btn']"));

    public PrimeTrustUserAgreementPage(Browser browser) {
        super(browser, confirmButton);
    }

    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return browser.findElement(confirmButton).isDisplayed();
    }

    public void check() {
        browser.findElement(agreementCheckBox).click();
    }

    public String getSignatureLabelValue() {
        return browser.getElementText(signatureLabelValue);
    }
    public void setSignatureField(String name) {
        browser.findElement(signatureField).sendKeys(name);
    }

    public void clickConfirmBtn() {
        browser.findElement(confirmButton).click();
    }

    public void clickCreateAcctSuccessBtn() {
        browser.waitForPageStable();
        browser.click(createAcctSuccessButton); //Review if correct of use browser.findElement(By).click()
    }

}
