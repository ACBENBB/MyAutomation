package io.securitize.tests.fundraise;

import io.securitize.infra.config.*;
import io.securitize.tests.fundraise.abstractclass.*;
import io.securitize.tests.fundraise.pojo.*;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT516_FT_Invest_WireTransfer extends AbstractFT {

    @Test(description = "AUT516_FT_Invest_WireTransfer")
    public void AUT516_FT_Invest_WireTransfer() throws Exception {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        FT_TestScenario testScenario = FT_TestScenario.FT_INVEST_WIRETRANSFER;
        FT_TestData testdata = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Add Issuer And Token Id To TestData Object");
        testdata.issuerId = Users.getProperty(UsersProperty.ft_issuerId_aut516);
        testdata.tokenId = Users.getProperty(UsersProperty.ft_tokenId_aut516);
        endTestLevel();

        startTestLevel("Create Investor From Test Scenario Data");
        testdata = createFtTestInvestor(testdata);
        endTestLevel();

        startTestLevel("Navigate To issuer And Login With Crated Investor");
        navigateToIssuerNieWebSite(Users.getProperty(UsersProperty.ft_issuerNieUrl_aut516));
        loginToNie(
                investorDetails.getEmail(),
                Users.getProperty(UsersProperty.apiInvestorPassword)
        );
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

        // INVEST WIZARD
        // TODO assert qualification is skipped

        startTestLevel("Sign Agreement");
        clickAgreementContinue();
        endTestLevel();

        startTestLevel("Assert Fund Your Investment Page");
        assertFundYourInvestmentPageWireTransferDetails();
        endTestLevel();

        startTestLevel("Go Back To Opportunity Invest Page");
        clickOnFundYourInvestmentPageExitProcess();
        endTestLevel();

        startTestLevel("Get Investment Atributesfrom CP via API");
        assertCpInvestmentViaApi(testScenario, testdata);
        endTestLevel();

    }

}