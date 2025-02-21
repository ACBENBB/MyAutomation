package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT624_ISR_Hold_Trading extends AbstractCpInvestorRegistrationFlow {
    @Test(description = "Hold Trading in token level by freeze contract in Operational Procedures page, and than un-freeze it")
    public void AUT624_ISR_Hold_Trading_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = AbstractCpInvestorRegistrationFlow.ISR_TestScenario.AUT624_ISR_Hold_Trading;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName, false);
        endTestLevel();

        startTestLevel("Check contract status to execute procedure");
        checkContractStatus();
        endTestLevel();

        startTestLevel("Operational Procedures - freeze trading");
        cpHoldTrading(td.issuerName, td.autName, td.documentID);
        endTestLevel();

        startTestLevel("Check contract status to execute procedure");
        checkContractStatus();
        endTestLevel();

        startTestLevel("Operational Procedures - un-freeze trading");
        cpHoldTrading(td.issuerName, td.autName, td.documentID);
        endTestLevel();
    }
}