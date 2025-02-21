package io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AccreditationPage extends AbstractPage {

    private static final ExtendedBy iAmAnAccreditedInvestorRadioBtn = new ExtendedBy("I am an Accredited Investor - Radio Button", By.xpath("//*[contains (text() , 'I am an Accredited Investor')]"));
    private static final ExtendedBy iAmAnAccreditedInvestorRadioInput = new ExtendedBy("I am an Accredited Investor - Input Element", By.xpath("//input[@id='accredited-yes-text']"));
    private static final ExtendedBy iAmNotAnAccreditedInvestorRadioBtn = new ExtendedBy("I am not an Accredited Investor - Radio Button", By.xpath("//label[contains (text() , 'I am not an Accredited Investor')]/preceding-sibling::input"));
    private static final ExtendedBy iConsiderMyselfAQualifiedInvestorLabel = new ExtendedBy("I am a Qualified Investor - Label", By.xpath("//*[contains (text() , 'I am a Qualified Investor')]"));
    private static final ExtendedBy iConsiderMyselfProfessionalInvestorRadioBtn = new ExtendedBy("I agree and I consider myself a professional investor - Radio Button", By.id("accredited-yes-text"));
    private static final ExtendedBy submitAccreditationStatusBtn = new ExtendedBy("Submit Accreditation Status Button", By.xpath("//button[@color='primary']"));

    private static final ExtendedBy accreditationVerifiedH1 = new ExtendedBy("Submit Accreditation Status Button", By.xpath("//div[contains (text() , 'Accreditation verified')]"));
    private static final ExtendedBy getAccreditedWithSecuritizeMarkets = new ExtendedBy("Submit Accreditation Status Button", By.xpath("//*[contains (text() , 'Get accredited with Securitize Markets')]"));
    private static final ExtendedBy upperRightMenuQualificationStatus = new ExtendedBy("Upper Right Menu Qualification Status", By.xpath("//*[contains(text(), 'Denied')]"));
    private static final ExtendedBy upperRightMenuQualificationStatusExpired = new ExtendedBy("Upper Right Menu Qualification Status", By.xpath("//*[contains(text(), 'Expired')]"));

    private static final ExtendedBy holdingFlag = new ExtendedBy("Secondary market dashboard - Holding flag indicator", By.xpath("//*[contains(text(), 'Holding')]"));

    private static final ExtendedBy tradingButton = new ExtendedBy("Submit Accreditation Status Button", By.id("home-page-nav-secondary-market"));
    private static final ExtendedBy tradingAssets = new ExtendedBy("Submit Accreditation Status Button", By.xpath("//*[contains(text(), 'Tradable Assets')]"));
    private static final ExtendedBy getAccreditedButton = new ExtendedBy("Get accredited button", By.id("start-accreditation"));


    public AccreditationPage(Browser browser) {
        super(browser, submitAccreditationStatusBtn);
    }

    public String getAccreditationStatus() {
        return browser.getElementAttribute(accreditationVerifiedH1, "innerText");
    }

    public WebElement getImNotAnAccreditedInvestorRadioBtn() {
        return browser.findElement(iAmNotAnAccreditedInvestorRadioBtn);
    }

    public WebElement getImAnAccreditedInvestorInput() {
        return browser.findElement(iAmAnAccreditedInvestorRadioInput);
    }

    public String getUpperRightMenuQualificationStatus() {
        return browser.findElement(upperRightMenuQualificationStatus).getText();
    }

    public String getUpperRightMenuQualificationStatusExpired() {
        return browser.findElement(upperRightMenuQualificationStatusExpired).getText();
    }

    public AccreditationPage clickIamAccreditedInvestor() {
        browser.click(iAmAnAccreditedInvestorRadioBtn, false);
        return this;
    }

    // FOR NON US ACCREDITATION FLOW
    public AccreditationPage clickIConsiderMyselfProfessionalInvestor() {
        browser.click(iConsiderMyselfAQualifiedInvestorLabel, false);
        return this;
    }

    public WebElement getIConsiderMyselfProfessionalInvestor() {
        return browser.findElement(iConsiderMyselfProfessionalInvestorRadioBtn);
    }

    public AccreditationPage clickSubmitAccreditationStatusButton() {
        browser.click(submitAccreditationStatusBtn, false);
        return this;
    }

    public void clickGetAccreditedWithSecuritizeMarkets() {
        browser.click(getAccreditedWithSecuritizeMarkets, false);
    }

    public void refreshPageToUpdateAccreditationStatus() {
        for(int i = 0; i < 5; i++) {
            if(!(browser.findElements(accreditationVerifiedH1).size() > 0)) {
                browser.refreshPage();
            }
        }
    }

    public boolean isAccreditationStatusVerified() {
        return getAccreditationStatus().equalsIgnoreCase("Accreditation verified");
    }

    public boolean isIAmAnAccreditedInvestorRadioBtnVisible() {
        return browser.isElementVisible(iAmAnAccreditedInvestorRadioBtn);
    }

    public boolean isIAmNotAnAccreditedInvestorRadioBtnChecked() {
        return getImNotAnAccreditedInvestorRadioBtn().isSelected();
    }

    public boolean isIAmAnAccreditedInvestorRadioBtnChecked() {
        return getImAnAccreditedInvestorInput().isSelected();
    }

    public boolean isGetAccreditedWithSecuritizeMarketsBtnVisible() {
        return browser.isElementVisible(getAccreditedWithSecuritizeMarkets);
    }

    public boolean isGetUpperRightMenuQualificationStatusDenied() {
        return getUpperRightMenuQualificationStatus().equalsIgnoreCase("Denied");
    }

    public boolean isGetUpperRightMenuQualificationStatusExpired() {
        return getUpperRightMenuQualificationStatusExpired().equalsIgnoreCase("Expired");
    }

    public void clickTradingButton(){
        browser.click(tradingButton, false);
    }

    public boolean tradingAssets() {
        browser.waitForElementVisibility(tradingAssets);
        return browser.isElementVisible(tradingAssets);

    }

    public AccreditationMethodPage clickGetAccreditedButton() {
        browser.click(getAccreditedButton, false);
        return new AccreditationMethodPage(browser);
    }
}