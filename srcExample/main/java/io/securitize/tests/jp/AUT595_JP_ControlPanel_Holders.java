package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanel.JpCpHolders;
import io.securitize.pageObjects.jp.controlPanel.JpCpSideMenu;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT595_JP_ControlPanel_Holders extends AbstractJpFlow {

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT595 - Japan - walk Control Panel Holders page - Issuer Sony Bank")
    void AUT595_JP_CP_Sony_Holders_test() {
        startTestLevel("Login");
        cpLogin();
        selectIssuer("ソニー銀行");
        JpCpSideMenu sideMenu = selectTokenName("TEST01");
        endTestLevel();

        String investorEmail = "0043000104@s.s.s.sso";
        String investorName = cpHoldersPageSearchAndGetInvestorName(investorEmail);

        startTestLevel("check table values on Holders page about " + investorName);
        SoftAssertions softAssertions = new SoftAssertions();
        JpCpHolders holders = new JpCpHolders((getBrowser()));
        softAssertions.assertThat(holders.getTableDataAbout("居住国", investorName)).isEqualTo("日本");
        softAssertions.assertThat(holders.getTableDataAbout("投資家\nタイプ", investorName)).isEqualTo("個人");
        softAssertions.assertThat(holders.getTableDataAbout("トークン\n保有量", investorName)).isEqualTo("1000");
        softAssertions.assertThat(holders.getTableDataAbout("パーセント", investorName)).isEqualTo("38.4615 %");
        softAssertions.assertThat(holders.getTableDataAbout("ウォレットアドレス", investorName)).isEqualTo("treasury");
        softAssertions.assertThat(holders.getTableDataAbout("ウォレット\n登録ステータス", investorName)).isNull();
        softAssertions.assertThat(holders.getTableDataAbout("ラベル", investorName)).isNull();
        softAssertions.assertAll();
        endTestLevel();

        startTestLevel("click checkbox check");
        holders.clickCheckboxOf(investorName);
        assertThat(holders.isSelectedCheckboxOf(investorName)).isTrue();
        endTestLevel();

        startTestLevel("click eye icon, then click Return to List button");
        holders.clickEyeIconOf(investorName)
               .clickReturnToListButton();
        endTestLevel();

        startTestLevel("click trash icon, then click cancel button");
        holders.clickTrashIconOf(investorName)
               .clickCancelButtonOnDeleteDialog();
        endTestLevel();

        cpLogout(sideMenu);
    }
}
