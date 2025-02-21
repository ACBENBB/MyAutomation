package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawSelectWalletModalPage extends AbstractPage {

    private static final ExtendedBy withdrawModalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy continueButton = new ExtendedBy("Continue button", By.cssSelector("[class*='ButtonComponent']"));
    private static final ExtendedBy walletCard = new ExtendedBy("Wallet card", By.xpath("//div[contains(text(), 'metamask 1')]"));

    public WithdrawSelectWalletModalPage(Browser browser) {
        super(browser, withdrawModalBody);
    }
    public void clickContinueButton() {
        browser.click(continueButton);
    }

    public void clickWallet() {
        browser.click(walletCard);
    }
    public WithdrawUSDCModalPage selectFirstWallet() {
        clickWallet();
        clickContinueButton();

        return new WithdrawUSDCModalPage(browser);
    }

}
