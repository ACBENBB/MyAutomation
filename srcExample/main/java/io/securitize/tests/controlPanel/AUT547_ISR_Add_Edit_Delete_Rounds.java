package io.securitize.tests.controlPanel;

import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT547_ISR_Add_Edit_Delete_Rounds extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "AUT547 - Add, Edit and Delete Rounds")
    public void AUT547_ISR_Add_Edit_Delete_Rounds() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT547_ISR_Add_Edit_Delete_Rounds;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel");
        cpLoginUsingEmailAndPassword();
        endTestLevel();

        startTestLevel("Select Issuer and token");
        cpSelectIssuer(td.issuerName);
        cpSelectToken(td.roundsTokenName);
        endTestLevel();

        startTestLevel("Navigate to Fundraise");
        cpNavigateToConfigurationFundraise();
        endTestLevel();

        startTestLevel("Clean up Rounds from Fundraise");
        cpRemoveAllUnwantedRounds();
        endTestLevel();

        startTestLevel("Add Round");
        cpAddRound(td.roundsAddRoundName,
                td.roundsAddStartDate,
                td.roundsAddEndDate,
                td.roundsAddIssuanceDate,
                td.roundsAddMinimumInvestmentCrypto,
                td.roundsAddMinimumInvestmentFiat,
                td.roundsAddText,
                td.roundsAddTokenValue);
        cpValidateRound(td.roundsAddRoundName,
                td.roundsAddStatusName,
                td.roundsAddStartDate,
                td.roundsAddEndDate,
                td.roundsAddIssuanceDate,
                td.roundsAddMinimumInvestmentCrypto,
                td.roundsAddMinimumInvestmentFiat,
                td.roundsAddText);
        endTestLevel();

        startTestLevel("Edit Round");
        cpEditRound(td.roundsEditRoundName,
                td.roundsEditStartDate,
                td.roundsEditEndDate,
                td.roundsEditIssuanceDate,
                td.roundsEditMinimumInvestmentCrypto,
                td.roundsEditMinimumInvestmentFiat,
                td.roundsEditTokenValue);
        cpValidateRound(td.roundsEditRoundName,
                td.roundsEditStatusName,
                td.roundsEditStartDate,
                td.roundsEditEndDate,
                td.roundsEditIssuanceDate,
                td.roundsEditMinimumInvestmentCrypto,
                td.roundsEditMinimumInvestmentFiat,
                td.roundsAddText);
        endTestLevel();

        startTestLevel("Delete Round");
        cpDeleteRound();
        endTestLevel();
    }
}
