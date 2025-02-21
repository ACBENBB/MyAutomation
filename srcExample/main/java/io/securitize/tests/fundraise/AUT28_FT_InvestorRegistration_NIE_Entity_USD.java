package io.securitize.tests.fundraise;

import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.Users;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.infra.wallets.UsingMetaMask;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT28_FT_InvestorRegistration_NIE_Entity_USD extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT28 - New Investor Experience - On-boarding Investor Registration - USD entity")
    @UsingMetaMask
    public void AUT28_FT_InvestorRegistration_NIE_Entity_USD_test() throws Exception {
        AUT28InternalLogic(CurrencyIds.USD, "Entity");
    }

    private void AUT28InternalLogic(CurrencyIds investmentCurrency, String legalSignerType) throws Exception {
        String issuerName = "Nie";
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        String investmentName = Users.getIssuerDetails(issuerName, IssuerDetails.investmentName);

        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();

        startTestLevel("Register Entity");
        nieInitialSecIdRegistration(issuerName, investorDetails);
        endTestLevel();

        startTestLevel("Do Entity KYB");
        fillEntityKybSteps(investorDetails, legalSignerType,true);
        endTestLevel();

        startTestLevel("Do Entity Investment");
        niePostSecIdRegistration(issuerName,
                investorDetails,
                investmentName,
                investmentCurrency,
                issuedTokenName,
                true);
        endTestLevel();
    }
}