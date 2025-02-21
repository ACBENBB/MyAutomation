package io.securitize.tests.transferAgent;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.utils.TokensTransferUtils;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.DistributionDetailsPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdPortfolio;
import io.securitize.tests.transferAgent.abstractClass.AbstractDistributions;
import io.securitize.tests.transferAgent.testData.DistributionData;
import io.securitize.tests.transferAgent.testData.DistributionsInvestorData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT476_TA_Distribution_Redemption_Complete extends AbstractDistributions {

    private String testDistributionId = null;
    private int expectedRows = 0;
    String tokenName = "AUT";


    @BeforeMethod
    public void beforeTest() {
        startTestLevel("Check for an open redemption and if exists, close it");
        expectedRows = getHoldersCountFromDristirbutionsDb(tokenName);
        testDistributionId = getIdOfRedemptionInPendingStatus(Users.getProperty(UsersProperty.ta_distribution_redemption_tokenId_aut476));
        if (testDistributionId != null) {
            closeRedemptionById(testDistributionId);
        }
        endTestLevel();
    }

    @Test(description = "Complete Redemption flow using csv to alter default redemption values.")
    public void AUT476_TA_Distribution_Redemption_Complete_test() {
        SoftAssert sftAssert = new SoftAssert();
        DistributionsInvestorData redemptionInvestor = new DistributionsInvestorData(DistributionsInvestorType.DISTRIBUTION_AUT476_TESTDATA.toString());
        String transactionHash;
        String initialCashAccountBalance;

        DistributionData redemptionData = new DistributionData(DistributionType.DISTRIBUTION_TYPE_REDEMPTION.toString());

        startTestLevel("Create a Redemption in CP");
        createSnapshotViaApi(redemptionData.issuerId, redemptionData.tokenId, "AUT476");
        loginToControlPanel();
        navigateToDistributionPage(redemptionData.issuerId, redemptionData.tokenId);
        generateDistribution(redemptionData);
        clickLatestDistributionCreatedByName(redemptionData.distributionName);
        endTestLevel();

        startTestLevel("Validate UI WebElements Visibility of created Redemption");
        DistributionDetailsPage redemptionPage = new DistributionDetailsPage(getBrowser());
        redemptionData.distributionId = getDistributionIdFromUrl();
        testDistributionId = redemptionData.distributionId;
        redemptionData.amountPerToken = getDistributionAmountPerToken();
        redemptionData.totalAmount = getDistributionTotalAmount();
        redemptionData.endDate = redemptionPage.getDistributionDate();
        logoutFromControlPanel();
        endTestLevel();

        startTestLevel("Validate Redemption Created Email is sent ");
        loginToSecuritizeId(redemptionInvestor.email, redemptionInvestor.password);
        validateEmailSent(redemptionInvestor.email, redemptionInvestor.testInvestorType, redemptionData.sellBackPrice);
        endTestLevel();

        startTestLevel("Validate UI WebElements Visibility of SecID Redemption Page ");
        validateVisibilityOfSecIdRedemptionPageElements(redemptionData.redemptionWallet, redemptionData.amountPerToken);
        goToPortfolioPage();
        SecuritizeIdPortfolio portfolioPage = new SecuritizeIdPortfolio(getBrowser());
        initialCashAccountBalance = String.valueOf(portfolioPage.getCashAccountBalance());
        info("Initial Cash Balance is: " + initialCashAccountBalance);
        navitagateToAssetDetailsPage(tokenName);
        goToRedemptionPageUsingRedeemButton();
        validateVisibilityOfSecIdRedemptionPageElements(redemptionData.redemptionWallet, redemptionData.amountPerToken);
        logoutFromSecId();
        endTestLevel();

        startTestLevel("Send tokens to redemption wallet");
        transactionHash = TokensTransferUtils.sendTokens(MainConfig.getProperty(MainConfigProperty.quorumTestNet), redemptionInvestor.investorPK, redemptionData.tokenContract, redemptionData.redemptionWallet, redemptionData.amount);
        info("Transaction hash is: " + transactionHash);
        endTestLevel();

        startTestLevel("Verify Redemption transaction is present in Securities Transactions");
        loginToControlPanel();
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl) + redemptionData.issuerId + "/" + redemptionData.tokenId + "/transfer-agent/dtl");
        getBrowser().waitForPageStable();
        validateRedemptionTransactionIsPresentInSecuritiesTransactions(transactionHash);
        logoutFromControlPanel();
        endTestLevel();

        startTestLevel("Validate Transaction is present in Transaction History");
        loginToSecuritizeId(redemptionInvestor.email, redemptionInvestor.password);
        navitagateToAssetDetailsPage(tokenName);
        sftAssert.assertTrue(validateTransactionIsPresentInSecId(redemptionData.tokenPrice));
        logoutFromSecId();
        endTestLevel();

        startTestLevel("Verify investor is present in Redemption");
        loginToControlPanel();
        navigateToDistributionPage(redemptionData.issuerId, redemptionData.tokenId);
        clickLatestDistributionCreatedByName(redemptionData.distributionName);
        validateRedemptionDataByRowNumber(1);
        assertDistributionStatus("Pending");
        closeRedemptionById(redemptionData.distributionId);
        getBrowser().refreshPage();
        endTestLevel();

        startTestLevel("Submitting new CSV and closing redemption");
        validatePaymentAmountIsExpected(redemptionData.initialPaymentAmount);
        String csvPath;
        // TODO move this to test data class.
        csvPath = ResourcesUtils.getResourcePathByName("images" + File.separator + MainConfig.getProperty(MainConfigProperty.environment) + "RedemptionCSV.csv");

        uploadNewCSV(csvPath);
        validateCSVDataIsLoaded(redemptionData.tokens, redemptionData.giveBackTokens, redemptionData.payment);
        confirmRedemption();
        getBrowser().refreshPage();
        getBrowser().waitForPageStable();
        assertDistributionStatus("Completed");
        logoutFromControlPanel();
        endTestLevel();

        startTestLevel("Validate Redeemed Tokens email is sent");
        //Emails are not going to be sent for a while
        //validatePaymentEmailSent(redemptionInvestor.email);
        endTestLevel();
        startTestLevel("Validate payout is received in both cash account and payouts history");
        loginToSecuritizeId(redemptionInvestor.email, redemptionInvestor.password);
        double payment1 = Double.parseDouble(redemptionData.payment);
        sftAssert.assertEquals(Double.valueOf(String.valueOf((((Double.parseDouble(initialCashAccountBalance) * 100.00) / 100.00 + (payment1 * 100.00) / 100.00) * 100.00) / 100.00)), Double.valueOf(getCurrentCashAccountBalance()) * 100.00 / 100.00);
        navitagateToAssetDetailsPage(tokenName);
        validateNewRecordInPaymentHistory(tokenName, redemptionData.payment);
        validateRedemptionOpenModalIsNoLongerDisplayed(redemptionData.tokenPrice);
        goToRedemptionPage(redemptionData.distributionId);
        validateRedemptionIsClosed();
        logoutFromSecId();
        endTestLevel();
        sftAssert.assertAll();

    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        if (testDistributionId != null) {
            closeRedemptionById(testDistributionId);
        }
    }

}
