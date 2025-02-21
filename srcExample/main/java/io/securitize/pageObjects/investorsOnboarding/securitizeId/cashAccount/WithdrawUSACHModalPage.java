package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawUSACHModalPage extends AbstractPage {

    private static final ExtendedBy withdrawModalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy amountField = new ExtendedBy("Amount Input Field", By.cssSelector("[data-test-id='ca-transfermodalachstep-cash-input']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Submit button", By.cssSelector("button[class*='styles__button__N']"));
    private static final ExtendedBy modalTitle = new ExtendedBy("ACH Modal title", By.xpath("//h5[contains(text(), 'Withdraw ACH')]"));
    private static final ExtendedBy accountNumberInCard = new ExtendedBy("Account number in card", By.cssSelector("[class*='style__MaskedNumber__']"));
    private static final ExtendedBy authorizationCheckbox = new ExtendedBy("Legal legend checkbox", By.cssSelector("input[id='authorization-checkbox']"));
    private static final ExtendedBy legalLegendText = new ExtendedBy("Legal legend text", By.cssSelector("[class*='style__labelText__'].wh-text-details"));
    private static final ExtendedBy transactionFee = new ExtendedBy("Transaction fee text", By.cssSelector("[class*=style__feeAmount]"));
    private static final ExtendedBy errorMessage = new ExtendedBy("Error validation message", By.cssSelector("[class*='style__errorMessage']"));

    public WithdrawUSACHModalPage(Browser browser) {
        super(browser, withdrawModalBody);
    }
    public void setAmount(String amount) {
        browser.clearTextInput(amountField);
        browser.sendKeysElement(amountField, "Set amount field", amount);
    }
    public WithdrawACHConfirmationModalPage clickSubmitBtn() {
        browser.click(submitButton, false);

        return new WithdrawACHConfirmationModalPage(browser);
    }

    public String getModalTitle() {
        return browser.getElementText(modalTitle);
    }

    public Boolean isSubmitButtonEnabled() {
        return browser.findElement(submitButton).isEnabled();
    }

    public String getAccountNumberInCard() {
        return browser.getElementText(accountNumberInCard).replaceAll("\\D", "");
    }

    public void clickAuthorizationCheckbox() {
        browser.click(authorizationCheckbox);
    }

    public String getLegalLegendText() {
        return browser.getElementText(legalLegendText);
    }

    public String getAccountNumberInLegalText() {
        return getLegalLegendText().replaceAll(".*?\\*+", "").replaceAll(" .*", "");
    }

    public String getAmountInLegalText() {
        return getLegalLegendText().replaceAll(".*?\\$","").replaceAll(" .*", "");
    }

    public String getTransactionFee() {
        return browser.getElementText(transactionFee).replaceAll("\\$", "");
    }

    public String getErrorValidationText() {
        return browser.getElementText(errorMessage);
    }

}
