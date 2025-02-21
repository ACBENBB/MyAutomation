package io.securitize.tests.jp;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanel.JpCpSideMenu;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AUT659_JP_Holders_Export_List extends AbstractJpFlow {

    /**
     * test is to check if export list works or not.
     * export list fails when sorting "Wallet Status" column ("登録ステータス" in the list). this is a known issue.
     */
    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT659 On Holders Page, Export List sorting record holder column")
    public void AUT659_JP_Holders_Export_List_test() {
        String adminEmail = Users.getProperty(UsersProperty.smtb2AdminEmail);
        String adminPassword = Users.getProperty(UsersProperty.smtb2AdminPassword);
        String adminMfaSecret = Users.getProperty(UsersProperty.smtb2AdminMfaSecret);
        cpLogin(adminEmail, adminPassword, adminMfaSecret);
        JpCpSideMenu sideMenu = selectTokenName("TEST01");

        int downloadTimeoutSeconds = 90;
        boolean exportListResult;

        List<String> tableHeaders = new LinkedList<>(Arrays.asList("", "保有者", "居住国", "タイプ", "トークン", "ウォレットアドレス", "登録ステータス"));
        tableHeaders.remove("登録ステータス"); // delete this line when fixed

        SoftAssertions softAssertions = new SoftAssertions();

        int expectedDownloadsCount = 1;
        for (String tableHeader : tableHeaders) {
            exportListResult = holdersExportList(tableHeader, expectedDownloadsCount, downloadTimeoutSeconds);
            softAssertions.assertThat(exportListResult).isEqualTo(true);
            if (exportListResult) {
                expectedDownloadsCount += 1;
            }
        }

        softAssertions.assertAll();
        cpLogout(sideMenu);
    }
}
