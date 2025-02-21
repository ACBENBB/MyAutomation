package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT619_ISR_Destroy_Tokens_Wallet extends AbstractCpInvestorRegistrationFlow {


    @Test(description = "AUT619 - Destroy wallet tokens")
    public void AUT619_ISR_Destroy_Token_Wallet_test()  {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT619_ISR_Destroy_Tokens_Wallet;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Find investor for Destroy wallet tokens procedure");
        findInvestorForDestroy(td.searchText, td.procedureType, td.tokensHeldIn);
        endTestLevel();

        startTestLevel("Operational Procedures - Destroy Regular Tokens");
        cpDestroyTokens(td.issuerName, td.autName, td.tokensHeldIn, td.tokenToProcedure);
        endTestLevel();
    }

}