package io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.tests.InvestorDetails;
import org.openqa.selenium.By;

public class SecuritizeIdCreateAccountRegistrationStep1 extends AbstractPage<SecuritizeIdCreateAccountRegistrationStep1> {

    public static final ExtendedBy firstName = new ExtendedBy("Securitize Id - Create account registration step 1 - Insert first name", By.id("registration-first-name"));
    public static final ExtendedBy lastName = new ExtendedBy("Securitize Id - Create account registration step 1 - Insert last name", By.id("registration-last-name"));
    public static final ExtendedBy loginButton = new ExtendedBy("Securitize Id - Create account registration step 1 - Login button", By.id("link-login"));
    public static final ExtendedBy nextButton = new ExtendedBy("Securitize Id - Create account registration step 1 - Next button", By.id("registration-next"));


    public SecuritizeIdCreateAccountRegistrationStep1(Browser browser) {
        super(browser, firstName);
    }

    public SecuritizeIdCreateAccountRegistrationStep1 insertFirstName(String value) {
        browser.typeTextElement(firstName, value);
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep1 insertLastName(String value) {
        browser.typeTextElement(lastName, value);
        return this;
    }

    public void createInvestor(InvestorDetails investorDetails) {
            insertFirstName(investorDetails.getFirstName())
                    .insertLastName(investorDetails.getLastName())
                    .clickNextButton()
                    .setCountryAndState(investorDetails.getCountry(), investorDetails.getState())
                    .insertPhoneNumber(investorDetails.getPhoneNumber())
                    .clickNextButton()
                    .selectInvestorType(investorDetails.getInvestorType());
            SecuritizeIdCreateAccountRegistrationStep4 securitizeIdCreateAccountRegistrationStep4;
            if (investorDetails.getInvestorType().equalsIgnoreCase("entity")) {
                new SecuritizeIdCreateAccountRegistrationStep3EntityInformation(browser)
                        .insertEntityName(investorDetails.getEntityName())
                        .setCountryAndState(investorDetails.getCountry(), investorDetails.getState())
                        .clickNextButton();
            }
            securitizeIdCreateAccountRegistrationStep4 = new SecuritizeIdCreateAccountRegistrationStep4(browser);
            securitizeIdCreateAccountRegistrationStep4
                    .insertEmailAddress(investorDetails.getEmail())
                    .insertPassword(investorDetails.getPassword())
                    .clickSubmitButton();
    }


    public SecuritizeIdInvestorLoginScreen clickLoginButton() {
        browser.click(loginButton, false);
        return new SecuritizeIdInvestorLoginScreen(browser);
    }

    public SecuritizeIdCreateAccountRegistrationStep2 clickNextButton() {
        browser.click(nextButton, false);
        return new SecuritizeIdCreateAccountRegistrationStep2(browser);
    }

}