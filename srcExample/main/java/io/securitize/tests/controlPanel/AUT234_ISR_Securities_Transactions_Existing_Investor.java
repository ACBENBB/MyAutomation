package io.securitize.tests.controlPanel;

import io.securitize.infra.config.InvestorsData;
import io.securitize.infra.config.InvestorsProperty;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT234_ISR_Securities_Transactions_Existing_Investor extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT234 - Control panel Securities Transactions - Verify existing investor's transactions")
    public void AUT234_ISR_Securities_Transactions_Existing_Investor_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT234_ISR_Securities_Transactions_Existing_Investor;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("CP Securities Transactions Verifying Investor transactions information");
        cpSecuritiesTransactionsValidateRegularTransactionData(InvestorsData.getUserDetails(InvestorsProperty.investorTokenEthTxId));
        cpSecuritiesTransactionsValidateTBETransactionData(InvestorsData.getUserDetails(InvestorsProperty.investorTBETokenEthTxId));
        endTestLevel();
    }
}
