package io.securitize.tests.securitytests;

import io.restassured.http.ContentType;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.tests.abstractClass.AbstractTest;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.info;

/**
 * This class validates that the login endpoint is protected by rate limiting. After several attempts
 * of successful login we get 429 error - and in a few minutes we can login again
 */
public class AUT617_Security_RateLimiting extends AbstractTest {

    private static final int ALLOWED_LOGIN_ATTEMPTS_BEFORE_RATE_LIMITING = 10;
    private static final int SLEEP_MILLISECONDS_BEFORE_VALIDATING_RATE_LIMITING_REMOVED = 3 * 60 * 1000;
    private static final String BASE_LOGIN_URL = "https://api-dsc%s.securitize.io/login";

    private String fullUrl = null;
    private String body = null;

    @BeforeClass
    public void beforeClass() {
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
            if (environment.equalsIgnoreCase("production")) {
            fullUrl = String.format(BASE_LOGIN_URL, "");
        } else {
            fullUrl = String.format(BASE_LOGIN_URL, "." + environment);
        }

        JSONObject bodyAsJson = new JSONObject();
        bodyAsJson.put("email", Users.getProperty(UsersProperty.AUT617_Security_RateLimiting_User_email));
        bodyAsJson.put("password", Users.getProperty(UsersProperty.defaultPasswordComplex));
        body = bodyAsJson.toString();
    }


    @Test(description = "Validating that we limit the allowed number of login attempts")
    public void rateLimitingLoginAttempts() {
        // attempts that should be successful
        for (int i = 1; i <= ALLOWED_LOGIN_ATTEMPTS_BEFORE_RATE_LIMITING ; i++) {
            info("Attempting successful login (expecting 200) #" + i);
            loginAttempt(fullUrl, body, 200);
        }

        // attempt that should fail
        info("Failed login attempt - expected 429 rate limiting");
        loginAttempt(fullUrl, body, 429);
    }


    @Test(description = "Validate that after x minutes we can access the API once again", dependsOnMethods = "rateLimitingLoginAttempts")
    public void rateLimitingRemovedAfterTimeout() throws InterruptedException {
        // sleep enough time for the rate limitation to be removed
        info(String.format("Waiting for %s milliseconds before attempting to login again", SLEEP_MILLISECONDS_BEFORE_VALIDATING_RATE_LIMITING_REMOVED));
        Thread.sleep(SLEEP_MILLISECONDS_BEFORE_VALIDATING_RATE_LIMITING_REMOVED);

        // attempt to perform a login - should work
        info("Attempting successful login (expecting 200)");
        loginAttempt(fullUrl, body, 200);
    }

    private void loginAttempt(String fullUrl, String body, int expectedStatusCode) {
        given()
                .body(body)
                .contentType(ContentType.JSON)
                .log().ifValidationFails()
                .when()
                .post(fullUrl)
                .then()
                .log().ifValidationFails()
                .statusCode(expectedStatusCode);
    }
}