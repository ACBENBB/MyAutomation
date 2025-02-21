package io.securitize.pageObjects.jp.sony;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpDashboard;
import org.openqa.selenium.By;

public class SonyDashboard extends AbstractJpDashboard {

    // below from JpDashboard
    // - sideMenuDocuments
    // - sideMenuFaq
    // - userMenuButton

    private static final ExtendedBy sideMenuOpportunities = new ExtendedBy("Side menu - opportunities", By.xpath("//*[text() = '商品一覧']"));
    private static final ExtendedBy sideMenuPortfolio = new ExtendedBy("Side menu - portfolio", By.xpath("//*[text() = '保有商品']"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Logout button inside user menu", By.xpath("//*[text()[contains(.,'とじる')]]"));
    private static final ExtendedBy invalidScreenTransitionText = new ExtendedBy("Invalid Screen Transition Text", By.xpath("//*[text()[contains(.,'不正な画面遷移が行われました')]]"));

    public SonyDashboard(Browser browser) {
        super(browser);
    }

    public void clickSideMenuOpportunities() {
        browser.click(sideMenuOpportunities, false);
        browser.waitForPageStable();
    }

    public SonyDashboardOpportunities switchToOpportunitiesTab() {
        browser.click(sideMenuOpportunities, false);
        return new SonyDashboardOpportunities(browser);
    }

    public SonyDashboardPortfolio switchToPortfolioTab() {
        browser.click(sideMenuPortfolio, false);
        return new SonyDashboardPortfolio(browser);
    }

    public SonyDashboardDocuments switchToDocumentsTab() {
        clickSideMenuDocuments();
        return new SonyDashboardDocuments(browser);
    }

    public SonyDashboardDocuments switchToDocumentsTab(boolean retry) {
        return retryFunctionWithRefreshPage(this::switchToDocumentsTab, retry);
    }

    public SonyDashboardFaq switchToFaqTab() {
        clickSideMenuFaq();
        return new SonyDashboardFaq(browser);
    }

    public SonyDashboard clickLogout(){
        browser.click(logoutButton, false);
        return this;
    }

    public boolean invalidScreenTransitionTextIsDisplayed() {
        return browser.isElementVisibleQuick(invalidScreenTransitionText, 5);
    }

    public SonyDashboard waitUntilSideMenuOpportunitiesBecomeVisible() {
        browser.waitForElementVisibility(sideMenuOpportunities, 60);
        return this;
    }

    public SonyDashboard waitUntilSideMenuOpportunitiesBecomeVisible(boolean retry) {
        return RetryOnExceptions.retryFunction(this::waitUntilSideMenuOpportunitiesBecomeVisible,
                () -> {browser.refreshPage(); return null;}, retry);
    }
}
