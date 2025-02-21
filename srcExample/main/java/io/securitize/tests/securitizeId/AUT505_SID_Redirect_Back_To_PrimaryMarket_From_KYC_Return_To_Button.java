package io.securitize.tests.securitizeId;

import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc01IndividualIdentityVerification;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingMarketPage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT505_SID_Redirect_Back_To_PrimaryMarket_From_KYC_Return_To_Button extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT505 - Enter Primary Market > Invest > Click Return to button and return to the opportunity.")
    public void AUT505_SID_Redirect_Back_To_PrimaryMarket_From_KYC_Return_To_Button_Test() {

        String issuerName = "Nie";
        String state = "Alaska";
        String investmentName = Users.getIssuerDetails(issuerName, IssuerDetails.investmentMarketName);

        startTestLevel("SecuritizeId create an account");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        investorDetails.setState(state);

        SecuritizeIdInvestorLoginScreen securitizeIdInvestorLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        SecuritizeIdCreateAccountRegistrationStep1 securitizeIdCreateAccountRegistrationStep1 = securitizeIdInvestorLoginScreen
                .clickAcceptCookies()
                .clickCreateAccount();
        securitizeIdCreateAccountRegistrationStep1
                .createInvestor(investorDetails);
        endTestLevel();


        startTestLevel("Extract link from email and navigate");
        SecuritizeIdCreateAccountRegistrationStep3 securitizeIdCreateAccountRegistrationStep3 = new SecuritizeIdCreateAccountRegistrationStep3(getBrowser());
        securitizeIdCreateAccountRegistrationStep3.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
        endTestLevel();


        startTestLevel("Skip two factor authentication");
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard.clickSkipTwoFactor();
        endTestLevel();


        startTestLevel("Go to SiD -> Primary Offerings");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.primaryOfferingClick();
        endTestLevel();


        startTestLevel("Click in Opportunity from Market By Name");
        PrimaryOfferingMarketPage primaryOfferingMarketPage = new PrimaryOfferingMarketPage(getBrowser());
        primaryOfferingMarketPage.clickOnPrimaryOfferingsOpp(investmentName);
        getBrowser().waitForPageStable();
        String expectedUrl = getBrowser().getCurrentUrl();
        endTestLevel();


        startTestLevel("Invest in the opportunity and do the KYC as required");
        opportunityStepOneSimplified_Investment(CurrencyIds.USD, 60_000, false, true);
        endTestLevel();


        startTestLevel("Enter KYC screen and exit back to Primary Market correct opportunity");
        SecuritizeIdInvestorKyc01IndividualIdentityVerification securitizeIdInvestorKyc01IndividualIdentityVerification = new SecuritizeIdInvestorKyc01IndividualIdentityVerification(getBrowser());
        getBrowser().waitForPageStable();
        securitizeIdInvestorKyc01IndividualIdentityVerification.clickExitProcess();
        String currentUrl = getBrowser().getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "Clicking 'Return to/Exit this process' should take us back to the Primary Market correct opportunity. Instead we got to another url");
        endTestLevel();
    }
}
