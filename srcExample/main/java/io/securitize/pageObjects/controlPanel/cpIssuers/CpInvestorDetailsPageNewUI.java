package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.exceptions.StringIsNotAsExpectedException;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class CpInvestorDetailsPageNewUI extends AbstractPage {

    // General locators
    private static final ExtendedBy tokenNameDropDown = new ExtendedBy("personal information section", By.xpath("//button[@aria-haspopup='true']//span"));
    private static final ExtendedBy securitizeLogoTotalTokenAmount = new ExtendedBy("wallets section", By.xpath("//span[@class='amount pr-2']"));

    // Tab locators
    private static final ExtendedBy generalInformationTab = new ExtendedBy("general information tab", By.xpath("//button[text()='General Information']"));
    private static final ExtendedBy verificationTab = new ExtendedBy("verification tab", By.xpath("//button[text()='Verification']"));
    private static final ExtendedBy investmentTab = new ExtendedBy("investment tab", By.xpath("//button[text()='Investment']"));
    private static final ExtendedBy tokensWalletsTab = new ExtendedBy("tokens & wallets tab", By.xpath("//button[text()='Tokens & Wallets']"));
    private static final ExtendedBy historyTab = new ExtendedBy("history tab", By.xpath("//button[text()='History']"));
    private static final ExtendedBy affiliateTab = new ExtendedBy("affiliate tab", By.xpath("//button[text()='Affiliate']"));
    private static final ExtendedBy documentsTab = new ExtendedBy("documents tab", By.xpath("//button[text()='Documents']"));

    // General Information Tab
    // Personal information section
    private static final ExtendedBy personalInformationSection = new ExtendedBy("personal information section", By.xpath("//div[text()='Personal Information']"));
    private static final ExtendedBy addressSection = new ExtendedBy("address section", By.xpath("//div[text()='Address']"));
    private static final ExtendedBy taxInformationSection = new ExtendedBy("tax information section", By.xpath("//div[text()=' Tax Information']"));
    private static final ExtendedBy additionalInformationSection = new ExtendedBy("additional information section", By.xpath("//div[text()='Additional Information']"));
    private static final ExtendedBy firstNameField = new ExtendedBy("first name field", By.xpath("//span[text()='First name']/following-sibling::span"));
    private static final ExtendedBy middleNameField = new ExtendedBy("middle name field", By.xpath("//span[text()='Middle name']/following-sibling::span"));
    private static final ExtendedBy lastNameField = new ExtendedBy("last name field", By.xpath("//span[text()='Last name']/following-sibling::span"));
    private static final ExtendedBy emailField = new ExtendedBy("email field", By.xpath("//span[text()='Email']/following-sibling::span"));
    private static final ExtendedBy countryField = new ExtendedBy("country field", By.xpath("//span[text()='Country']/following-sibling::span"));
    private static final ExtendedBy stateField = new ExtendedBy("state field", By.xpath("//span[text()='State']/following-sibling::span"));
    private static final ExtendedBy commentField = new ExtendedBy("comment field", By.xpath("//span[text()='Comment']/following-sibling::span"));
    private static final ExtendedBy custodianField = new ExtendedBy("custodian field", By.xpath("//form//td[contains(text(), 'Custodian')]/../td[2]"));
    private static final ExtendedBy fboEmailField = new ExtendedBy("fbo email field", By.xpath("//form//td[contains(text(), 'FBO Email')]/../td[2]"));
    private static final ExtendedBy phoneNumberField = new ExtendedBy("phone number field", By.xpath("//form//td[contains(text(), 'Phone:')]/../td[2]"));
    private static final ExtendedBy editButton = new ExtendedBy("edit Button", By.xpath("//button[contains(text(),'Edit')]"));
    private static final ExtendedBy saveButton = new ExtendedBy("save changes Button", By.xpath("//button[contains(text(), 'Save')]"));
    private static final ExtendedBy prefixPhoneNumberEditableField = new ExtendedBy("prefix phone number editable field", By.xpath("//div[@id='mui-component-select-prefixPhone']"));
    private static final ExtendedBy prefixPhoneOption = new ExtendedBy("phone number option", By.xpath("//li[@role='option']"));
    private static final ExtendedBy phoneNumberEditableField = new ExtendedBy("phone number editable field", By.name("phone"));
    private static final ExtendedBy birthDateEditableField = new ExtendedBy("birth date editable field", By.xpath("//input[@id='formik-id-birthday']"));
    private static final ExtendedBy dateInput = new ExtendedBy("birth date input field", By.xpath("//div[@role='dialog']//button[contains(text(), '10')]"));
    private static final ExtendedBy genderSelector = new ExtendedBy("gender editable selector", By.xpath("//div[@id='mui-component-select-gender']"));
    private static final ExtendedBy genderOption = new ExtendedBy("gender option", By.xpath("//li[contains(text(),'male')]"));

    // Adresses section
    private static final ExtendedBy addressEditableField = new ExtendedBy("address editable field", By.xpath("//input[@name='address']"));
    private static final ExtendedBy addressAdditionalInfoEditableField = new ExtendedBy("address additional info editable field", By.xpath("//input[@name='additionalInfo']"));
    private static final ExtendedBy postalCodeEditableField = new ExtendedBy("postal code editable field", By.name("postalCode"));
    private static final ExtendedBy cityEditableField = new ExtendedBy("city editable field", By.name("city"));
    private static final ExtendedBy FATCASelector = new ExtendedBy("FATCA editable selector", By.id("mui-component-select-taxCountryCode"));
    private static final ExtendedBy FATCAOption = new ExtendedBy("FATCA option", By.xpath("//li[contains(text(),'United States of')]"));
    private static final ExtendedBy commentEditableField = new ExtendedBy("comment editable field", By.xpath("//textarea[@name='comment']"));

    // Investment Tab
    private static final ExtendedBy investmentSection = new ExtendedBy("investment section", By.xpath("//div[contains(text(),'Investment')]"));
    private static final ExtendedBy depositTransactionsSection = new ExtendedBy("deposit transactions section", By.xpath("//div[contains(@class,'deposit-transaction-title')]"));
    private static final ExtendedBy depositAdressesSection = new ExtendedBy("deposit transactions section", By.xpath("//div[contains(text(),'Deposit Addresses')]"));
    private static final ExtendedBy addInvestmentButton = new ExtendedBy("add investment Button", By.xpath("//button[contains(text(),'Add investment')]"));
    private static final ExtendedBy addTransactionButton = new ExtendedBy("add transaction Button", By.xpath("//button[contains(text(), 'Add transaction')]"));
    private static final ExtendedBy pledgedAmount = new ExtendedBy("pledged amount", By.xpath("//span[contains(text(),'Pledged Amount')]/following-sibling::span"));
    private static final ExtendedBy pledgeDate = new ExtendedBy("pledged date", By.xpath("//span[contains(text(),'Pledge Date')]/following-sibling::span"));
    private static final ExtendedBy pledgeInitiator = new ExtendedBy("pledged operator", By.xpath("//span[contains(text(),'Pledge Initiator')]/following-sibling::span"));
    private static final ExtendedBy totalFunded = new ExtendedBy("total funded", By.xpath("//span[contains(text(),'Total Funded')]/following-sibling::span"));
    private static final ExtendedBy subscriptionAgreement = new ExtendedBy("actual subscription agreement", By.xpath("//span[contains(text(),'Subscription Agreement')]/following-sibling::span"));
    private static final ExtendedBy signatureDate = new ExtendedBy("actual signature date", By.xpath("//span[contains(text(),'Signature Date')]/following-sibling::span"));
    private static final ExtendedBy InvestmentRoundName = new ExtendedBy("investment round name", By.xpath("//div[@id='mui-component-select-round']"));
    private static final ExtendedBy InvestmentTokenName = new ExtendedBy("investment round token", By.xpath("//div[@id='mui-component-select-token']"));

    // Tax information section
    private static final ExtendedBy txTime = new ExtendedBy("tx time", By.xpath("//div[@data-field='transactionTime' and @role='cell']"));
    private static final ExtendedBy txRound = new ExtendedBy("tx round", By.xpath("//div[@data-field='roundName' and @role='cell']"));
    private static final ExtendedBy txType = new ExtendedBy("tx type", By.xpath("//div[@data-field='direction' and @role='cell']"));
    private static final ExtendedBy txAmount = new ExtendedBy("tx amount", By.xpath("//div[@data-field='amount' and @role='cell']"));
    private static final ExtendedBy txCurrency = new ExtendedBy("tx currency", By.xpath("//div[@data-field='currencyId' and @role='cell']"));
    private static final ExtendedBy txUsdValue = new ExtendedBy("tx in USD value", By.xpath("//div[@data-field='usdWorth' and @role='cell']"));
    private static final ExtendedBy txSource = new ExtendedBy("tx source", By.xpath("//div[@data-field='source' and @role='cell']"));
    private static final ExtendedBy txId = new ExtendedBy("tx ID", By.xpath("//div[@data-field='externalTransactionConfirmation' and @role='cell']"));

    // Token & Wallets tab
    // Tokens section
    private static final ExtendedBy generalSection = new ExtendedBy("general section", By.xpath("//div[@role='button']//div[contains(text(),'General')]"));
    private static final ExtendedBy totalTokensHeld = new ExtendedBy("Total tokens held", By.xpath("//span[contains(text(),'Total tokens held')]/following-sibling::*"));
    private static final ExtendedBy totalTBESecurities = new ExtendedBy("Total TBE securities", By.xpath("//span[contains(text(),'Total TBE securities')]/following-sibling::*"));
    private static final ExtendedBy totalLockedTokens = new ExtendedBy("Total locked tokens", By.xpath("//span[contains(text(),'Total locked tokens')]/following-sibling::*"));
    private static final ExtendedBy assignedTokens = new ExtendedBy("Assigned tokens", By.xpath("//span[contains(text(),'Assigned tokens')]/following-sibling::*"));

    // Wallet section
    private static final ExtendedBy walletSection = new ExtendedBy("wallets section", By.xpath("//div[@role='button']//div[contains(text(),'Wallets')]"));
    private static final ExtendedBy addWalletButton = new ExtendedBy("add wallet button", By.xpath("//div[@id='tokenWallet-header']//button"));
    private static final ExtendedBy walletName = new ExtendedBy("wallet name", By.xpath("//div[@data-field='name']//span"));
    private static final ExtendedBy walletAddress = new ExtendedBy("wallet address", By.xpath("//div[@data-field='address' and @role='cell']"));
    private static final ExtendedBy walletType = new ExtendedBy("wallet type", By.xpath("//div[@data-field='walletType' and @role='cell']"));
    private static final ExtendedBy walletOwner = new ExtendedBy("wallet owner", By.xpath("//div[@data-field='owner' and @role='cell']"));
    private static final ExtendedBy walletStatus = new ExtendedBy("wallet owner", By.xpath("//div[@data-field='status' and @role='cell']"));
    private static final ExtendedBy walletTokensPending = new ExtendedBy("wallet tokens Pending for display", By.xpath("//div[@data-field='tokensPendingForDisplay' and @role='cell']"));
    private static final ExtendedBy walletTokensHeld = new ExtendedBy("wallet tokens held for display", By.xpath("//div[@data-field='tokensHeldForDisplay' and @role='cell']"));
    private static final ExtendedBy walletCreationDate = new ExtendedBy("wallet creation date", By.xpath("//div[@data-field='creationDate' and @role='cell']"));
    private static final ExtendedBy walletTokenName = new ExtendedBy("wallet token name", By.xpath("//div[@data-field='tokenName' and @role='cell']"));

    // Issuance section
    private static final ExtendedBy issuanceSection = new ExtendedBy("issuance section", By.xpath("//div[@role='button']//div[contains(text(),'Issuance')]"));
    private static final ExtendedBy addIssuanceButton = new ExtendedBy("add issuance button", By.xpath("//div[@class='issuance-table-title']//button"));
    private static final ExtendedBy issuanceID = new ExtendedBy("issuance ID", By.xpath("//div[@role='cell' and @data-field='id']"));
    private static final String issuanceIdPattern = "(//div[@role='cell' and @data-field='id'])[%s]";
    private static final String issuanceCreatedPattern = "(//div[@role='cell' and @data-field='id'])[%s]/following-sibling::div";
    private static final String issuanceAmountPattern = "(//div[@role='cell' and @data-field='issueAmount']//div)[%s]";
    private static final String issuanceSourcePattern = "(//div[@role='cell' and @data-field='source'])[%s]";
    private static final String issuanceTargetPattern = "(//div[@role='cell' and @data-field='target'])[%s]";
    private static final String issuanceStatusPattern = "(//div[@role='cell' and @data-field='target'])[%s]/following-sibling::div";
    private static final String issuanceDatePattern = "(//div[@role='cell' and @data-field='target'])[%s]/following-sibling::div/following-sibling::div";
    private static final String issuanceTokensNamePattern = "(//div[@role='cell' and @data-field='description'])[%s]/following-sibling::div";
    private static final String issuanceDescriptionPattern = "(//div[@role='cell' and @data-field='description'])[%s]";
    private int issuanceCounter;

    // Documents tab
    private static final ExtendedBy documents = new ExtendedBy("documents section", By.xpath("//div[@role='tabpanel']//div[contains(text(),'Documents')]"));
    private static final ExtendedBy addDocument = new ExtendedBy("add documents button", By.xpath("//button[contains(text(),'Add document')]"));
    private static final ExtendedBy documentImg = new ExtendedBy("documents img", By.xpath("//div[@data-field='thumbnailUrl' and @role='cell']//img"));
    private static final ExtendedBy documentsTitle = new ExtendedBy("documents title", By.xpath("//div[@data-field='documentTitle' and @role='cell']"));
    private static final ExtendedBy documentsType = new ExtendedBy("documents typ", By.xpath("//div[@data-field='type' and @role='cell']"));
    private static final ExtendedBy documentsFace = new ExtendedBy("documents face", By.xpath("//div[@data-field='documentFace' and @role='cell']"));
    private static final ExtendedBy documentCreationDate = new ExtendedBy("documents creation date", By.xpath("//div[@data-field='createdAt' and @role='cell']"));
    private static final ExtendedBy documentOrigin = new ExtendedBy("documents origin", By.xpath("//div[@data-field='origin' and @role='cell']"));
    private static final ExtendedBy documentTokenName = new ExtendedBy("documents token name", By.xpath("//div[@data-field='tokenId' and @role='cell']"));

    // Verification tab
    private static final ExtendedBy editKycButton = new ExtendedBy("edit KYC button", By.xpath("//button[contains(text(),'Edit')]"));
    private static final ExtendedBy saveKycButton = new ExtendedBy("edit KYC button", By.xpath("//button[contains(text(),'Save')]"));
    private static final ExtendedBy kycStatus = new ExtendedBy("KYC status", By.xpath("//div[@id='mui-component-select-kycStatus']"));
    private static final ExtendedBy qualificationStatus = new ExtendedBy("qualification status", By.xpath("//div[@id='mui-component-select-userTokenQualificationStatus']"));
    private static final ExtendedBy accreditationStatus = new ExtendedBy("accreditation status", By.xpath("//div[@id='mui-component-select-accreditedStatus']"));
    private static final String kycPattern = "//div[@id='menu-kycStatus']//li[contains(text(),'%s')]";
    private static final String qualificationPattern = "//div[@id='menu-userTokenQualificationStatus']//li[contains(text(),'%s')]";
    private static final String accreditationPattern = "//div[@id='menu-accreditedStatus']//li[contains(text(),'%s')]";
    private static final ExtendedBy verificationComment = new ExtendedBy("verification comment", By.xpath("//input[@name='kycComments']"));

    private static final ExtendedBy kyc = new ExtendedBy("actual verification", By.xpath("//span[contains(text(),'KYC Status')]/following::span[1]"));
    private static final ExtendedBy qualification = new ExtendedBy("actual Qualification", By.xpath("//span[contains(text(),'Qualification Status')]/following::span[1]"));
    private static final ExtendedBy accreditation = new ExtendedBy("actualAccreditation", By.xpath("//span[contains(text(),'accreditation status')]/following::span[1]"));

    public CpInvestorDetailsPageNewUI(Browser browser) {
        super(browser, generalInformationTab);
    }

    public String getTokenTicker() {
        return browser.getElementText(tokenNameDropDown);
    }

    public CpInvestorDetailsPageNewUI clickGeneralInformation() {
        browser.click(generalInformationTab);
        browser.click(addressSection);
        browser.click(taxInformationSection);
        browser.click(additionalInformationSection);
        return this;
    }

    public CpInvestorDetailsPageNewUI clickPersonalInformation() {
        browser.click(personalInformationSection);
        return this;
    }

    public CpInvestorDetailsPageNewUI clickAddress() {
        browser.click(addressSection);
        return this;
    }

    public void clickVerificationTab() {
        browser.click(verificationTab);
    }

    public void clickInvestmentTab() {
        var elements = browser.findElementsQuick(depositTransactionsSection, 2);
        if (elements.isEmpty()) {
        browser.click(investmentTab);
        browser.click(depositTransactionsSection);
        browser.click(depositAdressesSection);
        }
    }

    public void clickTokensWalletTab() {
        var elements = browser.findElementsQuick(totalTokensHeld, 2);
        if (elements.isEmpty()) {
            browser.click(tokensWalletsTab);
            browser.click(walletSection);
            browser.click(issuanceSection);
        }
    }

    public void clickHistoryTab() {
        browser.click(historyTab);
    }

    public void clickAffiliate() {
        browser.click(affiliateTab);
    }

    public void clickDocumentsTab() {
        var elements = browser.findElementsQuick(documents, 2);
        if (elements.isEmpty()) {
            browser.click(documentsTab);
            browser.click(documents);
        }
    }

    public String getFirstName() {
        return browser.getElementText(firstNameField)
                .trim();
    }

    public String getMiddleName() {
        return browser.getElementText(middleNameField)
                .trim();
    }

    public String getLastName() {
        return browser.getElementText(lastNameField)
                .trim();
    }

    public String getEmail() {
        return browser.getElementText(emailField);
    }

    public String getCountry() {
        return browser.getElementText(countryField);
    }

    public String getState() {
        return browser.getElementText(stateField)
                .trim();
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

    public String getCustodian() {
        return browser.getElementText(custodianField);
    }

    public String getActualDocumentsTitle() {
        return browser.getElementText(documentsTitle);
    }

    public String getActualDocumentsType() {
        return browser.getElementText(documentsType);
    }

    public String getActualDocumentsFace() {
        return browser.getElementText(documentsFace);
    }

    public boolean isDocumentImgDisplay() {
        return browser.isElementPresent(documentImg, 5);
    }

    public CpInvestorDetailsPageNewUI clickEdit() {
        browser.click(editButton);
        return this;
    }

    public CpAddDocumentNewUI clickAddDocument() {
        browser.click(addDocument);
        return new CpAddDocumentNewUI(browser);
    }

    public CpInvestorDetailsPageNewUI typePhoneNumber(String valuePhone) {
        browser.click(prefixPhoneNumberEditableField);
        browser.click(prefixPhoneOption);
        browser.typeTextElementCtrlA(phoneNumberEditableField, valuePhone);
        return this;
    }

    public CpInvestorDetailsPageNewUI typeBirthDate() {
        browser.click(birthDateEditableField);
        browser.click(dateInput);
        return this;
    }

    public CpInvestorDetailsPageNewUI setRandomGender() {
        browser.click(genderSelector);
        browser.click(genderOption);
        return this;
    }

    public CpInvestorDetailsPageNewUI typeAddress(String value) {
        browser.typeTextElementCtrlA(addressEditableField, value);
        return this;
    }

    public CpInvestorDetailsPageNewUI typePostalCode(String value) {
        browser.typeTextElementCtrlA(postalCodeEditableField, value);
        return this;
    }

    public CpInvestorDetailsPageNewUI typeCity(String value) {
        browser.typeTextElementCtrlA(cityEditableField, value);
        return this;
    }

    public CpInvestorDetailsPageNewUI selectFATCA(String value) {
        browser.click(FATCASelector);
        browser.click(FATCAOption);
        return this;
    }

    public CpInvestorDetailsPageNewUI typeComment(String value) {
        browser.scrollWaitClick(commentEditableField);
        browser.typeTextElementCtrlA(commentEditableField, value);
        return this;
    }

    public CpInvestorDetailsPageNewUI typeAddressAdditionalInfo(String value) {
        browser.typeTextElementCtrlA(addressAdditionalInfoEditableField, value);
        return this;
    }

    public String getActualDocumentCreationDate() {
        return browser.getElementText(documentCreationDate);
    }

    public String getActualTokenName() {
        return browser.getElementText(documentTokenName);
    }

    public String getActualTokenOrigin() {
        return browser.getElementText(documentOrigin);
    }

    public String getActualPledgedDate() {
        return browser.getElementText(pledgeDate);
    }

    public String getActualPledgeInitiator() {
        return browser.getElementText(pledgeInitiator);
    }

    public void clickSave() {
        browser.clickAndWaitForElementToVanish(saveButton);
    }

    public CpAddInvestmentNewUI clickAddInvestment() {
        browser.click(addInvestmentButton);
        return new CpAddInvestmentNewUI(browser);
    }

    public CpAddTransactionNewUI clickAddTransaction() {
        browser.click(addTransactionButton);
        return new CpAddTransactionNewUI(browser);
    }

    public String getPledgedAmount() {
        return browser.getElementText(pledgedAmount);
    }

    public String getPledgedDate() {
        return browser.getElementText(pledgeDate);
    }

    public String getPledgedInitiator() {
        return browser.getElementText(pledgeInitiator);
    }

    public String getSubscriptionAgreement() {
        return browser.getElementText(subscriptionAgreement);
    }

    public String getSignatureDate() {
        return browser.getElementText(signatureDate);
    }

    public String getActualTotalFunded() {
        return browser.getElementText(totalFunded);
    }

    public String getActualSignatureDate() {
        return browser.getElementText(signatureDate);
    }

    public String getActualTxTime() {
        return browser.getElementText(txTime);
    }

    public String getActualTxRound() {
        return browser.getElementText(txRound);
    }

    public String getActualTxType() {
        return browser.getElementText(txType);
    }

    public String getActualTxAmount() {
        return browser.getElementText(txAmount);
    }

    public String getActualTxCurrency() {
        return browser.getElementText(txCurrency);
    }

    public String getActualTxUsdValue() {
        return browser.getElementText(txUsdValue);
    }

    public String getActualTxSource() {
        return browser.getElementText(txSource);
    }

    public String getActualTxId() {
        return browser.getElementText(txId);
    }

    public String getTxInvestmentRoundName() {
        return browser.getElementText(InvestmentRoundName);
    }

    public String getActualInvestmentTokenName() {
        return browser.getElementText(InvestmentTokenName);
    }

    public CpAddWalletNewUI clickAddWallet() {
        browser.click(addWalletButton);
        return new CpAddWalletNewUI(browser);
    }

    public String getActualWalletName() {
        return browser.getElementText(walletName);
    }

    public String getActualWalletAddress() {
        return browser.getElementText(walletAddress);
    }

    public String getActualWalletType() {
        return browser.getElementText(walletType);
    }

    public String getActualWalletOwner() {
        return browser.getElementText(walletOwner);
    }

    public String getActualWalletStatus() {
        return browser.getElementText(walletStatus);
    }

    public String getActualWalletTokensPending() {
        return browser.getElementText(walletTokensPending);
    }

    public String getActualWalletTokensHeld() {
        return browser.getElementText(walletTokensHeld);
    }

    public String getActualWalletCreationDate() {
        String fullDate = browser.getElementText(walletCreationDate);
        return DateTimeUtils.convertDate(fullDate, "yyyy-MM-dd, HH:mm:ss a", "yyyy-MM-dd");
    }

    public String getActualWalletTokenName() {
        return browser.getElementText(walletTokenName);
    }

    public void clickEditVerification() {
        browser.click(editKycButton);
        browser.waitForElementVisibility(saveKycButton);
    }

    public void setKYCStatus(String value) {
        ExtendedBy kycOption = setValueInXpath(kycPattern, value, "KYC option " + value);
        browser.click(kycStatus);
        browser.click(kycOption);
    }

    public void setQualificationStatus(String value) {
        ExtendedBy qualificationOption = setValueInXpath(qualificationPattern, value, "Qualification option " + value);
        browser.click(qualificationStatus);
        browser.click(qualificationOption);
    }

    public void setAccreditationStatus(String value) {
        ExtendedBy accreditationOption = setValueInXpath(accreditationPattern, value, "Accreditation option " + value);
        browser.click(accreditationStatus);
        browser.click(accreditationOption);
    }

    public void addVerificationComment(String comment) {
        browser.typeTextElementCtrlA(verificationComment, comment);
    }

    public void clickSaveVerification() {
        browser.click(saveKycButton);
        browser.waitForElementVisibility(editButton);
    }

    public String getActualKYC() {
        return browser.getElementText(kyc);
    }

    public String getActualQualification() {
        return browser.getElementText(qualification);
    }

    public String getActualAccreditation() {
        return browser.getElementText(accreditation);
    }

    public CpAddIssuanceNewUI clickAddIssuance() {
        browser.click(addIssuanceButton);
        return new CpAddIssuanceNewUI(browser);
    }

    public void setIssuanceRowIndex() {
        // issuance row index will always be the last
        browser.waitForPageStable();
        List<WebElement> listOfIssuanceID = browser.findElements(issuanceID);
        issuanceCounter = listOfIssuanceID.size();
    }

    public String getActualIssuanceID() {
        ExtendedBy actualIssuanceId = setValueInXpath(issuanceIdPattern, issuanceCounter, "actual issuance ID");
        return browser.getElementText(actualIssuanceId);
    }

    public String getActualIssuanceCreated() {
        ExtendedBy actualIssuanceCreated = setValueInXpath(issuanceCreatedPattern, issuanceCounter, "actual issuance creation date");
        String fullDate = browser.getElementText(actualIssuanceCreated);
        return DateTimeUtils.convertDate(fullDate, "yyyy-MM-dd, HH:mm:ss a", "yyyy-MM-dd");
    }

    public String getActualIssuanceAmount() {
        ExtendedBy actualIssuanceAmount = setValueInXpath(issuanceAmountPattern, issuanceCounter, "actual issuance amount");
        return browser.getElementText(actualIssuanceAmount);
    }

    public String getActualIssuanceSource() {
        ExtendedBy actualIssuanceSource = setValueInXpath(issuanceSourcePattern, issuanceCounter, "actual issuance source");
        return browser.getElementText(actualIssuanceSource);
    }

    public String getActualIssuanceTarget() {
        ExtendedBy actualIssuanceTarget = setValueInXpath(issuanceTargetPattern, issuanceCounter, "actual issuance target");
        return browser.getElementText(actualIssuanceTarget);
    }

    public String getActualIssuanceStatus() {
        ExtendedBy actualIssuanceStatus = setValueInXpath(issuanceStatusPattern, issuanceCounter, "actual issuance status");
        return browser.getElementText(actualIssuanceStatus);
    }

    public String getActualIssuanceStatus(int rowIndex) {
        ExtendedBy actualIssuanceStatus = setValueInXpath(issuanceStatusPattern, rowIndex, "actual issuance status");
        return browser.getElementText(actualIssuanceStatus);
    }

    public String getActualIssuanceDate() {
        ExtendedBy actualIssuanceDate = setValueInXpath(issuanceDatePattern, issuanceCounter, "actual issuance date");
        return browser.getElementText(actualIssuanceDate);
    }

    public String getActualIssuanceDescription() {
        ExtendedBy actualIssuanceDescription = setValueInXpath(issuanceDescriptionPattern, issuanceCounter, "actual issuance description");
        return browser.getElementText(actualIssuanceDescription);
    }

    public String getActualIssuanceTokenName() {
        ExtendedBy actualIssuanceTokensName = setValueInXpath(issuanceTokensNamePattern, issuanceCounter, "actual issuance tokens name");
        return browser.getElementText(actualIssuanceTokensName);
    }

    public void waitForIssuanceToBeSuccess(String expectedStatus, int issuanceRowIndex){
        final String[] actualStatus = new String[1];
        Function<String, String> internalWaitForExpectedStatus = t -> {
            actualStatus[0] = getActualIssuanceStatus(issuanceRowIndex).toLowerCase();
            try {
                if (!actualStatus[0].equals("success")) {
                    info("Actual status is not as expected. It is "+ actualStatus[0]);
                    browser.refreshPage();
                    clickTokensWalletTab();
                } else {
                    throw new RuntimeException("Issuance status did not change");
                }
            } catch (Exception e) {
            }
            actualStatus[0] = getActualIssuanceStatus().toLowerCase();
            return actualStatus[0];
        };
        String description = "Actual status should be "+expectedStatus ;
        StringIsNotAsExpectedException issuanceIsNotSuccess = new StringIsNotAsExpectedException("Actual status is no as expected after " + 900 + " seconds.", actualStatus[0], expectedStatus);
        Browser.waitForExpressionToEqual(internalWaitForExpectedStatus, expectedStatus, expectedStatus, description, 900, 30000, issuanceIsNotSuccess);
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
        waitForTextToBePresentInElementWithReloadPage(extendedBy, String.valueOf(expected), 600, 30, description);
    }

    public void waitForTextToBePresentInElementWithReloadPage(ExtendedBy by, String expected, int maxTimeout, int timeIntervals, String elementDescription) {
        final String[] actual = new String[1];
        Function<String, String> waitForTextBeEqual = t -> {
            try {
                actual[0] = browser.getElementText(by);
                if (!actual[0].equals(expected)) {
                    info(elementDescription+" is not as expected. It is "+ actual[0]+". waiting more "+timeIntervals+ " milliseconds.");
                    browser.refreshPage();
                    clickTokensWalletTab();
                }
            } catch (Exception e) {
                throw new RuntimeException(elementDescription+ " did not change to "+expected+", we reach timeout.");
            }
            return expected;
        };
        String description = elementDescription+" should be "+expected ;
        StringIsNotAsExpectedException stringIsNotAsExpected = new StringIsNotAsExpectedException(elementDescription+" is not "+expected+" as expected after " + timeIntervals + " seconds.", actual[0], expected);
        Browser.waitForExpressionToEqual(waitForTextBeEqual, expected, expected, description, timeIntervals, maxTimeout, stringIsNotAsExpected);
    }

}