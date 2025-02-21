package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.sumsub.RejectionLabels;
import io.securitize.infra.api.sumsub.ReviewRejectType;
import io.securitize.infra.api.sumsub.SumSubAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.Assert;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT293_SID_SumSub_Verified_Documents_Expired extends AbstractSecIdInvestorRegistrationFlow {

    @Test(description = "AUT293 - SumSub NonUS Investor's Status + sub status - verified[documents-expired]")
    public void AUT293_SID_SumSub_Verified_Documents_Expired_Test() {

        boolean isEnvironmentProduction = MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        InvestorDetails investorDetails = performSecuritizeIdCreateAccountAndLogin("randomNonUSInvestor", true);
        String securitizeIdProfileId;
        JSONObject secIdKycResult;

        startTestLevel("Register investor (KYC stepper) whom Sumsub approved (verified)");
        if (!isEnvironmentProduction) {
            fillIndividualKycSteps(investorDetails, true, SecuritizeIdDashboard.class, true);
            securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
            SumSubAPI.setApplicantAsProvidedInfo(securitizeIdProfileId);
            SumSubAPI.setApplicantAsProvidedInfo(securitizeIdProfileId);
        } else {
            securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
            investorsAPI.setKYCToPassed(securitizeIdProfileId, null);
        }
        endTestLevel();


        startTestLevel("SumSub: Verified[documents-expired]");
        if (!isEnvironmentProduction) {
            RejectionLabels[] rejectionLabels = {RejectionLabels.EXPIRATION_DATE};
            ReviewRejectType reviewRejectType = ReviewRejectType.RETRY;
            String moderationComment = "automation test - verified[documents-expired]";
            SumSubAPI.setApplicantAsRejected(securitizeIdProfileId, rejectionLabels, reviewRejectType, moderationComment);
        } else {
            investorsAPI.setVerificationStatus(securitizeIdProfileId, "verified", "documents-expired");

        }
        endTestLevel();


        startTestLevel("API check of SID KYC and failure reason");
        if (!isEnvironmentProduction) {
            // method getSecIdKycStatus has a delay in its update on rc
            investorsAPI.waitForKycStatusUpdateToVerified(securitizeIdProfileId, 60, 500, 1);
            secIdKycResult = investorsAPI.getSecIdKycStatus(securitizeIdProfileId, 1);
        } else {
            // method getSecIdKycStatus response has a different ordering in production than the other environments
            secIdKycResult = investorsAPI.getSecIdKycStatus(securitizeIdProfileId, 0);
        }
        Assert.assertEquals(secIdKycResult.getString("status").trim().toLowerCase(), "verified", "Verification status at this point should be 'verified' but this is not the case");
        Assert.assertEquals(secIdKycResult.getString("subStatus").trim().toLowerCase(), "documents-expired", "Verification sub status at this point should be 'documents-expired' but this is not the case");
        if (!isEnvironmentProduction) {
            // error can't be updated via an API call on production
            Assert.assertEquals(secIdKycResult.getJSONArray("verificationErrors").getString(0).trim().toLowerCase(), "expiration_date", "Failure reason at this point should be 'expiration_date' but this is not the case");
        }
        endTestLevel();
    }
}