package io.securitize.tests.securitizeId;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.*;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.pageObjects.controlPanel.cpIssuers.*;
import io.securitize.pageObjects.investorsOnboarding.nie.NieWelcomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.tests.abstractClass.AbstractSecIdInvestorRegistrationFlow;
import io.securitize.tests.InvestorDetails;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;
import java.time.Duration;
import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT29_SID_Sync_SecuritizeID_To_Issuer extends AbstractSecIdInvestorRegistrationFlow {

    @Test(description = "AUT29 - Sync Securitize Id to Issuer")
    public void AUT29_SID_Sync_SecuritizeID_To_Issuer_Test() {

        // we will conduct a non valid search to empty the table of investors
        // this will help us figure out when a later valid search finishes - the table will have at least
        // one row
        String nonValidSearch = "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        String issuerName = "Nie";
        boolean isEnvironmentProduction = MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production");

        startTestLevel("SecuritizeId create an account via API");
        InvestorDetails investorDetails = InvestorDetails.generateRandomNonUSInvestor();
        InvestorsAPI investorsAPI = new InvestorsAPI();
        investorsAPI.createNewSecuritizeIdInvestor(investorDetails);
        endTestLevel();


        startTestLevel("Login as new SecuritizeId investor");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen
                .clickAcceptCookies()
                .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        String sidCookie = (!isEnvironmentProduction) ?
                getBrowser().getCookie("_dev_securitize_id") :
                getBrowser().getCookie("_securitize_id_c");
        info("sidCookie: " + sidCookie);
        endTestLevel();


        startTestLevel("Login to issuer page");
        getBrowser().navigateTo(MainConfig.getInvestPageUrl(issuerName));
        NieWelcomePage welcomePage = new NieWelcomePage(getBrowser());
        welcomePage
                    .clickLogInWithoutAccountSelectorDirectAccess()
                    .clickAllowButton();
        endTestLevel();


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
        CpInvestorsList investorsList = sideMenu
                .clickSecuritizeId()
                .clickInvestors();
        endTestLevel();


        startTestLevel("Control panel -> Securitize ID -> Investors list");
        investorsList.searchInvestorByEmail(nonValidSearch);
        investorsList.waitForInvestorsTableToBeEmpty();
        investorsList.searchInvestorByEmail(investorDetails.getFirstName()); // to handle parallel runs where investor can't be found in the long list
        String currentIssuerName = investorsList.getInvestorDetailByName(investorDetails.getFirstName(), "Issuers");
        String issuerFullName = Users.getIssuerDetails(issuerName, IssuerDetails.issuerFullName);
        Assert.assertEquals(currentIssuerName, issuerFullName, "Issuer name specified in the investors table should match expected issuer name");
        endTestLevel();


        startTestLevel("Open issuer and specific investor details");
        CpSelectIssuer selectIssuer = sideMenu.clickIssuers();
        selectIssuer.clickViewIssuerByName(issuerFullName);
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        CpInvestorDetailsPage investorDetailsPage = onBoarding.clickShowInvestorDetailsByName(investorDetails.getFirstName());
        endTestLevel();


        startTestLevel("Validate details");
        SoftAssert softAssertion= new SoftAssert();
        softAssertion.assertEquals(investorDetailsPage.getFirstName(), investorDetails.getFirstName(), "incorrect first name, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getMiddleName(), investorDetails.getMiddleName(), "incorrect middle name, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getLastName(), investorDetails.getLastName(), "incorrect last name, this is not the value we set!");
        softAssertion.assertTrue(investorDetailsPage.getEmail().equalsIgnoreCase(investorDetails.getEmail()), "incorrect email, this is not the value we set!");
        String reformattedBirthDate = DateTimeUtils.convertDateFormat(investorDetails.getBirthDate(), "M/d/yyyy", "MMM d, yyyy");
        softAssertion.assertEquals(investorDetailsPage.getBirthday(), reformattedBirthDate, "incorrect birthdate, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getGender(), investorDetails.getGender(), "incorrect gender, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getInvestorType(), investorDetails.getInvestorType(), "incorrect investor type, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getAddress(), investorDetails.getStreetName(), "incorrect address, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getAddressAdditionalInfo(), investorDetails.getStreetAdditionalInfo(), "incorrect address additional info, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getPostalCode(), investorDetails.getPostalCode(), "incorrect postal code, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getCity(), investorDetails.getCity(), "incorrect city, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getCountry(), investorDetails.getCountry(), "incorrect country, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getTaxId(), investorDetails.getTaxId(), "incorrect taxId, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getTaxCountry(), investorDetails.getCountryOfTax(), "incorrect tax country, this is not the value we set!");
        softAssertion.assertEquals(investorDetailsPage.getCreationDate(), DateTimeUtils.currentDateFormat("MMM d, yyyy"), "incorrect creation date, this is not the value we set!");
        softAssertion.assertAll();
        endTestLevel();
    }
}