package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.MetaMaskEthereumNetwork;
import io.securitize.infra.wallets.UsingMetaMask;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskConnectWithMetaMask;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskHomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskWelcomePage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;


public class AUT181_SID_Add_Wallet_Via_UI extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT181 SecuritizeId Investor Including Add wallet")
    @UsingMetaMask
    public void AUT181_SID_Add_Wallet_Via_UI_Test() {

        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);

        startTestLevel("Create a verified investor");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        endTestLevel();

        startTestLevel("Login using email and password");
        getBrowser().navigateTo(sidURL);
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeLogin
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard
                .clickSkipTwoFactor();
        securitizeLogin.
                waitForSIDToLogin();
        endTestLevel();

        startTestLevel("Add new wallet to MetaMask");
        MetaMaskWelcomePage welcomePage = MetaMaskWelcomePage.openMetaMaskInNewTab(getBrowser());
        MetaMaskHomePage homePage = welcomePage.performFullSetup(Users.getProperty(UsersProperty.defaultPassword));
        String walletAddress = homePage
                .pickNetwork(MetaMaskEthereumNetwork.Mainnet.toString())
                .getWalletAddress();
        info("Wallet address: " + walletAddress);
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        endTestLevel();

        startTestLevel("Click wallets tab, Register wallet(Ethereum) & Metamask");
        int originalNumberOfWindows = getBrowser().getNumberOfWindows();
        securitizeIdDashboard
                .walletMenuClick()
                .clickRegisterWallet()
                .selectMetaMask();
        endTestLevel();

        startTestLevel("Add the wallet from Metamask");
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows + 1);
        getBrowser().switchToLatestWindow();
        MetaMaskConnectWithMetaMask connectWithMetaMaskPage = new MetaMaskConnectWithMetaMask(getBrowser());
        connectWithMetaMaskPage.connectAndSign();
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows);
        getBrowser().switchToFirstWindow();

        String walletDetailsLabel = securitizeIdDashboard.getWalletDetailsLabel();
        System.out.println("walletDetailsLabel is: " + walletDetailsLabel);
        Assert.assertTrue(walletDetailsLabel.toLowerCase().contains("metamask 1"), "Attached wallet address should match MetaMask created wallet. This is not the case");
        endTestLevel();
    }

}
