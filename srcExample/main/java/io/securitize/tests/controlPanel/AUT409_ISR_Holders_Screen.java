package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT409_ISR_Holders_Screen extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT409 - Holders screen")
    public void AUT409_ISR_Holders_Screen_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT409_ISR_Holders_Screen;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("CP Validate Holders screen data");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        cpVerifyHoldersOfRecordScreenData(td.searchText);
        endTestLevel();
    }
}
