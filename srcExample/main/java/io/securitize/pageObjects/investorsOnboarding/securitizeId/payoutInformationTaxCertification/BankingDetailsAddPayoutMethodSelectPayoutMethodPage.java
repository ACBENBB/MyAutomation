package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class BankingDetailsAddPayoutMethodSelectPayoutMethodPage extends AbstractPage {

    // Actually this id is missing, ask dev to add it.
    private static final ExtendedBy goBackButton = new ExtendedBy("Go back to payout preference",
            By.id("go-back"));
    //we need to do this dirty thing here because we are interacting with an external web-hook, devs can't change ids.
    private static final ExtendedBy BankTransferButton = new ExtendedBy("Bank Transfer Button",
            By.xpath("//div[contains(text(), 'Bank Transfer')]//ancestor::div[contains(@class, 'payout-select')])"));
    private static final ExtendedBy CheckButton = new ExtendedBy("Check Button",
            By.xpath("//div[contains(text(), 'Check')]//ancestor::div[contains(@class, 'payout-select')])"));
    private static final ExtendedBy routingNumberInput = new ExtendedBy("Routing number input",
            By.name("branchId"));
    private static final ExtendedBy accountNumberInput = new ExtendedBy("Account number input",
            By.name("accountNum"));
    private static final ExtendedBy accountHolderNameInput = new ExtendedBy("Account holder name",
            By.name("accountHolderName"));
    private static final ExtendedBy backButton = new ExtendedBy("Previous page button",
            By.xpath("//*[@id=\"app\"]//button[.//span[text()='Back']]"));
    private static final ExtendedBy addButton = new ExtendedBy("Add button",
            By.xpath("//button[.//span[text()='Add']]"));
    private static final ExtendedBy addAndActivateButton = new ExtendedBy("Add and activate button",
            By.cssSelector("button[type=submit]"));
    private static final ExtendedBy popUpSaveButton = new ExtendedBy("Please Confirm the name provided - Save button",
            By.xpath("//div[contains(@class, 'rc-dialog-content')//button[.//span[text()='Save']]\""));
    private static final ExtendedBy popUpBackButton = new ExtendedBy("Please Confirm the name provided - Back button",
            By.xpath("//div[contains(@class, 'rc-dialog-content')//button[.//span[text()='back']]"));

    public BankingDetailsAddPayoutMethodSelectPayoutMethodPage(Browser browser) {
        super(browser);
    }

    public BankingDetailsAddPayoutMethodSelectPayoutMethodPage setRoutingNumber(String routingNumber) {
        browser.sendKeysElement(routingNumberInput, "Routing Number", routingNumber);
        browser.pressTabKey(browser.findElement(routingNumberInput));
        return new BankingDetailsAddPayoutMethodSelectPayoutMethodPage(browser);
    }

    public BankingDetailsAddPayoutMethodSelectPayoutMethodPage setAccountNumber(String accountNumber) {
        browser.typeTextElement(accountNumberInput, accountNumber);
        return new BankingDetailsAddPayoutMethodSelectPayoutMethodPage(browser);
    }

    public BankingDetailsAddPayoutMethodSelectPayoutMethodPage setNameOfAccountHolder(String nameOfAccountHolder) {
        browser.typeTextElement(accountHolderNameInput, nameOfAccountHolder);
        return new BankingDetailsAddPayoutMethodSelectPayoutMethodPage(browser);
    }

    public void clickOnAddBtn() {
        waitForAddBtnToAppear();
        browser.click(addButton, false);
    }

    public void waitForAddBtnToAppear() {
        browser.waitForPresenceOfElementLocated(addButton.getBy());
    }

    public BankingDetailsAddPayoutMethodSuccessPage completeSelectPayoutMethodForm(String routingNumber,
                                                                String accountNumber, String nameOfAccountHolder) {
        setRoutingNumber(routingNumber).setAccountNumber(accountNumber).setNameOfAccountHolder(nameOfAccountHolder)
                .clickOnAddBtn();
        return new BankingDetailsAddPayoutMethodSuccessPage(browser);
    }


}
