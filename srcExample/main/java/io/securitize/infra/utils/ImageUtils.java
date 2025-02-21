package io.securitize.infra.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static io.securitize.infra.reporting.MultiReporter.errorAndStop;
import static io.securitize.infra.reporting.MultiReporter.info;

public class ImageUtils {

    private static final double PIXEL_DIFF_TOLERANCE = 5;

    public static String base64PngToBase64Jpg(String base64Png) {
        return base64PngToBase64Jpg(base64Png, 0.5f);
    }

    public static String base64PngToBase64Jpg(String base64Png, float scaleFactor) {
        // base64 png to regular png image
        BufferedImage pngImage = decodeBase64ToImage(base64Png);

        // scale image to required size
        pngImage = scaleImageSize(pngImage, scaleFactor);

        // regular png image to base64 jpg
        return encodeToJpgString(pngImage);
    }

    private static BufferedImage decodeBase64ToImage(String base64Image) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(base64Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            errorAndStop("An error occur trying to convert base64 string to image. Details: " + e, false);
        }
        return image;
    }

    public static String encodePngImageToBase64(BufferedImage image) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            errorAndStop("An error occur trying to convert image to base64 string. Details: " + e, false);
            return null;
        }
    }

    private static String encodeToJpgString(BufferedImage image) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            errorAndStop("An error occur trying to convert image to base64 string. Details: " + e, false);
            return null;
        }
    }

    public static BufferedImage scaleImageSize(BufferedImage originalImage, float scale) {
        // calculate desired size
        int targetWidth = (int) (originalImage.getWidth() * scale);
        int targetHeight = (int) (originalImage.getHeight() * scale);

        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static String scaleImageSize(String base64Screenshot, float scale) {
        BufferedImage original = decodeBase64ToImage(base64Screenshot);
        BufferedImage scaled = scaleImageSize(original, scale);
        return encodePngImageToBase64(scaled);
    }

    public static ImageComparisonResult calculateImagesSimilarity(BufferedImage img1, BufferedImage img2) throws IOException {
        int w1 = img1.getWidth();
        int w2 = img2.getWidth();
        int h1 = img1.getHeight();
        int h2 = img2.getHeight();
        if ((w1 != w2) || (h1 != h2)) {
            errorAndStop(String.format("Both images should have same dimensions, this is not the case! Image1 width:%s height%s Image2 width:%s height:%s", w1, h1, w2, h2), false);
            return new ImageComparisonResult( -1,null);
        } else {
            long changedPixelsCount = 0;
            BufferedImage diffImage = new BufferedImage(w1, h2, img1.getType());
            for (int j = 0; j < h1; j++) {
                for (int i = 0; i < w1; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = img1.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = color1.getRed();
                    int g1 = color1.getGreen();
                    int b1 = color1.getBlue();
                    int pixel2 = img2.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = color2.getRed();
                    int g2 = color2.getGreen();
                    int b2 = color2.getBlue();
                    //sum of differences of RGB values of the two images
                    long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    if (data > PIXEL_DIFF_TOLERANCE) {
                        changedPixelsCount++;
                    }

                    // keep building the diff image according to these rules:
                    // 8 bits of the ALPHA component occupy the bit position from index 24 to index 31.
                    // 8 bits of the RED component occupy the bit position from index 16 to index 23
                    // 8 bits of the GREEN component occupy the bit position from index 8 to index 15.
                    // for BLUE component we DON'T have to left shift
                    int pixelColor;
                    if (data > 0) {
                        // the bigger the difference in color - the stronger red will be used
                        pixelColor = (int) ((color2.getAlpha() << 24) | (data << 16) | (0));
                    } else {
                        pixelColor = (color2.getAlpha() << 24) | (color2.getRed() << 16) | (color2.getGreen() << 8) | color2.getBlue();
                    }
                    diffImage.setRGB(i, j, pixelColor);
                }
            }
            double changedPixelPercent = (changedPixelsCount * 1.0d) / (w1 * h1);
            info("Changed pixels: " + changedPixelPercent + "%");
            return new ImageComparisonResult(changedPixelPercent, diffImage);
        }
    }
}