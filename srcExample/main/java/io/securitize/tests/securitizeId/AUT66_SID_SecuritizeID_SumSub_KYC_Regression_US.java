package io.securitize.tests.securitizeId;

import io.securitize.infra.api.*;
import io.securitize.infra.config.*;
import io.securitize.infra.utils.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import io.securitize.tests.InvestorDetails;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT66_SID_SecuritizeID_SumSub_KYC_Regression_US extends AbstractSecIdInvestorRegistrationFlow {
    //Devops stopped Idology proxy (and thus receiving 502)
//    @IdologyDependent
    @Test(description = "AUT66 - SecuritizeID SumSub KYC Regression - US", groups = {"coreSID"})
    public void AUT66_SID_SecuritizeID_SumSub_KYC_Regression_US_Test() {

        String state = "Alaska";
        boolean isEnvironmentProduction = MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        InvestorDetails investorDetails = performSecuritizeIdCreateAccountAndLogin("usInvestorForSSN", true, state);

        startTestLevel("Register investor (KYC stepper) whom Sumsub approved (verified)");
        if (!isEnvironmentProduction) {
            fillIndividualKycSteps(investorDetails, true, SecuritizeIdDashboard.class, true);
        } else {
            //on production sumsub doesn't accept the uploaded images (status code 400)
            fillIndividualKycSteps(investorDetails, false, SecuritizeIdDashboard.class, false);
            String securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
            investorsAPI.setKYCToPassed(securitizeIdProfileId, null);
        }
        String securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
        endTestLevel();


        startTestLevel("API check of SID KYC and failure reason");
        JSONObject secIdKycResult = investorsAPI.getSecIdKycStatus(securitizeIdProfileId);
        Assert.assertEquals(secIdKycResult.getString("status").trim().toLowerCase(), "verified", "Verification status at this point should be 'verified' but this is not the case");
        endTestLevel();
    }
}