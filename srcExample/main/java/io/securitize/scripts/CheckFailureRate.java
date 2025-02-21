package io.securitize.scripts;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.*;

/**
 * This class is used to find out if the current failure rate is over 15%.
 * If so it finds the latest build with a failure rate lower than 15% and that shares the same parameters
 * (branch, env, domains) and triggers a job that collects all developer commits from the time of that build
 */
public class CheckFailureRate {
    private static final boolean IS_DEBUG_MODE = false;

    private static final String JENKINS_URL_PREFIX = "http://localhost:8080/job/";
    private static final String TEST_NG_SUMMARY_FILE_PATH = "target/failsafe-reports/testng-results.xml";
    private static final String ALL_BUILDS_QUERY_SUFFIX = "api/json?tree=allBuilds[id,description,result,timestamp,url,actions[parameters[*]]]&depth=5";
    private static final int FAILURE_PERCENTAGE_LIMIT = 15;
    private static final String JOB_NAME_TO_TRIGGER = "GetDevelopersCommitHistory";
    public static final String BRANCH_NAME = "branchName";
    public static final String ENVIRONMENT = "environment";
    private static String jenkinsUsername;
    private static String jenkinsApiToken;

    public static void main(String[] args) {
        // calculate failure percentage
        float failurePercentage = getFailurePercentage();
        if (failurePercentage < FAILURE_PERCENTAGE_LIMIT) {
            info("Failure percentage is low. Nothing to do: " + failurePercentage + "%");
            return;
        } else {
            info("Failure percentage is high. Will try to find a previous more successful run: " + failurePercentage + "%");
        }

        // read and prepare jenkins credentials and url
        initializeJenkinsParameters();

        // get timestamp of latest similar job in epoch
        long timestamp = findLatestSimilarJobTimestamp();

        // trigger the job that gets us the list of commits by the developers
        if (timestamp < 0) {
            info("No relevant previous execution found. Nothing to do");
            return;
        }
        triggerJob(timestamp);
    }

    private static float getFailurePercentage(String rawBody) {
        try {
            // Load and parse the XML file
            var factory = DocumentBuilderFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
            var builder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(rawBody.getBytes(StandardCharsets.UTF_8));
            var document = builder.parse(stream);
            var result = getFailurePercentage(document);
            if (result < 0) return 100;
            return result;
        } catch (Exception e) {
            error("An error occur trying to read testNg results file. Details: " + e, false);
        }
        return 100;
    }

