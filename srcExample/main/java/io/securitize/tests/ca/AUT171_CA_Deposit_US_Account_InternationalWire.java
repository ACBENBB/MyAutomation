package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.DepositInternationalWireModalPage;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT171_CA_Deposit_US_Account_InternationalWire extends AbstractCashAccount {

    @Test(description = "Cash Account - Deposit International Wire")
    public void AUT171_CA_Deposit_US_Account_InternationalWire() {

        SoftAssert softAssert = new SoftAssert();

        startTestLevel("login to CA investor");
        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut171);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        endTestLevel();

        startTestLevel("enter cash account screen");
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        DepositInternationalWireModalPage depositInternationalWireModalPage = cashAccountPage.clickAddFundsButton().selectInternationWire();

        startTestLevel("Validate Deposit - US Account - International Wire modal content");
        softAssert.assertEquals(depositInternationalWireModalPage.getBankNameTitle().toLowerCase(), "bank name", "Bank name title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getBankNameValue(), "", "Bank name must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getRoutingNumberTitle().toLowerCase(), "bank routing number", "Bank routing number title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getRoutingNumberValue(), "", "Bank routing number must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getSwiftCodeTitle().toLowerCase(), "bank swift code", "Bank SWIFT Code title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getSwiftCodeValue(), "", "Bank SWIFT Code must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getBankAddressTitle().toLowerCase(), "bank address", "Bank address title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getBankAddressValue(), "", "Bank address must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getRecipientTitle().toLowerCase(), "recipient", "Recipient title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getRecipientValue(), "", "Recipient must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getRecipientAddressTitle().toLowerCase(), "recipient address", "Recipient account address title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getRecipientAddressValue(), "", "Recipient account address must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getRecipientAccountNumberTitle().toLowerCase(), "recipient account number", "Recipient account Number title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getRecipientAccountNumberValue(), "", "Account Number must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getReferenceNumberTitle().toLowerCase(), "reference number", "Reference number title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getReferenceNumberValue(), "", "Reference Number must not be empty.");
        softAssert.assertAll();
        endTestLevel();

        startTestLevel("Validate Deposit - US Account - International Wire modal 'Understood' button behavior");
        depositInternationalWireModalPage.clickUnderstoodBtn();
        softAssert.assertFalse(depositInternationalWireModalPage.isPageLoaded(), "Deposit - International Wire modal should not be displayed");
        endTestLevel();

    }

}
