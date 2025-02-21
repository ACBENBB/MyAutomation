package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpDashboard;
import org.openqa.selenium.By;

public class MaruiDashboard extends AbstractJpDashboard {

    // below from JpDashboard
    // - sideMenuDocuments
    // - sideMenuFaq
    // - userMenuButton

    private static final ExtendedBy sideMenuOpportunities = new ExtendedBy("Side menu - opportunities", By.xpath("//*[text() = '社債一覧']"));
    private static final ExtendedBy sideMenuPortfolio = new ExtendedBy("Side menu - portfolio", By.xpath("//*[text() = 'お申し込み状況']"));
    private static final ExtendedBy logoutButton = new ExtendedBy("Logout button inside user menu", By.xpath("//*[text()[contains(.,'ログアウト')]]"));

    public MaruiDashboard(Browser browser) {
        super(browser);
    }

    public void clickSideMenuOpportunities() {
        browser.click(sideMenuOpportunities, false);
        browser.waitForPageStable();
    }

    public MaruiDashboardOpportunities switchToOpportunitiesTab() {
        browser.click(sideMenuOpportunities, false);
        return new MaruiDashboardOpportunities(browser);
    }

    public MaruiDashboardPortfolio switchToPortfolioTab() {
        browser.click(sideMenuPortfolio, false);
        return new MaruiDashboardPortfolio(browser);
    }

    public MaruiDashboardDocuments switchToDocumentsTab() {
        clickSideMenuDocuments();
        return new MaruiDashboardDocuments(browser);
    }

    public MaruiDashboardDocuments switchToDocumentsTab(boolean retry) {
        return retryFunctionWithRefreshPage(this::switchToDocumentsTab, retry);
    }

    public MaruiDashboardFaq switchToFaqTab() {
        clickSideMenuFaq();
        return new MaruiDashboardFaq(browser);
    }

    public MaruiDashboard clickLogout(){
        browser.click(logoutButton, false);
        browser.waitForPageStable();
        return this;
    }

    public MaruiDashboard waitUntilSideMenuOpportunitiesBecomeVisible() {
        browser.waitForElementVisibility(sideMenuOpportunities, 60);
        return this;
    }

    public MaruiDashboard waitUntilSideMenuOpportunitiesBecomeVisible(boolean retry) {
        return RetryOnExceptions.retryFunction(this::waitUntilSideMenuOpportunitiesBecomeVisible,
                () -> {browser.refreshPage(); return null;}, retry);
    }
}
