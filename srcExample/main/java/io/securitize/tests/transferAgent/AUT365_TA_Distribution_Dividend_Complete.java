package io.securitize.tests.transferAgent;

import io.securitize.tests.transferAgent.abstractClass.AbstractDistributions;
import io.securitize.tests.transferAgent.testData.*;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT365_TA_Distribution_Dividend_Complete extends AbstractDistributions {

    @Test(description = "AUT365_TA_Distribution_Dividend_Complete")
    public void AUT365_TA_Distribution_Dividend_Complete() {

        AUT365 aut365 = new AUT365();

        startTestLevel("Creating Investor Test Data Object");
        aut365.investorData = createDistributionInvestorDataObject(DistributionsInvestorType.DISTRIBUTION_AUT365_TESTDATA.toString());
        endTestLevel();

        startTestLevel("Creating Distribution Test Data Object");
        aut365.distributionData = createDistributionDataObject(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND.toString());
        endTestLevel();

        startTestLevel("Creating Snapshot for Distribution");
        createSnapshotViaApi(aut365.issuerId, aut365.tokenId, "AUT365 Snapshot");
        endTestLevel();

        startTestLevel("Get Test Investor Initial Cash Account Balance");
        aut365.investorData.initialInvestorCashAccountBalance = getCashAccountBalance(aut365.investorData.secId);
        endTestLevel();

        startTestLevel("Get Issuer Initial Cash Account Balance");
        aut365.distributionData.issuerInitialAccountBalance = getCashAccountBalance(aut365.issuerIdCashAccountId);
        endTestLevel();

        startTestLevel("Login to CP and Navigate to Distribution Page");
        loginToControlPanel();
        navigateToDistributionPage(aut365.issuerId, aut365.tokenId);
        endTestLevel();

        startTestLevel("Create Distribution type Dividend");
        generateDistribution(aut365.distributionData);
        clickLatestDistributionCreatedByName(aut365.distributionData.distributionName);
        aut365.distributionData.distributionId = getDistributionIdFromUrl();
        aut365.distributionData.fundingNeeds = getDistributionFundingNeeds();
        clickConfirmDistribution();
        logoutFromControlPanel();
        // TODO validateEmailSent(investorData.email, investorData.testInvestorType, null);
        endTestLevel();

        startTestLevel("Validate distribution amount has been locked from issuer account");
        validateDistributionAmountLockedInIssuerAccount(aut365.distributionData);
        endTestLevel();

        startTestLevel("Load Dividend CSV Data");
        loadCSVDistributionData(aut365.distributionData, aut365.issuerId, aut365.tokenId);
        endTestLevel();

        startTestLevel("Validate Investor Payment was Performed");
        validateDividendPayout(aut365.investorData, aut365.investorData.initialInvestorCashAccountBalance);
        logoutFromControlPanel();
        endTestLevel();

        startTestLevel("Validate Distribution is Completed");
        validateCompletedDistribution(aut365.distributionData.distributionId);
        endTestLevel();

    }

}