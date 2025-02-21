package io.securitize.infra.googleapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.dashboard.TestStatus;
import io.securitize.infra.dashboard.enums.TestCategory;
import io.securitize.infra.utils.DateTimeUtils;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.tests.abstractClass.AbstractTest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.testng.SkipException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.*;


public class GoogleSheets {
    private static final String INSERT_SKIP_DATE_COLUMN_NAME = "Insert Skip Date";
    private static final String DOMAIN_COLUMN_NAME = "Domain";
    private static final String SERVICE_NAME_COLUMN_NAME = "Service name";
    private static final String TEST_NAME_COLUMN_NAME = "Test Name";
    private static final String ENVIRONMENT_COLUMN_NAME = "Environment";
    private static final String SKIP_REASON_COLUMN_NAME = "Reason";
    private static final String MARKED_SKIP_BY_COLUMN_NAME = "Marked as skipped by";
    private static final String SKIP_ON_ALL_ENVIRONMENTS_VALUE = "all";
    private static final String ENVIRONMENT_SEPARATOR = ",";
    private static final String[] EXTRACT_DOMAIN_REGEX_OPTIONS = { "\\.cicd\\.(.*?)\\.", "\\.AUT\\d+_(.*?)_" };
    private static final String[] EXTRACT_SERVICE_NAME_REGEX_OPTIONS = { "\\.cicd\\..*\\.(.*)", "\\.AUT\\d+_(.*)" };
    private static final int DELAY_BETWEEN_READ_ATTEMPTS_SECONDS = 15;
    private static final int MAX_READ_RETRIES = 5;
    public static final int DEFAULT_COLUMNS_IN_SHEET = 26;
    public static final int NEEDED_COLUMNS_PER_SERVICE = 3;

    private static Map<String, List<SkipTestEntry>> testsToSkip = null;
    protected static Sheets sheetsService = null;
    private static boolean isPrintedTestsToBeSkippedList = false;


    public synchronized static void skipTestIfNeeded(String testName) {
        if (testsToSkip == null) {
            warning("List of tests to skip isn't loaded. Running current test anyway: " + testName, false);
            return;
        }
        Map<String, SkipTestEntry> filteredTestsToSkipByEnvironment = getFilteredTestsToSkip();
        if (filteredTestsToSkipByEnvironment.containsKey(testName)) {
            String message = "Skipping test as it is in the skip Google sheet. Details: " + filteredTestsToSkipByEnvironment.get(testName);
            debug(message);
            throw new SkipException(message);
        }
    }


    public synchronized static Map<String, SkipTestEntry> getTestsToSkip() {
        if (testsToSkip == null) {
            try {
                initTestsToSkip();
            } catch (Exception e) {
                warning("An error occur trying to obtain list of tests to skip. Will not skip any. Details: " + ExceptionUtils.getStackTrace(e), false);
            }
        }

        return getFilteredTestsToSkip();
    }

    public synchronized static Map<String, List<SkipTestEntry>> getTestsToSkipByList(TestsSkipList testsSkipList) {
        testsToSkip = null;
        try {
            initTestsToSkip(testsSkipList);
        } catch (Exception e) {
            warning("An error occur trying to obtain list of tests to skip. Will not skip any. Details: " + ExceptionUtils.getStackTrace(e), false);
        }

        return testsToSkip;
    }

    // filtered by environment
    private synchronized static Map<String, SkipTestEntry> getFilteredTestsToSkip() {
        Map<String, SkipTestEntry> result = new LinkedHashMap<>();
        if (testsToSkip == null) {
            return result; // if the list isn't initialized probably because of a bug. Don't block
        }
        String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);

        for (String currentTestName : testsToSkip.keySet()) {
            List<SkipTestEntry> currentEntries = testsToSkip.get(currentTestName);
            Optional<SkipTestEntry> possibleEntry = currentEntries.stream().filter(x -> x.getEnvironment().equalsIgnoreCase(SKIP_ON_ALL_ENVIRONMENTS_VALUE) || Arrays.stream(x.getEnvironment().split(ENVIRONMENT_SEPARATOR)).anyMatch(y -> y.equalsIgnoreCase(currentEnvironment))).findFirst();
            possibleEntry.ifPresent(skipTestEntry -> result.put(currentTestName, skipTestEntry));
        }

