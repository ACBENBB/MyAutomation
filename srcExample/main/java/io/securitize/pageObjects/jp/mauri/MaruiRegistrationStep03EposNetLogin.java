package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep03EposNetLogin extends AbstractJpPage {

    private static final ExtendedBy step03Header = new ExtendedBy("Step 03 Head Line", By.xpath("//h1[text()[contains(., 'エポスNetログイン')]]"));
    private static final ExtendedBy eposNetLoginButton = new ExtendedBy("Login Button (img)", By.xpath("//*[@class='d-flex justify-content-center']/img"));
    private static final ExtendedBy backButton = new ExtendedBy("Back Button", By.xpath("//button/*[text()[contains(.,'戻る')]]"));
    private static final ExtendedBy nextStepButton = new ExtendedBy("Next Step Button", By.xpath("//button/*[text()[contains(.,'次のステップに進む')]]"));

    public MaruiRegistrationStep03EposNetLogin(Browser browser) {
        super(browser, step03Header);
    }

    // update bank information of investor shall be done by api. because login to Epos Net does not work with test data.

    // "々" is categorized as a symbol. investor test data has this character in names and addresses. this character
    // seemed to have caused a problem. clickable on buttons is tested here. more check will be added when needed.
    public boolean buttonIsClickable(ExtendedBy extendedBy) {
        boolean result = true;
        try {
            browser.waitForElementClickable(extendedBy, 30);
        } catch (Exception exception) {
            result = false;
        }
        return result;
    }
    public boolean eposNetLoginButtonIsClickable() {
        return buttonIsClickable(eposNetLoginButton);
    }
    public boolean backButtonIsClickable() {
        return buttonIsClickable(backButton);
    }
    public boolean nextStepButtonIsClickable() {
        return buttonIsClickable(nextStepButton);
    }
    public boolean buttonsAreClickable() {
       return eposNetLoginButtonIsClickable() && backButtonIsClickable() && nextStepButtonIsClickable();
    }
}
