package io.securitize.infra.visualtesting;

import io.securitize.infra.webdriver.Browser;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.utils.ImageComparisonResult;
import io.securitize.infra.utils.ImageUtils;
import io.securitize.tests.abstractClass.AbstractTest;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static io.securitize.infra.reporting.MultiReporter.*;

public class VisualTestingLocal extends AbstractVisualTesting {

    private static final String OUTPUT_PATH = "VisualTestingOutput";
    private static boolean isInitialized = false;

    public VisualTestingLocal(Browser browser) {
        super(browser);
        info("Initiating local visual testing");

        // make sure output folder exists
        File outputPath = new File(OUTPUT_PATH);
        if (outputPath.exists()) {
            isInitialized = true;
        } else {
            if (outputPath.mkdir()) {
                isInitialized = true;
            } else {
                warning("Unable to create VisualTesting output folder for local execution. Images will not be saved.", false);
            }
        }
    }

    @Override
    public void snapshot(String name) {
        if (!isInitialized) {
            warning("Not saving local visual testing snapshot '" + name + "' as not initialized");
            return;
        }

        String testName = AbstractTest.getTestFullyQualifiedName();

        browser.waitForPageStable(Duration.ofSeconds(5));
//        showRecordingMessage();

        try {
            File currentStateFile = browser.getScreenshotAsFile();
            BufferedImage currentStateImage = ImageIO.read(currentStateFile);
            int width = currentStateImage.getWidth();
            int height = currentStateImage.getHeight();

            String folderPathAsString = OUTPUT_PATH + File.separator + testName;
            File folderPath = new File(folderPathAsString);
            if (!folderPath.exists()) {
                if (!folderPath.mkdirs()) {
                    errorAndStop("An error occur trying to create VisualTestingLocal folder of: " + folderPathAsString, false);
                }
            }

            String pathTemplate = folderPathAsString + File.separator + name + "_" + width + "x" + height + "_%s.png";
            String baselinePath = String.format(pathTemplate, "baseline");
            String currentPath = String.format(pathTemplate, "current");
            String diffPath = String.format(pathTemplate, "diff");
            File baselineFile = new File(baselinePath);

            if (baselineFile.exists()) {
                info(String.format("VisualTestingLocal: baseline path of '%s' already exists - comparing current version with existing version", baselinePath));
                BufferedImage img2 = ImageIO.read(baselineFile);
                ImageComparisonResult comparisonResult = ImageUtils.calculateImagesSimilarity(currentStateImage, img2);
                if (comparisonResult.isSimilar()) {
                    info(String.format("Images are considered similar! (changedPixelPercent=%s%%)", comparisonResult.getChangedPixelPercent()));
                } else {
                    MultiReporter.startTestLevel("Image comparison: " + name);
                    // write the current image and the diff image
                    ImageIO.write(comparisonResult.getDiffImage(), "png", new File(diffPath));
                    FileUtils.copyFile(currentStateFile, new File(currentPath));
                    MultiReporter.addImage(name + "_diff", ImageUtils.encodePngImageToBase64(comparisonResult.getDiffImage()));
                    error(String.format("Images are not similar! Compare yourself - baseline:%s vs current:%s Diff image at:%s", baselinePath, currentPath, diffPath), false);
                    endTestLevel(false);
                }
            } else {
                FileUtils.copyFile(currentStateFile, baselineFile);
                info("VisualTestingLocal: snapshot baseline doesn't exist. Saved new baseline as: " + baselinePath);
            }
        } catch (IOException e) {
            warning("An error occur trying to obtain screenshot. Details: " + e, false);
        }
    }
}