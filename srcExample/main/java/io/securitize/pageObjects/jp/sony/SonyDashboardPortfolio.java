package io.securitize.pageObjects.jp.sony;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class SonyDashboardPortfolio extends AbstractJpPage {

    private static final ExtendedBy portfolioTitle = new ExtendedBy("Portfolio title", By.xpath("//h1[text()='保有商品']"));

    public SonyDashboardPortfolio(Browser browser) {
        super(browser, portfolioTitle);
    }

}
