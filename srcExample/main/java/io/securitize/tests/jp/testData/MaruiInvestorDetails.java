package io.securitize.tests.jp.testData;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Random;

@EqualsAndHashCode(callSuper = true)
@Data
public class MaruiInvestorDetails extends InvestorDetailsCommon {
    private String pledgedAmount;
    private String confirmedPledgedAmount;
    private String incomeSource;
    private String job;
    private String annualIncome;
    private String assetStatus;
    private String fundNature;
    private String investmentPurpose;
    private String investmentPolicy;
    private String experiencesStock;
    private String experiencesBond;
    private String experiencesTrust;
    private String experiencesFuture;
    private String experiencesForeignCurrencyDeposit;
    private String experiencesForexMarginTrading;
    private String experiencesMarginTrading;
    private String financialBusinessExperiences;
    private String applicabilityOfPEPs;
    // questionnaire - how-you-know-this-bond checkbox. "Yes" to click the checkbox.
    private String howYouKnowThisBond_byHomePage;
    private String howYouKnowThisBond_byNewsRelease;
    private String howYouKnowThisBond_byEposCardMail;
    private String howYouKnowThisBond_byFriend;
    private String howYouKnowThisBond_bySns;
    private String howYouKnowThisBond_byOtherWay;
    private String howYouKnowThisBond_byOtherWay_comment;
    // questionnaire - why-you-choose-this-bond checkbox. "Yes" to click the checkbox.
    private String whyYouChooseThisBond_becauseAgreeWithConcept;
    private String whyYouChooseThisBond_becauseInterestIsGood;
    private String whyYouChooseThisBond_becauseEposPointsGiven;
    private String whyYouChooseThisBond_becauseBonusPointsAdded;
    private String whyYouChooseThisBond_becauseReasonablePrice;
    private String whyYouChooseThisBond_becauseOtherReason;
    private String whyYouChooseThisBond_becauseOtherReason_comment;

    public MaruiInvestorDetails() {
        this.birthYear = "1971";
        this.birthMonth = "1";
        this.birthDay = "15";
        this.gender = "女性";
        this.email = Users.getProperty(UsersProperty.marui_newInvestorEmail);
        this.financialBusinessExperiences = "無し";
        this.applicabilityOfPEPs = "該当しません";
        // questionnaire - how-you-know-this-bond checkbox. "Yes" to click the checkbox.
        this.howYouKnowThisBond_byHomePage = "Yes";                 // 01-1
        this.howYouKnowThisBond_byNewsRelease = "Yes";              // 01-2
        this.howYouKnowThisBond_byEposCardMail = "Yes";             // 01-3
        this.howYouKnowThisBond_byFriend = "Yes";                   // 01-4
        this.howYouKnowThisBond_bySns = "Yes";                      // 01-5
        this.howYouKnowThisBond_byOtherWay = "Yes";                 // 01-6
        this.howYouKnowThisBond_byOtherWay_comment = "コメント";
        // questionnaire - why-you-choose-this-bond checkbox. "Yes" to click the checkbox.
        this.whyYouChooseThisBond_becauseAgreeWithConcept = "Yes";  // 03-1
        this.whyYouChooseThisBond_becauseInterestIsGood = "Yes";    // 03-2
        this.whyYouChooseThisBond_becauseEposPointsGiven = "Yes";   // 03-3
        this.whyYouChooseThisBond_becauseBonusPointsAdded = "Yes";  // 03-4
        this.whyYouChooseThisBond_becauseReasonablePrice = "Yes";   // 03-5
        this.whyYouChooseThisBond_becauseOtherReason = "Yes";       // 03-6
        this.whyYouChooseThisBond_becauseOtherReason_comment = "コメントコメント";
    }

    public String getIncomeSourceRandomly(Random random) {
        String[] incomeSourceList = {
                "事業収入",
                "不動産収入",
                "給与収入",
                "利子・配当収入",
                "年金",
                "世帯主の収入",
                "その他",
                "無し"
        };
        return getRandomValueFromArray(incomeSourceList, random);
    }

