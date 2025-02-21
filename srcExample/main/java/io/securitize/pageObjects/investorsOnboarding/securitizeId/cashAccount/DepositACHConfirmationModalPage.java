package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class DepositACHConfirmationModalPage extends AbstractPage {

    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy modalTitle = new ExtendedBy("ACH Modal title", By.xpath("//h5[contains(text(), 'Deposit ACH')]"));
    private static final ExtendedBy modalInfo = new ExtendedBy("ACH Modal info", By.cssSelector("[class*='achSuccess'] [class*='info']"));
    private static final ExtendedBy gotItButton = new ExtendedBy("Got it Button", By.cssSelector("[data-test-id='ca-ach-transaction-success-btn']"));


    public DepositACHConfirmationModalPage(Browser browser) {
        super(browser, modalBody);
    }

    public String getModalTitle() {
        return browser.findElement(modalTitle).getText();
    }

    public String getModalInfoMessage() {
        return browser.findElement(modalInfo).getText();
    }

    public void clickGotItButton() {
        browser.findElement(gotItButton).click();
    }

}
