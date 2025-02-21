package io.securitize.tests.apiTests.abstractClass;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.IssuersAPI;
import io.securitize.infra.api.algorand.AlgorandApi;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.enums.BlockchainType;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.utils.EthereumUtils;
import io.securitize.infra.utils.SshRemoteExecutor;
import io.securitize.infra.wallets.AlgorandWallet;
import io.securitize.infra.wallets.AbstractWallet;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.InvestorDetails;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public abstract class AbstractCpApiRegistration extends AbstractTest {

    public void controlPanelRegistrationFlow(BlockchainType blockchainType, String issuerName, String tokenName, int issuedTokens, int issuedTbeTokens) {

        InvestorsAPI investorsAPI = new InvestorsAPI();
        IssuersAPI issuersAPI = new IssuersAPI();

        // login to CP
        startTestLevel("Login to CP");
        //loginAPI.postLoginControlPanel();
        endTestLevel(false);


        // add new investor
        startTestLevel("Add new investor");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        String investorId = investorsAPI.createFullNewInvestorByIssuer(investorDetails, issuerName, tokenName);
        endTestLevel(false);


        // add wallet to investor
        startTestLevel("add wallet to investor");
        String tokenId = investorsAPI.getTokenIdByName(issuerName, tokenName);
        AbstractWallet wallet = null;
        if (blockchainType == BlockchainType.ETHEREUM) {
            wallet = EthereumUtils.generateRandomWallet();
        } else if (blockchainType == BlockchainType.ALGORAND) {
            AlgorandWallet algoWallet = AlgorandWallet.GenerateWallet();

            // send initial funds to wallet
            AlgorandApi algorandApi = new AlgorandApi();
            // 500000 = 0.5 algo
            String txId = algorandApi.loadWalletWithFundsFromInternalFaucet(algoWallet, 500000);

            // wait for funds to arrive
            algorandApi.waitForConfirmation(txId, 20);

            // enable token for wallet
            String assetIdAsString = Users.getIssuerDetails(issuerName, IssuerDetails.issuedAssetId);
            long assetId = Long.parseLong(assetIdAsString);
            algorandApi.optInAsset(algoWallet, assetId);

            wallet = algoWallet;
        }
        if (wallet == null) return; // should never happen due to error_an_stop within
        String walletAddress = wallet.getAddress();
        String walletName = "CP test wallet: " + blockchainType.name();
        investorsAPI.addWalletToInvestor(issuerName, investorId, walletName, walletAddress, tokenId);

        // force cron jobs to run now - to make wallet status to become 'ready' faster
        String actualEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        List<String> productionEnvironments = Arrays.asList("production", "apac");
        TestNetwork currentTestNetwork = getTestNetwork();
        if ((productionEnvironments.stream().noneMatch(actualEnvironment::equalsIgnoreCase)) && (currentTestNetwork.needsBypass())) {
            SshRemoteExecutor.executeScript("walletRegistrationBypass.sh");
        }

        // wait for wallet to be ready
        int waitTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeWalletToBecomeReadyQuorumSeconds);
        int intervalTimeSecondsWalletReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeWalletToBecomeReadyQuorumSeconds);
        investorsAPI.waitForWalletReady(issuerName, investorId, walletName, tokenId, waitTimeSecondsWalletReady, intervalTimeSecondsWalletReady * 1000);
        endTestLevel(false);


        // add two issuance: regular and treasury
        startTestLevel("add two issuance: regular and treasury");
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        Map<String, Integer> issuanceToPerform = new HashMap<>();
        issuanceToPerform.put("wallet", issuedTokens);
        if (issuedTbeTokens > 0) {
            issuanceToPerform.put("treasury", issuedTbeTokens);
        }
        for (String currentIssuance : issuanceToPerform.keySet()) {
            int issuanceId = issuersAPI.performFullIssuance(blockchainType, issuerName, investorId, walletName, tokenName, currentIssuance, issuanceToPerform.get(currentIssuance));

            // Wait for transaction status to become 'success'
            // force cron jobs to run now - to make transaction status to become 'success' faster
            if ((productionEnvironments.stream().noneMatch(actualEnvironment::equalsIgnoreCase)) && (currentTestNetwork.needsBypass())) {
                SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
            }

            investorsAPI.waitForTransactionSuccess(issuerName, investorId, issuanceId, tokenId, waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady * 1000);
        }
        endTestLevel(false);


        // wait for wallet token balance match to issuance tokens
        startTestLevel("wait for wallet token balance match to issuance tokens");
        investorsAPI.waitForWalletPropertyToEqual(issuerName, investorId, tokenId,
                "tokensHeld", issuedTokens+"",
                waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady * 1000);

        if (issuedTbeTokens > 0) {
            investorsAPI.waitForWalletPropertyToEqual(issuerName, investorId, tokenId,
                    "tokensTreasury", issuedTbeTokens+"",
                    waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady * 1000);
        }
        JSONObject walletInfo = investorsAPI.getInvestorTokensDetails(issuerName, investorId, tokenId);
        int tokensHeld = walletInfo.getInt("tokensHeld");
        int tbeTokensHeld = walletInfo.getInt("tokensTreasury");
        endTestLevel(false);


        // validate correct number of tokens arrived to the wallet
        startTestLevel("Validating correct amount of tokens arrived to wallet");
        SoftAssert softAssertion = new SoftAssert();
        softAssertion.assertEquals(tokensHeld, issuedTokens, "Tokens held don't match number of sent tokens");
        if (issuedTbeTokens > 0) {
            softAssertion.assertEquals(tbeTokensHeld, issuedTbeTokens, "TBE tokens held don't match number of sent tokens");
        }
        softAssertion.assertAll();
        endTestLevel(false);
    }

    public JSONObject cpGetControlBookInfo (String issuerName, String tokenName) {

        InvestorsAPI investorsAPI = new InvestorsAPI();
        IssuersAPI issuersAPI = new IssuersAPI();

        String tokenId = investorsAPI.getTokenIdByName(issuerName, tokenName);

        return issuersAPI.getControlPanelControlBookIssuances(issuerName, tokenId);
    }

    public void validateControlBookTokenAmount (String issuerName, String tokenName, int initialTokenAmount, int issuedTokens, String tokenDescription) {

        SoftAssert softAssertion = new SoftAssert();

        softAssertion.assertEquals(initialTokenAmount + issuedTokens, cpGetControlBookInfo(issuerName, tokenName).getInt(tokenDescription), tokenDescription + " amount in Control Book doesn't match");
        softAssertion.assertAll();
    }
}
