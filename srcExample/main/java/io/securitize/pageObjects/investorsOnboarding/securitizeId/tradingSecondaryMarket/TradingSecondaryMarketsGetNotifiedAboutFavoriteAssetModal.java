package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal extends AbstractPage {

    private static final ExtendedBy watchlistTitle = new ExtendedBy("Watchlist title", By.xpath("//h2[@class=' watchlistTitle ']"));
    private static final ExtendedBy confirmButton = new ExtendedBy("Confirm button", By.xpath("//button[@data-test-id='watchlist-modal-button']"));
    private static final ExtendedBy toast = new ExtendedBy("Toast", By.xpath("//*[@class='securitize-toast__body__content']"));


    public TradingSecondaryMarketsGetNotifiedAboutFavoriteAssetModal(Browser browser) {
        super(browser);
    }

    public String getWatchlistTitle(){
        browser.waitForElementVisibility(watchlistTitle);
        return browser.getElementText(watchlistTitle);
    }

    public void clickConfirmButton() {
        browser.click(confirmButton);
    }

    public String getToastMessage(){
        return browser.getElementText(toast);
    }

    public boolean isToastVisible(){
        browser.waitForElementVisibility(toast);
        return browser.isElementVisible(toast);
    }

    public void clickToast() {
        browser.click(toast);
        browser.waitForElementToVanish(toast);
    }

}
