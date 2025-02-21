package io.securitize.pageObjects.jp.abstractClass;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;

public abstract class AbstractJpDashboardOpportunities extends AbstractJpPage {

    private static final ExtendedBy opportunityRow = new ExtendedBy("Opportunity row", By.xpath("//*[contains(@class,'Content')]"));
    private static final String VIEW_OPPORTUNITY_DETAILS_LOCATOR_TEMPLATE = "//*[contains(@class,'OpportunityCard') and contains(.,'%s')]/div/button";

    protected AbstractJpDashboardOpportunities(Browser browser, ExtendedBy extendedBy) {
        super(browser, extendedBy);
    }

    public void clickViewInvestmentDetailsByName(String name) {
        ExtendedBy viewOpportunityDetailsButton = new ExtendedBy("Opportunity details: " + name, By.xpath(String.format(VIEW_OPPORTUNITY_DETAILS_LOCATOR_TEMPLATE, name)));
        browser.clickWithJs(browser.findElement(viewOpportunityDetailsButton));
    }

    public void clickViewInvestmentDetailsByName(String name, boolean retry) {
        retryFunctionWithRefreshPage(()-> {clickViewInvestmentDetailsByName(name); return null;}, retry);
    }

    public void reopenInvestmentByName(String name) {
        ExtendedBy viewOpportunityDetailsButton = new ExtendedBy("Opportunity details: " + name, By.xpath(String.format(VIEW_OPPORTUNITY_DETAILS_LOCATOR_TEMPLATE, name)));
        browser.click(viewOpportunityDetailsButton);
    }
}
