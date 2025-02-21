package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdLoginInformationPage extends AbstractPage {

    private static final ExtendedBy loginInformationTitle = new ExtendedBy("Securitize Id - Login Information page title", By.xpath("//h1[text() = 'Login Information']"));

    public SecuritizeIdLoginInformationPage(Browser browser) {
        super(browser, loginInformationTitle);
    }


}
