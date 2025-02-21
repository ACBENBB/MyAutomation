package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanel.JpCpOnBoarding;
import io.securitize.pageObjects.jp.controlPanel.JpCpSideMenu;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT596_JP_ControlPanel_Onboarding extends AbstractJpFlow {

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT596 - Japan - walk Control Panel Onboarding Page - Issuer Sony Bank")
    void AUT596_JP_CP_Sony_Onboarding_test() {
        startTestLevel("Login");
        cpLogin();
        selectIssuer("ソニー銀行");
        JpCpSideMenu sideMenu = selectTokenName("TEST01");
        endTestLevel();

        String investorEmail = "0043000104@s.s.s.sso";
        String investorName = cpOnboardingPageSearchAndGetInvestorName(investorEmail);

        startTestLevel("check table values on OnBoarding page about " + investorName);
        SoftAssertions softAssertions = new SoftAssertions();
        JpCpOnBoarding onBoarding = new JpCpOnBoarding(getBrowser());
        softAssertions.assertThat(onBoarding.getTableDataAbout("居住国", investorName)).isEqualTo("日本");
        softAssertions.assertThat(onBoarding.getTableDataAbout("タイプ", investorName)).isEqualTo("個人");
        softAssertions.assertThat(onBoarding.getTableDataAbout("登録元", investorName)).isEqualTo("内部");
        softAssertions.assertThat(onBoarding.getTableDataAbout("SecuritizeID", investorName)).isEqualTo("なし");
        softAssertions.assertThat(onBoarding.getTableDataAbout("KYC\nステータス", investorName)).isEqualTo("なし");
        softAssertions.assertThat(onBoarding.getTableDataAbout("適格性\nステータス", investorName)).isEqualTo("なし");
        softAssertions.assertThat(onBoarding.getTableDataAbout("ラベル", investorName)).isNull();
        softAssertions.assertThat(onBoarding.getTableDataAbout("作成日", investorName)).isEqualTo("2023年4月19日");
        softAssertions.assertAll();
        endTestLevel();

        startTestLevel("click checkbox check");
        onBoarding.clickCheckboxOf(investorName);
        assertThat(onBoarding.isSelectedCheckboxOf(investorName)).isTrue();
        endTestLevel();

        startTestLevel("click eye icon, then click Return to List button");
        onBoarding.clickEyeIconOf(investorName)
                  .clickReturnToListButton();
        endTestLevel();

        startTestLevel("click trash icon, then click cancel button");
        onBoarding.clickTrashIconOf(investorName)
                  .clickCancelButtonOnDeleteDialog();
        endTestLevel();

        cpLogout(sideMenu);
    }
}
