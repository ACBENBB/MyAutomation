package io.securitize.pageObjects.controlPanel.cpIssuers.controlPanelLoginPage;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpGoogleSSOLoginScreen extends AbstractPage {

        private static final ExtendedBy googleSSOLoginButton = new ExtendedBy("Google SSO Login button", By.xpath("//*[@class='btn btn-block btn-google btn-block blue btn-primary']"));
        private static final ExtendedBy googleSSOEmailTextBox = new ExtendedBy("Login - Google SSO insert Email text box", By.id("identifierId"));
        private static final ExtendedBy googleSSOEmailNextButton = new ExtendedBy("Google SSO insert email , next button", By.xpath("//*[@id='identifierNext']/span/span"));
        private static final ExtendedBy googleSSOPasswordTextBox = new ExtendedBy("Google SSO insert password text box", By.id("//*[@id='password']"), true);
        private static final ExtendedBy googleSSOPasswordNextButton = new ExtendedBy("Google SSO insert password , next button", By.xpath("//*[@id='passwordNext']/div[2]"));

    public CpGoogleSSOLoginScreen(Browser browser, ExtendedBy... elements) {
        super(browser, elements);

        //basic functions







        //advanced functions




    }
}
