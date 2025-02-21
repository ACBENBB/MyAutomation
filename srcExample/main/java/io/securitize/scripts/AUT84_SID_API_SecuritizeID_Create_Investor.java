package io.securitize.scripts;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.InvestorDetails;
import org.testng.annotations.Test;

public class AUT84_SID_API_SecuritizeID_Create_Investor extends AbstractTest {
    @Test(description = "AUT84 Create US verified Investor Via API Calls")
    public void AUT84_SID_API_SecuritizeID_Create_Investor_Test() throws Exception {
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorId = investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        System.out.println("New investor created: " + investorId);
    }
}