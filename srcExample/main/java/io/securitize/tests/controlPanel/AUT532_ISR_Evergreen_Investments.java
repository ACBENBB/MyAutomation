package io.securitize.tests.controlPanel;

import io.securitize.tests.InvestorDetails;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT532_ISR_Evergreen_Investments extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT532 - Evergreen Round + Evergreen Investments - USD")
    public void AUT532_ISR_Evergreen_Investments_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT532_ISR_Evergreen_Investments;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Generates random investor details");
        InvestorDetails investorDetails = td.investorDetails;
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Investor registration and CP Evergreen Investments");
        cpInvestorRegistration(investorDetails, false);
        cpSelectToken(td.tokenName);
        cpSetKycAndAccreditaionToVerified();
        cpAddEvergreenInvestment(td.investmentCurrency, td.currencyName, investorDetails);
        cpAddSingleTbeIssuanceWithNoSignature(investorDetails, td.issuanceReason);
        cpAddSecondEvergreenInvestment(td.investmentCurrency);
        endTestLevel();
    }
}