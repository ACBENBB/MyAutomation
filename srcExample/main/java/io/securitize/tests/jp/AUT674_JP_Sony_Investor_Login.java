package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.infra.utils.SingleSignOnSony;
import io.securitize.pageObjects.jp.controlPanel.JpCpSideMenu;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;
import static org.assertj.core.api.Assertions.assertThat;

public class AUT674_JP_Sony_Investor_Login extends AbstractJpFlow {

    private static final boolean RETRY_FLAG = true;
    private static final String BANK_BRANCH_CODE = "501";

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT674 - Sony Investor site Login")
    public void AUT674_JP_Sony_Investor_Login_test() {
        startTestLevel("get unused bank account number");
        String accountNumber = sonyGetUnusedBankAccountNumberIn(BANK_BRANCH_CODE);
        assertThat(sonyInvestorIsFound(BANK_BRANCH_CODE, accountNumber)).isFalse();
        endTestLevel();

        startTestLevel("perform sso login (with registration)");
        String url = SingleSignOnSony.getUrl(BANK_BRANCH_CODE, accountNumber);
        getBrowser().navigateTo(url);
        getBrowser().waitForPageStable();
        endTestLevel();

        navigateSonyInvestorDashboardPagesAndLogout(RETRY_FLAG);

        startTestLevel("login Control Panel and check the newly registered investor on Onboarding page");
        String tokenName = "TEST01";
        JpCpSideMenu sideMenu = cpLoginSony(tokenName);
        String investorEmail = BANK_BRANCH_CODE + accountNumber + "@s.s.s.sso";
        String expectedInvestorName = BANK_BRANCH_CODE + " " + accountNumber;
        String expectedCountry = "日本";
        cpOnboardingPageCheckTableValues(investorEmail, expectedInvestorName, expectedCountry);
        endTestLevel();

        cpInvestorDetailsCheckGeneralInformationValues(investorEmail, BANK_BRANCH_CODE, accountNumber, "Japan");

        cpLogout(sideMenu);
    }
}
