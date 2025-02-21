package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieDashboardDocuments extends AbstractPage {

    private static final ExtendedBy yourDocumentsTitleLabel = new ExtendedBy("Your documents box title", By.xpath("//h1[text()='Your documents']"));
    private static final String documentNameOfRow = "//tbody/tr[$$ROW$$]/th[1]";
    private static final String documentDateOfRow = "//tbody/tr[$$ROW$$]/th[3]";
    private static final String documentDownloadOfRow = "//tbody/tr[1]/th[4]/button";
    private static final ExtendedBy documentImageDownload = new ExtendedBy("Image displayed when Download button is clicked", By.xpath("//body/img"));



    public NieDashboardDocuments(Browser browser) {
        super(browser, yourDocumentsTitleLabel);
    }

    public String getDocumentName(String row) {
        ExtendedBy element = new ExtendedBy("Your document name", By.xpath(documentNameOfRow.replace("$$ROW$$", row)));
        return browser.getElementText(element);
    }

    public String getDocumentDate(String row) {
        ExtendedBy element = new ExtendedBy("Your document date", By.xpath(documentDateOfRow.replace("$$ROW$$", row)));
        return browser.getElementText(element);
    }

    public void clickDownload(String row) {
        ExtendedBy element = new ExtendedBy("Download button", By.xpath(documentDownloadOfRow.replace("$$ROW$$", row)));
        browser.click(element, false);
    }

    public void waitForDocumentImageToBeDisplayed(){
        browser.waitForElementVisibility(documentImageDownload);
    }


}