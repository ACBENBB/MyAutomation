package io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.manageToken;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpActivateTokenPage extends AbstractPage {
    private static final ExtendedBy activateButton = new ExtendedBy("Activate token", By.xpath("//span[text()[contains(.,'Activate')]]"));
    private static final ExtendedBy modalActivateButton = new ExtendedBy("modal Activate token", By.xpath("//button[text()[contains(.,'Continue')]]"));
    private static final ExtendedBy backToTokenBtn = new ExtendedBy("Back to Token Button Activate token", By.xpath("//button[contains(text(), 'Go back to Token')]"));

    public CpActivateTokenPage(Browser browser) {
        super(browser);
    }
    public void clickActivateButton() {
        browser.waitForPageStable();
        browser.click(activateButton);
    }
    public void clickModalActivateButton() {
        browser.waitForPageStable();
        browser.click(modalActivateButton);
    }
    public void clickBackToTokenBtn() {browser.click(backToTokenBtn);}
    public void refreshPage() {browser.refreshPage(true);}

}
