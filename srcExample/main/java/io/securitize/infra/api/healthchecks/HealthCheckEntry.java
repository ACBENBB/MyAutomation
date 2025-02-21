package io.securitize.infra.api.healthchecks;

import java.util.List;

/**
 * Represents a line describing a health-check endpoint loaded from the resources CSV file
 */
public class HealthCheckEntry {
    public String name;
    public String url;
    public List<String> tags;
    public List<String> ignoreList; // list of json properties to ignore
}
