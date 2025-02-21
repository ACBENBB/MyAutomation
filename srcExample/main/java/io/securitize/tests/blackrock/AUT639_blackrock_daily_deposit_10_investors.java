package io.securitize.tests.blackrock;

import io.securitize.infra.api.EtherscanAPI;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.config.WalletDetails;
import io.securitize.infra.enums.MetaMaskEthereumNetwork;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.DefaultTestNetwork;
import io.securitize.infra.utils.RandomGenerator;
import io.securitize.infra.utils.SkipTestOnEnvironments;
import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpInvestorDetailsPage;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpOnBoarding;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSideMenu;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpSignatures;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.controlPanel.abstractClass.AbstractCpInvestorRegistrationFlow;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT639_blackrock_daily_deposit_10_investors extends AbstractCpInvestorRegistrationFlow {

    private static final int FUNDING_BASE_AMOUNT = 100000;
    private static final int FUNDING_MULTIPLIER_AMOUNT = 100000;
    private static final double MINIMUM_ISSUER_BALANCE_IN_WALLET = 0.001;


    @SkipTestOnEnvironments(environments = {"rc"})
    @Test(description = "AUT639 - blackrock - invest for all defined investors")
    @DefaultTestNetwork(TestNetwork.ETHEREUM_SEPOLIA)
    public void AUT639_blackrock_daily_deposit_10_investors_test() {
        String issuerName = "BlackRock_dataCreation";
        String operatorEmail = Users.getProperty(UsersProperty.blackrockCpE2eEmail);
        String operatorPassword = Users.getProperty(UsersProperty.automationCpPassword);

        // validate we got valid investor - otherwise stop
        String currentEmail = System.getenv("investorEmail");
        if (currentEmail == null) {
            errorAndStop("Missing environment variable 'investorNumber'. Can't resume", false);
            return;
        }
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorNameInContent = investorsAPI.getInvestorDetails(issuerName, investorsAPI.getInvestorIdFromInvestorEmail(issuerName, currentEmail), "name");

        // validate issuer wallet has sufficient balance - otherwise skip the flow
        WalletDetails walletDetails = Users.getIssuerWalletDetails(issuerName, AbstractTest.getTestNetwork());
        String walletAddress = walletDetails.getWalletAddress();
        double issuerBalance = EtherscanAPI.getWalletBalance(walletAddress, MetaMaskEthereumNetwork.Sepolia);
        if (issuerBalance < MINIMUM_ISSUER_BALANCE_IN_WALLET) {
            errorAndStop(String.format("Not enough Sepolia balance for issuer on wallet %s. Balance: %s < %s", walletAddress, issuerBalance, MINIMUM_ISSUER_BALANCE_IN_WALLET), false);
            return;
        } else {
            info(String.format("Issuer has sufficient balance of %s in wallet %s - we can resume!", issuerBalance, walletAddress));
        }


        startTestLevel("Operator - login into control panel");
        cpLoginUsingEmailAndPassword(operatorEmail, operatorPassword);
        cpSelectIssuer(issuerName);
        CpSideMenu sideMenu = new CpSideMenu(getBrowser());
        CpOnBoarding onBoarding = sideMenu.clickOnBoarding();
        endTestLevel();


        int fundingAmountForInvestor = FUNDING_BASE_AMOUNT + (int) (Math.random() * FUNDING_MULTIPLIER_AMOUNT);
        startTestLevel("Invest for investor " + currentEmail);
        CpInvestorDetailsPage cpInvestorDetailsPage = onBoarding
                .searchInvestorByTextBox(currentEmail)
                .waitForTableToContainExactlyOneRowWithContent(investorNameInContent)
                .waitForTableToBeNotEmpty()
                .clickOnFirstInvestor();


        getBrowser().waitForPageStable(Duration.ofSeconds(3)); // to avoid clicking the wrong "add investment button"
        if (!cpInvestorDetailsPage.isClickAddInvestmentEnabled()) {
            startTestLevel("Handle 'add investment' button not enabled");
            warning("Button 'Add investment isn't enabled. Trying to fix it by signing any pending issuance", true);
            String originalUrl = getBrowser().getCurrentUrl();
            CpSignatures cpSignatures = cpNavigateToSignaturesScreen();
            boolean shouldSign = cpSignatures.filterByStatus("Pending")
                    .waitForSignaturePageToStabilize()
                    .isTableContainingRowsByDescriptionContent(investorNameInContent, 0);
            if (shouldSign) {
                info("Found transaction to sign for investor " + currentEmail + ". Signing it now");
                String issuerSignerWalletAddress = Users.getIssuerDetails(issuerName, IssuerDetails.signerWalletAddress);
                String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);
                cpSignatures
                        .checkRowByUserFirstName(investorNameInContent)
                        .clickSignAllTransactionsButton()
                        .clickWalletTypeSignatureRadioButton()
                        .typeSignerAddress(issuerSignerWalletAddress)
                        .typePrivateKey(issuerSignerPrivateKey)
                        .clickSignNow();
                getBrowser().navigateTo(originalUrl);
                getBrowser().waitForPageStable();

                if (!cpInvestorDetailsPage.isClickAddInvestmentEnabled()) {
                    errorAndStop("Add investment button is disabled even after signing the pending transaction. Terminating", true);
                }
            } else {
                errorAndStop("Add investment button is disabled and no pending transaction is waiting. Terminating", true);
            }
            endTestLevel();
        }

        String todaysDate = DateTimeUtils.currentDate("MMM dd, yyyy");
        cpInvestorDetailsPage
                .clickAddInvestment()
                .typeAmount(fundingAmountForInvestor)
                .selectCurrency("USD")
                .typeDate(todaysDate)
                .selectSubscriptionAgreement("Confirmed")
                .clickOk();

        // wait initial total funded to become zero
        Browser.waitForExpressionToEqual(q -> {
                    int originalTotalFunded = cpInvestorDetailsPage.getTotalFunded();
                    info("Expecting total funded to be reset to 0. Currently it is: " + originalTotalFunded);
                    return originalTotalFunded == 0;
                },
                null,
                true,
                "wait for total funded to be reset to zero",
                180,
                1000);
        int originalTotalFunded = 0;
        String txId = RandomGenerator.randomString(8);
        cpInvestorDetailsPage.clickAddTransaction()
                .typeDate(todaysDate)
                .setType("Deposit")
                .typeAmount(fundingAmountForInvestor)
                .selectCurrency("USD")
                .typeTxId(txId)
                .clickOk();


        // validate total funded with time tolerance
        Browser.waitForExpressionToEqual(q -> {
                    int currentTotalFunded = cpInvestorDetailsPage.getTotalFunded();
                    info(String.format("Expecting current funded amount of %s to differ from original funded amount of %s", currentTotalFunded, originalTotalFunded));
                    return currentTotalFunded != originalTotalFunded;
                },
                null,
                true,
                "wait for total funded to be updated from " + originalTotalFunded + " to " + (originalTotalFunded + fundingAmountForInvestor),
                180,
                1000);
        int currentTotalFunded = cpInvestorDetailsPage.getTotalFunded();
        Assert.assertEquals(currentTotalFunded, originalTotalFunded + fundingAmountForInvestor, "Total funded value is not as expected");
        endTestLevel();
    }
}