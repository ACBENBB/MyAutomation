package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpDashboardOpportunities;
import org.openqa.selenium.By;

public class MaruiDashboardOpportunities extends AbstractJpDashboardOpportunities {

    private static final ExtendedBy opportunityTitle = new ExtendedBy("Opportunity title", By.xpath("//h1[text()='社債一覧']"));

    public MaruiDashboardOpportunities(Browser browser) {
        super(browser, opportunityTitle);
    }

}
