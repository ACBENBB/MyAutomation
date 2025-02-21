package io.securitize.pageObjects.investorsOnboarding.nie;

import io.securitize.infra.enums.CurrencyIds;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;
import org.openqa.selenium.By;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;
import static io.securitize.tests.fundraise.abstractclass.ConstantsStrings.*;
import static io.securitize.tests.fundraise.abstractclass.ConstantsStrings.KYCstatus.*;

public class NieInvestmentDetails extends AbstractPage<NieInvestmentDetails> {

    private static final ExtendedBy investmentAmount = new ExtendedBy("Investment amount field", By.xpath("(//input[@inputmode='decimal'])[1]"));
    private static final ExtendedBy receivedUnitsAmount = new ExtendedBy("Received units amount field", By.xpath("//input[@inputmode='decimal']"));
    private static final ExtendedBy investButton = new ExtendedBy("Invest button", By.id("btn-invest"));
    private static final ExtendedBy completeKYCVerification = new ExtendedBy("Invest button", By.id("btn-complete-verification"));
    private static final ExtendedBy viewIssuerDashboardLink = new ExtendedBy("View Issuer Dashboard Link", By.xpath("(//span[@class='ladda-label']/span[@class])[1]"));
    private static final ExtendedBy investmentCurrencyCombo = new ExtendedBy("Combo to see the currency options", By.xpath("(//*[@alt = 'USD' or @alt= 'ETH' or @alt = 'EUR'])[1]"));
    private static final ExtendedBy investmentCurrencyUsdButton = new ExtendedBy("Set investment current button: USD", By.xpath("//*[@alt = 'USD']"));
    private static final ExtendedBy investmentCurrencyEurButton = new ExtendedBy("Set investment current button: EUR", By.xpath("//*[@alt = 'EUR']"));
    private static final ExtendedBy investmentCurrencyEthButton = new ExtendedBy("Set investment current button: ETH", By.xpath("//*[@alt = 'ETH']"));
    private static final ExtendedBy fundYourInvestmentMsgTitle = new ExtendedBy("Fund your investment Msg Title", By.id("opportunity-details-card-fund-investment--title"));
    private static final ExtendedBy seeFundingInstructionsButton = new ExtendedBy("See funding instructions", By.id("opportunity-details-card-fund-investment--btn"));
    private static final ExtendedBy fundYourInvestmentMsgDetails = new ExtendedBy("Fund your investment Msg Details", By.id("opportunity-details-card-fund-investment--description"));
    private static final ExtendedBy verificationIsPendingModalMessage = new ExtendedBy("Your verification is pending modal message", By.xpath("//div[contains(@class, 'status-alert-modal')]/h4[contains(text(), 'Your verification is pending')]"));
    private static final ExtendedBy closeModalButton = new ExtendedBy("Close modal button", By.xpath("//button[contains(@class, 'close')]"));
    private static final ExtendedBy userVerificationStatus = new ExtendedBy("User verification status", By.xpath("//span[contains(@class, 'user-menu-nie_menu__status')]"));
    private static final ExtendedBy documentsSection = new ExtendedBy("Documents section", By.xpath("//div[contains(@class,'DocumentsCard__opportunityDocuments__MZ5Oj')]"));
    private static final ExtendedBy documentsTitle = new ExtendedBy("Documents title", By.xpath("//span[contains(@class,'DocumentsCard__opportunityDocuments__title__R7K5D')]"));
    private static final ExtendedBy documents = new ExtendedBy("Documents list", By.xpath("//div[contains(@class,'DocumentsCard__opportunityDocuments__document__z0kPY')]"));
    private static final ExtendedBy documentsBtn = new ExtendedBy("Documents button", By.xpath(".//div[2]"));
    private static final ExtendedBy documentsName = new ExtendedBy("Documents name", By.xpath(".//div[1]"));
    private static final ExtendedBy kycStatusPopUp = new ExtendedBy("KYC Status Popup", By.xpath("//div[@class='status-alert-modal__body  p-4 modal-body']"));
    private static final ExtendedBy popUpTitle = new ExtendedBy("KYC Status Text", By.xpath("//div[@class='status-alert-modal__body  p-4 modal-body']/h4"));
    private static final ExtendedBy popUpBtnCompleteVerification = new ExtendedBy("Button Complete Verification", By.id("btn-complete-verification"));
    private static final ExtendedBy popUpLogo = new ExtendedBy("Pop Up Logo", By.xpath("//div[@class='status-alert-modal__body  p-4 modal-body']/img"));
    private static final ExtendedBy popUpDescription = new ExtendedBy("Pop Up Description",By.xpath("//div[@class='status-alert-modal__body  p-4 modal-body']/p"));
    private static final ExtendedBy changeOfStatusLogo = new ExtendedBy("Change of Status Logo", By.xpath("//div[@id='opportunity-details-card-change-of-status']/img"));
    private static final ExtendedBy changeOfStatusTitle = new ExtendedBy("Change of Status Title", By.id("opportunity-details-card-change-of-status--title"));
    private static final ExtendedBy changeOfStatusDescription = new ExtendedBy("Change of Status Description", By.id("opportunity-details-card-change-of-status--description"));
    private static final ExtendedBy contactUsBtn = new ExtendedBy("Contact Us Button", By.id("opportunity-details-card-change-of-status--btn"));
    private static final ExtendedBy investmentRestrictedMessage = new ExtendedBy("Investment Privileges Restricted message", By.id("opportunity-details-card-investment-requires-kyc"));


