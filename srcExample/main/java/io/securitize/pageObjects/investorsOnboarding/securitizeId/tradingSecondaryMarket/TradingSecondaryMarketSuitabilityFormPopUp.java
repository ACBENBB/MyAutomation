package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketSuitabilityFormPopUp extends AbstractPage {
    private static final ExtendedBy answerSuitabilityFormButton = new ExtendedBy("Suitability form Popup answer button", By.xpath("//div[@class='Agreements form-group']"));

    public TradingSecondaryMarketSuitabilityFormPopUp(Browser browser) { super(browser, answerSuitabilityFormButton); }

    public void clickAnswerSuitabilityFormButton() {
        browser.click(answerSuitabilityFormButton, true);
    }
}
