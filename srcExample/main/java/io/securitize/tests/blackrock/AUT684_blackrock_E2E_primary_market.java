package io.securitize.tests.blackrock;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.IssuersAPI;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.BlockchainType;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.utils.*;
import io.securitize.infra.wallets.AbstractWallet;
import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.investorsOnboarding.nie.FundYourInvestmentPage;
import io.securitize.pageObjects.investorsOnboarding.nie.NieInvestmentDetails;
import io.securitize.pageObjects.investorsOnboarding.nie.NieInvestorQualifications;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdDashboard;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdPortfolio;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.SecuritizeIdInvestorRegistration04KeyParties;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.SecuritizeIdInvestorRegistration04_3KeyPartiesCheck;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.SecuritizeIdInvestorRegistration05KeyParties;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingSuitabiltyFormPopup;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.AccreditationStatusPage;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.securitizeIdProfileAccreditationQualification.UploadAccreditationDocumentsPage;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import io.securitize.tests.transferAgent.abstractClass.AbstractAccreditation;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.UUID;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT684_blackrock_E2E_primary_market extends AbstractSecIdNieSharedFlow {
    @DefaultTestNetwork(TestNetwork.ETHEREUM_SEPOLIA)
    @Test(description = "AUT684 - blackrock - happy flow: create entity investor, invest through Primary Market and make sure tokens have arrived to wallet")
    public void AUT684_blackrock_E2E_primary_market_test() {
        String issuerName = "BlackRock";
        int investmentAmount = 5000000;
        int tokenDecimals = 6;
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        String blkOpportunityId = Users.getProperty(UsersProperty.blkOpportunityId);
        CurrencyIds investmentCurrency = CurrencyIds.USD;
        String verifiedStatus = "verified";
        String legalSignerType = "individual";
        InvestorDetails investorDetails = InvestorDetails.generateRandomEntityInvestor();

        // add BLK to investor details, so it's easy to identify them and filter them
        investorDetails.setFirstName(investorDetails.getFirstName().replace(".AUT", ".BLK.AUT"));
        investorDetails.setEmail(investorDetails.getEmail().replace("+", "+BLK_"));
        setTestDetails(investorDetails.getEmail());
        String attorneyName = "Securitize Automation Bearer";


        startTestLevel("Navigate to the opportunity page");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.primaryOfferingMarketstUrl).replace("{opportunity-id}", blkOpportunityId));
        endTestLevel();


        startTestLevel("SID registration");
        SecuritizeIdInvestorLoginScreen securitizeIdLoginScreen = new SecuritizeIdInvestorLoginScreen(getBrowser());
        SecuritizeIdCreateAccountRegistrationStep1 securitizeIdCreateAccountRegistrationStep1 = securitizeIdLoginScreen
                .clickAcceptCookies()
                .clickCreateAccount();
        securitizeIdCreateAccountRegistrationStep1
                .createInvestor(investorDetails);
        endTestLevel();


        startTestLevel("Extract link from email and navigate to the email's link");
        SecuritizeIdCreateAccountRegistrationStep3 securitizeIdCreateAccountRegistrationStep3 = new SecuritizeIdCreateAccountRegistrationStep3(getBrowser());
        securitizeIdCreateAccountRegistrationStep3.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
        getBrowser().waitForPageStable(Duration.ofSeconds(2));
        String currentUrl = getBrowser().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/primary-market/opportunities/" + blkOpportunityId), "User should be directed to the opportunity but is directed to " + currentUrl);
        endTestLevel();


        startTestLevel("Fill the entity's authorized signer KYC");
        getBrowser().openNewTabAndSwitchToIt(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        getBrowser().waitForPageStable();
        SecuritizeIdPortfolio portfolio = new SecuritizeIdPortfolio(getBrowser());
        portfolio.clickVerifyYourself();
        fillIndividualKycSteps(investorDetails, SecuritizeIdDashboard.class);
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String individualInvestorSecuritizeId = investorsAPI.getSecuritizeIdOfIndividualByEmail(investorDetails.getEmail());
        investorsAPI.setKYCToPassed(individualInvestorSecuritizeId, verifiedStatus);
        getBrowser().switchToFirstWindow();
        getBrowser().refreshPage();
        endTestLevel();


        startTestLevel("Make an investment without verification and be directed to the KYB");
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        String receivedUnitsAsString = nieInvestmentDetails
                .setInvestmentCurrency(investmentCurrency)
                .setInvestmentAmount(investmentAmount)
                .getReceivedUnits();
        Assert.assertEquals(RegexWrapper.stringToInteger(receivedUnitsAsString.trim()), investmentAmount, "Received amount was expected to be the same as the investmentAmount but this is not the case");
        nieInvestmentDetails.clickInvestWithoutKYCVerified();
        endTestLevel();


        startTestLevel("Complete the KYB and verify it is the enhanced verification flow");
        fillEntityKybSteps(investorDetails, legalSignerType, false);
        SecuritizeIdInvestorRegistration05KeyParties reviewInformation = new SecuritizeIdInvestorRegistration05KeyParties(getBrowser());
        reviewInformation.clickGoBackButton();
        SecuritizeIdInvestorRegistration04KeyParties legalSigners = new SecuritizeIdInvestorRegistration04KeyParties(getBrowser());
        Assert.assertEquals(legalSigners.getSubtitleText(), "Please provide the roster of the entity’s legal signers and/or beneficial owners holding a stake of 10% or higher.", "Subtitle is incorrect");
        legalSigners.clickKeyPartiesList();
        SecuritizeIdInvestorRegistration04_3KeyPartiesCheck legalSignersCheck = new SecuritizeIdInvestorRegistration04_3KeyPartiesCheck(getBrowser());
        legalSignersCheck.clickContinue();
        reviewInformation.clickSubmitDocumentsForReviewBtn();
        getBrowser().waitForPageStable();
        currentUrl = getBrowser().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/primary-market/opportunities/" + blkOpportunityId), "User should be directed to the opportunity but is directed to " + currentUrl);
        endTestLevel();


        startTestLevel("API - Verify KYB statuses");
        String entityInvestorSecuritizeId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
        JSONObject investorIssuersDetails = investorsAPI.getInvestorIssuersDetailsBySecuritizeIdProfileId(entityInvestorSecuritizeId);
        String kybStatus = investorIssuersDetails.getString("verificationStatus");
        String verificationSubStatus = investorIssuersDetails.getString("verificationSubStatus");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(kybStatus, "processing", "User KYB status should be processing. This is not the case");
        softAssert.assertEquals(verificationSubStatus, "enhance-requested", "User verificationSubStatus status should be enhance-requested. This is not the case");
        softAssert.assertAll();
        endTestLevel();


        startTestLevel("API: Set investor as enhanced verified");
        investorsAPI.setKYBToStatusForBlackrockInvestor(entityInvestorSecuritizeId, verifiedStatus, "enhanced");
        endTestLevel();


        startTestLevel("Now that we are verified - try to do the investment again");
        getBrowser().refreshPage();
        int originalNumberOfTabs = getBrowser().getNumberOfWindows();
        nieInvestmentDetails.clickInvestButton();

        PrimaryOfferingSuitabiltyFormPopup suitabilityPopup = new PrimaryOfferingSuitabiltyFormPopup(getBrowser());
        suitabilityPopup
                .clickCompleteInvestorProfileBtn()
                .fillEntityInvestorInformationForm()
                .clickContinueBtn()
                .typeFullName()
                .clickSubmitBtn();

        AbstractWallet wallet = EthereumUtils.generateRandomWallet();
        assert wallet != null;
        NieInvestorQualifications nieInvestorQualifications = new NieInvestorQualifications(getBrowser());
        nieInvestorQualifications
                .clickAmAccreditedInvestorForAdditionalData()
                .fillAdditionalData(wallet.getAddress())
                .clickContinue()
                .clickGetAccredited();
        getBrowser().waitForNumbersOfTabsToBe(originalNumberOfTabs + 1);
        getBrowser().switchToLatestWindow();
        endTestLevel();


        startTestLevel("SecuritizeId - get accredited");
        AccreditationStatusPage accreditationStatusPage = new AccreditationStatusPage(getBrowser());
        UploadAccreditationDocumentsPage uploadAccreditationDocumentsPage = accreditationStatusPage
                .startAccreditationButton()
                .clickAssetsAndInvestmentsAccreditationMethodCard()
                .clickContinue();
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + File.separator + "passport-front.jpg");
        String accreditationStatus = uploadAccreditationDocumentsPage
                .uploadProofOfAssets(frontImagePath)
                .clickContinue()
                .clickOnSubmit()
                .getAccreditationStatusBadge();
        Assert.assertEquals(accreditationStatus, AbstractAccreditation.AccreditationStatus.PROCESSING.toString(), "Accreditation status should be Processing, but it is " + accreditationStatus);
        accreditationStatusPage.clickOnUnderstood();
        endTestLevel();


        startTestLevel("API - set investor as accredited");
        IssuersAPI issuersAPI = new IssuersAPI();
        String entityEmail = issuersAPI.getInvestorEmailFromInvestorName(issuerName, investorDetails.getEntityName());
        investorsAPI.setInvestorAttorney(entityEmail, attorneyName);
        investorsAPI.setInvestorAccreditation(entityEmail, verifiedStatus);
        endTestLevel();


        startTestLevel("Investor - can't see the wire transfer details - not approved");
        getBrowser().switchToFirstWindow();
        getBrowser().refreshPage(true);
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        FundYourInvestmentPage fundYourInvestmentPage = new FundYourInvestmentPage(getBrowser());
        Assert.assertTrue(fundYourInvestmentPage.isWireTransferDetailsHidden(), "Wire transfer should NOT be visible. This is not the case");
        endTestLevel();


        startTestLevel("API - mark investment as confirmed");
        issuersAPI.setInvestmentRequestStatus(issuerName, issuedTokenName, entityEmail);
        int pledgedAmount = issuersAPI.getPledgedAmount(issuerName, issuedTokenName, entityEmail);
        Assert.assertEquals(pledgedAmount, investmentAmount, "Expected pledged amount doesn't match the actual amount");
        endTestLevel();


        startTestLevel("API: Validate investor is verified and confirmed");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String investorId = investorsAPI.getInvestorIdFromInvestorEmail(issuerName, entityEmail);
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        JSONObject investorFullKycStatus = investorsAPI.getInvestorFullKycStatus(issuerId, investorId, tokenId);
        kybStatus = investorFullKycStatus.getString("kycStatus");
        String accreditedStatus = investorFullKycStatus.getString("accreditedStatus");
        String userTokenQualificationStatus = investorFullKycStatus.getString("userTokenQualificationStatus");
        softAssert.assertEquals(kybStatus, verifiedStatus, "User KYB status should be verified. This is not the case");
        softAssert.assertEquals(accreditedStatus, "confirmed", "User accreditedStatus status should be confirmed. This is not the case");
        softAssert.assertEquals(userTokenQualificationStatus, "confirmed", "User userTokenQualificationStatus status should be confirmed. This is not the case");
        softAssert.assertAll();
        endTestLevel();


        startTestLevel("Investor - see your wire transfer details");
        getBrowser().refreshPage(true);
        Assert.assertTrue(fundYourInvestmentPage.isWireTransferCardDetailsVisible(), "Wire transfer should be visible by now. This is not the case");
        endTestLevel();


        startTestLevel("API: Validate investor pledged amount");
        int roundId = investorsAPI.getActiveInvestmentRoundId(issuerName, tokenId);
        int investorPledgedAmount = issuersAPI.getInvestmentDetails(issuerId, tokenId, investorId, roundId + "", "investmentRequestForDisplay.pledged.amount");
        Assert.assertEquals(investorPledgedAmount, investmentAmount, "Investor actual pledged amount doesn't match expected pledged amount");
        endTestLevel();


        startTestLevel("API: Add transaction of the investor in the pledged amount");
        investorsAPI.postDepositTransactions(issuerName, investorId, CurrencyIds.USD, investmentAmount, tokenId);
        softAssert = new SoftAssert();

        // validate two transactions were created with desired investment amount
        int totalFunded = issuersAPI.getInvestmentDetails(issuerId, tokenId, investorId, roundId + "", "investmentRequestForDisplay.totalFunded.amount");
        net.minidev.json.JSONArray transactions = issuersAPI.getInvestmentDetails(issuerId, tokenId, investorId, roundId + "", "depositTransactions");
        softAssert.assertEquals(totalFunded, investmentAmount, "Total funded amount needs to match investment amount. This isn't the case");
        softAssert.assertEquals(transactions.size(), 2, "There should be 2 transactions with a single deposit. This is not the case");

        // validate one transaction is of type deposit and the other of type liquidation
        long depositTransactionsCount = transactions.stream().filter(x -> ((LinkedHashMap<String, Object>) x).get("direction").toString().equalsIgnoreCase("deposit")).count();
        softAssert.assertEquals(depositTransactionsCount, 1, "There should be exactly 1 deposit transactions. This is not the case");
        long liquidationTransactionsCount = transactions.stream().filter(x -> ((LinkedHashMap<String, Object>) x).get("direction").equals("liquidation")).count();
        softAssert.assertEquals(liquidationTransactionsCount, 1, "There should be exactly 1 deposit liquidation. This is not the case");

        // validate amount of tokens assigned matches investment amount
        int tokensAssigned = issuersAPI.getInvestmentDetails(issuerId, tokenId, investorId, roundId + "", "investmentRequestForDisplay.tokensAssigned");
        softAssert.assertEquals(tokensAssigned, investmentAmount, "There should be exactly " + investmentAmount + " tokens assigned. This is not the case");
        softAssert.assertAll();
        endTestLevel();


        startTestLevel("API: Trigger scheduled issuance by Kafka event and verify a signature has been created");
        int investmentRoundId = investorsAPI.getActiveInvestmentRoundId(issuerName, tokenId);
        UUID uuid = UUID.randomUUID();
        JSONObject body = new JSONObject()
                .put("issuerId", issuerId)
                .put("tokenId", tokenId)
                .put("investmentRoundId", investmentRoundId)
                .put("operatorId", Integer.parseInt(Users.getProperty(UsersProperty.automationCpBearerOperatorId)))
                .put("issuanceProcessId", uuid)
                .put("issuanceDate", DateTimeUtils.currentDateTimeUTC())
                .put("tokenDecimals", tokenDecimals);

        JSONObject bodyWrapper = new JSONObject()
                .put("type", "JSON")
                .put("data", body);

        JSONObject keyWrapper = new JSONObject()
                .put("type", "JSON")
                .put("data", tokenId);

        String fullBody = new JSONObject()
                .put("key", keyWrapper)
                .put("value", bodyWrapper).toString();

        KafkaHelper.produceKafkaEvent("isr.scheduled-issuance.created", fullBody);

        int transactionId = issuersAPI.waitForTransactionToWalletToBeCreated(issuerId, tokenId, wallet.getAddress());
        endTestLevel();


        startTestLevel("Investor - see your pending tokens in the portfolio");
        getBrowser().navigateTo(MainConfig.getProperty(MainConfigProperty.secIdUrl));
        getBrowser().waitForPageStable();
        SecuritizeIdPortfolio securitizeIdPortfolio = new SecuritizeIdPortfolio(getBrowser());
        String tokenNameInPortfolio = securitizeIdPortfolio.getTokenNameByIndex(0);
        int numberOfTokensInPortfolio = securitizeIdPortfolio.getNumberOfVisibleTokens();
        int amountOfTokens = securitizeIdPortfolio.getTokenTotalValueByTokenName(issuedTokenName);
        softAssert = new SoftAssert();
        softAssert.assertEquals(numberOfTokensInPortfolio, 1, "Only 1 token should appear in the portfolio, but there are: " + numberOfTokensInPortfolio);
        softAssert.assertEquals(amountOfTokens, investmentAmount, "Investment amount should be " + investmentAmount + "but it is " + amountOfTokens);
        softAssert.assertEquals(tokenNameInPortfolio, issuedTokenName, "Token name should be" + issuedTokenName + "but it is " + tokenNameInPortfolio);
        softAssert.assertTrue(securitizeIdPortfolio.isTokenPendingIssuance(issuedTokenName), "Token should be pending issuance but it is not");
        softAssert.assertAll();
        endTestLevel();


        startTestLevel("API: Sign transaction and validate the tokens have arrived");
        issuersAPI.signTransaction(BlockchainType.ETHEREUM, issuerName, issuedTokenName, transactionId + "");
        investorsAPI.waitForWalletPropertyToEqual(issuerName, investorId, tokenId, "tokensHeld", investmentAmount + "", 300, 10000);
        infoAndShowMessageInBrowser(investmentAmount + " tokens have arrived to the wallet! Test successful!");
        endTestLevel();


        startTestLevel("Investor - see the pending issuance in the portfolio disappeared");
        Browser.waitForExpressionToEqual(q -> {
                    info("Expecting pending issuance to vanish");
                    getBrowser().refreshPage();
                    return securitizeIdPortfolio.isTokenPendingIssuance(issuedTokenName);
                },
                null,
                false,
                "wait for pending issuance to vanish",
                60,
                1000);
        endTestLevel();
    }
}
