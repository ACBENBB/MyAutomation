package io.securitize.pageObjects.investorsOnboarding.investWizard;

import io.securitize.infra.webdriver.*;
import io.securitize.pageObjects.*;
import org.openqa.selenium.*;

public class InvestWizard_SubmitFormPage extends AbstractPage {

    private static final ExtendedBy submitBtn = new ExtendedBy("Continue Button", By.xpath("//button[@id='suitability-continue']"));
    private static final ExtendedBy fullName = new ExtendedBy("Full name", By.name("fullName"));

    public InvestWizard_SubmitFormPage(Browser browser) {
        super(browser);
    }

    public InvestWizard_SubmitFormPage typeFullName(){
        browser.waitForElementVisibility(fullName);
        browser.typeTextElement(fullName, "Automation - Fernando Martin");
        return this;
    }

    public InvestWizard_SubmitFormPage clickSubmitBtn(){
        browser.click(submitBtn, false);
        return this;
    }

}
