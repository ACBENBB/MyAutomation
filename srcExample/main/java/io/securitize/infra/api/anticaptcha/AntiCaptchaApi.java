package io.securitize.infra.api.anticaptcha;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.restassured.http.ContentType;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.database.MySqlDatabase;
import io.securitize.infra.webdriver.Browser;
import io.securitize.pageObjects.controlPanel.cpIssuers.CpLoginPage;
import org.json.JSONObject;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.*;
import static org.hamcrest.Matchers.equalTo;

public class AntiCaptchaApi {

    private static final String URL = "https://api.anti-captcha.com/";
    private static final int RECAPTCHA_SOLVE_ATTEMPTS = 3;
    private static final int RECAPTCHA_SLEEP_SECONDS_BETWEEN_ATTEMPTS = 5;

    public JSONObject solveAndGetToken(String websiteUrl, String websiteKey) {

        String taskId = startTask(websiteUrl, websiteKey);

        return waitForTaskResponse(taskId);
    }

    public double getBalance() {
        JSONObject body = new JSONObject()
                .put("clientKey", Users.getProperty(UsersProperty.antiCaptchaClientKey));

        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(body.toString())
                .post(URL + "getBalance")
                .then()
                .log().all()
                .statusCode(200)
                .body("errorId", equalTo(0))
                .extract().jsonPath().getDouble("balance");
    }


    private String startTask(String websiteUrl, String websiteKey) {
        JSONObject task = new JSONObject()
                .put("type", "RecaptchaV2TaskProxyless")
                .put("websiteURL", websiteUrl)
                .put("websiteKey", websiteKey)
                .put("websiteSToken", "")
                .put("recaptchaDataSValue", "");

        JSONObject body = new JSONObject()
                .put("clientKey", Users.getProperty(UsersProperty.antiCaptchaClientKey))
                .put("task", task);

        // start the task
        info("Sending request to Anti-Captcha");
        var taskId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(URL + "createTask")
                .then()
                .log().all()
                .statusCode(200)
                .body("errorId", equalTo(0))
                .extract().jsonPath().getString("taskId");
        info("Remote task started successfully: " + taskId);
        return taskId;
    }

    private JSONObject waitForTaskResponse(String taskId) {
        JSONObject body = new JSONObject()
                .put("clientKey", Users.getProperty(UsersProperty.antiCaptchaClientKey))
                .put("taskId", taskId);

        return Browser.waitForExpressionNotNull(q -> {
            info("Checking if remote task has been solved by Anti-Captcha...");
            String rawResponse = given()
                    .when()
                    .contentType(ContentType.JSON)
                    .body(body.toString())
                    .post(URL + "getTaskResult")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response().body().asString();

            JSONObject response = new JSONObject(rawResponse);
            if (response.getInt("errorId") == 0) {
                switch (response.getString("status")) {
                    case "processing":
                        info("task is still processing");
                        return null;
                    case "ready":
                        info("task solved");
                        return response;
                    default:
                        info("unknown status.. trying again..." + response.getString("status"));
                        return null;
                }
            } else {
                warning(String.format("API error %s: %s", response.getString("errorCode"), response.getString("errorDescription")));
                return null;
            }
        }, null, "Wait for Anti-Captcha result", 300, 10 * 1000);
    }

    public void solveRecaptchaWithRetries(String currentUrl, CpLoginPage loginPage) {
        String loginRecaptchaWebsiteKey = Users.getProperty(UsersProperty.loginRecaptchaWebsiteKey);

        // try several times running logic as sometimes the response from Anti-Captcha doesn't work
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .withMaxRetries(RECAPTCHA_SOLVE_ATTEMPTS)
                .withDelay(Duration.ofSeconds(RECAPTCHA_SLEEP_SECONDS_BETWEEN_ATTEMPTS))
                .handleResult(false)
                .build();

        AtomicInteger attemptNumber = new AtomicInteger(1);
        boolean isPassedRecaptcha = Failsafe.with(retryPolicy).get(() -> {
            JSONObject response = solveAndGetToken(currentUrl, loginRecaptchaWebsiteKey);
            String token = response.getJSONObject("solution").getString("gRecaptchaResponse");
            boolean isSuccessful = loginPage.injectRecaptchaToken(token);

            // report status to MySQL
            AntiCaptchaResult antiCaptchaResult = new AntiCaptchaResult(
                    attemptNumber.getAndIncrement(),
                    response.getLong("createTime"),
                    response.getLong("endTime"),
                    response.getString("cost"),
                    isSuccessful);
            MySqlDatabase.send(antiCaptchaResult);

            return isSuccessful;
        });

        if (isPassedRecaptcha) {
            info("Recaptcha bypass successful!");
        } else {
            errorAndStop("Couldn't bypass Recaptcha even after multiple attempts", true);
        }
    }
}