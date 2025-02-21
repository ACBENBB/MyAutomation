package io.securitize.tests.jp;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.AllowTestOnEnvironments;
import io.securitize.pageObjects.jp.mauri.*;
import io.securitize.tests.jp.abstractClass.AbstractJpFlow;
import io.securitize.tests.jp.testData.JpInvestorDetails;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;
import static org.assertj.core.api.Assertions.assertThat;

public class AUT601_JP_Marui_Application_PreEntry extends AbstractJpFlow {

    private static final boolean RETRY_FLAG = true;
    private static final String MARUI_EXISTING_INVESTORS_CSV = "src/main/resources/investorMetaData/jp-marui-existing-investors.csv";

    @AllowTestOnEnvironments(environments = {"sandbox"})
    @Test(description = "AUT601 - Japan - Marui Application PreEntry")
    void AUT601_JP_Marui_Application_PreEntry_test() {
        JpInvestorDetails investorDetails = getJpInvestorDetailsWithIndexFromCsvFile(MARUI_EXISTING_INVESTORS_CSV, "z001");
        String investorEmail = Users.getProperty(UsersProperty.marui_existingInvestorEmail); // z001
        String tokenName = getTokenNameWithAssert();

        startTestLevel("Before PreEntry Application");
        cpPlusEnsureInvestorCanMakePreEntryApplication(tokenName, investorEmail, RETRY_FLAG);
        String pledgedAmount = investorDetails.getPledgedAmount();
        endTestLevel();

        startTestLevel("Make PreEntry Application");
        MaruiApplicationPreEntryStep1AgreeTerms step1 = maruiPreEntryStart(investorEmail, RETRY_FLAG);
        MaruiApplicationPreEntryStep2Document step2 = maruiPreEntryStep1AgreeTerms(step1, RETRY_FLAG);
        MaruiApplicationPreEntryStep3InputAmount step3 = maruiPreEntryStep2Documents(step2, RETRY_FLAG);
        MaruiApplicationPreEntryStep4Confirm step4 = maruiPreEntryStep3EnterAmount(step3, pledgedAmount, RETRY_FLAG);
        MaruiApplicationPreEntryStep5Complete step5 = maruiPreEntryStep4Confirm(step4, pledgedAmount, RETRY_FLAG);
        maruiPreEntryStep4Complete(step5);
        endTestLevel();

        // email content check
        String familyName = investorDetails.getLastName();
        String givenName = investorDetails.getGivenName();
        emailContentCheckAfterPreEntry(tokenName, investorEmail, familyName, givenName, pledgedAmount);

        startTestLevel("[Control Panel] Check Onboarding page");
        cpLoginMarui(tokenName, RETRY_FLAG);
        String expectedCountry = "日本";
        String expectedInvestorName = investorDetails.getGivenName() + " " + investorDetails.getLastName();
        cpOnboardingPageCheckTableValues(investorEmail, expectedInvestorName, expectedCountry);
        endTestLevel();

        startTestLevel("[Control Panel] Check investor details investment section after PreEntry");
        cpOpenInvestorDetailsPageAndClickInvestmentLink(investorEmail);
        cpInvestorDetailsCheckInvestmentSectionValuesAfterPreEntry(tokenName);
        endTestLevel();

        startTestLevel("[Control Panel] Check Fundraise page");
        cpFundraisePageCheckTableValuesAfterPreEntry(investorEmail, pledgedAmount);
        cpLogout();
        endTestLevel();

        startTestLevel("[Control Panel Plus] Check Investor Details Investment section");
        String currencySymbol = "￥";
        cpPlusInvestmentValueCheckAfterPreEntry(tokenName, investorEmail, pledgedAmount, currencySymbol, RETRY_FLAG);
        endTestLevel();

        startTestLevel("[Control Panel Plus] Cancel Investment");
        cpPlusCancelInvestmentAndLogout(investorEmail, RETRY_FLAG);
        endTestLevel();

        // email content check
        emailContentCheckAfterCancelPreEntry(tokenName, investorEmail, familyName, givenName);

        startTestLevel("[Control Panel] Ensure the investor is not on Fundraise page");
        cpLoginMarui(tokenName, RETRY_FLAG);
        String investorName = cpFundraisePageSearchAndGetInvestorName(investorEmail);
        assertThat(investorName).isNull();
        endTestLevel();

        startTestLevel("[Control Panel] Check investor details investment section after cancelling PreEntry");
        cpOpenInvestorDetailsPageAndClickInvestmentLink(investorEmail);
        cpInvestorDetailsCheckInvestmentSectionValuesAfterCancelPreEntry(tokenName);
        endTestLevel();
    }
}
