package io.securitize.tests.controlPanel;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage2FA;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSideMenu;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import io.securitize.tests.controlPanel.pojo.ISR_TestData;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT540_ISR_Login_Auth_test extends AbstractCpInvestorRegistrationFlow {

    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @Test(description = "AUT540 - Control panel Login for non corporate basic email test - 2FA")
    public void AUT540_ISR_Login_Basic_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT540_ISR_Login_Auth_test;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Navigate to Login page");
        String url = MainConfig.getProperty(MainConfigProperty.cpUrl);
        getBrowser().navigateTo(url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        endTestLevel();

        startTestLevel("Login to control panel using basic email and password + 2FA ");
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingNonCorporateEmailPassword(td.nonCorporateBasicEmail, true);
        endTestLevel();

        startTestLevel("Populate Login 2FA with the secret key");
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUIBasicOperator();
        endTestLevel();

        startTestLevel("Navigate to Onboarding screen to assert login was successful");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        sideMenu.clickOnBoarding();
        endTestLevel();
    }

    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @Test(description = "AUT540 - Control panel Login for non corporate admin email test - 2FA")
    public void AUT540_ISR_Login_Admin_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT540_ISR_Login_Auth_test;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Navigate to Login page");
        getBrowser().navigateTo(td.url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        endTestLevel();

        startTestLevel("Login to control panel using admin non corporate email and password + 2FA ");
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingNonCorporateEmailPassword(td.nonCorporateAdminEmail, true);
        endTestLevel();

        startTestLevel("Populate Login 2FA with the secret key");
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        endTestLevel();
    }

    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @Test(description = "AUT540 - Control panel Login for non corporate super admin email test - 2FA")
    public void AUT540_ISR_Login_Super_Admin_test() {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        AbstractCpInvestorRegistrationFlow.ISR_TestScenario testScenario = ISR_TestScenario.AUT540_ISR_Login_Auth_test;
        ISR_TestData td = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Navigate to Login page");
        getBrowser().navigateTo(td.url);
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        endTestLevel();

        startTestLevel("Login to control panel using admin non corporate email and password + 2FA ");
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingNonCorporateEmailPassword(td.nonCorporateSuperAdminEmail, true);
        endTestLevel();

        startTestLevel("Populate Login 2FA with the secret key");
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        endTestLevel();
    }
}
