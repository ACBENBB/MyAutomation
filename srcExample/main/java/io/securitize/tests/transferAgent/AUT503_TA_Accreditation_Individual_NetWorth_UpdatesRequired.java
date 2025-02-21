package io.securitize.tests.transferAgent;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT503_TA_Accreditation_Individual_NetWorth_UpdatesRequired extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Individual Investor Net Worth - Updates Required")
    public void AUT503_TA_Accreditation_Individual_NetWorth_UpdatesRequired() {

        final String lawyer = "Securitize Automation AUT 503";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_individual_aut503);
        final String accreditationMethod = AbstractAccreditation.IndividualAccreditationMethod.NET_WORTH.name();

        startTestLevel("Starting Accreditation of an individual investor by Net Worth method status Updates Required");

        loginAndCompleteAccreditation(investorMail, accreditationMethod, lawyer);

        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AbstractAccreditation.AccreditationStatus.UPDATES_REQUIRED.toString());

        provideSecIdAccreditationUpdates(investorMail, accreditationMethod);

        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AbstractAccreditation.AccreditationStatus.VERIFIED.toString());

        endTestLevel();
    }
}
