package infra.reporting;

import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

import static io.restassured.RestAssured.given;

public class ReportPortalReporter implements IReporter {

    private static final boolean IS_DEBUG_MODE = false;
   // private static final String VIDEO_ICON_SOURCE = "https://securitize-dev-moon.s3.us-east-2.amazonaws.com/ReportPortalAssets/playVideo.png";

    private static String token;
    private static String launchId = null;
    private static String globalTestId = null;
    private static String baseUri;
    private static String projectName;
    private static final ThreadLocal<Stack<FrameDetails>> testAndNestedId = new ThreadLocal<>();
    private static RetryPolicy<Object> retryPolicy;

    // used to make sure we don't get stuck in a stackoverflow - trying to write a warning when can't
    // write a message to ReportPortal - thus failing to write the warning and so on...
    private static final ThreadLocal<Boolean> isInsideStepReporting = new ThreadLocal<>();

    private synchronized void authenticate() {
        try {
            if (token == null || token.isEmpty()) {
                // obtain UI-token
                var response = given()
                        .baseUri(baseUri)
                        .contentType(ContentType.URLENC)
                        .formParam("grant_type", "password")
                       // .formParam("username", Users.getProperty(UsersProperty.reportsPortalUsername))
                      //  .formParam("password", Users.getProperty(UsersProperty.reportsPortalPassword))
                        .auth().basic("ui", "uiman")
                        .post("uat/sso/oauth/token")
                        .then()
                        .log().ifError()
                        .statusCode(200)
                        .extract().response();
                String uiToken = response.jsonPath().getString("access_token");

                // use the UI-Token to obtain longer lasting API-token
                response = given()
                        .baseUri(baseUri)
                        .auth().oauth2(uiToken)
                        .get("uat/sso/me/apitoken")
                        .then()
                        .log().ifError()
                        .statusCode(200)
                        .extract().response();
                token = response.jsonPath().getString("access_token");
                MultiReporter.info("Successfully connected to ReportsPortal!");
            }
        } catch (Exception | AssertionError e) {
            MultiReporter.warning("Can't connect to ReportPortal. Will not report to it: " + ExceptionUtils.getStackTrace(e));
        }
    }

    private RequestSpecification getBaseGiven() {
        return given()
                .baseUri(baseUri)
                .auth().oauth2(token);
    }

    private void initRetryPolicy() {
        retryPolicy = RetryPolicy.builder()
                .handle(Throwable.class)
                .withDelay(Duration.ofSeconds(3))
                .withMaxRetries(5)
                .onRetry(e -> MultiReporter.warning("An error occur trying to write log details to ReportPortal. Details: " + e, false))
                .build();
    }

    private void runWithRetry(Runnable function) {
        if (isInsideStepReporting.get() == null) {
            isInsideStepReporting.set(false);
        }

        if (!isInsideStepReporting.get()) {
            try {
                isInsideStepReporting.set(true);
                Failsafe.with(retryPolicy).run(function::run);
            } catch (Exception | AssertionError e) {
                MultiReporter.warning("Some events couldn't be reported to ReportPortal", false);
            } finally {
                isInsideStepReporting.set(false);
            }
        }
    }

    private String runWithRetryAndResult(Supplier<String> function) {
        if (isInsideStepReporting.get() == null) {
            isInsideStepReporting.set(false);
        }

        if (!isInsideStepReporting.get()) {
            try {
                isInsideStepReporting.set(true);
                return Failsafe.with(retryPolicy).get(function::get);
            } catch (Exception | AssertionError e) {
                System.out.println("catching exception before failure");
            } finally {
                isInsideStepReporting.set(false);
            }
        }
        return "-1";
    }

/*    @Override
    public void initializeSuite(String details) {
        // only log to reportsPortal when running inside Jenkins
        if (!IS_DEBUG_MODE && !isRunningInsideJenkins()) return;

        if (IS_DEBUG_MODE) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
        baseUri = MainConfig.getProperty(MainConfigProperty.reportPortalBaseUri);
        projectName = MainConfig.getProperty(MainConfigProperty.reportPortalProjectName);

        token = Users.getProperty(UsersProperty.reportsPortalToken);
        initRetryPolicy();
        authenticate();

        if (token != null) {
            try {
                String suiteName = details.split("=>")[0].trim();
                internalStartSuite(suiteName);
                internalStartGlobalTest(suiteName);
            } catch (Exception | AssertionError e) {
                MultiReporter.warning("Can't connect to ReportsPortal. Will not report to it:" + ExceptionUtils.getStackTrace(e));
            }
        }
    }*/

