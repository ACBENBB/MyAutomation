package io.securitize.tests.transferAgent.abstractClass;

import io.securitize.infra.utils.*;

import java.util.*;
public class DistributionEmailParser {

    private static final String DISTRIBUTION_EMAIL_ISSUER_NAME_TITLE = "(You just received a payout from\\s+-*\\s+-*\\s+)(.*)(!)";
    private static final String DISTRIBUTION_EMAIL_PAYOUT_AMOUNT = "(payout of\\s)(.*)(,)";
    private static final String DISTRIBUTION_EMAIL_ISSUED_DATE = "(issued on\\s)(.*)(.)";
    private static final String REDEMPTION_EMAIL_ISSUER_NAME_TITLE = "(You can now sell back your AUT to\\s)(.*)(!)";
    private static final String REDEMPTION_START_DATE = "(between\\s)(.*)( and)";
    private static final String REDEMPTION_END_DATE = "(and\\s)(.*)(\\s)";
    private static final String REDEMPTION_SELL_BACK_PRICE = "(tokens is \\$)(.*)( per token)";
    private static final String DISTRIBUTION_EMAIL_BUTTON_LINK = "(https\\://)(.*)(\\s)";

    public DistributionEmailParser() {}

    public HashMap<String, String> parseTestDataFromDividendEmail(String investorEmail) {
        HashMap<String, String> dataFromEmail = new HashMap<>();
        String emailContent = getEmailContent(investorEmail);
        dataFromEmail.put("issuerName", getIssuerName(emailContent));
        dataFromEmail.put("payoutAmount", getPayoutAmount(emailContent));
        dataFromEmail.put("issuedDate", getIssuedDate(emailContent));
        return dataFromEmail;
    }

    public HashMap<String, String> parseTestDataFromRedemptionEmail(String investorEmail) {
        HashMap<String, String> dataFromEmail = new HashMap<>();
        String emailContent = getEmailContent(investorEmail);
        System.out.println(emailContent);
        dataFromEmail.put("issuerName", getRedemptionIssuerName(emailContent));
        dataFromEmail.put("redemptionStart", getRedemptionStart(emailContent));
        dataFromEmail.put("redemptionEnd", getRedemptionEnd(emailContent));
        dataFromEmail.put("sellBackPrice", getSellBackPrice(emailContent));
        dataFromEmail.put("link", getButtonLink(emailContent));
        return dataFromEmail;
    }

    public String getEmailContent(String investorEmail) {
        return EmailWrapper.getEmailContentByRecipientAddressAndRegex(investorEmail, null);
    }

    public String getIssuerName(String emailContent) {
        return RegexWrapper.getRegexByGroup(emailContent, DISTRIBUTION_EMAIL_ISSUER_NAME_TITLE, 2);
    }

    public static String getPayoutAmount(String emailContent) {
        return RegexWrapper.getRegexByGroup(emailContent, DISTRIBUTION_EMAIL_PAYOUT_AMOUNT, 2).split(" ")[0];
    }

    public String getIssuedDate(String emailContent) {
        return RegexWrapper.getRegexByGroup(emailContent, DISTRIBUTION_EMAIL_ISSUED_DATE, 2);
    }

    public String getButtonLink(String emailContent) {
        return RegexWrapper.getRegexByGroup(emailContent, DISTRIBUTION_EMAIL_BUTTON_LINK, 2);
    }

    public String getSellBackPrice(String emailContent) {
        return RegexWrapper.getRegexByGroup(emailContent, REDEMPTION_SELL_BACK_PRICE, 2);
    }

    public String getRedemptionStart(String emailContent) {
        return RegexWrapper.getRegexByGroup(emailContent, REDEMPTION_START_DATE, 2);
    }

    public String getRedemptionEnd(String emailContent) {
        return RegexWrapper.getRegexByGroup(emailContent, REDEMPTION_END_DATE, 2);
    }

    public String getRedemptionIssuerName(String emailContent) {
        return RegexWrapper.getRegexByGroup(emailContent, REDEMPTION_EMAIL_ISSUER_NAME_TITLE, 2);
    }
}
