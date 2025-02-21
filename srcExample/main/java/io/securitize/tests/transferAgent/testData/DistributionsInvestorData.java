package io.securitize.tests.transferAgent.testData;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.transferAgent.abstractClass.AbstractDistributions;

public class DistributionsInvestorData {

    public String fullName;
    public String secId;
    public boolean usInvestor;
    public boolean individualInvestor;
    public String testInvestorType;
    String cPiD;
    String referenceId;
    public String currency;
    String coverFees;
    String taxable;
    public String payoutMethod;
    String usTaxActivity;
    public String totalNumberOfSecurities;
    public String countryResidence;
    String countryTaxes;
    String walletAddress;
    public boolean csvTaxFormW9;
    public boolean csvTaxFormW8BEN;
    public boolean csvTaxFormW8BENE;
    public String taxFormType;
    public String taxWithholdingRate;
    public String taxWithholdingAmount;
    public String payoutAmount;
    public String payoutRate;
    public String email;
    public String password;
    public String investorPK;
    public boolean cashAccount;
    public double initialInvestorCashAccountBalance;
    public double finalCashAccountBalance;
    public String initialKYCStatus;
    public boolean initialTaxFormSubmitted;
    String taxCountry;
    public String distributionEmail;

    public DistributionsInvestorData() {
    }

    public DistributionsInvestorData(String investorType) {
        if (investorType.equalsIgnoreCase(AbstractDistributions.DistributionsInvestorType.DISTRIBUTION_AUT365_TESTDATA.toString())) {
            this.testInvestorType = investorType;
            this.secId = Users.getProperty(UsersProperty.ta_distribution_testUser_one_sid_aut365);
            this.initialKYCStatus = "verified";
            this.cashAccount = true;
            this.initialTaxFormSubmitted = false;
            this.taxFormType = "w9";
            this.initialInvestorCashAccountBalance = 0;
            this.finalCashAccountBalance = 0;
            this.distributionEmail = "TaxForm_Missing"; // this sould be an enum to decide which email to parse.
        } else if (investorType.equalsIgnoreCase(AbstractDistributions.DistributionsInvestorType.DISTRIBUTION_AUT476_TESTDATA.toString())) {
            this.testInvestorType = investorType;
            this.email = Users.getProperty(UsersProperty.ta_distribution_redemption_investor_aut476);
            this.password = Users.getProperty(UsersProperty.ta_distributionGenericInvestorPassword_aut365);
            this.investorPK = Users.getProperty(UsersProperty.ta_distribution_redemption_investor_pk_aut476);
        } else if (investorType.equalsIgnoreCase(AbstractDistributions.DistributionsInvestorType.DISTRIBUTION_AUT543_TESTDATA.toString())) {
            this.testInvestorType = investorType;
            this.email = Users.getProperty(UsersProperty.ta_distribution_dividend_investorMail_aut543);
            this.password = Users.getProperty(UsersProperty.ta_distributionGenericInvestorPassword_aut365);
        }

    }
}