  /*  private void internalStartSuite(String suiteName) {
        JSONArray attributes = buildAttributesObject();
        JSONObject body = new JSONObject()
                .put("name", suiteName)
                .put("startTime", System.currentTimeMillis())
                .put("attributes", attributes);

        // if running in Jenkins and able to find job url - add it as launch description
        String buildUrl = System.getenv("BUILD_URL");
        if (buildUrl != null && !buildUrl.isEmpty()) {
            body.put("description", "Link to build: " + buildUrl);
        }

        runWithRetry(() -> {
            Response response = getBaseGiven()
                    .contentType(ContentType.JSON)
                    .body(body.toString())
                    .post(String.format("api/v1/%s/launch", projectName))
                    .then()
                    .log().ifError()
                    .statusCode(201)
                    .extract().response();
            launchId = response.jsonPath().getString("id");
        });

        buildDirectLink();
    }*/

/*    private JSONArray buildAttributesObject() {
        JSONArray attributes = new JSONArray();

        String triggeringUserName = getValueOrUnknown(this::getTriggeringUsername);
        attributes.put(new JSONObject().put("key", "triggeredBy").put("value", triggeringUserName));

        attributes.put(new JSONObject().put("key", "env").put("value", MainConfig.getProperty(MainConfigProperty.environment)));

        String branchName = getValueOrUnknown(() -> System.getenv("branchName"));
        attributes.put(new JSONObject().put("key", "branch").put("value", branchName));

        String domainsToTest = getValueOrUnknown(() -> System.getenv("DOMAINS_TO_TEST"));
        attributes.put(new JSONObject().put("key", "domains").put("value", domainsToTest));

        String browserLocation = getValueOrUnknown(() -> System.getenv("browserLocation"));
        attributes.put(new JSONObject().put("key", "browserLocation").put("value", browserLocation));

        return attributes;
    }*/

    private String getValueOrUnknown(Supplier<String> function) {
        String result = function.get();
        if (result == null || result.isBlank()) {
            return "unknown";
        }
        return result;
    }

/*    private void buildDirectLink() {
        runWithRetry(() -> {
            // use api call to obtain id of build
            Response response = getBaseGiven()
                    .contentType(ContentType.JSON)
                    .queryParam("filter.eq.uuid", launchId)
                    .get(String.format("api/v1/%s/launch", projectName))
                    .then()
                    .log().ifError()
                    .statusCode(200)
                    .extract().response();
            long buildId = response.jsonPath().getLong("content[0].id");

            String fullReportPortalLaunchUrl = String.format(
                    MainConfig.getProperty(MainConfigProperty.reportPortalExternalLaunchUri),
                    MainConfig.getProperty(MainConfigProperty.reportPortalProjectName),
                    buildId);

            MultiReporter.info("Link to Launch in reportPortal: " + fullReportPortalLaunchUrl);
            try {
                FileUtils.writeStringToFile(new File("reportPortalLink.txt"), fullReportPortalLaunchUrl, StandardCharsets.UTF_8);
            } catch (IOException e) {
                MultiReporter.error("An error occur trying to write reportPortalLink.txt. Details: " + e, false);
            }
        });
    }*/

    private void internalStartGlobalTest(String suiteName) {
        if (launchId != null) {
            // build body
            JSONObject body = new JSONObject()
                    .put("name", suiteName)
                    .put("startTime", System.currentTimeMillis())
                    .put("type", "test")
                    .put("launchUuid", launchId)
                    .put("description", suiteName);

            runWithRetry(() -> {
                Response response = getBaseGiven()
                        .contentType(ContentType.JSON)
                        .body(body.toString())
                        .post(String.format("api/v1/%s/item", projectName))
                        .then()
                        .log().ifError()
                        .statusCode(201)
                        .extract().response();
                globalTestId = response.jsonPath().getString("id");
            });
        }
    }

    private boolean isRunningInsideJenkins() {
        return System.getenv("JENKINS_URL") != null;
    }

    private String getTriggeringUsername() {
        if (isRunningInsideJenkins()) {
            return System.getenv("BUILD_USER");
        } else {
            return System.getProperty("user.name");
        }
    }