    public NieInvestmentDetails(Browser browser) {
        super(browser);
    }

    public NieInvestmentDetails setInvestmentAmount(float amount) {
        browser.sendTextElementIncTrimDecimals(investmentAmount, amount + "");
        return this;
    }

    public NieInvestmentDetails setInvestmentAmount(int amount) {
        browser.typeTextElement(investmentAmount, amount + "");
        return this;
    }

    public NieInvestmentDetails setStringInvestmentAmount(String amount) {
        browser.typeTextElement(investmentAmount, amount);
        return this;
    }

    public String getReceivedUnits() {
        return browser.getElementText(receivedUnitsAmount);
    }

    public NieInvestorQualifications clickInvest() {
        browser.click(investButton, false);
        return new NieInvestorQualifications(browser);
    }

    public void clickInvestWithoutQualification() {
        browser.click(investButton, false);
    }

    public void clickViewIssuerDashboardLink() {
        browser.click(viewIssuerDashboardLink, false);
    }

    public void clickInvestWithAllowSkipAccreditation() {
        browser.click(investButton, false);
    }

    public void clickInvestWithoutKYCVerified() {
        browser.click(investButton, false);
        browser.click(completeKYCVerification, false);
    }

    public NieInvestmentDetails setInvestmentCurrencyString(String investmentCurrency) {
        browser.click(investmentCurrencyCombo, false);
        ExtendedBy by = null;
        switch (investmentCurrency) {
            case "USD":
                by = investmentCurrencyUsdButton;
                break;
            case "EUR":
                by = investmentCurrencyEurButton;
                break;
            case "ETH":
                by = investmentCurrencyEthButton;
                break;
            default:
                errorAndStop("Unsupported investment current type of: " + investmentCurrency, true);
        }
        browser.click(by, false);
        return this;
    }

    public NieInvestmentDetails setInvestmentCurrency(CurrencyIds investmentCurrency) {
        browser.click(investmentCurrencyCombo, false);

        ExtendedBy by = null;
        switch (investmentCurrency) {
            case USD:
                by = investmentCurrencyUsdButton;
                break;
            case EUR:
                by = investmentCurrencyEurButton;
                break;
            case ETH:
                by = investmentCurrencyEthButton;
                break;
            default:
                errorAndStop("Unsupported investment current type of: " + investmentCurrency, true);
        }
        browser.click(by, false);

        return this;
    }

    public NieInvestmentDetails clickInvestButton() {
        browser.click(investButton, false);
        return this;
    }

    public void clickInvestmentAmountInput() {
        browser.click(investmentAmount, false);
    }

    public boolean isInvestmentApprovedMsgTitleDisplayed() {
        return browser.isElementVisible(fundYourInvestmentMsgTitle);
    }

    public boolean isInvestmentApprovedButtonDisplayed() {
        return browser.isElementVisible(seeFundingInstructionsButton);
    }

    public boolean isInvestmentApprovedMsgDetailsDisplayed() {
        return browser.isElementVisible(fundYourInvestmentMsgDetails);
    }

    public NieInvestmentDetails waitForVerificationIsPendingMessageVisible() {
        browser.waitForElementVisibility(verificationIsPendingModalMessage);
        return this;
    }

    public void closeModalAlert() {
        browser.click(closeModalButton, false);
    }

    public String getUserVerificationState() {
        return browser.getElementText(userVerificationStatus);
    }

    public void waitForInvestorStatusToBecome(String expectedVerificationStatus) {
        Browser.waitForExpressionToEqual(t -> {
                    try {
                        browser.refreshPage(true);
                        new NieInvestmentDetails(browser); // waits for page to finish loading
                        info("Checking user verification state...");
                        String verificationState = getUserVerificationState();
                        return expectedVerificationStatus.equalsIgnoreCase(verificationState);
                    } catch (Exception e) {
                        return false;
                    }
                }, null, true,
                "Waiting for investor verification status to become " + expectedVerificationStatus
                , 60, 5000);
    }

    public void verifyDocumentsSectionIsDisplayed() {
        Assert.assertTrue(browser.isElementVisible(documentsSection));
    }

    public void verifyDocumentsTitleIsDisplayed() {
        Assert.assertTrue(browser.isElementVisible(documentsTitle));
        Assert.assertEquals(browser.findElement(documentsTitle).getText(), "Documents");
    }

