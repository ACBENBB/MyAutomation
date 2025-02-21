package io.securitize.tests.transferAgent.abstractClass;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.assetDetails.AssetDetailsPage;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.openqa.selenium.By;

import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class TransactionHistory extends AbstractUiTest {

    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[contains(text(), 'OK')]"));
    private static final ExtendedBy confirmationOkButton = new ExtendedBy("Confirmation ok button", By.xpath("//footer/button[2]"));

    public void loginToControlPanel() {
        startTestLevel("Login to control panel using email and password + 2FA ");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl));
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        endTestLevel();
        startTestLevel("Populate Login 2FA with the secret key");
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI(false);
        endTestLevel();
    }

    public void goToInvestorOnboarding(String issuerId, String tokenId, String investorId) {
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl) + issuerId + "/" + tokenId + "/investors/details/" + investorId + "?from=onboarding");
        getBrowser().waitForPageStable();
    }

    public void goToSignatures(String issuerId, String tokenId) {
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl) + issuerId + "/" + tokenId + "/signatures?status=pending");
        getBrowser().waitForPageStable();
    }

    public void goToSecuritiesTransactions(String issuerId, String tokenId) {
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl) + issuerId + "/" + tokenId + "/transfer-agent/dtl");
        getBrowser().waitForPageStable();
    }

    public void navitagateToAssetDetailsPage(String tokenName) {
        SecuritizeIdDashboard secIdPage = new SecuritizeIdDashboard(getBrowser());
        secIdPage.goToAssetDetails(tokenName);
    }

    public void addTransaction() {
        startTestLevel("Add transaction");
        CpInvestorDetailsPage onboardingPage = new CpInvestorDetailsPage(getBrowser());
        String todaysDate = DateTimeUtils.currentDate("MMM dd, yyyy");
        onboardingPage.waitForEditButtonToBeVisible();
        onboardingPage.clickAddTransaction()
                .typeDate(todaysDate)
                .setType("Deposit")
                .typeAmount(1)
                .selectCurrency("USD")
                .clickOk();
        endTestLevel();
    }

    public void addIssuance() {
        startTestLevel("Add issuance");
        CpInvestorDetailsPage onboardingPage = new CpInvestorDetailsPage(getBrowser());
        onboardingPage.clickAddIssuance()
                .typeReason("AUT562")
                .typeIssuanceAmount(1)
                .clickOk("Individual",
                "Investor",
                "Investor",
                1);
        endTestLevel();
    }

    public String getLastIssuancePrice() {
        CpInvestorDetailsPage onboardingPage = new CpInvestorDetailsPage(getBrowser());
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        return onboardingPage.getLatestIssuancePrice();
    }

    public void waitForTransactionsListToBeLoaded() {
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
//        cpSignatures.waitForInitialTransactionsToDisplayed();
    }

    public void signIssuance(String wallet, String pk) {
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        cpSignatures.filterByStatus("Pending")
                .clickSignAllTransactionsButton()
                .typeSignerAddress(wallet)
                .typePrivateKey(pk)
                .clickSignNow();
    }

    public String getLastTransactionHash() {
        String hash = "";
        CpSignatures cpSignatures = new CpSignatures(getBrowser());
        cpSignatures.filterByStatus("Success");
        hash = cpSignatures.getLatestSuccessTransactionHash();
        getBrowser().findElement(new ExtendedBy("OK Button", By.xpath("//button[text()='OK']"))).click();
        return hash;
    }

    public void waitForIssuanceTransactionToArrive(String hash) {
        CpSecuritiesTransactions cpSecuritiesTransactions = new CpSecuritiesTransactions(getBrowser());
        cpSecuritiesTransactions.waitFotTransactionToBePresentInSecurities(hash);
    }

    public void logoutFromControlPanel() {
        CpSideMenu cpSideMenu = new CpSideMenu(getBrowser());
        cpSideMenu.logoutFromCp();
        getBrowser().clearAllCookies();
    }

    public void logoutFromSecId() {
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.performLogoutIncludingClearCookies();
    }

    public void loginToSecuritizeId(String user, String pass) {
        startTestLevel("Login To Securitize ID");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen.clickAcceptCookies().performLoginWithCredentials(user, pass, true);
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        if (securitizeIdDashboard.isTwoFactorModalVisible()) {
            securitizeIdDashboard.clickSkipTwoFactor();
        }
        endTestLevel();
    }

    public boolean validateTransactionIsPresentInSecId(String tokenPrice) {
        AssetDetailsPage assetPage = new AssetDetailsPage(getBrowser());
        return assetPage.validateIssuanceIsPresent(tokenPrice);
    }

}
