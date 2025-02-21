package io.securitize.tests.transferAgent;

import io.securitize.tests.transferAgent.abstractClass.AbstractTaxForm;
import io.securitize.tests.transferAgent.testData.AUT492;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT492_TA_Distributions_W8BENE_Reject extends AbstractTaxForm {

    // TODO this class should extend from DISTRIBUTIONS since is a DISTRIBUTIONS TEST.

    @Test(description = "TA - AUT492_TA_Distributions_W8BENE_Reject")
    public void AUT492_TA_Distributions_W8BENE_Reject_Test() {

        startTestLevel("Create Test Data Object");
        AUT492 aut492 = new AUT492();
        endTestLevel();

        startTestLevel("Create Snapshot Via API");
        createSnapshotViaApi(aut492.getIssuerId(), aut492.getTokenId(), "AUT492 Snapshot");
        endTestLevel();

        startTestLevel("Login To Sid And Get Cash Acc Balance From UI");
        loginToSecIdWithInvestor(aut492.getTestInvestorMail(), aut492.getTestInvestorPass());
        aut492.setInitialCashAccountBalance(getCashBalanceFromSidUI());
        endTestLevel();

        startTestLevel("Submit TaxForm from SID UI");
        submitTaxform(aut492.getInvestorType());
        validateSubmittedTaxForm(aut492.getInvestorType(), "PENDING");
        logoutFromSid();
        endTestLevel();

        startTestLevel("Login To CP & Navigate To Distributions");
        loginToControlPanel();
        navigateToCpDistributions(aut492.getDistributionData());
        endTestLevel();

        startTestLevel("Generate and Select Distribution Type Dividend");
        generateDistribution(aut492.getDistributionData());
        selectDistributionByName(aut492.getDistributionData());
        endTestLevel();

        startTestLevel("Set Distribution TaxForm Status By Mail");
        setDistributionTaxFormStatusByMail(aut492.getTestInvestorMail(), "Invalid");
        endTestLevel();

        startTestLevel("Wait for TaxForm Status to be");
        waitForTaxFormStatusBe(aut492.getTestInvestorMail(), "Invalid");
        endTestLevel();

        startTestLevel("Confirm Distribution");
        confirmDistribution();
        endTestLevel();

        startTestLevel("Get Total Tokens From Distributions Details Page");
        aut492.setInitialInvestorTokens(getDistributionDetailsInvestorTokens(aut492.getTestInvestorMail()));
        getBrowser().refreshPage();
        endTestLevel();

        startTestLevel("Calculate Payment To Be Performed");
        aut492.setPaymentToBePerformed(
                calculateDividendPayment(aut492.getInitialInvestorTokens(), aut492.getDistributionData().amountPerToken)
        );
        endTestLevel();

        startTestLevel("Login To Sid And Get Cash Acc Balance From UI");
        loginToSecIdWithInvestor(aut492.getTestInvestorMail(), aut492.getTestInvestorPass());
        aut492.setActualCashAccountBalance(getCashBalanceFromSidUI());
        logoutFromSid();
        endTestLevel();

        startTestLevel("Assert Actual vs Expected Cash Account Balance");
        aut492.setExpectedCashAccountBalance((aut492.getInitialCashAccountBalance() + aut492.getPaymentToBePerformed()));
        info("aut492.getExpectedCashAccountBalance(): " + aut492.getExpectedCashAccountBalance());
        info("aut492.getActualCashAccountBalance(): " + aut492.getActualCashAccountBalance());
        info("aut492.getInitialCashAccountBalance(): " + aut492.getInitialCashAccountBalance());

        Assert.assertEquals(
                Math.round(aut492.getActualCashAccountBalance()),
                Math.round(aut492.getExpectedCashAccountBalance()));
        endTestLevel();

    }

}