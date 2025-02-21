package io.securitize.tests.jp;

import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.infra.utils.SingleSignOnSony;
import io.securitize.pageObjects.jp.controlPanel.JpCpSideMenu;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import io.securitize.tests.jp.testData.SonyInvestorDetails;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * sony sso login with bank branch code, bank account number, birthdate, suitability code, and country code
 */
public class AUT704_JP_Sony_Investor_Login_New extends AbstractJpFlow {

    private static final boolean RETRY_FLAG = true;
    private static final String BANK_BRANCH_CODE = "502";

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT704 - Sony Investor site Login")
    public void AUT704_JP_Sony_Investor_Login_test() {
        startTestLevel("get unused bank account number, and set investor data");
        String accountNumber = sonyGetUnusedBankAccountNumberIn(BANK_BRANCH_CODE);
        assertThat(sonyInvestorIsFound(BANK_BRANCH_CODE, accountNumber)).isFalse();
        SonyInvestorDetails investorDetails = new SonyInvestorDetails();
        investorDetails.setAddressAndCountryCodeRandomly();
        String birthDate = investorDetails.getBirthDate();
        String suitabilityCode = investorDetails.getSuitabilityCode();
        String countryCode = investorDetails.getResidenceCountryCode();
        endTestLevel();

        startTestLevel("perform sso login (with registration)");
        String url = SingleSignOnSony.getUrl(BANK_BRANCH_CODE, accountNumber, birthDate, suitabilityCode, countryCode);
        getBrowser().navigateTo(url);
        getBrowser().waitForPageStable();
        endTestLevel();

        navigateSonyInvestorDashboardPagesAndLogout(RETRY_FLAG);

        startTestLevel("login Control Panel and check the newly registered investor on Onboarding page");
        String tokenName = "TEST01";
        JpCpSideMenu sideMenu = cpLoginSony(tokenName);
        String investorEmail = BANK_BRANCH_CODE + accountNumber + "@s.s.s.sso";
        String expectedInvestorName = BANK_BRANCH_CODE + " " + accountNumber;
        String expectedCountry = getExpectedCountryInJapanese(countryCode);
        cpOnboardingPageCheckTableValues(investorEmail, expectedInvestorName, expectedCountry);
        endTestLevel();

        String country = investorDetails.getCountryNameFromCountryCode(countryCode);
        cpInvestorDetailsCheckGeneralInformationValues(investorEmail, BANK_BRANCH_CODE, accountNumber, country);

        cpLogout(sideMenu);
    }
}
