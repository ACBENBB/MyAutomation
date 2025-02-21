package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT536_ISR_Unlock_Tokens_TBE extends AbstractCpInvestorRegistrationFlow {


    @Test(description = "AUT536 - Lock TBE token")
    public void AUT536_ISR_Unlock_Tokens_TBE_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT536_ISR_Unlock_Tokens_TBE;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Find investor with TBE tokens");
        findInvestorForLock(td.searchText, td.procedureType,td.tokensHeldIn);
        endTestLevel();

        startTestLevel("Operational Procedures - Partial Lock TBE tokens");
        cpLockToken(td.procedureType, td.tokensHeldIn, td.issuerName, td.autName, td.tokenToProcedure, true);
        endTestLevel();

        startTestLevel("Operational Procedures - Full Lock TBE tokens");
        cpLockToken(td.procedureType, td.tokensHeldIn, td.issuerName, td.autName, td.tokenToProcedure, false);
        endTestLevel();

    }
}
