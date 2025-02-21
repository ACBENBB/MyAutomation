package io.securitize.pageObjects.investorsOnboarding.investWizard;

import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import io.securitize.pageObjects.investorsOnboarding.nie.FundYourInvestmentPage;
import io.securitize.pageObjects.investorsOnboarding.nie.docuSign.DocusignSigningPage;
import org.openqa.selenium.By;

public class InvestWizard_SignAgreement extends AbstractPage {

    private static final ExtendedBy signAgreementButton = new ExtendedBy("Sign subscription agreement button", By.xpath("//*[contains(@id, 'btn-sign-subscription-agreement') or contains(@id, 'btn-subscription-agreement--sign')]"));
    private static final ExtendedBy investmentCurrencyField = new ExtendedBy("Investment currency field", By.xpath("//h5[contains(@class,'mb-1')]"));
    private static final ExtendedBy investmentAmountField = new ExtendedBy("Investment amount field", By.xpath("//span[contains(text(), 'Send')]/ancestor::div[contains(@class, 'mb-3 row')]//span[contains(@class, 'InvestmentSummary_investmentSummary__investmentAmount')]"));
    private static final ExtendedBy investmentReceivedTokensField = new ExtendedBy("Investment received tokens field", By.xpath("//span[contains(@class, 'InvestmentSummary_investmentSummary__investmentAmountTokens')]"));
    private static final ExtendedBy continueButton = new ExtendedBy("Continue button", By.id("btn-funding-instructions--continue"));
    private static final ExtendedBy selectLegalSignerButton = new ExtendedBy("Select legal signer button", By.id("btn-sing-agreement--select-legal-signer"));
    private static final String legalSignerItemXpathTemplate = "//li//label[contains(text(), '%s')]";
    private static final ExtendedBy confirmLegalSignerButton = new ExtendedBy("Confirm legal signer button", By.xpath("//button[@type='submit']"));
    private static final ExtendedBy agreementView = new ExtendedBy("Agreement View", By.id("btn-subscription-agreement--signed-agreement-preview"));

    public InvestWizard_SignAgreement(Browser browser) {
        super(browser);
        // investmentAmountField wont appear on sandbox
    }

    public String getInvestmentCurrency() {
        String investmentAmountAsString = browser.getElementText(investmentCurrencyField);
        investmentAmountAsString = investmentAmountAsString.replace("$", "");
        return investmentAmountAsString.trim();
    }

    public float getInvestmentAmount() {
        String investmentAmountAsString = browser.getElementText(investmentAmountField);
        return RegexWrapper.stringToFloat(investmentAmountAsString);
    }

    public int getReceivedTokens() {
        String receivedTokensAsString = browser.getElementText(investmentReceivedTokensField);
        return Integer.parseInt(receivedTokensAsString.trim());
    }

    public DocusignSigningPage clickSignAgreement() {
        browser.click(signAgreementButton, false);
        return new DocusignSigningPage(browser);
    }

    public boolean isContinueBtnVisible() {
        return browser.isElementVisible(continueButton);
    }

    public FundYourInvestmentPage clickContinue() {
        browser.waitForPageStable();
        browser.waitForElementVisibility(agreementView);
        browser.waitForElementVisibility(continueButton);
        browser.waitForElementToBeClickable(continueButton, 360); // needed due to docuSign slowness
        browser.click(continueButton, false);
        return new FundYourInvestmentPage(browser);
    }

    public InvestWizard_SignAgreement SelectLegalSigner(String firstName) {
        browser.click(selectLegalSignerButton); // open list of legal signers
        ExtendedBy legalSignerEntryLocator = new ExtendedBy("Legal signer " + firstName, By.xpath(String.format(legalSignerItemXpathTemplate, firstName)));
        browser.click(legalSignerEntryLocator);// select requested legal signer
        // click confirm
        browser.click(confirmLegalSignerButton, false);
        browser.waitForElementVisibility(agreementView);
        return this;
    }
}