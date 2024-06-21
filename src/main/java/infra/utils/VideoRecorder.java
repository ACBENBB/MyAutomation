package infra.utils;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static infra.reporting.MultiReporter.info;
import static infra.reporting.MultiReporter.warning;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.VideoFormatKeys.*;
import static org.monte.media.VideoFormatKeys.QualityKey;

public class VideoRecorder {
    private static ScreenRecorder screenRecorder = null;

    public static int maxLocalOldVideosToKeep;

    public static String start() throws IOException, AWTException {
        File folder = new File("./videos");
        if (!folder.exists()) {
            if (folder.mkdir()) {
                info("local video folder created successfully");
            } else {
                warning("Unable to create local video folder.. skipping recording..");
                return null;
            }
        }

        // delete old videos if needed
        File[] oldVideoFiles = folder.listFiles();

        if (oldVideoFiles != null && oldVideoFiles.length > maxLocalOldVideosToKeep) {
            // sort files by order
            Arrays.sort(oldVideoFiles, Comparator.comparingLong(File::lastModified));

            // keep just the last maxLocalOldVideosToKeep files
            for (int i = 0; i < oldVideoFiles.length - maxLocalOldVideosToKeep ; i++) {
                info("Removing old video file: " + oldVideoFiles[i].getPath());
                if (!oldVideoFiles[i].delete()) {
                    warning("Unable to delete old local video file: " + oldVideoFiles[i].getPath());
                }
            }
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        screenRecorder = new ScreenRecorder(gc,
                captureSize,
                new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                        Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null,
                folder);

        try {
            screenRecorder.start();
            List<File> moviesFiles = screenRecorder.getCreatedMovieFiles();
            return moviesFiles.get(moviesFiles.size() - 1).getPath();
        } catch (IOException e) {
            warning("An error occur trying to start video recording. Details: " + e.toString(), false);
        }

        return null;
    }

    public static void stopRecord() throws Exception {
        screenRecorder.stop();
    }

    public static String getVideoFileName() {
        return screenRecorder.getCreatedMovieFiles().get(0).getPath();
    }
}
