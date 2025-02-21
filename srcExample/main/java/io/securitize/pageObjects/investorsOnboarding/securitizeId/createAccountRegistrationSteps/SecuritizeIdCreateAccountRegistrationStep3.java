package io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdCreateAccountRegistrationStep3 extends AbstractPage {
    private static final String EXTRACT_LINK_FROM_EMAIL_REGEX = "<a[\\s]*?href=\"(.*?)\"(.*?border-radius:[ ]{0,1}6px;)";
    public static final ExtendedBy emailTextBox = new ExtendedBy("Securitize Id - Create account registration step 3- Insert email", By.id("registration-email"));
    public static final ExtendedBy passwordTextBox = new ExtendedBy("Securitize Id - Create account registration step 3 - Insert password", By.id("registration-password"), true);
    public static final ExtendedBy submitButton = new ExtendedBy("Securitize Id - Create account registration step 3- Submit button", By.id("registration-submit"));
    public static final ExtendedBy securitizeFullLogo = new ExtendedBy("Securitize Id - Create account registration step 3- Securitize full logo", By.xpath("//img[@alt ='Securitize iD']"));
    public static final ExtendedBy selectInvestorTypeIndividual = new ExtendedBy("Securitize Id - Create account registration step 3- Select investor type individual", By.xpath("//div[text()='Individual']"));
    public static final ExtendedBy selectInvestorTypeEntity = new ExtendedBy("Securitize Id - Create account registration step 3- Select investor type entity", By.xpath("//div[text()='Entity']"));
    public static final ExtendedBy nextButton = new ExtendedBy("Securitize Id - Create account registration step 1- Next button", By.id("registration-next"));


    public SecuritizeIdCreateAccountRegistrationStep3(Browser browser) {
        super(browser, securitizeFullLogo);
    }


    public SecuritizeIdCreateAccountRegistrationStep3 insertEmailAddress(String value) {
        browser.typeTextElement(emailTextBox, value);
        return this;
    }

    public SecuritizeIdCreateAccountRegistrationStep3 insertPassword(String value) {
        browser.typeTextElement(passwordTextBox, value);
        return this;
    }

    public void clickSubmitButton() {
        browser.click(submitButton, false);
    }

    public String extractLinkFromEmail(String recipientAddress) {
        return extractLinkFromEmail(recipientAddress, EXTRACT_LINK_FROM_EMAIL_REGEX);
    }

    public SecuritizeIdCreateAccountRegistrationStep3 extractLinkFromEmailAndNavigate(String email) {
        String link = extractLinkFromEmail(email);
        browser.navigateTo("about:blank"); // workaround to a bug of navigation not happening in this page
        browser.navigateTo(link);
        return this;
    }

    public void selectInvestorType(String investorType) {
        if (investorType.equalsIgnoreCase("individual"))
            browser.click(selectInvestorTypeIndividual, false);
        if (investorType.equalsIgnoreCase("entity"))
            browser.click(selectInvestorTypeEntity, false);
        browser.click(nextButton, false);
    }

}