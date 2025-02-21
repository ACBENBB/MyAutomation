package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class CryptoCurrencyPreferencePage extends AbstractPage {

    private static final ExtendedBy etherBtn = new ExtendedBy("Payout preference table", By.id("ETH"));
    private static final ExtendedBy continueBtn = new ExtendedBy("Payout preference table", By.xpath("//button[not(contains(@id, 'addInvestor'))][text()='Continue']"));

    public CryptoCurrencyPreferencePage(Browser browser) {
        super(browser);
    }

    public CryptoCurrencyPreferencePage clickEtherBtn() {
        browser.click(etherBtn, false);
        return new CryptoCurrencyPreferencePage(browser);
    }

    public WalletSelectionPage clickContinue(){
        browser.click(continueBtn, false);
        return new WalletSelectionPage(browser);
    }

    public WalletSelectionPage selectEtherCrypto() {
        return clickEtherBtn().clickContinue();
    }

}