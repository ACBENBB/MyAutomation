package io.securitize.infra.api;

import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.api.algorand.AlgorandApi;
import io.securitize.infra.config.*;
import io.securitize.infra.enums.BlockchainType;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.EthereumUtils;
import io.securitize.infra.utils.UseLegacyWalletDetails;
import io.securitize.infra.webdriver.Browser;
import io.securitize.tests.abstractClass.AbstractTest;
import io.securitize.tests.fundraise.pojo.FT_TestData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.*;

public class IssuersAPI extends AbstractAPI {

    final String env;
    final String CPBASEURL;
    Response cpLoginResponse;

    public IssuersAPI() {
        this.env = MainConfig.getProperty(MainConfigProperty.environment);
        this.CPBASEURL = "https://cp." + env + ".securitize.io";
    }

    String getIssuerInfo(String issuerName) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        return getAPI("issuers/" + issuerId + "/info", 200, true);
    }

    public String createIssuanceTransactionInWallet(String issuerName, String investorId, int walletId, String tokenId, int amount) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String body = new JSONObject()
                .put("sourceType", "wallet")
                .put("tokenWalletId", walletId)
                .put("issuanceDate", DateTimeUtils.currentDateTimeUTC())
                .put("tokenId", tokenId)
                .put("issueAmount", amount)
                .put("reason", "test")
                .put("clearAfterIssuance", false).toString();

        return postAPI("issuers/" + issuerId + "/investors/" + investorId + "/token-issuances", body, 200, true);
    }

    public String createIssuanceTBETransactionInWallet(String issuerName, String investorId, String tokenId, int amount) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String body = new JSONObject()
                .put("sourceType", "treasury")
                .put("issuanceDate", DateTimeUtils.currentDateTimeUTC())
                .put("tokenId", tokenId)
                .put("issueAmount", amount)
                .put("reason", "test TBE tokens")
                .put("clearAfterIssuance", true).toString();

        return postAPI("issuers/" + issuerId + "/investors/" + investorId + "/token-issuances", body, 200, true);
    }

    public int getBlockchainTransactionIdByInvestorExternalId(String investorExternalId, String issuerName, String issuedTokenName) {
        infoAndShowMessageInBrowser("Run api call: getBlockchainTransactionIdByInvestorExternalId");

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", "100");
        queryParams.put("page", "0");
        queryParams.put("status", "all");

        String resultAsString = getAPI("blockchain/signatures/issuers/" + issuerId + "/tokens/" + tokenId + "/transactions", queryParams, 200, true);
        JSONObject resultAsJSONObject = new JSONObject(resultAsString);
        JSONArray transactionsData = resultAsJSONObject.getJSONArray("data");

        for (Object currentTransactionAsObject : transactionsData) {
            JSONObject currentTransaction = (JSONObject) currentTransactionAsObject;
            if (currentTransaction.getString("description").toLowerCase().contains(investorExternalId.toLowerCase())) {
                return currentTransaction.getInt("id");
            }
        }

        return -1;
    }

    public void setInvestmentRequestStatus(String issuerName, String issuedTokenName, String investorEmail) {
        infoAndShowMessageInBrowser("Run api call: setInvestmentRequestStatus");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        // investor ID
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorId = investorsAPI.getInvestorIdFromInvestorEmail(issuerName, investorEmail);

        // investment round id
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        int investmentRoundId = investorsAPI.getActiveInvestmentRoundId(issuerName, tokenId);

        // get investment request id
        String response = getAPI("issuers/" + issuerId + "/investors/" + investorId + "/investment?round-id=" + investmentRoundId + "&token-id=" + tokenId, 200, true);
        int requestId = new JSONObject(response).getJSONObject("investmentRequestForDisplay").getInt("id");
        float amount = new JSONObject(response).getJSONObject("investmentRequestForDisplay").getJSONObject("pledged").getFloat("amount");
        int currencyId = new JSONObject(response).getJSONObject("investmentRequestForDisplay").getJSONObject("pledged").getInt("currencyId");

        // build request body
        JSONObject pledgedObject = new JSONObject()
                .put("amount", amount)
                .put("currencyId", currencyId);

        String body = new JSONObject()
//                .put("status", "in-progress")
                .put("id", requestId)
                .put("tokenId", tokenId)
                .put("pledged", pledgedObject)
                .put("subscriptionAgreementStatus", "confirmed").toString();
//                .put("subscriptionAgreementSignedAt", DateTimeUtils.currentDateTimeUTC()).toString();

        patchAPI("issuers/" + issuerId + "/investors/" + investorId + "/investment?round-id=" + investmentRoundId, body, 200, true);
    }

    public int getPledgedAmount(String issuerName, String issuedTokenName, String investorEmail) {
        infoAndShowMessageInBrowser("Run api call: getPledgedAmount");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        // investor ID
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorId = investorsAPI.getInvestorIdFromInvestorEmail(issuerName, investorEmail);

        // investment round id
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);
        int investmentRoundId = investorsAPI.getActiveInvestmentRoundId(issuerName, tokenId);

        // get investment request id
        String response = getAPI("issuers/" + issuerId + "/investors/" + investorId + "/investment?round-id=" + investmentRoundId + "&token-id=" + tokenId, 200, true);
        return JsonPath.read(response, "investmentRequestForDisplay.pledged.amount");
    }

    public JSONObject getIssuerRates(String issuerName) {
        infoAndShowMessageInBrowser("Run api call: getIssuerRates");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String resultAsString = getAPI("issuers/" + issuerId + "/rates", 200, true);
        return new JSONObject(resultAsString);
    }

    public JSONObject getIssuerTransactions(String issuerId, String tokenId, String transactionsStatus) {
        infoAndShowMessageInBrowser("Run api call: getIssuerTransactions");
        String resultAsString = getAPI("blockchain/signatures/issuers/" + issuerId + "/tokens/" + tokenId + "/transactions?limit=50&page=0&status=" + transactionsStatus, 200, true);
        return new JSONObject(resultAsString);
    }


    public JSONObject getControlPanelControlBookIssuances(String issuerName, String tokenId) {
        infoAndShowMessageInBrowser("Run api call: getControlPanelControlBookIssuances");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String resultAsString = getAPI("issuers/" + issuerId + "/tokens/" + tokenId + "/control-book-records/info", 200, true);
        return new JSONObject(resultAsString);
    }

    public String getTransactionDetailsForSignatureNewVersion(String issuerName, String issuedTokenName, String transactionId, String... issuanceType) {
        infoAndShowMessageInBrowser("Run api call: getTransactionDetailsForSignatureNewVersion");

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);

        String signerWalletAddress;
        if (AbstractTest.getCurrentTestMethod().getAnnotation(UseLegacyWalletDetails.class) != null) {
            //retrieve Transfer Agent wallet’s data instead of Issuer wallet’s data
            //since txn “ReallocateTokens” requires transfer-agent role for signing this operation (it's one of the DS Protocol 3 changes)
            signerWalletAddress = getWalletAndPK(issuerName, issuedTokenName, issuanceType)[0];
        } else {
            WalletDetails walletDetails = Users.getIssuerWalletDetails(issuerName, AbstractTest.getTestNetwork());
            signerWalletAddress = walletDetails.getWalletAddress();
        }

        // build request body
        String body = new JSONObject()
                .put("identity", signerWalletAddress).toString();

        return postAPI("blockchain/signatures/issuers/" + issuerId + "/tokens/" + tokenId + "/transactions/" + transactionId + "/ds-protocol", body, 201, true);
    }

    public void signTransactionNewVersion(String issuerName, String transactionId, String issuedTokenName, String signedTransaction, boolean addPrefix) {
        infoAndShowMessageInBrowser("Run api call: signTransactionNewVersion");

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String tokenId = investorsAPI.getTokenIdByName(issuerName, issuedTokenName);

        String signerWalletAddress;
        if (AbstractTest.getCurrentTestMethod().getAnnotation(UseLegacyWalletDetails.class) != null) {
            signerWalletAddress = getWalletAndPK(issuerName, issuedTokenName)[0];
        } else {
            WalletDetails walletDetails = Users.getIssuerWalletDetails(issuerName, AbstractTest.getTestNetwork());
            signerWalletAddress = walletDetails.getWalletAddress();
        }

        // make sure we have the correct prefix
        if ((addPrefix) && (!signedTransaction.startsWith("0x"))) {
            signedTransaction = "0x" + signedTransaction;
        }

        String body = new JSONObject()
                .put("transactionData", signedTransaction)
                .put("identity", signerWalletAddress).toString();

        postAPI("blockchain/signatures/issuers/" + issuerId + "/tokens/" + tokenId + "/transactions/" + transactionId + "/signature", body, 201, true);
    }

    @SuppressWarnings("unused")
    public String getDeploymentIdByTokenId(String issuerName, String tokenId) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String resultAsString = getAPI("issuers/" + issuerId + "/deployments/" + tokenId, null, 200, true);
        JSONObject resultAsJSONObject = new JSONObject(resultAsString);
        return resultAsJSONObject.getString("abstractionLayerDeploymentId");
    }

    /* Return the Wallet/PK depending on the Token name (created due new tokens with new wallet/pk to sign) */
    public String[] getWalletAndPK(String issuerName, String tokenName, String... issuanceType) {
        //modify param 'tokenName' to handle retrieving Transfer Agent wallet’s data instead of Issuer wallet’s data
        //since txn “ReallocateTokens” requires transfer-agent role for signing this operation (it's one of the DS Protocol 3 changes)
        if (issuanceType.length == 1 && issuanceType[0].equals("ReallocateTokens")) {
            tokenName += issuanceType[0];
        }
        IssuerDetails[][] options = new IssuerDetails[][]{
                new IssuerDetails[]{IssuerDetails.issuedTokenName, IssuerDetails.signerWalletAddress, IssuerDetails.signerPrivateKey},
                new IssuerDetails[]{IssuerDetails.invPaysETHTokenName, IssuerDetails.invPaysETHSignerWalletAddress, IssuerDetails.invPaysETHSignerPrivateKey},
                new IssuerDetails[]{IssuerDetails.issuerPaysETHTokenName, IssuerDetails.issuerPaysETHSignerWalletAddress, IssuerDetails.issuerPaysETHSignerPrivateKey},
                new IssuerDetails[]{IssuerDetails.issuerPaysETHTokenNameTransferAgentRole, IssuerDetails.issuerPaysETHSignerWalletAddressTransferAgentRole, IssuerDetails.issuerPaysETHSignerPrivateKeyTransferAgentRole},
                new IssuerDetails[]{IssuerDetails.controlBookToken, IssuerDetails.controlBookTokenSignerWalletAddress, IssuerDetails.controlBookTokenSignerPrivateKey}
        };

        for (IssuerDetails[] currentPossibleCase : options) {
            String currentToken = Users.getIssuerDetails(issuerName, currentPossibleCase[0]).trim();
            if (tokenName.equals(currentToken)) {
                String wallet = Users.getIssuerDetails(issuerName, currentPossibleCase[1]);
                String pk = Users.getIssuerDetails(issuerName, currentPossibleCase[2]);
                return new String[]{wallet, pk};
            }
        }
        errorAndStop("Can't find wallet and PK for current issuer and token name", false);
        return null;
    }

    public int performFullIssuance(BlockchainType blockchainType, String issuerName, String investorId, String walletName, String tokenName, String issuanceType, int amount) {

        startTestLevel("API: Create Issuance Transaction via API in token ready wallet");
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String tokenId = investorsAPI.getTokenIdByName(issuerName, tokenName);

        int walletId = -1;
        String currentDate;
        if (!issuanceType.equalsIgnoreCase("treasury")) {
            JSONObject walletData = investorsAPI.getWalletDetails(issuerName, investorId, walletName, tokenId);
            walletId = walletData.getInt("id");
            currentDate = walletData.getString("creationDate");
        } else {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            sdfDate.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date now = new Date();
            currentDate = sdfDate.format(now);
        }

        JSONObject issuanceDetails = investorsAPI.addIssuance(investorId, issuerName, tokenId, walletId, currentDate, issuanceType, amount);
        int issuanceId = issuanceDetails.getInt("id");
        info("issuanceId: " + issuanceId);

        // find the correct transaction id by list of transactions for the issuer and by the walletId in the description
        String investorExternalId = investorsAPI.getInvestorDetails(issuerName, investorId, "externalId");
        int transactionId = getBlockchainTransactionIdByInvestorExternalId(investorExternalId, issuerName, tokenName);
        info("transactionId: " + transactionId);
        endTestLevel(false);

        signTransaction(blockchainType, issuerName, tokenName, transactionId + "");

        return issuanceId;
    }


    public void signTransaction(BlockchainType blockchainType, String issuerName, String tokenName, String transactionId, String... issuanceType) {
        startTestLevel("Sign the transaction");
        // get transaction data
        String transactionBodyForSigning = getTransactionDetailsForSignatureNewVersion(issuerName, tokenName, transactionId + "", issuanceType);

        String signingResponse = "";
        boolean addPrefix = true;
        if (blockchainType == BlockchainType.ETHEREUM) {
            String signerPrivateKey;
            if (AbstractTest.getCurrentTestMethod().getAnnotation(UseLegacyWalletDetails.class) != null) {
                //retrieve Transfer Agent wallet’s data instead of Issuer wallet’s data
                //since txn “ReallocateTokens” requires transfer-agent role for signing this operation (it's one of the DS Protocol 3 changes)
                signerPrivateKey = getWalletAndPK(issuerName, tokenName, issuanceType)[1];
            } else {
                WalletDetails walletDetails = Users.getIssuerWalletDetails(issuerName, AbstractTest.getTestNetwork());
                signerPrivateKey = walletDetails.getWalletPrivateKey();
            }
            signingResponse = EthereumUtils.signTransaction(transactionBodyForSigning, signerPrivateKey);
            Assert.assertNotNull(signingResponse, "Signing the token issuance response must not be null");
        } else if (blockchainType == BlockchainType.ALGORAND) {
            AlgorandApi algorandApi = new AlgorandApi();
            JSONObject transactionBody = new JSONObject(transactionBodyForSigning);
            String transactionDataAsString = transactionBody.getString("transactionData");
            JSONObject transactionData = new JSONObject(transactionDataAsString);
            String issuerSignerPrivateKey = Users.getIssuerDetails(issuerName, IssuerDetails.signerPrivateKey);
            signingResponse = algorandApi.signTransaction(transactionData, issuerSignerPrivateKey);
            addPrefix = false;
        } else {
            errorAndStop("Crypto type not supported: " + blockchainType.name(), false);
        }

        // run post script signing using API

        signTransactionNewVersion(issuerName, transactionId + "", tokenName, signingResponse, addPrefix);

        // run endpoint to finish the signature process
        endTestLevel(false);
    }

    public String getIssuerOnboardingAccreditationStatus(String issuerId, String tokenId, String investorCpId) {

        LoginAPI loginAPI = new LoginAPI();
        cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];

        RequestSpecification getIssuerOnboardingAccreditationStatusRequest = new RequestSpecBuilder()
                .setBaseUri(CPBASEURL)
                .addHeader("accessToken", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String getCpOnboardingInvestorAccreditationStatus = null;
        getCpOnboardingInvestorAccreditationStatus = given().log().all()
                .spec(getIssuerOnboardingAccreditationStatusRequest)
                .pathParam("issuerId", issuerId)
                .pathParam("investorCpId", investorCpId)
                .pathParam("tokenId", tokenId)
                .when().get(CPBASEURL + "/api/v2/issuers/{issuerId}/investors/{investorCpId}/kyc?tokenId={tokenId}")
                .then().assertThat().statusCode(200).extract().response().asString();

        JSONObject cpOnboardingResponseObject = new JSONObject(getCpOnboardingInvestorAccreditationStatus);
        return cpOnboardingResponseObject.getString("userTokenQualificationStatus");

    }

    public String getInvestorCpIdByEmail(String issuerId, String investorEmail) {
        LoginAPI loginAPI = new LoginAPI();
        cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];
        RequestSpecification getInvestorCpIdByEmailSpec = new RequestSpecBuilder()
                .setBaseUri(CPBASEURL)
                .addHeader("accessToken", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String cpInvestorIdResponse = given().spec(getInvestorCpIdByEmailSpec).when().get("/api/v2/issuers/" + issuerId + "/investors?view=onboarding&q=" + investorEmail + "&page=0&limit=25&order-field=created-at&order-direction=desc").then().extract().response().asString();
        JSONObject cpInvestorIdResponseObject = new JSONObject(cpInvestorIdResponse);
        JSONArray arrayDataObject = cpInvestorIdResponseObject.getJSONArray("data");
        JSONObject cpInvestorDataObject = arrayDataObject.getJSONObject(0);
        return cpInvestorDataObject.get("id").toString();

    }

    public String getCpOnboardingAccreditationStatus(String issuerId, String tokenId, String investorEmail) {
        String investorCpId = getInvestorCpIdByEmail(issuerId, investorEmail);
        return getIssuerOnboardingAccreditationStatus(issuerId, tokenId, investorCpId);
    }

    public String getCpOnboardingRoundId(String issuerId, String tokenId, String cpInvestorId) {
        return getRoundId(issuerId, tokenId, cpInvestorId);
    }

    public String getRoundId(String issuerId, String tokenId, String investorCpId) {
        LoginAPI loginAPI = new LoginAPI();
        cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];

        RequestSpecification getRoundIdRequestSpec = new RequestSpecBuilder()
                .setBaseUri(CPBASEURL)
                .addHeader("accessToken", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String getCpOnboardingInvestorRoundIdResponse = null;
        getCpOnboardingInvestorRoundIdResponse = given().log().all()
                .spec(getRoundIdRequestSpec)
                .pathParam("issuerId", issuerId)
                .pathParam("investorCpId", investorCpId)
                .pathParam("tokenId", tokenId)
                .when().get(CPBASEURL + "/api/v2/issuers/{issuerId}/investors/{investorCpId}/tokens?token-id={tokenId}")
                .then().assertThat().statusCode(200).extract().response().asString();

        JSONObject getCpOnboardingInvestorRoundIdResponseObject = new JSONObject(getCpOnboardingInvestorRoundIdResponse);
        System.out.println(getCpOnboardingInvestorRoundIdResponseObject);
        JSONObject roundObj = getCpOnboardingInvestorRoundIdResponseObject.getJSONObject("round");
        return String.valueOf(roundObj.getInt("id"));
    }

    public String getInvestmentPledgeAmount(String issuerId, String tokenId, String investorCpId, String roundId) {
        LoginAPI loginAPI = new LoginAPI();
        cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];

        RequestSpecification getRoundIdRequestSpec = new RequestSpecBuilder()
                .setBaseUri(CPBASEURL)
                .addHeader("accessToken", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();

        String getCpOnboardingInvestorRoundIdResponse = null;
        getCpOnboardingInvestorRoundIdResponse = given().log().all()
                .spec(getRoundIdRequestSpec)
                .pathParam("issuerId", issuerId)
                .pathParam("investorCpId", investorCpId)
                .pathParam("tokenId", tokenId)
                .pathParam("roundId", roundId)
                .when().get(CPBASEURL + "/api/v2/issuers/{issuerId}/investors/{investorCpId}/investment?token-id={tokenId}&round-id={roundId}")
                .then().assertThat().statusCode(200).extract().response().asString();

        JSONObject getCpOnboardingInvestorRoundIdResponseObject = new JSONObject(getCpOnboardingInvestorRoundIdResponse);
        JSONObject investmentRequestForDisplayObj = getCpOnboardingInvestorRoundIdResponseObject.getJSONObject("investmentRequestForDisplay");
        JSONObject pledgedObj = investmentRequestForDisplayObj.getJSONObject("pledged");
        return String.valueOf(pledgedObj.getInt("amount"));
    }

    public <T> T getInvestmentDetails(String issuerId, String tokenId, String investorCpId, String roundId) {
        return getInvestmentDetails(issuerId, tokenId, investorCpId, roundId, null);
    }

    public <T> T getInvestmentDetails(String issuerId, String tokenId, String investorCpId, String roundId, String jsonPath) {
        String url = String.format("issuers/%s/investors/%s/investment?token-id=%s&round-id=%s",
                issuerId,
                investorCpId,
                tokenId,
                roundId);
        String rawResult = getAPI(url, 200, true);

        return JsonPath.read(rawResult, Objects.requireNonNullElse(jsonPath, "*"));
    }

    public void performTransactions(List<String> investorsId, String issuerId, String tokenId) {

        LoginAPI loginAPI = new LoginAPI();
        cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];

        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri("https://cp." + env + ".securitize.io")
                .addHeader("accessToken", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();


        for (String investorId : investorsId) {
            String baseResp1 = null;
            baseResp1 = given().log().all()
                    .spec(request)
                    .when().get("https://cp." + env + ".securitize.io/api/v2/issuers/" + issuerId + "/investors/581499/tokens?token-id=" + tokenId)
                    .then().assertThat().statusCode(200).extract().response().asString();

            JSONObject jsonPath = new JSONObject(baseResp1);
            int roundId = jsonPath.getJSONObject("round").getInt("id");


            String baseResp2 = null;
            baseResp2 = given().log().all()
                    .spec(request)
                    .when().get("https://cp." + env + ".securitize.io/api/v2/issuers/" + issuerId + "/investors/" + investorId + "/investment?token-id=" + tokenId + "&round-id=" + roundId)
                    .then().assertThat().statusCode(200).extract().response().asString();

            System.out.println(baseResp2.toString());

            JSONObject jsonPath1 = new JSONObject(baseResp2);
            int investmentRequestId = jsonPath1.getJSONObject("investmentRequestForDisplay").getInt("id");
            int depositWalletId = 0;
            try {
                depositWalletId = jsonPath1.getJSONArray("depositWallets").getJSONObject(0).getInt("id");
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            String response2 = null;
            //Here we stablish a random amount for deposit between 1 - 10 this works as long Nav price is 1 usd
            int amount = new Random().nextInt(10 - 1 + 1) + 1;

            String body = new JSONObject()
                    .put("externalTransactionConfirmation", "")
                    .put("transactionTime", DateTimeUtils.currentDateTimeUTC())
                    .put("direction", "deposit")
                    .put("amount", amount)
                    .put("currencyId", 1)
                    .put("depositWalletId", depositWalletId)
                    .put("investmentRoundId", roundId)
                    .put("tokenId", tokenId)
                    .put("investmentRequestId", investmentRequestId).toString();

            response2 = given().log().all()
                    .spec(request)
                    .body(body)
                    .when().post("https://cp." + env + ".securitize.io/api/v2/issuers/" + issuerId + "/investors/" + investorId + "/deposit-transactions")
                    .then().assertThat().statusCode(201).extract().response().asString();

            System.out.println(response2);
        }
    }

    public void pachInvestment(FT_TestData testdata) throws Exception {
        LoginAPI loginAPI = new LoginAPI();
        cpLoginResponse = loginAPI.postLoginControlPanelReturnResponse();
        // LOGIN SESSION TOKEN
        String[] headerSetCookie = cpLoginResponse.getHeader("set-cookie").split(";");
        String bearerToken = headerSetCookie[0].split("=")[1];

        RequestSpecification getRoundIdRequestSpec = new RequestSpecBuilder()
                .setBaseUri(CPBASEURL)
                .addHeader("accessToken", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .build();
        // 1 GET CP respond from investors details
        String getCpOnboardingInvestorRoundIdResponse = null;
        getCpOnboardingInvestorRoundIdResponse = given().log().all()
                .spec(getRoundIdRequestSpec)
                .pathParam("issuerId", testdata.issuerId)
                .pathParam("investorCpId", testdata.cpInvestorId)
                .pathParam("tokenId", testdata.tokenId)
                .pathParam("roundId", testdata.roundId)
                .when().get(CPBASEURL + "/api/v2/issuers/{issuerId}/investors/{investorCpId}/investment?token-id={tokenId}&round-id={roundId}")
                .then().assertThat().statusCode(200).extract().response().asString();
        //generate the payload for PATCH
        String bodyForPatch = updateBodyToPatch(getCpOnboardingInvestorRoundIdResponse);
        // 2 PATCH:
        RequestSpecification patchInvestRequestSpec = new RequestSpecBuilder()
                .setBaseUri(CPBASEURL)
                .addHeader("accessToken", bearerToken)
                .addCookies(cpLoginResponse.getCookies())
                .setContentType(ContentType.JSON)
                .setBody(bodyForPatch)
                .build();
        given().log().all()
                .spec(patchInvestRequestSpec)
                .pathParam("issuerId", testdata.issuerId)
                .pathParam("investorCpId", testdata.cpInvestorId)
                .pathParam("roundId", testdata.roundId)
                .when().patch(CPBASEURL + "/api/v2/issuers/{issuerId}/investors/{investorCpId}/investment?round-id={roundId}")
                .then().assertThat().statusCode(200).extract().response().asString();

    }

    public String updateBodyToPatch(String bodyFromGet) {
        // take the response from GET call, update some data and use as Payload for PATCH call
        JSONObject jsonFromGetCall;
        jsonFromGetCall = new JSONObject(bodyFromGet);
        JSONObject investmentRound = (JSONObject) jsonFromGetCall.get("investmentRound");
        JSONObject investmentRequestForDisplay = (JSONObject) jsonFromGetCall.get("investmentRequestForDisplay");
        int id = investmentRequestForDisplay.getInt("id");
        JSONObject pledged = (JSONObject) investmentRequestForDisplay.get("pledged");
        JSONObject totalFunded = (JSONObject) investmentRequestForDisplay.get("totalFunded");
        String status = investmentRequestForDisplay.getString("status");
        String createdAt = investmentRequestForDisplay.getString("createdAt");
        String tokenId = investmentRound.getString("tokenId");
        String subscriptionAgreementSignedAt = investmentRequestForDisplay.getString("subscriptionAgreementSignedAt");
        //create Payload for PATCH call
        JSONObject patchString = jsonFromGetCall;
        patchString.putOpt("id", id);
        patchString.putOpt("pledged", pledged);
        patchString.putOpt("totalFunded", totalFunded);
        patchString.putOpt("status", status);
        patchString.putOpt("createdAt", createdAt);
        patchString.putOpt("tokenIssuanceId", null);
        patchString.putOpt("subscriptionAgreementStatus", "confirmed");
        patchString.putOpt("tokenId", tokenId);
        patchString.putOpt("subscriptionAgreementSignedAt", subscriptionAgreementSignedAt);
        return patchString.toString();

    }

    public String createAccrualDividendDistribution(String issuerId, String tokenId, int snapshotId) {
        infoAndShowMessageInBrowser("Run api call: createAccuralDividendDistribution");

        String body = new JSONObject()
                .put("name", "Accural Dividend test")
                .put("issuerId", issuerId)
                .put("tokenId", tokenId)
                .put("snapshotId", snapshotId)
                .put("distributionType", "accrued-dividend")
                .put("distributionModel", "total-amount")
                .put("amount", 1)
                .put("withholdTaxes", true)
                .put("startDate", DateTimeUtils.currentDateTimeUTC())
                .put("endDate", DateTimeUtils.currentDateTimeUTCWithDelta(2))
                .toString();

        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        String url = String.format("https://distributions-api.internal.%s.securitize.io/api/v2/distributions", environment);
        String responseAsString = postAPI(url, body, 201, true);
        return new JSONObject(responseAsString).getString("id");
    }

    public int createSnapshot(String issuerId, String tokenId, String snapshotName) {
        infoAndShowMessageInBrowser("Run api call: createSnapshot");

        String body = new JSONObject()
                .put("name", snapshotName)
                .put("date", DateTimeUtils.currentDateTimeUTC()).toString();

        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        String url = String.format("https://cp.%s.securitize.io/isr/api/v1/issuers/%s/tokens/%s/snapshots", environment, issuerId, tokenId);
        String responseAsString = postAPI(url, body, 201, true);
        JSONObject response = new JSONObject(responseAsString);
        return response.getInt("id");
    }

    public int getDistributionInvestorsCount(String distributionId) {
        infoAndShowMessageInBrowser("Run api call: getDistributionInvestors");

        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        String url = String.format("https://cp.%s.securitize.io/distributions/api/distributions/%s/investors?limit=100", environment, distributionId);
        String responseAsString = getAPI(url, 200, true);
        JSONObject response = new JSONObject(responseAsString);
        JSONArray arrayOfInvestors = response.getJSONArray("data");
        return arrayOfInvestors.length();
    }

    private JSONArray searchInvestorByName(String issuerName, String investorName) {
        infoAndShowMessageInBrowser("Run api call: searchInvestorByName");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("q", investorName);

        String result = getAPI("issuers/" + issuerId + "/investors", queryParams, 200, true);

        // convert result to JSON and parse for user id
        JSONObject investors = new JSONObject(result);
        return investors.getJSONArray("data");
    }

    public String getInvestorEmailFromInvestorName(String issuerName, String investorName) {
        JSONArray investorsData = searchInvestorByName(issuerName, investorName);

        if (investorsData.length() != 1) {
            errorAndStop("There should be exactly one investor using provided name of: " + investorName + ". Current number: " + investorsData.length(), true);
        }

        String investorEmail = investorsData.getJSONObject(0).getString("email");
        infoAndShowMessageInBrowser("investor email is: " + investorEmail);
        return investorEmail;
    }

    public int waitForTransactionToWalletToBeCreated(String issuerId, String tokenId, String walletAddress) {
        JSONObject transaction = Browser.waitForExpressionNotNull(q -> {
                    JSONObject pendingTransactions = getIssuerTransactions(issuerId, tokenId, "pending");
                    JSONArray jsonArray = pendingTransactions.getJSONArray("data");

                    List<JSONObject> filterableJsonArray = IntStream
                            .range(0, jsonArray.length())
                            .mapToObj(jsonArray::getJSONObject)
                            .collect(Collectors.toList());

                    for (JSONObject signature : filterableJsonArray) {
                        boolean isBulkIssuance = signature.getString("type").contains("BulkRegister");
                        if (isBulkIssuance) {
                            JSONArray holders = signature
                                    .getJSONObject("transaction")
                                    .getJSONObject("txCreationBody")
                                    .getJSONArray("holders");
                            List<JSONObject> filterableHoldersJsonArray = IntStream
                                    .range(0, holders.length())
                                    .mapToObj(holders::getJSONObject)
                                    .collect(Collectors.toList());
                            long holdersCount = filterableHoldersJsonArray.stream().filter(x -> x.getString("wallet").equals(walletAddress)).count();
                            if (holdersCount > 0) {
                                return signature;
                            }
                        } else { // not a bulk issuance
                            long transactionCount = filterableJsonArray.stream().filter(x -> x.getString("description").toLowerCase().contains(walletAddress)).count();
                            if (transactionCount > 0) {
                                return signature;
                            }
                        }
                    }
                    return null;
                },
                null,
                "wait for transaction to be created to wallet " + walletAddress,
                180,
                5000);
        assert transaction != null; // to avoid getting IDE warnings
        return transaction.getInt("id");
    }

    public String getContractStatus(String issuerId, String tokenId) {
        infoAndShowMessageInBrowser("Run api call: getContractStatus");
        String url = String.format("issuers/%s/tokens/%s/token-status",
                issuerId,
                tokenId);
        String response = getAPI(url, 200, true);

        return new JSONObject(response).getString("status");
    }

    public void performHoldTrading(String issuerName, String tokenId, boolean isFreeze) {
        infoAndShowMessageInBrowser("performHoldTrading");
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String url = String.format("https://cp.%s.securitize.io/isr/api/v1/issuers/%s/tokens/%s/transfer-agent/procedure/hold-trading",
                environment,
                issuerId,
                tokenId);

        JSONObject body = new JSONObject()
                .put("documentId", "test")
                .put("isPause", isFreeze)
                .put("reason", "performHoldTrading");

        String response = postAPI(url, body.toString(), 201, true);
        int transactionId = new JSONArray(response).getJSONObject(0).getInt("signatureTransactionId");
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        signTransaction(BlockchainType.ETHEREUM, issuerName, issuedTokenName, transactionId + "");

        String expectedContractStatus;
        if (isFreeze) {
            expectedContractStatus = "on-hold";
        } else {
            expectedContractStatus = "operational";
        }
        Browser.waitForExpressionToEqual(q -> getContractStatus(issuerId, tokenId).equals(expectedContractStatus),
                null,
                true,
                "wait for contact status to become " + expectedContractStatus,
                30,
                1000);
        infoAndShowMessageInBrowser("Contract status changed to " + expectedContractStatus);
    }

    public String getTokenAddress(String issuerName, String tokenId) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String resultAsString = getAPI("issuers/" + issuerId + "/deployments/" + tokenId, null, 200, true);
        return new JSONObject(resultAsString).getString("tokenAddress");
    }
}