    private String internalStartStep(String name, boolean isTest) {
        JSONObject body = new JSONObject()
                .put("name", name)
                .put("startTime", System.currentTimeMillis())
                .put("type", "step")
                .put("hasStats", isTest)
                .put("launchUuid", launchId);

        String parentItem;
        if (isTest) {
            parentItem = globalTestId;
        } else {
            parentItem = testAndNestedId.get().peek().getId();
        }

        return runWithRetryAndResult(() -> {
            Response response = getBaseGiven()
                    .contentType(ContentType.JSON)
                    .body(body.toString())
                    .post(String.format("api/v1/%s/item/%s", projectName, parentItem))
                    .then()
                    .log().ifError()
                    .statusCode(201)
                    .extract().response();
            return response.jsonPath().getString("id");
        });
    }

    @Override
    public void initializeSuite(String details) {

    }

    @Override
    public void startTest(String name, String description, String category) {
        isInsideStepReporting.remove();
        isInsideStepReporting.set(false);

        if (launchId != null) {
            testAndNestedId.remove();
            testAndNestedId.set(new Stack<>());

            testAndNestedId.get().push(new FrameDetails(internalStartStep(name, true)));
        }
    }

    @Override
    public void startTestLevelAsTopLevel(String reportMessage) {
        if (launchId != null) {
            while (testAndNestedId.get().size() > 1) {
                endTestLevel();
            }
            startTestLevel(reportMessage);
        }
    }

    @Override
    public void startTestLevel(String reportMessage) {
        if (launchId != null && testAndNestedId.get() != null && testAndNestedId.get().peek() != null) {
            testAndNestedId.get().push(new FrameDetails(internalStartStep(reportMessage, false)));
        }
    }

    @Override
    public void endTestLevel() {
        // only allow to end the level if we are not at the top level = test level instead of nested level
        if (launchId == null || testAndNestedId.get() == null || testAndNestedId.get().size() <= 1) {
            return;
        }

        // build body
        JSONObject body = new JSONObject()
                .put("endTime", System.currentTimeMillis())
                .put("status", testAndNestedId.get().peek().getWorstLevel().getStatusForReport())
                .put("launchUuid", launchId);

        runWithRetry(() -> getBaseGiven()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .put(String.format("api/v1/%s/item/%s", projectName, testAndNestedId.get().pop().getId()))
                .then()
                .log().ifError()
                .statusCode(200));
    }

    @Override
    public void endTestLevel(boolean addScreenShot) {
        endTestLevel();
        if (addScreenShot) {
            addScreenshot("end level screenshot");
        }
    }

    @Override
    public void endTest(ITestResult result, String skippedReason) {

    }

    @Override
    public void trace(String reportMessage) {
        // generalLog("trace", reportMessage);
    }

    @Override
    public void debug(String reportMessage) {
        // generalLog("debug", reportMessage);
    }

    @Override
    public void setTestDetails(String details) {

    }

    @Override
    public void setTestCategory(String category) {

    }

    @Override
    public void warning(String reportMessage) {
        generalLog("warn", reportMessage);
        updateFrameLevel(LogLevel.warn);
    }

    @Override
    public void warning(String reportMessage, boolean addScreenshot) {
        warning(reportMessage);
        if (addScreenshot) {
            addScreenshot("Warning!");
        }
    }

    @Override
    public void info(String reportMessage) {
        generalLog("info", reportMessage);
    }

    @Override
    public void info(String reportTitle, String reportContent) {
        info(reportTitle + ": " + reportContent);
    }

    @Override
    public void infoAndShowMessageInBrowser(String reportMessage) {
        info(reportMessage);
    }

    @Override
    public void error(String reportMessage) {
        generalLog("error", reportMessage);
        updateFrameLevel(LogLevel.error);
    }

    @Override
    public void error(String reportMessage, boolean addScreenshot) {
        error(reportMessage);
        if (addScreenshot) {
            addScreenshot("Error!");
        }
    }

    @Override
    public void skip(String message) {
        warning("Skipped: " + message);
    }

    @Override
    public void addVideoRecording(Map<String, String> nameAndPathToVideo) {

    }

    @Override
    public void addScreenshot(String description) {

    }


   /* @Override
    public void addVideoRecording(Map<String, String> nameAndPathToVideo) {
        if (nameAndPathToVideo.isEmpty()) {
            info("no video paths were provided");
        } else {
            String videoTemplate = "<img style='width:30px;vertical-align:middle;margin-right:10px;' src='%s'/>[%s Video recording](%s)";
            for (String current : nameAndPathToVideo.keySet()) {
                if (nameAndPathToVideo.get(current).toLowerCase().endsWith("avi")) {
                    generalLog("info", "Can't add video preview as AVI files not supported in browsers", true);
                } else {
                    generalLog("info", String.format(videoTemplate, VIDEO_ICON_SOURCE, current, nameAndPathToVideo.get(current)), true);
                }
            }
        }
    }*/

