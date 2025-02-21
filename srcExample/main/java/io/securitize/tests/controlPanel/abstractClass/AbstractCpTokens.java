package io.securitize.tests.controlPanel.abstractClass;

import io.securitize.infra.config.*;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.exceptions.IssuanceNotSuccessTimeoutException;
import io.securitize.infra.exceptions.WalletNotReadyTimeoutException;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.EthereumUtils;
import io.securitize.infra.utils.RandomGenerator;
import io.securitize.infra.utils.SshRemoteExecutor;
import io.securitize.infra.wallets.EthereumWallet;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.tests.InvestorDetails;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.*;
import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractCpTokens extends AbstractCpInvestments {

    private static final int ISSUED_TOKENS = 5;
    private static final int TBE_ISSUED_TOKENS = 6;
    private static final int ISSUED_TOKENS_CB = 1;
    private static final int NUMBER_OF_WALLETS_LOST_SHARES = 2;
    private CpInvestorDetailsPageNewUI investorDetailsPageNewUI;
    private CpInvestorDetailsPage investorDetailsPage;
    private String investmentCurrency;
    private int issuanceCounter;
    private String tokenName;
    private int investmentAmount;
    private int tokensToBeProcedure;
    private int tokensCanBeProcedure;
    private int tokensTBE;
    private int tokensWallet;
    private String investorDirectUrl;
    private String investorExternalID;
    private String walletAddress;
    private String actualWalletAddress;
    private String walletName;
    private String firstName;
    private int tokensAfterProcedure;
    private int expectedTokensAfterProcedure;
    private int totalLockTokens;
    private String fullOrPartially;
    private String freezeType;
    private final String signatures = "signatures";
    private final String holders = "holders";
    private final String controlBook = "control book";
    private final String operationalProcedures = "operational procedures";
    private final String NON_VALID_SEARCH = "ThisInvestorShouldNotExist";

    public void cpAddIssuanceNewUI(String issueTo) {

        startTestLevel("Set issuance details");
        String issuanceDescription = "AUT issue " + issueTo;
        int tokenAmount = setTokensAmount(issueTo);
        endTestLevel();

        startTestLevel("Add " + issueTo + " issuance");
        cpAddIssuance(issueTo, tokenAmount, investmentCurrency, issuanceDescription);
        endTestLevel();

        startTestLevel("Validate issuance details");
        validateIssuanceDetails(issueTo, tokenAmount, issuanceDescription);
        endTestLevel();

        startTestLevel("Update issuance counter");
        issuanceCounter++;
        endTestLevel();
    }

    public void validateIssuanceDetails(String issueTo, int issuanceAmount, String description) {
        // verify issuance counter - to know which issuance row we need to validate in case of many
        investorDetailsPageNewUI.setIssuanceRowIndex();

        // extract issuance id - for future use
        String issuanceID = investorDetailsPageNewUI.getActualIssuanceID();

        // validate creation date
        Assert.assertTrue(DateTimeUtils.validateDate("Creation Date", "yyyy-MM-dd", investorDetailsPageNewUI.getActualIssuanceCreated()));

        // validate issued tokens
        String actualIssuedAmount = investorDetailsPageNewUI.getActualIssuanceAmount();
        Assert.assertEquals(actualIssuedAmount, String.valueOf(issuanceAmount), "actual issued tokens amount doesn't match entered value");

        // validate source
        String actualSource = investorDetailsPageNewUI.getActualIssuanceSource();
        Assert.assertEquals(actualSource.toLowerCase(), "manual", "actual source should have been 'manual'");

        // validate target
        String actualTarget = investorDetailsPageNewUI.getActualIssuanceTarget().toLowerCase();
        if (actualTarget.equals("treasury")) {
            actualTarget = "TBE";
        }
        Assert.assertTrue(actualTarget.contains(issueTo), "actual target should have been '" + issueTo + "'");

        // validate status
        String actualStatus = investorDetailsPageNewUI.getActualIssuanceStatus().toLowerCase().trim();
        Assert.assertEquals(actualStatus, "processing", "actual status should have been 'processing'");

        // validate Issuance date
        String actualIssuanceDate = investorDetailsPageNewUI.getActualIssuanceDate();
        Assert.assertTrue(DateTimeUtils.validateDate("Issuance Date", "yyyy-MM-dd", actualIssuanceDate));

        // validate description
        String actualDescription = investorDetailsPageNewUI.getActualIssuanceDescription();
        Assert.assertEquals(actualDescription, description, "actual description doesn't match entered reason");
        endTestLevel();

        // validate token
        String actualToken = investorDetailsPageNewUI.getActualIssuanceTokenName();
        Assert.assertEquals(actualToken, tokenName, "actual description doesn't match entered reason");
        endTestLevel();

    }

    public int setTokensAmount(String tokensHeldIn) {
        int tokenAmount = 0;
        if (tokensHeldIn.equals("TBE")) {
            tokenAmount = ISSUED_TOKENS;
        } else if (tokensHeldIn.equals("wallet")) {
            tokenAmount = TBE_ISSUED_TOKENS;

        } else {
            errorAndStop("No token type detekted", false);
        }
        return tokenAmount;
    }

    public void cpAddIssuance(String tokensHeldIn, int tokenAmount, String currency, String issuanceDescription) {
        boolean isIssuanceDetailsCorrect = investorDetailsPageNewUI.clickAddIssuance()
                .addIssuanceDetails(tokensHeldIn, tokenAmount, investmentAmount, currency, issuanceDescription);
        Assert.assertTrue(isIssuanceDetailsCorrect);
    }

    public void cpAddIssuances(InvestorDetails investorDetails, String issuanceTBEReason, String issuerName) {

        startTestLevel("Add issuance");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        SoftAssert softAssertion = new SoftAssert();
        String walletName = "CP test wallet";
        String walletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        getBrowser().refreshPage();
        String issuanceReason = "automatic test issuance";
        investorDetailsPage.clickAddIssuance()
                .selectTokenWalletId(walletName, walletAddress)
                .typeReason(issuanceReason)
                .typeIssuanceAmount(ISSUED_TOKENS)
                .clickOk(investorDetails.getFirstName(), investorDetails.getMiddleName(), investorDetails.getLastName(), ISSUED_TOKENS);

        investorDetailsPage.issuancesCard.waitForTableToContainNumberOfRows(1);

        // extract transaction id
        String transactionId = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "ID");
        info("transactionId = " + transactionId);

        // validate creation date
        investorDetailsPage.validateDate("Issuances", investorDetailsPage, "Created");

        // validate executed
        String actualExecuted = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Executed");
        Assert.assertEquals(actualExecuted, "-", "actual executed value isn't empty");

        // validate issued tokens
        String actualIssuedAmount = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Issue Amount");
        Assert.assertEquals(actualIssuedAmount.trim(), ISSUED_TOKENS + "", "actual issued tokens amount doesn't match entered value");

        // validate source
        String actualSource = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Source");
        Assert.assertEquals(actualSource, "manual", "actual source should have been 'manual'");

        // validate target
        String actualTarget = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Target");
        Assert.assertEquals(actualTarget, "wallet", "actual target should have been 'wallet'");

        // validate status
        String actualStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(actualStatus, "processing", "actual status should have been 'processing'");

        // validate Issuance date
        investorDetailsPage.validateDate("Issuances", investorDetailsPage, "Created");

        // validate description
        String actualDescription = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Description");
        Assert.assertEquals(actualDescription, issuanceReason, "actual description doesn't match entered reason");
        endTestLevel();

        startTestLevel("Add TBE issuance");
        investorDetailsPage.clickAddIssuance()
                .typeReason(issuanceTBEReason)
                .typeIssuanceAmount(TBE_ISSUED_TOKENS)
                .clickOk(investorDetails.getFirstName(), investorDetails.getMiddleName(), investorDetails.getLastName(), TBE_ISSUED_TOKENS);

        investorDetailsPage.issuancesCard.waitForTableToContainNumberOfRows(2);

        // extract TBE transaction id
        String transactionTBEId = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "ID");
        info("TBEtransactionId = " + transactionTBEId);

        // validate creation date
        investorDetailsPage.validateDate("Issuances", investorDetailsPage, "Created");


        // validate executed
        String actualTBEExecuted = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Executed");
        softAssertion.assertEquals(actualTBEExecuted, "-", "actual executed value isn't empty");

        // validate issued TBE tokens
        String actualTBEIssuedAmount = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Issue Amount");
        softAssertion.assertEquals(actualTBEIssuedAmount.trim(), TBE_ISSUED_TOKENS + "", "actual issued tokens amount doesn't match entered value");

        // validate source
        actualSource = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Source");
        softAssertion.assertEquals(actualSource, "manual", "actual source should have been 'manual'");

        // validate TBE target
        String actualTBETarget = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Target");
        softAssertion.assertEquals(actualTBETarget, "TBE", "actual target should have been 'TBE'");

        // validate status
        String actualTBEStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Status");
        softAssertion.assertEquals(actualTBEStatus, "processing", "actual status should have been 'processing'");

        // validate TBE Issuance date
        investorDetailsPage.validateDate("Issuances", investorDetailsPage, "Created");


        // validate TBE description
        String actualTBEDescription = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Description");
        softAssertion.assertEquals(actualTBEDescription, issuanceTBEReason, "actual description doesn't match entered reason");
        endTestLevel();

        startTestLevel("Sign the transactions");
        String investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpSignatures signatures = sideMenu.clickSignatures();
        WalletDetails walletDetails = Users.getIssuerWalletDetails(issuerName, getTestNetwork());

        //signing signatures
        for (int i = 0; i < 2; i++) {
            signatures.waitForSignaturePageToStabilize();
            signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                    .clickSignAllTransactionsButton()
                    .clickWalletTypeSignatureRadioButton()
                    .typeSignerAddress(walletDetails.getWalletAddress())
                    .typePrivateKey(walletDetails.getWalletPrivateKey())
                    .clickSignNow();
        }
        endTestLevel();

        startTestLevel("validate tokens arrived to investor");
        navigateInvestorDirectUrl(investorDirectUrl);
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        // force cron jobs to run now - to make transaction status to become 'success' faster
        TestNetwork currentTestNetwork = getTestNetwork();
        if (!MainConfig.isProductionEnvironment() && (currentTestNetwork.needsBypass())) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }

        // validate regular token status
        IssuanceNotSuccessTimeoutException issuanceNotSuccessTimeoutException = new IssuanceNotSuccessTimeoutException("Issuance was not marked as success even after " + waitTimeSecondsIssuanceReady + " seconds.");
        investorDetailsPage.issuancesCard.waitForEntityInTableStatusToBecome(1, "success", waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady, issuanceNotSuccessTimeoutException);
        String finalWalletIssuanceStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(finalWalletIssuanceStatus, "success", "actual status should have been 'success'");

        // validate tbe token status
        investorDetailsPage.issuancesCard.waitForEntityInTableStatusToBecome(2, "success", waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady, issuanceNotSuccessTimeoutException);
        String finalTBEStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(2, "Status");
        Assert.assertEquals(finalTBEStatus, "success", "actual status should have been 'success'");

        // validate tokens held
        int waitTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.waitTimeTokensToArriveInWalletQuorumSeconds);
        int intervalTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTokensToArriveInWalletQuorumSeconds);
        investorDetailsPage.walletsCard.waitForWalletToHoldNumberOfTokens(1, ISSUED_TOKENS, waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        String actualTokensHeld = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Tokens Held");
        Assert.assertEquals(actualTokensHeld.trim(), ISSUED_TOKENS + "", "actual tokens held should match issuanced tokens");

        // validate Securitize total amount of tokens
        int expectedTotalTokensHeld = ISSUED_TOKENS + TBE_ISSUED_TOKENS;
        investorDetailsPage.waitForTokensUpdate("Securitize Logo", expectedTotalTokensHeld);
        int totalTokensHeld = investorDetailsPage.getSecuritizeTotalTokenAmount();
        Assert.assertEquals(totalTokensHeld, expectedTotalTokensHeld, "Total of actual tokens held should match total issuanced tokens");
        endTestLevel();
    }

    public void cpBulkIssuance(int amountOfInvestors, String lastName) {

        startTestLevel("Navigate to Fundraise");
        SoftAssert softAssertion = new SoftAssert();
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpFundraise fundraise = sideMenu.clickFundraise();
        endTestLevel();

        startTestLevel("Verify Investors are present on Fundraise screen");
        fundraise.searchInvestorByTextBox(lastName);
        softAssertion.assertEquals(amountOfInvestors, fundraise.countInvestorsByName("AUT519"));
        endTestLevel();

        startTestLevel("Perform a bulk issuance");
        fundraise.clickAllTickBoxes();
        fundraise.clickRunIssuanceButton();
        fundraise.clickConfirmRunButton();
        fundraise.clickOkButton();
        endTestLevel();
    }

    public void transferTBEToWallet(String procedureType, String firstName, String issuerName) {

        startTestLevel("Perform transfer TBE to wallet");
        performTransferTBEToWallet();
        endTestLevel();

        startTestLevel("Sign " + procedureType + " transaction");
        signSignatures(issuerName, firstName, 1);
        endTestLevel();

        startTestLevel("Accelerate transaction");
        accelerateTransaction();
        endTestLevel();

        startTestLevel("Validate TBE update after transaction");
        investorDetailsPage.waitForTokensUpdate("TBE", tokensTBE - tokensToBeProcedure);
        endTestLevel();

        startTestLevel("Validate TBE update after transaction");
        investorDetailsPage.waitForTokensUpdate("wallet", tokensWallet + tokensToBeProcedure);
        endTestLevel();
    }

    public void performTransferTBEToWallet() {
        startTestLevel("Click on Transfer to wallet from TBE button");
        getBrowser().refreshPage();
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.clickTransferToWalletFromTBE()
                .setAmountToWithdraw(tokensToBeProcedure)
                .clickOk();
        endTestLevel();
    }

    public void signSignatures(String issuerName, String firstName, int numOfSignatures) {
        navigateToPage(signatures);
        CpSignatures signatures = new CpSignatures(getBrowser());
        signatures.signSignatures(issuerName, firstName, numOfSignatures);
        accelerateTransaction();
    }

    public void accelerateTransaction() {
        navigateInvestorDirectUrl(investorDirectUrl);
        // force cron jobs to run now - to make transaction status to become 'success' faster
        TestNetwork currentTestNetwork = getTestNetwork();
        if (!MainConfig.isProductionEnvironment() && (currentTestNetwork.needsBypass())) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }
    }

    public void cpAddSingleTbeIssuanceWithNoSignature(InvestorDetails investorDetails, String issuanceTBEReason) {

        startTestLevel("Add single TBE issuance without signing it");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        //getBrowser().refreshPage();
        startTestLevel("Add TBE issuance");
        investorDetailsPage.clickAddIssuance()
                .typeReason(issuanceTBEReason)
                .typeIssuanceAmount(1)
                .clickOk(investorDetails.getFirstName(), investorDetails.getMiddleName(), investorDetails.getLastName(), 1);
        endTestLevel();
    }

    public void addIssuance(String firstName, String issuerName, boolean isSecuritiesExceeded) {

        startTestLevel("Create TBE issuance");
        addTBEIssuanceCB(isSecuritiesExceeded);
        endTestLevel();

        startTestLevel("Create wallet issuance");
        addWalletIssuanceCB(isSecuritiesExceeded);
        endTestLevel();

        startTestLevel("Sign issuance if created");
        // if 'isSecuritiesExceeded = true', issuance does not suppose to be created
        if (!isSecuritiesExceeded) {
            startTestLevel("Sign both issuance");
            signSignatures(issuerName, firstName, 2);
            endTestLevel();

            startTestLevel("Validate tokens arrive to investor");
            navigateInvestorDirectUrl(investorDirectUrl);
            investorDetailsPage.waitForTokensUpdate("TBE", tokensTBE + ISSUED_TOKENS_CB);
            investorDetailsPage.waitForTokensUpdate("wallet", tokensWallet + ISSUED_TOKENS_CB);
            endTestLevel();
        }
    }

    public void addTBEIssuanceCB(boolean isSecuritiesExceeded) {
        navigateInvestorDirectUrl(investorDirectUrl);
        getBrowser().refreshPage();
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        if (isSecuritiesExceeded) {
            startTestLevel("Add TBE issuance when Authorized Securities exceeded");
            investorDetailsPage.clickAddIssuance()
                    .typeReason("CB TBE issuance")
                    .typeIssuanceAmount(ISSUED_TOKENS_CB)
                    .clickOk(investorDetailsPage.getFirstName(), investorDetailsPage.getMiddleName(), investorDetailsPage.getLastName(), ISSUED_TOKENS_CB);
            investorDetailsPage.verifyIssuanceErrorMessage();
            endTestLevel();
        } else {
            startTestLevel("Add TBE issuance");
            investorDetailsPage.clickAddIssuance()
                    .typeReason("CB TBE issuance")
                    .typeIssuanceAmount(ISSUED_TOKENS_CB)
                    .clickOk(investorDetailsPage.getFirstName(), investorDetailsPage.getMiddleName(), investorDetailsPage.getLastName(), ISSUED_TOKENS_CB);
            investorDetailsPage.verifyIssuanceSuccessMessage();
        }
    }

    public void addWalletIssuanceCB(boolean isSecuritiesExceeded) {
        if (!getBrowser().getPageTitle()
                .equals("Details")) {
            CpSideMenu sideMenu = new CpSideMenu(getBrowser());
            CpOnBoarding cpOnBoarding = sideMenu.clickOnBoarding();
            String name = investorDetailsPage.getFirstName();
            cpOnBoarding.clickShowInvestorDetailsByName(name);
        }
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        getBrowser().refreshPage();
        if (isSecuritiesExceeded) {
            investorDetailsPage.clickAddIssuance()
                    .typeReason("CB wallet issuance")
                    .typeIssuanceAmount(ISSUED_TOKENS_CB)
                    .clickOk(investorDetailsPage.getFirstName(), investorDetailsPage.getMiddleName(), investorDetailsPage.getLastName(), ISSUED_TOKENS_CB);
            investorDetailsPage.verifyIssuanceErrorMessage();
        } else {
            String walletName = "CP test wallet";
            getBrowser().waitForPageStable(Duration.ofSeconds(10));
            String walletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
            investorDetailsPage.clickAddIssuance()
                    .selectTokenWalletId(walletName, walletAddress)
                    .typeReason("CB wallet issuance")
                    .typeIssuanceAmount(ISSUED_TOKENS_CB)
                    .clickOk(investorDetailsPage.getFirstName(), investorDetailsPage.getMiddleName(), investorDetailsPage.getLastName(), ISSUED_TOKENS_CB);
            investorDetailsPage.verifyIssuanceSuccessMessage();
        }
    }

    public void signIssuanceNewUI(String issuerName, String firstName) {
        startTestLevel("Sign issuance");
        signSignatures(issuerName, firstName, issuanceCounter);
        endTestLevel();
    }

    public void cpSignBulkIssuance(String issuerName, String randomGeneratedLastName) {

        startTestLevel("Sign the transactions");
        String bulkIssuanceDescriptionForTbe = "Issuing 18 tokens for 9 investors, address: TBE Wallet";
        String bulkIssuanceDescriptionForWallet = "Issuing 2 tokens to user AUT519 import " + randomGeneratedLastName + "wallet";
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpSignatures signatures = sideMenu.clickSignatures();
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerWalletAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);

        //signing bulk issuance
        signatures.waitForSignaturePageToStabilize()
                .waitForTableToContainRowsByDescriptionOnAllStatuses(bulkIssuanceDescriptionForWallet, 1)
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();

        signatures.filterByStatus("Success")
                .waitForSignaturePageToStabilize()
                .waitForTableToContainRowsByDescriptionOnAllStatuses(bulkIssuanceDescriptionForWallet, 1);
        endTestLevel();

    }

    public void signIssuanceLC(String issueTo, InvestorDetails investorDetails, String issuerName, int TOKENS) {
        startTestLevel("Sign the transactions");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        String investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpSignatures signatures = sideMenu.clickSignatures();
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerWalletAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);

        signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();

        startTestLevel("validate tokens arrived to investor");
        navigateInvestorDirectUrl(investorDirectUrl);
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);

        // force cron jobs to run now - to make transaction status to become 'success' faster
        if (!MainConfig.isProductionEnvironment()) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }

        // validate TBE token status
        IssuanceNotSuccessTimeoutException issuanceNotSuccessTimeoutException = new IssuanceNotSuccessTimeoutException("Issuance was not marked as success even after " + waitTimeSecondsIssuanceReady + " seconds.");
        investorDetailsPage.issuancesCard.waitForEntityInTableStatusToBecome(1, "success", waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady, issuanceNotSuccessTimeoutException);
        String finalIssuanceStatus = investorDetailsPage.issuancesCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(finalIssuanceStatus, "success", "actual status should have been 'success'");
        getBrowser().refreshPage();

        investorDetailsPage.waitForTokensUpdate(issueTo, TOKENS);
        endTestLevel();
    }



    public void updateControlBook() {
        startTestLevel("Navigate to Control book");
        navigateToPage(controlBook);
        CpControlBook cpControlBook = new CpControlBook(getBrowser());
        endTestLevel();

        startTestLevel("Update Control Book");
        cpControlBook.insertNewAuthorizedSecurities(ISSUED_TOKENS_CB);
        endTestLevel();
    }

    public void verifyControlBookUpdated() {
        startTestLevel("Navigate to Control book");
        navigateToPage(controlBook);
        CpControlBook cpControlBook = new CpControlBook(getBrowser());
        endTestLevel();

        startTestLevel("Verify if Control Book data updated after issuance");
        cpControlBook.isCBtoUpdateAfterIssuance(ISSUED_TOKENS_CB);
        endTestLevel();
    }

    public void cpAddWallet() {
        startTestLevel("Add wallet");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        EthereumWallet ethereumWallet = EthereumUtils.generateRandomWallet();
        if (ethereumWallet == null) return; // should never happen due to error_an_stop within
        walletAddress = ethereumWallet.getAddress();
        String walletName = "CP test wallet";
        info("New wallet address: " + walletAddress);
        investorDetailsPage.clickAddWallet()
                .typeName(walletName)
                .typeAddress(walletAddress)
                .clickOk();

        investorDetailsPage.walletsCard.waitForTableToContainNumberOfRows(1);

        // validate wallet name
        String actualWalletName = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Name");
        Assert.assertEquals(actualWalletName, walletName, "actual wallet name doesn't match entered value");

        // validate wallet address
        String actualWalletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        Assert.assertEquals(actualWalletAddress.toLowerCase(), walletAddress.toLowerCase(), "actual wallet address doesn't match entered value");

        // validate wallet status
        String actualWalletStatus = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(actualWalletStatus, "pending", "wallet initial status should be 'pending'");
        endTestLevel();


        startTestLevel("Set KYC & Accreditation");
        String kycComment = "comment_" + RandomGenerator.randomString(20);
        investorDetailsPage.clickEditKYC()
                .setCurrentKycStatus("Verified")
                .setCurrentAccreditationStatus("Confirmed")
                .setQualificationStatus("Confirmed")
                .typeKycComment(kycComment)
                .clickSaveChanges();
        endTestLevel();

/*

        startTestLevel("Validate wallet status changed to 'Syncing'");
        getBrowser().refreshPage();
        investorDetailsPage.walletsCard.scrollTableIntoView(1);
        investorDetailsPage.walletsCard.waitForTableToContainNumberOfRows(1);
        actualWalletStatus = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(actualWalletStatus, "syncing", "wallet status after KYC edit should be 'syncing'");
        endTestLevel();
*/

        startTestLevel("Wait for wallet status to become 'ready'");
        getBrowser().refreshPage();
        int waitTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeWalletToBecomeReadyQuorumSeconds);
        int intervalTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeWalletToBecomeReadyQuorumSeconds);
        // force cron jobs to run now - to make wallet 'ready' faster
        TestNetwork currentTestNetwork = getTestNetwork();
        if (!MainConfig.isProductionEnvironment() && (currentTestNetwork.needsBypass())) {
            SshRemoteExecutor.executeScript("walletRegistrationBypass.sh");
        }
        WalletNotReadyTimeoutException walletNotReadyTimeoutException = new WalletNotReadyTimeoutException("Wallet was not marked as ready even after " + waitTimeSecondsWalletReady + " seconds.");
        investorDetailsPage.walletsCard.waitForEntityInTableStatusToBecome(1, "ready", waitTimeSecondsWalletReady, intervalTimeSecondsWalletReady, walletNotReadyTimeoutException);
        endTestLevel();
    }

    public void cpAddWalletNewUI() {
        startTestLevel("Set wallet details");
        setWalletDetails();
        endTestLevel();

        startTestLevel("Add wallet");
        investorDetailsPageNewUI.clickTokensWalletTab();
        cpAddNewWallet();
        endTestLevel();

        startTestLevel("Validate Wallet details");
        validateWalletDetails();
        endTestLevel();
    }

    public void waitWalletToBeReady() {
        // We wait that wallet status change to 'syncing' but in some cases it is 'ready' immediately
        String actualWalletStatus = waitWalletChangeStatus("syncing");
        if (!actualWalletStatus.equals("ready")) {
            waitWalletChangeStatus("ready");
        }
    }

    public void setWalletDetails() {
        EthereumWallet ethereumWallet = EthereumUtils.generateRandomWallet();
        if (ethereumWallet == null) return; // should never happen due to error_an_stop within
        walletAddress = ethereumWallet.getAddress();
        walletName = "CP test wallet";
        info("New wallet address: " + walletAddress);
    }

    public void cpAddNewWallet() {
        investorDetailsPageNewUI.clickAddWallet()
                .typeName(walletName)
                .typeAddress(walletAddress)
                .clickOk();
    }

    public void validateWalletDetails() {
        // validate wallet name
        String actualWalletName = investorDetailsPageNewUI.getActualWalletName();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualWalletName, walletName, "actual wallet name doesn't match entered value");

        // validate wallet address
        String actualWalletAddress = investorDetailsPageNewUI.getActualWalletAddress();
        softAssert.assertEquals(actualWalletAddress.toLowerCase(), walletAddress.toLowerCase(), "actual wallet address doesn't match entered value");

        // validate wallet type
        String actualWalletType = investorDetailsPageNewUI.getActualWalletType();
        softAssert.assertEquals(actualWalletType.toLowerCase(), walletAddress.toLowerCase(), "actual wallet type doesn't match entered value");

        // validate wallet owner
        String actualWalletOwner = investorDetailsPageNewUI.getActualWalletOwner();
        softAssert.assertTrue(actualWalletOwner.toLowerCase()
                .contains(firstName), "actual wallet owner doesn't match entered value");

        // validate wallet status
        String actualWalletStatus = investorDetailsPageNewUI.getActualWalletStatus();
        softAssert.assertEquals(actualWalletStatus, "pending", "wallet initial status should be 'pending'");

        // validate wallet tokens pending
        String actualWalletTokensPending = investorDetailsPageNewUI.getActualWalletTokensPending();
        softAssert.assertEquals(actualWalletTokensPending, 0, "actual wallet tokens pending doesn't match entered value");

        // validate wallet tokens held
        String actualWalletTokensHeld = investorDetailsPageNewUI.getActualWalletTokensHeld();
        softAssert.assertEquals(actualWalletTokensHeld, 0, "actual wallet tokens held doesn't match entered value");

        // validate token name
        String actualWalletTokenName = investorDetailsPageNewUI.getActualWalletTokenName();
        softAssert.assertEquals(actualWalletTokenName, 0, "actual wallet tokens held doesn't match entered value");

        // validate wallet creation date
        String actualWalletCreationDate = investorDetailsPageNewUI.getActualWalletCreationDate();
        Assert.assertTrue(DateTimeUtils.validateDate("wallet creation date", "yyyy-MM-dd", actualWalletCreationDate));
    }

    public String waitWalletChangeStatus(String expectedStatus) {
        getBrowser().refreshPage();
        investorDetailsPageNewUI.clickTokensWalletTab();
        startTestLevel("Check wallet status");
        String actualWalletStatus = investorDetailsPageNewUI.getActualWalletStatus();
        while (!actualWalletStatus.equals(expectedStatus)) {
            if (actualWalletStatus.equals("pending")) {
                errorAndStop("Wallet status should not be 'pending'", true);
            } else if (actualWalletStatus.equals("ready")) {
                break;
            }
            getBrowser().waitForPageStable(Duration.ofSeconds(30));
            getBrowser().refreshPage();
            investorDetailsPageNewUI.clickTokensWalletTab();
            actualWalletStatus = investorDetailsPageNewUI.getActualWalletStatus();
        }
        endTestLevel();
        return actualWalletStatus;
    }

    public void cpAddWalletToImportedInvestor() {

        startTestLevel("Add wallet");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        EthereumWallet ethereumWallet = EthereumUtils.generateRandomWallet();
        if (ethereumWallet == null) return; // should never happen due to error_an_stop within
        String walletAddress = ethereumWallet.getAddress();
        String walletName = "CP test wallet";
        info("New wallet address: " + walletAddress);
        investorDetailsPage.clickAddWallet()
                .typeName(walletName)
                .typeAddress(walletAddress)
                .clickOk();

        investorDetailsPage.walletsCard.waitForTableToContainNumberOfRows(1);

        // validate wallet name
        String actualWalletName = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Name");
        Assert.assertEquals(actualWalletName, walletName, "actual wallet name doesn't match entered value");

        // validate wallet address
        String actualWalletAddress = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Address");
        Assert.assertEquals(actualWalletAddress.toLowerCase(), walletAddress.toLowerCase(), "actual wallet address doesn't match entered value");
        endTestLevel();

        startTestLevel("Validate wallet status changed to 'Syncing'");
        getBrowser().refreshPage();
        investorDetailsPage.walletsCard.scrollTableIntoView(1);
        investorDetailsPage.walletsCard.waitForTableToContainNumberOfRows(1);
        String actualWalletStatus = investorDetailsPage.walletsCard.getDetailAtIndex(1, "Status");
        Assert.assertEquals(actualWalletStatus, "syncing", "wallet status after KYC edit should be 'syncing'");
        endTestLevel();


        startTestLevel("Wait for wallet status to become 'ready'");
        int waitTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeWalletToBecomeReadyQuorumSeconds);
        int intervalTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeWalletToBecomeReadyQuorumSeconds);
        // force cron jobs to run now - to make wallet 'ready' faster
        if (!MainConfig.isProductionEnvironment()) {
            SshRemoteExecutor.executeScript("walletRegistrationBypass.sh");
        }
        WalletNotReadyTimeoutException walletNotReadyTimeoutException = new WalletNotReadyTimeoutException("Wallet was not marked as ready even after " + waitTimeSecondsWalletReady + " seconds.");
        investorDetailsPage.walletsCard.waitForEntityInTableStatusToBecome(1, "ready", waitTimeSecondsWalletReady, intervalTimeSecondsWalletReady, walletNotReadyTimeoutException);
        endTestLevel();
    }

    public void cpDestroyRegularToken(String issuerName, String firstName) {

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel("Input procedure data");
        performDestroyWalletTokens();
        endTestLevel();

        startTestLevel("Sign the transaction");
        signSignatures(issuerName, firstName, 1);
        endTestLevel();

        startTestLevel("Accelerate transaction");
        accelerateTransaction();
        endTestLevel();

        startTestLevel("Validate wallet reflects the correct amount of tokens held after destroy");
        investorDetailsPage.waitForTokensUpdate("wallet", tokensWallet - tokensCanBeProcedure);
        endTestLevel();
    }

    public void performDestroyTBETokens() {
        String reason = "Destroying " + tokensCanBeProcedure + " tbe tokens";
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        operationalProcedures.chooseDestroyTBETokenProcedure();
        operationalProcedures.addExternalInvestorId(investorExternalID);
        operationalProcedures.typeReason(reason);
        operationalProcedures.clickExecute(reason);
        operationalProcedures.verifySuccessMessage();
    }

    public void performDestroyWalletTokens() {
        String reason = "Destroying " + tokensCanBeProcedure + " regular tokens";
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        operationalProcedures.chooseDestroyRegularTokenProcedure();
        operationalProcedures.addWalletId(walletAddress);
        operationalProcedures.typeReason(reason);
        operationalProcedures.clickExecute(reason);
        operationalProcedures.verifySuccessMessage();
    }

    public void cpDestroyTBEToken(String issuerName, String firstName) {
        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel("Input procedure data");
        performDestroyTBETokens();
        endTestLevel();

        startTestLevel("Sign the transaction");
        signSignatures(issuerName, firstName, 1);
        endTestLevel();

        startTestLevel("Accelerate transaction");
        accelerateTransaction();
        endTestLevel();

        startTestLevel("Validate wallet reflects the correct amount of tokens held after destroy");
        investorDetailsPage.waitForTokensUpdate("TBE", tokensTBE - tokensCanBeProcedure);
        endTestLevel();
    }

    public void cpInternalTBETransfer(String firstName, String issuerName, int transferredAmount, String procedureType) {

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel("Internal TBE transfer procedure");
        performInternalTBETransfer(transferredAmount);
        signSignatures(issuerName, firstName, 1);
        verifyInternalTBETransfer(transferredAmount);
        endTestLevel();
    }

    public void verifyInternalTBETransfer(int transferredAmount) {
        startTestLevel("Validate TBE tokens were transferred");
        navigateInvestorDirectUrl(investorDirectUrl);
        // force cron jobs to run now - to make transaction status to become 'success' faster
        if (!MainConfig.isProductionEnvironment()) {
            SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        }
        // validate TBE amount updated
        investorDetailsPage.waitForTokensUpdate("TBE", tokensTBE + transferredAmount);
        endTestLevel();
    }

    public void performInternalTBETransfer(int transferredAmount) {
        startTestLevel("Perform internal TBE transfer");
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        String reason = "Transferring " + transferredAmount + " TBE tokens for Investor: " + investorExternalID;
        operationalProcedures.chooseInternalTransferProcedure();
        operationalProcedures.addSenderId(InvestorsData.getUserDetails(InvestorsProperty.investorSenderId));
        operationalProcedures.addReceiverId(investorExternalID);
        operationalProcedures.setTokenAmount(String.valueOf(transferredAmount));
        operationalProcedures.typeReason(reason);
        operationalProcedures.clickExecute(reason);
        operationalProcedures.verifySuccessMessage();
        endTestLevel();
    }

    public int setTotalLockedTokens(CpInvestorDetailsPage investorDetailsPage) {
        totalLockTokens = Integer.parseInt(investorDetailsPage.getTotalLockedTokens());
        return totalLockTokens;
    }

    public void setLockProcedureInput(String lockType, String tokenHeldIn, boolean isPartialLock) {

        if (lockType.equals("lock")) {
            if (tokenHeldIn.equals("TBE")) {
                if (isPartialLock) {
                    tokensToBeProcedure = tokensCanBeProcedure - 2;
                    tokensAfterProcedure = tokensCanBeProcedure - tokensToBeProcedure;
                    fullOrPartially = "Partially";
                } else {
                    tokensCanBeProcedure = tokensAfterProcedure;
                    tokensToBeProcedure = tokensAfterProcedure;
                    fullOrPartially = "Full";
                }
            }
            if (tokenHeldIn.equals("wallet")) {
                if (isPartialLock) {
                    tokensToBeProcedure = tokensCanBeProcedure - 2;
                    tokensAfterProcedure = tokensCanBeProcedure - tokensToBeProcedure;
                    fullOrPartially = "Partially";
                } else {
                    tokensCanBeProcedure = tokensAfterProcedure;
                    tokensToBeProcedure = tokensAfterProcedure;
                    fullOrPartially = "Full";
                }
            }
            expectedTokensAfterProcedure = totalLockTokens + tokensToBeProcedure;
            // totalLockTokens = totalLockTokens + tokensToBeProcedure;
            info(lockType + " input are set: " + tokenHeldIn + " " + fullOrPartially + " " + lockType + ". " + lockType + " " + tokensToBeProcedure + " tokens out of " + tokensCanBeProcedure + " unlocked tokens of investor");
            info("After procedure will be " + expectedTokensAfterProcedure + " locked tokens");
        }
        if (lockType.equals("unlock")) {
            if (tokenHeldIn.equals("wallet")) {
                tokensCanBeProcedure = totalLockTokens;
                tokensToBeProcedure = tokensCanBeProcedure;
                fullOrPartially = "Full";
            }
            expectedTokensAfterProcedure = totalLockTokens - tokensToBeProcedure;
            //  totalLockTokens = totalLockTokens - tokensToBeProcedure;
            info(lockType + " input are set: " + tokenHeldIn + " " + fullOrPartially + " " + lockType + ". " + lockType + " " + tokensToBeProcedure + " tokens from " + tokensCanBeProcedure + " locked tokens of investor");
        }
    }

    public void performLockTokens(String lockType, String tokenHeldIn, String issuerName, String firstName) {

        startTestLevel("Navigate to Operational Procedure page if necessary");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel("Input " + lockType + " " + tokenHeldIn + " Tokens data " + fullOrPartially + " " + lockType);
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        int numOfSignatures = 0;
        if (lockType.equals("lock")) {
            operationalProcedures.chooseLockTokensProcedure(lockType);
            operationalProcedures.addExternalInvestorId(investorExternalID);
            operationalProcedures.chooseTokenHeldIn(tokenHeldIn);
            // Add this validation after Story ISR2 - 1792 is Done - it shows total tokens of investor instead of available tokens for lock
            // Assert.assertEquals(operationalProcedures.getTotalAmountOfSecuritiesCanBeLock(), tokensCanBeProcedure-totalLockTokens, "Tokens available to lock should be "+(tokensCanBeProcedure-totalLockTokens)+" but they are "+operationalProcedures.getTotalAmountOfSecuritiesCanBeLock());
            operationalProcedures.typeNumberOfTokenToLock(tokensToBeProcedure);
            numOfSignatures = 1;
        }

        if (lockType.equals("unlock")) {
            operationalProcedures.chooseUnlockTokensProcedure();
            operationalProcedures.addExternalInvestorId(investorExternalID);
            // Add this validation after Story ISR2 - 1792 is Done - it shows total tokens of investor instead of available tokens for lock
            // actualTokensCanBeInProcedure = operationalProcedures.getTotalAmountOfSecuritiesCanBeProcedure();
            // Assert.assertEquals(actualTokensCanBeInProcedure, tokensCanBeProcedure - totalLockTokens, "Tokens available to lock should be " + (tokensCanBeProcedure - totalLockTokens) + " but they are " + actualTokensCanBeInProcedure);
            numOfSignatures = 2;
        }
        String reason = lockType + " " + tokenHeldIn + " tokens, " + fullOrPartially + " " + lockType;
        operationalProcedures.typeReasonLockToken(reason);
        operationalProcedures.clickExecute(reason);
        operationalProcedures.verifySuccessMessage();

        if (tokenHeldIn.equals("wallet")) {
            signSignatures(issuerName, firstName, numOfSignatures);
        }
        endTestLevel();
    }

    public void verifyLockProcedure(String lockType, String tokenHeldIn) {

        startTestLevel("Verify " + expectedTokensAfterProcedure + " tokens shown as " + lockType + " in investor details page");
        navigateInvestorDirectUrl(investorDirectUrl);
        investorDetailsPage.waitForTokensUpdate(lockType, expectedTokensAfterProcedure);
        setTotalLockedTokens(investorDetailsPage);
        info("Total locked tokens now is: " + expectedTokensAfterProcedure);
        // Assert.assertEquals(Integer.toString(expectedTotalLockTokens), totalLockTokens, "Total " + lockType + " tokens should be " + totalLockTokens + " but it is " + investorDetailsPage.getTotalLockedTokens() + "!");
        endTestLevel();
    }

    public void cpLockToken(String lockType, String tokenHeldIn, String issuerName, String firstName, boolean isPartialLock) {
        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel(lockType + " Tokens procedure");
        setLockProcedureInput(lockType, tokenHeldIn, isPartialLock);
        performLockTokens(lockType, tokenHeldIn, issuerName, firstName);
        verifyLockProcedure(lockType, tokenHeldIn);
        endTestLevel();
    }

    public void cpExecuteLostShares(String sourceWallet, String destinationWallet, InvestorDetails investorDetails, String issuerName) {
        startTestLevel("Navigate to Operational Procedures");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickOperationalProcedures();
        endTestLevel();

        startTestLevel("Lost Shares procedure");
        cpPerformLostShares(sourceWallet, destinationWallet, investorDetails, issuerName);
        cpVerifyLostSharesProcedure(sourceWallet, destinationWallet);
        endTestLevel();
    }

    public void cpPerformLostShares(String sourceWallet, String destinationWallet, InvestorDetails investorDetails, String issuerName) {
        startTestLevel("Perform lost shares procedure");
        String reason = "Automation - Perform lost shares procedure";
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        operationalProcedures.chooseLostSharesProcedure();
        operationalProcedures.typeSourceWallet(sourceWallet);
        int securitiesInSourceWallet = operationalProcedures.getNumberOfSecuritiesInSourceWallet();
        assertThat(securitiesInSourceWallet).as("Number of securities in the source wallet should be " + ISSUED_TOKENS + " but it's " + securitiesInSourceWallet).isEqualTo(ISSUED_TOKENS);
        operationalProcedures.typeDestinationWallet(destinationWallet);
        operationalProcedures.selectDateOfRedemption();
        operationalProcedures.typeReasonLostShares(reason);
        operationalProcedures.clickExecute(reason);
        operationalProcedures.verifySuccessMessage();
        cpSignLostSharesTransactions(investorDetails, issuerName);
        endTestLevel();
    }

    public void cpSignLostSharesTransactions(InvestorDetails investorDetails, String issuerName) {
        startTestLevel("Sign the lock wallet tokens transaction");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpSignatures signatures = sideMenu.clickSignatures();
        String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerWalletAddress);
        String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);
        signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();
        signatures.waitForSignaturePageToStabilize();
        signatures.checkRowByUserFirstName(investorDetails.getFirstName())
                .clickSignAllTransactionsButton()
                .clickWalletTypeSignatureRadioButton()
                .typeSignerAddress(issuerSignerWalletAddress)
                .typePrivateKey(issuerSignerPrivateKey)
                .clickSignNow();
        endTestLevel();
    }

    public void cpVerifyLostSharesInvestor(InvestorDetails investorDetails) {
        startTestLevel("Search for investor and go into investor details");
        cpNavigateToOnboarding();
        CpOnBoarding onBoarding = new CpOnBoarding(getBrowser());
        onBoarding.searchUniqueInvestorByTextBox(investorDetails.getFirstName(), NON_VALID_SEARCH);
        onBoarding.waitForTableToCountRowsByCellDetail(1, 120, investorDetails.getFirstName());
        onBoarding.clickOnFirstInvestor();
        endTestLevel();

        startTestLevel("Verify the investor has wallets and tokens");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        investorDetailsPage.waitForInvestorDetailsPageToStabilize();
        investorDetailsPage.waitForWalletTableToLoad();
        int numOfTokens = Integer.parseInt(investorDetailsPage.getTotalTokensHeld());
        int numOfWallets = investorDetailsPage.walletsCard.getEntityCount();
        Assert.assertEquals(numOfTokens, ISSUED_TOKENS, "The investor does not have " + ISSUED_TOKENS + " tokens");
        Assert.assertEquals(numOfWallets, NUMBER_OF_WALLETS_LOST_SHARES, "The investor does not have " + NUMBER_OF_WALLETS_LOST_SHARES + " wallets");
        investorDirectUrl = investorDetailsPage.getCurrentInvestorDirectUrl();
        endTestLevel();
    }

    public void cpVerifyLostSharesProcedure(String sourceWallet, String destinationWallet) {
        startTestLevel("Verify tokens shown at the destination wallet");
        navigateInvestorDirectUrl(investorDirectUrl);
        int waitTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.waitTimeTokensToArriveInWalletEthereumSeconds);
        int intervalTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTokensToArriveInWalletEthereumSeconds);
        int sourceWalletIndex = investorDetailsPage.getWalletRowIndex(sourceWallet);
        int destinationWalletIndex = investorDetailsPage.getWalletRowIndex(destinationWallet);
        investorDetailsPage.walletsCard.waitForWalletToHoldNumberOfTokens(sourceWalletIndex, 0, waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        investorDetailsPage.walletsCard.waitForWalletToHoldNumberOfTokens(destinationWalletIndex, ISSUED_TOKENS, waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        endTestLevel();
    }

    public void cpHoldTrading(String issuerName, String firstName, String documentID) {

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        endTestLevel();

        startTestLevel("Hold Trading procedure: " + freezeType + " Token");
        PerformHoldTrading(freezeType, issuerName, firstName, documentID);
        endTestLevel();

        startTestLevel("Verify token is " + freezeType);
        verifyContractStatusUpdated(freezeType);
        endTestLevel();
    }

    public void verifyContractStatusUpdated(String freezeType) {
        startTestLevel("Define expected contract status");
        String expectedStatus = null;
        if (freezeType.equals("freeze")) {
            expectedStatus = "on-hold";
        } else if (freezeType.equals("un-freeze")) {
            expectedStatus = "operational";
        } else {
            errorAndStop("Freeze type is not defined", true);
        }
        info("Expected contract status is " + expectedStatus);
        endTestLevel();

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        endTestLevel();

        startTestLevel("Verify token is " + freezeType);
        operationalProcedures.chooseHoldTradingProcedure();
        operationalProcedures.getUpdatedContractStatus(expectedStatus, 6000, 30000);
        endTestLevel();
    }

    public void checkContractStatus() {

        startTestLevel("Navigate to Operational Procedures");
        navigateToPage(operationalProcedures);
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        endTestLevel();

        startTestLevel("Check contract status");
        operationalProcedures.chooseHoldTradingProcedure();
        String actual = operationalProcedures.getContractStatus();
        if (actual.equals("on-hold")) {
            freezeType = "un-freeze";
        } else if (actual.equals("operational")) {
            freezeType = "freeze";
        }
        endTestLevel();
    }

    public void PerformHoldTrading(String freezeType, String issuerName, String firstName, String documentID) {
        info("Input " + freezeType + " data ");
        String reason = freezeType + " token contracts";
        CpOperationalProcedures operationalProcedures = new CpOperationalProcedures(getBrowser());
        operationalProcedures.chooseHoldTradingProcedure()
                .addDocumentId(documentID)
                .typeReason(reason)
                .executeHoldTrading()
                .confirmHoldTradingModal(issuerName);
        Assert.assertTrue(operationalProcedures.verifySuccessMessage());
        info("Procedure executed successfully and " + freezeType + " all contracts");
        signSignatures(issuerName, firstName, 1);
    }
}
