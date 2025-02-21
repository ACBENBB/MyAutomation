package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.securitize.infra.reporting.MultiReporter.info;

public class CpAddDocument extends AbstractPage {

    private static final ExtendedBy uploadDocumentField = new ExtendedBy("upload document field", By.id("id"));
    private static final ExtendedBy image = new ExtendedBy("image", By.xpath("(//input[@type='file'])"));
    private static final ExtendedBy category = new ExtendedBy("image category", By.xpath("//label[contains(text(),'Category')]/../select"));
    private static final ExtendedBy documentTitle = new ExtendedBy("Document image title", By.xpath("//input[@name='title']"));
    private static final ExtendedBy documentShowToInvestor = new ExtendedBy("Document show to investor checkbox", By.xpath("//input[@type='checkbox']/../label"));

    private static final String documentCategoryRealValuePlaceholder = "//option[text()='%s']";

    private static final ExtendedBy uploadProgressBar = new ExtendedBy("upload progress bar", By.xpath("//div[@class='dz-progress']"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));


    CpAddDocument(Browser browser) {
        super(browser, uploadDocumentField);
    }

    public CpAddDocument uploadImage(String path) {
        WebElement element = browser.findElement(image);

        browser.setElementAttribute(element, "style", "display:block;");
        browser.typeTextElement(image, path);

        // wait for progress bar icon to show
        WebElement uploadProgressBarElement = browser.findElement(uploadProgressBar);
        try {
            browser.waitForElementCssAttributeToEqual(uploadProgressBarElement, "opacity", "1", 10);
        } catch (RuntimeException e) {
            info("Didn't catch the image upload progress bar showing up - could be due to automation slowness.. Trying to resume with the test");
        }

        // wait for progress bar icon to vanish
        uploadProgressBarElement = browser.findElement(uploadProgressBar);
        browser.waitForElementCssAttributeToEqual(uploadProgressBarElement, "opacity", "0");

        return this;
    }

    public String selectRandomCategory() {
        return browser.selectRandomValueInElement(category);
    }

    public CpAddDocument typeDocumentTitle(String title) {
        browser.typeTextElement(documentTitle, title);
        return this;
    }

    public CpAddDocument pickShowToInvestor() {
        browser.click(documentShowToInvestor, false);
        return this;
    }

    public String getInnerValueForDocumentCategory(String displayText) {
        String fullSelectorXpath = String.format(documentCategoryRealValuePlaceholder, displayText);
        ExtendedBy locator = new ExtendedBy("inner value of document category of " + displayText, By.xpath(fullSelectorXpath));
        return browser.getElementAttribute(locator, "value");
    }

    public void clickOk() {
        browser.clickAndWaitForElementToVanish(okButton);
    }
}