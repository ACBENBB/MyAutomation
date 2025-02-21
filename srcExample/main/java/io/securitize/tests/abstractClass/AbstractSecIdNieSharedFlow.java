package io.securitize.tests.abstractClass;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.IssuersAPI;
import io.securitize.infra.api.MarketsAPI;
import io.securitize.infra.api.sumsub.SumSubAPI;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.infra.enums.MetaMaskEthereumNetwork;
import io.securitize.infra.utils.EthereumUtils;
import io.securitize.infra.utils.RandomGenerator;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.utils.SshRemoteExecutor;
import io.securitize.infra.wallets.WalletExtension;
import io.securitize.pageObjects.investorsOnboarding.investWizard.*;
import io.securitize.pageObjects.investorsOnboarding.nie.*;
import io.securitize.pageObjects.investorsOnboarding.nie.docuSign.DocusignSigningPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.coinBaseChromeExtension.CoinBaseConnectWithCoinBase;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.coinBaseChromeExtension.CoinBaseHomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.SecuritizeIdInvestorKyb01EntityInformation;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.individualInvestorKyc.SecuritizeIdInvestorKyc01IndividualIdentityVerification;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskConnectWithMetaMask;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskHomePage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.metaMaskChromeExtension.MetaMaskWelcomePage;
import io.securitize.tests.InvestorDetails;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.*;

public abstract class AbstractSecIdNieSharedFlow extends AbstractSecIdInvestorRegistrationFlow {

    private static final int USD_INVESTMENT_AMOUNT = 1000;
    private static final float ETH_INVESTMENT_AMOUNT = 0.1f;
    private static final float PRICE_PER_UNIT_USD = 5;

    public void nieInitialSecIdRegistration(String issuerName, InvestorDetails investorDetails) {
        startTestLevel("open SecuritizeID registration page and Click Register button");
        getBrowser().navigateTo(MainConfig.getInvestPageUrl(issuerName));
        NieWelcomePage nieWelcome = new NieWelcomePage(getBrowser());
        SecuritizeIdCreateAccountRegistrationStep1 securitizeIdCreateAccountRegistrationStep1 = nieWelcome.ClickCreateAccount();
        endTestLevel();


        startTestLevel("SecuritizeId create an account");
        securitizeIdCreateAccountRegistrationStep1.
                clickAcceptCookies().
                createInvestor(investorDetails);
        endTestLevel();


        startTestLevel("Extract link from email and navigate");
        SecuritizeIdCreateAccountRegistrationStep3 securitizeIdCreateAccountRegistrationStep3 = new SecuritizeIdCreateAccountRegistrationStep3(getBrowser());
        securitizeIdCreateAccountRegistrationStep3.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
        endTestLevel();


        startTestLevel("SecuritizeId Scope login screen - click allow");
        SecuritizeIdLoginScreenLoggedIn securitizeIdLoginScreenLoggedIn = new SecuritizeIdLoginScreenLoggedIn(browser.get());
        NieDashboard nieDashboard = securitizeIdLoginScreenLoggedIn.clickAllowButton();
        endTestLevel();

        startTestLevel("User Credentials - Validate NiE home page - Investor Email and Status");
        //condition for rc and sandbox, that will be updated to production after its deployment
        if (!MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production")) {
            Assert.assertEquals(nieDashboard.getInvestorEmailText().toLowerCase(), (investorDetails.getFirstName() + " " + investorDetails.getLastName()).toLowerCase());
        } else {
            Assert.assertEquals(nieDashboard.getInvestorEmailText(), investorDetails.getEmail().toLowerCase());
        }
        Assert.assertEquals(nieDashboard.getVerificationStateText(), "Not verified");
        nieDashboard.clickCompleteYourDetails();
        if (investorDetails.getInvestorType().equalsIgnoreCase("individual")) {
            SecuritizeIdInvestorKyc01IndividualIdentityVerification securitizeIdInvestorRegistration01IndividualIdentityVerification = new SecuritizeIdInvestorKyc01IndividualIdentityVerification(getBrowser());
            securitizeIdInvestorRegistration01IndividualIdentityVerification.waitForStepToLoad();
            if (getCurrentTestMethod().getName().equals("AUT165_SID_Validate_Entity_Information_Issuer_Test"))
                securitizeIdInvestorRegistration01IndividualIdentityVerification.clickHomeLink();
        } else { //investor type is entity
            SecuritizeIdInvestorKyb01EntityInformation securitizeIdInvestorKyb01EntityInformation = new SecuritizeIdInvestorKyb01EntityInformation(getBrowser());
        }
        endTestLevel();
    }

