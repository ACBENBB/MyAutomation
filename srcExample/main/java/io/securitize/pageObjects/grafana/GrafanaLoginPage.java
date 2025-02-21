package io.securitize.pageObjects.grafana;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class GrafanaLoginPage extends AbstractPage {

    private static final ExtendedBy usernameField = new ExtendedBy("Grafana login page - username field", By.xpath("//input[@name='user']"));
    private static final ExtendedBy passwordField = new ExtendedBy("Grafana login page - password field", By.xpath("//input[@name='password']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Grafana login page - submit button", By.xpath("//button[contains(@aria-label, 'Login')]"));

    public GrafanaLoginPage(Browser browser) {
        super(browser, usernameField);
    }

    public GrafanaLoginPage setUsername(String value) {
        browser.typeTextElement(usernameField, value);
        return this;
    }

    public GrafanaLoginPage setPassword(String value) {
        browser.typeTextElement(passwordField, value);
        return this;
    }

    public GrafanaDashboardPage clickSubmit() {
        browser.click(submitButton, false);
        return new GrafanaDashboardPage(browser);
    }
}
