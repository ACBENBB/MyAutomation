package io.securitize.infra.api.healthchecks;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.exceptions.HealthCheckException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class HealthCheckEndpoint extends AbstractHealthCheck {

    // colors used for nicer console printing
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\033[32m";

    private static final int THREAD_POOL_SIZE = 20;
    private static final int MAX_WAIT_TIME_SECONDS = 60;

    @SuppressWarnings("unused")
    public void healthCheck() {
        healthCheck("all"); // by default will run on all endpoints ignoring specific tag values
    }

    public void healthCheck(String tag) {
        ArrayList<String> pendingEndpoints = new ArrayList<>();
        ArrayList<String> failedEndpoints = new ArrayList<>();
        printConsoleHeader();
        info(String.format("Starting health check status: (tag: %s) (parallel threads: %s)", tag, THREAD_POOL_SIZE));

        // load list of relevant endpoints
        List<HealthCheckEntry> endpoints = loadHealthCheckEndpoints("HealthChecksEndpoints.csv");
        if ((endpoints == null) || (endpoints.size() == 0)) {
            String message = "No health check endpoints found. Shouldn't happen";
            errorAndStop(message, false, new HealthCheckException(message));
            return;
        }

        // call the endpoints in parallel
        ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        long startTime = System.currentTimeMillis();
        for (HealthCheckEntry current : endpoints) {
            // run only endpoints with relevant tag
            if ((!tag.equalsIgnoreCase("all")) && (!current.tags.contains(tag))) {
                continue;
            }
            pendingEndpoints.add(current.url);

            es.execute(() -> checkEndpoint(pendingEndpoints, failedEndpoints, current));
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

        if (pendingEndpoints.size() > 0) {
            StringBuilder message = new StringBuilder("Error! Not all endpoints finished returning status in time. Their list:").append(System.lineSeparator());
            for (String current: pendingEndpoints) {
                message.append(current).append(System.lineSeparator());
            }
            errorAndStop(message.toString(), false, new HealthCheckException(message.toString()));
        }

        if (!finished) {
            String message = "Error! Some endpoints checking threads didn't finish within the time limit";
            errorAndStop(message, false, new HealthCheckException(message));
        }
        if (failedEndpoints.size() > 0) {
            String message = "Some health checks failed, can't resume";
            errorAndStop(message, false, new HealthCheckException(message));
        }

        info("Health check finished successfully!");
    }


    private void checkEndpoint(ArrayList<String> pendingEndpoints, ArrayList<String> failedEndpoints, HealthCheckEntry current) {
        boolean isEndpointOk = true;
        RequestResponse response = getAPI(current.url);

        if (response.statusCode == -1) {
            String message = String.format("%s%s Failure trying to reach remote endpoint. Details: %s%s", ANSI_RED, current.name, response.bodyAsString, ANSI_RESET);
            info(message);
            isEndpointOk = false;
        } else if (response.statusCode != 200) {
            String message = String.format("%s%s Failure on invalid status code: %s%s", ANSI_RED, current.name, response.statusCode, ANSI_RESET);
            info(message);
            isEndpointOk = false;
        } else if (response.body == null) {
            String message = String.format("%s%s Failure on invalid response body: %s%s", ANSI_RED, current.name, response.bodyAsString, ANSI_RESET);
            info(message);
            isEndpointOk = false;
        } else {
            for (String currentKey : response.body.keySet()) {
                if ((current.ignoreList != null) && (current.ignoreList.contains(currentKey))) {
                    continue;
                }
                String currentKeyValue = response.body.get(currentKey).toString();
                if (!currentKeyValue.equalsIgnoreCase("ok")) {
                    String message = String.format("%s%s Failed on json property value (not ok): %s=%s%s", ANSI_RED, current.name, currentKey, currentKeyValue, ANSI_RESET);
                    info(message);
                    isEndpointOk = false;
                    break;
                }
            }
        }

        if (isEndpointOk) {
            String message = String.format("%s%s Ok!%s", ANSI_GREEN, current.name, ANSI_RESET);
            info(message);
        } else {
            failedEndpoints.add(current.url);
        }

        pendingEndpoints.remove(current.url);
    }


    private RequestResponse getAPI(String url) {

        RequestSpecification request = getSpecWithDebugFilter();
        request.baseUri(url);

        Response response;
        try {
            response = request.get(url);
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
            "*** HEALTH  CHECK  API ***\n" +
            "============================\n");
    }
}