package io.securitize.pageObjects.investorsOnboarding.nie.spiceVC;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieDashboardSpiceVC extends AbstractPage {


    private static final ExtendedBy welcomeToSpiceText = new ExtendedBy("Go to Securitize ID button", By.xpath("//*[contains(text(), 'Welcome to your SPiCE dashboard')]"));

    public NieDashboardSpiceVC(Browser browser) {
        super(browser, welcomeToSpiceText);
    }

}

