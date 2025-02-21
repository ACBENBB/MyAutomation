package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.TaxCenterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class SecuritizeIdProfile extends AbstractPage<SecuritizeIdProfile> {

    //Profile - Personal tabs
    private static final ExtendedBy generalTitle = new ExtendedBy("Profile - General title", By.xpath("//h1[text()='Account']"));
    private static final ExtendedBy personalInformationCard = new ExtendedBy("Profile - Personal Information Card", By.id("personal-info-card"));
    private static final ExtendedBy additionalInformationCard = new ExtendedBy("Profile - Additional Information Card", By.id("tax-card"));
    private static final ExtendedBy taxCenterCard = new ExtendedBy("Profile - Tax Center Card", By.xpath("//h6[contains(text(), 'Tax Center')]/../.."));
    private static final ExtendedBy entityDocumentsCard = new ExtendedBy("Profile - Entity documents and statements Card", By.id("documents-and-statements-view-card"));
    private static final ExtendedBy accreditationQualificationCard = new ExtendedBy("Profile - Accreditation Qualification Card", By.xpath("//a[@id='accreditation-view-card-entity' or @id='accreditation-view-card-individual']"));
    private static final ExtendedBy goBackToAccountLink = new ExtendedBy("Profile - Go back to account", By.xpath("//*[contains(text(),'Go back') or text()='Exit this process'] | //div[contains(@class, 'back-btn')]//i"));
    //Profile - Personal information tab
    private static final ExtendedBy personalInfoFirstName = new ExtendedBy("Personal Info - First name", By.xpath("//label[text()='First name (From Legal Document)' or text()='First name (from legal document)']/../input"));
    private static final ExtendedBy personalInfoMiddleName = new ExtendedBy("Personal Info - Middle name", By.xpath("//label[text() ='Middle name (from legal document)' or text()='Middle name (From Legal Document)']/../input"));
    private static final ExtendedBy personalInfoLastName = new ExtendedBy("Personal Info - Last name", By.xpath("//label[text()='Last name (Surname - From Legal Document)' or text()='Last name (from legal document)']/../input"));
    private static final ExtendedBy personalInfoBirth_Month = new ExtendedBy("Personal Info - Birth Month", By.xpath("//label[text()='Date of birth']/../div/input[1]"));
    private static final ExtendedBy personalInfoBirth_Day = new ExtendedBy("Personal Info - Birth Day", By.xpath("//label[text()='Date of birth']/../div/input[2]"));
    private static final ExtendedBy personalInfoBirth_Year = new ExtendedBy("Personal Info - Birth Year", By.xpath("//label[text()='Date of birth']/../div/input[3]"));
    //Profile - Address tab
    private static final ExtendedBy addressStreet = new ExtendedBy("Address - Street", By.xpath("//label[text()='Street address']/../input"));
    private static final ExtendedBy addressAdditionalInfo = new ExtendedBy("Address - Additional Info", By.xpath("//label[text()='Additional info']/../input"));
    private static final ExtendedBy addressPostalCode = new ExtendedBy("Address - Postal Code", By.xpath("//label[text()='Postal code/ZIP code']/../input"));
    private static final ExtendedBy addressCity = new ExtendedBy("Address - City", By.xpath("//label[text()='City']/../input"));
    private static final ExtendedBy addressCountry = new ExtendedBy("Address - Country", By.xpath("//label[text()='Country']/../input"));
    private static final ExtendedBy addressState = new ExtendedBy("Address - State", By.xpath("//label[text()='State']/../input"));
    //Profile - Additional information tab
    private static final ExtendedBy additionalInfoCountryOfTax = new ExtendedBy("Additional Info - Country of tax", By.xpath("//label[text()='Country of tax jurisdiction']/../input"));
    private static final ExtendedBy additionalInfoTaxID = new ExtendedBy("Additional Info - Tax ID", By.xpath("//label[text()='Tax ID']/../input"));

    //Profile - Entity tabs
    private static final ExtendedBy entityInformationCard = new ExtendedBy("Profile - Entity Information Card", By.id("entity-card"));
    private static final ExtendedBy entityLegalSignerCard = new ExtendedBy("Profile - Entity Legal Signer Card", By.xpath("//a[contains(@id,'signer-card')]"));
    //Profile - Entity information tab
    private static final ExtendedBy entityLegalName = new ExtendedBy("Entity Info - Legal Name", By.xpath("//label[text()='Entity legal name']/../input"));
    private static final ExtendedBy entityDoingBusinessAs = new ExtendedBy("Entity Info - Doing Business As", By.xpath("//label[contains(text(), 'DBA')]/../input"));
    private static final ExtendedBy entityType = new ExtendedBy("Entity Info - Type", By.xpath("//label[text()='Entity type']/../input"));
    private static final ExtendedBy entityTaxID = new ExtendedBy("Entity Info - Tax ID", By.xpath("//label[text()='Tax ID']/../input"));
    //Profile - Entity Legal Signer tab
    private static final ExtendedBy entityLegalSignerPersonalInformation = new ExtendedBy("Profile - Entity Legal Signer Personal information", By.id("selection-account-card"));
    private static final ExtendedBy entityLegalSignerLegalDocuments = new ExtendedBy("Profile - Entity Legal Signer Legal documents", By.id("selection-entity-card"));
    private static final ExtendedBy entityDocumentsUploadedImage = new ExtendedBy("Profile - Entity documents - uploaded images", By.xpath("//div[contains(@class, 'uploaded-files-item')]//img"));
    private static final ExtendedBy entityStatementsDownloadIcon = new ExtendedBy("Profile - Entity documents & statements - download icon", By.xpath("//div[contains(@class, 'icon-download-wrapper')]"));



    public SecuritizeIdProfile(Browser browser) {
        super(browser, generalTitle);
    }

    public void clickPersonalInformationCard() {
        browser.click(personalInformationCard, false);
    }

    public void clickAdditionalInformationCard() {
        browser.click(additionalInformationCard, false);
    }

    public TaxCenterPage clickTaxCenterCard() {
        browser.click(taxCenterCard, false);
        return new TaxCenterPage(browser);
    }

    public void clickEntityDocumentsCard() {
        browser.click(entityDocumentsCard, false);
    }

    //page:  /profile/entity-documents
    public long getEntityDocumentsVisibleImagesCount() {
        browser.waitForElementVisibility(entityDocumentsUploadedImage);
        List<WebElement> elements = browser.findElements(entityDocumentsUploadedImage);
        return elements.stream().filter(WebElement::isDisplayed).count();
    }

    //page:  /profile/documents
    public long getEntityStatementsVisibleImagesCount() {
        List<WebElement> elements = browser.findVisibleElements(entityStatementsDownloadIcon);
        return elements.stream().filter(WebElement::isDisplayed).count();
    }

    public void clickAccreditationQualificationCard() {
        browser.click(accreditationQualificationCard, false);
    }

    public void clickEntityLegalSignerCard() {
        browser.click(entityLegalSignerCard, false);
    }

    public void clickGoBackToAccountLink() {
        var backButton = browser.findFirstVisibleElementQuick(goBackToAccountLink, 5);
        if (backButton.isPresent()) {
            browser.click(backButton.get(), goBackToAccountLink.getDescription());
        } else {
            errorAndStop("Can't find 'go back' button", true);
        }
    }

    public boolean isPersonalInfoFirstNameVisible() {
        browser.waitForElementVisibility(personalInfoFirstName);
        return browser.isElementVisible(personalInfoFirstName);
    }
    public String getPersonalInfoFirstName() {
        return browser.getElementText(personalInfoFirstName);
    }

    public String getPersonalInfoMiddleName() {
        return browser.getElementText(personalInfoMiddleName);
    }

    public String getPersonalInfoLastName() {
        return browser.getElementText(personalInfoLastName);
    }

    public String getCompleteBirthDate() {
        String month = browser.getElementText(personalInfoBirth_Month);
        String day = browser.getElementText(personalInfoBirth_Day);
        String year = browser.getElementText(personalInfoBirth_Year);
        return month+"/"+day+"/"+year;
    }

    public boolean isAddressStreetVisible() {
        browser.waitForElementVisibility(addressStreet);
        return browser.isElementVisible(addressStreet);
    }

    public String getAddressStreet() {
        return browser.getElementText(addressStreet);
    }

    public String getAddressAdditionalInfo() {
        return browser.getElementText(addressAdditionalInfo);
    }

    public String getAddressPostalCode() {
        return browser.getElementText(addressPostalCode);
    }

    public String getAddressCity() {
        return browser.getElementText(addressCity);
    }

    public String getAddressCountry() {
        return browser.getElementText(addressCountry);
    }

    public String getAddressState() {
        return browser.getElementText(addressState);
    }

    public String getCountryOfTax() {
        return browser.getElementText(additionalInfoCountryOfTax);
    }

    public String getAdditionalInfoTaxID() {
        return browser.getElementText(additionalInfoTaxID);
    }

    public void clickEntityInformationCard() {
        browser.click(entityInformationCard, false);
    }

    public void clickEntityLegalSignerPersonalInformation() {
        browser.click(entityLegalSignerPersonalInformation, false);
    }

    public void clickEntityLegalSignerLegalDocuments() {
        browser.click(entityLegalSignerLegalDocuments, false);
    }

    public boolean isEntityLegalNameVisible() {
        browser.waitForElementVisibility(entityLegalName);
        return browser.isElementVisible(entityLegalName);
    }

    public String getEntityLegalName() {
        return browser.getElementText(entityLegalName);
    }

    public String getEntityDoingBusinessAs() {
        return browser.getElementText(entityDoingBusinessAs);
    }

    public String getEntityType() {
        return browser.getElementText(entityType);
    }

    public String getEntityTaxID() {
        return browser.getElementText(entityTaxID);
    }

}