    public void niePostSecIdRegistration(String issuerName, InvestorDetails investorDetails, String investmentName,
                                         CurrencyIds investmentCurrency, String issuedTokenName, boolean setKycViaNativeApi) {
        String depositWalletAddress = null;
        float investmentAmount = 0;
        int receivedUnits = 0;
        double issuerEthRate;

        WalletExtension walletExtension = getWalletExtensionToUse();

        startTestLevel("Set currency type and investment amount");
        if (investmentCurrency == CurrencyIds.unknown || investmentCurrency == CurrencyIds.USD) {
            investmentCurrency = CurrencyIds.USD; // default currency
            investmentAmount = USD_INVESTMENT_AMOUNT;
            receivedUnits = (int) (investmentAmount / PRICE_PER_UNIT_USD);
        } else if (investmentCurrency == CurrencyIds.ETH) {
            investmentAmount = ETH_INVESTMENT_AMOUNT;
            IssuersAPI issuersAPI = new IssuersAPI();
            // showing api call message in the browser breaks the login process if not already finished
            setShowMessageInBrowser(false);
            JSONObject issuerRates = issuersAPI.getIssuerRates(issuerName);
            setShowMessageInBrowser(true);
            issuerEthRate = issuerRates.getFloat("ETH");
            receivedUnits = (int) Math.round(investmentAmount * issuerEthRate / PRICE_PER_UNIT_USD);
        } else {
            errorAndStop("Currency type not yet supported: " + investmentCurrency, false);
        }
        endTestLevel(false);

        startTestLevel("verify user using KYC");
        // as both values can appear - check for either of them
        String[] expectedUserVerificationStatus = {
                "not verified",
                "pending verification"
        };
        NieDashboard nieDashboard = new NieDashboard(getBrowser());
        nieDashboard
                .waitForUserVerificationStateToBecome(expectedUserVerificationStatus);
        String userVerificationStatus = nieDashboard.getUserVerificationState();
        String expectedStatesAsString = String.join(", ", expectedUserVerificationStatus);
        List<String> expectedStatesAsList = Arrays.stream(expectedUserVerificationStatus).collect(Collectors.toList());
        Assert.assertTrue(expectedStatesAsList.stream().anyMatch(userVerificationStatus.trim()::equalsIgnoreCase), "Verification status at this point should be one of: " + expectedStatesAsString + " but this is not the case");

        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorID = investorsAPI.getInvestorIdFromInvestorEmail(issuerName, investorDetails.getEmail());
        String securitizeIdProfileId = investorsAPI.getInvestorDetails(issuerName, investorID + "", "securitizeIdProfileId");
        if (setKycViaNativeApi) {
            investorsAPI.setKYCToPassed(securitizeIdProfileId, null);
        } else {
            SumSubAPI.setApplicantAsTestComplete(securitizeIdProfileId);
        }

        nieDashboard.waitForUserVerificationStateToBecome("verified");
        userVerificationStatus = nieDashboard.getUserVerificationState();
        Assert.assertEquals(userVerificationStatus.trim().toLowerCase(), "verified", "Verification status at this point should be 'verified' but this is not the case");
        endTestLevel();


        startTestLevel("Handle 'Opportunities' tab - 01 details");
        NieDashboardOpportunities nieDashboardOpportunities = nieDashboard
                .switchToOpportunitiesTab();
        NieInvestmentDetails nieInvestmentDetails = nieDashboardOpportunities.clickViewInvestmentDetailsByName(investmentName);
        String receivedUnitsAsString = nieInvestmentDetails
                .setInvestmentCurrency(investmentCurrency)
                .setInvestmentAmount(investmentAmount)
                .getReceivedUnits();

//        Assert.assertEquals(receivedUnitsAsString.trim().toLowerCase(), receivedUnits + "", "Received amount was expected to be " + receivedUnits + " but this is not the case");
        NieInvestorQualifications nieInvestorQualifications = nieInvestmentDetails.clickInvest();
        endTestLevel();


        startTestLevel("Handle 'Opportunities' tab - 02 Investor qualification");
        InvestWizard_SignAgreement investWizardSignAgreement = nieInvestorQualifications.clickAmAccreditedInvestor();
        // due to bug we have to manually set qualification status
        warning("Workaround: setting qualification to 'confirmed' via API call until bug is fixed", false);
        investorsAPI.setQualificationStatus(investorID + "", issuerName, "confirmed");
        getBrowser().refreshPage(true);
        endTestLevel();


        startTestLevel("Handle 'Opportunities' tab - 03 Sign subscription agreement");

        float currentInvestmentAmount = investWizardSignAgreement.getInvestmentAmount();
//        Assert.assertEquals(currentInvestmentAmount, investmentAmount, "Investment amount should match value provided earlier, but this is not the case");

//        int currentReceivedTokens = nieSignAgreement.getReceivedTokens();
//        Assert.assertEquals(currentReceivedTokens, receivedUnits, "Investment received tokens amount should match value shown earlier, but this is not the case");

        DocusignSigningPage signingPage = investWizardSignAgreement.clickSignAgreement();
        endTestLevel();


        startTestLevel("Handle 'Opportunities' Sign the docuSign process");
        signingPage.signDocument();
        getBrowser().waitForPageStable();
        FundYourInvestmentPage fundYourInvestmentPage = investWizardSignAgreement.clickContinue();

        // set accreditation to 'confirmed'
        investorsAPI.setAccreditationStatus(investorID + "", issuerName, "confirmed");
        getBrowser().refreshPage();
        endTestLevel();

        startTestLevel("Switch to the 'Portfolio' tab");
        fundYourInvestmentPage.clickExitProcess();
        NieDashboardPortfolio nieDashboardPortfolio = nieDashboard.switchToPortfolioTab();
        int currentInvestmentRequests = nieDashboardPortfolio.getInvestmentsRequests();
        Assert.assertEquals(currentInvestmentRequests, 1, "There should be one investment request by now, but this is not the case");

        boolean isInvestmentVisible = nieDashboardPortfolio.isInvestmentRequestVisibleByHeader(issuedTokenName);
        Assert.assertTrue(isInvestmentVisible, "Our investment should be visible (by header) - " + issuedTokenName + ". This is not the case");

        float committedInvestmentAmount = nieDashboardPortfolio.getCommittedInvestmentByName(issuedTokenName);
//        Assert.assertEquals(committedInvestmentAmount, investmentAmount, "Committed investment amount should match previously provided value of " + issuedTokenName + ". This is not the case");

        float fundedInvestmentAmount = nieDashboardPortfolio.getFundedInvestmentByName(issuedTokenName);
//        Assert.assertEquals(fundedInvestmentAmount, 0, "Funded investment amount should be zero for now as we haven't invested yet, but this is not the case");

        String investmentVerificationStatus = nieDashboardPortfolio.getInvestmentVerificationStatusByName(issuedTokenName);
        // To validate with PO
        /*Assert.assertEquals(investmentVerificationStatus.trim().toLowerCase(), "pending verification", "Investment amount should be zero for now as we haven't invested yet, but this is not the case");
        endTestLevel();*/

        startTestLevel("API: Confirm investment request");
        IssuersAPI issuersAPI = new IssuersAPI();
        issuersAPI.setInvestmentRequestStatus(issuerName, issuedTokenName, investorDetails.getEmail());
        endTestLevel();

        startTestLevel("API: Confirm Account");
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketAccreditation(securitizeIdProfileId, "verified");
        marketsAPI.setMarketUSAccountStatus(securitizeIdProfileId, "approved", "automation tester");
        endTestLevel();

        startTestLevel("Validate investment request status changed");
        getBrowser().refreshPage();
        investmentVerificationStatus = nieDashboardPortfolio.getInvestmentVerificationStatusByName(issuedTokenName);
        Assert.assertEquals(investmentVerificationStatus.trim().toLowerCase(), "pending funding", "Investment amount should be zero for now as we haven't invested yet, but this is not the case");
        endTestLevel();

        startTestLevel("Go back to the opportunities tab and see bank account details / deposit wallet address");
        nieDashboardPortfolio.clickContinueInvestmentButton();
        if (investmentCurrency == CurrencyIds.USD) {
            // By default, the card is now already open. No need to click it open to see the details
            // fundYourInvestmentPage.clickWireTransferCard();
            Assert.assertTrue(fundYourInvestmentPage.isBankAccountDetailsVisible(), "At this point the bank details should be visible. This is not the case");
        } else if (investmentCurrency == CurrencyIds.ETH) {
            depositWalletAddress = fundYourInvestmentPage.getDestinationWalletAddress();
            Assert.assertTrue(RegexWrapper.isValidWalletAddress(depositWalletAddress), "A valid deposit wallet should be displayed. It is not the case");
        } else {
            errorAndStop("Currency not supported: " + investmentCurrency, true);
        }
        endTestLevel();

        startTestLevel("Add new wallet: " + walletExtension.toString());
        String walletAddress;
        switch (walletExtension) {
            case METAMASK:
                MetaMaskWelcomePage welcomePage = MetaMaskWelcomePage.openMetaMaskInNewTab(getBrowser());
                MetaMaskHomePage homePage = welcomePage.performFullSetup(Users.getProperty(UsersProperty.defaultPassword));
                walletAddress = homePage.pickNetwork(MetaMaskEthereumNetwork.Mainnet.toString())
                        .getWalletAddress();
                break;
            case COINBASE:
                String username = "sec_" + RandomGenerator.randomString(12);
                CoinBaseHomePage coinBaseHomePage = CoinBaseHomePage.openCoinBaseInNewTab(getBrowser());
                walletAddress = coinBaseHomePage.performFullRegistration(username, Users.getProperty(UsersProperty.automationCpPassword));
                break;
            default:
                errorAndStop("Unsupported wallet type: " + walletExtension, false);
                return;
        }

        info("Wallet address: " + walletAddress);
        getBrowser().closeLastTabAndSwitchToPreviousOne();
        endTestLevel();

        if (investmentCurrency == CurrencyIds.USD) {
            depositUsd(issuerName, investorID, investmentAmount, issuedTokenName);
        } else if (investmentCurrency == CurrencyIds.ETH) {
            depositEth(issuerName, depositWalletAddress, investmentAmount);
        }

        startTestLevel("UI: Validate investment is visible in portfolio page");
        // TODO navigation is broken here
//        fundYourInvestmentPage.clickExitProcess();
        getBrowser().refreshPage();// workaround due to product bug where the exit button doesn't work
        fundYourInvestmentPage.clickExitProcess();
        nieDashboard.switchToPortfolioTab();

        // todo: uncomment these lines once product bug is fixed
        /* fundedInvestmentAmount = nieDashboardPortfolio.getFundedInvestmentByName(ISSUED_TOKEN_NAME);
        Assert.assertEquals(fundedInvestmentAmount, INVESTMENT_AMOUNT, "Funded investment amount should be " + INVESTMENT_AMOUNT + " as we have invested. This is not the case");*/
        endTestLevel();

        startTestLevel("UI: Add new wallet");
        SecuritizeIdAddWallet securitizeIDAddWallet = nieDashboardPortfolio.switchToWalletsTab()
                .clickAddNewWallet()
                .clickAddWalletInSecId();

        int originalNumberOfWindows = getBrowser().getNumberOfWindows();

        SecuritizeIdAddWallet securitizeIdAddWallet = securitizeIDAddWallet.closeWalletPopUP().clickRegisterWallet().clickAddEtherumWallet();
        switch (walletExtension) {
            case METAMASK:
                securitizeIdAddWallet.selectMetaMask();
                break;
            case COINBASE:
                securitizeIdAddWallet.selectCoinBase();
                break;
            default:
                errorAndStop("Unsupported wallet type: " + walletExtension.toString(), false);
                return;
        }

        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows + 1);
        getBrowser().switchToLatestWindow();
        switch (walletExtension) {
            case METAMASK:
                MetaMaskConnectWithMetaMask connectWithMetaMaskPage = new MetaMaskConnectWithMetaMask(getBrowser());
                connectWithMetaMaskPage.connectAndSign();
                break;
            case COINBASE:
                CoinBaseConnectWithCoinBase connectWithCoinBasePage = new CoinBaseConnectWithCoinBase(getBrowser());
                connectWithCoinBasePage
                        .clickConnect()
                        .clickSign();
                break;
            default:
                errorAndStop("Unsupported wallet type: " + walletExtension.toString(), false);
                return;
        }

        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfWindows);
        getBrowser().switchToFirstWindow();

        // option 1 - try the refresh workaround - we encountered problem to navigate , but refresh page resolve it
        //browser.refreshPage();
        //browser.navigateTo("https://automation-issuer.invest.rc.securitize.io/#/portfolio?tab=wallets");

        // option 2 - navigate in a new page
        //browser.openNewTabAndSwitchToIt("https://automation-issuer.invest.rc.securitize.io/#/portfolio?tab=wallets");

        String walletDetailsLabel = nieDashboardPortfolio.getWalletDetailsLabel();
        String walletName;
        switch (walletExtension) {
            case METAMASK:
                walletName = "metamask 1";
                break;
            case COINBASE:
                walletName = "coinbase 1";
                break;
            default:
                errorAndStop("Unsupported wallet type: " + walletExtension.toString(), false);
                return;
        }
        Assert.assertTrue(walletDetailsLabel.contains(walletName), "Attached wallet address should match " + walletName + ". This is not the case");
        nieDashboardPortfolio.authorizeWalletByAssetName(issuedTokenName);
        endTestLevel();


        startTestLevel("UI: Wait for wallet status to become Syncing");
        int waitTimeSeconds = MainConfig.getIntProperty(MainConfigProperty.waitTimeWalletToBecomeSyncingQuorumSeconds);
        int waitIntervalSeconds = MainConfig.getIntProperty(MainConfigProperty.intervalTimeWalletToBecomeSyncingQuorumSeconds);
        nieDashboardPortfolio.waitForWalletStatusToBecome(issuedTokenName, "syncing", false, waitTimeSeconds, waitIntervalSeconds);
        endTestLevel();

        startTestLevel("UI: Wait for wallet status to become Ready");
        // force cron jobs to run now - to make wallet 'ready' faster
        SshRemoteExecutor.executeScript("walletRegistrationBypass.sh");

        waitTimeSeconds = MainConfig.getIntProperty(MainConfigProperty.waitTimeWalletToBecomeReadyQuorumSeconds);
        waitIntervalSeconds = MainConfig.getIntProperty(MainConfigProperty.intervalTimeWalletToBecomeReadyQuorumSeconds);
        nieDashboardPortfolio.waitForWalletStatusToBecome(issuedTokenName, "ready", true, waitTimeSeconds, waitIntervalSeconds);
        endTestLevel();


        startTestLevel("API: Create Issuance Transaction via API in token ready wallet");
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        JSONObject walletData = investorsAPI.getWalletDetails(issuerName, investorID + "", walletName, tokenId);
        int walletId = walletData.getInt("id");
        String issuanceDetailsAsString = issuersAPI.createIssuanceTransactionInWallet(issuerName, investorID + "", walletId, tokenId, receivedUnits);
        JSONObject issuanceDetails = new JSONObject(issuanceDetailsAsString);
        int issuanceId = issuanceDetails.getInt("id");
        info("issuanceId: " + issuanceId);

        // find the correct transaction id by list of transactions for the issuer and by the walletId in the description
        String investorExternalId = investorsAPI.getInvestorDetails(issuerName, investorID + "", "externalId");
        int transactionId = issuersAPI.getBlockchainTransactionIdByInvestorExternalId(investorExternalId, issuerName, issuedTokenName);
        info("transactionId: " + transactionId);
        endTestLevel(false);


        startTestLevel("Sign the transaction");
        // get transaction data
        String transactionBodyForSigning = issuersAPI.getTransactionDetailsForSignatureNewVersion(issuerName, issuedTokenName, transactionId + "");

        // Sign the transaction
        String signerPrivateKey = issuersAPI.getWalletAndPK(issuerName, issuedTokenName)[1];
        String signingResponse = EthereumUtils.signTransaction(transactionBodyForSigning, signerPrivateKey);
        Assert.assertNotNull(signingResponse, "Signing the token issuance response must not be null");

        // run post script signing using API
        issuersAPI.signTransactionNewVersion(issuerName, transactionId + "", issuedTokenName, signingResponse, true);

        // run endpoint to finish the signature process
        endTestLevel(false);


        startTestLevel("API: Wait for transaction status to become 'success'");
        // force cron jobs to run now - to make transaction status to become 'success' faster
        SshRemoteExecutor.executeScript("issuanceSetToReadyBypass.sh");

        int waitTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.waitTimeTransactionToBecomeSuccessQuorumSeconds);
        int intervalTimeSecondsIssuanceReady = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTransactionToBecomeSuccessQuorumSeconds);
        investorsAPI.waitForTransactionSuccess(issuerName, investorID + "", issuanceId, tokenId, waitTimeSecondsIssuanceReady, intervalTimeSecondsIssuanceReady * 1000);
        endTestLevel(false);


        startTestLevel("UI: Validate tokens are visible in the UI");
        int waitTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.waitTimeTokensToArriveInWalletQuorumSeconds);
        int intervalTimeSecondsTokensToArriveInWallet = MainConfig.getIntProperty(MainConfigProperty.intervalTimeTokensToArriveInWalletQuorumSeconds);
        nieDashboardPortfolio.waitForWalletTokensBalanceToBecome(issuedTokenName, receivedUnits, waitTimeSecondsTokensToArriveInWallet, intervalTimeSecondsTokensToArriveInWallet);
        getBrowser().refreshPage(); // due to product bug where links don't always work
        nieDashboardPortfolio.switchToOwnedAssetsTab();

        int ownedAssetsHeaderCount = nieDashboardPortfolio.getOwnedAssetsHeaderCount();
        Assert.assertEquals(ownedAssetsHeaderCount, 1, "After receiving the tokens, the owned assets count in the header should be 1");

        String assetStatus = nieDashboardPortfolio.getOwnedAssetStatusByName(issuedTokenName);
        Assert.assertEquals(assetStatus.trim().toLowerCase(), "owned", "Owned asset status should be 'OWNED'. This is not the case");

        int assetBalance = nieDashboardPortfolio.getOwnedAssetBalanceByName(issuedTokenName);
