package com.sergio.refacto.init;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.sergio.refacto.tools.ResourcesLoader;

public class LightLevelsInitializer {

    public static Map<Integer, BufferedImage> init() {
        Map<Integer, BufferedImage> lightLevels = new HashMap<>();

        for (int i = 0; i < 17; i++) {
            lightLevels.put(i, ResourcesLoader.loadImage("light/" + i + ".png"));
        }

        return lightLevels;
    }
}
