package infra.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ExtendedBy {

    private By by;
    private String description;
    private boolean secureField;

    public ExtendedBy(String description, By by) {
        this.description = description;
        this.by = by;
        this.secureField = false;
    }

    public ExtendedBy(String description, By by, boolean secureField) {
        this.description = description;
        this.by = by;
        this.secureField = secureField;
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
}
