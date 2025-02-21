package io.securitize.infra.utils;

import io.restassured.http.ContentType;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import org.json.JSONObject;
import org.testng.Assert;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public final class KafkaHelper {

    private static final String CONFLUENT_CLOUD_BASE_URL = Users.getProperty(UsersProperty.confluentCloudBaseUrl);
    private static final String CONFLUENT_CLOUD_AUTOMATION_KEY = Users.getProperty(UsersProperty.confluentCloudAutomationKey);
    private static final String CONFLUENT_CLOUD_AUTOMATION_SECRET = Users.getProperty(UsersProperty.confluentCloudAutomationSecret);
    private static final String CLUSTER_ID = Users.getProperty(UsersProperty.confluentCloudAutomationClusterId);
    private static final String AUTH_BASIC = java.util.Base64.getEncoder().encodeToString(String.format("%s:%s", CONFLUENT_CLOUD_AUTOMATION_KEY, CONFLUENT_CLOUD_AUTOMATION_SECRET).getBytes(StandardCharsets.UTF_8));

    private static final String BITBUCKET_EVENTS_PKG_URL = Users.getProperty(UsersProperty.bitbucketEventsPkgUrl);
    private static final String BITBUCKET_USERNAME = Users.getProperty(UsersProperty.bitbucketUsername);
    private static final String BITBUCKET_PASSWORD = Users.getProperty(UsersProperty.bitbucketPassword);
    private static final String BITBUCKET_AUTH_BASIC = java.util.Base64.getEncoder().encodeToString(String.format("%s:%s", BITBUCKET_USERNAME, BITBUCKET_PASSWORD).getBytes(StandardCharsets.UTF_8));

    private static final String TOPIC_NAME_PATTERN = "^(.*?)\\.(.*?)$";
    private static final String INTERFACE_PAYLOAD_PATTERN = "Payload\\s([^}]*)}";
    private static final String INTERFACE_PARAMETER_PATTERN = "\\s*(\\w+):";

    private KafkaHelper() {
    }

    public static void produceKafkaEvent(String topicName, String fullBody) {
        validateEventJson(topicName, fullBody);

        String fullUrl = String.format("%s/kafka/v3/clusters/%s/topics/%s/records", CONFLUENT_CLOUD_BASE_URL, CLUSTER_ID, topicName);
        String response = given()
                .when()
                .log().all()
                .header("Authorization", String.format("Basic %s", AUTH_BASIC))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(fullBody)
                .post(fullUrl)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().asString();
        int internalErrorCode = new JSONObject(response).getInt("error_code");
        Assert.assertEquals(internalErrorCode, 200, "Internal status code of publishing to Confluent Kafka should be 200. This is not the case");
    }

    public static String fetchFileFromBitbucket(String topicName) {
        Matcher matcher = Pattern.compile(TOPIC_NAME_PATTERN).matcher(topicName);
        if (!matcher.find()) {
            errorAndStop("Couldn't find matches in topicName", false);
        }
        String domain = matcher.group(1);
        String fileName = matcher.group(2).replace(".", "-") + ".event.ts";
        if (domain.equals("ta")) {
            domain += "/events";
        }
        String filePath = String.format("%s/%s/%s", BITBUCKET_EVENTS_PKG_URL, domain, fileName);
        return given()
                .when()
                .header("Authorization", String.format("Basic %s", BITBUCKET_AUTH_BASIC))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get(filePath)
                .then()
                .statusCode(200)
                .extract().response().asString();
    }

    private static List<String> readParametersFromFile(String topicName) {
        String fileContent = fetchFileFromBitbucket(topicName);
        Matcher payloadMatcher = Pattern.compile(INTERFACE_PAYLOAD_PATTERN).matcher(fileContent);

        if (!payloadMatcher.find()) {
            errorAndStop("Couldn't find the event interface payload", false);
        }

        String payloadInterface = payloadMatcher.group(1).trim();
        Matcher parameterMatcher = Pattern.compile(INTERFACE_PARAMETER_PATTERN).matcher(payloadInterface);

        if (!parameterMatcher.find()) {
            errorAndStop("Couldn't find the event parameters", false);
        }

        List<String> parameters = new ArrayList<>();
        while (parameterMatcher.find()) {
            parameters.add(parameterMatcher.group(1));
        }
        return parameters;
    }

    private static boolean compareParameters(JSONObject eventJson, List<String> payloadParameters) {
        for (String parameter : payloadParameters) {
            if (!eventJson.has(parameter)) {
                return false;
            }
        }
        return true;
    }

    public static void validateEventJson(String topicName, String fullEventBody) {
        try {
            List<String> payloadInterface = readParametersFromFile(topicName);
            JSONObject eventJson = new JSONObject(fullEventBody).getJSONObject("value").getJSONObject("data");
            boolean match = compareParameters(eventJson, payloadInterface);

            if (!match) {
                errorAndStop("Event JSON does not match the payload interface", false);
            }

            info("The JSON parameters match the parameters in the payload interface!");

        } catch (Exception e) {
            errorAndStop("Error validating event json: " + e, false);
        }
    }
}

