package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT563_ISR_Affiliate_Management extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT563 - Affiliate Management")
    public void AUT563_ISR_Affiliate_Management() {
        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT563_ISR_Affiliate_Management;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName, false);
        endTestLevel();

        startTestLevel("Create a new investor");
        cpInvestorRegistration(td.investorDetails, false);
        endTestLevel();

        startTestLevel("Activate Affiliate");
        cpActivateAffiliate();
        cpNavigateToSignaturesScreen();
        cpSignAffiliateTransaction(td.issuerSignerName, td.investorDetails);
        cpNavigateToOnboarding();
        cpValidateAffiliateStatus(td.investorDetails, td.activatedAffiliateStatus);
        cpNavigateToAffiliateManagement();
        cpValidateAffiliateManagement(td.investorDetails, td.activatedAffiliateStatus);
        endTestLevel();

        startTestLevel("Deactivate Affiliate");
        cpDeactivateAffiliate();
        cpNavigateToSignaturesScreen();
        cpSignAffiliateTransaction(td.issuerSignerName, td.investorDetails);
        cpNavigateToOnboarding();
        cpValidateAffiliateStatus(td.investorDetails, td.deactivatedAffiliateStatus);
        cpNavigateToAffiliateManagement();
        cpValidateAffiliateManagement(td.investorDetails, td.deactivatedAffiliateStatus);
        endTestLevel();
    }
}
