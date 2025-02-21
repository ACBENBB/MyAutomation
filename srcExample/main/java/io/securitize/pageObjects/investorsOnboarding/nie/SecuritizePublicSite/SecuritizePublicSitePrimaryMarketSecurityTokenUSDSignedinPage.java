package io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizePublicSitePrimaryMarketSecurityTokenUSDSignedinPage extends AbstractPage {

    private static final ExtendedBy securitizeSYPCryptocurrencyLargeCapExMegaFundText = new ExtendedBy("S&P Cryptocurrency Large Cap Ex Mega Fund - Text - Text", By.xpath("//*[contains(text(), 'S&P Cryptocurrency Large Cap Ex Mega Fund')]"));
    private static final ExtendedBy investButton = new ExtendedBy("Opportunity Invest button", By.xpath("//button[contains(@id, 'btn-invest')]"));
    private static final ExtendedBy primaryOfferingSideBarButton = new ExtendedBy("Opportunity Invest button", By.xpath("//a[@id='home-page-nav-market']"));


    public SecuritizePublicSitePrimaryMarketSecurityTokenUSDSignedinPage(Browser browser) {
        super(browser, primaryOfferingSideBarButton);
    }

    public void verifySecurityTokenMarketUSDOfferingPage(){
        browser.waitForElementVisibility(securitizeSYPCryptocurrencyLargeCapExMegaFundText);
        browser.waitForElementVisibility(investButton);
    }
}