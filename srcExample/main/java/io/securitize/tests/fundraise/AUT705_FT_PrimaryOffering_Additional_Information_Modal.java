package io.securitize.tests.fundraise;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.UseVisualTesting;
import io.securitize.pageObjects.investorsOnboarding.nie.NieInvestmentDetails;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingMarketPage;
import io.securitize.tests.ats.abstractClass.AbstractATS;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT705_FT_PrimaryOffering_Additional_Information_Modal extends AbstractATS {

    @Test(description = "AUT705_FT_PrimaryOffering_Additional_Information_Modal")
    @UseVisualTesting
    private void AUT705_FT_PrimaryOffering_Additional_Information_Modal() throws Exception {
        startTestLevel("Login to secId");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(
                        Users.getProperty(UsersProperty.SecId_EntityInvestor_Email),
                        Users.getProperty(UsersProperty.apiInvestorPassword), false);
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

        startTestLevel("Enter AUT705 opportunity");
        PrimaryOfferingMarketPage primaryOfferingMarketPage = new PrimaryOfferingMarketPage(getBrowser());
        primaryOfferingMarketPage.clickOnPrimaryOfferingsOpp("AUT705");
        endTestLevel();

        startTestLevel("Click invest and check additional information modal");
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        nieInvestmentDetails.setInvestmentAmount(5000000);
        nieInvestmentDetails.clickInvest();
        visualTesting.snapshot("Additional information required modal");
        endTestLevel();
    }
}
