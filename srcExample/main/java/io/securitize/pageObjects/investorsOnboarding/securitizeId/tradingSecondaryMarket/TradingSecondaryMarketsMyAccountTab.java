package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsMyAccountTab extends AbstractPage {

    private static final ExtendedBy linkedAccount = new ExtendedBy("Linked bank account", By.xpath("//div[text()='Linked bank account']"));
    private static final ExtendedBy UnlinkAccountLink = new ExtendedBy("Unlink Account Link", By.xpath("//span[@class='UnlinkAccount']/div/span[.='Unlink'][1]"));
    private static final ExtendedBy confirmUnlinkAccount = new ExtendedBy("Confirm unlink bank account button", By.xpath("//button[contains(text(),'Unlink Account')]"));
    private static final ExtendedBy typeColumn = new ExtendedBy("Type column", By.xpath("//th[text()='Type']"));

    public TradingSecondaryMarketsMyAccountTab(Browser browser) {
        super(browser, typeColumn);
    }

    public boolean assertAccountLinked() {
        boolean b = !browser.findElements(linkedAccount).isEmpty();
        return b;
    }

    public boolean assertUnlinkAccountLink() {
        boolean b = !browser.findElements(UnlinkAccountLink).isEmpty();
        return b;
    }

    public void clickUnlinkAccount() {
        browser.click(UnlinkAccountLink, false);
    }

    public void clickConfirmUnlinkAccount() {
        browser.click(confirmUnlinkAccount, false);
    }

}