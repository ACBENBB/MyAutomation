package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WalletSelectionPage extends AbstractPage {

    private static final ExtendedBy walletIdDropdown = new ExtendedBy("Select Wallet Dropbox", By.xpath("//select[@name='walletId']"));
    private static final ExtendedBy walletAddressCheckBox = new ExtendedBy("Confirm Wallet Address Checkbox", By.xpath("//label[@for='walletCheck']"));
    private static final ExtendedBy walletSelectionSubmitBtn = new ExtendedBy("Wallet Selection Submit Btn", By.xpath("//button[@name='wallet-selection-submit']"));

    public WalletSelectionPage(Browser browser) {
        super(browser, walletIdDropdown);
    }

    public WalletSelectionPage selectWalletAddress(String walletId) {
        browser.selectElementByVisibleText(walletIdDropdown, walletId);
        return this;
    }

    public WalletSelectionPage selectConfirmWalletAddressCheckBox() {
        browser.click(walletAddressCheckBox, false);
        return this;
    }

    public PayoutPreferencePage clickWalletSelectionConfirm() {
        browser.click(walletSelectionSubmitBtn, false);
        return new PayoutPreferencePage(browser);
    }

}