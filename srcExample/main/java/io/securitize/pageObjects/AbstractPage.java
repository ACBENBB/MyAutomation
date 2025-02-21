package io.securitize.pageObjects;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.exceptions.PageNotLoadedException;
import io.securitize.infra.utils.EmailWrapper;
import io.securitize.infra.utils.RegexWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;


public abstract class AbstractPage<R> {
    protected Browser browser;

    protected static final ExtendedBy acceptCookiesButton = new ExtendedBy("Cybot - Accept cookies button", By.xpath("//*[@id='CybotCookiebotDialogBodyButtonAccept']"));

    public AbstractPage(Browser browser, ExtendedBy... elements) {
        this.browser = browser;
        verifyOnPage(elements);
    }

    private void verifyOnPage(ExtendedBy[] elements) { // Verifying that the desired page has finished loading
        debug("Verifying we are on the correct page by elements...");

        for (ExtendedBy currentBy : elements) {
            try {
                browser.waitForElementVisibility(currentBy);
            } catch (TimeoutException e) {
                throw new PageNotLoadedException(this.getClass().getSimpleName(), currentBy);
            }
        }

        debug("Validated we are on the correct page by elements!");
    }

    protected String extractLinkFromEmail(String recipientAddress, String regex) {
        int maxAllowedAttempts = 3;
        // in order to retry reading the email in case of connections error, we run this logic in
        // a loop - which should exit after first successful attempt
        for (int i = 1; i <= maxAllowedAttempts ; i++) {
            info(String.format("Attempting to extractLinkFromEmail (%s/%s)", i, maxAllowedAttempts));

            try {
                String emailContent = EmailWrapper.waitAndGetEmailContentByRecipientAddressAndRegex(recipientAddress, regex);
                return RegexWrapper.getFirstGroup(emailContent, regex);
            } catch (IllegalStateException e) {
                warning("An error occur trying to read emails. Trying to reconnect and re-read emails. Details: " + e, false);
            } catch (RuntimeException e) {
                throw e; // throw the original error as is - no need to add additional details
            } catch (Exception e) {
                errorAndStop("An error occur trying to extract link from email. Details: " + e, false);
            }
        }

        errorAndStop(String.format("Even after %s attempts, still couldn't extract link from email. Stopping...", maxAllowedAttempts), false);
        return null;
    }

    protected List<ExtendedBy> visibleElements() {
        List<ExtendedBy> elementsList = new ArrayList<ExtendedBy>();
        for (Method m : this.getClass().getMethods()) {
            /*The following if will ensure to only use getters that starts with "getVisible" name and that these getters
            will not need parameters to work which normally indicates this is not a regular getter*/
            if (m.getName().startsWith("getVisible") && m.getParameterTypes().length == 0) {
                Object r = "";
                try {
                    r = m.invoke(this);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                elementsList.add((ExtendedBy) r);
            }
        }
        return elementsList;
    }

    public R clickAcceptCookies() {
        browser.waitForPageStable(Duration.ofSeconds(3));
        List<WebElement> elements = browser.findElementsQuick(acceptCookiesButton, 3);
        if (!elements.isEmpty() && elements.get(0).isDisplayed() && elements.get(0).isEnabled()) {
            browser.click(acceptCookiesButton, true);
        }

        return (R) this;
    }

    protected <T extends Annotation> T getTestAnnotation(Class<T> annotationClass) {
        for (StackTraceElement currentStackFrame: Thread.currentThread().getStackTrace()) {
            try {
                Class<?> currentClass = Class.forName(currentStackFrame.getClassName());
                Optional<Method> currentMethod = Arrays.stream(currentClass.getDeclaredMethods()).filter(x -> x.getName().contains(currentStackFrame.getMethodName())).findFirst();
                if (currentMethod.isPresent()) {
                    if (currentMethod.get().getAnnotation(Test.class) != null) {
                        return currentMethod.get().getAnnotation(annotationClass);
                    }
                }
            } catch (ClassNotFoundException e) {
                debug("method not found during reflection");
            }
        }
        errorAndStop("Can't find a method in the stack-trace with Test annotation!", false);
        return null;
    }

    public R runAction(Runnable runnable) {
        runnable.run();
        return (R)this;
    }

    public ExtendedBy setValueInXpath(String pattern, String value, String description) {
        String xPathString = String.format(pattern, value);
        ExtendedBy by = new ExtendedBy(description, By.xpath(xPathString));
        return by;
    }

    public ExtendedBy setValueInXpath(String pattern, int value, String description) {
        String xPathString = String.format(pattern, value);
        ExtendedBy by = new ExtendedBy(description, By.xpath(xPathString));
        return by;
    }

    public String waitInvestorDetailsPage(int timeoutSeconds, ExtendedBy clickElement){
        browser.clickWithJs(browser.findElement(clickElement));
        Function<String, String> internalWaitInvestorDetailsPage = t -> {
            try {
                String pageTitle = browser.getPageTitle().toLowerCase();
                browser.waitForPageStable(Duration.ofSeconds(5));
                if (!pageTitle.contains("details")){
                    browser.waitForElementClickable(clickElement);
                    browser.clickWithJs(browser.findElement(clickElement));
                    pageTitle = browser.getPageTitle().toLowerCase();
                }
                return pageTitle;
            } catch (Exception e) {
                return null;
            }
        };
        String description = "Wait for Investor Details Page to upload";
        return Browser.waitForExpressionNotNull(internalWaitInvestorDetailsPage, null, description, timeoutSeconds, 1000);
    }

}