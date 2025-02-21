package io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;

public class IssuanceCard extends AbstractCard {

    private static final ExtendedBy walletsCardLocator = new ExtendedBy("Wallets card", By.xpath("//div[@class='card mb-5']//h4[contains(text(), 'Wallets')]/ancestor::div[@class='card mb-5']"));
    private static final ExtendedBy issuancesTableRows = new ExtendedBy("Wallets card - issuances table's rows", By.xpath("(.//table)[2]//tr"));
    private static final ExtendedBy issuancesTable = new ExtendedBy("Wallets card - issuances table's rows", By.xpath("(.//table)[2]"));

    public IssuanceCard(Browser browser) {
        super(browser, "Issuances");
    }

    @Override
    public int getEntityCount() {
        int result = browser.findElementsInElement(walletsCardLocator, issuancesTableRows).size() - 1;
        info("found " + result + " rows in table");
        return result;
    }

    @Override
    public List<WebElement> getEntityTableRows() {
        return browser.findElementsInElement(walletsCardLocator, issuancesTableRows);
    }

    @Override
    public void scrollToTable() {
        List<WebElement> issuanceTableElement = browser.findElementsInElement(walletsCardLocator, issuancesTable);
        browser.executeScript("arguments[0].scrollIntoView(false)", issuanceTableElement.get(0));
    }
}
