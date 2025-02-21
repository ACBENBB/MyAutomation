package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiLoginPage extends AbstractJpPage {

    private static final ExtendedBy emailInputField = new ExtendedBy("Email Input Field", By.xpath("//*[@class='form-control' and @type='email']"));
    private static final ExtendedBy passwordInputField = new ExtendedBy("Password Input Field", By.xpath("//*[@class='form-control' and @type='password']"));
    private static final ExtendedBy loginButton = new ExtendedBy("Login Button", By.xpath("//button/*[text()[contains(.,'ログイン')]]"));
    private static final ExtendedBy loginErrorText = new ExtendedBy("Login Error Text", By.xpath("//*[text()[contains(.,'電子メールアドレスまたはパスワードが違います')]]"));

    public MaruiLoginPage(Browser browser) {
        super(browser, loginButton);
    }

    public void enterEmail(String email) {
        browser.typeTextElement(emailInputField, email);
    }

    public void enterPassword(String password) {
        browser.typeTextElement(passwordInputField, password);
    }

    public void clickLogin() {
        browser.click(loginButton, false);
        browser.waitForElementToVanish(emailInputField);
    }

    public MaruiLoginPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        return this;
    }

    public MaruiLoginPage login(String email, String password, boolean retry) {
        return RetryOnExceptions.retryFunction(()-> login(email, password), ()-> null, retry);
    }

    public boolean loginErrorTextIsVisible() {
        browser.waitForElementVisibility(loginErrorText, 60);
        return browser.isElementVisible(loginErrorText);
    }

}
