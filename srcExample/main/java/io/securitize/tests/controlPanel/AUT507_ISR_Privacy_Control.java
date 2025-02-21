package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT507_ISR_Privacy_Control extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT507 - Check privacy control when it's OFF (private info is shown) and ON (private info in asterisks)")
    public void AUT507_ISR_Privacy_Control_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT507_ISR_Privacy_Control;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName, false);
        endTestLevel();

        startTestLevel("Privacy control ON - Check info is hidden");
        privacyControlCheck(true);
        endTestLevel();

        startTestLevel("Privacy control OFF - Check info is shown");
        privacyControlCheck(false);
        endTestLevel();
    }


}
