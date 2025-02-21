package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiDashboardPortfolio extends AbstractJpPage {

    private static final ExtendedBy portfolioTitle = new ExtendedBy("Portfolio title", By.xpath("//h1[text()='お申し込み状況']"));

    public MaruiDashboardPortfolio(Browser browser) {
        super(browser, portfolioTitle);
    }

}
