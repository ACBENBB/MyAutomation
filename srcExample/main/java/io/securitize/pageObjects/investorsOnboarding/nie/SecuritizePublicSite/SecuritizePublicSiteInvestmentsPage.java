package io.securitize.pageObjects.investorsOnboarding.nie.SecuritizePublicSite;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class SecuritizePublicSiteInvestmentsPage extends AbstractPage {

    private static final ExtendedBy InvestmentPageHeaderText = new ExtendedBy("Build Your Private Markets Portfolio - Text", By.xpath("//*[contains(text(), 'Build Your Private Markets Portfolio')]"));
    private static final ExtendedBy securitizeSNPCryptocurrencyLargeCapExMegaTokenizedFundCard = new ExtendedBy("Securitize Web - Investments page - Hamilton Lane EOV Securitize Fund", By.xpath("//*[@href='https://id.securitize.io/primary-market/opportunities/277']"));
    private static final ExtendedBy SecondaryMarketOpportunity22xCard = new ExtendedBy("Securitize Web Page Main - Securitize Web - Investments page - 22x opportunity card", By.xpath("//a[@href='https://id.securitize.io/secondary-market/assets/bcd0242c-a729-4383-b470-4ba54c4cced4']"));

    
    
    public SecuritizePublicSiteInvestmentsPage(Browser browser) {
        super(browser, InvestmentPageHeaderText);
    }
    
    public void clickSnPCryptocurrencyFundCard() {
        browser.waitForElementVisibility(securitizeSNPCryptocurrencyLargeCapExMegaTokenizedFundCard);
        browser.click(securitizeSNPCryptocurrencyLargeCapExMegaTokenizedFundCard, false);
    }

    public void click22xOpporunityCard() {
        browser.waitForElementVisibility(SecondaryMarketOpportunity22xCard);
        browser.click(SecondaryMarketOpportunity22xCard, false);
    }
    
    
    
    


}