package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

import java.time.Duration;

public class CpTransferTBEToWallet extends AbstractPage {

    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));
    private static final ExtendedBy amountToWithdrawField = new ExtendedBy("amount to withdraw field", By.xpath("//input[@name='amount']"));

    CpTransferTBEToWallet(Browser browser) {
        super(browser, okButton);
    }

    public void clickOk() {
        browser.waitForElementClickable(okButton);
        browser.clickAndWaitForElementToVanish(okButton, ExecuteBy.WEBDRIVER, true, 160);
    }

    public CpTransferTBEToWallet setAmountToWithdraw(int amount) {
        browser.waitForPageStable(Duration.ofSeconds(5));
        browser.clearTextInput(amountToWithdrawField);
        browser.waitForPageStable(Duration.ofSeconds(5));
        browser.typeTextElement(amountToWithdrawField, amount + "");
        return this;
    }
}
