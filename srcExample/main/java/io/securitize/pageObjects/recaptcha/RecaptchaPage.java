package io.securitize.pageObjects.recaptcha;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static io.securitize.infra.reporting.MultiReporter.*;

public class RecaptchaPage extends AbstractPage<RecaptchaPage> {

    private static final ExtendedBy recaptchaIframe = new ExtendedBy("Recaptcha Iframe", By.xpath("//iframe[contains(@title, 'recaptcha')]"));
    private static final ExtendedBy audioChallengeButton = new ExtendedBy("Recaptcha audio challenge", By.id("recaptcha-audio-button"));
    private static final ExtendedBy visualChallengeButton = new ExtendedBy("Recaptcha visual challenge", By.id("recaptcha-image-button"));
    private static final ExtendedBy resetButton = new ExtendedBy("Recaptcha reset button", By.id("reset-button"));

    private static final int TIMEOUT_SECONDS = 5;
    private static final int SLEEP_SECONDS_BETWEEN_ATTEMPTS = 2;
    public RecaptchaPage(Browser browser) {
        super(browser);
    }

    public void handleRecaptchaIfNeeded() {
        handleRecaptchaIfNeeded(null);
    }

    /**
     * Handles the recaptcha logic if needed
     * @param recoverFunction Sometimes Google blocks us due to too many calls from the current IP. In such cases
     *                        we need to click the shown refresh button but the entire recaptcha flow is reset. In cases
     *                        such as login screen we need to re-click the login button. The logic in this function
     *                        needs to click or redo whatever is needed to make the recaptcha screen visible again
     */
    public void handleRecaptchaIfNeeded(Runnable recoverFunction) {
        if (!isRelevantEnvironment()) return;

        // check if the recaptcha iframe even exists
        List<WebElement> recaptchaIframeElement = browser.findElementsQuick(recaptchaIframe, TIMEOUT_SECONDS);
        if (recaptchaIframeElement.isEmpty()) {
            info("No recaptcha iFrame detected - Moving on");
        } else {
            // sometimes the recaptcha iFrame exists, but we should handle it only if it becomes visible
            try {
                browser.waitForElementVisibility(recaptchaIframe, TIMEOUT_SECONDS);
            } catch (TimeoutException e) {
                info("Recaptcha iFrame detected but not visible - Moving on");
                return;
            }

            browser.switchToFrame(recaptchaIframe);
            var recaptchaIframeToVanish = recaptchaIframeElement.get(0);
            // sometimes we can't handle recaptcha properly, so we will try several times if needed
            int attempts = 10;
            for (int i = 1; i <= attempts; i++) {
                info("Trying to beat recaptcha attempt #" + i);

                // the Buster extension adds a new button to auto-handle recaptcha, but that button in not accessible
                // to us as it is in a shadow-DOM. Our trick - find the button before it (The audio challenge button)
                // and click a few pixels to the right of it - landing in the button we need
                WebElement audioChallenge = browser.findElement(audioChallengeButton);
                info("Trying to click the auto-solve recaptcha button");
                Actions actions = new Actions(Browser.getDriver());
                int buttonWidth = audioChallenge.getSize().getWidth();
                try {
                    actions.moveToElement(audioChallenge).moveByOffset(buttonWidth, 0).click().perform();
                } catch (StaleElementReferenceException e) {
                    audioChallenge = browser.findElement(audioChallengeButton);
                    actions.moveToElement(audioChallenge).moveByOffset(buttonWidth, 0).click().perform();
                }

                // validate the iFrame was removed from sight - sign that we successfully solved the recaptcha
                try {
                    browser.switchBackToDefaultContent();
                    browser.waitForElementToVanish(recaptchaIframeToVanish, recaptchaIframe.getDescription());
                    info("Seems like we were able to beat recaptcha!");
                    browser.switchBackToDefaultContent();
                    return;
                } catch (TimeoutException e) {
                    info("The challenge button didn't vanish.. Will try again if possible in 2 seconds");
                    browser.switchToFrame(recaptchaIframe);
                    if (browser.findFirstVisibleElementQuick(visualChallengeButton, 3).isPresent()) {
                        info("Going back to the visual challenge mode");
                        browser.click(visualChallengeButton);
                    } else if (browser.findFirstVisibleElementQuick(resetButton, 3).isPresent()) {
                        info("Performing reset to the challenge");
                        browser.click(resetButton);
                        if (recoverFunction != null) {
                            browser.switchBackToDefaultContent();
                            browser.waitForPageStable(Duration.ofSeconds(3)); // give the UI enough time to stabilize
                            recoverFunction.run();
                            recaptchaIframeToVanish = browser.findElement(recaptchaIframe);
                            browser.switchToFrame(recaptchaIframe);
                        }
                    } else {
                        warning("Can't find nor the 'back to the visual challenge' button nor the 'reset' button", true);
                    }
                    browser.sleep(SLEEP_SECONDS_BETWEEN_ATTEMPTS * 1000);
                }
            }
            errorAndStop("Couldn't beat Recaptcha - must stop now", true);
        }
    }

    private boolean isRelevantEnvironment() {
        BypassRecaptcha bypassRecaptchaAnnotation = getTestAnnotation(BypassRecaptcha.class);
        if (bypassRecaptchaAnnotation != null) {
            String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
            Stream<String> bypassOnEnvironments = Arrays.stream(bypassRecaptchaAnnotation.environments());
            return bypassOnEnvironments.anyMatch(x -> x.equalsIgnoreCase("all") || x.equalsIgnoreCase(currentEnvironment));
        }

        return false;
    }
}