//        Assert.assertEquals(assetBalance, receivedUnits, "Owned asset balance should be " + receivedUnits + ". This is not the case");

        if (investmentCurrency == CurrencyIds.ETH) {
            int calculatedInvestmentAmount = (int) (receivedUnits * PRICE_PER_UNIT_USD);
            int assetCurrentValue = nieDashboardPortfolio.getOwnedAssetCurrentValueByName(issuedTokenName);
//            Assert.assertEquals(assetCurrentValue, calculatedInvestmentAmount, "Owned asset current value should be " + calculatedInvestmentAmount + ". This is not the case");
        } else {
            int assetCurrentValue = nieDashboardPortfolio.getOwnedAssetCurrentValueByName(issuedTokenName);
            // To validate with PO
            //Assert.assertEquals(assetCurrentValue, investmentAmount, "Owned asset current value should be " + investmentAmount + ". This is not the case");
        }
        endTestLevel();


        startTestLevel("UI: Validate owned asset details");
        NieOwnedAssetDetails nieOwnedAssetDetails = nieDashboardPortfolio.viewOwnedAssetDetailsByName(issuedTokenName);
        int totalUnitBalance = nieOwnedAssetDetails.getTotalUnitBalance();
//        Assert.assertEquals(totalUnitBalance, receivedUnits, "Total unit balance should be " + receivedUnits + ". This is not the case");

        int balanceInWallets = nieOwnedAssetDetails.getBalanceInWallets();
