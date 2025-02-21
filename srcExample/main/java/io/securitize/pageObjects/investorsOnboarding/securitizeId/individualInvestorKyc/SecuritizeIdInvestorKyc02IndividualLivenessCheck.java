package io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;


public class SecuritizeIdInvestorKyc02IndividualLivenessCheck extends SecuritizeIdInvestorAbstractPage {

    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 03 Liveness button - continue button", By.id("liveness-wizard-continue"));
    private static final ExtendedBy livenessIframe = new ExtendedBy("Liveness Iframe - continue button", By.id("sumsub-sdk-liveness"));

    private static final ExtendedBy sumsubIframe = new ExtendedBy("sumsub content iframe", By.xpath("//iframe[contains(@src, 'sumsub.com')]"));
    private static final ExtendedBy imReadyButton = new ExtendedBy("sumsub I'm ready button", By.xpath("//button[@class='submit']"));
    private static final ExtendedBy successMessage = new ExtendedBy("sumsub scan success message", By.xpath("//*[contains(@class, 'sumsub__completed')]/div[text()='Scanner successful!']"));



    public SecuritizeIdInvestorKyc02IndividualLivenessCheck(Browser browser) {
        super(browser, livenessIframe);
    }

    public SecuritizeIdInvestorKyc03IndividualAddress clickContinue() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorKyc03IndividualAddress(browser);
    }

    public SecuritizeIdInvestorKyc02IndividualLivenessCheck clickImReadyButton() {
        if (MainConfig.getBooleanProperty(MainConfigProperty.livenessCheckEnabled)) {
            browser.switchToFrame(sumsubIframe);

            // todo: added due to random hangs in the hourglass screen after clicking..
            // We suspect this is caused by timing issues.. remove this sleep once this issue is resolved
            browser.sleep(5000);

            browser.click(imReadyButton);
            browser.switchBackToDefaultContent();
        } else {
            MultiReporter.info("Skip livenessCheck - set in mainConfig.properties");
        }
        return this;
    }
    public SecuritizeIdInvestorKyc02IndividualLivenessCheck waitForScanSuccessful() {
        if (MainConfig.getBooleanProperty(MainConfigProperty.livenessCheckEnabled)) {
            waitForSuccessOrHandleError();
            browser.switchBackToDefaultContent();
        } else {
            MultiReporter.info("Skip livenessCheck - set in mainConfig.properties");
        }
        return this;
    }

    private void waitForSuccessOrHandleError() {
        Function<String, Boolean> internalWaitForSuccessOrHandleError = t -> {
            try {
                info("Checking if `I'm Ready` button is visible once more...");
                browser.switchToFrame(sumsubIframe);
                Optional<WebElement> button = browser.findFirstVisibleElementQuick(imReadyButton, 1);
                if (button.isPresent()) {
                    browser.click(imReadyButton);
                }

                // The success message is out of the iframe section now, we go out to validate and back inside to the iframe to retry
                browser.switchBackToDefaultContent();
                Optional<WebElement> successMessageElement = browser.findFirstVisibleElementQuick(successMessage, 1);
                return successMessageElement.isPresent();

            } catch (Exception e) {
                return false;
            }
        };

        Browser.waitForExpressionToEqual(internalWaitForSuccessOrHandleError, null, true, "Waiting for liveness success or error message", 90, 1000);
    }
}


