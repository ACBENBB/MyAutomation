package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.exceptions.WalletNotReadyTimeoutException;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;
import java.util.function.Function;

public class SecuritizeIdAddWallet extends AbstractPage<SecuritizeIdAddWallet> {
    //SID - general wallets page
    private static final ExtendedBy pageRegisterWalletButton = new ExtendedBy("Securitize Id - register wallet button inside page", By.xpath("//*[text() = 'Register wallet' or text() = 'Connect Wallet']"));
    private static final ExtendedBy modalPageExitButton = new ExtendedBy("Securitize Id - exit button inside register wallet's modal inside page", By.xpath("//button[@type = 'button' and @class ='close']"));
    private static final ExtendedBy continueButtonModalPageRegisterWallet = new ExtendedBy("Securitize Id - continue button inside register wallet's modal inside page", By.xpath("//span[text() = 'Continue']"));

    // SID - select Ethereum wallet
    private static final ExtendedBy addMetamaskWalletSelect = new ExtendedBy("Securitize Id - Add a wallet select - Metamask", By.xpath("//div[@class= 'wallet-provider']/span[text()= 'Metamask']"));
    private static final ExtendedBy addCoinbaseWalletSelect = new ExtendedBy("Securitize Id - Add a wallet select - Coinbase", By.xpath("//div[@class= 'wallet-provider']/span[text()= 'Coinbase Wallet']"));
    private static final ExtendedBy addWalletConnectWalletSelect = new ExtendedBy("Securitize Id - Add a wallet select - Wallet Connect", By.xpath("//div[@class= 'wallet-provider']/span[text()= 'Wallet Connect']"));
    private static final ExtendedBy addEthereumWalletSelect = new ExtendedBy("Securitize Id - Add a wallet select - Ethereum", By.xpath("//div[@class='modal-content']//*[text() = 'Ethereum']"));

    private static final ExtendedBy metaMaskWallet = new ExtendedBy("Securitize Id - Meta mask wallet", By.xpath("//span[text() = 'Metamask']/.."));
    private static final ExtendedBy coinBaseWallet = new ExtendedBy("Securitize Id - CoinBase wallet", By.id("coinbase-wallet-select-box"));
    private static final ExtendedBy walletWelcomePopupUnderstoodButton = new ExtendedBy("Store your digital securities.. - popup - understood button", By.xpath("//button[@class = 'ladda-button securitize-button securitize-button__lg securitize-button__primary securitize-button__primary btn-block wallet-registration-tutorial-modal__button']"));
    private static final ExtendedBy addAssetButton = new ExtendedBy("Add asset", By.xpath("(//div[text()='Add Asset'])[1]"));
    private static final ExtendedBy firstAsset = new ExtendedBy("First asset to authorize", By.xpath("//div[contains(@class, 'wallet-tokens-select__card__token')]"));
    private static final ExtendedBy authorizeAssetsMetamask = new ExtendedBy("Metamask on Authorize Asset", By.xpath("//div[@id='metamask-wallet-select-card']"));
    private static final ExtendedBy authorizeAssetGotItButton = new ExtendedBy("Authorize asset got it button", By.xpath("//button/span[text()='Got it']"));
    private static final ExtendedBy authorizeAssetsPopup = new ExtendedBy("Authorize assets popup", By.xpath("//div[contains(@class, 'modal-content')]"));
    private static final ExtendedBy walletUnitsValuedZero = new ExtendedBy("Wallet units valued '0' from the UI", By.xpath("(//span[text() = '0'])[1]"));
    public static final ExtendedBy[] visualIgnoreList = {};


    public SecuritizeIdAddWallet(Browser browser) {
        super(browser, pageRegisterWalletButton);
    }

    public SecuritizeIdAddWallet closeWalletPopUP() {
        browser.click(walletWelcomePopupUnderstoodButton, false);
        return this;
    }

    public void closeModalPageRegisterWalletPopup() {
        browser.click(modalPageExitButton, false);
    }

    public SecuritizeIdAddWallet clickRegisterWallet() {
        browser.click(pageRegisterWalletButton, false);
        return this;
    }

    public SecuritizeIdAddWallet clickAddEtherumWallet() {
        browser.click(addEthereumWalletSelect);
        browser.click(continueButtonModalPageRegisterWallet);
        return this;
    }

    public void selectMetaMask() {
        browser.click(metaMaskWallet, false);
    }

    public void selectCoinBase() {
        browser.click(coinBaseWallet, false);
    }

    public SecuritizeIdAddWallet waitForAndClickFirstAsset() {
        Function<String, Boolean> internalWaitForInvestorToBecomeOwnerOfTBE = t -> {
            addAssetButtonClick();
            browser.waitForElementVisibility(authorizeAssetsPopup);

            Optional<WebElement> optionalElement = browser.findFirstVisibleElementQuick(firstAsset, 5);
            if (optionalElement.isPresent()) {
                browser.click(optionalElement.get(), firstAsset.getDescription(), false);
                return true;
            } else {
                MultiReporter.infoAndShowMessageInBrowser("Waiting investor to become TBE owner (already success in CP)");
                browser.refreshPage(true);
            }
            return false;
        };


        String description = "waitForInvestorToHaveTBE";
        Browser.waitForExpressionToEqual(internalWaitForInvestorToBecomeOwnerOfTBE, null, true, description, 180, 10000);
        return this;
    }

    public void addAssetButtonClick() {
        browser.click(addAssetButton, false);
    }

    public void authorizeAssetsMetamaskClick() {
        browser.click(authorizeAssetsMetamask, false);
    }

    public SecuritizeIdAddWallet clickAuthorizeAssetGotItButton() {
        browser.click(authorizeAssetGotItButton, false);
        return this;
    }

    public void waitForWalletToBeReady() {
        Function<String, Boolean> internalWaitForInvestorToBecomeOwnerOfTBE = t -> {
            Optional<WebElement> optionalElement = browser.findFirstVisibleElementQuick(walletUnitsValuedZero, 5);
            if (optionalElement.isPresent()) {
                return true;
            } else {
                MultiReporter.infoAndShowMessageInBrowser("Waiting wallet to be ready (asset authorized)");
                browser.refreshPage(true);
            }
            return false;
        };

        String description = "waitForWalletToBeReady";
        Browser.waitForExpressionToEqual(internalWaitForInvestorToBecomeOwnerOfTBE, null, true, description, 3600, 5000, new WalletNotReadyTimeoutException("Wallet not ready (asset authorized) even after timeout"));
    }

    public boolean isMetamaskVisible() {
        browser.waitForElementVisibility(addMetamaskWalletSelect);
        return browser.isElementVisible(addMetamaskWalletSelect);
    }

    public boolean isCoinbaseVisible() {
        browser.waitForElementVisibility(addCoinbaseWalletSelect);
        return browser.isElementVisible(addCoinbaseWalletSelect);
    }

    public boolean isWalletConnectVisible() {
        browser.waitForElementVisibility(addWalletConnectWalletSelect);
        return browser.isElementVisible(addWalletConnectWalletSelect);
    }
}
