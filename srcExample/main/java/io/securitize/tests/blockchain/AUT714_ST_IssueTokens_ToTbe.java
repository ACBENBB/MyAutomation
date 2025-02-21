package io.securitize.tests.blockchain;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpAddIssuance;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpInvestorDetailsPage;
import io.securitize.tests.blockchain.abstractClass.AbstractBC;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;


public class AUT714_ST_IssueTokens_ToTbe extends AbstractBC {
    @Test
    public void AUT714_ST_IssueTokens_ToTbe_test() {
        int expectedBalance;
        String url;

        startTestLevel("instance all user properties for AUT714");
        String aut714_tokenId = Users.getProperty(UsersProperty.aut714_tokenId);
        String aut714_issuerId = Users.getProperty(UsersProperty.aut714_issuerId);
        String aut714_investorId = Users.getProperty(UsersProperty.aut714_investorId);
        String aut714_userFirstName = Users.getProperty(UsersProperty.aut714_userFirstName);
        String aut714_signerAddress = Users.getProperty(UsersProperty.aut714_signerAddress);
        String aut714_privateKey = Users.getProperty(UsersProperty.aut714_privateKey);
        int aut714_tokenAmount = 1;
        endTestLevel();

        startTestLevel("Get investor Balance");
        expectedBalance = Integer.parseInt(getTokenBalancesByType(aut714_issuerId, aut714_investorId,aut714_tokenId,"tokensTreasury")) + aut714_tokenAmount;
        info("Expected Balance is --> "+ expectedBalance);
        endTestLevel();

        startTestLevel("Login in to CP with AUT Operator");
        loginToCp();
        endTestLevel();

        startTestLevel("Navigate to investor page and add issuance");
        url = "https://cp." + MainConfig.getProperty(MainConfigProperty.environment) + ".securitize.io/" + aut714_issuerId + "/" + aut714_tokenId + "/investors/details/"+ aut714_investorId +"?from=onboarding";
        getBrowser().navigateTo(url);
        CpInvestorDetailsPage cpInvestorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        cpInvestorDetailsPage.clickAddIssuance();
        endTestLevel();

        startTestLevel("Issue to wallet");
        CpAddIssuance cpAddIssuance = new CpAddIssuance(getBrowser());
        cpAddIssuance.typeIssuanceAmount(aut714_tokenAmount);
        cpAddIssuance.typeReason("AUT714 Blockchain Sanity Issue tokens to TBE");
        cpAddIssuance.clickOk(aut714_userFirstName, "",aut714_userFirstName, aut714_tokenAmount);
        endTestLevel();

        startTestLevel("Sign Issuance");
        boolean isTransactionSignatureSent = signTransaction(
                aut714_userFirstName,
                aut714_signerAddress,
                aut714_privateKey
        );
        endTestLevel();

        startTestLevel("Check Signature Sent");
        Assert.assertTrue(isTransactionSignatureSent, "Transaction should be found in the 'sent' table after signing it. This is not the case...");
        endTestLevel();

        startTestLevel("Check Token Balance");
        assertTokenBalance(aut714_issuerId, aut714_investorId, aut714_tokenId , "tokensTreasury", expectedBalance);
        endTestLevel();
    }
}
