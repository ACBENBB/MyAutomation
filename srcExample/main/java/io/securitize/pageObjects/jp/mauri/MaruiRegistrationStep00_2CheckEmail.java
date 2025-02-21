package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.EmailWrapper;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep00_2CheckEmail extends AbstractJpPage {

    private static final ExtendedBy checkEmailText = new ExtendedBy("Check Email Text", By.xpath("//*[contains(@class,'card-text')]"));
    private static final ExtendedBy returnButton = new ExtendedBy("Return Button", By.xpath("//*[text()[contains(.,'戻る')]]"));
    private static final ExtendedBy resendEmailLink = new ExtendedBy("Resend Email Link", By.xpath("//button/*[text()[contains(.,'確認用のメールを再送する')]]"));

    public MaruiRegistrationStep00_2CheckEmail(Browser browser) {
        super(browser, returnButton);
    }

    public String getCheckEmailText() {
        return browser.getElementText(checkEmailText);
    }

    public boolean resendEmailLinkIsEnabled() {
        return browser.isElementEnabled(resendEmailLink);
    }

    public MaruiRegistrationStep00_1SendEmail clickReturnButton() {
        browser.clickAndWaitForElementToVanish(returnButton);
        return new MaruiRegistrationStep00_1SendEmail(browser);
    }

    public String getRegistrationLink(String recipientAddress) {
        String regex = "(https://oioi\\S+)</a>";
        String emailContent = EmailWrapper.waitAndGetEmailContentByRecipientAddressAndRegex(recipientAddress, regex);
        return RegexWrapper.getFirstGroup(emailContent, regex).trim();
    }
}
