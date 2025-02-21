package io.securitize.tests.fundraise;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;

import io.securitize.tests.fundraise.abstractclass.AbstractFT;
import io.securitize.tests.fundraise.pojo.FT_TestData;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;
import static io.securitize.tests.fundraise.abstractclass.ConstantsStrings.KYCstatus.*;

public class AUT672_FT_NIE_KYC_Status extends AbstractFT {

    @Test(description = "AUT672FT Verify KYC Status")
    public void AUT672_FT_NIE_KYC_Status() throws Exception {

        startTestLevel("Define Test Scenario and Create Test Data Object");
        FT_TestScenario testScenario = FT_TestScenario.FT_NIE_KYC_STATUS;
        FT_TestData testdata = createTestDataObject(testScenario);
        endTestLevel();

        startTestLevel("Add Issuer And Token Id To TestData Object");
        testdata.issuerId = Users.getProperty(UsersProperty.ft_issuerId_aut516);
        testdata.tokenId = Users.getProperty(UsersProperty.ft_tokenId_aut516);
        endTestLevel();

        startTestLevel("Create Investor From Test Scenario Data");
        testdata = createFtTestInvestor(testdata);
        endTestLevel();

        startTestLevel("Navigate To issuer And Login With Crated Investor");
        navigateToIssuerNieWebSite(Users.getProperty(UsersProperty.ft_issuerNieUrl_aut516));
        loginToNie(
                investorDetails.getEmail(),
                Users.getProperty(UsersProperty.apiInvestorPassword)
        );
        endTestLevel();

        startTestLevel("Click Nie Opportunities Tab");
        navigateToOpportunitiesTab();
        endTestLevel();

        startTestLevel("Select Opportunity By Name");
        clickInvestmentByName(testdata.investmentName);
        endTestLevel();

        startTestLevel("Set Investment Currency And Amount");
        setInvestmentCurrencyAndAmount(testdata.investmentCurrency, testdata.investmentAmount);
        endTestLevel();

        startTestLevel("Verify KYC Status 'Verified'");
        clickInvest();
        verifyInvestWizardQualification();
        endTestLevel();

        startTestLevel("Update KYC Status By API to VERIFIED_EXPIRED");
        updateKycStatusByAPI(testdata, VERIFIED_EXPIRED.getStatus());
        endTestLevel();

        startTestLevel("Verify KYC Status 'Verified Expired'");
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        clickInvest();
        verifyInvestWizardQualification();
        endTestLevel();

        startTestLevel("Update KYC Status By API to VERIFIED_DOCS_EXPIRED.");
        updateKycStatusByAPI(testdata, VERIFIED_DOCS_EXPIRED.getStatus());
        endTestLevel();

        startTestLevel("Verify KYC Status 'Verified Documents Expired'");
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        clickInvest();
        verifyInvestWizardQualification();
        endTestLevel();

        startTestLevel("Update KYC Status By API to UPDATES REQUIRED ");
        updateKycStatusByAPI(testdata, UPDATES_REQUIRED.getStatus());
        endTestLevel();

        startTestLevel("Verify Popup Updates required and Target Page");
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        verifyPopUpAndKYCStatus(testdata.investorKycStatus);
        verifyTypeOfInvestorStep();
        endTestLevel();

        startTestLevel("Update KYC Status By API to EXPIRED ");
        updateKycStatusByAPI(testdata, EXPIRED.getStatus());
        endTestLevel();

        startTestLevel("Verify Popup and KYC Status 'Expired' and Target Page URL");
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        verifyPopUpAndKYCStatus(testdata.investorKycStatus);
        verifyIdentityVerificationStep();
        endTestLevel();

        startTestLevel("Update KYC Status By API to NONE");
        updateKycStatusByAPI(testdata, NONE.getStatus());
        endTestLevel();

        startTestLevel("Verify Popup and KYC Status 'None' and Target Page ");
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        verifyPopUpAndKYCStatus(testdata.investorKycStatus);
        verifyIdentityVerificationStep();
        endTestLevel();

        startTestLevel("Update KYC Status By API to PROCESSING");
        updateKycStatusByAPI(testdata, PROCESSING.getStatus());
        endTestLevel();

        startTestLevel("Verify Popup and KYC Status 'Processing' and Target Page URL_RC");
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        verifyPopUpAndKYCStatus(testdata.investorKycStatus);
        endTestLevel();

        startTestLevel("Update KYC Status By API to REJECTED");
        updateKycStatusByAPI(testdata, REJECTED.getStatus());
        endTestLevel();

        startTestLevel("Verify KYC Status 'Rejected'");
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        verifyKycStatusRejected();
        endTestLevel();

        startTestLevel("Update KYC Status By API to REJECTED_BLOCKED");
        updateKycStatusByAPI(testdata, REJECTED_BLOCKED.getStatus());
        endTestLevel();

        startTestLevel("Verify KYC Status 'Rejected Blocked'");
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        getBrowser().refreshPage();
        verifyKycStatusRejected();
        endTestLevel();
    }
}
