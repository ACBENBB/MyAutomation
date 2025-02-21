package io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class PrimaryOfferingOpportunityPage extends AbstractPage<PrimaryOfferingOpportunityPage> {

    private static final ExtendedBy signUpAndInvest = new ExtendedBy("Sign up and Invest Button", By.xpath("//span[text() = 'Sign up and Invest']"));

    public PrimaryOfferingOpportunityPage(Browser browser) {
        super(browser, signUpAndInvest);
    }

    public void clickSignUpAndInvest() {
        browser.click(signUpAndInvest, false);
    }
}