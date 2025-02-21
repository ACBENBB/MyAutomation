package io.securitize.scripts;

import io.securitize.infra.googleapi.GoogleSheets;
import io.securitize.infra.googleapi.SkipTestEntry;
import io.securitize.infra.googleapi.TestsSkipList;
import io.securitize.infra.reporting.MultiReporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.securitize.infra.reporting.MultiReporter.info;

public class RunDynamicStabilizationSuite extends AbstractRunDynamicSuite {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // load list of tests from Google sheets
        List<String> testMethodsToRun = getListOfMethodsToRunFromGoogleSheets();

        // find class containing desired test method including skip of annotation @SkipTestOnStabilization
        Map<String, List<String>> classesAndTestMethods = extractClassesFromTestMethodNames(testMethodsToRun);

        // run testNG
        int exitCode = runTests(classesAndTestMethods, "stabilization");

        // workaround to avoid hanging due to daemon threads
        info("Workaround: kill all daemon threads");
        System.exit(exitCode);
    }

    private static List<String> getListOfMethodsToRunFromGoogleSheets(){
        GoogleSheets.initTestsToSkip(TestsSkipList.NONE);
        Map<String, SkipTestEntry> fullTestsToSkip = GoogleSheets.getTestsToSkip();
        info("Found " + fullTestsToSkip.keySet().size() + " tests to run: ");
        fullTestsToSkip.keySet().forEach(MultiReporter::info);
        return new ArrayList<>(fullTestsToSkip.keySet());
    }
}