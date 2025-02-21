package io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class AccreditationReviewAndSubmitPage extends AbstractPage {

    private static final ExtendedBy submitButton = new ExtendedBy("Submit Button", By.id("submitForm"));

    public AccreditationReviewAndSubmitPage(Browser browser) {
        super(browser, submitButton);
    }

    public AccreditationStatusPage clickOnSubmit() {
        browser.click(submitButton, false);
        return new AccreditationStatusPage(browser);
    }

}
