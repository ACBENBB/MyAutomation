package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CpAddRound extends AbstractPage {

    private static final ExtendedBy addRoundHeader = new ExtendedBy("Add Round Header", By.xpath("//header//h5[contains(text(), 'Add Round')]"));
    private static final ExtendedBy roundNameTextField = new ExtendedBy("round name text field", By.xpath("//input[@name='name']"));
    private static final ExtendedBy activeRoundSwitch = new ExtendedBy("active round switch", By.xpath("//td[contains(text(),'Active:')]/following-sibling::td/label"));
    private static final ExtendedBy minimumInvestmentFiat = new ExtendedBy("minimum investment fiat", By.xpath("//input[@name='minInvestmentFiat']"));
    private static final ExtendedBy minimumInvestmentCrypto = new ExtendedBy("minimum investment crypto", By.xpath("//input[@name='minInvestmentCrypto']"));
    private static final ExtendedBy startDatePicker = new ExtendedBy("start date picker", By.xpath("//input[@name='startsAt']"));
    private static final ExtendedBy endDatePicker = new ExtendedBy("end date picker", By.xpath("//input[@name='endsAt']"));
    private static final ExtendedBy issuanceDatePicker = new ExtendedBy("issuance date picker", By.xpath("//input[@name='issuanceDate']"));
    private static final ExtendedBy tokenValueField = new ExtendedBy("token value field", By.xpath("//input[@name='Token Value']"));
    private static final ExtendedBy textField = new ExtendedBy("text field", By.xpath("//div[@class='ql-editor ql-blank']/p"));
    private static final ExtendedBy addRoundButton = new ExtendedBy("ok button", By.xpath("//button[text()='Add Round']"));
    private static final ExtendedBy editRoundButton = new ExtendedBy("edit button", By.xpath("//button[text()='Edit Round']"));

    public CpAddRound(Browser browser) {
        super(browser, roundNameTextField);
    }

    public CpAddRound setRoundName(String value) {
        browser.typeTextElement(roundNameTextField, value);
        return this;
    }

    public CpAddRound switchRoundToActive() {
        WebElement switchActivate = browser.findElement(activeRoundSwitch);
        browser.clickWithJs(switchActivate);
        return this;
    }

    public CpAddRound setMinimumInvestmentFiat(String value) {
        browser.typeTextElement(minimumInvestmentFiat, value);
        return this;
    }

    public CpAddRound setMinimumInvestmentCrypto(String value) {
        browser.typeTextElement(minimumInvestmentCrypto, value);
        return this;
    }

    public CpAddRound setStartDate(String value) {
        WebElement startDateElement = browser.findElement(startDatePicker);
        browser.removeElementAttribute(startDateElement, "readonly", "false");
        browser.typeTextElement(startDatePicker, value);
        return this;
    }

    public CpAddRound setEndDate(String value) {
        WebElement startDateElement = browser.findElement(endDatePicker);
        browser.removeElementAttribute(startDateElement, "readonly", "false");
        browser.typeTextElement(endDatePicker, value);
        return this;
    }

    public CpAddRound setIssuanceDate(String value) {
        WebElement startDateElement = browser.findElement(issuanceDatePicker);
        browser.removeElementAttribute(startDateElement, "readonly", "false");
        browser.typeTextElement(issuanceDatePicker, value);
        return this;
    }

    public CpAddRound setTokenValue(String value) {
        browser.typeTextElement(tokenValueField, value);
        return this;
    }

    public CpAddRound setText(String value) {
        browser.typeTextElement(textField, value);
        return this;
    }

    public void clickAddRoundButton() {
        browser.clickAndWaitForElementToVanish(addRoundButton);
    }

    public void clickEditRoundButton() {
        browser.clickAndWaitForElementToVanish(editRoundButton);
    }

}
