package io.securitize.scripts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.info;

public class GetDevelopersCommitHistory {
    private static final boolean IS_DEBUG_MODE = false;

    private static final String OUTPUT_FILE_PATH = "commit_history.txt";
    private static final String JOBS_CLASS_NAME = "org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject";
    private static final String JOBS_NAME_REGEX_TEMPLATE = "^(%s)-.*";
    private static final String[] DEFAULT_DOMAINS = {"ATS", "BC", "CA", "FR", "INVT", "ISR", "JP", "TA"};

    private static final String JOB_DESCRIPTION_REGEX = "^<b>(.*?)<\\/b>.*?href=['|\"](.*?bitbucket.org.*?)['|\"]";

    // parameters we need to get from the environment in order to run
    private static String developersBranch;
    private static long fromTimestamp;
    private static String[] allowedDomains;

    private static String jenkinsUsername;
    private static String jenkinsApiToken;

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        // read and set needed parameters
        initialize();

        // Get the list of jobs
        List<JobDetails> multiBranchWorkflowJobs = getAllJobs();
        List<JobDetails> jobsWithBranch = addBranchToJobs(multiBranchWorkflowJobs);

        // build list of relevant execution details
        String history = buildChangesHistory(jobsWithBranch);
        info("Here are the results:" + System.lineSeparator() + history);
        Files.write(Paths.get(OUTPUT_FILE_PATH), history.getBytes());

        // print how much time it took
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) - minutes * 60;
        info("Finished in: " + minutes + " minutes and " + seconds + " seconds.");
    }


    private static void initialize() {
        // validate mandatory variables
        developersBranch = System.getenv("developersBranch");
        if (developersBranch == null) throw new IllegalArgumentException("the 'developersBranch' environment variable is mandatory! (example: rc)");
        String fromTimestampAsString = System.getenv("fromTimestamp");
        if (fromTimestampAsString == null) throw new IllegalArgumentException("the 'fromTimestamp' environment variable is mandatory! (example: 1685603611");
        fromTimestamp = Long.parseLong(fromTimestampAsString);

        // allow to analyze only certain domains
        String rawAllowedDomains = System.getenv("allowedDomains");
        if (rawAllowedDomains == null) {
            info("Allowing all domains: " + Arrays.toString(DEFAULT_DOMAINS));
            allowedDomains = DEFAULT_DOMAINS;
        } else {
            allowedDomains = rawAllowedDomains.split(",");
        }

        // load secrets from the vault
        // values loaded from the vault
        String jenkinsUrl = Users.getProperty(UsersProperty.jenkinsOpsUrl);
        jenkinsUsername = Users.getProperty(UsersProperty.jenkinsOpsUsername);
        jenkinsApiToken = Users.getProperty(UsersProperty.jenkinsOpsApiToken);

        // setup needed rest assured settings
        RestAssured.baseURI = jenkinsUrl;
        if (IS_DEBUG_MODE) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
    }

    private static RequestSpecification internalGiven() {
        return given()
                .auth().preemptive().basic(jenkinsUsername, jenkinsApiToken)
                .contentType(ContentType.JSON);
    }

    private static List<JobDetails> getAllJobs() {
        Response response = internalGiven()
                .get("api/json")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<JobDetails> jobs = jsonPath.getList("jobs", JobDetails.class);
        info(String.format("Found %s jobs\n", jobs.size()));

        // filter jobs of non-relevant type
        String allowedDomainsForRegex = String.join("|", allowedDomains).toUpperCase();
        String regex = String.format(JOBS_NAME_REGEX_TEMPLATE, allowedDomainsForRegex);
        Pattern pattern = Pattern.compile(regex);

        List<JobDetails> filteredJobs = jobs.stream().filter(x ->
                x.jobClass.equalsIgnoreCase(JOBS_CLASS_NAME) &&
                        pattern.matcher(x.name).matches()).collect(Collectors.toList());
        info(String.format("Found %s relevant jobs after applying filters\n", filteredJobs.size()));
        return filteredJobs;
    }

    private static List<JobDetails> addBranchToJobs(List<JobDetails> originalJobs) {
        originalJobs.forEach(x -> x.urlWithBranch = x.url + "job/" + developersBranch);
        return originalJobs;
    }

    private static String buildChangesHistory(List<JobDetails> jobsWithBranch) {
        StringBuilder history = new StringBuilder();
        int totalJobs = jobsWithBranch.size();
        int i = 0;
        for (JobDetails currentJob : jobsWithBranch) {
            i++;
            Response response = internalGiven()
                    .get(currentJob.urlWithBranch + "/api/json?tree=builds[id,url,timestamp,description,result]")
                    .then()
                    .extract().response();

            if (response.statusCode() != 200) {
                info(String.format("(%s/%s) response status was %s for job %s. It will be ignored\n",i, totalJobs, response.statusCode(), currentJob.name));
                continue;
            }

            JsonPath jsonPath = response.jsonPath();
            List<JobBuildDetails> builds = jsonPath.getList("builds", JobBuildDetails.class);
            info(String.format("(%s/%s) Found %s builds for job %s\n",i, totalJobs, builds.size(), currentJob.name));

            // filter old builds
            boolean jobNameAdded = false;
            for (JobBuildDetails currentBuild : builds) {
                if (currentBuild.timestamp < fromTimestamp) {
                    break;
                }

                String developerName = "unknown";
                if (currentBuild.description != null) {
                    try {
                        Pattern pattern = Pattern.compile(JOB_DESCRIPTION_REGEX);
                        Matcher matcher = pattern.matcher(currentBuild.description);
                        if (matcher.find()) {
                            developerName = matcher.group(1);
                        }
                    } catch (Exception e) {
                        developerName = "Can't extract developer name";
                    }
                }

                if (!jobNameAdded) {
                    jobNameAdded = true;
                    history.append(currentJob.name).append(System.lineSeparator());
                }
                String dateTime = ((Date)new Timestamp(currentBuild.timestamp)).toString();
                history.append("\t● ")
                        .append(dateTime).append(" (").append(currentBuild.result.toLowerCase()).append(")")
                        .append(" - Dev.: ").append(developerName).append(". Build: ").append(currentBuild.url).append(" \n");
            }
        }
        return history.toString();
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class JobDetails {
    @JsonProperty("name")
    String name;

    @JsonProperty("_class")
    String jobClass;

    @JsonProperty("url")
    String url;

    String urlWithBranch;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class JobBuildDetails {
    @JsonProperty("timestamp")
    long timestamp;

    @JsonProperty("description")
    String description;

    @JsonProperty("url")
    String url;

    @JsonProperty("result")
    String result;
}