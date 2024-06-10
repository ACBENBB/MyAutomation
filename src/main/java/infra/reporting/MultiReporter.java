package infra.reporting;

import infra.reporting.ConsoleReporter;
import infra.webdriver.Browser;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.Map;

public class MultiReporter {

    private static final ArrayList<IReporter> reporters = new ArrayList<>();
    private static final ThreadLocal<String> currentStepDescription = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> hasErrorOccur = new ThreadLocal<>();
    private static final ThreadLocal<Throwable> errorException = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> isShowMessageInBrowser = new ThreadLocal<>();
    private static final ThreadLocal<String> browserConsoleErrors = new ThreadLocal<>();

    static {
        reporters.add(new ConsoleReporter());
        reporters.add(new ExtentReporter());
        reporters.add(new LogToFileReporter());
        reporters.add(new ReportPortalReporter());
    }

    public static void initializeSuite(String details) {    // Initializes the test suite with the given details
        for (IReporter current : reporters) {
            current.initializeSuite(details);
        }
    }

    public static void startTest(String name, String description, String category) { // Starts a new test with the given name, description, and category
        for (IReporter current : reporters) {
            current.startTest(name, description, category);
        }
        SetCurrentStepDescription(null);
        setShowMessageInBrowser(true);
        hasErrorOccur.set(false);
        errorException.set(null);
        browserConsoleErrors.remove();
    }

    public static void startTestLevelAsTopLevel(String reportMessage) { // Starts a test level as the top level with the given report message
        for (IReporter current : reporters) {
            current.startTestLevelAsTopLevel(reportMessage);
        }
    }

    public static void startTestLevel(String reportMessage) { // Starts a new test level with the given report message
        SetCurrentStepDescription(reportMessage);
        for (IReporter current : reporters) {
            current.startTestLevel(reportMessage);
        }
    }

    public static void trace(String reportMessage) { // Logs a trace message
        for (IReporter current : reporters) {
            current.trace(reportMessage);
        }
    }

    public static void debug(String reportMessage) { // Logs a debug message
        for (IReporter current : reporters) {
            current.debug(reportMessage);
        }
    }

    public static void setTestDetails(String details) {  // Sets the test details
        for (IReporter current : reporters) {
            current.setTestDetails(details);
        }
    }

    public static void setTestCategory(String category) { // Sets the test category
        for (IReporter current : reporters) {
            current.setTestCategory(category);
        }
    }

    public static void info(String reportMessage) { // Logs an info message
        for (IReporter current : reporters) {
            current.info(reportMessage);
        }
    }

    public static void setShowMessageInBrowser(boolean showMessage) { // Sets whether to show messages in the browser
        isShowMessageInBrowser.set(showMessage);
    }

    public static void infoAndShowMessageInBrowser(String reportMessage) { // Logs an info message and shows it in the browser if enabled
        for (IReporter current : reporters) {
            if (isShowMessageInBrowser.get() == null || isShowMessageInBrowser.get()) {
                current.infoAndShowMessageInBrowser(reportMessage);
            } else {
                current.info(reportMessage);
            }
        }
    }

    public static void info(String reportTitle, String reportContent) { // Logs an info message with a title and content
        for (IReporter current : reporters) {
            current.info(reportTitle, reportContent);
        }
    }

    public static void warning(String reportMessage) { // Logs a warning message
        for (IReporter current : reporters) {
            current.warning(reportMessage);
        }
    }

    public static void warning(String reportMessage, boolean addScreenshot) { // Logs a warning message and adds a screenshot if specified
        for (IReporter current : reporters) {
            current.warning(reportMessage, addScreenshot);
        }
    }

    public static void error(String reportMessage) { // Logs an error message
        for (IReporter current : reporters) {
            current.error(reportMessage);
        }
        hasErrorOccur.set(true);
        errorException.set(new RuntimeException("Errors occur during the test so finishing this test with failure. For additional details please check the log"));
    }

    public static void error(String reportMessage, boolean addScreenshot) { // Logs an error message and adds a screenshot if specified
        error(reportMessage, addScreenshot, true);
    }

    public static void error(String reportMessage, boolean addScreenshot, boolean setErrorFlag) {  // Logs an error message, adds a screenshot if specified, and sets the error flag
        for (IReporter current : reporters) {
            current.error(reportMessage, addScreenshot);
        }
        if (setErrorFlag) {
            hasErrorOccur.set(true);
            errorException.set(new RuntimeException("Errors occur during the test so finishing this test with failure. For additional details please check the log"));
        }
    }

    public static void skip(String message) { // Logs a skip message
        for (IReporter current : reporters) {
            current.skip(message);
        }
    }

    public static void errorAndStop(String reportMessage, boolean addScreenshot) { // Logs an error message, adds a screenshot if specified, and stops the execution

        error(reportMessage, addScreenshot, false);
        throw new Error(reportMessage);
    }

    public static void errorAndStop(String reportMessage, boolean addScreenshot, RuntimeException exceptionToThrow) {
        error(reportMessage, addScreenshot, false);
        throw exceptionToThrow;
    }

    public static void addVideoRecording(Map<String, String> nameAndPathToVideo) { // Logs an error message, adds a screenshot if specified, and throws the specified exception
        for (IReporter current : reporters) {
            current.addVideoRecording(nameAndPathToVideo);
        }
    }

    public static void addScreenshot(String description) { // Adds a screenshot to the report
        for (IReporter current : reporters) {
            current.addScreenshot(description);
        }
    }

    public static void addImage(String message, String base64Image) {  // Adds an image to the report
        for (IReporter current : reporters) {
            current.addImage(message, base64Image);
        }
    }


    public static void reportHTML(String html) { // Reports an HTML snippet to the report
        for (IReporter current : reporters) {
            current.reportHTML(html);
        }
    }

    public static void reportRawHTML(String html) { // Reports a raw HTML snippet to the report
        for (IReporter current : reporters) {
            current.reportRawHTML(html);
        }
    }

    public static void endTestLevel() { // Ends the current test level
        SetCurrentStepDescription(null);
        for (IReporter current : reporters) {
            current.endTestLevel();
        }
    }

    public static void endTestLevel(boolean addScreenShot) { // Ends the current test level and adds a screenshot if specified
        SetCurrentStepDescription(null);
        for (IReporter current : reporters) {
            current.endTestLevel(addScreenShot);
        }
    }

    public static void endTest(ITestResult result, String skippedReason) { // Ends the current test with the given result and skipped reason
        for (IReporter current : reporters) {
            current.endTest(result, skippedReason);
        }
    }

    public static void addSkippedTest(String testName, String reason) { // Adds a skipped test with the given name and reason
        for (IReporter current : reporters) {
            if (!(current instanceof infra.reporting.ExtentReporter))
                current.addSkippedTest(testName, reason);
        }
    }

    public static void terminateSuite() {
        for (IReporter current : reporters) {  // Terminates the test suite
            current.terminateSuite();
        }
    }

    public static String getBrowserConsoleErrors() { // Gets the console errors from the browser
        if (browserConsoleErrors.get() == null) {
            browserConsoleErrors.set(Browser.getBrowserConsoleErrors());
        }
        return browserConsoleErrors.get();
    }

    public static boolean isErrorOccur() { // Checks if any error occurred during the test
        return hasErrorOccur.get();
    }

    public static Throwable getErrorException() { // Gets the exception that caused the error, if any
        return errorException.get();
    }

    public static String getCurrentStepDescription() { // Gets the description of the current step
        return currentStepDescription.get();
    }

    private static void SetCurrentStepDescription(String value) { // Sets the description of the current step
        currentStepDescription.set(value);
    }

}