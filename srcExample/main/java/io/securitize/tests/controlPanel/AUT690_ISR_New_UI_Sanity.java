package io.securitize.tests.controlPanel;

import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT690_ISR_New_UI_Sanity extends AbstractCpInvestorRegistrationFlow {

    @BypassRecaptcha(environments = {"production", "apac"})
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @Test(description = "AUT690 - New UI - Control panel issuer registration + Investor Information completion - USD")
    public void AUT690_ISR_New_UI_Sanity_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = AbstractCpInvestorRegistrationFlow.ISR_TestScenario.AUT690_ISR_New_UI_Sanity;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel");
        cpLoginUsingEmailAndPassword();
        endTestLevel();

        startTestLevel("Select issuer");
        cpSelectIssuer(td.issuerName);
        endTestLevel();

        startTestLevel("Investor registration");
        cpInvestorRegistrationNewUI(td.investorDetails, false);
        endTestLevel();

        startTestLevel("Add Document");
        cpAddDocumentNewUI(td.frontImagePath, td.documentCategory, td.documentFace, td.documentTitle);
        endTestLevel();

        startTestLevel("Add investment");
        cpAddInvestmentNewUI(td.investmentCurrency, false);
        endTestLevel();

        startTestLevel("Add wallet");
        cpAddWalletNewUI();
        endTestLevel();

        startTestLevel("Set verification");
        setVerification();
        endTestLevel();

        startTestLevel("Wait wallet to be ready");
        waitWalletToBeReady();
        endTestLevel();

        startTestLevel("Add issuance - wallet");
        cpAddIssuanceNewUI("wallet", false);
        endTestLevel();

        startTestLevel("Add issuance - TBE");
        cpAddIssuanceNewUI("TBE", false);
        endTestLevel();

        startTestLevel("Sign issuance");
        signIssuanceNewUI(td.issuerName, td.autName);
        endTestLevel();

        startTestLevel("Verify issuance and tokens after issuance");
        verifyIssuanceAndTokensAfterIssuance();
        endTestLevel();

    }

}
