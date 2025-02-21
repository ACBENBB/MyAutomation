package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT279_TA_Accreditation_Individual_Income_Expired extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Individual Investor - Expired")
    public void AUT279_TA_Accreditation_Individual_Income_Expired() {

        final String laywer = "Securitize Automation AUT 279";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_individual_aut279);
        startTestLevel("Starting Accreditation of an Individual investor by Income method status Expired");
        loginAndCompleteAccreditation(investorMail, IndividualAccreditationMethod.INCOME.name(), laywer);
        setCpAccreditationStatusAndSubmit(investorMail, laywer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.EXPIRED.toString());
        verifySecIdAccreditationStatus(AccreditationStatus.EXPIRED.toString());
        endTestLevel();
    }

}