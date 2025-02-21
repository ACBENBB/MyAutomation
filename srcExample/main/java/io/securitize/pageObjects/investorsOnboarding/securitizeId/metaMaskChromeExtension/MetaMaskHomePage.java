package io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension;

import io.securitize.infra.enums.MetaMaskEthereumNetwork;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.error;

public class MetaMaskHomePage extends AbstractPage<MetaMaskHomePage> {

    private static final ExtendedBy closeTokenSwappingWindowButton = new ExtendedBy("Close token swapping window - button", By.xpath("//button[@title='Close']"));
    private static final ExtendedBy openNetworkPickerButton = new ExtendedBy("Open network picker button", By.xpath("//*[@data-testid='network-display']"));
    private static final ExtendedBy networkPickerOptions = new ExtendedBy("Network picker options", By.xpath("//div[contains(@class, 'mm-box multichain-network-list-item')]/button"));

    private static final ExtendedBy depositButton = new ExtendedBy("Deposit button", By.xpath("//button[text()='Deposit']"));
    private static final ExtendedBy getEtherFromTestFaucetButton = new ExtendedBy("Get ether from test faucet button", By.xpath("//button[text()='Get Ether']"));

    private static final ExtendedBy currentAccountMenuButton = new ExtendedBy("Current account menu button", By.xpath("//button[@data-testid='account-options-menu-button']"));
    private static final ExtendedBy accountDetailsButton = new ExtendedBy("Account details button", By.xpath("//button[@data-testid= 'account-list-menu-details']"));
    private static final ExtendedBy walletAddressField = new ExtendedBy("Wallet address field", By.xpath("//div[@class='qr-code']/div/div/button"));
    private static final ExtendedBy walletAddressModal = new ExtendedBy("Wallet address modal", By.xpath("(//button[contains(@class, 'mm-button-icon')])[2]"));

    private static final ExtendedBy mainMenuButton = new ExtendedBy("Main menu button", By.xpath("//button[@data-testid='account-menu-icon']"));
    private static final ExtendedBy addAccountButton = new ExtendedBy("Main menu -> Add account or hardware wallet button", By.xpath("//button[@data-testid= 'multichain-account-menu-popover-action-button']"));
    private static final ExtendedBy importAccountButton = new ExtendedBy("Main menu -> Add account or hardware wallet button -> import account button", By.xpath("//button[text()= 'Import account']"));
    private static final ExtendedBy privateKeyField = new ExtendedBy("Import account -> private key field", By.xpath("//input[@id='private-key-box']"));
    private static final ExtendedBy importButton = new ExtendedBy("Import account -> import Button", By.xpath("//button[@data-testid= 'import-account-confirm-button']"));

    private static final ExtendedBy walletBalanceField = new ExtendedBy("Wallet balance field", By.xpath("//span[contains(@class, 'currency-display-component__text')]"));
    private static final ExtendedBy sendEthButton = new ExtendedBy("Send eth button", By.xpath("//button[@data-testid='eth-overview-send']"));
    private static final ExtendedBy recipientAddressField = new ExtendedBy("Recipient Address Field", By.xpath("//input[@data-testid='ens-input']"));
    private static final ExtendedBy sendAmountField = new ExtendedBy("Send amount field", By.xpath("//input[@class='unit-input__input']"));
    private static final ExtendedBy nextButton = new ExtendedBy("Next button", By.xpath("//button[@data-testid='page-container-footer-next']"));
    private static final ExtendedBy showHideTestNetworks = new ExtendedBy("Show/hide test networks", By.xpath("//a[contains(@class, 'network-dropdown-content')]"));
    private static final ExtendedBy toggleShowTestNetworks = new ExtendedBy("Show/hide test networks settings checkbox", By.xpath("(//label[@class= 'toggle-button toggle-button--off']/div/div)[1]"));
    private static final ExtendedBy closeSettingsScreen = new ExtendedBy("Close settings button", By.xpath("(//button[contains(@class, 'mm-button-icon')])[2]"));

    private static final String accountSelectorTemplate = "//button[contains(@class, 'multichain-account-list-item') and contains(text(), '%s')]";
    private static final ExtendedBy closePopupButton = new ExtendedBy("Close popup button", By.xpath("//button[@data-testid='popover-close']"));
    private static final ExtendedBy cancelButton = new ExtendedBy("Footer Cancel button", By.xpath("//button[@data-testid='page-container-footer-cancel']"));

    private static final ExtendedBy importTokensButton = new ExtendedBy("Import Tokens button", By.xpath("//button[@data-testid='import-token-button']"));
    private static final ExtendedBy tokenAddressField = new ExtendedBy("Import Tokens -> Token Address Field", By.xpath("//input[@data-testid='import-tokens-modal-custom-address']"));
    private static final ExtendedBy importTokensNextButton = new ExtendedBy("Import Tokens -> Next button", By.xpath("//button[text()='Next']"));
    private static final ExtendedBy importTokensImportButton = new ExtendedBy("Import Tokens -> Import button", By.xpath("//button[@data-testid='import-tokens-modal-import-button']"));

    private static final String tokenSymbolField = "//input[@data-testid='import-tokens-modal-custom-symbol' and @value='%s']";

