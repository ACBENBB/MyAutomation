package io.securitize.pageObjects.investorsOnboarding.nie.arcaLabs;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieDashboardArcaLabs extends AbstractPage {


    private static final ExtendedBy welcomeToArcaLabsText = new ExtendedBy("Go to Securitize ID button", By.xpath("//*[contains(text(), 'Welcome to the Arca Investor Dashboard')]"));

    public NieDashboardArcaLabs(Browser browser) {
        super(browser, welcomeToArcaLabsText);
    }

}

