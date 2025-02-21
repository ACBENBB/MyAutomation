package io.securitize.infra.googleapi;

/**
 * Represents an entry in the Google skips sheet (one row in the table with various values for all
 * columns
 */
@SuppressWarnings("unused")
public class SkipTestEntry {
    private final String insertSkipDate;
    private final String domain;
    private final String serviceName;
    private final String name;
    private final String environment;
    private final String reason;
    private final String markedSkippedBy;
    private final int indexInSheet;

    public SkipTestEntry(String insertSkipDate, String domain, String serviceName, String name, String environment, String reason, String markedSkippedBy, int indexInSheet) {
        this.insertSkipDate = insertSkipDate;
        this.domain = domain;
        this.serviceName = serviceName;
        this.name = name;
        this.environment = environment;
        this.reason = reason;
        this.markedSkippedBy = markedSkippedBy;
        this.indexInSheet = indexInSheet;
    }

    public String getInsertSkipDate() {
        return insertSkipDate;
    }

    public String getDomain() {
        return domain;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getName() {
        return name;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getReason() {
        return reason;
    }

    public String getMarkedSkippedBy() {
        return markedSkippedBy;
    }

    public int getIndexInSheet() {
        return indexInSheet;
    }

    @Override
    public String toString() {
        return "insertSkipDate: " + insertSkipDate
                + ", domain: " + domain
                + ", serviceName: " + serviceName
                + ", name: " + name
                + ", environment: " + environment
                + ", reason: " + reason
                + ", markedSkippedBy: " + markedSkippedBy;
    }
}
