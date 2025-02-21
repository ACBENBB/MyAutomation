package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT413_ISR_Internal_TBE_Transfer extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT413 - Internal TBE Transfer")
    public void AUT413_ISR_Internal_TBE_Transfer_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT413_ISR_Internal_TBE_Transfer;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Find investor with TBE and wallet");
        findInvestorForInternalTBETTransfer(td.searchText,td.tokensHeldIn);
        endTestLevel();

        startTestLevel("Transfer internal TBE");
        cpInternalTBETransfer(td.autName, td.issuerName, td.amountToBeTransferred, td.procedureType);
        endTestLevel();
    }

}
