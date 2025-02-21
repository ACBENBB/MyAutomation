package io.securitize.tests.jp;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.testng.annotations.Test;

public class AUT600_JP_Marui_Investor_Login_Sanity extends AbstractJpFlow {

    private static final boolean RETRY_FLAG = true;

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT600 - Marui Investor site Login", groups = {"sanityJP"})
    public void AUT600_JP_Marui_Investor_Login_test() {
        String validEmail = Users.getProperty(UsersProperty.marui_existingInvestorEmail);
        String validPassword = Users.getProperty(UsersProperty.marui_investorPassword);
        investorSiteMaruiLogin(validEmail, validPassword, RETRY_FLAG);
        investorSiteMaruiLogout(RETRY_FLAG);
    }
}
