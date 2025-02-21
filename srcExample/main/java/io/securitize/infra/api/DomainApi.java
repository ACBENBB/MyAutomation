package io.securitize.infra.api;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.securitize.infra.reporting.MultiReporter.*;

/**
 * The code here is a helper, for example, update investor bank information, delete investor.
 * It shall be updated when testing api.
 */
public class DomainApi {

    private final RetryPolicy<HttpResponse<String>> retryPolicy;
    private static final int RETRY_POLICY_MAX_ATTEMPTS = 6;
    private static final long RETRY_POLICY_DELAY_OF_SECONDS = 10;

    private final String apiBaseUrl;
    private final String domainId;
    private final String apiKey;
    private static final String URL_TEMPLATE_DOMAINS_INVESTORS = "%s/domains/%s/investors/%s";
    private static final String CONTENT_TYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String API_KEY = "apiKey ";

    public DomainApi(String apiBaseUrl, String domainId, String apiKey) {
        this.apiBaseUrl = apiBaseUrl;
        this.domainId = domainId;
        this.apiKey = apiKey;
        this.retryPolicy = RetryPolicy.<HttpResponse<String>>builder()
                .handleResultIf(response -> response.statusCode() != 200)
                .withDelay(Duration.ofSeconds(RETRY_POLICY_DELAY_OF_SECONDS))
                .withMaxRetries(RETRY_POLICY_MAX_ATTEMPTS)
                .onFailedAttempt(e -> warning("response status code not 200"))
                .build();
    }

    /**
     *  call api, get value about key (e.g. tokenId), and add the value to list
     *  value is added
     *  - when restrictions is null
     *  - when value of restrictions(key) == restrictions(value)
     *    e.g. key:tokenId, value:tokenName. add tokenId to list only when tokenName of tokenId == tokenName
     *  then return the list
     */
    public List<String> getList(String url, String key, Map<String, String> restrictions) {
        List<String> domainNameList = new ArrayList<>();
        HttpResponse<String> response = Failsafe.with(this.retryPolicy).get(() -> getApi(url));
        JSONObject object = new JSONObject(response.body());
        JSONArray array = object.getJSONArray("data");
        int numberOfItems = array.length();
        debug(String.format("found %s", numberOfItems));
        for (int i = 0; i < numberOfItems; i++) {
            String value = array.getJSONObject(i).optString(key);
            if (restrictions == null) {
                debug(key + ": " + value);
                domainNameList.add(value);
            } else {
                String valueRestrict = array.getJSONObject(i).optString(restrictions.get("key"));
                if (valueRestrict.equalsIgnoreCase(restrictions.get("value"))) {
                    debug("add key:" + key + ", value:" + value);
                    domainNameList.add(value);
                }
            }
        }
        return domainNameList;
    }

    public List<String> getDomainName() {
        debug("get domain name with domainId " + domainId);
        String url = String.format("%s/domains/list?domainIds=%s", this.apiBaseUrl, this.domainId);
        return getList(url, "name", null);
    }

    public List<String> getOpportunityId(String tokenId) {
        debug("get opportunityId, domainId: " + domainId + ", tokenId: " + tokenId);
        String url = String.format("%s/domains/%s/opportunities/list?tokenId=%s", this.apiBaseUrl, this.domainId, tokenId);
        return getList(url, "opportunityId", null);
    }

    public List<String> getHolderNameList(String tokenId) {
        debug("get Holders Name List, domainId: " + domainId + ", tokenId: " + tokenId);
        String url = String.format("%s/domains/%s/tokens/%s/holders/list", this.apiBaseUrl, this.domainId, tokenId);
        List<String> nameList = new ArrayList<>();
        HttpResponse<String> response = Failsafe.with(this.retryPolicy).get(() -> getApi(url));
        JSONObject object = new JSONObject(response.body());
        JSONArray array = object.getJSONArray("data");
        int numberOfItems = array.length();
        debug(String.format("number of opportunities: %s", numberOfItems));
        for (int i = 0; i < numberOfItems; i++) {
            String lastName = array.getJSONObject(i).optString("lastName");
            String firstName = array.getJSONObject(i).optString("firstName");
            String name = (firstName.equals("株式会社")) ? firstName + " " + lastName : lastName + " " + firstName;
            debug("name: " + name);
            nameList.add(name);
        }
        return nameList;
    }

    /**
     * check if investor is found or not using email address.
     */
    public boolean investorIsFound(String emailAddressAsQuery) {
        String url = String.format("%s/domains/%s/investors/list?q=%s",
                this.apiBaseUrl, this.domainId, URLEncoder.encode(emailAddressAsQuery, StandardCharsets.UTF_8));
        HttpResponse<String> response = Failsafe.with(this.retryPolicy).get(() -> getApi(url));
        JSONObject object = new JSONObject(response.body());
        JSONArray array = object.getJSONArray("data");
        return !array.isEmpty();
    }

