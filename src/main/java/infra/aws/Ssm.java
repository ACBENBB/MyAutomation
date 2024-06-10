package infra.aws;

import java.util.List;

public class Ssm extends AbstractAwsService {

/*    private static SsmClient ssmClient = null;

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
    }*/
}
