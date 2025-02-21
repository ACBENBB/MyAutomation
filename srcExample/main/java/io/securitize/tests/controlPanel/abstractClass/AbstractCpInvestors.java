package io.securitize.tests.controlPanel.abstractClass;

import com.neovisionaries.i18n.CountryCode;
import com.opencsv.exceptions.CsvException;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.Users;
import io.securitize.infra.utils.CSVUtils;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.RandomGenerator;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.tests.InvestorDetails;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;
import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public abstract class AbstractCpInvestors extends AbstractCpNavigation {

    private CpInvestorDetailsPage investorDetailsPage;
    private CpInvestorDetailsPageNewUI investorDetailsPageNewUI;
    private String investorDirectUrl;
    private String investorDirectUrlNewUI;
    private String investorExternalID;
    private String tokenTicker;
    private String tokenName;
    private String firstName;
    private final String onboarding = "onboarding";
    private String errorMessage;
    private final String NON_VALID_SEARCH = "ThisInvestorShouldNotExist";

    public void cpInvestorRegistration(InvestorDetails investorDetails, boolean addCustodian) {
        startTestLevel("Add investor");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        CpAddInvestor addInvestor = onBoarding.clickAddInvestor();
        String state = addInvestor
                .typeFirstName(investorDetails.getFirstName())
                .typeMiddleName(investorDetails.getMiddleName())
                .typeLastName(investorDetails.getLastName())
                .typeEmail(investorDetails.getEmail())
                .selectCountry(investorDetails.getCountry())
                .selectRandomState();
        if (addCustodian) {
            addInvestor.selectCustodian(investorDetails.getCustodian());
        }
        investorDetailsPage = addInvestor.clickOK();
        endTestLevel();

        startTestLevel("Saving External ID and investor url for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        investorExternalID = investorDetailsPage.getExternalInvestorId();
        endTestLevel();

        startTestLevel("Validate investor details");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(investorDetailsPage.getFirstName(), investorDetails.getFirstName(), "incorrect first name, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPage.getMiddleName(), investorDetails.getMiddleName(), "incorrect middle name, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPage.getLastName(), investorDetails.getLastName(), "incorrect last name, this is not the value we set!");
        if (addCustodian) {
            softAssert.assertEquals(investorDetailsPage.getFBOEmail(), investorDetails.getEmail(), "incorrect email, this is not the value we set!");
        } else {
            softAssert.assertEquals(investorDetailsPage.getEmail(), investorDetails.getEmail(), "incorrect email, this is not the value we set!");
        }
        softAssert.assertEquals(investorDetailsPage.getCountry(), investorDetails.getCountry(), "incorrect country, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPage.getState(), state, "incorrect state, this is not the value we set!");
        if (addCustodian) {
            softAssert.assertEquals(investorDetailsPage.getCustodian(), investorDetails.getCustodian(), "incorrect custodian, this is not the value we set!");
        }
        softAssert.assertAll();
        endTestLevel();

        startTestLevel("Add investor details");
        investorDetailsPage.clickEdit()
                .typePhoneNumber(investorDetails.getPhoneNumber())
                .typeBirthDate(investorDetails.getBirthDate())
                .setRandomGender()
                .typeAddress(investorDetails.getAddress())
                .typePostalCode(investorDetails.getPostalCode() + "")
                .typeCity(investorDetails.getCity())
                .selectFATCA(investorDetails.getCountry())
                .typeComment(investorDetails.getComment())
                .typeAddressAdditionalInfo(investorDetails.getStreetAdditionalInfo())
                .clickSaveChanges();
        endTestLevel();
    }

    public void cpInvestorRegistrationNewUI(InvestorDetails investorDetails, boolean addCustodian) {
        startTestLevel("Add investor");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        CpAddInvestor addInvestor = onBoarding.clickAddInvestor();
        String state = addInvestor.typeFirstName(investorDetails.getFirstName())
                .typeMiddleName(investorDetails.getMiddleName())
                .typeLastName(investorDetails.getLastName())
                .typeEmail(investorDetails.getEmail())
                .selectCountry(investorDetails.getCountry())
                .selectRandomState();
        if (addCustodian) {
            addInvestor.selectCustodian(investorDetails.getCustodian());
        }
        investorDetailsPage = addInvestor.clickOK();
        endTestLevel();

        startTestLevel("Saving External ID and investor url for future use");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        investorExternalID = investorDetailsPage.getExternalInvestorId();
        endTestLevel();

        startTestLevel("Saving new url and navigate to New UI investor details page");
        investorDirectUrlNewUI = investorDirectUrl.replace("/investors/", "/investors-mfe/");
        navigateNewInvestorDirectUrl(investorDirectUrlNewUI);
        endTestLevel();

        startTestLevel("Saving token ticker and token name");
        tokenTicker = investorDetailsPageNewUI.getTokenTicker();
        tokenName = getTokenName(tokenTicker);
        firstName = investorDetailsPageNewUI.getFirstName();
        endTestLevel();

        startTestLevel("Open all sections to show all data fields");
        investorDetailsPageNewUI.clickGeneralInformation();
        endTestLevel();

        startTestLevel("Validate investor details - New UI");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(investorDetailsPageNewUI.getFirstName(), investorDetails.getFirstName(), "incorrect first name, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPageNewUI.getMiddleName(), investorDetails.getMiddleName(), "incorrect middle name, this is not the value we set!");
        softAssert.assertEquals(investorDetailsPageNewUI.getLastName(), investorDetails.getLastName(), "incorrect last name, this is not the value we set!");
        if (addCustodian) {
            softAssert.assertEquals(investorDetailsPageNewUI.getFBOEmail(), investorDetails.getEmail(), "incorrect email, this is not the value we set!");
        } else {
            softAssert.assertEquals(investorDetailsPageNewUI.getEmail(), investorDetails.getEmail(), "incorrect email, this is not the value we set!");
        }
        softAssert.assertEquals(investorDetailsPageNewUI.getCountry(), investorDetails.getCountry(), "incorrect country, this is not the value we set!");
        // We have a bug here - I keep the note to remember - ISR2-2271
        // direct link - https://securitize.atlassian.net/browse/ISR2-2271
        //Assert.assertEquals(newInvestorDetailsPage.getState(), state, "incorrect state, this is not the value we set!");
        if (addCustodian) {
            softAssert.assertEquals(investorDetailsPageNewUI.getCustodian(), investorDetails.getCustodian(), "incorrect custodian, this is not the value we set!");
        }
        softAssert.assertAll();
        endTestLevel();

        startTestLevel("Add investor details");
        investorDetailsPageNewUI.clickEdit().typePhoneNumber(
                        investorDetails.getPhoneNumber())
                .typeBirthDate()
                .setRandomGender()
                .typeAddress(investorDetails.getAddress())
                .typePostalCode(investorDetails.getPostalCode() + "")
                .typeCity(investorDetails.getCity())
                .selectFATCA(investorDetails.getCountry())
                .typeComment(investorDetails.getComment())
                .typeAddressAdditionalInfo(investorDetails.getStreetAdditionalInfo())
                .clickSave();
        endTestLevel();
    }

    public void cpEditInvestorDetails(String searchText) {

        startTestLevel("Navigate to Onboarding");
        navigateToPage(onboarding);
        CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
        endTestLevel();

        startTestLevel("Filtering by investor's first name");
        onBoarding.searchUniqueInvestorByTextBox(searchText, NON_VALID_SEARCH);
        endTestLevel();

        startTestLevel("Enter to investor details and save the data to future validation");
        CpInvestorDetailsPage investorDetailsPage = onBoarding.clickOnFirstInvestor();
        String email = investorDetailsPage.getEmail();
        String address = investorDetailsPage.getAddress();
        String additionalInfo = investorDetailsPage.getAddressAdditionalInfo();
        String postalCode = investorDetailsPage.getPostalCode();
        String city = investorDetailsPage.getCity();
        String country = investorDetailsPage.getCountry();
        String taxCountry = investorDetailsPage.getTaxCountry();
        endTestLevel();

        startTestLevel("Checking if current investor is individual");
        String investorValue = investorDetailsPage.getCurrentInvestorType();
        if (!investorValue.equals("Individual")) {
            investorDetailsPage.clickEdit()
                    .setCurrentInvestorType("Individual")
                    .clickOK()
                    .clickSaveChanges();
        }
        endTestLevel();

        startTestLevel("Update investor type to 'Entity'");
        investorDetailsPage.clickEdit()
                .setCurrentInvestorType("Entity")
                .clickOK()
                .clickSaveChanges();
        endTestLevel();

        startTestLevel("Validate Entity details");
        Assert.assertEquals(investorDetailsPage.getEmail(), email, "incorrect email, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddress(), address, "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), additionalInfo, "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getPostalCode(), postalCode, "incorrect postal code, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCity(), city, "incorrect city, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCountry(), country, "incorrect country, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getTaxCountry(), taxCountry, "incorrect tax country, this is not the value we set!");
        endTestLevel();

        startTestLevel("Update investor type to 'Individual'");
        investorDetailsPage.clickEdit()
                .setCurrentInvestorType("Individual")
                .clickOK()
                .clickSaveChanges();
        endTestLevel();

        startTestLevel("Validate investor details");
        Assert.assertEquals(investorDetailsPage.getEmail(), email, "incorrect email, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddress(), address, "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), additionalInfo, "incorrect address, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getPostalCode(), postalCode, "incorrect postal code, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCity(), city, "incorrect city, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getCountry(), country, "incorrect country, this is not the value we set!");
        Assert.assertEquals(investorDetailsPage.getTaxCountry(), taxCountry, "incorrect tax country, this is not the value we set!");
        endTestLevel();
    }

    public void cpActivateAffiliate() {

        startTestLevel("Activating affiliate");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.clickSetAsAffiliate()
                .addComment("comment")
                .clickOk();
        endTestLevel();
    }

    public void cpSignAffiliateTransaction(String issuerName, InvestorDetails investorDetails) {

        startTestLevel("Sign affiliate transaction");
        CpSignatures signatures = new CpSignatures(getBrowser());
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPK);

        //signing affiliate transaction
        signatures.waitForSignaturePageToStabilize();
        signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();
    }

    public void cpDeactivateAffiliate() {

        startTestLevel("Deactivate Affiliate status");
        CpAffiliateManagement cpAffiliateManagement = new CpAffiliateManagement(getBrowser());
        cpAffiliateManagement.clickDeactivateAffiliate();
        cpAffiliateManagement.setDeactivateComment("comment");
        cpAffiliateManagement.clickSubmitButton();
        endTestLevel();
    }

    public void cpAddDocument(String frontImagePath) {
        startTestLevel("Add document to investor");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        CpAddDocument addDocumentPage = investorDetailsPage.clickAddDocument();
        // due to product behaving differently for different categories - sometimes shows display text
        // and sometimes showing inner value - we have to compare them both
        String randomlySelectedImageCategory = addDocumentPage.selectRandomCategory();
        String innerValueOfSelectedImageCategory = addDocumentPage.getInnerValueForDocumentCategory(randomlySelectedImageCategory);
        addDocumentPage.uploadImage(frontImagePath).clickOk();

        investorDetailsPage.documentsCard.waitForTableToContainNumberOfRows(1);

        // validate document type
        String actualCategory = investorDetailsPage.documentsCard.getDetailAtIndex(1, "Type");
        boolean documentCategoryMatch = actualCategory.equals(randomlySelectedImageCategory) || actualCategory.equals(innerValueOfSelectedImageCategory);
        Assert.assertTrue(documentCategoryMatch, "actual document category doesn't match set category");

        // validate document face
        String actualFace = investorDetailsPage.documentsCard.getDetailAtIndex(1, "Document Face");
        Assert.assertEquals(actualFace.toLowerCase(), "front", "actual document face doesn't match default value of 'front'");

        // validate creation date
        investorDetailsPage.validateDate("Documents", investorDetailsPage, "Creation Date");

        endTestLevel();
    }

    public void cpAddDocumentNewUI(String frontImagePath, String documentCategory, String documentFace, String documentTitle) {
        startTestLevel("Add document to investor");
        investorDetailsPageNewUI.clickDocumentsTab();
        CpAddDocumentNewUI addDocumentPage = investorDetailsPageNewUI.clickAddDocument();
        addDocumentPage.uploadImage(frontImagePath);
        addDocumentPage.addDocumentCategory();
        addDocumentPage.addDocumentFace();
        addDocumentPage.addDocumentTitle(documentTitle);
        addDocumentPage.addDocumentToken(tokenTicker);
        addDocumentPage.clickOk();
        addDocumentPage.clickDocumentsSection();
        endTestLevel();

        startTestLevel("Validate document details");
        SoftAssert softAssert = new SoftAssert();
        // validate document image
        // remove comment after fixing defect ISR2-2301
        // direct link - https://securitize.atlassian.net/browse/ISR2-2301
        // softAssert.assertTrue(investorDetailsPageNewUI.isDocumentImgDisplay());
        softAssert.assertEquals(investorDetailsPageNewUI.getActualDocumentsTitle(), documentTitle, "Document title doesn't match");
        softAssert.assertEquals(investorDetailsPageNewUI.getActualDocumentsType(), documentCategory, "Document title doesn't match");
        softAssert.assertEquals(investorDetailsPageNewUI.getActualDocumentsFace(), documentFace, "Document face doesn't match");
        softAssert.assertTrue(DateTimeUtils.validateDate("Creation Date", "MMM d, yyyy", investorDetailsPageNewUI.getActualDocumentCreationDate()));
        softAssert.assertEquals(investorDetailsPageNewUI.getActualTokenOrigin(), "Internal", "Document origin doesn't match");
        // validate document token
        // remove comment after fixing defect  ISR2-2302
        // direct link - https://securitize.atlassian.net/browse/ISR2-2302
        // String actualToken = investorDetailsPageNewUI.getActualTokenName();
        //softAssert.assertEquals(actualToken, tokenName, "Document origin doesn't match");
        softAssert.assertAll();
        endTestLevel();
    }

    public void setVerification() {

        startTestLevel("Set expected values to verification");
        String expectedKYC = "Verified";
        String expectedQualification = "Confirmed";
        String expectedAccreditation = "Confirmed";
        endTestLevel();

        startTestLevel("Navigate Verification tab");
        investorDetailsPageNewUI.clickVerificationTab();
        endTestLevel();

        startTestLevel("Set all verification");
        investorDetailsPageNewUI.clickEditVerification();
        investorDetailsPageNewUI.setKYCStatus(expectedKYC);
        investorDetailsPageNewUI.setQualificationStatus(expectedQualification);
        investorDetailsPageNewUI.setAccreditationStatus(expectedAccreditation);
        String comment = "comment_kyc_" + RandomGenerator.randomString(20);
        investorDetailsPageNewUI.addVerificationComment(comment);
        investorDetailsPageNewUI.clickSaveVerification();
        endTestLevel();

        startTestLevel("Verify verification saved");
        String actualKYC = investorDetailsPageNewUI.getActualKYC();
        Assert.assertEquals(expectedKYC, actualKYC);
        String actualQualification = investorDetailsPageNewUI.getActualQualification();
        Assert.assertEquals(expectedQualification, actualQualification);
        String actualAccreditation = investorDetailsPageNewUI.getActualAccreditation();
        Assert.assertEquals(expectedAccreditation, actualAccreditation);
        endTestLevel();
    }

    public void cpSetKycAndAccreditaionToVerified() {

        startTestLevel("Set KYC & Accreditation");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        String kycComment = "comment_" + RandomGenerator.randomString(20);
        investorDetailsPage.clickEditKYC()
                .setCurrentKycStatus("Verified")
                .setCurrentAccreditationStatus("Confirmed")
                .setQualificationStatus("Confirmed")
                .typeKycComment(kycComment)
                .clickSaveChanges();
        endTestLevel();
    }

    public void cpAssertOpportunity(String opportunityName, int numberOfCards) {
        CpMarketsOverview marketsOverview = new CpMarketsOverview(getBrowser());
        marketsOverview.searchOpportunityByTextBox(opportunityName);
        marketsOverview.assertNumberOfCards(numberOfCards);
    }

    public void cpSwitchToMarketsOverviewInvestorsTab() {
        CpMarketsOverview marketsOverview = new CpMarketsOverview(getBrowser());
        marketsOverview.clickInvestorsTab();
    }

    public void cpSelectOpportunity(String opportunityName) {
        CpMarketsOverview marketsOverview = new CpMarketsOverview(getBrowser());
        marketsOverview.selectOpportunity(opportunityName);
    }

    public void cpImportInvestor(InvestorDetails investorDetails, String importCSVFilePath) throws IOException {

        startTestLevel("Updating csv file with random email");
        String investorEmail = investorDetails.getEmail();
        CSVUtils importInvestorFile = new CSVUtils(new File("src/main/resources/investorMetaData/import_investor.csv"));
        importInvestorFile.updateCSVInvestorEmailByIndex(1, "Email", investorEmail, importCSVFilePath);
        String absoluteCSVImportFilePath = new File("tempImportFile.csv").getAbsolutePath();
        endTestLevel();

        startTestLevel("Navigate to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Import Investor");
        CpImportInvestors importInvestorsPage = onBoarding.clickImportInvestor();
        importInvestorsPage.uploadFile(absoluteCSVImportFilePath)
                .clickImportInvestorButton();
        endTestLevel();

        startTestLevel("Filtering by investor's email");
        waitForInvestorToBeFound(investorDetails.getEmail());
        endTestLevel();
    }

    public void cpImportMultipleInvestor(InvestorDetails firstInvestorDetails, InvestorDetails secondInvestorDetails, InvestorDetails thirdInvestorDetails, InvestorDetails fourthInvestorDetails, String importCSVFilePath) throws IOException, InterruptedException, CsvException {

        startTestLevel("Updating csv file with random emails");
        CSVUtils importInvestorFile = new CSVUtils(new File("src/main/resources/investorMetaData/import_multiple_investor.csv"));
        String firstInvestorEmail = firstInvestorDetails.getEmail();
        String secondInvestorEmail = secondInvestorDetails.getEmail();
        String thirdInvestorEmail = thirdInvestorDetails.getEmail();
        String fourthInvestorEmail = fourthInvestorDetails.getEmail();
        importInvestorFile.updateMultipleInvestorEmail("Email", firstInvestorEmail, secondInvestorEmail, thirdInvestorEmail, fourthInvestorEmail, importCSVFilePath);
        String absoluteCSVImportFilePath = new File("tempImportMultipleFile.csv").getAbsolutePath();
        endTestLevel();

        startTestLevel("Navigate to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Import Investor");
        CpImportInvestors importInvestorsPage = onBoarding.clickImportInvestor();
        importInvestorsPage.uploadFile(absoluteCSVImportFilePath)
                .clickImportInvestorButton();
        endTestLevel();
    }

    public void cpImportBulkIssuanceInvestors(String importCSVFilePath, int amountOfInvestors, String lastName) {

        startTestLevel("Updating csv file with random emails and a single random last name");
        SoftAssert softAssertion = new SoftAssert();
        CSVUtils importInvestorFile = new CSVUtils(new File("src/main/resources/investorMetaData/import_bulk_issuance.csv"));
        importInvestorFile.updateCsvFileForBulkIssuance("Email", "Last Name", amountOfInvestors, importCSVFilePath, lastName);
        String absoluteCSVImportFilePath = new File("tempImportBulkIssuanceFile.csv").getAbsolutePath();
        endTestLevel();

        startTestLevel("Navigate to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Import Investor");
        CpImportInvestors importInvestorsPage = onBoarding.clickImportInvestor();
        importInvestorsPage.uploadFile(absoluteCSVImportFilePath)
                .clickImportAndCloseButton();
        endTestLevel();

        startTestLevel("Verify Bulk Import was succesful");
        getBrowser().refreshPage();
        onBoarding.searchInvestorByTextBox(lastName);
        onBoarding.waitForTableToCountRowsByCellDetail(amountOfInvestors, 360, "AUT519");
        endTestLevel();

        startTestLevel("Selecting investor for edit last name");
        onBoarding.clickOnInvestorByIndex(0);
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        endTestLevel();

        startTestLevel("Editing investor's last name for future tbe validation");
        investorDetailsPage.waitForEditButtonToBeVisible()
                .clickEdit()
                .typeLastName(lastName + "tbe")
                .typePhoneNumber("123456")
                .clickSaveChanges();
        endTestLevel();

        startTestLevel("Go back to Onboarding screen");
        sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Selecting an investor for adding a wallet");
        onBoarding.searchInvestorByTextBox(lastName);
        onBoarding.waitForTableToCountRowsByCellDetail(amountOfInvestors, 180, "AUT519");
        onBoarding.clickOnInvestorByIndex(5);
        endTestLevel();

        startTestLevel("Editing investor's last name for future selection");
        investorDetailsPage.waitForEditButtonToBeVisible()
                .clickEdit()
                .typeLastName(lastName + "wallet")
                .typePhoneNumber("123456")
                .clickSaveChanges();
        endTestLevel();
    }

    public void cpExportList(String investorFilterName, String nonValidSearch) {
        startTestLevel("Navigate to Onboarding screen");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();

        startTestLevel("Filtering by investor's middlename");
        // first let's filter on something that will empty the table
        onBoarding.searchInvestorByTextBox(nonValidSearch);
        onBoarding.waitForTableToBeEmpty();
        onBoarding.searchInvestorByTextBox(investorFilterName);
        onBoarding.waitForTableToBeNotEmpty();
        endTestLevel();

        startTestLevel("Download Onboarding export list");
        getBrowser().refreshPage();
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        onBoarding.clickExportList();
        // give the download several seconds to start - we don't want to switch to the downloads tab too fast
        // but we don't have an event indicating a download has started...
        getBrowser().sleep(3000);
        endTestLevel();

        startTestLevel("Open Onboarding downloaded file");
        CSVUtils onboardingExportList = null;
        try {
            String downloadedCsvContent = getLastDownloadedInvestorFileContent();
            Files.write(Paths.get("tempFile.csv"), downloadedCsvContent.getBytes());
            onboardingExportList = new CSVUtils(new File("tempFile.csv"));
        } catch (IOException e) {
            errorMessage = "Error parsing data: " + e.getMessage();
            errorAndStop(errorMessage, true);
        } catch (NullPointerException e) {
            errorMessage = "Error creating file: " + e.getMessage();
            errorAndStop(errorMessage, true);
        }
        endTestLevel();

        startTestLevel("Filter by exported investor on CSV");
        onBoarding.waitForTableToBeNotEmpty();
        onBoarding.searchInvestorByTextBox(onboardingExportList.getCSVInvestorDetailByIndex(1, "First Name"));
        endTestLevel();

        startTestLevel("Verify Onboarding names");
        SoftAssertions softAssertions = new SoftAssertions();
        String firstRowName = onBoarding.getInvestorDetailByIndex(0, "Name / Entity");
        //validate First Name
        softAssertions.assertThat(firstRowName)
                .as("Validate First name value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "First Name"));
        //validate Middle Name
        softAssertions.assertThat(firstRowName)
                .as("Validate Middle name value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Middle Name"));
        //validate Last Name
        softAssertions.assertThat(firstRowName)
                .as("Validate Last name value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Last Name"));
        endTestLevel();

        startTestLevel("Verify Onboarding country codes");
        String firstRowCountry = onBoarding.getInvestorDetailByIndex(0, "Country");
        firstRowCountry = RegexWrapper.trimUSCountry(firstRowCountry);
        String firstRowCountryCode = CountryCode.findByName(firstRowCountry)
                .get(0)
                .name();
        //validate Country Code
        softAssertions.assertThat(firstRowCountryCode)
                .as("Validate Country code value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Tax Country Code"));
        endTestLevel();

        startTestLevel("Verify Onboarding investor type");
        String firstRowInvestorType = onBoarding.getInvestorDetailByIndex(0, "Type")
                .toLowerCase();
        firstRowInvestorType = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowInvestorType, "-");
        //validate Investor Type
        softAssertions.assertThat(firstRowInvestorType)
                .as("Validate Investor type")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Investor Type"));
        endTestLevel();

        startTestLevel("Verify SecuritizeID exists");
        String firstRowSecuritizeID = onBoarding.getInvestorDetailByIndex(0, "SecuritizeID")
                .toLowerCase();
        //validate Securitize ID exists
        softAssertions.assertThat(firstRowSecuritizeID)
                .as("Validate SecuritizeID value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "SecuritizeID"));
        endTestLevel();

        startTestLevel("Verify KYC Status");
        String firstRowKYCStatus = onBoarding.getInvestorDetailByIndex(0, "KYC Status");
        firstRowKYCStatus = RegexWrapper.replaceWhiteSpaceForCharacter(firstRowKYCStatus, "-");
        //validate KYC Status
        softAssertions.assertThat(firstRowKYCStatus)
                .as("Validate KYC Status value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "KYC Status"));
        endTestLevel();

        startTestLevel("Verify Accreditation Status");
        String firstRowAccreditationStatus = onBoarding.getInvestorDetailByIndex(0, "Accredited Status");
        //validate Accredited Status - with tolerance to minor changes such as "no accepted" vs "no_accepted"
        String expectedValue = onboardingExportList.getCSVInvestorDetailByIndex(1, "Accreditation Status");
        String expectedValue2 = expectedValue.replace("_", " ");
        softAssertions.assertThat(firstRowAccreditationStatus)
                .as("Validate Accreditation Status value")
                .containsAnyOf(expectedValue, expectedValue2);
        endTestLevel();

        startTestLevel("Verify Labels");
        String firstRowLabels = onBoarding.getInvestorDetailByIndex(0, "Labels");
        firstRowLabels = RegexWrapper.blankIfNull(firstRowLabels);
        //validate Labels
        softAssertions.assertThat(firstRowLabels)
                .as("Validate labels value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Labels"));
        endTestLevel();

        startTestLevel("Verify Registration Date");
        String firstRowRegistrationDate = onBoarding.getInvestorDetailByIndex(0, "Created");
        String reformattedRegistrationDate = DateTimeUtils.convertDateFormat(firstRowRegistrationDate, "MMM d, yyyy", "MM/dd/yyyy") + " (UTC)";
        //validate Registration Date
        softAssertions.assertThat(reformattedRegistrationDate)
                .as("Registration date value")
                .contains(onboardingExportList.getCSVInvestorDetailByIndex(1, "Registration Date"));
        softAssertions.assertAll();
        endTestLevel();
    }

    private void waitForInvestorToBeFound(String investorEmail) {
        Function<String, Boolean> internalWaitForInvestorToBeFound = t -> {
            try {
                CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
                onBoarding.searchUniqueInvestorWithTimeout(investorEmail, 10);
                return true;
            } catch (TimeoutException e) {
                getBrowser().refreshPage();
                return false;
            }
        };

        String description = "waitForInvestorToBeFound: " + investorEmail;
        Browser.waitForExpressionToEqual(internalWaitForInvestorToBeFound, null, true, description, 120, 10000);
    }

}
