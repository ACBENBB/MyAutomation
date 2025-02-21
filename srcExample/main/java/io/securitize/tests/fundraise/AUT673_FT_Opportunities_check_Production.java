package io.securitize.tests.fundraise;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.infra.utils.UseVisualTesting;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingMarketPage;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT673_FT_Opportunities_check_Production extends AbstractATS {


    @SkipTestOnEnvironments(environments = {"rc", "sandbox"})
    @UseVisualTesting
    @Test(description = "AUT673_FT_Opportunities_check_Production", groups = {})
    public void AUT673_FT_Opportunities_check_Production_test() {

        startTestLevel("Login to secId");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(
                        Users.getProperty(UsersProperty.SecID_IntegrityCheckProdATS_Email),
                        Users.getProperty(UsersProperty.apiInvestorPassword),false);
        endTestLevel();

        startTestLevel("SecId dashboard - skip 2fa");
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard
                .clickAcceptCookies()
                .clickSkipTwoFactor();
        endTestLevel();

        startTestLevel("Navigate to Primary market");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.primaryOfferingClick();
        endTestLevel();

        startTestLevel("Enter Hamilton Lane Senior Credit Opportunities Securitize Fund opportunity");
        PrimaryOfferingMarketPage primaryOfferingMarketPage = new PrimaryOfferingMarketPage(getBrowser());
        primaryOfferingMarketPage.clickHamiltonLaneSeniorCreditOpportunityCard();
        visualTesting.snapshot("Hamilton Lane Senior Credit Opportunity page");
        endTestLevel();

        startTestLevel("go back to Primary market opportunity list");
        securitizeIdDashboard.primaryOfferingClick();
        endTestLevel();

        startTestLevel("Enter Hamilton Lane Equity Opportunities Securitize Fund opportunity");
        primaryOfferingMarketPage.clickHamiltonLaneEOVOpportunityCard();
        visualTesting.snapshot("Hamilton Lane Equity Opportunity page");
        endTestLevel();

        startTestLevel("go back to Primary market opportunity list");
        securitizeIdDashboard.primaryOfferingClick();
        endTestLevel();

        startTestLevel("Enter KKR Health Care Growth II Tokenized Securitize Fund opportunity");
        primaryOfferingMarketPage.clickKKROpportunityCard();
        visualTesting.snapshot("KKR Health Care Growth II Tokenized Securitize Fund opportunity page");
        endTestLevel();
    }

}