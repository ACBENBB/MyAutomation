package io.securitize.pageObjects.jp.controlPanel;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class JpCpInvestorsDetails extends AbstractJpPage {

    private static final ExtendedBy returnToListButton = new ExtendedBy("Return to list button", By.xpath("//button[text()[contains(., 'リストに戻る')]]"));

    // Link
    private static final ExtendedBy generalInformationLink = new ExtendedBy("General Information Link", By.xpath("//a[@href='#general']"));
    private static final ExtendedBy kycAndAccreditationLink = new ExtendedBy("KYC And Accreditation Link", By.xpath("//a[@href='#detailsKyc']"));
    private static final ExtendedBy investmentLink = new ExtendedBy("Investment Link", By.xpath("//a[@href='#investorDetailsInvestment']"));
    private static final ExtendedBy tokenAndWalletsLink = new ExtendedBy("Token And Wallets Link", By.xpath("//a[@href='#tokensWallets']"));
    private static final ExtendedBy documentsLink = new ExtendedBy("Documents Link", By.xpath("//a[@href='#documents']"));

    // Investment Section
    private static final ExtendedBy tokenNameSelect = new ExtendedBy("Token Name Select", By.xpath("//*[@class='row align-items-center' and contains(.,' トークン ')]/div/div/select"));
    private static final ExtendedBy roundNameSelect = new ExtendedBy("Round Name Select", By.xpath("//select[contains(@class,'custom-select') and contains(.,'Default name')]"));
    private static final ExtendedBy statusText = new ExtendedBy("Status Text", By.xpath("//*[@class='small' and contains(.,'ステータス')]/following-sibling::span"));
    private static final ExtendedBy investmentStatusText = new ExtendedBy("Investment Status Text", By.xpath("//*[contains(@class,'status-select')]"));
    private static final ExtendedBy pledgedAmountText = new ExtendedBy("Pledged Amount Text", By.xpath("//*[@class='pledgedBlock']/strong"));
    private static final ExtendedBy pledgedDateText = new ExtendedBy("Pledged Date Text", By.xpath("//*[@class='col-2' and contains(.,'申込日')]/h5/strong"));
    private static final ExtendedBy pledgedInitiatorText = new ExtendedBy("Pledged Initiator Text", By.xpath("//*[@class='col-2' and contains(.,'申込登録方法')]/h5"));
    private static final ExtendedBy totalFundedText = new ExtendedBy("Total Funded Text", By.xpath("//*[@class='col-1' and contains(.,'払込額')]/h5/strong"));
    private static final ExtendedBy subscriptionStatusText = new ExtendedBy("Subscription Status Text", By.xpath("//*[@class='col-2' and contains(.,'契約ステータス:')]/h5"));
    private static final ExtendedBy signatureDateText = new ExtendedBy("Signature date Text", By.xpath("//*[@class='col-2' and contains(.,'契約署名日:')]/h5"));

    // General Information
    private static final ExtendedBy investorTypeField = new ExtendedBy("investor type field", By.xpath("//span[text()='投資家タイプ']//following-sibling::strong"));
    private static final ExtendedBy firstNameField = new ExtendedBy("first name field (for individual)", By.xpath("//td[text()[contains(.,' ファーストネーム（名）')]]//parent::tr//strong"));
    private static final ExtendedBy middleNameField = new ExtendedBy("middle name field (for individual)", By.xpath("//td[text()='ミドルネーム']//parent::tr//strong"));
    private static final ExtendedBy lastNameField = new ExtendedBy("last name field (for individual)", By.xpath("//td[text()[contains(.,' ラストネーム（氏）')]]//parent::tr//strong"));
    private static final ExtendedBy emailField = new ExtendedBy("email field", By.xpath("//form//td[contains(text(), ' Eメール ')]/../td[2]"));
    private static final ExtendedBy phonePrefixNumberField = new ExtendedBy("phone number field", By.xpath("//form//td[contains(text(), 'Phone Prefix:')]/../td[2]"));
    private static final ExtendedBy phoneNumberField = new ExtendedBy("phone number field", By.xpath("//form//td[contains(text(), '電話')]/../td[2]"));
    private static final ExtendedBy birthdayField = new ExtendedBy("birthday field", By.xpath("//form//td[contains(text(), ' 生年月日 ')]/../td[2]"));
    private static final ExtendedBy genderField = new ExtendedBy("gender field", By.xpath("//form//td[contains(text(), ' 性別 ')]/../td[2]"));
    private static final ExtendedBy custodianField = new ExtendedBy("custodian field", By.xpath("//form//td[contains(text(), 'カストディアン')]/../td[2]"));
    private static final ExtendedBy addressField = new ExtendedBy("address field", By.xpath("//td[text()='市区町村・町域・番地']//parent::tr//strong"));
    private static final ExtendedBy addressAdditionalInfoField = new ExtendedBy("address additional info field", By.xpath("//form//td[contains(text(), '建物名など')]/../td[2]"));
    private static final ExtendedBy postalCodeField = new ExtendedBy("postal code field", By.xpath("//td[text()='郵便番号']//parent::tr//strong"));
    private static final ExtendedBy cityField = new ExtendedBy("city field", By.xpath("//td[text()='都道府県']//parent::tr//strong"));

    private static final ExtendedBy countryField = new ExtendedBy("country field", By.xpath("//td[text()=' 居住国 ']//parent::tr//strong"));
    private static final ExtendedBy stateField = new ExtendedBy("state field", By.xpath("//td[text()=' 州 ']//parent::tr//strong"));
    private static final ExtendedBy taxIdField = new ExtendedBy("taxId field", By.xpath("//form//td[contains(text(), '納税者番号・ID')]/../td[2]"));
    private static final ExtendedBy taxCountryField = new ExtendedBy("tax country field", By.xpath("//form//td[contains(text(), '納税国 (FATCA)')]/../td[2]"));
    private static final ExtendedBy taxId2Field = new ExtendedBy("taxId field 2", By.xpath("//form//td[contains(text(), '納税者番号・ID 2')]/../td[2]"));
    private static final ExtendedBy taxCountry2Field = new ExtendedBy("tax country field 2", By.xpath("//form//td[contains(text(), '納税国 2 (FATCA)')]/../td[2]"));
    private static final ExtendedBy taxId3Field = new ExtendedBy("taxId field 3", By.xpath("//form//td[contains(text(), '納税者番号・ID 3')]/../td[2]"));
    private static final ExtendedBy taxCountry3Field = new ExtendedBy("tax country field 3", By.xpath("//form//td[contains(text(), '納税国 3 (FATCA)')]/../td[2]"));
    private static final ExtendedBy countryOfBirthField = new ExtendedBy("country of birth field", By.xpath("//td[text()='出生地 - 国']//parent::tr//strong"));
    private static final ExtendedBy cityOfBirthField = new ExtendedBy("city of birth field", By.xpath("//td[text()='出生地 - 都道府県']//parent::tr//strong"));
    private static final ExtendedBy identityDocumentNumberField = new ExtendedBy("identity document number field", By.xpath("//td[text()='身分証明書番号']//parent::tr//strong"));
    private static final ExtendedBy creationDateField = new ExtendedBy("creation date field", By.xpath("//form//td[contains(text(), '作成日')]/../td[2]"));

    public JpCpInvestorsDetails(Browser browser) {
        super(browser, generalInformationLink);
    }

    // return to Onboarding, Fundraise, or Holders page
    public void clickReturnToListButton() {
        browser.click(returnToListButton);
        browser.waitForPageStable();
    }

    public JpCpInvestorsDetails clickGeneralInformationLink() {
        browser.click(generalInformationLink);
        return this;
    }

    public JpCpInvestorsDetails clickKycAndAccreditationLink() {
        browser.click(kycAndAccreditationLink);
        return this;
    }

    public JpCpInvestorsDetails clickInvestmentLink() {
        browser.click(investmentLink);
        return this;
    }

    public JpCpInvestorsDetails clickTokenAndWalletsLink() {
        browser.click(tokenAndWalletsLink);
        return this;
    }

    public JpCpInvestorsDetails clickDocumentsLink() {
        browser.click(documentsLink);
        return this;
    }

    public String getTokenNameSelectText() {
        Select select = new Select(browser.findElement(tokenNameSelect));
        WebElement selectedOption = select.getFirstSelectedOption();
        return selectedOption.getText();
    }

    public String getRoundNameSelect() {
        Select select = new Select(browser.findElement(roundNameSelect));
        WebElement selectedOption = select.getFirstSelectedOption();
        return selectedOption.getText();
    }

    public String getStatusText() {
        return browser.getElementText(statusText);
    }

    public String getInvestmentStatusText() {
        return browser.getElementText(investmentStatusText);
    }

    public String getPledgedAmountText() {
        return browser.getElementText(pledgedAmountText);
    }

    public String getPledgedDateText() {
        return browser.getElementText(pledgedDateText);
    }

    public String getPledgedInitiatorText() {
        return browser.getElementText(pledgedInitiatorText);
    }

    public String getTotalFundedText() {
        return browser.getElementText(totalFundedText);
    }

    public String getSubscriptionStatusText() {
        return browser.getElementText(subscriptionStatusText);
    }

    public String getSignatureDateText() {
        return browser.getElementText(signatureDateText);
    }

    public String getInvestorTypeField() {
        return browser.getElementText(investorTypeField);
    }

    public String getFirstNameField() {
        return browser.getElementText(firstNameField);
    }

    public String getMiddleNameField() {
        return browser.getElementText(middleNameField);
    }

    public String getLastNameField() {
        return browser.getElementText(lastNameField);
    }

    public String getEmailField() {
        return browser.getElementText(emailField);
    }

    public String getPhonePrefixNumberField() {
        return browser.getElementText(phonePrefixNumberField);
    }

    public String getPhoneNumberField() {
        return browser.getElementText(phoneNumberField);
    }

    public String getBirthdayField() {
        return browser.getElementText(birthdayField);
    }

    public String getGenderField() {
        return browser.getElementText(genderField);
    }

    public String getCustodianField() {
        return browser.getElementText(custodianField);
    }

    public String getAddressField() {
        return browser.getElementText(addressField);
    }

    public String getAddressAdditionalInfoField() {
        return browser.getElementText(addressAdditionalInfoField);
    }

    public String getPostalCodeField() {
        return browser.getElementText(postalCodeField);
    }

    public String getCityField() {
        return browser.getElementText(cityField);
    }

    public String getCountryField() {
        return browser.getElementText(countryField);
    }

    public String getStateField() {
        return browser.getElementText(stateField);
    }

    public String getTaxIdField() {
        return browser.getElementText(taxIdField);
    }

    public String getTaxCountryField() {
        return browser.getElementText(taxCountryField);
    }

    public String getTaxId2Field() {
        return browser.getElementText(taxId2Field);
    }

    public String getTaxCountry2Field() {
        return browser.getElementText(taxCountry2Field);
    }

    public String getTaxId3Field() {
        return browser.getElementText(taxId3Field);
    }

    public String getTaxCountry3Field() {
        return browser.getElementText(taxCountry3Field);
    }

    public String getCountryOfBirthField() {
        return browser.getElementText(countryOfBirthField);
    }

    public String getCityOfBirthField() {
        return browser.getElementText(cityOfBirthField);
    }

    public String getIdentityDocumentNumberField() {
        return browser.getElementText(identityDocumentNumberField);
    }

    public String getCreationDateField() {
        return browser.getElementText(creationDateField);
    }

}
