package io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UploadAccreditationDocumentsPage extends AbstractPage {

    // INDIVIDUAL INVESTOR LOCATORS
    private static final ExtendedBy individualFileUploadOne = new ExtendedBy("Upload File Browse Button 1", By.id("individualFileUploadOne"));
    private static final ExtendedBy individualFileUploadTwo = new ExtendedBy("Upload File Browse Button 2", By.id("individualFileUploadTwo"));
    private static final ExtendedBy individualProofOfAssetsUpload = new ExtendedBy("Individual Proof Of Asset Upload", By.id("netWorthFileUploadOne"));
    private static final ExtendedBy individualCreditReportUpload = new ExtendedBy("Individual Credit Report Upload", By.id("netWorthFileUploadTwo"));

    private static final ExtendedBy fileUploadedElementOne = new ExtendedBy("Uploaded Document File 1", By.xpath("//span[text()='driverLicense-front.jpg']"));

    // ENTITY INVESTOR LOCATORS
    private static final ExtendedBy entityFileUploadOne = new ExtendedBy("Upload Proof of Assets", By.id("assetsAndInvestmentsUpload"));
    private static final ExtendedBy entityEquityOwnersFileUpload = new ExtendedBy("Equity Owner Documents", By.id("equityOwnersFileUploadTwo"));

    // ABSTRACT LOCATORS
    private static final ExtendedBy continueButton = new ExtendedBy("Upload Documents Continue Button", By.id("submitForm"));
    private static final ExtendedBy provideSpouceTaxYes = new ExtendedBy("Provide Spouce Tax Yes", By.id("radioUploadInvestorFile-true"));
    private static final ExtendedBy provideSpouceTaxNo = new ExtendedBy("Provide Spouce Tax Yes", By.id("radioUploadInvestorFile-false"));
    private static final ExtendedBy chooseRepresentativeDropdown = new ExtendedBy("Choose Representative Dropdown", By.xpath("//select[@id='letterUploadRepresentative']"));
    private static final ExtendedBy licenseOrRegistrationNumberInput = new ExtendedBy("License Or Registration Number Input Field", By.id("registrationNumberTextField"));
    private static final ExtendedBy letterUploadInput = new ExtendedBy("Equity Owner Documents", By.xpath("//input[contains(@name, 'FileUploadOne')]"));

    public UploadAccreditationDocumentsPage(Browser browser) {
        super(browser, continueButton);
    }

    public UploadAccreditationDocumentsPage chooseRepresentativeType(String representativeType) {
        browser.selectElementByVisibleText(chooseRepresentativeDropdown, representativeType);
        return this;
    }

    public UploadAccreditationDocumentsPage setLicenseOrRegistrationNumber(String licenseOrRegistrationNumber) {
        browser.typeTextElement(licenseOrRegistrationNumberInput, licenseOrRegistrationNumber);
        return this;
    }

    public UploadAccreditationDocumentsPage uploadVerificationLetter(String documentPath) {
        upLoadDocument(letterUploadInput, documentPath);
        return this;
    }

    // INDIVIDUAL INVESTOR - Proof Of Income - Upload Accreditation Documents
    public UploadAccreditationDocumentsPage upLoadTaxYearDocumentsOne(String documentPath) {
        upLoadDocument(individualFileUploadOne, documentPath);
        return this;
    }

    public UploadAccreditationDocumentsPage upLoadTaxYearDocumentsTwo(String documentPath) {
        upLoadDocument(individualFileUploadTwo, documentPath);
        return this;
    }

    // INDIVIDUAL INVESTOR - NetWorth - Upload Proof Of Assets / Credit Report
    public UploadAccreditationDocumentsPage individualUploadProofOfAssets(String documentPath) {
        upLoadDocument(individualProofOfAssetsUpload, documentPath);
        return this;
    }

    public UploadAccreditationDocumentsPage individualUploadCreditReport(String documentPath) {
        upLoadDocument(individualCreditReportUpload, documentPath);
        return this;
    }

    public AccreditationReviewAndSubmitPage clickContinue() {
        browser.click(continueButton);
        return new AccreditationReviewAndSubmitPage(browser);
    }

    // ENTITY INVESTOR - Upload Accreditation Documents
    public UploadAccreditationDocumentsPage uploadProofOfAssets(String documentPath) {
        upLoadDocument(entityFileUploadOne, documentPath);
        return this;
    }

    public UploadAccreditationDocumentsPage uploadEquityOwnerDocuments(String documentPath) {
        upLoadDocument(entityEquityOwnersFileUpload, documentPath);
        return this;
    }

    // GENERIC UPLOAD DOCUMENTS
    public UploadAccreditationDocumentsPage upLoadDocument(ExtendedBy elementBrowse, String path) {
        WebElement element = browser.findElement(elementBrowse);
        browser.setElementAttribute(element, "style", "display:block;");
        browser.typeTextElement(elementBrowse, path);
        return this;
    }

    public void waitForFirstUploadIsCompleted() {
        browser.waitForElementStalenes(browser.findElement(individualFileUploadTwo));
        browser.waitForPresenceOfElementLocated(individualFileUploadTwo.getBy());
    }

    public void waitForNetWorthUploadCompleted() {
        waitForUploadIsComplete(individualProofOfAssetsUpload);
    }

    public void waitForUploadIsComplete(ExtendedBy uploadElement) {
        browser.waitForElementStalenes(browser.findElement(uploadElement));
        browser.waitForPresenceOfElementLocated(uploadElement.getBy());
    }

    public UploadAccreditationDocumentsPage setProvideSpouceTaxYes(){
        browser.click(provideSpouceTaxYes);
        return this;
    }

    public UploadAccreditationDocumentsPage setProvideSpouceTaxNo(){
        browser.click(provideSpouceTaxNo);
        return this;
    }

}
