package io.securitize.tests.fundraise;

import io.securitize.infra.api.*;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.pageObjects.investorsOnboarding.nie.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.*;
import io.securitize.pageObjects.investorsOnboarding.investWizard.*;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.testng.*;
import org.testng.annotations.Test;
import org.testng.asserts.*;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT274_FT_PrimaryOffering_AccreditationFirst extends AbstractSecIdNieSharedFlow {

    @Test(description = "AUT274 - Primary Offerings - Investment flow for an investor from a country configured as accreditation first")
    public void AUT274_FT_PrimaryOffering_AccreditationFirst() throws Exception {

        startTestLevel("Get Test Data From AWS");
            String issuerId = Users.getProperty(UsersProperty.ft_issuerId_aut274);
            String tokenId = Users.getProperty(UsersProperty.ft_tokenId_aut274);
        endTestLevel();

        startTestLevel("Create investor data object");
            String issuerName = "Nie";
            String investorCountry = "Greenland"; // Dario: This Country Jurisdiction needs to have Accreditation First configured in CP
            String investmentName = Users.getIssuerDetails(issuerName, IssuerDetails.investmentMarketName);
            InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();
            investorDetails.setCountry(investorCountry);
            investorDetails.setPhoneNumber("221224"); // Dario: Phone number for the country, required when doing KYC
        endTestLevel();

        startTestLevel("Create investor via API");
            InvestorsAPI investorsAPI = new InvestorsAPI();
            String investorSecId = investorsAPI.createNewSecuritizeIdInvestor(investorDetails, false);
        endTestLevel();

        startTestLevel("Login To SiD with Created Accreditation First Investor");
            getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
            SecuritizeIdInvestorLoginScreen securitizeIdInvestorLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
            securitizeIdInvestorLoginScreen
                    .clickAcceptCookies()
                    .performLoginWithCredentials(investorDetails.getEmail(), investorDetails.getPassword(), false);
        endTestLevel();

        startTestLevel("Navigate To Primary Offerings");
            SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
            securitizeIdDashboard
                    .clickSkipTwoFactor()
                    .primaryOfferingClick();
        endTestLevel();

        startTestLevel("Click Im Interested Btn in the Request Additional Information PopUp");
            PrimaryOfferingRequestAdditionalInformationPopUp requestAdditionalInformationPopUp = new PrimaryOfferingRequestAdditionalInformationPopUp(getBrowser());
            requestAdditionalInformationPopUp.clickImInterestedBtn();
        endTestLevel();

        startTestLevel(" Click on Primary Offerings Opportunity");
            PrimaryOfferingMarketPage marketPage = new PrimaryOfferingMarketPage(getBrowser());
            marketPage.clickOnPrimaryOfferingsCardOpp(investmentName);
        endTestLevel();

        startTestLevel(" Validate Provide Further Information PopUp and click continue");
            ProvideFurtherInformationPopUp provideFurtherInformationPopUp = new ProvideFurtherInformationPopUp(getBrowser());
            SoftAssert furtherInformationPopUpSoftAsssert = new SoftAssert();
            furtherInformationPopUpSoftAsssert.assertEquals(provideFurtherInformationPopUp.getPopUpTitleText(), "Please provide further information");
            furtherInformationPopUpSoftAsssert.assertEquals(provideFurtherInformationPopUp.getPopUpBodyText(), "In order to invest, we need you to provide more information about you.");
            furtherInformationPopUpSoftAsssert.assertAll();
            provideFurtherInformationPopUp.clickOnContinue();
        endTestLevel();

        startTestLevel(" Accreditation First, Qualification Form - Click NO, validate PopUp and goBack");
            InvestWizard_QualificationPage accreditationFirstQualificationPage = new InvestWizard_QualificationPage(getBrowser());
            accreditationFirstQualificationPage.clickNoIAmRetailInvestor();
            QualificationRequiredPopUp accreditationFirstQualificationRequiredPopUp = new QualificationRequiredPopUp(getBrowser());
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(accreditationFirstQualificationRequiredPopUp.isPopUpTitleVisible());
            softAssert.assertTrue(accreditationFirstQualificationRequiredPopUp.isPopUpTextVisible());
            accreditationFirstQualificationRequiredPopUp.clickGoBackBtn();
        endTestLevel();

        startTestLevel(" Accreditation First, Qualification Form - validate PopUp and click YES");
            accreditationFirstQualificationPage.clickConsiderMyselfAProfessionalInvestor();
        endTestLevel();

        startTestLevel("Select Currency, enter amount and click Invest");
            NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
            nieInvestmentDetails.setInvestmentCurrency(CurrencyIds.USD).setInvestmentAmount(100000);
            nieInvestmentDetails.clickInvest();
        endTestLevel();

        startTestLevel("Click on Complete Your Investor Profile PopUp");
            PrimaryOfferingSuitabiltyFormPopup primaryOfferingSuitabiltyFormPopup = new PrimaryOfferingSuitabiltyFormPopup(getBrowser());
            primaryOfferingSuitabiltyFormPopup.clickCompleteInvestorProfileBtn();
        endTestLevel();

        startTestLevel(" Get And Assert CP -> Onboarding -> Accreditation Status");
            IssuersAPI issuersAPI = new IssuersAPI();
            String investorCpAccreditationStatus = issuersAPI.getCpOnboardingAccreditationStatus(issuerId, tokenId, investorDetails.getEmail());
            Assert.assertEquals(investorCpAccreditationStatus, "confirmed");
            // TODO this assertion should be done in multiple tokens, as the Qualification status should change in all issuer tokens.
        endTestLevel();

        startTestLevel("Invest Wizard - Fill Investor Information Form aka Markets Suitability");
            InvestWizard_InvestorInformationPage primaryOffering_investWizzard_investorInformationForm =
                    new InvestWizard_InvestorInformationPage(getBrowser());
            primaryOffering_investWizzard_investorInformationForm.fillInvestorInformationForm();
        endTestLevel();

        startTestLevel("Investor Wizard - Investor Information - Click Continue");
        primaryOffering_investWizzard_investorInformationForm.clickContinueBtn();
        endTestLevel();

        startTestLevel("Investor Wizard - Submit Form - Enter full Name and Click Submit");
            InvestWizard_SubmitFormPage primaryOffering_investWizard_submitForm =
                    new InvestWizard_SubmitFormPage(getBrowser());
            primaryOffering_investWizard_submitForm.typeFullName();
            primaryOffering_investWizard_submitForm.clickSubmitBtn();
        endTestLevel();

        // TODO this step will be removed in hte next sprint Jan/31
        // TODO Assert that the Qualification step is being skipped, since we've filled Qualification before as Accreditation First.
        startTestLevel("Qualification step should be skipped since it was completed before entering the opportunity");
//            Assert.assertFalse(accreditationFirstQualificationPage.isQualificationStepDisplayed());
        endTestLevel();

    }
}