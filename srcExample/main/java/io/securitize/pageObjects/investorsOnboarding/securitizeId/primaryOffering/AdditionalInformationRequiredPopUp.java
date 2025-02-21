package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class AdditionalInformationRequiredPopUp extends AbstractPage {

    private static final ExtendedBy popUpTitle = new ExtendedBy("Pop Up Title", By.xpath("//*[contains(@class, 'VerificationModal')]//h4[contains(@class, 'title')]"));
    private static final ExtendedBy pupUpText = new ExtendedBy("Pop Up Info Text", By.xpath("//*[contains(@class, 'VerificationModal')]//*[contains(@class, 'description')]"));
    private static final ExtendedBy reachMeButton = new ExtendedBy("Reach me button", By.xpath("//button[@id = 'btn-enhanced-verification-contact-me']//span"));
    private static final ExtendedBy notInterestedButton = new ExtendedBy("Im not interested button", By.xpath("//*[text() = 'I’m not interested']"));
    private static final ExtendedBy closeButton = new ExtendedBy("Close button", By.xpath("//button[@class = 'close']"));

    public AdditionalInformationRequiredPopUp(Browser browser) {
        super(browser);
    }

    public String getPopUpTitleText() {
        return browser.getElementText(popUpTitle);
    }

    public String getPupUpInfoTextText() {
        return browser.getElementText(pupUpText);
    }

    public void clickReachMeButton() {
        browser.click(reachMeButton);
        browser.waitForElementToVanish(closeButton);
    }

    public void clickNotInterestedButton() {
        browser.click(notInterestedButton);
    }

    public void clickCloseButton() {
        browser.click(closeButton);
    }

}
