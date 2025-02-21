package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class MigrationModalPage extends AbstractPage {

    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class*='style__BodyContainer']"));
    private static final ExtendedBy closeButton = new ExtendedBy("Close button", By.cssSelector("[class*='style__RightSectionContainer'] svg"));
    private static final ExtendedBy migrationMessage = new ExtendedBy("Migration message", By.cssSelector("[class*='MigrationModal__title']"));
    private static final ExtendedBy mainButton = new ExtendedBy("Migration message", By.cssSelector("[class*='style__BodyContainer'] button"));



    public MigrationModalPage(Browser browser) {
        super(browser, modalBody);
    }

    public String getModalInfoMessage() {
        return browser.findElement(migrationMessage).getText();
    }

    public void clickCloseBtn() {
        browser.click(closeButton);
    }

    public boolean isPresent() {
        return !browser.findElements(modalBody).isEmpty();
    }

    public void close() {
        browser.sendKeysElement(mainButton, "Send ESC", Keys.ESCAPE);
    }
}
