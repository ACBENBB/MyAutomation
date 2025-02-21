package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpConfirmSignature extends AbstractPage {

    private static final ExtendedBy signNowButton = new ExtendedBy("sign now button", By.xpath("//div[@role='document']//button[contains(@class, 'btn-primary') and contains(text(), 'Sign now')]"));
    private static final ExtendedBy signerAddressField = new ExtendedBy("signer address field", By.name("signerAddress"));
    private static final ExtendedBy signerPrivateKeyField = new ExtendedBy("signer private key field", By.name("privateKey"));
    private static final ExtendedBy walletTypeSignature = new ExtendedBy("wallet address and private key - radio button", By.xpath("//*[ contains(text() , 'Wallet Address & Private Key')]"));

    public CpConfirmSignature(Browser browser) {
        super(browser, signNowButton);
    }

    public CpConfirmSignature typeSignerAddress(String signerAddress) {
        browser.typeTextElement(signerAddressField, signerAddress);
        return this;
    }

    public CpConfirmSignature typePrivateKey(String privateKey) {
        browser.typeTextElement(signerPrivateKeyField, privateKey);
        return this;
    }

    public void clickSignNow() {
        browser.clickAndWaitForElementToVanish(signNowButton);
        browser.waitForElementToVanish(signerPrivateKeyField); // once vanished we know the popup has closed
    }

    public CpConfirmSignature clickWalletTypeSignatureRadioButton(){
        browser.click(walletTypeSignature, true);
        return this;
    }

    public void signTransaction(String signerAddress, String privateKey) {
        browser.typeTextElement(signerAddressField, signerAddress);
        browser.typeTextElement(signerPrivateKeyField, privateKey);
        browser.clickAndWaitForElementToVanish(signNowButton);
        browser.waitForElementToVanish(signerPrivateKeyField); // once vanished we know the popup has closed
    }

}