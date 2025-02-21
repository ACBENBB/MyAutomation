package io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;

public class DocumentsCard extends AbstractCard {

    private static final ExtendedBy documentsCard = new ExtendedBy("Documents card", By.xpath("//div[@class='card mb-5']//h4[contains(text(), 'Documents')]/ancestor::div[@class='card mb-5']"));
    private static final ExtendedBy documentsTableRows = new ExtendedBy("Documents card - table's rows", By.tagName("tr"));
    private static final ExtendedBy documentsTableThumbnail = new ExtendedBy("Documents card - thumbnail", By.xpath("//img[@alt='Document image']"));

    public DocumentsCard(Browser browser) {
        super(browser, "Documents");
    }

    @Override
    public int getEntityCount() {
        int result = browser.findElementsInElement(documentsCard, documentsTableThumbnail).size();
        info("found " + result + " rows in table");
        return result;
    }

    @Override
    public List<WebElement> getEntityTableRows() {
        return browser.findElementsInElement(documentsCard, documentsTableRows);
    }
}
