package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.DepositUSWireModalPage;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT172_CA_Deposit_US_Account_Wire extends AbstractCashAccount {

    @Test(description = "Cash Account - Deposit US Account Wire")
    public void AUT172_CA_Deposit_US_Account_Wire() {

        SoftAssert softAssert = new SoftAssert();

        startTestLevel("login to CA in Securitize iD");
        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut172);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        endTestLevel();

        startTestLevel("Navigate to CA screen");
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        DepositUSWireModalPage depositUSWireModalPage = cashAccountPage.clickAddFundsButton().selectUSWire();
        endTestLevel();

        startTestLevel("Validate Deposit - US Account - US Wire modal content");
        softAssert.assertEquals(depositUSWireModalPage.getBankNameTitle().toLowerCase(), "bank name", "Bank name title is not correct.");
        softAssert.assertNotEquals(depositUSWireModalPage.getBankNameValue(), "", "Bank name must not be empty.");
        softAssert.assertEquals(depositUSWireModalPage.getRoutingNumberTitle().toLowerCase(), "bank routing number", "Bank routing number title is not correct.");
        softAssert.assertNotEquals(depositUSWireModalPage.getRoutingNumberValue(), "", "Routing Number must not be empty.");
        softAssert.assertEquals(depositUSWireModalPage.getBankAddressTitle().toLowerCase(), "bank address", "Bank address title is not correct.");
        softAssert.assertNotEquals(depositUSWireModalPage.getBankAddressValue(), "", "Bank address must not be empty.");
        softAssert.assertEquals(depositUSWireModalPage.getAccountRecipientTitle().toLowerCase(), "recipient", "Bank address title is not correct.");
        softAssert.assertNotEquals(depositUSWireModalPage.getAccountRecipientValue(), "", "Recipient must not be empty.");
        softAssert.assertEquals(depositUSWireModalPage.getRecipientAddressTitle().toLowerCase(), "recipient address", "Recipient account address title is not correct.");
        softAssert.assertNotEquals(depositUSWireModalPage.getRecipientAddressValue(), "", "Recipient account address must not be empty.");
        softAssert.assertEquals(depositUSWireModalPage.getRecipientAccountNumberTitle().toLowerCase(), "recipient account number", "Account Number title is not correct.");
        softAssert.assertNotEquals(depositUSWireModalPage.getRecipientAccountNumberValue(), "", "Account Number must not be empty.");
        softAssert.assertEquals(depositUSWireModalPage.getReferenceNumberTitle().toLowerCase(), "reference number", "Reference Number title is not correct.");
        softAssert.assertNotEquals(depositUSWireModalPage.getReferenceNumberValue(), "", "Reference Number must not be empty.");
        softAssert.assertAll();
        endTestLevel();

        startTestLevel("Validate Deposit - US Account - US Wire modal 'Understood' button behavior");
        depositUSWireModalPage.clickUnderstoodBtn();
        Assert.assertFalse(depositUSWireModalPage.isPageLoaded(), "Deposit - US Account - US Wire modal should not be displayed");
        endTestLevel();

    }
}
