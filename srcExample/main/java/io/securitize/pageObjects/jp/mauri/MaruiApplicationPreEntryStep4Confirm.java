package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiApplicationPreEntryStep4Confirm extends AbstractJpPage {

    public MaruiApplicationPreEntryStep4Confirm(Browser browser) {
        super(browser, confirmButton);
    }

    // Step 4 お申し込み内容確認
    private static final ExtendedBy step04Header = new ExtendedBy("Step 04 Head Line", By.xpath("//h1[text()[contains(., 'お申し込み内容確認')]]"));
    private static final ExtendedBy confirmButton = new ExtendedBy("Confirm Button", By.xpath("//button/*[text()[contains(.,'上記内容で申し込む')]]"));
    private static final ExtendedBy tokenName = new ExtendedBy("Token Name", By.xpath("//*[@class='row' and contains(., '社債名')]"));
    private static final ExtendedBy amountYen = new ExtendedBy("Amount Yen", By.xpath("//*[@class='row' and contains(., 'お申し込み金額')]"));
    private static final ExtendedBy amount = new ExtendedBy("Amount", By.xpath("//*[@class='row' and contains(., 'お申し込み数量')]"));

    public String getText(String value, String name) {
        return value.replace(name, "").replace("\n", "");
    }

    public String tokenNameText() {
        return getText(browser.getElementText(tokenName), "社債名");
    }

    /** e.g. お申し込み金額: ￥100,000 */
    public String amountYenText() {
        return getText(browser.getElementText(amountYen), "お申し込み金額");
    }

    /** e.g. お申し込み数量: 10 単位 */
    public String amountText() {
        return getText(browser.getElementText(amount), "お申し込み数量");
    }

    public MaruiApplicationPreEntryStep5Complete clickConfirmButton() {
        browser.clickAndWaitForElementToVanish(confirmButton);
        if (browser.isElementVisibleQuick(step04Header, 5)) {
            browser.waitForElementToVanish(step04Header);
        }
        return new MaruiApplicationPreEntryStep5Complete(browser);
    }

    public MaruiApplicationPreEntryStep5Complete clickConfirmButton(boolean retry) {
        String url = getMaruiOpportunitiesUrl() + "/pre-entry/confirm";
        return RetryOnExceptions.retryFunction(this::clickConfirmButton,
                ()-> {
                    if (!browser.isElementVisible(confirmButton)) {
                        browser.navigateTo(url);
                    }
                    return null;
                },
                retry
        );
    }
}
