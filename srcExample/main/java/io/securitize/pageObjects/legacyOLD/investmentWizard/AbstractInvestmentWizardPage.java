package io.securitize.pageObjects.legacyOLD.investmentWizard;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class AbstractInvestmentWizardPage extends AbstractPage {
    protected ExtendedBy continueButton = new ExtendedBy("Continue button", By.xpath("//button[contains(@class, 'primary')]"));

    public AbstractInvestmentWizardPage(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    public AbstractPage clickContinue() {
        browser.clickAndWaitForElementToVanish(continueButton, ExecuteBy.WEBDRIVER, false);
        return this;
    }
}
