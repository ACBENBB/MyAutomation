package tests.abstractClass;

import infra.config.PropertiesFile;
import infra.exceptions.BrowserInitializationException;
import infra.utils.VideoRecorder;
import infra.webdriver.Browser;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

import static infra.reporting.MultiReporter.*;

public abstract class AbstractBaseTest{
    public static String driverPath;
    public static String driverName;

    public static String url;
    public static String videoFileName;
    WebDriver driver;

    protected ThreadLocal<Browser> browser = new ThreadLocal<>();

    public abstract String getWebsiteName();

    protected static final ThreadLocal<Method> currentTestMethod = new ThreadLocal<>();

    String webSiteName = getWebsiteName();

    private final ThreadLocal<Method> savedMethod = new ThreadLocal<>();
    private final ThreadLocal<Object[]> savedParameters = new ThreadLocal<>();

    protected Browser getBrowser() {
        return browser.get();
    }
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(final ITestContext testContext, Method method, Object[] parameters) throws Exception {
        savedMethod.set(method);
        savedParameters.set(parameters);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestContext context, ITestResult result) {
        // if test ended due to skip in its before method
        Set<ITestResult> skipsOfCurrentMethod = context.getSkippedTests().getResults(result.getMethod());
        if (skipsOfCurrentMethod.size() > 0) {
            Throwable throwable = skipsOfCurrentMethod.iterator().next().getThrowable();
            String skippedReason = "Can't extract skip reason, please check the logs";
            if (throwable != null) {
                skippedReason = throwable.getMessage();
            }
            endTest(result, skippedReason);
        } else {
            startTestLevelAsTopLevel("After method");
            endTest(result, null);
            endTestLevel(false);
        }
        currentTestMethod.remove();
    }

    protected void openBrowser() {
        try {
            driver = new ChromeDriver();
            driver.navigate().to(url);
            driver.manage().window().maximize();
            browser.set(new Browser(driver));
        } catch (Exception e) {
            throw new BrowserInitializationException("An error occurred trying to open browser. Details: " + e.getMessage(), e);
        }
    }

    @BeforeClass
    public void setup() throws IOException, AWTException {
        PropertiesFile.readPropertiesFile(webSiteName);
        System.setProperty(driverName, driverPath);
        openBrowser();
        videoFileName = VideoRecorder.start();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Thread thread = new Thread(() -> takeScreenShotFailure(result));
            thread.start();
            try {
                thread.join(5000); // Timeout of 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (thread.isAlive()) {
                thread.interrupt(); // Interrupt the thread if it's still alive after the timeout
            }
        }
    }

    @AfterClass
    public void quitDriver() {
        driver.quit();
    }

    public void clearCache() {
        driver.get("edge://settings/clearBrowserData");
        driver.findElement(By.cssSelector("#clear-now")).click();
    }

    public void takeScreenShotFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ss = (TakesScreenshot) driver;
            File srcFile = ss.getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(srcFile, new File("./screenshots/" + result.getName() + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getCurrentTestName() {
        if (currentTestMethod.get() == null) {
            return "Test name not set yet";
        }
        return currentTestMethod.get().getName();
    }

}
