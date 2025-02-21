package io.securitize.tests.blackrock;

import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.api.IssuersAPI;
import io.securitize.infra.api.MarketsAPI;
import io.securitize.infra.config.IssuerDetails;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.enums.BlockchainType;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.infra.enums.TestNetwork;
import io.securitize.infra.utils.*;
import io.securitize.infra.wallets.AbstractWallet;
import io.securitize.pageObjects.investorsOnboarding.nie.*;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdInvestorLoginScreen;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.SecuritizeIdLoginScreenLoggedIn;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep1;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.createAccountRegistrationSteps.SecuritizeIdCreateAccountRegistrationStep3;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.entityInvestorKyb.SecuritizeIdInvestorRegistration05KeyParties;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.AdditionalInformationRequiredPopUp;
import io.securitize.pageObjects.investorsOnboarding.securitizeId.primaryOffering.PrimaryOfferingSuitabiltyFormPopup;
import io.securitize.tests.InvestorDetails;
import io.securitize.tests.abstractClass.AbstractSecIdNieSharedFlow;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.UUID;

import static io.securitize.infra.reporting.MultiReporter.*;

public class AUT662_blackrock_entity_registration_Issuance_E2E extends AbstractSecIdNieSharedFlow {
    @DefaultTestNetwork(TestNetwork.ETHEREUM_SEPOLIA)
    @Test(description = "AUT662 - blackrock - happy flow: create entity investor, invest and make sure tokens have arrived to wallet")
    public void AUT662_blackrock_entity_registration_Issuance_E2E_test() {
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


        startTestLevel("Register Entity");
        getBrowser().navigateTo(MainConfig.getInvestPageUrl(issuerName));
        NieWelcomePage nieWelcome = new NieWelcomePage(getBrowser());
        SecuritizeIdCreateAccountRegistrationStep1 securitizeIdCreateAccountRegistrationStep1 = nieWelcome.ClickCreateAccount();
        securitizeIdCreateAccountRegistrationStep1.
                clickAcceptCookies().
                createInvestor(investorDetails);
        SecuritizeIdCreateAccountRegistrationStep3 securitizeIdCreateAccountRegistrationStep3 = new SecuritizeIdCreateAccountRegistrationStep3(getBrowser());
        securitizeIdCreateAccountRegistrationStep3.extractLinkFromEmailAndNavigate(investorDetails.getEmail());
        endTestLevel();


        startTestLevel("Fill the entity's authorized signer KYC");
        SecuritizeIdLoginScreenLoggedIn securitizeIdLoginScreenLoggedIn = new SecuritizeIdLoginScreenLoggedIn(browser.get());
        securitizeIdLoginScreenLoggedIn.clickCompleteIdentityVerificationButton();
        fillIndividualKycSteps(investorDetails, SecuritizeIdLoginScreenLoggedIn.class);
        // remove the refresh once ID-2498 is fixed
        getBrowser().refreshPage();
        getBrowser().waitForPageStable();
        securitizeIdLoginScreenLoggedIn
                .clickAllowButton()
                .clickCompleteYourDetails();
        String currentUrl = getBrowser().getCurrentUrl();
        Assert.assertTrue(getBrowser().getCurrentUrl().contains("invest"), "User should be directed to nie dashboard but is directed to " + currentUrl);
        endTestLevel();


        startTestLevel("Complete the KYB flow");
        fillEntityKybSteps(investorDetails, legalSignerType, false);
        SecuritizeIdInvestorRegistration05KeyParties reviewInformation = new SecuritizeIdInvestorRegistration05KeyParties(getBrowser());
        reviewInformation.clickSubmitDocumentsForReviewBtn();
        // remove if block once ID-2428 is fixed
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        currentUrl = getBrowser().getCurrentUrl();
        if (currentUrl.contains("/authorize")) {
            securitizeIdLoginScreenLoggedIn.clickContinueButton();
        }
        Assert.assertTrue(getBrowser().getCurrentUrl().contains("invest"), "User should be directed to nie dashboard but is directed to " + currentUrl);
        endTestLevel();


        startTestLevel("Validate the entity is 'pending verification'");
        NieDashboard nieDashboard = new NieDashboard(getBrowser());
        nieDashboard.waitForUserVerificationStateToBecome("Pending verification");
        endTestLevel();


        startTestLevel("Do Entity Investment - without authorized signer verification");
        getBrowser().navigateTo(MainConfig.getInvestPageUrl(issuerName) + "#/opportunity/" + blkOpportunityId);
        NieInvestmentDetails nieInvestmentDetails = new NieInvestmentDetails(getBrowser());
        String receivedUnitsAsString = nieInvestmentDetails
                .setInvestmentCurrency(investmentCurrency)
                .setInvestmentAmount(investmentAmount)
                .getReceivedUnits();
        Assert.assertEquals(RegexWrapper.stringToInteger(receivedUnitsAsString.trim()), investmentAmount, "Received amount was expected to be the same as the investmentAmount but this is not the case");
        nieInvestmentDetails
                .clickInvestButton()
                .waitForVerificationIsPendingMessageVisible()
                .closeModalAlert();
        endTestLevel();


        startTestLevel("API: Set individual as verified");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String individualInvestorSecuritizeId = investorsAPI.getSecuritizeIdOfIndividualByEmail(investorDetails.getEmail());
        investorsAPI.setKYCToPassed(individualInvestorSecuritizeId, verifiedStatus);
        endTestLevel();


        startTestLevel("Do Entity Investment - without entity verification");
        getBrowser().refreshPage();
        nieInvestmentDetails
                .clickInvestButton()
                .waitForVerificationIsPendingMessageVisible()
                .closeModalAlert();
        endTestLevel();


        startTestLevel("API: Set entity as verified");
        String entityInvestorSecuritizeId = investorsAPI.getSecuritizeIdByEmail(investorDetails.getEmail());
        investorsAPI.setKYBToStatusForBlackrockInvestor(entityInvestorSecuritizeId, verifiedStatus, "standard");
        endTestLevel();


        startTestLevel("As a verified investor - see the Enhanced Verification required pop-up");
        nieInvestmentDetails.waitForInvestorStatusToBecome(verifiedStatus);
        nieInvestmentDetails.clickInvestButton();
        AdditionalInformationRequiredPopUp additionalInformationRequiredPopUp = new AdditionalInformationRequiredPopUp(getBrowser());
        additionalInformationRequiredPopUp.clickReachMeButton();
        JSONObject investorIssuersDetails = investorsAPI.getInvestorIssuersDetailsBySecuritizeIdProfileId(entityInvestorSecuritizeId);
        String verificationSubStatus = investorIssuersDetails.getString("verificationSubStatus");
        Assert.assertEquals(verificationSubStatus, "enhance-requested", "User verificationSubStatus status should be enhance-requested. This is not the case");
        endTestLevel();


        startTestLevel("API: Set investor as enhanced verified and as accredited");
        investorsAPI.setKYBToStatusForBlackrockInvestor(entityInvestorSecuritizeId, verifiedStatus, "enhanced");
        MarketsAPI marketsAPI = new MarketsAPI();
        marketsAPI.setMarketAccreditation(entityInvestorSecuritizeId, verifiedStatus);
        endTestLevel();


        startTestLevel("As an enhanced verified investor - try to invest again");
        nieInvestmentDetails.waitForInvestorStatusToBecome(verifiedStatus);
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
                .SelectLegalSigner(investorDetails.getFirstName())
                .clickContinue();
        endTestLevel();


        startTestLevel("Investor - can't see the wire transfer details - not approved");
        getBrowser().refreshPage(true);
        getBrowser().waitForPageStable(Duration.ofSeconds(3));
        FundYourInvestmentPage fundYourInvestmentPage = new FundYourInvestmentPage(getBrowser());
        Assert.assertTrue(fundYourInvestmentPage.isWireTransferDetailsHidden(), "Wire transfer should NOT be visible. This is not the case");
        endTestLevel();


        startTestLevel("API - mark investment as confirmed");
        IssuersAPI issuersAPI = new IssuersAPI();
        String entityEmail = issuersAPI.getInvestorEmailFromInvestorName(issuerName, investorDetails.getEntityName());
        issuersAPI.setInvestmentRequestStatus(issuerName, issuedTokenName, entityEmail);
        int pledgedAmount = issuersAPI.getPledgedAmount(issuerName, issuedTokenName, entityEmail);
        Assert.assertEquals(pledgedAmount, investmentAmount, "Expected pledged amount doesn't match the actual amount");
        endTestLevel();


        startTestLevel("API: Validate investor is verified and confirmed");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String investorId = investorsAPI.getInvestorIdFromInvestorEmail(issuerName, entityEmail);
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        JSONObject investorFullKycStatus = investorsAPI.getInvestorFullKycStatus(issuerId, investorId, tokenId);
        String kybStatus = investorFullKycStatus.getString("kycStatus");
        String accreditedStatus = investorFullKycStatus.getString("accreditedStatus");
        String userTokenQualificationStatus = investorFullKycStatus.getString("userTokenQualificationStatus");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(kybStatus, verifiedStatus, "User KYB status should be verified. This is not the case");
        softAssert.assertEquals(accreditedStatus, "confirmed", "User accreditedStatus status should be confirmed. This is not the case");
        softAssert.assertEquals(userTokenQualificationStatus, "confirmed", "User userTokenQualificationStatus status should be confirmed. This is not the case");
        softAssert.assertAll();
        endTestLevel();


        startTestLevel("Investor - see your wire transfer details");
        getBrowser().refreshPage(true);
        Assert.assertTrue(fundYourInvestmentPage.isWireTransferApproved(), "Wire transfer should be visible by now. This is not the case");
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


        startTestLevel("API: Trigger scheduled issuance by Kafka event");
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


        startTestLevel("API: Sign transaction and validate the tokens have arrived");
        issuersAPI.signTransaction(BlockchainType.ETHEREUM, issuerName, issuedTokenName, transactionId + "");
        investorsAPI.waitForWalletPropertyToEqual(issuerName, investorId, tokenId, "tokensHeld", investmentAmount + "", 300, 10000);
        infoAndShowMessageInBrowser(investmentAmount + " tokens have arrived to the wallet! Test successful!");
        endTestLevel();
    }
}