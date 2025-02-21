package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.*;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT475_TA_Accreditation_Individual_Income_UpdatesRequired extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Individual Investor - Updates Required")
    public void AUT475_TA_Accreditation_Individual_Income_UpdatesRequired_test() {

        final String laywer = "Securitize Automation AUT 475";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_individual_aut475);
        final String accreditationMethod = AbstractAccreditation.IndividualAccreditationMethod.INCOME.name();
        startTestLevel("Starting Accreditation of an individual investor by Income method status Updates Required");
        loginAndCompleteAccreditation(investorMail, accreditationMethod, laywer);
        setCpAccreditationStatusAndSubmit(investorMail, laywer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AbstractAccreditation.AccreditationStatus.UPDATES_REQUIRED.toString());
        provideSecIdAccreditationUpdates(investorMail, accreditationMethod);
        setCpAccreditationStatusAndSubmit(investorMail, laywer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        endTestLevel();
    }


}
