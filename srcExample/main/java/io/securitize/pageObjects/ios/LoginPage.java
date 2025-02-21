package io.securitize.pageObjects.ios;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class LoginPage extends AbstractPage<LoginPage> {

    private static final ExtendedBy emailField = new ExtendedBy("Email login field", By.xpath("//XCUIElementTypeTextField"));
    private static final ExtendedBy passwordField = new ExtendedBy("Password login field", By.xpath("//XCUIElementTypeSecureTextField"));
    private static final ExtendedBy loginButton = new ExtendedBy("Login button", By.xpath("//XCUIElementTypeButton[@name='Log In'] | //XCUIElementTypeButton[contains(@name, 'login Button')]"));

    public LoginPage(Browser browser) {
        super(browser, emailField);
    }

    public LoginPage setEmail(String value) {
        browser.findElement(emailField).clear();
        browser.sendKeysElement(emailField, "email to login", value);
        return this;
    }

    public LoginPage setPassword(String value) {
        browser.findElement(passwordField).clear();
        browser.sendKeysElement(passwordField, "password to login", value);
        return this;
    }

    public void clickLogin() {
        browser.findElement(loginButton).click();
    }

    public PortfolioPage performFullLogin(String email, String password) {
        this.setEmail(email)
                .setPassword(password)
                .clickLogin();

        return new PortfolioPage(browser);
    }
}