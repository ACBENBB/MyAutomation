package io.securitize.tests.fundraise;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite.SecuritizePublicSiteInvestmentsPage;
import io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite.SecuritizePublicSiteMain;
import io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite.SecuritizePublicSitePrimaryMarketSecurityTokenMarketUSD;
import io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite.SecuritizePublicSitePrimaryMarketSecurityTokenUSDSignedinPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingMarketPage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT354_FT_Public_Securitize_Website_PrimaryMarket_Overview extends AbstractUiTest {

    @Test(description = "Sanity check")
    @AllowTestOnEnvironments(environments = {"production"})
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @BypassRecaptcha
    public void AUT354_FT_Public_Securitize_Website_Overview_PrimaryMarket() {
 
        String investmentName = "Securitize S&P Cryptocurrency Large Cap Ex Mega Tokenized Fund";
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();

        startTestLevel("Navigating to Securitize Public web site - No Session");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.securitizePublicSite));
        SecuritizePublicSiteMain securitizePublicSiteMain = new SecuritizePublicSiteMain(getBrowser());
        endTestLevel();


        startTestLevel("Navigate to investments list page");
        securitizePublicSiteMain.clickInvestButton();
        endTestLevel();


        startTestLevel("click Security Token Market - Security Token Market - Securitize S&P Cryptocurrency Large Cap Ex Mega Tokenized Fund - invest button");
        SecuritizePublicSiteInvestmentsPage securitizePublicSiteInvestmentsPage = new SecuritizePublicSiteInvestmentsPage(getBrowser());
        securitizePublicSiteInvestmentsPage.clickSnPCryptocurrencyFundCard();
        endTestLevel();


        startTestLevel("Select Security Token Market - Securitize S&P Cryptocurrency Large Cap Ex Mega Tokenized Fund");
        SecuritizePublicSitePrimaryMarketSecurityTokenMarketUSD securitizePublicSitePrimaryMarketSecurityTokenMarketUSD = new SecuritizePublicSitePrimaryMarketSecurityTokenMarketUSD(getBrowser());
        securitizePublicSitePrimaryMarketSecurityTokenMarketUSD
                .clickInvestButton();
        endTestLevel();


        startTestLevel("login to Securtizie iD Old login page");
        SecuritizeIdCreateAccountRegistrationStep1 securitizeIdCreateAccountRegistrationStep1 = new SecuritizeIdCreateAccountRegistrationStep1(getBrowser());
        securitizeIdCreateAccountRegistrationStep1
                .clickLoginButton()
                .performLoginWithCredentials(Users.getProperty(UsersProperty.SecID_IntegrityCheckProd_Email),
                        Users.getProperty(UsersProperty.apiInvestorPassword), false);
        endTestLevel();

        startTestLevel("Primary Offering - Security Token Market USD - RegCF - logged in");
        new SecuritizeIdDashboard(getBrowser()).primaryOfferingClick();
        PrimaryOfferingMarketPage marketPage = new PrimaryOfferingMarketPage(getBrowser());
        marketPage.clickOnPrimaryOfferingsCardOpp(investmentName);
        SecuritizePublicSitePrimaryMarketSecurityTokenUSDSignedinPage securitizePublicSitePrimaryMarketSecurityTokenUSDSignedinPage = new SecuritizePublicSitePrimaryMarketSecurityTokenUSDSignedinPage(getBrowser());
        securitizePublicSitePrimaryMarketSecurityTokenUSDSignedinPage.verifySecurityTokenMarketUSDOfferingPage();
        endTestLevel();
    }
}