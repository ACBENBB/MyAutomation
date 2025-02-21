package io.securitize.infra.api;

import com.jayway.jsonpath.JsonPath;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.config.DataManager;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.info;

public class CashAccountApi {

    // https://securitize.atlassian.net/browse/FT-2273

    private static RequestSpecification investorsApiRequestSpec;
    private static RequestSpecification synapseApiRequestSpec;

    private static RetryPolicy<Object> retryPolicy;
    private static final int retryPolicyMaxAttempts = 6;
    private static final long retryPolicyDelayOfSeconds = 10;

    private static final String BASE_URL_CA_INVESTOR_API = DataManager.getInstance("ca").getTeamProperty("caInvestorBaseUrl");
    private static final String BASE_URL_CA_SYNAPSE_API = DataManager.getInstance("ca").getTeamProperty("caBankSynapseBaseUrl");
    private static final String CA_INVESTOR_API_KEY = DataManager.getInstance("ca").getAwsProperty("caInvestorApiKey");
    private static final String CA_SYNAPSE_API_KEY = DataManager.getInstance("ca").getAwsProperty("apiCaBankSynapseApiKey");

    private static final String ENDPOINT_POST_CREATE_AGREEMENT = DataManager.getInstance("ca").getTeamProperty("createAgreementEndPoint");
    private static final String ENDPOINT_POST_ACCEPT_AGREEMENT = DataManager.getInstance("ca").getTeamProperty("acceptAgreementEndPoint");
    private static final String ENDPOINT_GET_PROVIDER_ID = DataManager.getInstance("ca").getTeamProperty("getProviderIdEndPoint");
    private static final String ENDPOINT_GET_CASH_ACCOUNT_BALANCE = DataManager.getInstance("ca").getTeamProperty("getCashAccountBalance");
    private static final String ENDPOINT_DELETE_CASH_ACCOUNT = DataManager.getInstance("ca").getTeamProperty("deleteCashAccount");
    private static final String ENDPOINT_GET_CASH_ACCOUNT = DataManager.getInstance("ca").getTeamProperty("getCashAccountEndPoint");

    private static final String ENDPOINT_POST_APPROVE_KYC = DataManager.getInstance("ca").getTeamProperty("postApproveKycEndPoint");
    private static final String ENDPOINT_POST_DUMMY_TRANSFER = DataManager.getInstance("ca").getTeamProperty("postDummyTransferEndPoint");
    private static final String ENDPOINT_POST_SYNAPSE_CARD_TRANSFER_METHODS = DataManager.getInstance("ca").getTeamProperty("postSynapseCardTransferMethodEndPoint");
    private static final String ENDPOINT_POST_SYNAPSE_DEPOSIT = DataManager.getInstance("ca").getTeamProperty("postSynapseDepositEndPoint");

    private static final String KEYWORD_ENVIRONMENT = "{environment}";
    private static final String KEYWORD_SEC_ID = "{investorId}";
    private static final String KEYWORD_CASH_ACCOUNT_ID = "{cashAccountId}";
    private static final String KEYWORD_PROVIDER_ID = "{providerId}";