    private void generalLog(String reportLevel, String reportMessage) {
        generalLog(reportLevel, reportMessage, false);
    }

    private void generalLog(String reportLevel, String reportMessage, boolean isTopLevel) {
        if ((token == null) || (launchId == null) || (testAndNestedId.get() == null)) return;

        String nestedId;
        if (isTopLevel) {
            nestedId = testAndNestedId.get().firstElement().getId();
        } else {
            nestedId = testAndNestedId.get().peek().getId();
        }

        // build body
        JSONObject body = new JSONObject()
                .put("launchUuid", launchId)
                .put("time", System.currentTimeMillis())
                .put("itemUuid", nestedId)
                .put("message", reportMessage)
                .put("level", reportLevel);

        runWithRetry(() -> getBaseGiven()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post(String.format("/api/v1/%s/log", projectName))
                .then()
                .log().ifError()
                .statusCode(201)
        );
    }

    private void attachFile(String message, String fileName, String fileMimeType, String base64Content) {
        if (launchId != null) {
            // build body
            JSONObject file = new JSONObject()
                    .put("name", fileName);

            JSONObject body = new JSONObject()
                    .put("launchUuid", launchId)
                    .put("time", System.currentTimeMillis())
                    .put("itemUuid", testAndNestedId.get().peek().getId())
                    .put("message", message)
                    .put("level", "info")
                    .put("file", file);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64Content));

            runWithRetry(() -> getBaseGiven()
                    .contentType(ContentType.MULTIPART)
                    .multiPart("json_request_part", "[" + body.toString() + "]", "application/json")
                    .multiPart("file", fileName, byteArrayInputStream, fileMimeType)
                    .post(String.format("/api/v1/%s/log", projectName))
                    .then()
                    .log().ifError()
                    .statusCode(201));
        }
    }

