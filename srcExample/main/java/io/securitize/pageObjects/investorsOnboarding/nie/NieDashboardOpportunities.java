package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieDashboardOpportunities extends AbstractPage {

    private static final ExtendedBy opportunityRow = new ExtendedBy("Opportunity row", By.xpath("//div[contains(@class, 'opportunity-card__row row')]"));
    private static final String viewOpportunityDetailsLocatorTemplate = "//div[contains(@class, 'opportunity-card__row row')]//div[contains(text(), '%s')]/../div/button";

    public NieDashboardOpportunities(Browser browser) {
        super(browser, opportunityRow);
    }

    public NieInvestmentDetails clickViewInvestmentDetailsByName(String name) {
        ExtendedBy viewOpportunityDetailsButton = new ExtendedBy("Opportunity details: " + name, By.xpath(String.format(viewOpportunityDetailsLocatorTemplate, name)));
        browser.click(viewOpportunityDetailsButton);
        return new NieInvestmentDetails(browser);
    }

    public void reopenInvestmentByName(String name) {
        ExtendedBy viewOpportunityDetailsButton = new ExtendedBy("Opportunity details: " + name, By.xpath(String.format(viewOpportunityDetailsLocatorTemplate, name)));
        browser.click(viewOpportunityDetailsButton);
    }
}