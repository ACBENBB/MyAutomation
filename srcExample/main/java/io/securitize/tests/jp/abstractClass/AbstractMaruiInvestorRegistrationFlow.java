package io.securitize.tests.jp.abstractClass;

import io.securitize.infra.api.DomainApi;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.RetryOnExceptions;
import io.securitize.pageObjects.jp.mauri.*;
import io.securitize.tests.abstractClass.AbstractUiTest;
import io.securitize.tests.jp.testData.MaruiInvestorDetails;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import java.util.List;
import java.util.function.Supplier;

import static io.securitize.infra.reporting.MultiReporter.*;
import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractMaruiInvestorRegistrationFlow extends AbstractUiTest {

    public boolean deleteInvestor(String emailAddress) {
        String apiBaseUrl = Users.getProperty(UsersProperty.jp_domainApiBaseUrl);
        String domainId = Users.getProperty(UsersProperty.marui_domainId);
        String apiKey = Users.getProperty(UsersProperty.jp_domainApiKey);
        DomainApi domainApi = new DomainApi(apiBaseUrl, domainId, apiKey);
        List<String> externalIdList = domainApi.getInvestorExternalId(emailAddress);

        boolean deleteResult;
        if (externalIdList.isEmpty()) {
            debug("investor with email (" + emailAddress + ") not exist. no investor to delete");
            deleteResult = true;
        } else if (externalIdList.size()==1) {
            deleteResult = domainApi.deleteInvestor(externalIdList.get(0));
        } else {
            errorAndStop("multiple investors with this email (" + emailAddress + ")", false); // should not happen
            deleteResult = false;
        }
        return deleteResult;
    }

    private JSONObject getInvestmentExperiencesJsonObject() {
        JSONObject investmentExperiencesObject = new JSONObject();
        investmentExperiencesObject.put("stock","none");
        investmentExperiencesObject.put("publicBond","none");
        investmentExperiencesObject.put("investmentTrust","none");
        investmentExperiencesObject.put("futures","none");
        investmentExperiencesObject.put("foreignCurrencyDeposits","none");
        investmentExperiencesObject.put("forexMarginTrading","none");
        investmentExperiencesObject.put("marginTrading","none");

        JSONObject investmentExperiences = new JSONObject();
        investmentExperiences.put("investmentExperiences", investmentExperiencesObject);
        return investmentExperiences;
    }

    private String getJsonInvestorAdditionalPrivateInformation(String lastNameKana, String firstNameKana) {
        JSONObject profileObject = new JSONObject();
        profileObject.put("nationality","JP");
        profileObject.put("lastNameKana",lastNameKana);
        profileObject.put("firstNameKana",firstNameKana);

        JSONObject bankObject = new JSONObject();
        bankObject.put("bankCode","0005");
        bankObject.put("branchCode","001");
        bankObject.put("accountNumber","2111107");
        bankObject.put("accountType","savings-account");
        bankObject.put("accountName","アサオカ\u3000ダイスケ");
        bankObject.put("eposnetCustomerId","0100000000112");
        bankObject.put("bankName","三菱ＵＦＪ");
        bankObject.put("branchName","本店");

        JSONObject investmentExperiences = getInvestmentExperiencesJsonObject();
        JSONObject showUIObject = new JSONObject();
        showUIObject.put("profile", profileObject);
        showUIObject.put("bank", bankObject);
        showUIObject.put("compatibility", investmentExperiences);

        JSONObject showUI = new JSONObject();
        showUI.put("showUI", showUIObject);

        String jsonPrivateValue = showUI.toString().replace("\"", "\\\"");
        return "{\"jsonPrivate\":\"" + jsonPrivateValue + "\"}";
    }

    public void updateInvestorAdditionalPrivateInformation(String email, String lastNameKana, String firstNameKana) {
        String apiBaseUrl = Users.getProperty(UsersProperty.jp_domainApiBaseUrl);
        String domainId = Users.getProperty(UsersProperty.marui_domainId);
        String apiKey = Users.getProperty(UsersProperty.jp_domainApiKey);
        DomainApi domainApi = new DomainApi(apiBaseUrl, domainId, apiKey);
        List<String> externalIdList = domainApi.getInvestorExternalId(email);
        assert externalIdList.size()==1 : "There should be only 1 investor";
        String externalId = externalIdList.get(0);
        domainApi.getInvestor(externalId); // before update
        String body = getJsonInvestorAdditionalPrivateInformation(lastNameKana, firstNameKana);
        boolean result = domainApi.patchInvestor(externalId, body);
        assert result : "update investor should complete successfully";
        domainApi.getInvestor(externalId); // after update
    }

    public void checkConfirmationPageExperienceValues(MaruiRegistrationStep06Confirm step06, MaruiInvestorDetails maruiInvestorDetails) {
        // construct expected
        String expectedName = maruiInvestorDetails.getFamilyName() + " " + maruiInvestorDetails.getGivenName();
        String expectedNameKana = maruiInvestorDetails.getFamilyNameKana() + " " + maruiInvestorDetails.getGivenNameKana();
        String expectedBirthday = String.format("%4d-%02d-%02d", Integer.valueOf(maruiInvestorDetails.getBirthYear()),
                Integer.valueOf(maruiInvestorDetails.getBirthMonth()), Integer.valueOf(maruiInvestorDetails.getBirthDay()));
        String expectedAddress = maruiInvestorDetails.getPrefecture() + " " + maruiInvestorDetails.getCity() +
                maruiInvestorDetails.getStreet() + " " + maruiInvestorDetails.getBuilding();

        SoftAssertions softAsserts = new SoftAssertions();
        softAsserts.assertThat(step06.getNameText()).isEqualTo(expectedName);
        softAsserts.assertThat(step06.getNameKanaText()).isEqualTo(expectedNameKana);
        softAsserts.assertThat(step06.getGenderText()).isEqualTo(maruiInvestorDetails.getGender());
        softAsserts.assertThat(step06.getBirthdayText()).isEqualTo(expectedBirthday);
        softAsserts.assertThat(step06.getPostalCodeText()).isEqualTo(maruiInvestorDetails.getPostalCode());
        softAsserts.assertThat(step06.getAddressText()).isEqualTo(expectedAddress.trim());
        softAsserts.assertThat(step06.getTelephoneNumberText()).isEqualTo(maruiInvestorDetails.getTelephone());
        softAsserts.assertThat(step06.getIncomeSourceText()).isEqualTo(maruiInvestorDetails.getIncomeSource());
        softAsserts.assertThat(step06.getJobText()).isEqualTo(maruiInvestorDetails.getJob());
        softAsserts.assertThat(step06.getAnnualIncomeText()).isEqualTo(maruiInvestorDetails.getAnnualIncome());
        softAsserts.assertThat(step06.getAssetStatusText()).isEqualTo(maruiInvestorDetails.getAssetStatus());
        softAsserts.assertThat(step06.getFundNatureText()).isEqualTo(maruiInvestorDetails.getFundNature());
        softAsserts.assertThat(step06.getInvestmentPurposeText()).isEqualTo(maruiInvestorDetails.getInvestmentPurpose());
        softAsserts.assertThat(step06.getInvestmentPolicyText()).isEqualTo(maruiInvestorDetails.getInvestmentPolicy());
        if (maruiInvestorDetails.getExperiencesStock().equals("なし")) {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).doesNotContain("株式");
        } else {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).contains("株式 " + maruiInvestorDetails.getExperiencesStock());
        }
        if (maruiInvestorDetails.getExperiencesBond().equals("なし")) {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).doesNotContain("公社債");
        } else {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).contains("公社債 " + maruiInvestorDetails.getExperiencesBond());
        }
        if (maruiInvestorDetails.getExperiencesTrust().equals("なし")) {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).doesNotContain("投資信託");
        } else {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).contains("投資信託 " + maruiInvestorDetails.getExperiencesTrust());
        }
        if (maruiInvestorDetails.getExperiencesFuture().equals("なし")) {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).doesNotContain("商品先物");
        } else {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).contains("商品先物 " + maruiInvestorDetails.getExperiencesFuture());
        }
        if (maruiInvestorDetails.getExperiencesForeignCurrencyDeposit().equals("なし")) {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).doesNotContain("外貨預金");
        } else {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).contains("外貨預金 " + maruiInvestorDetails.getExperiencesForeignCurrencyDeposit());
        }
        if (maruiInvestorDetails.getExperiencesForexMarginTrading().equals("なし")) {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).doesNotContain("外国為替証拠金取引");
        } else {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).contains("外国為替証拠金取引 " + maruiInvestorDetails.getExperiencesForexMarginTrading());
        }
        if (maruiInvestorDetails.getExperiencesMarginTrading().equals("なし")) {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).doesNotContain("信用取引・その他の金融先物");
        } else {
            softAsserts.assertThat(step06.getInvestmentExperiencesText()).contains("信用取引・その他の金融先物 " + maruiInvestorDetails.getExperiencesMarginTrading());
        }
        softAsserts.assertThat(step06.getFinancialBusinessExperiencesText()).isEqualTo(maruiInvestorDetails.getFinancialBusinessExperiences());
        softAsserts.assertThat(step06.getPoliticallyExposedPersonsText()).isEqualTo(maruiInvestorDetails.getApplicabilityOfPEPs());
        softAsserts.assertAll();
    }

    public void checkConfirmationPageQuestionnaireHowYouKnow(MaruiRegistrationStep06Confirm step06, MaruiInvestorDetails maruiInvestorDetails) {
        SoftAssertions softAsserts = new SoftAssertions();

        if (maruiInvestorDetails.getHowYouKnowThisBond_byHomePage().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getHowYouKnowThisBondText()).contains("丸井グループのHP");
        }
        if (maruiInvestorDetails.getHowYouKnowThisBond_byNewsRelease().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getHowYouKnowThisBondText()).contains("ニュースリリース");
        }
        if (maruiInvestorDetails.getHowYouKnowThisBond_byEposCardMail().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getHowYouKnowThisBondText()).contains("エポスカードからのご案内（メール・アプリ）");
        }
        if (maruiInvestorDetails.getHowYouKnowThisBond_byFriend().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getHowYouKnowThisBondText()).contains("友人知人の紹介");
        }
        if (maruiInvestorDetails.getHowYouKnowThisBond_bySns().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getHowYouKnowThisBondText()).contains("SNS");
        }
        if (maruiInvestorDetails.getHowYouKnowThisBond_byOtherWay().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getHowYouKnowThisBondText()).contains("その他");
            if (!maruiInvestorDetails.getHowYouKnowThisBond_byOtherWay_comment().isEmpty()) {
                softAsserts.assertThat(step06.getHowYouKnowThisBondOtherReasonText()).isEqualTo(maruiInvestorDetails.getHowYouKnowThisBond_byOtherWay_comment());
            }
        }
        softAsserts.assertAll();
    }

    public void checkConfirmationPageQuestionnaireWhyYouChoose(MaruiRegistrationStep06Confirm step06, MaruiInvestorDetails maruiInvestorDetails) {
        SoftAssertions softAsserts = new SoftAssertions();

        if (maruiInvestorDetails.getWhyYouChooseThisBond_becauseAgreeWithConcept().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getWhyYouChooseThisBondText()).contains("丸井グループの”応援投資”という考え方に共感したから");
        }
        if (maruiInvestorDetails.getWhyYouChooseThisBond_becauseInterestIsGood().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getWhyYouChooseThisBondText()).contains("利率がいいから");
        }
        if (maruiInvestorDetails.getWhyYouChooseThisBond_becauseEposPointsGiven().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getWhyYouChooseThisBondText()).contains("エポスポイントがもらえるから");
        }
        if (maruiInvestorDetails.getWhyYouChooseThisBond_becauseBonusPointsAdded().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getWhyYouChooseThisBondText()).contains("ボーナスポイント利用額に加算されるから");
        }
        if (maruiInvestorDetails.getWhyYouChooseThisBond_becauseReasonablePrice().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getWhyYouChooseThisBondText()).contains("一口当たりの金額が手頃だったから");
        }
        if (maruiInvestorDetails.getWhyYouChooseThisBond_becauseOtherReason().equalsIgnoreCase("Yes")) {
            softAsserts.assertThat(step06.getWhyYouChooseThisBondText()).contains("その他");
            if (!maruiInvestorDetails.getWhyYouChooseThisBond_becauseOtherReason_comment().isEmpty()) {
                softAsserts.assertThat(step06.getWhyYouChooseThisBondOtherReasonText()).isEqualTo(maruiInvestorDetails.getWhyYouChooseThisBond_becauseOtherReason_comment());
            }
        }
        softAsserts.assertAll();
    }

    protected <T> T retryFunctionWithRefreshPage(Supplier<T> func, boolean retry) {
        getBrowser().waitForPageStable();
        return RetryOnExceptions.retryFunction(func, ()-> {getBrowser().refreshPage(); return null;}, retry );
    }

    public MaruiInvestorDetails MaruiRegistrationGenerateInvestorData() {
        startTestLevel("generate investor data");
        MaruiInvestorDetails maruiInvestorDetails = new MaruiInvestorDetails();
        // construct unique email address using timestamp and set the email address
        String timestamp = DateTimeUtils.currentDateFormat("yyyyMMddhhmmss");
        maruiInvestorDetails.setEmail(maruiInvestorDetails.getEmail().replace("0001", timestamp));
        maruiInvestorDetails.generateRandomInvestorDetails();
        endTestLevel();
        return maruiInvestorDetails;
    }

    public MaruiRegistrationStep00_1SendEmail MaruiRegistrationStep0SendEmail(MaruiInvestorDetails maruiInvestorDetails, boolean retry) {
        startTestLevel("Step 00 Send Email");
        String maruiInvestorSiteBaseUrl = Users.getProperty(UsersProperty.marui_investorSiteBaseUrl);
        getBrowser().navigateTo(maruiInvestorSiteBaseUrl + "/registration");
        MaruiRegistrationStep00_1SendEmail sendEmailPage = retryFunctionWithRefreshPage(
                ()-> new MaruiRegistrationStep00_1SendEmail(getBrowser()), retry );
        sendEmailPage.sendEmail(maruiInvestorDetails.getEmail(), retry);
        endTestLevel();
        return sendEmailPage;
    }

    public MaruiRegistrationStep00_2CheckEmail MaruiRegistrationStep0CheckEmail(MaruiInvestorDetails maruiInvestorDetails, MaruiRegistrationStep00_1SendEmail sendEmailPage) {
        startTestLevel("Step 00 Check Email");
        MaruiRegistrationStep00_2CheckEmail checkEmailPage = new MaruiRegistrationStep00_2CheckEmail(getBrowser());
        assertThat(checkEmailPage.getCheckEmailText().contains(maruiInvestorDetails.getEmail())).isTrue();
        assertThat(checkEmailPage.resendEmailLinkIsEnabled()).isTrue();
        checkEmailPage.clickReturnButton();
        assertThat(sendEmailPage.sendEmailButtonIsVisible()).isTrue();
        endTestLevel();
        return checkEmailPage;
    }

    public MaruiRegistrationStep01AgreeToTerms MaruiRegistrationStep0SetupPassword(MaruiInvestorDetails maruiInvestorDetails, MaruiRegistrationStep00_2CheckEmail checkEmailPage, boolean retry) {
        startTestLevel("Step 00 Setup Password");
        String emailUser = maruiInvestorDetails.getEmail();
        String registrationLink = checkEmailPage.getRegistrationLink(emailUser);
        getBrowser().navigateTo(registrationLink);
        getBrowser().refreshPage();
        MaruiRegistrationStep00_3SetupPassword setupPasswordPage = retryFunctionWithRefreshPage(
                ()-> new MaruiRegistrationStep00_3SetupPassword(getBrowser()), retry );
        String password = Users.getProperty(UsersProperty.marui_investorPassword);
        MaruiRegistrationStep01AgreeToTerms step01 = setupPasswordPage.setupPassword(password, retry);
        endTestLevel();
        return step01;
    }

    public MaruiRegistrationStep02InputInvestorInformation MaruiRegistrationStep1AgreeToTerms(MaruiRegistrationStep01AgreeToTerms step01, boolean retry) {
        startTestLevel("Step 01 Agree to terms");
        MaruiRegistrationStep02InputInvestorInformation step02 = step01.agreeToTermsAndClickNextButton(retry);
        endTestLevel();
        return step02;
    }

    public MaruiRegistrationStep03EposNetLogin MaruiRegistrationStep2InputInvestorInformation(MaruiRegistrationStep02InputInvestorInformation step02, MaruiInvestorDetails maruiInvestorDetails, boolean retry) {
        startTestLevel("Step 02 Input Investor Information");
        assertThat(step02.backButtonIsEnabled()).isTrue();
        MaruiRegistrationStep03EposNetLogin step03 =
                step02.enterName(maruiInvestorDetails.getFamilyName(), maruiInvestorDetails.getGivenName())
                      .enterNameKana(maruiInvestorDetails.getFamilyNameKana(), maruiInvestorDetails.getGivenNameKana())
                      .clickGender(maruiInvestorDetails.getGender())
                      .setBirthday(maruiInvestorDetails.getBirthYear(), maruiInvestorDetails.getBirthMonth(), maruiInvestorDetails.getBirthDay())
                      .enterPostalCode(maruiInvestorDetails.getPostalCode())
                      .enterPrefectureName(maruiInvestorDetails.getPrefecture())
                      .enterCityName(maruiInvestorDetails.getCity())
                      .enterStreetName(maruiInvestorDetails.getStreet())
                      .enterBuildingName(maruiInvestorDetails.getBuilding())
                      .enterPhoneNumber(maruiInvestorDetails.getTelephone())
                      .clickNextStepButton(retry);
        endTestLevel();
        return step03;
    }

    public void MaruiRegistrationStep3UpdateBankInformation(MaruiRegistrationStep03EposNetLogin step03, MaruiInvestorDetails maruiInvestorDetails) {
        startTestLevel("Step 03 Epos Net Login (update bank information)");
        assertThat(step03.buttonsAreClickable()).isTrue();
        updateInvestorAdditionalPrivateInformation(maruiInvestorDetails.getEmail(),
                maruiInvestorDetails.getFamilyNameKana(),
                maruiInvestorDetails.getGivenNameKana());
        endTestLevel();
    }

    public MaruiRegistrationStep05Questionnaire MaruiRegistrationStep4InvestmentExperiences(MaruiInvestorDetails maruiInvestorDetails, boolean retry) {
        startTestLevel("Step 04 Investment Experiences");
        String maruiInvestorSiteBaseUrl = Users.getProperty(UsersProperty.marui_investorSiteBaseUrl);
        String maruiInvestorSiteCompatibility = maruiInvestorSiteBaseUrl + "/registration/compatibility";
        getBrowser().navigateTo(maruiInvestorSiteCompatibility);
        getBrowser().waitForPageStable();

        if (!getBrowser().getCurrentUrl().equalsIgnoreCase(maruiInvestorSiteCompatibility)) {
            getBrowser().navigateTo(maruiInvestorSiteCompatibility);
            getBrowser().waitForPageStable();
        }
        
        MaruiRegistrationStep04InvestmentExperiences step04 = retryFunctionWithRefreshPage(
                ()-> {
                        if (!getBrowser().getCurrentUrl().equalsIgnoreCase(maruiInvestorSiteCompatibility)) {
                            getBrowser().navigateTo(maruiInvestorSiteCompatibility);
                            getBrowser().waitForPageStable();
                        }
                        return new MaruiRegistrationStep04InvestmentExperiences(getBrowser());
                    },
                retry
        );
        assertThat(step04.backButtonIsEnabled()).isTrue();

        MaruiRegistrationStep05Questionnaire step05 =
                step04.selectIncomeSource(maruiInvestorDetails.getIncomeSource())
                      .selectJob(maruiInvestorDetails.getJob())
                      .selectAnnualIncome(maruiInvestorDetails.getAnnualIncome())
                      .selectAssetStatus(maruiInvestorDetails.getAssetStatus())
                      .selectFundNature(maruiInvestorDetails.getFundNature())
                      .selectInvestmentPurpose(maruiInvestorDetails.getInvestmentPurpose())
                      .selectInvestmentPolicy(maruiInvestorDetails.getInvestmentPolicy())
                      .selectExperienceStock(maruiInvestorDetails.getExperiencesStock())
                      .selectExperienceBond(maruiInvestorDetails.getExperiencesBond())
                      .selectExperienceTrust(maruiInvestorDetails.getExperiencesTrust())
                      .selectExperienceFuture(maruiInvestorDetails.getExperiencesFuture())
                      .selectExperienceForeignCurrencyDeposits(maruiInvestorDetails.getExperiencesForeignCurrencyDeposit())
                      .selectExperienceForexMarginTrading(maruiInvestorDetails.getExperiencesForexMarginTrading())
                      .selectExperienceMarginTrading(maruiInvestorDetails.getExperiencesMarginTrading())
                      .clickFinancialBusinessExperienceRadioButton(maruiInvestorDetails.getFinancialBusinessExperiences())
                      .selectPoliticallyExposedPerson(maruiInvestorDetails.getApplicabilityOfPEPs())
                      .clickNextButton(retry);
        endTestLevel();
        return step05;
    }

    public MaruiRegistrationStep06Confirm MaruiRegistrationStep5InputQuestionnaire(MaruiRegistrationStep05Questionnaire step05, MaruiInvestorDetails maruiInvestorDetails, boolean retry) {
        startTestLevel("Step 05 Input Questionnaire");
        assertThat(step05.backButtonIsEnabled()).isTrue();
        MaruiRegistrationStep06Confirm step06 =
                step05.howYouKnowThisBondByHomePage(maruiInvestorDetails.getHowYouKnowThisBond_byHomePage())
                      .howYouKnowThisBondByNewsRelease(maruiInvestorDetails.getHowYouKnowThisBond_byNewsRelease())
                      .howYouKnowThisBondByEposCardMail(maruiInvestorDetails.getHowYouKnowThisBond_byEposCardMail())
                      .howYouKnowThisBondByFriend(maruiInvestorDetails.getHowYouKnowThisBond_byFriend())
                      .howYouKnowThisBondBySns(maruiInvestorDetails.getHowYouKnowThisBond_bySns())
                      .howYouKnowThisBondByOtherWay(maruiInvestorDetails.getHowYouKnowThisBond_byOtherWay(), maruiInvestorDetails.getHowYouKnowThisBond_byOtherWay_comment())
                      .whyYouChooseThisBondBecauseAgreeWithIdeaOfMarui(maruiInvestorDetails.getWhyYouChooseThisBond_becauseAgreeWithConcept())
                      .whyYouChooseThisBondBecauseInterestIsGood(maruiInvestorDetails.getWhyYouChooseThisBond_becauseInterestIsGood())
                      .whyYouChooseThisBondBecauseEposPointsGiven(maruiInvestorDetails.getWhyYouChooseThisBond_becauseEposPointsGiven())
                      .whyYouChooseThisBondBecauseBonusPointsAdded(maruiInvestorDetails.getWhyYouChooseThisBond_becauseBonusPointsAdded())
                      .whyYouChooseThisBondBecauseReasonablePrice(maruiInvestorDetails.getWhyYouChooseThisBond_becauseReasonablePrice())
                      .whyYouChooseThisBondBecauseOtherReason(maruiInvestorDetails.getWhyYouChooseThisBond_becauseOtherReason(), maruiInvestorDetails.getWhyYouChooseThisBond_becauseOtherReason_comment())
                      .clickNextButton(retry);
        endTestLevel();
        return step06;
    }

    public void MaruiRegistrationStep6Confirm(MaruiRegistrationStep06Confirm step06, MaruiInvestorDetails maruiInvestorDetails, boolean retry) {
        startTestLevel("Step 06 Confirm");
        checkConfirmationPageExperienceValues(step06, maruiInvestorDetails);
        checkConfirmationPageQuestionnaireHowYouKnow(step06, maruiInvestorDetails);
        checkConfirmationPageQuestionnaireWhyYouChoose(step06, maruiInvestorDetails);
        step06.clickConfirmButton(retry);
        endTestLevel();
    }

    public void MaruiRegistration(MaruiInvestorDetails maruiInvestorDetails, boolean retry) {
        MaruiRegistrationStep00_1SendEmail sendEmailPage = MaruiRegistrationStep0SendEmail(maruiInvestorDetails, retry);
        MaruiRegistrationStep00_2CheckEmail checkEmailPage = MaruiRegistrationStep0CheckEmail(maruiInvestorDetails, sendEmailPage);
        MaruiRegistrationStep01AgreeToTerms step01 = MaruiRegistrationStep0SetupPassword(maruiInvestorDetails, checkEmailPage, retry);
        MaruiRegistrationStep02InputInvestorInformation step02 = MaruiRegistrationStep1AgreeToTerms(step01, retry);
        MaruiRegistrationStep03EposNetLogin step03 = MaruiRegistrationStep2InputInvestorInformation(step02, maruiInvestorDetails, retry);
        MaruiRegistrationStep3UpdateBankInformation(step03, maruiInvestorDetails);
        MaruiRegistrationStep05Questionnaire step05 = MaruiRegistrationStep4InvestmentExperiences(maruiInvestorDetails, retry);
        MaruiRegistrationStep06Confirm step06 = MaruiRegistrationStep5InputQuestionnaire(step05, maruiInvestorDetails, retry);
        MaruiRegistrationStep6Confirm(step06, maruiInvestorDetails, retry);
    }
}
