package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawUSDCModalPage extends AbstractPage {

    private static final ExtendedBy withdrawModalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy amountField = new ExtendedBy("Amount Input Field", By.cssSelector("[data-test-id='ca-transfermodalwithdrawform-input']"));
    private static final ExtendedBy usdcAmount = new ExtendedBy("USDC Amount", By.cssSelector("[data-test-id='ca-transfermodalwithdrawform-cashtotal-value']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Submit button", By.cssSelector("[class*='WithdrawCoinForm'] .btn-primary"));


    public WithdrawUSDCModalPage(Browser browser) {
        super(browser, withdrawModalBody);
    }
    public void setAmount(String amount) {
        browser.sendKeysElement(amountField, "Set amount field", amount);
    }
    public WithdrawUSDCConfirmationModalPage clickSubmitButton() {
        browser.click(submitButton);

        return new WithdrawUSDCConfirmationModalPage(browser);
    }

    public Double getUSDCAmountAsDouble() {
        return Double.parseDouble(browser.getElementText(usdcAmount).replaceAll("[^0-9.]", ""));
    }
}
