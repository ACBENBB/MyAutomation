package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.*;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT392_TA_Accreditation_Individual_ThirdParty extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Individual Investor - Third Party Letter")
    public void AUT392_TA_Accreditation_Individual_ThirdParty() {

        final String lawyer = "Securitize Automation AUT 392";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_individual_aut392);
        startTestLevel("Starting Accreditation of an individual investor by Third Party method");
        loginAndCompleteAccreditation(investorMail, IndividualAccreditationMethod.THIRD_PARTY.name(), lawyer);
        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AbstractAccreditation.AccreditationStatus.PROCESSING.name(),
                AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        verifySecIdAccreditationStatus(AbstractAccreditation.AccreditationStatus.VERIFIED.toString());
        endTestLevel();
    }

}