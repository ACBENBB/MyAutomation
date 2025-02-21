package io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.manageToken;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpManageRolesPage extends AbstractPage {
    private static final ExtendedBy masterWalletAddressField = new ExtendedBy("Master wallet address", By.xpath("//input[@name='masterWalletAddress']"));
    private static final ExtendedBy syncerWalletAddressField = new ExtendedBy("syncer wallet address", By.xpath("//input[@name='syncerOwnerAddress']"));
    private static final ExtendedBy syncerWalletPkField = new ExtendedBy("syncer private key", By.xpath("//input[@name='registrarOwnerPk']"));
    private static final ExtendedBy syncerHsmToggle = new ExtendedBy("Syncer HSM toggle ON", By.xpath("//span[@class='switcher-indicator']"));
    private static final ExtendedBy syncerHsmToggleOff = new ExtendedBy("Syncer HSM toggle OFF", By.xpath("//span[@class='switcher-no']"));
    private static final ExtendedBy tbeWalletField = new ExtendedBy("Compliance Type Dropdown", By.xpath("//input[@name='omnibusTBEAddress']"));
    private static final ExtendedBy redemptionWalletField = new ExtendedBy("Compliance Type Dropdown", By.xpath("//input[@name='redemptionAddress']"));
    private static final ExtendedBy issuerWalletField = new ExtendedBy("Compliance Type Dropdown", By.xpath("//input[@name='Wallet Address 0']"));
    private static final ExtendedBy continueBtn = new ExtendedBy("Continue Btn", By.xpath("//span[contains(text(), 'Continue')]"));

    public CpManageRolesPage(Browser browser) {
        super(browser);
    }
    public void addMasterWalletAddress(String masterWalletAddress) { browser.typeTextElement(masterWalletAddressField, masterWalletAddress);}
    public void addSyncerWalletAddress(String syncerWalletAddress) { browser.typeTextElement(syncerWalletAddressField, syncerWalletAddress);}
    public void addSyncerWalletPk(String syncerWalletPk) { browser.typeTextElement(syncerWalletPkField, syncerWalletPk);}
    public void addRedemptionWallet(String redemptionWallet) { browser.typeTextElement(redemptionWalletField, redemptionWallet);}
    public void addIssuerWalletField(String issuerWallet) { browser.typeTextElement(issuerWalletField, issuerWallet);}
    public void addTbeWallet(String tbeWallet) { browser.typeTextElement(tbeWalletField, tbeWallet);}
    public void toggleHsmSyncer(){
        browser.click(syncerHsmToggle);
    }
    public void clickContinueBtn() {
        browser.click(continueBtn);
    }
}
