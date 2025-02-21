package io.securitize.tests.securitizeId;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import org.testng.annotations.Test;

public class AUT444_SID_Individual_Flow_Sanity extends AbstractSecIdInvestorRegistrationFlow {

    @Test(description = "AUT444 - Create SecuritizeId NonUS Investor (part of AUT293)", groups = {"sanitySID"})
    public void AUT444_SID_Individual_Flow_Sanity_Test() {

        startTestLevel("SecuritizeId create an account");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        InvestorDetails investorDetails = InvestorDetails.generateRandomNonUSInvestor();

        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        SecuritizeIdCreateAccountRegistrationStep1 securitizeIdCreateAccountRegistrationStep1 = securitizeIdLoginScreen
                .clickAcceptCookies()
                .clickCreateAccount();
        securitizeIdCreateAccountRegistrationStep1
                .createInvestor(investorDetails);
        endTestLevel();


        startTestLevel("Extract link from email and navigate");
        SecuritizeIdCreateAccountRegistrationStep3 securitizeIdCreateAccountRegistrationStep3 = new SecuritizeIdCreateAccountRegistrationStep3(getBrowser());
        securitizeIdCreateAccountRegistrationStep3.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
        endTestLevel();


        startTestLevel("Skip two factor authentication");
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard
                .clickSkipTwoFactor()
                .clickCompleteYourDetails();
        endTestLevel();
    }
}