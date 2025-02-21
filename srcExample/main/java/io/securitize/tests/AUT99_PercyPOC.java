package io.securitize.tests;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.utils.UseVisualTesting;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT99_PercyPOC extends AbstractSecIdInvestorRegistrationFlow {

    @Test(description = "Percy POC test")
    @UseVisualTesting
    public void percyPOCtest() throws IOException {

        String state = "Alaska";

        startTestLevel("SecuritizeId create an account");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        investorDetails.setState(state);

        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        visualTesting.snapshot("SecuritizeId validate 01 - Login screen");

        //those lines were commented as the flow was changed so this code is obsolete & not relevant

//        SecuritizeIdRegistration01CreateAccountOldDelete securitizeIdCreateAccountPage = securitizeIdLoginScreen
//                .clickAcceptCookies()
//                .clickCreateAccountOldDelete();
//
//        securitizeIdCreateAccountPage
//                .insertEmailAddress(investorDetails.getEmail())
//                .insertPassword(investorDetails.getPassword())
//                .setCountryAndState("United States of America", "Alaska")
//                .disableReCaptcha()
//                .clickCreateAccountButton();
//        endTestLevel();
//
//
//        startTestLevel("Extract link from email and navigate");
//        securitizeIdCreateAccountPage.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
//        endTestLevel();
//
//
//        startTestLevel("Skip two factor authentication");
//        SecuritizeIdDashboard securitizeIDMyAccount = new SecuritizeIdDashboard(getBrowser());
//        ExtendedBy[] ignoreList = securitizeIDMyAccount.getVisualTestingIgnoreList();
//        visualTesting.snapshot("SecuritizeId validate 02 - SecuritizeId ask for two factor authentication", ignoreList);
//        securitizeIDMyAccount
//                .clickSkipTwoFactor()
//                .clickCompleteYourDetails();
//        endTestLevel();



       // those are the new relevant lines for investor registration:
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

  /*      startTestLevel("Register investor");
        fillIndividualRegistrationSteps(investorDetails, false, false, false);
        endTestLevel();*/
    }
}