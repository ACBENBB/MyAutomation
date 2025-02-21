package io.securitize.pageObjects.investorsOnboarding.securitizeId.tradingSecondaryMarket;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;

public class TradingSecondaryMarketsSuitabilityFrom extends AbstractPage {

    private static final ExtendedBy marketPageTitle = new ExtendedBy("Market Page Title", By.xpath("//span[@class='page-title__progress__title--desktop']"));
    private static final ExtendedBy suitabilityBox = new ExtendedBy("Suitability Box", By.xpath("//div[@class='suitability-investor-information-form p-4 p-md-5 border-0 box-shadow card']"));

    private static final ExtendedBy investorTypeIndividual = new ExtendedBy("Investor type Individual", By.xpath("//input[@id='investortype-individual']/.."));
    private static final ExtendedBy registeredRepresentativeNo = new ExtendedBy("Registered Representative No", By.xpath("//input[@id='registeredrepresentativeofbrokerdealer-no']/.."));
    private static final ExtendedBy finraMemberNo = new ExtendedBy("Finra Member No", By.xpath("//input[@id='ismemberoremployedatstockexchangeorfinra-no']/.."));

    private static final ExtendedBy stocksNoExperienceRadioBtn = new ExtendedBy("Stocks No Experience Radio Btn", By.xpath("//label[@for='stocks-no-experience']"));
    private static final ExtendedBy optionsNoExperienceRadioBtn = new ExtendedBy("Options No Experience Radio Btn", By.xpath("//label[@for='options-no-experience']"));
    private static final ExtendedBy digitalAssetsNoExperienceRadioBtn = new ExtendedBy("Digital Assets No Experience Radio Btn", By.xpath("//label[@for='digitalAssets-no-experience']"));
    private static final ExtendedBy privateNoExperienceRadioBtn = new ExtendedBy("Private Placement No Experience Radio Btn", By.xpath("//label[@for='privatePlacement-no-experience']"));
    private static final ExtendedBy bondsNoExperienceRadioBtn = new ExtendedBy("Bonds No Experience Radio Btn", By.xpath("//label[@for='bonds-no-experience']"));
    private static final ExtendedBy realStateNoExperienceRadioBtn = new ExtendedBy("Real State No Experience Radio Btn", By.xpath("//label[@for='realEstate-no-experience']"));

    private static final ExtendedBy exchangeFrequencyOften = new ExtendedBy("Exchange Frequency Often", By.xpath("//*[@id='exchange-often']/../label"));
    private static final ExtendedBy privatePlacement = new ExtendedBy("Private Placement Often", By.xpath("//*[@id='privateplacements-often']/../label"));
    private static final ExtendedBy objectivesConservative = new ExtendedBy("Objectives Conservative", By.xpath("//*[@id='objectives-conservative']/../label"));
    private static final ExtendedBy objectivesGrowthAndSpeculation = new ExtendedBy("Objectives Growth and Speculation", By.xpath("//*[@id='objectives-growth-and-speculation']/../label"));

    private static final ExtendedBy highRiskToleranceYes = new ExtendedBy("High Risk Tolerance Yes", By.xpath("//input[@id='highrisktolerance-yes']/.."));
    private static final ExtendedBy riskToleranceAffirmationOneYes = new ExtendedBy("Risk Tolerance Affirmation One Yes", By.xpath("//input[@id='risktoleranceaffirmationunderstandinvestmentondtm-yes']/.."));
    private static final ExtendedBy riskToleranceAffirmationTwoYes = new ExtendedBy("Risk Tolerance Affirmation Two Yes", By.xpath("//input[@id='risktoleranceaffirmationnoliquidityneedsforinvestmentondtm-yes']/.."));
    private static final ExtendedBy suitabilityApplicationSubmitButton = new ExtendedBy("Continue Button", By.id("suitability-continue"));
    private static final ExtendedBy reviewButton = new ExtendedBy("Review Button", By.xpath("//span[text()='Review']/../.."));

    private static final ExtendedBy fullName = new ExtendedBy("Full name", By.name("fullName"));
    private static final ExtendedBy answerSuitabilityFormButton = new ExtendedBy("Suitability form Popup answer button", By.xpath("//div[@class='Agreements form-group']"));


    public TradingSecondaryMarketsSuitabilityFrom(Browser browser) {
        super(browser, investorTypeIndividual);
    }

    public void pickInvestorTypeIndividual(){
        browser.click(investorTypeIndividual, false);
    }

    public TradingSecondaryMarketsSuitabilityFrom clickAnswerSuitabilityFormButton() {
        browser.click(answerSuitabilityFormButton, true);
        return this;
    }

    public void pickRegisteredRepresentativeNo(){
        browser.click(registeredRepresentativeNo, false);
    }

    public void pickFinraMemberNo(){
        browser.click(finraMemberNo, false);
    }

    public void clickStocksNoExperienceRadioBtn(){
        browser.click(stocksNoExperienceRadioBtn, false);
    }

    public void clickOptionsNoExperienceRadioBtn(){
        browser.click(optionsNoExperienceRadioBtn, false);
    }

    public void clickDigitalAssetsNoExperienceRadioBtn(){
        browser.click(digitalAssetsNoExperienceRadioBtn, false);
    }

    public void clickPrivatePlacementNoExperienceRadioBtn(){
        browser.click(privateNoExperienceRadioBtn, false);
    }

    public void clickBondsNoExperienceRadioBtn(){
        browser.click(bondsNoExperienceRadioBtn, false);
    }

    public void clickRealStateNoExperienceRadioBtn(){
        browser.click(realStateNoExperienceRadioBtn, false);
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
        browser.scrollToElement(browser.findElement(suitabilityApplicationSubmitButton), false);
    }

    public void clickContinueButton(){
        browser.click(suitabilityApplicationSubmitButton, false);
    }

    public void clickReviewButton(){
        browser.click(reviewButton, false);
    }


    public void typeFullName(){
        browser.waitForElementVisibility(fullName);
        browser.typeTextElement(fullName, "Automation Trader");
    }



    public void completeMarketSuitability(){
        pickInvestorTypeIndividual();
        pickRegisteredRepresentativeNo();
        pickFinraMemberNo();
        clickStocksNoExperienceRadioBtn();
        clickOptionsNoExperienceRadioBtn();
        clickDigitalAssetsNoExperienceRadioBtn();
        clickPrivatePlacementNoExperienceRadioBtn();
        clickBondsNoExperienceRadioBtn();
        clickRealStateNoExperienceRadioBtn();
        scrollToContinueButton();
        pickExchangeFrequencyOften();
        pickPrivatePlacement();
        pickGrowthAndSpeculation();
        pickHighRiskToleranceYes();
        pickRiskToleranceAffirmationOneYes();
        pickRiskToleranceAffirmationTwoYes();
        clickContinueButton();
        typeFullName();
        clickContinueButton();
    }

}







