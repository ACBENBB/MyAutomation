package io.securitize.scripts;

import io.securitize.infra.googleapi.GoogleSheets;
import io.securitize.infra.googleapi.SkipTestEntry;
import io.securitize.infra.googleapi.TestsSkipList;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This script loads the list of tests to be skipped as described in the Google skips sheet, and creates
 * a reminder file for each domain specifying the list of their unstable tests
 */
public class SendReminderUnstableTests {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // load list of tests from Google sheets
        Map<String, List<SkipTestEntry>> apiTestsToSkip = GoogleSheets.getTestsToSkipByList(TestsSkipList.API);
        Map<String, List<SkipTestEntry>> uiTestsToSkip = GoogleSheets.getTestsToSkipByList(TestsSkipList.UI);

        // find list of domains
        Set<String> domainsFound = extractDomains(apiTestsToSkip);
        domainsFound.addAll(extractDomains(uiTestsToSkip));

        // filter domains to analyze if requested so
        String domainsToTestEnvironmentVariableName = "DOMAINS_TO_TEST";
        if (System.getenv().containsKey(domainsToTestEnvironmentVariableName)) {
            String domainsFilterAsString = System.getenv(domainsToTestEnvironmentVariableName);
            List<String> domainsFilter = Arrays.asList(domainsFilterAsString.split(","));
            domainsFound.removeIf(x -> !domainsFilter.contains(x));
        }

        for (String currentDomain : domainsFound) {
            buildReport(currentDomain, apiTestsToSkip, uiTestsToSkip);
        }
    }

    private static Set<String> extractDomains(Map<String, List<SkipTestEntry>> input) {
        Set<String> result = new LinkedHashSet<>();

        for (String currentTest : input.keySet()) {
            List<SkipTestEntry> entries = input.get(currentTest);
            entries.forEach(x -> result.add(x.getDomain()));
        }

        return result;
    }

    private static void buildReport(String domain, Map<String, List<SkipTestEntry>> apiTestsToSkip, Map<String, List<SkipTestEntry>> uiTestsToSkip) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String currentDate = new Date().toString();
        stringBuilder.append(domain).append(" summary of skipped tests listed in \"Securitize Automation Stabilization - Skips list for API/E2E\": (Updated: ").append(currentDate).append(")\n");

        stringBuilder.append("API - skip tests summary:\n");
        String apiPerEnvironment = getTestCountPerEnvironment(domain, apiTestsToSkip);
        stringBuilder.append(apiPerEnvironment);

        stringBuilder.append("UI - skip tests summary:\n");
        String uiPerEnvironment = getTestCountPerEnvironment(domain, uiTestsToSkip);
        stringBuilder.append(uiPerEnvironment);

        System.out.println(stringBuilder);
        FileUtils.writeStringToFile(new File("unstable_" + domain + ".txt"), stringBuilder.toString(), StandardCharsets.UTF_8);
    }

    private static String getTestCountPerEnvironment(String domain, Map<String, List<SkipTestEntry>> testsToSkip) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Integer> counters = new HashMap<>();

        for (String currentTest : testsToSkip.keySet()) {
            List<SkipTestEntry> current = testsToSkip.get(currentTest);
            for (SkipTestEntry currentEntry : current) {
                if (currentEntry.getDomain().equalsIgnoreCase(domain)) {
                    String environmentValue = currentEntry.getEnvironment();
                    String[] splitEnvironments = environmentValue.split(",");
                    for (String currentEnvironment : splitEnvironments) {
                        String trimmedCurrentEnvironment = currentEnvironment.trim();
                        if (!counters.containsKey(trimmedCurrentEnvironment)) {
                            counters.put(trimmedCurrentEnvironment, 0);
                        }
                        counters.put(trimmedCurrentEnvironment, counters.get(trimmedCurrentEnvironment) + 1);
                    }
                }
            }
        }

        for (String currentEnvironment : counters.keySet()) {
            String testWord = "tests";
            if (counters.get(currentEnvironment) == 1) {
                testWord = "test";
            }
            stringBuilder.append("    ● ").append(currentEnvironment).append(": ").append(counters.get(currentEnvironment)).append(" ").append(testWord).append("\n");
        }

        if (stringBuilder.toString().isEmpty()) {
            return "    No unstable tests found ツ\n";
        } else {
            return stringBuilder.toString();
        }
    }
}