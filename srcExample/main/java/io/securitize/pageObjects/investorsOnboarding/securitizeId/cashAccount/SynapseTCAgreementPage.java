package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SynapseTCAgreementPage extends AbstractPage {

    private static final ExtendedBy documentCanvas = new ExtendedBy("Document canvas", By.className("react-pdf__Page__canvas"));
    private static final ExtendedBy acceptButton = new ExtendedBy("Accept terms and conditions button", By.cssSelector("[class*='styles__button__'] span"));

    public SynapseTCAgreementPage(Browser browser) {
        super(browser, documentCanvas);
    }

    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return browser.findElement(documentCanvas).isDisplayed();
    }

    public SynapseSuccessPage clickAcceptButton() {
        browser.click(acceptButton);
        return new SynapseSuccessPage(browser);
    }

}
