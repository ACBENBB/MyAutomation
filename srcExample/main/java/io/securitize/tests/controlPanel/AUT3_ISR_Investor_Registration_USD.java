package io.securitize.tests.controlPanel;

import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.tests.controlPanel.abstractClass.AbstractISR;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT3_ISR_Investor_Registration_USD extends AbstractISR {

    @BypassRecaptcha(environments = {"production", "apac"})
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @Test(description = "AUT3 - Control panel issuer registration + Investor Information completion - USD")
    public void AUT3_ISR_Investor_Registration_USD_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        ISR_TestScenario testScenario = ISR_TestScenario.AUT3_ISR_INVESTOR_REGISTRATION;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("CP Complete Registration Flow for Individual investor USD");
        cpInvestorRegistration(td.investorDetails, false);
        cpAddDocument(td.frontImagePath);
        cpAddInvestment(td.investmentCurrency, td.currencyName, td.investorDetails);
        cpAddWallet();
        cpAddIssuances(td.investorDetails, td.issuanceReason, td.issuerName);
    }
}