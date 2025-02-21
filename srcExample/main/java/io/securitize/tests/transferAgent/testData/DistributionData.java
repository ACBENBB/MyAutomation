package io.securitize.tests.transferAgent.testData;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.transferAgent.abstractClass.AbstractDistributions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DistributionData {

    public String distributionTestType;
    public String distributionId;
    public String type;
    public String distributionName;
    public String date;
    public Double totalAmount;
    public double fundingNeeds;
    public String amountPerToken;
    public String snapshotDate;
    public String issuerCashAccountSecId;
    public String issuerId;
    public String tokenId;
    public String redemptionWallet;
    public String initialPaymentAmount;
    public long amount;
    public String tokens;
    public String giveBackTokens;
    public String payment;
    public String tokenPrice;
    public String tokenContract;
    public String sellBackPrice;
    public String walletPk;
    public String walletAddress;
    public double totalTokensToBeIssued;
    public double initialTotalNumberOfIssuedSecurities;
    public String redemptionLimit;
    public String redemptionPrice;
    public double issuerInitialAccountBalance;
    public double issuerFinalAccountBalance;
    public String[] endDate;
    public String investorEmail;

    public DistributionData() {
    }

    public DistributionData(String distributionType) {
        if (distributionType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND.toString())) {
            this.distributionTestType = distributionType;
            this.issuerCashAccountSecId = Users.getProperty(UsersProperty.ta_distribution_issuer_CashAccount_Sid_aut365);
            this.type = "Dividend";
            this.distributionName = "AUT365_TA_Dividend " + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            this.date = null;
            this.totalAmount = 0.00;
            this.amountPerToken = "1";
            this.snapshotDate = null;
            this.issuerInitialAccountBalance = 0;
        } else if (distributionType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_REDEMPTION.toString())) {
            this.distributionTestType = distributionType;
            this.type = "Redemption";
            this.distributionName = "AUT476_TA_Redemption " + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            this.redemptionLimit = "25";
            this.redemptionPrice = "0.51234567";
            this.endDate = null;
            this.issuerId = Users.getProperty(UsersProperty.ta_distribution_redemption_issuerId_aut476);
            this.tokenId = Users.getProperty(UsersProperty.ta_distribution_redemption_tokenId_aut476);
            this.redemptionWallet = Users.getProperty(UsersProperty.ta_distribution_redemption_wallet_aut476);
            this.initialPaymentAmount = Users.getProperty(UsersProperty.ta_distribution_redemption_initial_payment_aut476);
            this.amount = Long.parseLong(Users.getProperty(UsersProperty.ta_distribution_redemption_amount_aut476));
            this.tokens = Users.getProperty(UsersProperty.ta_distribution_redemption_tokens_to_redeem_aut476);
            this.giveBackTokens = Users.getProperty(UsersProperty.ta_distribution_redemption_give_back_tokens_aut476);
            this.payment = Users.getProperty(UsersProperty.ta_distribution_redemption_payment_aut476);
            this.tokenPrice = Users.getProperty(UsersProperty.ta_distribution_redemption_token_price_aut476);
            this.tokenContract = Users.getProperty(UsersProperty.ta_distribution_redemption_token_contract_aut476);
            this.sellBackPrice = Users.getProperty(UsersProperty.ta_distribution_redemption_sell_back_price_aut476);
        } else if (distributionType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_ACCEPT.toString())) {
            this.distributionTestType = distributionType;
            this.type = "Dividend";
            this.distributionName = "AUT491_TA_Dividend " + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            this.date = null;
            this.totalAmount = 0.00;
            this.amountPerToken = "0.005";
            this.snapshotDate = null;
            this.issuerInitialAccountBalance = 0;
        } else if (distributionType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_REJECT.toString())) {
            this.distributionTestType = distributionType;
            this.type = "Dividend";
            this.distributionName = "AUT492_TA_Dividend " + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            this.date = null;
            this.totalAmount = 0.00;
            this.amountPerToken = "0.005";
            this.snapshotDate = null;
            this.issuerInitialAccountBalance = 0;
        } else if (distributionType.equalsIgnoreCase(AbstractDistributions.DistributionType.DISTRIBUTION_TYPE_DIVIDEND_DRIP_HAPPY_FLOW.toString())) {
            this.issuerId = Users.getProperty(UsersProperty.ta_issuer_id_aut626);
            this.tokenId = Users.getProperty(UsersProperty.ta_token_id_aut626);
            this.walletAddress = Users.getProperty(UsersProperty.ta_walletAddress_aut626);
            this.walletPk = Users.getProperty(UsersProperty.ta_privateKey_aut626);
            this.distributionTestType = distributionType;
            this.type = "Dividend";
            this.distributionName = "AUT626_TA_AutoIssuance " + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            this.date = null;
            this.totalAmount = 0.00;
            this.amountPerToken = "0.005";
//            this.amountPerToken = "1";
            this.snapshotDate = null;
            this.issuerInitialAccountBalance = 0;
        }

    }

}