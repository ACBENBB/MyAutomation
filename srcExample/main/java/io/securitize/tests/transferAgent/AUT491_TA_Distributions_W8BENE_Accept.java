package io.securitize.tests.transferAgent;

import io.securitize.tests.transferAgent.abstractClass.AbstractTaxForm;
import io.securitize.tests.transferAgent.testData.AUT491;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT491_TA_Distributions_W8BENE_Accept extends AbstractTaxForm {

    @Test(description = "TA - AUT491_TA_Distributions_WBENE_Accept")
    public void AUT491_TA_Distributions_W8BENE_Accept_Test() {

        startTestLevel("Create Test Data Object");
        AUT491 aut491 = new AUT491();
        endTestLevel();

        startTestLevel("Create Snapshot Via API");
        createSnapshotViaApi(aut491.getIssuerId(), aut491.getTokenId(), "AUT491 Snapshot");
        endTestLevel();

        startTestLevel("Login To Sid And Get Investor Initial Cash Acc Balance From UI");
        loginToSecIdWithInvestor(aut491.getTestInvestorMail(), aut491.getTestInvestorPass());
        aut491.setInitialCashAccountBalance(getCashBalanceFromSidUI());
        endTestLevel();

        startTestLevel("Submit TaxForm from SID UI");
        submitTaxform(aut491.getInvestorType());
        validateSubmittedTaxForm(aut491.getInvestorType(), "PENDING");
        logoutFromSid();
        endTestLevel();

        startTestLevel("Login To CP & Navigate To Distributions");
        loginToControlPanel();
        navigateToCpDistributions(aut491.getDistributionData());
        endTestLevel();

        startTestLevel("Generate and Select Distribution Type Dividend");
        generateDistribution(aut491.getDistributionData());
        selectDistributionByName(aut491.getDistributionData());
        endTestLevel();

        startTestLevel("Set Distribution TaxForm Status By Mail");
        setDistributionTaxFormStatusByMail(aut491.getTestInvestorMail(), "Valid");
        endTestLevel();

        startTestLevel("Wait for TaxForm Status to be");
        waitForTaxFormStatusBe(aut491.getTestInvestorMail(), "Valid");
        endTestLevel();

        startTestLevel("Confirm Distribution");
        confirmDistribution();
        endTestLevel();

        startTestLevel("Get Total Tokens From Distributions Details Page");
        aut491.setInitialInvestorTokens(getDistributionDetailsInvestorTokens(aut491.getTestInvestorMail()));
        getBrowser().refreshPage();
        endTestLevel();

        startTestLevel("Calculate Payment To Be Performed");
        aut491.setPaymentToBePerformed(
                calculateDividendPayment(aut491.getInitialInvestorTokens(), aut491.getDistributionData().amountPerToken)
        );
        endTestLevel();

        startTestLevel("Login To Sid And Get Cash Acc Balance From UI");
        loginToSecIdWithInvestor(aut491.getTestInvestorMail(), aut491.getTestInvestorPass());
        aut491.setActualCashAccountBalance(getCashBalanceFromSidUI());
        logoutFromSid();
        endTestLevel();

        startTestLevel("Assert Actual vs Expected Cash Account Balance");
        aut491.setExpectedCashAccountBalance((aut491.getInitialCashAccountBalance() + aut491.getPaymentToBePerformed()));
        info("aut492.expectedCashAccountBalance: " + aut491.getExpectedCashAccountBalance());
        info("aut492.actualCashAccountBalance: " + aut491.getActualCashAccountBalance());
        info("aut492.initialCashAccountBalance: " + aut491.getInitialCashAccountBalance());

        Assert.assertEquals(
                Math.round(aut491.getActualCashAccountBalance()),
                Math.round(aut491.getExpectedCashAccountBalance()));
        endTestLevel();

    }

}