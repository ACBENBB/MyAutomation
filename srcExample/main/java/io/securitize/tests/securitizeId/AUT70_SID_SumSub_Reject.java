package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.sumsub.*;
import io.securitize.infra.config.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.Assert;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT70_SID_SumSub_Reject extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT70 - SumSub - Investor's Status - Reject")
    public void AUT70_SID_SumSub_Reject_Test() {

        String state = "Alaska";
        boolean isEnvironmentProduction = MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        InvestorDetails investorDetails = performSecuritizeIdCreateAccountAndLogin("usInvestorForSSN", true, state);

        startTestLevel("Register investor (KYC stepper)");
        if (!isEnvironmentProduction) {
            fillIndividualKycSteps(investorDetails, SecuritizeIdDashboard.class);
        }
        String securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
        endTestLevel();


        startTestLevel("SumSub: Manual Review");
        if (!isEnvironmentProduction) {
            RejectionLabels[] rejectionLabels = {RejectionLabels.FORGERY};
            ReviewRejectType reviewRejectType = ReviewRejectType.FINAL;
            String moderationComment = "automation test - Manual Review";
            SumSubAPI.setApplicantAsRejected(securitizeIdProfileId, rejectionLabels, reviewRejectType, moderationComment);
        } else {
            investorsAPI.setVerificationStatus(securitizeIdProfileId, "manual-review");
        }
        endTestLevel();


        startTestLevel("UI check of updated SumSub status - 'In Review' (manual-review)");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.waitForUserVerificationStateToBecome("In Review");
        String userVerificationStatus = securitizeIdDashboard.getUserVerificationState();
        Assert.assertEquals(userVerificationStatus.trim().toLowerCase(), "in review", "Verification status at this point should be 'in review' but this is not the case");
        endTestLevel();


        startTestLevel("API check of SID KYC and failure reason");
        JSONObject secIdKycResult = investorsAPI.getSecIdKycStatus(securitizeIdProfileId);
        Assert.assertEquals(secIdKycResult.getString("status").trim().toLowerCase(), "manual-review", "Verification status at this point should be 'reject' but this is not the case");
        if (!isEnvironmentProduction) {
            // error can't be updated via an API call on production
            Assert.assertEquals(secIdKycResult.getJSONArray("verificationErrors").getString(0).trim().toLowerCase(), "forgery", "Failure reason at this point should be 'forgery' but this is not the case");
        }
        endTestLevel();


        startTestLevel("Set SecuritizeID (CP) - KYC Status - Rejected");
        investorsAPI.setKYCToReject(securitizeIdProfileId);
        endTestLevel();


        startTestLevel("Verify on Investor's SecuritizeId dashboard the Status change to Service denied");
        securitizeIdDashboard.waitForUserVerificationStateToBecome("Service denied");
        userVerificationStatus = securitizeIdDashboard.getUserVerificationState();
        Assert.assertEquals(userVerificationStatus.trim().toLowerCase(), "service denied", "Verification status at this point should be 'service denied' but this is not the case");
        endTestLevel();
    }
}