package io.securitize.pageObjects.investorsOnboarding.securitizeId.cashAccount;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DepositUSDCModalPage extends AbstractPage {

    private static final ExtendedBy closeButton = new ExtendedBy("Close Button", By.cssSelector("[class*='RightSectionContainer']"));
    private static final ExtendedBy modalBody = new ExtendedBy("Modal Body", By.cssSelector("[class^='style__BodyContainer__']"));
    private static final ExtendedBy modalTitle = new ExtendedBy("USDC Modal title", By.xpath("//h5[contains(text(), 'Deposit USDC')]"));
    private static final ExtendedBy topWarningMessage = new ExtendedBy("Top Warning Message", By.xpath("//span[contains(text(), 'Ethereum blockchain')]"));
    private static final ExtendedBy scanQRTextTitle = new ExtendedBy("Scan QR Text", By.xpath("//div[contains(text(), 'QR')]"));

    private static final ExtendedBy addressTitle = new ExtendedBy("Wallet Address title", By.xpath("//div[contains(text(), 'Address')]"));
    private static final ExtendedBy addressValue = new ExtendedBy("Wallet Address value", By.cssSelector("[class*='CopyButton']"));

    private static final ExtendedBy conversionFeeTitle = new ExtendedBy("Conversion Fee title", By.xpath("//div[contains(text(), 'Conversion fee')]"));
    private static final ExtendedBy conversionFeeValue = new ExtendedBy("Conversion Fee value", By.cssSelector("div[data-test-id='ca-transfermodaldepositcoinstep-conversionfee-value']"));
    private static final ExtendedBy bottomMessage = new ExtendedBy("Bottom message text", By.xpath("//span[contains(text(), 'instantaneous')]"));
    private static final ExtendedBy rateDateTime = new ExtendedBy("Rate DateTime", By.cssSelector("[class*='rate'] [class*='date']"));
    private static final ExtendedBy rateValue = new ExtendedBy("Rate Value", By.cssSelector("[class*='rate'] [class*='conversion']"));



    public DepositUSDCModalPage(Browser browser) {
        super(browser, modalBody);
    }


    public boolean isPageLoaded() {
        browser.waitForPageStable();
        return ! browser.findElements(modalBody).isEmpty();
    }

    public void clickClose() {
        browser.findElement(closeButton).click();
    }
    public String getModalTitle() {
        return browser.getElementText(modalTitle);
    }

    public String getTopWarningMessage() {
        return browser.getElementText(topWarningMessage);
    }

    public String getScanQRTextTitle() {
        return browser.getElementText(scanQRTextTitle);
    }

    public String getAddressTitle() {
        return browser.getElementText(addressTitle);
    }

    public String getAddressValue() {
        return browser.getElementText(addressValue);
    }

    public String getConversionFeeTitle() {
        return browser.getElementText(conversionFeeTitle);
    }

    public String getConversionFeeValue() {
        return browser.getElementText(conversionFeeValue);
    }

    public String getBottomMessage() {
        return browser.getElementText(bottomMessage);
    }

    public String getRateDateTime() {
        return browser.getElementText(rateDateTime);
    }

    public String getRateValue() {
        return browser.getElementText(rateValue);
    }

    public LocalDateTime getRateDateTimeObject() {
        String date = getRateDateTime();
        LocalDateTime rateDateTime = null;
        try {
            rateDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("'Rate' (MMM dd, yyyy, hh:mm a)"));
        } catch (Exception ignored) {
        }
        return rateDateTime;
    }
}
