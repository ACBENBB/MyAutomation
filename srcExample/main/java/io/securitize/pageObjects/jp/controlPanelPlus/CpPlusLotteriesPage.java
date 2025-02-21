package io.securitize.pageObjects.jp.controlPanelPlus;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import dev.failsafe.RetryPolicyBuilder;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

import java.time.Duration;

public class CpPlusLotteriesPage extends AbstractJpPage {

    private static final ExtendedBy createConditionButton = new ExtendedBy("Create Condition Button", By.xpath("//button/*[text()[contains(.,'条件作成')]]"));
    private static final ExtendedBy confirmResultButton = new ExtendedBy("Confirm Result Button", By.xpath("//button/*[text()[contains(.,'結果確定')]]"));
    private static final String TEMPLATE_XPATH_TOKEN_NAME_INPUT_ON_CONFIRM_DIALOG = "//input[@placeholder='%s']";
    private static final ExtendedBy resultConfirmStatusOk = new ExtendedBy("Result Confirm Status OK", By.xpath("//*[@class='badge badge-success' and text() = '完了']"));
    private static final ExtendedBy tokenNameInputOnConfirmDialog = new ExtendedBy("Token Name Input on Confirm Dialog", By.xpath("//*[@name='']"));
    private static final ExtendedBy okButtonOnConfirmDialog = new ExtendedBy("OK Button on Confirm Dialog", By.xpath("//*[@class='modal-content' and contains(.,'抽選確定')]//*[text()[contains(.,'OK')]]"));

    // %s below is condition name
    private static final String TEMPLATE_XPATH_CONDITION_CHECKBOX = "//tr[td[ text() = '%s' ]]/td[1]/label/input";
    private static final String TEMPLATE_XPATH_LOTTERY_METHOD = "//tr[td[ text() = '%s' ]]/td[2]";
    private static final String TEMPLATE_XPATH_CONDITION_STATUS = "//tr[td[ text() = '%s' ]]/td[4]";
    private static final String TEMPLATE_XPATH_REGISTERED_DATE = "//tr[td[ text() = '%s' ]]/td[5]";
    private static final String TEMPLATE_XPATH_DOWNLOAD_BUTTON = "//tr[td[ text() = '%s' ]]/td[7]";
    private static final String TEMPLATE_XPATH_EDIT_ICON = "//tr[td[ text() = '%s' ]]/td[8]/button[1]";
    private static final String TEMPLATE_XPATH_TRASH_ICON = "//tr[td[ text() = '%s' ]]/td[8]/button[2]";

    public CpPlusLotteriesPage(Browser browser) {
        super(browser, createConditionButton);
    }

    public CpPlusLotteriesPageCreateCondition clickCreateConditionButton() {
        browser.click(createConditionButton);
        return new CpPlusLotteriesPageCreateCondition(browser);
    }

    public CpPlusLotteriesPage clickConfirmResultButton() {
        browser.click(confirmResultButton);
        return this;
    }

    public CpPlusLotteriesPage enterTokenNameOnConfirmDialog(String tokenName) {
        ExtendedBy tokenNameInput = new ExtendedBy("Token Name Input on Confirm Dialog", By.xpath(String.format(TEMPLATE_XPATH_TOKEN_NAME_INPUT_ON_CONFIRM_DIALOG, tokenName)));
        browser.typeTextElement(tokenNameInput, tokenName);
        return this;
    }

    public CpPlusLotteriesPage clickOkButtonOnConfirmDialog() {
        browser.click(okButtonOnConfirmDialog);
        return this;
    }

    public CpPlusLotteriesPage waitUntilResultConfirmBecomesOk() {
        browser.waitForElementVisibility(resultConfirmStatusOk, 120);
        return this;
    }

    public CpPlusLotteriesPage enterTokenNameOnConfirmDialog() {
        browser.click(tokenNameInputOnConfirmDialog);
        return this;
    }

    public CpPlusLotteriesPage clickConditionCheckboxOf(String conditionName) {
        ExtendedBy conditionCheckbox = new ExtendedBy("Condition Checkbox", By.xpath(String.format(TEMPLATE_XPATH_CONDITION_CHECKBOX, conditionName)));
        browser.click(conditionCheckbox);
        return this;
    }

    public String getLotteryMethodText(String conditionName) {
        ExtendedBy lotteryMethodText = new ExtendedBy("Lottery Method Text", By.xpath(String.format(TEMPLATE_XPATH_LOTTERY_METHOD, conditionName)));
        return browser.getElementText(lotteryMethodText);
    }

    public String getLotteryStatusText(String conditionName) {
        ExtendedBy lotteryStatusText = new ExtendedBy("Condition Status Text", By.xpath(String.format(TEMPLATE_XPATH_CONDITION_STATUS, conditionName)));
        return browser.getElementText(lotteryStatusText);
    }

    public String getRegisteredDate(String conditionName) {
        ExtendedBy registeredDate = new ExtendedBy("Registered Date Text", By.xpath(String.format(TEMPLATE_XPATH_REGISTERED_DATE, conditionName)));
        return browser.getElementText(registeredDate);
    }

    public void clickDownloadButton(String conditionName) {
        ExtendedBy downloadButton = new ExtendedBy("Download Button", By.xpath(String.format(TEMPLATE_XPATH_DOWNLOAD_BUTTON, conditionName)));
        browser.click(downloadButton);
    }

    public CpPlusLotteriesPageEdit clickEditIcon(String conditionName) {
        ExtendedBy editIcon = new ExtendedBy("Edit Icon", By.xpath(String.format(TEMPLATE_XPATH_EDIT_ICON, conditionName)));
        browser.waitForElementClickable(editIcon);
        browser.clickWithJs(browser.findElement(editIcon));
        return new CpPlusLotteriesPageEdit(browser);
    }

    public void clickTrashIcon(String conditionName) {
        ExtendedBy trashIcon = new ExtendedBy("Trash Icon", By.xpath(String.format(TEMPLATE_XPATH_TRASH_ICON, conditionName)));
        browser.click(trashIcon);
    }

    public String waitUntilStatusBecomes(String status, String conditionName, int delayInSeconds, int maxRetries) {
        RetryPolicyBuilder<String> builder = RetryPolicy.builder();
        builder.handleResultIf(string -> !string.equals(status));
        builder.withDelay(Duration.ofSeconds(delayInSeconds));
        builder.withMaxRetries(maxRetries);
        RetryPolicy<String> retryPolicy = builder.build();
        return Failsafe.with(retryPolicy).get(() -> getLotteryStatusText(conditionName));
    }

    public String waitUntilStatusBecomesSuccess(String conditionName, int delayInSeconds, int maxRetries) {
        return waitUntilStatusBecomes("作成完了", conditionName, delayInSeconds, maxRetries);
    }
}
