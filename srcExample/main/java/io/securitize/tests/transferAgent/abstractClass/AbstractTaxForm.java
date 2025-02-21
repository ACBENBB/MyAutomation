package io.securitize.tests.transferAgent.abstractClass;

import io.restassured.response.Response;
import io.securitize.infra.api.SnapshotAPI;
import io.securitize.infra.api.LoginAPI;
import io.securitize.infra.api.transferAgent.TaxFormAPI;
import io.securitize.infra.config.*;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpInvestorDetailsPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage2FA;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.DistributionDetailsPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.DistributionsListPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.distributions.GenerateDistributionModalPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification.taxForm.*;

import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.transferAgent.testData.DistributionData;
import org.testng.asserts.SoftAssert;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractTaxForm extends AbstractUiTest {

    TaxFormInvestorData investorData;

    public enum TaxFormAutoCompleteInvestorType {
        INDIVIDUAL_US("Individual US"),
        ENTITY_US("Entity US"),
        INDIVIDUAL_NONUS("Individual NON US"),
        ENTITY_NONUS("Entity NONUS"),
        ENTITY_NONUS_ACCEPT("Entity NONUS ACCEPT"),
        ENTITY_NONUS_REJECT("Entity NONUS REJECT");

        private final String displayName;
        TaxFormAutoCompleteInvestorType(String displayname) {
            this.displayName = displayname;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public void loginToSecIdWithInvestor(String user, String pass) {
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        securitizeIdLoginScreen.clickAcceptCookies()
                .performLoginWithCredentials(user, pass, true);
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard
                .clickSkipTwoFactor();
    }

    public void logoutFromSid() {
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard.performLogout();
    }

    public String createSnapshotViaApi(String issuer, String token, String snapshotName) {
        startTestLevel("Create snapshot for Distributions with current holders on token: " + token);
        LoginAPI loginAPI = new LoginAPI();
        Response cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        SnapshotAPI snapshotAPI = new SnapshotAPI();
        String createSnapshotStringResponse = snapshotAPI.createSnapshot(cpLoginResponse, issuer, token, snapshotName);
        endTestLevel();
        return createSnapshotStringResponse;
    }
    public void loginToSecIdWithInvestorType(String investorType) {
        if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_US.toString())) {
            loginToSecIdWithInvestor(
                Users.getProperty(UsersProperty.taxFormIndividualUSInvestorMail),
                Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_US.toString())) {
            loginToSecIdWithInvestor(
                Users.getProperty(UsersProperty.taxFormEntityUSInvestorMail),
                Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString())) {
            loginToSecIdWithInvestor(
                Users.getProperty(UsersProperty.taxFormIndividualNONUSInvestorMail),
                Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS.toString())) {
            loginToSecIdWithInvestor(
                    Users.getProperty(UsersProperty.taxFormEntityNONUSInvestorMail),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_ACCEPT.toString())) {
            loginToSecIdWithInvestor(
                    Users.getProperty(UsersProperty.ta_taxforms_ent_nonus_accept_Email_aut491),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_REJECT.toString())) {
            loginToSecIdWithInvestor(
                    Users.getProperty(UsersProperty.ta_taxforms_ent_nonus_reject_Email_aut492),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        }
    }

    public void navigateToTaxFormPage() {
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        // TODO validate Two Factor btn is present and click.
        if(false) {
            securitizeIdDashboard.clickSkipTwoFactor();
        }
        SecuritizeIdProfile securitizeIdProfile = securitizeIdDashboard.clickAccount();
        TaxCenterPage payoutInformationAndTaxCertificationPage =
                securitizeIdProfile.clickTaxCenterCard();
        payoutInformationAndTaxCertificationPage.clickTaxFormCard();
    }

    public void completeTaxForm(String investorType) {
        if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_US.toString())) {
            deleteExistingTaxForm(
                    Users.getProperty(UsersProperty.taxFormIndividualUSInvestorMail),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_US.toString())) {
            deleteExistingTaxForm(
                    Users.getProperty(UsersProperty.taxFormEntityUSInvestorMail),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString())) {
            deleteExistingTaxForm(
                    Users.getProperty(UsersProperty.taxFormIndividualNONUSInvestorMail),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS.toString())) {
            deleteExistingTaxForm(
                    Users.getProperty(UsersProperty.taxFormEntityNONUSInvestorMail),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_ACCEPT.toString())) {
            deleteExistingTaxForm(
                    Users.getProperty(UsersProperty.ta_taxforms_ent_nonus_accept_Email_aut491),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_REJECT.toString())) {
            info("Attempting to delete taxform via API...");
            deleteExistingTaxForm(
                    Users.getProperty(UsersProperty.ta_taxforms_ent_nonus_reject_Email_aut492),
                    Users.getProperty(UsersProperty.taInvestorGenericPass)
            );
        }
        completeResidenceDetailsPage(investorType);
        completeTaxFormByInvestorType(investorType);
    }

    public void completeResidenceDetailsPage(String investorType) {
        SoftAssert softAssert = new SoftAssert();
        TaxFormPage taxFormPage = new TaxFormPage(getBrowser());
        TaxFormResidenceDetailsPage taxFormResidenceDetailsPage = taxFormPage.clickCompleteATaxForm();
        if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_US.toString())) {
            softAssert.assertTrue(taxFormResidenceDetailsPage.isIamAUSPersonOrUSEntityCheckboxSelected());
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_US.toString())) {
            softAssert.assertTrue(taxFormResidenceDetailsPage.isIamAUSPersonOrUSEntityCheckboxSelected());
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString())) {
            softAssert.assertTrue(taxFormResidenceDetailsPage.isIamNotAUSPersonOrUSEntityCheckboxSelected());
            taxFormResidenceDetailsPage.selectIamAnIndividualCheckBox();
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS.toString()) ||
                investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_ACCEPT.toString()) ||
                investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_REJECT.toString()) ) {
            softAssert.assertTrue(taxFormResidenceDetailsPage.isIamNotAUSPersonOrUSEntityCheckboxSelected());
        }
        softAssert.assertAll();
        taxFormResidenceDetailsPage.clickContinue();
    }

    public void completeTaxFormByInvestorType(String investorType) {
        if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_US.toString())) {
            completeW9TaxFormStepOne(investorData);
            completeW9TaxFormStepTwo(investorData);
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_US.toString())) {
            completeW9TaxFormStepOne(investorData);
            completeW9TaxFormStepTwo(investorData);
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString())) {
            completeW8BENTaxFormStepOne(investorData, TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString());
            completeW8BENTaxFormStepTwo(investorData);
            completeW8BENTaxFormStepThree(investorData);
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS.toString()) ||
                investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_ACCEPT.toString()) ||
                investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_REJECT.toString()) ) {
            completeW8BENETaxFormStepOne(investorData);
        }
    }

    public void completeW9TaxFormStepOne(TaxFormInvestorData investorData) {
        SoftAssert softAssert = new SoftAssert();
        TaxFormW9FirstPage taxFormW9Page = new TaxFormW9FirstPage(getBrowser());
        if (investorData.investorType.equalsIgnoreCase("Individual")) {
            softAssert.assertEquals(taxFormW9Page.getIndividualNameInputAutoComplete(), investorData.firstName + " " + investorData.midName + " " + investorData.lastName);
            softAssert.assertEquals(taxFormW9Page.getStreetNameAutoComplete(), investorData.streetName);
            softAssert.assertEquals(taxFormW9Page.getCityAutoComplete(), investorData.city);
            softAssert.assertEquals(taxFormW9Page.getPostalCodeAutoComplete(), investorData.postalCode);
            softAssert.assertEquals(taxFormW9Page.getStateRegionAutoComplete(), investorData.stateRegion);
            softAssert.assertEquals(taxFormW9Page.getFederalTaxClassification(), investorData.federalTaxClass);
            softAssert.assertEquals(taxFormW9Page.getTaxIdentifierTypeAutoComplete(), investorData.taxIdentifierType);
            taxFormW9Page.validateTaxIdFormat(taxFormW9Page.geTaxPayerIdNumber(), investorData.taxPayerId);
            taxFormW9Page.clickContinueBtn();
        } else {
            taxFormW9Page.setEntityName(investorData.entityName);
            softAssert.assertEquals(taxFormW9Page.getEntityBusinessName(), investorData.entityBusinessName);
            softAssert.assertEquals(taxFormW9Page.getCityAutoComplete(), investorData.city);
            softAssert.assertEquals(taxFormW9Page.getPostalCodeAutoComplete(), investorData.postalCode);
            softAssert.assertEquals(taxFormW9Page.getStateRegionAutoComplete(), investorData.stateRegion);
            taxFormW9Page.setEntityFederalTaxClassification(investorData.entityFederalTaxClass);
            taxFormW9Page.setEntityTaxIdentifierType(investorData.taxIdentifierType);
            taxFormW9Page.validateTaxIdFormat(taxFormW9Page.geTaxPayerIdNumber(), investorData.taxPayerId);
            taxFormW9Page.clickContinueBtn();
        }
        softAssert.assertAll();
    }

    public void completeW9TaxFormStepTwo(TaxFormInvestorData investorData) {
        browser.get().waitForPageStable();
        SoftAssert softAssert = new SoftAssert();
        TaxFormW9SeccondPage taxFormW9SeccondPage = new TaxFormW9SeccondPage(getBrowser());
        if (investorData.investorType.equalsIgnoreCase("Individual")) {
            softAssert.assertEquals(taxFormW9SeccondPage.getNameText(), investorData.firstName + " " + investorData.midName + " " + investorData.lastName);
            softAssert.assertEquals(taxFormW9SeccondPage.getFederalTaxClassificationText(), investorData.federalTaxClass);
        } else if (investorData.investorType.equalsIgnoreCase("Entity")) {
            softAssert.assertEquals(taxFormW9SeccondPage.getNameText(), investorData.entityName);
            softAssert.assertEquals(taxFormW9SeccondPage.getFederalTaxClassificationText(), investorData.entityFederalTaxClass);
        }

        softAssert.assertTrue(investorData.country.contains(taxFormW9SeccondPage.getCountryText()));
        softAssert.assertEquals(taxFormW9SeccondPage.getAddressText(), investorData.streetName, "Street Name match failed");
        softAssert.assertEquals(taxFormW9SeccondPage.getStateRegionText(), investorData.stateRegionShort, "State Region Short match failed");
        softAssert.assertEquals(taxFormW9SeccondPage.getCityText(), investorData.city, "City match failed");
        softAssert.assertEquals(taxFormW9SeccondPage.getZipText(), investorData.postalCode);
        taxFormW9SeccondPage.validateTaxIdFormat(taxFormW9SeccondPage.getSsnText(), investorData.taxPayerId);
        taxFormW9SeccondPage.clickCertificationCheckbox();
        taxFormW9SeccondPage.setSignatureInputBox(taxFormW9SeccondPage.getSignatureInputText());
        softAssert.assertEquals(taxFormW9SeccondPage.getDateInputBoxText(), DateTimeUtils.currentDate("d MMMM yyyy"));
        taxFormW9SeccondPage.clickSubmit();
        softAssert.assertAll();
    }

    public void completeW8BENTaxFormStepOne(TaxFormInvestorData investorData, String investorType) {
        SoftAssert softAssert = new SoftAssert();
        TaxFormW8BENFirstPage taxFormW8BENFirstPage = new TaxFormW8BENFirstPage(getBrowser());
        if (investorData.investorType.equalsIgnoreCase("Individual")) {
            softAssert.assertEquals(taxFormW8BENFirstPage.getNameInputAutoComplete(), investorData.firstName + " " + investorData.midName + " " + investorData.lastName);
            softAssert.assertEquals(taxFormW8BENFirstPage.getCountryOfCitizenship(), investorData.country);
        } else if (investorData.investorType.equalsIgnoreCase("Entity") && !investorType.contains("NONUS")) {
            softAssert.assertEquals(taxFormW8BENFirstPage.getNameText(), investorData.entityName);
            softAssert.assertEquals(taxFormW8BENFirstPage.getFederalTaxClassificationText(), investorData.entityFederalTaxClass);
        }
        softAssert.assertEquals(taxFormW8BENFirstPage.getStreetAddressAutoComplete(), investorData.streetName);
        softAssert.assertEquals(taxFormW8BENFirstPage.getCityAutoComplete(), investorData.city);
        softAssert.assertEquals(taxFormW8BENFirstPage.getPostalCodeAutoComplete(), investorData.postalCode);
        taxFormW8BENFirstPage.setStateRegion(investorData.stateRegionNonUs);
        softAssert.assertEquals(taxFormW8BENFirstPage.getAddressCountryAutoComplete(), investorData.country);
        softAssert.assertTrue(taxFormW8BENFirstPage.isSameAsPermanentAddressCheckboxSelected());
        softAssert.assertEquals(taxFormW8BENFirstPage.getMailingStreetAddressAutoComplete(), investorData.streetName);
        softAssert.assertEquals(taxFormW8BENFirstPage.getMailingCityAutoComplete(), investorData.city);
        softAssert.assertEquals(taxFormW8BENFirstPage.getMailingPostalCodeAutoComplete(), investorData.postalCode);
        softAssert.assertEquals(taxFormW8BENFirstPage.getMailingStateRegion(), investorData.stateRegionNonUs);
        softAssert.assertEquals(taxFormW8BENFirstPage.getMailingCountryAutoComplete(), investorData.country);
        taxFormW8BENFirstPage.validateTaxIdFormat(taxFormW8BENFirstPage.getForeignIdentifiyingNumber(), investorData.taxPayerId);
        taxFormW8BENFirstPage.clickContinueBtn();
        softAssert.assertAll();
    }

    public void completeW8BENTaxFormStepTwo(TaxFormInvestorData investorData) {
        TaxFormW8BENSeccondPage taxFormW8BENSeccondPage = new TaxFormW8BENSeccondPage(getBrowser());
        taxFormW8BENSeccondPage.clickCertifyBeneficialOwnerCheckBox();
        taxFormW8BENSeccondPage.clickSubmit();
        taxFormW8BENSeccondPage.checkCertificationAgreement();
        taxFormW8BENSeccondPage.setSignatureInputBox(taxFormW8BENSeccondPage.getSignatureInputText());
        taxFormW8BENSeccondPage.clickSubmit();
    }

    public void completeW8BENTaxFormStepThree(TaxFormInvestorData investorData) {

    }

    public void completeW8BENETaxFormStepOne(TaxFormInvestorData investorData) {
        TaxFormW8BENEPage taxFormW8BENEPage = new TaxFormW8BENEPage(getBrowser());
        taxFormW8BENEPage.clickOnDownloadTemplateAndSave();
        taxFormW8BENEPage.clickOnUploadAndUploadTaxForm();
        taxFormW8BENEPage.submit();
    }

    public void getInvestorDataFromInvestorType(String investorType) {
        // TODO create API request to get this data.
        investorData = new TaxFormInvestorData();
        if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_US.toString())) {
            investorData = getInvestorDataFromInvestorSecId(Users.getProperty(UsersProperty.taxFormIndividualUSInvestorSecID));
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_US.toString())) {
            investorData = getInvestorDataFromInvestorSecId(Users.getProperty(UsersProperty.taxFormEntityUSInvestorSecID));
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString())) {
            investorData = getInvestorDataFromInvestorSecId(Users.getProperty(UsersProperty.taxFormIndividualNONUSInvestorSecID));
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS.toString())) {
            investorData = getInvestorDataFromInvestorSecId(Users.getProperty(UsersProperty.taxFormEntityNONUSInvestorSecID));
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_ACCEPT.toString())) {
            investorData = getInvestorDataFromInvestorSecId(Users.getProperty(UsersProperty.ta_taxforms_ent_nonus_accept_profileId_aut491));
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_REJECT.toString())) {
            investorData = getInvestorDataFromInvestorSecId(Users.getProperty(UsersProperty.ta_taxforms_ent_nonus_reject_profileId_aut492));
        }
    }

    public void loginToControlPanel() {
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl));
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
    }

    public TaxFormInvestorData getInvestorDataFromInvestorSecId(String investorSecId) {
        String secIdCurrentUrl = getBrowser().getCurrentUrl();
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.cpUrl));
        CpLoginPage loginPage = new CpLoginPage(getBrowser());
        CpLoginPage2FA loginPage2Fa = loginPage.loginCpUsingEmailPassword();
        loginPage2Fa.obtainPrivateKey()
                .generate2FACode()
                .setTwoFaCodeInUI();
        String cpSecIdInvestorPageUrl = MainConfig.getProperty(MainConfigProperty.cpUrl) + "securitize-id/" + investorSecId;
        getBrowser().navigateTo(cpSecIdInvestorPageUrl);
        CpInvestorDetailsPage investorDetailsPage = new CpInvestorDetailsPage(getBrowser());
        TaxFormInvestorData investorData = new TaxFormInvestorData();
        investorData.investorType = investorDetailsPage.getInvestorTypeSecId();
        if (investorData.investorType.equalsIgnoreCase("Individual")) {
            investorData.country = investorDetailsPage.getCountry();
            if (investorData.country.contains("United States")) {
                investorData.stateRegion = investorDetailsPage.getState();
            }
            investorData.firstName = investorDetailsPage.getFirstNameSecId();
            investorData.midName = investorDetailsPage.getMiddleNameSecId();
            investorData.lastName = investorDetailsPage.getLastNameSecId();
            investorData.streetName = investorDetailsPage.getAddress();
            investorData.postalCode = investorDetailsPage.getPostalCode();
            investorData.city = investorDetailsPage.getCity();
            investorData.taxPayerId = investorDetailsPage.getTaxId();
            investorDetailsPage.clickEditKYC().setCurrentKycStatus("Verified").clickSaveChanges();
            getBrowser().navigateTo(secIdCurrentUrl);
            return investorData;
        } else {
            investorData.entityBusinessName = investorDetailsPage.getEntityNameSecId();
            investorData.streetName = investorDetailsPage.getAddress();
            investorData.country = investorDetailsPage.getCountry();
            if (investorData.country.contains("United States")) {
                investorData.stateRegion = investorDetailsPage.getState();
            }
            investorData.city = investorDetailsPage.getCity();
            investorData.postalCode = investorDetailsPage.getPostalCode();
            investorData.taxPayerId = investorDetailsPage.getTaxId();
            investorDetailsPage.clickEditKYB().setCurrentKycStatus("Verified").clickSaveChanges();
            getBrowser().navigateTo(secIdCurrentUrl);
            return investorData;
        }

    }

    public TaxFormPage deleteExistingTaxForm(String email, String password) {
        TaxFormAPI taxFormAPI = new TaxFormAPI();
        LoginAPI loginAPI = new LoginAPI();
        Response response = loginAPI.loginSecIdAndReturnBearerToken(email, password);
        taxFormAPI.deleteTaxForm(response);
        getBrowser().refreshPage();
        TaxFormPage taxFormPage = new TaxFormPage(getBrowser());
        if(!taxFormPage.isCompleteATaxFormBtnEnabled()) {
            TaxFormDeleteModalPage deleteModalPage = taxFormPage.clickDeleteTaxForm();
            return deleteModalPage.clickDeleteConfirmBtn();
        }
        return new TaxFormPage(getBrowser());
    }

    public void validateSubmittedTaxForm(String investorType) {
        SoftAssert softAssert = new SoftAssert();
        TaxFormPage taxFormPage = new TaxFormPage(getBrowser());
         if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_US.toString())) {
             softAssert.assertEquals(taxFormPage.getSubmittedTaxFormName(), "Form W-9");
             softAssert.assertEquals(taxFormPage.getSubmittedTaxFormStatus(), "VALID");
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_US.toString())) {
             softAssert.assertEquals(taxFormPage.getSubmittedTaxFormName(), "Form W-9");
             softAssert.assertEquals(taxFormPage.getSubmittedTaxFormStatus(), "VALID");
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString())) {
             softAssert.assertEquals(taxFormPage.getSubmittedTaxFormName(), "Form W-8BEN");
             softAssert.assertEquals(taxFormPage.getSubmittedTaxFormStatus(), "VALID");
        }  else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS.toString()) ||
                 investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_ACCEPT.toString()) ||
                 investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_REJECT.toString()) ) {
             softAssert.assertEquals(taxFormPage.getSubmittedTaxFormName(), "Form W-8BENE");
             softAssert.assertEquals(taxFormPage.getSubmittedTaxFormStatus(), "PENDING");
         }

        softAssert.assertAll();

    }

    public void validateSubmittedTaxForm(String investorType, String status) {
        SoftAssert softAssert = new SoftAssert();
        TaxFormPage taxFormPage = new TaxFormPage(getBrowser());
        if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_US.toString())) {
            softAssert.assertEquals(taxFormPage.getSubmittedTaxFormName(), "Form W-9");
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_US.toString())) {
            softAssert.assertEquals(taxFormPage.getSubmittedTaxFormName(), "Form W-9");
        } else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.INDIVIDUAL_NONUS.toString())) {
            softAssert.assertEquals(taxFormPage.getSubmittedTaxFormName(), "Form W-8BEN");
        }  else if(investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS.toString()) ||
                investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_ACCEPT.toString()) ||
                investorType.equalsIgnoreCase(TaxFormAutoCompleteInvestorType.ENTITY_NONUS_REJECT.toString()) ) {
            softAssert.assertEquals(taxFormPage.getSubmittedTaxFormName(), "Form W-8BENE");
        }
        softAssert.assertEquals(taxFormPage.getSubmittedTaxFormStatus(), status);

        softAssert.assertAll();

    }

    protected double getCashBalanceFromSidUI() {
        SecuritizeIdPortfolio portfolio = new SecuritizeIdPortfolio(getBrowser());
        return portfolio.getCashAccountBalance();
    }

    protected void submitTaxform(String investorType) {
        navigateToTaxFormPage();
        getBrowser().refreshPage();
        completeTaxForm(investorType);
    }

    public void goToControlPanel() {
        String url = MainConfig.getProperty(MainConfigProperty.cpUrl);
        getBrowser().navigateTo(url);
    }

    public void navigateToCpDistributions(DistributionData distributionData) {
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        getBrowser().navigateTo(
                "https://cp." + environment + ".securitize.io/" +
                        distributionData.issuerId + "/" +
                        distributionData.tokenId + "/distributions");
    }

    public void generateDistribution(DistributionData distributionData) {
        DistributionsListPage distributionsListPage = new DistributionsListPage(getBrowser());
        getBrowser().waitForElementVisibility(distributionsListPage.getDistributionsRows());
        if (distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND.toString()) ||
                distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_REJECT.toString()) ||
                distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_ACCEPT.toString()) ||
                distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_REDEMPTION.toString())
        ){
            startTestLevel("Generating Distribution Type: " + distributionData.distributionTestType);
            distributionsListPage.clickGenerateDistributionBtn();
            GenerateDistributionModalPage generateDistributionModalPage = new GenerateDistributionModalPage(getBrowser());
            generateDistributionModalPage.generateDistribution(distributionData);
            endTestLevel();
        }
    }

    public void selectDistributionByName(DistributionData distributionData) {
        DistributionsListPage distributionsListPage = new DistributionsListPage(getBrowser());
        getBrowser().waitForPageStable();
        getBrowser().refreshPage();
        getBrowser().waitForElementVisibility(distributionsListPage.getDistributionsRows());
        for (int j = 0; j < 3; j++) {
            if (distributionsListPage.getDistributionColumnDataByRow("distributionName", String.valueOf(j+1)).contains(distributionData.distributionName)) {
                distributionsListPage.clickDistributionByRow(String.valueOf(j));
                break;
            }
        }
    }

    public void confirmDistribution() {
        DistributionDetailsPage distributionPage = new DistributionDetailsPage(getBrowser());
        distributionPage.clickConfirmDristributionBtn();
        distributionPage.clickConfirmDistributionModalBtn();
    }

    public void setDistributionTaxFormStatusByMail(String investorMail, String status) {
        searchDistributionInvestorByMail(investorMail);
        setTaxFormStatusTo(status);
    }

    public void setTaxFormStatusTo(String taxFormStatus) {
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        distributionDetailsPage.setTaxFormStatusTo(taxFormStatus);
    }

    public void waitForTaxFormStatusBe(String investorMail, String taxFormStatus) {
        searchDistributionInvestorByMail(investorMail);
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        distributionDetailsPage.waitForTaxFormStatus(taxFormStatus);
    }

    public void searchDistributionInvestorByMail(String investorMail){
        DistributionDetailsPage distributionDetailsPage = new DistributionDetailsPage(getBrowser());
        distributionDetailsPage.searchDistributionInvestor(investorMail);
    }

    public double getDistributionDetailsInvestorTokens(String investorEmail) {
        // this method works only when there is ONE RESULT in the Distribution Details Page investors search.
        DistributionDetailsPage distributionsDetailsPage = new DistributionDetailsPage(getBrowser());
        return Double.parseDouble(distributionsDetailsPage.getDijstributionDetailsPageTokensByInvestorMail(investorEmail));
    }

    public double calculateDividendPayment(double initialInvestorTokens, String amountPerToken ) {
        return (initialInvestorTokens - ((initialInvestorTokens/100.00)*30.00))*Double.parseDouble(amountPerToken);
    }

}