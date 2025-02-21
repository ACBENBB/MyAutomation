package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieOwnedAssetTokenDetails extends AbstractPage {

    private static final ExtendedBy tokenDetailsTab = new ExtendedBy("token details tab", By.xpath("//div[contains(@class, 'token-details')]"));
    private static final ExtendedBy switchToInvestmentDetailsButton = new ExtendedBy("Switch to investment details button", By.id("investment-tab"));

    NieOwnedAssetTokenDetails(Browser browser) {
        super(browser, tokenDetailsTab);
    }

    public NieOwnedAssetDetails switchToInvestmentDetailsTab() {
        browser.click(switchToInvestmentDetailsButton, false);
        return new NieOwnedAssetDetails(browser);
    }
}