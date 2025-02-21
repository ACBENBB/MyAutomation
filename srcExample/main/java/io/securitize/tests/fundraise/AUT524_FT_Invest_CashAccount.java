package io.securitize.tests.fundraise;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.fundraise.abstractclass.AbstractFT;
import io.securitize.tests.fundraise.pojo.FT_TestData;
import org.testng.annotations.Test;
import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT524_FT_Invest_CashAccount extends AbstractFT{

    @Test(description = "AUT524_FT_Nie_invest_CashAccount")
    public void AUT524_FT_Invest_CashAccount() throws Exception {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractFT.FT_TestScenario testScenario = FT_TestScenario.FT_INVEST_CASHACCOUNT;
        FT_TestData testdata = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Add Issuer And Token Id To TestData Object");
        testdata.issuerId = Users.getProperty(UsersProperty.ft_issuerId_aut524);
        testdata.tokenId = Users.getProperty(UsersProperty.ft_tokenId_aut524);
        endTestLevel();

        startTestLevel("Create Investor From Test Scenario Data");
        testdata = createFtTestInvestor(testdata);
        endTestLevel();

        startTestLevel("Navigate To issuer And Login With Crated Investor");
        navigateToIssuerNieWebSite(Users.getProperty(UsersProperty.ft_issuerNieUrl_aut524));
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

        startTestLevel("Sign Agreement");
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

        startTestLevel("Get Investment Atributes from CP via API");
        assertCpInvestmentViaApi(testScenario, testdata);
        endTestLevel();

    }

}
