package io.securitize.infra.webdriver;

import io.securitize.infra.aws.S3;
import io.securitize.infra.aws.Ssm;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.securitize.infra.reporting.MultiReporter.debug;
import static io.securitize.infra.reporting.MultiReporter.errorAndStop;

public class Browserstack {

    public static final String SECURITIZE_PROD_IPA = "SecuritizeProd.ipa";
    public static final String SECURITIZE_DEV_IPA = "SecuritizeDev.ipa";
    public static final String BROWSERSTACK_UPLOAD_APP_URL = "https://api-cloud.browserstack.com/app-automate/upload";


    public static void uploadAppToBrowserstack() {
        // download relevant ipa file from S3
        File ipaFile = getIpaFile();

        // upload IPA to browserstack
        String appUrl = uploadToBrowserstackGetAppUrl(ipaFile);

        // update vault secret with uploaded app id
        updateConfiguration(appUrl);
    }


    @NotNull
    private static File getIpaFile() {
        String ipaFileName = getIpaFileName();

        File ipaFile = new File(ipaFileName);
        if (ipaFile.exists()) {
            debug("Local IPA already exists.. Removing it...");
            if (!ipaFile.delete()) {
                errorAndStop("An error occur trying to delete old local version of the IPA file: " + ipaFileName, false);
            }
        }
        String bucketName = MainConfig.getProperty(MainConfigProperty.iOSFilesBucketName);
        String path = "Securitize_IOS_Versions/" + ipaFileName;
        S3.downloadFile(bucketName, path, ipaFileName);
        return ipaFile;
    }


    private static String getIpaFileName() {
        if (MainConfig.isProductionEnvironment()) {
            return SECURITIZE_PROD_IPA;
        } else {
            return SECURITIZE_DEV_IPA;
        }
    }


    private static String uploadToBrowserstackGetAppUrl(File ipaFile) {
        debug("Uploading app to Browserstack");
        String browserstack_username = Users.getProperty(UsersProperty.automateUsername);
        String browserstack_token = Users.getProperty(UsersProperty.automateKey);

        String appUrl = given()
                .auth().preemptive().basic(browserstack_username, browserstack_token)
                .multiPart("file", ipaFile)
                .when()
                .post(BROWSERSTACK_UPLOAD_APP_URL)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().body().jsonPath().getString("app_url");
        debug("Finished uploading app to Browserstack");
        return appUrl;
    }

    private static void updateConfiguration(String appUrl) {
        debug("Updating parameters to use new app id");
        Ssm.updateParameter("/qa/" + MainConfig.getProperty(MainConfigProperty.environment) + "/ios_app_id", appUrl);
        Users.resetParameters();
    }
}