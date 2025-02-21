package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.investWizard.InvestWizard_InvestorInformationPage;
import org.openqa.selenium.By;

public class PrimaryOfferingSuitabiltyFormPopup extends AbstractPage {

    private static final ExtendedBy answerSuitabilityFormButton = new ExtendedBy("Answer Suitability form button", By.xpath("//*[@class = 'DisclosurePopup_container__body__infoModalBtn__F1smI btn btn-primary']"));
    private static final ExtendedBy SuitabilityFormPopUpText = new ExtendedBy("Suitability form popup text", By.xpath("//*[contains(text() , 'To proceed with your investment we are required by law to ask you some questions. We will ask you about your investment experience, risk profile and so in.')]"));
    private static final ExtendedBy accreditationbox = new ExtendedBy("Securitize Markets - Accreditation Box", By.id("accreditation-card"));
    private static final ExtendedBy completeInvestorProfileBtn = new ExtendedBy("Complete Investor Profile Btn", By.xpath("//*[contains(text(), 'Complete Investor Profile')]"));

    public PrimaryOfferingSuitabiltyFormPopup(Browser browser) {
        super(browser);
    }

    public PrimaryOfferingSuitabiltyFormPopup clickAnswerSuitabilityFormButton(){
        browser.click(answerSuitabilityFormButton,false);
        return this;
    }

    public PrimaryOfferingSuitabiltyFormPopup clickAnswerAccreditationFormButton() {
        browser.click(accreditationbox, false);
        return this;
    }

    public InvestWizard_InvestorInformationPage clickCompleteInvestorProfileBtn() {
        browser.click(completeInvestorProfileBtn);
        return new InvestWizard_InvestorInformationPage(browser);
    }
}