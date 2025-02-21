
package io.securitize.tests.blockchain;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.CpTokenConfigurationPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.manageToken.CpActivateTokenPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.manageToken.CpComplianceRulesPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.manageToken.CpDeployTokenContractPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.manageToken.CpManageRolesPage;
import io.securitize.tests.blockchain.abstractClass.AbstractBC;
import io.securitize.tests.blockchain.testData.AUT499_POJO;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT499_ST_ManageTokens_DeployTokens extends AbstractBC {

    // IF RUNNING LOCALLY SET DATA IN AUT499_POJO VARIABLES
    @Test
    public void AUT499_ST_ManageTokens_DeployTokens() {

        String tokenName = generateRandomTokenAUT();

        startTestLevel("create POJO for AUT499");
        AUT499_POJO aut499_pojo = new AUT499_POJO();
        endTestLevel();

        startTestLevel("Login in to CP with AUT Operator");
        loginToCp();
        endTestLevel();

        String url = "https://cp."+MainConfig.getProperty(MainConfigProperty.environment)+".securitize.io/"+aut499_pojo.getIssuerId()+"/"+aut499_pojo.getTokenId()+"/configuration/fundraise-token";

        startTestLevel("Navigate to Token Configuration Page");
        getBrowser().navigateTo(url);
        CpTokenConfigurationPage tokenConfigurationPage = new CpTokenConfigurationPage(getBrowser());
        tokenConfigurationPage.clickAddToken();
        endTestLevel();

        startTestLevel("Add new token Modal");
        tokenConfigurationPage.setTokenName(tokenName);
        tokenConfigurationPage.clickConfirmAddToken();
        endTestLevel();

        startTestLevel("Token Configuration Page");
        tokenConfigurationPage.clickManageToken();
        endTestLevel();

        startTestLevel("Deploy Token Contract Page");
        CpDeployTokenContractPage deployTokenContractPage = new CpDeployTokenContractPage(getBrowser());
        deployTokenContractPage.addTokenTicker(aut499_pojo.getTokenTicker());
        deployTokenContractPage.addTokenDecimals(aut499_pojo.getTokenDecimals());
        deployTokenContractPage.selectNetwork(aut499_pojo.getBlockchainNetwork());
        deployTokenContractPage.setCustodianAddressField(aut499_pojo.getCustodianAddress());
        deployTokenContractPage.clickContinueButton();
        Assert.assertTrue(deployTokenContractPage.isConfirmationToastVisible(), "'Token Description added' Toast is not visible");
        Assert.assertFalse(deployTokenContractPage.isTickerNameErrorToastDisplayed());
        endTestLevel();

        startTestLevel("Compliance Rules Page");
        CpComplianceRulesPage complianceRulesPage = new CpComplianceRulesPage(getBrowser());
        complianceRulesPage.selectComplianceType(aut499_pojo.getComplianceType());
        complianceRulesPage.clickContinueBtn();
        endTestLevel();

        startTestLevel("Manage Roles Page");
        CpManageRolesPage manageRolesPage = new CpManageRolesPage(getBrowser());
        manageRolesPage.addMasterWalletAddress(aut499_pojo.getMasterWalletAddress());
        manageRolesPage.addTbeWallet(aut499_pojo.getTbeWallet());
        manageRolesPage.addRedemptionWallet(aut499_pojo.getRedemptionWallet());
        manageRolesPage.addIssuerWalletField(aut499_pojo.getIssuerWallet());
        manageRolesPage.clickContinueBtn();
        Assert.assertTrue(deployTokenContractPage.isConfirmationToastVisible(), "Toast is not visible");
        endTestLevel();

        startTestLevel("Activate Token Page");
        CpActivateTokenPage activateTokenPage = new CpActivateTokenPage(getBrowser());
        activateTokenPage.clickActivateButton();
        activateTokenPage.clickModalActivateButton();
        activateTokenPage.clickBackToTokenBtn();
        endTestLevel();


        startTestLevel("Token Deployment ");
        CpTokenConfigurationPage configurationPage = new CpTokenConfigurationPage(getBrowser());
        configurationPage.waitForTokenToBeDeployed();
        endTestLevel();

        startTestLevel("Token deploy badge = Deployed");
        Assert.assertTrue(configurationPage.isTokenDeployed());
        endTestLevel();
    }

}