    public MetaMaskHomePage(Browser browser) {
        super(browser, openNetworkPickerButton);
    }

    public static MetaMaskHomePage openMetaMaskInNewTab(Browser browser) {
        browser.openNewTabAndSwitchToIt();
        String metamaskUrl = browser.getInstalledExtensionUrl("MetaMask");
        browser.navigateTo(metamaskUrl + "/home.html#initialize/welcome");

        return new MetaMaskHomePage(browser);
    }

    public MetaMaskHomePage closeTokenSwappingWindow() {
        if (browser.findElementsQuick(closeTokenSwappingWindowButton, 1).size() > 0) {
            browser.click(closeTokenSwappingWindowButton, false);
        }
        return this;
    }

    public MetaMaskHomePage pickNetwork(String name) {
        browser.click(openNetworkPickerButton, false);
        List<WebElement> networks = browser.findElements(networkPickerOptions);

        // if test network are hidden
        if (networks.size() < 3) {
            showTestNetworks();
            browser.click(openNetworkPickerButton, false);
            networks = browser.findElements(networkPickerOptions);
        }

        for (WebElement currentElement : networks) {
            if (browser.getElementText(currentElement, "current network option").equals(name)) {
                browser.click(currentElement, "network: " + name, false);
                return this;
            }
        }

        throw new RuntimeException("Requested network not found: " + name);
    }

    private void showTestNetworks() {
        browser.clickWithJs(browser.findElement(toggleShowTestNetworks));
        browser.clickWithJs(browser.findElement(closeSettingsScreen));
    }

    @SuppressWarnings("unused")
    public MetaMaskHomePage clickDepositButton() {
        browser.click(depositButton);
        return this;
    }

    @SuppressWarnings("unused")
    public MetaMaskHomePage clickGetEtherFromTestFaucet() {
        browser.click(getEtherFromTestFaucetButton);
        return this;
    }

    public String getWalletAddress() {
        // Open menu of options
        browser.click(currentAccountMenuButton, false);
        // Open account details page
        browser.click(accountDetailsButton, false);
        // Get the wallet address
        String walletAddress = browser.findFirstVisibleElementQuick(walletAddressField, 3).get().getText();
        // Close the modal
        browser.click(walletAddressModal, false);

        return walletAddress;
    }

    public MetaMaskHomePage importWallet(String privateKey) {
        browser.click(mainMenuButton, false);
        browser.click(addAccountButton, false);
        browser.click(importAccountButton, false);
        browser.typeTextElement(privateKeyField, privateKey);
        browser.click(importButton, false);
        return this;
    }

    @SuppressWarnings("unused")
    public double getCurrentWalletBalance() {
        String resultAsString = browser.getElementText(walletBalanceField);
        return RegexWrapper.stringToDouble(resultAsString);
    }

    public void sendToWallet(double amount, String recipientAddress) {
        browser.click(sendEthButton, false);
        browser.typeTextElement(recipientAddressField, recipientAddress);
        browser.typeTextElement(sendAmountField, amount + "");
        browser.click(nextButton, false); // click next

        browser.click(nextButton, false); // click confirm
    }

    public MetaMaskHomePage waitForWalletBalanceToLoad() {
        try {
            browser.waitForElementTextValueNotZero(walletBalanceField);
        } catch (TimeoutException e) {
            String message = "Timeout occur waiting for wallet balance to load.. Are you sure this wallet has funds?";
            error(message, true);
            throw new TimeoutException(message, e);
        }
        return this;
    }

    public MetaMaskHomePage selectAccountByName(String name) {
        browser.click(mainMenuButton, false);
        String fullSelector = String.format(accountSelectorTemplate, name);
        browser.click(new ExtendedBy("Account selector: " + name, By.xpath(fullSelector)), false);
        return this;
    }

    public MetaMaskHomePage importWalletAndSendFounds(String importedWalletPrivateKey, double amount, String targetAddress) {
        importWallet(importedWalletPrivateKey)
                .pickNetwork(MetaMaskEthereumNetwork.Sepolia.toString())
                .waitForWalletBalanceToLoad()
                .sendToWallet(amount, targetAddress);
        // wait for transaction to complete
        return this;
    }

    public MetaMaskHomePage closePopup() {
        if (browser.findElementsQuick(closePopupButton, 1).size() > 0) {
            browser.click(closePopupButton, false);
        }
        return this;
    }

    public MetaMaskHomePage clickCancelButton() {
        browser.click(cancelButton);
        return this;
    }

    public MetaMaskHomePage importToken(String tokenAddress, String tokenTicker) {
        browser.click(importTokensButton);
        browser.typeTextElement(tokenAddressField, tokenAddress);
        browser.waitForElementVisibility(new ExtendedBy("tokenSymbolField", By.xpath(String.format(tokenSymbolField, tokenTicker))));
        browser.click(importTokensNextButton);
        browser.click(importTokensImportButton);
        return this;
    }

    public boolean isTokenFrozen(double amount, String recipientAddress) {
        browser.click(sendEthButton, false);
        browser.typeTextElement(recipientAddressField, recipientAddress);
        browser.typeTextElement(sendAmountField, amount + "");
        browser.waitForPageStable(Duration.ofSeconds(2));
        return !browser.isElementEnabled(nextButton);
    }
}