package io.securitize.tests.blackrock;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.IssuersAPI;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.enums.BlockchainType;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.DefaultTestNetwork;
import io.securitize.infra.utils.KafkaHelper;
import io.securitize.infra.utils.RandomGenerator;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.DistributionDetailsPage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.transferAgent.abstractClass.AbstractDistributions;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT663_blackrock_accrued_dividend extends AbstractDistributions {
    @DefaultTestNetwork(TestNetwork.ETHEREUM_SEPOLIA)
    @Test(description = "AUT663 - blackrock - create accrued dividend distribution and verify payout rate")
    public void AUT663_blackrock_accrued_dividend_test() {

        String issuerName = "BlackRock";
        int investmentAmount = 5000000;
        InvestorsAPI investorsAPI = new InvestorsAPI();
        IssuersAPI issuersAPI = new IssuersAPI();
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        String walletName = "Automation wallet " + issuerName;
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();


        startTestLevel("API: Create an investor, perform an issuance and verify it succeeded");
        String issuerInvestorId = investorsAPI.createVerifiedIssuerInvestorWithWallet(investorDetails, issuerName, tokenId, walletName);
        JSONObject walletDetails = investorsAPI.getWalletDetails(issuerName, issuerInvestorId, walletName, tokenId);
        Assert.assertEquals(walletDetails.getString("status"), "syncing-investor-pays", "The wallet status should be syncing-investor-pays");
        investorsAPI.createInvestmentUSD(investorDetails, issuerName, tokenId);
        int issuanceId = issuersAPI.performFullIssuance(BlockchainType.ETHEREUM, issuerName, issuerInvestorId, walletName, issuedTokenName, "wallet", investmentAmount);
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTokensToArriveInWalletEthereumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTokensToArriveInWalletEthereumSeconds);
        investorsAPI.waitForTransactionSuccess(issuerName, issuerInvestorId, issuanceId, tokenId, waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady * 1000);
        endTestLevel();


        startTestLevel("API: Create a new snapshot");
        String snapshotName = "blk_snapshot_" + RandomGenerator.randomString(8);
        int snapshotId = issuersAPI.createSnapshot(issuerId, tokenId, snapshotName);
        endTestLevel();


        startTestLevel("API: Create an Accrual Dividend Distribution");
        String distributionId = issuersAPI.createAccrualDividendDistribution(issuerId, tokenId, snapshotId);
        int originalDistributionInvestorCount = issuersAPI.getDistributionInvestorsCount(distributionId);
        info(String.format("Distribution %s has %s investors", distributionId, originalDistributionInvestorCount));
        Assert.assertEquals(originalDistributionInvestorCount, 0, "Initially we expect the new distribution to have 0 investors");
        endTestLevel();


        startTestLevel("API: Produce a isr.snapshot.created Kafka event");
        JSONObject bodySnapshot = new JSONObject()
                .put("id", snapshotId)
                .put("issuerId", issuerId)
                .put("tokenId", tokenId)
                .put("date", DateTimeUtils.currentDateTimeUTC())
                .put("label", "accrued-dividend");

        JSONObject bodyWrapperSnapshot = new JSONObject()
                .put("type", "JSON")
                .put("data", bodySnapshot);

        JSONObject keyWrapperSnapshot = new JSONObject()
                .put("type", "JSON")
                .put("data", snapshotId);

        String fullBodySnapshot = new JSONObject()
                .put("key", keyWrapperSnapshot)
                .put("value", bodyWrapperSnapshot).toString();

        KafkaHelper.produceKafkaEvent("isr.snapshot.created", fullBodySnapshot);
        endTestLevel();


        startTestLevel("UI: Open the accrual dividend distribution");
        loginToControlPanel();
        String distributionURL = "https://cp." + MainConfig.getProperty(MainConfigProperty.environment) + ".securitize.io/" + issuerId + "/" + tokenId + "/distributions/" + distributionId;
        getBrowser().navigateTo(distributionURL);
        endTestLevel();


        startTestLevel("API: Produce a ta.payout-rate.received Kafka event");
        double payoutRate = Math.random() / 1000;
        JSONObject bodyPayoutRate = new JSONObject()
                .put("tokenId", tokenId)
                .put("payoutRate", payoutRate)
                .put("recordDate", DateTimeUtils.currentDateTimeUTC());

        JSONObject bodyWrapperPayoutRate = new JSONObject()
                .put("type", "JSON")
                .put("data", bodyPayoutRate);

        String fullBodyPayoutRate = new JSONObject()
                .put("value", bodyWrapperPayoutRate).toString();

        KafkaHelper.produceKafkaEvent("ta.payout-rate.received", fullBodyPayoutRate);
        endTestLevel();


        startTestLevel("UI: The accrual dividend distribution should have investors and the new investor should have the correct amount in 'Gross Amount'");
        DistributionDetailsPage distributionPage = new DistributionDetailsPage(getBrowser());
        distributionPage.waitForInvestorsDistributionToExist();
        distributionPage.waitForTokensToBeIssuedToLoad();
        distributionPage.searchDistributionInvestor(investorDetails.getEmail());
        String grossAmount = distributionPage.getSingleRowGrossAmountValueText().replace(",", "");
        String expectedGrossAmount = "$" + String.format("%.2f", payoutRate * investmentAmount);
        Assert.assertEquals(expectedGrossAmount, grossAmount, "The investor's gross amount should equal the investment amount multiplied by the payout rate");
        endTestLevel();
    }
}
