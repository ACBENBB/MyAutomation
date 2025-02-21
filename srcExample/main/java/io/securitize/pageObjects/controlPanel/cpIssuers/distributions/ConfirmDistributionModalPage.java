package io.securitize.pageObjects.controlPanel.cpIssuers.distributions;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ConfirmDistributionModalPage extends AbstractPage {

    public ConfirmDistributionModalPage(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    private static final ExtendedBy distributionConfirmationModalBtn = new ExtendedBy("Distribution Confirmation Modal Btn", By.xpath("//p[text()='Confirm']/parent::button"));

    public void clickConfirmDistributionModal() {
        WebElement element = browser.findElement(distributionConfirmationModalBtn);
        element.click();
    }

}