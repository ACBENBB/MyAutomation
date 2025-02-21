package io.securitize.tests.fundraise;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.investWizard.InvestWizard_SignAgreement;
import io.securitize.pageObjects.investorsOnboarding.nie.docuSign.DocusignSigningPage;
import io.securitize.tests.fundraise.abstractclass.AbstractFT;
import io.securitize.tests.fundraise.pojo.FT_TestData;
import org.testng.annotations.Test;


import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT548_FT_NieInvestment_Docusign_WireTransfer extends AbstractFT {

        @Test(description = "AUT548_FT_NieInvestment_Docusign_WireTransfer")
        public void AUT548_FT_NieInvestment_Docusign_WireTransfer() throws Exception {

            startTestLevel("Define Test Scenario and Create Test Data Object");
            FT_TestScenario testScenario = FT_TestScenario.FT_INVEST_DOCUSIGN;
            FT_TestData testdata = createTestDataObject(testScenario);
            endTestLevel();

            startTestLevel("Add Issuer And Token Id To TestData Object");
            testdata.issuerId = Users.getProperty(UsersProperty.ft_issuerId_aut548);
            testdata.tokenId = Users.getProperty(UsersProperty.ft_tokenId_aut548);
            endTestLevel();

            startTestLevel("Create Investor From Test Scenario Data");
            testdata = createFtTestInvestor(testdata);
            endTestLevel();

            startTestLevel("Navigate To issuer And Login With Crated Investor");
            navigateToIssuerNieWebSite(Users.getProperty(UsersProperty.ft_issuerNieUrl_aut548));
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

            startTestLevel("Click on Sign Agreement");
            InvestWizard_SignAgreement investWizardSignAgreement = new InvestWizard_SignAgreement(getBrowser());
            DocusignSigningPage signingPage = investWizardSignAgreement.clickSignAgreement();
            endTestLevel();

            startTestLevel("Sign Document");
            signingPage.signDocument();
            endTestLevel();

            startTestLevel("Click Continue at the Docusign Flow End");
            investWizardSignAgreement.clickContinue();
            endTestLevel();

            startTestLevel("Assert Fund Your Investment Page");
            assertFundYourInvestmentPageWireTransferDetails();
            endTestLevel();


            startTestLevel("Go Back To Opportunity Invest Page");
            clickOnFundYourInvestmentPageExitProcess();
            endTestLevel();

            startTestLevel("update subscriptionAgreementStatus on CP via API from Signed to Completed");
            updatesSubscriptionAgreementStatusViaApi(testdata);
            endTestLevel();

            startTestLevel("Assert Funding Instructions Msg");
            assertInvestmentApprovedMsgWireTransfer();
            endTestLevel();

            startTestLevel("Get Investment Attributes from CP via API");
            assertCpInvestmentViaApi(testScenario, testdata);
            endTestLevel();
        }

}
