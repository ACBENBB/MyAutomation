package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.investWizard.*;
import org.openqa.selenium.By;

public class NieInvestorQualifications extends AbstractPage {

    private static final ExtendedBy completeVerificationBtn = new ExtendedBy("Complete verification button", By.xpath("//button[@id='btn-complete-verification']"));
    private static final ExtendedBy amAccreditedOrQualifiedInvestor = new ExtendedBy("I am an Accredited Investor button", By.xpath("//button[contains(@id,'btn-question-option')][1]"));
    private static final ExtendedBy iAgreeAndIConsiderMyselfAProfessionalInvestor = new ExtendedBy("I agree and I consider myself a professional investor", By.id("btn-question-option-1"));


    public NieInvestorQualifications(Browser browser) {
        super(browser);
    }

    public void clickCompleteVerificationBtn() {
        browser.click(completeVerificationBtn, false);
    }

    public InvestWizard_SignAgreement clickAmAccreditedInvestor() {
        browser.click(amAccreditedOrQualifiedInvestor, false);
        return new InvestWizard_SignAgreement(browser);
    }

    public InvestWizard_AdditionalData clickAmAccreditedInvestorForAdditionalData() {
        browser.click(amAccreditedOrQualifiedInvestor, false);
        return new InvestWizard_AdditionalData(browser);
    }

    public NieInvestmentDetails clickAmAccreditedInvestorForAccreditationFirst() {
        browser.click(amAccreditedOrQualifiedInvestor, false);
        return new NieInvestmentDetails(browser);
    }

    public NieDashboard clickAmAccreditedInvestorForAccreditationFirst1() {
        browser.click(amAccreditedOrQualifiedInvestor, false);
        return new NieDashboard(browser);
    }

    public NieDashboard clickAgreeAndConsiderAProfessionalInvestor() {
        browser.click(iAgreeAndIConsiderMyselfAProfessionalInvestor, false);
        return new NieDashboard(browser);
    }
}