package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizeIdForgetPassword extends AbstractPage {
    private static final ExtendedBy securitizeIdResetPasswordEmailTextbox = new ExtendedBy("Securitize Id - Reset password screen - email textbox", By.id("reset-password-email"));
    private static final ExtendedBy securitizeIdResetPasswordSendMeRecoverLinkButton = new ExtendedBy( "Securitize Id - Reset password screen - Send me a recovery link", By.id("reset-password-submit") );
    private static final ExtendedBy securitizeIdResetPasswordGoBackButton = new ExtendedBy("Securitize Id - Reset Password screen - Go back button", By.id("link-go-back"));


    public SecuritizeIdForgetPassword(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }
}
