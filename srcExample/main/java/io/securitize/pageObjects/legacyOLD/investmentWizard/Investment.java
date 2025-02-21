package io.securitize.pageObjects.legacyOLD.investmentWizard;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Investment extends AbstractInvestmentWizardPage {

    private ExtendedBy investmentAmount = new ExtendedBy("Investment amount field", By.id("invest-eth"));
    private ExtendedBy investmentAmountError = new ExtendedBy("Investment amount error message", By.xpath("//span[contains(@class, 'invalid-feedback')]"));
    private ExtendedBy tokensAmount = new ExtendedBy("tokens amount field", By.xpath("//h2[@class='wizard-kyc-tokens my-0']"));

    Investment(Browser browser) {
        super(browser);
    }

    public Investment setInvestmentAmount(int amount) {
        // manually delete old value first - regular clear doesn't seem to work here
        WebElement element = browser.findElement(investmentAmount);
        Actions keyAction = new Actions(Browser.getDriver());
        element.sendKeys(Keys.HOME);
        keyAction.keyDown(Keys.SHIFT).sendKeys(Keys.END).perform();
        element.sendKeys(Keys.BACK_SPACE);

        browser.typeTextElement(investmentAmount, amount+"");
        browser.sendKeysElement(investmentAmount, "<TAB>", Keys.TAB);
        return this;
    }

    public double getTokensAmount() {
        browser.waitForElementVisibility(tokensAmount);
        String value = browser.getElementText(tokensAmount);
        return Double.parseDouble(value);
    }

    @Override
    public Subscription clickContinue() {
        super.clickContinue();
        return new Subscription(browser);
    }

    public boolean isMinimumAmountMessageVisible() {
        try {
            browser.setImplicitWaitTimeout(2);
            return browser.findElement(investmentAmountError).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } finally {
            browser.resetImplicitWaitTimeout();
        }
    }
}
