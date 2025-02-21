package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT620_ISR_Destroy_Tokens_TBE extends AbstractCpInvestorRegistrationFlow {


    @Test(description = "AUT620 - Destroy TBE tokens from the first flow")
    public void AUT620_ISR_Destroy_Token_TBE_test()  {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT620_ISR_Destroy_Tokens_TBE;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("CP Complete Registration Flow for Individual investor + issuance");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Find investor for Destroy TBE tokens procedure");
        findInvestorForDestroy(td.searchText, td.procedureType, td.tokensHeldIn);
        endTestLevel();

        startTestLevel("Operational Procedures - Destroy TBE Tokens");
        cpDestroyTokens(td.issuerName, td.autName, td.tokensHeldIn, td.tokenToProcedure);
        endTestLevel();
    }
}