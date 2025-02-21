package io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class SecuritizeIdInvestorKyc01IndividualIdentityVerification extends SecuritizeIdInvestorAbstractPage {

    private static final ExtendedBy sumsubIframe = new ExtendedBy("Sumsub iframe", By.xpath("//div[@id='sumsub-sdk-file-uploader']/iframe"));
    private static final ExtendedBy sumsubIframeCountrySelector = new ExtendedBy("Sumsub iframe - country selector", By.xpath("//*[@placeholder='Country']"));
    private static final ExtendedBy sumsubIframeDriversLicenseSelector = new ExtendedBy("Sumsub iframe - Driver's license selector", By.xpath("//input[@type='radio' and @value='DRIVERS']/.."));
    private static final ExtendedBy sumsubIframeDriversLicenseImageFront = new ExtendedBy("Sumsub iframe - Driver's license image front", By.xpath("//div[@name='imageIds[0]']//input"));
    private static final ExtendedBy sumsubIframeDriversLicenseImageBack = new ExtendedBy("Sumsub iframe - Driver's license image back", By.xpath("//div[@name='imageIds[1]']//input"));
    private static final ExtendedBy sumsubIframeImageTrashIcon = new ExtendedBy("Sumsub iframe - Image trash icon", By.xpath("//*[contains(@class, 'fa-trash')]"));
    private static final ExtendedBy sumsubIframeContinueButton = new ExtendedBy("Sumsub iframe - Continue button", By.xpath("//button[@class='submit']/.."));
    private static final ExtendedBy sumsubIframeMessageContent = new ExtendedBy("Sumsub iframe - message content", By.xpath("//div[@class='popup']//div[@class='message-content']"));
    private static final ExtendedBy sumsubIframeUploadError = new ExtendedBy("Sumsub iframe - upload error", By.xpath("//p[@class='error']"));
    private static final ExtendedBy sumsubIframeConfirmMessageButton = new ExtendedBy("Sumsub iframe - confirm message button", By.xpath("//div[@class='popup']//button[@class='blue']"));
    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 02 - continue button", By.id("wizard-continue"));
    private static final ExtendedBy popUpOtherRadioButton = new ExtendedBy("Securitize Id - 02 - other radio button inside pop up", By.xpath("//label[text() = ' Other ']"));
    private static final ExtendedBy sidHomeLink = new ExtendedBy("ecuritize Id - 02 - Left side bar - Link home icon ", By.id("link-home"));
    private static final ExtendedBy exitProcessButton = new ExtendedBy("Exit process button", By.xpath("//button[contains(@class, 'profile-verification-return-link')]/span[text() = 'Return to' or 'Exit this process']"));


    public SecuritizeIdInvestorKyc01IndividualIdentityVerification(Browser browser) {
        super(browser, sumsubIframe);
    }


    public SecuritizeIdInvestorKyc01IndividualIdentityVerification approveLetsGetYouVerifiedPopUp() {
        browser.switchToFrame(sumsubIframe);
        browser.click(popUpOtherRadioButton, false);
        browser.click(sumsubIframeContinueButton, false);
        browser.switchBackToDefaultContent();
        return this;
    }


    public SecuritizeIdInvestorKyc01IndividualIdentityVerification setCountry(String country) {
        browser.switchToFrame(sumsubIframe);
        browser.typeTextElement(sumsubIframeCountrySelector, country);
        browser.switchBackToDefaultContent();
        return this;
    }

    public SecuritizeIdInvestorKyc01IndividualIdentityVerification switchDriversLicensePane() {
        browser.switchToFrame(sumsubIframe);
        browser.click(sumsubIframeDriversLicenseSelector, false);
        browser.switchBackToDefaultContent();
        return this;
    }

    public SecuritizeIdInvestorKyc01IndividualIdentityVerification switchPassportPane() {
        browser.switchToFrame(sumsubIframe);
        browser.switchBackToDefaultContent();
        return this;
    }

    public SecuritizeIdInvestorKyc01IndividualIdentityVerification uploadDriverImageFront(String path) {
        boolean result = false;
        for (int i = 0; i < 10; i++) {
            result = internalSumsubUploadDriverImage(sumsubIframeDriversLicenseImageFront, path, 0);
            if (result) {
                break;
            } else {
                browser.switchToFrame(sumsubIframe);
                // confirm message and try
                Optional<WebElement> firstVisibleContinueButton = browser.findFirstVisibleElementQuick(sumsubIframeConfirmMessageButton, 3);
                firstVisibleContinueButton.ifPresent(webElement -> browser.click(webElement, "first visible continue button"));
                browser.switchBackToDefaultContent();
            }
        }

        if (!result) {
            errorAndStop("Unable to upload documents even after 10 attempts!", true);
        }
        return this;
    }

    public SecuritizeIdInvestorKyc01IndividualIdentityVerification uploadDriverImages(String frontPath, String backPath) {
        browser.scrollToBottomOfElement(sumsubIframe);

        // try to upload same images up to 10 times
        boolean result = false;
        for (int i = 0; i < 10; i++) {
            switchDriversLicensePane(); // sometimes this field gets deleted between attempts - so make sure it is marked

            result = internalSumsubUploadDriverImage(sumsubIframeDriversLicenseImageFront, frontPath, 0);

            // if first upload was successful try the second one
            if (result) {
                result = internalSumsubUploadDriverImage(sumsubIframeDriversLicenseImageBack, backPath, 1);
            }

            if (result) {
                break;
            } else {
                browser.switchToFrame(sumsubIframe);
                // confirm message and try
                Optional<WebElement> firstVisibleContinueButton = browser.findFirstVisibleElementQuick(sumsubIframeConfirmMessageButton, 3);
                firstVisibleContinueButton.ifPresent(webElement -> browser.click(webElement, "first visible continue button"));
                browser.switchBackToDefaultContent();
            }
        }

        if (!result) {
            errorAndStop("Unable to upload documents even after 10 attempts!", true);
        }

        return this;
    }

    private boolean internalSumsubUploadDriverImage(ExtendedBy element, String path, int expectedTrashIcons) {
        browser.switchToFrame(sumsubIframe);

        browser.setImplicitWaitTimeout(2);
        int deleteImageButtonCount = browser.findElements(sumsubIframeImageTrashIcon).size();
        browser.resetImplicitWaitTimeout();

        if (deleteImageButtonCount > expectedTrashIcons) {
            info("No need to upload image as trash icon already appears");
            browser.switchBackToDefaultContent();
            return true;
        }

        browser.typeTextElement(element, path);
        final boolean[] wasSuccessful = {false};

        Function<String, Boolean> internalUploadSuccessfulOrError = t -> {
            try {
                info("Checking for dialog error message...");
                List<WebElement> messages = browser.findElementsQuick(sumsubIframeMessageContent, 1);
                long visibleMessages = messages.stream().filter(WebElement::isDisplayed).count();
                if (visibleMessages > 0) {
                    wasSuccessful[0] = false;
                    return true;
                }

                info("Checking for error message in upload section...");
                messages = browser.findElementsQuick(sumsubIframeUploadError, 1);
                visibleMessages = messages.stream().filter(WebElement::isDisplayed).count();
                if (visibleMessages > 0) {
                    wasSuccessful[0] = false;
                    return true;
                }

                info("Counting amount of found trash icons...");
                List<WebElement> trashIcons = browser.findElementsQuick(sumsubIframeImageTrashIcon, 1);
                if (trashIcons.size() == deleteImageButtonCount + 1) {
                    wasSuccessful[0] = true;
                    return true;
                }

                return false;
            } catch (Exception e) {
                return false;
            }
        };

        Browser.waitForExpressionToEqual(internalUploadSuccessfulOrError, null, true, "some description", 60, 200);

        browser.switchBackToDefaultContent();
        return wasSuccessful[0];
    }

    public SecuritizeIdInvestorKyc02IndividualLivenessCheck clickSumbsubContinue() {
        browser.switchToFrame(sumsubIframe);
        browser.click(sumsubIframeContinueButton, false);
        browser.switchBackToDefaultContent();
        return new SecuritizeIdInvestorKyc02IndividualLivenessCheck(browser);
    }

    public SecuritizeIdInvestorKyc02IndividualLivenessCheck clickContinue() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorKyc02IndividualLivenessCheck(browser);
    }

    public SecuritizeIdDashboard clickHomeLink() {
        browser.click(sidHomeLink, false);
        return new SecuritizeIdDashboard(browser);
    }

    public SecuritizeIdInvestorKyc01IndividualIdentityVerification waitForStepToLoad() {
        browser.waitForElementVisibility(sumsubIframe);
        return this;
    }

    public void clickExitProcess() {
        browser.click(exitProcessButton);
    }


}