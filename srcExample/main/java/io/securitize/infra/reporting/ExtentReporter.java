package io.securitize.infra.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.ImageUtils;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.utils.Retry;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.webdriver.WebDriverFactory;
import io.securitize.tests.abstractClass.AbstractTest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Stack;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class ExtentReporter implements IReporter {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final ThreadLocal<Stack<ExtentTest>> nodesStacks = new ThreadLocal<>();
    private static final String UPDATE_SNAPSHOT_SCRIPT_PATH = "scripts/updateSnapshotBaseline.js";
    private static long lastFlushTime = -1;
    private static final long MAX_TIME_BETWEEN_FLUSHES_MILLISECONDS = 5 * 1000;

    @Override
    public void initializeSuite(String details) {
        extent = new ExtentReports();

        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("index.html");
        extentSparkReporter.config().setCss(".node-attr .badge-pill {display:none;}");
        // load custom js for visual regression testing - to allow updating the snapshot baseline
        // images from within the HTML report
        try {
            String s3FileUpdaterUrl = Users.getProperty(UsersProperty.s3fileupdaterurl);
            String script = ResourcesUtils.getResourceContentAsString(UPDATE_SNAPSHOT_SCRIPT_PATH);
            script = script.replace("[s3fileupdaterurl]", s3FileUpdaterUrl);

            extentSparkReporter.config().setJs(script);
        } catch (IOException e) {
            errorAndStop(String.format("Unable to load script file: %s. Details: %s", UPDATE_SNAPSHOT_SCRIPT_PATH, ExceptionUtils.getStackTrace(e)), false);
        }
        addSystemInfo();
        extent.attachReporter(extentSparkReporter);
    }

    private void addSystemInfo() {
        extent.setSystemInfo("OS name", System.getProperty("os.name"));
        extent.setSystemInfo("OS version", System.getProperty("os.version"));
        extent.setSystemInfo("OS architecture", System.getProperty("os.arch"));
        extent.setSystemInfo("Java version", System.getProperty("java.version"));
        extent.setSystemInfo("Account name", System.getProperty("user.name"));
        extent.setSystemInfo("Max memory", Runtime.getRuntime().maxMemory()+"");
        try {
            extent.setSystemInfo("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            // nothing we can do about such errors
        }
        extent.setSystemInfo("Browser type", MainConfig.getProperty(MainConfigProperty.browserType));
        extent.setSystemInfo("Environment", MainConfig.getProperty(MainConfigProperty.environment));
        extent.setSystemInfo("Starting time (UTC)", DateTimeUtils.currentDateTimeUTC());

    }

    @Override
    public void startTest(String name, String description, String category) {
        nodesStacks.set(new Stack<>());
        ExtentTest extentTest = extent.createTest(name, description);
        extentTest.assignCategory(category);
        addJenkinsLinkToStandaloneJob(extentTest);
        test.set(extentTest);
    }

    private void addJenkinsLinkToStandaloneJob(ExtentTest extentTest) {
        // check if running on Jenkins
        String jenkinsUrl = System.getenv("JENKINS_URL");
        if (jenkinsUrl != null) {
            String templateWithCss = "<div style='" +
                    "    background-color: lavender;" +
                    "    font-weight: bolder;" +
                    "    letter-spacing: 1px;" +
                    "    padding: 5px;'>" +
                    "    <span style='" +
                    "    font-size: x-large;" +
                    "    vertical-align: middle;'>▷</span>" +
                    "    <a href='%s' target='__blank'> Build job on Jenkins</a></div>";

            // try to get build number - if successful, the link will rebuild
            String buildNumber = System.getenv("BUILD_NUMBER");
            String jobName = System.getenv("JOB_NAME");
            String jobUrl = String.format("%sjob/%s/build?delay=0sec", jenkinsUrl, jobName);
            if (buildNumber != null) {
                jobUrl = String.format("%sjob/%s/%s/rebuild", jenkinsUrl, jobName, buildNumber);
            }


            String fullMessage = String.format(templateWithCss, jobUrl);
            extentTest.info(fullMessage);
        }
    }

    public void startTestLevelAsTopLevel(String reportMessage) {
        popToTop();
        startTestLevel(reportMessage);
    }

    @Override
    public void startTestLevel(String reportMessage) {
        if (test.get() != null) {
            nodesStacks.get().push(test.get());
            test.set(test.get().createNode(reportMessage));
        }
    }

    private ExtentTest getTestTopLevel() {
        if (nodesStacks.get() == null || nodesStacks.get().empty()) {
            return test.get();
        }

        return nodesStacks.get().get(0);
    }

    @Override
    public void trace(String reportMessage) {
        // do nothing - trace level messages will not appear in the HTML log
    }
    @Override
    public void debug(String reportMessage) {
        // do nothing - debug level messages will not appear in the HTML log
    }

    @Override
    public void setTestDetails(String details) {
        ExtentTest testTopLevel = getTestTopLevel();
        if (testTopLevel != null) {
            testTopLevel.assignAuthor(AbstractTest.getCurrentTestName() + " - " + details);
        }
    }

    @Override
    public void setTestCategory(String category) {
        ExtentTest testTopLevel = getTestTopLevel();
        if (testTopLevel != null) {
            // clear previous set category
            testTopLevel.getModel().getCategorySet().clear();

            // set new category
            testTopLevel.assignCategory(category);
        }
    }

    @Override
    public void info(String reportMessage) {
        if (test.get() != null) {
            test.get().info(cleanMessage(reportMessage).replace(System.lineSeparator(), "<br/>"));
        }
    }

    private String cleanMessage(String message) {
        return message.replace("<", "&lt;").replace(">", "&gt;");
    }

    @Override
    public void info(String reportTitle, String reportContent) {
        test.get().info(reportTitle + ": " + reportContent);
    }

    @Override
    public void infoAndShowMessageInBrowser(String reportMessage) {
        info(reportMessage);

        // browser logic is handled in the console reporter
    }

    @Override
    public void warning(String reportMessage) {
        warning(reportMessage, true);
    }

    @Override
    public void warning(String reportMessage, boolean addScreenshot) {
        if (test.get() != null) {
            test.get().warning(cleanMessage(reportMessage));
            if (addScreenshot) {
                addScreenshot("warning!");
            }
        }
    }

    @Override
    public void error(String reportMessage) {
        error(reportMessage, true);
    }

    @Override
    public void error(String reportMessage, boolean addScreenshot) {
        if (test.get() != null) {
            test.get().fail(cleanMessage(reportMessage));
            if (addScreenshot) {
                addScreenshot("error!");
            }
        }
    }

    @Override
    public void skip(String message) {
        if (test.get() != null) {
            popToTop();
            String name = test.get().getModel().getName();
            String description = test.get().getModel().getDescription();

            extent.removeTest(test.get());
            extent.createTest(name, description).skip(cleanMessage(message));
        }
    }

    @Override
    public void addVideoRecording(Map<String, String> nameAndPathToVideo) {
        ExtentTest level = getTestTopLevel();
        StringBuilder message = new StringBuilder("Video recording of test - <br/><br/>");

        if (nameAndPathToVideo.isEmpty()) {
            message.append("no video paths were provided");
        } else {
            StringBuilder videoSources = new StringBuilder();
            StringBuilder directLinksToSources = new StringBuilder();
            for (String current : nameAndPathToVideo.keySet()) {
                if (nameAndPathToVideo.get(current).toLowerCase().endsWith("avi")) {
                    message.append("<span style='color: lightgray;'>Can't add video preview as AVI files not supported in browsers...</span>");
                } else {
                    videoSources.append("<source src='").append(nameAndPathToVideo.get(current)).append("' type=video/mp4>");
                    directLinksToSources.append("<br/><a href='").append(nameAndPathToVideo.get(current)).append("' target='_blank'>link to <b>").append(current).append("</b> video recording").append("</a>");
                }
            }
            message.append("<br/><video preload='none' width='320' height='240' controls>").append(videoSources).append("Your browser does not support the video tag.</video>");
            message.append("<br/>For full screen: right click on video and choose to open it in a new tab<br/>");
            message.append(directLinksToSources);
            for (String current: nameAndPathToVideo.values()) {
                if (current.endsWith("video.mp4")) {
                    String logPath = current.replace("video.mp4", "session.log");
                    message.append(String.format("<br/><br/><a href='%s'>Link to <b>Moon</b> chromedriver logs", logPath));
                }
            }
        }
        if (level != null) {
            level.info(message.toString());
        }
    }

    @Override
    public void addScreenshot(String description) {
        String base64Screenshot = Browser.getScreenshotAsBase64();
        if (base64Screenshot != null) {
            // convert takes screenshot from base64 png to half size jpg image to make the HTML report smaller in
            // size and load faster
            String jpgBase64Screenshot = ImageUtils.base64PngToBase64Jpg(base64Screenshot);

            // if not specified, extent tries to add base64 images as png even if it is jpg - so we specify its type ourselves
            jpgBase64Screenshot = "data:image/jpg;base64," + jpgBase64Screenshot;
            test.get().addScreenCaptureFromBase64String(jpgBase64Screenshot, description);
        }
    }

    @Override
    public void addImage(String message, String base64Image) {
        if (test.get() != null) {
            // ignore visual regression image reports as images as due to extent bug they won't work
            // they are reported in addition as raw HTML
            if (!message.contains("-image")) {
                test.get().addScreenCaptureFromBase64String(base64Image, cleanMessage(message));
            }
        }
    }

    @Override
    public void reportHTML(String html) {
        if (test.get() != null) {
            String wrappedHTML = "<xmp>" + html + "</xmp>";
            test.get().info(wrappedHTML);
        }
    }

    @Override
    public void reportRawHTML(String html) {
        // ability to ignore messages intended for reportPortal
        if (!html.contains("ReportPortal?")) {
            test.get().info(html);
        }
    }

    @Override
    public void endTestLevel() {
//        endTestLevel(true);
        endTestLevel(false);
    }

    @Override
    public void endTestLevel(boolean addScreenShot) {
        if (addScreenShot) {
            addScreenshot("end level screenshot");
        }
        if ((nodesStacks.get() != null) && (nodesStacks.get().size() > 0)) {
            test.set(nodesStacks.get().pop());
        }
    }


    @Override
    public void endTest(ITestResult result, String skippedReason) {
        popToTop();

        if (result != null) {
            if (skippedReason != null && result.getStatus() != ITestResult.SUCCESS && result.getStatus() != ITestResult.FAILURE) {
                if (test.get() != null) {
                    String testName = test.get().getModel().getName();
                    int attemptNumber = Retry.getTestRetryCounter(testName);
                    if (attemptNumber > 0) {
                        test.get().getModel().setName(test.get().getModel().getName() + " - Execution #" + attemptNumber + " - Expected additional Rerun.");
                        addDebuggingDetails(result);
                    } else {
                        ExtentTest debuggingDetailsNode = test.get().createNode("Skipped details");
                        debuggingDetailsNode.skip("Test has been skipped: " + skippedReason);

                    }
                }
            }
            else if (result.getStatus() == ITestResult.CREATED) {
                if (test.get() != null) {
                    ExtentTest debuggingDetailsNode = test.get().createNode("Debugging details");
                    debuggingDetailsNode.fail("Test didn't start, check for errors in the 'Before method' section");
                }
            } else if (result.getStatus() == ITestResult.FAILURE) {
                addDebuggingDetails(result);
            }
        }

        if (shouldFlush()) {
            extent.flush();
        }
    }

    private synchronized boolean shouldFlush() {
        if (lastFlushTime < 0) {
            lastFlushTime = System.currentTimeMillis();
            return true;
        }

        long currentTime = System.currentTimeMillis();
        boolean shouldFlush = lastFlushTime + MAX_TIME_BETWEEN_FLUSHES_MILLISECONDS < currentTime;
        if (shouldFlush) {
            lastFlushTime = currentTime;
            return true;
        }

        return false;
    }

    private void addDebuggingDetails(ITestResult result) {
        if (test.get() != null) {
            ExtentTest debuggingDetailsNode = test.get().createNode("Debugging details");
            debuggingDetailsNode.createNode("Exception details").fail(result.getThrowable());

            // if this is a UI test involving a browser
            if (Browser.getDriver() != null) {
                // screenshot
                try {
                    // try to get full screenshot, if not possible just the regular one
                    var base64Screenshot = Browser.getScreenshotAsBase64();
                    if (base64Screenshot != null) {
                        String shrunkenScreenshot = ImageUtils.scaleImageSize(base64Screenshot, 0.75f);
                        debuggingDetailsNode.createNode("Screenshot").info(MediaEntityBuilder.createScreenCaptureFromBase64String(shrunkenScreenshot).build());
                    }
                } catch (Exception e) {
                    debuggingDetailsNode.createNode("Screenshot").fail("Unable to obtain browser screenshot. Details: " + e);
                }

                // browser console errors
                try {
                    String browserConsoleErrors = MultiReporter.getBrowserConsoleErrors();
                    if (browserConsoleErrors != null) {
                        debuggingDetailsNode.createNode("Browser console errors").info("<code>" + browserConsoleErrors + "</code>");
                    }
                } catch (Exception e) {
                    debuggingDetailsNode.createNode("Browser console errors").fail("Unable to obtain browser console errors. Details: " + e);
                }

                // browser network errors
                String remoteSessionId = WebDriverFactory.getRemoteSessionId();
                String browserNetworkErrors = WebDriverFactory.getNetworkErrors(remoteSessionId);
                if (browserNetworkErrors.length() > 0) {
                    debuggingDetailsNode.createNode("Browser network errors").fail("<code>" + browserNetworkErrors + "</code>");
                } else {
                    debuggingDetailsNode.createNode("Browser network errors").info("No browser network errors to report");
                }
            }
        }
    }

    // get to the top level
    private void popToTop() {
        if ((nodesStacks.get() != null) && (nodesStacks.get().size() > 0)) {
            test.set(nodesStacks.get().get(0));
            nodesStacks.get().empty();
        }
    }


    @Override
    public void addSkippedTest(String testName, String reason) {
        ExtentTest skippedTest = extent.createTest(testName, "This test has been skipped!");
        if (reason == null) {
            skippedTest.skip("This test has been skipped!");
        } else {
            skippedTest.skip("This test has been skipped due to: " + reason);
        }
    }


    @Override
    public void terminateSuite() {
        extent.flush();
    }
}
