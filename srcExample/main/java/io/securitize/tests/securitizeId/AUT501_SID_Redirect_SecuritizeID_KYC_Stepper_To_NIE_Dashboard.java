package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc01IndividualIdentityVerification;
import io.securitize.pageObjects.investorsOnboarding.nie.*;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.InvestorDetails;
import org.testng.annotations.Test;
import org.testng.Assert;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT501_SID_Redirect_SecuritizeID_KYC_Stepper_To_NIE_Dashboard extends AbstractUiTest {

    @Test(description = "Check redirect from SID KYC page to NIE Dashboard in 2 buttons: 'return to' & 'Go back'")
    public void AUT501_SID_Redirect_SecuritizeID_KYC_Stepper_To_NIE_Dashboard_Test() {

        String issuerName = "Nie";

        startTestLevel("SecuritizeId create an account via API");
        InvestorDetails investorDetails = InvestorDetails.generateRandomNonUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.createNewSecuritizeIdInvestor(investorDetails, true);
        endTestLevel();


        startTestLevel("Login to the issuer page");
        getBrowser().navigateTo(MainConfig.getInvestPageUrl(issuerName));
        NieWelcomePage welcomePage = new NieWelcomePage(getBrowser());
        welcomePage
                .clickLogInWithoutAccountSelector()
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);

        // confirm we trust the automation issuer
        SecuritizeIdLoginScreenLoggedIn securitizeIdLoginScreenLoggedIn = new SecuritizeIdLoginScreenLoggedIn(getBrowser());
        NieDashboard nieDashboard = securitizeIdLoginScreenLoggedIn.clickAllowButton();
        endTestLevel();


        startTestLevel("Enter the 'Complete your details' page and exit via 'return to' button");
        String expectedUrl = MainConfig.getInvestPageUrl(issuerName) + "#/";
        nieDashboard.clickCompleteYourDetails();
        getBrowser().waitForPageStable();
        SecuritizeIdInvestorKyc01IndividualIdentityVerification securitizeIdInvestorRegistration02IndividualIdentityVerification = new SecuritizeIdInvestorKyc01IndividualIdentityVerification(getBrowser());
        securitizeIdInvestorRegistration02IndividualIdentityVerification.clickExitProcess();
        getBrowser().waitForPageStable();
        String currentUrl = getBrowser().getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "Clicking 'return to' which basically means exit this process should take us back to the NIE dashboard. Instead we got to another url");
        endTestLevel();
    }
}
