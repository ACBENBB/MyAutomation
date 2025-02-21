package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.IssuersAPI;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.BlockchainType;
import io.securitize.infra.utils.SkipTestOnStabilization;
import io.securitize.infra.utils.SshRemoteExecutor;
import io.securitize.infra.utils.UseLegacyWalletDetails;
import io.securitize.infra.wallets.UsingMetaMask;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskConnectWithMetaMask;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskHomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskWelcomePage;
import io.securitize.scripts.GetEthGasPrice;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractDelayedUiTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT130_SID_Investor_Pays extends AbstractDelayedUiTest {

    @Test(description = "AUT130 - Register investor including register wallet with money, execute TBE issuance, " +
            "authorize asset using Investor Pays & transfer tokens from TBE to the asset-authorized wallet")
    @UsingMetaMask
    @UseLegacyWalletDetails
    @SkipTestOnStabilization
    public void AUT130_SID_Investor_Pays_Test() throws Exception {

        String issuerName = "Nie";
        int tokensIssuancedAmount = 5;
        int maxAllowedGasPriceGwei = 60;

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
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.invPaysETHTokenName);
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        investorsAPI.setAccreditationStatus(issuerInvestorId, issuerName, "confirmed");
        investorsAPI.setQualificationStatus(issuerInvestorId, issuerName, tokenId, "confirmed", "");
        endTestLevel();


        // phase 4 - use API to perform TBE issuance
        startTestLevel("Do a TBE issuance");
        String walletName = "CP test wallet: " + BlockchainType.ETHEREUM.name();
        IssuersAPI issuersAPI = new IssuersAPI();
        String internalInvestorId = investorsAPI.getInvestorIdFromInvestorEmail(issuerName, investorDetails.getEmail());
        int issuanceId = issuersAPI.performFullIssuance(BlockchainType.ETHEREUM, issuerName, internalInvestorId, walletName, issuedTokenName, "treasury", tokensIssuancedAmount);
        endTestLevel();


        startTestLevel("Waiting TBE issuance to be success");
        // force cron jobs to run now - to make transaction status to become 'success' faster
        SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");
        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        investorsAPI.waitForTransactionSuccess(issuerName, internalInvestorId, issuanceId, tokenId, waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady * 1000);
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
        endTestLevel();


        // add eth price check before import wallet and transfer funds
        startTestLevel("Eth price check");
        int suggestBaseFee = GetEthGasPrice.getGasViaApi();
        info("suggestBaseFee: " + suggestBaseFee);
        if (suggestBaseFee > maxAllowedGasPriceGwei) {
            Assert.fail("Current ETH rate of " + suggestBaseFee + " is too high - required rate to run test is " + maxAllowedGasPriceGwei);
        }
        endTestLevel();


        startTestLevel("MetaMask: Import wallet and transfer funds");
        String importedWalletPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.investorPaymentWalletPrivateKey);
        homePage
                .importWalletAndSendFounds(importedWalletPrivateKey, 0.003, walletAddress)
                .selectAccountByName("Account 1");
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
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows + 1, 45);
        getBrowser().switchToLatestWindow();
        MetaMaskConnectWithMetaMask connectWithMetaMaskPage = new MetaMaskConnectWithMetaMask(getBrowser());
        connectWithMetaMaskPage.connectAndSign();
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows,45);
        getBrowser().switchToFirstWindow();
        endTestLevel();


        startTestLevel("Authorize Asset and wait wallet status to be ready");
        SecuritizeIdAddWallet securitizeIDAddWalletSecond = new SecuritizeIdAddWallet(getBrowser());
        securitizeIDAddWalletSecond
                .waitForAndClickFirstAsset()
                .authorizeAssetsMetamaskClick();
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows + 1, 300);
        getBrowser().switchToLatestWindow();
        connectWithMetaMaskPage.clickConfirm();

        getBrowser().switchToFirstWindow();
        securitizeIDAddWalletSecond
                .clickAuthorizeAssetGotItButton();
        securitizeIDAddWalletSecond.waitForWalletToBeReady();

        String walletStatus = investorsAPI.getTokensAndWallets(issuerName, internalInvestorId, tokenId)
                .getJSONArray("wallets")
                .getJSONObject(0)
                .getString("status");
        info(String.format("walletStatus is: %s", walletStatus));
        Assert.assertEquals(walletStatus, "ready", "Wallet status extracted from method getTokensAndWallets should match expected wallet status. This is not the case");
        endTestLevel();


        startTestLevel("Reallocate tokens from TBE to the asset-authorized wallet");
        securitizeIDDashboardSecond.clickHomeLink();
        getBrowser().refreshPage();
        SecuritizeIdPortfolio securitizeIdPortfolio = new SecuritizeIdPortfolio(getBrowser());
        securitizeIdPortfolio.clickAsset();
        SecuritizeIdAssetDetailsPage securitizeIdAssetDetailsPage = new SecuritizeIdAssetDetailsPage(getBrowser());
        securitizeIdAssetDetailsPage.clickWalletsTab()
                .clickWithdraw()
                .fillWithdrawAmount(String.valueOf(tokensIssuancedAmount))
                .clickTransfer()
                .transferAssetsMetamaskClick();
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows + 1, 300);
        getBrowser().switchToLatestWindow();

        connectWithMetaMaskPage.clickConfirm();
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows,45);
        getBrowser().switchToFirstWindow();
        securitizeIdAssetDetailsPage.clickGotIt();
        securitizeIdAssetDetailsPage.waitForWalletToContainTokensTransferredFromTBE();

        int tokensHeldInWallet = investorsAPI.getTokensAndWallets(issuerName, internalInvestorId, tokenId)
                .getJSONArray("wallets")
                .getJSONObject(0)
                .getInt("tokensHeld");
        info(String.format("tokensHeldInWallet is: %s", tokensHeldInWallet));
        Assert.assertEquals(tokensHeldInWallet, 5, "Wallet tokens held extracted from method getTokensAndWallets should match expected wallet tokens held. This is not the case");
        endTestLevel();
    }
}