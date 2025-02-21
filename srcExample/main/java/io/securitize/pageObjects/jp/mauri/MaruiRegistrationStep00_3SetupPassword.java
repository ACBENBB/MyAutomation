package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep00_3SetupPassword extends AbstractJpPage {

    private static final ExtendedBy passwordInputBox = new ExtendedBy("Password Input Box", By.xpath("//*[@class='form-control' and @type='password']"));
    private static final ExtendedBy passwordRequirementSmallLetterOk = new ExtendedBy("Password Requirement small letter", By.xpath("//*[contains(@class,'requirementsCompleted') and contains(.,'1つの小文字')]"));
    private static final ExtendedBy passwordRequirementCapitalLetterOk = new ExtendedBy("Password Requirement capital letter", By.xpath("//*[contains(@class,'requirementsCompleted') and contains(.,'1つの大文字')]"));
    private static final ExtendedBy passwordRequirementNumberLetterOk = new ExtendedBy("Password Requirement number letter", By.xpath("//*[contains(@class,'requirementsCompleted') and contains(.,'1つの数字')]"));
    private static final ExtendedBy passwordRequirementLengthOk = new ExtendedBy("Password Requirement length", By.xpath("//*[contains(@class,'requirementsCompleted') and contains(.,'8文字以上')]"));
    private static final ExtendedBy residentOfJapanCheckbox = new ExtendedBy("Resident Of Japan Checkbox", By.xpath("//*[contains(@class,'form-check-input')]"));
    private static final ExtendedBy setupPasswordButton = new ExtendedBy("Setup Password Button", By.xpath("//button/*[text()[contains(.,'パスワード設定する')]]"));

    public MaruiRegistrationStep00_3SetupPassword(Browser browser) {
        super(browser, passwordInputBox);
    }

    public MaruiRegistrationStep00_3SetupPassword enterPassword(String password) {
        browser.typeTextElement(passwordInputBox, password);
        return this;
    }

    public MaruiRegistrationStep00_3SetupPassword clickResidentOfJapanCheckbox() {
        browser.click(residentOfJapanCheckbox);
        return this;
    }

    public boolean passwordRequirementSmallLetterOk() {
        return browser.isElementVisible(passwordRequirementSmallLetterOk);
    }
    public boolean passwordRequirementCapitalLetterOk() {
        return browser.isElementVisible(passwordRequirementCapitalLetterOk);
    }

    public boolean passwordRequirementNumberLetterOk() {
        return browser.isElementVisible(passwordRequirementNumberLetterOk);
    }

    public boolean passwordRequirementLengthOk() {
        return browser.isElementVisible(passwordRequirementLengthOk);
    }

    public boolean passwordRequirementsAreOkay() {
        return passwordRequirementSmallLetterOk() && passwordRequirementCapitalLetterOk() &&
               passwordRequirementNumberLetterOk() && passwordRequirementLengthOk();
    }

    public MaruiRegistrationStep01AgreeToTerms setupPassword(String password) {
        browser.typeTextElement(passwordInputBox, password);
        if (!browser.findElement(residentOfJapanCheckbox).isSelected()) {
            browser.click(residentOfJapanCheckbox);
        }
        browser.clickAndWaitForElementToVanish(setupPasswordButton);
        return new MaruiRegistrationStep01AgreeToTerms(browser);
    }

    public MaruiRegistrationStep01AgreeToTerms setupPassword(String password, boolean retry) {
        return RetryOnExceptions.retryFunction(
                ()-> setupPassword(password),
                ()-> {
                    if (!browser.isElementVisible(setupPasswordButton)) {
                        browser.refreshPage();
                    }
                    return null;
                },
                retry
        );
    }
}
