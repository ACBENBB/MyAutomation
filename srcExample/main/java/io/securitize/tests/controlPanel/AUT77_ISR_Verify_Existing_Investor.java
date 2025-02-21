package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT77_ISR_Verify_Existing_Investor extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT77 - Verifies data from existing investor")
    public void AUT77_ISR_Verify_Existing_Investor_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT77_ISR_Verify_Existing_Investor;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Verify an existing investor");
        cpVerifyExistingInvestor(td.searchText);
        endTestLevel();
    }
}
