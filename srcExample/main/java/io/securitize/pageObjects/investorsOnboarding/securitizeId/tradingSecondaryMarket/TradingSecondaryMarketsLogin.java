package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class TradingSecondaryMarketsLogin extends AbstractPage {
    private static final ExtendedBy emailField = new ExtendedBy("Email field", By.xpath("//*[@id='login-email']"));
    private static final ExtendedBy passwordField = new ExtendedBy("Password field", By.xpath("//*[@id='login-password']"));
    private static final ExtendedBy loginButton = new ExtendedBy("Login button", By.xpath("//*[@id='login-submit']"));

    public TradingSecondaryMarketsLogin(Browser browser) { super(browser); }

    public void setEmail(String email){ browser.typeTextElement(emailField, email);
    }

    public void setPassword(String password){
        browser.typeTextElement(passwordField, password);
    }

    public void clickLoginButton() {
        browser.click(loginButton, false);
    }

    public void performLoginWithValidUser(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    public void performLoginUsAccount(){
        browser.findElement(emailField).sendKeys(Keys.CONTROL + "a");
        browser.findElement(emailField).sendKeys(Keys.DELETE);
        this.performLoginWithValidUser(Users.getProperty(UsersProperty.ats_userAutomationUS),Users.getProperty(UsersProperty.ats_passwordUserAutomation));
    }

    public void performLoginNonUsAccount() {
        browser.findElement(emailField).sendKeys(Keys.CONTROL + "a");
        browser.findElement(emailField).sendKeys(Keys.DELETE);
        this.performLoginWithValidUser(Users.getProperty(UsersProperty.ats_userAutomationNonUS),Users.getProperty(UsersProperty.ats_passwordUserAutomation));
    }

    public TradingSecondaryMarketsLogin loginSecondaryMarketUsAccount(){
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(browser);
        securitizeLogin
                .clickAcceptCookies();

        this.performLoginUsAccount();

        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(browser);
        securitizeIDDashboard
                .clickSkipTwoFactor();
        securitizeLogin.waitForSIDToLogin();

        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(browser);
        securitizeIdDashboard.clickTrading();

        return this;
    }

    public TradingSecondaryMarketsLogin loginSecondaryMarketNONUsAccount(){
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(browser);
        securitizeLogin
                .clickAcceptCookies();

        this.performLoginNonUsAccount();

        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(browser);
        securitizeIDDashboard
                .clickSkipTwoFactor();
        securitizeLogin.waitForSIDToLogin();

        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(browser);
        securitizeIdDashboard.clickTrading();

        return this;
    }
}
