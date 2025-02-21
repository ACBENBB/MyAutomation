package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawUSDCConfirmationModalPage extends AbstractPage {

    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy modalTitle = new ExtendedBy("ACH Modal title", By.xpath("(//h5[contains(text(), 'Withdraw ACH')])"));
    private static final ExtendedBy modalInfo = new ExtendedBy("ACH Modal info", By.cssSelector("[class*='achSuccess'] [class*='info']"));
    private static final ExtendedBy understoodButton = new ExtendedBy("Understood Button", By.cssSelector("[class*='ButtonComponent']"));


    public WithdrawUSDCConfirmationModalPage(Browser browser) {
        super(browser, modalBody);
    }

    public String getModalTitle() {
        return browser.findElement(modalTitle).getText();
    }

    public String getModalInfoMessage() {
        return browser.findElement(modalInfo).getText();
    }

    public void clickUnderstoodBtn() {
        browser.findElement(understoodButton).click();
    }

}
