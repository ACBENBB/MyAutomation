package infra.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;

import java.util.ArrayList;

public class MultiReporter {

    private static final ArrayList<IReporter> reporters = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(MultiReporter.class);

    public static void errorAndStop(String reportMessage, boolean addScreenshot) {
        logger.error(reportMessage, addScreenshot, false);
        throw new Error(reportMessage);
    }

    public static void info(String reportMessage) {
        for (IReporter current : reporters) {
            logger.info(reportMessage);
        }
    }

}
