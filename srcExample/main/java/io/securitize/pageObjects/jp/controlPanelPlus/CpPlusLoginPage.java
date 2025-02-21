package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

import static io.securitize.infra.reporting.MultiReporter.*;

public class CpPlusLoginPage extends AbstractJpPage {

    private static final ExtendedBy loginButton = new ExtendedBy("Login button", By.xpath("//*[contains(@class,'btn-primary')]"));
    private static final ExtendedBy emailTextBox = new ExtendedBy("CP+ login screen - Email text box", By.xpath("//*[@class = 'form-control form-group']"));
    private static final ExtendedBy passwordTextBox = new ExtendedBy("CP+ login screen - Password text box", By.xpath("//*[@class = 'form-control form-group password-input']"), true);
    private static final ExtendedBy signInButton = new ExtendedBy("Login screen - Sign in button", By.xpath("//button[contains(@class, 'btn btn-outline-primary btn-block mb-3')]"));
    private static final ExtendedBy logo = new ExtendedBy("Logo", By.xpath("//*[contains(@class,'mr-auto ml-2 c-header-nav')]"));

    public CpPlusLoginPage(Browser browser) {
        super(browser, emailTextBox);
    }

    public CpPlusLoginPage2FA loginCpPlusUsingEmailPassword(String email, String password) {
        browser.typeTextElement(emailTextBox, email);
        browser.typeTextElement(passwordTextBox, password);
        browser.click(signInButton, false);
        return new CpPlusLoginPage2FA(browser);
    }

    /**
     * - unlike Control Panel login, an extra page (page with set language and login button)
     *   appears before login (email and password input) page.
     * - this extra page can be displayed even after successful login. if this happens, login
     *   procedures need to start over again.
     * - also, even after successful login, blank (or almost blank) page can be displayed.
     * - when 2FA fails, numbers in 2FA input are not cleared.
     */

    public void loginCpPlusUsingEmailPassword(String email, String password, String mfaSecretKey) {
        if (browser.isElementVisibleQuick(loginButton, 1)) {
            browser.clickWithJs(browser.findElement(loginButton));
        }

        browser.typeTextElement(emailTextBox, email);
        browser.typeTextElement(passwordTextBox, password);
        browser.click(signInButton, false);

        CpPlusLoginPage2FA page2FA = new CpPlusLoginPage2FA(browser);
        page2FA.enterTwoFaCodes(mfaSecretKey);

        if (page2FA.twoFactorAuthenticationOk()) {
            info("2FA Ok");
        } else {
            warning("2FA Not Ok. close MFA page");
            page2FA.clickCloseButton();
        }
        browser.waitForElementVisibility(logo);
    }

    public void loginCpPlusUsingEmailPassword(String email, String password, String mfaSecretKey, boolean retry) {
        RetryOnExceptions.retryFunction(
                ()-> {loginCpPlusUsingEmailPassword(email, password, mfaSecretKey); return null;},
                ()-> {
                    if (browser.isElementVisibleQuick(loginButton, 30)) {
                        warning("somehow, page with login button appears.");
                        browser.clickWithJs(browser.findElement(loginButton));
                    }
                    browser.refreshPage();
                    return null;
                }, retry
        );
    }
}
