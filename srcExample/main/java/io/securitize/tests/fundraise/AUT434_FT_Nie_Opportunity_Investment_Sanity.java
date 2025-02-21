package io.securitize.tests.fundraise;

import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.Users;
import io.securitize.tests.fundraise.abstractclass.Nie;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;


public class AUT434_FT_Nie_Opportunity_Investment_Sanity extends Nie {


    @Test(description = "AUT434_FT_Nie_Opportunity_Investment_Sanity")
    public void AUT434_FT_Nie_Opportunity_Investment_Sanity() throws Exception {

        String issuerName = "Nie";
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        String investmentName = Users.getIssuerDetails(issuerName, IssuerDetails.investmentName);
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        startTestLevel("Register new SID investor via API");
        String seciD = createSecuritizeIdInvestor();
        endTestLevel();

        startTestLevel("Set KYC to verify");
        setKYCToVerified(seciD);
        endTestLevel();

        startTestLevel("Changing Suitability and Accreditation flags in Markets");
        setMarketSuitabilityStatus(seciD, "verified");
        setMarketsAccreditationStatus(seciD, "verified");
        setMarketsAccountStatus(seciD, "approved", "");
        endTestLevel();

        startTestLevel("login to Securitize Id > and NIE");
        loginToSecID(investorDetails.getEmail(), investorDetails.getPassword());
        loginToNie(investorDetails.getPassword());
        endTestLevel();

        startTestLevel("Complete opportunity investment");
        completeOpportunityInvestmentFlow(investmentName, issuedTokenName, investorDetails.getEmail());
        endTestLevel();

    }
}