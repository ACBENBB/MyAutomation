package io.securitize.infra.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;

import static io.securitize.infra.reporting.MultiReporter.debug;

public class WebEventListener implements WebDriverListener {

    private final WebDriver driver;

    public WebEventListener(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void beforeClick(WebElement element) {
        highlightElement(element);
    }

    @Override
    public void beforeClear(WebElement element) {
        highlightElement(element);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        highlightElement(element);
    }

    @Override
    public void beforeGetAttribute(WebElement element, String name) {
        highlightElement(element);
    }

    @Override
    public void beforeGetText(WebElement element) {
        highlightElement(element);
    }


    private void highlightElement(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;

        Object[] arg = { element };
        String originalBackgroundColor = jsExecutor.executeScript("return arguments[0].style.background;", arg).toString();
        jsExecutor.executeScript("arguments[0].style.background = 'yellow';", arg);

        // wait for the effect to become humanly visible
        try {
            Thread.sleep(150L);
        } catch (InterruptedException e) {
            debug("Thread sleep was Interrupted");
            Thread.currentThread().interrupt();
        }

        // restore original value
        jsExecutor.executeScript("arguments[0].style.background = '" + originalBackgroundColor + "';", arg);
    }
}