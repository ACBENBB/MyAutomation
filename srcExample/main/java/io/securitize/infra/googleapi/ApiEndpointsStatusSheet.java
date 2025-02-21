package io.securitize.infra.googleapi;

import com.google.api.services.sheets.v4.model.*;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.utils.DateTimeUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ApiEndpointsStatusSheet extends GoogleSheets {
    private static final String TOTAL_API_SHEET_NAME = "Total API";
    private static final String ERROR_SHEET_NAME = "Errors";
    private static final int SUMMARY_SHEETS_START_ROW = 0;
    private static final int SUMMARY_SHEET_START_COLUMN = 0;
    private static final int SUMMARY_SHEETS_TOTAL_ROW = 8;
    private static final int SUMMARY_SHEET_TOTAL_COLUMN = 0;
    public static final String SKIPPED = "SKIPPED";
    public static final String PASS = "PASS";
    public static final String FAILED = "FAILED";
    public static final String FAIL = "FAIL";
    public static final String NOT_IMPLEMENTED = "NOT IMPLEMENTED";
    public static final String TOTAL_IN_THE_FILE_TEAM = "total (in the file/Team)";
    public static final String USER_ENTERED_VALUE = "userEnteredValue";
    public static final String CENTER = "center";
    public static final String SOLID = "SOLID";

    private static Map<String, Integer> domainCounters;
    private static int newSheetId;

    // data format is complex:
    //     domain   service data: microServiceName         operationId, status
    // Map<String,                 Map<String,           Map<String,     String>>>
    public static void updateTestedApiEndpoints(Map<String, Map<String, Map<String, String>>> data, Map<String, List<String>> errors) throws IOException {
        String spreadsheetId = Users.getProperty(UsersProperty.CheckForApiChangesStatusUpdateSpreadsheetId);

        initSheetsService();
        Map<String, Map<String, Integer>> allDomainCounters = new HashMap<>();
        for (Map.Entry<String, Map<String, Map<String, String>>> currentEntry : data.entrySet()) {
            String currentDomainName = currentEntry.getKey();
            Map<String, Map<String, String>> currentDomainData = currentEntry.getValue();
            // init domain counters
            domainCounters = initCounters();

            // start batch request which will be used to send all update requests
            List<Request> requests = new ArrayList<>();

            // create a new sheet for each domain and delete the old one if exists
            newSheetId = createSheetRemoveOldIfNeeded(spreadsheetId, currentDomainName);

            // prepare content of the new sheet
            int rangeCounter = 0;

            // add empty columns to matched number of services
            Request addColumnsRequest = addColumnsRequest(currentDomainData);
            if (addColumnsRequest != null) {
                requests.add(addColumnsRequest);
            }

            // iterate on all services in current domain
            for (String currentServiceName : currentDomainData.keySet()) {
                List<Request> serviceRequests = buildServiceData(currentDomainData, currentServiceName, rangeCounter);
                requests.addAll(serviceRequests);
                rangeCounter += 3;
            }

            // add updated date as cell A1 and total sheet statistics
            requests.addAll(buildDomainHeaders());

            // Add column auto-resize request
            requests.add(buildAutoColumnsWidthRequest());

            // send all the requests
            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(requests);
            sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();

            allDomainCounters.put(currentDomainName, domainCounters);
        }

        buildSummarySheet(spreadsheetId, allDomainCounters);

        buildErrorsSheet(spreadsheetId, errors);
    }

    private static void buildSummarySheet(String spreadsheetId, Map<String, Map<String, Integer>> allDomainCounters) throws IOException {
        // start batch request which will be used to send all update requests
        List<Request> requests = new ArrayList<>();

        // add Total Api sheet
        newSheetId = createSheetRemoveOldIfNeeded(spreadsheetId, TOTAL_API_SHEET_NAME);

        int column = SUMMARY_SHEET_START_COLUMN;
        for (Map.Entry<String, Map<String, Integer>> currentEntry : allDomainCounters.entrySet()) {
            String currentDomain = currentEntry.getKey();
            Map<String, Integer> currentDomainCounters = allDomainCounters.get(currentDomain);

            int totalEndpointsCount = currentDomainCounters.values().stream().reduce(0, Integer::sum);
            List<List<Object>> values = new ArrayList<>(Arrays.asList(
                    Arrays.asList(currentDomain, ""),
                    Arrays.asList(PASS, currentDomainCounters.get(PASS)),
                    Arrays.asList(SKIPPED, currentDomainCounters.get(SKIPPED)),
                    Arrays.asList(FAILED, currentDomainCounters.get(FAIL)),
                    Arrays.asList(NOT_IMPLEMENTED, currentDomainCounters.get(NOT_IMPLEMENTED)),
                    Arrays.asList(TOTAL_IN_THE_FILE_TEAM, totalEndpointsCount)));
            List<RowData> rows = convertDataFormat(values);

            // add current service to the sheet
            GridRange gridRange = new GridRange()
                    .setSheetId(newSheetId) // Set the sheet ID to 0 for the new sheet
                    .setStartRowIndex(SUMMARY_SHEETS_START_ROW) // Start from row 1
                    .setStartColumnIndex(column) // Start from column A
                    .setEndColumnIndex(column + 2); // End column based on the number of columns in the data

            requests.add(new Request().setUpdateCells(new UpdateCellsRequest()
                    .setFields(USER_ENTERED_VALUE)
                    .setRange(gridRange)
                    .setRows(rows)));
            column += 2;
        }

        // add total of all domains
        int allPass = returnCalculatedSum(allDomainCounters, PASS);
        int allSkipped = returnCalculatedSum(allDomainCounters, SKIPPED);
        int allFailed = returnCalculatedSum(allDomainCounters, FAIL);
        int allNotImplemented = returnCalculatedSum(allDomainCounters, NOT_IMPLEMENTED);
        int all = allPass + allSkipped + allFailed + allNotImplemented;
        List<List<Object>> values = new ArrayList<>(Arrays.asList(
                Arrays.asList("Total", ""),
                Arrays.asList(PASS, allPass),
                Arrays.asList(SKIPPED, allSkipped),
                Arrays.asList(FAILED, allFailed),
                Arrays.asList(NOT_IMPLEMENTED, allNotImplemented),
                Arrays.asList(TOTAL_IN_THE_FILE_TEAM, all)));
        List<RowData> rows = convertDataFormat(values);

        // add current service to the sheet
        GridRange gridRange = new GridRange()
                .setSheetId(newSheetId) // Set the sheet ID to 0 for the new sheet
                .setStartRowIndex(SUMMARY_SHEETS_TOTAL_ROW) // Start from row 1
                .setStartColumnIndex(SUMMARY_SHEET_TOTAL_COLUMN) // Start from column A
                .setEndColumnIndex(SUMMARY_SHEET_TOTAL_COLUMN + 2); // End column based on the number of columns in the data

        requests.add(new Request().setUpdateCells(new UpdateCellsRequest()
                .setFields(USER_ENTERED_VALUE)
                .setRange(gridRange)
                .setRows(rows)));

        // set background for PASS domain summary
        Color color = getColorForStatus(PASS);
        CellFormat cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(SUMMARY_SHEETS_START_ROW+1,SUMMARY_SHEETS_START_ROW+2,SUMMARY_SHEET_START_COLUMN,column, cellFormat));

        // set background for SKIPPED domain summary
        color = getColorForStatus(SKIPPED);
        cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(SUMMARY_SHEETS_START_ROW+2,SUMMARY_SHEETS_START_ROW+3,SUMMARY_SHEET_START_COLUMN,column, cellFormat));

        // set background for FAILED domain summary
        color = getColorForStatus(FAIL);
        cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(SUMMARY_SHEETS_START_ROW+3,SUMMARY_SHEETS_START_ROW+4,SUMMARY_SHEET_START_COLUMN,column, cellFormat));

        // set background for NOT IMPLEMENTED domain summary
        color = getColorForStatus(NOT_IMPLEMENTED);
        cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(SUMMARY_SHEETS_START_ROW+4,SUMMARY_SHEETS_START_ROW+5,SUMMARY_SHEET_START_COLUMN,column, cellFormat));

        // set total row to be bold
        cellFormat = new CellFormat().setTextFormat(new TextFormat().setBold(true)).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(SUMMARY_SHEETS_START_ROW+5,SUMMARY_SHEETS_START_ROW+6,SUMMARY_SHEET_START_COLUMN,column, cellFormat));

        // set background for PASS domain summary
        color = getColorForStatus(PASS);
        cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(SUMMARY_SHEETS_TOTAL_ROW+1,SUMMARY_SHEETS_TOTAL_ROW+2,0,2, cellFormat));

        // set background for SKIPPED domain summary
        color = getColorForStatus(SKIPPED);
        cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(SUMMARY_SHEETS_TOTAL_ROW+2,SUMMARY_SHEETS_TOTAL_ROW+3,0,2, cellFormat));

        // set background for FAILED domain summary
        color = getColorForStatus(FAIL);
        cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(SUMMARY_SHEETS_TOTAL_ROW+3,SUMMARY_SHEETS_TOTAL_ROW+4,0,2, cellFormat));

        // set background for NOT IMPLEMENTED domain summary
        color = getColorForStatus(NOT_IMPLEMENTED);
        cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(SUMMARY_SHEETS_TOTAL_ROW+4,SUMMARY_SHEETS_TOTAL_ROW+5,0,2, cellFormat));

        // set total row to be bold
        cellFormat = new CellFormat().setTextFormat(new TextFormat().setBold(true)).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(SUMMARY_SHEETS_TOTAL_ROW+5,SUMMARY_SHEETS_TOTAL_ROW+6,0,2, cellFormat));


        // Add column auto-resize request
        requests.add(buildAutoColumnsWidthRequest());

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
    }


    private static void buildErrorsSheet(String spreadsheetId, Map<String, List<String>> errors) throws IOException {
        // start batch request which will be used to send all update requests
        List<Request> requests = new ArrayList<>();

        // add Total Api sheet
        newSheetId = createSheetRemoveOldIfNeeded(spreadsheetId, ERROR_SHEET_NAME);

        List<List<Object>> values = new ArrayList<>();
        values.add(new ArrayList<>(List.of("Domain", "Error details")));
        for (Map.Entry<String, List<String>> currentEntry : errors.entrySet()) {
            String currentDomain = currentEntry.getKey();
            List<String> domainErrors = currentEntry.getValue();
            for (String currentError : domainErrors) {
                values.add(new ArrayList<>(List.of(currentDomain, currentError)));
            }
        }
        List<RowData> rows = convertDataFormat(values);

        // add errors to the sheet
        GridRange gridRange = new GridRange()
                .setSheetId(newSheetId)
                .setStartRowIndex(0)
                .setStartColumnIndex(0);

        requests.add(new Request().setUpdateCells(new UpdateCellsRequest()
                .setFields(USER_ENTERED_VALUE)
                .setRange(gridRange)
                .setRows(rows)));

        // add colors to the table headers
        var color = getColorForStatus(NOT_IMPLEMENTED);
        var cellFormat = new CellFormat().setBackgroundColor(color);
        requests.add(setStyleRequest(0,1,0, 2, cellFormat));

        // Add column auto-resize request
        requests.add(buildAutoColumnsWidthRequest());

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
    }


    private static int returnCalculatedSum(Map<String, Map<String, Integer>>  allDomainCounters, String category) {
        return allDomainCounters
                .values()
                .stream().map(x -> x.get(category))
                .collect(Collectors.toList())
                .stream().reduce(0, Integer::sum);
    }

    private static Integer createSheetRemoveOldIfNeeded(String spreadsheetId, String sheetName) throws IOException {
        List<Request> requests = new ArrayList<>();
        Spreadsheet spreadsheet = sheetsService.spreadsheets().get(spreadsheetId).execute();
        List<Sheet> sheets = spreadsheet.getSheets();

        // delete old sheet if exists
        Optional<Sheet> sheet = sheets.stream().filter(x -> x.getProperties().getTitle().equalsIgnoreCase(sheetName)).findFirst();
        sheet.ifPresent(value -> requests.add(new Request()
                .setDeleteSheet(new DeleteSheetRequest()
                        .setSheetId(value.getProperties().getSheetId()))));

        // create request to create new sheet
        AddSheetRequest addSheetRequest = new AddSheetRequest()
                .setProperties(new com.google.api.services.sheets.v4.model.SheetProperties()
                        .setTitle(sheetName));
        requests.add(new Request().setAddSheet(addSheetRequest));

        // send requests and return new sheetId
        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        BatchUpdateSpreadsheetResponse response = sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
        List<Response> replies = response.getReplies();
        return replies.get(replies.size() - 1).getAddSheet().getProperties().getSheetId();
    }

    private static Request addColumnsRequest(Map<String, Map<String, String>> currentDomainData) {
        int servicesCount = currentDomainData.keySet().size();
        int neededColumns = servicesCount * NEEDED_COLUMNS_PER_SERVICE;
        if (neededColumns > DEFAULT_COLUMNS_IN_SHEET) {
            return new Request().setAppendDimension(new AppendDimensionRequest()
                    .setSheetId(newSheetId)
                    .setDimension("COLUMNS")
                    .setLength(neededColumns - DEFAULT_COLUMNS_IN_SHEET));
        } else {
            return null;
        }
    }

    private static List<List<Object>> buildServiceHeaders(Map<String, Integer> counters, String currentServiceName) {
        return new ArrayList<>(Arrays.asList(
                Arrays.asList("success:", counters.get(PASS)),
                Arrays.asList("skip:", counters.get(SKIPPED)),
                Arrays.asList("failed:", counters.get(FAIL)),
                Arrays.asList("not implemented:", counters.get(NOT_IMPLEMENTED)),
                Arrays.asList("ServiceName:", currentServiceName),
                Arrays.asList("OperationId name", "Status")));
    }

    private static List<Request> buildServiceData(Map<String, Map<String, String>> currentDomainData, String currentServiceName, int rangeCounter) {
        List<Request> requests = new ArrayList<>();
        List<List<Object>> values = new ArrayList<>();
        Map<String, Integer> counters = initCounters();

        // parse operationIds and their status
        Map<String, String> operations = currentDomainData.get(currentServiceName);
        for (Map.Entry<String, String> currentEntry : operations.entrySet()) {
            String operationIdName = currentEntry.getKey();
            String operationStatus = currentEntry.getValue();

            counters.put(operationStatus, counters.get(operationStatus)+1);
            List<Object> convertedValue = Arrays.asList(operationIdName, operationStatus);
            values.add(convertedValue);
        }

        // prepare headers for current service
        List<List<Object>> fullValues = buildServiceHeaders(counters, currentServiceName);
        fullValues.addAll(values);

        // convert to a format supported by Google sheets
        List<RowData> rows = convertDataFormat(fullValues);

        // add current service to the sheet
        GridRange gridRange = new GridRange()
                .setSheetId(newSheetId) // Set the sheet ID to 0 for the new sheet
                .setStartRowIndex(9) // Start from row 1
                .setStartColumnIndex(rangeCounter) // Start from column A
                .setEndColumnIndex(rangeCounter + 2); // End column based on the number of columns in the data

        requests.add(new Request().setUpdateCells(new UpdateCellsRequest()
                .setFields(USER_ENTERED_VALUE)
                .setRange(gridRange)
                .setRows(rows)
        ));

        // set service name as bold
        CellFormat cellFormat = new CellFormat().setTextFormat(new TextFormat().setBold(true)).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(13,14,rangeCounter,rangeCounter + 2, cellFormat));

        // give border to table header
        cellFormat = new CellFormat()
                .setHorizontalAlignment(CENTER)
                        .setBorders(new Borders()
                .setTop(new Border().setStyle(SOLID))
                .setBottom(new Border().setStyle(SOLID))
                .setLeft(new Border().setStyle(SOLID))
                .setRight(new Border().setStyle(SOLID)));
        requests.add(setStyleRequest(14,15,rangeCounter,rangeCounter + 2, cellFormat));

        // set background colors
        for (int i = 6; i < fullValues.size(); i++) {
            String status = fullValues.get(i).get(1).toString().toLowerCase();
            Color color = getColorForStatus(status);
            cellFormat = new CellFormat().setBackgroundColor(color);
            requests.add(setStyleRequest(i+9,i+10,rangeCounter,rangeCounter + 2, cellFormat));
        }

        domainCounters.put(PASS, domainCounters.get(PASS) + counters.get(PASS));
        domainCounters.put(FAIL, domainCounters.get(FAIL) + counters.get(FAIL));
        domainCounters.put(NOT_IMPLEMENTED, domainCounters.get(NOT_IMPLEMENTED) + counters.get(NOT_IMPLEMENTED));

        return requests;
    }

    private static List<Request> buildDomainHeaders() {
        List<Request> requests = new ArrayList<>();

        int totalEndpointsCount = domainCounters.values().stream().reduce(0, Integer::sum);
        List<List<Object>> headerValues = new ArrayList<>(Arrays.asList(
                Arrays.asList("Updated on:", DateTimeUtils.currentDateTimeUTC()),
                Arrays.asList("", ""),
                Arrays.asList(PASS, domainCounters.get(PASS)),
                Arrays.asList(SKIPPED, domainCounters.get(SKIPPED)),
                Arrays.asList(FAILED, domainCounters.get(FAIL)),
                Arrays.asList(NOT_IMPLEMENTED, domainCounters.get(NOT_IMPLEMENTED)),
                Arrays.asList(TOTAL_IN_THE_FILE_TEAM, totalEndpointsCount),
                Arrays.asList("", "")
        ));
        List<RowData> convertedHeaderValues = convertDataFormat(headerValues);
        GridRange updatedOnRange = new GridRange()
                .setSheetId(newSheetId) // Set the sheet ID to 0 for the new sheet
                .setStartRowIndex(0) // Start from row 1
                .setEndRowIndex(headerValues.size())
                .setStartColumnIndex(0); // Start from column A

        requests.add(new Request().setUpdateCells(new UpdateCellsRequest()
                .setFields(USER_ENTERED_VALUE)
                .setRange(updatedOnRange)
                .setRows(convertedHeaderValues))
        );

        // set background colors for updated date
        Color color = new Color().setRed(1f).setGreen(1f).setBlue(0.33f); // color yellow
        CellFormat cellFormat = new CellFormat().setBackgroundColor(color).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(0,1,0,2, cellFormat));

        // set background for PASS domain summary
        color = getColorForStatus(PASS);
        cellFormat = new CellFormat().setBackgroundColor(color).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(2,3,0,2, cellFormat));

        // set background for SKIPPED domain summary
        color = getColorForStatus(SKIPPED);
        cellFormat = new CellFormat().setBackgroundColor(color).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(3,4,0,2, cellFormat));

        // set background for FAILED domain summary
        color = getColorForStatus(FAIL);
        cellFormat = new CellFormat().setBackgroundColor(color).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(4,5,0,2, cellFormat));

        // set background for NOT IMPLEMENTED domain summary
        color = getColorForStatus(NOT_IMPLEMENTED);
        cellFormat = new CellFormat().setBackgroundColor(color).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(5,6,0,2, cellFormat));

        // set total row to be bold
        cellFormat = new CellFormat().setTextFormat(new TextFormat().setBold(true)).setHorizontalAlignment(CENTER);
        requests.add(setStyleRequest(6,7,0,2, cellFormat));

        return requests;
    }

    private static Request setStyleRequest(int startRow, int endRow, int startColumn, int endColumn, CellFormat cellFormat) {
        GridRange rowRange = new GridRange()
                .setSheetId(newSheetId)
                .setStartRowIndex(startRow)
                .setEndRowIndex(endRow)
                .setStartColumnIndex(startColumn)
                .setEndColumnIndex(endColumn);

        return new Request().setRepeatCell(new RepeatCellRequest()
                .setRange(rowRange)
                .setCell(new CellData().setUserEnteredFormat(cellFormat))
                .setFields("userEnteredFormat.backgroundColor,userEnteredFormat.horizontalAlignment,userEnteredFormat.textFormat.bold,userEnteredFormat.borders"));
    }

    private static Request buildAutoColumnsWidthRequest() {
        DimensionRange columnRange = new DimensionRange()
                .setDimension("COLUMNS")
                .setSheetId(newSheetId)
                .setStartIndex(0);

        return new Request().setAutoResizeDimensions(new AutoResizeDimensionsRequest()
                .setDimensions(columnRange));
    }


    private static Color getColorForStatus(String status) {
        Color color = new Color();
        switch (status.toUpperCase()) {
            case PASS:
                color.setRed(0.85f);
                color.setGreen(0.91f);
                color.setBlue(0.83f);
                break;
            case FAIL:
                color.setRed(0.93f);
                color.setGreen(0.8f);
                color.setBlue(0.8f);
                break;
            case NOT_IMPLEMENTED:
                color.setRed(1f);
                color.setGreen(0.94f);
                color.setBlue(0.81f);
                break;
            case SKIPPED:
                color.setRed(0.8f);
                color.setGreen(0.85f);
                color.setBlue(0.95f);
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
        return color;
    }

    private static List<RowData> convertDataFormat(List<List<Object>> values) {
        List<RowData> rows = new ArrayList<>();
        for (List<Object> rowValues : values) {
            List<CellData> cellDataList = new ArrayList<>();
            for (Object cellValue : rowValues) {
                CellData cellData = new CellData();
                cellData.setUserEnteredValue(new ExtendedValue().setStringValue(cellValue.toString()));
                cellDataList.add(cellData);
            }
            RowData rowData = new RowData().setValues(cellDataList);
            rows.add(rowData);
        }
        return rows;
    }

    private static Map<String, Integer> initCounters() {
        Map<String, Integer> result = new HashMap<>();
        result.put(PASS, 0);
        result.put(SKIPPED, 0);
        result.put(FAIL, 0);
        result.put(NOT_IMPLEMENTED, 0);
        return result;
    }
}
