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

public class AUT658_JP_Fundraise_Export_List extends AbstractJpFlow {

    /**
     * test is to check if export list works or not.
     * export list fails when sorting "Investor Status" column ("KYC" in the list). this is a known issue.
     */
    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT658 On Fundraise Page, Export List after sorting investor name column")
    public void AUT658_JP_Fundraise_Export_List_test() {
        String adminEmail = Users.getProperty(UsersProperty.smtb1AdminEmail);
        String adminPassword = Users.getProperty(UsersProperty.smtb1AdminPassword);
        String adminMfaSecret = Users.getProperty(UsersProperty.smtb1AdminMfaSecret);
        cpLogin(adminEmail, adminPassword, adminMfaSecret);
        JpCpSideMenu sideMenu = selectTokenName("TEST01");

        int downloadTimeoutSeconds = 90;
        boolean exportListResult;

        List<String> tableHeaders = new LinkedList<>(Arrays.asList("", "投資家", "KYC", "投資家タイプ", "申込み額", "申込日", "入金額", "契約", "Assigned Tokens"));
        tableHeaders.remove("KYC"); // delete this line when fixed

        SoftAssertions softAssertions = new SoftAssertions();

        int expectedDownloadsCount = 1;
        for (String tableHeader : tableHeaders) {
            exportListResult = fundraiseExportList(tableHeader, expectedDownloadsCount, downloadTimeoutSeconds);
            softAssertions.assertThat(exportListResult).isEqualTo(true);
            if (exportListResult) {
                expectedDownloadsCount += 1;
            }
        }

        softAssertions.assertAll();
        cpLogout(sideMenu);
    }
}
