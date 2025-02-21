package io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.infoAndShowMessageInBrowser;

public class AffiliateCard extends AbstractCard{

    private static final ExtendedBy affiliateCard = new ExtendedBy("Affiliate card", By.xpath("//div[@class='card mb-5']//h5[contains(text(), 'Affiliate')]/ancestor::div[@class='card mb-5']"));
    private static final ExtendedBy affiliateRows = new ExtendedBy("affiliate rows", By.xpath(".//tr"));

    public AffiliateCard(Browser browser) {
        super(browser, "Affiliate");
    }

    @Override
    public int getEntityCount() {
        int result = browser.findElementsInElement(affiliateCard, affiliateRows).size();
        info("found " + result + " rows in table");
        return result;
    }

    @Override
    public List<WebElement> getEntityTableRows() {
        return browser.findElementsInElement(affiliateCard, affiliateRows);
    }

    public void waitForTableToContainAffiliateStatus(int index, String status, int timeoutSeconds, int checkIntervalSeconds) {

        Function<String, Boolean> waitForTableToContainAffiliateStatus = t -> {
            try {
                browser.refreshPage(true);
                info("Checking affiliate status...");
                String actualAffiliateStatus = getDetailAtIndex(index, "Status").trim();
                infoAndShowMessageInBrowser("Affiliate status: " + actualAffiliateStatus);
                scrollTableIntoView(index);
                return (actualAffiliateStatus.contains(status));
            } catch (Exception e) {
                return false;
            }
        };

        String description = "waitForTableToContainAffiliateStatus=" + status;
        Browser.waitForExpressionToEqual(waitForTableToContainAffiliateStatus, null, true, description, timeoutSeconds, checkIntervalSeconds * 1000);
    }

}
