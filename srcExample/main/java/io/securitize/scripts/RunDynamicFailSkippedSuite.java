package io.securitize.scripts;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.dashboard.TestStatus;
import io.securitize.infra.database.MySqlDatabase;
import io.securitize.infra.utils.ResourcesUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class RunDynamicFailSkippedSuite extends AbstractRunDynamicSuite {

    private static final String SQL_PATH = "sql/getTestsNamesForDynamicFailSkipSuite.sql";

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        // load list of tests from Google sheets
        List<String> testMethodsToRun = getListOfMethodsToRunStatisticsDB();

        // find class containing desired test method including skip of annotation @SkipTestOnStabilization
        Map<String, List<String>> classesAndTestMethods = extractClassesFromTestMethodNames(testMethodsToRun);

        // run testNG
        int exitCode = runTests(classesAndTestMethods, "run failed/skipped tests");

        // workaround to avoid hanging due to daemon threads
        info("Workaround: kill all daemon threads");
        System.exit(exitCode);
    }

    private static List<String> getListOfMethodsToRunStatisticsDB() throws IOException, SQLException {
        String testCategory = System.getenv("testCategory");
        if (StringUtils.isEmpty(testCategory)) {
            errorAndStop("Can't find needed 'testCategory' environment variable", false);
        }

        String hoursBackToScan = System.getenv("hoursBackToScan");
        if (StringUtils.isEmpty(hoursBackToScan)) {
            errorAndStop("Can't find needed 'hoursBackToScan' environment variable", false);
        }

        String query = ResourcesUtils.getResourceContentAsString(SQL_PATH);
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("environment", environment);
        parameters.put("testCategories", List.of(testCategory, testCategory.replace("NIGHTLY", "CICD")));
        parameters.put("hoursBackToScan", hoursBackToScan);
        List<TestStatus> testsToRun = MySqlDatabase.query(query, "TEST_STATUS_MAPPING", parameters);
        info("Found " + testsToRun.size() + " tests to run: ");
        testsToRun.forEach(x -> info(x.getTestName()));

        return testsToRun.stream().map(TestStatus::getTestName).collect(Collectors.toList());
    }
}