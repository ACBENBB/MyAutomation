package io.securitize.tests.ca;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT478_CA_Holdings_Btn_Add_Funds extends AbstractUiTest {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "Portfolio - Btn Manage Funds: validate component elements via visible text.", groups = {"sanityCA"})
    public void AUT478_CA_Holdings_Btn_Add_Funds_test() {

        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);

        startTestLevel("Login using email and password");
        getBrowser().navigateTo(sidURL);
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeLogin
                .clickAcceptCookies()
                .performLoginWithCredentials(Users.getProperty(UsersProperty.ca_create_account_investor_mail_aut427),
                        Users.getProperty(UsersProperty.ca_generic_account_password), false);
        SecuritizeIdDashboard dashboard = new SecuritizeIdDashboard(getBrowser()).clickSkipTwoFactor();
        endTestLevel();

        startTestLevel("Verify Manage Funds button label");
        Assert.assertEquals(dashboard.getAddFundsButtonText().trim(), "Manage Funds", "Manage Funds button link label should be correct.");
        endTestLevel();

        startTestLevel("Verify that clicking Manage Funds button leads to Cash Account Page");
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickManageFundsButton();
        Assert.assertTrue(cashAccountPage.isAddFundsButtonPresent(), "Add Funds button should be present in Cash Account page.");
        endTestLevel();

    }
}
