package io.securitize.tests.securitizeId;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.pageObjects.investorsOnboarding.nie.NieDashboard;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import static io.securitize.infra.reporting.MultiReporter.*;
import io.securitize.tests.InvestorDetails;
import org.testng.annotations.Test;

public class AUT504_SID_Redirect_Nie_To_SecuritizeID_And_Back extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT504 - Check redirect from NIE dashboard to SID KYC page (completing) & redirect back to NIE Dashboard")
    public void AUT504_SID_Redirect_Nie_To_SecuritizeiD_And_Back_Test() {

        String issuerName = "Nie";
        boolean isEnvironmentProduction = MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        investorDetails.setCountry("Israel");
        investorDetails.setCountryOfTax("Israel");
        investorDetails.setPhoneNumber("0541234567");
        investorDetails.setState(null);

        startTestLevel("Register Individual");
        nieInitialSecIdRegistration(issuerName, investorDetails);
        endTestLevel();


        startTestLevel("Do Individual KYC");
        if (!isEnvironmentProduction) {
            fillIndividualKycSteps(investorDetails, true, NieDashboard.class, false);
        } else {
            fillIndividualKycSteps(investorDetails, false, NieDashboard.class, false);
        }
        endTestLevel();
    }
}
