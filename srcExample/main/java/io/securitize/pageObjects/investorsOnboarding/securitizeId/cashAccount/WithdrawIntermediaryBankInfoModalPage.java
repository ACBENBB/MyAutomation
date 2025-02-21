package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawIntermediaryBankInfoModalPage extends AbstractPage {

    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy accountNumber = new ExtendedBy("Account Number Input Field", By.name("accountNumber"));
    private static final ExtendedBy bicSwiftField = new ExtendedBy("BIC / Swift Code Input Field", By.name("swiftCode"));
    private static final ExtendedBy intermediaryBankNameField = new ExtendedBy("Bank Name Input Field", By.name("bankName"));
    private static final ExtendedBy intermediaryBankAddress = new ExtendedBy("Intermediary Bank Address", By.name("streetLineOne"));
    private static final ExtendedBy cityField = new ExtendedBy("City Field", By.name("city"));

    private static final ExtendedBy countryDropdown = new ExtendedBy("Country Dropdown", By.xpath("//div[contains(@class, '_FormGroupComponent_')][.//label[contains(., 'Country')]]"));
    private static final ExtendedBy stateDropdown = new ExtendedBy("State Dropdown", By.xpath("//div[contains(@class, '_FormGroupComponent_')][.//label[contains(., 'State')]]"));
    private static final ExtendedBy zipCodeField = new ExtendedBy("Zip Code Dropdown", By.name("zipCode"));
    private static final ExtendedBy submitButton = new ExtendedBy("Submit Button", By.cssSelector("[data-test-id='intermediary-bank-submit-btn']"));


    public WithdrawIntermediaryBankInfoModalPage(Browser browser) {
        super(browser, modalBody);
    }

    //todo, change return modal page
    public WithdrawConfirmationModalPage clickSubmitButton() {
        browser.click(submitButton);

        return new WithdrawConfirmationModalPage(browser);
    }


    public void setAccountNumber(String number) {
        browser.findElement(accountNumber).sendKeys(number);
    }

    public void setBicSwift(String bicSwift) {
        browser.findElement(bicSwiftField).sendKeys(bicSwift);
    }

    public void setBankName(String bankName) {
        browser.findElement(intermediaryBankNameField).sendKeys(bankName);
    }

    public void setIntermediaryBankAddress(String intermediaryBankAddress) {
        browser.findElement(WithdrawIntermediaryBankInfoModalPage.intermediaryBankAddress).sendKeys(intermediaryBankAddress);
    }
    public void setCity(String city) {
        browser.findElement(cityField).sendKeys(city);
    }

    public void setCountry(String countryName) {

        browser.findElement(countryDropdown).findElement(By.tagName("button")).click();

        ExtendedBy option = new ExtendedBy(".", By.xpath("//a[contains(@class, 'DropDownItem')][text() = '" + countryName + "']"));

        browser.click(option);

    }

    public void setState(String stateName) {

        browser.findElement(stateDropdown).findElement(By.tagName("button")).click();

        ExtendedBy option = new ExtendedBy(".", By.xpath("//a[contains(@class, 'DropDownItem')][text() = '" + stateName + "']"));

        browser.click(option);

    }

    public void setZipCode(String zip) {
        browser.findElement(zipCodeField).sendKeys(zip);
    }

}
