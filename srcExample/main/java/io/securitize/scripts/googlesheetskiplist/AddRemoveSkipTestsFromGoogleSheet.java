package io.securitize.scripts.googlesheetskiplist;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.dashboard.TestStatus;
import io.securitize.infra.dashboard.enums.TestCategory;
import io.securitize.infra.database.MySqlDatabase;
import io.securitize.infra.googleapi.GoogleSheets;
import io.securitize.infra.googleapi.TestsSkipList;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.utils.ResourcesUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.info;

public class AddRemoveSkipTestsFromGoogleSheet {

    private static final Map<TestsSkipList, SkipListConfiguration> addToSkipListConfiguration = new HashMap<>();
    static {
        addToSkipListConfiguration.put(TestsSkipList.API, new SkipListConfiguration(7, 5, 2, new String[] {"CICD_API", "NIGHTLY_API"}));
        addToSkipListConfiguration.put(TestsSkipList.UI, new SkipListConfiguration(7, 5, 3, new String[] {"NIGHTLY_E2E", "MANUAL_STABILIZATION"}));
    }

    private static final Map<TestsSkipList, SkipListConfiguration> removeFromSkipListConfiguration = new HashMap<>();
    static {
        removeFromSkipListConfiguration.put(TestsSkipList.API, new SkipListConfiguration(7, 3, 3, new String[] {"CICD_API", "NIGHTLY_API"}));
        removeFromSkipListConfiguration.put(TestsSkipList.UI, new SkipListConfiguration(7, 3, 3, new String[] {"NIGHTLY_E2E", "MANUAL_STABILIZATION"}));
    }

    private static final String SQL_PATH = "sql/getTestsNamesForSkipList.sql";
    private static final String REASON_PREFIX = "automatic addition";
    private static final String MARKED_AS_SKIPPED_BY = "securitize_automation";
    private static final String RESULTS_FILE_NAME = "results.log";

    private static final RunMode DEFAULT_MODE = RunMode.ADD;

    public static void main(String[] args) throws ExecutionException, InterruptedException, SQLException, IOException {
        // get mode to run in - add to skip list or remove
        RunMode mode = getMode();

        // get list of tests from the database
        List<TestStatus> testsFromDb = getTestsDetailsFromDb(mode);

        // for the flow of adding tests to the unstable tests list - ignore tests that are in a sanity group
        if (mode == RunMode.ADD) {
            testsFromDb = filterOutSanityTests(testsFromDb);
        }

        // filter tests by domain
        testsFromDb = filterTestsByDomain(testsFromDb);

        // now add / remove the list of tests from Google sheet
        if (isDryRun()) {
            StringBuilder stringBuilder = new StringBuilder();
            testsFromDb.forEach(x -> stringBuilder.append(x.getTestClass()).append(".").append(x.getTestName()).append(System.lineSeparator()));
            info(String.format("This is dry-run mode. Will not %s these tests: %n%s", mode, stringBuilder));
        } else {
            String results = runGoogleSheetLogic(mode, testsFromDb);
            // write results to file for Jenkins job to pick that up and send to Slack channel
            FileUtils.writeStringToFile(new File(RESULTS_FILE_NAME), results, StandardCharsets.UTF_8);
        }
    }


    private static RunMode getMode() {
        String runModeFromEnv = System.getenv("AddOrRemoveMode");
        if (runModeFromEnv != null) {
            info("Using mode from environment variable of 'AddOrRemoveMode': " + runModeFromEnv);
            return Enum.valueOf(RunMode.class, runModeFromEnv);
        } else {
            info("Can't find environment variable of 'AddOrRemoveMode', resulting to default value of: " + DEFAULT_MODE);
            return DEFAULT_MODE;
        }
    }

