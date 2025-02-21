package io.securitize.tests.securitizeId;

import io.securitize.infra.api.LoginAPI;
import io.securitize.infra.config.*;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import io.securitize.tests.InvestorDetails;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT67_SID_SecuritizeID_KYB_Regression_US extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT67 - SecuritizeID KYB Regression - US")
    public void AUT67_SID_SecuritizeID_KYB_Regression_US_Test() {

        String state = "Alaska";

        startTestLevel("Register investor via login page");
        InvestorDetails investorDetails = performSecuritizeIdCreateAccountAndLogin("randomEntityInvestor", true, state);
        endTestLevel();


        startTestLevel("Fill KYB stepper to investor registered via login page");
        fillEntityKybSteps(investorDetails, "Individual", false);
        endTestLevel();


        startTestLevel("Set status to verified");
        LoginAPI loginAPI = new LoginAPI();
        String investorId = loginAPI.postLoginSecuritizeId(investorDetails.getEmail(), investorDetails.getPassword());
        setKYCAsVerified(investorId, true);
        endTestLevel();


        startTestLevel("Register investor via 'Create Account' button inside user menu");
        addEntityInvestorViaUserMenu(investorDetails);
        endTestLevel();


        startTestLevel("Fill KYB stepper to investor registered via 'Create Account' button inside user menu");
        fillEntityKybSteps(investorDetails, "Individual", false);
        endTestLevel();
    }
}