package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT228_ISR_MSF_Verify_Existing_Investor extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT228 - Control panel MSF - Verify existing investor")
    public void AUT228_ISR_MSF_Verify_Existing_Investor_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT228_ISR_MSF_Verify_Existing_Investor;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("CP MSF Verifying Investor information");
        cpMasterSecurityHolderFileValidateInvestorData(td.searchText);
        endTestLevel();
    }
}
