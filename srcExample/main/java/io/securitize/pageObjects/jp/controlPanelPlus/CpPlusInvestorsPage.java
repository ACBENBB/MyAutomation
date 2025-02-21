package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;

// 投資家一覧
public class CpPlusInvestorsPage extends AbstractJpPage {

    private static final ExtendedBy investorTableRows = new ExtendedBy("Investor Table Rows", By.xpath("//tbody/tr"));
    private static final ExtendedBy titleTextInvestorPage = new ExtendedBy("Title Text Investor Page", By.xpath("//*[contains(@class,'header-title') and contains(., '投資家一覧')]"));
    // %s below is email. this template is to find eye icon with the email
    private static final String TEMPLATE_EYE_ICON_WITH_EMAIL = "//tr[td[ text() = '%s' ]]/td[9]/button";

    public CpPlusInvestorsPage(Browser browser) {
        super(browser, titleTextInvestorPage);
    }

    public void clickEyeIconWith(String email) {
        ExtendedBy eyeIcon = new ExtendedBy("Eye Icon with email " + email, By.xpath(String.format(TEMPLATE_EYE_ICON_WITH_EMAIL, email)));
        browser.waitForElementClickable(eyeIcon, 120);
        browser.clickWithJs(browser.findElement(eyeIcon));
    }

    public void clickEyeIconWith(String email, boolean retry) {
        retryFunctionWithRefreshPage(()-> {clickEyeIconWith(email); return null;}, retry);
    }

    public int numberOfInvestorRowsInTable() {
        List<WebElement> webElements = browser.findElements(investorTableRows);
        return webElements.size();
    }

    public void waitUntilNumberOfInvestorsInTableBecomes(int expectedNumber) {
        browser.waitForElementCount(investorTableRows,expectedNumber);
        int numbersOfInvestorsInTable = browser.findElements(investorTableRows).size();
        if (numbersOfInvestorsInTable != expectedNumber) {
            throw new TimeoutException("numbers of investors: " + numbersOfInvestorsInTable + ", expected: " + expectedNumber);
        }
    }
}
