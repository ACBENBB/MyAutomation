package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT278_TA_Accreditation_Entity_Income_Rejected extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Entity Investor - Rejected")
    public void AUT278_TA_Accreditation_Entity_Income_Rejected() {

        final String laywer = "Securitize Automation AUT 278";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_entity_aut278);
        startTestLevel("Starting Accreditation of an Entity investor by Income method status Rejected");
        loginAndCompleteAccreditation(investorMail, EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name(), laywer);
        setCpAccreditationStatusAndSubmit(investorMail, laywer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.REJECTED.toString());
        verifySecIdAccreditationStatus(AccreditationStatus.REJECTED.toString());
        endTestLevel();
    }

}