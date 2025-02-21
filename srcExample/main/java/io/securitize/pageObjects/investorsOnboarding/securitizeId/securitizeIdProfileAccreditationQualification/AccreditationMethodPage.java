package io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification;

import io.securitize.infra.api.sumsub.SumsubUniqueImage;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.openqa.selenium.By;
import org.testng.Assert;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.info;

public class AccreditationMethodPage extends AbstractPage {

    private static final ExtendedBy updatesRequiredMsg = new ExtendedBy("Updated Required Msg", By.xpath("//div[@role='alert']/span[@class='pl-2']"));
    // INDIVIDUAL INVESTOR LOCATORS
    private static final ExtendedBy thirdPartyLetterCardRadioBtn = new ExtendedBy("Third Party Letter Accreditation Method Card - Radio Button", By.id("accreditationMethod-externalLetterUpload"));
    private static final ExtendedBy incomeCardRadioBtn = new ExtendedBy("Income Accreditation Method Card - Radio Button", By.id("accreditationMethod-income"));
    private static final ExtendedBy netWorthCardRadioBtn = new ExtendedBy("NetWorth Accreditation Method Card - Radio Button", By.id("accreditationMethod-netWorth"));
    private static final ExtendedBy professionalLicenceCardRadioBtn = new ExtendedBy("Professional License Accreditation Method Card - Radio Button", By.id("accreditationMethod-professional"));
    private static final ExtendedBy proofOfIncomeRadioBtn = new ExtendedBy("Proof of income - Radio Button", By.id("documentOrigin-personal"));
    private static final ExtendedBy licensedGeneralSecuritiesRepSeries7 = new ExtendedBy("Licensed General Securities Rep Series 7 - Radio Button", By.id("licenseType-finra-series-7"));
    private static final ExtendedBy crdNumberInputBox = new ExtendedBy("CRB Number - Input Box", By.id("crdNumber"));
    private static final ExtendedBy individualIncomeEarnedDropdown = new ExtendedBy("Individual Income Earned - Dropdown", By.xpath("//select[@id='incomeRange']"));
    // ENTITY INVESTOR LOCATORS
    private static final ExtendedBy assetsAndInvestmentsCardRadioBtn = new ExtendedBy("Assets & Investments Method Card - Radio Button", By.id("accreditationMethod-assetsAndInvestments"));
    private static final ExtendedBy proofOfAssetsRadioBtn = new ExtendedBy("Proof of Assets - Radio Button", By.id("documentOrigin-personal"));
    private static final ExtendedBy ownersOfEquitySecuritiesCardRadioBtn = new ExtendedBy("Owners Of Securities Method Card - Radio Button", By.id("accreditationMethod-equityOwners"));
    private static final ExtendedBy equityOwnerDocumentsRadioBtn = new ExtendedBy("Equity Owners Documents - Radio Button", By.id("documentOrigin-personal"));
    // ABSTRACT LOCATORS
    private static final ExtendedBy continueButton = new ExtendedBy("Accreditation Method Continue - Button", By.id("submitForm"));

    public AccreditationMethodPage(Browser browser) {
        super(browser, continueButton);
    }

    public String getUpdatesRequiredMsg() {
        return browser.findElement(updatesRequiredMsg).getText();
    }

