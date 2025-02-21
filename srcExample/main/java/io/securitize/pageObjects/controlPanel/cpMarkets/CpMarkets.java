package io.securitize.pageObjects.controlPanel.cpMarkets;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CpMarkets extends AbstractPage {

    private static final ExtendedBy marketsPageTitle = new ExtendedBy("Market CP search box", By.xpath("//h4[contains(text(),'Market investors')]"));
    private static final ExtendedBy cpSearchBox = new ExtendedBy("Market CP search box", By.xpath("//input[@type='text'][@placeholder='Search...']"));
    private static final ExtendedBy rowsCount = new ExtendedBy("Wait for only 1 row shown", By.xpath("//*[contains(text(), ' Showing 1 - 1 of 1 entries ')]"));
    private static final ExtendedBy verificationTab = new ExtendedBy("Verification tab", By.xpath("//a[text()='Verification']"));
    private static final ExtendedBy accreditationStatusText = new ExtendedBy("Accreditation status", By.xpath("//div[@id='accreditation-status-select']/div/span"));
    private static final ExtendedBy rowInvestorName = new ExtendedBy("Row Investor Name", By.xpath("//td[@aria-colindex][1]"));
    private static final ExtendedBy tableLoadingMsg = new ExtendedBy("Markets Accreditation - Table Data Loading Msg", By.xpath("//*[contains(text(),'please wait')]"));
    private static final ExtendedBy marketsInvestorNameCol = new ExtendedBy("Markets Investor Row", By.xpath("//td[@aria-colindex='1']"));

    public CpMarkets(Browser browser) {
        super(browser, marketsPageTitle, cpSearchBox);
    }

    private ExtendedBy getInvestorElement(String investorName){
        return new ExtendedBy("Get Investor name in table", By.xpath("//td[text()='" + investorName + "']"));
    }

    public void typeSearchBox(String investorName) {
        browser.waitForElementVisibility(cpSearchBox);
        browser.typeTextElement(cpSearchBox, investorName);
    }

    public void waitForUniqueSearchedRow(String investorName) {
        browser.waitForElementVisibility(rowsCount);
        browser.waitForElementVisibility(getInvestorElement(investorName));
    }

    public void clickInRow(String investorName){
        browser.click(getInvestorElement(investorName));
    }

    public void clickInVerificationTab(){
        browser.click(verificationTab);
    }

    public CpMarkets openMarketSpecificInvestor(String investorName) {
        typeSearchBox(investorName);
        waitForUniqueSearchedRow(investorName);
        clickInRow(investorName);
        return this;
    }

    public String getAccreditationStatus(){
        clickInVerificationTab();
        return browser.getElementText(accreditationStatusText);
    }

    public void waitForTableToLoad() {
        try {
            browser.waitForElementVisibility(tableLoadingMsg, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForUniqueSearchedResult() {
        waitForTableToLoad();
        browser.waitForElementVisibility(rowsCount);
    }
    public void clickUniqueMarketsInvestorRow() {
//        browser.waitForElementClickable(marketsInvestorRow, 5);
        browser.click(marketsInvestorNameCol);
    }

}
