package io.securitize.tests.transferAgent.testData;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.transferAgent.abstractClass.AbstractDistributions;
import io.securitize.tests.transferAgent.abstractClass.AbstractTaxForm;

public class AUT492 {

    private String investorType;
    private String testInvestorMail;
    private String testInvestorPass;
    private String issuerId;
    private String tokenId;
    private String distributionType;
    private DistributionData distributionData;
    private double initialCashAccountBalance;
    private double actualCashAccountBalance;
    private double expectedCashAccountBalance;
    private double initialInvestorTokens;
    private double paymentToBePerformed;

    public AUT492() {

        this.investorType = AbstractTaxForm.TaxFormAutoCompleteInvestorType.ENTITY_NONUS_REJECT.toString();
        this.testInvestorMail = Users.getProperty(UsersProperty.ta_taxforms_ent_nonus_reject_Email_aut492);
        this.testInvestorPass = Users.getProperty(UsersProperty.taInvestorGenericPass);
        this.issuerId = Users.getProperty(UsersProperty.ta_distribution_issuerId_aut365);
        this.tokenId = Users.getProperty(UsersProperty.ta_distribution_redemption_tokenId_aut491);

        this.distributionType = AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_REJECT.toString();
        this.distributionData = new DistributionData(distributionType);
        this.distributionData.issuerId = issuerId;
        this.distributionData.tokenId = tokenId;

    }

    public String getInvestorType() {
        return investorType;
    }

    public String getTestInvestorMail() {
        return testInvestorMail;
    }

    public String getTestInvestorPass() {
        return testInvestorPass;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getDistributionType() {
        return distributionType;
    }

    public DistributionData getDistributionData() {
        return distributionData;
    }

    public double getInitialCashAccountBalance() {
        return initialCashAccountBalance;
    }

    public double getActualCashAccountBalance() {
        return actualCashAccountBalance;
    }

    public double getExpectedCashAccountBalance() {
        return expectedCashAccountBalance;
    }

    public double getInitialInvestorTokens() {
        return initialInvestorTokens;
    }

    public double getPaymentToBePerformed() {
        return paymentToBePerformed;
    }

    public void setInitialCashAccountBalance(double initialCashAccountBalance) {
        this.initialCashAccountBalance = initialCashAccountBalance;
    }

    public void setActualCashAccountBalance(double actualCashAccountBalance) {
        this.actualCashAccountBalance = actualCashAccountBalance;
    }

    public void setExpectedCashAccountBalance(double expectedCashAccountBalance) {
        this.expectedCashAccountBalance = expectedCashAccountBalance;
    }

    public void setInitialInvestorTokens(double initialInvestorTokens) {
        this.initialInvestorTokens = initialInvestorTokens;
    }

    public void setPaymentToBePerformed(double paymentToBePerformed) {
        this.paymentToBePerformed = paymentToBePerformed;
    }

}