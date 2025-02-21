package io.securitize.tests.fundraise.pojo;

import io.securitize.tests.fundraise.abstractclass.*;

public class FT_TestData {

    public String investorKycStatus;
    public String investorSid;
    public String onboardingAccreditationStatus;
    public String onboardingQualificationStatus;
    public String marketSuitabilityStatus;
    public String marketUSAccountStatus;
    public String marketAccreditationStatus;
    public String issuerId;
    public String tokenId;
    public String cpInvestorId;
    public String roundId;
    public String investmentName;
    public String investmentCurrency;
    public String investmentAmount;
    public boolean hasCashAccount;
    public String cashAccountId;

    public FT_TestData(AbstractFT.FT_TestScenario ft_testScenario) {
        if(ft_testScenario.equals(AbstractFT.FT_TestScenario.FT_INVEST_WIRETRANSFER)) {
            this.investorKycStatus = "verified";
            this.onboardingAccreditationStatus = "confirmed";
            this.onboardingQualificationStatus = "confirmed";
            this.marketSuitabilityStatus = "verified";
            this.marketUSAccountStatus = "approved";
            this.marketAccreditationStatus = "verified";
            this.investmentName = "AUT516";
            this.investmentCurrency = "USD";
            this.investmentAmount = "1000";
        } else if(ft_testScenario.equals(AbstractFT.FT_TestScenario.FT_INVEST_CASHACCOUNT)) {
            this.investorKycStatus = "verified";
            this.onboardingAccreditationStatus = "confirmed";
            this.onboardingQualificationStatus = "confirmed";
            this.marketSuitabilityStatus = "verified";
            this.marketUSAccountStatus = "approved";
            this.marketAccreditationStatus = "verified";
            this.investmentName = "AUT524";
            this.investmentCurrency = "USD";
            this.investmentAmount = "1000";
            this.hasCashAccount = true;
        } else if(ft_testScenario.equals(AbstractFT.FT_TestScenario.FT_INVEST_WEB3)) {
            // TODO IMPLEMENT
        }else if(ft_testScenario.equals(AbstractFT.FT_TestScenario.FT_ACCREDITATION_FIRST)) {
            this.investorKycStatus = "verified";
            this.onboardingAccreditationStatus = "confirmed";
            this.onboardingQualificationStatus = "confirmed";
            this.marketSuitabilityStatus = "verified";
            this.marketUSAccountStatus = "approved";
            this.marketAccreditationStatus = "none";
            this.investmentName = "AUT539";
            this.investmentCurrency = "USD";
            this.investmentAmount = "1000";
            this.hasCashAccount = true;
        }else if(ft_testScenario.equals(AbstractFT.FT_TestScenario.FT_INVEST_DOCUSIGN)) {
            this.investorKycStatus = "verified";
            this.onboardingAccreditationStatus = "confirmed";
            this.onboardingQualificationStatus = "confirmed";
            this.marketSuitabilityStatus = "verified";
            this.marketUSAccountStatus = "approved";
            this.marketAccreditationStatus = "verified";
            this.investmentName = "AUT548";
            this.investmentCurrency = "USD";
            this.investmentAmount = "1000";
        }else if(ft_testScenario.equals(AbstractFT.FT_TestScenario.FT_INVEST_VERIFYDOC)) {
            this.investorKycStatus = "verified";
            this.onboardingAccreditationStatus = "confirmed";
            this.onboardingQualificationStatus = "confirmed";
            this.marketSuitabilityStatus = "verified";
            this.marketUSAccountStatus = "approved";
            this.marketAccreditationStatus = "verified";
            this.investmentName = "AUT670";
            this.investmentCurrency = "USD";
            this.investmentAmount = "1000";
        } else if(ft_testScenario.equals(AbstractFT.FT_TestScenario.FT_NIE_KYC_STATUS)) {
            this.investorKycStatus = "verified";
            this.onboardingAccreditationStatus = "confirmed";
            this.onboardingQualificationStatus = "confirmed";
            this.marketSuitabilityStatus = "verified";
            this.marketUSAccountStatus = "approved";
            this.marketAccreditationStatus = "none";
            this.investmentName = "AUT672";
            this.investmentCurrency = "USD";
            this.investmentAmount = "6000000";
        }
    }

}