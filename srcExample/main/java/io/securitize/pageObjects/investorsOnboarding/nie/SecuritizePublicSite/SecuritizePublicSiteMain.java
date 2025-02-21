package io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite;

import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class SecuritizePublicSiteMain extends AbstractPage<SecuritizePublicSiteMain> {

    private static final ExtendedBy weTokenizeRealWorldAssets = new ExtendedBy("Securitize Web Page Main - We Tokenize Real-World Assets", By.xpath("//*[contains(text(),'We Tokenize Real-World Assets')]"));
    private static final ExtendedBy issuerCheck22xTradeButton = new ExtendedBy("Securitize Web Page Main - Secondary Market 22x issuer Trade Button", By.xpath("//*[@href='https://id.securitize.io/secondary-market/assets/bcd0242c-a729-4383-b470-4ba54c4cced4']"));
    private static final ExtendedBy investButton = new ExtendedBy("Securitize Web Page Main - button", By.xpath("//li[contains(@class, 'wh-flex')]/a[text()='Invest']"));


    public SecuritizePublicSiteMain(Browser browser) {
        super(browser, weTokenizeRealWorldAssets);
    }


    public void clickInvestButton() {
        Optional<WebElement> element = browser.findFirstVisibleElementQuick(investButton, 15);
        if (element.isPresent()) {
            browser.click(element.get(), investButton.getDescription());
        } else {
            MultiReporter.errorAndStop("Couldn't find invest drop down.", true);
        }
    }

    public void clickIssuer22xTradeButton() {
        //browser.waitForElementVisibility(tradeDigitalAssetSecurityText);
        browser.click(issuerCheck22xTradeButton, false);
    }
}
