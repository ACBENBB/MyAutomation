package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class CpIssuerConfigurationVariables extends AbstractPage {

    private static final ExtendedBy searchButton = new ExtendedBy("search button", By.xpath("//input[@placeholder='Search...']"));
    private static final ExtendedBy tableRow = new ExtendedBy("row of variables table", By.xpath("//table//tr"));
    private static final ExtendedBy valueCell = new ExtendedBy("Value td of row of variables table", By.xpath("//table//tr//td[@aria-colindex=5]"));
    private static final ExtendedBy nameCell = new ExtendedBy("Name td of variables table", By.xpath("//table//tr//td[@aria-colindex=1]"));
    private static final ExtendedBy editVariable = new ExtendedBy("edit variable button", By.xpath("//table//tr//button"));
    private static final ExtendedBy valueTextBox = new ExtendedBy("value text box", By.xpath("//input[@name='value']"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[contains(text(), 'OK')]"));
    boolean privacyControlValue;

    public CpIssuerConfigurationVariables(Browser browser) {
        super(browser, searchButton);
    }

    public void waitForOneRowInTable() {
        // Wait privacy-control be the only row in the table after filtering
        browser.waitForElementCount(tableRow, 2);
        browser.waitForTextInElement(nameCell, "privacy-control");
        List<WebElement> listVariables = browser.findElements(valueCell);
        if (listVariables.size() == 1) {
            info("There is one row in table");
            if (listVariables.get(0).getText().contains("privacy-control")) {
                info("Privacy control variable is shown");
            } else {
                info("Privacy control variable is not shown after search");
            }
        } else {
            errorAndStop("More than one row in table", true);
        }
    }

    public void searchPrivacyControlVariable() {
        browser.typeTextElement(searchButton, "privacy-control");
        waitForOneRowInTable();
    }

    public boolean getPrivacyControlValue() {
        browser.refreshPage();
        List<WebElement> listVariables = browser.findElements(valueCell);
        if (listVariables.get(0).getText().contains("true")) {
            privacyControlValue = true;
            info("Privacy control is ON");
        }

        else if (listVariables.get(0).getText().contains("false")) {
            privacyControlValue = false;
            info("Privacy control is OFF");

        } else {
            info("Value is not clear, check the row:");
        }
        return privacyControlValue;
    }

    public void setPrivacyControl(boolean requestedValue) {
        // Check if requested value is the current value
        privacyControlValue = getPrivacyControlValue();
        if (privacyControlValue == requestedValue) {
            info("Requested value equal to current value: " + requestedValue);
        }
        // Change to requested value
        else {
            browser.click(editVariable);
            browser.typeTextElement(valueTextBox, Boolean.toString(requestedValue));
            browser.clickAndWaitForElementToVanish(okButton);
            browser.waitForElementVisibility(valueCell);
            // Verify value changed in variables table
            privacyControlValue = getPrivacyControlValue();
            if (privacyControlValue == requestedValue) {
                info("Value changed to: " + requestedValue);
            } else {
                info("Value didn't change, it's still: " + privacyControlValue);
                browser.refreshPage();
                setPrivacyControl(requestedValue);
            }
        }

    }


}


