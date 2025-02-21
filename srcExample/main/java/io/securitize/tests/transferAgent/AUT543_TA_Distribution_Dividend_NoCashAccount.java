package io.securitize.tests.transferAgent;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.transferAgent.abstractClass.AbstractDistributions;
import io.securitize.tests.transferAgent.testData.DistributionData;
import io.securitize.tests.transferAgent.testData.DistributionsInvestorData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT543_TA_Distribution_Dividend_NoCashAccount extends AbstractDistributions {

    String issuerId;
    String tokenId;
    DistributionsInvestorData investorData;
    DistributionData distributionData;

    @BeforeMethod
    public void AUT543_Initialize_TestData() {
        issuerId = Users.getProperty(UsersProperty.ta_distribution_issuerId_aut543);
        tokenId = Users.getProperty(UsersProperty.ta_distribution_tokenId_aut543);
        investorData = createDistributionInvestorDataObject(AbstractDistributions.DistributionsInvestorType.DISTRIBUTION_AUT543_TESTDATA.toString());
        distributionData = createDistributionDataObject(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND.toString());
        distributionData.issuerInitialAccountBalance = getCashAccountBalance("");
        createSnapshotViaApi(issuerId, tokenId, "AUT543 Snapshot");
    }

    @Test(description = "AUT543_TA_Distribution_Dividend_NoCashAccount")
    public void AUT543_TA_Distribution_Dividend_NoCashAccount() {

        startTestLevel("Create a Distribution type Dividend");
        loginToControlPanel();
        navigateToDistributionPage(issuerId, tokenId);
        generateDistribution(distributionData);
        clickLatestDistributionCreatedByName(distributionData.distributionName);
        distributionData.distributionId = getDistributionIdFromUrl();
        distributionData.totalAmount = getDistributionTotalAmount();
        clickConfirmDistribution();
        logoutFromControlPanel();
        validateEmailSent(investorData.email, investorData.testInvestorType, null);
        endTestLevel();

        startTestLevel("Validate distribution amount has been locked from issuer account");
        validateDistributionAmountLockedInIssuerAccount(distributionData);
        endTestLevel();

        startTestLevel("Load Dividend CSV Data");
        loadCSVDistributionData(distributionData, issuerId, tokenId);
        endTestLevel();

        startTestLevel("Validate distribution payments");
        validateDividendPayout(investorData, distributionData.issuerInitialAccountBalance);
        validateCompletedDistribution(distributionData.distributionId);
        endTestLevel();

    }

}