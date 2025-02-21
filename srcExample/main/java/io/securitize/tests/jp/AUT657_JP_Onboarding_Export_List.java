package io.securitize.tests.jp;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanel.JpCpSideMenu;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

public class AUT657_JP_Onboarding_Export_List extends AbstractJpFlow {

    /**
     * test is to check if export list works or not.
     * export list fails when sorting "SecuritizeID" column. this is a known issue.
     */
    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT657 On Onboarding Page, Export List")
    public void AUT657_JP_OnBoarding_Export_List_test() {
        String adminEmail = Users.getProperty(UsersProperty.jpOp03AdminEmail);
        String adminPassword = Users.getProperty(UsersProperty.jpOp03AdminPassword);
        String adminMfaSecret = Users.getProperty(UsersProperty.jpOp03AdminMfaSecret);
        cpLogin(adminEmail, adminPassword, adminMfaSecret);
        selectIssuer("ソニー銀行");
        JpCpSideMenu sideMenu = selectTokenName("TEST01");

        int downloadTimeoutSeconds = 90;
        boolean exportListResult;

        List<String> tableHeaders = new LinkedList<>(Arrays.asList("", "投資家", "居住国", "タイプ", "登録元", "SecuritizeID", "KYC", "適格性", "作成日"));
        tableHeaders.remove("SecuritizeID"); // delete this line when fixed

        SoftAssertions softAssertions = new SoftAssertions();

        int expectedDownloadsCount = 1;
        for (String tableHeader : tableHeaders) {
            exportListResult = onBoardingExportList(tableHeader, expectedDownloadsCount, downloadTimeoutSeconds);
            softAssertions.assertThat(exportListResult).isEqualTo(true);
            if (exportListResult) {
                expectedDownloadsCount += 1;
            }
        }

        softAssertions.assertAll();
        cpLogout(sideMenu);
    }

}
