package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT220_TA_Accreditation_Entity_Income_Verified extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Entity Investor Happy Path - Verified")
    public void AUT220_TA_Accreditation_Entity_Income_Verified() {

        final String lawyer = "Securitize Automation AUT 220";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_entity_aut220);
        startTestLevel("Starting Accreditation of an entity investor by Income method");
        loginAndCompleteAccreditation(investorMail, EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name(), lawyer);
        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.VERIFIED.toString());
        verifySecIdAccreditationStatus(AccreditationStatus.VERIFIED.toString());
        endTestLevel();
    }

}