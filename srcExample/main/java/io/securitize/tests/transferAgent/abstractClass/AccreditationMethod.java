package io.securitize.tests.transferAgent.abstractClass;

import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationMethodPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationReviewAndSubmitPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationStatusPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.UploadAccreditationDocumentsPage;

public class AccreditationMethod extends AbstractPage {

    public AccreditationMethod(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    public void completeThirdPartyAccreditationLetter() {
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        AccreditationMethodPage accreditationMethodPage = accreditationStatusPage.startAccreditationButton();
        accreditationMethodPage.clickThirdPartyVerificationLetter().clickContinue();
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = new UploadAccreditationDocumentsPage(browser);
        uploadAccreditationDocumentsPage.chooseRepresentativeType("Licensed Attorney").setLicenseOrRegistrationNumber("123456");
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-front.jpg");
        uploadAccreditationDocumentsPage.uploadVerificationLetter(frontImagePath);
        uploadAccreditationDocumentsPage.clickContinue();
        AccreditationReviewAndSubmitPage reviewAndSubmitPage = new AccreditationReviewAndSubmitPage(browser);
        reviewAndSubmitPage.clickOnSubmit();
    }

    public void individualAccreditationIncome() {
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        AccreditationMethodPage accreditationMethodPage = accreditationStatusPage.startAccreditationButton();
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = accreditationMethodPage
                .clickIncomeAccreditationMethodCard()
                .selectIndividualEarned().clickContinue();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-front.jpg");
        uploadAccreditationDocumentsPage.upLoadTaxYearDocumentsOne(frontImagePath);
        uploadAccreditationDocumentsPage.waitForFirstUploadIsCompleted();
        AccreditationReviewAndSubmitPage reviewAndSubmitPage = uploadAccreditationDocumentsPage
                .upLoadTaxYearDocumentsTwo(frontImagePath)
                .clickContinue();
        reviewAndSubmitPage.clickOnSubmit();
    }

    public void individualAccreditationSanityProd() {
        // this is the flow that we use for PROD, note that it doesn't submit the Accreditation
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        AccreditationMethodPage accreditationMethodPage = accreditationStatusPage.startAccreditationButton();
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = accreditationMethodPage
                .clickIncomeAccreditationMethodCard()
                .selectIndividualEarned().clickContinue();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-front.jpg");
        uploadAccreditationDocumentsPage.upLoadTaxYearDocumentsOne(frontImagePath);
        uploadAccreditationDocumentsPage.waitForFirstUploadIsCompleted();
        uploadAccreditationDocumentsPage.upLoadTaxYearDocumentsTwo(frontImagePath).clickContinue();
    }

    public void individualAccreditationNetWorth() {
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        AccreditationMethodPage accreditationMethodPage = accreditationStatusPage.startAccreditationButton();
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = accreditationMethodPage
                .clickNetWorthAccreditationMethodCard()
                .clickContinue();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-front.jpg");
        uploadAccreditationDocumentsPage.individualUploadProofOfAssets(frontImagePath);
        uploadAccreditationDocumentsPage.waitForNetWorthUploadCompleted();
        AccreditationReviewAndSubmitPage reviewAndSubmitPage = uploadAccreditationDocumentsPage
                .individualUploadCreditReport(frontImagePath)
                .clickContinue();
        reviewAndSubmitPage.clickOnSubmit();
    }

    public void individualAccreditationProfessionalLicense() {
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        AccreditationMethodPage accreditationMethodPage = accreditationStatusPage.startAccreditationButton();
        accreditationMethodPage
                .clickProfessionalLicenceAccreditationMethodCard()
                .selectLicensedGeneralSecuritiesRepSeries7RadioBtn()
                .enterCrdNumber()
                .clickContinue();
        AccreditationReviewAndSubmitPage reviewAndSubmitPage = new AccreditationReviewAndSubmitPage(browser);
        reviewAndSubmitPage.clickOnSubmit();
    }

    public void individualAccreditationThirdParty() {
        completeThirdPartyAccreditationLetter();
    }

    public void entityAccreditationAssetsAndInvestments() {
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        AccreditationMethodPage accreditationMethodPage = accreditationStatusPage.startAccreditationButton();
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = accreditationMethodPage
                .clickAssetsAndInvestmentsAccreditationMethodCard().clickContinue();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "passport-front.jpg");
        AccreditationReviewAndSubmitPage reviewAndSubmitPage = uploadAccreditationDocumentsPage
                .uploadProofOfAssets(frontImagePath).clickContinue();
        reviewAndSubmitPage.clickOnSubmit();
    }

    public void entityAccreditationOwnersOfEquitySecurities() {
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        AccreditationMethodPage accreditationMethodPage = accreditationStatusPage.startAccreditationButton();
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = accreditationMethodPage
                .clickOwnersOfEquitySecuritiesMethodCard()
                .clickContinue();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "passport-front.jpg");
        AccreditationReviewAndSubmitPage reviewAndSubmitPage = uploadAccreditationDocumentsPage
                .uploadEquityOwnerDocuments(frontImagePath).clickContinue();
        reviewAndSubmitPage.clickOnSubmit();
    }

    public void entityAccreditationThirdParty() {
        completeThirdPartyAccreditationLetter();
    }

}