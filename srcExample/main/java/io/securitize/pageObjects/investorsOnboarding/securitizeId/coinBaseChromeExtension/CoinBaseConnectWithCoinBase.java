package io.securitize.pageObjects.investorsOnboarding.securitizeId.coinBaseChromeExtension;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;

public class CoinBaseConnectWithCoinBase extends AbstractPage<CoinBaseConnectWithCoinBase> {

    private static final ExtendedBy connectButton = new ExtendedBy("Connect button", By.xpath("//button[@data-testid='allow-authorize-button']"));
    private static final ExtendedBy signButton = new ExtendedBy("Sign button", By.xpath("//button[@data-testid='sign-message']"));


    public CoinBaseConnectWithCoinBase(Browser browser) {
        super(browser, connectButton);
    }

    public CoinBaseConnectWithCoinBase clickConnect() {
        waitForElementClickableInNewWindowAndClick(connectButton);
        return this;
    }



    public void clickSign() {
        waitForElementClickableInNewWindowAndClick(signButton);
    }

    private void waitForElementClickableInNewWindowAndClick(ExtendedBy by) {
        Function<ExtendedBy, Boolean> internalWaitForElementVisible = t -> {
            info("Switching to latest window and searching for element: " + by.getDescription());
            try {
                browser.switchToLatestWindow();
                List<WebElement> elementsFound = browser.findElementsQuick(by, 1);
                if (elementsFound.isEmpty()) {
                    info("element not found yet...");
                    return false;
                }

                browser.switchToLatestWindow();
                browser.waitForElementClickable(by, 1);
                info("element is found and clickable - will click on it now");
                browser.click(by, false);
                return true;
            } catch (Exception e) {
                info("Element not clickable, will try again");
                return false;
            }
        };

        Browser.waitForExpressionToEqual(internalWaitForElementVisible, null, true, "wait for element to be clickable in a new window and click it", 30, 1000);
    }
}