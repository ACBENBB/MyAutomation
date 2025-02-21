package io.securitize.infra.api;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.restassured.response.*;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.*;
import org.json.JSONObject;

import java.time.Duration;
import java.util.*;

import static io.securitize.infra.reporting.MultiReporter.*;
import static io.securitize.infra.utils.Authentication.getTOTPCode;

public class LoginAPI extends AbstractAPI {

    public String postLoginControlPanel() {
        if (!isCalledByValidTest()) {
            errorAndStop("Error! Login of the CP api account can only be performed by script AUT236 - to avoid breaking the commonly shared bearer token stored in the cloud", false);
        }

        String secret = Users.getProperty(UsersProperty.automationCp2FaSecret);

        return postLoginControlPanel(
                Users.getProperty(UsersProperty.automationCpApiEmail),
                Users.getProperty(UsersProperty.automationCpPassword),
                getTOTPCode(secret));
    }

    // As performing login causes the cloud stored bearer to become obsolete, this function can only
    // be called as part of the cloud stored bearer update process (Script AUT236).
    // Any other job calling this function should be rejected
    private boolean isCalledByValidTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return Arrays.stream(stackTrace).anyMatch(x -> x.getClassName().contains("AUT236"));
    }

    private String postLoginControlPanel(String email, String password, String token2Fa) {
        infoAndShowMessageInBrowser("Run api call: postLoginControlPanel");

        Authentication.clearBearerToken();

        String body = new JSONObject()
                .put("email", email)
                .put("password", password)
                .put("tfaToken", token2Fa).toString();

        return postLoginAPI("session/login", body, 201, true);
    }

    public String postLoginSecuritizeId(String email, String password) {
        infoAndShowMessageInBrowser("Run api call: postLoginSecuritizeId");

        String body = new JSONObject()
                .put("email", email)
                .put("password", password)
                .toString();

        String responseAsString  = postAPI(MainConfig.getProperty(MainConfigProperty.baseDsApiUrl) + "login", body,200,true);
        JSONObject response = new JSONObject(responseAsString);
        String investorId = response.getJSONObject("user").getString("investorId");
        info("investorId is: " + investorId);
        return investorId;

    }

    public Response loginSecIdAndReturnBearerToken(String email, String password) {
        infoAndShowMessageInBrowser("Run api call: postLoginSecuritizeId");
        String body = new JSONObject()
                .put("email", email)
                .put("password", password)
                .toString();
        return postLoginAPIb(MainConfig.getProperty(MainConfigProperty.baseDsApiUrl) + "login", body, 200, true);
    }

    public Response postLoginControlPanelReturnResponse() {
        infoAndShowMessageInBrowser("Run api call: postLoginControlPanel and return response obj");
        Authentication.clearBearerToken();
        String secret = Users.getProperty(UsersProperty.automationCp2FaSecret);

        // to handle rare cases where we generate the otp right before it expires and then fail the login
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(AssertionError.class)
                .withDelay(Duration.ofSeconds(5))
                .withMaxRetries(2)
                .onRetry(e -> info("Login failed, will try again. Error: " + e.getLastException()))
                .build();

        return Failsafe.with(retryPolicy).get(() -> {
            String token2Fa = getTOTPCode(secret);

            String body = new JSONObject()
                    .put("email", buildEmailAddress())
                    .put("password", Users.getProperty(UsersProperty.automationCpPassword))
                    .put("tfaToken", token2Fa).toString();
            return postLoginAPIReturnResponse("session/login", body, 201, true);
        });
    }

    private String buildEmailAddress() {
        String emailAddressTemplate = Users.getProperty(UsersProperty.automationCpE2eEmail);
        String currentTestName = getCurrentTestNameFromStack();
        return String.format(emailAddressTemplate, currentTestName);
    }

    private String getCurrentTestNameFromStack() {
        // search the current stackTrace for a class containing AUTXX_ string and extract it
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Optional<StackTraceElement> result = Arrays.stream(stackTrace).filter(x -> RegexWrapper.getTestNameFromClassName(x.getMethodName()) != null).findFirst();
        if (!result.isPresent()) {
            errorAndStop("Unable to extract test name like AUTXX_ from test class name, make sure you give this test class a proper name that includes the JIRA test name AUTXX_", false);
        } else {
            return RegexWrapper.getTestNameFromClassName(result.get().getMethodName());
        }
        return null; // will never run, here just to avoid code warning
    }

}