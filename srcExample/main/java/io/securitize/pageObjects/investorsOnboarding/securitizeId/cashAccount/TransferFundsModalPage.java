package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.info;

public class TransferFundsModalPage extends AbstractPage {

    private static final ExtendedBy depositModalBody = new ExtendedBy("Transfer Types Section Title", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy continueButton = new ExtendedBy("Continue button", By.cssSelector("[class^='style__nextActionButton_']"));
    private static final ExtendedBy closeButton = new ExtendedBy("Close button", By.cssSelector("[class*='WireTransferModal'] [class^='style__RightSectionContainer'] svg"));
    private static final ExtendedBy closeBlueButton = new ExtendedBy("Close blue button", By.xpath("//*[contains(@class, 'style__nextActionButton__')]"));
    private static final ExtendedBy USWireTransfer = new ExtendedBy("US Wire Transfer", By.xpath("//span[contains(text(), 'United States Wire Transfer')]"));
    private static final ExtendedBy ACHTransfer = new ExtendedBy("ACH Transfer", By.xpath("//div[contains(@class,'paymentCardContainer')][.//span[contains(text(), 'ACH') or contains(text(), 'checking')]]"));
    private static final ExtendedBy InternationalWireTransfer = new ExtendedBy("Internation Wire Transfer", By.xpath("//span[contains(text(), 'International Wire Transfer')]"));
    private static final ExtendedBy USDC = new ExtendedBy("Internation Wire Transfer", By.xpath("//span[contains(text(), 'next 24')]"));
    private static final ExtendedBy paymentMethods = new ExtendedBy("Payment Methods", By.xpath("//div[contains(@class,'styles__paymentCardHeaderContainer')]/div/span"));
    private static final ExtendedBy linkAcctMessage = new ExtendedBy("Link Account Message", By.xpath("//div[contains(@class,'bottomMessage')]"));


    public TransferFundsModalPage(Browser browser) {
        super(browser, depositModalBody);
    }

    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return browser.findElement(depositModalBody).isDisplayed();
    }

    public String getDepositModalBodyText() {
        browser.waitForElementVisibility(closeBlueButton); // wait for modal content to load
        return browser.getElementText(depositModalBody);
    }

    public void clickCloseBtn() {
        browser.click(closeButton);
    }

    public void clickACH() {
        browser.findElement(ACHTransfer).click();
    }

    public DepositACHModalPage selectACH() {
        clickACH();
        clickContinueBtn();
        return new DepositACHModalPage(browser);
    }

    public DepositUSWireModalPage selectUSWire() {
        clickUSWire();
        clickContinueBtn();
        return new DepositUSWireModalPage(browser);
    }

    public void clickUSWire() {
        browser.click(USWireTransfer);
    }

    public void clickContinueBtn() {
        browser.findElement(continueButton).click();
    }

    public void clickInternationWire() {
        browser.click(InternationalWireTransfer);
    }

    public DepositInternationalWireModalPage selectInternationWire() {
        clickInternationWire();
        clickContinueBtn();
        return new DepositInternationalWireModalPage(browser);
    }

    public void clickUSDC() {
        browser.click(USDC);
    }

    public DepositUSDCModalPage selectUSDC() {
        clickUSDC();
        clickContinueBtn();
        return new DepositUSDCModalPage(browser);
    }

    public List<String> getPaymentMethods() {
        return browser.findElements(paymentMethods).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    //This method returns the text that appears after clicking ACH card with no linked account.
    public String getLinkAcctText() {
        return browser.findElement(linkAcctMessage).getText();
    }

    public LinkBankAccountPlaidModal selectAchWithUnlinkedAccount() {
        clickACH();
        if (getLinkAcctText().equals("Continue to link an account")) {
            info("Linking bank account");
            clickContinueBtn();
            return new LinkBankAccountPlaidModal(browser);
        } else {
            info("Bank account already linked.");
            return null;
        }
    }

}
