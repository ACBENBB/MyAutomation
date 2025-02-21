package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SecuritizeIdNavigationPane extends AbstractPage {

    private static final ExtendedBy headerImage = new ExtendedBy("Navigation pane - header image", By.id("link-home"));

    private static final ExtendedBy activePhase = new ExtendedBy("Active phase", By.xpath("//ul[@class='nav']/li/a[contains(@class, 'wizard-menu-item__link_active')]"));
    private static final ExtendedBy activeSubPhase = new ExtendedBy("Active phase", By.xpath("//ul[@class='nav']/li/a[contains(@class, 'wizard-menu-item__link_active') and contains(@id, 'sub-menu')]"));
    private static final ExtendedBy phaseHeader = new ExtendedBy("Phase header", By.xpath(".//span/span"));


    public SecuritizeIdNavigationPane(Browser browser) {
        super(browser, headerImage);
    }

    public static boolean isSideBarVisible(Browser browser){
        return browser.isElementVisibleQuick(headerImage, 2);
    }

    public String getActivePhaseName() {
        WebElement activeStageHeader = browser.findElementInElement(activePhase, phaseHeader);
        return browser.getElementText(activeStageHeader, "active phase header");
    }

    public String getActiveSubPhaseName() {
        WebElement activeStageHeader = browser.findElementInElement(activeSubPhase, phaseHeader);
        return browser.getElementText(activeStageHeader, "active sub phase header");
    }
}