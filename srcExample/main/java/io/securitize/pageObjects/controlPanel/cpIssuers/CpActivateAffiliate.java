package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpActivateAffiliate extends AbstractPage {

    private static final ExtendedBy commentField = new ExtendedBy("comment text field", By.name("comment"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[contains(text(), 'OK')]"));

    CpActivateAffiliate(Browser browser) {
        super(browser, commentField);
    }

    public CpActivateAffiliate addComment(String value) {
        browser.typeTextElement(commentField, value);
        return this;
    }

    public void clickOk() {
        browser.clickAndWaitForElementToVanish(okButton);
    }
}
