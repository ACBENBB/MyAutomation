package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiApplicationPreEntryStep0Start extends AbstractJpPage {

    public MaruiApplicationPreEntryStep0Start(Browser browser) {
        super(browser, proceedToPreEntryApplication);
    }

    // 抽選申込に進む on (社債一覧 > 詳細情報)
    private static final ExtendedBy proceedToPreEntryApplication = new ExtendedBy("Proceed to preEntry application", By.xpath("//button/*[text()[contains(.,'抽選申込に進む')]]"));
    // below appears when an oder has already placed.
    private static final ExtendedBy confirmingOrderButton = new ExtendedBy("Confirming order button", By.xpath("//button/*[text()[contains(.,'お申し込み内容確認中')]]"));
    private static final ExtendedBy opportunityStatus = new ExtendedBy("Opportunity status", By.xpath("//*[text()[contains(.,'募集中')]]"));

    public MaruiApplicationPreEntryStep1AgreeTerms clickProceedToPreEntryApplication() {
        browser.clickAndWaitForElementToVanish(proceedToPreEntryApplication);
        return new MaruiApplicationPreEntryStep1AgreeTerms(browser);
    }

    public MaruiApplicationPreEntryStep1AgreeTerms clickProceedToPreEntryApplication(boolean retry) {
        String url = getMaruiOpportunitiesUrl();
        return retryFunctionWithRefreshPageAndNavigateToUrl(this::clickProceedToPreEntryApplication, url, retry);
    }
}
