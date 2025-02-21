package io.securitize.tests.securitizeId;

import io.securitize.infra.config.*;
import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT337_SID_ControlPanel_Regression_Overview extends AbstractCpInvestorRegistrationFlow {

    @Test(description = "Sanity check in SID's CP - investor details page")
    @BypassRecaptcha(environments = {"production"})
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    public void AUT337_SID_ControlPanel_Regression_Overview_Test() {

        // we will conduct a non valid search to empty the table of investors
        // this will help us figure out when a later valid search finishes -
        // the table will have at least one row
        String nonValidSearch = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        String issuerName = "Nie";
        String investorName = SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.investorName);

        startTestLevel("Open control panel -> Login");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl));
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        endTestLevel();


        startTestLevel("Open control panel -> investors list");
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpInvestorsList cpInvestorsList = sideMenu
                .clickSecuritizeId()
                .clickInvestors();
        endTestLevel();


        startTestLevel("Apply filters");
        cpInvestorsList.searchInvestorByEmail(Users.getProperty(UsersProperty.SecID_IntegrityCheck_Email));
        cpInvestorsList.waitForTableToContainNumberOfRows(2);
        cpInvestorsList.applyAllFilters(
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.filterIssuers),
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.filterCountry),
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.filterState),
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.filterInvestorType),
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.filterVerificationStatus),
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.filterVerificationErrors),
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.filterAssignee),
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.startDate),
                SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.endDate)
        );
        endTestLevel();


        startTestLevel("Verify investor");
        SoftAssert softAssertion = new SoftAssert();
        softAssertion.assertEquals(cpInvestorsList.getInvestorDetailByName(investorName, "Country"), SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.country));
        softAssertion.assertEquals(cpInvestorsList.getInvestorDetailByName(investorName, "Type").toLowerCase(), SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.investorType).toLowerCase());
        softAssertion.assertEquals(cpInvestorsList.getInvestorDetailByName(investorName, "KYC Status"), SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.kycStatus));
        softAssertion.assertTrue(cpInvestorsList.getInvestorDetailByName(investorName, "Issuers").contains(SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.issuer)));
        softAssertion.assertEquals(cpInvestorsList.getInvestorDetailByName(investorName, "Creation date"), SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.startDate));
        softAssertion.assertTrue(cpInvestorsList.getInvestorDetailByName(investorName, "Assignee").contains(SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.assignee)));
        endTestLevel();


        startTestLevel("Clear all filters");
        cpInvestorsList.clearAllFilters();
        endTestLevel();


        startTestLevel("Control panel -> Securitize ID -> Investors list");
        cpInvestorsList.searchInvestorByEmail(nonValidSearch);
        cpInvestorsList.waitForInvestorsTableToBeEmpty();
        cpInvestorsList.searchInvestorByEmail(Users.getProperty(UsersProperty.SecID_IntegrityCheck_Email));
        String actualIssuersFullName = cpInvestorsList.getInvestorDetailByName("Sid Integrity Check Name", "Issuers");
        String expectedIssuerFullName = Users.getIssuerDetails(issuerName, IssuerDetails.issuersFullName);
        Assert.assertEquals(actualIssuersFullName, expectedIssuerFullName, "Issuers names specified in the investors table should match expected issuers names");
        endTestLevel();


        startTestLevel("Open specific investor details");
        cpInvestorsList.filterAssignee(SidExistingInvestorData.getUserDetails(SidSanityInvestorProperty.filterAssignee));
        cpInvestorsList.clickViewInvestorDetailsBtn();
        CpInvestorDetailsPage cpInvestorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        String externalIdPageUrl = getBrowser().getCurrentUrl().split("id/", 2)[1];
        String externalIdPageContent = cpInvestorDetailsPage.getExternalInvestorId();
        Assert.assertEquals(externalIdPageUrl, externalIdPageContent, "externalId in page url should match externalId in page content. This is not the case");
        endTestLevel();


        startTestLevel("Validate General information card");
        getBrowser().waitForPageStable();
        Assert.assertTrue(cpInvestorDetailsPage.isGeneralInformationSecIdVisible(), "General information must be visible. This is not the case");
        Assert.assertTrue(Strings.isNotNullAndNotEmpty(cpInvestorDetailsPage.getInvestorTypeSecId()), "Investor type must not be empty. This is not the case");
        String currentEmail = cpInvestorDetailsPage.getEmail();
        String email = Users.getProperty(UsersProperty.SecID_IntegrityCheck_Email);
        Assert.assertEquals(currentEmail, email, "Email specified in the 'General information' should match expected email. This is not the case");
        String expectedAuthorizedBy = Users.getProperty(UsersProperty.SecID_IntegrityCheck_AuthorizedBy);
        String actualAuthorizedBy = cpInvestorDetailsPage.getAuthorizedBy();
        Assert.assertEquals(actualAuthorizedBy, expectedAuthorizedBy, "'Authorized By' specified in the 'General information' should match expected 'Authorized By'. This is not the case");
        endTestLevel();


        startTestLevel("Validate Legal Signers card");
        Assert.assertTrue(cpInvestorDetailsPage.isLegalSignersSecIdVisible(), "Legal signers (Key Parties) must be visible. This is not the case");
        Assert.assertTrue(Strings.isNotNullAndNotEmpty(cpInvestorDetailsPage.getLegalSignersTypeSecId()), "Legal signers (Key Parties) type must not be empty. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.getLegalSignersEntriesSecIdVisibleCount() >= 1, "Legal signers (Key Parties) entries should be at least one visible entry. This is not the case");
        endTestLevel();


        startTestLevel("Validate Authorized Accounts card");
        Assert.assertTrue(cpInvestorDetailsPage.isAuthorizedAccountsSecIdVisible(), "Authorized accounts must be visible. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.getAuthorizedAccountEntriesSecIdVisibleCount() >= 1, "Authorized accounts entries should be at least one visible entry. This is not the case");
        endTestLevel();


        startTestLevel("Validate KYB card");
        Assert.assertTrue(cpInvestorDetailsPage.isKybSecIdVisible(), "KYB must be visible. This is not the case");
        Assert.assertTrue(Strings.isNotNullAndNotEmpty(cpInvestorDetailsPage.getCurrentKybStatusSecId()), "Current KYB status must not be empty. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.getKybStatusLogEntriesSecIdVisibleCount() >= 1, "KYB status log entries should be at least one visible entry. This is not the case");
        endTestLevel();


        startTestLevel("Validate Documents card");
        Assert.assertTrue(cpInvestorDetailsPage.isDocumentsSecIdVisible(), "Documents must be visible. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.isDocumentsAddDocumentSecIdVisible(), "Add document must be visible. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.getDocumentsImageSecIdVisibleCount() >= 1, "Document image entries should be at least one visible entry. This is not the case");
        endTestLevel();


        startTestLevel("Validate Confidential Documents card");
        Assert.assertTrue(cpInvestorDetailsPage.isConfidentialDocumentsSecIdVisible(), "Confidential documents must be visible. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.isConfidentialDocumentsAddDocumentSecIdVisible(), "Add confidential document must be visible. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.getConfidentialDocumentsImageSecIdVisibleCount() >= 1, "Confidential document image entries should be at least one visible entry. This is not the case");
        endTestLevel();


        startTestLevel("Validate Wallets card");
        Assert.assertTrue(cpInvestorDetailsPage.isWalletsSecIdVisible(), "Wallets must be visible. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.isAddWalletButtonSecIdVisible(), "Add wallets button must be visible. This is not the case");
        Assert.assertTrue(cpInvestorDetailsPage.getWalletsEntriesSecIdVisibleCount() >= 1, "Wallets entries should be at least one visible entry. This is not the case");
        softAssertion.assertAll();
        endTestLevel();
    }

}


