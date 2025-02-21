package io.securitize.infra.reporting;

import org.testng.ITestResult;

import java.util.Map;

/**
 * Interface containing all the methods used to info test status. Can be implemented by several
 * reporters and then all test logging will be done to all at the same time. */
public interface IReporter {

    void initializeSuite(String details);

    void startTest(String name, String description, String category);

    void startTestLevelAsTopLevel(String reportMessage);

    void startTestLevel(String reportMessage);

    void trace(String reportMessage);

    void debug(String reportMessage);

    void setTestDetails(String details);

    void setTestCategory(String category);

    void info(String reportMessage);

    void info(String reportTitle, String reportContent);

    void infoAndShowMessageInBrowser(String reportMessage);

    void warning(String reportMessage);

    void warning(String reportMessage, boolean addScreenshot);

    void error(String reportMessage);

    void error(String reportMessage, boolean addScreenshot);

    void skip(String message);

    void addVideoRecording(Map<String, String> nameAndPathToVideo);

    void addScreenshot(String description);

    void addImage(String message, String base64Image);

    void reportHTML(String html);

    void reportRawHTML(String html);

    void endTestLevel();

    void endTestLevel(boolean addScreenShot);

    void endTest(ITestResult result, String skippedReason);

    void addSkippedTest(String testName, String reason);

    void terminateSuite();
}