package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.securitize.infra.reporting.MultiReporter.info;

public class CpAddDocumentNewUI extends AbstractPage {

    private static final ExtendedBy documentsSection = new ExtendedBy("Documents section", By.xpath("//div[text()='Documents']"));
    private static final ExtendedBy image = new ExtendedBy("image", By.xpath("//input[@type='file']"));
    private static final ExtendedBy category = new ExtendedBy("document category field", By.xpath("//div[@id='mui-component-select-category']"));
    private static final ExtendedBy categoryOption = new ExtendedBy("document category option", By.xpath("//li[contains(text(),'Driver')]"));
    private static final ExtendedBy documentFace = new ExtendedBy("document face field", By.xpath("//div[@id='mui-component-select-documentFace']"));
    private static final ExtendedBy documentFaceOption = new ExtendedBy("document face option", By.xpath("//li[@data-value='front']"));
    private static final ExtendedBy replaceButton = new ExtendedBy("replace button", By.xpath("//button[contains(text(),'Replace')]"));
    private static final ExtendedBy documentTitle = new ExtendedBy("document title field", By.xpath("//input[@name='documentTitle']"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));
    private static final ExtendedBy tokenNameField = new ExtendedBy("document token field", By.xpath("//div[@id='mui-component-select-token']"));
    private static final String tokenOption = "//li[@role='option' and contains(text(),'%s')]";

    CpAddDocumentNewUI(Browser browser) {
        super(browser);
    }

    public void clickDocumentsSection(){
        browser.click(documentsSection);
    }

    public CpAddDocumentNewUI uploadImage(String path) {
        browser.waitForPageStable();
        browser.isElementPresentOrVisible(image);
        WebElement element = browser.findElement(image);
        browser.setElementAttribute(element, "style", "display:block;");
        browser.typeTextElement(image, path);
        // wait for replace button to show - means the documents uploaded successfully
        try {
            browser.waitForElementVisibility(replaceButton);
        } catch (RuntimeException e) {
            info("Didn't catch the image upload progress bar showing up - could be due to automation slowness.. Trying to resume with the test");
        }
        return this;
    }

    public void addDocumentCategory() {
        browser.click(category);
        browser.click(categoryOption);
    }

    public void addDocumentFace() {
        browser.click(documentFace);
        browser.click(documentFaceOption);
    }

    public void addDocumentTitle(String documentTitleText) {
        browser.typeTextElement(documentTitle, documentTitleText);
    }

    public void addDocumentToken(String value){
        String tokenName = value;
        browser.click(tokenNameField);
        String xPathString = String.format(tokenOption, tokenName);
        ExtendedBy tokenOption = new ExtendedBy("Chosen token: " +tokenName , By.xpath(xPathString));
        browser.click(tokenOption);
    }

    public void clickOk() {
        browser.clickAndWaitForElementToVanish(okButton);
    }
}