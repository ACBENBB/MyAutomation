package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep04InvestmentExperiences extends AbstractJpPage {

    private static final ExtendedBy step04Header = new ExtendedBy("Step 04 Head Line", By.xpath("//h1[text()[contains(., '投資経験などの入力')]]"));
    private static final ExtendedBy inputInvestmentExperienceButton = new ExtendedBy("Input Investment Experience Button", By.xpath("//*[text()[contains(.,'投資経験などの入力')]]"));
    private static final ExtendedBy incomeSourceSelect = new ExtendedBy("Income Source Select", By.xpath("//*[@name='incomeSource']"));
    private static final ExtendedBy jobSelect = new ExtendedBy("Job Select", By.xpath("//*[@name='job']"));
    private static final ExtendedBy annualIncomeSelect = new ExtendedBy("Annual Income Select", By.xpath("//*[@name='annualIncome']"));
    private static final ExtendedBy assetStatusSelect = new ExtendedBy("Asset Status Select", By.xpath("//*[@name='assetAmount']"));
    private static final ExtendedBy fundNatureSelect = new ExtendedBy("Fund Nature Select", By.xpath("//*[@name='assetType']"));
    private static final ExtendedBy investmentPurposeSelect = new ExtendedBy("Investment Purpose Select", By.xpath("//*[@name='investmentPurpose']"));
    private static final ExtendedBy investmentPolicySelect = new ExtendedBy("Investment Policy Select", By.xpath("//*[@name='investmentPolicy']"));
    private static final ExtendedBy experienceStockSelect = new ExtendedBy("Experience Stock Select", By.xpath("//*[@name='investmentExperiences.stock']"));
    private static final ExtendedBy experienceBondSelect = new ExtendedBy("Experience Bond Select", By.xpath("//*[@name='investmentExperiences.publicBond']"));
    private static final ExtendedBy experienceTrustSelect = new ExtendedBy("Experience Trust Select", By.xpath("//*[@name='investmentExperiences.investmentTrust']"));
    private static final ExtendedBy experienceFutureSelect = new ExtendedBy("Experience Future Select", By.xpath("//*[@name='investmentExperiences.futures']"));
    private static final ExtendedBy experienceForeignCurrencyDepositsSelect = new ExtendedBy("Experience Foreign Currency Deposits Select", By.xpath("//*[@name='investmentExperiences.foreignCurrencyDeposits']"));
    private static final ExtendedBy experienceForexMarginTradingSelect = new ExtendedBy("Experience Forex Margin Trading Select", By.xpath("//*[@name='investmentExperiences.forexMarginTrading']"));
    private static final ExtendedBy experienceMarginTradingSelect = new ExtendedBy("Experience Margin Trading Select", By.xpath("//*[@name='investmentExperiences.marginTrading']"));
    private static final ExtendedBy financialBusinessExperienceNoRadioButton = new ExtendedBy("Finance Business Experience No Radio Button", By.xpath("//*[@name='financialWorkExperience']"));
    private static final ExtendedBy financialBusinessExperienceYesRadioButton = new ExtendedBy("Finance Business Experience Yes Radio Button", By.xpath("//*[@name='financialBusinessExperience']"));
    private static final ExtendedBy politicallyExposedPersonSelect = new ExtendedBy("Politically Exposed Person Select", By.xpath("//*[@name='foreignPoliticallyExposedPerson']"));
    private static final ExtendedBy backButton = new ExtendedBy("Back Button", By.xpath("//button/*[text()[contains(.,'戻る')]]"));
    private static final ExtendedBy nextStepButton = new ExtendedBy("Next Step Button", By.xpath("//button/*[text()[contains(.,'次のステップに進む')]]"));

    public MaruiRegistrationStep04InvestmentExperiences(Browser browser) {
        super(browser, step04Header);
    }

    public void clickInputInvestmentExperienceButton() {
        browser.click(inputInvestmentExperienceButton);
    }

    public MaruiRegistrationStep04InvestmentExperiences selectIncomeSource(String value) {
        browser.selectElementByVisibleText(incomeSourceSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectJob(String value) {
        browser.selectElementByVisibleText(jobSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectAnnualIncome(String value) {
        browser.selectElementByVisibleText(annualIncomeSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectAssetStatus(String value) {
        browser.selectElementByVisibleText(assetStatusSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectFundNature(String value) {
        browser.selectElementByVisibleText(fundNatureSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectInvestmentPurpose(String value) {
        browser.selectElementByVisibleText(investmentPurposeSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectInvestmentPolicy(String value) {
        browser.selectElementByVisibleText(investmentPolicySelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectExperienceStock(String value) {
        browser.selectElementByVisibleText(experienceStockSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectExperienceBond(String value) {
        browser.selectElementByVisibleText(experienceBondSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectExperienceTrust(String value) {
        browser.selectElementByVisibleText(experienceTrustSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectExperienceFuture(String value) {
        browser.selectElementByVisibleText(experienceFutureSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectExperienceForeignCurrencyDeposits(String value) {
        browser.selectElementByVisibleText(experienceForeignCurrencyDepositsSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectExperienceForexMarginTrading(String value) {
        browser.selectElementByVisibleText(experienceForexMarginTradingSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectExperienceMarginTrading(String value) {
        browser.selectElementByVisibleText(experienceMarginTradingSelect, value);
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences clickFinancialBusinessExperienceRadioButton(String value) {
        if (value.equals("無し")) {
            browser.click(financialBusinessExperienceNoRadioButton);
        } else {
            browser.click(financialBusinessExperienceYesRadioButton);
        }
        return this;
    }

    public MaruiRegistrationStep04InvestmentExperiences selectPoliticallyExposedPerson(String value) {
        browser.selectElementByVisibleText(politicallyExposedPersonSelect, value);
        return this;
    }

    public MaruiRegistrationStep05Questionnaire clickNextButton() {
        browser.click(nextStepButton);
        browser.waitForElementToVanish(step04Header);
        return new MaruiRegistrationStep05Questionnaire(browser);
    }

    public MaruiRegistrationStep05Questionnaire clickNextButton(boolean retry) {
        return RetryOnExceptions.retryFunction(this::clickNextButton, ()-> null, retry);
    }

    public boolean backButtonIsEnabled() {
        return browser.isElementEnabled(backButton);
    }
}
