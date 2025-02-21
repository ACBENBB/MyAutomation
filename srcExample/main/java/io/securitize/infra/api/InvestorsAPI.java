package io.securitize.infra.api;

import com.jayway.jsonpath.JsonPath;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.config.*;
import io.securitize.infra.dataGenerator.InvestorDataObject;
import io.securitize.infra.enums.CurrencyIds;
import io.securitize.infra.exceptions.IssuanceNotSuccessTimeoutException;
import io.securitize.infra.exceptions.KycStatusNotUpdateTimeoutException;
import io.securitize.infra.exceptions.UserIdNotReturnTimeoutException;
import io.securitize.infra.exceptions.WalletNotReadyTimeoutException;
import io.securitize.infra.utils.*;
import io.securitize.infra.wallets.EthereumWallet;
import io.securitize.infra.webdriver.Browser;
import io.securitize.tests.InvestorDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.*;

public class InvestorsAPI extends AbstractAPI {

    public String postDepositTransactions(String issuerName, String investorId, CurrencyIds currencyId, float amount, String tokenId) {
        infoAndShowMessageInBrowser("Run api call: postDepositTransactions");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        int investmentRoundId = getActiveInvestmentRoundId(issuerName, tokenId);
        String response = getAPI("issuers/" + issuerId + "/investors/" + investorId + "/investment?round-id=" + investmentRoundId + "&token-id=" + tokenId, 200, true);
        int requestId = JsonPath.read(response, "investmentRequestForDisplay.id");
        int walletId = JsonPath.read(response, "depositWallets[0].id");

        String body = new JSONObject()
                .put("currencyId", currencyId.getId())
                .put("direction", "deposit")
                .put("amount", amount)
                .put("externalTransactionConfirmation", "1")
                .put("transactionTime", DateTimeUtils.currentDateTimeUTC())
                .put("tokenId", tokenId)
                .put("investmentRequestId", requestId)
                .put("depositWalletId", walletId)
                .put("investmentRoundId", investmentRoundId).toString();
//                .put("mainCurrencyWorth", 0).toString();

        return postAPI("issuers/" + issuerId + "/investors/" + investorId + "/deposit-transactions", body, 201, true);
    }

    public int getActiveInvestmentRoundId(String issuerName, String tokenId) {
        IssuersAPI issuersAPI = new IssuersAPI();
        String info = issuersAPI.getIssuerInfo(issuerName);

        // convert to json for easier search
        JSONObject infoAsJson = new JSONObject(info);

        // find later active round
        ArrayList<Integer> activeRounds = new ArrayList<>();
        JSONArray rounds = infoAsJson.getJSONArray("rounds");
        for (Object currentRoundAsObject : rounds) {
            JSONObject currentRound = ((JSONObject) currentRoundAsObject);
            if (currentRound.getString("status").equals("active") && currentRound.getString("tokenId").equals(tokenId)) {
                activeRounds.add(currentRound.getInt("id"));
            }
        }

        if (activeRounds.size() != 1) {
            throw new RuntimeException("There should be exactly one active round! Current: " + activeRounds.size());
        }

        return activeRounds.get(0);
    }

    public String getTokenIdByName(String issuerName, String tokenName) {
        IssuersAPI issuersAPI = new IssuersAPI();
        String info = issuersAPI.getIssuerInfo(issuerName);

        // convert to json for easier search
        JSONObject infoAsJson = new JSONObject(info);

        // find token id by provided name
        JSONArray tokens = infoAsJson.getJSONArray("tokens");
        for (Object currentTokenAsObject : tokens) {
            JSONObject currentToken = ((JSONObject) currentTokenAsObject);
            if (currentToken.getString("name").equals(tokenName)) {
                return currentToken.getString("id");
            }
        }

        throw new RuntimeException("Can't find tokenId by given Token name of: " + tokenName);
    }

    public String getTokenTicker(String issuerName, String tokenName) {
        IssuersAPI issuersAPI = new IssuersAPI();
        String info = issuersAPI.getIssuerInfo(issuerName);

        // convert to json for easier search
        JSONObject infoAsJson = new JSONObject(info);

        // find token id by provided name
        JSONArray tokens = infoAsJson.getJSONArray("tokens");
        for (Object currentTokenAsObject : tokens) {
            JSONObject currentToken = ((JSONObject) currentTokenAsObject);
            if (currentToken.getString("name").equals(tokenName)) {
                return currentToken.getString("symbol");
            }
        }

        throw new RuntimeException("Can't find tokenId by given Token name of: " + tokenName);
    }

    public boolean isInvestorExistsByEmail(String issuerName, String investorEmail) {
        return searchInvestorByEmail(issuerName, investorEmail).length() > 0;
    }

    public boolean isInvestorListExistsByEmail(List<String> investorEmails) throws RuntimeException {
        //TO BE IMPLEMENTED
        return false;
    }

    private JSONArray searchInvestorByEmail(String issuerName, String investorEmail) {
        infoAndShowMessageInBrowser("Run api call: searchInvestorByEmail");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("email", investorEmail);

        String result = getAPI("issuers/" + issuerId + "/investors", queryParams, 200, true);

        // convert result to JSON and parse for user id
        JSONObject investors = new JSONObject(result);
        return investors.getJSONArray("data");
    }

    public String getInvestorIdFromInvestorEmail(String issuerName, String investorEmail) {
        JSONArray investorsData = searchInvestorByEmail(issuerName, investorEmail);

        if (investorsData.length() != 1) {
            throw new RuntimeException("There should be exactly one investor using provided email of: " + investorEmail + ". Current number: " + investorsData.length());
        }

        int investorId = investorsData.getJSONObject(0).getInt("id");
        infoAndShowMessageInBrowser("investor id is: " + investorId);
        return String.valueOf(investorId);
    }

    public void addWalletToInvestor(String issuerName, String investorId, String walletName, String walletAddress, String tokenId) {
        infoAndShowMessageInBrowser("Run api call: addWalletToInvestor");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String body = new JSONObject()
                .put("name", walletName)
                .put("address", walletAddress)
                .put("actionMadeBy", "operator")
                .put("tokenId", tokenId).toString();

        postAPI("issuers/" + issuerId + "/investors/" + investorId + "/token-wallets", body, 200, true);
    }

    public JSONObject getInvestorTokensDetails(String issuerName, String investorId, String tokenId) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("token-id", tokenId);

