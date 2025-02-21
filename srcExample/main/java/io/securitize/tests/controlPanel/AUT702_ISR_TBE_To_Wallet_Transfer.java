package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT702_ISR_TBE_To_Wallet_Transfer extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT702 - Transfer TBE to wallet")
    public void AUT702_ISR_TBE_To_Wallet_Transfer_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = AbstractCpInvestorRegistrationFlow.ISR_TestScenario.AUT702_ISR_TBE_To_Wallet_Transfer;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Find investor with TBE and wallet");
        findInvestorForTBEToWalletTransfer(td.searchText, td.tokensHeldIn, td.tokenToProcedure);
        endTestLevel();

        startTestLevel("Transfer TBE to wallet");
        transferTBEToWallet(td.procedureType, td.autName, td.issuerName);
        endTestLevel();
    }
}
