package io.securitize.tests.fundraise;

import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT555_FT_Individual_Sanity_Flow extends AbstractSecIdNieSharedFlow {

    @BypassRecaptcha(environments = {"production"})
    @Test(description = "AUT555 - Create SecuritizeId NonUS Investor & register via NIE" ,groups = {"sanityFT"})
    public void AUT555_FT_Individual_Sanity_Flow_Test() {

        String issuerName = "Nie";
        startTestLevel("SecuritizeId create an account");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        investorDetails.setCountry("Israel");
        investorDetails.setState(null);
        investorDetails.setPhoneNumber("0502345678");
        endTestLevel();


        startTestLevel("Register Individual");
        nieInitialSecIdRegistration(issuerName, investorDetails);
        endTestLevel();
    }
}