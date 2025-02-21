package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpAddWallet extends AbstractPage {

    private static final ExtendedBy nameField = new ExtendedBy("name field", By.name("name"));
    private static final ExtendedBy addressField = new ExtendedBy("Address field", By.name("address"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));

    CpAddWallet(Browser browser) {
        super(browser, nameField);
    }

    public CpAddWallet typeName(String value) {
        browser.typeTextElement(nameField, value);
        return this;
    }

    public CpAddWallet typeAddress(String value) {
        browser.typeTextElement(addressField, value);
        return this;
    }

    public void clickOk() {
        browser.waitForElementClickable(okButton, 160);
        browser.clickAndWaitForElementToVanish(okButton, ExecuteBy.WEBDRIVER, true, 160);
    }

}