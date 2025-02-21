package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.SecuritizeIdInvestorKyb01EntityInformation;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc01IndividualIdentityVerification;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorRegistration01InvestorType extends SecuritizeIdInvestorAbstractPage {

    private static final ExtendedBy individualTypeButton = new ExtendedBy("Securitize Id - 01 - individual type button", By.id("wizard-type-individual"));
    private static final ExtendedBy entityTypeButton = new ExtendedBy("Securitize Id - 01 - entity type button", By.id("wizard-type-entity"));
    public static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 01 - conitnue button", By.id("wizard-continue"));
    public static final ExtendedBy individualContinueButton = new ExtendedBy("Securitize Id - 01 - conitnue button", By.id("individual-wizard-continue"));
    public static final ExtendedBy entityContinueButton = new ExtendedBy("Securitize Id - 01 - entity conitnue button", By.id("entity-wizard-continue"));
    private static final ExtendedBy exitProcessButton = new ExtendedBy("Exit process button", By.xpath("//button[contains(@class, 'profile-verification-return-link')]/span[text() = 'Return to' or 'Exit this process']"));
    private static final ExtendedBy documentsPopUp = new ExtendedBy("Documents Pop Up", By.xpath("//div[@class='modal-content p-0']"));
    private static final ExtendedBy closePopUpBtn = new ExtendedBy("Close Pop Up Button", By.xpath("//i[@class='icon-exit font-20 text-dark']"));

    public SecuritizeIdInvestorRegistration01InvestorType(Browser browser) {
        super(browser);
        waitForStepToLoad();
    }

    public SecuritizeIdInvestorRegistration01InvestorType(Browser browser, boolean updatesRequired) {
        super(browser);
        waitForPopUpToLoad();
    }

    public SecuritizeIdInvestorRegistration01InvestorType waitForStepToLoad() {
        browser.waitForElementVisibility(individualTypeButton);
        browser.waitForElementVisibility(entityTypeButton);
        return this;
    }

    public SecuritizeIdInvestorRegistration01InvestorType waitForPopUpToLoad() {
        browser.waitForElementVisibility(documentsPopUp);
        return this;
    }

    public SecuritizeIdInvestorKyc01IndividualIdentityVerification clickIndividualContinue() {
        browser.click(individualContinueButton, false);
        return new SecuritizeIdInvestorKyc01IndividualIdentityVerification(browser);
    }

    public SecuritizeIdInvestorKyb01EntityInformation clickEntityContinue() {
        browser.click(entityContinueButton, false);
        return new SecuritizeIdInvestorKyb01EntityInformation(browser);
    }

    public SecuritizeIdInvestorRegistration01InvestorType SelectTypeEntity() {
        browser.click(entityTypeButton);
        return this;
    }

    public void clickExitProcess() {
        browser.click(exitProcessButton);
    }

    public boolean isDocumentsPopUpDisplayed(){
        return browser.isElementVisible(documentsPopUp);
    }
    public void closePopUp(){
        browser.click(closePopUpBtn);
    }

}
