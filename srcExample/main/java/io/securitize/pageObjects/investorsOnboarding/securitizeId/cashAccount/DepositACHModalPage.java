package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class DepositACHModalPage extends AbstractPage {

    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy modalTitle = new ExtendedBy("ACH Modal title", By.xpath("//h5[contains(text(), 'Deposit ACH')]"));
    private static final ExtendedBy amountInput = new ExtendedBy("Amount Input", By.cssSelector("[data-test-id='ca-transfermodalachstep-cash-input']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Submit Button", By.cssSelector("[data-test-id='ca-transfermodalachstep-submit-btn']"));
    private static final ExtendedBy accountNumberInCard = new ExtendedBy("Account number in card", By.cssSelector("[class*='style__MaskedNumber__']"));
    private static final ExtendedBy authorizationCheckbox = new ExtendedBy("Legal legend checkbox", By.id("authorization-checkbox"));
    private static final ExtendedBy legalLegendText = new ExtendedBy("Legal legend text", By.cssSelector("[class*='style__labelText__'].wh-text-details"));


    public DepositACHModalPage(Browser browser) {
        super(browser, modalBody);
    }


    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return ! browser.findElements(modalBody).isEmpty();
    }

    public String getModalTitle() {
        return browser.getElementText(modalTitle);
    }

    public void setAmount(String amount) {
        browser.clearTextInput(amountInput);
        browser.sendKeysElement(amountInput, "typing " + amount + " into amount field", amount);
    }

    public Boolean isSubmitButtonEnabled() {
        return browser.findElement(submitButton).isEnabled();
    }

    public DepositACHConfirmationModalPage clickSubmitBtn() {
        browser.click(submitButton);

        return new DepositACHConfirmationModalPage(browser);
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

}
