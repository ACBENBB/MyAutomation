package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class PrimaryOfferingRedirectSecuritizeMarketsPopUp extends AbstractPage {

    private static final ExtendedBy redirectedToSecuritizeMarketsTitle = new ExtendedBy("Title", By.xpath("//div[contains(@class,'step-current')]/div/h4"));
    private static final ExtendedBy description = new ExtendedBy("Description Step One", By.xpath("//p[contains(@class,'step__description-current')]"));
    private static final ExtendedBy btnContinue = new ExtendedBy("Continue button", By.id("market-accept"));
    private static final ExtendedBy imInterestedBtn = new ExtendedBy("Im Interested Btn", By.xpath("//button[@id='reverse-solicitation-modal-accept']"));

    public PrimaryOfferingRedirectSecuritizeMarketsPopUp(Browser browser) {
        super(browser);
    }

    public void clickImInterestedBtn() {
        browser.click(imInterestedBtn, false);
    }

    public String getTitleText() {
        return browser.getElementText(redirectedToSecuritizeMarketsTitle);
    }

    public String getDescriptionText() {
        return browser.getElementText(description);
    }

    public PrimaryOfferingRedirectSecuritizeMarketsPopUp waitForMarketPopUpFromUI() {
        browser.waitForElementVisibility(redirectedToSecuritizeMarketsTitle);
        browser.waitForElementVisibility(description);
        return this;
    }

    public PrimaryOfferingRedirectSecuritizeMarketsPopUp clickContinueBtn() {
        browser.waitForElementClickable(btnContinue, 5);
        browser.click(btnContinue, false);
        return this;
    }

    public void accessMarketWithInvestorCreatedFromUIStepOne() {
        waitForMarketPopUpFromUI();
        clickContinueBtn();
    }
}
