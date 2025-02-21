package io.securitize.pageObjects.investorsOnboarding.securitizeId;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.securitize.infra.reporting.MultiReporter.info;

public class SecuritizeIdInvestorHomePage extends AbstractPage {

    private static final ExtendedBy registerWalletButton = new ExtendedBy("Register wallet button", By.xpath("//button[contains(@class, 'primary') and contains(text(), 'wallet')]"));
    private static final ExtendedBy completeInvestorRegistrationButton = new ExtendedBy("Complete your investor information button", By.xpath("//button[contains(@class, 'primary')]"));
    private static final ExtendedBy totalAmountInvestedField = new ExtendedBy("Total amount invested field", By.xpath("//p[@class='summary-amount']"));
    //private static final ExtendedBy depositsAndIssuanceText = new ExtendedBy("investor dashaboard - Deposits & Issuance - text validator", By.xpath("//*[contains(text(),'Deposits & Issuance')]"));
    private static final ExtendedBy investTermsAndBonus = new ExtendedBy("investor dashaboard - Investment Terms & Bonus - text validator", By.xpath("  //h2[contains(text(),' Investment Terms')]"));
    private static final ExtendedBy tokenAndPayoutsButton = new ExtendedBy("tokens and payouts button", By.xpath("//a[contains(@href,'/token')]"));
    private static final ExtendedBy tokensCount = new ExtendedBy("tokens count", By.xpath("//img[@alt = \"Securitize Token\"]/.."));
    private static final ExtendedBy activeStage = new ExtendedBy("Active stage", By.xpath("//div[contains(@class,'col-lg box active')]"));
    private static final ExtendedBy pendingStage = new ExtendedBy("Pending stage", By.xpath("//div[contains(@class,'col-lg box pending')]"));
    private static final ExtendedBy stageHeader = new ExtendedBy("Stage header", By.xpath(".//h2"));
    private static final ExtendedBy getFundedInvestmentAmount = new ExtendedBy("Total investment funded", By.xpath("//*[contains(@class,'summary-amount')]"));

    public SecuritizeIdInvestorHomePage(Browser browser) {
        super(browser, investTermsAndBonus);
    }



    public void clickRegisterWalletButtonIfVisible() {
        browser.setImplicitWaitTimeout(2);
        if (browser.isElementVisible(registerWalletButton)) {
            info(registerWalletButton.getDescription() + " is visible - clicking it now...");
            clickRegisterWalletButton();
        } else {
            info(registerWalletButton.getDescription() + " is not visible - no need to click it");
        }
        browser.resetImplicitWaitTimeout();
    }

    private void clickRegisterWalletButton() {
        browser.clickAndWaitForElementToVanish(registerWalletButton);
    }



    public SecuritizeIdInvestorRegistration01InvestorType securitizeIdClickCompleteInvestorInformation() {
        browser.clickAndWaitForElementToVanish(completeInvestorRegistrationButton);
        return new SecuritizeIdInvestorRegistration01InvestorType(browser);
    }

    public int getAmountInvested() {
        browser.waitForElementTextNotEmpty(totalAmountInvestedField);
        String result = browser.getElementText(totalAmountInvestedField);
        return Integer.parseInt(result);
    }

    public SecuritizeIdInvestorTokensPayoutsPage switchToTokensAndPayoutsScreen() {
        browser.click(tokenAndPayoutsButton);
        return new SecuritizeIdInvestorTokensPayoutsPage(browser);
    }

    public SecuritizeIdInvestorTokensPayoutsPage switchToTokensAndPayoutsScreen(boolean isLegacyFlow) {
        browser.click(tokenAndPayoutsButton);
        return new SecuritizeIdInvestorTokensPayoutsPage(browser, isLegacyFlow);
    }

    public int getNumberOfTokens() {
        String tokensCountAsString = browser.getElementText(tokensCount);
        return Integer.parseInt(tokensCountAsString);
    }

    public String getActiveStage() {
        WebElement activeStageHeader = browser.findElementInElement(activeStage, stageHeader);
        return browser.getElementText(activeStageHeader, "active stage header");
    }

    public String getPendingStage() {
        WebElement activeStageHeader = browser.findElementInElement(pendingStage, stageHeader);
        return browser.getElementText(activeStageHeader, "pending stage header");
    }

    public int getFundedInvestmentAmount() {
        String resultAsString = browser.getElementText(getFundedInvestmentAmount);
        return RegexWrapper.stringToInteger(resultAsString);
    }
}
