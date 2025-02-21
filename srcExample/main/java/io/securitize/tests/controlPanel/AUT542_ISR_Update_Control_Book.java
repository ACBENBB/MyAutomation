package io.securitize.tests.controlPanel;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT542_ISR_Update_Control_Book extends AbstractCpInvestorRegistrationFlow {


    @Test(description = "AUT523 - Verify Control can be updated manually, update after TBE issuance and throws error message when authorized securities exceeded", priority = 1)
    public void AUT542_ISR_Update_Control_Book_test() {

        startTestLevel("Add Current Environment To TestData Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT542_ISR_Update_Control_Book;
        ISR_TestData td = createTestDataObject(testScenario);
        td.currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName, false);
        endTestLevel();

        startTestLevel("Update 'Total number of Authorized Securities' in Control Book");
        updateControlBook();
        endTestLevel();

        startTestLevel("Find investor for TBE");
        findInvestorForCB(td.searchText, td.autName, td.procedureType, td.tokensHeldIn);
        endTestLevel();

        startTestLevel("Add TBE and wallet issuance till reach the limit of Authorized Securities");
        addIssuance(td.autName, td.issuerName, false);
        endTestLevel();

        startTestLevel("Verify 'Total number of Issued Securities' updated in Control Book");
        verifyControlBookUpdated();
        endTestLevel();

        startTestLevel("Add TBE issuance and wait for error message: Authorized securities exceeded");
        addIssuance(td.autName, td.issuerName, true);
        endTestLevel();

    }

}




