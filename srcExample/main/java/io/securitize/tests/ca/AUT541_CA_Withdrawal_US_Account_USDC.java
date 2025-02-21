package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.*;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT541_CA_Withdrawal_US_Account_USDC extends AbstractCashAccount {

    @Test(description = "Cash Account - Withdrawal US Account - USDC")
    public void AUT541_CA_Withdrawal_US_Account_USDC() {

        startTestLevel("CA Withdrawal US Account ACH - Set up");
        SoftAssert softAssertion = new SoftAssert();
        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut541);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        String withdrawAmountString = "10";
        Double withdrawAmount = Double.parseDouble(withdrawAmountString);
        endTestLevel();

        startTestLevel("get current balance");
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        Double initialBalance = Double.parseDouble(cashAccountPage.getUSDBalance().replaceAll("[$,]", ""));
        LocalDateTime initialLastTransactionDate = cashAccountPage.getTransactionRow(0).getDateAsDateTime();
        endTestLevel();

        startTestLevel("perform withdrawal");
        WithdrawSelectWalletModalPage withdrawSelectWalletModalPage = cashAccountPage.clickWithdrawButton().selectUSDC();
        WithdrawUSDCModalPage withdrawUSDCModalPage = withdrawSelectWalletModalPage.selectFirstWallet();
        withdrawUSDCModalPage.setAmount(withdrawAmountString);
        Double usdcAmount = withdrawUSDCModalPage.getUSDCAmountAsDouble();
        WithdrawUSDCConfirmationModalPage withdrawUSDCConfirmationModalPage = withdrawUSDCModalPage.clickSubmitButton();
        withdrawUSDCConfirmationModalPage.clickUnderstoodBtn();
        endTestLevel();

        startTestLevel("Refresh page to update balance and transaction history");
        cashAccountPage = cashAccountPage.refreshPage();
        LocalDateTime actualLastTransactionDate = cashAccountPage.getTransactionRow(0).getDateAsDateTime();
        endTestLevel();

        startTestLevel("Validate Transaction History Table was updated with last transaction");
        softAssertion.assertTrue(actualLastTransactionDate.isAfter(initialLastTransactionDate), "Last transaction should have later DateTime than the previous");
        endTestLevel();

        // validate that current balance = prev balance - amount
        startTestLevel("Validate Balance was updated");
        Double actualBalance = Double.parseDouble(cashAccountPage.getUSDBalance().replaceAll("[$,]", ""));
        Double expectedBalance = initialBalance - withdrawAmount;
        softAssertion.assertEquals(actualBalance, expectedBalance, "Actual balance should be initialBalance - withdrawAmount.");
        endTestLevel();

        // validate that history shows transaction at top with correct amount
        startTestLevel("Validating Transaction History Amounts");
        TransactionRow transactionRow = cashAccountPage.getTransactionRow(0);
        Double historyAmount = transactionRow.getAmountAsDouble();
        Double historyTotal = transactionRow.getTotalAsDouble();
        softAssertion.assertEquals(historyAmount, usdcAmount, "Transaction History Amount should be the same " +
                "as Withdrawal Amount.");
        softAssertion.assertEquals(historyTotal, -usdcAmount, "Transaction History Total should be the same as" +
                " withdrawal amount + withdrawal.");
        endTestLevel();

        startTestLevel("Validating Transaction History Currencies");
        String historyAmountString = transactionRow.getAmount();
        String historyTotalString = transactionRow.getTotal();
        softAssertion.assertTrue(historyAmountString.contains("USDC"), "Transaction History Amount currency should be 'USDC'");
        softAssertion.assertTrue(historyTotalString.contains("USDC"), "Transaction History Total currency should be 'USDC'");
        softAssertion.assertAll();
        endTestLevel();

    }
}
