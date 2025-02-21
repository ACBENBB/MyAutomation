package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.Authentication;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

import static io.securitize.infra.reporting.MultiReporter.*;

public class CpLoginPage2FA extends AbstractPage<CpLoginPage2FA> {

    private static final ExtendedBy wrong2FaCodeError = new ExtendedBy("Wrong 2fa error message", By.xpath("//div[contains(@class, 'alert-danger')]/small"));
    private static final ExtendedBy signInButton = new ExtendedBy("Sign-in button", By.xpath("//div[contains(@class, 'modal')]//button[contains(@class, 'primary')]"));
    public static final ExtendedBy twoFaPrivateKeyTextfield = new ExtendedBy("Securitize Id - 2FA - Get Private Key", By.xpath("//*[contains(text(), 'Two-factor Authentication (2FA)')]"), true);
    private static final ExtendedBy twoFaCodeNumberOneInput = new ExtendedBy("Securitize Id - 2FA - Code number ONE", By.xpath("//div[@class='d-flex mb-4']//input[@type='text'][1]"), true);
    private static final ExtendedBy twoFaCodeNumberTwoInput = new ExtendedBy("Securitize Id - 2FA - Code number TWO", By.xpath("//div[@class='d-flex mb-4']//input[@type='text'][2]"), true);
    private static final ExtendedBy twoFaCodeNumberThreeInput = new ExtendedBy("Securitize Id - 2FA - Code number THREE", By.xpath("//div[@class='d-flex mb-4']//input[@type='text'][3]"), true);
    private static final ExtendedBy twoFaCodeNumberFourInput = new ExtendedBy("Securitize Id - 2FA - Code number FOUR", By.xpath("//div[@class='d-flex mb-4']//input[@type='text'][4]"), true);
    private static final ExtendedBy twoFaCodeNumberFiveInput = new ExtendedBy("Securitize Id - 2FA - Code number FIVE", By.xpath("//div[@class='d-flex mb-4']//input[@type='text'][5]"), true);
    private static final ExtendedBy twoFaCodeNumberSixInput = new ExtendedBy("Securitize Id - 2FA - Code number SIX", By.xpath("//div[@class='d-flex mb-4']//input[@type='text'][6]"), true);
    private String twoFaCode;
    private String twoFaPrivateKey;


    public CpLoginPage2FA(Browser browser) {
        super(browser, twoFaPrivateKeyTextfield);
    }

    public CpLoginPage2FA obtainPrivateKey(){
        this.twoFaPrivateKey  = Users.getProperty(UsersProperty.automationCp2FaSecret);
        return this;
    }

    //Generate the code from given Private Key
    public CpLoginPage2FA generate2FACode(String twoFaPrivateKey){
        this.twoFaCode = Authentication.getTOTPCode(twoFaPrivateKey);
        return this;
    }

    //Generate the code from stored in object Private Key
    public CpLoginPage2FA generate2FACode(){
        this.twoFaCode = Authentication.getTOTPCode(this.twoFaPrivateKey);
        return this;
    }

    public CpSelectIssuer setTwoFaCodeInUI() {
        return setTwoFaCodeInUI(true);
    }

    public CpSelectIssuer setTwoFaCodeInUI(boolean returnIssuerScreen) {
        int maxAttempts = 3;
        boolean successful = false;

        for (int i = 0; i < maxAttempts; i++) {
            browser.typeTextElement(twoFaCodeNumberOneInput,     String.valueOf(twoFaCode.charAt(0)));
            browser.typeTextElement(twoFaCodeNumberTwoInput,     String.valueOf(twoFaCode.charAt(1)));
            browser.typeTextElement(twoFaCodeNumberThreeInput,   String.valueOf(twoFaCode.charAt(2)));
            browser.typeTextElement(twoFaCodeNumberFourInput,    String.valueOf(twoFaCode.charAt(3)));
            browser.typeTextElement(twoFaCodeNumberFiveInput,    String.valueOf(twoFaCode.charAt(4)));
            browser.typeTextElement(twoFaCodeNumberSixInput,     String.valueOf(twoFaCode.charAt(5)));

            // first attempt - sign in is clicked automatically
            if (i > 0) {
                browser.click(signInButton ,false);
            }

            // if time passed just as we placed the OTP code - do it again
            if (browser.isElementVisibleQuick(wrong2FaCodeError, 10)) {
                info("Wrong 2fa code error, trying again...");
                this.twoFaCode = Authentication.getTOTPCode(this.twoFaPrivateKey);
            } else {
                successful = true;
                break;
            }
        }

        if (!successful) {
            errorAndStop("Unable to set correct 2FA code even after " + maxAttempts + " attempts", true);
        }

        if (returnIssuerScreen) {
            return new CpSelectIssuer(browser);
        } else {
            return null;
        }
    }

    public void setTwoFaCodeInUIBasicOperator() {
        int maxAttempts = 3;
        boolean successful = false;

        for (int i = 0; i < maxAttempts; i++) {
            browser.typeTextElement(twoFaCodeNumberOneInput,     String.valueOf(twoFaCode.charAt(0)));
            browser.typeTextElement(twoFaCodeNumberTwoInput,     String.valueOf(twoFaCode.charAt(1)));
            browser.typeTextElement(twoFaCodeNumberThreeInput,   String.valueOf(twoFaCode.charAt(2)));
            browser.typeTextElement(twoFaCodeNumberFourInput,    String.valueOf(twoFaCode.charAt(3)));
            browser.typeTextElement(twoFaCodeNumberFiveInput,    String.valueOf(twoFaCode.charAt(4)));
            browser.typeTextElement(twoFaCodeNumberSixInput,     String.valueOf(twoFaCode.charAt(5)));

            // first attempt - sign in is clicked automatically
            if (i > 0) {
                browser.click(signInButton ,false);
            }

            // if time passed just as we placed the OTP code - do it again
            if (browser.isElementVisibleQuick(wrong2FaCodeError, 10)) {
                info("Wrong 2fa code error, trying again...");
                this.twoFaCode = Authentication.getTOTPCode(this.twoFaPrivateKey);
            } else {
                successful = true;
                break;
            }
        }

        if (!successful) {
            errorAndStop("Unable to set correct 2FA code even after " + maxAttempts + " attempts", true);
        }
    }
}