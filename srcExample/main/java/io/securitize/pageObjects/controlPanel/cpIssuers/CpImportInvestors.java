package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.nio.file.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.infra.reporting.MultiReporter.warning;

public class CpImportInvestors extends AbstractPage {

    private static final ExtendedBy importInvestorField = new ExtendedBy("import investor field", By.id("id"));
    private static final ExtendedBy image = new ExtendedBy("image", By.xpath("(//input[@type='file'])"));
    private static final ExtendedBy uploadProgressBar = new ExtendedBy("upload progress bar", By.xpath("//div[@class='dz-progress']"));
    private static final ExtendedBy importInvestorsButton = new ExtendedBy("import investors button", By.xpath("//button[text()='Import Investors']"));
    private static final ExtendedBy closeButton = new ExtendedBy("close button", By.xpath("//button[text()='Close']"));
    private static final ExtendedBy csvErrorPopup = new ExtendedBy("csv popup after import", By.xpath("//div[@class='modal-content']"));
    private static final ExtendedBy csvErrorMessage = new ExtendedBy("csv message after import", By.xpath("//div[@class='modal-body']//div"));
    private static final ExtendedBy okErrorMessageButton = new ExtendedBy("csv OK button after import", By.xpath("//button[text()='OK']"));

    CpImportInvestors(Browser browser) {
        super(browser, importInvestorField);
    }

    public CpImportInvestors uploadFile(String path) {
        try {
            String content = Files.readString(Paths.get(path));
            info("Uploading this CSV file: " + content);
        } catch (IOException e) {
            warning("An error occur trying to read CSV file so we can log its content: " + ExceptionUtils.getStackTrace(e), false);
        }

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

    public void checkErrorMessage(){
        String csvErrorMessageText = "No error message";
        //Check if there is an error message after upload
        List<WebElement> el = browser.findElements(csvErrorPopup);
        if (el.size() > 0) {
            csvErrorMessageText = browser.findElement(csvErrorMessage).getText();
            browser.click(okErrorMessageButton);
        }
        info("Error after upload CSV: " + csvErrorMessageText);
    }

    public void clickImportInvestorButton() {
        browser.clickAndWaitForElementToVanish(importInvestorsButton);
        checkErrorMessage();
    }

    public void clickImportAndCloseButton () {
        browser.click(importInvestorsButton);
        browser.click(closeButton);
    }


}
