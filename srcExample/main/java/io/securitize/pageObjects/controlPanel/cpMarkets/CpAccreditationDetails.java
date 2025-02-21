package io.securitize.pageObjects.controlPanel.cpMarkets;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CpAccreditationDetails extends AbstractPage {

    private static final ExtendedBy accreditationDetailsStatusDropDown = new ExtendedBy("Accreditation Details Status DropDown", By.xpath("//select[@name='status']"));
    private static final ExtendedBy accreditationDetailsMessageForInvestorTextArea = new ExtendedBy("Accreditation Message for Investor TextArea", By.xpath("//textarea[@name='rejectReason']"));
    private static final ExtendedBy accreditationDetailsMessageForInvestorWarn = new ExtendedBy("Message For Investor Warn Text", By.xpath("//span[contains(text(), 'Please introduce a message for investor')]"));
    private static final ExtendedBy accreditationDetailsSubmitReviewBtnForDisabledForValidation = new ExtendedBy("Accreditation Submit Button", By.xpath("//span[contains(text(), 'Submit review')]/ancestor::button"));
    private static final ExtendedBy accreditationDetailsSubmitReviewBtnForClick = new ExtendedBy("Accreditation Submit Span", By.xpath("//span[contains(text(), 'Submit review')]"));
    private static final ExtendedBy youAreNotAssignedThisApplicantMsg = new ExtendedBy("You Are Not Assigned This Applicant Msg", By.xpath("//*[contains(text(), 'You are not assigned this applicant so cannot submit a review')]"));


    public CpAccreditationDetails(Browser browser) {
        super(browser);
    }

    public CpAccreditationDetails selectAccreditationStatus(String accreditationStatus) {
        browser.waitForPageStable();
        browser.waitForElementVisibility(accreditationDetailsStatusDropDown);
        browser.selectElementByVisibleText(accreditationDetailsStatusDropDown, accreditationStatus);
        return this;
    }

    public CpAccreditationDetails setMessageForInvestor(String massageForInvestor) {
        browser.typeTextElement(accreditationDetailsMessageForInvestorTextArea, massageForInvestor);
        return this;
    }

    public CpAccreditation clickAccreditationSubmitReviewBtn(){
        browser.clickAndWaitForElementToVanish(accreditationDetailsSubmitReviewBtnForClick);
        return new CpAccreditation(browser);
    }

    public boolean isMessageForInvestorWarnVisible() {
        return browser.isElementVisible(accreditationDetailsMessageForInvestorWarn);
    }

    public boolean isSubmitReviewBtnEnabled() {
        WebElement element = browser.findElement(accreditationDetailsSubmitReviewBtnForDisabledForValidation);
        browser.waitForElementToHaveAttribute(element, "Submit Review Btn", "disabled");
        return element.isEnabled();
    }

    public boolean isLawyerNotAssignedMsg() {
        return browser.isElementVisible(youAreNotAssignedThisApplicantMsg);
    }

}