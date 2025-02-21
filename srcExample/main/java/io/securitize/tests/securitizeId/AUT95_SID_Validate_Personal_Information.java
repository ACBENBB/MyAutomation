package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdProfile;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import io.securitize.tests.InvestorDetails;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;



public class AUT95_SID_Validate_Personal_Information extends AbstractSecIdInvestorRegistrationFlow {

    @Test(description = "AUT95 - Personal Information Validation")
    public void AUT95_SID_Validate_Personal_Information_Test() {
        String sidURL = MainConfig.getProperty(MainConfigProperty.secIdUrl);

        startTestLevel("API: Create new SecuritizeId Investor");
        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        endTestLevel();


        startTestLevel("Login to Securitize iD");
        getBrowser().navigateTo(sidURL);
        SecuritizeIdInvestorLoginScreen securitizeIdInvestorLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdInvestorLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);
        endTestLevel();


        startTestLevel("Go to Personal Information and validate Personal Information's card data");
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        SecuritizeIdProfile securitizeIdProfile = securitizeIdDashboard
                .clickSkipTwoFactor()
                .clickAccount();
        securitizeIdProfile.clickPersonalInformationCard();
        Assert.assertEquals(investorDetails.getFirstName(), securitizeIdProfile.getPersonalInfoFirstName(), "Validate first name");
        Assert.assertEquals(investorDetails.getMiddleName(), securitizeIdProfile.getPersonalInfoMiddleName(), "Validate middle name");
        Assert.assertEquals(investorDetails.getLastName(), securitizeIdProfile.getPersonalInfoLastName(), "Validate last name");
        Assert.assertEquals(investorDetails.getBirthDate(), securitizeIdProfile.getCompleteBirthDate(), "Validate birth date");
        endTestLevel();


        startTestLevel("Validate Address card's data");
        Assert.assertEquals(investorDetails.getStreetName(), securitizeIdProfile.getAddressStreet(), "Validate street address");
        Assert.assertEquals(investorDetails.getStreetAdditionalInfo(), securitizeIdProfile.getAddressAdditionalInfo(), "Validate additional info");
        Assert.assertEquals(investorDetails.getPostalCode(), securitizeIdProfile.getAddressPostalCode(), "Validate postal code");
        Assert.assertEquals(investorDetails.getCity(), securitizeIdProfile.getAddressCity(), "Validate city");
        Assert.assertEquals(investorDetails.getCountry(), securitizeIdProfile.getAddressCountry(), "Validate country");
        Assert.assertEquals(investorDetails.getStateCode(), securitizeIdProfile.getAddressState(), "Validate state");
        securitizeIdProfile.clickGoBackToAccountLink();
        endTestLevel();


        startTestLevel("Go to Additional Information and validate data");
        securitizeIdProfile.clickAdditionalInformationCard();
        Assert.assertEquals(investorDetails.getCountryOfTax(), securitizeIdProfile.getCountryOfTax(), "Validate country of tax");
        //Tax ID has ***** at the begining, and only 4 visible numbers at the end
        String taxID = investorDetails.getTaxId();
        String lastTaxIDChars = taxID.substring(taxID.length() - 4);
        String censuredTaxID = "*****" + lastTaxIDChars;
        Assert.assertEquals(censuredTaxID, securitizeIdProfile.getAdditionalInfoTaxID(), "Validate tax id");
        securitizeIdProfile.clickGoBackToAccountLink();
        endTestLevel();


        startTestLevel("Go to Accreditation/Qualification page");
        securitizeIdProfile.clickAccreditationQualificationCard();
        securitizeIdProfile.clickGoBackToAccountLink();
        endTestLevel();
    }
}