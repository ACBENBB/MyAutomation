package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanel.JpCpFundraise;
import io.securitize.pageObjects.jp.controlPanel.JpCpSideMenu;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT594_JP_ControlPanel_Fundraise extends AbstractJpFlow {

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT594 - Japan - walk Control Panel Fundraise page - Issuer Sony Bank")
    void AUT594_JP_CP_Sony_Fundraise_test() {
        startTestLevel("Login");
        cpLogin();
        selectIssuer("ソニー銀行");
        JpCpSideMenu sideMenu = selectTokenName("TEST01");
        endTestLevel();

        String investorEmail = "0043000104@s.s.s.sso";
        String investorName = cpFundraisePageSearchAndGetInvestorName(investorEmail);

        startTestLevel("check table values on Fundraise page about " + investorName);
        SoftAssertions softAssertions = new SoftAssertions();
        JpCpFundraise fundraise = new JpCpFundraise(getBrowser());
        softAssertions.assertThat(fundraise.getTableDataAbout("KYC\nステータス", investorName)).isEqualTo("KYC待ち");
        softAssertions.assertThat(fundraise.getTableDataAbout("投資家タイプ", investorName)).isEqualTo("個人");
        softAssertions.assertThat(fundraise.getTableDataAbout("申込み額", investorName)).contains("¥\n10000000");
        softAssertions.assertThat(fundraise.getTableDataAbout("申込日", investorName)).isEqualTo("-");
        softAssertions.assertThat(fundraise.getTableDataAbout("入金額", investorName)).contains("¥\n10000000");
        softAssertions.assertThat(fundraise.getTableDataAbout("契約\nステータス", investorName)).isEqualTo("確認済");
        softAssertions.assertThat(fundraise.getTableDataAbout("Assigned Tokens", investorName)).isEqualTo("0");
        softAssertions.assertThat(fundraise.getTableDataAbout("ラウンド", investorName)).isEqualTo("Default name");
        softAssertions.assertThat(fundraise.getTableDataAbout("ラベル", investorName)).isNull();
        softAssertions.assertAll();
        endTestLevel();

        startTestLevel("click checkbox check");
        fundraise.clickCheckboxOf(investorName);
        assertThat(fundraise.isSelectedCheckboxOf(investorName)).isTrue();
        endTestLevel();

        startTestLevel("click eye icon, then click Return to List button");
        fundraise.clickEyeIconOf(investorName)
                 .clickReturnToListButton();
        endTestLevel();

        startTestLevel("click trash icon, then click cancel button");
        fundraise.clickTrashIconOf(investorName)
                 .clickCancelButtonOnDeleteDialog();
        endTestLevel();

        cpLogout(sideMenu);
    }
}
