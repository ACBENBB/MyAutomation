package io.securitize.tests.controlPanel;

import com.opencsv.exceptions.CsvException;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT134_ISR_Import_Investor_And_Multiple_Investors extends AbstractCpInvestorRegistrationFlow {

    private static final InvestorsAPI investorsAPI = new InvestorsAPI();
    private static final String issuerName = "Nie";

    //Adding priority to prevent these two tests run in parallel.
    @Test(description = "AUT134 - Control panel import investor",priority = 0)
    public void AUT134_ISR_Import_Investors_test() throws IOException, InterruptedException {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT134_ISR_Import_Investors;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Add Current Environment To TestData Object");
        td.currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        endTestLevel();

        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        String email = investorDetails.getEmail();

        startTestLevel("API: validate no investor exists with randomly generated email");
        validateEmailDoesNotExist(email);
        endTestLevel();

        startTestLevel("Login to control panel and select issuer");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(issuerName);

        startTestLevel("CP Import Investor Flow");
        cpImportInvestor(investorDetails, td.importCSVFilePath);
        endTestLevel();

        startTestLevel("API: validate investor exists with randomly generated email");
        validateEmailDoesExist(email);
        endTestLevel();

        startTestLevel("UI: Validate investor exist");
        cpVerifyFirstRowImportInvestorInformation(td.importCSVFilePath);
        endTestLevel();
    }

    //Adding priority to prevent these two tests run in parallel.
    @Test(description = "AUT368 - Control panel multiple import investor", priority = 1)
    public void AUT368_ISR_Import_Multiple_Investors_test() throws IOException, InterruptedException, CsvException {
        String importCSVFilePath = "tempImportMultipleFile.csv";

        InvestorDetails firstInvestorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorDetails secondInvestorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorDetails thirdInvestorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorDetails fourthInvestorDetails = InvestorDetails.generateRandomUSInvestor();

        startTestLevel("API: validate no investor exists with randomly generated email");
        validateEmailDoesNotExist(firstInvestorDetails.getEmail());
        validateEmailDoesNotExist(secondInvestorDetails.getEmail());
        validateEmailDoesNotExist(thirdInvestorDetails.getEmail());
        validateEmailDoesNotExist(fourthInvestorDetails.getEmail());
        endTestLevel();

        startTestLevel("CP Multiple Import Investor Flow");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(issuerName);
        cpImportMultipleInvestor(firstInvestorDetails, secondInvestorDetails, thirdInvestorDetails, fourthInvestorDetails, importCSVFilePath);
        endTestLevel();

        startTestLevel("API: validate all investors exists with randomly generated email");
        validateEmailDoesExist(firstInvestorDetails.getEmail());
        validateEmailDoesExist(secondInvestorDetails.getEmail());
        validateEmailDoesExist(thirdInvestorDetails.getEmail());
        validateEmailDoesExist(fourthInvestorDetails.getEmail());
        endTestLevel();

        startTestLevel("UI: Validate all investors exist");
        cpVerifyImportedInvestorInformation(importCSVFilePath, firstInvestorDetails, 1);
        cpVerifyImportedInvestorInformation(importCSVFilePath, secondInvestorDetails, 2);
        cpVerifyImportedInvestorInformation(importCSVFilePath, thirdInvestorDetails, 3);
        cpVerifyImportedInvestorInformation(importCSVFilePath, fourthInvestorDetails, 4);
        endTestLevel();
    }

    private void validateEmailDoesNotExist(String email) {
        Assert.assertFalse(investorsAPI.isInvestorExistsByEmail(issuerName, email), String.format("investor by random email %s shouldn't pre-exist in the DB but this is not the case", email));
    }

    private void validateEmailDoesExist(String email) {
        Assert.assertTrue(investorsAPI.isInvestorExistsByEmail(issuerName, email), String.format("investor by random email %s should exist in the DB after import but this is not the case", email));
    }

    //adding priority since the test includes import investors
    @Test(description = "AUT519 - Verifies Bulk Issuance is performed properly", priority = 2)
    public void AUT519_ISR_Bulk_Issuance_test()  {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT519_ISR_Bulk_Issuance;
        ISR_TestData testData = createTestDataObject(testScenario);
        String randomGeneratedLastName = testData.randomGeneratedLastName;
        endTestLevel();

        startTestLevel("CP Performs a Bulk Issuance and verifies all its data");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(issuerName);
        cpImportBulkIssuanceInvestors(testData.importCSVFilePath, testData.amountOfInvestors, randomGeneratedLastName);
        cpAddWalletToImportedInvestor();
        cpBulkIssuance(testData.amountOfInvestors, randomGeneratedLastName);
        cpSignBulkIssuance(issuerName, randomGeneratedLastName);
        cpValidateBulkIssuanceForRegularToken(randomGeneratedLastName);
        cpValidateBulkIssuanceForTbeToken(randomGeneratedLastName);
        endTestLevel();
    }
}
