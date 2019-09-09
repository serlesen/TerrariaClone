package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.dto.Backgrounds;
import com.sergio.refacto.tools.ResourcesLoader;


public class BackgroundImagesInitializer {

    public static Map<Backgrounds, BufferedImage> init() {
        Map<Backgrounds, BufferedImage> backgroundImgs = new HashMap<>();

        for (Backgrounds background : Backgrounds.values()) {
            if (background == Backgrounds.EMPTY) {
                continue;
            }
            backgroundImgs.put(background, ResourcesLoader.loadImage("backgrounds/" + background.getFileName()));
        }
        return backgroundImgs;
    }
}
