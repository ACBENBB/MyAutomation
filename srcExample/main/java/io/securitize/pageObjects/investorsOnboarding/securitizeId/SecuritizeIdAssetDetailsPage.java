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


public class SecuritizeIdAssetDetailsPage extends AbstractPage {

    private static final ExtendedBy arrowBackButton = new ExtendedBy("Securitize Id - Asset details page - Icon arrow back button", By.xpath("//span[contains(@class, 'asset-details__back-btn') and contains(text(), 'Back')]"));
    private static final ExtendedBy walletsTab = new ExtendedBy("Securitize Id - Wallets tab", By.xpath("//span[@class != 'sec-id-main-menu__title'  and text()= 'Wallets']"));
    private static final ExtendedBy withdrawButton = new ExtendedBy("Securitize Id - Asset details page - Withdraw button", By.xpath("(//span[text()='Withdraw'])[1]"));
    private static final ExtendedBy withdrawAmount = new ExtendedBy("Securitize Id - Asset details page - Withdraw amount input", By.id("withdraw-from-securitize"));

    private static final ExtendedBy transferButton = new ExtendedBy("Securitize Id - Asset details page - Transfer button", By.xpath("//button//span[text()='Transfer']"));
    private static final ExtendedBy transferAssetsMetamask = new ExtendedBy("Securitize Id - Asset details page - Metamask on Transfer Asset", By.xpath("//div[@id='metamask-wallet-select-card']"));
    private static final ExtendedBy gotItButton = new ExtendedBy("Securitize Id - Asset details page - Got it button", By.xpath("//button/span/span[text()='Got it']/../.."));

    private static final ExtendedBy metamaskWalletCard = new ExtendedBy("Securitize Id - Asset details page - Metamask wallet card from the UI", By.xpath("//div[text()= 'metamask 1']"));

    public SecuritizeIdAssetDetailsPage(Browser browser) {
        super(browser, arrowBackButton);
    }

    public SecuritizeIdAssetDetailsPage clickWalletsTab() {
        browser.click(walletsTab, false);
        return this;
    }

    public SecuritizeIdAssetDetailsPage clickWithdraw() {
        browser.click(withdrawButton, false);
        return this;
    }

    public SecuritizeIdAssetDetailsPage fillWithdrawAmount(String tokensAmount) {
        browser.typeTextElement(withdrawAmount, tokensAmount);
        return this;
    }

    public SecuritizeIdAssetDetailsPage clickTransfer() {
        browser.click(transferButton, false);
        return this;
    }

    public SecuritizeIdAssetDetailsPage clickGotIt() {
        var elements = browser.findElements(gotItButton);
        if (!elements.isEmpty()) {
            browser.click(gotItButton, false);
        }
        return this;
    }

    public void transferAssetsMetamaskClick(){
        browser.click(transferAssetsMetamask, false);
    }

    public void waitForWalletToContainTokensTransferredFromTBE() {
        Function<String, Boolean> internalWaitForWalletToContainTokenTransferredFromTBE = t -> {
            Optional<WebElement> optionalElement = browser.findFirstVisibleElementQuick(metamaskWalletCard, 5);
            if (optionalElement.isPresent()) {
                return true;
            } else {
                MultiReporter.infoAndShowMessageInBrowser("Waiting wallet to contain tokens transferred from TBE (asset was authorized and withdrawed)");
                browser.refreshPage(true);
                browser.click(walletsTab);
            }
            return false;
        };

        String description = "waitForWalletToContainTokens";
        Browser.waitForExpressionToEqual(internalWaitForWalletToContainTokenTransferredFromTBE, null, true, description, 3600, 5000, new WalletNotReadyTimeoutException("Wallet does not contain tokens even after timeout"));
    }

}