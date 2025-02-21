package io.securitize.scripts;

import io.securitize.infra.api.InvestorsAPI;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;


public class AUT442_SidCpKYCUpdate {

    private static final String PROFILE_ID = "63b577fded2ac95f52e6d7e5";
    private static final KycStatus KYC_STATUS_TO_SET = KycStatus.verified;

    public static void main(String[] args) {
        InvestorsAPI investorsAPI = new InvestorsAPI();

        String profileId = getProfileId();
        KycStatus setToStatus = getKycStatusToSet();
        info(String.format("Setting profile %s to KYC status of %s%n", profileId, setToStatus));
        if (setToStatus == KycStatus.verified) {
            investorsAPI.setKYCToPassed(profileId, null);
        } else {
            investorsAPI.setKYCToReject(profileId);
        }
    }

    private static String getProfileId() {
        String valueFromEnvironment = System.getenv("PROFILE_ID");
        if (valueFromEnvironment == null) {
            return PROFILE_ID;
        }

        if (valueFromEnvironment.trim().isEmpty()) {
            errorAndStop("PROFILE_ID value can't be empty!", false);
        }
        return valueFromEnvironment.trim();
    }

    private static KycStatus getKycStatusToSet() {
        String valueFromEnvironment = System.getenv("KYC_STATUS_TO_SET");
        if (valueFromEnvironment != null) {
            return KycStatus.valueOf(valueFromEnvironment);
        }
        return KYC_STATUS_TO_SET;
    }

    enum KycStatus {
        verified,
        rejected
    }
}