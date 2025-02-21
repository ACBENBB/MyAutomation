package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.utils.Authentication;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class CpPlusLoginPage2FA extends AbstractJpPage {

    private static final ExtendedBy wrong2FaCodeError = new ExtendedBy("Wrong 2fa error message", By.xpath("//*[text()[contains(.,'The login process has failed')]]"));
    private static final ExtendedBy closeButton = new ExtendedBy("close button on login page", By.xpath("//button[@class='close']"));
    private static final ExtendedBy signInButton = new ExtendedBy("Sign-in button", By.xpath("//div[contains(@class, 'modal')]//button[contains(@class, 'primary')]"));
    private static final String TWO_FA_CODE_INPUT_LOCATOR_TEMPLATE = "//*[contains(@class, 'form-control') and @type='text'][%d]";

    public CpPlusLoginPage2FA(Browser browser) {
        super(browser, signInButton);
    }

    public void enterTwoFaCodes(String mfaSecretKey) {
        char[] ch = Authentication.getTOTPCode(mfaSecretKey).toCharArray();
        for(int i = 0; i < 6; i++) {
            ExtendedBy twoFaCodeNumberInput = new ExtendedBy("2FA Code Box " + (i+1), By.xpath(String.format(TWO_FA_CODE_INPUT_LOCATOR_TEMPLATE, (i+1))), true);
            browser.typeTextElement(twoFaCodeNumberInput, String.valueOf(ch[i]));
        }
    }

    public void clickCloseButton() {
        browser.click(closeButton);
    }

    public void clickSignInButton() {
        browser.click(signInButton);
    }

    public boolean twoFactorAuthenticationOk() {
        return !browser.isElementVisibleQuick(wrong2FaCodeError, 15);
    }

}
