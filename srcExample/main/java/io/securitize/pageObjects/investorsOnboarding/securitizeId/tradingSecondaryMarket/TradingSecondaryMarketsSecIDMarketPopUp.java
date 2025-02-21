package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsSecIDMarketPopUp extends AbstractPage {
    private static final ExtendedBy dontShowAgainCheckbox = new ExtendedBy("Don't show message again", By.id("market-dont-show"));
    private static final ExtendedBy acceptRedirectModal = new ExtendedBy("Close redirect modal", By.id("market-accept"));

    public TradingSecondaryMarketsSecIDMarketPopUp(Browser browser) { super(browser); }

    public TradingSecondaryMarketsSecIDMarketPopUp acceptRedirectModal(){
        browser.click(acceptRedirectModal,false);
        return this;
    }
}
