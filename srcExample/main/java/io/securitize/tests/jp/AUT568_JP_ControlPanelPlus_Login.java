package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanelPlus.CpPlusSideMenu;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT568_JP_ControlPanelPlus_Login extends AbstractJpFlow {

    private static final boolean RETRY_FLAG = true;

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT568 - Control Panel Plus Login")
    public void AUT568_JP_ControlPanelPlus_Login_test() {
        String tokenName = getTokenNameWithAssert();
        CpPlusSideMenu sideMenu = cpPlusLoginMarui(tokenName, RETRY_FLAG);

        startTestLevel("navigate to investors page");
        sideMenu.clickInvestors(RETRY_FLAG);
        endTestLevel();

        startTestLevel("logout");
        sideMenu.clickLogoutButton();
        endTestLevel();
    }
}
