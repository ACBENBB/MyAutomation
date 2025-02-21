package io.securitize.tests.controlPanel;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage2FA;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSelectIssuer;
import io.securitize.pageObjects.controlPanel.cpIssuers.google.CpGoogleSignIn;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT82_ISR_Login_2FA_Test extends AbstractCpInvestorRegistrationFlow {

    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @Test(description = "AUT82 - Control panel Login test - Google SSO and Email/Password + 2FA")
    public void AUT82_ISR_Login_2FA_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT82_ISR_Login_2FA;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Login to control panel VIA Google SSO");
        getBrowser().navigateTo(td.url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        CpGoogleSignIn signIn = loginPage.clickLoginWithGoogle();
        signIn.performLogin(
                Users.getProperty(UsersProperty.automationMailUsername),
                Users.getProperty(UsersProperty.automationMailUIPassword),
                Users.getProperty(UsersProperty.automationMail2FaSecret));
        endTestLevel();

        startTestLevel("Logout from CP");
        CpSelectIssuer selectIssuerPage = new CpSelectIssuer(getBrowser());
        selectIssuerPage.logoutFromCP();
        endTestLevel();

        startTestLevel("Login to control panel using email and password + 2FA ");
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        endTestLevel();

        startTestLevel("Populate Login 2FA with the secret key");
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        endTestLevel();
    }
}