package io.securitize.infra.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;

class CustomConditions {
    static ExpectedCondition<List<WebElement>> numberOfVisibleElementsToBe(final By locator, final Integer number) {
        return webDriver -> {
            int currentNumber = 0;

            List<WebElement> elements = webDriver.findElements(locator);
            for (WebElement currentElement : elements) {
                if (currentElement.isDisplayed()) {
                    currentNumber++;
                }
            }
            info("Found " + currentNumber + " such elements");
            return currentNumber ==  number ? elements : null;
        };
    }
}
