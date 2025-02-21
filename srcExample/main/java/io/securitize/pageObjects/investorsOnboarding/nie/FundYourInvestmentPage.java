package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class FundYourInvestmentPage extends AbstractPage {

    private static final ExtendedBy paymentPendingImage = new ExtendedBy("Payment is pending image", By.xpath("//div[contains(@class,'PendingInfoCard_pendingInfoCard__col__217nK')]"));
    private static final ExtendedBy seeOtherOpportunitiesButton = new ExtendedBy("See other opportunities button", By.id("btn-funding-instructions--continue"));
    private static final ExtendedBy bankAccountDetails = new ExtendedBy("Bank account details", By.xpath("//div[contains(@class,'wireTransfer__bankAccount')]/pre"));
    private static final ExtendedBy depositWalletAddress = new ExtendedBy("Deposit wallet address", By.xpath("//div[contains(@class, 'DepositOptions')]//i[contains(@class, 'icon-wallet')]/.."));
    private static final ExtendedBy exitProcessButton = new ExtendedBy("Exit process button", By.id("btn-funding-instructions--exit-process"));
    private static final ExtendedBy wireTransferCard = new ExtendedBy("Wire Transfer Card", By.xpath("//h5[text()='Wire Transfer']"));
    private static final ExtendedBy wireTransferTitle = new ExtendedBy("Wire Transfer Title", By.xpath("//h4[contains(text(), 'Transfer')]"));
    private static final ExtendedBy wireTransferDetails = new ExtendedBy("Wire Transfer Details", By.xpath("//h5[text()='Can take up to 48 hours']"));
    private static final ExtendedBy notifiedWhenSubscriptionApprovedMessage = new ExtendedBy("You will be notified when your subscription is approved message", By.xpath("//div[contains(@class, 'warning')]/div[contains(text(), 'when your subscription is approved')]"));
    private static final ExtendedBy confirmationWhenFundsReceivedMessage = new ExtendedBy("We will send you a confirmation email as soon as we receive your funds message", By.xpath("//div[contains(@class, 'warning')]/div[contains(text(), 'We will send you a confirmation email as soon as we receive your funds.')]"));
    private static final ExtendedBy cashAccountTitle = new ExtendedBy("Cash Account Title", By.xpath("//h5[contains(text(), 'Cash balance')]"));
    private static final ExtendedBy cashAccountDetails = new ExtendedBy("Cash Account Details", By.xpath("//span[text()='Instant payment']"));
    private static final ExtendedBy cashAccountConfirmPurchaseButton = new ExtendedBy("Invest Button", By.id("btn-cash-account--confirm-purchase"));
    private static final ExtendedBy cashAccountInfo = new ExtendedBy("Info Cash Account", By.xpath("//i[contains(text(),'Amount to insvest')]"));
    private static final ExtendedBy getAccreditedButton = new ExtendedBy("Get accredited button", By.id("btn-additional-accreditation--open"));


    public FundYourInvestmentPage(Browser browser) {
        super(browser);
    }

    public void clickExitProcess() {
        browser.waitForElementToBeClickable(exitProcessButton, 60);
        browser.click(exitProcessButton, true);
    }

    public void clickWireTransferCard() {
        browser.click(wireTransferCard);
    }

    public boolean isBankAccountDetailsVisible() {
        browser.waitForElementVisibility(bankAccountDetails);
        return browser.isElementVisible(bankAccountDetails);
    }

    public String getDestinationWalletAddress() {
        return browser.getElementText(depositWalletAddress);
    }

    public boolean isWireTransferCardTitleVisible() {
        return browser.isElementVisible(wireTransferTitle);
    }

    public boolean isWireTransferCardDetailsVisible() {
        return browser.isElementVisible(wireTransferDetails);
    }

    public boolean isWireTransferDetailsHidden() {
        return browser.isElementVisible(notifiedWhenSubscriptionApprovedMessage);
    }

    public boolean isWireTransferApproved() {
        return browser.isElementVisible(confirmationWhenFundsReceivedMessage);
    }

    public boolean isCashAccountCardTitleVisible() {
        return browser.isElementVisible(cashAccountTitle);
    }

    public boolean isCashAccountCardDetailsVisible() {
        return browser.isElementVisible(cashAccountDetails);
    }

    public void clickCashAccountOption() {
        browser.click(cashAccountTitle);
    }

    public void clickCashAccountConfirmPurchaseButton() {
        browser.isElementVisible(cashAccountInfo);
        browser.isElementVisible(cashAccountConfirmPurchaseButton);
        browser.click(cashAccountConfirmPurchaseButton,false);
    }

    public void clickGetAccredited() {
        browser.click(getAccreditedButton, false);
    }
}