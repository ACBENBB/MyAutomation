package io.securitize.tests.controlPanel;

import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT420_ISR_Investor_Registration_Sanity extends AbstractCpInvestorRegistrationFlow {

    @BypassRecaptcha(environments = {"production", "apac"})
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub", forceOnEnvironments = {"production", "apac"})
    @Test(description = "AUT420 - Control panel issuer registration + Investor Information completion - USD - No Wallet and issuances", groups = {"sanityISR"})
    public void AUT420_ISR_Investor_Registration_Sanity_test() throws IOException, ParseException {
        AUT3InternalLogic("USD", "United States Dollar");
    }

    private void AUT3InternalLogic(String investmentCurrency, String currencyName) {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT3_ISR_Investor_Registration;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("CP Complete Registration Flow for Individual investor USD");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        cpInvestorRegistration(td.investorDetails, false);
        cpAddDocument(td.frontImagePath);
        cpAddInvestment(investmentCurrency, currencyName, investorDetails);
        endTestLevel();
    }
}