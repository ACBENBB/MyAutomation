package io.securitize.pageObjects.jp.controlPanelPlus;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CpPlusSideMenu extends AbstractJpPage {

    private static final ExtendedBy languageLabelJapanese = new ExtendedBy("Language Label JA", By.xpath("//*[contains(@class,'toggle-label') and text() = 'JA' ]"));
    private static final ExtendedBy languageLabelEnglish = new ExtendedBy("Language Label EN", By.xpath("//*[contains(@class,'toggle-label') and text() = 'EN' ]"));
    private static final ExtendedBy languageSelectJapanese = new ExtendedBy("Language Select Japanese", By.xpath("//*[@class='dropdown-item' and text() = 'Japanese' ]"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Logout Button", By.xpath("//*[@class='c-header-toggler']"));
    private static final ExtendedBy issuerSelect = new ExtendedBy("Issuer Select", By.xpath("//*[contains(@class,'SearchableDropdownComponent')]/button"));
    private static final ExtendedBy issuerSearchInput = new ExtendedBy("Issuer Search Input", By.xpath("//*[contains(@placeholder,'検索')]"));
    private static final String TEMPLATE_TOKEN_SELECT = "//*[contains(@class,'DropdownComponent') and contains(., '%s')]/button";

    private static final String TEMPLATE_NAME_IN_DROPDOWN = "//*[@class='dropdown-item' and contains(., '%s')]";
    private static final ExtendedBy investorsMenu = new ExtendedBy("Investors Menu", By.xpath("//a[@href='#/investors']"));
    private static final ExtendedBy lotteriesMenu = new ExtendedBy("Investors Menu", By.xpath("//a[@href='#/tokens/lotteries']"));
    private static final ExtendedBy corporateBondLedgersMenu = new ExtendedBy("Corporate Bond Ledgers Menu", By.xpath("//a[@href='#/tokens/corporate-bond-ledgers']"));
    private static final ExtendedBy bankDepositFileMenu = new ExtendedBy("Bank Deposit File Menu", By.xpath("//a[@href='#/tokens/bank-deposit-files']"));

    public CpPlusSideMenu(Browser browser) {
        super(browser, investorsMenu);
    }

    public CpPlusSideMenu selectLanguageJapanese() {
        browser.waitForPageStable();
        List<WebElement> webElements = browser.findElements(languageLabelJapanese);
        if (webElements.isEmpty()) {
            browser.click(languageLabelEnglish);
            browser.click(languageSelectJapanese);
        }
        return this;
    }

    public CpPlusSideMenu selectIssuerName(String issuerName) {
        browser.waitForElementVisibility(issuerSelect);
        browser.clickWithJs(browser.findElement(issuerSelect));
        browser.typeTextElement(issuerSearchInput, issuerName);
        ExtendedBy dropdown = new ExtendedBy("Issuer Dropdown " + issuerName, By.xpath(String.format(TEMPLATE_NAME_IN_DROPDOWN, issuerName)));
        browser.waitForElementVisibility(dropdown);
        browser.clickWithJs(browser.findElement(dropdown));
        return this;
    }

    public CpPlusSideMenu selectIssuerName(String issuerName, boolean retry) {
        return RetryOnExceptions.retryFunction(
                ()-> selectIssuerName(issuerName),
                ()-> {browser.refreshPage(); return null;},
                retry
        );
    }

    public CpPlusSideMenu selectTokenName(String tokenName) {
        ExtendedBy tokenSelect = new ExtendedBy("Token Select Box " + tokenName, By.xpath(String.format(TEMPLATE_TOKEN_SELECT, tokenName)));
        browser.waitForElementVisibility(tokenSelect);
        browser.clickWithJs(browser.findElement(tokenSelect));
        ExtendedBy dropdown = new ExtendedBy("Token Dropdown " + tokenName, By.xpath(String.format(TEMPLATE_NAME_IN_DROPDOWN, tokenName)));
        browser.waitForElementVisibility(dropdown);
        browser.clickWithJs(browser.findElement(dropdown));
        return this;
    }

    public CpPlusSideMenu selectTokenName(String tokenName, boolean retry) {
        return retryFunctionWithRefreshPage(()-> selectTokenName(tokenName), retry );
    }

    public void clickLogoutButton() {
        browser.waitForElementClickable(logoutButton);
        browser.clickWithJs(browser.findElement(logoutButton));
        browser.clearAllCookies();
        browser.waitForPageStable();
    }

    public CpPlusInvestorsPage clickInvestors(boolean retry) {
        browser.click(investorsMenu);
        browser.waitForPageStable();
        return retryFunctionWithRefreshPage(()->new CpPlusInvestorsPage(browser), retry);
    }

    public CpPlusLotteriesPage clickLotteries(boolean retry) {
        browser.click(lotteriesMenu);
        browser.waitForPageStable();
        return retryFunctionWithRefreshPage(()->new CpPlusLotteriesPage(browser), retry);
    }

    public CpPlusCorporateBondLedgerPage clickCorporateBondLedgersMenu(boolean retry) {
        browser.click(corporateBondLedgersMenu);
        browser.waitForPageStable();
        return retryFunctionWithRefreshPage(()->new CpPlusCorporateBondLedgerPage(browser), retry);
    }

    public CpPlusBankDepositFilePage clickBankDepositFileMenu(boolean retry) {
        browser.click(bankDepositFileMenu);
        browser.waitForPageStable();
        return retryFunctionWithRefreshPage(()->new CpPlusBankDepositFilePage(browser), retry);
    }
}
