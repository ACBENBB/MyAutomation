package io.securitize.tests.securitizeId;

import io.securitize.infra.config.*;
import static io.securitize.infra.reporting.MultiReporter.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc04IndividualLegalInformation;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket.TradingSecondaryMarketsAssetDetail;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AUT506_SID_Redirect_Back_To_ATS_From_KYC_Return_To_Button extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT506 - Enter Trading > Click Verify Your Account > Exiting the KYC process and return to Trading dashboard")
    public void AUT506_SID_Redirect_Back_To_ATS_From_KYC_Return_To_Button_Test() {

        startTestLevel("Login to secId");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(
                        Users.getProperty(UsersProperty.SecID_KycRedirectCheck_Email),
                        Users.getProperty(UsersProperty.apiInvestorPassword),
                        false);
        endTestLevel();


        startTestLevel("SecId dashboard - skip 2fa");
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard.clickSkipTwoFactor();
        endTestLevel();


        startTestLevel("Go to SiD -> Trading");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.clickTrading();
        getBrowser().waitForPageStable();
        String expectedUrl = getBrowser().getCurrentUrl();
        endTestLevel();


        startTestLevel("Click Verify your identity card");
        TradingSecondaryMarketsAssetDetail tradingSecondaryMarketsAssetDetail = new TradingSecondaryMarketsAssetDetail(getBrowser());
        tradingSecondaryMarketsAssetDetail.clickVerifyYourIdentityCard();
        endTestLevel();


        startTestLevel("Enter KYC screen and exit back to Trading tab");
            SecuritizeIdInvestorKyc04IndividualLegalInformation legalInformationPage = new SecuritizeIdInvestorKyc04IndividualLegalInformation(getBrowser());
            getBrowser().waitForPageStable();
            legalInformationPage.clickExitProcess();

        String currentUrl = getBrowser().getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "Clicking 'Exit this process' should take us back to the Trading tab. Instead we got to another url");
        endTestLevel();
    }
}
