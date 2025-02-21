package io.securitize.tests.fundraise;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.infra.wallets.UsingMetaMask;
import io.securitize.pageObjects.investorsOnboarding.nie.NieWelcomePage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT5_FT_InvestorRegistration_Individual_NIE_SumSub extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT5 - New Investor Experience - On-boarding Investor Registration USD")
    @UsingMetaMask
    public void AUT5_FT_InvestorRegistration_Individual_NIE_SumSub_USD() throws Exception {
        AUT5InternalLogic(CurrencyIds.USD);
    }

    @Test(description = "AUT5 - New Investor Experience - On-boarding Investor Registration ETH")
    @UsingMetaMask
    public void AUT5_FT_InvestorRegistration_Individual_NIE_SumSub_ETH() throws Exception {
        AUT5InternalLogic(CurrencyIds.ETH);
    }


    private void AUT5InternalLogic(CurrencyIds investmentCurrency) throws Exception {
        String issuerName = "Nie";
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        String investmentName = Users.getIssuerDetails(issuerName, IssuerDetails.investmentName);

        InvestorDetails investorDetails;
        if (investmentCurrency == CurrencyIds.ETH) {
            investorDetails = InvestorDetails.generateRandomNonUSInvestor();
            investorDetails.setCountry("Israel");
            investorDetails.setCountryOfTax("Israel");
            investorDetails.setPhoneNumber("0541234567");
            investorDetails.setState(null);
        } else {
            investorDetails = InvestorDetails.generateUSInvestorForSSN();
        }

        // phase 1 - create investor via API
        startTestLevel("Create investor");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorId = investorsAPI.createNewSecuritizeIdInvestor(investorDetails, true);
        endTestLevel();


        // phase 2 - use API to connect investor with issuer
        startTestLevel("Use API to connect investor with issuer");
        String investorCode = investorsAPI.getInvestorCode(issuerName, investorId);
        investorsAPI.authorizeIssuerForInvestor(issuerName, investorCode);
        endTestLevel();


        startTestLevel("Set accreditation Status to confirmed");
        // wait for userId to be return
        int waitTimeUseIdReturnSeconds = MainConfig.getIntProperty(MainConfigProperty.waitTimeUseIdReturnSeconds);
        int intervalTimeUserIdReturnSeconds = MainConfig.getIntProperty(MainConfigProperty.intervalTimeUserIdReturnSeconds);
        String issuerInvestorId = investorsAPI.getIssuersUserIdBySecuritizeIdProfileId(investorId, waitTimeUseIdReturnSeconds, intervalTimeUserIdReturnSeconds);
        investorsAPI.setAccreditationStatus(issuerInvestorId, issuerName, "confirmed");
        endTestLevel();


        startTestLevel("Login using email and password");
        getBrowser().navigateTo(MainConfig.getInvestPageUrl(issuerName));
        NieWelcomePage welcomePage = new NieWelcomePage(getBrowser());
        welcomePage
                .clickLogInWithoutAccountSelector()
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);
        endTestLevel();


        startTestLevel("Do Individual Investment");
        niePostSecIdRegistration(issuerName,
                investorDetails,
                investmentName,
                investmentCurrency,
                issuedTokenName,
                true);
        endTestLevel();
    }
}