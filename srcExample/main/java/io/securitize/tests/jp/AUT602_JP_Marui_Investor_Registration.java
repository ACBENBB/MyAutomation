package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.tests.jp.abstractClass.AbstractMaruiInvestorRegistrationFlow;
import io.securitize.tests.jp.testData.MaruiInvestorDetails;
import org.testng.annotations.Test;

public class AUT602_JP_Marui_Investor_Registration extends AbstractMaruiInvestorRegistrationFlow {

    private static final boolean RETRY_FLAG = true;

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT602 - Japan - Marui Investor Registration")
    public void AUT602_JP_Marui_Investor_Registration_test() {
        MaruiInvestorDetails maruiInvestorDetails = MaruiRegistrationGenerateInvestorData();
        MaruiRegistration(maruiInvestorDetails, RETRY_FLAG);
    }
}
