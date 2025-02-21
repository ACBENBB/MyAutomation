package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SynapseSuccessPage extends AbstractPage {

    private static final ExtendedBy documentCanvas = new ExtendedBy("Modal body", By.cssSelector("[class*='style__SuccessStep']"));
    private static final ExtendedBy okButton = new ExtendedBy("Ok button", By.cssSelector("[class*='style__button__'] span"));

    public SynapseSuccessPage(Browser browser) {
        super(browser, documentCanvas);
    }

    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return browser.findElement(documentCanvas).isDisplayed();
    }

    public void clickOkButton() {
        browser.click(okButton);
    }

}
