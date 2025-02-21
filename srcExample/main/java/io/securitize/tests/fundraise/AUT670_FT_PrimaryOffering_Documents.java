package io.securitize.tests.fundraise;
import io.securitize.infra.config.*;
import io.securitize.pageObjects.investorsOnboarding.nie.NieInvestmentDetails;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingMarketPage;
import io.securitize.tests.fundraise.abstractclass.AbstractFT;
import io.securitize.tests.fundraise.pojo.FT_TestData;
import org.testng.annotations.Test;
import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;
public class AUT670_FT_PrimaryOffering_Documents extends AbstractFT {
    @Test(description = "AUT2548 - Primary Offerings - Flow for opportunity documents download")
    public void AUT670_FT_PrimaryOffering_Documents() throws Exception {
        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractFT.FT_TestScenario testScenario = AbstractFT.FT_TestScenario.FT_INVEST_VERIFYDOC;
        FT_TestData testdata = createTestDataObject(testScenario);
        endTestLevel();
        startTestLevel("Add Issuer And Token Id To TestData Object");
        testdata.issuerId = Users.getProperty(UsersProperty.ft_issuerId_aut548);
        testdata.tokenId = Users.getProperty(UsersProperty.ft_tokenId_aut548);
        endTestLevel();
        startTestLevel("Create Investor From Test Scenario Data");
        testdata = createFtTestInvestor(testdata);
        endTestLevel();
        startTestLevel("Login To SiD with Created Accreditation First Investor");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdInvestorLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdInvestorLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);
        endTestLevel();
        startTestLevel("Navigate To Primary Offerings");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard
                .clickSkipTwoFactor()
                .primaryOfferingClick();
        endTestLevel();
        startTestLevel(" Click on Primary Offerings Opportunity");
        PrimaryOfferingMarketPage marketPage = new PrimaryOfferingMarketPage(getBrowser());
        marketPage.clickOnPrimaryOfferingsCardOpp(testdata.investmentName);
        endTestLevel();

        startTestLevel("Verify Documents available on the opportunity");
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        nieInvestmentDetails.verifyDocumentsSectionIsDisplayed();
        nieInvestmentDetails.verifyDocumentsTitleIsDisplayed();
        nieInvestmentDetails.verifyDownloadDocuments();
        endTestLevel();
    }
}


