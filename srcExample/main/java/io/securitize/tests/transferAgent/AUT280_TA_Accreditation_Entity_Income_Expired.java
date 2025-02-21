package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT280_TA_Accreditation_Entity_Income_Expired extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Entity Investor - Expired")
    public void AUT280_TA_Accreditation_Entity_Income_Expired() {

        final String laywer = "Securitize Automation AUT 280";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_entity_aut280);
        startTestLevel("Starting Accreditation of an Entity investor by Income method status Expired");
        loginAndCompleteAccreditation(investorMail, EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name(), laywer);
        setCpAccreditationStatusAndSubmit(investorMail, laywer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.EXPIRED.toString());
        verifySecIdAccreditationStatus(AccreditationStatus.EXPIRED.toString());
        endTestLevel();
    }

}