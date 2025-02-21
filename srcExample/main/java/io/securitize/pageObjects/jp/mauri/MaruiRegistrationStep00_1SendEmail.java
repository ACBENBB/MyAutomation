package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep00_1SendEmail extends AbstractJpPage {

    private static final ExtendedBy emailInputBox = new ExtendedBy("Email Input Box", By.xpath("//*[@class='form-control' and @type='email']"));
    private static final ExtendedBy sendEmailButton = new ExtendedBy("Send Email Button", By.xpath("//button/*[text()[contains(.,'メールを送信する')]]"));

    public MaruiRegistrationStep00_1SendEmail(Browser browser) {
        super(browser, emailInputBox);
    }

    public MaruiRegistrationStep00_2CheckEmail sendEmail(String email) {
        browser.typeTextElement(emailInputBox, email);
        browser.clickAndWaitForElementToVanish(sendEmailButton);
        return new MaruiRegistrationStep00_2CheckEmail(browser);
    }

    public MaruiRegistrationStep00_2CheckEmail sendEmail(String email, boolean retry) {
        return retryFunctionWithRefreshPage(()-> sendEmail(email), retry);
    }

    public boolean sendEmailButtonIsVisible() {
        return browser.isElementVisible(sendEmailButton);
    }

}
