package io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;

public class TransactionsCard extends AbstractCard {

    private static final ExtendedBy investmentsCard = new ExtendedBy("Investments card", By.xpath("//div[@class='card mb-5']//h4[contains(text(), 'Investment')]/ancestor::div[@class='card mb-5']"));
    private static final ExtendedBy investmentTransactionTrashIcon = new ExtendedBy("Investments card - transaction trash icon", By.xpath(".//i[contains(@class, 'trash')]"));
    private static final ExtendedBy transactionsTableRows = new ExtendedBy("Investments card - transactions table's rows", By.tagName("tr"));

    public TransactionsCard(Browser browser) {
        super(browser, "Transactions");
    }

    @Override
    public int getEntityCount() {
        int result = browser.findElementsInElement(investmentsCard, investmentTransactionTrashIcon).size();
        info("found " + result + " rows in table");
        return result;
    }

    @Override
    public List<WebElement> getEntityTableRows() {
        return browser.findElementsInElement(investmentsCard, transactionsTableRows);
    }
}
