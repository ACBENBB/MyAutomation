package io.securitize.infra.aws;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import org.apache.commons.lang3.exception.ExceptionUtils;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.securitize.infra.reporting.MultiReporter.*;

public class S3 extends AbstractAwsService {

    private static S3Client s3Client = null;
    private static String bucketName = null;

//    public static boolean isBucketExist(String bucketName) {
//        initS3Client();
//
//        ListBucketsResponse buckets = s3Client.listBuckets();
//        return buckets.buckets().stream().anyMatch(x -> x.name().equalsIgnoreCase(bucketName));
//    }

    public static BufferedImage getScreenshot(String testName, String screenshotName, String screenResolution) {
        initS3Client();

//        if (!isBucketExist(getBucketName())) {
//            errorAndStop("Visual regression baseline bucket can't be found: " + getBucketName(), false);
//        }

        String fullPath = getPath(testName, screenshotName, screenResolution);

        // check if item exists
        try {
            debug("Checking if image exists at: " + fullPath);
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(getBucketName()).key(fullPath).build();
            s3Client.headObject(headObjectRequest);
            info("Image does exist at: " + fullPath);
        } catch (NoSuchKeyException e) {
            info("Image does NOT exist at: " + fullPath);
            return null;
        }

        // try to obtain object
        try {
            debug("Reading image from S3");
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(getBucketName()).key(fullPath).build();
            ResponseInputStream<GetObjectResponse> result = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
            debug("Finished reading image from S3");
            return ImageIO.read(result);

        } catch (Exception e) {
            errorAndStop("An error occur trying to read remote file from S3: " + fullPath + ". Details: " + ExceptionUtils.getStackTrace(e), false);
            return null;
        }
    }

    public static void downloadFile(String bucketName, String path, String outputPath) {
        debug("Downloading file " + path);
        initS3Client();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        byte[] data = objectBytes.asByteArray();
        File file = new File(outputPath);
        try {
            Files.write(file.toPath(), data);
        } catch (IOException e) {
            errorAndStop(String.format("An error occur trying to download file from S3 bucket %s, from path %s to local file of %s", bucketName, path, outputPath), false);
        }
        debug("File successfully downloaded!");
    }


    public static void uploadScreenshot(String testName, String screenshotName, String screenResolution, BufferedImage image) {
        initS3Client();
//
//        if (!isBucketExist(getBucketName())) {
//            errorAndStop("Visual regression baseline bucket can't be found: " + getBucketName(), false);
//        }

        String fullPath = getPath(testName, screenshotName, screenResolution);

        try {
            info("Upload image to S3 to: " + fullPath);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            byte[] buffer = os.toByteArray();
            InputStream is = new ByteArrayInputStream(buffer);
            PutObjectRequest request = PutObjectRequest.builder().bucket(getBucketName()).key(fullPath).build();
            s3Client.putObject(request, RequestBody.fromInputStream(is, buffer.length));
            info("Successfully uploaded image to S3 to: " + fullPath);
        } catch (Exception e) {
            errorAndStop("An error occur trying to upload baseline image to S3: " + fullPath + ". Details: " + ExceptionUtils.getStackTrace(e), false);
        }
    }

    public static String getPath(String testName, String screenshotName, String screenResolution) {
        String environment = MainConfig.getProperty(MainConfigProperty.environment);
        return environment + "/" + testName + "/" + screenshotName + "/" + screenResolution + "/baseLine.png";
    }

    private static synchronized String getBucketName() {
        if (bucketName == null) {
            bucketName = Users.getProperty(UsersProperty.visualRegressionBucketName);
        }

        return bucketName;
    }

    private static synchronized void initS3Client() {
        if (s3Client != null) {
            return;
        }

        // different machines have different profiles. Support them all but by priority
        List<String> awsProfiles = listProfiles();
        List<String> profilesPriority = Arrays.asList("securitize", "S3SnapshotUpdater", "default");
        Optional<String> awsProfileName = profilesPriority.stream().filter(awsProfiles::contains).findFirst();

        if (awsProfileName.isPresent()) {
            ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.builder().profileName(awsProfileName.get()).build();
            s3Client = S3Client.builder()
                    .credentialsProvider(credentialsProvider)
                    .region(Region.US_EAST_2)
                    .build();
        } else {
            errorAndStop("Can't find any relevant configured S3 profiles", false);
        }
    }
}