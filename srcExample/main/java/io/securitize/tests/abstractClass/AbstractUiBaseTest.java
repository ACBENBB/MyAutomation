package io.securitize.tests.abstractClass;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.webdriver.WebDriverFactory;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.utils.UseVisualTesting;
import io.securitize.infra.visualtesting.*;
import io.securitize.infra.wallets.UsingCoinBase;
import io.securitize.infra.wallets.UsingMetaMask;
import io.securitize.infra.wallets.WalletExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractUiBaseTest extends AbstractTest {
    protected Browser browser;

    // initializing a dummy visual testing engine so tests could run the visualTesting logic without failing even
    // if visualTesting isn't configured (Thus avoiding NullReferenceException)
    protected AbstractVisualTesting visualTesting = new VisualTestingDummy(null);

    protected void openBrowser(Method method, Object[] parameters) throws Exception {
        try {
            if (browser == null) {
                String parametersAsString = "";
                if (parameters != null && parameters.length > 0) {
                    parametersAsString = " " + Arrays.toString(parameters);
                }
                WalletExtension walletExtension = getWalletToUseFromMethodByExtension(method);
                WebDriver driver = WebDriverFactory.getWebDriver(method, method.getName() + parametersAsString, walletExtension);
                browser = new Browser(driver);
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
        for (StackTraceElement currentStackFrame: Thread.currentThread().getStackTrace()) {
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

    protected void closeBrowser() {
        try {
            if (browser != null) {
                browser.quit();
                Browser.clearDriver();
                browser = null;
            }
        } catch (WebDriverException e) {
            // we don't want the AfterMethod to throw an exception because it will prevent from the
            // rest of the tests to run
            warning("Reporting test result failed! Details: " + e, false);
        }
    }

    protected String getLatestDownloadedFileContent() {
        String jsPathToDownloadedFile = "document.getElementsByTagName(\"downloads-manager\")[0].shadowRoot.children[\"mainContainer\"].children[\"downloadsList\"].querySelector(\"downloads-item\").shadowRoot";

        // open the downloads tab (done in two steps otherwise it won't work - blocked by Chrome)
        browser.openNewTabAndSwitchToIt();
        browser.navigateTo("chrome://downloads");

        // make sure file finished downloading - there is at least one file in the list of downloaded files
        int expectedDownloadsCount = 1;
        Function<String, Boolean> internalWaitForFileFinishedLoading = t -> {
            try {
                String currentDownloadsCountAsString = browser.executeScript("return " + jsPathToDownloadedFile + ".querySelectorAll(\"a\").length");
                int currentDownloadsCount = Integer.parseInt(currentDownloadsCountAsString) - 2; // we always have 2 non relevant items
                return (currentDownloadsCount >= expectedDownloadsCount);
            } catch (Exception e) {
                info("Exception occur waiting for number of downloads. Details: " + e.toString());
                return false;
            }
        };
        String description = "expectedDownloadsCount=" + expectedDownloadsCount;
        Browser.waitForExpressionToEqual(internalWaitForFileFinishedLoading, null, true, description,10, 1);

        // open first downloaded file and get content
        String downloadedFileUrl = browser.executeScript("return " + jsPathToDownloadedFile + ".querySelector(\"a\").href");
        browser.openNewTabAndSwitchToIt(); // done in two steps otherwise it won't work - blocked by Chrome
        browser.navigateTo(downloadedFileUrl);
        String downloadedFileContent = browser.getElementText(new ExtendedBy("content of downloaded file as body of new tab", By.tagName("body")));

        // close both tabs
        browser.closeLastTabAndSwitchToPreviousOne();
        browser.closeLastTabAndSwitchToPreviousOne();

        return downloadedFileContent;
    }

    private void initVisualTesting() {
        String engineToUse = MainConfig.getProperty(MainConfigProperty.visualTestingEngine);
        switch (engineToUse.toLowerCase()) {
            case "local":
                visualTesting = new VisualTestingLocal(browser);
                break;
            case "percy":
                visualTesting = new VisualTestingPercy(browser);
                break;
            case "s3":
                visualTesting = new VisualTestingS3(browser);
                break;
            default:
                errorAndStop("Unsupported visual testing engine: " + engineToUse, false);
        }
    }
}
