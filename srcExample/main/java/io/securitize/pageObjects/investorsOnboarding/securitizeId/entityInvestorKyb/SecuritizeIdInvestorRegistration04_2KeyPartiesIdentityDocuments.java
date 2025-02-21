package io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SecuritizeIdInvestorRegistration04_2KeyPartiesIdentityDocuments extends SecuritizeIdInvestorAbstractPage {

    private static final ExtendedBy driverLicensePaneButton = new ExtendedBy("Driver's license pane button", By.id("wizard-signer-drivers-license-title"));

    private static final ExtendedBy frontImage = new ExtendedBy("front image", By.xpath("(//input[@type='file'])[1]"));
    private static final ExtendedBy backImage = new ExtendedBy("back image", By.xpath("(//input[@type='file'])[3]"));
    private static final ExtendedBy deleteImageButton = new ExtendedBy("Delete image button", By.id("file-delete"));
    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 05.2 - continue button", By.id("wizard-continue"));


    SecuritizeIdInvestorRegistration04_2KeyPartiesIdentityDocuments(Browser browser) {
        super(browser, driverLicensePaneButton);
    }

    public SecuritizeIdInvestorRegistration04_2KeyPartiesIdentityDocuments switchDriversLicensePane() {
        browser.click(driverLicensePaneButton, false);
        return this;
    }

    public SecuritizeIdInvestorRegistration04_2KeyPartiesIdentityDocuments uploadFrontImage(String path) {
        internalUploadImage(frontImage, path);
        return this;
    }

    public SecuritizeIdInvestorRegistration04_2KeyPartiesIdentityDocuments uploadBackImage(String path) {
        internalUploadImage(backImage, path);
        return this;
    }

    public SecuritizeIdInvestorRegistration04_3KeyPartiesCheck clickContinue() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorRegistration04_3KeyPartiesCheck(browser);
    }


    private void internalUploadImage(ExtendedBy elementBy, String path) {
        // check how many delete image buttons are visible
        browser.setImplicitWaitTimeout(2);
        int deleteImageButtonCount = 0;
        List<WebElement> elements = browser.findElements(deleteImageButton);
        for (WebElement currentElement : elements) {
            if (currentElement.isDisplayed()) {
                deleteImageButtonCount++;
            }
        }
        browser.resetImplicitWaitTimeout();

        WebElement element = browser.findElement(elementBy);
        browser.setElementAttribute(element, "style", "display:block;");
        browser.typeTextElement(elementBy, path);

        // wait for an additional delete button to show up
        browser.waitForElementVisibleCount(deleteImageButton, deleteImageButtonCount + 1);
    }
}
