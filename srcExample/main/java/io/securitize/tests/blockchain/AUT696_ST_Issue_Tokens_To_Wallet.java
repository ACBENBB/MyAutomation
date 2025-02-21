package io.securitize.tests.blockchain;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.tests.blockchain.abstractClass.AbstractBC;
import io.securitize.tests.blockchain.testData.AUT696_POJO;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;


public class AUT696_ST_Issue_Tokens_To_Wallet extends AbstractBC {
    @Test
    public void AUT696_ST_Issue_Tokens_To_Wallet_Test() {
        int expectedBalance;
        String url;

        startTestLevel("create POJO for AUT696");
        AUT696_POJO aut696_pojo = new AUT696_POJO();
        endTestLevel();

        startTestLevel("Get investor Balance");
        expectedBalance = Integer.parseInt(getTokenBalancesByType(aut696_pojo.ISSUER_ID, aut696_pojo.INVESTOR_ID,aut696_pojo.TOKEN_ID,"tokensHeld")) + Integer.parseInt(aut696_pojo.TOKEN_AMOUNT);
        info("Expected Balance is --> "+ expectedBalance);
        endTestLevel();

        startTestLevel("Login in to CP with AUT Operator");
        loginToCp();
        endTestLevel();

        url = "https://cp." + MainConfig.getProperty(MainConfigProperty.environment) + ".securitize.io/" + aut696_pojo.ISSUER_ID + "/" + aut696_pojo.TOKEN_ID + "/investors/details/"+ aut696_pojo.INVESTOR_ID +"?from=onboarding";

        startTestLevel("Navigate to investor page and add issuance");
        getBrowser().navigateTo(url);
        CpInvestorDetailsPage cpInvestorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        cpInvestorDetailsPage.clickAddIssuance();
        endTestLevel();

        startTestLevel("Issue to wallet");
        CpAddIssuance cpAddIssuance = new CpAddIssuance(getBrowser());
        cpAddIssuance.selectTokenWalletId("AUT696", aut696_pojo.INVESTOR_WALLET);
        cpAddIssuance.typeIssuanceAmount(Integer.parseInt(aut696_pojo.TOKEN_AMOUNT));
        cpAddIssuance.typeReason("AUT696 Blockchain Sanity Issue tokens to Wallet");
        cpAddIssuance.clickOk(aut696_pojo.USER_FIRST_NAME, "",aut696_pojo.USER_FIRST_NAME, Integer.parseInt(aut696_pojo.TOKEN_AMOUNT));
        endTestLevel();

        startTestLevel("Sign Issuance");
        boolean isTransactionSignatureSent = signTransaction(
                aut696_pojo.USER_FIRST_NAME,
                aut696_pojo.SIGNER_ADDRESS,
                aut696_pojo.PRIVATE_KEY
        );

        startTestLevel("Assert Signature Sent");
        Assert.assertTrue(isTransactionSignatureSent, "Transaction should be found in the 'sent' table after signing it. This is not the case...");
        endTestLevel();

        startTestLevel("Assert Token Balance");
        assertTokenBalance(aut696_pojo.ISSUER_ID,aut696_pojo.INVESTOR_ID, aut696_pojo.TOKEN_ID, "tokensHeld", expectedBalance);
        endTestLevel();


    }
}
