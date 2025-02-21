package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT526_CA_Site_Navigation_GoBack_Arrow extends AbstractCashAccount {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "Cash Account - US Account - International Wire")
    public void AUT526_CA_Site_Navigation_GoBack_Arrow() {

        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut526);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);

        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);

        startTestLevel("Validate Cash Account 'Go Back' Arrow Button navigates to holdings page.");
        SecuritizeIdCashAccountPage securitizeIdCashAccountPage = dashboard.clickCashAccountCard();
        SecuritizeIdDashboard securitizeIdDashboard = securitizeIdCashAccountPage.clickGoBack();
        Assert.assertTrue(securitizeIdDashboard.isPageLoaded(), "Holdings page should be loaded.");
        endTestLevel();

    }
}
