package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.utils.Authentication;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdTwoFactorPopUp extends AbstractPage {
    private static final ExtendedBy twoFaPrivateKeyTextfield = new ExtendedBy("Securitize Id - 2FA - Get Private Key", By.id("backup-key"), true);
    private static final ExtendedBy twoFaCodeNumberOneInput = new ExtendedBy("Securitize Id - 2FA - Code number ONE", By.id("twofa-digits-inp0"), true);
    private static final ExtendedBy twoFaCodeNumberTwoInput = new ExtendedBy("Securitize Id - 2FA - Code number TWO", By.id("twofa-digits-inp1"), true);
    private static final ExtendedBy twoFaCodeNumberThreeInput = new ExtendedBy("Securitize Id - 2FA - Code number THREE", By.id("twofa-digits-inp2"), true);
    private static final ExtendedBy twoFaCodeNumberFourInput = new ExtendedBy("Securitize Id - 2FA - Code number FOUR", By.id("twofa-digits-inp3"), true);
    private static final ExtendedBy twoFaCodeNumberFiveInput = new ExtendedBy("Securitize Id - 2FA - Code number FIVE", By.id("twofa-digits-inp4"), true);
    private static final ExtendedBy twoFaCodeNumberSixInput = new ExtendedBy("Securitize Id - 2FA - Code number SIX", By.id("twofa-digits-inp5"), true);
    private String twoFaCode;
    private String twoFaPrivateKey;


    public SecuritizeIdTwoFactorPopUp(Browser browser) {
        super(browser, twoFaCodeNumberOneInput);
    }

    public SecuritizeIdTwoFactorPopUp obtainPrivateKey(){
        this.twoFaPrivateKey = browser.getElementText(twoFaPrivateKeyTextfield);
        return this;
    }

    //Generate the code from given Private Key
    public SecuritizeIdTwoFactorPopUp generate2FACode(String twoFaPrivateKey){
        this.twoFaCode = Authentication.getTOTPCode(twoFaPrivateKey);
        return this;
    }

    //Generate the code from stored in object Private Key
    public SecuritizeIdTwoFactorPopUp generate2FACode(){
        this.twoFaCode = Authentication.getTOTPCode(this.twoFaPrivateKey);
        return this;
    }

    public SecuritizeIdTwoFactorPopUp setTwoFaCodeInUI(){
        browser.typeTextElement(twoFaCodeNumberOneInput,     String.valueOf(twoFaCode.charAt(0)));
        browser.typeTextElement(twoFaCodeNumberTwoInput,     String.valueOf(twoFaCode.charAt(1)));
        browser.typeTextElement(twoFaCodeNumberThreeInput,   String.valueOf(twoFaCode.charAt(2)));
        browser.typeTextElement(twoFaCodeNumberFourInput,    String.valueOf(twoFaCode.charAt(3)));
        browser.typeTextElement(twoFaCodeNumberFiveInput,    String.valueOf(twoFaCode.charAt(4)));
        browser.typeTextElement(twoFaCodeNumberSixInput,     String.valueOf(twoFaCode.charAt(5)));
        return this;
    }

    public String getTwoFaPrivateKey(){
        return this.twoFaPrivateKey;
    }

}
