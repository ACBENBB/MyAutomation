package io.securitize.pageObjects.controlPanel.cpIssuers.distributions;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.tests.transferAgent.abstractClass.AbstractDistributions;
import io.securitize.tests.transferAgent.testData.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.*;

public class GenerateDistributionModalPage extends AbstractPage<GenerateDistributionModalPage> {

    private static final ExtendedBy distributionTypeDropdown = new ExtendedBy("Distribution Type DropDown", By.xpath("//div[@id='ta-distributions-input-distribution-type']"));
    private static final ExtendedBy issuerProfileIdInputBox = new ExtendedBy("Issuer Profile Id Input Box", By.xpath("//input[@id='ta-distributions-input-issuer-sec-id']"));
    private static final ExtendedBy distributionNameInputBox = new ExtendedBy("Distribution Name Input Box", By.xpath("//input[@id='ta-distributions-input-name']"));
    private static final ExtendedBy snapshotDateInputBox = new ExtendedBy("Snapshot Date Input Box", By.xpath("//input[@id='ta-distributions-input-snapshot-date']"));
    private static final ExtendedBy snapshotDateInputLabel = new ExtendedBy("Snapshot Date Input Label", By.xpath("//label[@id='ta-distributions-input-snapshot-date-label']"));
    private static final ExtendedBy snapshotTimeDropdown = new ExtendedBy("Snapshot Time Dropdown", By.xpath("//div[@id='ta-distributions-input-snapshot-id']"));
    private static final ExtendedBy snapshotTimeDropdownText = new ExtendedBy("Snapshot Time Dropdown", By.xpath("//span[text()='Snapshot time *']"));
    private static final ExtendedBy snapshotTimeFirstDropdownOption = new ExtendedBy("Snapshot Time First Dropdown Option", By.xpath("(//li[@role='option'])[1]"));
    private static final ExtendedBy payoutTypeDropdown = new ExtendedBy("Payout Type Dropdown", By.xpath("//div[@id='ta-distributions-input-distribution-type']"));
    private static final ExtendedBy totalAmountInputBox = new ExtendedBy("Total Amount Input Box", By.xpath("//input[@id='ta-distributions-input-amount']"));
    private static final ExtendedBy generateDistributionButton = new ExtendedBy("Generate Distribution Submit Btn", By.xpath("//button[@id='ta-distributions-button-submit']"));
    private static final ExtendedBy generateRedemptionLimmit = new ExtendedBy("Generate Redemption Limmit Input", By.xpath("//input[@id='ta-distributions-input-limitation']"));
    private static final ExtendedBy generateRedemptionPrice = new ExtendedBy("Generate Redemption Price Input", By.xpath("//input[@id='ta-distributions-input-amount']"));
    private static final ExtendedBy generateRedemptionEndDateInput = new ExtendedBy("Generate Redemption End Date Input", By.xpath("//input[@id='ta-distributions-endDate']"));
    private static final ExtendedBy generateRedemptionNextDayButton = new ExtendedBy("Generate Redemption Next Day Button", By.xpath("//button[contains(@class, 'MuiButtonBase-root') and not(contains(@class, 'Mui-disabled')) and contains(@class, 'MuiPickersDay-root')]"));
    private static final ExtendedBy calendarNextPageButton = new ExtendedBy("Calendar nex page button", By.cssSelector("[data-testid='ArrowRightIcon']"));

    public GenerateDistributionModalPage(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    public void generateDistribution(DistributionData distributionData) {
        // TODO if issuer cash acc sid == null -> enter issuer cash acc sid
        selectDistributionType(distributionData.type);
        setDistributionName(distributionData.distributionName);
        if (distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_REDEMPTION.toString())) {
            selectRedemptionDate();
            setRedemptionLimit(distributionData.redemptionLimit);
            setRedemptionPrice(distributionData.redemptionPrice);
        } else if (distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_ACCEPT.toString())
                || distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_REJECT.toString())
                || distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND.toString())
                || distributionData.distributionTestType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_DRIP_HAPPY_FLOW.toString())) {
            selectSnapshotDate(0);
            selectSnapshotTimeFirstDropdownOption();
            setTotalAmount(distributionData.amountPerToken);
        }
        clickGenerateDistributionSubmitBtn();
    }

    public void selectDistributionType(String distributionType) {
        browser.findElement(distributionTypeDropdown).click();
        Actions keyDown = browser.getActionsClassInstance();
        if(distributionType.equalsIgnoreCase("dividend")) {
            keyDown.sendKeys(Keys.chord(Keys.ENTER)).perform();
        } else if(distributionType.equalsIgnoreCase("redemption")) {
            keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        }
    }

    public void setDistributionName(String distributionName) {
        browser.sendKeysElement(distributionNameInputBox, "Set Distribution Name", distributionName);
    }

    public void selectPayoutType(String payoutType) {
        WebElement dropdownElement = browser.findElement(payoutTypeDropdown);
        dropdownElement.click();
        Actions keyDown = browser.getActionsClassInstance();
        if(payoutType.equalsIgnoreCase("dividend")) {
            keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        } else if(payoutType.equalsIgnoreCase("redemption")) {
            keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        }
    }

    public void selectSnapshotDate(int daysBeforeToday) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int daysBefore = day - daysBeforeToday;
        browser.click(snapshotDateInputLabel);
        String yesterDayElementXpath = String.format("//button[text()='%s']", daysBefore);
        browser.click(new ExtendedBy("Yesterday Dynamic Xpath", By.xpath(yesterDayElementXpath)));
    }

    public void selectSnapshotTimeFirstDropdownOption() {
        browser.waitForElementToVanish(snapshotTimeDropdownText);
        WebElement element = browser.findElement(snapshotTimeDropdown);
        element.click();
        browser.click(snapshotTimeFirstDropdownOption);
    }

    public void selectRedemptionDate() {
        browser.click(generateRedemptionEndDateInput);
        try {
            browser.waitForElementClickable(generateRedemptionNextDayButton, 2);
            browser.click(generateRedemptionNextDayButton);
        } catch (Exception e) {
            browser.click(calendarNextPageButton);
            browser.waitForElementClickable(generateRedemptionNextDayButton, 2);
            browser.click(generateRedemptionNextDayButton);
        }
    }
    public void setTotalAmount(String totalAmount) {
        browser.sendKeysElement(totalAmountInputBox, "Set Total Amount", totalAmount);
    }

    public void clickGenerateDistributionSubmitBtn() {
        browser.clickAndWaitForElementToVanish(generateDistributionButton);
    }

    public void setRedemptionLimit(String limit) {
        browser.sendKeysElement(generateRedemptionLimmit, "Set Redemption Limit", limit);
    }

    public void setRedemptionPrice(String price) {
        browser.sendKeysElement(generateRedemptionPrice, "Set Redemption Price", price);
    }

    public void setIssuerProfileId(String issuerProfileId) {
        browser.sendKeysElement(issuerProfileIdInputBox, "Set Issuer Profile ID", issuerProfileId);
    }

    public String getIssuerProfileId() {
        return browser.findElement(issuerProfileIdInputBox).getAttribute("value");
    }

    public String getSnapshotDate() {
        return browser.findElement(snapshotDateInputBox).getAttribute("value");
    }

}
