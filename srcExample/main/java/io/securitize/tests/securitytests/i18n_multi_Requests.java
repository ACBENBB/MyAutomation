package io.securitize.tests.securitytests;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class i18n_multi_Requests {
    private static final int THREAD_COUNT = 20;
    private static final int REQUESTS_COUNT = 400;
    public static void main(String[] args) {
        ExecutorService taskExecutor = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 1; i <= REQUESTS_COUNT; i++) {
            System.out.println("Starting #" + i);
            int finalI = i;
            //taskExecutor.execute(() -> getAPI(finalI, "https://api-dsc.securitize.io/i18n/en"));
            taskExecutor.execute(() -> getAPI(finalI, "http://id.rc.securitize.io/i18n/en"));
        }
        taskExecutor.shutdown();
        try {
            taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Interrupted!");
        }
        System.out.println("Finished!");
    }

         public static void getAPI(int i, String url) {

// set default headers if none provided
        HashMap<String, Object> headers;

            headers = new HashMap<>();
            //headers.put("Host", "api-dsc.securitize.io");
            headers.put("Host", "api-dsc.rc.securitize.io");

        Response response =
                given()
                        .headers(headers)
                       //  .log().all()
                        .get(url)
                        .then()
                       // .log().all()
                        .extract()
                        .response();
        int actualResponseCode = response.statusCode();
        System.out.println(i + " Response code: " + actualResponseCode);
        String responseAsString = response.body().asString();
        // System.out.println("Response: " + responseAsString);
    }
}
