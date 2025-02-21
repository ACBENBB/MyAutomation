package io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SecuritizeIdInvestorKyb03EntityDocuments extends SecuritizeIdInvestorAbstractPage {

    private static final ExtendedBy LegalDocumentsTitle = new ExtendedBy("Legal documents title", By.xpath("//*[text()='Legal documents']"));

    private static final ExtendedBy byLawsDocument = new ExtendedBy("By laws document document upload", By.xpath("(//input[@type='file'])[1]"));
    private static final ExtendedBy certificateOfFormationDocument = new ExtendedBy("Certificate of formation document upload", By.xpath("(//input[@type='file'])[2]"));
    private static final ExtendedBy ArticlesOfCorporationDocument = new ExtendedBy("Articles of corporation document upload", By.xpath("(//input[@type='file'])[3]"));
    private static final ExtendedBy shareHoldersListDocument = new ExtendedBy("Shareholder's list and authorized signers list document upload", By.xpath("(//input[@type='file'])[4]"));
    private static final ExtendedBy orgChartDocument = new ExtendedBy("Org Chart document upload", By.xpath("(//input[@type='file'])[5]"));
    private static final ExtendedBy deleteImageButton = new ExtendedBy("Delete image button", By.xpath("//*[@class='ml-3 border-0 w-auto h-auto bg-white text-danger uploader__remove-btn font-12 p-0' or @id='-delete']"));
    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 04 - continue button", By.id("wizard-continue"));

    public SecuritizeIdInvestorKyb03EntityDocuments(Browser browser) {
        super(browser, LegalDocumentsTitle);
    }

    public SecuritizeIdInvestorKyb03EntityDocuments byLawsDocumentUpload(String path) {
        internalUploadImage(byLawsDocument, path);
        return this;
    }

    public SecuritizeIdInvestorKyb03EntityDocuments certificateUpload(String path) {
        internalUploadImage(certificateOfFormationDocument, path);
        return this;
    }

    public SecuritizeIdInvestorKyb03EntityDocuments articlesUpload(String path) {
        internalUploadImage(ArticlesOfCorporationDocument, path);
        return this;
    }

    public SecuritizeIdInvestorKyb03EntityDocuments shareHoldersUpload(String path) {
        internalUploadImage(shareHoldersListDocument, path);
        return this;
    }

    public SecuritizeIdInvestorKyb03EntityDocuments orgChartUpload(String path) {
        internalUploadImage(orgChartDocument, path);
        return this;
    }


    public SecuritizeIdInvestorRegistration04KeyParties clickContinue() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorRegistration04KeyParties(browser);
    }

    public SecuritizeIdInvestorRegistration04_3KeyPartiesCheck clickContinueToLegalSignerCheck() {
        browser.click(continueButton, false);
        return new SecuritizeIdInvestorRegistration04_3KeyPartiesCheck(browser);
    }


    private void internalUploadImage(ExtendedBy elementBy, String path) {
        // check how many delete image buttons are visible
        browser.setImplicitWaitTimeout(2);
        int deleteImageButtonCount = 0;
        List<WebElement> elements = browser.findElements(deleteImageButton);
        for (WebElement currentElement : elements) {
            if (currentElement.isDisplayed()) {
                deleteImageButtonCount++;
            }
        }
        browser.resetImplicitWaitTimeout();

        WebElement element = browser.findElement(elementBy);
        browser.setElementAttribute(element, "style", "display:block;");
        browser.typeTextElement(elementBy, path);

        // wait for an additional delete button to show up
        browser.waitForElementVisibleCount(deleteImageButton, deleteImageButtonCount + 1);
    }
}
