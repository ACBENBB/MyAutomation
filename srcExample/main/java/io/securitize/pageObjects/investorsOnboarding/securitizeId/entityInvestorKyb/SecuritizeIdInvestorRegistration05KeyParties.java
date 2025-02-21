package io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import io.securitize.pageObjects.legacyOLD.investmentWizard.InvestorType;
import io.securitize.pageObjects.investorsOnboarding.nie.NieDashboard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SecuritizeIdInvestorRegistration05KeyParties extends SecuritizeIdInvestorAbstractPage {

    private static final ExtendedBy drivingLicenseImage = new ExtendedBy("Driving license image", By.xpath("//h6[contains(text(), 'Driving licence')]"));
    private static final ExtendedBy textHeader = new ExtendedBy("Text: 'Does this look ok?' or 'Review Information'", By.xpath("//*[text()='Does this look ok?'] | //*[text()='Review Information']"));
    private static final ExtendedBy documentsImages = new ExtendedBy("Documents images", By.xpath("//div[contains(@class, 'box-shadow document')]//a/img"));
    private static final ExtendedBy continueButton = new ExtendedBy("Securitize Id - 08 - continue button", By.id("wizard-continue"));
    private static final ExtendedBy submitDocumentsForReviewBtn = new ExtendedBy("Submit Documents for Review - Button", By.xpath("//*[text()='Submit Documents for Review']"));
    private static final ExtendedBy goBackButton = new ExtendedBy("Securitize Id - 08 - Go back button", By.id("wizard-go-back"));

    public SecuritizeIdInvestorRegistration05KeyParties(Browser browser) {
        super(browser, textHeader);
    }

    @SuppressWarnings("unused")
    public String getDocumentImageSourceByIndex(int index) {
        List<WebElement> images = browser.findElements(documentsImages);
        return images.get(index).getAttribute("src");
    }

    public void clickSubmitDocumentsForReviewBtn() {
        browser.click(submitDocumentsForReviewBtn, false);
    }

    public InvestorType clickContinue() {
        browser.click(continueButton, false); // submit documents for review
        browser.click(continueButton, false); // go to dashboard

        return new InvestorType(browser);
    }

    public NieDashboard clickContinueBackToNieDashboard() {
        browser.click(continueButton, false);
        new NieDashboard(browser);
        return null;
    }

    public NieDashboard clickSubmitDocumentsForReview() {
        browser.click(continueButton, false);
        return new NieDashboard(browser);
    }

    public boolean isDocumentImageLoaded(int index) {
        List<WebElement> images = browser.findElements(documentsImages);
        WebElement element = images.get(index);

        // in rare cases the image is loaded but the attribute isn't ready yet.. Added smart wait
        browser.waitForElementToHaveAttribute(element,  "document image #" + index, "complete");
        browser.waitForElementToHaveAttribute(element, "document image #" + index,"naturalWidth");

        return element.getAttribute("complete").equals("true")
                && !element.getAttribute("naturalWidth").equals("undefined")
                && !element.getAttribute("naturalWidth").equals("0");
    }

    public void clickGoBackButton() {
        browser.click(goBackButton, false);
    }
}
