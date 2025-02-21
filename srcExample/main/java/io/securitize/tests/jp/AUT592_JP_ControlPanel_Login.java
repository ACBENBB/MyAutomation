package io.securitize.tests.jp;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanel.*;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

/**
 * note to self
 * - when login is SONY or SMTB, Issuer List page should not appear. Dashboard should be displayed instead.
 * - when Login is Marui, Issuer List page should appear.
 */
public class AUT592_JP_ControlPanel_Login extends AbstractJpFlow {

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT592 - Control Panel login by Sony Admin")
    public void AUT592_JP_ControlPanel_Login_SONY_test() {
        startTestLevel("login Control Panel as Sony admin");
        String adminEmail = Users.getProperty(UsersProperty.sonyAdminEmail);
        String adminPassword = Users.getProperty(UsersProperty.sonyAdminPassword);
        String adminMfaSecret = Users.getProperty(UsersProperty.sonyAdminMfaSecret);
        cpLogin(adminEmail, adminPassword, adminMfaSecret);
        endTestLevel();

        startTestLevel("Navigate to Onboarding page, and then logout");
        JpCpSideMenu sideMenu = new JpCpSideMenu(getBrowser());
        sideMenu.clickOnBoarding();
        sideMenu.logout();
        endTestLevel();
    }

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT592 - Control Panel login by Sumitomo-Mitsui-Trust-Bank Admin")
    public void AUT592_JP_ControlPanel_Login_SMTB1_test() {
        startTestLevel("login Control Panel as SMTB admin");
        String adminEmail = Users.getProperty(UsersProperty.smtb1AdminEmail);
        String adminPassword = Users.getProperty(UsersProperty.smtb1AdminPassword);
        String adminMfaSecret = Users.getProperty(UsersProperty.smtb1AdminMfaSecret);
        cpLogin(adminEmail, adminPassword, adminMfaSecret);
        endTestLevel();

        startTestLevel("Navigate to Onboarding page, and then logout");
        JpCpSideMenu sideMenu = new JpCpSideMenu(getBrowser());
        sideMenu.clickOnBoarding();
        sideMenu.logout();
        endTestLevel();
    }

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT592 - Control Panel login by Marui Admin")
    public void AUT592_JP_ControlPanel_Login_Marui_test() {
        startTestLevel("login Control Panel as Marui admin");
        String adminEmail = Users.getProperty(UsersProperty.marui_adminEmail);
        String adminPassword = Users.getProperty(UsersProperty.marui_adminPassword);
        String adminMfaSecret = Users.getProperty(UsersProperty.marui_adminMfaSecret);
        cpLogin(adminEmail, adminPassword, adminMfaSecret);
        endTestLevel();

        startTestLevel("logout");
        JpCpSelectIssuer selectIssuer = new JpCpSelectIssuer(getBrowser());
        selectIssuer.logout();
        endTestLevel();
    }
}
