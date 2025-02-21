package io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractCard extends AbstractPage {

    private final String entityTypeName;

    AbstractCard(Browser browser, String entityTypeName) {
        super(browser);
        this.entityTypeName = entityTypeName;
    }

    public abstract int getEntityCount();

    public abstract List<WebElement> getEntityTableRows();

    public void scrollToTable() {
        throw new NotImplementedException("Scrolling for this card type not yet implemented!");
    }

    public void waitForTableToContainNumberOfRows(int expectedNumberOfRows) {
        waitForTableToContainNumberOfRows(expectedNumberOfRows, 30);
    }

    public void waitForTableToContainNumberOfRows(int expectedNumberOfRows, int timeoutSeconds) {
        info("Waiting for table of " + entityTypeName + " to contain at least " + expectedNumberOfRows + " rows");
        Function<String, Boolean> internalWaitForTableToContainNumberOfRows = t -> {
            try {
                if (getEntityCount() < expectedNumberOfRows) {
                    return false;
                }

                List<WebElement> rows = getEntityTableRows();

                // find all table headers -> from it get index of needed column
                WebElement element = rows.get(1).findElements(By.tagName("td")).get(0);
                String value = element.getText();
                return !value.contains("Loading table data");
            } catch (StaleElementReferenceException e) {
                debug("A StaleElementReferenceException occur trying to waitForTableToContainNumberOfRows. Details: " + e.toString());
                return false;
            } catch (Exception e) {
                warning("An error occur trying to waitForTableToContainNumberOfRows. Details: " + e.toString(), true);
                return false;
            }
        };
        String description = "TableToContainNumberOfRows=" + expectedNumberOfRows;
        Browser.waitForExpressionToEqual(internalWaitForTableToContainNumberOfRows, null, true, description, timeoutSeconds, 1000);
    }

    public void scrollTableIntoView(int index) {
        waitForTableToContainNumberOfRows(index);
        List<WebElement> rows = getEntityTableRows();
        try {
            browser.scrollToElement(rows.get(index), false);
        } catch (StaleElementReferenceException e) {
            warning("Stale element occur trying to scroll " + entityTypeName + " index " + index + " into view. Trying again...");
            rows = getEntityTableRows();
            browser.scrollToElement(rows.get(index), false);
        }
    }

    public String getDetailAtIndex(int index, String columnName) {
        List<WebElement> rows = getEntityTableRows();
        int NUMBER_OF_ATTEMPTS_TO_READ_DETAIL_AT_INDEX = 5;

        // find all table headers -> from it get index of needed column
        List<WebElement> columnHeadersElements = rows.get(0).findElements(By.tagName("th"));
        for (int i = 0; i < columnHeadersElements.size(); i++) {
            if (columnHeadersElements.get(i).getText().equalsIgnoreCase(columnName)) {

                // we sometimes get multiple staleElementExceptions - so try this logic several times if needed
                for (int j = 0; j < NUMBER_OF_ATTEMPTS_TO_READ_DETAIL_AT_INDEX; j++) {
                    try {
                        browser.waitForPageStable(Duration.ofSeconds(5));
                        WebElement element = rows.get(index).findElements(By.tagName("td")).get(i);
                        String value = element.getText();
                        if (value.contains("Loading table data")) {
                            browser.waitForElementToVanish(element, "table text placeholder (loading data)");
                            element = rows.get(index).findElements(By.tagName("td")).get(i);
                            value = element.getText();
                        }
                        return value;
                    } catch (StaleElementReferenceException e) {
                        debug("Got StaleElementReferenceException on getDetailAtIndex");
                        try {
                            Thread.sleep(2000); // wait 2 seconds before trying again
                        } catch (InterruptedException interruptedException) {
                            // if interrupted exit the function
                            errorAndStop("Interrupted trying to getDetailAtIndex", true);
                        }
                        rows = getEntityTableRows();
                    }
                }

                errorAndStop("Couldn't getDetailAtIndex even after " + NUMBER_OF_ATTEMPTS_TO_READ_DETAIL_AT_INDEX + " attempts. Keep getting StaleElementException", true);
            }
        }

        throw new RuntimeException("Can't find column with name '" + columnName + "' in table of " + entityTypeName);
    }


    public void waitForEntityInTableStatusToBecome(int index, String expectedStatus, int timeoutSeconds, int intervalCheckSeconds, RuntimeException exceptionToThrowOnTimeout) {
        String originalUrl = browser.getCurrentUrl();

        Function<String, Boolean> internalWaitForStatus = t -> {
            try {
                if (!browser.getCurrentUrl().equalsIgnoreCase(originalUrl)) {
                    warning("Current url doesn't match original url.. Trying to get back there");
                    browser.navigateTo(originalUrl);
                } else {
                    // refresh page and wait for table to finish loading
                    browser.refreshPage(true);
                }
                waitForTableToContainNumberOfRows(index, 60);
                scrollTableIntoView(index);

                info("Checking " + entityTypeName + " status...");
                String entityStatus = getDetailAtIndex(index, "Status");
                infoAndShowMessageInBrowser(entityTypeName + " status: " + entityStatus);
                return (entityStatus.equals(expectedStatus));
            } catch (StaleElementReferenceException e) {
                debug("A StaleElementReferenceException occur trying to waitForEntityInTableStatusToBecome. Details: " + e.toString());
                return false;
            } catch (Exception e) {
                warning("An error occur trying to waitForEntityInTableStatusToBecome. Details: " + e.toString(), true);
                return false;
            }
        };

        String description = "EntityInTableStatusToBecome=" + expectedStatus;
        Browser.waitForExpressionToEqual(internalWaitForStatus, null, true, description, timeoutSeconds, intervalCheckSeconds * 1000, exceptionToThrowOnTimeout);
    }
}
