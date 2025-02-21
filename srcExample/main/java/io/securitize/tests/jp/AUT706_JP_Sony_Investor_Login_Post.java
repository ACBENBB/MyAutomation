package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.controlPanel.JpCpSideMenu;
import io.securitize.pageObjects.jp.sony.SonyDashboard;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import io.securitize.tests.jp.testData.SonyInvestorDetails;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static org.assertj.core.api.Assertions.assertThat;
import static io.securitize.infra.reporting.MultiReporter.*;

/**
 * sony sso login with post operation
 */
public class AUT706_JP_Sony_Investor_Login_Post extends AbstractJpFlow {

    private static final boolean RETRY_FLAG = true;
    private static final String BANK_BRANCH_CODE = "503";

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT706 - Sony Investor site Login")
    public void AUT706_JP_Sony_Investor_Login_test() {
        startTestLevel("get unused bank account number, and set investor data");
        String accountNumber = sonyGetUnusedBankAccountNumberIn(BANK_BRANCH_CODE);
        assertThat(sonyInvestorIsFound(BANK_BRANCH_CODE, accountNumber)).isFalse();
        SonyInvestorDetails investorDetails = new SonyInvestorDetails();
        investorDetails.setBankAccountNumber(BANK_BRANCH_CODE, "1", accountNumber);
        investorDetails.setNamesRandomly();
        investorDetails.setAddressAndCountryCodeRandomly();
        String countryCode = investorDetails.getResidenceCountryCode();
        endTestLevel();

        startTestLevel("perform sso login (with registration)");
        startSingleSignOnWithPost(investorDetails);
        SonyDashboard sonyDashboard = retryFunctionWithRefreshPage(()-> new SonyDashboard(getBrowser()), RETRY_FLAG);
        assertThat(sonyDashboard.invalidScreenTransitionTextIsDisplayed()).isFalse();
        endTestLevel();

        navigateSonyInvestorDashboardPagesAndLogout(RETRY_FLAG);

        startTestLevel("login Control Panel and check the newly registered investor on Onboarding page");
        String tokenName = "TEST01";
        JpCpSideMenu sideMenu = cpLoginSony(tokenName);
        String investorEmail = BANK_BRANCH_CODE + accountNumber + "@s.s.s.sso";
        String expectedInvestorName = investorDetails.getGivenName() + " " + investorDetails.getFamilyName();
        String expectedCountry = getExpectedCountryInJapanese(investorDetails.getResidenceCountryCode());
        cpOnboardingPageCheckTableValues(investorEmail, expectedInvestorName, expectedCountry);
        endTestLevel();

        String country = investorDetails.getCountryNameFromCountryCode(countryCode);
        cpInvestorDetailsCheckGeneralInformationValues(investorEmail, investorDetails.getGivenName(), investorDetails.getFamilyName(), country);

        cpLogout(sideMenu);
    }

}
