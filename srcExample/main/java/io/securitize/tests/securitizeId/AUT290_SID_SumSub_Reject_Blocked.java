package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.sumsub.*;
import io.securitize.infra.config.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import io.securitize.tests.InvestorDetails;
import org.json.JSONObject;
import org.testng.annotations.*;
import org.testng.Assert;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT290_SID_SumSub_Reject_Blocked extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT290 - SumSub - Investor's Status + sub status - Rejected [Blocked]")
    public void AUT290_SID_SumSub_Reject_Blocked_Test() {

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


        startTestLevel("SumSub: Rejected[Blocked]");
        if (!isEnvironmentProduction) {
            RejectionLabels[] rejectionLabels = {RejectionLabels.BLOCKLIST};
            ReviewRejectType reviewRejectType = ReviewRejectType.FINAL;
            String moderationComment = "automation test - rejected[blocked]";
            SumSubAPI.setApplicantAsRejected(securitizeIdProfileId, rejectionLabels, reviewRejectType, moderationComment);
        } else {
            investorsAPI.setVerificationStatus(securitizeIdProfileId, "rejected", "blocked");
        }
        endTestLevel();


        startTestLevel("UI check of updated SumSub status - 'In Review' (manual-review)");
        if (!isEnvironmentProduction) {
            SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
            String userVerificationStatus = securitizeIdDashboard.getUserVerificationState();
            securitizeIdDashboard.waitForUserVerificationStateToBecome("In Review");
            Assert.assertEquals(userVerificationStatus.trim().toLowerCase(), "in review", "Verification status at this point should be 'in review' but this is not the case");
        }
        endTestLevel();


        startTestLevel("API check of SID KYC and failure reason");
        JSONObject secIdKycResult = investorsAPI.getSecIdKycStatus(securitizeIdProfileId);
        if (!isEnvironmentProduction) {
            Assert.assertEquals(secIdKycResult.getString("status").trim().toLowerCase(), "manual-review", "Verification status at this point should be 'manual-review' but this is not the case");
            Assert.assertEquals(secIdKycResult.getString("subStatus").trim().toLowerCase(), "none", "Verification sub status at this point should be 'none' but this is not the case");
            // error can't be updated via an API call on production
            Assert.assertEquals(secIdKycResult.getJSONArray("verificationErrors").getString(0).trim().toLowerCase(), "blocklist", "Failure reason at this point should be 'blacklist' but this is not the case");
        } else {
            Assert.assertEquals(secIdKycResult.getString("status").trim().toLowerCase(), "rejected", "Verification status at this point should be 'rejected' but this is not the case");
            Assert.assertEquals(secIdKycResult.getString("subStatus").trim().toLowerCase(), "blocked", "Verification sub status at this point should be 'blocked' but this is not the case");
        }

//        if (!isEnvironmentProduction) {
//            // error can't be updated via an API call on production
//            Assert.assertEquals(secIdKycResult.getJSONArray("verificationErrors").getString(0).trim().toLowerCase(), "blocklist", "Failure reason at this point should be 'blacklist' but this is not the case");
//        }
        endTestLevel();
    }
}