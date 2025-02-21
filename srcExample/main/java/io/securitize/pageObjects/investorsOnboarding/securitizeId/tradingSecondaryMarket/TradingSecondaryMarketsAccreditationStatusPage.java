package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.api.sumsub.SumsubUniqueImage;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationMethodPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationReviewAndSubmitPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.UploadAccreditationDocumentsPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsAccreditationStatusPage extends AbstractPage {

    private static final String STATUS_PROCESSING = "Processing";
    private static final String STATUS_VERIFIED = "Verified";

    private static final ExtendedBy startAccreditationButton = new ExtendedBy("Start Accreditation Button", By.xpath("//button[@name='start-accreditation']"));
    private static final ExtendedBy accreditationStatusBadge = new ExtendedBy("Accreditation Status Badge", By.xpath("//span[contains(@class, 'text-white')]"));

    // Documents Submitted Modal
    private static final ExtendedBy understoodButton = new ExtendedBy("Understood Button", By.xpath("//button[contains(text(), 'Understood')]"));


    public TradingSecondaryMarketsAccreditationStatusPage(Browser browser) {
        super(browser, startAccreditationButton);
    }

    public String getAccreditationStatusBadge() {
        return browser.getElementAttribute(accreditationStatusBadge, "innerText");
    }

    public AccreditationMethodPage startAccreditationButton() {
        browser.click(startAccreditationButton, false);
        return new AccreditationMethodPage(browser);
    }

    public void clickOnUnderstood() {
        browser.click(understoodButton, false);
    }

    public void completeIndividualInvestorAccreditation() {
        TradingSecondaryMarketsAccreditationStatusPage accreditationStatusPage = new TradingSecondaryMarketsAccreditationStatusPage(browser);
        AccreditationMethodPage accreditationMethodPage = accreditationStatusPage.startAccreditationButton();
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
        // TODO check this Accreditation Method implementation. Refactor to use unique accreditation method.
        // accreditationStatusPage.clickOnUnderstood();
    }

}
