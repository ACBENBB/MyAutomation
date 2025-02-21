package io.securitize.pageObjects.reportportal;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.grafana.GrafanaDashboardPage;
import org.openqa.selenium.By;

public class ReportPortalLoginPage extends AbstractPage<ReportPortalLoginPage> {

    private static final ExtendedBy usernameField = new ExtendedBy("Report portal login page - username field", By.xpath("//input[@name='login']"));
    private static final ExtendedBy passwordField = new ExtendedBy("Report portal login page - password field", By.xpath("//input[@name='password']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Report portal login page - submit button", By.xpath("//button[@type='submit']"));

    public ReportPortalLoginPage(Browser browser) {
        super(browser, usernameField);
    }

    public ReportPortalLoginPage setUsername(String value) {
        browser.typeTextElement(usernameField, value);
        return this;
    }

    public ReportPortalLoginPage setPassword(String value) {
        browser.typeTextElement(passwordField, value);
        return this;
    }

    public ReportPortalDashboardPage clickSubmit() {
        browser.click(submitButton, false);
        return new ReportPortalDashboardPage(browser);
    }
}
