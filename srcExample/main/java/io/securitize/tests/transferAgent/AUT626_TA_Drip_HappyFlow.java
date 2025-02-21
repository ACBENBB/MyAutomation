package io.securitize.tests.transferAgent;

import io.securitize.tests.transferAgent.abstractClass.Drip;
import io.securitize.tests.transferAgent.testData.DistributionData;
import org.testng.annotations.Test;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT626_TA_Drip_HappyFlow extends Drip {

    DistributionData distributionData;
    int totalInvestorsCount;
    LinkedHashMap<String, String> initialInvestorsTokenBalance;
    BigDecimal totalTokensToBeIssuedFromCsv = BigDecimal.ZERO;
    String uploadCsvFilePath;

    @Test(description = "TA - DRIP HAPPY FLOW")
    public void AUT626_TA_DRIP_HappyFlow_Test() throws IOException {

        // https://securitize.atlassian.net/browse/TA-3008

        startTestLevel("Creating Distribution Test Data Object");
        distributionData = createDistributionDataObject(DistributionType.DISTRIBUTION_TYPE_DIVIDEND_DRIP_HAPPY_FLOW.toString());
        endTestLevel();

        startTestLevel("Get Total Number Of Issued Securities from Control Book");
        distributionData.initialTotalNumberOfIssuedSecurities = getTotalNunmberOfIssuedSecurities(distributionData);
        endTestLevel();

        startTestLevel("Login as CP and Go to distribution page");
        loginToControlPanel();
        navigateToDistributionPage(
                distributionData.issuerId,
                distributionData.tokenId);
        endTestLevel();

        startTestLevel("PRE  CONDITION - Navigate To Signatures page");
        navigateToSignaturesPage();
        endTestLevel();

        startTestLevel("PRE CONDITION - Sign All Tx if there are");
        clearAllPendingTransactions(distributionData);
        endTestLevel();

        startTestLevel("Navigate To Distribution page");
        navigateToDistributionPage(
                distributionData.issuerId,
                distributionData.tokenId);
        endTestLevel();

        startTestLevel("Generate Distribution");
        generateDistribution(distributionData);
        clickLatestDistributionCreatedByName(distributionData.distributionName);
        endTestLevel();

        startTestLevel("Wait for Distribution Dividend Status is Ready To Distribute");
        String statusToWait = "Ready to distribute";
        waitForDistributionStatus(statusToWait);
        endTestLevel();

        startTestLevel("Download Dividend CSV");
        downloadCSVFromDistributionDetailsPage();
        endTestLevel();

        startTestLevel("Get Total Investors Count for Signatures validation");
        totalInvestorsCount = getTotalInvestorsCount();
        endTestLevel();

        startTestLevel("Get Initial Token Balance from CSV Investors");
        initialInvestorsTokenBalance = getInitialInvestorsTokenBalance();
        endTestLevel();

        startTestLevel("Write Dividend CSV with Reinvest PayoutMethod");
        uploadCsvFilePath = writePayoutMethodCSVColumn();
        endTestLevel();

        startTestLevel("Upload New Dividend CSV File");
        uploadNewCSV(uploadCsvFilePath);
        endTestLevel();

        startTestLevel("Wait for Reinvest is populated in the Distribution Details List");
        waitForCSVLoadedInUI();
        waitForUIUpdateAfterCsvUpload();
        endTestLevel();

        // GET TOKENS TO BE ISSUED AFTER UPLOADING THE REINVEST CSV
        startTestLevel("Get Total Tokens to Be Issued from Distribution UI");
        distributionData.totalTokensToBeIssued = getTotalTokensToBeIssuedFromDistributionUI();
        endTestLevel();

        // TODO getTokensToBeIssuedFromCSV
        // TODO assertEquals tokensToBeIssuedFromCSV vs tokensToBeIssuedFromDistUI

        startTestLevel("Click on Confirm Distribution");
        clickConfirmDistribution();
        endTestLevel();

        startTestLevel("Navigate To Signatures page");
        navigateToSignaturesPage();
        endTestLevel();

        startTestLevel("Wait TXs List To Be Fully Loaded");
        waitForTransactionsCount(totalInvestorsCount);
        endTestLevel();

        startTestLevel("Wait for TXs and Sign them");
        signAllPendingTransactions(distributionData);
        endTestLevel();

        startTestLevel("Get Total Number Of Issued Securities from Control Book");
        assertTotalNumberOfIssuedSecurities(distributionData);
        endTestLevel();

    }

}