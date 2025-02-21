package io.securitize.scripts;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractTest;
import org.testng.annotations.Test;

public class AUT85_CA_API_CashAccountInvestorReady extends AbstractTest {
    @Test
    public void AUT85_CA_API_CashAccountInvestorReady() throws Exception {
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        investorDetails.setState("Alaska");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorId = investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        System.out.println("New investor created: " + investorId);
    }
}