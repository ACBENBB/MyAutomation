package io.securitize.pageObjects.controlPanel.cpMarkets;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation.EXTRACT_LINK_FROM_EMAIL_REGEX;

public class CpAccreditation extends AbstractPage {

    private static final ExtendedBy accreditationSearchBox = new ExtendedBy("CP > Accreditation Search Box", By.xpath("//input[@placeholder='Search...']"));
    private static final ExtendedBy accreditationStatusYellowBadge = new ExtendedBy("CP > Accreditation Status Badge", By.xpath("//td//span[contains(@class, 'badge-yellow')]"));
    private static final ExtendedBy accreditationMethodText = new ExtendedBy("CP > Accreditation Method Text", By.xpath("//td//span[contains(@class, 'badge-yellow')]"));

    private static final ExtendedBy accreditationResultsCount = new ExtendedBy("CP > Accreditation Results Count", By.xpath("//span[contains(text(), 'Showing 1 - 1 of 1 entries')]"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Issuers list page - logout button", By.xpath("//*[@class = 'ion ion-ios-log-out navbar-icon align-middle']"));
    private static final ExtendedBy accreditationViewBtn = new ExtendedBy("CP > Accreditation View Btn", By.xpath("(//span[contains(text(), 'View')])[1]"));
    private static final ExtendedBy assignLawyerDropdown = new ExtendedBy("CP > Assign Lawyer Dropdown", By.xpath("(//select[@class='custom-select'])[1]"));
    private static final ExtendedBy tableLoadingMsg = new ExtendedBy("Table Data Loading Msg Text", By.xpath("//*[contains(text(),'please wait')]"));
    private static final ExtendedBy accreditationMainTableLoadingMsg = new ExtendedBy("Table Data Loading Msg Text", By.xpath("//*[contains(text(),'please wait')]"));


    public CpAccreditation(Browser browser) {
        // TODO ADD PAGE OBJECT VALIDATION
        super(browser);
    }

    public CpAccreditation searchAccreditationBy(String investorName) {
        browser.waitForPageStable();
        browser.waitForElementClickable(accreditationSearchBox, 3);
        browser.typeTextElement(accreditationSearchBox, investorName);
        try {
            browser.waitForElementVisibility(tableLoadingMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        browser.waitForElementClickable(accreditationViewBtn, 10);
        return this;
    }

    public String getAccreditationMethodText() {
        browser.waitForPageStable();
        return getAccreditationColumnDataByRow("accreditation method", "1");
    }

    public String getAccreditationInvestorNameText() {
        browser.waitForPageStable();
        return getAccreditationColumnDataByRow("name", "1");
    }

    public String getAccreditationStatusBadge() {
        browser.waitForPageStable();
        return getAccreditationColumnDataByRow("status", "1");
    }

    public CpAccreditation assignLawyer(String lawyer) {
        browser.waitForElementClickable(assignLawyerDropdown, 7);
        browser.selectElementByVisibleText(assignLawyerDropdown, lawyer);
        return this;
    }

    public CpAccreditationDetails clickViewAccreditationBtn() {
        browser.waitForPageStable(Duration.ofSeconds(5));
        browser.waitForElementClickable(accreditationViewBtn, 10);
        browser.click(accreditationViewBtn);
        return new CpAccreditationDetails(browser);
    }

    public void logoutFromCP(){
        browser.click(logoutButton);
    }

    public String extractLinkFromEmail(String recipientAddress) {
        return extractLinkFromEmail(recipientAddress, EXTRACT_LINK_FROM_EMAIL_REGEX);
    }

    public void waitForAccreditationTableIsLoaded() {
        try {
            WebElement element = browser.findElement(accreditationMainTableLoadingMsg);
            browser.waitForElementToVanish(element, "Accreditation Table Is Loading Msg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAccreditationColumnDataByRow(String columnHeader, String row) {
        browser.waitForPageStable();
        final String accreditationRow = "(//tr[@aria-rowindex='"+row+"']";
        final String accreditationRowContent = "//td)["+setColumnHeaderXpath(columnHeader)+"]";
        if(columnHeader.equalsIgnoreCase("status")) {
            String statusBadgeXpath = "//td//span[contains(@class, 'badge')])";
            WebElement rowDataColumn = Browser.getDriver().findElement(By.xpath(accreditationRow+statusBadgeXpath));
            return rowDataColumn.getText();
        } else {
            WebElement rowDataColumn = Browser.getDriver().findElement(By.xpath(accreditationRow+accreditationRowContent));
            return rowDataColumn.getText();
        }
    }

    public String setColumnHeaderXpath(String columnHeader) {
        String columnHeaderIndex = null;
        if(columnHeader.equalsIgnoreCase("name")) {
            columnHeaderIndex = "1";
        } else if(columnHeader.equalsIgnoreCase("investor type")) {
            columnHeaderIndex = "2";
        } else if(columnHeader.equalsIgnoreCase("accreditation method")) {
            columnHeaderIndex = "3";
        } else if(columnHeader.equalsIgnoreCase("document type")) {
            columnHeaderIndex = "4";
        } else if(columnHeader.equalsIgnoreCase("date submitted")) {
            columnHeaderIndex = "5";
        } else if(columnHeader.equalsIgnoreCase("status")) {
            columnHeaderIndex = "6";
        } else if(columnHeader.equalsIgnoreCase("assignee")) {
            columnHeaderIndex = "7";
        }
        return columnHeaderIndex;
    }

}