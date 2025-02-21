package io.securitize.infra.utils;

import de.taimos.totp.TOTP;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class Authentication {

    private static String BEARER_TOKEN = null;

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public synchronized static void clearBearerToken() {
        BEARER_TOKEN = null;
    }

    public synchronized static String getBearerToken() {

        // bearer token is always loaded from cloud configuration as follows:
        // * sandbox - hardcoded until issue ISR-3422 is resolved
        // * rc - daily refreshed by Jenkins job AUT236_UpdateCpBearer
        BEARER_TOKEN = Users.getProperty(UsersProperty.automationCpBearer);

        if (BEARER_TOKEN == null || BEARER_TOKEN.isEmpty()) {
            errorAndStop("Can't load bearer token from configuration. Cannot resume", false);
        }

        return BEARER_TOKEN;
    }
}
