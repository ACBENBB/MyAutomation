package io.securitize.pageObjects.investorsOnboarding.investWizard;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class InvestWizard_QualificationPage extends AbstractPage {

    private static final ExtendedBy qualificationTitle = new ExtendedBy("Qualification Title ", By.xpath("(//span[text()='qualification'])[1]"));
    private static final ExtendedBy iAgreeAndIConsiderMyselfProfessionalInvestor = new ExtendedBy("I consider Myself a professional investor", By.xpath("//span[text()='I agree and I consider myself a professional investor']"));
    private static final ExtendedBy noIAmRetailInvestor = new ExtendedBy("No, I am a retail investor", By.xpath("//span[text()='No, I am a retail investor']"));
    private static final ExtendedBy IAmAnAccreditationInvestor = new ExtendedBy("I am an Accredited Investor", By.xpath("//span[text()='I am an Accredited Investor']"));
    private static final ExtendedBy exitProcessBtn = new ExtendedBy("Exit this process button", By.id("btn-qualification--exit-process"));


    public InvestWizard_QualificationPage(Browser browser) {
        super(browser);
    }

    public void clickConsiderMyselfAProfessionalInvestor() {
        browser.click(iAgreeAndIConsiderMyselfProfessionalInvestor);
    }

    public void clickNoIAmRetailInvestor() {
        browser.click(noIAmRetailInvestor);
    }

    public InvestWizard_QualificationPage waitForMarketPopUpFromUI(){
        browser.waitForElementVisibility(iAgreeAndIConsiderMyselfProfessionalInvestor);
        browser.waitForElementVisibility(noIAmRetailInvestor);
        return this;
    }

    public boolean isQualificationStepDisplayed() {
        return browser.isElementVisible(iAgreeAndIConsiderMyselfProfessionalInvestor);
    }

    public void clickIAmAnAccreditedInvestor() {
        browser.click(IAmAnAccreditationInvestor);
    }

    public void clickExitProcess() {
        browser.click(exitProcessBtn);
    }

}