        // let's print to the console the list of tests to be skipped from the skips-sheet, but only once per execution
        if (!isPrintedTestsToBeSkippedList) {
            debug("Tests marked to be skipped in the skips sheet for the current environment: " + result.keySet());
            isPrintedTestsToBeSkippedList = true;
        }
        return result;
    }


    public static String addToSkipsList(List<TestStatus> testsToAddToSkipList, String reason, String markedBy) {
        initTestsToSkip(TestsSkipList.NONE);
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        List<List<Object>> valuesToAppend = new ArrayList<>();
        Map<String, SkipTestEntry> filteredByCurrentEnvironment = getFilteredTestsToSkip();
        for (TestStatus current : testsToAddToSkipList) {
            if (!filteredByCurrentEnvironment.containsKey(current.getTestName())) {
                List<Object> valueToAppend = Arrays.asList(
                        DateTimeUtils.currentDate("dd/MM/yyyy"),
                        RegexWrapper.getFirstGroupOrDefault(current.getTestClass(), EXTRACT_DOMAIN_REGEX_OPTIONS, "Error extracting domain"), // domain
                        RegexWrapper.getFirstGroupOrDefault(current.getTestClass(), EXTRACT_SERVICE_NAME_REGEX_OPTIONS, "Error extracting service name"), // service name
                        current.getTestName(),
                        environment,
                        reason + ": " + current.getFailureReason(),
                        markedBy);
                valuesToAppend.add(valueToAppend);
            }
        }

        if (valuesToAppend.size() > 0) {
            try {
                return appendValues(valuesToAppend);
            } catch (Exception e) {
                String message = "An error occur trying to append row to google sheet. Details: " + ExceptionUtils.getStackTrace(e);
                errorAndStop(message, false);
                return message;
            }
        } else {
            String result = "No new values to add to the Google Sheet";
            info(result);
            return result;
        }
    }

    private static TestsSkipList getSkipsListType() {
        TestCategory currentTestCategory = TestCategory.getTestCategory();
        if ((currentTestCategory == TestCategory.CICD_API) || (currentTestCategory == TestCategory.NIGHTLY_API)) {
            return TestsSkipList.API;
        } else if (currentTestCategory == TestCategory.NIGHTLY_E2E) {
            return TestsSkipList.UI;
        } else if (currentTestCategory == TestCategory.JENKINS || currentTestCategory ==TestCategory.MANUAL_STABILIZATION) {
            if (AbstractTest.isUiTest()) {
                return TestsSkipList.UI;
            } else {
                return TestsSkipList.API;
            }
        } else {
            return TestsSkipList.NONE;
        }
    }

    private static String getSpreadsheetId() {
        return Users.getProperty(UsersProperty.skipApiTestsSpreadsheetId);
    }


    protected static void initSheetsService() {
        if (sheetsService == null) {
            try {
                info("Initializing Google Sheets service...");
                String keyDetails = Users.getProperty(UsersProperty.googleSheetsKey);
                InputStream targetStream = new ByteArrayInputStream(keyDetails.getBytes());
                GoogleCredentials credentials = GoogleCredentials.fromStream(targetStream)
                        .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
                HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                        credentials);

                // Create the sheets API client
                sheetsService = new Sheets.Builder(new NetHttpTransport(),
                        GsonFactory.getDefaultInstance(),
                        requestInitializer)
                        .setApplicationName("Automation Sheet editor (append tests to skip)")
                        .build();
            } catch (IOException e) {
                errorAndStop("An error occur trying to init Google Sheets service. Details: " + ExceptionUtils.getStackTrace(e), false);
            }
        }
    }

    private static String appendValues(List<List<Object>> values) throws IOException {
        String spreadsheetId = getSpreadsheetId();
        TestsSkipList spreadsheetName = getSkipsListType();
        if (spreadsheetName == TestsSkipList.NONE) {
            return "Invalid spreadsheetName";
        }
        final String range = spreadsheetName.toString() + "!A:Z";

        initSheetsService();

        // Append values to the specified range.
        ValueRange body = new ValueRange()
                .setValues(values);
        sheetsService.spreadsheets().values().append(spreadsheetId, range, body)
                .setValueInputOption("USER_ENTERED")
                .execute();

        info("Added successfully " + values + " entries");
        return "Added successfully " + values.size() + " entries";
    }

    /**
     * Removes tests from the Google sheet skips list
     * @param testsToRemove list of tests details to be removed from the list if they are there
     * @param reason will only remove if the reason in the sheet matches the one provided
     * @param markedBy will only remove if the markedBy in the sheet matches the one provided
     * @throws IOException if removal error occurs
     */
    public static String removeFromSkipsList(List<TestStatus> testsToRemove, String reason, String markedBy) throws IOException {
        initTestsToSkip(TestsSkipList.NONE);

        //List<SkipTestEntry> currentSkipsList = new ArrayList<>(testsToSkip.values());
        List<String> currentSkipsListNames = new ArrayList<>(testsToSkip.keySet());
        String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);

        // find which tests are both in the ignoring list and also in the list of provided now stable tests
        List<Integer> rowIndicesToRemove = new ArrayList<>();
        // we perform a back loop to create a list of rows to remove starting from the bottom of the
        // table otherwise deleting top row will affect the indices of the next rows
        for (int i = currentSkipsListNames.size() - 1; i >= 0; i--) {
            List<SkipTestEntry> currentTestList = testsToSkip.get(currentSkipsListNames.get(i));
            Optional<SkipTestEntry> currentItemOptional = currentTestList.stream().filter(x -> x.getEnvironment().equalsIgnoreCase(SKIP_ON_ALL_ENVIRONMENTS_VALUE) || Arrays.stream(x.getEnvironment().split(ENVIRONMENT_SEPARATOR)).anyMatch(y -> y.equalsIgnoreCase(currentEnvironment))).findFirst();
            if (currentItemOptional.isPresent()) {
                SkipTestEntry currentItem = currentItemOptional.get();
                if ((currentItem.getMarkedSkippedBy().equalsIgnoreCase(markedBy)) && (currentItem.getReason().contains(reason))) {
                    String currentTestNameToCheck = currentItem.getName();
                    if (testsToRemove.stream().anyMatch(x -> x.getTestName().equalsIgnoreCase(currentTestNameToCheck))) {
                        info("Will take test named '" + currentTestNameToCheck + "' out of the skip list");
                        // we do i+2 to compensate for missing headers row and for list starting at index 0 (while rows start at 1)
                        rowIndicesToRemove.add(currentItem.getIndexInSheet());
                    }
                }
            }
        }
        if (rowIndicesToRemove.size() > 0) {
            info("Found " + rowIndicesToRemove.size() + " tests that are now stable and are in the Google skip tests sheet");
        } else {
            String message = "Didn't find any tests that are now stable and also appear in the Google skip sheet.";
            info(message);
            return message;
        }

        int sheetId = getSheetId();
        // define sheet indices to delete
        List<Request> requests = new ArrayList<>();
        for (int currentIndex : rowIndicesToRemove) {
            Request request = new Request()
                    .setDeleteDimension(new DeleteDimensionRequest()
                            .setRange(new DimensionRange()
                                    .setSheetId(sheetId)
                                    .setDimension("ROWS")
                                    .setStartIndex(currentIndex - 1)
                                    .setEndIndex(currentIndex)
                            )
                    );
            requests.add(request);
        }
        BatchUpdateSpreadsheetRequest content = new BatchUpdateSpreadsheetRequest();
        content.setRequests(requests);

        // send delete rows command to Google API
        String spreadsheetId = getSpreadsheetId();
        initSheetsService();
        Sheets.Spreadsheets.BatchUpdate deleteRequest = sheetsService.spreadsheets().batchUpdate(spreadsheetId, content);
        deleteRequest.execute();
        String message = requests.size() + " entries removed!";
        info(message);
        return message;
    }

    private static int getSheetId() throws IOException {
        TestsSkipList spreadsheetName = getSkipsListType();
        if (spreadsheetName == TestsSkipList.NONE) {
            return -1;
        }

        String spreadsheetId = getSpreadsheetId();
        initSheetsService();

        Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
        List<String> ranges = new ArrayList<>();
        request.setRanges(ranges);
        request.setIncludeGridData(false);

        Spreadsheet response = request.execute();

        Optional<Sheet> sheet = response.getSheets().stream().filter(x -> x.getProperties().getTitle().equalsIgnoreCase(spreadsheetName.toString())).findFirst();
        if (sheet.isPresent()) {
            return sheet.get().getProperties().getSheetId();
        } else {
            return -1;
        }
    }

    public static void initTestsToSkip() {
        initTestsToSkip(TestsSkipList.ALL);
    }

    public static void initTestsToSkip(TestsSkipList testsSkipList) {
        if (testsToSkip != null) return;
        testsToSkip = new LinkedHashMap<>();

        String spreadsheetId = getSpreadsheetId();

        List<String> sheetsToRead = new ArrayList<>();
        if (testsSkipList == TestsSkipList.ALL) {
            // add all real sheets mentioned in the enum, in the form of String
            sheetsToRead.addAll(Arrays.stream(TestsSkipList.values())
                    .filter(TestsSkipList::isRealSheet)
                    .map(Enum::toString)
                    .collect(Collectors.toList()));
        } else {
            TestsSkipList spreadSheetName = testsSkipList;
            if (spreadSheetName == TestsSkipList.NONE) {
                spreadSheetName = getSkipsListType();
            }
            if (spreadSheetName == TestsSkipList.NONE) {
                testsToSkip = new LinkedHashMap<>();
                return;
            }
            sheetsToRead.add(spreadSheetName.toString());
        }

        initSheetsService();
        readDataFromSheets(sheetsToRead, spreadsheetId);
    }

    private static void readDataFromSheets(List<String> sheetsToRead, String spreadsheetId) {
        RetryPolicy<Object> retryPolicy = RetryPolicy.builder()
                .handle(Exception.class)
                .withDelay(Duration.ofSeconds(DELAY_BETWEEN_READ_ATTEMPTS_SECONDS))
                .withMaxRetries(MAX_READ_RETRIES)
                .onFailedAttempt(e -> warning("Reading google sheet failed, will try again. Error: " + ExceptionUtils.getStackTrace(e.getLastException()), false))
                .onFailure(e -> errorAndStop("An error occur trying to read list of tests to skip from Google sheets. Details: " + ExceptionUtils.getStackTrace(e.getException()), false))
                .build();

        for (String currentSheet : sheetsToRead) {
            Failsafe.with(retryPolicy).run(x -> {
                final String range = currentSheet + "!A:Z";
                    // read values from the specified range.
                    Sheets.Spreadsheets.Values.Get request =
                            sheetsService.spreadsheets().values().get(spreadsheetId, range);
                    request.setValueRenderOption("UNFORMATTED_VALUE");
                    request.setDateTimeRenderOption("FORMATTED_STRING");

                    info("Reading skips list...");
                    ValueRange response = request.execute();
                    // we do -1 to not count the headers row
                    info("Found " + (response.getValues().size() - 1) + " entries");
                    parseSkipEntries(response.getValues());
            });
        }
    }

    private static void parseSkipEntries(List<List<Object>> values) {
        // parse headers and indices - which are in the first row
        List<Object> headersObject = values.get(0);
        int dateIndex = getColumnIndex(headersObject, INSERT_SKIP_DATE_COLUMN_NAME);
        int domainIndex = getColumnIndex(headersObject, DOMAIN_COLUMN_NAME);
        int serviceNameIndex = getColumnIndex(headersObject, SERVICE_NAME_COLUMN_NAME);
        int nameIndex = getColumnIndex(headersObject, TEST_NAME_COLUMN_NAME);
        int environmentIndex = getColumnIndex(headersObject, ENVIRONMENT_COLUMN_NAME);
        int reasonIndex = getColumnIndex(headersObject, SKIP_REASON_COLUMN_NAME);
        int markedSkippedByIndex = getColumnIndex(headersObject, MARKED_SKIP_BY_COLUMN_NAME);
        
        // convert generic objects to SkipTestEntry
        for (int i = 1; i < values.size(); i++) {
            List<Object> current = values.get(i);
            String currentTestName = current.get(nameIndex).toString();
            if (!testsToSkip.containsKey(currentTestName)) {
                testsToSkip.put(currentTestName, new ArrayList<>());
            }

            // validate value exists in mandatory fields
            String domain = getValue(current, domainIndex, null);
            String serviceName = getValue(current, serviceNameIndex, null);
            String environment = getValue(current, environmentIndex, null);
            if ((domain == null) || (serviceName == null) || (environment == null)) {
                String delimiter = "\t";
                String currentRowAsString = current.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(delimiter));
                warning("Invalid row in Google skip list. It will be ignored: " + currentRowAsString);
                continue;
            }

            testsToSkip.get(currentTestName).add(new SkipTestEntry(
                    getValue(current, dateIndex, "MISSING_DATE_VALUE"),
                    domain,
                    serviceName,
                    currentTestName,
                    environment,
                    getValue(current, reasonIndex, "MISSING_REASON_VALUE"),
                    getValue(current, markedSkippedByIndex, "MISSING_MARKED_SKIPPED_BY_VALUE"),
                    i+1));
        }
    }

    private static String getValue(List<Object> currentRow, int index, String defaultValue) {
        if (index < currentRow.size()) {
            return currentRow.get(index).toString();
        } else {
            return defaultValue;
        }
    }

    private static int getColumnIndex(List<Object> headersObject, String columnHeader) {
        int index = headersObject.indexOf(columnHeader);
        if (index < 0) {
            errorAndStop("Can't find column header named '" + columnHeader + "' in the Sheet. Make sure it didn't change!", false);
            return -1;
        }
        return index;
    }

    /**
     * Reads entire spreadsheet into java objects
     * @param spreadsheetId id of spreadsheet to read
     * @return map with key string - sheet name, and list of T values - rows
     * @param <T> POJO object
     */
    public static <T> Map<String,List<T>> readDataFromSheets(String spreadsheetId, Class<T> type) throws IOException {
        initSheetsService();

        // get list of sheets inside requested spreadsheet
        Sheets.Spreadsheets.Get request = sheetsService.spreadsheets().get(spreadsheetId);
        List<String> ranges = new ArrayList<>();
        request.setRanges(ranges);
        request.setIncludeGridData(false);

        Spreadsheet spreadsheet = request.execute();
        List<Sheet> sheets = spreadsheet.getSheets();

        Map<String, List<T>> result = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Sheet currentSheet : sheets) {
            String currentSheetName = currentSheet.getProperties().getTitle();
            ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, currentSheetName + "!A1:Z").execute();
            List<List<Object>> rows = response.getValues();

            List<T> currentSheetValues = new ArrayList<>();
            for (int i = 1; i < rows.size(); i++) {
                var headersRow = rows.get(0);
                var currentRow = rows.get(i);
                JSONObject currentRowAsJson = new JSONObject();

                // build a JSON from the current row with sheet headers
                for (int j = 0; j < headersRow.size(); j++) {
                    String currentHeaderAsString = headersRow.get(j).toString();
                    if (currentRow.size() > j) {
                        String currentValue = currentRow.get(j).toString();
                        currentRowAsJson.put(currentHeaderAsString, currentValue);
                    }
                }
                T currentRowAsPojo = mapper.readValue(currentRowAsJson.toString(), type);
                currentSheetValues.add(currentRowAsPojo);
            }
            result.put(currentSheetName, currentSheetValues);
        }

        return result;
    }
}