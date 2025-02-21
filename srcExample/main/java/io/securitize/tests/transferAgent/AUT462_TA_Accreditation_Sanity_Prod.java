package io.securitize.tests.transferAgent;

import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT462_TA_Accreditation_Sanity_Prod extends AbstractAccreditation {


    @BypassRecaptcha(environments = "production")
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @SkipTestOnEnvironments(environments = {"rc", "sandbox"})
    @Test(description = "TA - Individual Investor Accreditation Sanity Prod", groups = {"sanityTA"})
    public void AUT462_TA_Accreditation_Sanity_Prod() {
        final String investorMail = Users.getProperty(UsersProperty.ta_accreditation_sanity_prod_aut462);
        startTestLevel("Starting Accreditation Individual Investor Accreditation Sanity Prod");
        accreditationProdSanity(investorMail, IndividualAccreditationMethod.INCOME_PROD.name());
        endTestLevel();
    }

}