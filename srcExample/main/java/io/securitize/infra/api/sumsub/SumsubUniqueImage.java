package io.securitize.infra.api.sumsub;

public class SumsubUniqueImage {

    private static int DRIVER_LICENSE_FRONT_INDEX_LIMIT = 10;
    private static int currentDriverLicenseFrontIndex = 1;

    public synchronized static int getDriverLicenseFrontIndex() {
        if (currentDriverLicenseFrontIndex > DRIVER_LICENSE_FRONT_INDEX_LIMIT) {
            currentDriverLicenseFrontIndex = 1;
        }

        int result = currentDriverLicenseFrontIndex;
        currentDriverLicenseFrontIndex++;

        return result;
    }
}
