package io.securitize.tests.fundraise;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.infra.wallets.UsingCoinBase;
import io.securitize.pageObjects.investorsOnboarding.nie.NieWelcomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdLoginScreenLoggedIn;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT188_FT_InvestorRegistration_Individual_NIE_CoinBase extends AbstractSecIdNieSharedFlow {

    private static final String ISSUER_NAME = "Nie";

    @Test(description = "AUT188 - New Investor Experience - On-boarding Investor Registration using Coinbase Wallet")
    @UsingCoinBase
    @Parameters({ "investmentCurrency" })
    public void AUT188_FT_InvestorRegistration_Individual_NIE_CoinBase(@Optional("unknown") CurrencyIds investmentCurrency) throws Exception {

        String issuerName = "Nie";
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        String investmentName = Users.getIssuerDetails(issuerName, IssuerDetails.investmentName);

        InvestorDetails investorDetails;
        if (investmentCurrency == CurrencyIds.ETH) {
            investorDetails = InvestorDetails.generateRandomUSInvestor();
            investorDetails.setCountry("Israel");
            investorDetails.setCountryOfTax("Israel");
            investorDetails.setState(null);
        } else {
            investorDetails = InvestorDetails.generateUSInvestorForSSN();
        }

        startTestLevel("Create SecuritizeID investor using API");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.createNewSecuritizeIdInvestor(investorDetails, true);
        endTestLevel();

        startTestLevel("Login to SiD and then to issuer page");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false)
                .waitForSIDToLogin()
                .clickSkipTwoFactor()
                .clickHomeLink()
                .performLogout();

        getBrowser().navigateTo(MainConfig.getInvestPageUrl(ISSUER_NAME));
        NieWelcomePage welcomePage = new NieWelcomePage(getBrowser());
        welcomePage
                .clickLogInWithoutAccountSelector()
                .performLoginWithCredentials(investorDetails.getPassword());
        SecuritizeIdLoginScreenLoggedIn securitizeIdLoginScreenLoggedIn = new SecuritizeIdLoginScreenLoggedIn(browser.get());
        securitizeIdLoginScreenLoggedIn.clickAllowButton();
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