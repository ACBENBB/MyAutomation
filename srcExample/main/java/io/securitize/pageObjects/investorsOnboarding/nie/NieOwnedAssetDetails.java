package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class NieOwnedAssetDetails extends AbstractPage {

    private static final ExtendedBy cardHeader = new ExtendedBy("Owned asset details - card header", By.xpath("//div[contains(@class, 'card-body')]/h1"));
    private static final ExtendedBy totalUnitBalance = new ExtendedBy("Total unit balance field", By.id("total-share-value"));
    private static final ExtendedBy balanceInWallets = new ExtendedBy("Balance in wallets field", By.id("total-share-in-wallet-value"));
    private static final ExtendedBy committedInvestmentAmount = new ExtendedBy("Committed investment amount field", By.id("0-pledged"));
    private static final ExtendedBy fundedInvestmentAmount = new ExtendedBy("Funded investment amount field", By.id("0-totalFunded"));


    NieOwnedAssetDetails(Browser browser) {
        super(browser, cardHeader);
    }


    public int getTotalUnitBalance() {
        String valueAsString = browser.getElementText(totalUnitBalance);
        return RegexWrapper.stringToInteger(valueAsString);
    }

    public int getBalanceInWallets() {
        String valueAsString = browser.getElementText(balanceInWallets);
        return RegexWrapper.stringToInteger(valueAsString);
    }

    public float getCommittedInvestment() {
        String valueAsString = browser.getElementText(committedInvestmentAmount);
        return RegexWrapper.stringToFloat(valueAsString);
    }

    public float getFundedInvestment() {
        String valueAsString = browser.getElementText(fundedInvestmentAmount);
        return RegexWrapper.stringToFloat(valueAsString);
    }
}