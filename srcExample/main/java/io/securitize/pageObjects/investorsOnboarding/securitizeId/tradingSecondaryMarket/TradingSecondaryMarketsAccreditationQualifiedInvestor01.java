package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsAccreditationQualifiedInvestor01 extends AbstractPage {
    private static final ExtendedBy submitAccreditationButton = new ExtendedBy("ATS Accreditation - investor qualification - submit button", By.xpath("//*[@class='ladda-button btn btn-primary btn-block font-weight-bold text-white max-width-330 p-3']"));
    private static final ExtendedBy aQualifiedInvestorText = new ExtendedBy("A qualified investor ... text", By.xpath("//*[text()='To be considered an Accredited Investor in the United States, one must be one of the following:']"));
    private static final ExtendedBy qualifiedRadioButton = new ExtendedBy("A qualified investor radio button", By.xpath("//*[@id='accredited-yes-text']"));
    private static final ExtendedBy getAccreditedButton = new ExtendedBy("Get accredited with Securitize.. button", By.xpath("//*[contains(text(),'Get accredited with Securitize Markets')]"));


    public TradingSecondaryMarketsAccreditationQualifiedInvestor01(Browser browser) { super(browser, aQualifiedInvestorText); }

    public TradingSecondaryMarketsAccreditationQualifiedInvestor01 qualifiedInvestorRadioButton() {
        browser.click(qualifiedRadioButton, false);
        return this;
    }

    public TradingSecondaryMarketsAccreditationQualifiedInvestor01 clickSubmitAccreditationStatusButton() {
        browser.click(submitAccreditationButton, false);
        return this;
    }

    public TradingSecondaryMarketsAccreditationQualifiedInvestor01 clickGetAccreditedButton() {
        browser.click(getAccreditedButton, false);
        return this;
    }
}
