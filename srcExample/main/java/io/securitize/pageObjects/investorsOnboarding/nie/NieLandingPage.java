package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieLandingPage extends AbstractPage {

    private static final ExtendedBy registerButton = new ExtendedBy("Register button", By.xpath("//a[contains(@class, 'btn-primary') and contains(text(), 'Register')]"));
    private static final ExtendedBy loginButton = new ExtendedBy("Login button", By.id("btn-log-in"));


    public NieLandingPage(Browser browser) {
        super(browser, registerButton);
    }

    public NieWelcomePage ClickRegister() {
        browser.click(registerButton);
        return new NieWelcomePage(browser);
    }

    public void ClickLogin() {
        browser.click(loginButton);

    }

}
