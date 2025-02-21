package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.SynapseTCAgreementPage;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT477_CA_Holdings_Btn_Create_Account extends AbstractCashAccount {

    @SkipTestOnEnvironments(environments = {"production"})
    @Test(description = "Holdings - Btn Create Account: validate component elements via visible text.")
    public void AUT477_CA_Holdings_Btn_Create_Account_test() {

        String email = Users.getProperty(UsersProperty.ca_create_account_investor_mail_aut477);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        deleteCashAccountByEmail(email);
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(email, password);

        startTestLevel("Verify Create an account button label");
        Assert.assertTrue(dashboard.getCreateAccountButtonText().toLowerCase().contains("account"), "Create account button label is incorrect.");
        endTestLevel();

        startTestLevel("Verify that Create Account button leads to PrimeTrust user agreement modal");
        SynapseTCAgreementPage synapseTCAgreementPage = dashboard.clickCreateAnAccountButton();
        Assert.assertTrue(synapseTCAgreementPage.isPageLoaded(), "Synapse Terms and Conditions agreement modal should be loaded");
        endTestLevel();

        startTestLevel("Verify Cash Account balance");
        synapseTCAgreementPage.clickAcceptButton().clickOkButton();
        String balance = dashboard.getCashAccountBalance();
        System.out.println(balance);
        Assert.assertEquals(balance, "$0.00");
        endTestLevel();

    }
}
