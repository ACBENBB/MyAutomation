package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.DepositUSDCModalPage;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT173_CA_Deposit_US_Account_USDC extends AbstractCashAccount {

    @Test(description = "Cash Account - Deposit USDC")
    public void AUT173_CA_Deposit_US_Account_USDC() {

        int ethAddressLength = 42;

        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut173);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        DepositUSDCModalPage depositUSDCModalPage = cashAccountPage.clickAddFundsButton().selectUSDC();

        startTestLevel("Validate Deposit - US Account - USDC modal content");
        Assert.assertEquals(depositUSDCModalPage.getModalTitle(), "Deposit USDC", "Deposit USDC title is not correct.");
        Assert.assertEquals(depositUSDCModalPage.getTopWarningMessage(), "Make sure you are sending only USDC through the Ethereum blockchain. " +
                        "Any other transfer will be lost.",
                "Top warning message is not correct.");

        Assert.assertEquals(depositUSDCModalPage.getScanQRTextTitle(), "Scan the QR to deposit your asset", "Scan QR title is not correct.");
        Assert.assertEquals(depositUSDCModalPage.getAddressTitle(), "Address", "Address title is not correct.");
        Assert.assertTrue(depositUSDCModalPage.getAddressValue().startsWith("0x"), "Address must start with 0x.");
        Assert.assertEquals(depositUSDCModalPage.getAddressValue().length(), ethAddressLength, "Address length size must be 42.");
        Assert.assertEquals(depositUSDCModalPage.getConversionFeeTitle(), "Conversion fee", "Conversion fee title is not correct.");
        Assert.assertEquals(depositUSDCModalPage.getConversionFeeValue(), "0%", "Conversion fee value is not correct.");
        Assert.assertEquals(depositUSDCModalPage.getBottomMessage(), "USDC deposits will automatically be converted into USD. " +
                "This is usually instantaneous but may take up to 24h. Feel free to close this window after initiating your transfer.",
                "Bottom message text is not correct.");

        Assert.assertNotNull(depositUSDCModalPage.getRateDateTimeObject(), "Rate Date Time format is incorrect.");
        Assert.assertTrue(depositUSDCModalPage.getRateValue().matches("\\$\\d = \\d\\.\\d{3} USDC"));
        endTestLevel();

        startTestLevel("Validate Deposit - US Account - USDC modal close button behavior");
        depositUSDCModalPage.clickClose();
        Assert.assertFalse(depositUSDCModalPage.isPageLoaded(), "Deposit - USDC modal should not be displayed");
        endTestLevel();

    }
}
