package io.securitize.tests.ats;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.MarketsAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingSuitabiltyFormPopup;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket.TradingSecondaryMarketsAccreditationStatusPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket.TradingSecondaryMarketsAssetDetail;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket.TradingSecondaryMarketsSuitabilityFrom;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT184_ATS_InvestorRegistration_ATS_Ready extends AbstractSecIdNieSharedFlow {

    @Test(description = "Secondary Market - Register new Securitize iD investor which is ATS Ready")
    public void AUT184_ATS_InvestorRegistration_ATS_Ready() throws Exception {

        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);
        // Modifying the existing property for market one, to locate the market opportunity
        //String investmentName = Users.getIssuerDetails(issuerName, IssuerDetails.investmentName);
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String InvestorID = investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        String completeName = investorDetails.getFirstName() + " " + investorDetails.getMiddleName() + " " + investorDetails.getLastName();

        startTestLevel("Login using email and password");
        getBrowser().navigateTo(sidURL);
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeLogin
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard
                .clickSkipTwoFactor();
        securitizeLogin.waitForSIDToLogin();
        endTestLevel();

        startTestLevel("Go to Trading");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.clickTrading();
        //REMOVE This workaround: due to fundemental intraction issue - if we can't connect them - we will show maintenance .. refresh page fix that.
        getBrowser().refreshPage();
        endTestLevel();

        startTestLevel("Click suitability popup");
        TradingSecondaryMarketsAssetDetail secondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        secondaryMarketsAssetDetail.clickSuitabilityCard();
        PrimaryOfferingSuitabiltyFormPopup suitablityPopup = new PrimaryOfferingSuitabiltyFormPopup(getBrowser());
        suitablityPopup.clickCompleteInvestorProfileBtn();
        endTestLevel();

        startTestLevel("Complete Market Suitability");
        TradingSecondaryMarketsSuitabilityFrom tradingSecondaryMarketsSuitabilityFrom = new TradingSecondaryMarketsSuitabilityFrom(getBrowser());
        tradingSecondaryMarketsSuitabilityFrom.completeMarketSuitability();
        endTestLevel();

        startTestLevel("Click Accreditation in Opportunity Investment");
        AccreditationPage accreditationPage = secondaryMarketsAssetDetail.clickAccreditationCard();
        accreditationPage.clickIamAccreditedInvestor()
                .clickSubmitAccreditationStatusButton();
        endTestLevel();

        startTestLevel("Complete Accreditation");
        TradingSecondaryMarketsAccreditationStatusPage tradingSecondaryMarketsAccreditationStatusPage = new TradingSecondaryMarketsAccreditationStatusPage(getBrowser());
        tradingSecondaryMarketsAccreditationStatusPage.completeIndividualInvestorAccreditation();
        endTestLevel();

        startTestLevel("change investor's markets suitability to Verified");
        String securitizeIdProfileId = investorsAPI.getSecuritizeIdByName(investorDetails.getFirstName());
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketUSSuitability(securitizeIdProfileId,"verified");
        marketsAPI.setMarketAccreditation(securitizeIdProfileId, "verified");
        marketsAPI.setMarketUSAccountStatus(securitizeIdProfileId, "approved", "automation tester");
        endTestLevel();

        startTestLevel("verify tradable Assets card is visible");
        getBrowser().refreshPage();
        accreditationPage.clickTradingButton();
        endTestLevel();
    }
}