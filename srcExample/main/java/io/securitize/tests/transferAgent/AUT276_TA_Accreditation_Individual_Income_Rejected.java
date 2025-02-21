package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT276_TA_Accreditation_Individual_Income_Rejected extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Individual Investor - Rejected")
    public void AUT276_TA_Accreditation_Individual_Income_Rejected() {

        final String laywer = "Securitize Automation AUT 276";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_individual_aut276);
        startTestLevel("Starting Accreditation of an individual investor by Income method with Rejected Status");
        loginAndCompleteAccreditation(investorMail, IndividualAccreditationMethod.INCOME.name(), laywer);
        setCpAccreditationStatusAndSubmit(investorMail, laywer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.REJECTED.toString());
        verifySecIdAccreditationStatus(AccreditationStatus.REJECTED.toString());
        endTestLevel();
    }

}