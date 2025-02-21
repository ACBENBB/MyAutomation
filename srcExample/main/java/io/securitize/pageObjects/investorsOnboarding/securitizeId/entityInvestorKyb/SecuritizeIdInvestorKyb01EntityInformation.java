package io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.investorsOnboarding.SecuritizeIdInvestorAbstractPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc03IndividualAddress;
import org.openqa.selenium.By;

public class SecuritizeIdInvestorKyb01EntityInformation extends SecuritizeIdInvestorAbstractPage {
    private static final ExtendedBy pageTitle = new ExtendedBy("02.entity information page title", By.xpath("//*[contains(@class , 'title') and contains(text() , 'Entity information')]"));
    private static final ExtendedBy entityName = new ExtendedBy("Entity name text box", By.id("wizard-entity-name"));
    private static final ExtendedBy entityTypeSelector = new ExtendedBy("Entity type selector", By.xpath("//*[text()='Entity type']/../*/div/div/div"));
    private static final ExtendedBy entityBusinesses = new ExtendedBy("Entity businesses text box", By.id("wizard-businesses"));
    private static final ExtendedBy entityTypeCooperate = new ExtendedBy("Entity type combo box", By.xpath("//div[text()='Corporation']"));
    private static final ExtendedBy entityIdNumber = new ExtendedBy("Entity Id Number text box", By.id("wizard-entity-id-number"));
    private static final ExtendedBy entityContinue = new ExtendedBy("Entity continue button", By.id("wizard-continue"));
    private static final ExtendedBy entitySourceOfFunds = new ExtendedBy("Entity source of funds text box", By.id("wizard-source-of-funds"));
    private static final ExtendedBy entityLineOfBusiness = new ExtendedBy("Entity line of business text box", By.id("wizard-line-of-business"));
    private static final ExtendedBy entityIncorporationDay = new ExtendedBy("Entity Incorporation Day box", By.xpath("//div[text()='Day']"));
    private static final ExtendedBy entityIncorporationMonth = new ExtendedBy("Entity Incorporation Month box", By.xpath("//div[text()='Month']"));
    private static final ExtendedBy entityIncorporationYear = new ExtendedBy("Entity Incorporation Year box", By.xpath("//div[text()='Year']"));
    private static final ExtendedBy entityIncorporationDayFirst = new ExtendedBy("Entity Incorporation Day box", By.xpath("//div[text()='1']"));
    private static final ExtendedBy entityIncorporationMonthJan = new ExtendedBy("Entity Incorporation Month box", By.xpath("//div[text()='Jan']"));
    private static final ExtendedBy entityIncorporationYear2021 = new ExtendedBy("Entity Incorporation Year box", By.xpath("//div[text()='2021']"));


    public SecuritizeIdInvestorKyb01EntityInformation(Browser browser) {
        super(browser, pageTitle);
    }

    public SecuritizeIdInvestorKyb01EntityInformation insertEntityLegalName(String value) {
        browser.clearTextInput(entityName);
        browser.typeTextElement(entityName, value);
        return this;
    }

    public SecuritizeIdInvestorKyb01EntityInformation insertDoingBusiness() {
        browser.sendKeysElement(entityBusinesses, "Automation Testing", "Automation Testing");
        return this;
    }

    public SecuritizeIdInvestorKyb01EntityInformation selectEntityTypeCooperation() {
        browser.click(entityTypeSelector);
        browser.click(entityTypeCooperate);
        return this;
    }

    public SecuritizeIdInvestorKyb01EntityInformation insertTaxId(String taxId) {
        browser.typeTextElement(entityIdNumber, taxId);
        return this;
    }

    public SecuritizeIdInvestorKyb01EntityInformation insertSourceOfFunds(String sourceOfFunds) {
        browser.typeTextElement(entitySourceOfFunds, sourceOfFunds);
        return this;
    }

    public SecuritizeIdInvestorKyb01EntityInformation insertLineOfBusiness(String lineOfBusiness) {
        browser.typeTextElement(entityLineOfBusiness, lineOfBusiness);
        return this;
    }

    public SecuritizeIdInvestorKyb01EntityInformation insertIncorporationDate () {
            browser.click(entityIncorporationMonth);
            browser.click(entityIncorporationMonthJan);
            browser.click(entityIncorporationDay);
            browser.click(entityIncorporationDayFirst);
            browser.click(entityIncorporationYear);
            browser.click(entityIncorporationYear2021);
            return this;
        }

    public SecuritizeIdInvestorKyb02Address clickContinue() {
        browser.click(entityContinue, false);
        return new SecuritizeIdInvestorKyb02Address(browser);
        }

    }
