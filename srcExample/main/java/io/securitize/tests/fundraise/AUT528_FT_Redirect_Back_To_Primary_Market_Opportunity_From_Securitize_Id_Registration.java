package io.securitize.tests.fundraise;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingOpportunityPage;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import io.securitize.tests.InvestorDetails;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT528_FT_Redirect_Back_To_Primary_Market_Opportunity_From_Securitize_Id_Registration extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT528 - Enter Primary Market Opportunity > Register to Securitize Id > Click verify email and return to the opportunity page")
    public void AUT528_FT_Redirect_Back_To_Primary_Market_Opportunity_From_Securitize_Id_Registration_Test() {

        String state = "Alaska";

        startTestLevel("Create investor details object via an Api call & test data");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        investorDetails.setState(state);
        String marketsOpportunityId = Users.getProperty(UsersProperty.marketsOpportunityId);
        endTestLevel();


        startTestLevel("Navigate to opportunity page & click 'Sign up and Invest'");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.primaryOfferingMarketstUrl).replace("{opportunity-id}", marketsOpportunityId));
        PrimaryOfferingOpportunityPage primaryOfferingOpportunityPage = new PrimaryOfferingOpportunityPage(getBrowser());
        primaryOfferingOpportunityPage
                .clickAcceptCookies()
                .clickSignUpAndInvest();
        endTestLevel();


        startTestLevel("SecuritizeId create an account");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        SecuritizeIdCreateAccountRegistrationStep1 securitizeIdCreateAccountRegistrationStep1 = securitizeIdLoginScreen
                .clickAcceptCookies()
                .clickCreateAccount();
        securitizeIdCreateAccountRegistrationStep1
                .createInvestor(investorDetails);
        endTestLevel();


        startTestLevel("Extract link from email and navigate");
        SecuritizeIdCreateAccountRegistrationStep3 securitizeIdCreateAccountRegistrationStep3 = new SecuritizeIdCreateAccountRegistrationStep3(getBrowser());
        securitizeIdCreateAccountRegistrationStep3.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
        getBrowser().waitForPageStable();
        endTestLevel();


        startTestLevel("Validate redirect back to primary market opportunity page");
        String currentUrl = getBrowser().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/primary-market/opportunities/"), "url should contain '/primary-market/opportunities/' but this is not the case");
        endTestLevel();
    }
}
