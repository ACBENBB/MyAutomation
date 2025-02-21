package io.securitize.pageObjects.investorsOnboarding.securitizeId.assetDetails;

import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import static io.securitize.infra.reporting.MultiReporter.info;

public class AssetDetailsPage extends AbstractPage {

    private static final ExtendedBy arrowBackButton = new ExtendedBy("Transfer Agent - Asset details page - Icon arrow back button", By.xpath("//span[contains(@class, 'asset-details__back-btn') and contains(text(), 'Back')]"));
    private static final ExtendedBy windowMessageTitle = new ExtendedBy("Redemption window message title", By.xpath("//*[contains(text(), 'A Redemption window is now open!')]"));
    private static final ExtendedBy redeemButton = new ExtendedBy("Redemption window button", By.xpath("//*[text()='Redeem Units']"));
    private static final ExtendedBy transactionsTabButton = new ExtendedBy("Transactions tab", By.xpath("//span[text()='Transactions']/ancestor::button"));
    private static final ExtendedBy payoutsTabButton = new ExtendedBy("Payouts tab", By.xpath("//span[text()='Payouts']/ancestor::button"));
    private static final ExtendedBy historyTable = new ExtendedBy("Transactions Table", By.xpath("//*[@class='d-block']//table"));
    private static final String openWindowRedemptionModalDescription = "//*[contains(text(), 'Now you can sell back your tokens to Transfer Agent Team Issuer') and contains(text(), ' at %s per token.')]";
    private static final String latestTransactionRow = "//*[contains(text(), '%s')]/ancestor::tr//*[contains(text(), '%s')]";
    private static final String payoutRow = "//*[contains(text(), '%s')]/ancestor::tr//*[contains(text(), 'Redemption')]/ancestor::tr//*[contains(text(), '%s')]";

    public AssetDetailsPage(Browser browser) {
        super(browser, arrowBackButton);
    }
    public boolean isRedemptionTitlePresent() {
        boolean isPresent = true;
        isPresent = browser.isElementPresentOrVisible(windowMessageTitle);
        return isPresent;
    }
    public boolean isRedemptionDescriptionPresent(String tokenPrice) {
        boolean isPresent = true;
        isPresent = browser.isElementPresentOrVisible(new ExtendedBy("Redemption Modal Description", By.xpath(String.format(openWindowRedemptionModalDescription, tokenPrice))));
        return isPresent;
    }
    public boolean isRedeemButtonPresent() {
        boolean isPresent = true;
        isPresent = browser.isElementPresentOrVisible(redeemButton);
        return isPresent;
    }
    public void clickOnRedeemButton() {
        browser.click(redeemButton);
    }
    public void clickOnPayoutsTab() {
        WebElement payoutsTab = browser.findElement(payoutsTabButton);
        browser.executeScript("arguments[0].click();", payoutsTab);
        browser.waitForElementVisibility(historyTable);
    }
    public void clickOnTransactionsTab() {
        WebElement payoutsTab = browser.findElement(transactionsTabButton);
        browser.executeScript("arguments[0].click();", payoutsTab);
        browser.waitForElementVisibility(historyTable);
    }
    public void validateNewPayoutRowIsVisible(String currDate, String payment) {
        String[] params = new String[]{currDate, payment};
        browser.waitForElementVisibility(new ExtendedBy("Today's Redemption transaction row", By.xpath(String.format(payoutRow, (Object[]) params))));
    }

    public boolean validateTransactionIsPresent(String tokenPrice) {
        boolean isPresent = false;
        SoftAssert softAssert = new SoftAssert();
        String[] data = new String[]{DateTimeUtils.currentDate("dd MMM yyyy"), "Redemption"};
        info("Current date and format is: " + DateTimeUtils.currentDate("dd MMM yyyy"));
        //The following assertions may be re used in the future
        //softAssert.assertTrue(isRedemptionTitlePresent(), "Redemption title was not present");
        //softAssert.assertTrue(isRedemptionDescriptionPresent(tokenPrice), "Redemption description was not present");
        softAssert.assertTrue(isRedeemButtonPresent(), "Redemption button was not present");
        softAssert.assertAll();
        WebElement transactionsTab = browser.findElement(transactionsTabButton);
        browser.executeScript("arguments[0].click();", transactionsTab);
        try {
            browser.waitForElementVisibility(new ExtendedBy("Today's Redemption transaction row", By.xpath(String.format(latestTransactionRow, (Object[]) data))));
            isPresent = true;
        } catch (TimeoutException e) {

        }
        return isPresent;
    }

    public boolean validateIssuanceIsPresent(String tokenPrice) {
        boolean isPresent = false;
        String[] data = new String[]{DateTimeUtils.currentDate("dd MMM yyyy"), "Issuance"};
        info("Current date and format is: " + DateTimeUtils.currentDate("dd MMM yyyy"));
        WebElement transactionsTab = browser.findElement(transactionsTabButton);
        browser.executeScript("arguments[0].click();", transactionsTab);
        try {
            browser.waitForElementVisibility(new ExtendedBy("Today's Issuance transaction row", By.xpath(String.format(latestTransactionRow, (Object[]) data))));
            isPresent = true;
        } catch (TimeoutException e) {

        }
        return isPresent;
    }

}
