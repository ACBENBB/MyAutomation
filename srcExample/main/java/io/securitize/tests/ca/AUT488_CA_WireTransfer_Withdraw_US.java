package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.TransactionRow;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.WithdrawConfirmationModalPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.WithdrawUSWireModalPage;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT488_CA_WireTransfer_Withdraw_US extends AbstractCashAccount {

    @Test(description = "Cash Account - Wire Transfer - Withdraw US")
    public void AUT488_CA_WireTransfer_Withdraw_US() {

        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut488);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        String withdrawAmountString = "10";
        Double withdrawAmount = Double.parseDouble(withdrawAmountString);



        startTestLevel("get current balance");
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        Double initialBalance = Double.parseDouble(cashAccountPage.getUSDBalance().replaceAll("[$,]", ""));
        LocalDateTime initialLastTransactionDate = cashAccountPage.getTransactionRows().get(0).getDateAsDateTime();
        endTestLevel();

        startTestLevel("perform withdrawal usd (saving fee amount");
        WithdrawUSWireModalPage withdrawUSWireModalPage = cashAccountPage.clickWithdrawButton().selectUSWire();
        String feeString = withdrawUSWireModalPage.getFee();
        Double fee = Double.parseDouble(feeString.replaceAll("[$,]", ""));
        withdrawUSWireModalPage.setAmount(withdrawAmountString);
        withdrawUSWireModalPage.setRecipientName(withdrawUSWireModalPage.getAccountHolder());
        withdrawUSWireModalPage.setAccountNumber(Users.getProperty(UsersProperty.ca_withdraw_us_wire_account_number));
        withdrawUSWireModalPage.setRoutingNumber(Users.getProperty(UsersProperty.ca_withdraw_us_wire_routing_number));
        WithdrawConfirmationModalPage withdrawConfirmationModalPage = withdrawUSWireModalPage.clickSubmitBtn();
        withdrawConfirmationModalPage.clickGotItBtn();
        // Refresh page to update balance and transaction history
        getBrowser().refreshPage();
        LocalDateTime actualLastTransactionDate = cashAccountPage.getTransactionRows().get(0).getDateAsDateTime();
        endTestLevel();

        startTestLevel("Validate Transaction History Table was updated with last transaction");
        Assert.assertTrue(actualLastTransactionDate.isAfter(initialLastTransactionDate), "Last transaction should have later DateTime than the previous");
        endTestLevel();

        startTestLevel("validate that current balance = prev balance - amount - fee");
        Double actualBalance = Double.parseDouble(cashAccountPage.getUSDBalance().replaceAll("[$,]", ""));
        Double expectedBalance = initialBalance - withdrawAmount - fee;
        Assert.assertEquals(actualBalance, expectedBalance, "Actual balance should be initialBalance - withdrawAmount - fee.");
        endTestLevel();

        startTestLevel("validate that history shows transaction at top with correct amount and fee");
        TransactionRow transactionRow = cashAccountPage.getTransactionRows().get(0);
        Double historyAmount = transactionRow.getAmountAsDouble();
        Double historyFee = transactionRow.getFeeAsDouble();
        Double historyTotal = transactionRow.getTotalAsDouble();
        Assert.assertEquals(historyAmount, withdrawAmount, "Transaction History Amount should be the same " +
                "as Withdrawal Amount.");
        Assert.assertEquals(historyFee, fee, "Transaction History Fee should be the same as Withdrawal Fee.");
        Assert.assertEquals(historyTotal, -(withdrawAmount + fee), "Transaction History Total should be the same as" +
                " withdrawal amount + withdrawal fee.");
        endTestLevel();

        startTestLevel("Validating Transaction History Currencies");
        String historyAmountString = transactionRow.getAmount();
        String historyFeeString = transactionRow.getFee();
        String historyTotalString = transactionRow.getTotal();
        Assert.assertTrue(historyAmountString.contains("$"), "Transaction History Amount currency should be '$' (dollar sign)");
        Assert.assertTrue(historyFeeString.contains("$"), "Transaction History Fee currency should be '$' (dollar sign)");
        Assert.assertTrue(historyTotalString.contains("$"), "Transaction History Total currency should be '$' (dollar sign)");
        endTestLevel();

    }
}
