package io.securitize.tests.jp.testData;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        "index",                                              // 01
        "lastName",                                           // 02
        "givenName",                                          // 03
        "lastNameKana",                                       // 04
        "givenNameKana",                                      // 05
        "nationality",                                        // 06
        "birthYear",                                          // 07
        "birthMonth",                                         // 08
        "birthDay",                                           // 09
        "gender",                                             // 10
        "telephone",                                          // 11
        "postalCode",                                         // 12
        "prefecture",                                         // 13
        "city",                                               // 14
        "street",                                             // 15
        "building",                                           // 16
        "email",                                              // 17 +string is +index
        "pledgedAmount",                                      // 18
        "confirmedPledgedAmount",                             // 19
        "bankCode",                                           // 20
        "branchCode",                                         // 21
        "accountNumber",                                      // 22
        "accountType",                                        // 23
        "accountName",                                        // 24
        "eposnetCustomerId",                                  // 25
        "bankName",                                           // 26
        "branchName",                                         // 27
        "investmentExperiencesStock",                         // 28
        "investmentExperiencesPublicBond",                    // 29
        "investmentExperiencesInvestmentTrust",               // 30
        "investmentExperiencesFutures",                       // 31
        "investmentExperiencesForeignCurrencyDeposits",       // 32
        "investmentExperiencesForexMarginTrading",            // 33
        "investmentExperiencesMarginTrading",                 // 34
        "financialWorkExperience",                            // 35
        "foreignPoliticallyExposedPerson",                    // 36
        "incomeSource",                                       // 37
        "job",                                                // 38
        "annualIncome",                                       // 39
        "assetAmount",                                        // 40
        "assetType",                                          // 41
        "investmentPurpose",                                  // 42
        "investmentPolicy",                                   // 43
        "kycStatus",                                          // 44
        "identificationStatus",                               // 45
        "howYouKnowThisProductByHomePage",                    // 46
        "howYouKnowThisProductByNewsRelease",                 // 47
        "howYouKnowThisProductByEposCardMail",                // 48
        "howYouKnowThisProductByFriend",                      // 49
        "howYouKnowThisProductBySns",                         // 50
        "howYouKnowThisProductByOtherWay",                    // 51
        "howYouKnowThisProductByOtherWayComment",             // 52
        "whyYouChooseThisProductBecauseAgreeWithConcept",     // 53
        "whyYouChooseThisProductBecauseInterestIsGood",       // 54
        "whyYouChooseThisProductBecauseEposPointsGiven",      // 55
        "whyYouChooseThisProductBecauseBonusPointsAdded",     // 56
        "whyYouChooseThisProductBecauseReasonablePrice",      // 57
        "whyYouChooseThisProductBecauseOtherReason",          // 58
        "whyYouChooseThisProductBecauseOtherReasonComment",   // 59
        "reserved01",                                         // 60
        "reserved02",                                         // 61
        "reserved03",                                         // 62
        "reserved04",                                         // 63
        "reserved05",                                         // 64
        "reserved06",                                         // 65
        "reserved07",                                         // 66
        "reserved08",                                         // 67
        "reserved09",                                         // 68
        "reserved10",                                         // 69
        "reserved11",                                         // 70
        "reserved12",                                         // 71
        "reserved13",                                         // 72
        "reserved14",                                         // 73
        "reserved15",                                         // 74
        "reserved16",                                         // 75
        "reserved17",                                         // 76
        "reserved18",                                         // 77
        "reserved19",                                         // 78
        "reserved20",                                         // 79
})
public class JpInvestorDetails {
    private String index;                                              // 01
    private String lastName;                                           // 02
    private String givenName;                                          // 03
    private String lastNameKana;                                       // 04
    private String givenNameKana;                                      // 05
    private String nationality;                                        // 06
    private String birthYear;                                          // 07
    private String birthMonth;                                         // 08
    private String birthDay;                                           // 09
    private String gender;                                             // 10
    private String telephone;                                          // 11
    private String postalCode;                                         // 12
    private String prefecture;                                         // 13
    private String city;                                               // 14
    private String street;                                             // 15
    private String building;                                           // 16
    private String email;                                              // 17 +string is +index
    private String pledgedAmount;                                      // 18
    private String confirmedPledgedAmount;                             // 19
    private String bankCode;                                           // 20
    private String branchCode;                                         // 21
    private String accountNumber;                                      // 22
    private String accountType;                                        // 23
    private String accountName;                                        // 24
    private String eposnetCustomerId;                                  // 25
    private String bankName;                                           // 26
    private String branchName;                                         // 27
    private String investmentExperiencesStock;                         // 28
    private String investmentExperiencesPublicBond;                    // 29
    private String investmentExperiencesInvestmentTrust;               // 30
    private String investmentExperiencesFutures;                       // 31
    private String investmentExperiencesForeignCurrencyDeposits;       // 32
    private String investmentExperiencesForexMarginTrading;            // 33
    private String investmentExperiencesMarginTrading;                 // 34
    private String financialWorkExperience;                            // 35
    private String foreignPoliticallyExposedPerson;                    // 36
    private String incomeSource;                                       // 37
    private String job;                                                // 38
    private String annualIncome;                                       // 39
    private String assetAmount;                                        // 40
    private String assetType;                                          // 41
    private String investmentPurpose;                                  // 42
    private String investmentPolicy;                                   // 43
    private String kycStatus;                                          // 44
    private String identificationStatus;                               // 45
    private String howYouKnowThisProductByHomePage;                    // 46
    private String howYouKnowThisProductByNewsRelease;                 // 47
    private String howYouKnowThisProductByEposCardMail;                // 48
    private String howYouKnowThisProductByFriend;                      // 49
    private String howYouKnowThisProductBySns;                         // 50
    private String howYouKnowThisProductByOtherWay;                    // 51
    private String howYouKnowThisProductByOtherWayComment;             // 52
    private String whyYouChooseThisProductBecauseAgreeWithConcept;     // 53
    private String whyYouChooseThisProductBecauseInterestIsGood;       // 54
    private String whyYouChooseThisProductBecauseEposPointsGiven;      // 55
    private String whyYouChooseThisProductBecauseBonusPointsAdded;     // 56
    private String whyYouChooseThisProductBecauseReasonablePrice;      // 57
    private String whyYouChooseThisProductBecauseOtherReason;          // 58
    private String whyYouChooseThisProductBecauseOtherReasonComment;   // 59
    private String reserved01;                                         // 60
    private String reserved02;                                         // 61
    private String reserved03;                                         // 62
    private String reserved04;                                         // 63
    private String reserved05;                                         // 64
    private String reserved06;                                         // 65
    private String reserved07;                                         // 66
    private String reserved08;                                         // 67
    private String reserved09;                                         // 68
    private String reserved10;                                         // 69
    private String reserved11;                                         // 70
    private String reserved12;                                         // 71
    private String reserved13;                                         // 72
    private String reserved14;                                         // 73
    private String reserved15;                                         // 74
    private String reserved16;                                         // 75
    private String reserved17;                                         // 76
    private String reserved18;                                         // 77
    private String reserved19;                                         // 78
    private String reserved20;                                         // 79
}
