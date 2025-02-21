package io.securitize.pageObjects.jp.abstractClass;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.api.DomainApi;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractJpPage extends AbstractPage<AbstractJpPage> {

    protected static final ExtendedBy loading = new ExtendedBy("Loading", By.xpath("//*[@aria-label='Loading']"));
    protected static final String XPATH_TEMPLATE_TABLE_DATA_BY_ROW_AND_HEADER = "//table/tbody/tr[%d]/td[count(//table/thead/tr/th[.='%s']/preceding-sibling::th)+1]";
    protected static final ExtendedBy noDataText = new ExtendedBy("No Data Text", By.xpath("//*[ text() = 'データがありません' ]"));
    protected static final ExtendedBy tableRows = new ExtendedBy("Table Rows", By.xpath("//tbody/tr"));

    protected AbstractJpPage(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    public int numberOfRowsInTable() {
        List<WebElement> webElements = browser.findElements(tableRows);
        return webElements.size();
    }

    public boolean isNoDataTextFound() {
        return browser.isElementVisibleQuick(noDataText, 15);
    }

    public <T> void waitUntilStatusBecomes(Supplier<T> func, String status, int delayInSeconds, int maxRetries) {
        RetryPolicy<String> retryPolicy = RetryPolicy.<String>builder()
                .handleResultIf(string -> !string.equals(status))
                .withDelay(Duration.ofSeconds(delayInSeconds))
                .withMaxRetries(maxRetries)
                .build();
        Failsafe.with(retryPolicy).get(() -> {
            func.get(); return null;
        });
        browser.waitForPageStable();
    }

    protected <T> T retryFunctionWithRefreshPage(Supplier<T> func, boolean retry) {
        browser.waitForPageStable(Duration.ofSeconds(3));
        return RetryOnExceptions.retryFunction(func, ()-> {browser.refreshPage(); return null;}, retry );
    }

    protected <T> T retryFunctionWithRefreshPageAndNavigateToUrl(Supplier<T> func, String url, boolean retry) {
        browser.waitForPageStable(Duration.ofSeconds(3));
        return RetryOnExceptions.retryFunction(func, ()-> {browser.refreshPage(); browser.navigateTo(url); return null;}, retry );
    }

    public DomainApi domainApi(String domainId) {
        return new DomainApi(
                Users.getProperty(UsersProperty.jp_domainApiBaseUrl),
                domainId,
                Users.getProperty(UsersProperty.jp_domainApiKey)
        );
    }

    public DomainApi domainApiMarui() {
        return domainApi(Users.getProperty(UsersProperty.marui_domainId));
    }

    public String getOpportunityId(String tokenId) {
        List<String> opportunityIdList = domainApiMarui().getOpportunityId(tokenId);
        return opportunityIdList.get(0);
    }

    public String getMaruiOpportunityId() {
        return getOpportunityId(Users.getProperty(UsersProperty.marui_tokenId));
    }

    public String getMaruiOpportunitiesUrl() {
        String url = Users.getProperty(UsersProperty.marui_investorSiteBaseUrl);
        String opportunityId = getMaruiOpportunityId();
        return url + "/opportunities/" + opportunityId;
    }

}
