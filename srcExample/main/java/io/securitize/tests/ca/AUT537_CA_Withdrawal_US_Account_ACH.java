package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.*;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT537_CA_Withdrawal_US_Account_ACH extends AbstractCashAccount {

    @Test(description = "Cash Account - Withdrawal US Account - ACH")
    public void AUT537_CA_Withdrawal_US_Account_ACH() {

        startTestLevel("CA Withdrawal US Account ACH - Set up");
        SoftAssert softAssertion = new SoftAssert();
        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut537);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        String withdrawAmountString = "1";
        Double withdrawAmount = Double.parseDouble(withdrawAmountString);
        String negativeAmount = "-10.00";
        String invalidCharacters = "abcd";
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        // Get current balance
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        Double initialBalance = Double.parseDouble(cashAccountPage.getUSDBalance().replaceAll("[$,]", ""));
        LocalDateTime initialLastTransactionDate = cashAccountPage.getTransactionRow(0).getDateAsDateTime();
        WithdrawUSACHModalPage withdrawUSACHModalPage = cashAccountPage.clickWithdrawButton().selectACH();
        Double transactionFee = Double.parseDouble(withdrawUSACHModalPage.getTransactionFee());
        endTestLevel();

        startTestLevel("Validate Withdrawal - US Account - ACH modal content");
        Assert.assertEquals(withdrawUSACHModalPage.getModalTitle(), "Withdraw ACH", "Withdrawal ACH title is not correct.");
        Assert.assertFalse(withdrawUSACHModalPage.isSubmitButtonEnabled(), "Submit button must not be enabled.");
        endTestLevel();

        startTestLevel("Validate invalid input values - US Account - ACH withdrawal modal");
        withdrawUSACHModalPage.setAmount(negativeAmount);
        Assert.assertEquals(withdrawUSACHModalPage.getErrorValidationText(), ("Invalid amount"), "Error validation is not appearing when entered invalid input.");
        Assert.assertFalse(withdrawUSACHModalPage.isSubmitButtonEnabled(), "Submit button must not be enabled.");
        withdrawUSACHModalPage.setAmount(invalidCharacters);
        Assert.assertEquals(withdrawUSACHModalPage.getErrorValidationText(), ("Invalid amount"), "Error validation is not appearing when entered invalid input.");
        Assert.assertFalse(withdrawUSACHModalPage.isSubmitButtonEnabled(), "Submit button must not be enabled.");
        endTestLevel();

        startTestLevel("Validate Withdrawal - US Account - ACH withdraw modal");
        withdrawUSACHModalPage.setAmount(withdrawAmount.toString());
        withdrawUSACHModalPage.clickAuthorizationCheckbox();
        String accountNumberInLegalText = withdrawUSACHModalPage.getAccountNumberInLegalText();
        String depositAmountInLegalText = withdrawUSACHModalPage.getAmountInLegalText();
        Assert.assertEquals(withdrawUSACHModalPage.getAccountNumberInCard(), accountNumberInLegalText);
        Assert.assertEquals(Double.parseDouble(depositAmountInLegalText), withdrawAmount);
        Assert.assertTrue(withdrawUSACHModalPage.getLegalLegendText().contains(currentDate));
        Assert.assertTrue(withdrawUSACHModalPage.isSubmitButtonEnabled(), "Submit button must be enabled.");
        endTestLevel();


        // Perform withdrawal
        startTestLevel("withdraw check");
        withdrawUSACHModalPage.setAmount(withdrawAmountString);
        WithdrawACHConfirmationModalPage withdrawACHConfirmationModalPage = withdrawUSACHModalPage.clickSubmitBtn();
        withdrawACHConfirmationModalPage.clickGotItButton();
        // Refresh page to update transaction history
        cashAccountPage = cashAccountPage.refreshPage();
        LocalDateTime actualLastTransactionDate = cashAccountPage.getTransactionRow(0).getDateAsDateTime();
        endTestLevel();

        // Validate Transaction History Table was updated with last transaction
        startTestLevel("Validate Transaction History Table was updated with last transaction");
        softAssertion.assertTrue(actualLastTransactionDate.isAfter(initialLastTransactionDate), "Last transaction should have later DateTime than the previous");
        endTestLevel();

        // Validate that current balance = prev balance - (amount + fee)
        startTestLevel("Validate Balance was updated");
        Double actualBalance = Double.parseDouble(cashAccountPage.getUSDBalance().replaceAll("[$,]", ""));
        Double expectedBalance = initialBalance - (withdrawAmount + transactionFee);
        softAssertion.assertEquals(actualBalance, expectedBalance, 0.0001, "Actual balance should be initialBalance - (withdrawAmount + transactionFee)");
        endTestLevel();

        startTestLevel("Validating Transaction History Amounts");
        TransactionRow transactionRow = cashAccountPage.getTransactionRow(0);
        Double historyAmount = transactionRow.getAmountAsDouble();
        Double historyTotal = transactionRow.getTotalAsDouble();
        softAssertion.assertEquals(historyAmount, withdrawAmount, "Transaction History Amount should be the same " +
                "as Withdrawal Amount.");
        softAssertion.assertEquals(historyTotal, -(withdrawAmount + transactionFee), "Transaction History Total should be the same as" +
                " withdrawal amount + withdrawal fee.");
        endTestLevel();

        startTestLevel("Validating Transaction History Currencies");
        String historyAmountString = transactionRow.getAmount();
        String historyTotalString = transactionRow.getTotal();
        softAssertion.assertTrue(historyAmountString.contains("$"), "Transaction History Amount currency should be '$' (dollar sign)");
        softAssertion.assertTrue(historyTotalString.contains("$"), "Transaction History Total currency should be '$' (dollar sign)");
        softAssertion.assertAll();
        endTestLevel();

    }
}
