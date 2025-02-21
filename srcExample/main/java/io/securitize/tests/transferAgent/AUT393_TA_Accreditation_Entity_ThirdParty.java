package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.*;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT393_TA_Accreditation_Entity_ThirdParty extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Entity Investor - Third Party Letter")
    public void AUT393_TA_Accreditation_Entity_ThirdParty() {

        final String lawyer = "Securitize Automation AUT 393";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_entity_aut393);
        startTestLevel("Starting Accreditation of an Entity investor by Third Party method");
        loginAndCompleteAccreditation(investorMail, EntityAccreditationMethod.THIRD_PARTY.name(), lawyer);
        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        verifySecIdAccreditationStatus(AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        endTestLevel();
    }

}