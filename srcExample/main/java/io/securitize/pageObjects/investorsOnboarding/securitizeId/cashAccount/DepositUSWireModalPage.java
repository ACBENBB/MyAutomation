package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

import static io.securitize.infra.reporting.MultiReporter.info;

public class DepositUSWireModalPage extends AbstractPage {

    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy bankNameTitle = new ExtendedBy("Bank name title", By.cssSelector("[data-test-id='ca-depositinfo-bankname-title']"));
    private static final ExtendedBy bankNameValue = new ExtendedBy("Bank name value", By.cssSelector("[data-test-id='ca-depositinfo-bankname-value']"));
    private static final ExtendedBy routingNumberTitle = new ExtendedBy("Routing Number title", By.cssSelector("[data-test-id='ca-depositinfo-routingnumber-title']"));
    private static final ExtendedBy routingNumberValue = new ExtendedBy("Routing Number value", By.cssSelector("[data-test-id='ca-depositinfo-routingnumber-value']"));
    private static final ExtendedBy bankAddressTitle = new ExtendedBy("Bank Address title", By.cssSelector("[data-test-id='ca-depositinfo-bankinfo-title']"));
    private static final ExtendedBy bankAddressValue = new ExtendedBy("Bank Address value", By.cssSelector("[data-test-id='ca-depositinfo-bankinfo-value']"));
    private static final ExtendedBy accountRecipientTitle = new ExtendedBy("Account Recipient title", By.cssSelector("[data-test-id='ca-depositinfo-holderinfo-title']"));
    private static final ExtendedBy accountRecipientValue = new ExtendedBy("Account Recipient value", By.cssSelector("[data-test-id='ca-depositinfo-holderinfo-value']"));
    private static final ExtendedBy recipientAddressTitle = new ExtendedBy("Recipient Address title", By.cssSelector("[data-test-id='ca-depositinfo-beneficiaryinfo-title']"));
    private static final ExtendedBy recipientAddressValue = new ExtendedBy("Recipient Address value", By.cssSelector("[data-test-id='ca-depositinfo-beneficiaryinfo-value']"));
    private static final ExtendedBy recipientAccountNumberTitle = new ExtendedBy("Recipient Account Number title", By.cssSelector("[data-test-id='ca-depositinfo-accountnumber-title']"));
    private static final ExtendedBy recipientAccountNumberValue = new ExtendedBy("Recipient Account Number value", By.cssSelector("[data-test-id='ca-depositinfo-accountnumber-value']"));
    private static final ExtendedBy referenceNumberTitle = new ExtendedBy("Reference Number title", By.cssSelector("[data-test-id='ca-depositinfo-referencenumber-title']"));
    private static final ExtendedBy referenceNumberValue = new ExtendedBy("Reference Number value", By.cssSelector("[data-test-id='ca-depositinfo-referencenumber-value']"));
    private static final ExtendedBy understoodButton = new ExtendedBy("Understood Button", By.cssSelector("[data-test-id='ca-depositinfo-submit-btn']"));




    public DepositUSWireModalPage(Browser browser) {
        super(browser, modalBody);
    }


    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return ! browser.findElements(modalBody).isEmpty();
    }

    public void clickUnderstoodBtn() {
        //browser.click gives a Timeout exception that's why browser.findElement(understoodButton).click is used.
        info("About to Click on element: " + understoodButton.getDescription());
        browser.findElement(understoodButton).click();
    }


    public String getBankNameTitle() {
        return browser.getElementText(bankNameTitle);
    }

    public String getBankNameValue() {
        return browser.getElementText(bankNameValue);
    }

    public String getRoutingNumberTitle() {
        return browser.getElementText(routingNumberTitle);
    }

    public String getRoutingNumberValue() {
        return browser.getElementText(routingNumberValue);
    }

    public String getBankAddressTitle() {
        return browser.getElementText(bankAddressTitle);
    }

    public String getBankAddressValue() {
        return browser.getElementText(bankAddressValue);
    }

    public String getAccountRecipientTitle() {
        return browser.getElementText(accountRecipientTitle);
    }

    public String getAccountRecipientValue() {
        return browser.getElementText(accountRecipientValue);
    }
    public String getRecipientAddressTitle() {
        return browser.getElementText(recipientAddressTitle);
    }

    public String getRecipientAddressValue() {
        return browser.getElementText(recipientAddressValue);
    }

    public String getRecipientAccountNumberTitle() {
        return browser.getElementText(recipientAccountNumberTitle);
    }

    public String getRecipientAccountNumberValue() {
        return browser.getElementText(recipientAccountNumberValue);
    }

    public String getReferenceNumberTitle() {
        return browser.getElementText(referenceNumberTitle);
    }

    public String getReferenceNumberValue() {
        return browser.getElementText(referenceNumberValue);
    }

}
