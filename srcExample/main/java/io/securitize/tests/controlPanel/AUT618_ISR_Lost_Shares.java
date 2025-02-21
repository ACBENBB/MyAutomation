package io.securitize.tests.controlPanel;

import io.securitize.pageObjects.controlPanel.cpIssuers.CpInvestorDetailsPage;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT618_ISR_Lost_Shares extends AbstractCpInvestorRegistrationFlow {
    @Test(description = "AUT618 - Destroy and reissue tokens through lost shares procedure")
    public void AUT618_ISR_Lost_Shares_Test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT618_ISR_Lost_Shares;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Pre-conditions - verify investor and get test data");
        td.investorDetails.setFirstName(td.investorFirstName);
        cpVerifyLostSharesInvestor(td.investorDetails);
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        String[] walletsAddress = investorDetailsPage.getInvestorWallets();
        String sourceWallet = walletsAddress[0];
        String destinationWallet = walletsAddress[1];
        endTestLevel();

        startTestLevel("Operational Procedures - perform lost shares procedure");
        cpExecuteLostShares(sourceWallet, destinationWallet, td.investorDetails, td.issuerName);
        endTestLevel();

    }
}
