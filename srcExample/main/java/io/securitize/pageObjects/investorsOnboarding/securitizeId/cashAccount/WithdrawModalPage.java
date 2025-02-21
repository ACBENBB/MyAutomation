package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class WithdrawModalPage extends AbstractPage {

    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy continueButton = new ExtendedBy("Continue button", By.cssSelector("[class^='style__nextActionButton_']"));
    private static final ExtendedBy closeButton = new ExtendedBy("Close button", By.cssSelector(".modal-content [class^='style__HeaderContainer_'] svg"));
    private static final ExtendedBy USWireTransfer = new ExtendedBy("US Wire Transfer", By.xpath("//span[contains(text(), 'United States Wire Transfer')]"));
    private static final ExtendedBy InternationalWireTransfer = new ExtendedBy("International Wire Transfer", By.xpath("//span[contains(text(), 'International Wire Transfer')]"));
    private static final ExtendedBy ACHTransfer = new ExtendedBy("ACH Transfer", By.xpath("//div[contains(@class,'paymentCardContainer')][.//span[contains(text(), 'ACH') or contains(text(), 'checking')]]"));
    private static final ExtendedBy USDCTransfer = new ExtendedBy("USDC Transfer", By.cssSelector("[class*='ChipComponent']"));
    public WithdrawModalPage(Browser browser) {
        super(browser, modalBody);
    }


    public void clickUSWire() {
        browser.click(USWireTransfer);
    }
    public void clickInternationalWire() {
        browser.click(InternationalWireTransfer);
    }
    public void clickContinueBtn() {
        browser.findElement(continueButton).click();
    }

    public WithdrawUSWireModalPage selectUSWire() {
        clickUSWire();
        clickContinueBtn();
        return new WithdrawUSWireModalPage(browser);
    }

    public WithdrawInternationalWireModalPage selectInternationalWire() {
        clickInternationalWire();
        clickContinueBtn();
        return new WithdrawInternationalWireModalPage(browser);
    }

    public void clickACH(){
        browser.findElement(ACHTransfer).click();
    }

    public WithdrawUSACHModalPage selectACH() {
        clickACH();
        clickContinueBtn();
        return new WithdrawUSACHModalPage(browser);
    }

    public void clickUSDC(){
        browser.findElement(USDCTransfer).click();
    }

    public WithdrawSelectWalletModalPage selectUSDC() {
        clickUSDC();
        clickContinueBtn();
        return new WithdrawSelectWalletModalPage(browser);
    }
    public void clickCloseBtn() {
        browser.click(closeButton);
    }
}
