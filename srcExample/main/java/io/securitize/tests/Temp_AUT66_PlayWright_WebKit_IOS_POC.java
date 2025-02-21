package io.securitize.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.HttpCredentials;
import com.microsoft.playwright.options.WaitUntilState;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.sumsub.IdDocSubType;
import io.securitize.infra.api.sumsub.IdDocType;
import io.securitize.infra.api.sumsub.SumSubAPI;
import io.securitize.infra.api.sumsub.SumsubUniqueImage;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.EmailWrapper;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorRegistration01InvestorType;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc03IndividualAddress;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc04IndividualLegalInformation;
import io.securitize.tests.abstractClass.AbstractTest;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static io.securitize.infra.reporting.MultiReporter.info;

public class Temp_AUT66_PlayWright_WebKit_IOS_POC extends AbstractTest {

    private static final String EXTRACT_LINK_FROM_EMAIL_REGEX = "<a href=\"(.*?)\" class=\"action-button\"";
    private static final String SELECT_OPTION_TEMPLATE = "//div[@role = 'option' and (text()='%s')]";

    @Test
    public void playWrightPOCWebkitTest() throws IOException {
        String state = "Alaska";
        String country = "United States of America";

        try (Playwright playwright = Playwright.create()) {
            // open browser (local)
       //     Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));

            // open browser (remote @ moon)
          Browser browser = playwright.webkit().connect("wss://moon.rc.securitize.io/playwright/webkit/playwright-1.22.2?headless=false&enableVideo=true");

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(390, 844)
                    .setRecordVideoDir(Paths.get("playWrightVideos/"))
                    .setIgnoreHTTPSErrors(true)
                    .setHttpCredentials(new HttpCredentials(Users.getProperty(UsersProperty.httpAuthenticationUsername), Users.getProperty(UsersProperty.httpAuthenticationPassword))));
            Page page = context.newPage();

            // generate random investor
            InvestorDetails investorDetails = InvestorDetails.generateUSInvestorForSSN();
            investorDetails.setState(state);

            // SecuritizeId create an account
            page.navigate(MainConfig.getProperty(MainConfigProperty.secIdUrl));

            /* those lines were commented as the flow was changed so this code is obsolete & not relevant

            page.click(locatorConvertor(SecuritizeIdRegistration01CreateAccountOldDelete.acceptCookiesButton)); // accept cookies
            page.click(locatorConvertor(SecuritizeIdInvestorLoginScreen.registrationButton)); // create account
            page.type(locatorConvertor(SecuritizeIdRegistration01CreateAccountOldDelete.emailTextBox), investorDetails.getEmail());
            page.type(locatorConvertor(SecuritizeIdRegistration01CreateAccountOldDelete.passwordTextBox), investorDetails.getEmail());

            // select country
            page.click(locatorConvertor(SecuritizeIdRegistration01CreateAccountOldDelete.countryOfResidenceSelector));
            String locatorCountryXpath = String.format(SELECT_OPTION_TEMPLATE, country);
            page.click(locatorCountryXpath);

            // select state
            page.click(locatorConvertor(SecuritizeIdRegistration01CreateAccountOldDelete.stateOfResidenceSelector));
            String locatorStateXpath = String.format(SELECT_OPTION_TEMPLATE, state);
            page.click(locatorStateXpath);

            // click submit
            ElementHandle submitButton = page.querySelector(locatorConvertor(SecuritizeIdRegistration01CreateAccountOldDelete.createAccountButton));
            submitButton.click();
            submitButton.waitForElementState(ElementState.HIDDEN);
             */


            // Extract link from email and navigate
            String emailContent = EmailWrapper.waitAndGetEmailContentByRecipientAddressAndRegex(investorDetails.getEmail(), EXTRACT_LINK_FROM_EMAIL_REGEX);
            String link = RegexWrapper.getFirstGroup(emailContent, EXTRACT_LINK_FROM_EMAIL_REGEX);
            page.navigate("about:blank"); // workaround to a bug of navigation not happening in this page
            page.navigate(link);

            // clickSkipTwoFactor
            page.click(locatorConvertor(SecuritizeIdDashboard.twoFaLater));

            // clickCompleteYourDetails
            page.click(locatorConvertor(SecuritizeIdDashboard.completeInvestorDetailsButton));

            // click continue
            page.click(locatorConvertor(SecuritizeIdInvestorRegistration01InvestorType.continueButton));


            // sumsub bypass
            int imageIndex = SumsubUniqueImage.getDriverLicenseFrontIndex();
            String frontImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-front" + imageIndex + ".jpg");
            String backImagePath = ResourcesUtils.getResourcePathByName("images" + "/" + "driverLicense-back.jpg");
            String israeliPassportPath = ResourcesUtils.getResourcePathByName("images" + "/" + "IsraeliPassport.jpg");
            String israeliIdPath = ResourcesUtils.getResourcePathByName("images" + "/" + "Israeli-Id.jpg");
            String selfiePath = ResourcesUtils.getResourcePathByName("images" + "/" + "selfie.jpg");

            InvestorsAPI investorsAPI = new InvestorsAPI();
            String securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
            String applicantID = SumSubAPI.getApplicantId(securitizeIdProfileId);
            info(String.format("securitizeProfileId=%s is applicantId=%s", securitizeIdProfileId, applicantID));
            if (investorDetails.getCountry().equalsIgnoreCase("United States of America")) {
                SumSubAPI.addIdDocument(applicantID, IdDocType.DRIVERS, IdDocSubType.FRONT_SIDE, frontImagePath, investorDetails.getCountryCode());
                SumSubAPI.addIdDocument(applicantID, IdDocType.DRIVERS, IdDocSubType.BACK_SIDE, backImagePath, investorDetails.getCountryCode());
            } else {
                SumSubAPI.addIdDocument(applicantID, IdDocType.ID_CARD, IdDocSubType.FRONT_SIDE, israeliIdPath, investorDetails.getCountryCode());
                SumSubAPI.addIdDocument(applicantID, IdDocType.ID_CARD, IdDocSubType.BACK_SIDE, israeliPassportPath, investorDetails.getCountryCode());
            }

            SumSubAPI.addIdDocument(applicantID, IdDocType.SELFIE, IdDocSubType.FRONT_SIDE, selfiePath, investorDetails.getCountryCode());
            page.reload(new Page.ReloadOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));



