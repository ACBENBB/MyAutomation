package Tests;

import Config.PropertiesFile;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;


import java.io.File;
import java.io.IOException;

public abstract class BaseTest {
    public static String driverPath;
    public static String driverName;
    public static String url;

    public static String tableId;

    public static String searchColumn;

    public static String searchText;

    public static String returnColumnText;

    public static String expectedText;

    WebElement table;
    WebDriver driver;

    public abstract String getTaskNumber();

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

}