//        Assert.assertEquals(balanceInWallets, receivedUnits, "Balance in wallets should be " + receivedUnits + ". This is not the case");

        float committedInvestment = nieOwnedAssetDetails.getCommittedInvestment();
//        Assert.assertEquals(committedInvestment, investmentAmount, "Committed investment amount should be " + investmentAmount + ". This is not the case");

        if (investmentCurrency == CurrencyIds.ETH) {
            warning("Due to a bug - we don't yet see ETH investments as being funded", true);
        } else {
            float fundedInvestment = nieOwnedAssetDetails.getFundedInvestment();
            Assert.assertEquals(fundedInvestment, investmentAmount, "Funded investment amount should be " + investmentAmount + ". This is not the case");
        }
        endTestLevel();


        startTestLevel("API: Validate investor number of tokens");
        walletData = investorsAPI.getWalletDetails(issuerName, investorID + "", walletName, tokenId);
        int walletTokensHeld = walletData.getInt("tokensHeld");
        Assert.assertEquals(walletTokensHeld, receivedUnits, "Tokens held in wallet by API should be " + receivedUnits + ". This is not the case");
        endTestLevel();
    }

    private void depositUsd(String issuerName, String investorID, float investmentAmount, String issuedTokenName) {
        startTestLevel("API: Create investment deposit");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        info("tokenId found using API call: " + tokenId);

        String transactionDetails = investorsAPI.postDepositTransactions(issuerName,
                investorID + "",
                CurrencyIds.USD,
                investmentAmount,
                tokenId);
        info("Transaction details: " + transactionDetails);
        endTestLevel();
    }

    private void depositEth(String issuerName, String depositWalletAddress, float investmentAmount) {
        startTestLevel("MetaMask: Import wallet and transfer funds");

        MetaMaskHomePage homePage = MetaMaskHomePage.openMetaMaskInNewTab(getBrowser());
        String importedWalletPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.investorPaymentWalletPrivateKey);
        homePage
                .importWalletAndSendFounds(importedWalletPrivateKey, investmentAmount, depositWalletAddress)
                // switch back to the mainnet wallet
                .selectAccountByName("Account 1")
                .pickNetwork(MetaMaskEthereumNetwork.Mainnet.toString());

        // close metamask tab
        getBrowser().closeLastTabAndSwitchToPreviousOne();

        endTestLevel();
    }

    public void setKYCAsVerifiedWithoutValidation(String issuerName, InvestorDetails investorDetails, boolean setKycViaNativeApi) {
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String securitizeIdProfileId;
        if (MainConfig.getProperty(MainConfigProperty.environment).equalsIgnoreCase("production")) {
            String investorID = investorsAPI.getInvestorIdFromInvestorEmail(issuerName, investorDetails.getEmail());
            securitizeIdProfileId = investorsAPI.getInvestorDetails(issuerName, investorID + "", "securitizeIdProfileId");
        } else { //environment is rc or sandbox
            securitizeIdProfileId = investorsAPI.getSecuritizeIdByName(investorDetails.getEntityName());
        }
        if (setKycViaNativeApi) {
            investorsAPI.setKYCToPassed(securitizeIdProfileId, null);
        } else {
            SumSubAPI.setApplicantAsTestComplete(securitizeIdProfileId);
        }
        getBrowser().refreshPage();
    }

    public void setVerificationStatusAsVerified(String issuerName, InvestorDetails investorDetails, boolean setKycViaNativeApi) {
        setKYCAsVerifiedWithoutValidation(issuerName, investorDetails, setKycViaNativeApi);
        NieDashboard nieDashboard = new NieDashboard(getBrowser());
        nieDashboard.waitForUserVerificationStateToBecome("verified");
        String userVerificationStatus = nieDashboard.getUserVerificationState();
        Assert.assertEquals(userVerificationStatus.trim().toLowerCase(), "verified", "Verification status at this point should be 'verified' but this is not the case");
    }


    public void opportunityStepOneSimplified_Investment(CurrencyIds investmentCurrency, float investmentAmount, boolean allowSkipAccreditation, boolean withoutKYCverified) {
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        nieInvestmentDetails
                .setInvestmentCurrency(investmentCurrency)
                .setInvestmentAmount(investmentAmount)
                .getReceivedUnits();
        if (allowSkipAccreditation) {
            nieInvestmentDetails.clickInvestWithAllowSkipAccreditation();
        } else if (withoutKYCverified) {
            nieInvestmentDetails.clickInvestWithoutKYCVerified();
        } else {
            nieInvestmentDetails.clickInvest();
        }
    }

}

