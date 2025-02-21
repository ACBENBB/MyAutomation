package io.securitize.pageObjects.jp.sony;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpDashboardOpportunities;
import org.openqa.selenium.By;

public class SonyDashboardOpportunities extends AbstractJpDashboardOpportunities {

    private static final ExtendedBy opportunityTitle = new ExtendedBy("Opportunity title", By.xpath("//h1[text()='商品一覧']"));

    public SonyDashboardOpportunities(Browser browser) {
        super(browser, opportunityTitle);
    }

}