    private static float getFailurePercentage() {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
            var builder = factory.newDocumentBuilder();
            var document = builder.parse(new File(TEST_NG_SUMMARY_FILE_PATH));
            return getFailurePercentage(document);
        } catch (Exception e) {
            error("An error occur trying to read testNg results file. Details: " + e, false);
        }
        return -1;
    }

    private static float getFailurePercentage(Document document) {
        // Find the 'testng-results' tag and retrieve the failure percentage
        var nodeList = document.getElementsByTagName("testng-results");
        if (nodeList.getLength() > 0) {
            var mainElement = (Element) nodeList.item(0);
            String failedValue = mainElement.getAttribute("failed");
            String totalTests = mainElement.getAttribute("total");
            String skippedTests = mainElement.getAttribute("skipped");
            String ignoredTests = mainElement.getAttribute("ignored");
            int totalNonSkippedTests = Integer.parseInt(totalTests) - Integer.parseInt(skippedTests) - Integer.parseInt(ignoredTests);
            return Float.parseFloat(failedValue) / totalNonSkippedTests * 100f;
        }
        return -1;
    }


    private static void initializeJenkinsParameters() {
        // jenkins doesn't have access to itself using an external hostname
        String jenkinsJobUrl = JENKINS_URL_PREFIX + System.getenv("JOB_BASE_NAME");
        // load secrets from the vault
        jenkinsUsername = Users.getProperty(UsersProperty.jenkinsUsername);
        jenkinsApiToken = Users.getProperty(UsersProperty.jenkinsApiToken);

        // setup needed rest assured settings
        RestAssured.baseURI = jenkinsJobUrl;
        if (IS_DEBUG_MODE) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
    }

    private static RequestSpecification internalGiven() {
        return given()
                .auth().preemptive().basic(jenkinsUsername, jenkinsApiToken)
                .contentType(ContentType.JSON);
    }


    private static long findLatestSimilarJobTimestamp() {
        String buildBranch = System.getenv(BRANCH_NAME);
        String buildEnvironment = System.getenv(ENVIRONMENT);
        String buildDomainsToTest = System.getenv("DOMAINS_TO_TEST");

        var response = internalGiven()
                .get(ALL_BUILDS_QUERY_SUFFIX)
                .then()
                .extract().response();

        if (response.statusCode() != 200) {
            errorAndStop("Unable to read job history. Terminating", false);
        }

        var fullResponse = new JSONObject(response.asString());
        var allBuilds = fullResponse.getJSONArray("allBuilds");
        // starting from i=1 because i=0 means the current build
        for (int i = 1; i < allBuilds.length(); i++) {
            var currentBuild = allBuilds.getJSONObject(i);
            var currentBuildId = currentBuild.getString("id");
            String currentBuildBranch = getBuildParameter(currentBuild, BRANCH_NAME);
            String currentBuildEnvironment = getBuildParameter(currentBuild, ENVIRONMENT);
            String currentBuildDomainsToTest = getBuildParameter(currentBuild, "DOMAINS_TO_TEST");
            String currentBuildRunFailedTests = getBuildParameter(currentBuild, "runFailedTests");

            // ignore builds with different parameter values
            if (!currentBuildBranch.equalsIgnoreCase(buildBranch) ||
             (!currentBuildEnvironment.equalsIgnoreCase(buildEnvironment)) ||
             (!currentBuildDomainsToTest.equalsIgnoreCase(buildDomainsToTest))) {
                info(String.format("Ignoring build #%s as parameters don't match", currentBuildId));
                continue;
            }

            // ignore builds with runFailedTests=True
            if (currentBuildRunFailedTests.equalsIgnoreCase("true")) {
                info(String.format("Ignoring build #%s as parameter runFailedTests=true", currentBuildId));
                continue;
            }

            // load more details of the current job
            var currentBuildResult = currentBuild.getString("result");
            var currentBuildTimestamp = currentBuild.getLong("timestamp");


            if (currentBuildResult.equalsIgnoreCase("success")) {
                info(String.format("Build %s is successful! Taking its timestamp of %s", currentBuildId, currentBuildTimestamp));
                return currentBuildTimestamp;
            } else if (!currentBuildResult.equalsIgnoreCase("failure")) {
                info(String.format("Ignoring build #%s as build result type not supported:%s", currentBuildId, currentBuildResult));
                continue;
            }

            float currentBuildFailurePercentage = loadCurrentBuildFailurePercentage(currentBuildId);
            var decimalFormat = new DecimalFormat("##.##");
            String roundedFailurePercentage = decimalFormat.format(currentBuildFailurePercentage);
            if (currentBuildFailurePercentage < FAILURE_PERCENTAGE_LIMIT) {
                info(String.format("Build %s has a failure percentage of %s%%. Taking its timestamp of %s", currentBuildId, roundedFailurePercentage, currentBuildTimestamp));
                return currentBuildTimestamp;
            } else {
                info(String.format("Build %s has a failure percentage of %s%% which is higher then the limit set of %s%%. Not using it", currentBuildId, roundedFailurePercentage, FAILURE_PERCENTAGE_LIMIT));
            }
        }

        return -1;
    }

    private static float loadCurrentBuildFailurePercentage(String buildId) {
        if (buildId == null || buildId.isBlank()) return 100;

        String fullUrl = JENKINS_URL_PREFIX + System.getenv("JOB_BASE_NAME") + "/" + buildId + "/artifact/" + TEST_NG_SUMMARY_FILE_PATH;
        var response = internalGiven()
                .when()
                .get(fullUrl)
                .then()
                .extract().response();

        if (response.statusCode() != 200) return 100;

        return getFailurePercentage(response.body().asString());
    }

    private static String getBuildParameter(JSONObject currentBuild, String parameterName) {
        var actions = currentBuild.getJSONArray("actions");
        for (int i = 0; i < actions.length(); i++) {
            var currentAction = actions.getJSONObject(i);
            var currentActionClass = currentAction.has("_class") ? currentAction.getString("_class") : "CLASS_NOT_FOUND";
            if (currentActionClass.equalsIgnoreCase("hudson.model.ParametersAction")) {
                var parameters = currentAction.getJSONArray("parameters");
                for (int j = 0; j < parameters.length(); j++) {
                    var currentParameter = parameters.getJSONObject(j);
                    var currentParameterName = currentParameter.getString("name");
                    if (currentParameterName.equalsIgnoreCase(parameterName)) {
                        return currentParameter.get("value").toString();
                    }
                }
            }
        }
        return "PARAMETER_NOT_FOUND";
    }

    private static void triggerJob(long timestamp) {
        info(String.format("Trying to trigger the %s Jenkins job", JOB_NAME_TO_TRIGGER));

        // prepare the remote trigger url
        var url = String.format("%s%s/buildWithParameters", JENKINS_URL_PREFIX, JOB_NAME_TO_TRIGGER);

        // POST the request to the Jenkins server
        internalGiven()
                .queryParam(BRANCH_NAME, System.getenv(BRANCH_NAME))
                .queryParam(ENVIRONMENT, System.getenv(ENVIRONMENT))
                .queryParam("fromTimestamp", timestamp)
                .when()
                .post(url)
                .then()
                .log().ifError()
                .statusCode(201);
        info("Job successfully triggered!");
    }
}


