package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT271_TA_Accreditation_Entity_Income_UpdatesRequired extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Entity Investor - Updates Required")
    public void AUT271_TA_Accreditation_Entity_Income_UpdatesRequired() {

        final String laywer = "Securitize Automation AUT 271";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_entity_aut271);
        final String accreditationMethod = EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name();
        startTestLevel("Starting Accreditation of an Entity non us investor by Income method status Updates Required");
        loginAndCompleteAccreditation(investorMail, accreditationMethod, laywer);
        setCpAccreditationStatusAndSubmit(investorMail, laywer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.UPDATES_REQUIRED.toString());
        provideSecIdAccreditationUpdates(investorMail, accreditationMethod);
        setCpAccreditationStatusAndSubmit(investorMail, laywer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.VERIFIED.toString());
        endTestLevel();
    }

}