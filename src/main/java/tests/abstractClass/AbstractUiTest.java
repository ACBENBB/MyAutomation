/*
package tests.abstractClass;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import infra.config.MainConfig;
import infra.config.MainConfigProperty;
import infra.visualtesting.AbstractVisualTesting;
import infra.visualtesting.VisualTestingDummy;
import infra.webdriver.Browser;
import infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static infra.reporting.MultiReporter.*;

public class AbstractUiTest {

    protected ThreadLocal<Browser> browser = new ThreadLocal<>();

    // initializing a dummy visual testing engine so tests could run the visualTesting logic without failing even
    // if visualTesting isn't configured (Thus avoiding NullReferenceException)
   // protected AbstractVisualTesting visualTesting = new VisualTestingDummy(null);

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
            if (getBrowser() == null) {
                String parametersAsString = "";
                if (parameters != null && parameters.length > 0) {
                    parametersAsString = " " + Arrays.toString(parameters);
                }

            }
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

    */
/**
     * Idea of the method below is from the above method. There are small differences.
     * ToDo merge the methods
     *//*

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
}
*/
