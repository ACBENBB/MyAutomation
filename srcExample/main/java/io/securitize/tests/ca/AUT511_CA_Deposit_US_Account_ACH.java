package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.DepositACHConfirmationModalPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.DepositACHModalPage;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT511_CA_Deposit_US_Account_ACH extends AbstractCashAccount {

    @Test(description = "Cash Account - Deposit ACH")
    public void AUT511_CA_Deposit_US_Account_ACH() {

        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut511);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        Double depositAmount = 10.00;
        String negativeAmount = "-10.00";
        String invalidCharacters = "abcd";

        startTestLevel("login to Securitize iD");
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        endTestLevel();

        startTestLevel("enter CA page");
        DepositACHModalPage depositACHModalPage = cashAccountPage.clickAddFundsButton().selectACH();
        endTestLevel();

        startTestLevel("Validate Deposit - US Account - ACH modal content");
        Assert.assertEquals(depositACHModalPage.getModalTitle(), "Deposit ACH", "Deposit ACH title is not correct.");
        Assert.assertFalse(depositACHModalPage.isSubmitButtonEnabled(), "Submit button must not be enabled.");
        endTestLevel();

        startTestLevel("Validate invalid input values - US Account - ACH deposit modal");
        depositACHModalPage.setAmount(negativeAmount);
        Assert.assertFalse(depositACHModalPage.isSubmitButtonEnabled(), "Submit button must not be enabled.");
        depositACHModalPage.setAmount(invalidCharacters);
        Assert.assertFalse(depositACHModalPage.isSubmitButtonEnabled(), "Submit button must not be enabled.");
        endTestLevel();

        startTestLevel("Validate Deposit - US Account - ACH deposit modal");
        depositACHModalPage.setAmount(depositAmount.toString());
        depositACHModalPage.clickAuthorizationCheckbox();
        String accountNumberInLegalText = depositACHModalPage.getAccountNumberInLegalText();
        String depositAmountInLegalText = depositACHModalPage.getAmountInLegalText();
        Assert.assertEquals(depositACHModalPage.getAccountNumberInCard(), accountNumberInLegalText);
        Assert.assertEquals(Double.parseDouble(depositAmountInLegalText), depositAmount);
        Assert.assertTrue(depositACHModalPage.getLegalLegendText().contains(currentDate));
        Assert.assertTrue(depositACHModalPage.isSubmitButtonEnabled(), "Submit button must be enabled.");
        DepositACHConfirmationModalPage depositACHConfirmationModalPage = depositACHModalPage.clickSubmitBtn();
        endTestLevel();


        startTestLevel("Validate Deposit - US Account - ACH Confirmation modal");
        Assert.assertEquals(depositACHConfirmationModalPage.getModalTitle(), "Deposit ACH", "ACH Confirmation modal title is not correct.");
        Assert.assertTrue(depositACHConfirmationModalPage.getModalInfoMessage().contains("You’re almost there!"),
                "ACH Confirmation modal info should say 'Instant Deposit'.");
        depositACHConfirmationModalPage.clickGotItButton();
        Assert.assertFalse(depositACHModalPage.isPageLoaded(), "Deposit - ACH modal should not be displayed");
        endTestLevel();

    }
}
