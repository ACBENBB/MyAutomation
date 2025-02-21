package io.securitize.pageObjects.legacyOLD.investmentWizard;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.nie.docuSign.DocusignSigningPage;
import org.openqa.selenium.By;

public class Subscription extends AbstractInvestmentWizardPage {

    private ExtendedBy fullNameField = new ExtendedBy("full name field", By.id("fullname"));
    private ExtendedBy docuSignFrame = new ExtendedBy("DocuSign frame", By.id("docusign-agreement"));

    public Subscription(Browser browser) {
        super(browser);
    }

    public void typeFullName(String firstName, String lastName) {
        browser.typeTextElement(fullNameField, firstName + " " + lastName);
    }

    @Override
    public ThankYou clickContinue() {
        super.clickContinue();
        return new ThankYou(browser);
    }

    public ThankYou docuSignDocument() {
        browser.switchToFrame(docuSignFrame);

        DocusignSigningPage signingPage = new DocusignSigningPage(browser);
        signingPage.signDocument();

        browser.switchBackToDefaultContent();
        return new ThankYou(browser);
    }
}