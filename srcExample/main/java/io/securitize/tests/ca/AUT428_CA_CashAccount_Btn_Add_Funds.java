package io.securitize.tests.ca;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.TransferFundsModalPage;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT428_CA_CashAccount_Btn_Add_Funds extends AbstractUiTest {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "Cash Account - Btn Add Funds: validate component elements via visible text.", groups = {"sanityCA"})
    public void AUT428_CA_CashAccount_Btn_Add_Funds() {

        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);

        startTestLevel("Login using email and password");
        getBrowser().navigateTo(sidURL);
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeLogin
                .clickAcceptCookies()
                .performLoginWithCredentials(Users.getProperty(UsersProperty.ca_create_account_investor_mail_aut428),
                        Users.getProperty(UsersProperty.ca_generic_account_password), false);
        SecuritizeIdDashboard dashboard = new SecuritizeIdDashboard(getBrowser()).clickSkipTwoFactor();
        endTestLevel();

        startTestLevel("Verity Add Funds button label");
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();

        Assert.assertEquals(cashAccountPage.getAddFundsButtonText(), "Add funds", "Create an account button label should be correct.");
        endTestLevel();

        startTestLevel("Verify that clicking Add Funds button leads to Transfer Types modal");
        TransferFundsModalPage transferFundsModalPage = cashAccountPage.clickAddFundsButton();
        Assert.assertTrue(transferFundsModalPage.isPageLoaded(), "Transfer Types modal should be loaded");
        Assert.assertTrue(transferFundsModalPage.getDepositModalBodyText().contains("deposit"),
                "Deposit Modal does not correctly displayed.");
        transferFundsModalPage.clickCloseBtn();
        endTestLevel();

    }

}
