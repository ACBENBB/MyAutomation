package io.securitize.infra.api.healthchecks;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.api.InvestorsAPI;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.exceptions.HealthCheckException;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.securitize.infra.reporting.MultiReporter.*;

public class HealthCheckUrls extends AbstractHealthCheck {

    // colors used for nicer console printing
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\033[32m";

    private static final int THREAD_POOL_SIZE = 30;
    private static final int MAX_WAIT_TIME_SECONDS = 60;

    @SuppressWarnings("unused")
    public void healthCheck() {
        healthCheck("all"); // by default will run on all urls ignoring specific tag values
    }

    public void healthCheck(String tag) {
        ArrayList<String> pendingUrls = new ArrayList<>();
        ArrayList<String> failedUrls = new ArrayList<>();

        printConsoleHeader();
        info("Starting health check status on urls");

        String issuerName = "Nie"; //todo: this is now hardcoded. Needs to be parameterized

        // load list of relevant urls
        List<HealthCheckEntry> urls = loadHealthCheckEndpoints("HealthChecksUrls.csv");
        if ((urls == null) || (urls.size() == 0)) {
            String message = "No health check endpoints found. Shouldn't happen";
            errorAndStop(message, false, new HealthCheckException(message));
            return;
        }
        info("Founds " + urls.size() + " urls to validate");

        // perform needed login and authorize
        info("Logging in as investor");
        String baseUri = MainConfig.getProperty(MainConfigProperty.baseDsApiUrl);
        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(baseUri).setPort(443).build();
        String bearerToken;
        try {
            bearerToken = loginAndAuthorize(spec, issuerName);
        } catch (Exception | AssertionError e) {
            throw new HealthCheckException("An error occur trying to login in order to perform URLs health-checks. Details: " + e);
        }

        // call the urls in parallel
        info(String.format("Starting health check status: (tag: %s) (parallel threads: %s)", tag, THREAD_POOL_SIZE));
        ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long startTime = System.currentTimeMillis();
        for (HealthCheckEntry current : urls) {
            // run only urls with relevant tag
            if ((!tag.equalsIgnoreCase("all")) && (!current.tags.contains(tag))) {
                continue;
            }
            pendingUrls.add(current.url);

            es.execute(() -> checkUrl(pendingUrls, failedUrls, spec, bearerToken, current));
        }

        boolean finished = false;
        try {
            es.shutdown();
            finished = es.awaitTermination(MAX_WAIT_TIME_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // don't do anything special - will be checked later on
        }

        long endTime = System.currentTimeMillis();
        float duration = (endTime - startTime) / 1000f;
        info("Finished in " + duration + " seconds");

        if (pendingUrls.size() > 0) {
            StringBuilder message = new StringBuilder("Error! Not all endpoints finished returning status in time. Their list:").append(System.lineSeparator());
            for (String current: pendingUrls) {
                message.append(current).append(System.lineSeparator());
            }
            errorAndStop(message.toString(), false, new HealthCheckException(message.toString()));
        }

        if (!finished) {
            String message = "Error! Some endpoints checking threads didn't finish within the time limit";
            errorAndStop(message, false, new HealthCheckException(message));
        }
        if (failedUrls.size() > 0) {
            String message = "Some health checks failed, can't resume";
            errorAndStop(message, false, new HealthCheckException(message));
        }

        info("Health check finished successfully!");
    }

    private String loginAndAuthorize(RequestSpecification spec, String issuerName) {
        // try this logic up to 3 times as sometimes due to parallel execution this might fail
        int maxAttempts = 5;
        for (int i = 1; i <= maxAttempts; i++) {
            try {
                info("loginAndAuthorize attempt #" + i);
                InvestorsAPI investorsAPI = new InvestorsAPI();
                String bearerToken = investorsAPI.login(spec, issuerName);
                String code = investorsAPI.isAuthorized(spec, issuerName, bearerToken);
                return investorsAPI.authorize(spec, issuerName, code);
            } catch (AssertionError e) {
                if (i < maxAttempts) {
                    info("Unable to obtain proper authorization code in attempt #" + i + ". Details: " + e + ". Trying again in 30 seconds");
                    try {
                        Thread.sleep(30 * 1000);
                    } catch (InterruptedException interruptedException) {
                        debug("Interrupted sleeping between loginAndAuthorize attempt.. Terminating..");
                        return null;
                    }
                }
            }
        }
        errorAndStop("Couldn't obtain proper authorization code even in " + maxAttempts + " attempts", false);
        return null;
    }



    private void checkUrl(ArrayList<String> pendingUrls, ArrayList<String> failedUrls, RequestSpecification spec, String bearer, HealthCheckEntry current) {
        boolean isEndpointOk = true;

        RequestResponse response = getAPI(spec, current.url, bearer, !current.url.contains("/api/"));

        if (response.statusCode == -1) {
            String message = String.format("%s%s Failure trying to reach remote endpoint. Details: %s%s", ANSI_RED, current.name, response.bodyAsString, ANSI_RESET);
            info(message);
            isEndpointOk = false;
        } else if (response.statusCode != 200) {
            String message = String.format("%s%s Failure on invalid status code: %s%s", ANSI_RED, current.name, response.statusCode, ANSI_RESET);
            info(message);
            isEndpointOk = false;
        }

        if (isEndpointOk) {
            String message = String.format("%s%s Ok!%s", ANSI_GREEN, current.name, ANSI_RESET);
            info(message);
        } else {
            failedUrls.add(current.url);
        }

        pendingUrls.remove(current.url);
    }


    private RequestResponse getAPI(RequestSpecification spec, String url, String bearer, boolean useBasicAuthentication) {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", ContentType.JSON);

        // build the host header
        URI uri;
        String domain;
        try {
            uri = new URI(url);
            domain = uri.getHost();
        } catch (URISyntaxException e) {
            return new RequestResponse(-1, null, "An error occur trying to deduce host name for request: " + e.toString());
        }
        headers.put("Host", domain);

        Response response;
        try {
            RequestSpecification basicGiven =
                    getSpecWithDebugFilter().spec(spec);

            if (useBasicAuthentication) {
                basicGiven.auth().basic(Users.getProperty(UsersProperty.httpAuthenticationUsername),
                        Users.getProperty(UsersProperty.httpAuthenticationPassword));
            } else {
                headers.put("authorization", bearer);
            }
            response = basicGiven
                    .with()
                    .headers(headers)
                    .get(url);
        } catch (Exception e) {
            return new RequestResponse(-1, null, e.toString());
        }

        String responseAsString = response.body().asString();

        JSONObject bodyAsJson;
        try {
            bodyAsJson = new JSONObject(responseAsString);
        } catch (JSONException e) {
            bodyAsJson = null;
        }

        return new RequestResponse(response.statusCode(), bodyAsJson, responseAsString);
    }

    private void printConsoleHeader() {
        System.out.println(
            "\n============================\n" +
            "*** HEALTH  CHECK  Url ***\n" +
            "============================\n");
    }
}