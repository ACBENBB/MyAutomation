package io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension;

import io.securitize.infra.exceptions.InsufficientFundsException;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.warning;


public class MetaMaskConnectWithMetaMask extends AbstractPage {

    private static final ExtendedBy nextButton = new ExtendedBy("Next button", By.xpath("//button[text()='Next']"));
    private static final ExtendedBy connectButton = new ExtendedBy("Connect button", By.xpath("//button[@data-testid='page-container-footer-next']"));
    private static final ExtendedBy signButton = new ExtendedBy("Sign button", By.xpath("//button[text()= 'Sign']"));
    private static final ExtendedBy confirmButton = new ExtendedBy("Confirm button", By.xpath("//button[text()='Confirm']"));
    private static final ExtendedBy notHavingEnoughSepoliaGasMessage = new ExtendedBy("Not having enough Sepolia gas message", By.xpath("//p[text() = 'You do not have enough SepoliaETH in your account to pay for transaction fees on Sepolia network. Deposit SepoliaETH from another account.']"));
    private static final ExtendedBy switchNetworkButton = new ExtendedBy("Switch network button", By.xpath("//button[contains(text(), 'Switch network')]"));


    public MetaMaskConnectWithMetaMask(Browser browser) {
        super(browser, nextButton);
    }

    public MetaMaskConnectWithMetaMask clickNext() {
        browser.clickAndWaitForElementToVanish(nextButton, ExecuteBy.WEBDRIVER, false);
        return this;
    }

    public MetaMaskConnectWithMetaMask clickConnect() {
        browser.clickAndWaitForElementToVanish(connectButton, ExecuteBy.WEBDRIVER, false);
        return this;
    }

    public void clickSign() {
        try {
            browser.click(signButton, false);
        } catch (TimeoutException e) {
            warning("Sign MetaMask wallet wasn't found.. Trying again with latest window...", false);
            browser.switchToLatestWindow();
            browser.click(signButton, false);
        }
    }

    public void clickConfirm() {
        try {
            if(browser.isElementPresent(notHavingEnoughSepoliaGasMessage, 10)){
                throw new InsufficientFundsException("Not enough gas lest to pay for transaction fees on Sepolia network");
            }
            browser.click(confirmButton, false);
        } catch (TimeoutException e) {
            info("Confirm MetaMask wallet wasn't found.. Trying again with latest window...");
            browser.switchToLatestWindow();
            browser.click(confirmButton, false);
        }
    }

    public void connectAndSign() {
        clickNext();
        clickConnect();

        // due to bug, we sometimes have to do this twice
        if (browser.isElementVisibleQuick(nextButton, 8)) {
            warning("Workaround: Metamask issue - should be resolve - only click -next once! ", false);
            clickNext();
            clickConnect();
        }

        // due to bug, we sometimes have to do this twice
        if (browser.isElementVisibleQuick(switchNetworkButton, 8)) {
            browser.click(switchNetworkButton);
        }

        clickSign();
    }
}