package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TaxFormPage extends AbstractPage {

    private static final ExtendedBy titleHeader = new ExtendedBy("Header", By.xpath("//h1"));
    private static final ExtendedBy completeTaxFormBtn = new ExtendedBy("Complete TaxForm Btn", By.xpath("//button[text()='Complete a Tax Form']"));
    private static final ExtendedBy deleteTaxFormBtn = new ExtendedBy("Delete Submitted Tax Form", By.xpath("//span[text()='Delete']"));
    private static final ExtendedBy taxFormSubmittedData = new ExtendedBy("Submitted Tax FormDate", By.xpath("//td[3]"));
    private static final ExtendedBy submittedTaxFormStatus = new ExtendedBy("Submitted Tax Form Status", By.xpath("//td[2]//span"));
    private static final ExtendedBy taxFormName = new ExtendedBy("Submitted Tax FormDate", By.xpath("//td[1]"));

    public TaxFormPage(Browser browser) {
        super(browser, titleHeader);
    }

    public void deleteAllTaxForms() {
        try{
            do {
                clickDeleteTaxForm().clickDeleteConfirmBtn();
            } while (browser.findElement(deleteTaxFormBtn).isDisplayed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TaxFormResidenceDetailsPage clickCompleteATaxForm() {
        browser.waitForPageStable();
        browser.waitForElementClickable(completeTaxFormBtn, 5);
        browser.click(completeTaxFormBtn, true);
        return new TaxFormResidenceDetailsPage(browser);
    }

    public TaxFormDeleteModalPage clickDeleteTaxForm() {
        browser.click(deleteTaxFormBtn);
        return new TaxFormDeleteModalPage(browser);
    }

    public void deleteTaxForm() {
        TaxFormDeleteModalPage taxFormDeleteModalPage = clickDeleteTaxForm();
        taxFormDeleteModalPage.clickDeleteConfirmBtn();
    }

    public boolean isCompleteATaxFormBtnVisible(){
        return browser.findElement(completeTaxFormBtn).isDisplayed();
    }

    public boolean isCompleteATaxFormBtnEnabled() {
        return browser.findElement(completeTaxFormBtn).isEnabled();
    }

    public String getTaxFormSubmittedDate() {
        return browser.findElement(taxFormSubmittedData).getText();
    }

    public String getSubmittedTaxFormStatus() {
        return browser.findElement(submittedTaxFormStatus).getText();
    }

    public String getSubmittedTaxFormName() {
        browser.waitForElementVisibility(taxFormName);
        return browser.findElement(taxFormName).getText();
    }

}