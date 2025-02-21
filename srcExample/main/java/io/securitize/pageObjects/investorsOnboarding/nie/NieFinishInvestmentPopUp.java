package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieFinishInvestmentPopUp extends AbstractPage {
    private static final ExtendedBy finishInvestmentPopUpTitle = new ExtendedBy("Finish Invesment PopUp Title", By.xpath("//h4[contains(text(),'Congratulations')]"));
    private static final ExtendedBy finishInvestmentPopUpDescription = new ExtendedBy("Finish Invesment PopUp Description", By.xpath("//p[contains(text(),'You have completed')]"));
    private static final ExtendedBy goToHoldingsButton = new ExtendedBy("Go to holding Button", By.id("btn-investment--confirm"));

    public NieFinishInvestmentPopUp(Browser browser) {
        super(browser);
    }

    public void assertAllElementAreDisplayed() {
        browser.isElementVisible(finishInvestmentPopUpTitle);
        browser.isElementVisible(finishInvestmentPopUpDescription);
        browser.isElementVisible(goToHoldingsButton);
    }

}
