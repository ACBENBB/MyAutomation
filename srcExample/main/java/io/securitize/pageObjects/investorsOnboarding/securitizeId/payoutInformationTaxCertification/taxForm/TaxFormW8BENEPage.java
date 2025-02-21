package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm;

import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.regex.Matcher;

public class TaxFormW8BENEPage extends AbstractPage {

    private static final ExtendedBy uploadW8BENECardDiv = new ExtendedBy("Submit W8BENE", By.xpath("//div[@style='display: none;']"));
    private static final ExtendedBy uploadW8BENECardInput = new ExtendedBy("Submit W8BENE", By.xpath("//input[@id='upload']"));
    private static final ExtendedBy downloadDocumentButton = new ExtendedBy("Download Document Button", By.xpath("//*[@id='downloadCard']/span"));
    private static final ExtendedBy uploadDocumentButton = new ExtendedBy("Upload Document Button", By.xpath("//input[@id='upload']"));
    private static final ExtendedBy submitButton = new ExtendedBy("Submit button", By.xpath("//button[text()='Submit']"));

    public TaxFormW8BENEPage(Browser browser) {
        super(browser);
    }

    public void uploadTaxForm() {
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "passport-front.jpg");
        upLoadDocument(uploadW8BENECardDiv, frontImagePath);
    }

    public TaxFormW8BENEPage upLoadDocument(ExtendedBy elementBrowse, String path) {
        browser.waitForPageStable();
        WebElement element = browser.findElement(elementBrowse);
        browser.setElementAttribute(element, "style", "display:block;");
        browser.typeTextElement(uploadW8BENECardInput, path);
        return this;
    }

    public void clickOnDownloadTemplateAndSave() {
        System.out.println("Downloading document...");
        browser.downloadDocument(downloadDocumentButton.getBy());
        browser.waitForNumbersOfTabsToBe(2);
        browser.closeLastTabAndSwitchToPreviousOne();
        browser.switchToFirstWindow();
    }

    public void clickOnUploadAndUploadTaxForm() {
        String pdfPath = null;
        File file = new File("test.txt");
        String sep = "\\\\";
        String osPath = file.getAbsolutePath();
        if (osPath.contains("/")) {
            osPath.replaceAll("/", File.separator);
        } else {
            osPath = osPath.replaceAll(Matcher.quoteReplacement(sep), File.separator);
        }
        browser.waitForElementVisibility(submitButton);
        pdfPath = osPath.replaceAll("test.txt", "");
        pdfPath = pdfPath + "target" + File.separator + "classes" + File.separator + "images" + File.separator + "accreditationTemplate.pdf";
        browser.sendKeysElement(uploadDocumentButton, "Sending keys to upload element", pdfPath);
        browser.waitForElementClickable(submitButton, 14);
    }

    public void submit() {
        browser.waitForElementClickable(submitButton, 14);
        browser.click(submitButton);
    }

}