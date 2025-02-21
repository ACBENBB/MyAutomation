package io.securitize.tests.blackrock;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.IssuersAPI;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.MetaMaskEthereumNetwork;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.utils.DefaultTestNetwork;
import io.securitize.infra.wallets.UsingMetaMask;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskHomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskWelcomePage;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT722_blackrock_hold_trading extends AbstractCpInvestorRegistrationFlow {

    @DefaultTestNetwork(TestNetwork.ETHEREUM_SEPOLIA)
    @Test(description = "AUT722 - blackrock - Freeze the token contract, check transfer isn't possible and then un-freeze it")
    @UsingMetaMask
    public void AUT722_blackrock_hold_trading_test() {

        String issuerName = "BlackRock";
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        String investorWalletPrivateKey = Users.getProperty(UsersProperty.blackrockHolderWalletPrivateKey);
        String recipientAddress = Users.getProperty(UsersProperty.blackrockAuthorizedRecipientAddress);


        startTestLevel("API - Check contract status");
        IssuersAPI issuersAPI = new IssuersAPI();
        String contractStatus = issuersAPI.getContractStatus(issuerId, tokenId);
        if (contractStatus.equals("on-hold")) {
            issuersAPI.performHoldTrading(issuerName, tokenId, false);
            contractStatus = issuersAPI.getContractStatus(issuerId, tokenId);
        }
        Assert.assertEquals(contractStatus, "operational", "Token status isn't operational, it's: " + contractStatus);
        endTestLevel();


        startTestLevel("API - Freeze token contract");
        issuersAPI.performHoldTrading(issuerName, tokenId, true);
        endTestLevel();


        startTestLevel("MetaMask - setup");
        MetaMaskWelcomePage welcomePage = MetaMaskWelcomePage.openMetaMaskInNewTab(getBrowser());
        MetaMaskHomePage homePage = welcomePage.performFullSetup(Users.getProperty(UsersProperty.defaultPassword));
        endTestLevel();


        startTestLevel("MetaMask - Check that the token is frozen");
        String tokenAddress = issuersAPI.getTokenAddress(issuerName, tokenId);
        boolean isTokenFrozen = homePage
                .pickNetwork(MetaMaskEthereumNetwork.Sepolia.toString())
                .importWallet(investorWalletPrivateKey)
                .importToken(tokenAddress, issuedTokenName)
                .isTokenFrozen(1, recipientAddress);
        Assert.assertTrue(isTokenFrozen, "Token contract isn't frozen");
        homePage.clickCancelButton();
        endTestLevel();


        startTestLevel("API - Unfreeze token contract");
        issuersAPI.performHoldTrading(issuerName, tokenId, false);
        endTestLevel();


        startTestLevel("MetaMask - Check that the token isn't frozen");
        getBrowser().refreshPage();
        isTokenFrozen = homePage
                .isTokenFrozen(1, recipientAddress);
        Assert.assertFalse(isTokenFrozen, "Token contract is frozen");
        endTestLevel();
    }
}
