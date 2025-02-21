package io.securitize.tests.fundraise;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.infra.utils.UseVisualTesting;
import io.securitize.pageObjects.investorsOnboarding.nie.NieWelcomePage;
import io.securitize.pageObjects.investorsOnboarding.nie.arcaLabs.NieDashboardArcaLabs;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT679_FT_ArcaLabs_CustomDomain_Login_production extends AbstractSecIdNieSharedFlow {

    @SkipTestOnEnvironments(environments = {"rc", "sandbox"})
    @UseVisualTesting
    @Test(description = "AUT679_FT_ArcaLabs_CustomDomain_Login_production", groups = {})
    public void AUT679_FT_ArcaLabs_CustomDomain_Login_production_test() {

        startTestLevel("Reaching SpiceVC Welcome page");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.arcaLabsLoginPage));
        NieWelcomePage nieWelcomePage = new NieWelcomePage(getBrowser());
        visualTesting.snapshot("ArcaLabs Welcome Page");
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = nieWelcomePage.clickLogInWithoutAccountSelector();
        endTestLevel();

        startTestLevel("Login to secId");

        securitizeIdLoginScreen
                .clickAcceptCookies()
                .runAction(() -> visualTesting.snapshot("ArcaLabs Securitize iD login page"))
                .performLoginWithCredentials(
                        Users.getProperty(UsersProperty.SecID_SpiceVC_Production_Email),
                        Users.getProperty(UsersProperty.apiInvestorPassword),false);
        endTestLevel();

        startTestLevel("Validate ArcaLabs NIE dashboard");
        NieDashboardArcaLabs nieDashboardArcaLabs = new NieDashboardArcaLabs(getBrowser());
        visualTesting.snapshot("ArcaLabs NIE dashboard - after login");
        endTestLevel();
    }
}

