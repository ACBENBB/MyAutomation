package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawConfirmationModalPage extends AbstractPage {

    private static final ExtendedBy withdrawConfirmationModalBody = new ExtendedBy("Withdraw confirmation modal", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy gotItButton = new ExtendedBy("Continue Button", By.cssSelector("[data-test-id='ca-withdrawmodal-success-btn']"));


    public WithdrawConfirmationModalPage(Browser browser) {
        super(browser, withdrawConfirmationModalBody);
    }

    public void clickGotItBtn() {
        browser.click(gotItButton, true);
    }


}
