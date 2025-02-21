package io.securitize.pageObjects.grafana;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class GrafanaDashboardPage extends AbstractPage {

    private static final ExtendedBy dashboardScroll = new ExtendedBy("Grafana dashboard page - scrolling element", By.xpath("//div[contains(@class, 'react-grid-item')]"));

    public GrafanaDashboardPage(Browser browser) {
        super(browser, dashboardScroll);
    }
}
