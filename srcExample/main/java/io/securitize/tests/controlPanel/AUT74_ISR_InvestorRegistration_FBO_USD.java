package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT74_ISR_InvestorRegistration_FBO_USD extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT74 - Control panel FBO Individual registration + Investor Information completion - USD")
    public void AUT74_ISR_InvestorRegistration_FBO_USD_test() {
        AUT74InternalLogic();
    }

    private void AUT74InternalLogic() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT74_ISR_InvestorRegistration_FBO_USD;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("CP Complete Registration Flow for FBO investor USD");
        cpInvestorRegistration(td.investorDetails, true);
        cpAddDocument(td.frontImagePath);
        cpAddInvestment(td.investmentCurrency, td.currencyName, td.investorDetails);
        cpAddWallet();
        cpAddIssuances(td.investorDetails, td.issuanceReason, td.issuerName);
        endTestLevel();

    }
}