    private static List<TestStatus> getTestsDetailsFromDb(RunMode mode) throws IOException, SQLException {
        Map<TestsSkipList, SkipListConfiguration> addRemoveConfiguration;
        if (mode == RunMode.ADD) {
            addRemoveConfiguration = addToSkipListConfiguration;
        } else {
            addRemoveConfiguration = removeFromSkipListConfiguration;
        }

        // get list of tests from the dashboard db
        TestsSkipList key = getSkipsListType();
        SkipListConfiguration configuration = addRemoveConfiguration.get(key);
        String query = ResourcesUtils.getResourceContentAsString(SQL_PATH);
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("environment", environment);
        parameters.put("testType", key.toString().toUpperCase());
        parameters.put("testCategories", configuration.getTestCategories());
        parameters.put("maxDaysToScan", configuration.getMaxDaysToScan());
        parameters.put("runsToAnalyze", configuration.getRunsToAnalyze());
        if (mode == RunMode.ADD) {
            parameters.put("status", "FAIL");
        } else {
            parameters.put("status", "PASS");
        }
        parameters.put("minimalAmountOfRunsWithStatus", configuration.getMinimalAmountOfRunsWithStatus());
        return MySqlDatabase.query(query, "TEST_STATUS_MAPPING", parameters);
    }


    private static String runGoogleSheetLogic(RunMode mode, List<TestStatus> testsFromDb) throws IOException {
        if (mode == RunMode.ADD) {
            return GoogleSheets.addToSkipsList(testsFromDb, REASON_PREFIX, MARKED_AS_SKIPPED_BY);
        } else {
            return GoogleSheets.removeFromSkipsList(testsFromDb, REASON_PREFIX, MARKED_AS_SKIPPED_BY);
        }
    }

    private static TestsSkipList getSkipsListType() {
        TestCategory currentTestCategory = TestCategory.getTestCategory();
        if ((currentTestCategory == TestCategory.CICD_API) || (currentTestCategory == TestCategory.NIGHTLY_API)) {
            return TestsSkipList.API;
        } else if (currentTestCategory == TestCategory.NIGHTLY_E2E) {
            return TestsSkipList.UI;
        } else {
            return TestsSkipList.NONE;
        }
    }

    private static List<TestStatus> filterOutSanityTests(List<TestStatus> testsFromDb) {
        List<TestStatus> result = new ArrayList<>();

        for (TestStatus current : testsFromDb) {
            try {
                Optional<Method> currentMethod = Arrays.stream(Class.forName(current.getTestClass()).getMethods()).filter(x -> x.getName().equalsIgnoreCase(current.getTestName())).findFirst();
                if (currentMethod.isPresent()) {
                    String[] groupsInTestAnnotation = currentMethod.get().getAnnotation(Test.class).groups();
                    List<String> currentMethodSanityGroups = Arrays.stream(groupsInTestAnnotation).filter(x -> x.startsWith("sanity")).collect(Collectors.toList());
                    if (currentMethodSanityGroups.isEmpty()) {
                        result.add(current);
                    } else {
                        info(String.format("Removing test '%s.%s' from the list of tests to ignore, as it is a part of a sanity group: %s", current.getTestClass(), current.getTestName(), currentMethodSanityGroups));
                    }
                } else {
                    info(String.format("Can't find test method '%s.%s' via reflection. Not filtering it out", current.getTestClass(), current.getTestName()));
                    result.add(current);
                }
            } catch (Exception e) {
                info(String.format("Can't check if test method '%s.%s' is a part of a sanity group. Details: %s", current.getTestClass(), current.getTestName(), e));
                result.add(current);
            }
        }

        return result;
    }

    private static List<TestStatus> filterTestsByDomain(List<TestStatus> testsFromDb) {
        // if no filter value is set - no need to filter
        String domainFilterValue = System.getenv("DOMAINS_TO_ANALYZE");
        if (StringUtils.isBlank(domainFilterValue)) {
            info("No domain filter set by environment variable 'DOMAINS_TO_ANALYZE'. Filtering is skipped");
            return testsFromDb;
        }
        List<String> allowedDomain = Arrays.asList(domainFilterValue.split(","));

        info("Original test count: " + testsFromDb.size());
        List<TestStatus> filteredTests = testsFromDb.stream()
                .filter(x -> allowedDomain.contains(RegexWrapper.getDomainNameFromClassName(x.getTestClass())))
                .collect(Collectors.toList());

        info(String.format("Test count after filtering by domains (%s): %s", domainFilterValue, filteredTests.size()));
        return filteredTests;
    }

    private static boolean isDryRun() {
        String isDryRun = System.getenv("isDryRun");
        return isDryRun != null && isDryRun.trim().equalsIgnoreCase("true");
    }
}