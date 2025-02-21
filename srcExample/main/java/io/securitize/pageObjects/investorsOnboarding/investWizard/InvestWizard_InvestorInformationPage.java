package io.securitize.pageObjects.investorsOnboarding.investWizard;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class InvestWizard_InvestorInformationPage extends AbstractPage {

    private static final ExtendedBy investorTypeIndividual = new ExtendedBy("Investor type Individual", By.xpath("//input[@id='investortype-individual']/.."));
    private static final ExtendedBy investorTypeEntity = new ExtendedBy("Investor type Entity", By.xpath("//input[@id='investortype-entity']/.."));
    private static final ExtendedBy licensedBrokerDealerNo = new ExtendedBy("Licensed Broker Dealer No", By.xpath("//input[@id='licensedbrokerdealer-no']/.."));
    private static final ExtendedBy registeredRepresentativeNo = new ExtendedBy("Registered Representative No", By.xpath("//input[@id='registeredrepresentativeofbrokerdealer-no']/.."));
    private static final ExtendedBy finraMemberNo = new ExtendedBy("Finra Member No", By.xpath("//input[@id='ismemberoremployedatstockexchangeorfinra-no']/.."));

    private static final ExtendedBy experienceStockBtn = new ExtendedBy("Experience Stocks Radio Btn", By.xpath("//label[@for='stocks-no-experience']"));
    private static final ExtendedBy experienceStockOneToFour = new ExtendedBy("Experience Stocks One To Four Years Radio Btn", By.xpath("//label[@for='stocks-one-to-four-years']"));
    private static final ExtendedBy experienceOptionsBtn = new ExtendedBy("Experience Options Radio Btn", By.xpath("//label[@for='options-no-experience']"));
    private static final ExtendedBy experienceDigitalAssetsBtn = new ExtendedBy("Experience DigitalAssets Radio Btn", By.xpath("//label[@for='digitalAssets-no-experience']"));
    private static final ExtendedBy experiencePrivatePlacementBtn = new ExtendedBy("Experience Private Placement Radio Btn", By.xpath("//label[@for='privatePlacement-no-experience']"));
    private static final ExtendedBy experienceBondsBtn = new ExtendedBy("Experience Bonds Radio Btn", By.xpath("//label[@for='bonds-no-experience']"));
    private static final ExtendedBy experienceRealStateBtn = new ExtendedBy("Experience RealState Radio Btn", By.xpath("//label[@for='realEstate-no-experience']"));

    private static final ExtendedBy exchangeFrequencyOften = new ExtendedBy("Exchange Frequency Often", By.xpath("//*[@id='exchange-often']/../label"));
    private static final ExtendedBy privatePlacement = new ExtendedBy("Private Placement Often", By.xpath("//*[@id='privateplacements-often']/../label"));
    private static final ExtendedBy objectivesConservative = new ExtendedBy("Objectives Conservative", By.xpath("//*[@id='objectives-conservative']/../label"));
    private static final ExtendedBy objectivesGrowthAndSpeculation = new ExtendedBy("Objectives Growth and Speculation", By.xpath("//*[@id='objectives-growth-and-speculation']/../label"));

    private static final ExtendedBy highRiskToleranceYes = new ExtendedBy("High Risk Tolerance Yes", By.xpath("//input[@id='highrisktolerance-yes']/.."));
    private static final ExtendedBy riskToleranceAffirmationOneYes = new ExtendedBy("Risk Tolerance Affirmation One Yes", By.xpath("//input[@id='risktoleranceaffirmationunderstandinvestmentondtm-yes']/.."));
    private static final ExtendedBy riskToleranceAffirmationTwoYes = new ExtendedBy("Risk Tolerance Affirmation Two Yes", By.xpath("//input[@id='risktoleranceaffirmationnoliquidityneedsforinvestmentondtm-yes']/.."));
    private static final ExtendedBy continueButton = new ExtendedBy("Continue Button", By.xpath("//button[@id='suitability-continue']"));
    private static final ExtendedBy fullName = new ExtendedBy("Full name", By.name("fullName"));

    public InvestWizard_InvestorInformationPage(Browser browser) {
        super(browser);
    }

    public void pickInvestorTypeIndividual(){
        browser.click(investorTypeIndividual, false);
    }

    public void pickInvestorTypeEntity(){
        browser.click(investorTypeEntity, false);
    }

    public void pickRegisteredRepresentativeNo(){
        browser.click(registeredRepresentativeNo, false);
    }

    public void pickFinraMemberNo(){
        browser.click(finraMemberNo, false);
    }

    public void pickExchangeFrequencyOften(){
        browser.click(exchangeFrequencyOften, false);
    }

    public void pickPrivatePlacement(){
        browser.click(privatePlacement, false);
    }

    public void pickObjectivesConservative(){
        browser.click(objectivesConservative, false);
    }

    public void pickGrowthAndSpeculation(){
        browser.click(objectivesGrowthAndSpeculation, false);
    }

    public void pickHighRiskToleranceYes(){
        browser.click(highRiskToleranceYes, false);
    }

    public void pickRiskToleranceAffirmationOneYes(){
        browser.click(riskToleranceAffirmationOneYes, false);
    }

    public void pickRiskToleranceAffirmationTwoYes(){
        browser.click(riskToleranceAffirmationTwoYes, false);
    }

    public void scrollToContinueButton(){
        browser.scrollToElement(browser.findElement(continueButton), false);
    }

    public InvestWizard_SubmitFormPage clickContinueBtn(){
        browser.click(continueButton, false);
        return new InvestWizard_SubmitFormPage(browser);
    }

    public void typeFullName(){
        browser.waitForElementVisibility(fullName);
        browser.typeTextElement(fullName, "Automation - Fernando Martin");
    }

    public void pickExperience_Stocks_NoExperience() {
        browser.click(experienceStockBtn);
    }
    public void pickExperience_Stocks_OneToFourYears() {
        browser.click(experienceStockOneToFour);
    }
    public void pickExperience_Options_NoExperience() {
        browser.click(experienceOptionsBtn);
    }
    public void pickExperience_DigitalAssets_NoExperience() {
        browser.click(experienceDigitalAssetsBtn);
    }
    public void pickExperience_PrivatePlacement_NoExperience() {
        browser.click(experiencePrivatePlacementBtn);
    }
    public void pickExperience_Bonds_NoExperience() {
        browser.click(experienceBondsBtn);
    }
    public void pickExperience_RealState_NoExperience() {
        browser.click(experienceRealStateBtn);
    }

    public void pickLicensedBrokerDealerNo(){
        browser.click(licensedBrokerDealerNo, false);
    }

    public void fillInvestorInformationForm(){
        browser.refreshPage();
        // Investor Information Section
        pickInvestorTypeIndividual();
        pickRegisteredRepresentativeNo();
        pickFinraMemberNo();
        // Investment Experience
        pickExperience_Stocks_NoExperience();
        pickExperience_Options_NoExperience();
        pickExperience_DigitalAssets_NoExperience();
        pickExperience_PrivatePlacement_NoExperience();
        pickExperience_Bonds_NoExperience();
        pickExperience_RealState_NoExperience();
        // Investment Frequency
        pickExchangeFrequencyOften();
        pickPrivatePlacement();
        pickObjectivesConservative(); // click this one to see the msg. // TODO validate err msg
        pickGrowthAndSpeculation();
        // Risk Tolerance Affirmation
        pickHighRiskToleranceYes();
        pickRiskToleranceAffirmationOneYes();
        pickRiskToleranceAffirmationTwoYes();
    }

    public InvestWizard_InvestorInformationPage fillEntityInvestorInformationForm(){
        // Investor Information Section
        pickInvestorTypeEntity();
        pickLicensedBrokerDealerNo();
        pickFinraMemberNo();
        // Investment Experience
        pickExperience_Stocks_OneToFourYears();
        pickExperience_Options_NoExperience();
        pickExperience_DigitalAssets_NoExperience();
        pickExperience_PrivatePlacement_NoExperience();
        pickExperience_Bonds_NoExperience();
        pickExperience_RealState_NoExperience();
        // Investment Frequency
        pickExchangeFrequencyOften();
        pickPrivatePlacement();
        pickGrowthAndSpeculation();
        // Risk Tolerance Affirmation
        pickHighRiskToleranceYes();
        pickRiskToleranceAffirmationOneYes();
        pickRiskToleranceAffirmationTwoYes();
        return this;
    }

}
