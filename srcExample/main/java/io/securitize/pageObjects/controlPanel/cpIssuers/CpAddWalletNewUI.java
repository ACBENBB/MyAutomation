package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpAddWalletNewUI extends AbstractPage {

    private static final ExtendedBy nameField = new ExtendedBy("name field", By.xpath("//input[@placeholder='Name']"));
    private static final ExtendedBy addressField = new ExtendedBy("Address field", By.xpath("//input[@placeholder='Address']"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[contains(text(),'OK')]"));

    CpAddWalletNewUI(Browser browser) {
        super(browser);
    }

    public CpAddWalletNewUI typeName(String value) {
        browser.typeTextElement(nameField, value);
        return this;
    }

    public CpAddWalletNewUI typeAddress(String value) {
        browser.typeTextElement(addressField, value);
        return this;
    }

    public void clickOk() {
        browser.waitForElementClickable(okButton);
        browser.clickAndWaitForElementToVanish(okButton, ExecuteBy.WEBDRIVER, true, 160);
    }

}