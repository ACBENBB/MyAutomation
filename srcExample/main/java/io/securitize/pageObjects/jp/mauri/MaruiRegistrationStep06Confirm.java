package io.securitize.pageObjects.jp.mauri;

import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.jp.abstractClass.AbstractJpPage;
import org.openqa.selenium.By;

public class MaruiRegistrationStep06Confirm extends AbstractJpPage {

    private static final ExtendedBy step06Header = new ExtendedBy("Step 06 Head Line", By.xpath("//h1[text()[contains(., '入力内容確認')]]"));
    private static final ExtendedBy nameRow = new ExtendedBy("Name Row", By.xpath("//*[@class='row' and contains(.,'氏名') and not(contains(.,'カナ'))]"));
    private static final ExtendedBy nameKanaRow = new ExtendedBy("Name Kana Row", By.xpath("//*[@class='row' and contains(.,'氏名（カナ）')]"));
    private static final ExtendedBy genderRow = new ExtendedBy("Gender Row", By.xpath("//*[@class='row' and contains(.,'性別')]"));
    private static final ExtendedBy birthdayRow = new ExtendedBy("Birthday Row", By.xpath("//*[@class='row' and contains(.,'生年月日')]"));
    private static final ExtendedBy postalCodeRow = new ExtendedBy("Postal Code Row", By.xpath("//*[@class='row' and contains(.,'郵便番号')]"));
    private static final ExtendedBy addressRow = new ExtendedBy("Address Row", By.xpath("//*[@class='row' and contains(.,'住所')]"));
    private static final ExtendedBy telephoneRow = new ExtendedBy("Telephone Row", By.xpath("//*[@class='row' and contains(.,'電話番号')]"));
    private static final ExtendedBy incomeSourceRow = new ExtendedBy("Income Source Row", By.xpath("//*[@class='row' and contains(.,'主な収入源')]"));
    private static final ExtendedBy jobRow = new ExtendedBy("Job Row", By.xpath("//*[@class='row' and contains(.,'職業')]"));
    private static final ExtendedBy annualIncomeRow = new ExtendedBy("Annual Income Row", By.xpath("//*[@class='row' and contains(.,'年収')]"));
    private static final ExtendedBy assetStatusRow = new ExtendedBy("Asset Status Row", By.xpath("//*[@class='row' and contains(.,'資産の状況')]"));
    private static final ExtendedBy fundNature = new ExtendedBy("Fund Nature Row", By.xpath("//*[@class='row' and contains(.,'資金の種類')]"));
    private static final ExtendedBy investmentPurposeRow = new ExtendedBy("Investment Purpose Row", By.xpath("//*[@class='row' and contains(.,'投資目的')]"));
    private static final ExtendedBy investmentPolicyRow = new ExtendedBy("Investment Policy Row", By.xpath("//*[@class='row' and contains(.,'投資で重視すること')]"));
    private static final ExtendedBy investmentExperiencesRow = new ExtendedBy("Investment Experiences Row", By.xpath("//*[@class='row' and contains(.,'投資経験')]"));
    private static final ExtendedBy financialExperiencesRow = new ExtendedBy("Financial Experiences Row", By.xpath("//*[@class='row' and contains(.,'金融に係る業務経験')]"));
    private static final ExtendedBy politicallyExposedPersonsRow = new ExtendedBy("Politically Exposed Persons Row", By.xpath("//*[@class='row' and contains(.,'外国PEPsへの該当性')]"));
    private static final ExtendedBy howYouKnowThisBondRow = new ExtendedBy("How You Know This Bond Row", By.xpath("//*[@class='row' and contains(.,'本社債をお知りになった')]"));
    private static final ExtendedBy otherReason1Row = new ExtendedBy("Other Reason 1 Row", By.xpath("(//*[@class='row' and contains(.,'前問でその他を選択された方')])[1]"));
    private static final ExtendedBy whyYouChooseThisBondRow = new ExtendedBy("How You Choose This Bond Row", By.xpath("//*[@class='row' and contains(.,'本社債をお選びいただいた')]"));
    private static final ExtendedBy otherReason2Row = new ExtendedBy("Other Reason 2 Row", By.xpath("(//*[@class='row' and contains(.,'前問でその他を選択された方')])[2]"));
    private static final ExtendedBy backButton = new ExtendedBy("Back Button", By.xpath("//button/*[text()[contains(.,'戻る')]]"));
    private static final ExtendedBy confirmButton = new ExtendedBy("Next Step Button", By.xpath("//button/*[text()[contains(.,'上記内容に間違いありません')]]"));

