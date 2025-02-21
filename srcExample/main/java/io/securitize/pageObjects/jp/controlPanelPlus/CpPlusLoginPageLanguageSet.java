package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CpPlusLoginPageLanguageSet extends AbstractJpPage {

    private static final ExtendedBy languageLabelJapanese = new ExtendedBy("Language Label JA", By.xpath("//*[contains(@class,'toggle-label') and text() = 'JA' ]"));
    private static final ExtendedBy languageLabelEnglish = new ExtendedBy("Language Label EN", By.xpath("//*[contains(@class,'toggle-label') and text() = 'EN' ]"));
    private static final ExtendedBy languageSelectJapanese = new ExtendedBy("Language Select Japanese", By.xpath("//*[@class='dropdown-item' and text() = 'Japanese' ]"));

    private static final ExtendedBy loginButton = new ExtendedBy("Login button", By.xpath("//*[contains(@class,'btn-primary')]"));

    public CpPlusLoginPageLanguageSet(Browser browser) {
        super(browser, loginButton);
    }

    public CpPlusLoginPageLanguageSet selectLanguageJapanese() {
        browser.waitForPageStable();
        List<WebElement> webElements = browser.findElements(languageLabelJapanese);
        if (webElements.isEmpty()) {
            browser.click(languageLabelEnglish);
            browser.click(languageSelectJapanese);
        }
        return this;
    }

    public CpPlusLoginPage clickLoginButton() {
        browser.clickWithJs(browser.findElement(loginButton));
        return new CpPlusLoginPage(browser);
    }
}