    public String getJobRandomly(Random random) {
        String[] jobList = {
                "経営者・役員",
                "会社員（正社員）",
                "会社員（契約社員）",
                "会社員（派遣社員）",
                "パート・アルバイト",
                "公務員",
                "自営業",
                "自由業",
                "専業主婦・主夫",
                "大学生・大学院生",
                "専門学校生・短大生",
                "高校生",
                "医師",
                "士業（公認会計士・弁護士・税理士・司法書士）",
                "NGO・NPO法人職員",
                "家事手伝い",
                "無職",
                "定年退職"
        };
        return getRandomValueFromArray(jobList, random);
    }

    public String getAnnualIncomeRandomly(Random random) {
        String[] annualIncomeList = {
                "300万円未満",
                "300～500万円",
                "500～1,000万円",
                "1,000～2,000万円",
                "2,000～3,000万円",
                "3,000万円以上"
        };
        return getRandomValueFromArray(annualIncomeList, random);
    }

    public String getAssetStatusRandomly(Random random) {
        String[] assetStatusList = {
                "300万円未満",
                "300～1,000万円",
                "1,000～3,000万円",
                "3,000万円～5,000万円",
                "5,000～万円～1億円", // typo
                "1億円以上"
        };
        return getRandomValueFromArray(assetStatusList, random);
    }

    public String getFundNatureRandomly(Random random) {
        String[] fundNatureList = {
                "余裕資金",
                "準備資金",
                "生活資金"
        };
        return getRandomValueFromArray(fundNatureList, random);
    }

    public String getInvestmentPurposeRandomly(Random random) {
        String[] investmentPurposeList = {
                "長期的な資産運用として",
                "短期的な値動きによる利益獲得",
                "その他"
        };
        return getRandomValueFromArray(investmentPurposeList, random);
    }

    public String getInvestmentPolicyRandomly(Random random) {
        String[] investmentPolicyList = {
                "元本の安全性重視",
                "分配金による安定的な収入重視",
                "分配金や利金による収入と共に、値上がり益を追求",
                "利回り・値上がり益を追求",
                "高い利回り・大幅な値上がり益を追求",
        };
        return getRandomValueFromArray(investmentPolicyList, random);
    }

    public String getExperienceYearsRandomly(Random random) {
        String[] experienceYearsList = {
                "なし",
                "1年未満",
                "3年未満",
                "5年未満",
                "5年以上"
        };
        return getRandomValueFromArray(experienceYearsList, random);
    }

    public void generateRandomInvestorDetails() {
        setNamesRandomly();

        String addressLine = getAddressRandomly(random);
        String[] address = addressLine.split(",");
        setPostalCode(address[0]);
        setTelephone(address[1]);
        setPrefecture(address[2]);
        setCity(address[3]);
        setStreet(address[4]);
        setBuilding(address[5]);

        setIncomeSource(getIncomeSourceRandomly(random));
        setJob(getJobRandomly(random));
        setAnnualIncome(getAnnualIncomeRandomly(random));
        setAssetStatus(getAssetStatusRandomly(random));
        setFundNature(getFundNatureRandomly(random));
        setInvestmentPurpose(getInvestmentPurposeRandomly(random));
        setInvestmentPolicy(getInvestmentPolicyRandomly(random));

        setExperiencesStock(getExperienceYearsRandomly(random));
        setExperiencesBond(getExperienceYearsRandomly(random));
        setExperiencesTrust(getExperienceYearsRandomly(random));
        setExperiencesFuture(getExperienceYearsRandomly(random));
        setExperiencesForeignCurrencyDeposit(getExperienceYearsRandomly(random));
        setExperiencesForexMarginTrading(getExperienceYearsRandomly(random));
        setExperiencesMarginTrading(getExperienceYearsRandomly(random));
    }
}