    public CashAccountApi() {
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        investorsApiRequestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL_CA_INVESTOR_API.replace(KEYWORD_ENVIRONMENT, environment))
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", CA_INVESTOR_API_KEY)
                .addHeader("accept", "application/json")
                .build();
        synapseApiRequestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL_CA_SYNAPSE_API.replace(KEYWORD_ENVIRONMENT, environment))
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", CA_SYNAPSE_API_KEY)
                .addHeader("accept", "application/json")
                .build();
        retryPolicy = getRetryPolicyInstance();
    }

    private RetryPolicy<Object> initRetryPolicy() {
        return retryPolicy = RetryPolicy.builder()
                .handle(AssertionError.class)
                .withDelay(Duration.ofSeconds(retryPolicyDelayOfSeconds))
                .withMaxRetries(retryPolicyMaxAttempts)
                .onRetry(e -> info("API request failed, will try again. Error: " + e.getLastException()))
                .build();
    }

    private RetryPolicy<Object> getRetryPolicyInstance() {
        if(retryPolicy == null) {
            return initRetryPolicy();
        }
        return retryPolicy;
    }

    public double getCashAccountBalance(String investorSid) {

        return Failsafe.with(retryPolicy).get(() -> {
            Response getProviderIdResponse = given().spec(investorsApiRequestSpec).when()
                    .get(ENDPOINT_GET_CASH_ACCOUNT_BALANCE.replace(KEYWORD_SEC_ID, investorSid)).then()
                    .log().all()
                    .assertThat().statusCode(200).extract().response();
            String bodyAsString = getProviderIdResponse.getBody().asString();
            return parseAvailableValueFromJson(bodyAsString);
        });

    }

    public String getInvestorIdByEmail(String email){
        InvestorsAPI investorsAPI = new InvestorsAPI();
        String investorId = investorsAPI.getUniqueSecuritizeIdByEmail(email);
        info("InvestorId: " + investorId + " for email: " + email);
        return investorId;
    }

    public String getCashAccountId(String investorId) {

        return Failsafe.with(retryPolicy).get(() -> {
            Response cashAccountIdResponse = given().spec(investorsApiRequestSpec).when()
                    .queryParam("investorId", investorId)
                    .get(ENDPOINT_GET_CASH_ACCOUNT).then()
                    .assertThat().statusCode(200)
                    .extract().response();
            String cashAccountId = cashAccountIdResponse.path("data[0].cashAccountId");
            info("CashAccountId: " + cashAccountId + "for InvestorId: " + investorId);

            return cashAccountId;

        });

    }

    public String createCashAccountWithFunds(String investorId, Object funds) {
        // https://securitize.atlassian.net/browse/FT-2273
        createCashAccountAgreement(investorId);
        String cashAccountId = acceptCashAccountAgreement(investorId);
        String providerId = getProviderId(investorId);
        Double fundingAmount = Double.parseDouble(funds.toString());
        if (fundingAmount > 0) {
            transferFundsByCard(providerId, fundingAmount, "USD");
        }

        return cashAccountId;
    }

    public String createCashAccountAgreement(String investorSid) {

        return Failsafe.with(retryPolicy).get(() -> {
            Response createAgreementResponse = given().spec(investorsApiRequestSpec).when()
                    .post(ENDPOINT_POST_CREATE_AGREEMENT.replace(KEYWORD_SEC_ID, investorSid)).then()
                    .log().all()
                    .assertThat().statusCode(201).extract().response();

            String body = createAgreementResponse.getBody().asString();
            return createAgreementResponse.jsonPath().getString("investorId");
        });

    }

    public String acceptCashAccountAgreement(String investorSid) {

        HashMap<String, String> body = new HashMap<>();
        body.put("providerType", "SYNAPSE");
        body.put("signature", "AUTOMATION");

        return Failsafe.with(retryPolicy).get(() -> {
            investorsApiRequestSpec.body(body);
            Response acceptAgreementResponse = given().spec(investorsApiRequestSpec).when()
                    .post(ENDPOINT_POST_ACCEPT_AGREEMENT.replace(KEYWORD_SEC_ID, investorSid)).then()
                    .log().all()
                    .assertThat().statusCode(201).extract().response();
            String bodyAsString = acceptAgreementResponse.getBody().asString();
            return acceptAgreementResponse.jsonPath().getString("cashAccountId");
        });

    }

    public String getProviderId(String investorSid) {

        return Failsafe.with(retryPolicy).get(() -> {
            Response getProviderIdResponse = given().spec(investorsApiRequestSpec).when()
                    .get(ENDPOINT_GET_PROVIDER_ID.replace(KEYWORD_SEC_ID, investorSid)).then().log().all()
                    .assertThat().statusCode(200).extract().response();
            String bodyAsString = getProviderIdResponse.getBody().asString();
            return parseProviderIdFromJson(bodyAsString);
        });

    }

    public String approveKyc(String providerId) {

        HashMap<String, String> body = new HashMap<>();
        body.put("providerId", providerId);

        return Failsafe.with(retryPolicy).get(() -> {
            synapseApiRequestSpec.body(body);
            Response getProviderIdResponse = given().spec(synapseApiRequestSpec).when()
                    .post(ENDPOINT_POST_APPROVE_KYC).then()
                    .assertThat().statusCode(201).extract().response();
            return getProviderIdResponse.getBody().asString();
        });

    }

    public String transferFunds(String providerId, Object amount) {

        HashMap<String, Object> body = new HashMap<>();
        body.put("userId", providerId);
        body.put("amount", amount);
        body.put("direction", "DEPOSIT");
        body.put("currency", "USD");
        body.put("reference", "string");

        return Failsafe.with(retryPolicy).get(() -> {
            synapseApiRequestSpec.body(body);
            Response postDummyTransactionResponse = given().spec(synapseApiRequestSpec).when()
                    .post(ENDPOINT_POST_DUMMY_TRANSFER).then()
                    .log().all()
                    .assertThat().statusCode(201).extract().response();
            return postDummyTransactionResponse.getBody().asString();
        });

    }

    public String deleteCashAccount(String cashAccountId){

        info("Deleting account with CashAccountId: " + cashAccountId);

        return Failsafe.with(retryPolicy).get(() -> {
            Response deleteCashAccountResponse = given().spec(investorsApiRequestSpec).when()
                    .delete(ENDPOINT_DELETE_CASH_ACCOUNT.replace(KEYWORD_CASH_ACCOUNT_ID, cashAccountId)).then()
                    .assertThat().statusCode(200).extract().response();

            return deleteCashAccountResponse.getBody().asString();
        });

    }

    public String parseProviderIdFromJson(String getProviderIdBodyAsString) {
        net.minidev.json.JSONArray providerIds = JsonPath.parse(getProviderIdBodyAsString)
                .read("data[0].systemAccounts[?(@.provider=='SYNAPSE')].providerId");
        return providerIds.get(0).toString();

    }

    public double parseAvailableValueFromJson(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray balanceArray = jsonObject.getJSONObject("data")
                .getJSONArray("balance");
        JSONObject firstBalanceObject = balanceArray.getJSONObject(0);
        return firstBalanceObject.getDouble("available");
    }

    public void transferFundsByCard(String providerId, Number amount, String currency) {
        String transferMethodId = addCreditCard(providerId);
        deposit("CREDIT_CARD", providerId, transferMethodId, amount, currency);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String addCreditCard(String providerId) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("cardNumber", "L2C6J1IcmqB03Wk/IKy6QMbIlfBom55jjgW5fR8bmlf7K5FA6Qb3JDkVHmgtBtZxgvVKMeeaJOV3fudDm6rnLw==");
        body.put("expirationDate", "jV5XxYHhbXmoxpL4Vj1ME6VC+dKKCwnweCt07Qh+tKycHO+1YVf6ARWgXvhJluOO3ico2ReuzCmobVbXPjWzkg==");
        body.put("securityCode", "h1aZQqpa38Kz3gPfqSlRG59pJ7knw8/l50JCbG+JgvbOC7MA6HJMtaYfwX1k+6ZbfU3uk0qqHaVIakUwuLzEaQ==");

        return Failsafe.with(retryPolicy).get(() -> {
            synapseApiRequestSpec.body(body);
            Response response = given().spec(synapseApiRequestSpec).when()
                    .post(ENDPOINT_POST_SYNAPSE_CARD_TRANSFER_METHODS.replace(KEYWORD_PROVIDER_ID, providerId)).then()
                    .log().all()
                    .assertThat().statusCode(201).extract().response();

            return response.getBody().jsonPath().getString("transferMethodId");
        });
    }

    public String deposit(String transferMethod, String toUserId, String transferMethodId, Number amount, String currency) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("transferMethod", transferMethod);
        body.put("toUserId", toUserId);
        body.put("transferMethodId", transferMethodId);
        body.put("amount", amount);
        body.put("currency", currency);
        body.put("externalId", "string");
        body.put("reference", "auto");

        return Failsafe.with(retryPolicy).get(() -> {
            synapseApiRequestSpec.body(body);
            Response response = given().spec(synapseApiRequestSpec).when()
                    .post(ENDPOINT_POST_SYNAPSE_DEPOSIT).then()
                    .log().all()
                    .assertThat().statusCode(201).extract().response();

            return response.getBody().jsonPath().getString("id");
        });
    }


}