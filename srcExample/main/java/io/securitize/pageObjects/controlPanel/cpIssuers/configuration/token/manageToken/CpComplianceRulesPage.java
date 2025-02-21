package io.securitize.pageObjects.controlPanel.cpIssuers.configuration.token.manageToken;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpComplianceRulesPage extends AbstractPage {

    private static final ExtendedBy complianceTypeDropDown = new ExtendedBy("Compliance Type Dropdown", By.xpath("//div[@id='selectCompliance']/select"));
    private static final ExtendedBy totalInvestorLimitField = new ExtendedBy("Total Investors Limit Field", By.xpath("(//*[@class='row variable-input-row'])[2]//input"));
    private static final ExtendedBy minUsTokensField = new ExtendedBy("Min US Tokens Field", By.xpath("(//*[@class='row variable-input-row'])[3]//input"));
    private static final ExtendedBy minEuTokensField = new ExtendedBy("Min EU Tokens Field", By.xpath("(//*[@class='row variable-input-row'])[4]//input"));
    private static final ExtendedBy usInvestorLimitField = new ExtendedBy("US Investors Limit Field", By.xpath("(//*[@class='row variable-input-row'])[5]//input"));
    private static final ExtendedBy usAccreditedInvestorLimitField = new ExtendedBy("US Accredited Investors Limit Field", By.xpath("(//*[@class='row variable-input-row'])[6]//input"));
    private static final ExtendedBy nonAccreditedInvestorLimitField = new ExtendedBy("Non Accredited Investors Limit Field", By.xpath("(//*[@class='row variable-input-row'])[7]//input"));
    private static final ExtendedBy maxUsInvestorsPercentageField = new ExtendedBy("Max US Investors Percentage Field", By.xpath("(//*[@class='row variable-input-row'])[8]//input"));
    private static final ExtendedBy blockFlowbackEndTimeField = new ExtendedBy("Block Flowback End Time Field", By.xpath("(//*[@class='row variable-input-row'])[9]//input"));
    private static final ExtendedBy nonUsLockPeriodField = new ExtendedBy("Non US Lock Period Field", By.xpath("(//*[@class='row variable-input-row'])[10]//input"));
    private static final ExtendedBy minimumTotalInvestorsField = new ExtendedBy("Minimum Total Investors Field", By.xpath("(//*[@class='row variable-input-row'])[11]//input"));
    private static final ExtendedBy minimumHoldingsPerInvestorField = new ExtendedBy("Minimum Holdings Per Investor Field", By.xpath("(//*[@class='row variable-input-row'])[12]//input"));
    private static final ExtendedBy maximumHoldingsPerInvestorField = new ExtendedBy("Maximum Holdings Per Investor Field", By.xpath("(//*[@class='row variable-input-row'])[13]//input"));
    private static final ExtendedBy euRetailInvestorsLimitField = new ExtendedBy("EU Retail Investors Limit Field", By.xpath("(//*[@class='row variable-input-row'])[14]//input"));
    private static final ExtendedBy numberOfAuthorizedSecuritiesField = new ExtendedBy("Number of authorized securities Field", By.xpath("(//*[@class='row variable-input-row'])[15]//input"));
    private static final ExtendedBy usLockPeriodField = new ExtendedBy("US Lock Period Field", By.xpath("(//*[@class='row variable-input-row'])[16]//input"));
    private static final ExtendedBy jpInvestorLimitField = new ExtendedBy("JP Investors Limit Field", By.xpath("(//*[@class='row variable-input-row'])[17]//input"));
    private static final ExtendedBy usForceFullTransferDropdown = new ExtendedBy("US Force Full Transfer Dropdown", By.xpath("(//*[@class='row variable-input-row'])[18]/div/div"));
    private static final ExtendedBy worldWideForceFullTransferDropdown = new ExtendedBy("World Wide Force Full Transfer Dropdown", By.xpath("(//*[@class='row variable-input-row'])[19]/div/div"));
    private static final ExtendedBy forceAccreditedDropdown = new ExtendedBy("Force Accredited Dropdown", By.xpath("(//*[@class='row variable-input-row'])[20]/div/div"));
    private static final ExtendedBy forceAccreditedUSDropdown = new ExtendedBy("Force Accredited US Dropdown", By.xpath("(//*[@class='row variable-input-row'])[21]/div/div"));
    private static final ExtendedBy continueBtn = new ExtendedBy("Continue Btn", By.xpath("//span[contains(text(), 'Continue')]"));

    public CpComplianceRulesPage(Browser browser) {
        super(browser);
    }

    public void selectComplianceType(String complianceType) {
        browser.selectElementByVisibleText(complianceTypeDropDown, complianceType);
        browser.waitForPageStable();
    }
    public void addTotalInvestorLimit(String totalInvestorLimit) { browser.typeTextElement(totalInvestorLimitField, totalInvestorLimit);}
    public void addMinUsTokens(String minUsTokens) { browser.typeTextElement(minUsTokensField, minUsTokens);}
    public void addMinEuTokens(String minEuTokens) { browser.typeTextElement(minEuTokensField, minEuTokens);}
    public void addUsInvestorLimit(String usInvestorLimit) { browser.typeTextElement(usInvestorLimitField, usInvestorLimit);}
    public void addUsAccreditedInvestorLimit(String usAccreditedInvestorLimit) { browser.typeTextElement(usAccreditedInvestorLimitField, usAccreditedInvestorLimit);}
    public void addNonAccreditedInvestorLimit(String nonAccreditedInvestorLimit) { browser.typeTextElement(nonAccreditedInvestorLimitField, nonAccreditedInvestorLimit);}
    public void clickContinueBtn() {
        browser.click(continueBtn);
    }

}