    public MaruiRegistrationStep06Confirm(Browser browser) {
        super(browser, confirmButton);
    }

    public String trim(String value, String delete) {
        return value.replace(delete, "").replace("\n", "");
    }

    public String getNameText() {
        return trim(browser.getElementText(nameRow), "氏名");
    }

    public String getNameKanaText() {
        return trim(browser.getElementText(nameKanaRow), "氏名（カナ）");
    }

    public String getGenderText() {
        return trim(browser.getElementText(genderRow), "性別");
    }

    public String getBirthdayText() {
        return trim(browser.getElementText(birthdayRow), "生年月日");
    }

    public String getPostalCodeText() {
        return trim(browser.getElementText(postalCodeRow), "郵便番号");
    }

    public String getAddressText() {
        return trim(browser.getElementText(addressRow), "住所");
    }

    public String getTelephoneNumberText() {
        return trim(browser.getElementText(telephoneRow), "電話番号");
    }

    public String getIncomeSourceText() {
        return trim(browser.getElementText(incomeSourceRow), "主な収入源");
    }

    public String getJobText() {
        return trim(browser.getElementText(jobRow), "職業");
    }

    public String getAnnualIncomeText() {
        return trim(browser.getElementText(annualIncomeRow), "年収");
    }

    public String getAssetStatusText() {
        return trim(browser.getElementText(assetStatusRow), "資産の状況");
    }

    public String getFundNatureText() {
        return trim(browser.getElementText(fundNature), "資金の種類");
    }

    public String getInvestmentPurposeText() {
        return trim(browser.getElementText(investmentPurposeRow), "投資目的");
    }

    public String getInvestmentPolicyText() {
        return trim(browser.getElementText(investmentPolicyRow), "投資で重視すること");
    }

    public String getInvestmentExperiencesText() {
        return trim(browser.getElementText(investmentExperiencesRow), "投資経験");
    }

    public String getFinancialBusinessExperiencesText() {
        return trim(browser.getElementText(financialExperiencesRow), "金融に係る業務経験");
    }

    public String getPoliticallyExposedPersonsText() {
        return trim(browser.getElementText(politicallyExposedPersonsRow), "外国PEPsへの該当性");
    }

    public String getHowYouKnowThisBondText() {
        return trim(browser.getElementText(howYouKnowThisBondRow), "本社債をお知りになったきっかけ（複数ご選択頂けます）");
    }

    public String getHowYouKnowThisBondOtherReasonText() {
        return trim(browser.getElementText(otherReason1Row), "（前問でその他を選択された方）以下にご入力ください");
    }

    public String getWhyYouChooseThisBondText() {
        return trim(browser.getElementText(whyYouChooseThisBondRow), "本社債をお選びいただいた理由（複数ご選択頂けます）");
    }

    public String getWhyYouChooseThisBondOtherReasonText() {
        return trim(browser.getElementText(otherReason2Row), "（前問でその他を選択された方）以下にご入力ください");
    }

    public void clickConfirmButton() {
        browser.click(confirmButton);
        browser.waitForElementToVanish(step06Header);
    }

    public void clickConfirmButton(boolean retry) {
        RetryOnExceptions.retryFunction(()-> {clickConfirmButton(); return null;}, ()-> null, retry);
    }

    public boolean backButtonIsEnabled() {
        return browser.isElementEnabled(backButton);
    }
}
