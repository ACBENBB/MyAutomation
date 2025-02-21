package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsConfirmationTradeModal extends AbstractPage {
    private static final ExtendedBy congratsModal = new ExtendedBy("Congrats modal", By.className("title"));
    private static final ExtendedBy gotItButton = new ExtendedBy("Got it button", By.xpath("//button[text()='Got it']"));

    public TradingSecondaryMarketsConfirmationTradeModal(Browser browser) { super(browser, congratsModal); }

    public void clickGotItButton() {
        browser.click(gotItButton, false);
    }
}
