package io.securitize.tests.ca;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.LinkBankAccountPlaidModal;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.TransferFundsModalPage;
import io.securitize.tests.ca.abstractClass.AbstractCashAccount;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT552_CA_LinkUnlink_US_Account_ACH extends AbstractCashAccount {

    @Test(description = "Cash Account - Link/Unlink - US Account - ACH", groups = {"sanityCA"})
    public void AUT552_CA_LinkUnlink_US_Account_ACH() {

        startTestLevel("CA Link US Account ACH - Set up");
        SoftAssert softAssertion = new SoftAssert();
        String mail = Users.getProperty(UsersProperty.ca_investor_mail_aut552);
        String password = Users.getProperty(UsersProperty.apiInvestorPassword);
        SecuritizeIdDashboard dashboard = loginToSecuritizeId(mail, password);
        SecuritizeIdCashAccountPage cashAccountPage = dashboard.clickCashAccountCard();
        if(cashAccountPage.isUnlinkBankLinkPresent()){
            cashAccountPage.clickUnlinkLink();
            cashAccountPage.clickUnlinkAccountButton();
        }
        endTestLevel();

        startTestLevel("Linking account");
        TransferFundsModalPage transferFundsModalPage = cashAccountPage.clickAddFundsButton();
        LinkBankAccountPlaidModal linkBankAccountPlaidModal = transferFundsModalPage.selectAchWithUnlinkedAccount();
        Assert.assertNotNull(linkBankAccountPlaidModal);
        linkBankAccountPlaidModal.linkBankAccount();
        transferFundsModalPage.clickCloseBtn();
        softAssertion.assertTrue(cashAccountPage.isUnlinkBankLinkPresent(), "Unlink Bank account link should be present in Cash Account page.");
        endTestLevel();

        startTestLevel("Unlinking account");
        cashAccountPage.clickUnlinkLink();
        cashAccountPage.clickUnlinkAccountButton();
        cashAccountPage.refreshPage();
        softAssertion.assertFalse(cashAccountPage.isUnlinkBankLinkPresent(), "Unlink Bank account link should not be present in Cash Account page.");
        softAssertion.assertAll();
        endTestLevel();

    }
}
