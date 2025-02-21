package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsAssetInfoTab <R> extends AbstractPage<R> {

    private static final ExtendedBy firstCard = new ExtendedBy("Type column", By.xpath("//*[@class='InfoSection']//*[@class='TitleCardHeader']"));

    public TradingSecondaryMarketsAssetInfoTab(Browser browser) {
        super(browser, firstCard);
    }

    public String getFirstCardTitle() {
        return browser.getElementText(firstCard);
    }

}