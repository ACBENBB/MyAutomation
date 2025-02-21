package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.*;

import java.util.List;

public class TradingSecondaryMarketsMyAccountButton extends AbstractPage {
    private static final ExtendedBy userIcon = new ExtendedBy("User icon", By.xpath("//i[@class='d-flex icon-individual-wrapper']"));
    private static final ExtendedBy logoutLink = new ExtendedBy("Logout link", By.xpath("//span[.='Log out']"));
    private static final ExtendedBy sidNavBarTradingBtn = new ExtendedBy("Sid NavBar Trading Btn", By.xpath("//a[@id='home-page-nav-secondary-market']"));



    public TradingSecondaryMarketsMyAccountButton(Browser browser) {
        super(browser, userIcon);
    }

    public TradingSecondaryMarketsMyAccountButton clickUserIcon() {
        browser.click(userIcon, false);
        return this;
    }

    public TradingSecondaryMarketsMyAccountButton clickLogoutLink() {
        browser.click(logoutLink, false);
        browser.waitForPageStable();
        return this;
    }

    public void waitForLogoutToComplete() {
        List<WebElement> elements = browser.findElementsQuick(sidNavBarTradingBtn, 5);
        if (!elements.isEmpty()) {
            browser.waitForElementToVanish(elements.get(0), "Sid NavBar Trading Btn");
        }
    }
}