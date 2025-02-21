package io.securitize.pageObjects.legacyOLD.investmentWizard;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;

public class InvestorType extends AbstractInvestmentWizardPage {

    private static final ExtendedBy qualifiedInvestorRadio = new ExtendedBy("Qualified investor radio button", By.id("exampleRadios1"));

    public InvestorType(Browser browser) {
        super(browser, qualifiedInvestorRadio);
    }

    public InvestorType clickQualifiedInvestor() {
        browser.click(qualifiedInvestorRadio);
        return this;
    }

    @Override
    public Investment clickContinue() {
        super.clickContinue();
        return new Investment(browser);
    }
}
