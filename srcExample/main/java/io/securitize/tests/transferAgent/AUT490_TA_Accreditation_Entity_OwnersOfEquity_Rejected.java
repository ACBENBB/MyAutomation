package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.*;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT490_TA_Accreditation_Entity_OwnersOfEquity_Rejected extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Entity Investor Owners Of Equity - Rejected")
    public void AUT490_TA_Accreditation_Entity_OwnersOfEquity_Rejected() {

        final String lawyer = "Securitize Automation AUT 490";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_entity_aut490);
        startTestLevel("Starting Accreditation of an entity investor by Income method");
        loginAndCompleteAccreditation(investorMail, EntityAccreditationMethod.OWNERS_OF_EQUITY_SECURITIES.name(), lawyer);
        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.REJECTED.toString());
        verifySecIdAccreditationStatus(AccreditationStatus.REJECTED.toString());
        endTestLevel();
    }


}
