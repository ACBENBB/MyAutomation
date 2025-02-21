package io.securitize.tests.ios;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.pageObjects.ios.LoginPage;
import io.securitize.pageObjects.ios.PortfolioPage;
import io.securitize.tests.abstractClass.AbstractUiTest;
import org.testng.annotations.Test;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT675_IOS_Login extends AbstractUiTest {

    @Test(description = "Validate we are able to login into the IOS app")
    public void AUT675_IOS_Login_test() {
        startTestLevel("Perform login");
        LoginPage loginPage = new LoginPage(getBrowser());
        PortfolioPage portfolioPage = loginPage.performFullLogin(
                Users.getProperty(UsersProperty.ios_investor_email),
                Users.getProperty(UsersProperty.defaultPasswordComplex));
        endTestLevel(true);


        startTestLevel("Validate portfolio page");
        portfolioPage.clickAcceptCookiesIfNeeded();
        endTestLevel(true);
    }
}