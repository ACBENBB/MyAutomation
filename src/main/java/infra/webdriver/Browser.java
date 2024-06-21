package infra.webdriver;

import infra.utils.RandomGenerator;
import infra.utils.RegexWrapper;
import infra.enums.ExecuteBy;
import infra.exceptions.StringIsNotAsExpectedException;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v122.page.Page;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.*;
import org.testng.util.Strings;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static infra.reporting.MultiReporter.*;

public class Browser {

    private static final Logger logger = LoggerFactory.getLogger(Browser.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private final ThreadLocal<WebDriverWait> webDriverWait = new ThreadLocal<>();
    private final ThreadLocal<JavascriptExecutor> javascriptExecutor = new ThreadLocal<>();
    private static final int SLEEP_IN_MILLIS_AFTER_ACTION_FAIL = 1000;
    private static final int MAX_ACTION_ATTEMPTS = 3;
    private static final int MAX_STALE_ELEMENT_RETRIES = 5;

    private static final String MILLISECONDS_LITERAL = "milliseconds";

    private static final List<String> consoleErrorsToIgnore = List.of(
            "security - Error with Feature-Policy header: Unrecognized"
    );


    public Browser(WebDriver providedDriver) {
        // proper cleanup of previous content if needed
        driver.remove();
        webDriverWait.remove();
        javascriptExecutor.remove();

        driver.set(providedDriver);
        webDriverWait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(5)));
        javascriptExecutor.set((JavascriptExecutor) driver.get());

