package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT284_TA_Accreditation_Individual_ProfessionalLicence_Verified extends AbstractAccreditation {

    @Test(description = "TA - Accreditation for Individual Investor, Method: Professional Licence")
    public void AUT284_TA_Accreditation_Individual_ProfessionalLicence_Verified() throws Exception {

        final String lawyer = "Securitize Automation AUT 284";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_individual_aut284);
        final String investorPassword = Users.getProperty(UsersProperty.ta_distributionGenericInvestorPassword_aut365);
        startTestLevel("Starting Accreditation of an individual investor by Professional Licence method");
        loginAndCompleteAccreditation(investorMail, investorPassword, IndividualAccreditationMethod.PROFESSIONAL_LICENSE.name(), lawyer);
        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.VERIFIED.toString());
        verifySecIdAccreditationStatus(AccreditationStatus.VERIFIED.toString());
        endTestLevel();
    }

}
