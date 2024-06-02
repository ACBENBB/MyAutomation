package tests.abstractClass;

import infra.config.PropertiesFile;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BaseTest {
    public static String driverPath;
    public static String driverName;
    public static String url;
    WebDriver driver;

    public abstract String getTaskNumber();

    protected static final ThreadLocal<Method> currentTestMethod = new ThreadLocal<>();

    String taskNumber = getTaskNumber();

    @BeforeClass
    public void setup() {
        PropertiesFile.readPropertiesFile(taskNumber);
        System.setProperty(driverName, driverPath);
        driver = new ChromeDriver();
        driver.navigate().to(url);
        driver.manage().window().maximize();
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
                FileUtils.copyFile(srcFile, new File("./ScreenShots/" + result.getName() + ".jpg"));
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
