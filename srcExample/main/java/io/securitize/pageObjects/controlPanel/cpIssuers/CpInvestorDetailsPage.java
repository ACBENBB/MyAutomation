package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExecuteBy;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.investorDetailsCards.*;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public class CpInvestorDetailsPage extends AbstractPage {

    // SEC ID LOCATORS
    private static final ExtendedBy generalInformationFieldSecId = new ExtendedBy("general information card field", By.xpath("//*[text()=' General information ']"));
    private static final ExtendedBy investorTypeFieldSecId = new ExtendedBy("investor type field", By.xpath("//span[text()='Investor Type: ']//following-sibling::strong"));
    private static final ExtendedBy firstNameFieldSecId = new ExtendedBy("first name field (for individual)", By.xpath("//td[text()='First Name:']//parent::tr//strong"));
    private static final ExtendedBy middleNameFieldSecId = new ExtendedBy("middle name field (for individual)", By.xpath("//td[text()='Middle Name:']//parent::tr//strong"));
    private static final ExtendedBy lastNameFieldSecId = new ExtendedBy("last name field (for individual)", By.xpath("//td[text()='Last Name:']//parent::tr//strong"));
    private static final ExtendedBy authorizedByFieldSecId = new ExtendedBy("authorized by field", By.xpath("//*[text()='Authorized By:']//../td[2]"));
    private static final ExtendedBy legalSignersFieldSecId = new ExtendedBy("legal signers (Key Parties) card field (for entity)", By.xpath("//*[text()=' Key Parties ']"));
    private static final ExtendedBy legalSignersEntriesFieldSecId = new ExtendedBy("legal signers (Key Parties) entries field (for entity)", By.xpath("//*[text() = 'Type']/../../..//tbody/tr"));
    private static final ExtendedBy legalSignersTypeFieldSecId = new ExtendedBy("legal signers (Key Parties) type field (for entity)", By.xpath("//*[text() = 'Type']/../../..//tbody/tr/td[@aria-colindex='1']"));
    private static final ExtendedBy authorizedAccountsFieldSecId = new ExtendedBy("authorized accounts card field", By.xpath("//*[@class=\"card-header\"]//*[text()=' Authorized Accounts ']"));
    private static final ExtendedBy authorizedAccountsEntriesFieldSecId = new ExtendedBy("authorized accounts entries field", By.xpath("//*[text()=' Authorized Accounts ']/../../../../..//table/tbody/tr"));
    private static final ExtendedBy authorizedAccountsEmptyEntryFieldSecId = new ExtendedBy("authorized accounts empty entry field", By.xpath("//*[text()=' No authorized account found ']"));
    private static final ExtendedBy kybFieldSecId = new ExtendedBy("KYB card field (for entity)", By.xpath("//*[text()=' KYB ']"));
    private static final ExtendedBy currentKybStatusFieldSecId = new ExtendedBy("current KYB status field (for entity)", By.xpath("//*[text()=' Current KYB status ']/..//span"));
    private static final ExtendedBy kybStatusLogEntriesFieldSecId = new ExtendedBy("KYB status log entries field (for entity)", By.xpath("//*[text()=' KYB Status Log ']/../div/div/div/div/table/./tbody/tr"));
    private static final ExtendedBy documentsFieldSecId = new ExtendedBy("documents card field", By.xpath("//*[text()=' Documents ']"));
    private static final ExtendedBy documentAddDocumentsFieldSecId = new ExtendedBy("add documents button - documents card)", By.xpath("(//*[text()=' Add document '])[1]"));
    private static final ExtendedBy documentImageFieldSecId = new ExtendedBy("image field - documents card", By.xpath("(//*[@alt='Document preview']/../../..)[1]/tr/td/img"));
    private static final ExtendedBy confidentialDocumentsFieldSecId = new ExtendedBy("confidential documents field", By.xpath("//*[text()=' Confidential Documents ']"));
    private static final ExtendedBy confidentialDocumentsImageFieldSecId = new ExtendedBy("image field - confidential documents card", By.xpath("(//*[@alt='Document preview']/../../..)[2]/tr/td/img"));
    private static final ExtendedBy confidentialDocumentAddDocumentsFieldSecId = new ExtendedBy("add documents button - confidential documents card", By.xpath("(//*[text()=' Add document '])[2]"));
    private static final ExtendedBy walletsFieldSecId = new ExtendedBy("wallets card field", By.xpath("//*[@class=\"card-header\"]//*[text()=' Wallets ']"));
    private static final ExtendedBy walletsEntriesFieldSecId = new ExtendedBy("wallets entries field", By.xpath("//*[@class=\"card-header\"]//*[text()=' Wallets ']/../../../..//table/tbody/tr"));

    // ISSUER LOCATORS
    private static final ExtendedBy firstNameField = new ExtendedBy("first name field", By.xpath("//td[text()=' First name: ']//parent::tr//strong"));
    private static final ExtendedBy firstNameEditableField = new ExtendedBy("first name editable field", By.name("firstName"));
    private static final ExtendedBy middleNameField = new ExtendedBy("middle name field", By.xpath("//td[text()='Middle name:']//parent::tr//strong"));
    private static final ExtendedBy middleNameEditableField = new ExtendedBy("middle name editable field", By.name("middleName"));
    private static final ExtendedBy lastNameField = new ExtendedBy("last name field", By.xpath("//td[text()=' Last name: ']//parent::tr//strong"));
    private static final ExtendedBy lastNameEditableField = new ExtendedBy("last name editable field", By.name("lastName"));
    private static final ExtendedBy emailField = new ExtendedBy("email field", By.xpath("//form//td[contains(text(), 'Email')]/../td[2]"));
    private static final ExtendedBy fboEmailField = new ExtendedBy("fbo email field", By.xpath("//form//td[contains(text(), 'FBO Email')]/../td[2]"));
    private static final ExtendedBy countryField = new ExtendedBy("country field", By.xpath("//td[text()=' Country: ']//parent::tr//strong"));
    private static final ExtendedBy stateField = new ExtendedBy("state field", By.xpath("//td[text()=' State: ']//parent::tr//strong"));
    private static final ExtendedBy birthdayField = new ExtendedBy("birthday field", By.xpath("//form//td[contains(text(), 'Birthday')]/../td[2]"));
    private static final ExtendedBy genderField = new ExtendedBy("gender field", By.xpath("//form//td[contains(text(), 'Gender')]/../td[2]"));
    private static final ExtendedBy custodianField = new ExtendedBy("custodian field", By.xpath("//form//td[contains(text(), 'Custodian')]/../td[2]"));
    private static final ExtendedBy investorTypeField = new ExtendedBy("investor type field", By.xpath("//span[text()='Investor Type:']//following-sibling::strong"));
    private static final ExtendedBy addressField = new ExtendedBy("address field", By.xpath("//form//td[contains(text(), 'Address')]/../td[2]"));
    private static final ExtendedBy addressAdditionalInfoField = new ExtendedBy("address additional info field", By.xpath("//form//td[contains(text(), 'Additional Info')]/../td[2]"));
    private static final ExtendedBy postalCodeField = new ExtendedBy("postal code field", By.xpath("//td[text()='Postal Code:']//parent::tr//strong"));
    private static final ExtendedBy cityField = new ExtendedBy("city field", By.xpath("//td[text()='City:']//parent::tr//strong"));
    private static final ExtendedBy taxIdField = new ExtendedBy("taxId field", By.xpath("//form//td[contains(text(), 'Tax ID')]/../td[2]"));
    private static final ExtendedBy taxCountryField = new ExtendedBy("tax country field", By.xpath("//form//td[contains(text(), 'Tax Country')]/../td[2]"));
    private static final ExtendedBy creationDateField = new ExtendedBy("creation date field", By.xpath("//form//td[contains(text(), 'Creation date')]/../td[2]"));
    private static final ExtendedBy pledgedAmountField = new ExtendedBy("pledged amount field", By.xpath("//*[contains(@class, 'form-control form-group')]"));
    private static final ExtendedBy phoneNumberField = new ExtendedBy("phone number field", By.xpath("//form//td[contains(text(), 'Phone:')]/../td[2]"));
    private static final ExtendedBy commentField = new ExtendedBy("comment field", By.xpath("//div[contains(@class, 'cp-comment-height overflow-auto')]"));
    private static final ExtendedBy entityNameField = new ExtendedBy("entity name field", By.xpath("//form//td[contains(text(), 'Entity Name')]/../td[2]"));
    private static final ExtendedBy entityNameFieldSecId = new ExtendedBy("entity name field", By.xpath("//td[text()='Name:']//parent::tr//strong"));
    private static final ExtendedBy entityDbaField = new ExtendedBy("entity dba field", By.xpath("//form//td[contains(text(), 'Entity DBA')]/../td[2]"));
    private static final ExtendedBy entityDbaFieldSecId = new ExtendedBy("entity dba field", By.xpath("//td[text()='Entity DBA:']//parent::tr//strong"));
    private static final ExtendedBy entityTypeField = new ExtendedBy("entity type field", By.xpath("//form//td[contains(text(), 'Type')]/../td[2]"));

    private static final ExtendedBy investorExternalId = new ExtendedBy("investor external id", By.xpath("//span[contains(text(), 'External ID')]"));

    private static final ExtendedBy editButton = new ExtendedBy("edit Button", By.xpath("//button[contains(text(),'Edit')]"));
    private static final ExtendedBy saveChangesButton = new ExtendedBy("save changes Button", By.xpath("//button[contains(text(), 'Save changes')]"));
    private static final ExtendedBy confirmSaveChangesButton = new ExtendedBy("ok button to confirm save changes Button", By.xpath("//button[contains(text(),'OK')]"));

    private static final ExtendedBy phoneNumberEditableField = new ExtendedBy("phone number editable field", By.name("phoneNumber"));
    private static final ExtendedBy birthDateEditableField = new ExtendedBy("birth date editable field", By.name("birthdate"));
    private static final ExtendedBy genderSelector = new ExtendedBy("gender editable selector", By.name("gender"));
    private static final ExtendedBy addressEditableField = new ExtendedBy("address editable field", By.name("address1"));
    private static final ExtendedBy addressAdditionalInfoEditableField = new ExtendedBy("address additional info editable field", By.name("additionalInfo"));
    private static final ExtendedBy postalCodeEditableField = new ExtendedBy("postal code editable field", By.name("zipCode"));
    private static final ExtendedBy cityEditableField = new ExtendedBy("city editable field", By.name("city"));
    private static final ExtendedBy entityNameEditableField = new ExtendedBy("entity name editable field", By.name("company"));
    private static final ExtendedBy FATCASelector = new ExtendedBy("FATCA editable selector", By.name("taxCountryCode"));
    private static final ExtendedBy commentEditableField = new ExtendedBy("comment editable field", By.xpath("//textarea"));

    private static final ExtendedBy addDocumentButton = new ExtendedBy("add document Button", By.xpath("//button/span[contains(text(), 'Add document')]"));
    private static final ExtendedBy addInvestmentButton = new ExtendedBy("add investment Button", By.xpath("//button/span[contains(text(), 'Add Investment')]"));
    private static final ExtendedBy addInvestmentButtonDirect = new ExtendedBy("add investment Button - direct", By.xpath("//button/span[contains(text(), 'Add Investment')]/.."));
    private static final ExtendedBy investmentTotalFundedAmount = new ExtendedBy("investment card - total funded field", By.xpath("//div[contains(text(), \"Total Funded\")]/..//h5//span"));

    private static final ExtendedBy addTransactionButton = new ExtendedBy("add transaction Button", By.xpath("//button/span[contains(text(), 'Add transaction')]"));
    private static final ExtendedBy editInvestmentButton = new ExtendedBy("Edit investment button", By.xpath(".//button/i[contains(@class, 'ion ion-md-create')]"));
    private static final ExtendedBy subscriptionAgreementSelector = new ExtendedBy("Investment subscription agreement selector", By.xpath("//select[@class='custom-select']"));
    private static final ExtendedBy setSubscriptionDate = new ExtendedBy("set subscription date", By.xpath("//*[contains(@class ,'cp-border-r-off form-control')]"));

    private static final ExtendedBy addWalletButton = new ExtendedBy("Add wallet button", By.xpath("//button/span[contains(text(), 'Add wallet')]"));

    private static final ExtendedBy kycCard = new ExtendedBy("KYC card", By.xpath("//div[@class='card mb-5']//h4[contains(text(), 'KYC')]/ancestor::div[@class='card mb-5']"));
    private static final ExtendedBy kybCard = new ExtendedBy("KYB card", By.xpath("//div[@class='card mb-5']//h4[contains(text(), 'KYB')]/ancestor::div[@class='card mb-5']"));
    private static final ExtendedBy editKycButton = new ExtendedBy("Edit KYC button", By.xpath(".//button/i[contains(@class, 'ion ion-md-create')]"));
    private static final ExtendedBy currentKycStatusSelector = new ExtendedBy("KYC status selector", By.xpath("//select[@class='custom-select']"));
    private static final ExtendedBy currentAccreditationStatusSelector = new ExtendedBy("Current accreditation status selector", By.xpath("(//select[@class='custom-select'])[2]"));
    private static final ExtendedBy currentQualificationStatusSelector = new ExtendedBy("Current accreditation status selector", By.xpath("(//select[@class='custom-select'])[3]"));
    private static final ExtendedBy kycCommentField = new ExtendedBy("KYC comment editable field", By.xpath("//textarea"));

    private static final ExtendedBy kycCurrentStatus = new ExtendedBy("KYC current status", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Current KYC status')]/..//span"));
    private static final ExtendedBy lastKYCChange = new ExtendedBy("last KYC change", By.xpath("//div[@class='card mb-5']//span[contains(text(), 'Last KYC Change')]/..//strong"));
    private static final ExtendedBy kycExpirationDate = new ExtendedBy("KYC expiration date", By.xpath("//div[@class='card mb-5']//span[contains(text(), 'KYC Expiration Date')]/..//strong"));
    private static final ExtendedBy kycProvider = new ExtendedBy("KYC provider", By.xpath("//div[@class='card mb-5']//span[contains(text(), 'KYC Provider')]/..//strong"));
    private static final ExtendedBy currentAccreditationStatus = new ExtendedBy("current accreditation status", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Current Accreditation status')]/..//span"));
    private static final ExtendedBy lastAccreditationChange = new ExtendedBy("last accreditation change", By.xpath("//div[@class='card mb-5']//span[contains(text(), 'Last Accreditation Change')]/..//strong"));
    private static final ExtendedBy qualificationStatus = new ExtendedBy("current qualification status", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Qualification Status')]/..//span"));


    private static final ExtendedBy currentPledgedAmount = new ExtendedBy("current pledged amount", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Pledged Amount')]/..//span"));
    private static final ExtendedBy currentTotalFunded = new ExtendedBy("current total funded", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Total Funded')]/..//span"));
    private static final ExtendedBy subscriptionAgreement = new ExtendedBy("subscription agreement", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Subscription Agreement')]/..//span"));
    private static final ExtendedBy signatureDate = new ExtendedBy("signature date", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Signature date')]/..//strong"));

    private static final ExtendedBy totalTokensHeld = new ExtendedBy("Total tokens held", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Total Tokens Held')]/..//p"));
    private static final ExtendedBy totalTBESecurities = new ExtendedBy("Total TBE securities", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Total TBE Securities')]/..//p"));
    private static final ExtendedBy totalLockedTokens = new ExtendedBy("Total Locked Tokens", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Total Locked Tokens')]/..//p"));
    private static final ExtendedBy assignedTokens = new ExtendedBy("Assigned Tokens", By.xpath("//div[@class='card mb-5']//div[contains(text(), 'Assigned Tokens')]/..//p"));

    private static final ExtendedBy latestIssuancePrice = new ExtendedBy("Latest Issuance's price", By.xpath("//table/tbody/tr[1]/td[4]/span"));

    private static final ExtendedBy addIssuanceButton = new ExtendedBy("add issuance Button", By.xpath("//button/span[contains(text(), 'Add issuance')]"));

    private static final ExtendedBy resetPasswordButton = new ExtendedBy("reset password Button", By.xpath("//button/span[contains(text(), 'Reset Password')]"));

    private static final ExtendedBy investmentsCard = new ExtendedBy("Investments card", By.xpath("//div[@class='card mb-5']//h4[contains(text(), 'Investment')]/ancestor::div[@class='card mb-5']"));

    private static final ExtendedBy securitizeLogoTotalTokenAmount = new ExtendedBy("Securitize Total Token Amount", By.xpath("//div[@class='card bg-light my-3']//h4[@class='mb-0 mr-2 d-inline-block']"));
    private static final ExtendedBy securitizeLogo = new ExtendedBy("Securitize Logo", By.xpath("//div[@class='card bg-light my-3']//img[@class='ui-w-30 rounded-circle d-inline-block']"));

    private static final ExtendedBy transferToWalletFromTBEButton = new ExtendedBy("transfer to wallet from TBE button", By.xpath("//button/span[contains(text(), 'Transfer to wallet from TBE')]"));

    private static final ExtendedBy investorTypeSelector = new ExtendedBy("investor type selector", By.name("investorType"));
    private static final ExtendedBy investorTypeValue = new ExtendedBy("investor type value", By.xpath("//*[contains(@class ,'investor-type-value')]"));

    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[contains(text(), 'OK')]"));
    private static final ExtendedBy backToListButton = new ExtendedBy("back to list button", By.xpath("//button[contains(text(), 'Back to List')]"));
    private static final ExtendedBy securitiesExceededPopup = new ExtendedBy("Authorized securities exceeded popup", By.xpath("//p[contains(@class, 'toast-text')]"));
    private static final ExtendedBy setAsAffiliateButton = new ExtendedBy("set as affiliate button", By.xpath("//button/span[contains(text(), 'Set as Affiliate')]"));
    private static final ExtendedBy roundSelector = new ExtendedBy("Round Selector", By.xpath("//label[contains(text(), 'Round:')]/../div//select"));


    //Wallets
    private static final ExtendedBy walletDetailsLabelCP = new ExtendedBy("Wallet details label CP", By.xpath("//td[text()=' CP test wallet ']"));
    private static final ExtendedBy addEvergreenInvestmentButton = new ExtendedBy("add evergreen investment button", By.xpath("//button/span[contains(text(), 'Add Investment')]"));
    private static final ExtendedBy editWallet = new ExtendedBy("Edit wallet button", By.xpath("(//*[@id = 'tokensWallets']//tbody)[1]//*[contains(@class, 'ion ion-md-create')]"));
    private static final String walletRow = "//*[contains(text(), ' %s ')]//parent::td/parent::tr";


    public DocumentsCard documentsCard;
    public TransactionsCard transactionsCard;
    public WalletsCard walletsCard;
    public IssuanceCard issuancesCard;
    public AffiliateCard affiliateCard;

    public CpInvestorDetailsPage(Browser browser) {
        super(browser, editButton);
        documentsCard = new DocumentsCard(browser);
        transactionsCard = new TransactionsCard(browser);
        walletsCard = new WalletsCard(browser);
        issuancesCard = new IssuanceCard(browser);
        affiliateCard = new AffiliateCard(browser);
    }

    public String getFirstNameSecId() {
        return browser.getElementText(firstNameFieldSecId).trim();
    }

    public String getMiddleNameSecId() {
        return browser.getElementText(middleNameFieldSecId).trim();
    }

    public String getLastNameSecId() {
        return browser.getElementText(lastNameFieldSecId).trim();
    }

    public String getFirstName() {
        return browser.getElementText(firstNameField).trim();
    }

    public String getMiddleName() {
        return browser.getElementText(middleNameField).trim();
    }

    public String getLastName() {
        return browser.getElementText(lastNameField).trim();
    }

    public String getEmail() {
        return browser.getElementText(emailField);
    }

    public String getCountry() {
        return browser.getElementText(countryField).trim();
    }

    public String getState() {
        return browser.getElementText(stateField).trim();
    }

    public String getBirthday() {
        return browser.getElementText(birthdayField);
    }

    public String getGender() {
        return browser.getElementText(genderField);
    }

    public String getInvestorType() {
        return browser.getElementText(investorTypeField).trim();
    }

    public String getInvestorTypeSecId() {
        return browser.getElementText(investorTypeFieldSecId).trim();
    }

    public String getGeneralInformationSecId() {
        return browser.getElementText(generalInformationFieldSecId).trim();
    }

    public String getAuthorizedBy() {
        return browser.getElementText(authorizedByFieldSecId);
    }

    public String getLegalSignersSecId() {
        return browser.getElementText(legalSignersFieldSecId).trim();
    }

    public String getLegalSignersTypeSecId() {
        return browser.getElementText(legalSignersTypeFieldSecId).trim();
    }

    public String getWalletStatus() {
        return walletsCard.getDetailAtIndex(1, "Status");
    }

    public String getWalletAddress() {
        return walletsCard.getDetailAtIndex(1, "Address");
    }

    public String getAuthorizedAccountsSecId() {
        return browser.getElementText(authorizedAccountsFieldSecId).trim();
    }

    public String getKybSecId() {
        return browser.getElementText(kybFieldSecId).trim();
    }

    public String getLegalSignersEntriesSecId() {
        return browser.getElementText(legalSignersEntriesFieldSecId).trim();
    }

    public long getLegalSignersEntriesSecIdVisibleCount() {
        return getElementVisibleCount(legalSignersEntriesFieldSecId);
    }

    public boolean isGeneralInformationSecIdVisible() {
        return isElementVisible(generalInformationFieldSecId);
    }

    public boolean isLegalSignersSecIdVisible() {
        return isElementVisible(legalSignersFieldSecId);
    }

    public boolean isAuthorizedAccountsSecIdVisible() {
        return isElementVisible(authorizedAccountsFieldSecId);
    }

    public boolean isAuthorizedAccountsEmptyEntryFieldSecIdVisible() {
        return isElementVisible(authorizedAccountsEmptyEntryFieldSecId);
    }

    public long getAuthorizedAccountEntriesSecIdVisibleCount() {
        return getElementVisibleCount(authorizedAccountsEntriesFieldSecId);
    }

    public boolean isKybSecIdVisible() {
        return isElementVisible(kybFieldSecId);
    }

    public String getCurrentKybStatusSecId() {
        return browser.getElementText(currentKybStatusFieldSecId).trim();
    }

    public String getKybStatusLogEntriesSecId() {
        return browser.getElementText(kybStatusLogEntriesFieldSecId).trim();
    }

    public long getKybStatusLogEntriesSecIdVisibleCount() {
        return getElementVisibleCount(kybStatusLogEntriesFieldSecId);
    }

    public String getDocumentsSecId() {
        return browser.getElementText(documentsFieldSecId).trim();
    }

    public boolean isDocumentsSecIdVisible() {
        return isElementVisible(documentsFieldSecId);
    }

    public String getDocumentsAddDocumentSecId() {
        return browser.getElementText(documentAddDocumentsFieldSecId).trim();
    }

    public boolean isDocumentsAddDocumentSecIdVisible() {
        return isElementVisible(documentAddDocumentsFieldSecId);
    }

    public String getDocumentsImageSecId() {
        return browser.getElementText(documentImageFieldSecId).trim();
    }

    public long getDocumentsImageSecIdVisibleCount() {
        return getElementVisibleCount(documentImageFieldSecId);
    }

    public String getConfidentialDocumentsSecId() {
        return browser.getElementText(confidentialDocumentsFieldSecId).trim();
    }

    public boolean isConfidentialDocumentsSecIdVisible() {
        return isElementVisible(confidentialDocumentsFieldSecId);
    }

    public String getConfidentialDocumentsImageSecId() {
        return browser.getElementText(confidentialDocumentsImageFieldSecId).trim();
    }

    public long getConfidentialDocumentsImageSecIdVisibleCount() {
        return getElementVisibleCount(confidentialDocumentsImageFieldSecId);
    }

    public String getConfidentialDocumentsAddDocumentSecId() {
        return browser.getElementText(confidentialDocumentAddDocumentsFieldSecId).trim();
    }

    public boolean isConfidentialDocumentsAddDocumentSecIdVisible() {
        return isElementVisible(confidentialDocumentAddDocumentsFieldSecId);
    }

    public String getWalletsSecId() {
        return browser.getElementText(walletsFieldSecId).trim();
    }

    public boolean isWalletsSecIdVisible() {
        return isElementVisible(walletsFieldSecId);
    }

    public boolean isAddWalletButtonSecIdVisible() {
        return isElementVisible(addWalletButton);
    }

    public String getWalletsEntriesSecId() {
        return browser.getElementText(walletsEntriesFieldSecId).trim();
    }

    public long getWalletsEntriesSecIdVisibleCount() {
        return getElementVisibleCount(walletsEntriesFieldSecId);
    }

    public String getAddress() {
        return browser.getElementText(addressField).trim();
    }

    public String getAddressAdditionalInfo() {
        return browser.getElementText(addressAdditionalInfoField);
    }

    public String getPostalCode() {
        return browser.getElementText(postalCodeField).trim();
    }

    public String getCity() {
        return browser.getElementText(cityField).trim();
    }

    public String getTaxId() {
        return browser.getElementText(taxIdField).trim();
    }

    public String getTaxCountry() {
        return browser.getElementText(taxCountryField);
    }

    public String getCreationDate() {
        return browser.getElementText(creationDateField);
    }

    public String getPledgedAmount() {
        return browser.getElementText(pledgedAmountField);
    }

    public String getCustodian() {
        return browser.getElementText(custodianField);
    }

    public String getFBOEmail() {
        return browser.getElementText(fboEmailField);
    }

    public String getPhoneNumber() {
        return browser.getElementText(phoneNumberField);
    }

    public String getComment() {
        return browser.getElementText(commentField);
    }

    public String getKYCStatus() {
        return browser.getElementText(kycCurrentStatus);
    }

    public String getLastKYCChange() {
        return browser.getElementText(lastKYCChange);
    }

    public String getKYCExpirationDate() {
        return browser.getElementText(kycExpirationDate);
    }

    public String getKYCProvider() {
        return browser.getElementText(kycProvider);
    }

    public String getCurrentAccreditationStatus() {
        return browser.getElementText(currentAccreditationStatus);
    }

    public String getLastAccreditationChange() {
        return browser.getElementText(lastAccreditationChange);
    }

    public String getQualificationStatus() {
        return browser.getElementText(qualificationStatus);
    }

    public String getCurrentPledgedAmount() {
        return browser.getElementText(currentPledgedAmount);
    }

    public boolean isCurrentPledgedAmountVisible() {
        return browser.isElementVisibleQuick(currentPledgedAmount);
    }

    public String getCurrentTotalFunded() {
        return browser.getElementText(currentTotalFunded);
    }

    public String getSubscriptionAgreement() {
        return browser.getElementText(subscriptionAgreement);
    }

    public String getSignatureDate() {
        return browser.getElementText(signatureDate);
    }

    public String getTotalTokensHeld() {
        return browser.getElementAttribute(totalTokensHeld,"data-original-title").replaceAll(",", "");
    }

    public String getTotalTBESecurities() {
        return browser.getElementAttribute(totalTBESecurities, "data-original-title").replaceAll(",", "");
    }

    public String getTotalLockedTokens() { return browser.getElementAttribute(totalLockedTokens, "data-original-title").replaceAll(",", "");
    }

    public String getAssignedTokens() {
        return browser.getElementAttribute(assignedTokens, "data-original-title").replaceAll(",", "");
    }

    public String getEntityName() {
        return browser.getElementText(entityNameField);
    }

    public String getEntityNameSecId() {
        return browser.getElementText(entityNameFieldSecId);
    }

    public String getEntityDba() {
        return browser.getElementText(entityDbaField);
    }

    public String getEntityDbaSecId() {
        return browser.getElementText(entityDbaFieldSecId);
    }

    public String getEntityType() {
        return browser.getElementText(entityTypeField);
    }

    public String getLatestIssuancePrice() {
        return browser.getElementText(latestIssuancePrice).replace("\\$", "");
    }

    public int getSecuritizeTotalTokenAmount() {
        String valueAsString = browser.getElementText(securitizeLogoTotalTokenAmount).trim().replace(",", "");
        return Integer.parseInt(valueAsString);
    }

    @SuppressWarnings("unused")
    public String getInvestorId() {
        String url = browser.getCurrentUrl();
        String investorId = url.substring(url.lastIndexOf("/") + 1);
        info("Current investorId is: " + investorId);
        return investorId;
    }

    public static ExtendedBy getWalletRow(String walletAddress) {
        String dayXpath = String.format(walletRow, walletAddress);
        return new ExtendedBy("Wallet Row", By.xpath(dayXpath));
    }

    public CpInvestorDetailsPage clickEdit() {
        browser.click(editButton);
        return this;
    }

    public CpInvestorDetailsPage waitForEditButtonToBeVisible() {
        browser.waitForPageStable();
        browser.waitForElementVisibility(editButton);
        return this;
    }

    public CpInvestorDetailsPage typeFirstName(String value) {
        browser.typeTextElement(firstNameEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typeMiddleName(String value) {
        browser.typeTextElement(middleNameEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typeLastName(String value) {
        browser.typeTextElement(lastNameEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typePhoneNumber(String value) {
        browser.typeTextElement(phoneNumberEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typeBirthDate(String value) {
        browser.typeTextElement(birthDateEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage setRandomGender() {
        browser.selectRandomValueInElement(genderSelector);
        return this;
    }

    public CpInvestorDetailsPage typeAddress(String value) {
        browser.typeTextElement(addressEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typeAddressAdditionalInfo(String value) {
        browser.typeTextElement(addressAdditionalInfoEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typePostalCode(String value) {
        browser.typeTextElement(postalCodeEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typeCity(String value) {
        browser.typeTextElement(cityEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typeEntityName(String value) {
        browser.typeTextElement(entityNameEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage selectFATCA(String value) {
        browser.selectElementByVisibleText(FATCASelector, value);
        return this;
    }

    public CpInvestorDetailsPage typeComment(String value) {
        browser.typeTextElement(commentEditableField, value);
        return this;
    }

    public CpInvestorDetailsPage typePledgedAmount(String value) {
        browser.typeTextElement(pledgedAmountField, value);
        return this;
    }

    public void clickSaveChanges() {
        browser.click(saveChangesButton);

        var elements = browser.findElementsQuick(confirmSaveChangesButton, 5);
        if (!elements.isEmpty()) {
            browser.click(confirmSaveChangesButton);
        }
        else{
            browser.waitForElementVisibility(editButton);
        }
    }

    public CpAddDocument clickAddDocument() {
        browser.click(addDocumentButton);
        return new CpAddDocument(browser);
    }

    public boolean isClickAddInvestmentEnabled() {
        browser.scrollToBottomOfElement(addInvestmentButtonDirect);
        return browser.isElementEnabled(addInvestmentButtonDirect);
    }

    public CpAddInvestment clickAddInvestment() {
        browser.click(addInvestmentButton);
        return new CpAddInvestment(browser);
    }

    public CpTransferTBEToWallet clickTransferToWalletFromTBE() {
        browser.click(transferToWalletFromTBEButton);
        return new CpTransferTBEToWallet(browser);
    }

    public int getTotalFunded() {
        String valueAsString = browser.getElementText(investmentTotalFundedAmount).trim();
        return RegexWrapper.stringToInteger(valueAsString);
    }

    public CpAddTransaction clickAddTransaction() {
        browser.click(addTransactionButton);
        return new CpAddTransaction(browser);
    }

    public CpAddIssuance clickAddIssuance() {
        browser.click(addIssuanceButton);
        return new CpAddIssuance(browser);
    }

    public CpInvestorDetailsPage clickEditInvestment() {
        WebElement element = browser.findElementInElement(investmentsCard, editInvestmentButton);
        browser.click(element, editInvestmentButton.getDescription());
        return this;
    }

    public CpInvestorDetailsPage setInvestmentSubscriptionAgreement(String value) {
        browser.selectElementByVisibleText(subscriptionAgreementSelector, value);
        return this;
    }

    public CpAddWallet clickAddWallet() {
        browser.click(addWalletButton);
        return new CpAddWallet(browser);
    }

    public CpSecuritizeIdAddWallet clickSiDAddWallet() {
        browser.click(addWalletButton);
        return new CpSecuritizeIdAddWallet(browser);
    }

    public CpInvestorDetailsPage clickEditKYB() {
        WebElement element = browser.findElementInElement(kybCard, editKycButton);
        browser.click(element, editKycButton.getDescription());
        return this;
    }

    public CpInvestorDetailsPage setCurrentKybStatus(String status) {
        browser.selectElementByVisibleText(currentKycStatusSelector, status);
        return this;
    }

    public CpInvestorDetailsPage clickEditKYC() {
        WebElement element = browser.findElementInElement(kycCard, editKycButton);
        browser.click(element, editKycButton.getDescription(), ExecuteBy.JAVASCRIPT, false);
        return this;
    }

    public CpInvestorDetailsPage setCurrentKycStatus(String status) {
        browser.selectElementByVisibleText(currentKycStatusSelector, status);
        return this;
    }

    public CpInvestorDetailsPage setCurrentAccreditationStatus(String status) {
        browser.selectElementByVisibleText(currentAccreditationStatusSelector, status);
        return this;
    }

    public CpInvestorDetailsPage setQualificationStatus(String status) {
        browser.selectElementByVisibleText(currentQualificationStatusSelector, status);
        return this;
    }

    public CpInvestorDetailsPage selectRound(String roundName) {
        browser.selectElementByVisibleText(roundSelector, roundName);
        return this;
    }

    public CpInvestorDetailsPage typeKycComment(String value) {
        browser.typeTextElement(kycCommentField, value);
        return this;
    }

    public String getCurrentInvestorDirectUrl() {
        return browser.getCurrentUrl();
    }

    public void navigateInvestorDirectUrl(String url) {
        browser.navigateTo(url);
    }

    public void clickResetPassword() {
        browser.click(resetPasswordButton);
    }

    public void clickBackToListButton() {
        browser.click(backToListButton);
    }

    public String extractLinkFromEmail(String recipientAddress) {
        String regex = "(http[\\s\\S]*?password_reset\\/[\\s\\S]*?)[<| )]";
        return extractLinkFromEmail(recipientAddress, regex);
    }

    public CpInvestorDetailsPage setSignatureDate(String todaysDate) {
        browser.typeTextElement(setSubscriptionDate, todaysDate);
        return this;
    }

    public String getWalletDetailsLabelCP() {
        browser.waitForElementVisibility(walletDetailsLabelCP, 60);
        return browser.getElementText(walletDetailsLabelCP);
    }

    public String getExternalInvestorId() {
        String investorId = browser.getElementText(investorExternalId).trim();
        String externalInvestorId = investorId.substring(investorId.lastIndexOf(":") + 2);
        info("Current external investorId is: " + externalInvestorId);
        return externalInvestorId;
    }

    public CpInvestorDetailsPage setCurrentInvestorType(String type) {
        browser.selectElementByVisibleText(investorTypeSelector, type);
        return this;
    }

    public CpInvestorDetailsPage clickOK() {
        browser.clickAndWaitForElementToVanish(okButton);
        return new CpInvestorDetailsPage(browser);
    }

    public String getCurrentInvestorType() {
        {
            return browser.getElementText(investorTypeValue).trim();
        }
    }

    public boolean isElementVisible(ExtendedBy by) {
        browser.waitForElementVisibility(by);
        return browser.isElementVisible(by);
    }

    public long getElementVisibleCount(ExtendedBy by) {
        browser.waitForElementVisibility(by);
        List<WebElement> elements = browser.findElements(by);
        info("element '" + by.getDescription() + "' visible count is: " + elements.stream().filter(WebElement::isDisplayed).count());
        return elements.stream().filter(WebElement::isDisplayed).count();
    }

    public void waitForSecuritizeLogoToExist(int timeoutSeconds, int checkIntervalSeconds) {

        Function<String, Boolean> internalWaitForSecuritizeLogoToExist = t -> {
            try {
                browser.refreshPage();
                info("Checking if Securitize Logo is present or visible on the page...");
                return (browser.isElementPresentOrVisible(securitizeLogo));
            } catch (Exception e) {
                return false;
            }
        };

        String description = "waitForSecuritizeLogoToExist";
        Browser.waitForExpressionToEqual(internalWaitForSecuritizeLogoToExist, null, true, description, timeoutSeconds, checkIntervalSeconds * 1000);
    }

    public CpAddInvestment clickAddInvestmentWithJs() {
        WebElement investmentButton = browser.findElement(addInvestmentButton);
        browser.click(investmentButton, "adding investment", ExecuteBy.JAVASCRIPT, false);
        return new CpAddInvestment(browser);
    }

    public CpAddEvergreenInvestment clickAddEvergreenInvestment() {
        browser.waitForElementVisibility(currentPledgedAmount);
        browser.click(addEvergreenInvestmentButton);
        return new CpAddEvergreenInvestment(browser);
    }

    public void verifyIssuanceErrorMessage() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(browser.isElementVisible(securitiesExceededPopup));
        info("Error message after issuance is: " + browser.getElementText(securitiesExceededPopup));
        softAssert.assertEquals(browser.getElementText(securitiesExceededPopup), "Authorized securities exceeded");
        softAssert.assertAll();
    }

    public boolean verifyIssuanceSuccessMessage() {
        boolean isIssuanceMessageSuccess = false;
        String message;
        if (browser.isElementVisible(securitiesExceededPopup)) {
            message = browser.getElementText(securitiesExceededPopup).toLowerCase();
            if (message.contains("success")){
                isIssuanceMessageSuccess = true;
            }
        } else {
            errorAndStop("After creating issuance there is no message at all", true);
        }
        return isIssuanceMessageSuccess;
    }

    public CpActivateAffiliate clickSetAsAffiliate() {
        browser.waitForElementVisibility(setAsAffiliateButton);
        browser.click(setAsAffiliateButton);
        return new CpActivateAffiliate(browser);
    }

    public void waitForInvestorDetailsPageToStabilize() {
        browser.waitForPageStable();
    }

    public void waitForTokensUpdate(String tokenType, int expected) {
        ExtendedBy extendedBy = null;
        String description = null;
        switch (tokenType) {
            case "TBE":
                description = "Total TBE Securities";
                extendedBy = totalTBESecurities;
                break;
            case "wallet":
                description = "Total Tokens Held";
                extendedBy = totalTokensHeld;
                break;
            case "lock":
            case "unlock":
                description = "Total Locked Tokens";
                extendedBy = totalLockedTokens;
                break;
            case "Securitize Logo":
                description = "Total Tokens in Securitize Logo: TBE + Wallet";
                extendedBy = securitizeLogoTotalTokenAmount;
                break;
        }
        browser.waitForTextToBePresentInElementWithReloadPage(extendedBy, String.valueOf(expected), 30000, 1800, description);
    }

    public void waitForWalletTableToLoad() {
        browser.waitForElementVisibility(editWallet);
    }

    public int getWalletRowIndex(String walletAddress) {
        ExtendedBy wallet = getWalletRow(walletAddress);
        return Integer.parseInt(browser.getElementAttribute(wallet, "aria-rowindex"));
    }

    public String[] getInvestorWallets() {
        waitForInvestorDetailsPageToStabilize();
        waitForWalletTableToLoad();
        int numberOfWallets = walletsCard.getEntityCount();
        String[] walletsAddress = new String[numberOfWallets];
        for (int i = 1; i <= numberOfWallets; i++) {
            walletsCard.scrollTableIntoView(i);
            walletsCard.waitForTableToContainNumberOfRows(numberOfWallets);
            String walletAddress = walletsCard.getDetailAtIndex(i, "Address");
            int tokensHeld = Integer.parseInt(walletsCard.getDetailAtIndex(i, "Tokens Held"));
            if (tokensHeld != 0) {
                walletsAddress[0] = walletAddress;
            } else {
                if (walletsAddress.length > 1) {
                    walletsAddress[1] = walletAddress;
                } else {
                    info("walletAddress length is: " + walletsAddress.length + ", index 1 is out of bounds.");
                }
            }
        }
        return walletsAddress;
    }

    public void validateDate(String cardType, CpInvestorDetailsPage investorDetailsPage, String dateDescription) {
        String errorMessage;
        String actualDateAsString = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss");

        if (cardType.equals("Issuances")) {
            actualDateAsString = investorDetailsPage.issuancesCard.getDetailAtIndex(1, dateDescription);
        } else if (cardType.equals("Documents")) {
            actualDateAsString = investorDetailsPage.documentsCard.getDetailAtIndex(1, dateDescription);
            df = new SimpleDateFormat("MMM d, yyyy");
        } else if (cardType.equals("Wallets")) {
            actualDateAsString = investorDetailsPage.walletsCard.getDetailAtIndex(1, dateDescription);
        } else if (cardType.equals("Transactions")) {
            actualDateAsString = investorDetailsPage.transactionsCard.getDetailAtIndex(1, dateDescription);
        } else if (cardType.equals("Affiliate")) {
            actualDateAsString = investorDetailsPage.affiliateCard.getDetailAtIndex(1, dateDescription);
        }
        try {
            Date actualCreationDate = df.parse(actualDateAsString);
            Date currentDate = new Date();
            Assert.assertTrue(DateUtils.isSameDay(actualCreationDate, currentDate), "Automation " + dateDescription + " date of " + cardType + " type isn't as expected. Should be " + actualCreationDate + " but it's " + currentDate);
        } catch (ParseException e) {
            errorMessage = "Error parsing date: " + e.getMessage();
            errorAndStop(errorMessage, true);
        }
    }

    public int getTokensToProcedure(String tokenType, String procedureType) {
        int tokenAmount = 0;
        if (tokenType.equals("wallet")) {
            tokenAmount = Integer.parseInt(getTotalTokensHeld());
        } else if (tokenType.equals("TBE")) {
            tokenAmount = Integer.parseInt(getTotalTBESecurities());
        } else {
            errorAndStop("Couldn't detect token type", true);
        }
        int lockTokens =  Integer.parseInt(getTotalLockedTokens());
        if (procedureType.equals("destroy")){
            lockTokens = 0;
        }
        return tokenAmount - lockTokens;
    }

    public int getTokensToUnlock() {
        return Integer.parseInt(getTotalLockedTokens());
    }


}