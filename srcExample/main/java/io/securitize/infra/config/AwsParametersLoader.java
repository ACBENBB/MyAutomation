package io.securitize.infra.config;

import io.securitize.infra.aws.Ssm;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

public class AwsParametersLoader {

    private static final SsmClient SSM_CLIENT = Ssm.getSsmClient();


    public static String getParameterValue(String parameter) {
        GetParameterRequest getParameterRequest = GetParameterRequest.builder().name(parameter).withDecryption(true).build();
        GetParameterResponse response = SSM_CLIENT.getParameter(getParameterRequest);
        return response.parameter().value();
    }

}