            // fill in the address page
            page.type(locatorConvertor(SecuritizeIdInvestorKyc03IndividualAddress.streetNameField), investorDetails.getStreetName());
            page.type(locatorConvertor(SecuritizeIdInvestorKyc03IndividualAddress.streetAdditionalInfoField), investorDetails.getStreetAdditionalInfo());
            page.type(locatorConvertor(SecuritizeIdInvestorKyc03IndividualAddress.cityField), investorDetails.getCity());
            page.type(locatorConvertor(SecuritizeIdInvestorKyc03IndividualAddress.zipCodeField), investorDetails.getPostalCode());
            page.type(locatorConvertor(SecuritizeIdInvestorKyc03IndividualAddress.phoneNumberField), investorDetails.getPhoneNumber());
            page.click(locatorConvertor(SecuritizeIdInvestorKyc03IndividualAddress.continueButton));


            // fill legal information
            page.click(locatorConvertor(SecuritizeIdInvestorKyc04IndividualLegalInformation.countryOfBirthSelector));
            String fullXpathSelector = String.format(SELECT_OPTION_TEMPLATE, investorDetails.getCountryOfBirth());
            page.click(fullXpathSelector);

            page.click(locatorConvertor(SecuritizeIdInvestorKyc04IndividualLegalInformation.stateOfBirthSelector));
            fullXpathSelector = String.format(SELECT_OPTION_TEMPLATE, investorDetails.getState());
            page.click(fullXpathSelector);

            page.type(locatorConvertor(SecuritizeIdInvestorKyc04IndividualLegalInformation.cityOfBirthField), investorDetails.getCityOfBirth());
            page.type(locatorConvertor(SecuritizeIdInvestorKyc04IndividualLegalInformation.taxIdField), investorDetails.getTaxId());
            page.click(locatorConvertor(SecuritizeIdInvestorKyc04IndividualLegalInformation.continueButton));

            securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
            SumSubAPI.setApplicantAsProvidedInfo(securitizeIdProfileId);

            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("webkit.png")));
        }
    }

    private String locatorConvertor(ExtendedBy extendedBy) {
        By internalBy = extendedBy.getBy();

        if (internalBy instanceof By.ByXPath) {
           return internalBy.toString().replace("By.xpath: ", "");
        } else if (internalBy instanceof By.ById) {
            return "#" + internalBy.toString().replace("By.id: ", "");
        } else {
            throw new NotImplementedException("Locator type not yet supported: " + internalBy.toString());
        }
    }
}


//
//        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
//        SecuritizeIdRegistration01CreateAccountOldDelete securitizeIdCreateAccountPage = securitizeIdLoginScreen
//        .clickAcceptCookies()
//        .clickCreateAccount();
//
//        securitizeIdCreateAccountPage
//        .insertEmailAddress(investorDetails.getEmail())
//        .insertPassword(investorDetails.getPassword())
//        .setCountryOfResidenceUSA_Alaska()
//        .clickCreateAccountButton();
//        endTestLevel();
//
//
//        startTestLevel("Extract link from email and navigate");
//        securitizeIdCreateAccountPage.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
//        endTestLevel();
//
//
//        startTestLevel("Skip two factor authentication");
//        SecuritizeIdDashboard securitizeIdDashboard = new SecuritizeIdDashboard(getBrowser());
//        securitizeIdDashboard
//        .clickSkipTwoFactor()
//        .clickCompleteYourDetails();
//        endTestLevel();
//
//
//        startTestLevel("Register investor whom Sumsub approved (verified)");
//        fillIndividualRegistrationSteps(investorDetails, true, false, true);
//        InvestorsAPI investorsAPI = new InvestorsAPI();
//        String securitizeIdProfileId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
//        SumSubAPI.setApplicantAsProvidedInfo(securitizeIdProfileId);
//        endTestLevel();