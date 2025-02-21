package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.IssuersAPI;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.BlockchainType;
import io.securitize.infra.utils.SshRemoteExecutor;
import io.securitize.infra.utils.UseLegacyWalletDetails;
import io.securitize.infra.wallets.UsingMetaMask;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskConnectWithMetaMask;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskHomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskWelcomePage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractDelayedUiTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT133_SID_Single_Txn_Issuer_Pays extends AbstractDelayedUiTest {

    @Test(description = "AUT133 - Register investor including register wallet, execute TBE issuance, " +
            "execute single txn (authorize asset using Issuer Pays & transfer tokens from TBE to the asset-authorized wallet)")
    @UseLegacyWalletDetails
    @UsingMetaMask
    public void AUT133_SID_Single_Txn_Issuer_Pays_Test() throws Exception {

        String issuerName = "Nie";
        int tokensIssuancedAmount = 5;

        // phase 1 - create investor via API
        startTestLevel("Create investor");
        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorId = investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        endTestLevel();


        // phase 2 - use API to connect investor with issuer
        startTestLevel("Use API to connect investor with issuer");
        String investorCode = investorsAPI.getInvestorCode(issuerName, investorId);
        investorsAPI.authorizeIssuerForInvestor(issuerName, investorCode);
        endTestLevel();


        // phase 3 - set accreditation & qualification statuses to 'confirmed' via API
        startTestLevel("Set accreditation & qualification statuses to confirmed");
        // wait for userId to be return
        int waitTimeUseIdReturnSeconds = MainConfig.getIntProperty(MainConfigProperty.waitTimeUseIdReturnSeconds);
        int intervalTimeUserIdReturnSeconds = MainConfig.getIntProperty(MainConfigProperty.intervalTimeUserIdReturnSeconds);
        String issuerInvestorId = investorsAPI.getIssuersUserIdBySecuritizeIdProfileId(investorId, waitTimeUseIdReturnSeconds, intervalTimeUserIdReturnSeconds);
        // set accreditation & qualification statuses
        investorsAPI.setAccreditationStatus(issuerInvestorId, issuerName, "confirmed");
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuerPaysETHTokenName);
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        investorsAPI.setQualificationStatus(issuerInvestorId, issuerName, tokenId, "confirmed", "");
        endTestLevel();


        // phase 4 - use API to perform TBE issuance
        startTestLevel("Do a TBE issuance");
        String walletName = "CP test wallet: " + BlockchainType.ETHEREUM.name();
        IssuersAPI issuersAPI = new IssuersAPI();
        int issuanceId = issuersAPI.performFullIssuance(BlockchainType.ETHEREUM, issuerName, issuerInvestorId, walletName, issuedTokenName, "treasury", tokensIssuancedAmount);
        endTestLevel();


        startTestLevel("Waiting TBE issuance to be success");
        // force cron jobs to run now - to make transaction status to become 'success' faster
        SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        investorsAPI.waitForTransactionSuccess(issuerName, issuerInvestorId, issuanceId, tokenId, waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady * 1000);
        endTestLevel(false);


        // phase 5 - open the browser for UI validation
        startTestLevel("Open browser");
        openBrowser(getCurrentTestMethod(), null);
        endTestLevel();


        // phase 6 - execute the test flow via UI
        startTestLevel("Add new wallet to MetaMask");
        MetaMaskWelcomePage welcomePage = MetaMaskWelcomePage.openMetaMaskInNewTab(getBrowser());
        MetaMaskHomePage homePage = welcomePage.performFullSetup(Users.getProperty(UsersProperty.defaultPassword));
        String walletAddress = homePage.getWalletAddress();
        info("Wallet address: " + walletAddress);
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        endTestLevel();


        startTestLevel("Login using email and password to SID");
        getBrowser().navigateTo(sidURL);
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeLogin
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard
                .clickSkipTwoFactor();
        securitizeLogin.waitForSIDToLogin();
        endTestLevel();


        startTestLevel("Add a wallet");
        int originalNumberOfWindows = getBrowser().getNumberOfWindows();
        getBrowser().navigateTo(sidURL);
        SecuritizeIdDashboard securitizeIDDashboardSecond = new SecuritizeIdDashboard(getBrowser());
        SecuritizeIdAddWallet securitizeIDAddWallet = securitizeIDDashboardSecond.walletMenuClick();
        securitizeIDAddWallet
                .clickRegisterWallet()
                .selectMetaMask();
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows + 1);
        getBrowser().switchToLatestWindow();
        MetaMaskConnectWithMetaMask connectWithMetaMaskPage = new MetaMaskConnectWithMetaMask(getBrowser());
        connectWithMetaMaskPage.connectAndSign();
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows);
        getBrowser().switchToFirstWindow();
        endTestLevel();


        startTestLevel("Single txn: Authorize Asset & Reallocate tokens from TBE to the asset-authorized wallet");
        securitizeIDDashboardSecond.clickHomeLink();
        getBrowser().refreshPage();
        SecuritizeIdPortfolio securitizeIdPortfolio = new SecuritizeIdPortfolio(getBrowser());
        securitizeIdPortfolio.clickAsset();
        SecuritizeIdAssetDetailsPage securitizeIdAssetDetailsPage = new SecuritizeIdAssetDetailsPage(getBrowser());
        securitizeIdAssetDetailsPage.clickWalletsTab()
                .clickWithdraw()
                .fillWithdrawAmount(String.valueOf(tokensIssuancedAmount))
                .clickTransfer();
//                .clickGotIt();
        getBrowser().refreshPage();

        //sign pending authorize & reallocation txn (ReallocateTokens)
        String investorExternalId = investorsAPI.getInvestorDetails(issuerName, issuerInvestorId, "externalId");
        String transactionId = String.valueOf(issuersAPI.getBlockchainTransactionIdByInvestorExternalId(investorExternalId, issuerName, issuedTokenName));
        issuersAPI.signTransaction(BlockchainType.ETHEREUM, issuerName, issuedTokenName, transactionId, "ReallocateTokens");

        securitizeIdAssetDetailsPage.waitForWalletToContainTokensTransferredFromTBE();

        int tokensHeldInWallet = investorsAPI.getTokensAndWallets(issuerName, issuerInvestorId, tokenId)
                .getJSONArray("wallets")
                .getJSONObject(0)
                .getInt("tokensHeld");
        info(String.format("tokensHeldInWallet is: %s", tokensHeldInWallet));
        Assert.assertEquals(tokensHeldInWallet, 5, "Wallet tokens held extracted from method getTokensAndWallets should match expected wallet tokens held. This is not the case");
        endTestLevel();
    }
}