/*    @Override
    public void addScreenshot(String description) {
        String base64Screenshot = Browser.getScreenshotAsBase64();
        if (base64Screenshot != null) {
            attachFile(description, "screenshot.png", "image/png", base64Screenshot);
        }
    }*/

    @Override
    public void addImage(String message, String base64Image) {
        attachFile(message, "image.png", "image/png", base64Image);
    }

    @Override
    public void reportHTML(String html) {

    }

    @Override
    public void reportRawHTML(String html) {
        if (!html.contains("image/png;base64") && !html.contains("-image") && !html.contains("onclick")) {
            info(html);
        }
    }

    private static String convertStatus(int testNGStatus) {
        switch (testNGStatus) {
            case 1:
                return "passed";
            case -1:
            case 3:
                return "skipped";
            case 16:
                return "interrupted";
            default:
                return "failed";
        }
    }

  /*  @Override
    public void endTest(ITestResult result, String skippedReason) {
        if (launchId != null) {
            if (result != null) {
                if (skippedReason != null && result.getStatus() != ITestResult.SUCCESS && result.getStatus() != ITestResult.FAILURE) {
                    int attemptNumber = Retry.getTestRetryCounter(result.getName());
                    if (attemptNumber > 0) {
                        addDebuggingDetails(result);
                    } else {
                        skip("Test has been skipped: " + skippedReason);
                    }
                } else if (result.getStatus() == ITestResult.CREATED) {
                    if (testAndNestedId.get() != null) {
                        if (skippedReason != null) {
                            skip(skippedReason);
                        } else if (result.getThrowable() != null) {
                            error(ExceptionUtils.getStackTrace(result.getThrowable()));
                        } else {
                            error("Test didn't start, check for errors in the 'Before method' section");
                        }
                    }
                } else if (result.getStatus() == ITestResult.FAILURE) {
                    addDebuggingDetails(result);
                }
            }
            endTestInternal(result);
        }
    }

    private void endTestInternal(ITestResult testResult) {
        String result = "failed";
        if (testResult != null && !testResult.wasRetried()) {
            result = convertStatus(testResult.getStatus());
        }
        // build body
        JSONObject body = new JSONObject()
                .put("launchUuid", launchId)
                .put("status", result)
                .put("endTime", System.currentTimeMillis());

        String testId = testAndNestedId.get().elementAt(0).getId();

        runWithRetry(() -> getBaseGiven()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .put(String.format("api/v1/%s/item/%s", projectName, testId))
                .then()
                .log().ifError()
                .statusCode(200));
    }


    private void addDebuggingDetails(ITestResult result) {
        if (testAndNestedId.get() != null) {
            startTestLevelAsTopLevel("Debugging details");

            startTestLevel("Exception details");
            error(ExceptionUtils.getStackTrace(result.getThrowable()));
            endTestLevel();

            // if this is a UI test involving a browser
            if (Browser.getDriver() != null) {
                // screenshot
                try {
                    // try to get full screenshot, if not possible just the regular one
                    String base64Screenshot = Browser.getFullPageScreenshot();
                    if (base64Screenshot == null) {
                        base64Screenshot = Browser.getScreenshotAsBase64();
                    }
                    if (base64Screenshot != null) {
                        startTestLevel("Screenshot");
                        addScreenshot("last screenshot");
                        endTestLevel();
                    }
                } catch (Exception e) {
                    startTestLevel("Screenshot");
                    error("Unable to obtain browser screenshot. Details: " + e);
                    endTestLevel();
                }

                // browser console errors
                try {
                    String browserConsoleErrors = MultiReporter.getBrowserConsoleErrors();
                    if (browserConsoleErrors != null) {
                        startTestLevel("Browser console errors");
                        info(browserConsoleErrors);
                        endTestLevel();
                    }
                } catch (Exception e) {
                    startTestLevel("Browser console errors");
                    error("Unable to obtain browser console errors. Details: " + e);
                    endTestLevel();
                }

                // browser network errors
                String remoteSessionId = WebDriverFactory.getRemoteSessionId();
                String browserNetworkErrors = WebDriverFactory.getNetworkErrors(remoteSessionId);
                if (browserNetworkErrors.length() > 0) {
                    startTestLevel("Browser network errors");
                    error(browserNetworkErrors);
                    endTestLevel();
                } else {
                    startTestLevel("Browser network errors");
                    info("No browser network errors to report");
                    endTestLevel();
                }
            }
            endTestLevel();
        }
    }*/

    @Override
    public void addSkippedTest(String testName, String reason) {

    }

    @Override
    public void terminateSuite() {
        if (launchId != null) {
            internalEndGlobalTest();
            internalEndSuite();
        }
    }

    private void internalEndGlobalTest() {
        // build body
        JSONObject body = new JSONObject()
                .put("endTime", System.currentTimeMillis());

        runWithRetry(() -> getBaseGiven()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .put(String.format("api/v1/%s/item/%s", projectName, globalTestId))
                .then()
                .log().ifError()
                .statusCode(200));
    }

    private void internalEndSuite() {
        // build body
        JSONObject body = new JSONObject()
                .put("endTime", System.currentTimeMillis());

        runWithRetry(() -> getBaseGiven()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .put(String.format("api/v1/%s/launch/%s/finish", projectName, launchId))
                .then()
                .log().ifError()
                .statusCode(200));
    }

    private void updateFrameLevel(LogLevel level) {
        if (testAndNestedId.get() != null) {
            // update current frame and if needed all parent levels
            for (int i = testAndNestedId.get().size() - 1; i >= 1; i--) {
                FrameDetails currentFrame = testAndNestedId.get().get(i);
                if (currentFrame != null && level.getLevelValue() > currentFrame.getWorstLevel().getLevelValue()) {
                    currentFrame.setWorstLevel(level);
                }
            }
        }
    }

    static class FrameDetails {
        private final String id;
        private LogLevel worstLevel;

        public FrameDetails(String id) {
            this.id = id;
            this.setWorstLevel(LogLevel.info);
        }

        public String getId() {
            return id;
        }

        public LogLevel getWorstLevel() {
            return worstLevel;
        }

        public void setWorstLevel(LogLevel level) {
            this.worstLevel = level;
        }
    }

    enum LogLevel {
        trace(5000, "passed"),
        debug(10000, "passed"),
        info(20000, "passed"),
        warn(30000, "passed"),
        error(40000, "failed"),
        fatal(50000, "failed"),
        unknown(60000, "interrupted");

        private final int levelValue;
        private final String statusForReport;

        LogLevel(int level, String statusForReport) {
            this.levelValue = level;
            this.statusForReport = statusForReport;
        }

        public int getLevelValue() {
            return levelValue;
        }

        public String getStatusForReport() {
            return statusForReport;
        }
    }
}



