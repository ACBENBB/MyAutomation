package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT135_ISR_Edit_Existing_Investor_Validation extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT135 - Verifies data from an edit of an existing investor")
    public void AUT135_ISR_Edit_Existing_Investor_Validation_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT135_ISR_Edit_Existing_Investor_Validation;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("CP Verifying existing data for an edit investor");
        cpEditInvestorDetails(td.searchText);
        endTestLevel();

    }
}
