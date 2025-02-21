package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpDashboardDocuments;
import org.openqa.selenium.By;

public class MaruiDashboardDocuments extends AbstractJpDashboardDocuments {

    private static final ExtendedBy domainDocumentsTab = new ExtendedBy("Domain Documents Tab", By.xpath("//a[@data-tab='domain-documents']"));

    public MaruiDashboardDocuments(Browser browser) {
        super(browser);
    }

    public void clickDomainDocumentsTab() {
        int timeoutSeconds = 5;
        browser.click(domainDocumentsTab, false);
        browser.waitForPageStable();
        // wait until "No Items" to disappear
        if (browser.isElementVisibleQuick(noItemsText, timeoutSeconds)) {
            browser.waitForElementToVanish(noItemsText);
        }
        // if error (page) is displayed, the statement below fails
        browser.waitForElementVisibility(domainDocumentsTab, timeoutSeconds);
    }

    public void clickDomainDocumentsTab(boolean retry) {
        retryFunctionWithRefreshPage(()->{clickDomainDocumentsTab(); return null;}, retry);
    }

    public String getDocumentOnDomainDocumentsTabByTableHeaderAndRowNumber(String tableHeader, int row, boolean retry) {
        return RetryOnExceptions.retryFunction(
                ()-> getDocumentByTableHeaderAndRowNumber(tableHeader, row),
                ()-> {browser.refreshPage(); clickDomainDocumentsTab(); return null;},
                retry
        );
    }

    public void clickDownloadOnDomainDocumentsTabByRowNumber(int row, boolean retry) {
        RetryOnExceptions.retryFunction(
                ()-> {clickDownloadByRowNumber(row); return null;},
                ()-> {browser.refreshPage(); clickDomainDocumentsTab(); return null;},
                retry);
    }
}
