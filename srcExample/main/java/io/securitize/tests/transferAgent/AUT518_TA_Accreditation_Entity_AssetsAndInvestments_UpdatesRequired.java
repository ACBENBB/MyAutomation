package io.securitize.tests.transferAgent;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT518_TA_Accreditation_Entity_AssetsAndInvestments_UpdatesRequired extends AbstractAccreditation {

        @Test(description = "TA - Accreditation for Entity Investor - Assets & Investments - Updates Required")
        public void AUT518_TA_Accreditation_Entity_AssetsAndInvestments_UpdatesRequired() {

            final String lawyer = "Securitize Automation AUT 518";
            final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_entity_aut518);
            final String accreditationMethod = AbstractAccreditation.EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name();

            startTestLevel("Starting Accreditation of an entity investor by Assets and Investments method status Updates Required");
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
