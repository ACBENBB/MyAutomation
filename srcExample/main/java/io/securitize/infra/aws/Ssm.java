package io.securitize.infra.aws;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.SsmClientBuilder;
import software.amazon.awssdk.services.ssm.model.PutParameterRequest;

import java.util.List;

import static io.securitize.infra.reporting.MultiReporter.info;

public class Ssm extends AbstractAwsService {

    private static SsmClient ssmClient = null;

    public synchronized static SsmClient getSsmClient() {
        if (ssmClient != null) {
            return ssmClient;
        }

        List<String> awsProfiles = listProfiles();

        info("Connecting to AWS parameter store...");
        Region region = Region.US_EAST_2;
        SsmClientBuilder builder = SsmClient.builder()
                .region(region);

        // if multiple aws profiles exist on current machine, use the one marked for securitize
        if (awsProfiles.contains("securitize")) {
            builder = builder.credentialsProvider(ProfileCredentialsProvider.create("securitize"));
        }
        ssmClient = builder.build();
        return ssmClient;
    }

    public static void updateParameter(String key, String value) {
        SsmClient ssmClient = getSsmClient();

        PutParameterRequest request = PutParameterRequest.builder()
                .name(key)
                .value(value)
                .overwrite(true) // Set to true to update an existing parameter
                .build();

        ssmClient.putParameter(request);
    }
}