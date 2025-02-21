package io.securitize.pageObjects.reportportal;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class ReportPortalDashboardPage extends AbstractPage {

    private static final ExtendedBy fullScreenButton = new ExtendedBy("Report portal full screen button", By.xpath("//button//span[contains(text(), 'Full screen')]"));

    public ReportPortalDashboardPage(Browser browser) {
        super(browser, fullScreenButton);
    }

    public void clickFullScreen() {
        browser.click(fullScreenButton, false);
    }
}
