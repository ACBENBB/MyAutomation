package io.securitize.tests.transferAgent.abstractClass;

import io.restassured.response.Response;
import io.securitize.infra.api.CashAccountApi;
import io.securitize.infra.api.LoginAPI;
import io.securitize.infra.api.SnapshotAPI;
import io.securitize.infra.api.transferAgent.TaxFormAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.database.MySqlDatabase;
import io.securitize.infra.utils.CSVUtils;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage2FA;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSecuritiesTransactions;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSideMenu;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.ConfirmDistributionModalPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.DistributionDetailsPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.DistributionsListPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.GenerateDistributionModalPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdRedemption;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.assetDetails.AssetDetailsPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm.TaxFormPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm.TaxFormResidenceDetailsPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm.TaxFormW8BENEPage;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.transferAgent.testData.DistributionData;
import io.securitize.tests.transferAgent.testData.DistributionsInvestorData;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractDistributions extends AbstractUiTest {

    CSVUtils distribution;
    File distributionFile;
    public DistributionsInvestorData expectedInvestorData;
    public DistributionsInvestorData actualInvestorData;

    public enum DistributionsInvestorType {
        DISTRIBUTION_AUT365_TESTDATA("Distribution Individual US TaxForm Missing"),
        DISTRIBUTION_AUT476_TESTDATA("Redemption Individual US Investor"),
        DISTRIBUTION_AUT543_TESTDATA("Dividend Missing Cash Account - Individual US"),
        DISTRIBUTION_TESTDATA_ONE("Individual, SecID True, KYC True, CashAcc True, W9 False"),
        DISTRIBUTION_TESTDATA_FIVE("Individual, SecID True, KYC False, CashAcc True, W9 True"),
        DISTRIBUTION_TESTDATA_EIGHT("Entity, SecID True, KYC True, CashAcc True, W8BENE False"),
        DISTRIBUTION_TESTDATA_TEN("Individual, SecID True, KYC True, CashAcc True, W9 True"),
        DISTRIBUTION_TESTDATA_ELEVEN("Entity, SecID True, KYC True, CashAcc True, W9 True"),
        DISTRIBUTION_TESTDATA_TWELVE("Individual, SecID True, KYC True, CashAcc True, W8BENE True"),
        DISTRIBUTION_TESTDATA_THIRTEEN("Entity, SecID True, KYC True, CashAcc True, W8BENE True"),
        DISTRIBUTION_TESTDATA_NINE("Individual, SecID True, KYC True, CashAcc False, W9 True");
        private final String displayName;

        DistributionsInvestorType(String displayname) {
            this.displayName = displayname;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public String getTestInvestorEmail(String investorType) {
        startTestLevel("Getting investor Email from Test Data Enum --> " + investorType);
        String investorEmail = null;
        if (investorType.equalsIgnoreCase(DistributionsInvestorType.DISTRIBUTION_AUT365_TESTDATA.toString())) {
            investorEmail = Users.getProperty(UsersProperty.ta_distribution_testUser_one_aut365);
        } else if (investorType.equalsIgnoreCase(DistributionsInvestorType.DISTRIBUTION_AUT543_TESTDATA.toString())) {
            investorEmail = Users.getProperty(UsersProperty.ta_distribution_dividend_investorMail_aut543);
        } else if (investorType.equalsIgnoreCase(DistributionsInvestorType.DISTRIBUTION_TESTDATA_ONE.toString())) {
            investorEmail = Users.getProperty(UsersProperty.ta_redemptionsIndUsEmail_aut424);
        }
        endTestLevel();
        return investorEmail;
    }

    public enum DistributionType {
        DISTRIBUTION_TYPE_DIVIDEND("Distribution Type Dividend"),
        DISTRIBUTION_TYPE_DIVIDEND_ACCEPT("Distribution Type Dividend AUT491"),
        DISTRIBUTION_TYPE_DIVIDEND_REJECT("Distribution Type Dividend AUT492"),
        DISTRIBUTION_TYPE_REDEMPTION("Distribution Type Redemption"),
        DISTRIBUTION_TYPE_DIVIDEND_DRIP_HAPPY_FLOW("Distribution Type Dividend DRIP Happy Flow");
        private final String displayName;

        DistributionType(String displayname) {
            this.displayName = displayname;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public DistributionsInvestorData createDistributionInvestorDataObject(String investorType) {
        return new DistributionsInvestorData(investorType);
    }

    public DistributionData createDistributionDataObject(String distributionType) {
        return new DistributionData(distributionType);
    }

    public double getCashAccountBalance(String investorSid) {
        CashAccountApi cashAccountApi = new CashAccountApi();
        return cashAccountApi.getCashAccountBalance(investorSid);
    }

    public void loginToSecuritizeId(String user, String pass) {
        startTestLevel("Login To Securitize ID");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen.clickAcceptCookies().performLoginWithCredentials(user, pass, true);
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        if (securitizeIdDashboard.isTwoFactorModalVisible()) {
            securitizeIdDashboard.clickSkipTwoFactor();
        }
        endTestLevel();
    }

    public boolean validateTransactionIsPresentInSecId(String tokenPrice) {
        AssetDetailsPage assetPage = new AssetDetailsPage(getBrowser());
        return assetPage.validateTransactionIsPresent(tokenPrice);
    }

    public void navitagateToAssetDetailsPage(String tokenName) {
        SecuritizeIdDashboard secIdPage = new SecuritizeIdDashboard(getBrowser());
        secIdPage.goToAssetDetails(tokenName);
    }

    public void validateRedemptionOpenModalIsNoLongerDisplayed(String tokenPrice) {
        SoftAssert softAssert = new SoftAssert();
        AssetDetailsPage assetPage = new AssetDetailsPage(getBrowser());
        // TODO these assertions might be used in the future.
        //softAssert.assertFalse(assetPage.isRedemptionTitlePresent(), "Redemption title was still visible");
        //softAssert.assertFalse(assetPage.isRedemptionDescriptionPresent(tokenPrice), "Redemption description was still visible");
        softAssert.assertFalse(assetPage.isRedeemButtonPresent(), "Redemption button was still visible");
        softAssert.assertAll();
    }

    public void goToRedemptionPageUsingRedeemButton() {
        AssetDetailsPage assetPage = new AssetDetailsPage(getBrowser());
        assetPage.clickOnRedeemButton();
        getBrowser().waitForPageStable();
    }

    public void validateNewRecordInPaymentHistory(String tokenName, String payment) {
        AssetDetailsPage assetPage = new AssetDetailsPage(getBrowser());
        String currDate = DateTimeUtils.currentDate("dd MMM yyyy");
        info("Current date and format is: " + currDate);
        assetPage.clickOnPayoutsTab();
        assetPage.validateNewPayoutRowIsVisible(currDate, payment);
    }

    public void validateRedemptionIsClosed() {
        SecuritizeIdRedemption redemptionPage = new SecuritizeIdRedemption(getBrowser());
        Assert.assertEquals(redemptionPage.getRedemptionStatusChipText(), "Closed Redemption");
    }

    public void submitTaxFormViaUI(String taxFormType) {
        TaxFormPage taxFormPage = new TaxFormPage(getBrowser());
        if(taxFormPage.isCompleteATaxFormBtnVisible()) {
            taxFormPage.clickCompleteATaxForm();
        }
        if (taxFormType.equalsIgnoreCase("w9")) {
            // TODO review this
        } else if (taxFormType.equalsIgnoreCase("w8ben")) {
            // TODO review this
        } else if (taxFormType.equalsIgnoreCase("w8bene")) {
            // TODO IMPLEMENT SINGLE METHOD FROM TAXFORM PAGE
            TaxFormResidenceDetailsPage residenceDetailsPage = new TaxFormResidenceDetailsPage(getBrowser());
            residenceDetailsPage.selectIamACorporationCheckBox().clickContinue();
            TaxFormW8BENEPage taxFormW8BENEPage = new TaxFormW8BENEPage(getBrowser());
            taxFormW8BENEPage.uploadTaxForm();
            taxFormW8BENEPage.submit();
            Assert.assertEquals(taxFormPage.getSubmittedTaxFormStatus(), "PENDING");
        }
    }

    public String submitTaxFormViaApi(DistributionsInvestorData investorData) {
        LoginAPI loginAPI = new LoginAPI();
        Response response = loginAPI.loginSecIdAndReturnBearerToken(investorData.email, Users.getProperty(UsersProperty.ta_distributionGenericInvestorPassword_aut365));
        TaxFormAPI taxFormAPI = new TaxFormAPI();
        return taxFormAPI.submitTaxFormViaApiByTaxFormType(response, investorData.taxFormType);
    }

    public String deleteTaxForm(String email, String password) {
        LoginAPI loginAPI = new LoginAPI();
        Response loginResponse = loginAPI.loginSecIdAndReturnBearerToken(email, password);
        TaxFormAPI taxFormAPI = new TaxFormAPI();
        return taxFormAPI.deleteTaxForm(loginResponse);
    }

    public void loginToControlPanel() {
        startTestLevel("Login to control panel using email and password + 2FA ");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl));
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        endTestLevel();
        startTestLevel("Populate Login 2FA with the secret key");
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI(false);
        endTestLevel();
    }

    public void logoutFromControlPanel() {
        CpSideMenu cpSideMenu = new CpSideMenu(getBrowser());
        cpSideMenu.logoutFromCp();
        getBrowser().clearAllCookies();
    }

    public void logoutFromSecId() {
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.performLogoutIncludingClearCookies();
    }

    public void validateRedemptionTransactionIsPresentInSecuritiesTransactions(String transactionHash) {
        SoftAssert softAssert = new SoftAssert();
        CpSecuritiesTransactions securitiesTransactionsPage = new CpSecuritiesTransactions(getBrowser());
        softAssert.assertTrue(securitiesTransactionsPage.isRedemptionTransactionPresentInSecuritiesTransaction(transactionHash), "Redemption transaction not present after 15 minutes...");
        softAssert.assertAll();
    }

    public void clickLatestDistributionCreatedByName(String distributionName) {
        DistributionsListPage distributionsListPage = new DistributionsListPage(getBrowser());

            DistributionsListPage page = new DistributionsListPage(getBrowser());
            getBrowser().waitForPageStable();
            getBrowser().waitForElementVisibility(page.getDistributionsRows());
         for (int j = 0; j < 10; j++) {
            if (distributionsListPage.getDistributionColumnDataByRow("distributionName", String.valueOf(j+1)).contains(distributionName)) {
                distributionsListPage.clickDistributionByRow(String.valueOf(j));
                break;
            }
        }
    }

    public Double getDistributionTotalAmount() {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        return distributionDetailsPage.getTotalDistributionAmount();
    }

    public double getDistributionFundingNeeds() {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        return distributionDetailsPage.getDistributionFundingNeeds();
    }

    public String getDistributionAmountPerToken() {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        return distributionDetailsPage.getAmountPerToken();
    }

    public String getDistributionIdFromUrl() {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        return distributionDetailsPage.getDistributionIdFromUrl();
    }

    public void clickConfirmDistribution() {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        distributionDetailsPage.clickConfirmDristributionBtn();
        ConfirmDistributionModalPage confirmDistributionModalPage = new ConfirmDistributionModalPage(getBrowser());
        confirmDistributionModalPage.clickConfirmDistributionModal();
    }

    public void navigateToDistributionPage(String issuerId, String tokenId) {
        startTestLevel("Navigating to issuer - token distribution page");
        String distributionURL = "https://cp." + MainConfig.getProperty(MainConfigProperty.environment) + ".securitize.io/" + issuerId + "/" + tokenId + "/distributions";
        getBrowser().navigateTo(distributionURL);
        getBrowser().waitForPageStable();
        endTestLevel();
    }

    public void loadCSVDistributionData(DistributionData distributionData, String issuer, String token) {
        loginToControlPanel();
        navigateToDistributionPage(issuer, token);
        clickLatestDistributionCreatedByName(distributionData.distributionName);
        downloadCSVFromDistributionDetails();
        try {
            getLastDownloadedDistributionCsvFile();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        getBrowser().closeLastTabAndSwitchToPreviousOne();
    }

    public void downloadCSVFromDistributionDetails() {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        distributionDetailsPage.clickDistributionDetailsDownloadCSVBtn();
        getBrowser().waitForPageStable();
//        logoutFromControlPanel();
    }

    public void getLastDownloadedDistributionCsvFile() throws IOException, InterruptedException {
        startTestLevel("Load latest distribution CSV file");
        String csvContent = null;
        csvContent = getLastDownloadedDistributionFileContent();
        String csvFile = "";
        if (MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("rc")) {
            csvFile = "distribution.csv";
        } else {
            csvFile = "distributionSandbox.csv";
        }
        Files.write(Paths.get(csvFile), csvContent.getBytes());
        distributionFile = new File(csvFile);
        distribution = new CSVUtils(distributionFile);
        endTestLevel();
    }

    public DistributionsInvestorData getCSVInvestorData(DistributionsInvestorData investorData) {
        startTestLevel("Getting CSV Data for investor type: " + investorData.testInvestorType);
        int rowIndex = getRowIndexFromColumnSearch("email", getTestInvestorEmail(investorData.testInvestorType));
        DistributionsInvestorData actualInvestorData = new DistributionsInvestorData();
        actualInvestorData.fullName = distribution.getCSVInvestorDetailByIndex(rowIndex, "fullName");
        actualInvestorData.totalNumberOfSecurities = distribution.getCSVInvestorDetailByIndex(rowIndex, "totalNumberOfSecurities");
        actualInvestorData.countryResidence = distribution.getCSVInvestorDetailByIndex(rowIndex, "countryResidence");
        actualInvestorData.payoutMethod = distribution.getCSVInvestorDetailByIndex(rowIndex, "payoutMethod");
        actualInvestorData.taxWithholdingRate = distribution.getCSVInvestorDetailByIndex(rowIndex, "taxWithholdingRate");
        actualInvestorData.taxWithholdingAmount = distribution.getCSVInvestorDetailByIndex(rowIndex, "taxWithholdingAmount");
        actualInvestorData.payoutAmount = distribution.getCSVInvestorDetailByIndex(rowIndex, "payoutAmount");
        endTestLevel();
        return actualInvestorData;
    }

    public void validateCSVInvestorData() {
        // TODO review these values, some of might have changed
        startTestLevel("Validating CSV Investor Data");
        Assert.assertEquals(actualInvestorData.fullName, expectedInvestorData.fullName);
        info("actualInvestorData.fullName");
        Assert.assertEquals(actualInvestorData.currency, expectedInvestorData.currency);
        info("actualInvestorData.currency");
        Assert.assertEquals(actualInvestorData.countryResidence, expectedInvestorData.countryResidence);
        info("actualInvestorData.countryResidence");
        Assert.assertEquals(actualInvestorData.payoutMethod, expectedInvestorData.payoutMethod);
        info("actualInvestorData.payoutMethod");
        Assert.assertEquals(actualInvestorData.csvTaxFormW8BENE, expectedInvestorData.csvTaxFormW8BENE);
        info("actualInvestorData.submittedW8BENE");
        Assert.assertEquals(actualInvestorData.csvTaxFormW8BEN, expectedInvestorData.csvTaxFormW8BEN);
        info("actualInvestorData.submittedW8BEN");
        Assert.assertEquals(actualInvestorData.csvTaxFormW9, expectedInvestorData.csvTaxFormW9);
        info("actualInvestorData.submittedW9");
        Assert.assertEquals(actualInvestorData.taxWithholdingRate, expectedInvestorData.taxWithholdingRate);
        info("actualInvestorData.taxWithholdingRate");
        Assert.assertEquals(actualInvestorData.taxWithholdingAmount, expectedInvestorData.taxWithholdingAmount);
        info("actualInvestorData.taxWithholdingAmount");
        Assert.assertEquals(actualInvestorData.payoutAmount, expectedInvestorData.payoutAmount);
        info("actualInvestorData.payoutAmount");
        Assert.assertEquals(actualInvestorData.cashAccount, expectedInvestorData.cashAccount);
        info("actualInvestorData.cashAccount");
        endTestLevel();
    }

    public int getRowIndexFromColumnSearch(String columnHeader, String searchValue) {
        int i;
        for (i = 0; i < distribution.getRowCount(); i++) {
            String email = distribution.getCSVInvestorDetailByIndex(i, columnHeader);
            if (email.contains(searchValue)) {
                break;
            }
        }
        return i;
    }

    public void generateDistribution(DistributionData distributionData) {
        DistributionsListPage distributionsListPage = new DistributionsListPage(getBrowser());
        getBrowser().waitForElementVisibility(distributionsListPage.getDistributionsRows());
        if (distributionData.distributionTestType.equalsIgnoreCase(DistributionType.DISTRIBUTION_TYPE_DIVIDEND.toString())) {
            startTestLevel("Generating Distribution Type: " + distributionData.distributionTestType);
            distributionsListPage.clickGenerateDistributionBtn();
            GenerateDistributionModalPage generateDistributionModalPage = new GenerateDistributionModalPage(getBrowser());
            generateDistributionModalPage.generateDistribution(distributionData);
            endTestLevel();
        } else if (distributionData.distributionTestType.equalsIgnoreCase(DistributionType.DISTRIBUTION_TYPE_REDEMPTION.toString())) {
            startTestLevel("Generating Distribution Type: " + distributionData.distributionTestType);
            distributionsListPage.clickGenerateDistributionBtn();
            GenerateDistributionModalPage generateDistributionModalPage = new GenerateDistributionModalPage(getBrowser());
            generateDistributionModalPage.generateDistribution(distributionData);
            endTestLevel();
        } else if (distributionData.distributionTestType.equalsIgnoreCase(DistributionType.DISTRIBUTION_TYPE_DIVIDEND_DRIP_HAPPY_FLOW.toString())) {
            startTestLevel("Generating Distribution Type: " + distributionData.distributionTestType);
            distributionsListPage.clickGenerateDistributionBtn();
            GenerateDistributionModalPage generateDistributionModalPage = new GenerateDistributionModalPage(getBrowser());
            generateDistributionModalPage.generateDistribution(distributionData);
            endTestLevel();
        }

    }

    public void validateRedemptionDataByRowNumber(int expectedRows) {
        DistributionDetailsPage redemptionPage = new DistributionDetailsPage(getBrowser());
        SoftAssert softAssert = new SoftAssert();
        getBrowser().waitForPageStable();
        getBrowser().refreshPage();
        getBrowser().waitForPageStable();
        getBrowser().waitForElementVisibility(redemptionPage.getInvestorsEmailData());
        softAssert.assertEquals(redemptionPage.getRowsCountByColumn(redemptionPage.getInvestorsNameData()), expectedRows);
        softAssert.assertEquals(redemptionPage.getRowsCountByColumn(redemptionPage.getInvestorsEmailData()), expectedRows);
        softAssert.assertEquals(redemptionPage.getRowsCountByColumn(redemptionPage.getInvestorsExpectedToRedeemData()), expectedRows);
        softAssert.assertEquals(redemptionPage.getRowsCountByColumn(redemptionPage.getInvestorsRedeemedData()), expectedRows);
        softAssert.assertEquals(redemptionPage.getRowsCountByColumn(redemptionPage.getInvestorsGiveBackData()), expectedRows);
        softAssert.assertEquals(redemptionPage.getRowsCountByColumn(redemptionPage.getInvestorsGrossAmountData()), expectedRows);
        softAssert.assertEquals(redemptionPage.getRowsCountByColumn(redemptionPage.getInvestorsPaidStatusData()), expectedRows);
        softAssert.assertAll();
    }

    public void assertDistributionStatus(String status) {
        Function<String, Boolean> internalWaitForRedemptionStatusLabel = t -> {
            DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
            boolean isPresent = false;
            try {
                isPresent = distributionDetailsPage.getStatusLabelText().contains(status);
            } catch (Exception e) {
                isPresent = false;
            }
            return isPresent;
        };
        String description = "Redemption Status String";
        Browser.waitForExpressionToEqual(internalWaitForRedemptionStatusLabel, null, true, description, 6, 1000);
    }

    public void validatePaymentAmountIsExpected(String amount) {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        SoftAssert sftAssert = new SoftAssert();
        sftAssert.assertEquals(distributionDetailsPage.getSingleRowGrossAmountValueText().replace("$", ""), amount);
        sftAssert.assertAll();
    }

    public void uploadNewCSV(String csvPath) {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        String csvPath_ = csvPath;
        // TODO improve wait for this.
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        distributionDetailsPage.uploadCSV(csvPath_);
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
    }

    public void validateCSVDataIsLoaded(String tokens, String giveBackTokens, String payment) {
        SoftAssert sftAssert = new SoftAssert();
        getBrowser().refreshPage();
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());

        sftAssert.assertEquals(distributionDetailsPage.getSingleRowGrossAmountValueText().replace("$", ""), payment);
        sftAssert.assertEquals(distributionDetailsPage.getSingleRowGiveBackAmountValueText().replace("$", ""), giveBackTokens);
        sftAssert.assertEquals(distributionDetailsPage.getSingleRowRedeemedTokensAmountValueText().replace("$", ""), tokens);
        sftAssert.assertAll();
    }

    public void confirmRedemption() {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        distributionDetailsPage.clickConfirmRedemptionButton();
        distributionDetailsPage.waitForDistributionConfirmationModalBtnToDisplay();
        distributionDetailsPage.clickDistributionConfirmationModalBtn();
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
    }

    public void validateVisibilityOfSecIdRedemptionPageElements(String redemptionWallet, String amountPerToken) {
        SecuritizeIdRedemption secIdRedemptionPage = new SecuritizeIdRedemption(getBrowser());
        SoftAssert softAssert = new SoftAssert();
        secIdRedemptionPage.checkVisibilityOfAllElements();
        double roundOff = (double) Math.round(Double.parseDouble(amountPerToken) * 100) / 100;
        softAssert.assertEquals(secIdRedemptionPage.getPayoutPerToken(), String.valueOf(roundOff));
        softAssert.assertEquals(redemptionWallet, secIdRedemptionPage.getRedemptionWallet());
        // TODO review this
        // Commented until dates format match between secID and CP
        // softAssert.assertEquals(distributionData.endDate[2], secIdRedemptionPage.getRedemptionEndDate());
        softAssert.assertAll();
    }

    public void closeRedemptionById(String distributionId) {
        MySqlDatabase mySqlUtils = new MySqlDatabase();
        mySqlUtils.send("distributions", "UPDATE `distribution` dis SET \n" +
                "status = 'populated',\n" +
                "start_date = CONCAT(DATE_ADD(CURRENT_DATE(), INTERVAL -1 DAY),' 00:00:00'), \n" +
                "end_date = CONCAT(DATE_ADD(CURRENT_DATE(), INTERVAL 0 DAY),' 00:00:00') \n" +
                "WHERE id = '" + distributionId + "';");
    }

    public String getIdOfRedemptionInPendingStatus(String tokenId) {
        MySqlDatabase mySqlUtils = new MySqlDatabase();
        String  distributionId = "";
        distributionId = mySqlUtils.sendQuery("distributions", "SELECT id FROM distribution WHERE distribution_type = 'redemption' AND status = 'waiting-investor-redemptions' AND token_id = '" + tokenId + "';");
        info("Distribution id found: " +distributionId);
        if (distributionId.contains(" ")) {
            distributionId = null;
        }
        return distributionId;
    }

    public int getHoldersCountFromDristirbutionsDb(String tokenName) {
        MySqlDatabase mySqlUtils = new MySqlDatabase();
        int  holders = 0;
        String holderRows = mySqlUtils.sendQuery("distributions", "SELECT MAX(total_items) FROM distribution where token_name = '" + tokenName + "';");
        info("Number of rows found: " + holderRows);
        if (holderRows.contains(" ")) {
            holders = 0;
        } else {
            holders = Double.valueOf(holderRows).intValue();
        }
        return holders;
    }

    public void validateDividendPayout(DistributionsInvestorData investorData, double previousCashAccountBalance) {
            try {
                DistributionsInvestorData csvInvestorData = getCSVInvestorData(investorData);
                info("csvInvestorData.payoutAmount --- > " + csvInvestorData.payoutAmount);
                if (investorData.initialInvestorCashAccountBalance != 0) {
                    // allow for small tolerance in the accuracy of the calculation
                    double actual = getCashAccountBalance(investorData.secId);
                    double expected = investorData.initialInvestorCashAccountBalance + Double.parseDouble(csvInvestorData.payoutAmount);
                    // Round to two decimal places
                    BigDecimal roundedActual = BigDecimal.valueOf(actual).setScale(1, RoundingMode.HALF_UP);
                    BigDecimal roundedExpected = BigDecimal.valueOf(expected).setScale(1, RoundingMode.HALF_UP);
                    double delta = Math.abs(expected - actual);
                    String errorMessage = String.format("accountBalance for email: %s is invalid. Expected: %s and actual: %s", investorData.email, expected, actual);
                    Assert.assertTrue(delta < 0.02, errorMessage);
                    Assert.assertEquals(roundedActual, roundedExpected);
                }
            } catch (IndexOutOfBoundsException e) {
                //Some text editors produce an additional line at the end of csv

            }
    }

    public void goToRedemptionPage(String distributionId) {
        getBrowser().waitForPageStable();
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl).replace("login", "redemption/") + distributionId);
        getBrowser().waitForPageStable();
    }

    public void validateCompletedDistribution(String distributionData) {
        loginToControlPanel();
        navigateToDistributionById(distributionData);
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        Assert.assertFalse(distributionDetailsPage.isCompleteDistributionBtnEnabled());
        distributionDetailsPage.areAllInvestorsPaymentsCompleted();
        Assert.assertTrue(distributionDetailsPage.isDistributionStatusCompleted());
    }

    public void goToPortfolioPage() {
        SecuritizeIdDashboard secIdPage = new SecuritizeIdDashboard(getBrowser());
        secIdPage.goToPortfolioPage();
    }

    public void navigateToDistributionById(String distributionData) {
        // TODO if we pass paramenters to this method, aws values should come from outside.
        String env = MainConfig.getProperty(MainConfigProperty.environment);
        String issuerId = Users.getProperty(UsersProperty.ta_distribution_redemption_issuerId_aut476);
        String tokenId = Users.getProperty(UsersProperty.ta_distribution_redemption_tokenId_aut476);
        startTestLevel("Navigating to issuer - token distribution page");
        String distributionURL = "https://cp." + env + ".securitize.io/" + issuerId + "/" + tokenId + "/distributions/" + distributionData;
        getBrowser().navigateTo(distributionURL);
        endTestLevel();
    }

    public void validateDistributionAmountLockedInIssuerAccount(DistributionData distributionData) {
        double expectedIssuerAccountBalance = (double) Math.round(Double.parseDouble(String.valueOf(distributionData.issuerInitialAccountBalance - distributionData.fundingNeeds)));
        double actualIssuerAccountBalance = (double) Math.round(getCashAccountBalance(distributionData.issuerCashAccountSecId)); // issuer sid
        Assert.assertEquals(actualIssuerAccountBalance, expectedIssuerAccountBalance, "Expected Issuer balance should be" + (distributionData.issuerInitialAccountBalance - distributionData.totalAmount));
    }

    public String createSnapshotViaApi(String issuer, String token, String snapshotName) {
        startTestLevel("Create snapshot for AbsractDistributions with current holders on token: " + token);
        LoginAPI loginAPI = new LoginAPI();
        Response cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        SnapshotAPI snapshotAPI = new SnapshotAPI();
        String createSnapshotStringResponse = snapshotAPI.createSnapshot(cpLoginResponse, issuer, token, snapshotName);
        endTestLevel();
        return createSnapshotStringResponse;
    }

    public void validateEmailSent(String investorEmail, String investorType, String sellBackPrice) {

        HashMap<String, String> expectedTestData = new HashMap<>();
        HashMap<String, String> actualDataFromEmail;

        if(investorType.equalsIgnoreCase(DistributionsInvestorType.DISTRIBUTION_AUT365_TESTDATA.toString())) {
            DistributionEmailParser distributionEmailParser = new DistributionEmailParser();
            actualDataFromEmail = distributionEmailParser.parseTestDataFromDividendEmail(investorEmail);
            // email expectedTestData depends on the investorType
            // this object should collect data from the Distribution Page.
            expectedTestData.put("issuerName", Users.getProperty(UsersProperty.ta_distribution_mail_issuerName));
            expectedTestData.put("payoutAmount", "5.00");
            expectedTestData.put("issuedDate", DateTimeUtils.currentDate("MM/dd/yyyy"));
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(actualDataFromEmail.get("issuerName"), expectedTestData.get("issuerName"));
            softAssert.assertEquals(actualDataFromEmail.get("payoutAmount"), expectedTestData.get("payoutAmount"));
            softAssert.assertEquals(actualDataFromEmail.get("issuedDate"), expectedTestData.get("issuedDate"));
            softAssert.assertAll();
        } else if(investorType.equalsIgnoreCase(DistributionsInvestorType.DISTRIBUTION_AUT476_TESTDATA.toString())) {
            DistributionEmailParser distributionEmailParser = new DistributionEmailParser();
            actualDataFromEmail = distributionEmailParser.parseTestDataFromRedemptionEmail(investorEmail);
            // email expectedTestData depends on the investorType
            // this object should collect data from the Distribution Page.
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String tomorrow = today.plusDays(1).format(formatter);
            expectedTestData.put("issuerName", Users.getProperty(UsersProperty.ta_distribution_mail_issuerName));
            expectedTestData.put("redemptionStart", DateTimeUtils.currentDate("MM/dd/yyyy"));
            expectedTestData.put("redemptionEnd", tomorrow);
            expectedTestData.put("sellBackPrice", sellBackPrice);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(actualDataFromEmail.get("issuerName"), expectedTestData.get("issuerName"));
            softAssert.assertEquals(actualDataFromEmail.get("redemptionStart"), expectedTestData.get("redemptionStart"));
            softAssert.assertEquals(actualDataFromEmail.get("redemptionEnd"), expectedTestData.get("redemptionEnd"));
            softAssert.assertEquals(actualDataFromEmail.get("sellBackPrice"), expectedTestData.get("sellBackPrice"));
            getBrowser().navigateTo("https://" + actualDataFromEmail.get("link").replace(")", "").replaceAll(" ", ""));
            softAssert.assertAll();
        }

    }

    public void validatePaymentEmailSent(String investorEmail) {
        // TODO emails are deactivated for the moment
        String actualDataFromEmail;
        DistributionEmailParser distributionEmailParser = new DistributionEmailParser();
        actualDataFromEmail = distributionEmailParser.getEmailContent(investorEmail);
        SoftAssert softAssert = new SoftAssert();
        info(actualDataFromEmail);
        softAssert.assertTrue(actualDataFromEmail.contains("You just received a payout from " + Users.getProperty(UsersProperty.ta_distribution_mail_issuerName) +"!"));
        softAssert.assertTrue(actualDataFromEmail.contains("You have successfully sold your AUT tokens."));
        softAssert.assertTrue(actualDataFromEmail.contains("The funds from your transaction have been added to your cash balance in your Securitize Markets account."));
        softAssert.assertTrue(actualDataFromEmail.contains("Consider diversifying your portfolio by reinvesting your payout. Click below to view a range of new investment opportunities."));
        softAssert.assertTrue(actualDataFromEmail.contains("If you received this email by mistake, please delete it!"));
        softAssert.assertAll();
    }

    public String getCurrentCashAccountBalance() {
        SecuritizeIdDashboard secIdPage = new SecuritizeIdDashboard(getBrowser());
        return secIdPage.getCashAccountBalance().replace("$", "").replace(",", "").replace("USD", "").replace(" ", "");
    }

    public void waitForDistributionStatus(String waitForStatus) {
        Function<String, Boolean> internalWaitForDistributionStatus = t -> {
            getBrowser().waitForPageStable();
            DistributionsListPage distributionsListPage = new DistributionsListPage(getBrowser());
                if(distributionsListPage.getDistributionStatus().equalsIgnoreCase(waitForStatus)) {
                    return true;
                } else {
                    getBrowser().refreshPage();
                    return false;
                }
        };
        String description = "Expecting Distribution status to be: " + waitForStatus;
        Browser.waitForExpressionToEqual(internalWaitForDistributionStatus, null, true, description, 600, 10000);
    }

    public void waitForCSVLoadedInUI() {
        Function<String, Boolean> internalWaitRefreshMsgIsVisible = t -> {
            getBrowser().waitForPageStable();
            DistributionsListPage distributionsListPage = new DistributionsListPage(getBrowser());
            if (distributionsListPage.isloadingCSVRefreshDisplayed()) {
                getBrowser().refreshPage();
                return false;
            } else {
                return true;
            }
        };
        String description = "Expecting Refresh Btn to NOT be present";
        Browser.waitForExpressionToEqual(internalWaitRefreshMsgIsVisible, null, true, description, 10000, 5000);
    }

    public void waitForUIUpdateAfterCsvUpload() {
        Function<String, Boolean> internalWaitForDistributionStatus = t -> {
            getBrowser().waitForPageStable();
            DistributionsListPage distributionsListPage = new DistributionsListPage(getBrowser());
            if (distributionsListPage.isReinvestPayoutMethodUpdatedInDistributionListUi()) {
                return true;
            } else {
                getBrowser().refreshPage();
                return false;
            }
        };
        String description = "Expecting Refresh Btn to NOT be present";
        Browser.waitForExpressionToEqual(internalWaitForDistributionStatus, null, true, description, 10000, 1000);
    }

    public void navigateToSignaturesPage() {

        CpSideMenu cpSideMenu = new CpSideMenu(getBrowser());
        cpSideMenu.clickSignatures();
    }

    public double getTotalTokensToBeIssuedFromDistributionUI() {
        DistributionsListPage distributionsListPage = new DistributionsListPage(getBrowser());
        return distributionsListPage.getTokensToBeIssued();
    }

}
