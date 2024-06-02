package infra.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExtendedBy {

    private static WebDriver driver;
    private By by;
    private String description;
    private boolean secureField;

    private static final int DEFAULT_TIMEOUT = 5;

    public static void setDriver(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance cannot be null");
        }
        ExtendedBy.driver = driver;
    }

    // constructors with default timeout
    public ExtendedBy(String description, By by) {
        this.description = description;
        this.by = by;
        this.secureField = false;
        waitForVisibility();
    }

    public ExtendedBy(String description, By by, boolean secureField) {
        this.description = description;
        this.by = by;
        this.secureField = secureField;
        waitForVisibility();
    }

    private void waitForVisibility(int timeOutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(this.by));
    }

    private void waitForVisibility() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(this.by));
    }

    public By getBy() {
        return by;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSecureField() {
        return secureField;
    }

    // constructors with custom timeout
    public ExtendedBy(String description, By by, int timeOutSeconds) {
        this.description = description;
        this.by = by;
        this.secureField = false;
        waitForVisibility(timeOutSeconds);
    }

    public ExtendedBy(String description, By by, boolean secureField, int timeOutSeconds) {
        this.description = description;
        this.by = by;
        this.secureField = secureField;
        waitForVisibility(timeOutSeconds);
    }

}
