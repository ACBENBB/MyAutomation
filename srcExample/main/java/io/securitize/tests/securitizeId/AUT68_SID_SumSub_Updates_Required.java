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

public class AUT68_SID_SumSub_Updates_Required extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT68 - SumSub - Investor's Status - Updates Required")
    public void AUT68_SID_SumSub_Updates_Required_Test() {

        String state = "Alaska";
        boolean isEnvironmentProduction = MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        InvestorDetails investorDetails = performSecuritizeIdCreateAccountAndLogin("usInvestorForSSN", true, state);

        startTestLevel("Register investor (KYC stepper)");
        if (!isEnvironmentProduction) {
            fillIndividualKycSteps(investorDetails, SecuritizeIdDashboard.class);
        }
        endTestLevel();


        startTestLevel("SumSub: Updates Required");
        String securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
        if (!isEnvironmentProduction) {
            RejectionLabels[] rejectionLabels = {RejectionLabels.UNSATISFACTORY_PHOTOS};
            ReviewRejectType reviewRejectType = ReviewRejectType.RETRY;
            String moderationComment = "automation test - updates required";
            SumSubAPI.setApplicantAsRejected(securitizeIdProfileId, rejectionLabels, reviewRejectType, moderationComment);
        } else {
            investorsAPI.setVerificationStatus(securitizeIdProfileId, "updates-required");
        }
        endTestLevel();


        startTestLevel("UI check of updated SumSub status - Updates Required");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.waitForUserVerificationStateToBecome("Updates required");
        String userVerificationStatus = securitizeIdDashboard.getUserVerificationState();
        Assert.assertEquals(userVerificationStatus.trim().toLowerCase(), "updates required", "Verification status at this point should be 'updates required' but this is not the case");
        endTestLevel();


        startTestLevel("API check of SID KYC and failure reason");
        JSONObject secIdKycResult = investorsAPI.getSecIdKycStatus(securitizeIdProfileId);
        Assert.assertEquals(secIdKycResult.getString("status").trim().toLowerCase(), "updates-required", "Verification status at this point should be 'updates-required' but this is not the case");
        if (!isEnvironmentProduction) {
            // error can't be updated via an API call on production
            Assert.assertEquals(secIdKycResult.getJSONArray("verificationErrors").getString(0).trim().toLowerCase(), "unsatisfactory_photos", "Failure reason at this point should be 'unsatisfactory_photos' but this is not the case");
        }
        endTestLevel();
    }
}
