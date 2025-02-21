package io.securitize.pageObjects.legacyOLD.investmentWizard;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorHomePage;
import org.openqa.selenium.By;

public class ThankYou extends AbstractInvestmentWizardPage {

    private ExtendedBy investorCode = new ExtendedBy("investor code field", By.id("wizard-kyc-id"));

    ThankYou(Browser browser) {
        super(browser);
    }

    public String getInvestorCode() {
        browser.waitForElementVisibility(investorCode, 60); // long time due to docuSign random slowness
        return browser.getElementText(investorCode);
    }

    @Override
    public SecuritizeIdInvestorHomePage clickContinue() {
        super.clickContinue();
        return new SecuritizeIdInvestorHomePage(browser);
    }
}
