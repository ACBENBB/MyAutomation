package infra.reporting;

import infra.utils.ResourcesUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.testng.ITestResult;

import java.io.File;
import java.util.Map;

public class LogToFileReporter implements IReporter {

    static {
        String configFilePath = ResourcesUtils.getResourcePathByName("config" + File.separator + "log4j2.xml");
        File file = new File(configFilePath);
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        context.setConfigLocation(file.toURI());
    }

    private static final Logger logger = LogManager.getLogger(LogToFileReporter.class);

    @Override
    public void initializeSuite(String details) {
        logger.info("Starting Suite: " + details, false);
    }

    @Override
    public void startTest(String name, String description, String category) {
        logger.info(String.format("Starting test: name='%s', description='%s', category='%s'", name, description, category));
    }

    public void startTestLevelAsTopLevel(String reportMessage) {
        startTestLevel(reportMessage);
    }

    @Override
    public synchronized void startTestLevel(String reportMessage) {
        logger.info("New level: " + reportMessage, false);
    }

    @Override
    public void trace(String reportMessage) {
        logger.trace(reportMessage);
    }

    @Override
    public void debug(String reportMessage) {
        logger.debug(reportMessage);
    }

    @Override
    public void setTestDetails(String details) {
        logger.info("Setting additional test details: " + details);
    }

    @Override
    public void setTestCategory(String category) {
        logger.info("Setting test category: " + category);
    }

    @Override
    public void info(String reportMessage) {
        logger.info(reportMessage);
    }

    @Override
    public void info(String reportTitle, String reportContent) {
        logger.info(reportTitle + " : " + reportContent);
    }

    @Override
    public void infoAndShowMessageInBrowser(String reportMessage) {
        info(reportMessage);
    }

    @Override
    public void warning(String reportMessage) {
        logger.warn(reportMessage);
    }

    @Override
    public void warning(String reportMessage, boolean addScreenshot) {
        logger.warn(reportMessage + ". Add screenshot: " + addScreenshot);
    }

    @Override
    public void error(String reportMessage) {
        logger.error(reportMessage);
    }

    @Override
    public void error(String reportMessage, boolean addScreenshot) {
        logger.error(reportMessage + ". Add screenshot: " + addScreenshot);
    }

    @Override
    public void skip(String message) {
        logger.warn(message);
    }

    @Override
    public void addVideoRecording(Map<String, String> nameAndPathToVideo) {
        nameAndPathToVideo.forEach((name, path) -> info("Video file can be found under: "
                + String.join(System.lineSeparator() + "or" + System.lineSeparator(), path)));
    }

    @Override
    public void addScreenshot(String description) {
        logger.info("Adding screenshot with description: " + description);
    }

    @Override
    public void addImage(String message, String base64Image) {
        logger.info(String.format("Adding image with message %s", message));
    }

    @Override
    public void reportHTML(String html) {
        logger.info("Reporting HTML content: " + System.lineSeparator() + html);
    }

    @Override
    public void reportRawHTML(String html) {
        logger.info("Reporting raw HTML content: " + System.lineSeparator() + html);
    }

    @Override
    public synchronized void endTestLevel() {
        logger.info("Finished level");
    }

    @Override
    public void endTestLevel(boolean addScreenShot) {
        logger.info("Finished level. AddScreenshot? " + addScreenShot);
    }

    @Override
    public void endTest(ITestResult result, String skippedReason) {
        if (result != null) {
            if (skippedReason != null) {
                logger.info("Test skipped: " + skippedReason);
            } else {
                logger.info("Test finished with status: " + result.getStatus());
            }

            if (result.getThrowable() != null) {
                logger.error("throwable: " + ExceptionUtils.getStackTrace(result.getThrowable()));
            }
        }
    }

    @Override
    public void addSkippedTest(String testName, String reason) {
        String message = "Skipped test: " + testName;
        if (reason != null) {
            message += " due to: " + reason;
        }
        logger.info(message);
    }

    @Override
    public void terminateSuite() {
        logger.info("Suite finished!");
    }
}