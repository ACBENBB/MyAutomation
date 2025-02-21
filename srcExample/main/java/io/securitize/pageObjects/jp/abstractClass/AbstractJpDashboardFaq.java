package io.securitize.pageObjects.jp.abstractClass;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;

public abstract class AbstractJpDashboardFaq extends AbstractJpPage {

    protected static final ExtendedBy faqTitle = new ExtendedBy("Your documents box title", By.xpath("//h1[text()='FAQ']"));

    protected AbstractJpDashboardFaq(Browser browser) {
        super(browser, faqTitle);
    }

}