        if (!providedDriver.toString().contains("IOSDriver")) {
            clearAllCookies();
        }
    }

    public void setImplicitWaitTimeout(long timeoutInSeconds) {
        logger.debug(String.format("Setting driver implicit wait time to %s seconds", timeoutInSeconds));
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeoutInSeconds));
    }

    // call this method with your element and a color you like (red, green, orange, blue etc...)
    protected void highlightElement(ExtendedBy by, String color) {
        //keep the old style to change it back
        WebElement element = findElement(by);
        String originalStyle = element.getAttribute("style");
        String newStyle = "border: 4px solid " + color + ";" + originalStyle;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Change the style
        js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style','" + newStyle + "');},0);", element);
    }

    public void clearAllCookies() {
        driver.get().manage().deleteAllCookies();
    }

    public WebElement findElement(ExtendedBy extendedBy) {
        return driver.get().findElement(extendedBy.getBy());
    }

    @SuppressWarnings("unused")
    public WebElement findElementInElement(WebElement parentElement, ExtendedBy child) {
        return parentElement.findElement(child.getBy());
    }

    public void resetImplicitWaitTimeout(long value) {
        logger.debug(String.format("Resetting driver implicit wait time to %s seconds", value));
        setImplicitWaitTimeout(value);
    }

    public List<WebElement> findElementsInElementQuick(WebElement parentElement, ExtendedBy child, int timeoutSeconds) {
        setImplicitWaitTimeout(timeoutSeconds);
        List<WebElement> result = parentElement.findElements(child.getBy());
        resetImplicitWaitTimeout(5);
        return result;
    }

    public WebElement findElementInElement(ExtendedBy parent, ExtendedBy child) {
        WebElement parentElement = driver.get().findElement(parent.getBy());
        return parentElement.findElement(child.getBy());
    }

    public List<WebElement> findElementsInElement(ExtendedBy parent, ExtendedBy child) {
        WebElement parentElement = driver.get().findElement(parent.getBy());
        return parentElement.findElements(child.getBy());
    }

    public List<WebElement> findElementsQuick(ExtendedBy extendedBy) {
        return findElementsQuick(extendedBy, 5);
    }

    public List<WebElement> findElementsQuick(ExtendedBy extendedBy, int timeoutSeconds) {
        setImplicitWaitTimeout(timeoutSeconds);
        List<WebElement> result = driver.get().findElements(extendedBy.getBy());
        resetImplicitWaitTimeout(5);
        return result;
    }

    public List<WebElement> findVisibleElements(ExtendedBy extendedBy) {
        List<WebElement> allSuchElements = findElements(extendedBy);
        return allSuchElements.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
    }

    public Optional<WebElement> findFirstVisibleElementQuick(ExtendedBy extendedBy, int timeoutSeconds) {
        List<WebElement> allSuchElements = findElementsQuick(extendedBy, timeoutSeconds);
        return allSuchElements.stream().filter(WebElement::isDisplayed).findFirst();
    }

    public void clickAndWaitForElementToVanish(ExtendedBy by) {
        clickAndWaitForElementToVanish(by, ExecuteBy.WEBDRIVER, true);
    }

    public void clickAndWaitForElementToVanish(ExtendedBy by, ExecuteBy executeBy, boolean forceElementOnTop) {
        clickAndWaitForElementToVanish(by, executeBy, forceElementOnTop, -1);
    }

    public void clickAndWaitForElementToVanish(ExtendedBy by, ExecuteBy executeBy, boolean forceElementOnTop, int timeoutSeconds) {
        WebElement element = click(by, executeBy, forceElementOnTop);
        logger.info("waiting for element to vanish after clicking it: " + by.getDescription());
        if (timeoutSeconds < 0) {
            webDriverWait.get().until(ExpectedConditions.invisibilityOf(element));
        } else {
            logger.info("waiting for custom amount of seconds: " + timeoutSeconds);
            new WebDriverWait(driver.get(), Duration.ofSeconds(timeoutSeconds)).until(ExpectedConditions.invisibilityOf(element));
        }
        logger.info("Element vanished! Moving on...");
    }

    public void waitForElementToVanish(ExtendedBy by) {
        WebElement element = findElement(by);
        waitForElementToVanish(element, by.getDescription());
    }

    public void waitForElementToVanish(WebElement element, String description) {
        try {
            logger.info("waiting for element to vanish: " + description);
            webDriverWait.get().until(ExpectedConditions.invisibilityOf(element));
            logger.info("Element vanished! Moving on...");
        } catch (StaleElementReferenceException e) {
            logger.info("Element vanished!");
        }
    }

    public void waitForPageStable() {
        waitForPageStable(null);
    }

    public void waitForPageStable(Duration duration) {
        logger.debug("Waiting for page to become stable - by no body changes between several seconds");
        final String[] originalBody = {null};

        FluentWait<WebDriver> waiter;
        if (duration == null) {
            waiter = webDriverWait.get();
        } else {
            waiter = new WebDriverWait(driver.get(), Duration.ofSeconds(duration.getSeconds()))
                    .pollingEvery(duration);
        }

        waiter.until(webDriver -> {
            try {
                String currentBody = webDriver.getPageSource();

                // if need more waiting
                if ((originalBody[0] == null) || (!currentBody.equals(originalBody[0]))) {
                    originalBody[0] = currentBody;
                    return false;
                }
                return true;
            } catch (Exception e) {
                logger.debug("Error occur trying to wait for page stable: " + ExceptionUtils.getStackTrace(e));
                return false;
            }
        });
        logger.debug("Page now seems stable!");
    }

    public WebElement scrollWaitClick(ExtendedBy by) {
        scrollToBottomOfElement(by);
        return click(by, ExecuteBy.WEBDRIVER, true);
    }

    public List<WebElement> findElements(ExtendedBy extendedBy) {
        return driver.get().findElements(extendedBy.getBy());
    }

    public WebElement click(ExtendedBy by, ExecuteBy executeBy, boolean forceElementOnTop) {
        WebElement webElement;
        highlightElement(by,"yellow");
        try {
            webElement = webDriverWait.get().until(ExpectedConditions.elementToBeClickable(by.getBy()));
            click(webElement, by.getDescription(), executeBy, forceElementOnTop);
        } catch (StaleElementReferenceException e) {
            logger.debug("A StaleElementReferenceException occur trying to click element: " + by.toString() + ". Trying again. Details: " + e);
            webElement = webDriverWait.get().until(ExpectedConditions.elementToBeClickable(by.getBy()));
            click(webElement, by.getDescription(), executeBy, forceElementOnTop);
        } catch (TimeoutException e) {
            String message = String.format("Timeout has occurred trying to wait for element '%s' to become clickable by '%s'", by.getDescription(), by.getBy());
            throw new TimeoutException(message, e);
        }
        return webElement;
    }


    public WebElement click(ExtendedBy by) {
        return click(by, ExecuteBy.WEBDRIVER, true);
    }

    public void click(WebElement element, String description, boolean forceElementOnTop) {
        click(element, description, ExecuteBy.WEBDRIVER, forceElementOnTop);
    }

    public void click(WebElement element, String description) {
        click(element, description, ExecuteBy.WEBDRIVER, true);
    }

    public void click(ExtendedBy by, boolean forceElementOnTop) {
        click(by, ExecuteBy.WEBDRIVER, forceElementOnTop);
    }

    public WebElement clickWithJs(ExtendedBy by) {
        return click(by, ExecuteBy.JAVASCRIPT, true);
    }

    public void click(WebElement webElement, String description, ExecuteBy executeBy, boolean forceElementOnTop) {
        for (int i = 1; i <= MAX_ACTION_ATTEMPTS; i++) {

            try {
                info("About to Click on element: " + description);

                webDriverWait.get().until(ExpectedConditions.elementToBeClickable(webElement));

                scrollToElement(webElement, forceElementOnTop);


                if (executeBy == ExecuteBy.WEBDRIVER) {
                    webElement.click();
                    info("Clicked on element: " + description);
                } else if (executeBy == ExecuteBy.JAVASCRIPT) {
                    javascriptExecutor.get().executeScript("arguments[0].click();", webElement);
                    info("Clicked on element with JS: " + description);
                }
                break;
            } catch (Exception e) {
                if (i < MAX_ACTION_ATTEMPTS) {
                    info("[WARNING]: Click on " + description + " failed due to (" + e + ") . Trying again in " + SLEEP_IN_MILLIS_AFTER_ACTION_FAIL + " " + MILLISECONDS_LITERAL);
                    sleep(SLEEP_IN_MILLIS_AFTER_ACTION_FAIL);
                } else {
                    throw e;
                }
            }
        }
    }


    public void typeTextElement(ExtendedBy extendedBy, String text) {
        highlightElement(extendedBy, "orange");
        WebElement element = driver.get().findElement(extendedBy.getBy());
        boolean successful = false;

        for (int i = 1; i <= MAX_STALE_ELEMENT_RETRIES; i++) {
            try {
                typeTextElement(element, extendedBy.getDescription(), text, extendedBy.isSecureField());
                successful = true;
                break;
            } catch (StaleElementReferenceException e) {
                logger.debug("A StaleElementReferenceException occur trying to send text to element: " + extendedBy.getDescription() + " trying again... (" + i + "/" + MAX_STALE_ELEMENT_RETRIES + ")");
                element = driver.get().findElement(extendedBy.getBy());
            }
        }

        if (!successful) {
            errorAndStop("Unable to properly send text to element - stale element error occur too many times", true);
        }

        // remove focus from element - so we can see validation errors if they occur
        try {
            javascriptExecutor.get().executeScript("arguments[0].blur()", element);
        } catch (StaleElementReferenceException e) {
            // this is not a crucial event so we can ignore staleElement error at such time
        }
    }

    public void sendKeysElement(ExtendedBy extendedBy, String keysDescription, CharSequence... keys) {
        logger.info("Sending keys: " + keysDescription + " to " + extendedBy.getDescription());
        WebElement element = driver.get().findElement(extendedBy.getBy());
        element.sendKeys(keys);
        logger.info("sent!");
    }

    private void typeTextElement(WebElement element, String description, String text, boolean isSecureField) {
        String textToShow;
        if (isSecureField) {
            textToShow = "******** (secure field)";
        } else {
            textToShow = text;
        }

        for (int i = 1; i <= MAX_ACTION_ATTEMPTS; i++) {
            try {

                logger.info("About to send text '" + textToShow + "' on element: " + description);

                scrollToElement(element, false);

                logger.info("Clearing text in element " + description);
                element.clear();

                element.sendKeys(text);
                logger.info("Finished sending text '" + textToShow + "' to element: " + description);
                break;
            } catch (Exception e) {
                if (i < MAX_ACTION_ATTEMPTS - 1) {
                    logger.info("[WARNING]: Send text to element " + description + " failed (" + e + ") . Trying again in " + SLEEP_IN_MILLIS_AFTER_ACTION_FAIL + " " + MILLISECONDS_LITERAL);
                    sleep(SLEEP_IN_MILLIS_AFTER_ACTION_FAIL);
                } else {
                    throw e;
                }
            }
        }
    }


    public void sendTextElementIncTrimDecimals(ExtendedBy extendedBy, String text) {
        WebElement element = driver.get().findElement(extendedBy.getBy());
        logger.info("About to send text '" + text + "' on element: " + extendedBy.getDescription());
        element.sendKeys(text);
        //delete 3 chars: ".00"
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(Keys.BACK_SPACE);
        //send last 2 chars instead of the 2 decimal digits ".00"
        element.sendKeys(text.substring(text.length() - 4, text.length() - 2));
        logger.info("Finished sending text '" + text + "' to element: " + extendedBy.getDescription());
    }

    /**
     * Used in rare cases where regular webdriver clear just doesn't work
     *
     * @param extendedBy element to locate and clear
     */
    public void clearTextElement(ExtendedBy extendedBy) {
        WebElement element = findElement(extendedBy);
        waitForElementTextNotEmpty(extendedBy);

        String elementText = getElementText(element, extendedBy.getDescription());
        for (int i = 0; i < elementText.length(); i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void clearTextInput(ExtendedBy extendedBy) {
        WebElement element = findElement(extendedBy);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }

    public void typeTextElementCtrlA(ExtendedBy extendedBy, String text) {
        WebElement element = driver.get().findElement(extendedBy.getBy());
        if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("mac")) {
            elementSendKeys(extendedBy, Keys.COMMAND, "a");
        } else {
            elementSendKeys(extendedBy, Keys.CONTROL, "a");
        }
        element.sendKeys(text);
    }

    public void elementSendKeys(ExtendedBy extendedBy, Keys key, String keyboardLetter) {
        WebElement element = driver.get().findElement(extendedBy.getBy());
        element.sendKeys(key + keyboardLetter);
    }

    public String getElementText(ExtendedBy extendedBy) {
        waitForElementVisibility(extendedBy);
        try {
            WebElement webElement = driver.get().findElement(extendedBy.getBy());
            return getElementText(webElement, extendedBy.getDescription());
        } catch (StaleElementReferenceException e) {
            logger.debug("Stale element issue occur reading text of element " + extendedBy.getDescription() + ". Trying again...");
            WebElement webElement = driver.get().findElement(extendedBy.getBy());
            return getElementText(webElement, extendedBy.getDescription());
        }
    }

    public String getElementText(WebElement webElement, String elementDescription) {
        String text = webElement.getText();
        if (Strings.isNullOrEmpty(text)) {
            text = webElement.getAttribute("value");
        }
        logger.info(elementDescription + " text: '" + text + "'");
        return text;
    }


    public void selectElementByVisibleText(ExtendedBy extendedBy, String value) {
        Select select = new Select(driver.get().findElement(extendedBy.getBy()));
        select.selectByVisibleText(value);
        logger.info("selected value " + value + " in element: " + extendedBy.getDescription());
    }

    public String selectRandomValueInElement(ExtendedBy extendedBy, boolean allowFirstValue) {
        for (int i = 0; i < 5; i++) {
            try {
                Select select = new Select(driver.get().findElement(extendedBy.getBy()));
                int optionsCount = select.getOptions().size();
                int minimalValue = allowFirstValue ? 0 : 1;
                int randomIndex = RandomGenerator.randInt(minimalValue, optionsCount - 1);
                select.selectByIndex(randomIndex);
                String selectedText = select.getOptions().get(randomIndex).getText();
                logger.info("selected random value of " + selectedText + " in element: " + extendedBy.getDescription());
                return selectedText;
            } catch (StaleElementReferenceException e) {
                logger.debug("A StaleElementReferenceException occur trying to select random value in element " + extendedBy.toString() + ". Trying again. Details: " + e);
            }
        }
        errorAndStop("Too many StaleElementReferenceException occur. Can't resume", false);
        return null;
    }

    public void navigateTo(String url) {
        logger.info("Navigating to: " + url);
        if (url.contains(".production.")) {
            url = url.replace(".production", "");
        }
        driver.get().navigate().to(url);
    }

    public String getCurrentUrl() {
        String result = driver.get().getCurrentUrl();
        logger.info("Current URL is: " + result);
        return result;
    }

    public boolean isElementVisible(ExtendedBy extendedBy) {
        List<WebElement> elements = findElements(extendedBy);
        return (elements.size() > 0) && (elements.get(0).isDisplayed());
    }

    public boolean isElementVisibleQuick(ExtendedBy extendedBy) {
        return isElementVisibleQuick(extendedBy, 2);
    }

    public boolean isElementVisibleQuick(ExtendedBy extendedBy, int timeoutSeconds) {
        // do to possible StaleElementException which sometimes happens here, we try it several times if needed
        int maxAttempts = 3;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                List<WebElement> elements = findElementsQuick(extendedBy, timeoutSeconds);
                return (elements.size() > 0) && (elements.get(0).isDisplayed());
            } catch (StaleElementReferenceException e) {
                String message = String.format("StaleElementReferenceException occur trying to determine if element '%s' is visible (quick) - this was attempt %s/%s", extendedBy.getDescription(), i, maxAttempts);
                logger.info(message);
                sleep(1000);
            }
        }

        String message = String.format("Error! Even after %s attempts, we weren't able to figure out if element '%s' is visible (quick) or not", maxAttempts, extendedBy.getDescription());
        errorAndStop(message, true);
        return false;
    }

    public void waitForElementVisibility(ExtendedBy extendedBy, int timeoutSeconds) {
        logger.info("Waiting for '" + extendedBy.getDescription() + "' to be visible by: " + extendedBy.getBy() + " waiting up to " + timeoutSeconds + " seconds");
        var wait = new WebDriverWait(driver.get(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(extendedBy.getBy()));
    }

    public WebElement waitForElementVisibility(ExtendedBy extendedBy) {
        logger.info("Waiting for '" + extendedBy.getDescription() + "' to be visible by: " + extendedBy.getBy());
        return webDriverWait.get().until(ExpectedConditions.visibilityOfElementLocated(extendedBy.getBy()));
    }

    public void waitForElementClickable(ExtendedBy extendedBy, int timeoutSeconds) {
        logger.info("Waiting for '" + extendedBy.getDescription() + "' to be clickable by: " + extendedBy.getBy() + " waiting up to " + timeoutSeconds + " seconds");
        var wait = new WebDriverWait(driver.get(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(extendedBy.getBy()));
    }

    public void waitForElementCount(ExtendedBy extendedBy, int expectedCount) {
        logger.info("Waiting for '" + extendedBy.getDescription() + "' to be visible by: " + extendedBy.getBy());
        webDriverWait.get().until(ExpectedConditions.numberOfElementsToBe(extendedBy.getBy(), expectedCount));
    }

    public void waitForElementTextNotEmpty(ExtendedBy extendedBy) {
        logger.info("Waiting for '" + extendedBy.getDescription() + "' text to not be null. Finding it by: " + extendedBy.getBy());
        webDriverWait.get().until((ExpectedCondition<Boolean>) d -> {
            try {
                return StringUtils.isNotEmpty(findElement(extendedBy).getText()) || StringUtils.isNotEmpty(findElement(extendedBy).getAttribute("value"));
            } catch (Exception e) {
                return false;
            }
        });
    }

    public WebElement waitForElementClickable(ExtendedBy extendedBy) {
        logger.info("Waiting for '" + extendedBy.getDescription() + "' not to be clickable by: " + extendedBy.getBy());
        return webDriverWait.get().until(ExpectedConditions.elementToBeClickable(extendedBy.getBy()));
    }

    public void waitForElementTextValueNotZero(ExtendedBy extendedBy) {
        logger.info("Waiting for '" + extendedBy.getDescription() + "' text value to be not zero. Finding it by: " + extendedBy.getBy());
        webDriverWait.get().until((ExpectedCondition<Boolean>) d -> {
            try {
                String currentValueAsString = getElementText(extendedBy);
                logger.info("Current value is: " + currentValueAsString);
                return RegexWrapper.stringToDouble(currentValueAsString) != 0;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void waitForElementToHaveAttribute(WebElement element, String description, String attributeName) {
        logger.info("Waiting for element '" + description + "' to have an attribute named: " + attributeName);
        webDriverWait.get().until((ExpectedCondition<Boolean>) d -> {
            try {
                return StringUtils.isNotEmpty(element.getAttribute(attributeName));
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void scrollToElement(WebElement element, boolean forceElementOnTop) {
        String scrollToElementScript =
                "function isInViewPort(element) {" +
                        "	var bounding = element.getBoundingClientRect();" +
                        "	return (" +
                        "		bounding.top >= 0 &&" +
                        "		bounding.left >= 0 &&" +
                        "		bounding.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&" +
                        "		bounding.right <= (window.innerWidth || document.documentElement.clientWidth)" +
                        "	);" +
                        "}" +
                        "function isElementTopMost(element) {" +
                        "	var rect = element.getBoundingClientRect();" +
                        "	var x = rect.left;" +
                        "	var y = rect.top;" +
                        "	x += element.offsetWidth/2;" +
                        "	y += element.offsetHeight/2;" +
                        "	var topElement = document.elementFromPoint(x, y);" +
                        "	return (element.isSameNode(topElement));" +
                        "}" +
                        "function scrollToElement(element) {" +
                        "	// if element is not visible - scroll the element to top of screen\n" +
                        "	if (!isInViewPort(element)) {" +
                        "		element.scrollIntoView(true);" +
                        "	}" +
                        "	// if element still not visible (covered by another element) - scroll the element to bottom of screen\n" +
                        "	if (!isElementTopMost(element)) {" +
                        "		element.scrollIntoView(false);" +
                        "	}" +
                        "}" +
                        "scrollToElement(element);" +
                        "return isElementTopMost(element);";

        String scriptToAppend = "var element = arguments[0];";

        try {
            if (forceElementOnTop) {
                // sometimes scrolling can't be done until some CSS or JS code finishes and is hard to catch.
                // we will try to scroll our element into view until success - up to 10 seconds
                var wait = new WebDriverWait(driver.get(), Duration.ofSeconds(10));
                wait.until((driver) -> (javascriptExecutor.get().executeScript(scriptToAppend + scrollToElementScript, element)).equals(true));
            } else {
                javascriptExecutor.get().executeScript(scriptToAppend + scrollToElementScript, element);
            }
        } catch (JavascriptException e) {
            logger.debug("An error occur trying to scroll to element " + element + ". Details: " + e);
        }
    }

    public void scrollToBottomOfElement(ExtendedBy extendedBy) {
        WebElement element = findElement(extendedBy);
        javascriptExecutor.get().executeScript("arguments[0].scrollIntoView(false)", element);
    }

    public void scrollDownToPageBottom() {
        //Scroll down till the bottom of the page
        javascriptExecutor.get().executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void sleep(long milliseconds) {
        logger.info("Sleeping for " + milliseconds + " " + MILLISECONDS_LITERAL);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.debug("sleep interrupted!");
            Thread.currentThread().interrupt();
        }
    }

    public String getElementAttribute(ExtendedBy locator, String attributeName) {
        WebElement element = findElement(locator);
        String result = element.getAttribute(attributeName);
        logger.info("For element " + locator.getDescription() + " attribute " + attributeName + "=" + result);
        return result;
    }

    public void setElementAttribute(WebElement element, String attribute, String value) {
        String script = String.format("arguments[0].setAttribute('%s', '%s')", attribute, value);
        javascriptExecutor.get().executeScript(script, element);
    }

    public void removeElementAttribute(WebElement element, String attribute, String value) {
        String script = String.format("arguments[0].removeAttribute('%s', '%s')", attribute, value);
        javascriptExecutor.get().executeScript(script, element);
    }

    public static String getFullPageScreenshot() {
        // legacy such as selenoid/moon1 not supported
        try {
            if (driver.get() != null) {
                if (!(driver.get() instanceof HasDevTools)) {
                    driver.set(new Augmenter().augment(driver.get()));
                }

                var devTools = ((HasDevTools) driver.get()).getDevTools(); // Create devTool instance
                devTools.createSessionIfThereIsNotOne();

                return devTools.send(Page.captureScreenshot(
                        Optional.of(Page.CaptureScreenshotFormat.JPEG),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(true),
                        Optional.empty()));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String getScreenshotAsBase64() {
        if (driver.get() != null) {
            try {
                return ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BASE64);
            } catch (NoSuchWindowException e) {
                new Browser(driver.get()).switchToLatestWindow(); // sometimes we were focused on secondary window which closed
                return ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BASE64);
            }
        } else {
            logger.warn("Can't take screenshot when driver is not initialized", false);
            return null;
        }
    }

    public File getScreenshotAsFile() {
        return ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
    }

    public static String getBrowserConsoleErrors() {
        if (driver.get() == null) {
            logger.warn("Can't get browser console errors when driver is not initialized", false);
            return null;
        }
        LogEntries logs = driver.get().manage().logs().get("browser");
        StringBuilder stringBuilder = new StringBuilder();
        for (LogEntry entry : logs) {
            if (consoleErrorsToIgnore.stream().noneMatch(entry.getMessage()::contains)) {
                stringBuilder.append(entry.getMessage()).append("<br><br>").append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    public void refreshPage(boolean validateUrlDidNotChange) {
        refreshPage(validateUrlDidNotChange, true);
    }

    public void refreshPage(boolean validateUrlDidNotChange, boolean waitForPageStable) {
        String originalUrl = getCurrentUrl();

        logger.debug("Adding element in page so we know refresh is done (once element no longer exists)");
        String addingElementScript = "document.body.innerHTML += \"<div id='uniqueElementForAutomationRefresh'></div>\"";
        executeScript(addingElementScript);
        WebElement uniqueElementForAutomationRefresh = driver.get().findElement(By.id("uniqueElementForAutomationRefresh"));

        logger.info("Refreshing current page... (" + originalUrl + ")");
        driver.get().navigate().refresh();

        waitForElementToVanish(uniqueElementForAutomationRefresh, "Element indicating if refresh finished");
        if (waitForPageStable) {
            waitForPageStable();
        }
        logger.info("Finished refreshing page!");

        if (validateUrlDidNotChange) {
            String currentUrl = getCurrentUrl();
            if (!currentUrl.equalsIgnoreCase(originalUrl)) {
                String message = String.format("Url changed after a refresh action on the page - should not have happened! Original url was: '%s' and current url is: '%s'", originalUrl, currentUrl);
                logger.warn(message, true);
                navigateTo(originalUrl);
            }
        }
    }

    public void refreshPage() {
        refreshPage(false, true);
    }

    public int getNumberOfOpenWindows() {
        int result = driver.get().getWindowHandles().size();
        logger.info("There are " + result + " open windows/tabs");
        return result;
    }

    public void waitForNumbersOfTabsToBe(int expected) {
        waitForNumbersOfTabsToBe(expected, 30);
    }

    public void waitForNumbersOfTabsToBe(int expected, int timeoutSeconds) {
        Function<String, Boolean> internalWaitForNumberOfTabs = t -> {
            int numberOfOpenTabs = driver.get().getWindowHandles().size();
            return numberOfOpenTabs == expected;
        };

        String description = "NumbersOfTabsToBe " + expected;
        Browser.waitForExpressionToEqual(internalWaitForNumberOfTabs, null, true, description, timeoutSeconds, 200);
    }


    public void waitForElementCssAttributeToEqual(WebElement element, String cssAttributeName, String expected) {
        waitForElementCssAttributeToEqual(element, cssAttributeName, expected, 30);
    }

    public void waitForElementCssAttributeToEqual(WebElement element, String cssAttributeName, String expected, int timeoutSeconds) {
        logger.info("Waiting for css attribute of " + cssAttributeName + " to become: " + expected);
        Function<String, Boolean> internalWaitForElementAttributeToEqual = t -> {
            String attributeValue = element.getCssValue(cssAttributeName);
            logger.debug("css attribute " + cssAttributeName + " expected value: " + expected + " actual value: " + attributeValue);
            return attributeValue.equals(expected);
        };

        String description = "CssAttribute " + cssAttributeName + " ToEqual " + expected;
        Browser.waitForExpressionToEqual(internalWaitForElementAttributeToEqual, null, true, description, timeoutSeconds, 200);
    }

    public WebElement waitForElementToBeClickable(ExtendedBy extendedBy, int timeoutSeconds) {
        logger.info("Waiting for '" + extendedBy.getDescription() + "' to be clickable by: " + extendedBy.getBy());
        return webDriverWait.get().until(ExpectedConditions.elementToBeClickable(extendedBy.getBy()));
    }

    public void switchToFirstWindow() {
        logger.info("Switching back to the original window/tab");
        ArrayList<String> tabs = new ArrayList<>(driver.get().getWindowHandles());
        driver.get().switchTo().window(tabs.get(0));
    }

    public void switchToFrame(ExtendedBy by) {
        logger.info("Switching to frame: " + by.getDescription());
        WebElement frameElement = findElement(by);
        driver.get().switchTo().frame(frameElement);
    }

    public void switchBackToDefaultContent() {
        logger.info("Returning from frame to default content");
        // driver.get().switchTo().defaultContent();
        driver.get().switchTo().parentFrame();
    }

    public int getNumberOfWindows() {
        return driver.get().getWindowHandles().size();
    }

    public void switchToLatestWindow() {
        int maxAttempts = 5;

        // try to switch to the latest window up to 5 times (windows could be just closing)
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                logger.info("Switching to last (new) tab - attempt #" + i);
                ArrayList<String> tabs = new ArrayList<>(driver.get().getWindowHandles());
                driver.get().switchTo().window(tabs.get(tabs.size() - 1));
                return;
            } catch (NoSuchWindowException e) {
                logger.info("Error occur trying to switch to latest window.. Will try again in 2 seconds. Details: " + e);
                sleep(2000);
            }
        }

        errorAndStop("Unable to switch to latest window even after " + maxAttempts + " attempts", true);
    }

    public void openNewTabAndSwitchToIt() {
        openNewTabAndSwitchToIt(null);
    }

    public void openNewTabAndSwitchToIt(String url) {
        // to handle rare cases where we couldn't open the new tab
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(org.openqa.selenium.TimeoutException.class)
                .withDelay(Duration.ofSeconds(5))
                .withMaxRetries(2)
                .build();

        if (url == null) {
            url = "about:blank";
        }

        final String finalUrl = url;
        Failsafe.with(retryPolicy).run(() -> {
            int originalNumberOfTabs = driver.get().getWindowHandles().size();

            logger.info("Opening new tab in url: " + finalUrl);
            // trying to navigate to such pages might cause the following error: "Not allowed to load local resource"
            if (finalUrl.startsWith("chrome://")) {
                javascriptExecutor.get().executeScript("window.open('about:blank','_blank');");
            } else {
                javascriptExecutor.get().executeScript("window.open('" + finalUrl + "','_blank');");
            }

            // wait for new tab to finish opening
            int expectedNumberOfTabs = originalNumberOfTabs + 1;
            waitForNumbersOfTabsToBe(expectedNumberOfTabs, 15);

            switchToLatestWindow();

            if (finalUrl.startsWith("chrome://")) {
                navigateTo(finalUrl);
            }
        });
    }

    public void disableReCaptcha() {
        String script = "window.disableReCaptchaUI=true";
        executeScript(script);
    }

    public String executeScriptSilent(String script, Object... objects) {
        return executeScriptInternal(script, false, objects);
    }

    public String executeScript(String script, Object... objects) {
        return executeScriptInternal(script, true, objects);
    }

    private String executeScriptInternal(String script, boolean isLogResponse, Object... objects) {
        if (isLogResponse) {
            logger.info("Executing js script: " + script);
        }
        Object response = javascriptExecutor.get().executeScript(script, objects);
        if (response != null) {
            String responseAsString = response.toString();
            if (isLogResponse) {
                logger.info("Finished executing js script with result: " + responseAsString);
            } else {
                logger.debug("Finished executing js script");
            }
            return responseAsString;
        }
        if (isLogResponse) {
            logger.debug("Finished executing js script");
        }
        return null;
    }

    public void closeLastTabAndSwitchToPreviousOne() {
        logger.info("Closing current tab opened in: " + driver.get().getCurrentUrl());
        ArrayList<String> tabs = new ArrayList<>(driver.get().getWindowHandles());
        driver.get().switchTo().window(tabs.get(tabs.size() - 1));
        driver.get().close();

        logger.info("Switching to previous tab");
        driver.get().switchTo().window(tabs.get(tabs.size() - 2));
    }

    /**
     * Used to wait until a provided expression results in an expected value
     *
     * @param <T>      input
     * @param <R>      output
     * @param func     function to run again and again until desired result is returned
     * @param param    possible parameter for provided function
     * @param expected expected result
     */
    public static <T, R> void waitForExpressionToEqual(Function<T, R> func, T param, R expected, String description, int timeoutSeconds, int checkIntervalMilliseconds) {
        waitForExpressionToEqual(func, param, expected, description, timeoutSeconds, checkIntervalMilliseconds, null);
    }

    /**
     * Used to wait until a provided expression results in an expected value
     *
     * @param <T>                       input
     * @param <R>                       output
     * @param func                      function to run again and again until desired result is returned
     * @param param                     possible parameter for provided function
     * @param expected                  expected result
     * @param exceptionToThrowOnTimeout specific error to throw in case of timeout
     */
    public static <T, R> void waitForExpressionToEqual(Function<T, R> func, T param, R expected, String description, int timeoutSeconds, int checkIntervalMilliseconds, RuntimeException exceptionToThrowOnTimeout) {
        long startTime = System.currentTimeMillis();
        long maxTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        float waitTimePercentage;

        do {
            R result = func.apply(param);
            if (result.equals(expected)) {
                logger.info("waitForExpressionToEqual (" + description + "): Finished! Condition met in " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
                return;
            }

            long passedTime = System.currentTimeMillis() - startTime;
            waitTimePercentage = (passedTime * 100.0f) / (timeoutSeconds * 1000);
            logger.debug("waitForExpressionToEqual (" + description + "): Condition not met yet.. Trying again in " + checkIntervalMilliseconds + " milliseconds. (Max wait time passed: " + waitTimePercentage + "% out of " + timeoutSeconds + " allowed seconds)");

            try {
                Thread.sleep(checkIntervalMilliseconds);
            } catch (InterruptedException e) {
                logger.warn("waitForExpressionToEqual (" + description + "): Interrupted!");
                Thread.currentThread().interrupt();
                return;
            }

        } while (System.currentTimeMillis() <= maxTime);

        // condition not met in max time
        if (exceptionToThrowOnTimeout == null) {
            throw new TimeoutException("waitForExpressionToEqual  (" + description + "): condition not met even in " + timeoutSeconds + " seconds");
        } else {
            throw exceptionToThrowOnTimeout;
        }
    }


    /**
     * Used to wait until a provided expression to not be null
     *
     * @param <T>   input
     * @param <R>   output
     * @param func  function to run again and again until desired result is returned
     * @param param possible parameter for provided function
     */
    public static <T, R> R waitForExpressionNotNull(Function<T, R> func, T param, String description, int timeoutSeconds, int checkIntervalMilliseconds) {
        long startTime = System.currentTimeMillis();
        long maxTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        float waitTimePercentage;

        do {
            R result = func.apply(param);
            if (result != null) {
                logger.info("waitForExpressionNotNull (" + description + "): Finished! Condition met in " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
                return result;
            }

            long passedTime = System.currentTimeMillis() - startTime;
            waitTimePercentage = (passedTime * 100.0f) / (timeoutSeconds * 1000);
            logger.info("waitForExpressionNotNull (" + description + "): Condition not met yet.. Trying again in " + checkIntervalMilliseconds + " milliseconds. (Max wait time passed: " + waitTimePercentage + "% out of " + timeoutSeconds + " allowed seconds)");

            try {
                Thread.sleep(checkIntervalMilliseconds);
            } catch (InterruptedException e) {
                logger.warn("waitForExpressionNotNull (" + description + "): Interrupted!");
                Thread.currentThread().interrupt();
                return null;
            }

        } while (System.currentTimeMillis() <= maxTime);

        // condition not met in max time
        throw new TimeoutException("waitForExpressionNotNull  (" + description + "): condition not met even in " + timeoutSeconds + " seconds");
    }


    @SuppressWarnings("unused")
    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void clearDriver() {
        driver.remove();
    }

    public static void showMessageInBrowser(String message) {
        if (driver.get() == null) {
            return;
        }

        String showMessageScript =
                "function showSnackBar(message) {" +
                        "var d = new Date();" +
                        "var suffix = d.getTime();" +
                        "var idValue = 'snackbar_' + suffix;" +

                        "var l = document.querySelectorAll('[id^=snackbar_]');" +
                        "l.forEach(x=>x.outerHTML='');" +

                        "document.body.innerHTML += \"<div id='\" + idValue + \"' style='background-color: #333; padding: 10px; border-radius: 20px; font-size: 18px; text-align: center; position: fixed; left: 50%; transform: translateX(-50%); top: 85%; color: #fff; z-index: 10000000; min-width:200px;pointer-events:none;'></div>\";" +
                        "snackbar = document.getElementById(idValue);" +

                        "snackbar.innerHTML = message;" +

                        "timeoutPointer = setTimeout(function(){ var snackbar = document.getElementById(idValue); if (snackbar != null) {snackbar.outerHTML = ''}; }, 3000);" +
                        "}" +

                        "showSnackBar('" + message + "');";

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver.get();

        javascriptExecutor.executeScript(showMessageScript);
    }

    @SuppressWarnings("unused")
    public void printPageSource() {
        String content = driver.get().findElement(By.tagName("body")).getAttribute("innerHTML");
        logger.info("Current page HTML: " + content);

        List<WebElement> frames = driver.get().findElements(By.tagName("iframe"));
        for (WebElement currentFrame : frames) {
            driver.get().switchTo().frame(currentFrame);
            content = driver.get().findElement(By.tagName("body")).getAttribute("innerHTML");
            logger.info("Current frame HTML: " + content);
            driver.get().switchTo().defaultContent();
        }
    }

    @SuppressWarnings("unused")
    public WebElement setFocusElement(ExtendedBy by) {
        logger.info("Setting element " + by.getDescription() + " as selected element (focus)");
        waitForElementVisibility(by);
        WebElement element = findElement(by);
        new Actions(driver.get()).moveToElement(element).perform();
        return element;
    }

    public void setSelectText(WebElement el, String value) {
        Select select = new Select(el);
        select.selectByVisibleText(value);
    }

    public void setSelectIndex(WebElement el, int index) {
        Select select = new Select(el);
        select.selectByIndex(index);
    }


    public void mouseHold(WebElement el) {
        Actions actions = new Actions(driver.get());
        actions.moveToElement(el).build().perform();
    }


    public void doubleClick(WebElement el) {
        Actions actions = new Actions(driver.get());
        actions.doubleClick(el).perform();
    }

    public void switchFrameByWebElement(WebElement frame) {
        driver.get().switchTo().frame(frame);
    }
    public void switchFrameById(String frameId) {
        WebElement frame = driver.get().findElement(By.id(frameId));
        driver.get().switchTo().frame(frame);
    }

    public void switchFrameByXpath(String frameXpath) {
        WebElement frame = driver.get().findElement(By.xpath(frameXpath));
        driver.get().switchTo().frame(frame);
    }
    public void quitFrame() {
        driver.get().switchTo().defaultContent();
    }

    public void accept() {
        driver.get().switchTo().alert().accept();
    }

    public void dismiss() {
        driver.get().switchTo().alert().dismiss();
    }

    public String getPopupText() {
        String message = driver.get().switchTo().alert().getText();
        return message;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        return date1;
    }

    public String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        return date1;
    }


    public List<String> convertListWebEtoString(List<WebElement> listWebE) {
        ArrayList<String> listString = new ArrayList<String>();
        for (int i = 0; i < listWebE.size(); i++) {
            String user = listWebE.get(i).getText();
            listString.add(user);
        }
        return listString;
    }

    public int getCurrentDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        return day;
    }

    public String getExactTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyy");
        Date date = new Date();
        String date1 = dateFormat.format(date);
        return date1;
    }

    public String getTitleChildWindow() {
        String titleChild = null;
        String parent = driver.get().getWindowHandle();
        Set<String> allHandles = driver.get().getWindowHandles();
        Iterator<String> allChilds = allHandles.iterator();
        while (allChilds.hasNext()) {
            String child_window = allChilds.next();
            if (!parent.equals(child_window)) {
                driver.get().switchTo().window(child_window);
                titleChild = driver.get().switchTo().window(child_window).getTitle();
                driver.get().close();
                driver.get().switchTo().window(parent);
                System.out.println(driver.get().getTitle());
            }
        }
        return titleChild;
    }


    public void pressTabKey(WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    public void waitForElementStaleness(WebElement element) {
        webDriverWait.get().until(ExpectedConditions.stalenessOf(element));
    }

    public void waitForPresenceOfElementLocated(By by) {
        webDriverWait.get().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public Actions getActionsClassInstance() {
        return new Actions(driver.get());
    }


    public boolean isElementEnabled(ExtendedBy extendedBy) {
        logger.info("Checking if element " + extendedBy.getDescription() + " is enabled");
        WebElement element;
        for (int i = 1; i <= MAX_STALE_ELEMENT_RETRIES; i++) {
            try {
                element = findElement(extendedBy);
                return element.isEnabled();
            } catch (StaleElementReferenceException e) {
                logger.debug("A StaleElementReferenceException occur trying to check if element is enabled: " + extendedBy.getDescription() + " trying again... (" + i + "/" + MAX_STALE_ELEMENT_RETRIES + ")");
                sleep(SLEEP_IN_MILLIS_AFTER_ACTION_FAIL);
            }
        }

        errorAndStop(String.format("Unable to find if element %s is enabled even after %s attempts", extendedBy.getDescription(), MAX_STALE_ELEMENT_RETRIES), true);
        return false;
    }

    public void downloadDocument(By el) {
        clickAndOpenInNewWindow(el);
        waitForNumbersOfTabsToBe(2);
        ArrayList<String> tab = new ArrayList<>(driver.get().getWindowHandles());
        driver.get().switchTo().window(tab.get(0));
        driver.get().switchTo().defaultContent();
    }

    public boolean waitForTextInElement(ExtendedBy extendedBy, String text) {
        return webDriverWait.get().until(ExpectedConditions.textToBePresentInElementLocated(extendedBy.getBy(), text));
    }

    public void clickAndOpenInNewWindow(By el) {
        Actions shiftClick = new Actions(driver.get());
        shiftClick.keyDown(Keys.SHIFT).click(driver.get().findElement(el)).keyUp(Keys.SHIFT).perform();
    }

    public boolean isElementPresent(ExtendedBy by, int timeoutSeconds) {
        var elements = findElementsQuick(by, timeoutSeconds);
        return !elements.isEmpty();
    }

    public boolean isElementPresentOrVisible(ExtendedBy by) {
        try {
            if (!isElementPresentTrue(by)) {
                return false;
            }
            if (!isElementVisibleTrue(by)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresentTrue(ExtendedBy by) {
        try {

            getElement(by).getLocation();
            return true;

        } catch (StaleElementReferenceException e) {

            e.printStackTrace();
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementVisibleTrue(ExtendedBy by) {
        try {

            return getElement(by).isDisplayed();

        } catch (StaleElementReferenceException e) {

            e.printStackTrace();
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getElement(ExtendedBy by) {
        try {

            return driver.get().findElement(by.getBy());

        } catch (StaleElementReferenceException e) {

            e.printStackTrace();
            return null;

        } catch (Exception e) {
            return null;
        }
    }

    public String getCookie(String cookieName) {
        return getDriver().manage().getCookieNamed(cookieName).getValue();
    }

    public void waitForTextToBePresentInElementWithReloadPage(ExtendedBy by, String expected, int checkIntervalMilliseconds, int timeoutSeconds, String elementDescription) {
        final String[] actual = new String[1];
        Function<String, String> waitForTextBeEqual = t -> {
            try {
                actual[0] = getElementText(by);
                if (!actual[0].equals(expected)) {
                    logger.info(elementDescription + " is not as " + expected + " expected. It is " + actual[0]);
                    refreshPage();
                }
            } catch (Exception e) {
                throw new RuntimeException(elementDescription + " did not change to " + expected + ", we reach timeout.");
            }
            return actual[0];
        };
        String errorMessage = "Actual text in " + elementDescription + " after " + timeoutSeconds + " seconds should be " + expected + " but it is not";
        StringIsNotAsExpectedException stringIsNotAsExpected = new StringIsNotAsExpectedException(errorMessage, actual[0], expected);
        Browser.waitForExpressionToEqual(waitForTextBeEqual, null, expected, errorMessage, timeoutSeconds, checkIntervalMilliseconds, stringIsNotAsExpected);
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public void navigateBack() {
        driver.get().navigate().back();
        waitForPageStable();
    }

    public boolean verifyAlertMessage(ExtendedBy extendedBy, String message, boolean isMessageShouldShow) {
        boolean isMessageShow = false;
        boolean isMessageCorrect = false;
        boolean verifyAlert = false;
        String actualMessage;

        // Bulk of condition if message should show
        if (isMessageShouldShow) {
            // Check if alert message show - It should
            if (isElementVisible(extendedBy)) {
                isMessageShow = true;
                actualMessage = getElementText(extendedBy);
                // If message show, check the text
                if (actualMessage.contains(message)) {
                    isMessageCorrect = true;
                    logger.info("Message shown and it is as expected" +
                            "The message is : " + actualMessage);
                } else {
                    logger.info("Message shown but it is not as expected" +
                            "Expected: " + message + " " +
                            "Actual: " + actualMessage);
                }
            } else {
                logger.info("Message is not shown and it should");
            }
        }
        // Bulk of condition if message should NOT show
        else {
            // Check if alert message show - It should NOT
            if (isElementVisible(extendedBy)) {
                isMessageShow = true;
                actualMessage = getElementText(extendedBy);
                logger.info("Message is shown and it should NOT");
                logger.info("The message is : " + actualMessage);
            } else {
                logger.info("Procedure succeed, there is no error message");
            }
        }
        if (isMessageShouldShow == isMessageShow) {
            verifyAlert = isMessageCorrect;
        }
        return verifyAlert;
    }

    public String getInstalledExtensionUrl(String extensionName) {
        // we find out the needed extension url from the chrome system summary page
        navigateTo("chrome://system");

        // get list of all installed extensions
        click(new ExtendedBy("expand extensions list button", By.id("btn-extensions-value")));
        String allElementsText = getElementText(new ExtendedBy("all installed extensions", By.id("div-extensions-value")));
        //handle scenarios of os (as windows) which its line separator contains "\r" for correct extensions split extraction
        String lineSeperator = System.lineSeparator();
        if (System.lineSeparator().contains("\r")) {
            lineSeperator = "\n";
        }
        String[] extensions = allElementsText.split(lineSeperator);
        // try to find the needed extension, and from it extract the needed id for the url
        Optional<String> extension = Arrays.stream(extensions).filter(x -> x.contains(extensionName)).findFirst();
        return extension.map(s -> "chrome-extension://" + s.split(":")[0].trim()).orElse(null);
    }

}
