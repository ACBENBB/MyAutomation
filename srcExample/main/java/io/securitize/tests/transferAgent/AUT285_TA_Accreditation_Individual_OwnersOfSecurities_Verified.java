package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT285_TA_Accreditation_Individual_OwnersOfSecurities_Verified extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Entity Investor, Method: Owners of Securities")
    public void AUT285_TA_Accreditation_Individual_OwnersOfSecurities_Verified() {

        final String lawyer = "Securitize Automation AUT 285";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_entity_aut285);
        startTestLevel("Starting Accreditation of an Entity investor by Owner of Securities method");
        loginAndCompleteAccreditation(investorMail, EntityAccreditationMethod.OWNERS_OF_EQUITY_SECURITIES.name(), lawyer);
        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        verifySecIdAccreditationStatus(AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        endTestLevel();
    }

}