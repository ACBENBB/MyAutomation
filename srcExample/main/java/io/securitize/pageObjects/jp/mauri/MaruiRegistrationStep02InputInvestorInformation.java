package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep02InputInvestorInformation extends AbstractJpPage {

    private static final ExtendedBy step02Header = new ExtendedBy("Step 02 Head Line", By.xpath("//h1[text()[contains(., '本人情報の入力')]]"));
    private static final ExtendedBy familyNameInputBox = new ExtendedBy("Family Name Input Box", By.xpath("//input[@placeholder='山田']"));
    private static final ExtendedBy givenNameInputBox = new ExtendedBy("Given Name Input Box", By.xpath("//input[@placeholder='太郎']"));
    private static final ExtendedBy familyNameKanaInputBox = new ExtendedBy("Family NameKana Input Box", By.xpath("//input[@placeholder='ヤマダ']"));
    private static final ExtendedBy givenNameKanaInputBox = new ExtendedBy("Given NameKana Input Box", By.xpath("//input[@placeholder='タロウ']"));
    private static final ExtendedBy genderFemaleRadioButton = new ExtendedBy("Gender Female Radio Button", By.xpath("//*[@name='gender' and @value='female']"));
    private static final ExtendedBy genderMaleRadioButton = new ExtendedBy("Gender Male Radio Button", By.xpath("//*[@name='gender' and @value='male']"));
    private static final ExtendedBy genderNoAnswerRadioButton = new ExtendedBy("Gender No Answer Radio Button", By.xpath("//*[@name='gender' and @value='']"));
    private static final ExtendedBy birthdayYearSelect = new ExtendedBy("Birth Year Select", By.xpath("//*[@autocomplete='bday-year']"));
    private static final ExtendedBy birthdayMonthSelect = new ExtendedBy("Birth Month Select", By.xpath("//*[@autocomplete='bday-month']"));
    private static final ExtendedBy birthdayDaySelect = new ExtendedBy("Birth Day Select", By.xpath("//*[@autocomplete='bday-day']"));
    private static final ExtendedBy postalCodeInputBox = new ExtendedBy("Postal Code Input Box", By.xpath("//*[@autocomplete='postal-code']"));
    private static final ExtendedBy addressLevel1Select = new ExtendedBy("Address Level 1 (prefecture) Select", By.xpath("//*[@autocomplete='address-level1']"));
    private static final ExtendedBy addressLevel2InputBox = new ExtendedBy("Address Level 2 (city) Input Box", By.xpath("//*[@autocomplete='address-level2']"));
    private static final ExtendedBy addressLine1InputBox = new ExtendedBy("Address Line 1 (street) Input Box", By.xpath("//*[@autocomplete='address-line1']"));
    private static final ExtendedBy addressLine2InputBox = new ExtendedBy("Address Line 2 (building) Input Box", By.xpath("//*[@autocomplete='address-line2']"));
    private static final ExtendedBy phoneNumberInputBox = new ExtendedBy("Phone Number Input Box", By.xpath("//*[@autocomplete='tel']"));
    private static final ExtendedBy backButton = new ExtendedBy("Back Button", By.xpath("//button/*[text()[contains(.,'戻る')]]"));
    private static final ExtendedBy nextStepButton = new ExtendedBy("Next Step Button", By.xpath("//button/*[text()[contains(.,'次のステップに進む')]]"));

    public MaruiRegistrationStep02InputInvestorInformation(Browser browser) {
        super(browser, step02Header);
    }

    public MaruiRegistrationStep02InputInvestorInformation enterName(String familyName, String givenName) {
        browser.typeTextElement(familyNameInputBox, familyName);
        browser.typeTextElement(givenNameInputBox, givenName);
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation enterNameKana(String familyNameKana, String givenNameKana) {
        browser.typeTextElement(familyNameKanaInputBox, familyNameKana);
        browser.typeTextElement(givenNameKanaInputBox, givenNameKana);
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation clickGender(String gender) {
        if (gender.equals("女性")) {
            browser.click(genderFemaleRadioButton);
        } else if (gender.equals("男性")) {
            browser.click(genderMaleRadioButton);
        } else {
            browser.click(genderNoAnswerRadioButton);
        }
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation setBirthday(String year, String month, String day) {
        browser.selectElementByVisibleText(birthdayYearSelect, year + "年");
        browser.selectElementByVisibleText(birthdayMonthSelect, month + "月");
        browser.selectElementByVisibleText(birthdayDaySelect, day + "日");
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation enterPostalCode(String postalCode) {
        browser.typeTextElement(postalCodeInputBox, postalCode);
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation enterPrefectureName(String prefectureName) {
        browser.selectElementByVisibleText(addressLevel1Select, prefectureName);
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation enterCityName(String cityName) {
        browser.typeTextElement(addressLevel2InputBox, cityName);
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation enterStreetName(String streetName) {
        browser.typeTextElement(addressLine1InputBox, streetName);
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation enterBuildingName(String buildingName) {
        browser.typeTextElement(addressLine2InputBox, buildingName);
        return this;
    }

    public MaruiRegistrationStep02InputInvestorInformation enterPhoneNumber(String phoneNumber) {
        browser.typeTextElement(phoneNumberInputBox, phoneNumber);
        return this;
    }

    public MaruiRegistrationStep03EposNetLogin clickNextStepButton() {
        browser.click(nextStepButton);
        browser.waitForElementToVanish(step02Header);
        return new MaruiRegistrationStep03EposNetLogin(browser);
    }

    public MaruiRegistrationStep03EposNetLogin clickNextStepButton(boolean retry) {
        return RetryOnExceptions.retryFunction(this::clickNextStepButton, ()-> null, retry);
    }

    public boolean backButtonIsEnabled() {
        return browser.isElementEnabled(backButton);
    }
}
