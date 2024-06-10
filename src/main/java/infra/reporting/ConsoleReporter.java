package infra.reporting;
import infra.webdriver.Browser;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;
import tests.abstractClass.BaseTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

/**
 * Reports all test data to console
 * As the console is static, no need to bother about running parallel tests or calling the logging logic from
 * different parts of the code
 */
public class ConsoleReporter implements IReporter {

    private void generalLog(String message) {
        generalLog(message, true);
    }

    private String generalLog(String message, boolean printMessage) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String currentTestName = BaseTest.getCurrentTestName();
        if ((currentTestName.equalsIgnoreCase("Test name not set yet")) && (!Thread.currentThread().getName().startsWith("Thread"))) {
            currentTestName = Thread.currentThread().getName();
        }
        String output = String.format("%s - %s - %s", timeStamp, currentTestName, message);

        if (printMessage) {
            System.out.println(output);
        }

        return output;
    }

    @Override
    public void initializeSuite(String details) {
        String message = generalLog("Starting Suite: " + details, false);
        String separatorLine = String.join("", Collections.nCopies(message.length(), "="));

        System.out.println();
        System.out.println(separatorLine);
        System.out.println(message);
        System.out.println(separatorLine);
        System.out.println();
    }

    @Override
    public void startTest(String name, String description, String category) {
        String message = String.format("Starting test: name='%s', description='%s', category='%s'", name, description, category);
        generalLog(message);
    }

    public void startTestLevelAsTopLevel(String reportMessage) {
        startTestLevel(reportMessage);
    }

    @Override
    public synchronized void startTestLevel(String reportMessage) {
        String message = generalLog("New level: " + reportMessage, false);

        int messageLength = message.length();
        String separatorLine = String.join("", Collections.nCopies(messageLength, "-"));

        System.out.println(separatorLine);
        System.out.println(message);
        System.out.println(separatorLine);
    }

    @Override
    public void trace(String reportMessage) {
        // trace level has moved to log4j external log file
    }

    @Override
    public void debug(String reportMessage) {
        generalLog(reportMessage);
    }

    @Override
    public void setTestDetails(String details) {
        generalLog("Setting additional test details: " + details);
    }

    @Override
    public void setTestCategory(String category) {
        generalLog("Setting test category: " + category);
    }

    @Override
    public void info(String reportMessage) {
        generalLog(reportMessage);
    }

    @Override
    public void info(String reportTitle, String reportContent) {
        generalLog(reportTitle + " : " + reportContent);
    }

    @Override
    public void infoAndShowMessageInBrowser(String reportMessage) {
        info(reportMessage);

        try {
            Browser.showMessageInBrowser(reportMessage);
        } catch (Exception e) {
            warning("Unable to show message in browser. Details: " + e);
        }
    }

    @Override
    public void warning(String reportMessage) {
        generalLog("WARNING: " + reportMessage);
    }

    @Override
    public void warning(String reportMessage, boolean addScreenshot) {
        generalLog("WARNING: " + reportMessage + ". Add screenshot: " + addScreenshot);
    }

    @Override
    public void error(String reportMessage) {
        generalLog("FAIL: " + reportMessage);
    }

    @Override
    public void error(String reportMessage, boolean addScreenshot) {
        generalLog("FAIL: " + reportMessage + ". Add screenshot: " + addScreenshot);
    }

    @Override
    public void skip(String message) {
        generalLog("SKIP: " + message);
    }

    @Override
    public void addVideoRecording(Map<String, String> nameAndPathToVideo) {
        nameAndPathToVideo.forEach((name, path) -> info("Video file can be found under: "
                + String.join(System.lineSeparator() + "or" + System.lineSeparator(), path)));
    }

    @Override
    public void addScreenshot(String description) {
        generalLog("Adding screenshot with description: " + description);
    }

    @Override
    public void addImage(String message, String base64Image) {
        generalLog(String.format("Adding image with message %s", message));
    }

    @Override
    public void reportHTML(String html) {
        generalLog("Reporting HTML content: " + System.lineSeparator() + html);
    }
    @Override
    public void reportRawHTML(String html) {
        generalLog("Reporting raw HTML content of length: " + html.length());
    }

    @Override
    public synchronized void endTestLevel() {
        String message = "Finished level";
        String fullMessage = generalLog(message, false);

        int fullMessageLength = fullMessage.length();
        String separatorLine = String.join("", Collections.nCopies(fullMessageLength, "-"));

        System.out.println(separatorLine);
        System.out.println(fullMessage);
        System.out.println(separatorLine);
    }

    @Override
    public void endTestLevel(boolean addScreenShot) {
        generalLog("Finished level. AddScreenshot? " + addScreenShot);
    }

    @Override
    public void endTest(ITestResult result, String skippedReason) {
        if (result != null) {
            if (skippedReason != null) {
                generalLog("Test skipped: " + skippedReason);
            } else {
                generalLog("Test finished with status: " + result.getStatus());
            }

            if (result.getThrowable() != null) {
                generalLog("throwable: " + ExceptionUtils.getStackTrace(result.getThrowable()));
            }
        }
    }

    @Override
    public void addSkippedTest(String testName, String reason) {
        String message = "Skipped test: " + testName;
        if (reason != null) {
            message += " due to: " + reason;
        }
        generalLog(message);
    }

    @Override
    public void terminateSuite() {
        System.out.println();
        System.out.println("======================================================================");
        generalLog("Suite finished!");
        System.out.println("======================================================================");
        System.out.println();
    }
}