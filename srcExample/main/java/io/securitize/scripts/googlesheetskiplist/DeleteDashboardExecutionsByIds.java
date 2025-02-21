package io.securitize.scripts.googlesheetskiplist;

import io.securitize.infra.database.MySqlDatabase;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.utils.ResourcesUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class DeleteDashboardExecutionsByIds {

    private static final String SQL_PATH = "sql/deleteExecutionsByIds.sql";

    public static void main(String[] args) throws ExecutionException, InterruptedException, SQLException, IOException {
        String query = ResourcesUtils.getResourceContentAsString(SQL_PATH);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("executionIdsToDelete", getExecutionIds());
        MySqlDatabase.executeUpdate(query, parameters);
    }

    private static String[] getExecutionIds() {
        String executionIdsToDelete = System.getenv("executionIdsToDelete");
        if (executionIdsToDelete == null) {
            errorAndStop("Can't find environment variable of 'executionIdsToDelete'. Terminating!", false);
            return null;
        }

        if (executionIdsToDelete.trim().length() == 0) {
            errorAndStop("Environment variable of 'executionIdsToDelete' is empty. Terminating!", false);
            return null;
        }

        if (RegexWrapper.isValidValueByRegex(executionIdsToDelete, "^([0-9]+,? ?)+$")) {
            info("Will remove the following executionIds: " + executionIdsToDelete);
            return Arrays.stream(executionIdsToDelete.split(",")).map(String::trim).toArray(String[]::new);
        } else {
            errorAndStop("Invalid provided executionIdsToDelete. Must be numeric separated by commas: " + executionIdsToDelete, false);
            return null;
        }
    }
}