package io.securitize.tests.fundraise;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.fundraise.abstractclass.AbstractFT;
import io.securitize.tests.fundraise.pojo.FT_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT539_FT_NieInvest_CashAccount_AccreditationFirst extends AbstractFT {

    @Test(description = "AUT539_FT_NieInvest_CashAccount_AccreditationFirst")
    public void AUT539_FT_NieInvest_CashAccount_AccreditationFirst() throws Exception {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        FT_TestScenario testScenario = FT_TestScenario.FT_ACCREDITATION_FIRST;
        FT_TestData testdata = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Add Issuer And Token Id To TestData Object");
        testdata.issuerId = Users.getProperty(UsersProperty.ft_issuerId_aut539);
        testdata.tokenId = Users.getProperty(UsersProperty.ft_tokenId_aut539);
        endTestLevel();

        startTestLevel("Create Investor From Test Scenario Data");
        testdata = createFtTestInvestor(testdata);
        endTestLevel();

        startTestLevel("Navigate To issuer And Login With Crated Investor");
        navigateToIssuerNieWebSite(Users.getProperty(UsersProperty.ft_issuerNieUrl_aut539));
        loginToNie(
                investorDetails.getEmail(),
                Users.getProperty(UsersProperty.apiInvestorPassword)
        );
        endTestLevel();

        startTestLevel("Click I am an Accredited Investor - Accreditation First");
        clickIAmAnAccreditedInvestor();
        endTestLevel();

        startTestLevel("Click Nie Opportunities Tab");
        navigateToOpportunitiesTab();
        endTestLevel();

        startTestLevel("Select Opportunity By Name");
        clickInvestmentByName(testdata.investmentName);
        endTestLevel();

        startTestLevel("Set Investment Currency And Amount");
        setInvestmentCurrencyAndAmount(testdata.investmentCurrency, testdata.investmentAmount);
        endTestLevel();

        startTestLevel("Click Invest Btn");
        clickInvest();
        endTestLevel();

        startTestLevel("Click agreement continue btn");
        clickAgreementContinue();
        endTestLevel();

        startTestLevel("Assert Fund Your Investment Page");
        assertFundYourInvestmentPageCashAccountDetails();
        endTestLevel();

        startTestLevel("Click on cash Account option");
        clickCashAccountOption();
        endTestLevel();

        startTestLevel("Validate cash account card and click on invest button");
        clickInvestButtonOnFundingPage();
        endTestLevel();

        startTestLevel("Assert Finish Investment PopUp");
        assertCashAccountFinishInvestmentPopUp();
        endTestLevel();

        startTestLevel("Assert Funding Instructions Msg");
        assertCpInvestmentViaApi(testScenario, testdata);
        endTestLevel();
    }
}
