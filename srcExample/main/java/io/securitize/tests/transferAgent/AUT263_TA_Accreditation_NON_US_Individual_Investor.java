package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT263_TA_Accreditation_NON_US_Individual_Investor extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Individual NON US Investor Happy Path")
    public void AUT263_TA_Accreditation_NON_US_Individual_Investor() {

        final String lawyer = ""; // NON US investors DONT enter the Accreditation flow.
        final String accreditationMethod = InvestorType.INDIVIDUAL_NON_US.toString();
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_nonus_individual_aut263);
        startTestLevel("Starting Accreditation of a non us individual investor by Income method");
        loginAndCompleteAccreditation(investorMail, accreditationMethod, lawyer);
        verifySecIdNonUsAccreditationStatus();
        endTestLevel();
    }

}