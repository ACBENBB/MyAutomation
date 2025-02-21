package io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.infoAndShowMessageInBrowser;

public class GeneralCard extends AbstractPage {

    private static final ExtendedBy generalCard = new ExtendedBy("General card", By.xpath("//div[@class='card mb-5']//h4[contains(text(), 'General')]/ancestor::div[@class='card mb-5']"));
    private static final ExtendedBy totalTbeSecurities = new ExtendedBy("total tbe securities held", By.xpath("//div[contains(text(), 'Total TBE Securities:')]/..//h5/..//p"));

    public GeneralCard(Browser browser) {
        super(browser, generalCard);
    }

    public void waitForHoldTotalNumberOfSecurities(int numberOfTokens, int timeoutSeconds, int checkIntervalSeconds) {

        Function<String, Boolean> internalWaitForWalletToHoldTokens = t -> {
            try {
                browser.refreshPage();
                info("Checking number of tbe tokens in the general section...");
                String actualTokensHeld = browser.getElementText(totalTbeSecurities).trim();
                infoAndShowMessageInBrowser("TBE tokens held: " + actualTokensHeld);
                return (actualTokensHeld.equals(numberOfTokens+""));
            } catch (Exception e) {
                return false;
            }
        };

        String description = "waitForHoldTotalNumberOfSecurities=" + numberOfTokens;
        Browser.waitForExpressionToEqual(internalWaitForWalletToHoldTokens, null, true, description, timeoutSeconds, checkIntervalSeconds * 1000);
    }

    public String getTotalTbeSecurities() {
        return browser.getElementText(totalTbeSecurities).trim();
    }


}
