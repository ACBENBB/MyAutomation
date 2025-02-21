package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieTermsPage extends AbstractPage {

    private static final ExtendedBy acceptTermsCheckbox = new ExtendedBy("Accept terms checkbox", By.id("check-agree-terms-and-conditions"));
    private static final ExtendedBy enterDashboardButton = new ExtendedBy("Enter dashboard button", By.id("btn-enter-dashboard"));


    public NieTermsPage(Browser browser) {
        super(browser, acceptTermsCheckbox);
    }

    public NieTermsPage clickAcceptTerms() {
        browser.click(acceptTermsCheckbox, false);
        return this;
    }

    public NieDashboard clickEnterDashboard() {
        browser.click(enterDashboardButton, false);
        return new NieDashboard(browser);
    }
}