    /**
     * get external id list of investor using GET /v1/domains/{domainId}/investors/list
     * @param emailAddressAsQuery email address, which should identify only 1 investor
     * @return external id list. empty list when api call fails.
     */
    public List<String> getInvestorExternalId(String emailAddressAsQuery) {
        debug("get investor with email address " + emailAddressAsQuery);
        String url = String.format("%s/domains/%s/investors/list?q=%s",
                this.apiBaseUrl, this.domainId, URLEncoder.encode(emailAddressAsQuery, StandardCharsets.UTF_8));
        return getList(url, "externalId", null);
    }

    /**
     * delete investor
     * @param externalId investor externalId
     * @return true when successful, false otherwise
     */
    public boolean deleteInvestor(String externalId) {
        debug("delete investor with externalId " + externalId);
        String url = String.format(URL_TEMPLATE_DOMAINS_INVESTORS, this.apiBaseUrl, this.domainId, externalId);
        HttpResponse<String> response = Failsafe.with(this.retryPolicy).get(() -> deleteApi(url));
        return (response.statusCode() == 200);
    }

    public List<String> getTokenId(String tokenName) {
        String url = String.format("%s/domains/%s/tokens/list?limit=1000", this.apiBaseUrl, this.domainId);
        HashMap<String, String> restrictions = new HashMap<>();
        restrictions.put("key", "name");
        restrictions.put("value", tokenName);
        return getList(url, "tokenId", restrictions);
    }

    public JSONObject getToken(String tokenId) {
        String url = String.format("%s/domains/%s/tokens/%s", this.apiBaseUrl, this.domainId, tokenId);
        HttpResponse<String> response = Failsafe.with(this.retryPolicy).get(() -> getApi(url));
        return new JSONObject(response.body());
    }

    /**
     * get investor of the external id
     * @param externalId investor external id
     * @return JSONObject (response.body())
     */
    public HttpResponse<String> getInvestor(String externalId) {
        String url = String.format(URL_TEMPLATE_DOMAINS_INVESTORS, this.apiBaseUrl, this.domainId, externalId);
        return getApi(url);
    }

    /**
     * update investor with the externalId
     * @param externalId which identifies investor
     * @param body to pass api to update investor
     * @return true when update is successful, false otherwise
     */
    public boolean patchInvestor(String externalId, String body) {
        String url = String.format(URL_TEMPLATE_DOMAINS_INVESTORS, this.apiBaseUrl, this.domainId, externalId);
        HttpResponse<String> response = Failsafe.with(this.retryPolicy).get(() -> patchApi(url, body));
        return (response.statusCode() == 200);
    }

    /**
     * Send Request and Get Response
     */
    private HttpResponse<String> sendRequestAndGetResponse(HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            var client = HttpClient.newHttpClient();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            error("Interrupted Exception: " + interruptedException);
        }

        if (response == null) {
            warning("response is null");
        } else {
            debug("response status code: " + response.statusCode());
            debug("response body: " + response.body());
        }
        return response;
    }

    /**
     * GET
     * @param url for api
     * @return HttpResponse<String> response
     */
    public HttpResponse<String> getApi(String url) {
        debug("GET " + url);
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .headers(CONTENT_TYPE, APPLICATION_JSON, AUTHORIZATION, API_KEY + this.apiKey)
                    .GET()
                    .build();
        } catch (Exception exception) {
            error("exception on GET: " + exception);
        }
        return sendRequestAndGetResponse(request);
    }

    /**
     * DELETE
     * @param url for api
     * @return HttpResponse<String> response
     */
    public HttpResponse<String> deleteApi(String url) {
        debug("DELETE " + url);
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .headers(CONTENT_TYPE, APPLICATION_JSON, AUTHORIZATION, API_KEY + this.apiKey)
                    .DELETE()
                    .build();
        } catch (Exception exception) {
            error("exception on DELETE: " + exception);
        }
        return sendRequestAndGetResponse(request);
    }

    /**
     * PATCH
     * @param url for api
     * @param body to pass api to update investor
     * @return HttpResponse<String> response
     */
    public HttpResponse<String> patchApi(String url, String body) {
        debug("PATCH " + url);
        debug("body " + body);
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .headers(CONTENT_TYPE, APPLICATION_JSON, AUTHORIZATION, API_KEY + this.apiKey)
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (Exception exception) {
            error("exception on PATCH: " + exception);
        }
        return sendRequestAndGetResponse(request);
    }
}
