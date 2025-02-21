package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT538_ISR_MarketsOverview extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT538 - Markets Overview - Cards + Investors")
    public void AUT538_ISR_Markets_Overview_Cards() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT538_ISR_Markets_Overview;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Create a new investor");
        cpInvestorRegistration(td.investorDetails, false);
        endTestLevel();

        startTestLevel("CP Markets Overview Cards");
        cpSelectToken(td.marketsTokenName);
        cpSetKycAndAccreditaionToVerified();
        if (td.currentEnvironment.equals("sandbox")) {
            cpAddInvestmentForMktsOverviewSandbox(td.investmentCurrency);
        }
        else {
            cpAddInvestmentNonDefaultToken(td.investmentCurrency);
        }
        cpValidateInvestmentData(td.currencyName, td.investorDetails);
        cpNavigateToMarketsOverview();
        cpAssertOpportunity(td.opportunityName, td.numberOfCardsToAssert);
        cpSwitchToMarketsOverviewInvestorsTab();
        cpSelectOpportunity(td.opportunityName);
        cpAssertInvestorData(td.investorDetails,
                td.opportunityName,
                td.marketsPledgedAmount,
                td.marketsFundedAmount);
        endTestLevel();
    }
}
