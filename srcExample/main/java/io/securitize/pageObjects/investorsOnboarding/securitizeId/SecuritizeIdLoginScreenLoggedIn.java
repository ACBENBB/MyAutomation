package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import io.securitize.pageObjects.investorsOnboarding.nie.NieDashboard;
import io.securitize.pageObjects.investorsOnboarding.nie.NieInvestorQualifications;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc01IndividualIdentityVerification;
import org.openqa.selenium.By;

public class SecuritizeIdLoginScreenLoggedIn extends SecuritizeIdInvestorAbstractPage<SecuritizeIdLoginScreenLoggedIn> {

    private static final ExtendedBy loggedInUserAccountSelector = new ExtendedBy("Logged in user - account selector", By.xpath("//div[@class='name-email']"));
    private static final String continueAsLoggedInUserXpathTemplate = "//div[@class='investor-info-box dropdown']//div[@class='name-email']//div[contains(text(),'%s')][2]";
    private static final ExtendedBy useAnotherAccountButton = new ExtendedBy("Use another account button", By.xpath("//*[text() = 'Use another account']"));
    private static final ExtendedBy allowButton = new ExtendedBy("Allow button", By.xpath("//*[text() =  'Allow']"));
    private static final ExtendedBy continueButton = new ExtendedBy("Continue button", By.xpath("//*[text() =  'Continue']"));
    private static final ExtendedBy completeIdentityVerificationButton = new ExtendedBy("Continue button", By.xpath("//*[text() =  'Complete Identity Verification']"));
    public static final ExtendedBy[] visualIgnoreList = {loggedInUserAccountSelector};

    public SecuritizeIdLoginScreenLoggedIn(Browser browser) {
        super(browser, loggedInUserAccountSelector);
    }

    public NieDashboard clickAllowButton() {
        browser.click(allowButton);
        return new NieDashboard(browser);
    }

    public NieInvestorQualifications clickAllowButtonWithAccreditationFirst() {
        browser.click(allowButton);
        return new NieInvestorQualifications(browser);
    }

    public NieDashboard loginWithRememberedUserRedirectToNiePlatformHomePage(String email) {
        String fullSelector = String.format(continueAsLoggedInUserXpathTemplate, email.toLowerCase());
        ExtendedBy selector = new ExtendedBy("Resume as logged in user by email: " + email, By.xpath(fullSelector));

        browser.isElementVisible(selector);
        browser.click(allowButton, false);
        return new NieDashboard(browser);
    }

    @SuppressWarnings("unused")
    public NieDashboard loginWithRememberedUserRedirectToNieDashboard(String email) {
        String fullSelector = String.format(continueAsLoggedInUserXpathTemplate, email.toLowerCase());
        ExtendedBy selector = new ExtendedBy("Resume as logged in user by email: " + email, By.xpath(fullSelector));

        browser.click(selector, false);
        return new NieDashboard(browser);
    }

    public SecuritizeIdInvestorLoginScreen loginWithUserCredentials() {
        browser.click(useAnotherAccountButton, false);
        return new SecuritizeIdInvestorLoginScreen(browser);
    }

    public NieDashboard waitForNIELogin() {
        return new NieDashboard(browser);
    }

    public NieDashboard clickContinueButton() {
        browser.click(continueButton);
        return new NieDashboard(browser);
    }

    public SecuritizeIdInvestorKyc01IndividualIdentityVerification clickCompleteIdentityVerificationButton() {
        browser.click(completeIdentityVerificationButton);
        return new SecuritizeIdInvestorKyc01IndividualIdentityVerification(browser);
    }
}