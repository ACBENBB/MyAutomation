package io.securitize.scripts;

import io.securitize.infra.api.*;
import io.securitize.tests.abstractClass.AbstractTest;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class AUT446_AssociateSidInvestorToIssuer extends AbstractTest {

    static final String ISSUER_ID = "a69cff9b-56d4-44b7-bcd2-7ac21602d57f";
    static final String SECID_PROFILEID = "64c117b877b80c10311f76de";

    @Test(description = "AUT446 ' Script to Associate Sid Investor to Issuer DB")
    public void AUT446_AssociateSidInvestorToIssuer() {
        String issuerId = getIssuerId();
        String scuritizeProfileId = getSecuritizeProfileId();

        TransferAgentAPI.associateSidInvestorToIssuerOnboardingDB(issuerId, scuritizeProfileId);
    }


    private static String getIssuerId() {
        String valueFromEnvironment = System.getenv("issuerId");
        if (valueFromEnvironment == null) {
            return ISSUER_ID;
        }
        return valueFromEnvironment;
    }

    private String getSecuritizeProfileId() {
        String valueFromEnvironment = System.getenv("securitizeId");
        if (valueFromEnvironment == null) {
            return SECID_PROFILEID;
        }
        return valueFromEnvironment;
    }

}
