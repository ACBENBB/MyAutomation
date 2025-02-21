package io.securitize.tests.fundraise.abstractclass;

import io.securitize.infra.api.*;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.*;
import io.securitize.pageObjects.investorsOnboarding.nie.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.tests.*;
import io.securitize.tests.abstractClass.*;

import static io.securitize.infra.reporting.MultiReporter.*;
import static io.securitize.infra.reporting.MultiReporter.endTestLevel;

public class Nie extends AbstractUiTest {

    public InvestorDetails investorDetails;
    private static final String ISSUER_NAME = "Nie";

    public String createSecuritizeIdInvestor() throws Exception {
        investorDetails = InvestorDetails.generateRandomUSInvestor();
        investorDetails = InvestorDetails.generateUSInvestorForSSN();

        startTestLevel("Create SecuritizeID investor using API");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        return investorsAPI.createNewSecuritizeIdInvestor(investorDetails, true);
    }

    public void addDetailsToInvestor(String secId) {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.addInvestorDetails(secId, investorDetails);
    }

    public void setKYCToVerified(String secId) {
        startTestLevel("set kyc status to verified");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.setKYCToPassed(secId, "verified");
        endTestLevel();
    }

    public void setMarketsAccreditationStatus(String secId, String status) {
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketAccreditation(secId, status);
    }

    public void setMarketsAccountStatus(String secId, String accreditationStatus, String signature) {
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketUSAccountStatus(secId, accreditationStatus, signature);
    }

    public void setMarketSuitabilityStatus(String secId, String accreditationStatus) {
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketUSSuitability(secId, accreditationStatus);
    }

    public void loginToSecID(String investorEmail, String investorPassword) {
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(investorEmail, investorPassword, false)
                .waitForSIDToLogin()
                .clickSkipTwoFactor()
                .clickHomeLink()
                .performLogout();
    }

    public void loginToNie(String investorPassword) {
        getBrowser().navigateTo(MainConfig.getInvestPageUrl(ISSUER_NAME));
        NieWelcomePage welcomePage = new NieWelcomePage(getBrowser());
        welcomePage
                .clickLogInWithoutAccountSelector()
                .performLoginWithCredentials(investorPassword);
        SecuritizeIdLoginScreenLoggedIn securitizeIdLoginScreenLoggedIn = new SecuritizeIdLoginScreenLoggedIn(browser.get());
        securitizeIdLoginScreenLoggedIn.clickAllowButton();
    }

    public void completeOpportunityInvestmentFlow(String investmentName, String issuedTokenName, String investorEmail) {

        NieDashboard nieDashboard = new NieDashboard(getBrowser());
        startTestLevel("Handle 'Opportunities' tab - 01 details");
        NieDashboardOpportunities nieDashboardOpportunities = nieDashboard
                .switchToOpportunitiesTab();
        NieInvestmentDetails nieInvestmentDetails = nieDashboardOpportunities.clickViewInvestmentDetailsByName(investmentName);
        nieInvestmentDetails
                .setInvestmentCurrency(CurrencyIds.USD)
                .setInvestmentAmount(1000)
                .getReceivedUnits();
        nieInvestmentDetails.clickInvest();
    }
}