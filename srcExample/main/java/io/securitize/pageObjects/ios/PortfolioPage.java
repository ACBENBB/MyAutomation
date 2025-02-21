package io.securitize.pageObjects.ios;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;

public class PortfolioPage extends AbstractPage<PortfolioPage> {

    private static final String portfolioValueXpath = "//XCUIElementTypeStaticText[@name='Portfolio Value']";
    private static final String acceptCookiesButtonXpath = "//XCUIElementTypeButton[@name='Allow all']";
    private static final String bothXpaths = portfolioValueXpath + " | " + acceptCookiesButtonXpath;

    private static final ExtendedBy acceptCookiesButton = new ExtendedBy("Accept Cookies Button", By.xpath(acceptCookiesButtonXpath));
    private static final ExtendedBy bothLocators = new ExtendedBy("Complete your account details OR Accept Cookies Button", By.xpath(bothXpaths));

    public PortfolioPage(Browser browser) {
        super(browser, bothLocators);
    }


    public void clickAcceptCookiesIfNeeded() {
        List<WebElement> elements = browser.findElementsQuick(acceptCookiesButton, 5);
        if (elements.isEmpty()) {
            info("Didn't find accept cookies button. No need to click it");
        } else {
            info("found accept cookies button. Clicking it now!");
            elements.get(0).click();
        }
    }
}