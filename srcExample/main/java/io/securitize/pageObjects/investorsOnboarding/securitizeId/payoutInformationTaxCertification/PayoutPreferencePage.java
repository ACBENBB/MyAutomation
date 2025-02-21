package io.securitize.pageObjects.investorsOnboarding.securitizeId.payoutInformationTaxCertification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class PayoutPreferencePage extends AbstractPage {

    private static final ExtendedBy payoutPreferenceTable = new ExtendedBy("Payout preference table", By.id("payout-preference-table"));
    private static final ExtendedBy fiatEditButton = new ExtendedBy("Bank transfer edit button", By.id("fiat-edit"));
    private static final ExtendedBy cryptoEditButton = new ExtendedBy("Crypto edit button", By.id("crypto-edit"));
    private static final ExtendedBy goBackButton = new ExtendedBy("Go back to Payout Information & Tax Certification", By.id("go-back"));
    private static final ExtendedBy bankTransferToggleStatus = new ExtendedBy("Bank Transfer Toggle Switch", By.xpath("//label[@for='fiat-switch']//span[contains(@class, 'PayoutPreference_toggleStatus')]"));
    private static final ExtendedBy cryptoToggleStatus = new ExtendedBy("Bank Transfer Toggle Switch", By.xpath("//label[@for='crypto-switch']//span[contains(@class, 'PayoutPreference_toggleStatus')]"));
    private static final ExtendedBy bankTransferToggleActive = new ExtendedBy("Bank Transfer Toggle Switch", By.xpath("//label[@for='fiat-switch']//span[text()='Active']"));
    private static final ExtendedBy cryptoToggleActive = new ExtendedBy("Crypto Toggle Switch", By.xpath("//label[@for='crypto-switch']//span[text()='Active']"));

    public PayoutPreferencePage(Browser browser) {
        super(browser, payoutPreferenceTable);
    }

    public PayoutPreferencePage clickGoBackButtonButton() {
        browser.click(goBackButton, false);
        return new PayoutPreferencePage(browser);
    }

    public String getBankTransferToggleStatus() {
        return browser.getElementAttribute(bankTransferToggleStatus, "innerText");
    }

    public String getCryptoToggleStatus() {
        return browser.getElementAttribute(cryptoToggleStatus, "innerText");
    }

    public boolean isBankTransferToggleStatusActive() {
        return getBankTransferToggleStatus().equalsIgnoreCase("Active");
    }

    public boolean isCryptoToggleStatusActive() {
        return getCryptoToggleStatus().equalsIgnoreCase("Active");
    }

    public BankingDetailsAddPayoutMethodPage clickFiatEditButton() {
        browser.click(fiatEditButton, false);
        return new BankingDetailsAddPayoutMethodPage(browser);
    }

    public CryptoCurrencyPreferencePage clickCryptoEditButton() {
        browser.click(cryptoEditButton, false);
        return new CryptoCurrencyPreferencePage(browser);
    }

    public void addBankTransferPayoutPreference(String firstName, String lastName, String year, String month,
                                                String day, String country, String address, String address2,
                                                String city, String state, String zipCode, String routingNumber,
                                                String accountNumber, String nameOfAccountHolder) {
        BankingDetailsAddPayoutMethodPage bankingDetailsAddPayoutMethodPage = clickFiatEditButton();
        BankingDetailsAddPayoutMethodGeneralInformationPage bankingDetailsAddPayoutMethodGeneralInformationPage =
                bankingDetailsAddPayoutMethodPage.clickAddPayoutMethodButton();
        BankingDetailsAddPayoutMethodSelectPayoutMethodPage bankingDetailsAddPayoutMethodSelectPayoutMethodPage =
                bankingDetailsAddPayoutMethodGeneralInformationPage.completeGeneralBankInformationForm(
                        firstName, lastName, year, month, day, country, address, address2, city, state, zipCode);
        BankingDetailsAddPayoutMethodSuccessPage bankingDetailsAddPayoutMethodSuccessPage =
                bankingDetailsAddPayoutMethodSelectPayoutMethodPage.completeSelectPayoutMethodForm(
                        routingNumber, accountNumber, nameOfAccountHolder);
        bankingDetailsAddPayoutMethodSuccessPage.clickDoneBtn().clickOnBackToPayoutPreference();
    }

    public void addCryptoPayoutPreference(String walletAddress) {

        CryptoCurrencyPreferencePage bankingDetailsAddPayoutMethodPage = clickCryptoEditButton();
        WalletSelectionPage walletSelectionPage = bankingDetailsAddPayoutMethodPage.selectEtherCrypto();
        walletSelectionPage.selectWalletAddress(walletAddress).selectConfirmWalletAddressCheckBox()
                .clickWalletSelectionConfirm();
    }

    public void waitForBankToggleToUpdateStatus() {
        refreshPageToUpdateBankTransferToggleStatus();
    }

    public void waitForCryptoToggleToUpdateStatus() {
        refreshPageToUpdateCryptoToggleStatus();
    }

    public void refreshPageToUpdateBankTransferToggleStatus() {
        for (int i = 0; i < 5; i++) {
            if (!(browser.findElements(bankTransferToggleActive).size() > 0)) {
                browser.refreshPage();
            }
        }
    }

    public void refreshPageToUpdateCryptoToggleStatus() {
        for (int i = 0; i < 5; i++) {
            if (!(browser.findElements(cryptoToggleActive).size() > 0)) {
                browser.refreshPage();
            }
        }
    }

}