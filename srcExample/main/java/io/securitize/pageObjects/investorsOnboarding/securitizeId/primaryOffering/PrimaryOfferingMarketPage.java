package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class PrimaryOfferingMarketPage extends AbstractPage {

    private static final ExtendedBy marketOpportunityList = new ExtendedBy("Market opportunity list", By.xpath("//div[contains(@class,'market__container')]"));
    private static final String opportunityTemplate = "//span[contains(text(), '%s')]";

    private static final String opportunityTitle = "//h1[text()='%s'] | //span[text()='%s']";
    private static final String opportunityCardTemplate = "//div/*[contains(text(), '%s')]";

    //opportunities production selectors
    private static final ExtendedBy KKROpportunityTextCard = new ExtendedBy("KKR Health Care Growth Opportunity card", By.xpath("//*[contains(text(), 'KKR Health Care Growth')]"));
    private static final ExtendedBy HamiltonLaneSeniorCreditOpportunityTextCard = new ExtendedBy("Hamilton Lane Senior Credit Opportunity card", By.xpath("//*[contains(text(), 'Hamilton Lane Senior Credit Opportunities Securitize Fund')]"));
    private static final ExtendedBy HamiltonLaneEOVOpportunityTextCard = new ExtendedBy("Hamilton Lane Equity Opportunity card", By.xpath("//*[contains(text(), 'Hamilton Lane Equity Opportunities Securitize')]"));
    private static final ExtendedBy SnPCryptocurrencyOpportunityTextCard = new ExtendedBy("S&P Cryptocurrency Large Cap Ex Mega Fund Opportunity card", By.xpath("//*[contains(text(), 'Securitize S&P Cryptocurrency Large')]"));


    public PrimaryOfferingMarketPage(Browser browser) {
        super(browser, marketOpportunityList);
    }

    public void clickOnPrimaryOfferingsOpp(String opportunityName) {
        ExtendedBy opportunity = new ExtendedBy("Invest in the opportunity given: " + opportunityName, By.xpath(String.format(opportunityTemplate, opportunityName)));
        browser.click(opportunity, false);
    }

    public void clickOnPrimaryOfferingsCardOpp(String opportunityName) {
        ExtendedBy opportunity = new ExtendedBy("Invest in the opportunity given: " + opportunityName, By.xpath(String.format(opportunityCardTemplate, opportunityName)));
        browser.click(opportunity, false);
    }

    public boolean isPrimaryOfferingsOppVisible(String opportunityName) {
        ExtendedBy opportunity = new ExtendedBy("Wait for opportunity to be visible: " + opportunityName, By.xpath(String.format(opportunityTitle, opportunityName, opportunityName)));
        browser.scrollToBottomOfElement(opportunity);
        return browser.isElementVisible(opportunity);
    }

    public void clickKKROpportunityCard() {
        browser.click(KKROpportunityTextCard);
    }

    public void clickHamiltonLaneSeniorCreditOpportunityCard() {
        browser.click(HamiltonLaneSeniorCreditOpportunityTextCard);
    }

    public void clickHamiltonLaneEOVOpportunityCard() {
        browser.click(HamiltonLaneEOVOpportunityTextCard);
    }

    public void clickSnPCryptocurrencyOpportunityCard() {
        browser.click(SnPCryptocurrencyOpportunityTextCard);
    }

}