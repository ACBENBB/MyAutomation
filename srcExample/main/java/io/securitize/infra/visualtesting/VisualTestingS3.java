package io.securitize.infra.visualtesting;

import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.aws.S3;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.utils.ImageComparisonResult;
import io.securitize.infra.utils.ImageUtils;
import io.securitize.tests.abstractClass.AbstractTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.*;

public class VisualTestingS3 extends AbstractVisualTesting {

    public VisualTestingS3(Browser browser) {
        super(browser);
        info("Initiating visual testing: VisualTestingS3");
    }

    @Override
    public void snapshot(String screenshotName) {
        MultiReporter.startTestLevel("Image comparison: " + screenshotName);

        String testName = AbstractTest.getTestFullyQualifiedName();

        browser.waitForPageStable(Duration.ofSeconds(5));
        // Disabling the recording message, so it won't break the page elements
//        showRecordingMessage();

        try {
            // read current state screenshot
            File currentStateFile = browser.getScreenshotAsFile();
            BufferedImage currentStateImage = ImageIO.read(currentStateFile);
            int width = currentStateImage.getWidth();
            int height = currentStateImage.getHeight();

            // read baseline image
            BufferedImage baselineImage = S3.getScreenshot(testName, screenshotName, width+"x"+height);

            // if baseline image exists
            if (baselineImage != null) {
                info("VisualTestingS3: baseline image found in S3 - comparing current version with existing version");
                ImageComparisonResult comparisonResult = ImageUtils.calculateImagesSimilarity(baselineImage, currentStateImage);
                if (comparisonResult.isSimilar()) {
                    info(String.format("Images are considered similar! (changedPixelPercent=%s%%)", comparisonResult.getChangedPixelPercent()));
                    MultiReporter.addImage("diff image", ImageUtils.encodePngImageToBase64(comparisonResult.getDiffImage()));
                } else {
                    String htmlTemplate = "<a href=\"data:image/png;base64,%s\" data-featherlight=\"image\"><span class=\"label grey badge white-text text-white\">%s</span></a>";
                    // for extent report
                    MultiReporter.reportRawHTML(String.format(htmlTemplate, ImageUtils.encodePngImageToBase64(baselineImage), "baseline-image"));
                    MultiReporter.reportRawHTML(String.format(htmlTemplate, ImageUtils.encodePngImageToBase64(currentStateImage), "current-image"));
                    MultiReporter.reportRawHTML(String.format(htmlTemplate, ImageUtils.encodePngImageToBase64(comparisonResult.getDiffImage()), "diff-image"));

                    // for report portal
                    MultiReporter.addImage("baseline-image", ImageUtils.encodePngImageToBase64(baselineImage));
                    MultiReporter.addImage("current-image", ImageUtils.encodePngImageToBase64(currentStateImage));
                    MultiReporter.addImage("diff-image", ImageUtils.encodePngImageToBase64(comparisonResult.getDiffImage()));


                    error("Images are not similar! See attached diff image!");
                    // for extent report
                    MultiReporter.reportRawHTML("Want to update the baseline image? <button onclick=\"updateBaseline('"+testName+"', '"+screenshotName+"', '"+S3.getPath(testName, screenshotName, width+"x"+height)+"', '"+ImageUtils.encodePngImageToBase64(currentStateImage)+"')\">click here!</button");

                    // for report portal
                    String url = Users.getProperty(UsersProperty.s3fileupdaterurl) + "api/s3Screenshot/";
                    String contentForReportPortal = String.format("<form action='%s' method='post'><input type='hidden' name='path' value='%s'><input type='hidden' name='base64Content' value='%s'>Want to update the baseline image from ReportPortal?<button>Click here!</button></form>",
                            url,
                            S3.getPath(testName, screenshotName, width+"x"+height),
                            ImageUtils.encodePngImageToBase64(currentStateImage));
                    MultiReporter.reportRawHTML(contentForReportPortal);
                }
            } else {
                info("VisualTestingS3: baseline image NOT found in S3 - will save current status as baseline");
                MultiReporter.addImage("current", ImageUtils.encodePngImageToBase64(currentStateImage));
                S3.uploadScreenshot(testName, screenshotName, width+"x"+height, currentStateImage);
                info("VisualTestingS3: baseline image upload successful!");
            }
        } catch (IOException e) {
            warning("An error occur trying to obtain screenshot. Details: " + e, false);
        } finally {
            endTestLevel(false);
        }
    }
}