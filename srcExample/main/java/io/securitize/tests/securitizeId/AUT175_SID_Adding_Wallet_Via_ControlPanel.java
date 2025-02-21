package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.utils.EthereumUtils;
import io.securitize.infra.wallets.EthereumWallet;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpInvestorDetailsPage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT175_SID_Adding_Wallet_Via_ControlPanel extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT175 - creating SecuritizeId Investor Including Add wallet via CP")
    public void AUT175_SID_Adding_Wallet_Via_ControlPanel_Test() {

        startTestLevel("Create a verified investor");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorId = investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        endTestLevel();

        startTestLevel("CP login & navigating to SID's investor page");
        cpLoginUsingEmailAndPassword();
        MainConfigProperty env = MainConfigProperty.environment;
        String environment = MainConfig.getProperty(env);
        info("environment is: " +  environment);
        String investorUrl = "https://cp." + environment + ".securitize.io/securitize-id/" + investorId;
        getBrowser().navigateTo(investorUrl);
        endTestLevel();

        startTestLevel("Generate a wallet");
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        EthereumWallet ethereumWallet = EthereumUtils.generateRandomWallet();
        if (ethereumWallet == null) return; // should never happen due to error_an_stop within
        String walletAddress = ethereumWallet.getAddress();
        String walletName = "CP test wallet";
        info("New wallet address: " + walletAddress);
        endTestLevel();

        startTestLevel("CP: add a wallet");
        investorDetailsPage.clickAddWallet()
                .typeName(walletName)
                .typeAddress(walletAddress)
                .clickOk();
        String walletDetailsLabelCP = investorDetailsPage.getWalletDetailsLabelCP();
        info("walletDetailsLabelCP is: " + walletDetailsLabelCP);
        Assert.assertTrue(walletDetailsLabelCP.contains("CP test wallet"), "Attached wallet address should match MetaMask CP created wallet. This is not the case");
        endTestLevel();
    }
}


