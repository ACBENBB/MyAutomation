package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.DepositInternationalWireModalPage;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT512_CA_Deposit_EU_Account_International extends AbstractCashAccount {

    @Test(description = "Cash Account - EU Account - Deposit International")
    public void AUT512_CA_Deposit_EU_Account_International() {

        SoftAssert softAssert = new SoftAssert();

        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut512);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);

        startTestLevel("Login to Securitize iD");
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        DepositInternationalWireModalPage depositInternationalWireModalPage = cashAccountPage.clickAddFundsButton().selectInternationWire();
        endTestLevel();

        startTestLevel("Validate Deposit - EU Account - International Wire modal content");
        softAssert.assertEquals(depositInternationalWireModalPage.getBankNameTitle().toLowerCase(), "bank name", "Bank name title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getBankNameValue(), "", "Bank name must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getRoutingNumberTitle().toLowerCase(), "bank routing number", "Bank routing number title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getRoutingNumberValue(), "", "Bank routing number must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getSwiftCodeTitle().toLowerCase(), "bank swift code", "Bank SWIFT Code title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getSwiftCodeValue(), "", "Bank SWIFT Code must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getBankAddressTitle().toLowerCase(), "bank address", "Bank Info title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getBankAddressValue(), "", "Bank address must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getRecipientTitle().toLowerCase(), "recipient", "Recipient title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getRecipientValue(), "", "Recipient must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getRecipientAddressTitle().toLowerCase(), "recipient address", "Recipient account address title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getRecipientAddressValue(), "", "Recipient account address must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getRecipientAccountNumberTitle().toLowerCase(), "recipient account number", "Account Number title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getRecipientAccountNumberValue(), "", "Account Number must not be empty.");
        softAssert.assertEquals(depositInternationalWireModalPage.getReferenceNumberTitle().toLowerCase(), "reference number", "Reference Number title is not correct.");
        softAssert.assertNotEquals(depositInternationalWireModalPage.getReferenceNumberValue(), "", "Reference Number must not be empty.");
        softAssert.assertAll();
        endTestLevel();


        startTestLevel("Validate Deposit - EU Account - International Wire modal 'Understood' button behavior");
        depositInternationalWireModalPage.clickUnderstoodBtn();
        Assert.assertFalse(depositInternationalWireModalPage.isPageLoaded(), "Deposit - International Wire modal should not be displayed");
        endTestLevel();

    }
}
