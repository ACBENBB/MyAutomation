package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdSettings extends AbstractPage {
    //2FA
    private static final ExtendedBy twoFaEnable = new ExtendedBy("Securitize Id - 2FA pop up - Enable button", By.id("twofa-enable"));
    private static final ExtendedBy twoFaPassword = new ExtendedBy("Securitize Id - 2FA - Set password", By.id("twofa-password"), true);
    private static final ExtendedBy twoFaPasswordNext = new ExtendedBy("Securitize Id - 2FA - Click password next", By.xpath("//button[@id='twofa-next'][not(@disabled)]"));
    private static final ExtendedBy twoFaToggle = new ExtendedBy("Securitize Id - 2FA Settings - Toggle", By.xpath("//span[@class='switch-slider']"));
    //Settings
    private static final ExtendedBy loginInformationCard = new ExtendedBy("Settings - Login information card", By.id("account-card"));


    public SecuritizeIdSettings(Browser browser) {
        super(browser);
    }

    public SecuritizeIdSettings clickEnableTwoFactorPopUp(){
        browser.waitForElementVisibility(twoFaEnable);
        browser.click(twoFaEnable);
        return this;
    }

    public SecuritizeIdSettings setTwoFaPassword(String password){
        browser.typeTextElement(twoFaPassword, password);
        return this;
    }

    public void clickTwoFaPasswordNext(){
        browser.waitForElementClickable(twoFaPasswordNext, 10);
        browser.click(twoFaPasswordNext, false);
    }

    public SecuritizeIdLoginInformationPage clickSettingsLoginInformationCard() {
        browser.waitForElementVisibility(loginInformationCard);
        browser.click(loginInformationCard, false);
        return new SecuritizeIdLoginInformationPage(browser);
    }

    public SecuritizeIdSettings toggle2Fa(){
        browser.click(twoFaToggle, false);
        return this;
    }

}
