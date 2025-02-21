package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class BankingDetailsAddPayoutMethodGeneralInformationPage extends AbstractPage {

    private static final ExtendedBy paymentRailsIframe = new ExtendedBy("Payment Rails Iframe",
            By.xpath("//*[contains(@class, 'PaymentRails_responsiveIframe')]"));

    private static final ExtendedBy generalInformationIndividualRadioBtn = new ExtendedBy("General Information, Individual Radio Btn",
            By.xpath("//*[@id='app']//input[@value='individual']"));

    private static final ExtendedBy generalInformationBusinessRadioBtn = new ExtendedBy("Add Payout Method - Button",
            By.xpath("//*[@id=\"app\"]//label[.//span[text()='Business']]"));

    private static final ExtendedBy goBackButton = new ExtendedBy("Go back to payout preference",
            By.id("go-back"));
    //we need to do this dirty thing here because we are interacting with an external web-hook, devs can't change ids.
    private static final ExtendedBy individualRadioButton = new ExtendedBy("individual radio button",
            By.cssSelector("input[value=individual]"));
    private static final ExtendedBy businessRadioButton = new ExtendedBy("business radio button",
            By.cssSelector("input[value=individual]"));
    private static final ExtendedBy firstNameInput = new ExtendedBy("First Name input",
            By.name("firstName"));
    private static final ExtendedBy lastNameInput = new ExtendedBy("Last Name input",
            By.name("lastName"));
    private static final ExtendedBy emailAutocompleted = new ExtendedBy("Autocompleted email",
            By.name("email"));
    private static final ExtendedBy yearSelect = new ExtendedBy("Selector for Year",
            By.name("undefined_year"));
    private static final ExtendedBy monthSelect = new ExtendedBy("Selector for Month",
            By.name("undefined_month"));
    private static final ExtendedBy daySelect = new ExtendedBy("Selector for Day",
            By.name("undefined_date"));
    private static final ExtendedBy countrySelect = new ExtendedBy("Selector for Day",
            By.name("country"));
    private static final ExtendedBy addressInput = new ExtendedBy("Address input",
            By.name("street1"));
    private static final ExtendedBy optionalAddress2Input = new ExtendedBy("Address input",
            By.name("street2"));
    private static final ExtendedBy cityInput = new ExtendedBy("Address input",
            By.name("city"));
    private static final ExtendedBy stateSelect = new ExtendedBy("Selector for State",
            By.name("region"));
    private static final ExtendedBy postalCodeInput = new ExtendedBy("Postal/Zip code input",
            By.name("postalCode"));
    private static final ExtendedBy nextButton = new ExtendedBy("Next page button",
            By.cssSelector("button[type=submit]"));
    private static final ExtendedBy backButton = new ExtendedBy("Previous page button",
            By.cssSelector("button[type=button]"));

    public BankingDetailsAddPayoutMethodGeneralInformationPage(Browser browser) {
        super(browser);

    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage selectIndividualAccountTypeRadioBtn(){
        browser.click(generalInformationIndividualRadioBtn, false);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage selectBusinessAccountTypeRadioBtn(){
        browser.click(generalInformationBusinessRadioBtn, false);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setFirstName(String firstName) {
        browser.switchToFrame(paymentRailsIframe);
        browser.typeTextElement(firstNameInput, firstName);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setLastName(String lastName) {
        browser.typeTextElement(lastNameInput, lastName);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setDOBYear(String year) {
        browser.selectElementByVisibleText(yearSelect, year);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setDOBMonth(String month) {
        browser.selectElementByVisibleText(monthSelect, month);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setDOBDay(String day) {
        browser.selectElementByVisibleText(daySelect, day);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setCountry(String country) {
        browser.selectElementByVisibleText(countrySelect, country);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setAddress(String address) {
        browser.typeTextElement(addressInput, address);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setAddress2(String address2) {
        browser.typeTextElement(optionalAddress2Input, address2);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setCity(String city) {
        browser.typeTextElement(cityInput, city);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setState(String state) {
        browser.selectElementByVisibleText(stateSelect, state);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodGeneralInformationPage setZipCode(String zipCode) {
        browser.typeTextElement(postalCodeInput, zipCode);
        return new BankingDetailsAddPayoutMethodGeneralInformationPage(browser);
    }

    public BankingDetailsAddPayoutMethodSelectPayoutMethodPage clickNextBtn(){
        browser.click(nextButton, false);
        return new BankingDetailsAddPayoutMethodSelectPayoutMethodPage(browser);
    }

    public BankingDetailsAddPayoutMethodSelectPayoutMethodPage completeGeneralBankInformationForm(
            String firstName, String lastName, String year, String month, String day, String country,
           String address, String address2, String city, String state, String zipCode) {
        setFirstName(firstName).setLastName(lastName).setDOBYear(year).setDOBMonth(month).setDOBDay(day)
                .setCountry(country).setAddress(address).setAddress2(address2).setCity(city).setState(state)
                .setCity(city).setZipCode(zipCode).clickNextBtn();
        return new BankingDetailsAddPayoutMethodSelectPayoutMethodPage(browser);
    }

}
