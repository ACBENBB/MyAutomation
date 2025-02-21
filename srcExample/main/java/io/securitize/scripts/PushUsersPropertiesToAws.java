package io.securitize.scripts;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.ParameterType;
import software.amazon.awssdk.services.ssm.model.PutParameterRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.securitize.infra.reporting.MultiReporter.*;

public class PushUsersPropertiesToAws {
    public static void main(String[] args) {
        Properties properties = new Properties();
        boolean isDryRun = true;

        // load all properties from local file
        info("Loading properties from local file");
        File localPropertiesFile = new File("users.properties");
        if (localPropertiesFile.exists()) {
            try (FileInputStream input = new FileInputStream(localPropertiesFile)) {
                properties.load(input);
            } catch (IOException e) {
                errorAndStop("An error occur trying to load local users config: " + e, false);
            }
        }

        // connect to aws
        info("Connecting to AWS parameter store...");
        Region region = Region.US_EAST_2;
        SsmClient ssmClient = SsmClient.builder()
                .region(region)
                .build();

        // for each parameter loaded
        for (Object currentParameterAsObject : properties.keySet()) {
            String currentParameter = currentParameterAsObject.toString();
            String path = "/qa/";
            // if parameter contains environment prefix set path (such as _rc)
            if (currentParameter.startsWith("rc_")) {
                path += "rc/";
                currentParameter = currentParameter.replace("rc_", "");
            } else if (currentParameter.startsWith("sandbox_")) {
                path += "sandbox/";
                currentParameter = currentParameter.replace("sandbox_", "");
            } else {
                // else use generic path
                path += "generic/";
            }

            path += currentParameter;
            info("path to create or update: " + path);

            // push parameter to AWS
            if (isDryRun) {
                info("Dry run mode... not pushing to AWS...");
            } else {
                try {
                    PutParameterRequest parameterRequest = PutParameterRequest.builder()
                            .name(path)
                            .value(properties.getProperty(currentParameterAsObject.toString()))
                            .type(ParameterType.STRING)
                            .overwrite(false)
                            .build();

                    ssmClient.putParameter(parameterRequest);
                    info("successfully updated: " + path);
                } catch (Exception e) {
                    warning("An error occur trying to push " + path + ". Details: " + e, false);
                }
            }
        }
    }
}
