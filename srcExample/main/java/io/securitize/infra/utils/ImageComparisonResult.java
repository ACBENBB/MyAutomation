package io.securitize.infra.utils;

import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;

import java.awt.image.BufferedImage;

public class ImageComparisonResult {

    private static final double MAXIMUM_PIXEL_CHANGED_PERCENT = MainConfig.getDoubleProperty(MainConfigProperty.imageComparisonMaximumPixelChangedPercent);

    private final double changedPixelPercent;
    private final BufferedImage diffImage;

    public ImageComparisonResult(double changedPixelPercent, BufferedImage diffImage) {
        this.changedPixelPercent = changedPixelPercent;
        this.diffImage = diffImage;
    }

    public BufferedImage getDiffImage() {
        return this.diffImage;
    }

    public double getChangedPixelPercent() {
        return changedPixelPercent;
    }

    public boolean isSimilar() {
        return getChangedPixelPercent() < MAXIMUM_PIXEL_CHANGED_PERCENT;
    }
}