package io.securitize.pageObjects.controlPanel.cpIssuers.distributions;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.ExtendedBy;
import io.securitize.pageObjects.AbstractPage;

public class DividendEmailPage extends AbstractPage {

    public String emailIssuerName;
    public String dividendAmount;
    public String dividendDate;

    public static final String DIVIDEND_MAIL_BODY = "(?<=You just received a dividend from)(.*?)(?=\\!)";
    public static final String DIVIDEND_RECEIVED_ISSUER_NAME = "(?<=You just received a dividend from)(.*?)(?=\\!)";
    public static final String DIVIDEND_TAXFORM_MISSING = "";
    public static final String DIVIDEND_KYC_MISSING = "";
    public static final String DIVIDEND_CASH_ACCOUNT_MISSING = "";


    public DividendEmailPage(Browser browser, ExtendedBy... elements) {
        super(browser, elements);
    }

    public String parseEmailDividend(String recipientAddress, String emailType) {
        String emailBody = null;
        if (emailType.equalsIgnoreCase("TaxForm_Missing")) {
            emailBody = extractLinkFromEmail(recipientAddress, DIVIDEND_TAXFORM_MISSING);
        } else if (emailType.equalsIgnoreCase("KYC_Missing")) {
            emailBody = extractLinkFromEmail(recipientAddress, DIVIDEND_KYC_MISSING);
        } else if (emailType.equalsIgnoreCase("CashAccount_Missing")) {
            emailBody = extractLinkFromEmail(recipientAddress, DIVIDEND_CASH_ACCOUNT_MISSING);
        }
        return emailBody;
    }

}