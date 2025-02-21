package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.sony.SonyDashboard;
import io.securitize.pageObjects.jp.sony.SonyDashboardDocuments;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT606_JP_Investor_Documents extends AbstractJpFlow {

    private static final int AT_THE_TOP_ROW = 1;

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT606 - Sony Investor site Documents")
    public void AUT606_JP_Sony_Investor_Documents_test() {
        boolean retryFlag = true;
        startTestLevel("login sony investor site");
        String bankBranchCode = "004";
        String bankAccountNumber = "3000104";
        SonyDashboard sonyDashboard = loginSonyInvestorPage(bankBranchCode, bankAccountNumber, retryFlag);
        endTestLevel();

        startTestLevel("navigate to documents tab, and select opportunity");
        String opportunityName = "総合試験用案件01";
        SonyDashboardDocuments documents = sonyDashboard.switchToDocumentsTab(retryFlag);
        documents.selectOpportunity(opportunityName, retryFlag);
        endTestLevel();

        startTestLevel("check the document at the top in the table");
        SoftAssertions softAssertions = new SoftAssertions();
        String documentNameAtRow1 = documents.getDocumentByTableHeaderAndRowNumber("名称", AT_THE_TOP_ROW, retryFlag);
        softAssertions.assertThat(documentNameAtRow1).isEqualTo("信託財産状況報告書");
        String documentDateAtRow1 = documents.getDocumentByTableHeaderAndRowNumber("交付日/確認日", AT_THE_TOP_ROW, retryFlag);
        softAssertions.assertThat(documentDateAtRow1).isEqualTo("2023/07/07");
        softAssertions.assertAll();
        endTestLevel();

        startTestLevel("click download on the document");
        documents.clickDownloadByRowNumber(AT_THE_TOP_ROW, retryFlag);
        getBrowser().waitForPageStable();
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        endTestLevel();

        startTestLevel("logout");
        sonyDashboard.performLogout(retryFlag);
        endTestLevel();
    }
}
