package io.securitize.tests.transferAgent;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.transferAgent.abstractClass.TransactionHistory;
import io.securitize.tests.transferAgent.testData.AUT562;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT562_TA_TransactionHistory_IssuanceDisplayed extends TransactionHistory {

    @Test(description = "Issuance Displays on CP and SID Transaction History")
    public void AUT562_TA_TransactionHistory_IssuanceDisplayed_test() {

        startTestLevel("Create Automation Test Data Object");
        AUT562 aut562 = new AUT562();
        endTestLevel();

        startTestLevel("Login To Control Panel An Perform Issuance");
        loginToControlPanel();
        goToInvestorOnboarding(aut562.issuerId, aut562.tokenId, aut562.investorId);
        addTransaction();
        addIssuance();
        aut562.tokenPrice = getLastIssuancePrice();
        endTestLevel();

        startTestLevel("Sign the Issuance");
        goToSignatures(aut562.issuerId, aut562.tokenId);
        waitForTransactionsListToBeLoaded();
        signIssuance(aut562.wallet, aut562.pk);
        getBrowser().waitForPageStable(Duration.ofSeconds(10));
        getBrowser().refreshPage();
        aut562.transactionHash = getLastTransactionHash();
        info("Transaction Hash of Issuance is: " + aut562.transactionHash);
        endTestLevel();

        startTestLevel("Wait for the transaction to arrive to Securities Transactions");
        goToSecuritiesTransactions(aut562.issuerId, aut562.tokenId);
        waitForIssuanceTransactionToArrive(aut562.transactionHash);
        logoutFromControlPanel();
        endTestLevel();

        startTestLevel("Validate Issuance transaction displays in SID");
        loginToSecuritizeId(aut562.investorEmail, Users.getProperty(UsersProperty.taInvestorGenericPass));
        navitagateToAssetDetailsPage("AUT562");
        Assert.assertTrue(validateTransactionIsPresentInSecId(aut562.tokenPrice), "Transaction was not present after 5 seconds...");
        logoutFromSecId();
        endTestLevel();

    }

}