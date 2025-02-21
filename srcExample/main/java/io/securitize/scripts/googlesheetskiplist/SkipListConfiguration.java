package io.securitize.scripts.googlesheetskiplist;

public class SkipListConfiguration {
    private final int maxDaysToScan;
    private final int runsToAnalyze;
    private final int minimalAmountOfRunsWithStatus;
    private final String[] testCategories;

    public SkipListConfiguration(int maxDaysToScan, int runsToAnalyze, int minimalAmountOfRunsWithStatus, String[] testCategories) {
        this.maxDaysToScan = maxDaysToScan;
        this.runsToAnalyze = runsToAnalyze;
        this.minimalAmountOfRunsWithStatus = minimalAmountOfRunsWithStatus;
        this.testCategories = testCategories;
    }

    public int getMaxDaysToScan() {
        return maxDaysToScan;
    }

    public int getRunsToAnalyze() {
        return runsToAnalyze;
    }

    public int getMinimalAmountOfRunsWithStatus() {
        return minimalAmountOfRunsWithStatus;
    }

    public String[] getTestCategories() {
        return testCategories;
    }
}