    // INDIVIDUAL INVESTOR ACCREDITATION METHODS
    public AccreditationMethodPage clickThirdPartyVerificationLetter() {
        browser.click(thirdPartyLetterCardRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage clickIncomeAccreditationMethodCard() {
        browser.click(incomeCardRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage clickNetWorthAccreditationMethodCard() {
        browser.click(netWorthCardRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage clickProfessionalLicenceAccreditationMethodCard() {
        browser.click(professionalLicenceCardRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage selectLicensedGeneralSecuritiesRepSeries7RadioBtn() {
        browser.click(licensedGeneralSecuritiesRepSeries7, false);
        return this;
    }

    public AccreditationMethodPage selectProofOfIncomeRadioBtn() {
        browser.click(proofOfIncomeRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage selectProofOfAssetsAndCreditReportRadioBtn() {
        browser.click(proofOfIncomeRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage selectIndividualEarned() {
        browser.selectElementByVisibleText(individualIncomeEarnedDropdown, "Individually or Jointly 300k or more");
        return this;
    }

    public UploadAccreditationDocumentsPage clickContinue() {
        browser.click(continueButton, false);
        return new UploadAccreditationDocumentsPage(browser);
    }

    // ENTITY INVESTOR ACCREDITATION METHODS
    public AccreditationMethodPage clickAssetsAndInvestmentsAccreditationMethodCard() {
        browser.click(assetsAndInvestmentsCardRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage selectProofOfAssetsRadioBtn() {
        browser.click(proofOfAssetsRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage clickOwnersOfEquitySecuritiesMethodCard() {
        browser.click(ownersOfEquitySecuritiesCardRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage selectEquityOwnerDocumentsRadioBtn() {
        browser.click(proofOfAssetsRadioBtn, false);
        return this;
    }

    public AccreditationMethodPage enterCrdNumber() {
        String testCRNNumber = "1234567890";
        browser.typeTextElement(crdNumberInputBox, testCRNNumber);
        return this;
    }

    public void provideUpdates(String accreditationMethod) {
        if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.INCOME.name())) {
            provideUpdatesIncome();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.NET_WORTH.name())) {
            provideUpdatesNetWorth();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.IndividualAccreditationMethod.PROFESSIONAL_LICENSE.name())) {
            provideUpdatesProfessionalLicense();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.EntityAccreditationMethod.ASSETS_AND_INVESTMENTS.name())) {
            provideUpdatesAssetsAndInvestments();
        } else if (accreditationMethod.equalsIgnoreCase(AbstractAccreditation.EntityAccreditationMethod.OWNERS_OF_EQUITY_SECURITIES.name())) {
            provideUpdatesOwnersOfSecurities();
        }
        // TODO uncomment when fix is merged
        // AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        // accreditationStatusPage.clickOnUnderstood();
    }

    public void provideUpdatesIncome() {
        AccreditationMethodPage accreditationMethodPage = new AccreditationMethodPage(browser);
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = accreditationMethodPage
                .clickIncomeAccreditationMethodCard()
                .selectIndividualEarned().clickContinue();
        int imageIndex = SumsubUniqueImage.getDriverLicenseFrontIndex();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-front" + imageIndex + ".jpg");
        uploadAccreditationDocumentsPage.upLoadTaxYearDocumentsOne(frontImagePath);
        uploadAccreditationDocumentsPage.waitForFirstUploadIsCompleted();
        AccreditationReviewAndSubmitPage reviewAndSubmitPage = uploadAccreditationDocumentsPage
                .upLoadTaxYearDocumentsTwo(frontImagePath)
                .clickContinue();
        reviewAndSubmitPage.clickOnSubmit();
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        info("PROVIDE UPDATES FLOW Assert PROCESSING Accreditation Status");
        Assert.assertEquals(accreditationStatusPage.getAccreditationStatusBadge(), AbstractAccreditation.AccreditationStatus.PROCESSING.toString());
    }

    public void provideUpdatesNetWorth() {
        AccreditationMethodPage accreditationMethodPage = new AccreditationMethodPage(browser);
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

    public void provideUpdatesProfessionalLicense() {
        // TODO IMPLEMENT AFTER MERGE
    }

    public void provideUpdatesAssetsAndInvestments() {
        AccreditationMethodPage accreditationMethodPage = new AccreditationMethodPage(browser);
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = accreditationMethodPage
                .clickAssetsAndInvestmentsAccreditationMethodCard().clickContinue();
        endTestLevel();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "passport-front.jpg");
        AccreditationReviewAndSubmitPage reviewAndSubmitPage = uploadAccreditationDocumentsPage
                .uploadProofOfAssets(frontImagePath).clickContinue();
        endTestLevel();
        reviewAndSubmitPage.clickOnSubmit();
        endTestLevel();
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(browser);
        info("PROVIDE UPDATES FLOW Assert PROCESSING Accreditation Status");
        Assert.assertEquals(accreditationStatusPage.getAccreditationStatusBadge(), AbstractAccreditation.AccreditationStatus.PROCESSING.toString());
    }

    public void provideUpdatesOwnersOfSecurities() {
        // TODO IMPLEMENT AFTER MERGE
    }

}
