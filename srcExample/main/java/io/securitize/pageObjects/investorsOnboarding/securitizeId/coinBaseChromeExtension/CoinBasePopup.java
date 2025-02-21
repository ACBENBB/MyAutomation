package io.securitize.pageObjects.investorsOnboarding.securitizeId.coinBaseChromeExtension;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CoinBasePopup extends AbstractPage {

    private static final ExtendedBy singCoinBaseWalletButton = new ExtendedBy("List of words", By.xpath("//*[@data-testid='sign-message']"));

    public CoinBasePopup(Browser browser) {
        super(browser, singCoinBaseWalletButton);
    }




    public CoinBasePopup signCoinbaseWallet() {
        browser.click(singCoinBaseWalletButton, false);
        return this;
    }









}
