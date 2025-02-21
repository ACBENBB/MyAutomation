package io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class SecuritizePublicSitePrimaryMarketSecurityTokenMarketUSD extends AbstractPage {

    private static final ExtendedBy investInHamiltonLaneEOVFundHeader = new ExtendedBy("Hamilton Lane EOV Securitize Fund - Text", By.xpath("//*[contains(text() ,'Hamilton Lane EOV Securitize Fund')]"));
    private static final ExtendedBy opportunityCalculatorTextBox = new ExtendedBy("Opportunity calculator text box", By.xpath("//*[contains(@id ,'opportunity-calculator-input')]"));
    private static final ExtendedBy investButton = new ExtendedBy("Opportunity Invest button", By.xpath("//button[contains(@id, 'btn-invest')]"));
    private static final ExtendedBy opportunityCookieAcceptButton = new ExtendedBy("Security Token Market RegCF - cookie accept button", By.id("hs-eu-confirmation-button"));
    private static final ExtendedBy signupAndInvestButton = new ExtendedBy("Sign up and Invest", By.xpath("//button/*[contains(text() ,'Sign up and Invest')]"));

    public SecuritizePublicSitePrimaryMarketSecurityTokenMarketUSD(Browser browser) {
        super(browser, investInHamiltonLaneEOVFundHeader);
    }

    public AbstractPage clickAcceptCookies() {
        browser.waitForElementClickable(opportunityCookieAcceptButton, 2);
        browser.click(opportunityCookieAcceptButton, true);
        return this;
    }


    public void insertInvestmentToCalculator() {
        Optional<WebElement> element = browser.findFirstVisibleElementQuick(opportunityCalculatorTextBox,15);
        if(element.isPresent()){
            browser.click(element.get(),opportunityCalculatorTextBox.getDescription());
            browser.sendKeysElement(opportunityCalculatorTextBox, "keys" , "100");
        }
        else{
            MultiReporter.errorAndStop("Couldn't find Investment Calculator.", true);
        }
    }


    public void clickInvestButton() {
        browser.findElement(investButton).click();
    }

    public void clickSignUpAndInvestButton() {
        browser.findElement(signupAndInvestButton).click();
    }
}