        String resultAsString = getAPI("issuers/" + issuerId + "/investors/" + investorId + "/tokens", queryParams, 200, true);
        return new JSONObject(resultAsString);
    }

    public JSONObject getWalletDetails(String issuerName, String investorId, String walletName, String tokenId) {
        JSONObject resultAsJSON = getInvestorTokensDetails(issuerName, investorId, tokenId);

        JSONArray wallets = resultAsJSON.getJSONArray("wallets");
        for (Object currentWalletAsObject : wallets) {
            JSONObject currentWallet = (JSONObject) currentWalletAsObject;
            if (currentWallet.getString("name").toLowerCase().trim().equals(walletName.toLowerCase().trim())) {
                return currentWallet;
            }
        }

        errorAndStop(String.format("Unable to find wallet named %s in response json of wallets", walletName.toLowerCase()), false);
        return null;
    }

    private JSONObject getInvestorIssuanceDetails(String issuerName, String investorId, int issuenceId, String tokenId) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("token-id", tokenId);

        String resultAsString = getAPI("issuers/" + issuerId + "/investors/" + investorId + "/tokens", queryParams, 200, true);
        JSONObject resultAsJSON = new JSONObject(resultAsString);

        JSONArray issuances = resultAsJSON.getJSONArray("issuances");
        for (Object currentIssuanceAsObject : issuances) {
            JSONObject currentIssuance = (JSONObject) currentIssuanceAsObject;
            if (currentIssuance.getInt("id") == issuenceId) {
                return currentIssuance;
            }
        }

        return null;
    }

    public JSONObject getTokensAndWallets(String issuerName, String userId, String tokenId) {
        infoAndShowMessageInBrowser("Run api call: getTokensAndWallets");

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("token-id", tokenId);

        String responseAsString = getAPI("/issuers/" + issuerId + "/investors/" + userId + "/tokens", queryParams, 200, true);
        return new JSONObject(responseAsString);
    }

    public String getSecuritizeIdByEmail(String email) {
        infoAndShowMessageInBrowser("Run api call: getSecuritizeIdByEmail");

        JSONObject body = new JSONObject()
                .put("page", 0)
                .put("limit", 50)
                .put("order-direction", "desc")
                .put("order-field", "created-at")
                .put("q", email);

        String responseAsString = postAPI("/securitize-id/investors", body.toString(), 201, true);
        JSONObject responseAsJsonObject = new JSONObject(responseAsString);
        JSONArray listOfInvestors = responseAsJsonObject.getJSONArray("data");
        for (int i = 0; i < listOfInvestors.length(); i++) {
            JSONObject currentInvestor = listOfInvestors.getJSONObject(i);
            return currentInvestor.getString("securitizeIdProfileId");
        }

        errorAndStop("Securitize investor id not found in latest 50 investors for email " + email, false);
        return null;
    }

    public String getUniqueSecuritizeIdByEmail(String email) {
        infoAndShowMessageInBrowser("Run api call: getUniqueSecuritizeIdByEmail");
        JSONObject body = new JSONObject()
                .put("limit", 1)
                .put("q", email);
        String responseAsString = postAPI("/securitize-id/investors", body.toString(), 201, true);
        JSONObject responseAsJsonObject = new JSONObject(responseAsString);
        if (responseAsJsonObject.get("totalItems") != null) {
            return responseAsJsonObject.getJSONArray("data").getJSONObject(0).getString("securitizeIdProfileId");
        } else {
            errorAndStop("Securitize investor id not found for email " + email, false);
            return null;
        }
    }

    public boolean isEmailAlreadyInUse(String email) {
        infoAndShowMessageInBrowser("Run api call: isEmailAlreadyInUse");
        JSONObject body = new JSONObject()
                .put("limit", 1)
                .put("q", email);
        String responseAsString = postAPI("/securitize-id/investors", body.toString(), 201, true);
        JSONObject responseAsJsonObject = new JSONObject(responseAsString);
        return !responseAsJsonObject.get("totalItems").toString().equals("0");
    }

    public String getSecuritizeIdByName(String name) {
        infoAndShowMessageInBrowser("Run api call: getSecuritizeIdByName");

        JSONObject body = new JSONObject()
                .put("page", 0)
                .put("limit", 50)
                .put("order-direction", "desc")
                .put("order-field", "created-at");

        String responseAsString = postAPI("/securitize-id/investors", body.toString(), 201, true);
        JSONObject responseAsJsonObject = new JSONObject(responseAsString);
        JSONArray listOfInvestors = responseAsJsonObject.getJSONArray("data");
        for (int i = 0; i < listOfInvestors.length(); i++) {
            JSONObject currentInvestor = listOfInvestors.getJSONObject(i);
            if (currentInvestor.getString("investorName").contains(name)) {
                return currentInvestor.getString("securitizeIdProfileId");
            }
        }

        errorAndStop("Securitize investor id not found in latest 50 investors for Name " + name, false);
        return null;
    }

    public void setKYCToPassed(String securitizeIdProfileId, String kycStatus) {
        infoAndShowMessageInBrowser("Run api call: setKYCToPassed");
        String newSubStatus = "none";
        JSONObject body = new JSONObject()
                .put("errors", new JSONArray())
                .put("newStatus", "verified")
                .put("newSubStatus", newSubStatus);

        if (kycStatus != null) {


            String newStatus = kycStatus;


            if (kycStatus.equals("verified-expired")) {
                newStatus = "verified";
                newSubStatus= "expired";

            } else if (kycStatus.equals("rejected-blocked")) {
                newStatus = "rejected";
                newSubStatus= "blocked";

            } else if (kycStatus.equals("verified-docs-expired")) {
                newStatus = "verified";
                newSubStatus= "documents-expired";
            }

            body.put("newStatus", newStatus)
                    .put("newSubStatus",newSubStatus );

        }

        postAPI("/securitize-id/investors/" + securitizeIdProfileId + "/update-verification-status", body.toString(), 201, true);
    }

    public void setKYBToStatusForBlackrockInvestor(String securitizeIdProfileId, String kybStatus, String verificationLevel) {
        infoAndShowMessageInBrowser("Run api call: setKYBToStatusForBlackrockInvestor");

        JSONObject body = new JSONObject()
                .put("errors", new JSONArray())
                .put("newStatus", kybStatus)
                .put("newSubStatus", "none")
                .put("newVerificationLevel", verificationLevel);

        postAPI("/securitize-id/investors/" + securitizeIdProfileId + "/update-verification-status", body.toString(), 201, true);
    }

    public void setVerificationStatus(String securitizeIdProfileId, String... kycStatus) {
        infoAndShowMessageInBrowser("Run api call: setVerificationStatus");

        JSONObject body = new JSONObject()
                .put("errors", new JSONArray())
                .put("newStatus", "verified")
                .put("newSubStatus", "none");

        if (kycStatus != null) {
            body.put("newStatus", kycStatus[0]);
        }
        if (kycStatus.length == 2) {
            body.put("newSubStatus", kycStatus[1]);
        }

        postAPI("/securitize-id/investors/" + securitizeIdProfileId + "/update-verification-status", body.toString(), 201, true);
    }

    public void setKYCToReject(String securitizeIdProfileId) {
        infoAndShowMessageInBrowser("Run api call: setKYCToReject");

        JSONObject body = new JSONObject()
                .put("newStatus", "rejected")
                .put("errors", new JSONArray());

        postAPI("/securitize-id/investors/" + securitizeIdProfileId + "/update-verification-status", body.toString(), 201, true);
    }

    @SuppressWarnings("unused")
    public void setWalletStatusToSyncing(String issuerName, String investorId, String walletName, String tokenId) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        // get initial wallet status
        JSONObject walletDetails = getWalletDetails(issuerName, investorId, walletName, tokenId);
        if (walletDetails == null) {
            throw new RuntimeException("Requested wallet with name " + walletName + " not found!");
        }

        if (walletDetails.getString("status").equals("syncing")) {
            info("Requested wallet with name " + walletName + " already has a status of syncing... Nothing to do here...");
            return;
        }

        // run call to set wallet as syncing
        String body = new JSONObject()
                .put("name", walletName)
                .put("address", walletDetails.getString("address"))
                .put("status", "syncing").toString();

        patchAPI("issuers/" + issuerId + "/investors/" + investorId + "/token-wallets/" + walletDetails.getInt("id"), body, 200, true);

        // make sure wallet status changes to syncing
        walletDetails = getWalletDetails(issuerName, investorId, walletName, tokenId);
        if (walletDetails != null) {
            if (walletDetails.getString("status").toLowerCase().trim().equals("syncing")) {
                info("Requested wallet with name " + walletName + " status successfully set to syncing");
            } else {
                throw new RuntimeException("Requested wallet with name " + walletName + " status isn't set to 'syncing' even after changing it!");
            }
        } else {
            throw new RuntimeException("Unable to extract wallet details (returned null)");
        }
    }

    public void waitForWalletPropertyToEqual(String issuerName, String investorId, String tokenId, String propertyName, String expectedValue, int timeoutSeconds, int checkIntervalMilliseconds) {
        Function<String, Boolean> internalWaitForWalletPropertyToEqual = t -> {
            try {
                infoAndShowMessageInBrowser("Waiting for wallet property of: " + propertyName + " to equal: " + expectedValue);
                info("Reading wallet details...");
                JSONObject walletInfo = getInvestorTokensDetails(issuerName, investorId, tokenId);
                if (!walletInfo.has(propertyName)) {
                    info("wallet doesn't have requested property of: " + propertyName);
                    return false;
                }

                Object propertyValue = walletInfo.get(propertyName);
                if (propertyValue == null) {
                    info("wallet property of: " + propertyName + " has null value");
                    return false;
                }

                info("Found value of: " + propertyValue);
                return propertyValue.toString().equalsIgnoreCase(expectedValue);
            } catch (Exception e) {
                info("An error occur trying to get wallet property of: " + propertyName + ". Details: " + e);
                return false;
            }
        };
        String description = "Wallet property of: " + propertyName + " to become: " + expectedValue;
        Browser.waitForExpressionToEqual(internalWaitForWalletPropertyToEqual, null, true, description, timeoutSeconds, checkIntervalMilliseconds);
    }

    public void waitForUserIdReturn(String investorId, int timeoutSeconds, int checkIntervalMilliseconds) {
        Function<String, Boolean> internalWaitForUserIdReturn = t -> {
            try {
                String investorDetailsCurrentUserId;
                info("Reading response details...");
                JSONObject investorDetails = getInvestorIssuersDetailsBySecuritizeIdProfileId(investorId);
                if (investorDetails != null) {
                    investorDetailsCurrentUserId = investorDetails.getJSONArray("issuersData")
                            .getJSONObject(0)
                            .get("userId")
                            .toString().toLowerCase().trim();
                } else {
                    throw new RuntimeException("Can't find userId details (returned null) in the response");
                }
                infoAndShowMessageInBrowser("Waiting for userId to return. Current userId: " + investorDetailsCurrentUserId);
                return (!(investorDetailsCurrentUserId.isEmpty()));
            } catch (Exception e) {
                return false;
            }
        };
        String description = "UserId to become return";
        UserIdNotReturnTimeoutException userIdNotReturnTimeoutException = new UserIdNotReturnTimeoutException("UserId was not marked as return even after " + timeoutSeconds + " seconds.");
        Browser.waitForExpressionToEqual(internalWaitForUserIdReturn, null, true, description, timeoutSeconds, checkIntervalMilliseconds, userIdNotReturnTimeoutException);
    }

    public void waitForWalletReady(String issuerName, String investorId, String walletName, String tokenId, int timeoutSeconds, int checkIntervalMilliseconds) {
        Function<String, Boolean> internalWaitForWalletReady = t -> {
            try {
                info("Reading wallet details...");
                JSONObject walletDetails = getWalletDetails(issuerName, investorId, walletName, tokenId);
                String walletCurrentStatus;
                if (walletDetails != null) {
                    walletCurrentStatus = walletDetails.getString("status").toLowerCase().trim();
                } else {
                    throw new RuntimeException("Unable to extract wallet details (returned null)");
                }
                infoAndShowMessageInBrowser("Waiting for wallet to become ready. Current status: " + walletCurrentStatus);

                return (walletCurrentStatus.equals("ready"));
            } catch (Exception e) {
                return false;
            }
        };
        String description = "Wallet to become ready";
        WalletNotReadyTimeoutException walletNotReadyTimeoutException = new WalletNotReadyTimeoutException("Wallet was not marked as ready even after " + timeoutSeconds + " seconds.");
        Browser.waitForExpressionToEqual(internalWaitForWalletReady, null, true, description, timeoutSeconds, checkIntervalMilliseconds, walletNotReadyTimeoutException);
    }

    public void waitForTransactionSuccess(String issuerName, String investorId, int issuanceId, String tokenId, int timeoutSeconds, int checkIntervalMilliseconds) {
        Function<String, Boolean> internalWaitForTransactionSuccess = t -> {
            try {
                info("Reading issuance details...");
                JSONObject issuanceDetails = getInvestorIssuanceDetails(issuerName, investorId, issuanceId, tokenId);
                String issuanceCurrentStatus;
                if (issuanceDetails != null) {
                    issuanceCurrentStatus = issuanceDetails.getString("status").toLowerCase().trim();
                } else {
                    throw new RuntimeException("Unable to extract issuance details due to null return");
                }
                infoAndShowMessageInBrowser("Waiting for issuance to become success. Current status: " + issuanceCurrentStatus);

                return (issuanceCurrentStatus.equals("success"));
            } catch (Exception e) {
                return false;
            }
        };
        String description = "Transaction to become success";
        IssuanceNotSuccessTimeoutException issuanceNotSuccessTimeoutException = new IssuanceNotSuccessTimeoutException("Issuance was not marked as success even after " + timeoutSeconds + " seconds.");
        Browser.waitForExpressionToEqual(internalWaitForTransactionSuccess, null, true, description, timeoutSeconds, checkIntervalMilliseconds, issuanceNotSuccessTimeoutException);
    }

    public void waitForKycStatusUpdateToVerified(String securitizeIdProfileId, int timeoutSeconds, int checkIntervalMilliseconds, int... index) {
        Function<String, Boolean> internalwaitForKycStatusUpdateToVerified = t -> {
            try {
                info("Reading kyc status...");
                JSONObject secIdKycResult = getSecIdKycStatus(securitizeIdProfileId, index);
                String kycCurrentStatus;
                if (secIdKycResult != null) {
                    kycCurrentStatus = secIdKycResult.getString("status").trim().toLowerCase();
                } else {
                    throw new RuntimeException("Unable to extract kyc status due to null return");
                }
                infoAndShowMessageInBrowser("Waiting for kyc status update to 'verified'. Current status: " + kycCurrentStatus);

                return (kycCurrentStatus.equals("verified"));
            } catch (Exception e) {
                return false;
            }
        };
        String description = "Status update to 'verified'";
        KycStatusNotUpdateTimeoutException kycStatusNotUpdateTimeoutException = new KycStatusNotUpdateTimeoutException("Status was not update to 'verified' even after " + timeoutSeconds + " seconds.");
        Browser.waitForExpressionToEqual(internalwaitForKycStatusUpdateToVerified, null, true, description, timeoutSeconds, checkIntervalMilliseconds, kycStatusNotUpdateTimeoutException);
    }

    public JSONObject getSecIdKycStatus(String securitizeIdProfileId, int... index) {
        infoAndShowMessageInBrowser("Run api call: getInvestorSecIdKycStatus");
        String result = getAPI("securitize-id/investors/" + securitizeIdProfileId + "/kyc-history", 200, true);
        int currentIndex = index.length > 0 ? index[0] : 0;
        return new JSONArray(result).getJSONObject(currentIndex);
    }

    public String getInvestorDetails(String issuerName, String investorId, String propertyName) {
        infoAndShowMessageInBrowser("Run api call: getInvestorDetails for property " + propertyName);

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String result = getAPI("issuers/" + issuerId + "/investors/" + investorId + "/details", 200, true);
        JSONObject detailsObject = new JSONObject(result);
        return detailsObject.getString(propertyName);
    }

    public JSONObject getInvestorIssuersDetailsBySecuritizeIdProfileId(String securitizeIdProfileId) {
        infoAndShowMessageInBrowser("Run api call: getInvestorIssuersDetailsBySecuritizeIdProfileId");
        String responseAsString = getAPI("securitize-id/investors/" + securitizeIdProfileId, 200, true);
        JSONObject responseAsJSON = new JSONObject(responseAsString);
        return responseAsJSON;
    }


    public String getIssuersUserIdBySecuritizeIdProfileId(String securitizeIdProfileId, int timeoutSeconds, int checkIntervalMilliseconds) {
        waitForUserIdReturn(securitizeIdProfileId, timeoutSeconds, checkIntervalMilliseconds);
        infoAndShowMessageInBrowser("Run api call: getIssuersUserIdBySecuritizeIdProfileId");
        String responseAsString = getAPI("securitize-id/investors/" + securitizeIdProfileId, 200, true);
        JSONObject responseAsObject = new JSONObject(responseAsString);
        int userId = (int) responseAsObject.getJSONArray("issuersData")
                .getJSONObject(0)
                .get("userId");
        return Integer.toString(userId);
    }


    public String createNewInvestor(InvestorDetails investorDetails) {
        JSONObject body = new JSONObject()
                .put("investorType", investorDetails.getInvestorType().toLowerCase())
                .put("firstName", investorDetails.getFirstName())
                .put("middleName", investorDetails.getMiddleName())
                .put("lastName", investorDetails.getLastName())
                .put("email", investorDetails.getEmail())
                .put("countryCode", investorDetails.getCountryCode())
                .put("state", investorDetails.getStateCode());

        String result = postAPI("/securitize-id/investors/add", body.toString(), 201, true);
        JSONObject resultAsJson = new JSONObject(result);
        return resultAsJson.getJSONObject("data").getString("investorId");
    }

    public String createNewInvestorByIssuer(InvestorDetails investorDetails, String issuerName) {
        JSONObject body = new JSONObject()
                .put("investorType", investorDetails.getInvestorType().toLowerCase())
                .put("firstName", investorDetails.getFirstName())
                .put("middleName", investorDetails.getMiddleName())
                .put("lastName", investorDetails.getLastName())
                .put("email", investorDetails.getEmail())
                .put("countryCode", investorDetails.getCountryCode())
                .put("state", investorDetails.getStateCode());

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String result = postAPI("/issuers/" + issuerId + "/investors", body.toString(), 200, true);
        JSONObject resultAsJson = new JSONObject(result);
        int investorId = resultAsJson.getInt("id");
        return String.valueOf(investorId);
    }

    public void addInvestorDetails(String investorId, InvestorDetails investorDetails) {
        JSONObject body = new JSONObject()
                .put("accreditationStatus", "none")
                .put("comment", investorDetails.getComment())
                .put("countryCode", investorDetails.getCountryCode())
                .put("email", investorDetails.getEmail())
                .put("legalSigner", investorDetails.getLegalSigner())
                .put("firstName", investorDetails.getFirstName())
                .put("fullName", investorDetails.getFirstName() + " " + investorDetails.getMiddleName() + " " + investorDetails.getLastName());
        if (investorDetails.getInvestorType().equalsIgnoreCase("entity")) {
            body.put("entityDba", investorDetails.getEntityDba())
                    .put("entityType", investorDetails.getEntityType())
                    .put("entityIdNumber", investorDetails.getEntityIdNumber());
        }
        body.put("investorId", investorId)
                .put("investorType", investorDetails.getInvestorType().toLowerCase())
                .put("issuersData", new JSONArray())
                .put("kycComment", "")
                .put("language", "EN")
                .put("lastName", investorDetails.getLastName())
                .put("middleName", investorDetails.getMiddleName())
                .put("state", investorDetails.getStateCode())
                .put("street", investorDetails.getStreetName())
                .put("taxCountryCode1", investorDetails.getCountryOfTaxCode())
                .put("taxId1", investorDetails.getTaxId())
                .put("addressAdditionalInfo", investorDetails.getStreetAdditionalInfo())
                .put("birthDay", investorDetails.getBirthDate())
                .put("city", investorDetails.getCity())
                .put("phoneNumber", investorDetails.getPhoneNumber())
                .put("postalCode", investorDetails.getPostalCode())
                .put("countryOfBirth", investorDetails.getCountryOfBirthCode())
                .put("identityDocumentNumber", investorDetails.getDocumentNumber())
                .put("gender", investorDetails.getGender().toLowerCase())
                .put("cityOfBirth", "Tel Aviv")
                .put("phonePrefix", "+1")
                .put("tfaEnabled", false)
                .put("verificationStatus", "none");

        putAPI("/securitize-id/investors/" + investorId, body.toString(), 200, true);
    }

    public void addDetailsInvestorByIssuer(String investorId, InvestorDetails investorDetails, String issuerName) {

        String modifiedBirthDate;
        modifiedBirthDate = DateTimeUtils.convertDateFormat(investorDetails.getBirthDate(), "MM/dd/YYYY", "YYYY-MM-dd");

        JSONObject body = new JSONObject()
                .put("accreditationStatus", "none")
                .put("operatorComments", investorDetails.getComment())
                .put("countryCode", investorDetails.getCountryCode())
                .put("email", investorDetails.getEmail())
                .put("firstName", investorDetails.getFirstName())
                .put("fullName", investorDetails.getFirstName() + " " + investorDetails.getMiddleName() + " " + investorDetails.getLastName())
                .put("investorId", investorId)
                .put("investorType", investorDetails.getInvestorType().toLowerCase())
                .put("issuersData", new JSONArray())
                .put("kycComment", "")
                .put("language", "EN")
                .put("lastName", investorDetails.getLastName())
                .put("middleName", investorDetails.getMiddleName())
                .put("state", investorDetails.getStateCode())
                .put("address1", investorDetails.getStreetName())
                .put("taxCountryCode", investorDetails.getCountryOfTaxCode())
                .put("taxId", investorDetails.getTaxId())
                .put("additionalInfo", investorDetails.getStreetAdditionalInfo())
                .put("birthdate", modifiedBirthDate)
                .put("city", investorDetails.getCity())
                .put("phoneNumber", investorDetails.getPhoneNumber())
                .put("zipCode", investorDetails.getPostalCode())
                .put("countryOfBirth", investorDetails.getCountryOfBirthCode())
                .put("identityDocumentNumber", investorDetails.getDocumentNumber())
                .put("gender", investorDetails.getGender().toLowerCase())
                .put("cityOfBirth", "Tel Aviv")
                .put("phonePrefix", "+1")
                .put("is2FaEnabled", false)
                .put("verificationStatus", "none");

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        patchAPI("/issuers/" + issuerId + "/investors/" + investorId + "/details", body.toString(), 200, true);
    }

    public void setKYCStatus(String investorId, String issuerName, String kycStatus) {
        JSONObject body = new JSONObject()
                .put("kycProvider", "internal")
                .put("kycStatus", kycStatus);

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        patchAPI("/issuers/" + issuerId + "/investors/" + investorId + "/kyc", body.toString(), 200, true);
    }

    public void setAccreditationStatus(String investorId, String issuerName, String accreditationStatus) {
        JSONObject body = new JSONObject()
                .put("accreditedStatus", accreditationStatus);

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        patchAPI("/issuers/" + issuerId + "/investors/" + investorId + "/kyc", body.toString(), 200, true);
    }

    public void setQualificationStatus(String investorId, String issuerName, String qualificationStatus) {
        infoAndShowMessageInBrowser("Run api call: setQualificationStatus");
        JSONObject body = new JSONObject()
                .put("userTokenQualificationStatus", qualificationStatus);

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        String tokenId = getTokenIdByName(issuerName, issuedTokenName);
        patchAPI("/issuers/" + issuerId + "/investors/" + investorId + "/kyc?tokenId=" + tokenId, body.toString(), 200, true);
    }

    public void setQualificationStatus(String investorId, String issuerName, String tokenId, String qualificationStatus, String dummy) {
        infoAndShowMessageInBrowser("Run api call: setQualificationStatus");
        JSONObject body = new JSONObject()
                .put("userTokenQualificationStatus", qualificationStatus);

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String issuedTokenName = Users.getIssuerDetails(issuerName, IssuerDetails.issuedTokenName);
        patchAPI("/issuers/" + issuerId + "/investors/" + investorId + "/kyc?tokenId=" + tokenId, body.toString(), 200, true);
    }

    public void setQualificationStatusForToken(String investorId, String issuerName, String tokenId, String qualificationStatus) {
        infoAndShowMessageInBrowser("Run api call: setQualificationStatus");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        JSONObject body = new JSONObject()
                .put("userTokenQualificationStatus", qualificationStatus);
        patchAPI("/issuers/" + issuerId + "/investors/" + investorId + "/kyc?tokenId=" + tokenId, body.toString(), 200, true);
    }
    public void setQualificationStatus(String issuerId, String cpInvestorId, String tokenId, String qualificationStatus) {
        infoAndShowMessageInBrowser("Run api call: setQualificationStatus");
        JSONObject body = new JSONObject()
                .put("userTokenQualificationStatus", qualificationStatus);
        patchAPI("/issuers/" + issuerId + "/investors/" + cpInvestorId + "/kyc?tokenId=" + tokenId, body.toString(), 200, true);
    }

    public JSONObject getInvestorFullKycStatus(String issuerId, String investorId, String tokenId) {
        infoAndShowMessageInBrowser("Run api call: getInvestorFullKycStatus");
        String result = getAPI("/issuers/" + issuerId + "/investors/" + investorId + "/kyc?tokenId=" + tokenId, 200, true);
        return new JSONObject(result);
    }

    public String addDocumentToInvestor(String investorId, String documentPath) {
        // get upload details
        JSONObject uploadingDetails = getDocumentUploadDetails();

        // upload file to AWS S3
        uploadFileToS3(investorId, uploadingDetails, documentPath);

        // link document to investor
        return linkDocumentToInvestor(investorId, uploadingDetails);
    }

    public void addDocumentToInvestorInCp(String investorId, String documentPath, String issuerName) {
        // get upload details
        JSONObject uploadingDetails = getDocumentUploadDetails();

        // upload file to AWS S3
        uploadFileToS3(investorId, uploadingDetails, documentPath);

        // link document to investor
        linkDocumentToInvestorInCp(investorId, issuerName, uploadingDetails);
    }

    private JSONObject getDocumentUploadDetails() {
        infoAndShowMessageInBrowser("Run api call: getDocumentUploadDetails");

        String result = getAPI("upload?use-secure=true", 200, true);
        return new JSONObject(result);
    }

    private void uploadFileToS3(String investorId, JSONObject uploadingDetails, String filePath) {
        infoAndShowMessageInBrowser("Run api call: uploadFileToS3");

        String url = uploadingDetails.getString("url");
        HashMap<String, Object> multipartContent = new HashMap<>();

        for (String currentKey : uploadingDetails.getJSONObject("fields").keySet()) {
            multipartContent.put(currentKey, uploadingDetails.getJSONObject("fields").getString(currentKey));
        }
        multipartContent.put("content-type", "image/jpeg");

        // build File object but hiding the full path
        String workingDirectory = System.getProperty("user.dir");
        String cleanPath = filePath.replace(workingDirectory, "");
        if (cleanPath.startsWith("/")) {
            cleanPath = cleanPath.substring(1);
        }
        MultiPartSpecification fileMultipart = new MultiPartSpecBuilder(new File(cleanPath))
                .fileName("passport.jpg")
                .controlName("file")
                .mimeType("image/jpeg")
                .build();

        postMultipartAPI(investorId, url, multipartContent, fileMultipart, 204, true);
    }

    private String linkDocumentToInvestor(String investorId, JSONObject uploadingDetails) {
        infoAndShowMessageInBrowser("Run api call: linkDocumentToInvestor");

        JSONObject body = new JSONObject()
                .put("fileKey", uploadingDetails.getString("fileKey"))
                .put("fileType", "image/jpeg")
                .put("side", "front")
                .put("docCategory", "identification")
                .put("docType", "driving-licence");

        String result = postAPI("securitize-id/investors/" + investorId + "/documents", body.toString(), 201, true);
        JSONObject resultAsJson = new JSONObject(result);
        return resultAsJson.getJSONArray("data").getJSONObject(0).getString("documentId");
    }

    private void linkDocumentToInvestorInCp(String investorId, String issuerName, JSONObject uploadingDetails) {
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        JSONObject body = new JSONObject()
                .put("fileKey", uploadingDetails.getString("fileKey"))
                .put("documentFace", "front")
                .put("documentTitle", "doctitle")
                .put("type", "passport")
                .put("showToInvestor", false);

        String result = postAPI("/issuers/" + issuerId + "/investors/" + investorId + "/documents", body.toString(), 200, true);
        JSONObject resultAsJson = new JSONObject(result);
        resultAsJson.getString("id");
    }

    public void setDocumentAsValid(String investorId, String documentId) {
        infoAndShowMessageInBrowser("Run api call: setDocumentAsValid");

        JSONObject document = new JSONObject()
                .put("documentId", documentId)
                .put("verificationStatus", "verified");

        JSONArray documentsArray = new JSONArray();
        documentsArray.put(document);

        JSONObject body = new JSONObject()
                .put("documents", documentsArray);

        patchAPI("securitize-id/investors/" + investorId + "/documents", body.toString(), 200, true);
    }

    public void resetPassword(String investorId) {
        infoAndShowMessageInBrowser("Run api call: resetPassword");
        getAPI("securitize-id/investors/" + investorId + "/reset-password");
    }

    public void setPassword(String password, String resetPasswordCode) {
        infoAndShowMessageInBrowser("Run api call: setPassword");

        JSONObject body = new JSONObject()
                .put("code", resetPasswordCode)
                .put("newPassword", password);

        String baseUri = ("https://api-dsc." + MainConfig.getProperty(MainConfigProperty.environment) + ".").replace(".production", "") + "securitize.io/";
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(baseUri).setPort(443).build();

        postAPI(spec, "reset-password", body.toString(), null, 200, false, false, true);
    }

    public JSONObject addIssuance(String investorId, String issuerName, String tokenId, int tokenWalletId, String issuanceDate, String issuanceType, int tokensAmount) {
        infoAndShowMessageInBrowser("Run api call: addIssuance");

        JSONObject body = new JSONObject()
                .put("clearAfterIssuance", true)
                .put("issuanceDate", issuanceDate)
                .put("issueAmount", tokensAmount)
                .put("reason", "issue " + tokensAmount + " tokens")
                .put("sourceType", issuanceType)
                .put("tokenId", tokenId);

        if (!issuanceType.equalsIgnoreCase("treasury")) {
            body.put("tokenWalletId", tokenWalletId);
        }

        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String result = postAPI("/issuers/" + issuerId + "/investors/" + investorId + "/token-issuances", body.toString(), 200, true);
        return new JSONObject(result);
    }

    public String createNewSecuritizeIdInvestor(InvestorDetails investorDetails, boolean withoutKYC) {
        return createNewSecuritizeIdInvestor(investorDetails, true, withoutKYC);
    }

    public String createNewSecuritizeIdInvestor(InvestorDetails investorDetails) {
        return createNewSecuritizeIdInvestor(investorDetails, false, false);
    }

    public String createCashAccountReadyInvestor(InvestorDetails investorDetails) throws Exception {
        return createNewSecuritizeIdInvestor(investorDetails, false, true);
    }

    private String createNewSecuritizeIdInvestor(InvestorDetails investorDetails, boolean minimal, boolean withoutKYC) {
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + File.separator + "passport-front.jpg");

        // create new investor
        String investorId = createNewInvestor(investorDetails);

        if (!minimal) {
            // add details to him
            addInvestorDetails(investorId, investorDetails);

            // add document
            String documentId = addDocumentToInvestor(investorId, frontImagePath);

            // set document as valid
            setDocumentAsValid(investorId, documentId);
        }

        if (!withoutKYC) {
            // set kyc
            setKYCToPassed(investorId, null);
        }

        // login to investor and set its password
        resetPassword(investorId);
        String resetPasswordRegex = "href=['|\"]http[s]?.*?verify-reset-password\\?code=(.*?)['|\"]";
        String emailContent = EmailWrapper.waitAndGetEmailContentByRecipientAddressAndRegex(investorDetails.getEmail(), resetPasswordRegex);
        String resetPasswordCode = RegexWrapper.getFirstGroup(emailContent, resetPasswordRegex).trim();
        setPassword(investorDetails.getPassword(), resetPasswordCode);

        return investorId;
    }

    public void addValidDocument(String investorId) throws Exception {
        String frontImagePath = ResourcesUtils.getResourcePathByName("images" + File.separator + "passport-front.jpg");
        // add document
        String documentId = addDocumentToInvestor(investorId, frontImagePath);
        // set document as valid
        setDocumentAsValid(investorId, documentId);
    }

    public void verifyEmail(InvestorDataObject investor) {
        // login to investor and set its password
        resetPassword(investor.getSecID());
        String resetPasswordRegex = "href=['|\"]http[s]?.*?verify-reset-password\\?code=(.*?)['|\"]";
        String emailContent = EmailWrapper.getEmailContentByRecipientAddressAndRegex(investor.getEmail(), resetPasswordRegex);
        String resetPasswordCode = RegexWrapper.getFirstGroup(emailContent, resetPasswordRegex);
        setPassword(investor.getPassword(), resetPasswordCode.replace(" ", ""));

    }

    public String createFullNewInvestorByIssuer(InvestorDetails investorDetails, String issuerName) {
        return createFullNewInvestorByIssuer(investorDetails, issuerName, null);
    }
    public String createFullNewInvestorByIssuer(InvestorDetails investorDetails, String issuerName, String tokenName) {
        String frontImagePath = null;
        frontImagePath = ResourcesUtils.getResourcePathByName("images" + File.separator + "passport-front.jpg");

        // create new investor
        String investorId = createNewInvestorByIssuer(investorDetails, issuerName);

        // add details to him
        addDetailsInvestorByIssuer(investorId, investorDetails, issuerName);

        // change kyc status
        String kycStatus = "verified";
        setKYCStatus(investorId, issuerName, kycStatus);

        // change accreditation status
        String accreditationStatus = "confirmed";
        setAccreditationStatus(investorId, issuerName, accreditationStatus);

        // set qualification status
        String qualificationStatus = "confirmed";
        if (tokenName != null) {
            String tokenId = getTokenIdByName(issuerName, tokenName);
            setQualificationStatusForToken(investorId, issuerName, tokenId, qualificationStatus);
        } else {
            setQualificationStatus(investorId, issuerName, qualificationStatus);
        }


        // add document to investor
        addDocumentToInvestorInCp(investorId, frontImagePath, issuerName);

        return investorId;
    }

    public String login(RequestSpecification spec, String issuerName) {
        infoAndShowMessageInBrowser("Run api call: login");

        String email = Users.getIssuerDetails(issuerName, IssuerDetails.investorEmailHealthChecks);

        JSONObject body = new JSONObject()
                .put("email", email)
                .put("password", Users.getProperty(UsersProperty.defaultPasswordComplex));

        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String responseAsString = postAPI(spec, "login", body.toString(), headers, 200, false, false, false, false);
        JSONObject responseAsJson = new JSONObject(responseAsString);
        return responseAsJson.getString("token");
    }

    public String isAuthorized(RequestSpecification spec, String issuerName, String bearerToken) {
        infoAndShowMessageInBrowser("Run api call: isAuthorized");

        // such as: https://automation-issuer.invest.rc.securitize.io/#/authorize
        String investUrl = MainConfig.getInvestPageUrl(issuerName) + "#/authorize";

        JSONArray permissionsList = new JSONArray()
                .put("info")
                .put("details")
                .put("verification");

        JSONObject body = new JSONObject()
                .put("redirectUrl", investUrl)
                .put("issuerId", Users.getIssuerDetails(issuerName, IssuerDetails.issuerID))
                .put("permissions", permissionsList);

        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", bearerToken);

        String responseAsString = postAPI(spec, "is-authorized", body.toString(), headers, 200, false, false, false, false);
        JSONObject responseAsJson = new JSONObject(responseAsString);
        return responseAsJson.getString("code");
    }

    public String authorize(RequestSpecification spec, String issuerName, String code) {
        infoAndShowMessageInBrowser("Run api call: authorize");

        // such as: https://automation-issuer.invest.rc.securitize.io/api/authorize
        String authorizeUrl = MainConfig.getInvestPageUrl(issuerName) + "api/authorize";

        JSONObject body = new JSONObject()
                .put("code", code)
                .put("disclaimer", "a")
                .put("generalDisclaimer", "b")
                .put("countryCode", "US")
                .put("isAgree", true);

        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String responseAsString = postAPI(spec, authorizeUrl, body.toString(), headers, 200, false, false, false, false);
        JSONObject responseAsJson = new JSONObject(responseAsString);
        return responseAsJson.getString("token");
    }

    public String getInvestorCode(String issuerName, String investorId) {
        infoAndShowMessageInBrowser("Run api call: getInvestorCode");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);

        String redirectUrl = MainConfig.getInvestPageUrl(issuerName) + "login";
        JSONObject body = new JSONObject()
                .put("permissions", new JSONArray().put("info").put("details").put("verification").put("kyc"))
                .put("investorId", investorId)
                .put("redirectUrl", redirectUrl);

        String investorPermissionsServiceUrl = MainConfig.getProperty(MainConfigProperty.baseIpsApiUrl);
        String responseAsString = postAPI(investorPermissionsServiceUrl + "/oauth2/" + issuerId + "/create-request", body.toString(), 200, true);
        JSONObject responseAsJsonObject = new JSONObject(responseAsString);

        return responseAsJsonObject.getJSONObject("data").getString("code");
    }

    public void authorizeIssuerForInvestor(String issuerName, String investorCode) {
        infoAndShowMessageInBrowser("Run api call: authorizeIssuerForInvestor");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String issuerSecret = Users.getIssuerDetails(issuerName, IssuerDetails.issuerSecret);

        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Authorization", issuerSecret);
        headers.put("clientid", issuerId);
        headers.put("Content-Type", ContentType.JSON);
        JSONObject body = new JSONObject()
                .put("code", investorCode);

        String connectGatewayBaseUrl = MainConfig.getProperty(MainConfigProperty.baseConnectGwApiUrl);
        postAPI(null, connectGatewayBaseUrl + "/authorize", body.toString(), headers, 200, false, false, true);
    }

    public Double getCashAccountBalanceViaApi(Response loginResponse) {
        String[] headerSetCookie = loginResponse.getHeader("set-cookie").split(";");
        System.out.println("headerSetCookie " + headerSetCookie[0].split("=")[1]);
        JSONObject jsonObject = new JSONObject(loginResponse.getBody().asString());
        String bearerToken = jsonObject.get("token").toString();
        System.out.println("bearerToken" + bearerToken);

        RequestSpecification balanceRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://account-gw." + MainConfig.getProperty(MainConfigProperty.environment) + ".securitize.io")
                .setContentType(ContentType.JSON)
                .addHeader("authorization", bearerToken)
                .addCookies(loginResponse.getCookies())
                .addQueryParam("include", "instant-settlement")
                .build();

        String balanceResponse = given().spec(balanceRequestSpec).when().get("/api/v1/account/balance").then().extract().response().asString();
        JSONObject balanceResponseObject = new JSONObject(balanceResponse);
        System.out.print(balanceResponseObject);
        String cashBalanceString = balanceResponseObject.get("cash").toString();
        JSONObject cashBalanceObject = new JSONObject(cashBalanceString);
        return Double.parseDouble(cashBalanceObject.get("total").toString());
    }

    public String getCashAccountIdByInvestorId(String investorId) {

        String url = MainConfig.getProperty(MainConfigProperty.caInvestorCashAccountsV1Url);
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("x-api-key", Users.getProperty(UsersProperty.ca_investor_xApiKey));
        Response response = given()
                .log().all()
                .headers(headers)
                .queryParam("investorId", investorId)
                .get(url)
                .then().log().all()
                .statusCode(200)
                .extract().response();

        String cashAccountId = response.path("data[0].cashAccountId");
        info("CashAccountId: " + cashAccountId + "for InvesotrId: " + investorId);

        return cashAccountId;

    }

    public int deleteCashAccount(String cashAccountId){
        String url = MainConfig.getProperty(MainConfigProperty.caInvestorDeleteCashAccount);
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("x-api-key", Users.getProperty(UsersProperty.ca_investor_xApiKey));
        Response response = given()
                .log().all()
                .headers(headers)
                .pathParam("cashAccountId", cashAccountId)
                .delete(url)
                .then().log().all()
                .statusCode(200)
                .extract().response();

        return response.statusCode();

    }

    private JSONObject getAttorneyByName(String attorneyName) {
        String attorneysAsString = getAPI("broker-dealer/attorneys", 200, true);
        JSONArray attorneys = new JSONArray(attorneysAsString);
        for (int i = 0; i < attorneys.length() ; i++) {
            JSONObject current = attorneys.getJSONObject(i);
            if (current.getString("name").equalsIgnoreCase(attorneyName)) {
                return current;
            }
        }
        errorAndStop("Needed attorney (" + attorneyName + ") can't be found", false);
        return null;
    }

    private String getAccreditationIdByInvestorEmail(String investorEmail) {
        String accreditationsAsString = getAPI("broker-dealer/accreditations?zip-file=true&kyc-status=verified&q=" + investorEmail + "&page=0&limit=25&order-direction=desc", 200, true);
        JSONObject rawAccreditations = new JSONObject(accreditationsAsString);
        JSONArray accreditations = rawAccreditations.getJSONArray("data");
        for (int i = 0; i < accreditations.length() ; i++) {
            JSONObject current = accreditations.getJSONObject(i);
            if (current.getString("investorEmail").equalsIgnoreCase(investorEmail)) {
                return current.getString("id");
            }
        }
        errorAndStop("Needed accreditation by investor email (" + investorEmail + ") can't be found", false);
        return null;
    }
    public void setInvestorAttorney(String investorEmail, String attorneyName) {
        infoAndShowMessageInBrowser("Run api call: setInvestorAttorney");

        JSONObject attorney = getAttorneyByName(attorneyName);
        String body = Objects.requireNonNull(attorney).toString();
        String accreditationId = getAccreditationIdByInvestorEmail(investorEmail);

        putAPI("broker-dealer/accreditations/" + accreditationId + "/attorney", body, 200, true);
    }
    public void setInvestorAccreditation(String investorEmail, String accreditationStatus) {
        infoAndShowMessageInBrowser("Run api call: setInvestorAccreditation");

        String accreditationId = getAccreditationIdByInvestorEmail(investorEmail);

        String body = new JSONObject()
                .put("note", "")
                .put("rejectReason", "")
                .put("status", accreditationStatus).toString();

        patchAPI("broker-dealer/accreditations/" + accreditationId, body, 200, true);
    }

    public JSONObject getTokenWalletsAndBalance(String issuerName, String tokenName) {
        infoAndShowMessageInBrowser("Run api call: getWalletBalanceByTokenName");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        String tokenId = getTokenIdByName(issuerName, tokenName);

        String resultAsString = getAPI("issuers/" + issuerId + "/deployments/" + tokenId, 200, true);
        String balanceAsString = JsonPath.read(resultAsString, "owners.walletRegistrarOwnerBalance");
        double balanceAsDouble = Double.parseDouble(balanceAsString) / Math.pow(10, 18);
        JSONObject result = new JSONObject();
        String walletMasterAddress = JsonPath.read(resultAsString, "owners.tokenOwner");
        String walletSyncerAddress = JsonPath.read(resultAsString, "owners.walletRegistrarOwner");
        result.put("walletMasterAddress", walletMasterAddress);
        result.put("walletSyncerAddress", walletSyncerAddress);
        result.put("balance", BigDecimal.valueOf(balanceAsDouble).setScale(5, RoundingMode.CEILING).doubleValue());
        return result;
    }

    public String createInvestmentUSD(InvestorDetails investorDetails, String issuerName, String tokenId) {
        infoAndShowMessageInBrowser("Run api call: createInvestmentUSD");
        String issuerId = Users.getIssuerDetails(issuerName, IssuerDetails.issuerID);
        int investmentRoundId = getActiveInvestmentRoundId(issuerName, tokenId);
        String body = new JSONObject()
                .put("currencyId", CurrencyIds.USD.getId())
                .put("roundId", investmentRoundId)
                .put("tokenId", tokenId)
                .toString();

        String issuerInvestorId = getInvestorIdFromInvestorEmail(issuerName, investorDetails.getEmail());
        String url = String.format("/issuers/%s/investors/%s/investment", issuerId, issuerInvestorId);
        return postAPI(url, body, 201, true);
    }

    public String createVerifiedIssuerInvestorWithWallet(InvestorDetails investorDetails, String issuerName, String tokenId, String walletName) {
        String investorId = createNewSecuritizeIdInvestor(investorDetails);
        String investorCode = getInvestorCode(issuerName, investorId);
        authorizeIssuerForInvestor(issuerName, investorCode);
        int waitTimeUseIdReturnSeconds = MainConfig.getIntProperty(MainConfigProperty.waitTimeUseIdReturnSeconds);
        int intervalTimeUserIdReturnSeconds = MainConfig.getIntProperty(MainConfigProperty.intervalTimeUserIdReturnSeconds);
        String issuerInvestorId = getIssuersUserIdBySecuritizeIdProfileId(investorId, waitTimeUseIdReturnSeconds, intervalTimeUserIdReturnSeconds);
        setAccreditationStatus(issuerInvestorId, issuerName, "confirmed");
        setQualificationStatus(issuerInvestorId, issuerName, tokenId, "confirmed", "");
        EthereumWallet ethereumWallet = EthereumUtils.generateRandomWallet();
        if (ethereumWallet == null) return null; // should never happen due to error_and_stop within
        String walletAddress = ethereumWallet.getAddress();
        addWalletToInvestor(issuerName, issuerInvestorId, walletName, walletAddress, tokenId);
        return issuerInvestorId;
    }

    public String getSecuritizeIdOfIndividualByEmail(String email) {
        infoAndShowMessageInBrowser("Run api call: getSecuritizeIdOfIndividualByEmail");
        JSONObject body = new JSONObject()
                .put("page", 0)
                .put("limit", 50)
                .put("order-direction", "desc")
                .put("order-field", "created-at")
                .put("investor-type", new JSONArray().put(0, "individual"))
                .put("q", email);

        String responseAsString = postAPI("/securitize-id/investors", body.toString(), 201, true);
        JSONObject responseAsJsonObject = new JSONObject(responseAsString);
        JSONArray listOfInvestors = responseAsJsonObject.getJSONArray("data");
        for (Object currentInvestorAsObject : listOfInvestors) {
            JSONObject currentInvestor = (JSONObject) currentInvestorAsObject;
            if (currentInvestor.getString("investorType").equals("individual")) {
                return currentInvestor.getString("securitizeIdProfileId");
            }
        }

        errorAndStop("Securitize investor id not found in latest 50 investors for email " + email, false);
        return null;
    }
}