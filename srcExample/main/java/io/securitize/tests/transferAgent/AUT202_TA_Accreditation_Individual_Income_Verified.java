package io.securitize.tests.transferAgent;

import io.securitize.infra.config.*;
import io.securitize.infra.utils.*;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.*;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT202_TA_Accreditation_Individual_Income_Verified extends AbstractAccreditation {

    @SkipTestOnEnvironments(environments = {"production"})
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @Test(description = "TA - Individual Investor Accreditation Method Income Verified", groups = {"sanityTA"})
    public void AUT202_TA_Accreditation_Individual_Income_Verified() {

        final String lawyer = "Securitize Automation AUT 202";
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_individual_aut202);
        startTestLevel("Starting Accreditation of a us individual investor by Income method");
        loginAndCompleteAccreditation(investorMail, IndividualAccreditationMethod.INCOME.name(), lawyer);
        setCpAccreditationStatusAndSubmit(investorMail, lawyer,
                AccreditationStatus.PROCESSING.name(),
                AccreditationStatus.VERIFIED.toString());
        verifySecIdAccreditationStatus(AccreditationStatus.VERIFIED.toString());
        endTestLevel();

    }

}