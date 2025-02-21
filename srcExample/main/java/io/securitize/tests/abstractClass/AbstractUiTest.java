package io.securitize.tests.abstractClass;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.webdriver.WebDriverFactory;
import io.securitize.infra.utils.UseVisualTesting;
import io.securitize.infra.visualtesting.*;
import io.securitize.infra.wallets.UsingCoinBase;
import io.securitize.infra.wallets.UsingMetaMask;
import io.securitize.infra.wallets.WalletExtension;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3;
import io.securitize.pageObjects.ios.LoginPage;
import io.securitize.pageObjects.ios.PortfolioPage;
import io.securitize.tests.InvestorDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.function.Function;
import java.time.Duration;
import java.util.Optional;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractUiTest extends AbstractTest {
    protected ThreadLocal<Browser> browser = new ThreadLocal<>();

    // initializing a dummy visual testing engine so tests could run the visualTesting logic without failing even
    // if visualTesting isn't configured (Thus avoiding NullReferenceException)
    protected AbstractVisualTesting visualTesting = new VisualTestingDummy(null);


    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final ITestContext testContext, Method method, Object[] parameters) throws Exception {
        super.beforeMethod(testContext, method, parameters);

        startTestLevel("Before method");
        if (!(this instanceof AbstractDelayedUiTest)) {
            openBrowser(method, parameters);
        }
        endTestLevel(false);
    }

    protected Browser getBrowser() {
        return browser.get();
    }

    protected void openBrowser(Method method, Object[] parameters) throws Exception {
        try {
            if (getBrowser() == null) {
                String parametersAsString = "";
                if (parameters != null && parameters.length > 0) {
                    parametersAsString = " " + Arrays.toString(parameters);
                }
                WalletExtension walletExtension = getWalletToUseFromMethodByExtension(method);
                WebDriver driver = WebDriverFactory.getWebDriver(method, method.getName() + parametersAsString, walletExtension);
                browser.set(new Browser(driver));
            }

            // if this is a visual regression test - initialize the percy mechanism
            boolean useVisualTesting = method.getDeclaredAnnotation(UseVisualTesting.class) != null;
            if (useVisualTesting) {
                info("Setting up visual testing...");
                initVisualTesting();
            }
        } catch (Exception e) {
            error("An error occur trying to open browser: " + e, false);
            throw e;
        }
    }

    protected WalletExtension getWalletExtensionToUse() {
        for (StackTraceElement currentStackFrame : Thread.currentThread().getStackTrace()) {
            try {
                Class<?> currentClass = Class.forName(currentStackFrame.getClassName());
                Optional<Method> currentMethod = Arrays.stream(currentClass.getDeclaredMethods()).filter(x -> x.getName().contains(currentStackFrame.getMethodName())).findFirst();
                if (currentMethod.isPresent()) {
                    WalletExtension result = getWalletToUseFromMethodByExtension(currentMethod.get());
                    if (result != WalletExtension.NONE) {
                        return result;
                    }
                }
            } catch (ClassNotFoundException e) {
                debug("method not found during reflection: " + currentStackFrame.getClassName() + "->" + currentStackFrame.getMethodName());
            }
        }

        errorAndStop("No wallet to use was defined by annotation in this test, such as @UsingMetaMask", false);
        return WalletExtension.NONE;
    }

    private WalletExtension getWalletToUseFromMethodByExtension(Method method) {
        if (method.getDeclaredAnnotation(UsingMetaMask.class) != null) return WalletExtension.METAMASK;
        if (method.getDeclaredAnnotation(UsingCoinBase.class) != null) return WalletExtension.COINBASE;

        return WalletExtension.NONE;
    }

    @AfterMethod(alwaysRun = true)
    @Override
    public void afterMethod(ITestContext context, ITestResult result) {
        RuntimeException exception = null;
        try {
            super.afterMethod(context, result);
        } catch (RuntimeException e) {
            warning("An issue occur trying to run AbstractTest afterMethod: " + e, false);
            exception = e;
        }

        try {
            // we don't want to start a new passed level in a skipped tests as it might appear as passed
            // instead of skipped in some HTML reports
            if (result.getStatus() != ITestResult.CREATED && result.getStatus() != ITestResult.SKIP) {
                startTestLevel("After method UI");
            }
            if (getBrowser() != null) {
                getBrowser().quit();
            }
        } catch (WebDriverException e) {
            // we don't want the AfterMethod to throw an exception because it will prevent from the
            // rest of the tests to run
            warning("Reporting test result failed! Details: " + e, false);
        } finally {
            Browser.clearDriver();
            browser.set(null);
            if (result.getStatus() != ITestResult.STARTED && result.getStatus() != ITestResult.SKIP) {
                endTestLevel(false);
            }
        }

        if (exception != null) {
            throw exception;
        }
    }

    public String getChromeLastDownloadedFileUrl() {
        String jsPathToDownloadedFile = "document.getElementsByTagName(\"downloads-manager\")[0].shadowRoot.children[\"mainContainer\"].children[\"downloadsList\"].querySelector(\"downloads-item\").shadowRoot";
        // open the downloads tab (done in two steps otherwise it won't work - blocked by Chrome)
        getBrowser().openNewTabAndSwitchToIt();
        getBrowser().navigateTo("chrome://downloads");
        // make sure file finished downloading - there is at least one file in the list of downloaded files
        int expectedDownloadsCount = 1;
        Function<String, Boolean> internalWaitForFileFinishedLoading = t -> {
            try {
                String currentDownloadsCountAsString = getBrowser().executeScript("return " + jsPathToDownloadedFile + ".querySelectorAll(\"a\").length");
                int currentDownloadsCount = Integer.parseInt(currentDownloadsCountAsString) - 2; // we always have 2 non relevant items
                return (currentDownloadsCount >= expectedDownloadsCount);
            } catch (Exception e) {
                info("Exception occur waiting for number of downloads. Details: " + e);
                return false;
            }
        };
        String description = "expectedDownloadsCount=" + expectedDownloadsCount;
        Browser.waitForExpressionToEqual(internalWaitForFileFinishedLoading, null, true, description, 60, 1000);
        // open first downloaded file and get content
        return getBrowser().executeScript("return " + jsPathToDownloadedFile + ".querySelector(\"a\").href");
    }

    /**
     * Idea of the method below is from the above method. There are small differences.
     * ToDo merge the methods
     */
    public String getChromeLastDownloadedFileUrl(int expectedDownloadsCount, int timeoutSeconds) {
        String jsPathToDownloadedList = "document.getElementsByTagName('downloads-manager')[0].shadowRoot.children['mainContainer'].children['downloadsList']";
        String jsPathToDownloadedFile = jsPathToDownloadedList + ".querySelector('downloads-item').shadowRoot";
        // open the downloads tab (done in two steps otherwise it won't work - blocked by Chrome)
        getBrowser().openNewTabAndSwitchToIt();
        getBrowser().navigateTo("chrome://downloads");
        // make sure file finished downloading - there is at least one file in the list of downloaded files
        Function<String, Boolean> internalWaitForFileFinishedLoading = t -> {
            try {
                String currentDownloadsCountAsString = getBrowser().executeScript("return " + jsPathToDownloadedList + ".querySelectorAll('downloads-item').length");
                int currentDownloadsCount = Integer.parseInt(currentDownloadsCountAsString);
                return (currentDownloadsCount >= expectedDownloadsCount);
            } catch (Exception e) {
                info("Exception occur waiting for number of downloads. Details: " + e);
                return false;
            }
        };
        String description = "expectedDownloadsCount=" + expectedDownloadsCount;
        Browser.waitForExpressionToEqual(internalWaitForFileFinishedLoading, null, true, description, timeoutSeconds, 1000);
        // retry for the return line as javascript exception can happen (quite rare, I believe)
        RetryPolicy<String> retryPolicyOnJavascriptException = RetryPolicy.<String>builder()
                .handle(org.openqa.selenium.JavascriptException.class)
                .withDelay(Duration.ofSeconds(3))
                .withMaxRetries(5)
                .onRetry(e -> info("Javascript Exception: " + e.getLastException()))
                .build();
        // open first downloaded file and get content
        return Failsafe.with(retryPolicyOnJavascriptException).get(() -> getBrowser().executeScript("return " + jsPathToDownloadedFile + ".querySelector('a').href"));
    }

    protected String getLastDownloadedInvestorFileContent() {
        String downloadedFileUrl = getChromeLastDownloadedFileUrl();
        getBrowser().openNewTabAndSwitchToIt(); // done in two steps otherwise it won't work - blocked by Chrome
        getBrowser().navigateTo(downloadedFileUrl);
        String downloadedFileContent = getBrowser().getElementText(new ExtendedBy("content of downloaded file as body of new tab", By.tagName("body")));
        // close both tabs
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        return downloadedFileContent;
    }

    protected String getLastDownloadedDistributionFileContent() {
        String downloadedFileUrl = getChromeLastDownloadedFileUrl();
        return distributionCSVFileGetRequest(downloadedFileUrl);
    }

    public String distributionCSVFileGetRequest(String downloadedFileUrl) {
        return urlGetRequest(downloadedFileUrl);
    }

    public String urlGetRequest(String fullUrl) {
        try {
            URL url = new URL(fullUrl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append(System.lineSeparator());
            }
            in.close();
            http.disconnect();
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initVisualTesting() {
        String engineToUse = MainConfig.getProperty(MainConfigProperty.visualTestingEngine);
        switch (engineToUse.toLowerCase()) {
            case "local":
                visualTesting = new VisualTestingLocal(browser.get());
                break;
            case "percy":
                visualTesting = new VisualTestingPercy(browser.get());
                break;
            case "s3":
                visualTesting = new VisualTestingS3(browser.get());
                break;
            default:
                errorAndStop("Unsupported visual testing engine: " + engineToUse, false);
        }
    }

    protected void performSecuritizeIdLogin() {
        if (!MainConfig.getProperty(MainConfigProperty.browserType).toLowerCase().contains("ios")) {
            startTestLevel("Login to secId");
            getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
            SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
            securitizeIdLoginScreen
                    .clickAcceptCookies()
                    .performLoginWithCredentials(
                            Users.getProperty(UsersProperty.SecID_IntegrityCheck_Email),
                            Users.getProperty(UsersProperty.apiInvestorPassword),
                            false);
            endTestLevel();


            startTestLevel("SecId dashboard - skip 2fa");
            SecuritizeIdDashboard securitizeIDDashboard = new SecuritizeIdDashboard(getBrowser());
            securitizeIDDashboard.clickSkipTwoFactor();
            endTestLevel();


            startTestLevel("Ensuring displayed investor is the main investor");
            if (!securitizeIDDashboard.getUserMenuData().equalsIgnoreCase("sid integrity check name")) {
                if (!MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production")) {
                    securitizeIDDashboard
                            .clickSwitchAccountButton()
                            .clickSwitchAccountItem("Sid Integrity Check Name");
                } else {
                    securitizeIDDashboard.clickAuthorizedAccount();
                }
            }

            endTestLevel();

        } else {
            // if iOS
            startTestLevel("Perform login");
            LoginPage loginPage = new LoginPage(getBrowser());
            PortfolioPage portfolioPage = loginPage.performFullLogin(
                    Users.getProperty(UsersProperty.SecID_IntegrityCheck_Email),
                    Users.getProperty(UsersProperty.apiInvestorPassword));
            endTestLevel(true);


            startTestLevel("Validate portfolio page");
            portfolioPage.clickAcceptCookiesIfNeeded();
            getBrowser().switchToWebViewContext();
            endTestLevel(true);
        }
    }

    protected InvestorDetails performSecuritizeIdCreateAccountAndLogin(String accountType, boolean skip2faAuthentication, String... state) {
        startTestLevel("SecuritizeId create an account");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        InvestorDetails investorDetails;
        if (accountType.equals("randomUSInvestor"))
            investorDetails = InvestorDetails.generateRandomUSInvestor();
        else if (accountType.equals("randomNonUSInvestor"))
            investorDetails = InvestorDetails.generateRandomNonUSInvestor();
        else if (accountType.equals("usInvestorForSSN"))
            investorDetails = InvestorDetails.generateUSInvestorForSSN();
        else if (accountType.equals("randomEntityInvestor"))
            investorDetails = InvestorDetails.generateRandomEntityInvestor();
        else
            investorDetails = null;

        if (state != null && state.length > 0)
            investorDetails.setState(state[0]);

        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        SecuritizeIdCreateAccountRegistrationStep1 securitizeIdCreateAccountRegistrationStep1 = securitizeIdLoginScreen
                .clickAcceptCookies()
                .clickCreateAccount();
        securitizeIdCreateAccountRegistrationStep1.createInvestor(investorDetails);
        endTestLevel();


        startTestLevel("Extract link from email and navigate");
        SecuritizeIdCreateAccountRegistrationStep3 securitizeIdCreateAccountRegistrationStep3 = new SecuritizeIdCreateAccountRegistrationStep3(getBrowser());
        securitizeIdCreateAccountRegistrationStep3.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
        endTestLevel();


        if (skip2faAuthentication)
            executeSkip2faAuthentication();
        return investorDetails;
    }

    private void executeSkip2faAuthentication() {
        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
        securitizeIdDashboard
                .clickSkipTwoFactor();
        String currentTestMethodName = getCurrentTestMethod().getName();
        String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        boolean isCurrentTestMethodAUT65 = currentTestMethodName.equals("AUT65_SID_SecuritizeID_SumSub_KYC_Regression_NonUS_Test");
        boolean isCurrentTestMethodAUT66 = currentTestMethodName.equals("AUT66_SID_SecuritizeID_SumSub_KYC_Regression_US_Test");
        boolean isCurrentTestMethodAUT67 = currentTestMethodName.equals("AUT67_SID_SecuritizeID_KYB_Regression_US_Test");
        boolean isCurrentTestMethodAUT65OrAut66OrAut67 = isCurrentTestMethodAUT65 || isCurrentTestMethodAUT66 || isCurrentTestMethodAUT67;
        if ((isCurrentTestMethodAUT65OrAut66OrAut67)
                || !isCurrentTestMethodAUT65OrAut66OrAut67 && !currentEnvironment.equalsIgnoreCase("production")) {
            securitizeIdDashboard.clickCompleteYourDetails();
        }
    }
}
