package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;
import static io.securitize.infra.reporting.MultiReporter.*;

/**
 * 投資ステータス Investment status Section on each investor
 */
public class CpPlusInvestorsPageDetail extends AbstractJpPage {
    private static final ExtendedBy investmentStatusTitleText = new ExtendedBy("Investment Status Title Text", By.xpath("//*[contains(@class,'CardHeaderComponent') and contains(.,'投資ステータス')]"));
    private static final ExtendedBy investmentStatusText = new ExtendedBy("Investment Status Text", By.xpath("//*[contains(@class,'BadgeComponent d-flex flex-wrap') and contains(.,'ステータス')]/span"));
    private static final ExtendedBy investmentStatusCanceledText  = new ExtendedBy("Investment Status Canceled Text", By.xpath("//*[contains(@class,'badge-danger') and contains(.,'キャンセル済み')]"));
    private static final ExtendedBy tokenNameText = new ExtendedBy("Token Name Text", By.xpath("//*[@class='row' and contains(.,'トークン名')]/div[2]"));
    private static final ExtendedBy tokenAmountHeldInWallet = new ExtendedBy("Token Amount Held In Wallet", By.xpath("//*[@class='row' and contains(.,'保有数量（ウォレット）')]/div[2]"));
    private static final ExtendedBy tokenAmountHeldInTreasure = new ExtendedBy("Token Amount Held In Treasure", By.xpath("//*[@class='row' and contains(.,'保有数量（トレジャリー）')]/div[2]"));
    private static final ExtendedBy pledgedAmountYen = new ExtendedBy("Pledged Amount Yen", By.xpath("//*[@class='row' and contains(.,'申込金額')]/div[2]"));
    private static final ExtendedBy conformedPledgedAmountYen = new ExtendedBy("Confirmed Pledged Amount Yen", By.xpath("//*[@class='row' and contains(.,'当選金額')]/div[2]"));
    private static final ExtendedBy totalFundedYen = new ExtendedBy("Total Funded Yen", By.xpath("//*[@class='row' and contains(.,'払込金額')]/div[2]"));

    private static final ExtendedBy executeCancelButton = new ExtendedBy("Execute Cancel Button", By.xpath("//*[@class='d-flex justify-content-center' and contains(., 'キャンセル処理実行')]"));
    private static final String TEMPLATE_TOKEN_NAME_INPUT_ON_CONFIRM_DIALOG = "//input[@placeholder='%s']";
    private static final ExtendedBy okButtonOnConfirmDialog = new ExtendedBy("OK Button on Confirm Dialog", By.xpath("//*[@class='modal-content' and contains(.,'キャンセル処理実行')]//*[text()='OK']"));
    private static final String CANCELED_IN_JAPANESE = "キャンセル済み";

    public CpPlusInvestorsPageDetail(Browser browser) {
        super(browser, investmentStatusTitleText);
    }

    public void moveToInvestmentStatusSection() {
        browser.scrollToBottomOfElement(totalFundedYen);
    }

    public String getInvestmentStatusText() {
        return browser.getElementText(investmentStatusText);
    }

    public String getTokenNameText() {
        return browser.getElementText(tokenNameText);
    }

    public String getTokenHeldWalletText() {
        return browser.getElementText(tokenAmountHeldInWallet);
    }

    public String getTokenHeldTreasureText() {
        return browser.getElementText(tokenAmountHeldInTreasure);
    }

    public String getPledgedAmountText() {
        return browser.getElementText(pledgedAmountYen);
    }

    public String getConformedPledgedAmountText() {
        return browser.getElementText(conformedPledgedAmountYen);
    }

    public String getTotalFundedText() {
        return browser.getElementText(totalFundedYen);
    }

    public void clickExecuteCancelButton() {
        browser.click(executeCancelButton);
    }

    public void enterTokenNameOnConfirmDialog(String tokenName) {
        ExtendedBy tokenNameInput = new ExtendedBy("Token Name Input", By.xpath(String.format(TEMPLATE_TOKEN_NAME_INPUT_ON_CONFIRM_DIALOG, tokenName)));
        browser.typeTextElement(tokenNameInput, tokenName);
    }

    public void clickOkOnConfirmDialog() {
        browser.clickAndWaitForElementToVanish(okButtonOnConfirmDialog);
    }

    public String cancelInvestment(String tokenName) {
        String investmentStatus = getInvestmentStatusText();
        if (investmentStatus.equals("なし") || investmentStatus.equals(CANCELED_IN_JAPANESE)) {
            info("investment status: " + investmentStatus + ". cancel investment is not needed.");
            return investmentStatus;
        }
        info("perform cancel investment ");
        browser.waitForElementClickable(executeCancelButton, 120);
        clickExecuteCancelButton();
        enterTokenNameOnConfirmDialog(tokenName);
        clickOkOnConfirmDialog();
        browser.waitForElementVisibility(investmentStatusCanceledText);
        browser.waitForPageStable();
        return getInvestmentStatusText();
    }

    public String cancelInvestment(String tokenName, boolean retry) {
        return retryFunctionWithRefreshPage(()-> cancelInvestment(tokenName), retry);
    }

}
