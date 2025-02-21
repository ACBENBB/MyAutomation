package io.securitize.tests.ats;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite.SecuritizeIdInvestorLoginScreenOLD;
import io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite.SecuritizePublicSiteMain;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.ats.constants.AtsGroup;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT355_ATS_Public_Securitize_Website_SecondaryMarket_Overview extends AbstractUiTest {

    @BypassRecaptcha(environments = {"production"})
    @AllowTestOnEnvironments(environments = {"production"})
    @Test(description = "Securitize.io public website Sanity check", groups = {AtsGroup.SANITY_ATS})
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    public void AUT355_ATS_Public_Securitize_Website_Overview_SecondaryMarket() {

            startTestLevel("Navigating to Securitize Public web site - No Session");
            getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.securitizePublicSite));
            SecuritizePublicSiteMain securitizePublicSiteMain = new SecuritizePublicSiteMain(getBrowser());
            endTestLevel();


            startTestLevel("Navigate to investments list page");
            securitizePublicSiteMain.clickInvestButton();
            endTestLevel();


            startTestLevel("Verify 22x issuer and click Trade button");
            securitizePublicSiteMain.clickIssuer22xTradeButton();
            endTestLevel();

            startTestLevel("login to Securitize iD Old login page");
            SecuritizeIdInvestorLoginScreenOLD securitizeIdInvestorLoginScreenOLD = new SecuritizeIdInvestorLoginScreenOLD(getBrowser());
            securitizeIdInvestorLoginScreenOLD
                    .performLoginWithCredentials(
                            Users.getProperty(UsersProperty.SecID_IntegrityCheckProdATS_Email),
                            Users.getProperty(UsersProperty.apiInvestorPassword),
                            getBrowser(),
                            false);
            endTestLevel();


            startTestLevel("Validate 'Trading main screen with 'trading soon available'message Page");
            SecuritizeIdDashboard securitizeIDDashboardSecond = new SecuritizeIdDashboard(getBrowser());
            Assert.assertTrue(
                    securitizeIDDashboardSecond.isTradingSoonAvailableTextDisplayed(),
                    "The 'Trading soon available text' page is not displayed");
            endTestLevel();


            startTestLevel("Navigating to Securitize Public web site - With Session");
            getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.securitizePublicSite));
            securitizePublicSiteMain = new SecuritizePublicSiteMain(getBrowser());
            endTestLevel();


            startTestLevel("Navigate to Secondary Market page - With Session");
            securitizePublicSiteMain.clickInvestButton();
            endTestLevel();


            startTestLevel("Verify 22x issuer and click Trade button - With Session");
            securitizePublicSiteMain.clickIssuer22xTradeButton();
            endTestLevel();


            startTestLevel("Validate 'Trading Paused' Page");
            securitizeIDDashboardSecond = new SecuritizeIdDashboard(getBrowser());
            Assert.assertTrue(
                    securitizeIDDashboardSecond.isTradingSoonAvailableTextDisplayed(),
                    "Trading main screen with 'Trading soon available' page is not displayed");
            endTestLevel();


            startTestLevel("Logout");
            securitizeIDDashboardSecond.performLogout();
            endTestLevel();

    }
}
