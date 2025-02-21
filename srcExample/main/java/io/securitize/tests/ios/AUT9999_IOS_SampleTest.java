//package io.securitize.tests.ios;
//
//import io.securitize.infra.config.Users;
//import io.securitize.infra.config.UsersProperty;
//import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdPortfolio;
//import io.securitize.pageObjects.ios.LoginPage;
//import io.securitize.pageObjects.ios.PortfolioPage;
//import io.securitize.tests.abstractClass.AbstractUiTest;
//import org.testng.annotations.Test;
//
//import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
//import static io.securitize.infra.reporting.MultiReporter.startTestLevel;
//
//public class AUT9999_IOS_SampleTest extends AbstractUiTest {
//
//    @Test(description = "Validate we are able to login into the IOS app")
//    public void AUT9999_IOS_SampleTest_test() {
//        startTestLevel("Perform login");
//        LoginPage loginPage = new LoginPage(getBrowser());
//        PortfolioPage portfolioPage = loginPage.performFullLogin(
//                Users.getProperty(UsersProperty.SecID_IntegrityCheck_Email),
//                Users.getProperty(UsersProperty.defaultPasswordComplex));
//        endTestLevel(true);
//
//
//        startTestLevel("Validate portfolio page");
//        portfolioPage.clickAcceptCookiesIfNeeded();
//        endTestLevel(true);
//
//        // example code for Daniel - after switching to WebView context you can use the regular
//        // page objects as usual
//        getBrowser().switchToWebViewContext();
//        SecuritizeIdPortfolio securitizeIdPortfolio = new SecuritizeIdPortfolio(getBrowser());
//        int numberOfVisibleTokens = securitizeIdPortfolio.getNumberOfVisibleTokens();
//        int x = 3;
//    }
//}