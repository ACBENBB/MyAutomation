package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiApplicationPreEntryStep5Complete extends AbstractJpPage {

    public MaruiApplicationPreEntryStep5Complete(Browser browser) {
        super(browser, returnToCorporateBondListButton);
    }

    // Step 5 完了
    private static final ExtendedBy returnToCorporateBondListButton = new ExtendedBy("Return To Corporate Bond List Button", By.xpath("//button/*[text()[contains(.,'社債一覧に戻る')]]"));

    public void clickReturnToCorporateBondListButton() {
        browser.click(returnToCorporateBondListButton);
    }

}
