package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpSecuritizeIdAddWallet extends AbstractPage {

    private static final ExtendedBy nameField = new ExtendedBy("name field", By.xpath("//input[@name='name']"));
    private static final ExtendedBy addressField = new ExtendedBy("Address field", By.name("address"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));

    CpSecuritizeIdAddWallet(Browser browser) {
        super(browser, nameField);
    }

    public CpSecuritizeIdAddWallet typeName(String walletName) {
        browser.typeTextElement(nameField, walletName);
        return this;
    }

    public CpSecuritizeIdAddWallet typeAddress(String walletAddress) {
        browser.typeTextElement(addressField, walletAddress);
        return this;
    }

    public void clickOk() {
        browser.clickAndWaitForElementToVanish(okButton);
    }

    public void addWallet(String walletName, String walletAddress) {
        typeName(walletName);
        typeAddress(walletAddress);
        clickOk();
    }

}