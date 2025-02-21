package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT523_ISR_Decimal_Flow extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT523 - Control panel issuer registration + Investor Information completion - USD with decimal")
    public void AUT523_ISR_Decimal_Flow_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT523_ISR_Decimal_Flow;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel");
        cpLoginUsingEmailAndPassword();
        endTestLevel();

        startTestLevel("Select issuer");
        cpSelectIssuer(td.issuerName, false);
        endTestLevel();

        startTestLevel("Investor registration");
        cpInvestorRegistrationNewUI(td.investorDetails, false);
        endTestLevel();

        startTestLevel("Add investment");
        cpAddInvestmentNewUI(td.investmentCurrency, true);
        endTestLevel();

        startTestLevel("Add wallet");
        cpAddWalletNewUI();
        endTestLevel();

        startTestLevel("Set verification");
        setVerification();
        endTestLevel();

        startTestLevel("Wait wallet to be ready");
        waitWalletToBeReady();
        endTestLevel();

        startTestLevel("Add issuance - wallet");
        cpAddIssuanceNewUI("wallet", true);
        endTestLevel();

        startTestLevel("Add issuance - TBE");
        cpAddIssuanceNewUI("TBE", true);
        endTestLevel();

        startTestLevel("Sign issuance");
        signIssuanceNewUI(td.issuerName, td.autName);
        endTestLevel();

        startTestLevel("Verify issuance and tokens after issuance");
        verifyIssuanceAndTokensAfterIssuance();
        endTestLevel();
    }

}
