package io.securitize.pageObjects.investorsOnboarding.investWizard;

import io.securitize.infra.utils.RandomGenerator;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class InvestWizard_AdditionalData extends AbstractPage<InvestWizard_AdditionalData> {

    private static final ExtendedBy bankNameField = new ExtendedBy("Bank name field", By.xpath("//input[contains(@id, 'bankName')]"));
    private static final ExtendedBy bankAddressField = new ExtendedBy("Bank address field", By.xpath("//input[contains(@id, 'branchAddress')]"));
    private static final ExtendedBy bankCountryField = new ExtendedBy("Bank country field", By.xpath("//input[contains(@id, 'country')]"));
    private static final ExtendedBy routingNumberField = new ExtendedBy("Routing number field", By.xpath("//input[contains(@id, 'branchId')]"));
    private static final ExtendedBy bankAccountNumberField = new ExtendedBy("Bank account number field", By.xpath("//input[contains(@id, 'accountNum')]"));
    private static final ExtendedBy accountHolderNameField = new ExtendedBy("Account holder name field", By.xpath("//input[contains(@id, 'accountHolderName')]"));
    private static final ExtendedBy swiftValueField = new ExtendedBy("Swift value field", By.xpath("//input[contains(@id, 'swift')]"));
    private static final ExtendedBy walletAddressField = new ExtendedBy("Distribution wallet address", By.xpath("//input[contains(@id, 'distributionsWalletAddress')]"));
    private static final ExtendedBy continueButton = new ExtendedBy("Continue button", By.xpath("//button[contains(@id, 'btn-additional-data--continue')]"));

    public InvestWizard_AdditionalData(Browser browser) {
        super(browser);
    }

    public InvestWizard_AdditionalData fillBankName() {
        String bankName = "bank_" + RandomGenerator.randomString(8);
        browser.typeTextElement(bankNameField, bankName);
        return this;
    }

    public InvestWizard_AdditionalData fillBankAddress() {
        String bankAddress = "bank_address_" + RandomGenerator.randomString(8);
        browser.typeTextElement(bankAddressField, bankAddress);
        return this;
    }

    public InvestWizard_AdditionalData fillBankCountry() {
        String bankCountry = "bank_country_" + RandomGenerator.randomString(8);
        browser.typeTextElement(bankCountryField, bankCountry);
        return this;
    }

    public InvestWizard_AdditionalData fillRoutingNumber() {
        String routingNumber = RandomGenerator.randomNumber(8);
        browser.typeTextElement(routingNumberField, routingNumber);
        return this;
    }

    public InvestWizard_AdditionalData fillBankAccountNumber() {
        String bankAccountNumber = RandomGenerator.randomNumber(8);
        browser.typeTextElement(bankAccountNumberField, bankAccountNumber);
        return this;
    }

    public InvestWizard_AdditionalData fillAccountHolderName() {
        String accountHolderName = "account_holder_" + RandomGenerator.randomString(8);
        browser.typeTextElement(accountHolderNameField, accountHolderName);
        return this;
    }

    public InvestWizard_AdditionalData fillSwift() {
        String swiftValue = "SBIC" + RandomGenerator.randomNumber(8);
        browser.typeTextElement(swiftValueField, swiftValue);
        return this;
    }

    public InvestWizard_AdditionalData fillWalletAddress(String walletAddress) {
        browser.typeTextElement(walletAddressField, walletAddress);
        return this;
    }

    public InvestWizard_SignAgreement clickContinue() {
        browser.click(continueButton, false);
        return new InvestWizard_SignAgreement(browser);
    }

    public InvestWizard_SignAgreement fillAdditionalData(String walletAddress) {
        return fillBankName()
                .fillBankAddress()
                .fillBankCountry()
                .fillRoutingNumber()
                .fillBankAccountNumber()
                .fillAccountHolderName()
                .fillSwift()
                .fillWalletAddress(walletAddress)
                .clickContinue();
    }
}