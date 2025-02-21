package io.securitize.pageObjects.jp.abstractClass;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import org.openqa.selenium.By;

public abstract class AbstractJpDashboard extends AbstractJpPage {

    protected static final ExtendedBy sideMenuDocuments = new ExtendedBy("Side menu - documents", By.xpath("//*[text() = 'ドキュメント']"));
    protected static final ExtendedBy sideMenuFaq = new ExtendedBy("Side menu - faq", By.xpath("//*[text() = 'FAQ']"));
    protected static final ExtendedBy userMenuButton = new ExtendedBy("Top right user menu button", By.xpath("//*[@class='icon-profile']"));

    protected AbstractJpDashboard(Browser browser) {
        super(browser, sideMenuFaq);
    }

    public void clickSideMenuDocuments() {
        browser.click(sideMenuDocuments, false);
        browser.waitForPageStable();
    }

    public void clickSideMenuFaq() {
        browser.click(sideMenuFaq, false);
        browser.waitForPageStable();
    }

    public void openUserMenu(){
        browser.waitForElementVisibility(userMenuButton);
        browser.click(userMenuButton, false);
    }

    public abstract AbstractJpDashboard clickLogout();

    public AbstractJpDashboard performLogout() {
        openUserMenu();
        clickLogout();
        return this;
    }

    public AbstractJpDashboard performLogout(boolean retry) {
        return RetryOnExceptions.retryFunction(this::performLogout, () -> {browser.refreshPage(); return null;}, retry);
    }

    public void performLogoutIncludingClearCookies() {
        openUserMenu();
        clickLogout();
        browser.clearAllCookies();
    }

    public abstract AbstractJpDashboard waitUntilSideMenuOpportunitiesBecomeVisible(boolean retry);
}
