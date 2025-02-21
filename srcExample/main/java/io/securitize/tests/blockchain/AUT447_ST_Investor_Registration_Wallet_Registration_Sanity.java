package io.securitize.tests.blockchain;

import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.webdriver.ForceBrowserHubUrl;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static io.securitize.infra.reporting.MultiReporter.endTestLevel;
import static io.securitize.infra.reporting.MultiReporter.startTestLevel;

public class AUT447_ST_Investor_Registration_Wallet_Registration_Sanity extends AbstractCpInvestorRegistrationFlow {

    @BypassRecaptcha(environments = {"production", "apac"})
    @ForceBrowserHubUrl(url = "http://localhost:4444/wd/hub")
    @Test(description = "AUT447_ST_Investor_Registration_Wallet_Registration_Sanity", groups = {"sanityST"})
    public void AUT447_ST_Investor_Registration_Wallet_Registration_Sanity_test() throws IOException, ParseException {
        AUT3InternalLogic("USD", "United States Dollar");
    }

    private void AUT3InternalLogic(String investmentCurrency, String currencyName) throws IOException, ParseException {
        String issuerName = "Nie";
        String issuanceTBEReason = "automatic test TBE issuance";
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + File.separator + "passport-front.jpg");

        InvestorDetails investorDetails = InvestorDetails.generateRandomUSInvestor();

        startTestLevel("CP Complete Registration Flow for Individual investor USD");
        cpLoginUsingEmailAndPassword();
        cpSelectIssuer(issuerName);
        cpInvestorRegistration(investorDetails, false);
        //Removed to make the test slim and fast
        //cpAddDocument(frontImagePath);
        //cpAddInvestment(investmentCurrency, currencyName, investorDetails);
        cpAddWallet();
        //Removed to make the test slim and fast
        //cpAddIssuances(investorDetails, issuanceTBEReason, issuerName);
        //cpTransferTBEToWallet(investorDetails, issuerName);
        endTestLevel();

    }
}