package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

/**
 * note to self
 * click "create template" button. while processing, "loading" appears
 * when done, the button name changes to "download template" button
 * when "upload results" button is clicked, dialog with file-upload (browse) and "register button" appears.
 */
public class CpPlusLotteriesPageEdit extends AbstractJpPage {

    private static final ExtendedBy createTemplateButton = new ExtendedBy("Create Template Button", By.xpath("//button/*[text()[contains(.,'テンプレート作成')]]"));
    private static final ExtendedBy loading = new ExtendedBy("Loading", By.xpath("//*[@aria-label='Loading']"));
    private static final ExtendedBy downloadTemplateButton = new ExtendedBy("Download Template Button", By.xpath("//button/*[text()[contains(.,'テンプレートダウンロード')]]"));
    private static final ExtendedBy uploadResultsButton = new ExtendedBy("Upload Results Button", By.xpath("//button/*[text()[contains(.,'結果アップロード')]]"));
    private static final ExtendedBy fileUpload = new ExtendedBy("File Upload", By.xpath("//*[@name='fileUpload']"));
    private static final ExtendedBy browseButton = new ExtendedBy("Browse Button", By.xpath("//*[text()[contains(.,'ブラウズ')]]"));
    private static final ExtendedBy registerButton = new ExtendedBy("Register Button", By.xpath("//button/*[text()[contains(.,'登録')]]"));
    private static final ExtendedBy returnButton = new ExtendedBy("Return Button", By.xpath("//*[text()[contains(.,'戻る')]]"));

    public CpPlusLotteriesPageEdit(Browser browser) {
        super(browser, uploadResultsButton);
    }

    public CpPlusLotteriesPageEdit clickCreateTemplateButton() {
        browser.click(createTemplateButton);
        return this;
    }

    public CpPlusLotteriesPageEdit waitUntilLoadingDisappears() {
        browser.waitForElementToVanish(loading);
        return this;
    }

    public CpPlusLotteriesPageEdit clickDownloadTemplateButton() {
        browser.click(downloadTemplateButton);
        return this;
    }

    public CpPlusLotteriesPageEdit clickBrowseButton() {
        browser.click(browseButton);
        return this;
    }

    public CpPlusLotteriesPageEdit enterFilePathToUpload(String filePath) {
        browser.waitForElementClickable(browseButton);
        browser.findElement(fileUpload).sendKeys(filePath);
        return this;
    }

    public CpPlusLotteriesPageEdit clickUploadResultsButton() {
        browser.click(uploadResultsButton);
        return this;
    }

    public CpPlusLotteriesPageEdit clickRegisterButton() {
        browser.waitForElementToVanish(loading);
        browser.waitForElementVisibility(browseButton);
        browser.clickAndWaitForElementToVanish(registerButton);
        return this;
    }

    public void clickReturnButton() {
        browser.click(returnButton);
    }
}
