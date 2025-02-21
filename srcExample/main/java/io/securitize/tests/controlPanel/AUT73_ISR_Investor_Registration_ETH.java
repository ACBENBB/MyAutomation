package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT73_ISR_Investor_Registration_ETH extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT73 - Control panel issuer registration + ETH Investment")
    public void AUT73_ISR_Investor_Registration_ETH_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT73_ISR_Investor_Registration_ETH;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("CP Complete Registration Flow for Individual investor ETH");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        cpInvestorRegistration(td.investorDetails, false);
        cpAddInvestment(td.investmentCurrency, td.currencyName, td.investorDetails);
        cpAddWallet();
        cpAddIssuances(td.investorDetails, td.issuanceReason, td.issuerName);
        endTestLevel();

    }
}
