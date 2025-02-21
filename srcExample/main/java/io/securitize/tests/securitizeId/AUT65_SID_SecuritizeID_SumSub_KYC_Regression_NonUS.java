package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.*;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.tests.InvestorDetails;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT65_SID_SecuritizeID_SumSub_KYC_Regression_NonUS extends AbstractSecIdInvestorRegistrationFlow {

    @Test(description = "AUT65 - SecuritizeID SumSub KYC Regression - NonUS")
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    public void AUT65_SID_SecuritizeID_SumSub_KYC_Regression_NonUS_Test() {

        boolean isEnvironmentProduction = MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        InvestorDetails investorDetails = performSecuritizeIdCreateAccountAndLogin("randomNonUSInvestor", true);

        startTestLevel("Register investor (KYC stepper) whom Sumsub approved (verified)");
        if (!isEnvironmentProduction) {
            fillIndividualKycSteps(investorDetails, false, SecuritizeIdDashboard.class, true);
        } else {
            fillIndividualKycSteps(investorDetails, false, SecuritizeIdDashboard.class, false);
            String securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
            investorsAPI.setKYCToPassed(securitizeIdProfileId, null);
        }
        String securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
        endTestLevel();


        startTestLevel("API check of SID KYC and failure reason");
        // method getSecIdKycStatus has a delay in its update on rc
        investorsAPI.waitForKycStatusUpdateToVerified(securitizeIdProfileId, 60, 500);
        JSONObject secIdKycResult = investorsAPI.getSecIdKycStatus(securitizeIdProfileId);
        Assert.assertEquals(secIdKycResult.getString("status").trim().toLowerCase(), "verified", "Verification status at this point should be 'verified' but this is not the case");
        endTestLevel();
    }
}

