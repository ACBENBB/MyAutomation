package io.securitize.tests.securitizeId;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT443_SID_SecuritizeID_Login_Sanity extends AbstractSecIdInvestorRegistrationFlow {

    @Test(description = "AUT443 - Slim Securitize iD Login Sanity" ,groups = {"sanitySID"})
    public void AUT443_SID_SecuritizeID_Login_Sanity_Test() {

        startTestLevel("Login to secId");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(
                        Users.getProperty(UsersProperty.SecID_IntegrityCheck_Email),
                        Users.getProperty(UsersProperty.apiInvestorPassword),
                        false);
        endTestLevel();

        startTestLevel("SecId dashboard - skip 2fa");
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard.clickSkipTwoFactor();
        endTestLevel();

    }
}