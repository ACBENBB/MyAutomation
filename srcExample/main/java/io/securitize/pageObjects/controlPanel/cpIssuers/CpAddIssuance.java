package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CpAddIssuance extends AbstractPage {

    private static final ExtendedBy issueToTokenWalletSelector = new ExtendedBy("Issue to - token wallet selector", By.name("tokenWalletId"));
    private static final ExtendedBy reasonField = new ExtendedBy("reason field", By.name("reason"));
    private static final ExtendedBy issuanceAmountField = new ExtendedBy("issuance amount field", By.name("issueAmount"));

    private static final String issuanceConfirmationTextXpathTemplate = "//div[@role='document']//p[contains(text(),'%s') and contains(text(),'%s') and contains(text(),'%s') and contains(text(),'%s')]";


    private static final ExtendedBy okButton = new ExtendedBy("ok button", By.xpath("//button[text()='OK']"));
    private static final ExtendedBy confirmationOkButton = new ExtendedBy("confirmation Ok button", By.xpath("//button[text()='OK' and not(@disabled)]"));

    public CpAddIssuance(Browser browser) {
        super(browser, issueToTokenWalletSelector);
    }

    public CpAddIssuance selectTokenWalletId(String walletName, String walletAddress) {
        String value = walletName + ": " + walletAddress.toLowerCase();
        browser.selectElementByVisibleText(issueToTokenWalletSelector, value);
        return this;
    }

    public CpAddIssuance typeReason(String reason) {
        browser.typeTextElement(reasonField, reason);
        return this;
    }

    public CpAddIssuance typeIssuanceAmount(int amount) {
        browser.typeTextElement(issuanceAmountField, amount + "");
        return this;
    }

    public void clickOk(String issuerFirstName, String issuerMiddleName, String issuerLastName, int issuanceAmount) {
        WebElement firstOkButton = browser.click(okButton);
        // wait for confirmation message to show up
        String xPathString = String.format(issuanceConfirmationTextXpathTemplate, issuerFirstName, issuerMiddleName, issuerLastName, issuanceAmount);
        ExtendedBy confirmationMessage = new ExtendedBy("issuance confirmation text", By.xpath(xPathString));
        browser.findElement(confirmationMessage).isDisplayed();
        browser.clickAndWaitForElementToVanish(confirmationOkButton);
        browser.waitForElementToVanish(firstOkButton, "first ok button in the dialog");
    }
}