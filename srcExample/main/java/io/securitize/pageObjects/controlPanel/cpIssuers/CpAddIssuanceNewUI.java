package io.securitize.pageObjects.controlPanel.cpIssuers;

import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.web3j.abi.datatypes.Int;

public class CpAddIssuanceNewUI extends AbstractPage {


    private static final ExtendedBy issueToTokenWalletSelector = new ExtendedBy("Issue to - token wallet selector", By.xpath("//div[@id='mui-component-select-issueTo']"));
    private static final String issueToPattern = "//li[contains(text(),'%s')]";
    private static final ExtendedBy issuanceDescription = new ExtendedBy("issuance description", By.xpath("//input[@placeholder='Description']"));
    private static final ExtendedBy customAmountRadioButton = new ExtendedBy("custom amount radio button", By.xpath("//input[@value='custom_amount']"));
    private static final ExtendedBy customAmountField = new ExtendedBy("custom amount field", By.xpath("//input[@placeholder='Custom token amount']"));
    private static final ExtendedBy okButton = new ExtendedBy("ok button - confirm issuance", By.xpath("//button[contains(text(), 'OK')]"));
    private static final ExtendedBy totalFunded = new ExtendedBy("total funded in issuance modal", By.xpath("//div[contains(text(),'Total amount funded')]/following-sibling::div"));
    private static final ExtendedBy pricePerUnit = new ExtendedBy("price per unit in issuance modal", By.xpath("//div[contains(text(),'Price per unit')]/following-sibling::div"));

    public CpAddIssuanceNewUI(Browser browser) {
        super(browser, issueToTokenWalletSelector);
    }

    public boolean addIssuanceDetails(String issueTo, double tokenAmount, double investmentAmount, String currency, String description) {
        boolean isPricePerUnitCorrect = false;
        browser.click(issueToTokenWalletSelector);
        ExtendedBy issueToOption = setValueInXpath(issueToPattern, issueTo, "Issue to " + issueTo + " option");
        browser.click(issueToOption);
        browser.typeTextElement(issuanceDescription, description);
        browser.clickWithJs(browser.findElement(customAmountRadioButton));
        browser.waitForElementClickable(customAmountField);
        browser.typeTextElement(customAmountField, String.valueOf(tokenAmount));
        String actualTotalFunded = browser.getElementText(totalFunded);

        boolean isTotalFundedCorrect = RegexWrapper.isStringMatch(actualTotalFunded, String.valueOf(investmentAmount));
        boolean isCurrencyCorrect = RegexWrapper.isStringMatch(actualTotalFunded, currency);
        if (isTotalFundedCorrect && isCurrencyCorrect){
            String actualPricePerUnit = browser.getElementText(pricePerUnit);
            double expectedPricePerUnit = (double)investmentAmount / tokenAmount;
            String formattedResult = String.format("%.2f", expectedPricePerUnit);
            isPricePerUnitCorrect = RegexWrapper.isStringMatch(String.valueOf(formattedResult), actualPricePerUnit);
            browser.clickAndWaitForElementToVanish(okButton);
        }
        return isPricePerUnitCorrect;
    }

}
