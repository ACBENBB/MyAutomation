package io.securitize.tests.securitizeId;

import java.time.Duration;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdProfile;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import io.securitize.tests.InvestorDetails;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT165_SID_Validate_Entity_Information_Issuer extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT165 - Entity Information Validation")
    public void AUT165_SID_Validate_Entity_Information_Issuer_Test() {

        String issuerName = "Nie";
        InvestorDetails investorDetails = InvestorDetails.generateRandomEntityInvestor();
        nieInitialSecIdRegistration(issuerName, investorDetails);

        startTestLevel("Register investor and set KYB to pass");
        fillEntityKybSteps(investorDetails, "Individual", true);
        setVerificationStatusAsVerified(issuerName, investorDetails, true);
        endTestLevel();


        startTestLevel("Go to SiD - Profile");
        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);
        getBrowser().navigateTo(sidURL);
        endTestLevel();


        startTestLevel("Go to Entity information page & validate Entity information card's data");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        SecuritizeIdProfile securitizeIdProfile = securitizeIdDashboard.clickAccount();
        securitizeIdProfile.clickEntityInformationCard();

        SoftAssert softAsser = new SoftAssert();
        softAsser.assertEquals(securitizeIdProfile.getEntityLegalName(), investorDetails.getEntityName(), "Validate Entity legal name");
        softAsser.assertEquals(securitizeIdProfile.getEntityDoingBusinessAs(), "Automation Testing", "Validate Doing business as (DBA)");
        softAsser.assertEquals(securitizeIdProfile.getEntityType(), "Corporation", "Validate Entity type");
        //Tax ID has ***** at the beginning, and only 4 visible numbers at the end
        String taxID = investorDetails.getTaxId();
        String lastTaxIDChars = taxID.substring(taxID.length() - 4);
        String censoredTaxID = "*****" + lastTaxIDChars;
        softAsser.assertEquals(securitizeIdProfile.getEntityTaxID(), censoredTaxID, "Validate Tax ID");
        endTestLevel();


        startTestLevel("Validate Address card's data");
        softAsser.assertEquals(securitizeIdProfile.getAddressStreet(), investorDetails.getStreetName(), "Validate Street address");
        softAsser.assertEquals(securitizeIdProfile.getAddressAdditionalInfo(), investorDetails.getStreetAdditionalInfo(), "Validate Additional info");
        softAsser.assertEquals(securitizeIdProfile.getAddressPostalCode(), investorDetails.getPostalCode(), "Validate Postal code/ZIP code");
        softAsser.assertEquals(securitizeIdProfile.getAddressCity(), investorDetails.getCity(), "Validate City");
        softAsser.assertEquals(securitizeIdProfile.getAddressCountry(), investorDetails.getCountry(), "Validate Country");
        softAsser.assertEquals(securitizeIdProfile.getAddressState(), investorDetails.getStateCode(), "Validate State");
        securitizeIdProfile.clickGoBackToAccountLink();
        endTestLevel();


        startTestLevel("Go to Tax Center page");
        securitizeIdProfile.clickTaxCenterCard();
        getBrowser().waitForPageStable(Duration.ofSeconds(5));
        //Nothing to validate here, just going and going back in case the page gets broken
        securitizeIdProfile.clickGoBackToAccountLink();
        endTestLevel();


        startTestLevel("Go to Accreditation/Qualification page");
        securitizeIdProfile.clickAccreditationQualificationCard();
        securitizeIdDashboard.clickAccount();
        endTestLevel();


        startTestLevel("Go to Legal signers page");
        securitizeIdProfile.clickEntityLegalSignerCard();
        securitizeIdProfile.clickEntityLegalSignerPersonalInformation();
        softAsser.assertEquals(securitizeIdProfile.getPersonalInfoFirstName(), investorDetails.getFirstName(), "Validate first name");
        softAsser.assertEquals(securitizeIdProfile.getPersonalInfoMiddleName(), investorDetails.getMiddleName(), "Validate middle name");
        softAsser.assertEquals(securitizeIdProfile.getPersonalInfoLastName(), investorDetails.getLastName(), "Validate last name");
        softAsser.assertEquals(securitizeIdProfile.getCompleteBirthDate(), investorDetails.getBirthDate(), "Validate birth date");
        softAsser.assertAll();
        endTestLevel();
    }
}