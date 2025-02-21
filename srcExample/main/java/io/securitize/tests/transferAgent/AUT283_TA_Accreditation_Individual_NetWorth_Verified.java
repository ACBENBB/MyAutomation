package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT283_TA_Accreditation_Individual_NetWorth_Verified extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Individual Investor, Method: NetWorth")
    public void AUT283_TA_Accreditation_Individual_NetWorth_Verified() {

        final String lawyer = "Securitize Automation AUT 283";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_individual_aut283);
        startTestLevel("Starting Accreditation of an individual investor by NetWorth method");
        loginAndCompleteAccreditation(investorMail, IndividualAccreditationMethod.NET_WORTH.name(), lawyer);
        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        verifySecIdAccreditationStatus(AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        endTestLevel();
    }

}
