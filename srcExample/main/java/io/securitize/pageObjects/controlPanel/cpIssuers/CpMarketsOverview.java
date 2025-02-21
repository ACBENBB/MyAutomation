package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

import java.time.Duration;

public class CpMarketsOverview extends AbstractPage {

    private static final ExtendedBy moPageHeader = new ExtendedBy("Markets Overview Page Header", By.xpath("//div[@id='markets_overview-frame']//h4[contains(text(), 'Securitize Markets Overview')]"));

    private static final ExtendedBy searchOpportunityTextBox = new ExtendedBy("Search for opportunity", By.xpath("//*[contains(@placeholder, 'Search by name')]"));

    private static final ExtendedBy marketsOverviewCards = new ExtendedBy("Markets Overview total cards", By.xpath("//div[@class='MuiPaper-root MuiPaper-outlined MuiPaper-rounded MuiCard-root OpportunityCard css-ksg9m3']"));
    private static final ExtendedBy marketsOverviewCardsTab = new ExtendedBy("Cards Tab", By.xpath("//button[contains(text(), 'Cards')]"));
    private static final ExtendedBy marketsOverviewInvestorsTab = new ExtendedBy("Investors Tab", By.xpath("//button[contains(text(), 'Investors')]"));
    private static final ExtendedBy marketsOverviewOpportunitySelector = new ExtendedBy("Opportunity Dropdown Selector", By.xpath("//div[@translatekeystring='MARKETS_OVERVIEW.INVESTORS.FILTER.OPPORTUNITY.PLACEHOLDER']/div[@id='mui-component-select-opportunityId']"));
    private static final String opportunityNameSelectorTemplate = "//*[contains(text(), '%s')]//parent::li";

    public CpMarketsOverview(Browser browser) {
        super(browser, moPageHeader);
    }

    public void searchOpportunityByTextBox(String opportunityName){
        browser.click(searchOpportunityTextBox).clear();
        browser.click(searchOpportunityTextBox).sendKeys(opportunityName);
        browser.waitForElementTextNotEmpty(searchOpportunityTextBox);
    }

    public void assertNumberOfCards (int numberOfCards) {
        browser.waitForElementCount(marketsOverviewCards, numberOfCards);
    }

    public CpMarketsOverviewInvestorsTab clickInvestorsTab() {
        browser.click(marketsOverviewInvestorsTab);
        return new CpMarketsOverviewInvestorsTab(browser);
    }

    public CpMarketsOverview selectOpportunity(String opportunity) {
        browser.waitForPageStable(Duration.ofSeconds(5));
        browser.click(marketsOverviewOpportunitySelector);
        browser.waitForPageStable(Duration.ofSeconds(5));
        browser.click(getOpportunityNameFromDropdown(opportunity));
        return new CpMarketsOverview(browser);
    }

    private ExtendedBy getOpportunityNameFromDropdown(String opportunityName) {
        String opportunityXpath = String.format(opportunityNameSelectorTemplate, opportunityName);
        return new ExtendedBy("Opportunity Name", By.xpath(opportunityXpath));
    }
}
