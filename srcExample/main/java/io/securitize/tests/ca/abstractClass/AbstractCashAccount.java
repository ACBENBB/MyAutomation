package io.securitize.tests.ca.abstractClass;

import io.securitize.infra.api.primetrust.PrimeTrustAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdCashAccountPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount.SynapseTCAgreementPage;
import io.securitize.scripts.AUT521_CA_API_CreateSidAndCashAccount;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractCashAccount extends AbstractUiTest {

    public void cashAccountSanityFlowProd(String email, String password) {
        navigateToSecuritzeId();
        loginToSecuritizeId(email, password);
        clickOnCashAccountCard();
        validateUSDCurrencyCard();
        validateEURCurrencyCard();
        validateUnlinkCard();
        validateTransactionHistory();
    }

    public void navigateToSecuritzeId() {
        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);
        startTestLevel("Login using email and password");
        getBrowser().navigateTo(sidURL);
    }

    public SecuritizeIdDashboard loginToSecuritizeId(String mail, String password) {
        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);
        startTestLevel("Login using email and password");
        getBrowser().navigateTo(sidURL);
        SecuritizeIdInvestorLoginScreen securitizeLogin = new SecuritizeIdInvestorLoginScreen(getBrowser());
        if(securitizeLogin.isAcceptCookiesButtonPresent()) {
            securitizeLogin.clickAcceptCookies();
        }
        securitizeLogin.performLoginWithCredentials(mail, password, true);
        SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIDDashboard.clickSkipTwoFactor();
        securitizeLogin.waitForSIDToLogin();
        endTestLevel();
        return securitizeIDDashboard;
    }

    public void verifyCreateAccountBtnAndModal() {
        startTestLevel("Verity Create an account button label");
        SecuritizeIdDashboard dashboard = new SecuritizeIdDashboard(getBrowser());
        Assert.assertEquals(dashboard.getCreateAccountButtonText(), "Create account", "Create an account button label should be correct.");
        endTestLevel();
        startTestLevel("Verify that Create Account button leads to Synapse user agreement modal");
        SynapseTCAgreementPage agreementPage = dashboard.clickCreateAnAccountButton();
        Assert.assertTrue(agreementPage.isPageLoaded(), "Synapse user agreement modal should be loaded");
        endTestLevel();
    }

    @Deprecated
    public void unlinkPrimeTrustFromSid(UsersProperty email) {
        PrimeTrustAPI.unlinkAccountByEmail(Users.getProperty(email));
    }

    public void deleteCashAccountByEmail(String email){
        AUT521_CA_API_CreateSidAndCashAccount cashAccountAPI = new AUT521_CA_API_CreateSidAndCashAccount();

        String investorId = cashAccountAPI.getInvestorIdByEmail(email);
        String cashAccountId = cashAccountAPI.getCashAccountId(investorId);

        if(cashAccountId == null){
            info("CashAccountId not found for email: " + email + ", with InvestorId: " + investorId );
            return;
        }
        info("Deleting account email: " + email + ", with InvestorId: " + investorId + " CashAccountId: " + cashAccountId);
        cashAccountAPI.deleteCashAccount(cashAccountId);
    }

    public void clickOnCashAccountCard() {
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.clickCashAccountCard();
    }

    public void validateUSDCurrencyCard() {
        startTestLevel("Validating USD Currency Card");
        SecuritizeIdCashAccountPage securitizeIdCashAccountPage = new SecuritizeIdCashAccountPage(getBrowser());
        String usdCurrencyText = securitizeIdCashAccountPage.getUSDCurrencyCard().getText();
        // TODO: Expected behavior of localization (when to show $ vs USD) is not yet completely defined
        // https://securitize.atlassian.net/browse/CA-716
        Assert.assertTrue(usdCurrencyText.contains("$") || usdCurrencyText.contains("USD"),
                "USD Currency Card does not contain '$' sign or 'USD' abbrev");
        endTestLevel();
    }

    public void validateEURCurrencyCard() {
        startTestLevel("Validating EUR Currency Card");
        SecuritizeIdCashAccountPage securitizeIdCashAccountPage = new SecuritizeIdCashAccountPage(getBrowser());
        String eurCurrencyText = securitizeIdCashAccountPage.getEURCurrencyCard().getText();
        // TODO: Expected behavior of localization (when to show € vs EUR) is not yet completely defined
        // https://securitize.atlassian.net/browse/CA-716
        Assert.assertTrue(eurCurrencyText.contains("€") || eurCurrencyText.contains("EUR"),
                "EUR Currency Card does not contain '€' sign or 'EUR' abbrev");
        endTestLevel();
    }

    public void validateUnlinkCard() {
        startTestLevel("Validating Unlink Account Modal");
        SecuritizeIdCashAccountPage securitizeIdCashAccountPage = new SecuritizeIdCashAccountPage(getBrowser());
        securitizeIdCashAccountPage.getUnlinkButton().click();
        String unlinkCardButtonText = securitizeIdCashAccountPage.getUnlinkCardModalButton().getText();
        Assert.assertEquals(unlinkCardButtonText, "Unlink Card", "Unlink Card button text does not match");
        securitizeIdCashAccountPage.getCloseAccountModal().click();
        endTestLevel();
    }

    public void validateTransactionHistory() {
        startTestLevel("Validating Transaction History");
        SecuritizeIdCashAccountPage securitizeIdCashAccountPage = new SecuritizeIdCashAccountPage(getBrowser());
        List<WebElement> historyRows = securitizeIdCashAccountPage.getTransactionHistoryRows();
        Assert.assertFalse(historyRows.isEmpty(), "History Rows must not be empty");
        endTestLevel();
    }

}