    public void clickOnDownloadDoc(WebElement doc) {
        browser.click(doc, "Click on download button");
    }

    public List<WebElement> getDocuments() {
        return browser.findElements(documents);
    }

    public void verifyDownloadDocuments() {
        String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
        List<WebElement> listOfDocuments = browser.findElements(documents);
        String AWS_Document_url = null;
        final String environment = "sandbox";

        for (WebElement document : listOfDocuments) {
            // select url by name of document
            if (document.findElement(documentsName.getBy()).getText().equals(DOCUMENT_FIRST_NAME)) {

                if (currentEnvironment.equals(environment)) {
                    AWS_Document_url = DOCUMENT_EXAMPLE_URL_SANDBOX;
                } else {
                    AWS_Document_url = DOCUMENT_EXAMPLE_URL_RC;
                }

            } else if (document.findElement(documentsName.getBy()).getText().equals(DOCUMENT_SECOND_NAME)) {

                if (currentEnvironment.equals(environment)) {
                    AWS_Document_url = DOCUMENT_OFFERING_MEMORANDUM_URL_SANDBOX;
                } else {
                    AWS_Document_url = DOCUMENT_OFFERING_MEMORANDUM_URL_RC;
                }

            } else if (document.findElement(documentsName.getBy()).getText().equals(DOCUMENT_THIRD_NAME)) {

                if (currentEnvironment.equals(environment)) {
                    AWS_Document_url = DOCUMENT_FORM_CRS_URL_SANDBOX;
                } else {
                    AWS_Document_url = DOCUMENT_FORM_CRS_URL_RC;
                }
            }
            // click on download button
            document.findElement(documentsBtn.getBy()).click();
            // verify url
            browser.getDriver().getWindowHandles().forEach(tab -> browser.getDriver().switchTo().window(tab));
            Assert.assertTrue(browser.getCurrentUrl().contains(AWS_Document_url));
            browser.closeLastTabAndSwitchToPreviousOne();
        }
    }

    public boolean isPopUpTitleCorrect(String kycStatus) {
        String textDetail = null;
        if (kycStatus.equals(UPDATES_REQUIRED.getStatus())) {
            textDetail = KYC_UPDATES_REQUIRED_TITLE;
        } else if (kycStatus.equals(PROCESSING.getStatus())) {
            textDetail = KYC_PROCESSING_TITLE;
        } else if (kycStatus.equals(EXPIRED.getStatus())) {
            textDetail = KYC_EXPIRED_TITLE;
        } else if (kycStatus.equals(NONE.getStatus())) {
            textDetail = KYC_EXPIRED_TITLE;
        }
        Assert.assertTrue(browser.isElementVisible(popUpTitle));
        return browser.findElement(popUpTitle).getText().equals(textDetail);
    }
    public boolean isPopUpBtnTextCorrect(String kycStatus) {
        String btnText = null;
        if (kycStatus.equals(UPDATES_REQUIRED.getStatus())) {
            btnText = KYC_UPDATES_REQUIRED_BTN;
        } else if (kycStatus.equals(PROCESSING.getStatus())) {
            btnText = CONTACT_US_BTN;
        } else if (kycStatus.equals(EXPIRED.getStatus())) {
            btnText = KYC_EXPIRED_BTN;
        } else if (kycStatus.equals(NONE.getStatus())) {
            btnText = KYC_EXPIRED_BTN;
        }
        Assert.assertTrue(browser.isElementVisible(popUpBtnCompleteVerification));
        return browser.findElement(popUpBtnCompleteVerification).getText().equals(btnText);
    }
    public boolean isPopUpLogoDisplayed() {
        return browser.isElementVisible(popUpLogo);
    }
    public boolean isPopUpDescriptionDisplayed() {
        return browser.isElementVisible(popUpDescription);
    }
    public void clickCompleteVerification() {
        browser.findElement(popUpBtnCompleteVerification).click();
    }
    public void waitForPopUpToBeDisplayed() {
        browser.waitForElementVisibility(kycStatusPopUp);
    }
    public boolean isChangeOfStatusLogoDisplayed() {
        return browser.isElementVisible(changeOfStatusLogo);
    }
    public String getChangeOfStatusTitle() {
        Assert.assertTrue(browser.isElementVisible(changeOfStatusTitle));
        return browser.findElement(changeOfStatusTitle).getText();
    }
    public boolean isChangeOfStatusDescriptionDisplayed() {
        return browser.isElementVisible(changeOfStatusDescription);
    }
    public String getContactUsBtnText() {
        Assert.assertTrue(browser.isElementVisible(contactUsBtn));
        return browser.findElement(contactUsBtn).getText();
    }
    public boolean isInvestmentRestrictedMessageDisplayed() {
        return browser.isElementVisible(investmentRestrictedMessage);
    }
}